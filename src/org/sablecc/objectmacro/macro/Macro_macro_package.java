/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.macro;

public class Macro_macro_package {

    // parameter declarations
    private final String param_name;

    // nested macro declarations

    // constructor
    Macro_macro_package(
            String param_name) {

        this.param_name = param_name;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("line.separator"));
        sb.append("package ");
        sb.append(this.param_name);
        sb.append(";");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

    // nested macros

}
