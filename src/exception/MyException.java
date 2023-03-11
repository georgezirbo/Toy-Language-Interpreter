package exception;

public class MyException extends Exception {
    String msg;

    public MyException(String message){
        msg = message;
    }

    public String getMessage(){
        return msg;
    }
}
