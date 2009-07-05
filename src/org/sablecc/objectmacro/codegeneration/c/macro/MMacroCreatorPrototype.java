/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.codegeneration.c.macro;

import java.util.LinkedList;
import java.util.List;

public class MMacroCreatorPrototype {

    private final String pName;

    private final MMacroCreatorPrototype mMacroCreatorPrototype = this;

    private final MFile mFile;

    private final List<Object> eParamParam = new LinkedList<Object>();

    MMacroCreatorPrototype(
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

    public MParamParam newParamParam(
            String pName) {

        MParamParam lParamParam = new MParamParam(pName);
        this.eParamParam.add(lParamParam);
        return lParamParam;
    }

    String pName() {

        return this.pName;
    }

    private String rName() {

        return this.mMacroCreatorPrototype.pName();
    }

    private String rFileName() {

        return this.mFile.pFileName();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("struct M");
        sb.append(rName());
        sb.append("* M");
        sb.append(rFileName());
        sb.append("_new");
        sb.append(rName());
        sb.append("(M");
        sb.append(rFileName());
        sb.append("* m");
        sb.append(rFileName());
        if (this.eParamParam.size() > 0) {
            sb.append(", ");
        }
        {
            boolean first = true;
            for (Object oParamParam : this.eParamParam) {
                if (first) {
                    first = false;
                }
                else {
                    sb.append(", ");
                }
                sb.append(oParamParam.toString());
            }
        }
        sb.append(");");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

}
