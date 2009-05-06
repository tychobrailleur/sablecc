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

package org.sablecc.objectmacro.launcher;

import static org.sablecc.objectmacro.launcher.Version.VERSION;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.PushbackReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.sablecc.exception.InternalException;
import org.sablecc.objectmacro.codegeneration.CodeGenerator;
import org.sablecc.objectmacro.codegeneration.IntermediateRepresentation;
import org.sablecc.objectmacro.codegeneration.intermediate.IntermediateCodeGenerator;
import org.sablecc.objectmacro.codegeneration.java.JavaCodeGenerator;
import org.sablecc.objectmacro.codegeneration.scala.ScalaCodeGenerator;
import org.sablecc.objectmacro.errormessages.MInternalError;
import org.sablecc.objectmacro.errormessages.MLexicalError;
import org.sablecc.objectmacro.errormessages.MSyntaxError;
import org.sablecc.objectmacro.exception.CompilerException;
import org.sablecc.objectmacro.intermediate.syntax3.node.AEolMacroPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.AEolTextPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.AExpandInsert;
import org.sablecc.objectmacro.intermediate.syntax3.node.AExpandInsertMacroPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.AExpandedMacro;
import org.sablecc.objectmacro.intermediate.syntax3.node.AFalseBoolean;
import org.sablecc.objectmacro.intermediate.syntax3.node.AIntermediateRepresentation;
import org.sablecc.objectmacro.intermediate.syntax3.node.AMacro;
import org.sablecc.objectmacro.intermediate.syntax3.node.AParamInsertMacroPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.AParamInsertTextPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.AParamInsertValue;
import org.sablecc.objectmacro.intermediate.syntax3.node.AParamRef;
import org.sablecc.objectmacro.intermediate.syntax3.node.AStringMacroPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.AStringTextPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.AStringValue;
import org.sablecc.objectmacro.intermediate.syntax3.node.AText;
import org.sablecc.objectmacro.intermediate.syntax3.node.ATextInsert;
import org.sablecc.objectmacro.intermediate.syntax3.node.ATextInsertMacroPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.ATextInsertTextPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.ATextInsertValue;
import org.sablecc.objectmacro.intermediate.syntax3.node.ATrueBoolean;
import org.sablecc.objectmacro.intermediate.syntax3.node.PBoolean;
import org.sablecc.objectmacro.intermediate.syntax3.node.PExpandedMacro;
import org.sablecc.objectmacro.intermediate.syntax3.node.PIntermediateRepresentation;
import org.sablecc.objectmacro.intermediate.syntax3.node.PMacro;
import org.sablecc.objectmacro.intermediate.syntax3.node.PMacroPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.PParamRef;
import org.sablecc.objectmacro.intermediate.syntax3.node.PText;
import org.sablecc.objectmacro.intermediate.syntax3.node.PTextInsert;
import org.sablecc.objectmacro.intermediate.syntax3.node.PTextPart;
import org.sablecc.objectmacro.intermediate.syntax3.node.PValue;
import org.sablecc.objectmacro.intermediate.syntax3.node.TString;
import org.sablecc.objectmacro.structures.Expand;
import org.sablecc.objectmacro.structures.ExpandSignature;
import org.sablecc.objectmacro.structures.GlobalIndex;
import org.sablecc.objectmacro.structures.Macro;
import org.sablecc.objectmacro.structures.Param;
import org.sablecc.objectmacro.structures.Scope;
import org.sablecc.objectmacro.structures.TextBlock;
import org.sablecc.objectmacro.structures.TextInsert;
import org.sablecc.objectmacro.syntax3.lexer.LexerException;
import org.sablecc.objectmacro.syntax3.node.AEolMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.AEolTextBlockBodyPart;
import org.sablecc.objectmacro.syntax3.node.AEscapeMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.AEscapeStringPart;
import org.sablecc.objectmacro.syntax3.node.AEscapeTextBlockBodyPart;
import org.sablecc.objectmacro.syntax3.node.AExpandMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.AMacroMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.AString;
import org.sablecc.objectmacro.syntax3.node.AStringStaticValue;
import org.sablecc.objectmacro.syntax3.node.ATextBlockMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.ATextBlockTextBlockBodyPart;
import org.sablecc.objectmacro.syntax3.node.ATextInsertMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.ATextInsertStaticValue;
import org.sablecc.objectmacro.syntax3.node.ATextInsertTextBlockBodyPart;
import org.sablecc.objectmacro.syntax3.node.ATextMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.ATextStringPart;
import org.sablecc.objectmacro.syntax3.node.ATextTextBlockBodyPart;
import org.sablecc.objectmacro.syntax3.node.AVarMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.AVarStaticValue;
import org.sablecc.objectmacro.syntax3.node.AVarTextBlockBodyPart;
import org.sablecc.objectmacro.syntax3.node.PMacroBodyPart;
import org.sablecc.objectmacro.syntax3.node.PStaticValue;
import org.sablecc.objectmacro.syntax3.node.PString;
import org.sablecc.objectmacro.syntax3.node.PStringPart;
import org.sablecc.objectmacro.syntax3.node.PTextBlockBodyPart;
import org.sablecc.objectmacro.syntax3.node.Start;
import org.sablecc.objectmacro.syntax3.node.TText;
import org.sablecc.objectmacro.syntax3.parser.Parser;
import org.sablecc.objectmacro.syntax3.parser.ParserException;
import org.sablecc.objectmacro.util.Utils;
import org.sablecc.objectmacro.walkers.CyclicTextBlockDetector;
import org.sablecc.objectmacro.walkers.DeclarationCollector;
import org.sablecc.objectmacro.walkers.ExpandCollector;
import org.sablecc.objectmacro.walkers.TextInsertCollector;
import org.sablecc.objectmacro.walkers.VarVerifier;
import org.sablecc.util.Strictness;
import org.sablecc.util.Verbosity;

/**
 * The main class of ObjectMacro.
 */
public class ObjectMacro {

    /** Prevents instantiation of this class. */
    private ObjectMacro() {

        throw new InternalException("this class may not have instances");
    }

    /** Launches ObjectMacro. */
    public static void main(
            String[] args) {

        try {
            compile(args);
        }
        catch (CompilerException e) {
            System.err.print(e.getMessage());
            System.err.flush();
            System.exit(1);
        }
        catch (ParserException e) {
            int start = e.getMessage().indexOf(' ');
            System.err.print(new MSyntaxError(e.getToken().getLine() + "", e
                    .getToken().getPos()
                    + "", e.getToken().getClass().getSimpleName().substring(1)
                    .toLowerCase(), e.getToken().getText(), e.getMessage()
                    .substring(start)));
            System.err.flush();
            System.exit(1);
        }
        catch (LexerException e) {
            int start = e.getMessage().indexOf('[') + 1;
            int end = e.getMessage().indexOf(',');
            String line = e.getMessage().substring(start, end);

            start = e.getMessage().indexOf(',') + 1;
            end = e.getMessage().indexOf(']');
            String pos = e.getMessage().substring(start, end);

            start = e.getMessage().indexOf(' ') + 1;

            System.err.print(new MLexicalError(line, pos, e.getMessage()
                    .substring(start)));
            System.err.flush();
            System.exit(1);
        }
        catch (InternalException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            System.err.print(new MInternalError(sw.toString(), e.getMessage()));
            System.err.flush();
            System.exit(1);
        }
        catch (Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            System.err.print(new MInternalError(sw.toString(), e.getMessage()));
            System.err.flush();
            System.exit(1);
        }

        // finish gracefully
        System.exit(0);
    }

    /**
     * Parses the provided arguments and launches macro compilation.
     */
    public static void compile(
            String[] arguments)
            throws ParserException, LexerException {

        // default target is java
        String targetLanguage = "java";

        // default destination directory is current working directory
        File destinationDirectory = new File(System.getProperty("user.dir"));

        // default destination package is anonymous
        String destinationPackage = "";

        // default option values
        boolean generateCode = true;
        Verbosity verbosity = Verbosity.INFORMATIVE;
        Strictness strictness = Strictness.STRICT;

        // parse command line arguments
        ArgumentCollection argumentCollection = new ArgumentCollection(
                arguments);

        // handle option arguments
        for (OptionArgument optionArgument : argumentCollection
                .getOptionArguments()) {

            switch (optionArgument.getOption()) {

            case LIST_TARGETS:
                System.out.println("Available targets:");
                System.out.println(" java (default)");
                System.out.println(" scala");
                System.out.println(" intermediate");
                return;

            case TARGET:
                targetLanguage = optionArgument.getOperand();
                break;

            case DESTINATION:
                destinationDirectory = new File(optionArgument.getOperand());
                break;

            case PACKAGE:
                destinationPackage = optionArgument.getOperand();
                break;

            case GENERATE:
                generateCode = true;
                break;

            case NO_CODE:
                generateCode = false;
                break;

            case LENIENT:
                strictness = Strictness.LENIENT;
                break;

            case STRICT:
                strictness = Strictness.STRICT;
                break;

            case QUIET:
                verbosity = Verbosity.QUIET;
                break;

            case INFORMATIVE:
                verbosity = Verbosity.INFORMATIVE;
                break;

            case VERBOSE:
                verbosity = Verbosity.VERBOSE;
                break;

            case VERSION:
                System.out.println("ObjectMacro, part of SableCC version "
                        + VERSION);
                return;

            case HELP:
                System.out.println("Usage: objectmacro "
                        + Option.getShortHelpMessage() + " file.objectmacro");
                System.out.println("Options:");
                System.out.println(Option.getLongHelpMessage());
                return;

            default:
                throw new InternalException("unhandled option "
                        + optionArgument.getOption());
            }
        }

        switch (verbosity) {
        case INFORMATIVE:
        case VERBOSE:
            System.out.println();
            System.out.println("ObjectMacro, part of SableCC version "
                    + VERSION);
            System.out
                    .println("by Etienne M. Gagnon <egagnon@j-meg.com> and other contributors.");
            System.out.println();
            break;
        }

        // handle text arguments
        if (argumentCollection.getTextArguments().size() == 0) {
            System.out.println("Usage: objectmacro "
                    + Option.getShortHelpMessage() + " file.objectmacro");
            return;
        }
        else if (argumentCollection.getTextArguments().size() > 1) {
            throw CompilerException.invalidArgumentCount();
        }

        // check target
        if (!(targetLanguage.equals("java")
                || targetLanguage.equals("intermediate") || targetLanguage
                .equals("scala"))) {
            throw CompilerException.unknownTarget(targetLanguage);
        }

        // check argument
        TextArgument textArgument = argumentCollection.getTextArguments()
                .get(0);

        if (!textArgument.getText().endsWith(".objectmacro")) {
            throw CompilerException.invalidSuffix(textArgument.getText());
        }

        File macroFile = new File(textArgument.getText());

        if (!macroFile.exists()) {
            throw CompilerException.missingMacroFile(textArgument.getText());
        }

        if (!macroFile.isFile()) {
            throw CompilerException.macroNotFile(textArgument.getText());
        }

        compile(macroFile, targetLanguage, destinationDirectory,
                destinationPackage, generateCode, strictness, verbosity);
    }

    /**
     * Compiles the provided macro file.
     */
    private static void compile(
            File macroFile,
            String targetLanguage,
            File destinationDirectory,
            String destinationPackage,
            boolean generateCode,
            Strictness strictness,
            Verbosity verbosity)
            throws ParserException, LexerException {

        switch (verbosity) {
        case INFORMATIVE:
        case VERBOSE:
            System.out.println("Compiling \"" + macroFile + "\"");
            break;
        }

        Start ast;

        try {
            FileReader fr = new FileReader(macroFile);
            BufferedReader br = new BufferedReader(fr);
            PushbackReader pbr = new PushbackReader(br, 1024);

            switch (verbosity) {
            case VERBOSE:
                System.out.println(" Parsing");
                break;
            }

            ast = new Parser(new CustomLexer(pbr)).parse();

            pbr.close();
            br.close();
            fr.close();
        }
        catch (IOException e) {
            throw CompilerException.inputError(macroFile.toString(), e);
        }

        GlobalIndex globalIndex = verifySemantics(ast, strictness, verbosity);

        processSemantics(globalIndex, verbosity);

        PIntermediateRepresentation intermediateAST = generateIntermediateAST(
                globalIndex, verbosity);

        IntermediateRepresentation ir = new IntermediateRepresentation(
                intermediateAST, macroFile, destinationDirectory,
                destinationPackage);

        CodeGenerator codeGenerator;

        if (targetLanguage.equals("java")) {
            codeGenerator = new JavaCodeGenerator(ir);
        }
        else if (targetLanguage.equals("intermediate")) {
            codeGenerator = new IntermediateCodeGenerator(ir);
        }
        else if (targetLanguage.equals("scala")) {
            codeGenerator = new ScalaCodeGenerator(ir);
        }
        else {
            throw new InternalException("unhandled case");
        }

        switch (verbosity) {
        case VERBOSE:
            System.out.println(" Verifying target-specific semantics");
            break;
        }

        codeGenerator.verifyTargetSpecificSemantics(strictness);

        if (generateCode) {
            switch (verbosity) {
            case VERBOSE:
                System.out.println(" Generating code");
                break;
            }

            codeGenerator.generateCode();
        }
    }

    private static GlobalIndex verifySemantics(
            Start ast,
            Strictness strictness,
            Verbosity verbosity) {

        switch (verbosity) {
        case VERBOSE:
            System.out.println(" Verifying semantics");
            break;
        }

        GlobalIndex globalIndex = new GlobalIndex();

        ast.apply(new DeclarationCollector(globalIndex));
        ast.apply(new ExpandCollector(globalIndex));
        ast.apply(new TextInsertCollector(globalIndex));
        ast.apply(new VarVerifier(globalIndex));

        globalIndex.computeIndirectlyReferencedTextBlocks();

        for (Macro macro : globalIndex.getAllMacros()) {
            for (TextBlock textBlock : macro
                    .getIndirectlyReferencedTextBlocks()) {
                textBlock.setReachable();
            }
        }

        ast.apply(new CyclicTextBlockDetector(globalIndex));

        if (strictness == Strictness.STRICT) {
            for (TextBlock textBlock : globalIndex.getAllTextBlocks()) {
                if (!textBlock.isReachable()) {
                    throw CompilerException.unusedTextBlock(textBlock);
                }
            }

            for (Macro macro : globalIndex.getAllMacros()) {
                for (Param param : macro.getParams()) {
                    if (!param.isUsed()) {
                        throw CompilerException.unusedParam(param);
                    }
                }
            }

            for (TextBlock textBlock : globalIndex.getAllTextBlocks()) {
                for (Param param : textBlock.getParams()) {
                    if (!param.isUsed()) {
                        throw CompilerException.unusedParam(param);
                    }
                }
            }
        }

        return globalIndex;
    }

    private static void processSemantics(
            GlobalIndex globalIndex,
            Verbosity verbosity) {

        switch (verbosity) {
        case VERBOSE:
            System.out.println(" Processing semantics");
            break;
        }

        for (Macro macro : globalIndex.getAllMacros()) {
            macro.computeImplicitExpansion();
        }

        boolean modified;
        do {
            modified = false;

            for (TextBlock textBlock : globalIndex.getAllTextBlocks()) {
                if (textBlock.propagateAncestorReferences()) {
                    modified = true;
                }
            }

            for (Macro macro : globalIndex.getAllMacros()) {
                if (macro.propagateAncestorReferences()) {
                    modified = true;
                }
            }
        }
        while (modified);
    }

    private static PIntermediateRepresentation generateIntermediateAST(
            GlobalIndex globalIndex,
            Verbosity verbosity) {

        switch (verbosity) {
        case VERBOSE:
            System.out.println(" Creating intermediate structure");
            break;
        }

        List<PText> texts = new LinkedList<PText>();

        for (TextBlock textBlock : globalIndex.getAllTextBlocks()) {

            texts.add(createText(textBlock));
        }

        List<PMacro> macros = new LinkedList<PMacro>();

        for (Macro macro : globalIndex.getAllMacros()) {

            macros.add(createMacro(macro));
        }

        return new AIntermediateRepresentation(texts, macros);
    }

    private static AText createText(
            TextBlock textBlock) {

        TString name = new TString("'" + textBlock.getCamelCaseName() + "'");

        List<TString> params = new LinkedList<TString>();
        for (Param param : textBlock.getParams()) {
            params.add(new TString("'" + param.getCamelCaseName() + "'"));
        }

        PBoolean self_ref;
        if (textBlock.referencesSelf()) {
            self_ref = new ATrueBoolean();
        }
        else {
            self_ref = new AFalseBoolean();
        }

        List<TString> ancestor_refs = new LinkedList<TString>();
        for (Scope referencedAncestor : textBlock.getReferencedAncestors()) {
            TString ancestorName = new TString("'"
                    + referencedAncestor.getCamelCaseName() + "'");
            ancestor_refs.add(ancestorName);
        }

        List<PParamRef> param_refs = new LinkedList<PParamRef>();
        for (Param referencedParam : textBlock.getReferencedParams()) {
            TString paramName = new TString("'"
                    + referencedParam.getCamelCaseName() + "'");
            TString scopeName = new TString("'"
                    + referencedParam.getScope().getCamelCaseName() + "'");
            param_refs.add(new AParamRef(paramName, scopeName));
        }

        List<PTextPart> text_parts = new LinkedList<PTextPart>();
        {
            StringBuilder textBuilder = null;

            for (PTextBlockBodyPart pTextBlockBodyPart : textBlock
                    .getDeclaration().getParts()) {

                if (pTextBlockBodyPart instanceof ATextTextBlockBodyPart) {
                    ATextTextBlockBodyPart textBlockBodyPart = (ATextTextBlockBodyPart) pTextBlockBodyPart;
                    if (textBuilder == null) {
                        textBuilder = new StringBuilder();
                    }
                    TText textToken = textBlockBodyPart.getText();
                    for (char c : textToken.getText().toCharArray()) {
                        if (c == '\'') {
                            textBuilder.append("\\'");
                        }
                        else {
                            textBuilder.append(c);
                        }
                    }
                }
                else if (pTextBlockBodyPart instanceof AEolTextBlockBodyPart) {
                    if (textBuilder != null) {
                        text_parts.add(new AStringTextPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                    text_parts.add(new AEolTextPart());
                }
                else if (pTextBlockBodyPart instanceof AEscapeTextBlockBodyPart) {
                    AEscapeTextBlockBodyPart textBlockBodyPart = (AEscapeTextBlockBodyPart) pTextBlockBodyPart;
                    if (textBuilder == null) {
                        textBuilder = new StringBuilder();
                    }
                    if (textBlockBodyPart.getEscape().getText().charAt(1) == '$') {
                        textBuilder.append("$");
                    }
                    else {
                        textBuilder.append("\\\\");
                    }
                }
                else if (pTextBlockBodyPart instanceof AVarTextBlockBodyPart) {
                    AVarTextBlockBodyPart textBlockBodyPart = (AVarTextBlockBodyPart) pTextBlockBodyPart;
                    if (textBuilder != null) {
                        text_parts.add(new AStringTextPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                    text_parts.add(new AParamInsertTextPart(new TString("'"
                            + Utils.toCamelCase(textBlockBodyPart.getVar())
                            + "'")));
                }
                else if (pTextBlockBodyPart instanceof ATextInsertTextBlockBodyPart) {
                    ATextInsertTextBlockBodyPart textBlockBodyPart = (ATextInsertTextBlockBodyPart) pTextBlockBodyPart;
                    if (textBuilder != null) {
                        text_parts.add(new AStringTextPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                    TextInsert textInsert = textBlock
                            .getTextInsert(textBlockBodyPart.getTextInsert());
                    text_parts.add(new ATextInsertTextPart(createTextInsert(
                            textInsert, textBlock)));
                }
                else {
                    if (!(pTextBlockBodyPart instanceof ATextBlockTextBlockBodyPart)) {
                        throw new InternalException("unhandled case");
                    }
                    if (textBuilder != null) {
                        text_parts.add(new AStringTextPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                }
            }

            if (textBuilder != null) {
                text_parts.add(new AStringTextPart(new TString("'"
                        + textBuilder.toString() + "'")));
                textBuilder = null;
            }
        }

        return new AText(name, params, self_ref, ancestor_refs, param_refs,
                text_parts);
    }

    private static PTextInsert createTextInsert(
            TextInsert textInsert,
            Scope context) {

        TString name = new TString("'"
                + textInsert.getInsertedTextBlock().getCamelCaseName() + "'");

        List<PValue> args = new LinkedList<PValue>();
        for (PStaticValue staticValue : textInsert.getDeclaration()
                .getStaticValues()) {
            args.add(createValue(staticValue, context));
        }

        List<TString> ancestor_refs = new LinkedList<TString>();
        for (Scope ancestor : textInsert.getInsertedTextBlock()
                .getReferencedAncestors()) {
            ancestor_refs.add(new TString("'" + ancestor.getCamelCaseName()
                    + "'"));
        }

        return new ATextInsert(name, args, ancestor_refs);
    }

    private static PValue createValue(
            PStaticValue pStaticValue,
            Scope context) {

        if (pStaticValue instanceof ATextInsertStaticValue) {
            ATextInsertStaticValue staticValue = (ATextInsertStaticValue) pStaticValue;

            TextInsert textInsert = context.getTextInsert(staticValue
                    .getTextInsert());
            return new ATextInsertValue(createTextInsert(textInsert, context));
        }
        else if (pStaticValue instanceof AVarStaticValue) {
            AVarStaticValue staticValue = (AVarStaticValue) pStaticValue;

            return new AParamInsertValue(new TString("'"
                    + Utils.toCamelCase(staticValue.getVar()) + "'"));
        }
        else if (pStaticValue instanceof AStringStaticValue) {
            AStringStaticValue staticValue = (AStringStaticValue) pStaticValue;

            return new AStringValue(createString(staticValue.getString()));
        }
        else {
            throw new InternalException("unhandled case");
        }
    }

    private static TString createString(
            PString pString) {

        AString string = (AString) pString;
        StringBuilder textBuilder = new StringBuilder();

        for (PStringPart pStringPart : string.getParts()) {
            if (pStringPart instanceof ATextStringPart) {
                ATextStringPart stringPart = (ATextStringPart) pStringPart;

                String text = stringPart.getText().getText();
                for (char c : text.toCharArray()) {
                    if (c == '\'') {
                        textBuilder.append("\\'");
                    }
                    else {
                        textBuilder.append(c);
                    }
                }
            }
            else if (pStringPart instanceof AEscapeStringPart) {
                AEscapeStringPart stringPart = (AEscapeStringPart) pStringPart;

                if (stringPart.getEscape().getText().charAt(1) == '$') {
                    textBuilder.append("$");
                }
                else if (stringPart.getEscape().getText().charAt(1) == '\"') {
                    textBuilder.append("\"");
                }
                else {
                    textBuilder.append("\\\\");
                }
            }
            else {
                throw new InternalException("unhandled case");
            }
        }

        return new TString("'" + textBuilder.toString() + "'");
    }

    private static AMacro createMacro(
            Macro macro) {

        TString name = new TString("'" + macro.getCamelCaseName() + "'");

        PBoolean is_public;
        if (macro.getParent() == null) {
            is_public = new ATrueBoolean();
        }
        else {
            is_public = new AFalseBoolean();
        }

        List<TString> params = new LinkedList<TString>();
        for (Param param : macro.getParams()) {
            params.add(new TString("'" + param.getCamelCaseName() + "'"));
        }

        PBoolean self_ref;
        if (macro.referencesSelf()) {
            self_ref = new ATrueBoolean();
        }
        else {
            self_ref = new AFalseBoolean();
        }

        List<TString> ancestor_refs = new LinkedList<TString>();
        for (Scope referencedAncestor : macro.getReferencedAncestors()) {
            TString ancestorName = new TString("'"
                    + referencedAncestor.getCamelCaseName() + "'");
            ancestor_refs.add(ancestorName);
        }

        List<PParamRef> param_refs = new LinkedList<PParamRef>();
        for (Param referencedParam : macro.getReferencedParams()) {
            TString paramName = new TString("'"
                    + referencedParam.getCamelCaseName() + "'");
            TString scopeName = new TString("'"
                    + referencedParam.getScope().getCamelCaseName() + "'");
            param_refs.add(new AParamRef(paramName, scopeName));
        }

        List<TString> expands = new LinkedList<TString>();
        for (ExpandSignature signature : macro.getExpandSignatures()) {
            expands.add(new TString("'" + signature.toCamelCase() + "'"));
        }

        List<PExpandedMacro> expanded_macros = new LinkedList<PExpandedMacro>();
        for (Macro implicitlyExpandedMacro : macro
                .getImplicitlyExpandedMacros()) {
            TString macroName = new TString("'"
                    + implicitlyExpandedMacro.getCamelCaseName() + "'");
            List<TString> macroParams = new LinkedList<TString>();
            for (Param param : implicitlyExpandedMacro.getParams()) {
                macroParams.add(new TString("'" + param.getCamelCaseName()
                        + "'"));
            }
            List<TString> ancestors = new LinkedList<TString>();
            for (Scope ancestor : implicitlyExpandedMacro
                    .getReferencedAncestors()) {
                TString ancestorName = new TString("'"
                        + ancestor.getCamelCaseName() + "'");
                ancestors.add(ancestorName);
            }
            List<TString> macroExpands = new LinkedList<TString>();
            for (ExpandSignature signature : macro.getExpandSignatures()) {
                if (signature.getMacroSet().contains(implicitlyExpandedMacro)) {
                    macroExpands.add(new TString("'" + signature.toCamelCase()
                            + "'"));
                }
            }
            expanded_macros.add(new AExpandedMacro(macroName, macroParams,
                    ancestors, macroExpands));
        }
        for (Macro explicitlyExpandedMacro : macro
                .getExplicitlyExpandedMacros()) {
            TString macroName = new TString("'"
                    + explicitlyExpandedMacro.getCamelCaseName() + "'");
            List<TString> macroParams = new LinkedList<TString>();
            for (Param param : explicitlyExpandedMacro.getParams()) {
                macroParams.add(new TString("'" + param.getCamelCaseName()
                        + "'"));
            }
            List<TString> ancestors = new LinkedList<TString>();
            for (Scope ancestor : explicitlyExpandedMacro
                    .getReferencedAncestors()) {
                TString ancestorName = new TString("'"
                        + ancestor.getCamelCaseName() + "'");
                ancestors.add(ancestorName);
            }
            List<TString> macroExpands = new LinkedList<TString>();
            for (ExpandSignature signature : macro.getExpandSignatures()) {
                if (signature.getMacroSet().contains(explicitlyExpandedMacro)) {
                    macroExpands.add(new TString("'" + signature.toCamelCase()
                            + "'"));
                }
            }
            expanded_macros.add(new AExpandedMacro(macroName, macroParams,
                    ancestors, macroExpands));
        }

        List<PMacroPart> macro_parts = new LinkedList<PMacroPart>();
        {
            StringBuilder textBuilder = null;

            for (PMacroBodyPart pMacroBodyPart : macro.getDeclaration()
                    .getParts()) {
                if (pMacroBodyPart instanceof ATextMacroBodyPart) {
                    ATextMacroBodyPart macroBodyPart = (ATextMacroBodyPart) pMacroBodyPart;
                    if (textBuilder == null) {
                        textBuilder = new StringBuilder();
                    }
                    TText textToken = macroBodyPart.getText();
                    for (char c : textToken.getText().toCharArray()) {
                        if (c == '\'') {
                            textBuilder.append("\\'");
                        }
                        else {
                            textBuilder.append(c);
                        }
                    }
                }
                else if (pMacroBodyPart instanceof AEolMacroBodyPart) {
                    if (textBuilder != null) {
                        macro_parts.add(new AStringMacroPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                    macro_parts.add(new AEolMacroPart());
                }
                else if (pMacroBodyPart instanceof AEscapeMacroBodyPart) {
                    AEscapeMacroBodyPart macroBodyPart = (AEscapeMacroBodyPart) pMacroBodyPart;
                    if (textBuilder == null) {
                        textBuilder = new StringBuilder();
                    }
                    char c = macroBodyPart.getEscape().getText().charAt(1);
                    if (c == '\\') {
                        textBuilder.append("\\\\");
                    }
                    else {
                        textBuilder.append(c);
                    }
                }
                else if (pMacroBodyPart instanceof AVarMacroBodyPart) {
                    AVarMacroBodyPart macroBodyPart = (AVarMacroBodyPart) pMacroBodyPart;
                    if (textBuilder != null) {
                        macro_parts.add(new AStringMacroPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                    macro_parts
                            .add(new AParamInsertMacroPart(new TString("'"
                                    + Utils.toCamelCase(macroBodyPart.getVar())
                                    + "'")));
                }
                else if (pMacroBodyPart instanceof ATextInsertMacroBodyPart) {
                    ATextInsertMacroBodyPart macroBodyPart = (ATextInsertMacroBodyPart) pMacroBodyPart;
                    if (textBuilder != null) {
                        macro_parts.add(new AStringMacroPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                    TextInsert textInsert = macro.getTextInsert(macroBodyPart
                            .getTextInsert());
                    macro_parts.add(new ATextInsertMacroPart(createTextInsert(
                            textInsert, macro)));
                }
                else if (pMacroBodyPart instanceof AExpandMacroBodyPart) {
                    AExpandMacroBodyPart macroBodyPart = (AExpandMacroBodyPart) pMacroBodyPart;
                    if (textBuilder != null) {
                        macro_parts.add(new AStringMacroPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                    Expand expand = macro.getExpand(macroBodyPart.getExpand());
                    TString expandName = new TString("'"
                            + expand.getSignature().toCamelCase() + "'");
                    PValue none = null;
                    PValue separator = null;
                    PValue before_first = null;
                    PValue after_last = null;

                    if (expand.getNone() != null) {
                        none = createValue(expand.getNone().getStaticValue(),
                                macro);
                    }

                    if (expand.getSeparator() != null) {
                        separator = createValue(expand.getSeparator()
                                .getStaticValue(), macro);
                    }

                    if (expand.getBeforeFirst() != null) {
                        before_first = createValue(expand.getBeforeFirst()
                                .getStaticValue(), macro);
                    }

                    if (expand.getAfterLast() != null) {
                        after_last = createValue(expand.getAfterLast()
                                .getStaticValue(), macro);
                    }

                    macro_parts.add(new AExpandInsertMacroPart(
                            new AExpandInsert(expandName, none, separator,
                                    before_first, after_last)));
                }
                else if (pMacroBodyPart instanceof AMacroMacroBodyPart) {
                    AMacroMacroBodyPart macroBodyPart = (AMacroMacroBodyPart) pMacroBodyPart;
                    if (textBuilder != null) {
                        macro_parts.add(new AStringMacroPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                    Macro subMacro = macro
                            .getMacro(((org.sablecc.objectmacro.syntax3.node.AMacro) macroBodyPart
                                    .getMacro()).getName());
                    if (subMacro.isImplicitlyExpanded()) {
                        TString expandName = new TString("'"
                                + subMacro.getImplicitSignature().toCamelCase()
                                + "'");
                        macro_parts.add(new AExpandInsertMacroPart(
                                new AExpandInsert(expandName, null, null, null,
                                        null)));
                    }
                }
                else {
                    if (!(pMacroBodyPart instanceof ATextBlockMacroBodyPart)) {
                        throw new InternalException("unhandled case");
                    }
                    if (textBuilder != null) {
                        macro_parts.add(new AStringMacroPart(new TString("'"
                                + textBuilder.toString() + "'")));
                        textBuilder = null;
                    }
                }
            }

            if (textBuilder != null) {
                macro_parts.add(new AStringMacroPart(new TString("'"
                        + textBuilder.toString() + "'")));
                textBuilder = null;
            }
        }

        return new AMacro(name, is_public, params, self_ref, ancestor_refs,
                param_refs, expands, expanded_macros, macro_parts);
    }
}
