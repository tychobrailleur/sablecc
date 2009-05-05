/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.codegeneration.java.macro;

public class MParamField {

    private final String pName;

    private final MParamField mParamField = this;

    public MParamField(
            String pName) {

        this.pName = pName;
    }

    String pName() {

        return this.pName;
    }

    private String rName() {

        return this.mParamField.pName();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("  private final String p");
        sb.append(rName());
        sb.append(";");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

}