/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.objectmacro.macro;

public class M_text_insert_append
        extends Macro {

    // ---- constructor ----

    M_text_insert_append(
            String p_text_insert_name) {

        this.p_text_insert_name = p_text_insert_name;
    }

    // ---- parent ----

    @Override
    Macro get_parent() {

        return null;
    }

    // ---- parameters ----

    private final String p_text_insert_name;

    String get_local_p_text_insert_name() {

        return this.p_text_insert_name;
    }

    // ---- parameter accessors ----

    private String cached_p_text_insert_name;

    private String get_p_text_insert_name() {

        String result = this.cached_p_text_insert_name;

        if (result == null) {
            Macro current = this;

            while (!(current instanceof M_text_insert_append)) {
                current = current.get_parent();
            }

            result = ((M_text_insert_append) current)
                    .get_local_p_text_insert_name();
            this.cached_p_text_insert_name = result;
        }

        return result;
    }

    // ---- appendTo ----

    @Override
    public void appendTo(
            StringBuilder sb) {

        sb.append("    get_t_");

        sb.append(get_p_text_insert_name());

        sb.append("().appendTo(sb);");

        sb.append(EOL);
    }

}
