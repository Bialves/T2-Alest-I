import java.time.LocalDate;

public class ListaDeSinalizacoes {
    class Node {
        public Sinalizacao element;
        public Node next;

        public Node(Sinalizacao e) {
            this.element = e;
            next = null;
        }
    }

    private Node head;
    private Node tail;
    private Node current;
    private int count;
    
    public ListaDeSinalizacoes() {
        head = null;
        tail = null;
        count = 0;
    }

    public void add(Sinalizacao e) {
        if(contains(e) == null) {
            Node n = new Node(e);

            if(head == null) { // Se é o primeiro elemento da lista
                head = n;
            }else{ // Senão, adicionado após o último elemento
                tail.next = n;
            }
            tail = n; 
            count++;
        }
    }

    public int size() {
        return count;
    }

    public Sinalizacao next(){
        Sinalizacao aux;

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
        LocalDate date;

        if(index < 0 || index >= count) {
            throw new IndexOutOfBoundsException(); // Exceção
        }else if(index == count-1) { // Se é o último elemento da lista
            date = tail.element.getImplantacao(); // Para evitar NullPointerException de sinalizações sem data de implementação
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
            throw new IndexOutOfBoundsException(); // Exceção
        }else if(index == count-1) {
            return tail.element.getImplantacao(); // Se é o último elemento da lista
        }else{
            Node aux = head;
            for(int i=0; i<index; i++) {
                aux = aux.next;
            }
            return aux.element.getImplantacao();
        }
    }

    public ListaDeSinalizacoes getMaiorData() {
        ListaDeSinalizacoes lista = new ListaDeSinalizacoes();

        if(count == 0) {
            throw new NullPointerException(); // Exceção
        }else if(count == 1) {
            lista.add(head.element);
            return lista; // Apenas 1 elemento na lista
        }else{
            Node aux = head;
            LocalDate maior = aux.element.getImplantacao();

            for(int i=0; i<count; i++) {
                if(aux.element.getImplantacao().isAfter(maior)) { // Descobre a maior data
                    maior = aux.element.getImplantacao(); 
                    lista = new ListaDeSinalizacoes(); // Reseta a lista
                    lista.add(aux.element); // Adiciona a data maior na lista
                }else if(aux.element.getImplantacao().equals(maior)) {
                    lista.add(aux.element); // Se é igual a maior data existente, adiciona na lista
                }
                aux = aux.next;
            } 
            return lista;
        }
    }

    public ListaDeSinalizacoes getMenorData() {
        ListaDeSinalizacoes lista = new ListaDeSinalizacoes();

        if(count == 0) {
            throw new NullPointerException(); // Exceção
        }else if(count == 1) {
            lista.add(head.element);
            return lista; // Apenas 1 elemento na lista
        }else{
            Node aux = head;
            LocalDate menor = aux.element.getImplantacao();

            for(int i=0; i<count; i++) {
                if(aux.element.getImplantacao().isBefore(menor)) { // Descobre a menor data
                    menor = aux.element.getImplantacao();
                    lista = new ListaDeSinalizacoes(); // Reseta a lista
                    lista.add(aux.element); // Adiciona a data menor na lista
                }else if(aux.element.getImplantacao().equals(menor)) {
                    lista.add(aux.element); // Se é igual a menor data existente, adiciona na lista
                }
                aux = aux.next;
            } 
            return lista;
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
