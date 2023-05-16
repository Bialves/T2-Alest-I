public class Rua {
    private String logradouro;
    private String nome;
    private ListaDeSinalizacoes lista;

    public Rua(String log, String nome, ListaDeSinalizacoes lista) {
        this.logradouro = log;
        this.nome = nome;
        this.lista = lista;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNome() {
        return nome;
    }

    public ListaDeSinalizacoes getListaSinalizacoes() {
        return lista;
    }

    @Override
    public String toString() {
        return logradouro + " " + nome;
    }
}
