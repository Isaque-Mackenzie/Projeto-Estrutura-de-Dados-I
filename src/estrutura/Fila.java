package estrutura;



// Classe Fila<T>: relativa a fila dinâmica e encadeada.
// Implementada como uma lista encadeada com nós do tipo classe Node<T>
// Fila Genérica, de qualquer tipo de dado não primitivo.
// Autor1: Ivan Carlos Alcântara de Oliveira.
// Data da Criação: 10/10/2026. 15h.
public class Fila<T> implements TADFila<T> {
    private Node<T> inicio;
    private Node<T> fim;
    private int qtde;

    // Construtor 
    public Fila( ) {
        this.inicio = null;
        this.fim = null;
        this.qtde = 0;
    }

    // Verifica se a fila esta vazia.
    public boolean qIsEmpty() {
        return qtde == 0;
    }

    // Verifica se a lista está cheia.
    // Como a lista utiliza alocação dinâmica de memória,
    // não possui capacidade máxima predefinida.
    // Caso não haja memória suficiente, 
    // ocorre o erro em Java (OutOfMemoryError).
    // @return false, pois a lista não possui limite fixo.
    public boolean qIsFull() {
       return false;
    }

    // Insere um elemento no final da fila.
    public void enqueue(T e) throws Exception {
        Node<T> novo = new Node<T>(e, null);
        if (qIsEmpty()) {
            inicio = novo;
        } else {
            fim.setProx(novo);
        }
        fim = novo;
        qtde++;
    }

    // Remove e retorna o elemento do inicio da fila.
    public T dequeue() throws Exception {
        if (qIsEmpty()) {
            throw new Exception("underflow - Esvaziamento de Fila");
        }
        T elemento = inicio.getDado();
        inicio = inicio.getProx();
        qtde--;
        if (qIsEmpty()) {
            fim = null;
        }
        return elemento;
    }

    
//Coloue isse removeMeio para remover a solicitação da fila, quando removemos solicitação com o case 13
    public boolean removerMeio(T e) throws Exception {

        Node<T> anterior = null;
        Node<T> atual = inicio;

        // procura o nó que guarda o elemento, lembrando de quem vem antes dele
        while (atual != null && !atual.getDado().equals(e)) { //quando achar ele para o loop, e o atual sera aquele que devemos remover
            anterior = atual;
            atual = atual.getProx();
        }
        if (atual == null) {                   //se o atual for null signifia que sairam do loop justamente porque atual chegou a null e não achou
            return false;                    // não achou
        }


        if (anterior == null) {                //se o anterior do atual(aquele que devemos remover) for null
            dequeue();                       // era o primeiro da fila, e temos o dequeue
            return true;   // era o primeiro da fila, então apenas amarramos 
        
        }



        if (atual == fim) {
            fim = anterior;                  //se o atual for o fim (fim é atributo dessa classe, então)
        }

        
        qtde--;
        return true;
    }

    
    // Retorna o primeiro elemento, sem remover.
    public T front() throws Exception {
        if (qIsEmpty()) {
            throw new Exception("underflow - Esvaziamento de Fila");
        }
        return inicio.getDado();
    }

    // Retorna o ultimo elemento, sem remover.
    public T rear() throws Exception {
        if (qIsEmpty()) {
            throw new Exception("underflow - Esvaziamento de Fila");
        }
        return fim.getDado();
    }

    // Retorna o total de elementos armazenados.
    public int size() {
        return qtde;
    }

    // Representacao textual do inicio para o fim.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Fila] quantidade: ").append(qtde);
        if (!qIsEmpty()) {
            sb.append(", primeiro: ").append(inicio.getDado())
              .append(", último: ").append(fim.getDado());
        }
        sb.append("\nConteudo da Fila': [ ");
        Node<T> atual = inicio;
        while (atual != null) {
            sb.append("[").append(atual.getDado()).append("]");
            atual = atual.getProx();
        }
        sb.append(" ]");
        return sb.toString();
    }
}
