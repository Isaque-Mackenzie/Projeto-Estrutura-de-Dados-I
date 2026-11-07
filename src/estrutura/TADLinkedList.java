package estrutura;
  
public interface TADLinkedList <T> {
    int size () ;
    boolean isEmpty () ;
    boolean addFirst ( T element ) ;
    boolean addLast ( T element ) ;
    boolean remove (T element) ;
}
