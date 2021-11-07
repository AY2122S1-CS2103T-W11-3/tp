package manageme.logic.commands.link;

import static java.util.Objects.requireNonNull;
import static manageme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static manageme.logic.parser.CliSyntax.PREFIX_MODULE;
import static manageme.logic.parser.CliSyntax.PREFIX_NAME;
import static manageme.model.Model.PREDICATE_SHOW_ALL_LINKS;

import java.util.List;
import java.util.Optional;

import manageme.commons.core.Messages;
import manageme.commons.core.index.Index;
import manageme.commons.util.CollectionUtil;
import manageme.logic.commands.Command;
import manageme.logic.commands.CommandResult;
import manageme.logic.commands.exceptions.CommandException;
import manageme.model.Model;
import manageme.model.Name;
import manageme.model.TagModule;
import manageme.model.link.Link;
import manageme.model.link.LinkAddress;
import manageme.model.module.Module;

/**
 * Edits the details of an existing link in the address book.
 */
public class EditLinkCommand extends Command {

    public static final String COMMAND_WORD = "editLink";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the link identified "
            + "by the index number used in the displayed link list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "LINK ADDRESS] "
            + "[" + PREFIX_MODULE + "ASSOCIATED_MODULE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "CS2100 "
            + PREFIX_NAME + "CS2100 Exam Zoom link";

    public static final String MESSAGE_EDIT_LINK_SUCCESS = "Edited Link: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LINK = "This link already exists in the address book.";
    public static final String MESSAGE_NONEXISTENT_MODULE = "The module you have associated does not exist";

    private final Index index;
    private final EditLinkDescriptor editLinkDescriptor;

    /**
     * @param index of the link in the filtered link list to edit
     * @param editLinkDescriptor details to edit the link with
     */
    public EditLinkCommand(Index index, EditLinkDescriptor editLinkDescriptor) {
        requireNonNull(index);
        requireNonNull(editLinkDescriptor);

        this.index = index;
        this.editLinkDescriptor = new EditLinkDescriptor(editLinkDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Link> lastShownList = model.getFilteredLinkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LINK_DISPLAYED_INDEX);
        }

        Link linkToEdit = lastShownList.get(index.getZeroBased());
        Link editedLink = createEditedLink(linkToEdit, editLinkDescriptor);

        if (!linkToEdit.isSame(editedLink) && model.has(editedLink)) {
            throw new CommandException(MESSAGE_DUPLICATE_LINK);
        }

        //if there is a module being associated, check it exists
        if (!editedLink.getLinkModule().value.isEmpty()) {
            if (!model.has(new Module(new Name(editedLink.getLinkModule().value)))) {
                throw new CommandException(MESSAGE_NONEXISTENT_MODULE);
            }
        }

        model.set(linkToEdit, editedLink);
        model.updateFilteredLinkList(PREDICATE_SHOW_ALL_LINKS);
        return new CommandResult(String.format(MESSAGE_EDIT_LINK_SUCCESS, editedLink));
    }

    /**
     * Creates and returns a {@code Link} with the details of {@code linkToEdit}
     * edited with {@code editLinkDescriptor}.
     */
    private static Link createEditedLink(Link linkToEdit, EditLinkDescriptor editLinkDescriptor) {
        assert linkToEdit != null;

        Name updatedName = editLinkDescriptor.getName().orElse(linkToEdit.getName());
        LinkAddress updatedAddress = editLinkDescriptor.getAddress().orElse(linkToEdit.getAddress());
        TagModule updatedModule = editLinkDescriptor.getLinkModule().orElse(linkToEdit.getLinkModule());

        return new Link(updatedName, updatedAddress, updatedModule);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditLinkCommand)) {
            return false;
        }

        // state check
        EditLinkCommand e = (EditLinkCommand) other;
        return index.equals(e.index)
                && editLinkDescriptor.equals(e.editLinkDescriptor);
    }

    /**
     * Stores the details to edit the link with. Each non-empty field value will replace the
     * corresponding field value of the link.
     */
    public static class EditLinkDescriptor {
        private Name name;
        private LinkAddress address;
        private TagModule module;

        public EditLinkDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditLinkDescriptor(EditLinkDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
            setModule(toCopy.module);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address, module);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAddress(LinkAddress address) {
            this.address = address;
        }

        public Optional<LinkAddress> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setModule(TagModule module) {
            this.module = module;
        }

        public Optional<TagModule> getLinkModule() {
            return Optional.ofNullable(module);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLinkDescriptor)) {
                return false;
            }

            // state check
            EditLinkDescriptor e = (EditLinkDescriptor) other;

            return getName().equals(e.getName())
                    && getAddress().equals(e.getAddress())
                    && getLinkModule().equals(e.getLinkModule());
        }
    }
}
