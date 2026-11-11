package service;

import java.time.LocalDateTime;

import estrutura.LinkedList;
import estrutura.TADFila;
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
     //instancia da LstaSimples
    private LinkedList<Solicitacao> listaDeSolicitacoes = new LinkedList<>(); 


    Solicitacao joao = null;
    Solicitacao maria = null;
    Solicitacao pedro = null;
    Solicitacao andre = null;
    Solicitacao carlos = null;
    Solicitacao ana = null;

    



    //No caso 1
    public boolean filaCheia() {
        return filaDeSolicitacoes.qIsFull();
    }

    //1 CADASTRO
    //esse throws Exception é chato demais, essa foi a forma mais rapida que encontrei de parar os erros, mas aparentemente fuunciona tambem se usarmos try-catch
    public Solicitacao cadastrar(String nome, String descricao, String categoria, int prioridade) throws Exception {
        LocalDateTime dataHora = LocalDateTime.now();// Ex: 2026-09-20T17:05:19.123

        contador++; //soma o contador toda vez que apertamos para fazer uma nova solicitação, mesmo que desfeita ou manipulada, ela contará como uma.

        //instancia a solicitação com as informações fornecidas pelo usuário, sempre vai como agurdando para a ista
        Solicitacao novaSolicitacao = new Solicitacao(contador, nome, descricao, categoria, prioridade, dataHora, "AGUARDANDO", null);

        // Adiciona na Fila
        filaDeSolicitacoes.enqueue(novaSolicitacao);

        // Registra a Operacao na Pilha, ESTA CADASTRO PORQUE VOCE ACABOU DE CADASTRAR UMA NOVA PESSOAS
        Operacao operacao = new Operacao("CADASTRO", novaSolicitacao);
        
        //Adiciona na Pilha
        pilhaDeOperacoes.push(operacao);//coloca na pilha


        //Adiciona na Lista
        listaDeSolicitacoes.addLast(novaSolicitacao);

        return novaSolicitacao;
    }


    //No caso 2, 3, 4, 5, 6, 7
    public boolean filaVazia() throws Exception{
        return filaDeSolicitacoes.qIsEmpty();
    }

    //No caso 2
    // devolve a primeira solicitação da fila sem tirar (opção 2)
    public Solicitacao primeiraDaFila() throws Exception  {
        return filaDeSolicitacoes.front();
    }


    //3 ATENDIMENTO
    public Solicitacao atender(String responsavelAtendimento) throws Exception {
        Solicitacao seguraSolicitacao = filaDeSolicitacoes.dequeue();//dequeue tira a solicitacao ultima e coloca no segura_solicitacao

        seguraSolicitacao.setStatus("EM_ATENDIMENTO"); // Atualiza o status da solicitação que esta sendo segurada
        seguraSolicitacao.setResponsavelAtendimento(responsavelAtendimento);

        Operacao operacao = new Operacao("ATENDIMENTO", seguraSolicitacao);// Empilha a operação de atendimento, aqui vamos criar uma operação que é a solicitação antiga, agora atualizada, jogar com o pop para a pilha
        pilhaDeOperacoes.push(operacao);

        return seguraSolicitacao;
    }


    //no caso 4
    //filade solicitacao para o menu poder imprimir
    public TADFila<Solicitacao> getFila() throws Exception{
        return filaDeSolicitacoes;
    }


    //no caso 5
    public int tamanhoFila() throws Exception{
        return filaDeSolicitacoes.size();
    }


    //no caso 6
    public boolean pilhaVazia() throws Exception{
        return pilhaDeOperacoes.isEmpty();
    }


    //no caso 6
    //topo da pilha sem tirar
    public Operacao ultimaOperacao() throws Exception{
        return pilhaDeOperacoes.top();
    }


    //7 HISTÓRICO
    // monta o texto do histórico e devolve para o menu imprimir
    public String historico() throws Exception {
        Pilha<Operacao> pilhaAuxiliar = new Pilha<>();//pop perde o elemento quando tira
        String caixinha_operacao="";

        // Desempilhando e guardando todos os nomes (pop)
        while (!pilhaDeOperacoes.isEmpty()) {
            Operacao operacao = pilhaDeOperacoes.pop();//pega o topo da pilha, lembra que pop tira e depois perde o elemento se não colocado em algum lugar
            caixinha_operacao += "Solicitação: " + operacao.getSolicitacao().getCodigo() + " " + operacao.getTipo() + "\n";
            pilhaAuxiliar.push(operacao);
        }

        // Devolve os elementos para a pilha normal de antes, sem ser a auxiliar
        while (!pilhaAuxiliar.isEmpty()) {
            pilhaDeOperacoes.push(pilhaAuxiliar.pop());
        }


        return caixinha_operacao;
    }


    //8 DESFAZER
    // devolve a mensagem que o menu vai mostrar
    public String desfazer() throws Exception{
        String mensagem_tipo = "";

        // Remove a última operação realizada no topo da pilha, pega a última
        Operacao ultimaOperacao = pilhaDeOperacoes.pop();

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


    //9 DADOS SINTÉTICOS

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
        pedro = new Solicitacao(contador+=1, "Pedro", "Problema com computador", "TI", 3, dataHora,"AGUARDANDO",null);
        andre = new Solicitacao(contador+=1, "André", "Problema com celular", "TI", 4, dataHora,"AGUARDANDO",null);
        carlos = new Solicitacao(contador+=1, "Carlos", "Problema com notebook", "TI", 5, dataHora,"AGUARDANDO",null);
        ana = new Solicitacao(contador+=1, "Ana", "Problema com monitor", "TI", 6, dataHora,"AGUARDANDO",null);

        filaDeSolicitacoes.enqueue(joao);
        filaDeSolicitacoes.enqueue(maria);
        filaDeSolicitacoes.enqueue(pedro);
        filaDeSolicitacoes.enqueue(andre);
        filaDeSolicitacoes.enqueue(carlos);
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

    
    //Usado na 10 e na 11 
    public boolean listaSimplesVazia() throws Exception{
        return listaDeSolicitacoes.isEmpty();
    }


    //10 EXIBIR TODAS AS SOLICITAÇÕES
    public String listar() throws Exception {
        return listaDeSolicitacoes.toString();
    }


    
    //11 Pesquisa por codigo
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


    

    
    
    //13 Pesquisa por codigo
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



    //14 Pesquisa por categoria
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

}//fim CentralDeAtendimento
