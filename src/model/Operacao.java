package model;

public class Operacao {
 private String tipo;
 private Solicitacao solicitacao;
    // usada para a desfazer uma operacao em atendimento e conseguir recuperar o estado anterior
    private int altasConsecutivasAntes = 0;

    public Operacao(String tipo, Solicitacao solicitacao) {
        this.tipo = tipo;
        this.solicitacao = solicitacao;
    }

    // novo construtor com o atributo altasConsecutivasAntes, usado para desfazer uma operacao em atendimento (remover o primeiro?)
    public Operacao(String tipo, Solicitacao solicitacao, int altasConsecutivasAntes) {
        this.tipo = tipo;
        this.solicitacao = solicitacao;
        this.altasConsecutivasAntes = altasConsecutivasAntes;
    }

    public int getAltasConsecutivasAntes() {
        return altasConsecutivasAntes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

}

    
