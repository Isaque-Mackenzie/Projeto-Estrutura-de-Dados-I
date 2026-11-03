package estrutura;
// Classe NodeDLL<T>: Node de uma lista duplamente encadeada
// genérica
// Autor1: Ivan Carlos Alcântara de Oliveira.
// Data da Criação: 10/10/2026. 15h.
public class NoDuplo<T extends Comparable<T>>{
	private T element;
	private	NoDuplo<T> prev, next;
	
	public	NoDuplo() {
		this(null, null, null);
	}
	
	public NoDuplo(T element, NoDuplo<T> prev, NoDuplo<T> next){
			this.element = element;
			this.prev = prev;
			this.next = next;
	};

	public T getElement() { return element;};
	public NoDuplo<T> getPrev(){ return prev;};
	public NoDuplo<T> getNext(){ return next;};
	public void setElement(T element) { this.element = element;};
	public void setPrev(NoDuplo<T> prev){ this.prev = prev; };
	public void setNext(NoDuplo<T> next){ this.next = next; };
}
