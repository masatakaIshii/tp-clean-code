package fr.esgi.masa.tpcleancode.core;

import fr.esgi.masa.tpcleancode.core.use_case.SeeContent;
import fr.esgi.masa.tpcleancode.core.use_case.LibraryAction;

import java.util.List;
import java.util.Map;


public class Library {
    private Map<String, LibraryAction> actions;
    private SeeContent seeContent;

    public Library(Map<String, LibraryAction> actions, SeeContent seeContent) {
        this.actions = actions;
        this.seeContent = seeContent;
    }

    public void start(List<String> arguments) {
        var actionName = arguments.get(0);
        if (actionName.equals("seeContent")) {
            this.seeContent.execute();
            return;
        }
        otherActions(arguments);
    }

    private void otherActions(List<String> arguments) {
        var action = actions.get(arguments.get(0));
        if (actionIsUndefinedOrArgumentsAreLessThan3(arguments, action)) {
            throw new IllegalArgumentException();
        }
        try {
            action.execute(arguments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean actionIsUndefinedOrArgumentsAreLessThan3(List<String> arguments, LibraryAction action) {
        return action == null || arguments.size() < 3;
    }
}
