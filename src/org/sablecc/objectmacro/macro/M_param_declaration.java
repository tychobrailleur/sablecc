/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.macro;

public class M_param_declaration
        extends Macro {

    // ---- constructor ----

    M_param_declaration(
            Macro parent,
            String p_param_name) {

        this.parent = parent;
        this.p_param_name = p_param_name;
    }

    // ---- parent ----

    private final Macro parent;

    @Override
    Macro get_parent() {

        return this.parent;
    }

    // ---- parameters ----

    private final String p_param_name;

    String get_local_p_param_name() {

        return this.p_param_name;
    }

    // ---- parameter accessors ----

    private String cached_p_param_name;

    private String get_p_param_name() {

        String result = this.cached_p_param_name;

        if (result == null) {
            Macro current = this;

            while (!(current instanceof M_param_declaration)) {
                current = current.get_parent();
            }

            result = ((M_param_declaration) current).get_local_p_param_name();
            this.cached_p_param_name = result;
        }

        return result;
    }

    // ---- appendTo ----

    @Override
    public void appendTo(
            StringBuilder sb) {

        sb.append("  private final String p_");

        sb.append(get_p_param_name());

        sb.append(";");

        sb.append(EOL);

        sb.append(EOL);

        sb.append("  String get_local_p_");

        sb.append(get_p_param_name());

        sb.append("() {");

        sb.append(EOL);

        sb.append("    return this.p_");

        sb.append(get_p_param_name());

        sb.append(";");

        sb.append(EOL);

        sb.append("  }");

        sb.append(EOL);

        sb.append(EOL);
    }

}
