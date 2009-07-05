/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.codegeneration.c.macro;

import java.util.LinkedList;
import java.util.List;

public class MTextC {

    private final MFile mFile;

    private final List<Object> eParamParam_AncestorParam = new LinkedList<Object>();

    private final List<Object> eParamConstructorInit_SelfRefC_AncestorConstructorInit = new LinkedList<Object>();

    private final List<Object> eParam = new LinkedList<Object>();

    private final List<Object> eParamRef = new LinkedList<Object>();

    private final List<Object> eStringPart_EolPart_ParamInsertPart_TextInsertPart = new LinkedList<Object>();

    MTextC(
            MFile mFile) {

        if (mFile == null) {
            throw new NullPointerException();
        }
        this.mFile = mFile;
    }

    public MParamParam newParamParam(
            String pName) {

        MParamParam lParamParam = new MParamParam(pName);
        this.eParamParam_AncestorParam.add(lParamParam);
        return lParamParam;
    }

    public MAncestorParam newAncestorParam(
            String pName) {

        MAncestorParam lAncestorParam = new MAncestorParam(pName);
        this.eParamParam_AncestorParam.add(lAncestorParam);
        return lAncestorParam;
    }

    public MParamConstructorInit newParamConstructorInit(
            String pName) {

        MParamConstructorInit lParamConstructorInit = new MParamConstructorInit(
                pName, this.mFile);
        this.eParamConstructorInit_SelfRefC_AncestorConstructorInit
                .add(lParamConstructorInit);
        return lParamConstructorInit;
    }

    public MSelfRefC newSelfRefC() {

        MSelfRefC lSelfRefC = new MSelfRefC(this.mFile);
        this.eParamConstructorInit_SelfRefC_AncestorConstructorInit
                .add(lSelfRefC);
        return lSelfRefC;
    }

    public MAncestorConstructorInit newAncestorConstructorInit(
            String pName) {

        MAncestorConstructorInit lAncestorConstructorInit = new MAncestorConstructorInit(
                pName, this.mFile);
        this.eParamConstructorInit_SelfRefC_AncestorConstructorInit
                .add(lAncestorConstructorInit);
        return lAncestorConstructorInit;
    }

    public MParam newParam(
            String pName) {

        MParam lParam = new MParam(pName, this.mFile);
        this.eParam.add(lParam);
        return lParam;
    }

    public MParamRef newParamRef(
            String pName,
            String pContext) {

        MParamRef lParamRef = new MParamRef(pName, pContext, this.mFile);
        this.eParamRef.add(lParamRef);
        return lParamRef;
    }

    public MStringPart newStringPart(
            String pString) {

        MStringPart lStringPart = new MStringPart(pString);
        this.eStringPart_EolPart_ParamInsertPart_TextInsertPart
                .add(lStringPart);
        return lStringPart;
    }

    public MEolPart newEolPart() {

        MEolPart lEolPart = new MEolPart();
        this.eStringPart_EolPart_ParamInsertPart_TextInsertPart.add(lEolPart);
        return lEolPart;
    }

    public MParamInsertPart newParamInsertPart(
            String pName) {

        MParamInsertPart lParamInsertPart = new MParamInsertPart(pName,
                this.mFile);
        this.eStringPart_EolPart_ParamInsertPart_TextInsertPart
                .add(lParamInsertPart);
        return lParamInsertPart;
    }

    public MTextInsertPart newTextInsertPart() {

        MTextInsertPart lTextInsertPart = new MTextInsertPart(this.mFile);
        this.eStringPart_EolPart_ParamInsertPart_TextInsertPart
                .add(lTextInsertPart);
        return lTextInsertPart;
    }

    private String rFileName() {

        return this.mFile.pFileName();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(new MHeader().toString());
        sb.append(System.getProperty("line.separator"));
        sb.append("#include \"M");
        sb.append(rFileName());
        sb.append(".h\"");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("M");
        sb.append(rFileName());
        sb.append("* M");
        sb.append(rFileName());
        sb.append("_init(");
        {
            boolean first = true;
            for (Object oParamParam_AncestorParam : this.eParamParam_AncestorParam) {
                if (first) {
                    first = false;
                }
                else {
                    sb.append(", ");
                }
                sb.append(oParamParam_AncestorParam.toString());
            }
        }
        sb.append(") {");
        sb.append(System.getProperty("line.separator"));
        sb.append("  M");
        sb.append(rFileName());
        sb.append("* m");
        sb.append(rFileName());
        sb.append(" = (M");
        sb.append(rFileName());
        sb.append("*)malloc(sizeof(M");
        sb.append(rFileName());
        sb.append("));");
        sb.append(System.getProperty("line.separator"));
        sb.append("  m");
        sb.append(rFileName());
        sb.append("->toString = &M");
        sb.append(rFileName());
        sb.append("_toString;");
        sb.append(System.getProperty("line.separator"));
        sb.append("  m");
        sb.append(rFileName());
        sb.append("->free = &M");
        sb.append(rFileName());
        sb.append("_free;");
        sb.append(System.getProperty("line.separator"));
        for (Object oParamConstructorInit_SelfRefC_AncestorConstructorInit : this.eParamConstructorInit_SelfRefC_AncestorConstructorInit) {
            sb.append(oParamConstructorInit_SelfRefC_AncestorConstructorInit
                    .toString());
        }
        sb.append("  return m");
        sb.append(rFileName());
        sb.append(";");
        sb.append(System.getProperty("line.separator"));
        sb.append("}");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("void M");
        sb.append(rFileName());
        sb.append("_free(M");
        sb.append(rFileName());
        sb.append("* m");
        sb.append(rFileName());
        sb.append(") {");
        sb.append(System.getProperty("line.separator"));
        sb.append("  free(m");
        sb.append(rFileName());
        sb.append(");");
        sb.append(System.getProperty("line.separator"));
        sb.append("}");
        sb.append(System.getProperty("line.separator"));
        if (this.eParam.size() > 0) {
            sb.append(System.getProperty("line.separator"));
        }
        {
            boolean first = true;
            for (Object oParam : this.eParam) {
                if (first) {
                    first = false;
                }
                else {
                    sb.append(System.getProperty("line.separator"));
                }
                sb.append(oParam.toString());
            }
        }
        if (this.eParamRef.size() > 0) {
            sb.append(System.getProperty("line.separator"));
        }
        {
            boolean first = true;
            for (Object oParamRef : this.eParamRef) {
                if (first) {
                    first = false;
                }
                else {
                    sb.append(System.getProperty("line.separator"));
                }
                sb.append(oParamRef.toString());
            }
        }
        sb.append(System.getProperty("line.separator"));
        sb.append("//@Override");
        sb.append(System.getProperty("line.separator"));
        sb.append("char* M");
        sb.append(rFileName());
        sb.append("_toString(M");
        sb.append(rFileName());
        sb.append("* m");
        sb.append(rFileName());
        sb.append(") {");
        sb.append(System.getProperty("line.separator"));
        sb.append("  int sizeString = 1;");
        sb.append(System.getProperty("line.separator"));
        sb.append("  List* listString = List_init();");
        sb.append(System.getProperty("line.separator"));
        for (Object oStringPart_EolPart_ParamInsertPart_TextInsertPart : this.eStringPart_EolPart_ParamInsertPart_TextInsertPart) {
            sb.append(oStringPart_EolPart_ParamInsertPart_TextInsertPart
                    .toString());
        }
        sb.append(System.getProperty("line.separator"));
        sb.append("  char* str = (char*)calloc(sizeString, sizeof(char));");
        sb.append(System.getProperty("line.separator"));
        sb.append("  Node* temp = listString->_first_;");
        sb.append(System.getProperty("line.separator"));
        sb.append("  while(temp != NULL) {");
        sb.append(System.getProperty("line.separator"));
        sb.append("    strcat(str, temp->_elem_);");
        sb.append(System.getProperty("line.separator"));
        sb.append("    temp = temp->_next_;");
        sb.append(System.getProperty("line.separator"));
        sb.append("  }");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("  List_free(listString);");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        sb.append("  return str;");
        sb.append(System.getProperty("line.separator"));
        sb.append("}");
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

}
