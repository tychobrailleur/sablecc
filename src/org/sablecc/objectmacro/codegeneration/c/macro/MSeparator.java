/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.codegeneration.c.macro;

import java.util.LinkedList;
import java.util.List;

public class MSeparator {

    private final MFile mFile;

    private final MExpandInsertPart mExpandInsertPart;

    private final List<Object> eBrace = new LinkedList<Object>();

    private final List<Object> eTextInsert = new LinkedList<Object>();

    private final List<Object> eInlineText_ParamInsert_TextInsertString = new LinkedList<Object>();

    private final List<Object> eTextInsertDestructor = new LinkedList<Object>();

    private final List<Object> eBraceEnd = new LinkedList<Object>();

    MSeparator(
            MFile mFile,
            MExpandInsertPart mExpandInsertPart) {

        if (mFile == null) {
            throw new NullPointerException();
        }
        this.mFile = mFile;
        if (mExpandInsertPart == null) {
            throw new NullPointerException();
        }
        this.mExpandInsertPart = mExpandInsertPart;
    }

    public MBrace newBrace() {

        MBrace lBrace = new MBrace();
        this.eBrace.add(lBrace);
        return lBrace;
    }

    public MTextInsert newTextInsert(
            String pName) {

        MTextInsert lTextInsert = new MTextInsert(pName, this.mFile);
        this.eTextInsert.add(lTextInsert);
        return lTextInsert;
    }

    public MInlineText newInlineText() {

        MInlineText lInlineText = new MInlineText();
        this.eInlineText_ParamInsert_TextInsertString.add(lInlineText);
        return lInlineText;
    }

    public MParamInsert newParamInsert(
            String pName) {

        MParamInsert lParamInsert = new MParamInsert(pName, this.mFile);
        this.eInlineText_ParamInsert_TextInsertString.add(lParamInsert);
        return lParamInsert;
    }

    public MTextInsertString newTextInsertString(
            String pName) {

        MTextInsertString lTextInsertString = new MTextInsertString(pName);
        this.eInlineText_ParamInsert_TextInsertString.add(lTextInsertString);
        return lTextInsertString;
    }

    public MTextInsertDestructor newTextInsertDestructor(
            String pName) {

        MTextInsertDestructor lTextInsertDestructor = new MTextInsertDestructor(
                pName);
        this.eTextInsertDestructor.add(lTextInsertDestructor);
        return lTextInsertDestructor;
    }

    public MBraceEnd newBraceEnd() {

        MBraceEnd lBraceEnd = new MBraceEnd();
        this.eBraceEnd.add(lBraceEnd);
        return lBraceEnd;
    }

    private String rFileName() {

        return this.mFile.pFileName();
    }

    private String rName() {

        return this.mExpandInsertPart.pName();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("  {");
        sb.append(System.getProperty("line.separator"));
        sb.append("    int first = 1;");
        sb.append(System.getProperty("line.separator"));
        sb.append("    Node* temp = m");
        sb.append(rFileName());
        sb.append("->_e");
        sb.append(rName());
        sb.append("_->_first_;");
        sb.append(System.getProperty("line.separator"));
        sb.append("    while(temp != NULL) {");
        sb.append(System.getProperty("line.separator"));
        sb.append("      if(first)");
        sb.append(System.getProperty("line.separator"));
        sb.append("        first = 0;");
        sb.append(System.getProperty("line.separator"));
        sb.append("      else ");
        for (Object oBrace : this.eBrace) {
            sb.append(oBrace.toString());
        }
        sb.append("      ");
        if (this.eTextInsert.size() == 0) {
            sb.append(System.getProperty("line.separator"));
        }
        for (Object oTextInsert : this.eTextInsert) {
            sb.append(oTextInsert.toString());
        }
        sb.append("        sizeString += List_pushback(listString, strdup(");
        if (this.eInlineText_ParamInsert_TextInsertString.size() == 0) {
            sb.append("\"\"");
        }
        for (Object oInlineText_ParamInsert_TextInsertString : this.eInlineText_ParamInsert_TextInsertString) {
            sb.append(oInlineText_ParamInsert_TextInsertString.toString());
        }
        sb.append("));");
        sb.append(System.getProperty("line.separator"));
        sb.append("      ");
        for (Object oTextInsertDestructor : this.eTextInsertDestructor) {
            sb.append(oTextInsertDestructor.toString());
        }
        sb.append("      ");
        for (Object oBraceEnd : this.eBraceEnd) {
            sb.append(oBraceEnd.toString());
        }
        sb.append(System.getProperty("line.separator"));
        sb.append("      struct AbstractMacro* Mtemp = temp->_elem_;");
        sb.append(System.getProperty("line.separator"));
        sb
                .append("      sizeString += List_pushback(listString, Mtemp->toString(Mtemp));");
        sb.append(System.getProperty("line.separator"));
        sb.append("      temp = temp->_next_;");
        sb.append(System.getProperty("line.separator"));
        sb.append("    }");
        sb.append(System.getProperty("line.separator"));
        sb.append("  }");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

}
