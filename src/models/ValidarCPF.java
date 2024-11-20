package models;

public class ValidarCPF {


    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            int digito = Character.getNumericValue(str.charAt(i));
            soma += digito * peso[peso.length - str.length() + i];
        }
        int resto = 11 - (soma % 11);
        return (resto > 9) ? 0 : resto;
    }

    public static boolean cpfEhValido(String cpf) {

        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        String base = cpf.substring(0, 9);

        int digito1 = calcularDigito(base, PESO_CPF);
        int digito2 = calcularDigito(base + digito1, PESO_CPF);

        return cpf.equals(base + digito1 + digito2);
    }

}
