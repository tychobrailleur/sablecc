/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.codegeneration.c.macro;

public class MParamInsertPart {

    private final String pName;

    private final MParamInsertPart mParamInsertPart = this;

    private final MFile mFile;

    MParamInsertPart(
            String pName,
            MFile mFile) {

        if (pName == null) {
            throw new NullPointerException();
        }
        this.pName = pName;
        if (mFile == null) {
            throw new NullPointerException();
        }
        this.mFile = mFile;
    }

    String pName() {

        return this.pName;
    }

    private String rFileName() {

        return this.mFile.pFileName();
    }

    private String rName() {

        return this.mParamInsertPart.pName();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("  sizeString += List_pushback(listString, strdup(M");
        sb.append(rFileName());
        sb.append("_r");
        sb.append(rName());
        sb.append("(m");
        sb.append(rFileName());
        sb.append(")));");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

}
