package br.ueg.posse.tsi.exceptions;

public class PeopleNotFoundException extends RuntimeException {
    public PeopleNotFoundException(String id){
        super("Pessoa não encontrada com o id: "+ id);
    }
}
