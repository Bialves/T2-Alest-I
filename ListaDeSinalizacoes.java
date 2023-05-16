import java.time.LocalDate;

public class ListaDeSinalizacoes {
    private Node head;
    private Node tail;
    private Node current;
    private int count;

    class Node {
        public Sinalizacao element;
        public Node next;

        public Node(Sinalizacao e) {
            this.element = e;
            next = null;
        }
    }
    
    public ListaDeSinalizacoes() {
        head = null;
        tail = null;
        count = 0;
    }

    public boolean add(Sinalizacao e) {
        if(contains(e) == null) {
            Node n = new Node(e);

            if(head == null) { //se é o 1° elemento da lista
                head = n;
            }else{ //senão, adicionado após o último elemento
                tail.next = n;
            }
            tail = n; //atualiza tail
            count++; //atualiza o contador
            return true;
        }
        return false;
    }

    public int size() {
        return count;
    }

    public Sinalizacao next(){
        Sinalizacao aux = null;

        if(current != null) {
            aux = current.element;
            current = current.next;

            return aux;
        }
        return null;
    }

    public void reset() {
        current = head;
    }

    public Sinalizacao contains(Sinalizacao e) {
        Node aux = head;
        
        for(int i=0; i<count; i++) {
            if(aux.element.equals(e)){
                return aux.element;
            }
            aux = aux.next;
        }
        return null;
    }    

    public int getMes(int index) {
        LocalDate date = null;

        if(index < 0 || index >= count) {
            throw new IndexOutOfBoundsException(); //exceção
        }else if(index == count-1) { //se é o último elemento da lista
            date = tail.element.getImplantacao(); //condição para evitar NullPointerException de sinalizações sem data de implementação
            if(date == null) {
                return -1;
            }else{
                return date.getMonthValue(); 
            }
        }else{
            Node aux = head;
            for(int i=0; i<index; i++) {
                aux = aux.next;
            }
            date = aux.element.getImplantacao();
            if(date == null) { 
                return -1;
            }else{
                return date.getMonthValue();
            }
        }  
    }

    public LocalDate getDataImplantacao(int index) {
        if(index < 0 || index >= count) {
            throw new IndexOutOfBoundsException(); //exceção
        }else if(index == count-1) {
            return tail.element.getImplantacao(); //se é o último elemento da lista
        }else{
            Node aux = head;
            for(int i=0; i<index; i++) {
                aux = aux.next;
            }
            return aux.element.getImplantacao();
        }
    }

    public Sinalizacao getMaiorData() {
        if(count == 0) {
            throw new NullPointerException(); //exceção
        }else if(count == 1) {
            return head.element; //se já apenas 1 elemento na lista
        }else{
            Node aux = head;
            Sinalizacao sinalizacao = aux.element;
            LocalDate maior = aux.element.getImplantacao();

            for(int i=0; i<count; i++) {
                if(aux.element.getImplantacao().isAfter(maior)) {
                    maior = aux.element.getImplantacao();
                    sinalizacao = aux.element;
                }
                aux = aux.next;
            } 
            return sinalizacao;
        }
    }

    public Sinalizacao getMenorData() {
        if(count == 0) {
            throw new NullPointerException(); //exceção
        }else if(count == 1) {
            return head.element; //se já apenas 1 elemento na lista
        }else{
            Node aux = head;
            Sinalizacao sinalizacao = aux.element;
            LocalDate menor = aux.element.getImplantacao();

            for(int i=0; i<count; i++) {
                if(aux.element.getImplantacao().isBefore(menor)) {
                    menor = aux.element.getImplantacao();
                    sinalizacao = aux.element;
                }
                aux = aux.next;
            } 
            return sinalizacao;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node aux = head;
        for(int i = 0; i < count; i++) {
            s.append(aux.element);
            s.append("\n");
            aux = aux.next;
        }
        return s.toString();
    }    
}
