package br.ueg.posse.tsi.exception;

public class PeopleAlreadyExistsException extends RuntimeException {
    public PeopleAlreadyExistsException(String email) {
        super("Já existe uma pessoa cadastrada com o email: " + email);
    }
}
