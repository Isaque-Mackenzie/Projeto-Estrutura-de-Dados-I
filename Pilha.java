public interface Pilha<E> {
    void push(E element);
    E pop();
    E top();
    boolean isEmpty();
    int size();
}

class PilhaComArray<E> implements Pilha<E> {
    private E[] elements;
    private int topIndex;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public PilhaComArray() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        topIndex = -1;
    }

    @Override
    public void push(E element) {
        topIndex++;
        elements[topIndex] = element;
    }

    @Override
    public E pop() {
        E element = elements[topIndex];
        elements[topIndex] = null;
        topIndex--;
        return element;
    }

    @Override
    public E top() {
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

    
    
    