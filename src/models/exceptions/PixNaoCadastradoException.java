package models.exceptions;

/**
 * Exceção lançada quando um CPF ou chave Pix não está cadastrado no sistema de Pix.
 * Esta exceção é utilizada quando se tenta realizar uma operação Pix envolvendo um CPF
 * que não foi registrado no sistema.
 */
public class PixNaoCadastradoException extends Exception {

    /**
     * Construtor da exceção, que recebe a mensagem de erro a ser exibida.
     *
     * @param message A mensagem detalhando a razão pela qual a exceção foi lançada.
     */
    public PixNaoCadastradoException(String message) {
        super(message);
    }
}
