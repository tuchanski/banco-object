package models.interfaces;

import models.ContaCorrente;
import models.exceptions.PixJaCadastradoException;
import models.exceptions.PixNaoCadastradoException;
import models.exceptions.SaldoInsuficienteException;

import java.util.List;

public interface Pix {
    void cadastrarPix(List<String> usuariosPix) throws PixJaCadastradoException;
    void efetuarPix(List<String> usuariosPix, ContaCorrente destinatario, double valor) throws PixNaoCadastradoException, SaldoInsuficienteException;
    void receberPix(List<String> usuariosPix, double valor) throws PixNaoCadastradoException;
}
