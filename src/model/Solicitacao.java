import java.time.LocalDateTime;

public class Solicitacao {//inicio class solicitação
    private int codigo;
    private String solicitante;
    private String descricao;
    private String categoria;
    private int prioridade;
    private LocalDateTime dataHora;
    private String status;
    private String responsavelAtendimento;

    public Solicitacao(int codigo, String solicitante, String descricao, String categoria, int prioridade, LocalDateTime dataHora, String status, String responsavelAtendimento) {//inicio metodo solicitacao
        this.codigo = codigo;
        this.solicitante = solicitante;
        this.descricao = descricao;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.dataHora = dataHora;
        this.status = status;
        this.responsavelAtendimento=responsavelAtendimento; 
        
        //imprimi a solicitação, como teste para saber que a solicitação foi criada, e que os dados foram passados corretamente, e que o construtor está funcionando
        //TIRAR DAQUI, ISSO ESTA DESCONTANDO PONTO
        System.out.printf("\n === Solicitação cadastrada: ===\nCódigo: %d, Solicitante: %s, Descrição: %s, Categoria: %s, Prioridade: %d, Data: %s Status: %s, Responsavel pelo Atendimento: %s\n", codigo, solicitante, descricao, categoria, prioridade, dataHora, status, responsavelAtendimento);
        System.out.printf("\n");
    }//fim metodo solicitacao



    // get e set

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataHota() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getResponsavelAtendimento() {
        return responsavelAtendimento;
    }

    public void setResponsavelAtendimento(String responsavelAtendimento) {
        this.responsavelAtendimento = responsavelAtendimento;
    }


    @Override
    public String toString() {
        return "Código: " + this.codigo + 
               "\nSolicitante: " + this.solicitante + 
               "\nDescrição: " + this.descricao + 
               "\nCategoria: " + this.categoria + 
               "\nPrioridade: " + this.prioridade + 
               "\nData/hora " + this.dataHora +
               "\nStatus: " + this.status +
               "\nResponsvel pelo Atendimento: " + this.responsavelAtendimento + "\n";
    }
}//fim class solicitação



