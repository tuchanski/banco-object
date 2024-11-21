package models.interfaces;

import models.ContaCorrente;
import models.exceptions.PixJaCadastradoException;
import models.exceptions.PixNaoCadastradoException;
import models.exceptions.SaldoInsuficienteException;

import java.util.List;

/**
 * Interface que define os comportamentos necessários para contas que oferecem a funcionalidade de Pix.
 * Contas que implementam essa interface podem cadastrar, efetuar e receber transferências via Pix.
 */

public interface Pix {

    /**
     * Cadastra um CPF no sistema de Pix, permitindo que a conta possa realizar transferências via Pix.
     *
     * @param usuariosPix Lista de CPFs cadastrados para realizar transações via Pix.
     * @throws PixJaCadastradoException Se o CPF já estiver cadastrado no sistema de Pix.
     */
    void cadastrarPix(List<String> usuariosPix) throws PixJaCadastradoException;

    /**
     * Efetua uma transferência via Pix para outra conta, debitando o valor do saldo da conta de origem.
     *
     * @param usuariosPix Lista de CPFs cadastrados para realizar transações via Pix.
     * @param destinatario Conta de destino da transferência via Pix.
     * @param valor Valor a ser transferido.
     * @throws PixNaoCadastradoException Se o CPF do remetente ou do destinatário não estiver cadastrado no sistema de Pix.
     * @throws SaldoInsuficienteException Se o saldo da conta de origem for insuficiente para realizar a transferência.
     */
    void efetuarPix(List<String> usuariosPix, ContaCorrente destinatario, double valor) throws PixNaoCadastradoException, SaldoInsuficienteException;

    /**
     * Recebe uma transferência via Pix na conta de destino, creditando o valor transferido no saldo da conta.
     *
     * @param usuariosPix Lista de CPFs cadastrados para realizar transações via Pix.
     * @param valor Valor a ser creditado na conta de destino.
     * @throws PixNaoCadastradoException Se o CPF do destinatário não estiver cadastrado no sistema de Pix.
     */
    void receberPix(List<String> usuariosPix, double valor) throws PixNaoCadastradoException;
}
