package models.enums;

public enum IdentificadorTipo {

    SAQUE("Saque"),
    DEPOSITO("Depósito"),
    TRANSFERENCIA("Transferência"),
    PIX_IN("Pix In"),
    PIX_OUT("Pix Out"),
    CORRECAO_TAX("Correção Taxa");

    private final String tipoNome;

    IdentificadorTipo(String tipoNome){
        this.tipoNome = tipoNome;
    }

    public String getTipoNome(){
        return tipoNome;
    }

}
