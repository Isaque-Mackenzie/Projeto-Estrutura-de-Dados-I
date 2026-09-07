/*
    Cada solicitação deverá possuir, no mínimo, informações como: 
    • Código da solicitação 
    • Nome do solicitante 
    • Descrição do problema 
    • Categoria 
    • Prioridade 
    • Data/hora de abertura 
    • Status 
    • Responsável pelo atendimentop 
    
    Possíveis estados: 
    • AGUARDANDO 
    • EM_ATENDIMENTO 
    • CONCLUIDA 
    • CANCELADA
*/

public class Solicitacao {//inicio class solicitação
    public int codigo;
    public String solicitante;
    public String descricao;
    public String categoria;
    public int prioridade;
    public String status;

    public Solicitacao(int codigo, String solicitante, String descricao, String categoria, int prioridade, String status) {//inicio metodo solicitacao
        this.codigo = codigo;
        this.solicitante = solicitante;
        this.descricao = descricao;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = status;
        //imprimi a solicitação, como teste para saber que a solicitação foi criada, e que os dados foram passados corretamente, e que o construtor está funcionando
        System.out.printf("\n === Solicitação: ===\nCódigo: %d, Solicitante: %s, Descrição: %s, Categoria: %s, Prioridade: %d, Status: %s\n", codigo, solicitante, descricao, categoria, prioridade, status);
        System.out.printf("\n");
    }//fim metodo solicitacao
}//fim class solicitação


    



