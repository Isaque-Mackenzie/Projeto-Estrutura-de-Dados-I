
// A interface lista os métodos que a classe de Fila precisa ter
interface TADFila<T> {
    void enqueue(T elemento); // Método pra adicionar elemento no fim
    T dequeue(); // Método pra remover elemento do início
    T rear(); // Método pra ver qual é o elemento do fim
    T front(); // Método pra ver qual é o elemento do inicio
    int size(); // Método pra ver qual o tamanho da fila
    boolean qIsEmpty(); // Método pra ver se a fila tá vazia
    boolean qIsFull(); // Método pra ver se a fila tá cheia
}

public class Fila<T> implements TADFila<T> {
    private T[] dados;
    private int inicio;
    private int fim;
    private int qtde;

    @SuppressWarnings("unchecked")
    public Fila(int capacidade) {
        this.dados = (T[]) new Object[capacidade];
        this.inicio = 0;
        this.fim = -1;
        this.qtde = 0;
    }

    public boolean qIsEmpty() {
        return this.qtde == 0;
    }

    public boolean qIsFull() {
        return this.qtde == this.dados.length;
    }
    
    public int size() {
        return this.qtde;
    }

    public void enqueue(T elemento) {
        if (qIsFull()) {
            throw new IllegalStateException("Fila cheia! Não é possível enfileirar.");
        }
        this.fim = (this.fim + 1) % this.dados.length;
        this.dados[this.fim] = elemento;
        this.qtde++;
    }

    public T dequeue() {
        if (qIsEmpty()) {
            throw new IllegalStateException("Fila vazia! Não é possível desenfileirar.");
        }
        T elementoRemovido = this.dados[this.inicio];
        this.dados[this.inicio] = null; 
        
        this.inicio = (this.inicio + 1) % this.dados.length;
        this.qtde--;
        
        return elementoRemovido;
    }

    public T front() {
        if (qIsEmpty()) {
            throw new IllegalStateException("A fila está vazia!");
        }
        return this.dados[this.inicio];
    }

    public T rear() {
        if (qIsEmpty()) {
            throw new IllegalStateException("A fila está vazia!");
        }
        return this.dados[this.fim];
    }
}

// Comportamento FIFO
// enqueue(e): insere no final
// dequeue(): remove e retorna o início
// front(): consulta o início (sem remover)
// isEmpty(): verifica se está vazia
// size(): retorna a quantidade
