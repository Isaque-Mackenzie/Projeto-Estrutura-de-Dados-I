import java.time.LocalDateTime;
import java.util.Scanner;


public class Menu {//inicio da classe Menu

    //contador para controlar a quantidade de solicitações cadastradas, e numera a solicitação
    int contador=0;

    //instanciação da Pilha
    public Pilha<Operacao> pilhaDeOperacoes = new PilhaComArray<>();
    //instanciação da Fila
    public TADFila<Solicitacao> filaDeSolicitacoes = new Fila<>(10);


    LocalDateTime data_hora = LocalDateTime.now();// Ex: 2026-09-20T17:05:19.123

    //instanciação do scanner para ler a entrada do usuário
    Scanner scanner = new Scanner(System.in);

    Solicitacao joao = null;
    Solicitacao maria = null;
    Solicitacao pedro = null;
    Solicitacao andre = null;
    Solicitacao carlos = null;
    Solicitacao ana = null;

    //método exibir, para mostrar o menu, chamado anteriormente pelo main
    public boolean exibir() {//inicio exibir

        //menu
        System.out.println(//inicio menu
            "\n-=-=-=-=-=-=-=-=- MENU -=-=-=-=-=-=-=-=-\n" +
            "1 - Cadastrar nova solicitação\n" +
            "2 - Consultar próxima solicitação\n" +
            "3 - Atender próxima solicitação\n" +
            "4 - Exibir fila de solicitações\n" +
            "5 - Exibir quantidade de solicitações\n" +
            "6 - Consultar última operação realizada\n" +
            "7 - Exibir histórico de operações\n" +
            "8 - Desfazer última operação\n" +
            "9 - Inserir Dados Sintéticos\n" +
            "0 - Encerrar a operação\n"
        );//fim menu

        //solicita ao usuário que escolha uma opção do menu
        System.out.print("\nEscolha uma opção: ");
        int opcao;
        try{
            opcao = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("\nERRO! DIGITE APENAS NUMEROS\n");
            scanner.nextLine(); // Limpa o buffer do teclado, igual o de c, não muda nada
            return false; // para continuar o loop do maiN
        }

        switch (opcao) {
            case 1:{ // CADASTRA A NOVA SOLICITAÇÃO
                System.out.println("Opção 1 selecionada: Cadastrar nova solicitação");

                // CASO A FILA ESTIVER CHEIA, ENTÃO NEM PERGUNTA AS INFO PARA CADASTRAR, SO AVISA Q TA CHEIA E VOLTA PARA O MENU
                if (filaDeSolicitacoes.qIsFull()) {
                    System.out.println("\nA fila está cheia! Não é possível cadastrar novas solicitações.\n");
                    break;
                }

                contador++; //soma o contador toda vez que apertamos para fazer uma nova solicitação, mesmo que desfeita ou manipulada, ela contará como uma.

                // Limpa o buffer do teclado
                scanner.nextLine();  //igual o de c, não muda nada

                System.out.println(" === Cadastrar nova solicitação ===");

                //prompts com as informações necessárias para criar uma nova solicitação
                System.out.print("Digite a descrição: ");
                String descricao = scanner.nextLine();

                System.out.print("Digite a categoria: ");
                String categoria = scanner.nextLine();

                // Solicita a prioridade - COM TRATAMENTO DE ERRO PARA ENTRADA INVÁLIDA (se errar, pede pra digitar novamente)
                int prioridade = -1;
                boolean prioridadeValida = false;
                while (!prioridadeValida) {
                    System.out.print("Digite a prioridade (número): ");
                    try {
                        prioridade = scanner.nextInt();
                        prioridadeValida = true;
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("ERRO! DIGITE APENAS NUMEROS");
                        scanner.nextLine(); // Limpa o buffer do teclado
                    }
                }
                scanner.nextLine(); //igual c, coloquei para limpar em sempre que o codigo pedia

                System.out.print("Digite o nome do solicitante: ");
                String nome = scanner.nextLine();

                System.out.print("Digite o nome do responsavel pelo atendimento solicitante: ");
                String responsavel_atendimento = scanner.nextLine();


                

                
                //instancia a solicitação com as informações fornecidas pelo usuário, sempre vai como agurdando para a ista
                Solicitacao nova_solicitacao = new Solicitacao(contador, nome, descricao, categoria, prioridade, data_hora, "AGUARDANDO", responsavel_atendimento);

                // Adiciona na Fila, coloquei o codigo da adição da fila aqui
                filaDeSolicitacoes.enqueue(nova_solicitacao);
                System.out.println("Primeiro da fila: " + filaDeSolicitacoes.front().getSolicitante());//teste
                
                // Registra a Operacao na Pilha, ESTA CADASTRO PORQUE VOCE ACABOU DE CADASTRAR UMA NOVA PESSOAS
                Operacao operacao = new Operacao("CADASTRO", nova_solicitacao);
                pilhaDeOperacoes.push(operacao);//coloca na pilha

                break;
            }
            
            case 2:{ // CONSULTA A PROXIMA SOLICITAÇÃO
                System.out.println("Opção 2 selecionada: Consultar próxima solicitação");
                if(filaDeSolicitacoes.qIsEmpty()){
                    System.out.println("\nA fila está vazia!\n");
                }
                else{
                    System.out.println("\n =-=-=-= Próxima solicitação a ser atendida =-=-=-=");
                    System.out.println(filaDeSolicitacoes.front());
                }                
                break;
            }

            case 3:{// ATENDE A PROXIMA SOLICITAÇÃO ################### FALTA IMPLEMENTAR
                System.out.println("Opção 3 selecionada: Atender próxima solicitação");
                if(filaDeSolicitacoes.qIsEmpty()){
                    System.out.println("\nA fila está vazia! Não há solicitação para ser atendida\n");
                }
                else{
                    Solicitacao segura_solicitacao = filaDeSolicitacoes.dequeue();//dequeue tira a solicitacao ultima e coloca no segura_solicitacao
                    segura_solicitacao.setStatus("ATENDIDO"); // Atualiza o status da solicitação que esta sendo segurada
    
                    
                    Operacao operacao = new Operacao("ATENDIMENTO", segura_solicitacao);// Empilha a operação de atendimento, aqui vamos criar uma operação que é a solicitação antiga, agora atualizada, jogar com o pop para a pilha
                    pilhaDeOperacoes.push(operacao);
    
                    System.out.println("\nSolicitação atendida com sucesso!");
                    System.out.println("Código: " + segura_solicitacao.getCodigo() + " | Solicitante: " + segura_solicitacao.getSolicitante());
                }
                break;
            }
            
            case 4:{ // EXIBE A FILA DE SOLICITAÇÕES
                System.out.println("Opção 4 selecionada: Exibir fila de solicitações");
                if(filaDeSolicitacoes.qIsEmpty()){
                    System.out.println("A fila está vazia!");
                }
                else{
                    System.out.println(filaDeSolicitacoes);
                }
                break;
            }

            case 5:{ // EXIBE A QUANTIDADE DE SOLICITAÇÕES
                System.out.println("Opção 5 selecionada: Exibir quantidade de solicitações");
                System.out.printf("Quantidade de solicitações: %d\n", filaDeSolicitacoes.size());
                break;
            }

            case 6:{ // CONSULTA A ÚLTIMA OPERAÇÃO REALIZADA ################ FALTA IMPLEMENTAR
                System.out.println("Opção 6 selecionada: Consultar última operação realizada");
                if(pilhaDeOperacoes.isEmpty()){
                    System.out.println("\nNão há operações registradas!\n");
                }

                else{
                    Operacao segura_operacao = pilhaDeOperacoes.top();
                    System.out.println("Ultima Operacao realizada");
                    System.out.println("Tipo: " + segura_operacao.getTipo());
                    System.out.println("Solicitacao Codigo: " + segura_operacao.getSolicitacao().getCodigo());
                    System.out.println("Solicitante: " + segura_operacao.getSolicitacao().getSolicitante());
                }
                
                break;
            }

            case 7:{ // EXIBE O HISTÓRICO DE OPERAÇÕES
                System.out.println("Opção 7 selecionada: Exibir histórico de operações");
                if (pilhaDeOperacoes.isEmpty()) {
                    System.out.println("Nenhuma operação realizada para exibir\n");
                } 
                else{    
                    Pilha<Operacao> pilhaAuxiliar = new PilhaComArray<>();//pop perde o elemento quando tira
                    // Desempilhando e mostrando todos os nomes (pop)
                    System.out.println("\n--- HISTÓRICOS DE OPERAÇÕES ---\n");
                    while (!pilhaDeOperacoes.isEmpty()) {
                        Operacao operacao = pilhaDeOperacoes.pop();//pega o topo da pilha, lembra que pop tira e depois perde o elemento se não colocado em algum lugar
                        System.out.println("Solicitação: " + operacao.getSolicitacao().getCodigo() + " " + operacao.getTipo() );
                        pilhaAuxiliar.push(operacao);
                    }
                
                    // Devolve os elementos para a pilha normal de antes, sem ser a auxiliar
                    while (!pilhaAuxiliar.isEmpty()) {
                        pilhaDeOperacoes.push(pilhaAuxiliar.pop());
                    }  
                }              
                break;
            }

            case 8: { // DESFAZ A ÚLTIMA OPERAÇÃO - Inicio case 8
                if (pilhaDeOperacoes.isEmpty()) {
                    System.out.println("Não há nenhuma operação realizada para desfazer.");
                } else {
                    System.out.println("Opção 8 selecionada: Desfazer última operação");
                
                    // Remove a última operação realizada no topo da pilha, pega a última
                    Operacao ultimaOperacao = pilhaDeOperacoes.pop();
                
                    // vê o que tem nessa ultima operação
                    // se for um cadastro, desfaremos cadastro
                    if (ultimaOperacao.getTipo().equals("CADASTRO")) { // inicio if cadastro
                        int qtdParaMover = filaDeSolicitacoes.size() - 1;
                        TADFila<Solicitacao> filaAux = new Fila<>(10);
                    
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
                    
                        System.out.println("Último cadastro desfeito com sucesso!");
                    } // fim if cadastro
                
                    // se for um atendimento, desfaremos o status atendimento e colocamos a
                    else if (ultimaOperacao.getTipo().equals("ATENDIMENTO")) { // inicio if atendimento
                        ultimaOperacao.getSolicitacao().setStatus("AGUARDANDO");
                        
                        // Criação da fila auxiliar para recolocação do objeto na fila na sua posição original
                        TADFila<Solicitacao> filaRecolocacao = new Fila<>(10);

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
                    
                        System.out.println("Atendimento desfeito! O cliente retornou à fila na sua posição original");
                    } // fim if atendimento
                }
            
                 break;
            }// Fim case 8
             
            case 9:{ // DADOS SINTÉTICOS

                // Verifica se as solicitações de teste já estão na fila aguardando
                if (       joao != null && joao.getStatus().equals("AGUARDANDO")
                        || maria != null && maria.getStatus().equals("AGUARDANDO")
                        || pedro != null && pedro.getStatus().equals("AGUARDANDO")
                        || andre != null && andre.getStatus().equals("AGUARDANDO")
                        || carlos != null && carlos.getStatus().equals("AGUARDANDO")
                        || ana != null && ana.getStatus().equals("AGUARDANDO")
                    ) {
                    
                    System.out.println("Os dados sintéticos já foram inseridos e estão na fila!");
                    System.out.println("Atenda ou remova as solicitações atuais antes de carregar novamente.");
                    
                    break; // Cancela a Opção 9 e volta pro menu
                }
                System.out.println("Opção 9 selecionada: Dados Sintéticos");
                
        

                joao = new Solicitacao(contador+=1, "João", "Problema no sistema", "TI", 1, data_hora, "AGUARDANDO","Vital Dias");
                maria = new Solicitacao(contador+=1, "Maria", "Problema com impressora", "TI", 2, data_hora,"AGUARDANDO","Hebert Vianna");
                pedro = new Solicitacao(contador+=1, "Pedro", "Problema com computador", "TI", 3, data_hora,"AGUARDANDO","Arnaldo Antunes");
                andre = new Solicitacao(contador+=1, "André", "Problema com celular", "TI", 4, data_hora,"AGUARDANDO","Paulo Ricardo");
                carlos = new Solicitacao(contador+=1, "Carlos", "Problema com notebook", "TI", 5, data_hora,"AGUARDANDO","Paula Toller");
                ana = new Solicitacao(contador+=1, "Ana", "Problema com monitor", "TI", 6, data_hora,"AGUARDANDO","Bruno Gouveia");

                
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
                
                
                break;
            }
                
            case 0:{
                System.out.println("Encerrando o programa...");
                return true; // Sai do método exibir, encerrando o programa
            }
            default:{
                System.out.println("ERRO! ESSA OPÇÃO NÃO EXISTE");
            }
        }
        return false; // Continua o loop no método main
    }//fim exibir
}//fim menu
