package models.interfaces;

/**
 * Interface que define um comportamento para contas que podem aplicar taxas de correção ao saldo.
 * Contas que implementam essa interface devem fornecer uma implementação do método taxaCorrecao.
 */

public interface Remunerada {

    /**
     * Aplica uma taxa de correção ao saldo da conta.
     * O valor do saldo será ajustado com base na porcentagem da taxa fornecida.
     *
     * @param taxa Porcentagem da taxa de correção a ser aplicada.
     */
    void taxaCorrecao(double taxa);
}
