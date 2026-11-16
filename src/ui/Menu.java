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
            "\n=================================================\n" +
            "        CENTRAL DE ATENDIMENTO E SERVIÇOS        \n" +
            "=================================================\n" +
            "ATENDIMENTO\n" +
            "1 - Cadastrar nova solicitação\n" + //
            "2 - Consultar próxima solicitação\n" + //
            "3 - Atender próxima solicitação\n" + //
            "4 - Exibir fila de espera\n" + //
            "5 - Quantidade de solicitações aguardando\n\n" + //
            
            "OPERAÇÕES\n" +
            "6 - Consultar última operação\n" + //
            "7 - Concluir atendimento\n" + //################### era a 15
            "8 - Exibir histórico recente\n" + // ######### e a 7
            "9 - Desfazer última operação\n\n" + // ######### e a 8
            
            "CADASTRO GERAL\n" +
            "10 - Exibir todas as solicitações\n" + //
            "11 - Pesquisar solicitação por código\n" + //
            "12 - Remover solicitação\n" + //
            "13 - Exibir solicitações por status\n" + //
            "14 - Exibir solicitações por categoria\n\n" + 
            
            "HISTÓRICO DE ATENDIMENTOS\n" +
            "15 - Exibir do primeiro ao último\n" + // 
            "16 - Exibir do último ao primeiro\n" +
            "17 - Navegar pelo histórico\n" +
            "18 - Pesquisar atendimento\n\n" +
            
            "RELATÓRIOS E SISTEMA\n" +
            "19 - Exibir resumo do sistema\n" +
            "20 - Inserir Dados Sintéticos\n\n" + // Funcionalidade extra do menu antigo mantida aqui
            
            "0 - Encerrar\n" +
            "================================================="
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
            case 1:{ // CADASTRAR NOVA SOLICITAÇÃO
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

                System.out.println("Prioridades: 1 = Alta | 2 = Baixa");

                // Solicita a prioridade - COM TRATAMENTO DE ERRO PARA ENTRADA INVÁLIDA (se errar, pede pra digitar novamente)
                int prioridade = -1;
                boolean prioridadeValida = false;
                while (!prioridadeValida) {
                    System.out.print("Digite a prioridade (número): ");
                    try {
                        prioridade = scanner.nextInt();
                        prioridadeValida = true;
                        // PRIORIDADE (NOVO): só aceita 1 ou 2
                        if (prioridade != 1 && prioridade != 2) {
                            System.out.println("ERRO! A PRIORIDADE DEVE SER 1 (ALTA) OU 2 (BAIXA)");
                            prioridadeValida = false;
                        }
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("ERRO! DIGITE APENAS NUMEROS");
                        scanner.nextLine(); // Limpa o buffer do teclado
                    }
                }
                scanner.nextLine(); //igual c, coloquei para limpar em sempre que o codigo pedia

                System.out.print("Digite o nome do solicitante: ");
                String nome = scanner.nextLine();


                
                centralAtendimento.cadastrar(nome, descricao, categoria, prioridade);
                

                //TODO: VERIFIQUE OS PONTOS QUE O PROFESSOR TIROU PONTO, ISSO AQUI ESTA TIRANDO PONTO
                System.out.println("Primeiro da fila: " + centralAtendimento.primeiraDaFila().getSolicitante() + " | Codigo: " + centralAtendimento.primeiraDaFila().getCodigo());//teste

                break;
            }

            case 2:{ // CONSULTAR PROXIMA SOLICITAÇÃO
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

            case 3:{ // ATENDER A PROXIMA SOLICITAÇÃO
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
                    System.out.println("Prioridade: " + seguraSolicitacao.getPrioridade());                    
                }
                break;
            }

            case 4:{ // EXIBIR A FILA DE ESPERA
                System.out.println("Opção 4 selecionada: Exibir fila de espera");
                if(centralAtendimento.filaVazia()){
                    System.out.println("A fila está vazia!");
                }
                else{
                      System.out.println(centralAtendimento.filaGlobal());
                }
                break;
            }

            case 5:{ // QUANTIDADE DE SOLICITAÇÕES AGUARDANDO
                System.out.println("Opção 5 selecionada: Quantidade de solicitações aguardando");
                System.out.printf("Quantidade de solicitações: %d\n", centralAtendimento.tamanhoFila());

                System.out.printf("  Prioridade 1 (Alta): %d\n", centralAtendimento.tamanhoPrioridade(1));
                System.out.printf("  Prioridade 2 (Baixa): %d\n", centralAtendimento.tamanhoPrioridade(2));                
                break;
            }

            case 6:{ // CONSULTAR A ÚLTIMA OPERAÇÃO
                System.out.println("Opção 6 selecionada: Consultar a última operação");
                if(centralAtendimento.pilhaVazia()){
                    System.out.println("\nNão há operações registradas!\n");
                }

                else{
                    Operacao seguraOperacao = centralAtendimento.ultimaOperacao();
                    System.out.println("Ultima Operacao realizada");
                    System.out.println("Tipo: " + seguraOperacao.getTipo());
                    System.out.println("Solicitacao Codigo: " + seguraOperacao.getSolicitacao().getCodigo());
                    System.out.println("Solicitante: " + seguraOperacao.getSolicitacao().getSolicitante());
                    System.out.println("Prioridade: " + seguraOperacao.getSolicitacao().getPrioridade()); // PRIORIDADE (NOVO)
                }

                break;
            }

            case 7:{ // CONCLUIR ATENDIMENTO
                System.out.println("Opção 7 selecionada: Concluir atendimento");

                if (!centralAtendimento.existeEmAtendimento()) {
                    System.out.println("\nNão há nenhuma solicitação EM_ATENDIMENTO para concluir!\n");
                    break;
                }

                // usa o buscaStatus para listar quem está em atendimento
                System.out.println("\nSOLICITAÇÕES EM ATENDIMENTO\n");
                System.out.println(centralAtendimento.buscaStatus("EM_ATENDIMENTO"));

                System.out.print("Digite o código da solicitação a concluir: ");
                scanner.nextLine(); // limpa o buffer do teclado

                int codigoConcluir;
                try {
                    codigoConcluir = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("ERRO! DIGITE APENAS NUMEROS");
                    break;
                }

                System.out.println("\n" + centralAtendimento.concluirAtendimento(codigoConcluir));
                break;
            }
            case 8:{ // EXIBIR HISTÓRICO RECENTE
                System.out.println("Opção 8 selecionada: Exibir histórico recente");
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

            case 9:{ // DESFAZER ÚLTIMA OPERAÇÃO
                if (centralAtendimento.pilhaVazia()) {
                    System.out.println("Não há nenhuma operação realizada para desfazer.");
                } else {
                    System.out.println("Opção 9 selecionada: Desfazer última operação");

                    // a central desfaz e devolve a mensagem de como foi
                    System.out.println(centralAtendimento.desfazer());
                }

                 break;
            }// Fim case 8




            case 10:{ // EXIBE TODAS AS SOLICITAÇÕES (percorre a lista simples)
                System.out.println("Opção 10 selecionada: Exibir todas as solicitações");
 
                if(centralAtendimento.listaSimplesVazia()){//se a lista de solicitação tiver vazia
                    System.out.println("\nNão há nenhuma solicitação cadastrada!\n");
                }
                else{
                    System.out.println("\nCADASTRO GERAL DE SOLICITAÇÕES\n");//se tiver alguma coisa na ista de solicitação
                    System.out.print(centralAtendimento.listar());
                }
                break;
            }
 


            case 11:{ // PESQUISAR SOLICITAÇÃO POR CÓDIGO
                System.out.println("Opção 11 selecionada: Pesquisar Solicitação por codigo");

                //se a lista tiver vazia ENTÃO
                    //tratamento de erro
                if(centralAtendimento.listaSimplesVazia()){
                    System.out.println("\nNão há nada na Lista!\n");
                    break;
                }


                //se não tiver o fluxo segue
              
                System.out.println("Digite o codigo da solicitação: ");
                scanner.nextLine();
                int codigoDaBusca;

                //leitura do codigoDa busca
                codigoDaBusca = Integer.parseInt(scanner.nextLine().trim());

                //TODO=NÃO ÉSQUECER DE COLOCAR ESSES TRATAMENTOS DE ERROS ABAIXO       
                //se codiDa Busca <= 0 então da valor invalido
                //se codigoDaBusca > que o size da lista, da valor invalido
                //se digitar letra, da valor invalido

                Solicitacao achei = centralAtendimento.buscaCodigo(codigoDaBusca);

                //na volta se não achou, mensagem de erro
                //se achou, um print
                System.out.println("\nSolicitaçao");

                //sera que esse print imprime a solicitação? vou tentar o mais facil
                System.out.println(achei);

                break;

            } 



            case 12:{ // REMOVER SOLICITAÇÃO
                System.out.println("Opção 12 selecionada: Remover solicitação");
                if(centralAtendimento.listaSimplesVazia()){
                    System.out.println("\nNão há nada na Lista!\n");
                    break;
                }

                
                System.out.println("Digite o codigo da solicitação para remoção: ");
                scanner.nextLine();
                int codigoDaBusca;

                //leitura do codigoDa busca
                codigoDaBusca = Integer.parseInt(scanner.nextLine().trim());

                centralAtendimento.buscaRemove(codigoDaBusca);


                break;
            }




            case 13:{ // EXIBIR SOLICITAÇÕES POR STATUS
                System.out.println("Opção 13 selecionada: Exibir solicitações por status");
                if(centralAtendimento.listaSimplesVazia()){
                    System.out.println("\nNão há nada na Lista!\n");
                    break;
                }

                else{
                    System.out.println("Digite o Status da solicitação [AGUARDANDO/EM_ANDAMENTO/CONCLUIDA/CANCELADA]: ");
                    scanner.nextLine();
                    String statusDaBusca;

                    statusDaBusca = scanner.nextLine().trim();

                    if (!statusDaBusca.equalsIgnoreCase("AGUARDANDO") && !statusDaBusca.equalsIgnoreCase("EM_ATENDIMENTO") && !statusDaBusca.equalsIgnoreCase("CONCLUIDA") && !statusDaBusca.equalsIgnoreCase("CANCELADA")) {
                        System.out.println("\nStatus inválido!\n");
                        break;
                    }
                    System.out.println("\nSOLICITAÇÕES POR STATUS\n");//se tiver alguma coisa na ista de solicitação
                    String achei = centralAtendimento.buscaStatus(statusDaBusca);  
                    System.out.println(achei);
                }  
                break; 
            }

            case 14:{ // EXIBIR SOLICITAÇÕES POR CATEGORIA
                System.out.println("Opção 14 selecionada: Exibir solicitações por categoria");
                if(centralAtendimento.listaSimplesVazia()){
                    System.out.println("\nNão há nada na Lista!\n");
                    break;
                }

                else{
                    System.out.print("Digite a categoria: ");
                    scanner.nextLine();
                    String categoriaDaBusca;

                    categoriaDaBusca = scanner.nextLine().trim();

                    // categoria O USUARIO QUE DIGITA, então não sendo null vale tudo (ler novamento do documento para saber se o professor não colocou nem um criterio)
                    if (categoriaDaBusca.isEmpty()) {
                        System.out.println("\nCategoria inválida!\n");
                        break;
                    }
                    System.out.println("\nSOLICITAÇÕES POR CATEGORIA\n");
                    String achei = centralAtendimento.buscaCategoria(categoriaDaBusca);
                    System.out.println(achei);
                }
                break;
            }
            




            case 15:{ // EXIBIR DO PRIMEIRO AO ÚLTIMO
                System.out.println("Opção 15 selecionada: Exibir Historico de Atendimento (primeiro ao ultimo)\n");

                if (centralAtendimento.listaDuplaVazia()) {
                    System.out.println("\nNão há nenhuma solicitação no Historico.\n");
                    break;
                }
                
                System.out.println("\nHistorico de Atendimento (Do Primeiro O Ultimo)\n");
                centralAtendimento.atendimentoPU();//ATENDIMENTO PRIMEIRO AO ULTIMO
                System.out.println(); 
                 break;


            }


            case 16:{ // EXIBIR DO ÚLTIMO AO PRIMEIRO
                System.out.println("Opção 16 selecionada: Exibir Historico de Atendimento (ultimo ao primeiro)\n");

                if (centralAtendimento.listaDuplaVazia()) {
                    System.out.println("\nNão há nenhuma solicitação no Historico.\n");
                    break;
                }
                
                System.out.println("\nHistorico de Atendimento (Do ultimo ao Primeiro)\n");
                centralAtendimento.atendimentoUP();//ATENDIMENTO ULTIMO AO PRIMEIRO
                System.out.println(); 
                break;

            }



            case 17:{ // NAVEGAR PELO HISTÓRICO
                
                System.out.println("Opção 17 selecionada: Navegar pelo histórico");

                if (centralAtendimento.listaDuplaVazia()) {
                    System.out.println("\nNenhum atendimento concluído ainda!\n");
                    break;
                }

                scanner.nextLine(); // limpa o buffer do teclado

                centralAtendimento.inicio(); // começa no primeiro atendimento

                String opcaoN= "";
                while (!opcaoN.equalsIgnoreCase("S")) {   //enquanto a pessoa não digitar S (de sair) ela navegará pelo historico
                    System.out.println("\nAtendimento: ");
                    System.out.println(centralAtendimento.atendimento());   //toString na central, vai imprimir o atendimento
                    System.out.println("\n[A] Anterior | [P] Próximo | [S] Sair");
                    System.out.print("Opcao: ");

                    opcaoN = scanner.nextLine().trim();

                    if (opcaoN.equalsIgnoreCase("A")) {
                        if (!centralAtendimento.Anterior()) {
                            System.out.println("\nEsse é primeito atendimento.");
                        }
                    }
                    else if (opcaoN.equalsIgnoreCase("P")) {
                        if (!centralAtendimento.Proximo()) {
                            System.out.println("\nEsse é o ultimo.");
                        }
                    }
                    else if (!opcaoN.equalsIgnoreCase("S")) {
                        System.out.println("\nOpção inválida.");
                    }
                }
                break;
            }
            




             case 18:{ // PESQUISAR ATENDIMENTO (pelo código da solicitação)
                System.out.println("Opção 18 selecionada: Pesquisar atendimento");

                if (centralAtendimento.listaDuplaVazia()) {
                    System.out.println("\nNenhum atendimento concluído ainda!\n");
                    break;
                }

                scanner.nextLine(); // limpa o buffer do teclado

                System.out.print("Digite o código da solicitação: ");
                int codigoAtendimento;
                try {
                    codigoAtendimento = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("ERRO! DIGITE APENAS NUMEROS");
                    break;
                }

                Solicitacao atendimentoAchado = centralAtendimento.buscaAtendimento(codigoAtendimento);

                if (atendimentoAchado == null) {
                    System.out.println("\nNão há atendimento concluído com o código " + codigoAtendimento + "\n");
                } else {
                    System.out.println("\nAtendimento encontrado:");
                    System.out.println(atendimentoAchado);
                }
                break;
            }



            case 19:{ // EXIBIR RESUMO DO SISTEMA
                System.out.println("Opção 19 selecionada: Exibir resumo do sistema");
                System.out.println(
                    "\n=================================================\n" +
                    "               RESUMO DO SISTEMA                 \n" +
                    "=================================================\n" +
                    "Total de solicitações cadastradas: " + centralAtendimento.total() + "\n" +
                    " - Aguardando Prioridade 1 (Alta): " + centralAtendimento.aguardandoP1() + "\n" +
                    " - Aguardando Prioridade 2 (Baixa): " + centralAtendimento.aguardandoP2() + "\n" +
                    " - Em Atendimento: " + centralAtendimento.emAtendimento() + "\n" +
                    " - Concluídas (no Histórico): " + centralAtendimento.concluidos() + "\n" +
                    " - Canceladas / Removidas: " + centralAtendimento.canceladas() + "\n" +
                    "Quantidade de operações na Pilha: " + centralAtendimento.pilhaDeOperacoes() + "\n" +
                    "=================================================\n"
                );
                
    

                break;
            }


            case 20:{ // DADOS SINTÉTICOS

                // a central devolve false se as solicitações de teste já estão na fila aguardando
                if (!centralAtendimento.inserirDadosSinteticos()) {

                    System.out.println("Os dados sintéticos já foram inseridos e estão na fila!");
                    System.out.println("Atenda ou remova as solicitações atuais antes de carregar novamente.");

                    break; // Cancela a Opção 20 e volta pro menu
                }
                System.out.println("Opção 20 selecionada: Inserir Dados Sintéticos");

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

