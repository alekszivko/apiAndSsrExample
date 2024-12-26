package at.spengergasse.sj2324seedproject.exceptions;


public class ProducerException extends Exception{

    public ProducerException(String message, Throwable cause){
        super(message, cause);
    }

    public ProducerException(String message){
        super(message);
    }
}
