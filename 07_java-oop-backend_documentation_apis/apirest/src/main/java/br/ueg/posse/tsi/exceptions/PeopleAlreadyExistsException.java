package br.ueg.posse.tsi.exceptions;

public class PeopleAlreadyExistsException extends RuntimeException {
    public PeopleAlreadyExistsException(String email){
        super("E-mail "+email+" já está cadastrado.");
    }
}
