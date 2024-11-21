package models.exceptions;

/**
 * Exceção lançada quando um documento (CPF) é considerado inválido.
 * Esta exceção é utilizada quando um documento fornecido não atende aos critérios ou formato esperado.
 */
public class DocumentoInvalidoException extends Exception {

    /**
     * Construtor da exceção, que recebe a mensagem de erro a ser exibida.
     *
     * @param message A mensagem detalhando a razão pela qual a exceção foi lançada.
     */
    public DocumentoInvalidoException(String message) {
        super(message);
    }
}
