package service;

import java.time.LocalDateTime;
import estrutura.LinkedList;
import estrutura.TADFila;
import estrutura.ListaDupla;
import estrutura.Fila;
import estrutura.Pilha;
import estrutura.Node;
import model.Operacao;
import model.Solicitacao;

public class CentralDeAtendimento {//inicio da classe CentralDeAtendimento

    //contador para controlar a quantidade de solicitações cadastradas, e numera a solicitação
    int contador=0;

    //instanciação da Pilha
    private Pilha<Operacao> pilhaDeOperacoes = new Pilha<>();
    //instanciação da Fila
    private Fila<Solicitacao> filaDeSolicitacoes = new Fila<>();
     
    //fila de solicitacoes PRIORIDADE 1 ALTA
    private LinkedList<Solicitacao> listaDeSolicitacoes = new LinkedList<>(); 

//
    //lista duplamente encadeada: histórico dos atendimentos CONCLUÍDOS (ordenada pela hora da conclusão)
    private ListaDupla<Solicitacao> historicoAtendimentos = new ListaDupla<>();  
//
    

    //fila de solicitacoes PRIORIDADE 2 BAIXA
    private Fila<Solicitacao> filaDeSolicitacoesP2 = new Fila<>();



    // rodizio 3 de prioridade alta para 1 de prioridade baixa 3:1 (evita a prioridade 2 esperar para sempre)
    public static final int LIMITE_ALTA_CONSECUTIVA = 3;

    // quantos atendimentos seguidos da prioridade 1 ja ocorreram enquanto havia prioridade 2 esperando
    private int altasConsecutivas = 0;

    
    Solicitacao joao = null;
    Solicitacao maria = null;
    Solicitacao pedro = null;
    Solicitacao andre = null;
    Solicitacao carlos = null;
    Solicitacao ana = null;


// =-=-=-=-=-=-=-= MÉTODOS DA PRIORIDADE (ALTA E BAIXA) PARA SABER QUAL FILA ATENDER =-=-=-=-=-=-=-=

    //decide qual fila atende, so tem uma com gente  entao é fila usada. se tiver duas com algo, pega prioridade 1 alta, ate completar LIMITE_ALTA_CONSECUTIVA
    private Fila<Solicitacao> filaDaVez() throws Exception {
        boolean p1Vazia = filaDeSolicitacoes.qIsEmpty();
        boolean p2Vazia = filaDeSolicitacoesP2.qIsEmpty();

        if (p1Vazia && !p2Vazia) {
            return filaDeSolicitacoesP2;
        }
        if (p2Vazia) {
            return filaDeSolicitacoes;
        }
        return (altasConsecutivas >= LIMITE_ALTA_CONSECUTIVA) ? filaDeSolicitacoesP2 : filaDeSolicitacoes;
    }

    //no caso 4
    //monta o texto da fila global (c as 2 prioridades), sem instanciar uma nova lista contendo as todas as solicitaçôes
    public String filaGlobal() throws Exception {
        TADFila<Solicitacao> aux1 = new Fila<>();
        TADFila<Solicitacao> aux2 = new Fila<>();
        String texto = "";
        int simulaAltas = altasConsecutivas;  // copia para a exibicao nao mexer no rodizio de filas de verdade
        int posicao = 0;

        while (!filaDeSolicitacoes.qIsEmpty() || !filaDeSolicitacoesP2.qIsEmpty()) {
            boolean p1Vazia = filaDeSolicitacoes.qIsEmpty();
            boolean p2Vazia = filaDeSolicitacoesP2.qIsEmpty();
            boolean daP2 = (p1Vazia && !p2Vazia) || (!p1Vazia && !p2Vazia && simulaAltas >= LIMITE_ALTA_CONSECUTIVA);

            Solicitacao s;
            if (daP2) {
                s = filaDeSolicitacoesP2.dequeue();
                aux2.enqueue(s);
                simulaAltas = 0;
            } 
            else {
                s = filaDeSolicitacoes.dequeue();
                aux1.enqueue(s);
                if (!p2Vazia) {
                    simulaAltas++;
                }
            }

            posicao++;
            texto += posicao + "º | Prioridade " + s.getPrioridade() + " | Código: " + s.getCodigo() + " | Solicitante: " + s.getSolicitante() + " | Descrição: " + s.getDescricao() + "\n";
        }

        // devolve cada solicitação para a sua fila, na ordem original
        while (!aux1.qIsEmpty()) {
            filaDeSolicitacoes.enqueue(aux1.dequeue());
        }
        while (!aux2.qIsEmpty()) {
            filaDeSolicitacoesP2.enqueue(aux2.dequeue());
        }

        return "Fila de espera, na ordem de atendimento (rodízio " + LIMITE_ALTA_CONSECUTIVA + " de prioridade 1 : 1 de prioridade 2)\n" + texto;
    }

    //no caso 5
    //quantidade aguardando em cada prioridade
    public int tamanhoPrioridade(int prioridade) throws Exception {
        if (prioridade == 2) {
            return filaDeSolicitacoesP2.size();
        }
        return filaDeSolicitacoes.size();
    }

    // no caso 15 concluir atendimento
    // existe alguma solicitação EM_ATENDIMENTO no cadastro geral?
    public boolean existeEmAtendimento() throws Exception {
        Node<Solicitacao> atual = listaDeSolicitacoes.getHead();

        while (atual != null) {
            if (atual.getDado().getStatus().equals("EM_ATENDIMENTO")) {
                return true;
            }
            atual = atual.getProx();
        }
        return false;
    }


// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- MÉTODOS AUXILIARES =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public boolean filaCheia() { 
        return filaDeSolicitacoes.qIsFull() || filaDeSolicitacoesP2.qIsFull();
    }

    public boolean filaVazia() throws Exception{
        return filaDeSolicitacoes.qIsEmpty() && filaDeSolicitacoesP2.qIsEmpty();
    }    

    public boolean pilhaVazia() throws Exception{
        return pilhaDeOperacoes.isEmpty();
    }

    public boolean listaSimplesVazia() throws Exception{
        return listaDeSolicitacoes.isEmpty();
    }

    public boolean listaDuplaVazia() throws Exception{
        return historicoAtendimentos.isEmpty();   
    }


// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=- MÉTODOS PRINCIPAIS (DE CADA CASE) =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    // ===================== CASE 1 = Cadastrar nova solicitação ======================
    public Solicitacao cadastrar(String nome, String descricao, String categoria, int prioridade) throws Exception {
        if (prioridade != 1 && prioridade != 2) {
            throw new IllegalArgumentException("Prioridade inválida: " + prioridade + ". Use 1 (Alta) ou 2 (Baixa).");
        }        
        
        // data em formato timestamp para guardar na solicitação
        LocalDateTime dataHora = LocalDateTime.now();// Ex: 2026-09-20T17:05:19.123

        contador++; //soma o contador toda vez que cria uma nova solicitação, mesmo que desfeita ou manipulada, ela contará como uma. 
                    // o contador é usado como código da solicitação, e o código é unico e nao se repete

        //instancia a solicitação com as informações fornecidas pelo usuário, sempre vai como agurdando para a ista
        Solicitacao novaSolicitacao = new Solicitacao(contador, nome, descricao, categoria, prioridade, dataHora, "AGUARDANDO", null);

        if (prioridade == 2) {
            filaDeSolicitacoesP2.enqueue(novaSolicitacao);
        } else {
            filaDeSolicitacoes.enqueue(novaSolicitacao);
        }

        // Registra a Operacao na Pilha
        Operacao operacao = new Operacao("CADASTRO", novaSolicitacao);
        
        //Adiciona na Pilha
        pilhaDeOperacoes.push(operacao);//coloca na pilha

        //Adiciona na Lista
        listaDeSolicitacoes.addLast(novaSolicitacao);

        return novaSolicitacao;
    }

    // ==================== CASE 2 = Consultar próxima solicitação ====================
    // devolve a primeira solicitação da fila sem tirar 
    public Solicitacao primeiraDaFila() throws Exception  {
        if (filaDaVez() == filaDeSolicitacoesP2) {
            return filaDeSolicitacoesP2.front();
        }
        return filaDeSolicitacoes.front();
    }

    // ===================== CASE 3 = Atender próxima solicitação =====================
    public Solicitacao atender(String responsavelAtendimento) throws Exception {

        Fila<Solicitacao> filaEscolhida = filaDaVez(); // descobre qual fila atende agora - prioridade alta ou baixa
        int altasAntes = altasConsecutivas; // guarda o rodizio de antes para o desfazer

        Solicitacao seguraSolicitacao = filaEscolhida.dequeue();//dequeue tira a solicitacao ultima e coloca no segura_solicitacao

        seguraSolicitacao.setStatus("EM_ATENDIMENTO"); // Atualiza o status da solicitação que esta sendo segurada
        seguraSolicitacao.setResponsavelAtendimento(responsavelAtendimento);

        // atualiza o rodizio   se tendeu P2 = zera, se atendeu P1 com P2 esperando = soma 1
        if (filaEscolhida == filaDeSolicitacoesP2) {
            altasConsecutivas = 0;
        } else if (!filaDeSolicitacoesP2.qIsEmpty()) {
            altasConsecutivas++;
        }

        Operacao operacao = new Operacao("ATENDIMENTO", seguraSolicitacao, altasAntes);// Empilha a operação de atendimento, aqui vamos criar uma operação que é a solicitação antiga, agora atualizada, jogar com o pop para a pilha
        pilhaDeOperacoes.push(operacao);

        return seguraSolicitacao;
    }

    // ===================== CASE 4 = Exibir fila de solicitações =====================
    public TADFila<Solicitacao> getFila() throws Exception{
        return filaDeSolicitacoes;
    }

    // ================== CASE 5 = Exibir quantidade de solicitações ==================
    public int tamanhoFila() throws Exception{
        return filaDeSolicitacoes.size() + filaDeSolicitacoesP2.size(); // soma as 2 filas de prioridades
    }

    // ================= CASE 6 = Consultar última operação realizada =================
    //devolve o topo da pilha sem tirar
    public Operacao ultimaOperacao() throws Exception{
        return pilhaDeOperacoes.top();
    }

    // ==================== CASE 7 = Exibir histórico de operações ====================
    // monta o texto do histórico e devolve para o menu imprimir
    public String historico() throws Exception {
        Pilha<Operacao> pilhaAuxiliar = new Pilha<>();//pop perde o elemento quando tira
        String caixinha_operacao="";

        // Desempilhando e guardando todos os nomes (pop)
        while (!pilhaDeOperacoes.isEmpty()) {
            Operacao operacao = pilhaDeOperacoes.pop();//pega o topo da pilha, lembra que pop tira e depois perde o elemento se não colocado em algum lugar
            caixinha_operacao += "Solicitação: " + operacao.getSolicitacao().getCodigo() + " " + operacao.getTipo() + " (Prioridade " + operacao.getSolicitacao().getPrioridade() + ")\n"; // mostrando a prioridade
            pilhaAuxiliar.push(operacao);
        }

        // Devolve os elementos para a pilha normal de antes, sem ser a auxiliar
        while (!pilhaAuxiliar.isEmpty()) {
            pilhaDeOperacoes.push(pilhaAuxiliar.pop());
        }
        return caixinha_operacao;
    }

    // ====================== CASE 8 = Desfazer última operação =======================
    // devolve a mensagem que o menu vai mostrar
    public String desfazer() throws Exception{
        String mensagem_tipo = "";

        // Remove a última operação realizada no topo da pilha, pega a última
        Operacao ultimaOperacao = pilhaDeOperacoes.pop();

        //  se for atendimento, devolve o rodizio ao estado de antes dele
        if (ultimaOperacao.getTipo().equals("ATENDIMENTO")) {
            altasConsecutivas = ultimaOperacao.getAltasConsecutivasAntes();
        }

        // CADASTRO de prioridade 2: mesma logica do cadastro mais abaixo, mas na fila P2
        if (ultimaOperacao.getTipo().equals("CADASTRO") && ultimaOperacao.getSolicitacao().getPrioridade() == 2) {
            int qtdParaMover = filaDeSolicitacoesP2.size() - 1;
            TADFila<Solicitacao> filaAux = new Fila<>();

            for (int i = 0; i < qtdParaMover; i++) {
                filaAux.enqueue(filaDeSolicitacoesP2.dequeue());
            }

            filaDeSolicitacoesP2.dequeue(); // remove o último elemento que fica
            ultimaOperacao.getSolicitacao().setStatus("CANCELADA");

            while (!filaAux.qIsEmpty()) {
                filaDeSolicitacoesP2.enqueue(filaAux.dequeue());
            }

            return "Último cadastro desfeito com sucesso!";
        }

        // ATENDIMENTO de prioridade 2: mesma logica do atendimento mais abaixo, mas na fila P2
        if (ultimaOperacao.getTipo().equals("ATENDIMENTO") && ultimaOperacao.getSolicitacao().getPrioridade() == 2) {
            ultimaOperacao.getSolicitacao().setStatus("AGUARDANDO");

            TADFila<Solicitacao> filaRecolocacao = new Fila<>();
            filaRecolocacao.enqueue(ultimaOperacao.getSolicitacao());

            while(!filaDeSolicitacoesP2.qIsEmpty()){
                filaRecolocacao.enqueue(filaDeSolicitacoesP2.dequeue());
            }
            while(!filaRecolocacao.qIsEmpty()){
                filaDeSolicitacoesP2.enqueue(filaRecolocacao.dequeue());
            }

            return "Atendimento desfeito! O cliente retornou à fila na sua posição original";
        }

        // CONCLUSAO (opção 20) -> volta a solicitação para EM_ATENDIMENTO
        if (ultimaOperacao.getTipo().equals("CONCLUSAO")) {
            ultimaOperacao.getSolicitacao().setStatus("EM_ATENDIMENTO");
            // TODO (integrante da Lista Dupla): remover esta solicitação do histórico de atendimentos concluídos aqui
//
            historicoAtendimentos.remove(ultimaOperacao.getSolicitacao());
//
            return "Conclusão desfeita! A solicitação voltou para EM_ATENDIMENTO";
        }

        // vê o que tem nessa ultima operação
        // se for um cadastro, desfaremos cadastro
        if (ultimaOperacao.getTipo().equals("CADASTRO")) { // inicio if cadastro
            int qtdParaMover = filaDeSolicitacoes.size() - 1;
            TADFila<Solicitacao> filaAux = new Fila<>();

            for (int i = 0; i < qtdParaMover; i++) {
                filaAux.enqueue(filaDeSolicitacoes.dequeue());
            }

            filaDeSolicitacoes.dequeue(); // remove o último elemento que fica
            // Altera o status para não ficar mais "AGUARDANDO"
            ultimaOperacao.getSolicitacao().setStatus("CANCELADA");

            // enche a fila original de novo
            while (!filaAux.qIsEmpty()) {
                filaDeSolicitacoes.enqueue(filaAux.dequeue());
            }

            mensagem_tipo = "Último cadastro desfeito com sucesso!";
        } // fim if cadastro

        // se for um atendimento, desfaremos o status atendimento e colocamos a
        else if (ultimaOperacao.getTipo().equals("ATENDIMENTO")) { // inicio if atendimento
            ultimaOperacao.getSolicitacao().setStatus("AGUARDANDO");

            // Criação da fila auxiliar para recolocação do objeto na fila na sua posição original
            TADFila<Solicitacao> filaRecolocacao = new Fila<>();

            // Remove o objeto atual da fila e coloca numa auxiliar
            filaRecolocacao.enqueue(ultimaOperacao.getSolicitacao());

            // Move todos os objetos da fila original e os coloca na fila auxiliar
            while(!filaDeSolicitacoes.qIsEmpty()){
                filaRecolocacao.enqueue(filaDeSolicitacoes.dequeue());
            }
            // Move todos os objetos da fila auxiliar para a original, com a solicitação do antendimento desfeito na posição original
            while(!filaRecolocacao.qIsEmpty()){
                filaDeSolicitacoes.enqueue(filaRecolocacao.dequeue());
            }

            mensagem_tipo = "Atendimento desfeito! O cliente retornou à fila na sua posição original";
        } // fim if atendimento

        return mensagem_tipo;
    }

    // ====================== CASE 9 = Inserir dados sintéticos =======================
    // devolve false se os dados já estão na fila (o menu avisa o usuário)
    public boolean inserirDadosSinteticos() throws Exception {

        // Verifica se as solicitações de teste já estão na fila aguardando
        if (       joao != null && joao.getStatus().equals("AGUARDANDO")
                || maria != null && maria.getStatus().equals("AGUARDANDO")
                || pedro != null && pedro.getStatus().equals("AGUARDANDO")
                || andre != null && andre.getStatus().equals("AGUARDANDO")
                || carlos != null && carlos.getStatus().equals("AGUARDANDO")
                || ana != null && ana.getStatus().equals("AGUARDANDO")
            ) {
            return false;
        }

        LocalDateTime dataHora = LocalDateTime.now();// Ex: 2026-09-20T17:05:19.123

        joao = new Solicitacao(contador+=1, "João", "Problema no sistema", "TI", 1, dataHora, "AGUARDANDO",null);
        maria = new Solicitacao(contador+=1, "Maria", "Problema com impressora", "TI", 2, dataHora,"AGUARDANDO",null);
        pedro = new Solicitacao(contador+=1, "Pedro", "Problema com computador", "TI", 1, dataHora,"AGUARDANDO",null);
        andre = new Solicitacao(contador+=1, "André", "Problema com celular", "TI", 1, dataHora,"AGUARDANDO",null);
        carlos = new Solicitacao(contador+=1, "Carlos", "Problema com notebook", "TI", 2, dataHora,"AGUARDANDO",null);
        ana = new Solicitacao(contador+=1, "Ana", "Problema com monitor", "TI", 1, dataHora,"AGUARDANDO",null);

        filaDeSolicitacoes.enqueue(joao);
        filaDeSolicitacoesP2.enqueue(maria); 
        filaDeSolicitacoes.enqueue(pedro);
        filaDeSolicitacoes.enqueue(andre);
        filaDeSolicitacoesP2.enqueue(carlos);
        filaDeSolicitacoes.enqueue(ana);

        pilhaDeOperacoes.push(new Operacao("CADASTRO", joao));
        pilhaDeOperacoes.push(new Operacao("CADASTRO", maria));
        pilhaDeOperacoes.push(new Operacao("CADASTRO", pedro));
        pilhaDeOperacoes.push(new Operacao("CADASTRO", andre));
        pilhaDeOperacoes.push(new Operacao("CADASTRO", carlos));
        pilhaDeOperacoes.push(new Operacao("CADASTRO", ana));


        listaDeSolicitacoes.addLast(joao);
        listaDeSolicitacoes.addLast(maria);
        listaDeSolicitacoes.addLast(pedro);
        listaDeSolicitacoes.addLast(andre);
        listaDeSolicitacoes.addLast(carlos);
        listaDeSolicitacoes.addLast(ana);


        return true;
    }

    // ==================== CASE 10 = Exibir todas as solicitações ====================
    public String listar() throws Exception {
        return listaDeSolicitacoes.toString();
    }

    // ================== CASE 11 = Pesquisar solicitação por código ==================
    public Solicitacao buscaCodigo (int codigoDaBusca) throws Exception{
        Node<Solicitacao> atual=listaDeSolicitacoes.getHead();
        
        while(atual!=null){
            //pegar o Dado do nó atual, e o dado vai ser uma solicitação, e pega o codigo dessa solicitação
            if (atual.getDado().getCodigo()==codigoDaBusca){
                return atual.getDado();
            }
            atual=atual.getProx();
        }
        return null;

    }

    // =================== CASE 13 = Exibir solicitações por status ===================
    public String buscaStatus (String statusDaBusca) throws Exception{
        String mensagemErro = "Não há uma solicitação com esse STATUS.";
        String resultado = "";   // começa vazio
        Node<Solicitacao> atual=listaDeSolicitacoes.getHead();
        
        while(atual!=null){
            //pegar o Dado do nó atual, e o dado vai ser uma solicitação, e pega o codigo dessa solicitação
            if (atual.getDado().getStatus().equalsIgnoreCase(statusDaBusca)){
                // achou: guarda no texto (o + chama o toString() da Solicitacao)
                resultado = resultado + atual.getDado() + "\n";
            }
            atual=atual.getProx();
        }

        if(resultado.isEmpty()){
            return mensagemErro;
        }
        return resultado;
    }

    // ================= CASE 14 = Exibir solicitações por categoria ==================
    public String buscaCategoria (String categoriaDaBusca) throws Exception{
        String mensagemErro = "Não há uma solicitação com essa CATEGORIA.";
        String resultado = "";   // começa vazio
        Node<Solicitacao> atual=listaDeSolicitacoes.getHead();

        while(atual!=null){
            // pega o Dado do nó atual (uma solicitação) e olha a categoria dela
            if (atual.getDado().getCategoria().equalsIgnoreCase(categoriaDaBusca)){
                // achou: guarda no texto (o + chama o toString() da Solicitacao)
                resultado = resultado + atual.getDado() + "\n";
            }
            atual=atual.getProx();
        }

        if(resultado.isEmpty()){
            return mensagemErro;
        }
        return resultado;
    }

    // ======================== CASE 15 = Concluir atendimento ========================
    // conclui o atendimento, devolve a mensagem que o menu vai mostrar (igual ao desfazer)
    // só conclui solicitação EM_ATENDIMENTO e registra a operação CONCLUSAO na pilha
    public String concluirAtendimento(int codigo) throws Exception {
        Solicitacao s = buscaCodigo(codigo); 

        if (s == null) {
            return "Não existe solicitação com o código " + codigo + ".";
        }
        if (!s.getStatus().equals("EM_ATENDIMENTO")) {
            return "A solicitação " + codigo + " está com status " + s.getStatus() + ". Só é possível concluir solicitações EM_ATENDIMENTO.";
        }

        s.setStatus("CONCLUIDA");
        pilhaDeOperacoes.push(new Operacao("CONCLUSAO", s));

        // TODO (integrante da Lista Dupla): inserir esta solicitação no histórico de atendimentos concluídos aqui
        historicoAtendimentos.insertAscending(s);


        return "Atendimento da solicitação " + codigo + " concluído com sucesso!";
    }


    //========================== CASE 15 = HISTORICO DE ATENDIMENTO ULTIMO AO PRIMEIRO===================

    public void atendimentoPU() {
        historicoAtendimentos.showAscending();    // case 15
    }

    //========================== CASE 16 = HISTORICO DE ATENDIMENTO ULTIMO AO PRIMEIRO===================
    public void atendimentoUP() {
        historicoAtendimentos.showDescending();   // case 16
    }


}//fim CentralDeAtendimento
