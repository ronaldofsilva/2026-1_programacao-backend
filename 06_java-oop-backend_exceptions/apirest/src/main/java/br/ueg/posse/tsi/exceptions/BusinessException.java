package br.ueg.posse.tsi.exceptions;

public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super("Erro: "+message);
    }
}
