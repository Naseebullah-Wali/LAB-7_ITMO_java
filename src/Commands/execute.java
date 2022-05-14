//package Commands;
//
//import Collection.CollectionManager;
//import Interaction.Response;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//
//public class execute extends AbstractCommand{
//    private CollectionManager cm;
//
//    public execute (CollectionManager cm){
//        this.cm=cm;
//    }
//    public boolean execute(String arg){
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arg))) {
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                if (!line.split(" ")[0].equals("srcexecute_script")) {
//                    System.out.println(line);
//                  //  cm.execute(line, this);
//                } else {
//                    System.out.println("Invalid command");
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("File not found, please, input existent file");
//        }
//        return true;
//    }
//
//    @Override
//    public Response execute(String stringArgument, Object objectArgument) {
//        return null;
//    }
//}
//
//
