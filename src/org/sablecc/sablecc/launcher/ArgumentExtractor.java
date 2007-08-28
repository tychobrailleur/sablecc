/* This file is part of SableCC ( http://sablecc.org ).
 *
 * See the NOTICE file distributed with this work for copyright information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sablecc.sablecc.launcher;

import java.util.List;

import org.sablecc.sablecc.exception.InternalException;
import org.sablecc.sablecc.exception.InvalidArgumentException;
import org.sablecc.sablecc.exception.InvalidArgumentRuntimeException;
import org.sablecc.sablecc.launcher.syntax3.analysis.DepthFirstAdapter;
import org.sablecc.sablecc.launcher.syntax3.node.ALongOption;
import org.sablecc.sablecc.launcher.syntax3.node.AOperand;
import org.sablecc.sablecc.launcher.syntax3.node.APlainArgument;
import org.sablecc.sablecc.launcher.syntax3.node.AShortOption;
import org.sablecc.sablecc.launcher.syntax3.node.Start;

/**
 * 
 * TODO
 * 
 */
class ArgumentExtractor
        extends DepthFirstAdapter {

    /** The list of option arguments of this argument extractor. */
    private final List<OptionArgument> optionArguments;

    /** The list of text arguments of this argument extractor. */
    private final List<TextArgument> textArguments;

    /** An incomplete option. */
    private Option incompleteOption;

    /**
     * Constructs a new argument extractor.
     * 
     * @param optionArguments
     *            the list of option arguments
     * @param textArguments
     *            the list of text arguments
     * @throws InternalException
     *             if the list of option arguments or the list of text arguments
     *             is <code>null</code>.
     */
    private ArgumentExtractor(
            final List<OptionArgument> optionArguments,
            final List<TextArgument> textArguments) {

        if (optionArguments == null) {
            throw new InternalException("optionArguments may not be null");
        }

        if (textArguments == null) {
            throw new InternalException("textArguments may not be null");
        }

        this.optionArguments = optionArguments;
        this.textArguments = textArguments;
    }

    /**
     * Adds a plain argument to the list of text arguments
     * 
     * @param node
     *            the node containing the plain argument.
     */
    @Override
    public void caseAPlainArgument(
            APlainArgument node) {

        this.textArguments.add(new TextArgument(node.getText().getText()));
    }

    /**
     * Treats and adds a long option to the list of option arguments.
     * 
     * @param node
     *            the node containing the long option.
     * @throws InvalidArgumentRuntimeException
     *             if the long option is invalid or has an unexpected operand.
     */
    @Override
    public void caseALongOption(
            ALongOption node)
            throws InvalidArgumentRuntimeException {

        String longName = node.getLongName().getText();

        // make sure option is valid
        Option option = Option.getLongOption(longName);

        if (option == null) {
            throw new InvalidArgumentRuntimeException("invalid option: --"
                    + longName);
        }

        // expects an operand?
        if (option.hasOperand()) {
            // yes

            AOperand operand = (AOperand) node.getOperand();

            // is it there?
            if (operand != null) {
                // yes

                if (operand.getOperandText() != null) {
                    this.optionArguments.add(new OptionArgument(option, operand
                            .getOperandText().getText()));
                }
                else {
                    this.optionArguments.add(new OptionArgument(option, ""));
                }
            }
            else {
                // no, we have an incomplete option
                this.incompleteOption = option;
            }
        }
        else {
            // no

            if (node.getOperand() != null) {
                throw new InvalidArgumentRuntimeException("option --"
                        + longName + " does not expect an operand");
            }

            this.optionArguments.add(new OptionArgument(option, null));
        }
    }

    /**
     * Treats and adds a short option to the list of option arguments.
     * 
     * @param node
     *            the node containing the short option.
     * @throws InvalidArgumentRuntimeException
     *             if the previous option needed an operand, if the short option
     *             is invalid or has an unexpected operand.
     */
    @Override
    public void caseAShortOption(
            AShortOption node) {

        if (this.incompleteOption != null) {
            throw new InvalidArgumentRuntimeException("option -"
                    + this.incompleteOption.getShortName() + " is missing a "
                    + this.incompleteOption.getOperandName() + " operand");
        }

        String shortName = node.getShortName().getText();

        // make sure option is valid
        Option option = Option.getShortOption(shortName);

        if (option == null) {
            throw new InvalidArgumentRuntimeException("invalid option: -"
                    + shortName);
        }

        // expects an operand?
        if (option.hasOperand()) {
            // yes

            AOperand operand = (AOperand) node.getOperand();

            // is it there?
            if (operand != null) {
                // yes

                if (operand.getOperandText() != null) {
                    this.optionArguments.add(new OptionArgument(option, operand
                            .getOperandText().getText()));
                }
                else {
                    this.optionArguments.add(new OptionArgument(option, ""));
                }
            }
            else {
                // no, we have an incomplete option
                this.incompleteOption = option;
            }
        }
        else {
            // no

            if (node.getOperand() != null) {
                throw new InvalidArgumentRuntimeException("option -"
                        + shortName + " does not expect an operand");
            }

            this.optionArguments.add(new OptionArgument(option, null));
        }

    }

    /**
     * Extracts all the arguments from an ast.
     * 
     * @param ast
     *            the abstract syntax tree.
     * 
     * @param optionArguments
     *            the list of option arguments already extracted.
     * @param textArguments
     *            the list of text arguments already extracted.
     * @return an incomplete option.
     * 
     * @throws InvalidArgumentRuntimeException
     *             if the lists are corrupted.
     */
    static Option extractArguments(
            Start ast,
            List<OptionArgument> optionArguments,
            List<TextArgument> textArguments)
            throws InvalidArgumentException {

        ArgumentExtractor extractor = new ArgumentExtractor(optionArguments,
                textArguments);

        try {
            ast.apply(extractor);
        }
        catch (InvalidArgumentRuntimeException e) {
            throw new InvalidArgumentException(e.getMessage(), e);
        }

        return extractor.incompleteOption;
    }
}