package estrutura;

public interface TADFila<T> {
    void enqueue(T elemento) throws Exception;  // Método pra adicionar elemento no fim
    T dequeue() throws Exception ;              // Método pra remover elemento do início
    T rear() throws Exception;                  // Método pra ver qual é o elemento do fim
    T front() throws Exception ;                // Método pra ver qual é o elemento do inicio
    int size() throws Exception;                // Método pra ver qual o tamanho da fila
    boolean qIsEmpty() throws Exception ;       // Método pra ver se a fila tá vazia
    boolean qIsFull() throws Exception;         // Método pra ver se a fila tá cheia
}