package common.Interaction;


import java.io.Serializable;
public class Request implements Serializable{
    // class for getting request
    private String commmandName;
    private String commandStringArgument;
    private Serializable commandObjectArgument;
    private User user;
    public Request (String commmandName, String commandStringArgument,Serializable commandObjectArgument, User user){
        this.commmandName=commmandName;
        this.commandStringArgument=commandStringArgument;
        this.commandObjectArgument=commandObjectArgument;
        this.user=user;
    }

    public Request(String commmandName, String commandStringArgument,User user) {
       this(commmandName,commandStringArgument,null,user);
    }
    public Request(User user){
        this("","", user);
    }

    public String getCommmandName() {
        return commmandName;
    }

    public String getCommandStringArgument() {
        return commandStringArgument;
    }

    public Serializable getCommandObjectArgument() {
        return commandObjectArgument;
    }
    public boolean isEmpty(){
        return commmandName.isEmpty() && commandStringArgument.isEmpty() && commandObjectArgument==null;
    }
    public User getUser(){
        return user;
    }

    @Override
    public String toString() {
        return "Request; [ " +
                  commmandName + "," +
                 commandStringArgument + "," +
                 commandObjectArgument +"]"+
                user+ "]"
                ;
    }
}
