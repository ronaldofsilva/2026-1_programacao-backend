package br.ueg.posse.tsi.exception;

public class PeopleNotFoundException extends RuntimeException {
    public PeopleNotFoundException(String id) {
        super("Pessoa não encontrada com id: " + id);
    }
}
