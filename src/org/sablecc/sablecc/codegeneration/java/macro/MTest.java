/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.sablecc.codegeneration.java.macro;

import java.util.*;

public class MTest {

    private final List<Object> eDefaultPackage_SpecifiedPackage = new LinkedList<Object>();

    public MTest() {

    }

    public MDefaultPackage newDefaultPackage(
            String pLanguageName) {

        MDefaultPackage lDefaultPackage = new MDefaultPackage(pLanguageName);
        this.eDefaultPackage_SpecifiedPackage.add(lDefaultPackage);
        return lDefaultPackage;
    }

    public MSpecifiedPackage newSpecifiedPackage(
            String pLanguageName,
            String pPackage) {

        MSpecifiedPackage lSpecifiedPackage = new MSpecifiedPackage(
                pLanguageName, pPackage);
        this.eDefaultPackage_SpecifiedPackage.add(lSpecifiedPackage);
        return lSpecifiedPackage;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(new MHeader().toString());
        if (this.eDefaultPackage_SpecifiedPackage.size() > 0) {
            sb.append(System.getProperty("line.separator"));
        }
        for (Object oDefaultPackage_SpecifiedPackage : this.eDefaultPackage_SpecifiedPackage) {
            sb.append(oDefaultPackage_SpecifiedPackage.toString());
        }
        sb.append(System.getProperty("line.separator"));
        sb.append("import java.io.*;");
        sb.append(System.getProperty("line.separator"));
        sb.append("public class Test {");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("  public static void main(String[] args)");
        sb.append(System.getProperty("line.separator"));
        sb.append("    throws Exception {");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("    Reader in;");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("    if(args.length > 0) {");
        sb.append(System.getProperty("line.separator"));
        sb.append("      in = new FileReader(args[0]);");
        sb.append(System.getProperty("line.separator"));
        sb.append("    }");
        sb.append(System.getProperty("line.separator"));
        sb.append("    else {");
        sb.append(System.getProperty("line.separator"));
        sb.append("      in = new InputStreamReader(System.in);");
        sb.append(System.getProperty("line.separator"));
        sb.append("    }");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("    Node syntaxTree = new Parser(in).parse();");
        sb.append(System.getProperty("line.separator"));
        sb.append("    syntaxTree.apply(new Walker() {");
        sb.append(System.getProperty("line.separator"));
        sb.append("      @Override");
        sb.append(System.getProperty("line.separator"));
        sb.append("      public void defaultOut(Node node) {");
        sb.append(System.getProperty("line.separator"));
        sb.append("        if(node instanceof Token) {");
        sb.append(System.getProperty("line.separator"));
        sb.append("          System.out.println(node.getText());");
        sb.append(System.getProperty("line.separator"));
        sb.append("        }");
        sb.append(System.getProperty("line.separator"));
        sb.append("      }");
        sb.append(System.getProperty("line.separator"));
        sb.append("    });");
        sb.append(System.getProperty("line.separator"));
        sb.append("  }");
        sb.append(System.getProperty("line.separator"));
        sb.append("}");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

}
