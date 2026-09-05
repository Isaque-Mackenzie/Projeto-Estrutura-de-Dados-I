import java.util.Scanner;
//class menu
    //metodo exibir, para mostrar o menu, chamado anteriormente pelo main
    //print com as opções de menu
    //condições para cada opção, ou seja, se apertar em solicitção, ele chama outro aqrquivo onde esta a solução, não esqueça de instanciar para uso dos metodos.
public class Menu {
    Pilha<Solicitacao> pilha = new Pilha<>(7);
    public void exibir() {
        
        Scanner s = new Scanner(System.in);
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
        int opcao = s.nextInt();

    

        if(opcao==1){
            Solicitacao joao = new Solicitacao(1, "João", "Problema no sistema", "TI", 1, "AGUARDANDO");
            pilha.push(joao);
            Solicitacao maria = new Solicitacao(2, "Maria", "Problema com impressora", "TI", 2, "AGUARDANDO");
            pilha.push(maria);
            Solicitacao pedro = new Solicitacao(3, "Pedro", "Problema com computador", "TI", 3, "AGUARDANDO");
            pilha.push(pedro);
        
            System.out.println(pilha.getTopo());
            


            System.out.println("-------------------");

        }
        

    }
}


    
    


