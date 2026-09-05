//class menu
    //metodo exibir, para mostrar o menu, chamado anteriormente pelo main
    //print com as opções de menu
    //condições para cada opção, ou seja, se apertar em solicitção, ele chama outro aqrquivo onde esta a solução, não esqueça de instanciar para uso dos metodos.


import java.util.Scanner;

public class Menu{
    public static void main(String[] args) {
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
            "0 - Encerrar\n"
            
        );
        
    }
}