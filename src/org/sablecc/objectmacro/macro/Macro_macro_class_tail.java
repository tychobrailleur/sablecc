/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.macro;

public class Macro_macro_class_tail {

    // parameter declarations
    private final String param_indent;

    // nested macro declarations

    // constructor
    Macro_macro_class_tail(
            String param_indent) {

        this.param_indent = param_indent;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.param_indent);
        sb.append("}");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

    // nested macros

}
