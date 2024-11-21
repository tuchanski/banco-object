package models.exceptions;

/**
 * Exceção lançada quando uma tentativa de cadastrar uma conta que já está registrada no sistema é feita.
 * Esta exceção é usada para evitar duplicidade no cadastro de contas.
 */
public class ContaJaCadastradaException extends Exception {

    /**
     * Construtor da exceção, que recebe a mensagem de erro a ser exibida.
     *
     * @param message A mensagem detalhando a razão pela qual a exceção foi lançada.
     */
    public ContaJaCadastradaException(String message) {
        super(message);
    }
}
