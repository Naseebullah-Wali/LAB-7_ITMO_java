package common.Exceptions;

public class CommandUsageException extends Exception{
    public CommandUsageException(){
        super();
    }
    public CommandUsageException(String msg){
        super(msg);
    }
}
