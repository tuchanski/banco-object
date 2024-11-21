package models.exceptions;

/**
 * Exceção lançada quando um CPF já está cadastrado no sistema de Pix.
 * Esta exceção é utilizada quando se tenta cadastrar um Pix que já está previamente registrado.
 */
public class PixJaCadastradoException extends Exception {

    /**
     * Construtor da exceção, que recebe a mensagem de erro a ser exibida.
     *
     * @param message A mensagem detalhando a razão pela qual a exceção foi lançada.
     */
    public PixJaCadastradoException(String message) {
        super(message);
    }
}
