package manageme.testutil;

import static manageme.logic.parser.CliSyntax.PREFIX_NAME;

import manageme.logic.commands.module.AddModuleCommand;
import manageme.logic.commands.module.EditModuleCommand;
import manageme.model.module.Module;

public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddModuleCommand(Module module) {
        return AddModuleCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + module.getName().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditModuleDescriptor}'s details.
     */
    public static String getEditModuleDescriptorDetails(EditModuleCommand.EditModuleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.value).append(" "));
        return sb.toString();
    }
}
