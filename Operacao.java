//Uma classe como Operacao poderá armazenar
public class Operacao {
 public String tipo;
 public Solicitacao solicitacao;

    public Operacao(String tipo, Solicitacao solicitacao) {
        this.tipo = tipo;
        this.solicitacao = solicitacao;
    }

}