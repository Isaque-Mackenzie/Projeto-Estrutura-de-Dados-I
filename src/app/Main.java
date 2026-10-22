public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        boolean fim = false;

        // Loop principal para manter o programa rodando até o usuário digitar 0
        while (fim==false) {
            fim=menu.exibir();
        }

    }
}

    
