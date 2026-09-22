public interface Pilha<E> {
    void push(E element);   // push (e) insere e no topo
    E pop();                // pop () remove e retorna o topo
    E top();                // top () consulta o topo sem remover
    boolean isEmpty();      // isEmpty () verifica se esta vazia
    int size();             // size () retorna a quantidade de elementos
}

class PilhaComArray<E> implements Pilha<E> {
    private E[] elements;
    private int topIndex;
    private static final int DEFAULT_CAPACITY = 100;

    @SuppressWarnings("unchecked")
    public PilhaComArray() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        topIndex = -1;
    }

    @Override
    public void push(E element) {
        if ( topIndex == elements . length - 1) {
            throw new IllegalStateException (" Pilha cheia ") ;
        }
        topIndex++;
        elements[topIndex] = element;
    }

    @Override
    public E pop() {
        if ( isEmpty () ) {
            throw new IllegalStateException (" Pilha vazia ") ;
        }
        E element = elements[topIndex];
        elements[topIndex] = null;
        topIndex--;
        return element;
    }

    @Override
    public E top() {
        if ( isEmpty () ) throw new IllegalStateException (" Pilha vazia ");
        return elements[topIndex];
    }

    @Override
    public boolean isEmpty() {
        return topIndex == -1;
    }

    @Override
    public int size() {
        return topIndex + 1;
    }
}
    
    
    
    
    
