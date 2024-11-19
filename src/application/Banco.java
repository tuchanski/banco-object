package application;

import models.Conta;

import java.util.ArrayList;
import java.util.List;

public class Banco {

    private static List<Conta> contas = new ArrayList<>();
    private static List<String> cpfsPix = new ArrayList<>();

    private static void getMenu() {
        System.out.println("""
                - Banco Object -
                [1] - Criar Conta Corrente
                [2] - Criar Conta Poupança
                [3] - Efetuar Depósito
                [4] - Efetuar Saque
                [5] - Aplicar Correção
                [6] - Cadastrar PIX
                [7] - Efetuar Pix
                [8] - Consultar Extrato
                [0] - Sair""");
    }

    public static void main(String[] args) {


    }
    
}