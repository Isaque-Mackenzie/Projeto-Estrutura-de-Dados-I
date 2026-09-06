//Uma classe como Operacao poderá armazenar
public class Operacao {
 private String tipo;
 private Solicitacao solicitacao;

    public Operacao(String tipo, Solicitacao solicitacao) {
        this.tipo = tipo;
        this.solicitacao = solicitacao;
    }

}