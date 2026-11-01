package estrutura;


public interface TADPilha<T> {
    //adicionei esse throws Exception na interface
    void push(T e) throws Exception ;        // push (e) insere e no topo
    T pop() throws Exception;                // pop () remove e retorna o topo
    T top() throws Exception;                // top () consulta o topo sem remover
    boolean isEmpty() throws Exception;      // isEmpty () verifica se esta vazia
    int sizeElements() throws Exception ;    // size () retorna a quantidade de elementos
}
