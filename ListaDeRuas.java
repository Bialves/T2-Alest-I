public class ListaDeRuas {
    private Node header;
    private Node trailer;
    private Node current;      
    private int count;

    private class Node {
        public Rua element;
        public Node next;
        public Node prev;

        public Node(Rua element) {
            this.element = element;
            next = null;
        }
    }

    public ListaDeRuas() {
        header = new Node(null);
        trailer = new Node(null);
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }
    
    public int size() {
        return count;
    }

    public boolean orderedAdd(Rua e)  { 
        if(contains(e.getNome()) == null) {  //senão contám element, o insere
            Node n = new Node(e);

            if(header.next == trailer) { //se a lista está vazia
                n.prev = header;
                n.next = trailer;
                trailer.prev = n;
                header.next = n;

            } 
            else if(e.getNome().compareTo(header.next.element.getNome())<0) { //se for menor que o primeiro, insere no inicio
                n.next = header.next;
                n.prev = header;
                header.next = n;
                n.next.prev = n;
            }
            else if(e.getNome().compareTo(trailer.prev.element.getNome())>0) { //se for maior que o último, insere no final
                n.next = trailer;
                n.prev = trailer.prev;
                trailer.prev.next = n;
                trailer.prev = n;
            }
            else { //senão procura a posição correta para inserção
                Node aux = header.next;
                boolean inseriu=false;
                while(aux!=trailer && !inseriu) {
                    if(e.getNome().compareTo(aux.element.getNome())<0) {
                        inseriu = true;
                        n.next = aux;
                        n.prev=aux.prev;
                        aux.prev.next = n;
                        aux.prev = n;
                    }
                    aux = aux.next;
                }
            }
            count++;
            return true;
        }
        return false;
    }
    
    public Rua contains(String nome) {
        Node aux = header.next;
        
        while(aux != trailer) {
            if(aux.element.getNome().equals(nome)) return aux.element;
            aux = aux.next;
        }
        return null;
    }    
    
    public Rua next() {
        if(current != trailer) {
            Rua aux = current.element;
            current = current.next;
            return aux;
        }
        return null;
    }
    
    public Rua prev() {
        if(current != header) {
            Rua aux = current.element;
            current = current.prev;
            return aux;
        }
        return null;
    }

    public void reset() {
        current = header.next;
    } 

    public Rua current() {
        return current.element;
    }

    public Rua getRuaComMaisSinalizacoes() {
        if(count == 0) {
            throw new NullPointerException();
        }else if(count == 1) {
            return header.next.element;
        }else{
            Rua auxRua = null;
            Node aux = header.next;
            int maior = 0;

            for(int i=0; i<count; i++) {
                int num = aux.element.getListaSinalizacoes().size();
                if(num > maior) {
                    maior = num;
                    auxRua = aux.element;
                }
                aux = aux.next;
            }
            return auxRua;
        }
    }

    public int getMesComMaisSinalizacoes() {
        int[] meses = new int[12];
        Node aux = header.next;

        for(int i=0; i<count; i++) {
            Rua rua = aux.element;
            int tam = rua.getListaSinalizacoes().size();
            for(int j=0; j<tam; j++) {
                int mes = rua.getListaSinalizacoes().getMes(j);
                if(mes != -1) {
                    meses[mes-1] = meses[mes-1] + 1;
                }
            }
            aux = aux.next;
        }

        int maior = 0;
        int r = 0;
        for(int k=0; k<meses.length; k++) {
            if(meses[k] > maior) {
                maior = meses[k];
                r = k+1;
            }
        }
        return r;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node aux = header.next;
        for(int i = 0; i < count; i++) {
            s.append(aux.element.getLogradouro() + " " + aux.element.getNome());
            s.append("\n");
            aux = aux.next;
        }
        return s.toString();
    }    
}
