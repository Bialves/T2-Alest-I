public class ListaDeRuas {
    class Node {
        public Rua element;
        public Node next;
        public Node prev;

        public Node(Rua element) {
            this.element = element;
            next = null;
        }
    }

    private Node header;
    private Node trailer;
    private Node current;
    private int count;

    public ListaDeRuas() {
        header = new Node(null);
        trailer = new Node(null);
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }

    public void orderedAdd(Rua e)  {
        if(contains(e.getNome()) == null) {  // Senão contém element, o insere na lista
            Node n = new Node(e);

            if(header.next == trailer) { // Se a lista está vazia, insere no inicio
                n.prev = header;
                n.next = trailer;
                trailer.prev = n;
                header.next = n;

            }else if(e.getNome().compareTo(header.next.element.getNome())<0) { // Se for menor que o primeiro, insere no inicio
                n.next = header.next;
                n.prev = header;
                header.next = n;
                n.next.prev = n;
            }else if(e.getNome().compareTo(trailer.prev.element.getNome())>0) { // Se for maior que o último, insere no final
                n.next = trailer;
                n.prev = trailer.prev;
                trailer.prev.next = n;
                trailer.prev = n;
            }else{ // Senão procura a posição correta para inserção
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
        }
    }
    
    public Rua contains(String nome) {
        Node aux = header.next;
        
        while(aux != trailer) {
            if(aux.element.getNome().equals(nome)) return aux.element;
            aux = aux.next;
        }
        return null;
    }    
    
    public Rua next() { // Método que permitir percorrer avançando na lista
        if(current != trailer) {
            Rua aux = current.element;
            current = current.next;
            return aux;
        }
        return null;
    }
    
    public Rua prev() { // Mesma lógica do next(), mas retrocede na lista
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

    public int size() {
        return count;
    }

    public Rua getCurrent() {
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
        int[] meses = new int[12]; // Arranjo onde cada índice é um mês
        Node aux = header.next;
        int mes = 0;

        for(int i=0; i<count; i++) {
            Rua rua = aux.element;
            int tam = rua.getListaSinalizacoes().size(); // Atualiza o número de iterações
            for(int j=0; j<tam; j++) {
                mes = rua.getListaSinalizacoes().getMes(j); // Descobre o mês da Sinalização
                if(mes != -1) { // Se a Sinalização possui data
                    meses[mes-1] += 1; // Incrementa 1 no índice do mês correspondente
                }
            }
            aux = aux.next;
        }
        // Descobre o mês com mais Sinalizações (confere o índice do arranjo)
        int maior = 0;
        for(int k=0; k<meses.length; k++) {
            if(meses[k] > maior) {
                maior = meses[k];
                mes = k+1;
            }
        }
        return mes;
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
