package inf.unideb.hu.exception.customer;

public class UnknownCustomerException extends  Exception{

    public UnknownCustomerException(){

    }

    public UnknownCustomerException(String messsage){
        super(messsage);
    }
}