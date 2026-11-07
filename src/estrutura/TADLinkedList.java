package estrutura;
  
public interface TADLinkedList <T> {
    int getSize () ;  //esse get size, rever esse nome aqui
    boolean isEmpty () ;
    boolean addFirst ( T element ) ;
    boolean addLast ( T element ) ;
    boolean remove (T element) ;
}

