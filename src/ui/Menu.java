package ui;

import java.util.Scanner;

import model.Operacao;
import model.Solicitacao;
import service.CentralDeAtendimento;



public class Menu{//inicio da classe Menu

    //instanciação da central, é ela que cuida da fila, da pilha e das regras
    private CentralDeAtendimento centralAtendimento = new CentralDeAtendimento();

    //instanciação do scanner para ler a entrada do usuário
    Scanner scanner = new Scanner(System.in);


    //método exibir, para mostrar o menu, chamado anteriormente pelo main
    public boolean exibir() throws Exception{//inicio exibir

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
            "10 - Exibir todas as solicitações" +
            "11 - Pesquisar solicitação por código" +
            "12 - Remover solicitação " +
            "13 - Exibir solicitações por status" +
            "14 - Exibir solicitações por categoria" +
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
                if (centralAtendimento.filaCheia()) {
                    System.out.println("\nA fila está cheia! Não é possível cadastrar novas solicitações.\n");
                    break;
                }

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


                
                centralAtendimento.cadastrar(nome, descricao, categoria, prioridade);
                

                //TIRA DAQUI DEPOIS, É SÓ DE TESTEEEEEEEEEE
                System.out.println("Primeiro da fila: " + centralAtendimento.primeiraDaFila().getSolicitante() + " | Codigo: " + centralAtendimento.primeiraDaFila().getCodigo());//teste

                break;
            }

            case 2:{ // CONSULTA A PROXIMA SOLICITAÇÃO
                System.out.println("Opção 2 selecionada: Consultar próxima solicitação");
                if(centralAtendimento.filaVazia()){
                    System.out.println("\nA fila está vazia!\n");
                }
                else{
                    System.out.println("\n =-=-=-= Próxima solicitação a ser atendida =-=-=-=");
                    System.out.println(centralAtendimento.primeiraDaFila());
                }
                break;
            }

            case 3:{
                System.out.println("Opção 3 selecionada: Atender próxima solicitação");
                if(centralAtendimento.filaVazia()){
                    System.out.println("\nA fila está vazia! Não há solicitação para ser atendida\n");
                }
                else{
                    scanner.nextLine();//novamente, sem isso fica dando erro, não me deixa digitar, assim como em c

                    System.out.print("Digite o nome do responsável pelo atendimento: ");
                    String responsavelAtendimento = scanner.nextLine();

                    // a central tira da fila, muda o status e empilha a operação
                    Solicitacao seguraSolicitacao = centralAtendimento.atender(responsavelAtendimento);

                    System.out.println("\nSolicitação em atendimento com sucesso!");
                    System.out.println("Código: " + seguraSolicitacao.getCodigo() + " | Solicitante: " + seguraSolicitacao.getSolicitante());
                }
                break;
            }

            case 4:{ // EXIBE A FILA DE SOLICITAÇÕES
                System.out.println("Opção 4 selecionada: Exibir fila de solicitações");
                if(centralAtendimento.filaVazia()){
                    System.out.println("A fila está vazia!");
                }
                else{
                      System.out.println(centralAtendimento.getFila());
                }
                break;
            }

            case 5:{ // EXIBE A QUANTIDADE DE SOLICITAÇÕES
                System.out.println("Opção 5 selecionada: Exibir quantidade de solicitações");
                System.out.printf("Quantidade de solicitações: %d\n", centralAtendimento.tamanhoFila());
                break;
            }

            case 6:{ // CONSULTA A ÚLTIMA OPERAÇÃO REALIZADA
                System.out.println("Opção 6 selecionada: Consultar última operação realizada");
                if(centralAtendimento.pilhaVazia()){
                    System.out.println("\nNão há operações registradas!\n");
                }

                else{
                    Operacao seguraOperacao = centralAtendimento.ultimaOperacao();
                    System.out.println("Ultima Operacao realizada");
                    System.out.println("Tipo: " + seguraOperacao.getTipo());
                    System.out.println("Solicitacao Codigo: " + seguraOperacao.getSolicitacao().getCodigo());
                    System.out.println("Solicitante: " + seguraOperacao.getSolicitacao().getSolicitante());
                }

                break;
            }

            case 7:{ // EXIBE O HISTÓRICO DE OPERAÇÕES
                System.out.println("Opção 7 selecionada: Exibir histórico de operações");
                if (centralAtendimento.pilhaVazia()) {
                    System.out.println("Nenhuma operação realizada para exibir\n");
                }
                else{
                    System.out.println("\n--- HISTÓRICOS DE OPERAÇÕES ---\n");
                    // a central desempilha, monta o texto e devolve a pilha como estava
                    System.out.print(centralAtendimento.historico());
                }
                break;
            }

            case 8: { // DESFAZ A ÚLTIMA OPERAÇÃO - Inicio case 8
                if (centralAtendimento.pilhaVazia()) {
                    System.out.println("Não há nenhuma operação realizada para desfazer.");
                } else {
                    System.out.println("Opção 8 selecionada: Desfazer última operação");

                    // a central desfaz e devolve a mensagem de como foi
                    System.out.println(centralAtendimento.desfazer());
                }

                 break;
            }// Fim case 8

            case 9:{ // DADOS SINTÉTICOS

                // a central devolve false se as solicitações de teste já estão na fila aguardando
                if (!centralAtendimento.inserirDadosSinteticos()) {

                    System.out.println("Os dados sintéticos já foram inseridos e estão na fila!");
                    System.out.println("Atenda ou remova as solicitações atuais antes de carregar novamente.");

                    break; // Cancela a Opção 9 e volta pro menu
                }
                System.out.println("Opção 9 selecionada: Dados Sintéticos");

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
