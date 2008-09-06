/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.macro;

public class M_package
        extends Macro {

    private final String p_package_name;

    public M_package(
            String p_package_name) {

        this.p_package_name = p_package_name;
    }

    @Override
    public void appendTo(
            StringBuilder sb) {

        sb.append(System.getProperty("line.separator"));
        sb.append("package ");
        sb.append(this.p_package_name);
        sb.append(";");
        sb.append(System.getProperty("line.separator"));
    }

}
