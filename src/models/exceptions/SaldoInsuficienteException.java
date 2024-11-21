package models.exceptions;

/**
 * Exceção lançada quando o saldo de uma conta é insuficiente para realizar uma operação.
 * Esta exceção é utilizada quando o usuário tenta realizar um saque, transferência ou
 * qualquer operação que exija um saldo superior ao disponível na conta.
 */
public class SaldoInsuficienteException extends Exception {

    /**
     * Construtor da exceção, que recebe a mensagem de erro a ser exibida.
     *
     * @param message A mensagem detalhando a razão pela qual a exceção foi lançada.
     */
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
