package models.enums;

/**
 * Enum que define os diferentes tipos de operações bancárias.
 * Cada tipo de operação é representado por um nome descritivo, utilizado para identificar a natureza da operação.
 */

public enum IdentificadorTipo {

    /**
     * Representa uma operação de saque, onde o cliente retira dinheiro da conta.
     */
    SAQUE("Saque"),

    /**
     * Representa uma operação de depósito, onde o cliente insere dinheiro na conta.
     */
    DEPOSITO("Depósito"),

    /**
     * Representa uma transferência entre contas bancárias.
     */
    TRANSFERENCIA("Transferência"),

    /**
     * Representa um Pix recebido pela conta (Pix In).
     */
    PIX_IN("Pix In"),

    /**
     * Representa um Pix enviado para outra conta (Pix Out).
     */
    PIX_OUT("Pix Out"),

    /**
     * Representa uma correção de taxa aplicada sobre o saldo da conta.
     */
    CORRECAO_TAX("Correção Taxa");

    private final String tipoNome;

    /**
     * Construtor da enumeração, atribuindo o nome do tipo da operação.
     *
     * @param tipoNome Nome descritivo do tipo de operação.
     */
    IdentificadorTipo(String tipoNome){
        this.tipoNome = tipoNome;
    }

    /**
     * Retorna o nome descritivo do tipo de operação.
     *
     * @return O nome do tipo de operação.
     */
    public String getTipoNome(){
        return tipoNome;
    }

}
