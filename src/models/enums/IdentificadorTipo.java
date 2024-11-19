package models.enums;

public enum IdentificadorTipo {

    SAQUE("Saque"),
    DEPOSITO("Depósito");

    private final String tipoNome;

    IdentificadorTipo(String tipoNome){
        this.tipoNome = tipoNome;
    }

    public String getTipoNome(){
        return tipoNome;
    }

}
