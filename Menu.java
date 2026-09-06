import java.util.Scanner;


public class Menu {//inicio da classe Menu

    //contador para controlar a quantidade de solicitações cadastradas, e numera a solicitação
    int contador=0;


    // Declaração e instanciação da Fila e da Pilha, eu deixei a instancia da Fila em comentario, já que eu não tenho a fila ainda
    /* 
    public Fila<Solicitacao> filaDeSolicitacoes = new FilaComArray<>();
    */
    public Pilha<Operacao> pilhaDeOperacoes = new PilhaComArray<>();


    //instanciação da pilha de solicitações
    public Pilha<Solicitacao> pilhaDeNomes = new PilhaComArray<>();

    //instanciação do scanner para ler a entrada do usuário
    Scanner scanner = new Scanner(System.in);
    

    //método exibir, para mostrar o menu, chamado anteriormente pelo main
    public boolean exibir() {

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




        /*Operações mínimas
        O programa deverá permitir:
        1. cadastrar nova solicitação;                      corresponde ao 1 do menu
        2. inserir a solicitação na Fila;                    
        3. consultar a próxima solicitação;                 corresponde ao 3 do menu    
        4. remover a próxima solicitação para atendimento;   
        5. verificar se a Fila está vazia;                  
        6. mostrar a quantidade de solicitações aguardando;     
        7. exibir as solicitações presentes na Fila.         corresponde ao 4 do menu
        */




        //solicita ao usuário que escolha uma opção do menu
        System.out.println("Escolha uma opção: ");
        int opcao = scanner.nextInt();

        //condicional para verificar a opção escolhida pelo usuário
        if (opcao ==1){//inicio if 1
            contador++; //soma o contador toda vz que apertamos para fazer uma nova solicitação, mesmo que desfeita ou manipulada, ela contará como uma.

            // Limpa o buffer do teclado
            scanner.nextLine();  //igual o de c, não muda nada

            System.out.println("Cadastrar nova solicitação");

            //prompets com as informações necessárias para criar uma nova solicitação
            System.out.println("Digite a descrição: ");
            String descricao = scanner.nextLine();

            System.out.println("Digite a categoria: ");
            String categoria = scanner.nextLine();

            System.out.println("Digite a prioridade (número): ");
            int prioridade = scanner.nextInt();
            scanner.nextLine(); //igual c, coloquei para limpar em sempre que o codigo pedia

            System.out.println("Digite o nome do solicitante: ");
            String nome = scanner.nextLine();
            
    

            //instancia a solicitação com as informações fornecidas pelo usuário
            Solicitacao  nova_solicitacao = new Solicitacao(contador, nome, descricao, categoria, prioridade,"AGUARDANDO");
            

            // Adiciona na Fila, coloquei o codigo da adição da fila aqui

            
            // Registra a Operacao na Pilha, ESTA CADASTRO PORQUE VOCE ACABOU DE CADASTRAR UMA NOVA PESSOAS
            Operacao operacao = new Operacao("CADASTRO", nova_solicitacao);
            pilhaDeOperacoes.push(operacao);
            

        }//fim if 1
    




        //condicional para verificar a opção escolhida pelo usuário
        if (opcao==5){//inicio if 5
            System.out.printf("Quantidade de solicitação: %d\n", contador);
        }//fim if 5
    

        if(opcao==7){
             // Desempilhando e mostrando todos os nomes (pop)
            System.out.println("\n--- DESEMPILHANDO OS NOMES ---");
            while (!pilhaDeNomes.isEmpty()) {

                Solicitacao solicitacao = pilhaDeNomes.pop();
                System.out.println("Nome desempilhado: " + solicitacao.solicitante);
            }
        }



        if(opcao==9){
            Solicitacao joao = new Solicitacao(1, "João", "Problema no sistema", "TI", 1, "AGUARDANDO");
            Solicitacao maria = new Solicitacao(2, "Maria", "Problema com impressora", "TI", 2, "AGUARDANDO");
            Solicitacao pedro = new Solicitacao(3, "Pedro", "Problema com computador", "TI", 3, "AGUARDANDO");
            contador=3;
        

            // Usando o novo nome "Pilha" e "PilhaComArray"
            
            pilhaDeNomes.push(joao);
            pilhaDeNomes.push(maria);
            pilhaDeNomes.push(pedro);

               
            System.out.println("Nome no topo: " + pilhaDeNomes.top().solicitante);
            
        }


        if(opcao==0){//inicio if 0
            System.out.println("Encerrando o programa...");
            return true; // Sai do método exibir, encerrando o programa
        }//fim if 0
        
        return false; // Continua o loop no método main
    }
}


    
    


/*
while (resposta != 0) { // Compara com o inteiro 0
    System.out.print("Digite o nome: ");
    String nome = scanner.nextLine();
    pilhaDeNomes.push(nome);

    System.out.print("Quer digitar outro nome? (0/1): ");
    resposta = scanner.nextInt(); // Pede a resposta novamente para atualizar a variável do loop
    scanner.nextLine(); // Limpa o buffer do teclado
}
*/