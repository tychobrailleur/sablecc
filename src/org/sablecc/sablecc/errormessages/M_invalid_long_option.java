/* This file was generated by SableCC's ObjectMacro. */

package org.sablecc.sablecc.errormessages;

public class M_invalid_long_option
        extends Macro {

    // ---- constructor ----

    public M_invalid_long_option(
            String p_option_name) {

        this.p_option_name = p_option_name;
    }

    // ---- parent ----

    @Override
    Macro get_parent() {

        return null;
    }

    // ---- parameters ----

    private final String p_option_name;

    String get_local_p_option_name() {

        return this.p_option_name;
    }

    // ---- parameter accessors ----

    private String cached_p_option_name;

    private String get_p_option_name() {

        String result = this.cached_p_option_name;

        if (result == null) {
            Macro current = this;

            while (!(current instanceof M_invalid_long_option)) {
                current = current.get_parent();
            }

            result = ((M_invalid_long_option) current)
                    .get_local_p_option_name();
            this.cached_p_option_name = result;
        }

        return result;
    }

    // ---- text block accessors ----

    private T_command_line_error_head cached_t_command_line_error_head;

    private T_command_line_error_head get_t_command_line_error_head() {

        T_command_line_error_head result = this.cached_t_command_line_error_head;

        if (result == null) {
            result = T_command_line_error_head.getInstance();
            this.cached_t_command_line_error_head = result;
        }

        return result;
    }

    private T_command_line_error_tail cached_t_command_line_error_tail;

    private T_command_line_error_tail get_t_command_line_error_tail() {

        T_command_line_error_tail result = this.cached_t_command_line_error_tail;

        if (result == null) {
            result = T_command_line_error_tail.getInstance();
            this.cached_t_command_line_error_tail = result;
        }

        return result;
    }

    // ---- appendTo ----

    @Override
    public void appendTo(
            StringBuilder sb) {

        get_t_command_line_error_head().appendTo(sb);
        sb.append(EOL);
        sb.append("The following option is rejected:");
        sb.append(EOL);
        sb.append(" --");
        sb.append(get_p_option_name());
        sb.append(EOL);
        sb.append("It is not a valid option.");
        sb.append(EOL);
        sb.append(EOL);
        get_t_command_line_error_tail().appendTo(sb);
    }

}
