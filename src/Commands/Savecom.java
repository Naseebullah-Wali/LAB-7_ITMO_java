package Commands;

import Collection.CollectionManager;
import common.Interaction.User;

public class Savecom extends AbstractCommand{
    CollectionManager collection;
    public Savecom(CollectionManager collectionManager) {
        super("save", "elements"," : Saving Collection");
        collection = collectionManager;
    }

//    public Savecom(CollectionManager collectionManager) {
//        super("save","Collecion saving",);
//    }


    @Override
    public boolean execute(String commandStringArgument, Object commandObjectArgument, User user) {
        collection.saveCollection();
        //answerMsg.addMsg("Сохранено");
        return true;

    }
}
