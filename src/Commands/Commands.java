package Commands;

import common.Interaction.User;

public interface Commands {
    String getName();

    String getUsage();

    String getDescription();

    boolean execute(String commandStringArgument, Object commandObjectArgument, User user);
}

//public interface Commands {
//    boolean execute(String stringArgument, Object objectArgument);
//
//
//}
