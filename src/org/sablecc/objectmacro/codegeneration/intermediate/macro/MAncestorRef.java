/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.codegeneration.intermediate.macro;

public class MAncestorRef {

    private final String pName;

    private final String pIndent;

    private final MAncestorRef mAncestorRef = this;

    public MAncestorRef(
            String pName,
            String pIndent) {

        if (pName == null) {
            throw new NullPointerException();
        }
        this.pName = pName;
        if (pIndent == null) {
            throw new NullPointerException();
        }
        this.pIndent = pIndent;
    }

    String pName() {

        return this.pName;
    }

    String pIndent() {

        return this.pIndent;
    }

    private String rIndent() {

        return this.mAncestorRef.pIndent();
    }

    private String rName() {

        return this.mAncestorRef.pName();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(rIndent());
        sb.append("  ancestor_ref = ");
        sb.append(rName());
        sb.append(";");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

}
