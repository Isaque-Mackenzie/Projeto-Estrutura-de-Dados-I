import java.util.Scanner;
//class menu
    //metodo exibir, para mostrar o menu, chamado anteriormente pelo main
    //print com as opções de menu
    //condições para cada opção, ou seja, se apertar em solicitção, ele chama outro aqrquivo onde esta a solução, não esqueça de instanciar para uso dos metodos.
public class Menu {
    public void exibir() {
        System.out.println("Menu:/n1. Solicitação/n2. Opção 2/n3. Opção 3");      
        Scanner scanner = new Scanner(System.in);
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
    }
}



//teste pull

// teste isaquinho