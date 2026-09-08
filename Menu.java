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
            "1 - Cadastrar nova solicitação\n" +
            "2 - Consultar próxima solicitação\n" +
            "3 - Atender próxima solicitação\n" +
            "4 - Exibir fila de solicitações\n" +
            "5 - Exibir quantidade de solicitações\n" +
            "6 - Consultar última operação realizada\n" +
            "7 - Exibir histórico de operações\n" +
            "8 - Desfazer última operação\n" +
            "9 - TESTE\n" +
            "0 - Encerrar\n"
        );//fim menu



        //solicita ao usuário que escolha uma opção do menu
        System.out.print("\nEscolha uma opção: ");
        int opcao = scanner.nextInt();

        //condicional para verificar a opção escolhida pelo usuário
        if (opcao ==1){//inicio if 1
            contador++; //soma o contador toda vez que apertamos para fazer uma nova solicitação, mesmo que desfeita ou manipulada, ela contará como uma.

            // Limpa o buffer do teclado
            scanner.nextLine();  //igual o de c, não muda nada

            System.out.println(" === Cadastrar nova solicitação ===");

            //prompets com as informações necessárias para criar uma nova solicitação
            System.out.print("Digite a descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Digite a categoria: ");
            String categoria = scanner.nextLine();

            System.out.print("Digite a prioridade (número): ");
            int prioridade = scanner.nextInt();
            scanner.nextLine(); //igual c, coloquei para limpar em sempre que o codigo pedia

            System.out.print("Digite o nome do solicitante: ");
            String nome = scanner.nextLine();
            
    

            //instancia a solicitação com as informações fornecidas pelo usuário, sempre vai como agurdando para a ista
            Solicitacao  nova_solicitacao = new Solicitacao(contador, nome, descricao, categoria, prioridade,"AGUARDANDO");
            

            // Adiciona na Fila, coloquei o codigo da adição da fila aqui
            filaDeSolicitacoes.enqueue(nova_solicitacao);
            System.out.println("Primeiro da fila: " + filaDeSolicitacoes.front().solicitante);//teste
            
            // Registra a Operacao na Pilha, ESTA CADASTRO PORQUE VOCE ACABOU DE CADASTRAR UMA NOVA PESSOAS
            Operacao operacao = new Operacao("CADASTRO", nova_solicitacao);
            pilhaDeOperacoes.push(operacao);//coloca na pilha
            

        }//fim if 1
        
        // Mostra próxima solicitação a ser atendida
        if(opcao == 2){ // Inicio if 2
            if(filaDeSolicitacoes.qIsEmpty()){
                System.out.println("\nA fila está vazia!\n");
            }
            else{
                System.out.println("\n === Próxima solicitação a ser atendida ===");
                System.out.println(filaDeSolicitacoes.front());

            }
        } // Fim if 2

        // Mostra a fila completa de solicitações
        if (opcao == 4){ // Inicio if 4
            if(filaDeSolicitacoes.qIsEmpty()){
                System.out.println("A fila está vazia!");
            }
            else{
                filaDeSolicitacoes.exibir_fila();
            }
        } // Fim if 4

        //condicional para verificar a opção escolhida pelo usuário
        if (opcao==5){//inicio if 5
            System.out.printf("Quantidade de solicitações: %d\n", filaDeSolicitacoes.size());
        }//fim if 5
    

        if(opcao==7){//inicio if 7

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

        }//fim if 7



        if(opcao==9){//inicio if 9
            Solicitacao joao = new Solicitacao(1, "João", "Problema no sistema", "TI", 1, "AGUARDANDO");
            Solicitacao maria = new Solicitacao(2, "Maria", "Problema com impressora", "TI", 2, "AGUARDANDO");
            Solicitacao pedro = new Solicitacao(3, "Pedro", "Problema com computador", "TI", 3, "AGUARDANDO");
        
            filaDeSolicitacoes.enqueue(joao);
            filaDeSolicitacoes.enqueue(maria);
            filaDeSolicitacoes.enqueue(pedro);

            contador += 3;

            // Usando o novo nome "Pilha" e "PilhaComArray"
            
            //pilhaDeNomes.push(joao);
            //pilhaDeNomes.push(maria);
            //pilhaDeNomes.push(pedro);

            // System.out.println("Nome no topo: " + pilhaDeNomes.top().solicitante);
            
        }//fim if 9


        if(opcao==0){//inicio if 0
            System.out.println("Encerrando o programa...");
            return true; // Sai do método exibir, encerrando o programa
        }//fim if 0
       
        
        return false; // Continua o loop no método main
    }//fim exibir
}//fim menu


    
    
