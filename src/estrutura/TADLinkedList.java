package estrutura;
  
public interface MyLinkedList <T> {
    int size () ;
    boolean isEmpty () ;
    boolean addFirst ( T element ) ;
    boolean addLast ( T element ) ;
    boolean remove (T element) ;
}
