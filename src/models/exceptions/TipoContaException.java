package models.exceptions;

/**
 * Exceção lançada quando um tipo de conta inválido é fornecido.
 * Esta exceção pode ser utilizada em cenários onde diferentes tipos de conta precisam ser
 * verificados ou manipulados de forma específica.
 */
public class TipoContaException extends Exception {

    /**
     * Construtor da exceção, que recebe a mensagem de erro a ser exibida.
     *
     * @param message A mensagem detalhando a razão pela qual a exceção foi lançada.
     */
    public TipoContaException(String message) {
        super(message);
    }
}
