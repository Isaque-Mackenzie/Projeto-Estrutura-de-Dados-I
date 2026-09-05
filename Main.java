//classe com nome do arquivo, não esqueça de colocar em maiusculo tanto o nome do arquivo quanto o nome da classe
    //metodo main, para o java saber por onde começar
    //Instanciar o objeto menu, para poder chamar o metodo exibir (pois o o metodo exibir esta em outra classe, então precisa do objeto)
    //usar o metodo para exibir que esta no outro arquivo

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibir();
    }
}

    