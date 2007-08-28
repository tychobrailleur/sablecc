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

import java.io.PushbackReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.sablecc.sablecc.exception.InvalidArgumentException;
import org.sablecc.sablecc.launcher.syntax3.lexer.Lexer;
import org.sablecc.sablecc.launcher.syntax3.node.Start;
import org.sablecc.sablecc.launcher.syntax3.parser.Parser;

/**
 * <code>Arguments</code> include option and text arguments.
 * 
 */
class Arguments {

    /** The list of option arguments. */
    private final List<OptionArgument> optionArguments;

    /** The list of text arguments. */
    private final List<TextArgument> textArguments;

    /**
     * Constructs a new instance of Arguments. It does so by sorting the given
     * string array between option and text arguments.
     * 
     * @param args
     *            The string array that will be compute.
     * @throws InvalidArgumentException
     *             if one of the option or text arguments is invalid.
     */
    Arguments(
            String[] args)
            throws InvalidArgumentException {

        List<OptionArgument> optionArguments = new LinkedList<OptionArgument>();

        List<TextArgument> textArguments = new LinkedList<TextArgument>();

        int currentArgIndex = 0;

        // process options, until a text argument is found
        while (currentArgIndex < args.length && textArguments.size() == 0) {

            try {
                Start ast = new Parser(new Lexer(new PushbackReader(
                        new StringReader(args[currentArgIndex]), 1024)))
                        .parse();

                Option incompleteOption = ArgumentExtractor.extractArguments(
                        ast, optionArguments, textArguments);

                if (incompleteOption != null) {

                    if (currentArgIndex + 1 >= args.length) {

                        if (args[currentArgIndex].startsWith("--")) {
                            throw new InvalidArgumentException("option --"
                                    + incompleteOption.getLongName()
                                    + " is missing a "
                                    + incompleteOption.getOperandName()
                                    + " operand");
                        }
                        else {
                            throw new InvalidArgumentException("option -"
                                    + incompleteOption.getShortName()
                                    + " is missing a "
                                    + incompleteOption.getOperandName()
                                    + " operand");
                        }
                    }

                    optionArguments.add(new OptionArgument(incompleteOption,
                            args[++currentArgIndex]));
                }
            }
            catch (InvalidArgumentException e) {
                throw new InvalidArgumentException("invalid argument \""
                        + args[currentArgIndex] + "\": " + e.getMessage(), e);
            }
            catch (Exception e) {
                throw new InvalidArgumentException("invalid argument \""
                        + args[currentArgIndex] + "\"", e);
            }

            currentArgIndex++;
        }

        // process remaining text arguments
        while (currentArgIndex < args.length) {

            textArguments.add(new TextArgument(args[currentArgIndex]));

            currentArgIndex++;
        }

        this.optionArguments = Collections.unmodifiableList(optionArguments);
        this.textArguments = Collections.unmodifiableList(textArguments);
    }

    /**
     * Returns the list of option arguments of this <code>Arguments</code>.
     * 
     * @return the list of option arguments.
     */
    List<OptionArgument> getOptionArguments() {

        return this.optionArguments;
    }

    /**
     * Returns the list of text arguments of this <code>Arguments</code>.
     * 
     * @return the list of text arguments.
     */
    List<TextArgument> getTextArguments() {

        return this.textArguments;
    }

}