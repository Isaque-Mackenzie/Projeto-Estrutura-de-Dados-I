import java.util.Scanner;

public class Menu {//inicio da classe Menu

    //contador para controlar a quantidade de solicitações cadastradas, e numera a solicitação
    int contador=0;

    //instanciação da Pilha
    public Pilha<Operacao> pilhaDeOperacoes = new PilhaComArray<>();
    //instanciação da Fila
    public TADFila<Solicitacao> filaDeSolicitacoes = new Fila<>(5);

    //instanciação do scanner para ler a entrada do usuário
    Scanner scanner = new Scanner(System.in);

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

                //instancia a solicitação com as informações fornecidas pelo usuário, sempre vai como agurdando para a ista
                Solicitacao nova_solicitacao = new Solicitacao(contador, nome, descricao, categoria, prioridade,"AGUARDANDO");

                // Adiciona na Fila, coloquei o codigo da adição da fila aqui
                filaDeSolicitacoes.enqueue(nova_solicitacao);
                System.out.println("Primeiro da fila: " + filaDeSolicitacoes.front().solicitante);//teste
                
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
                    System.out.println("\nA fila está vazia!\n");
                }
                else{
                    Solicitacao segura_solicitacao = filaDeSolicitacoes.dequeue();//dequeeu tira a solicitacao ultima e coloca no segura_solicitacao
                    segura_solicitacao.status = "ATENDIDO"; // Atualiza o status da solicitação que esta sendo segurada
    
                    
                    Operacao operacao = new Operacao("ATENDIMENTO", segura_solicitacao);// Empilha a operação de atendimento, aqui vamos criar uma operação que é a solicitação antiga, agora atualizada, jogar com o pop para a pilha
                    pilhaDeOperacoes.push(operacao);
    
                    System.out.println("\nSolicitação atendida com sucesso!");
                    System.out.println("Código: " + segura_solicitacao.codigo + " | Solicitante: " + segura_solicitacao.solicitante);
                }
                break;
            }
            
            case 4:{ // EXIBE A FILA DE SOLICITAÇÕES
                System.out.println("Opção 4 selecionada: Exibir fila de solicitações");
                if(filaDeSolicitacoes.qIsEmpty()){
                    System.out.println("A fila está vazia!");
                }
                else{
                    filaDeSolicitacoes.exibir_fila();
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
                Operacao segura_operacao = pilhaDeOperacoes.top();
                System.out.println("Ultima Operacao realizada");
                System.out.println("Tipo: " + segura_operacao.tipo);
                System.out.println("Solicitacao Codigo: " + segura_operacao.solicitacao.codigo);
                System.out.println("Solicitante: " + segura_operacao.solicitacao.solicitante);
                break;
            }

            case 7:{ // EXIBE O HISTÓRICO DE OPERAÇÕES
                System.out.println("Opção 7 selecionada: Exibir histórico de operações");
                Pilha<Operacao> pilhaAuxiliar = new PilhaComArray<>();//pop perde o elemento quando tira
                // Desempilhando e mostrando todos os nomes (pop)
                System.out.println("\n--- DESEMPILHANDO OS NOMES ---");
                while (!pilhaDeOperacoes.isEmpty()) {
                    Operacao operacao = pilhaDeOperacoes.pop();//pega o topo da pilha, lembra que pop tira e depois perde o elemento se não colocado em algum lugar
                    System.out.println("Solicitação: " + operacao.solicitacao.codigo + " " + operacao.tipo );
                    pilhaAuxiliar.push(operacao);
                }
            
                // Devolve os elementos para a pilha normal de antes, sem ser a auxiliar
                while (!pilhaAuxiliar.isEmpty()) {
                    pilhaDeOperacoes.push(pilhaAuxiliar.pop());
                }                
                break;
            }

            case 8:{ // DESFAZ A ÚLTIMA OPERAÇÃO ############ FALTA IMPLEMENTAR
                System.out.println("Opção 8 selecionada: Desfazer última operação");
                // Remove a última operação realizada no topo da pilha, pega a ultima do topo da pilha e coloca no ultima operação
                Operacao ultimaOperacao = pilhaDeOperacoes.pop();

                //ve oque tem nessa ultima operação
                //se for um cadastro, desfaremos cadastro
                if (ultimaOperacao.tipo=="CADASTRO"){//inicio if cadastro
                    //tirar o ultimo elemnto da fila
                }//fim if cadastro
                

                //se for um atendimento, desfaremos o status atendimento e colocamos aguardando
                else if (ultimaOperacao.tipo=="ATENDIMENTO"){//inicio if atendimento
                    ultimaOperacao.solicitacao.status="AGUARDANDO";
                    filaDeSolicitacoes.enqueue(ultimaOperacao.solicitacao);//COLOCA NA FILA DE SOLICITACAO POR ULTIMO
                }//fim if atendimento
                break;
            }

            case 9:{ // DADOS SINTÉTICOS
                System.out.println("Opção 9 selecionada: Dados Sintéticos");
                Solicitacao joao = new Solicitacao(1, "João", "Problema no sistema", "TI", 1, "AGUARDANDO");
                Solicitacao maria = new Solicitacao(2, "Maria", "Problema com impressora", "TI", 2, "AGUARDANDO");
                Solicitacao pedro = new Solicitacao(3, "Pedro", "Problema com computador", "TI", 3, "AGUARDANDO");
            
                filaDeSolicitacoes.enqueue(joao);
                filaDeSolicitacoes.enqueue(maria);
                filaDeSolicitacoes.enqueue(pedro);

                pilhaDeOperacoes.push(new Operacao("CADASTRO", joao));
                pilhaDeOperacoes.push(new Operacao("CADASTRO", maria));
                pilhaDeOperacoes.push(new Operacao("CADASTRO", pedro));

                contador += 3;
               
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


    
    
