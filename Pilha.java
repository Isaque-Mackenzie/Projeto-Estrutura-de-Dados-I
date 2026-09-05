public class Pilha <T>{
    private static final int TAM_DEFAULT = 100;
    private int topo;
    private Object[] elementos;

    public Pilha(int tamanho) {
        elementos = new Object[tamanho];
        topo = -1;
        
    }

    // Insere no topo
    public void push(T item) {
        if (topo == elementos.length - 1) {
            System.out.println("Pilha cheia! Nao e possivel inserir: " + item);
            return;
        }
        topo++;
        elementos[topo] = item;
    }

    
    

    // Retorna o elemento do topo sem remover
    @SuppressWarnings("unchecked")
    public T getTopo() {
        if (topo == -1) {
            System.out.println("Pilha vazia!");
            return null;
        }
        return (T) elementos[topo]; // Faz a conversao de volta para o tipo T
    }
    
}


    
    
    