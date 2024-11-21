package models;

/**
 * Classe utilitária para validação de números de CPF (Cadastro de Pessoa Física).
 * Essa classe fornece métodos para verificar se um CPF é válido seguindo as regras
 * estabelecidas pelo Ministério da Fazenda do Brasil.
 */

public class ValidarCPF {

    /**
     * Pesos utilizados para o cálculo dos dígitos verificadores do CPF.
     */
    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     * Calcula um dígito verificador de acordo com os pesos fornecidos.
     *
     * @param str A sequência de números a ser avaliada.
     * @return O dígito verificador calculado.
     */
    private static int calcularDigito(String str) {
        int soma = 0;

        for (int i = 0; i < str.length(); i++) {
            int digito = Character.getNumericValue(str.charAt(i));
            soma += digito * ValidarCPF.PESO_CPF[ValidarCPF.PESO_CPF.length - str.length() + i];
        }

        int resto = 11 - (soma % 11);
        return (resto > 9) ? 0 : resto;
    }

    /**
     * Verifica se o número de CPF fornecido é válido.
     * Um CPF válido deve ter 11 dígitos, não ser composto por todos os dígitos iguais,
     * e possuir dígitos verificadores que atendam aos critérios do cálculo.
     *
     * @param cpf O número de CPF a ser validado, sem formatação (somente números).
     * @return {@code true} Se o CPF for válido; {@code false} caso contrário.
     */
    public static boolean cpfEhValido(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        String base = cpf.substring(0, 9);

        int digito1 = calcularDigito(base);
        int digito2 = calcularDigito(base + digito1);

        return cpf.equals(base + digito1 + digito2);
    }

}
