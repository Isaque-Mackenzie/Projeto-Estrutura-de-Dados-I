import java.util.Scanner;
//class menu
    //metodo exibir, para mostrar o menu, chamado anteriormente pelo main
    //print com as opções de menu
    //condições para cada opção, ou seja, se apertar em solicitção, ele chama outro aqrquivo onde esta a solução, não esqueça de instanciar para uso dos metodos.
public class Menu {
    int contador=0;
    public Pilha<Solicitacao> pilhaDeNomes = new PilhaComArray<>();

    public void exibir() {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println(
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
            
        );
        System.out.println("Escolha uma opção: ");
        int opcao = scanner.nextInt();


        if (opcao ==1){
            contador++;

            // Limpa o buffer do teclado
            scanner.nextLine();
            System.out.println("Cadastrar nova solicitação");

            System.out.println("Digite a descrição: ");
            String descricao = scanner.nextLine();

            System.out.println("Digite a categoria: ");
            String categoria = scanner.nextLine();

            System.out.println("Digite a prioridade (número): ");
            int prioridade = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Digite o nome do solicitante: ");
            String nome = scanner.nextLine();
            

            System.out.println("Digite o status: ");
            String status = scanner.nextLine();
        
            

            Solicitacao  nova_solicitaçao = new Solicitacao(contador, nome, descricao, categoria, prioridade, status);
            pilhaDeNomes.push(nova_solicitaçao);
        }
    

        if(opcao==9){
            Solicitacao joao = new Solicitacao(1, "João", "Problema no sistema", "TI", 1, "AGUARDANDO");
            Solicitacao maria = new Solicitacao(2, "Maria", "Problema com impressora", "TI", 2, "AGUARDANDO");
            Solicitacao pedro = new Solicitacao(3, "Pedro", "Problema com computador", "TI", 3, "AGUARDANDO");
            
        

            // Usando o novo nome "Pilha" e "PilhaComArray"
            
            pilhaDeNomes.push(joao);
            pilhaDeNomes.push(maria);
            pilhaDeNomes.push(pedro);

               
            System.out.println("Nome no topo: " + pilhaDeNomes.top().solicitante);

            // Desempilhando e mostrando todos os nomes (pop)
            System.out.println("\n--- DESEMPILHANDO OS NOMES ---");
            while (!pilhaDeNomes.isEmpty()) {

                Solicitacao solicitacao = pilhaDeNomes.pop();
            }
            

        }
        

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