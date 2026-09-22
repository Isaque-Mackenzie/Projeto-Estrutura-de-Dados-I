import java.time.LocalDateTime;

public class Solicitacao {//inicio class solicitação
    private int codigo;
    private String solicitante;
    private String descricao;
    private String categoria;
    private int prioridade;
    private LocalDateTime data_hora;
    private String status;
    private String responsavel_atendimento;

    public Solicitacao(int codigo, String solicitante, String descricao, String categoria, int prioridade, LocalDateTime data_hora, String status, String responsavel_atendimento) {//inicio metodo solicitacao
        this.codigo = codigo;
        this.solicitante = solicitante;
        this.descricao = descricao;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.data_hora = data_hora;
        this.status = status;
        this.responsavel_atendimento=responsavel_atendimento; 
        
        //imprimi a solicitação, como teste para saber que a solicitação foi criada, e que os dados foram passados corretamente, e que o construtor está funcionando
        System.out.printf("\n === Solicitação cadastrada: ===\nCódigo: %d, Solicitante: %s, Descrição: %s, Categoria: %s, Prioridade: %d, Data: %s Status: %s, Responsavel pelo Atendimento: %s\n", codigo, solicitante, descricao, categoria, prioridade, data_hora, status, responsavel_atendimento);
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

    public LocalDateTime getData_Hota() {
        return data_hora;
    }

    public void setData_Hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getResponsavel_Atendimento() {
        return responsavel_atendimento;
    }

    public void setResponsavel_Atendimento(String responsavel_atendimento) {
        this.responsavel_atendimento = responsavel_atendimento;
    }


    @Override
    public String toString() {
        return "Código: " + this.codigo + 
               " | Solicitante: " + this.solicitante + 
               " | Descrição: " + this.descricao + 
               " | Categoria: " + this.categoria + 
               " | Prioridade: " + this.prioridade + 
               " | Data/hora " + this.data_hora +
               " | Status: " + this.status +
               " | Responsvel pelo Atendimento: " + this.responsavel_atendimento;
    }
}//fim class solicitação


