package models.exceptions;

/**
 * Exceção lançada quando uma conta não é encontrada no sistema.
 * Esta exceção é usada quando há uma tentativa de acessar ou realizar uma operação em uma conta inexistente.
 */
public class ContaNaoEncontradaException extends Exception {

    /**
     * Construtor da exceção, que recebe a mensagem de erro a ser exibida.
     *
     * @param message A mensagem detalhando a razão pela qual a exceção foi lançada.
     */
    public ContaNaoEncontradaException(String message) {
        super(message);
    }
}
