import java.time.LocalDate;

public class Sinalizacao {
    private String descricao;
    private double numInicial;
    private double numFinal;
    private String localInstalacao;
    private LocalDate implantacao;
    private String cruzamento;
    private String lado;
    private String fluxo;
    private String defronte;
    private String complemento;
    private String estado;

    public Sinalizacao(double in, double fin, String insta, LocalDate im, String c, String l, String d, String f, String def, String comp, String e) {
        this.numInicial = in;
        this.numFinal = fin;
        this.localInstalacao = insta;
        this.implantacao = im;
        this.cruzamento = c;
        this.lado = l;
        this.descricao = d;
        this.fluxo = f;
        this.defronte = def;
        this.complemento = comp;
        this.estado = e;
    }

    public double getNumInicial() {
        return numInicial;
    }

    public double getNumFinal() {
        return numFinal;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLocalInstalacao() {
        return localInstalacao;
    }

    public String getCruzamento() {
        return cruzamento;
    }

    public String getLado() {
        return lado;
    }

    public LocalDate getImplantacao() {
        return implantacao;
    }

    public String getEstado() {
        return estado;
    }

    public String getComplemento() {
        return complemento;
    }
    public String getFluxo() {
        return fluxo;
    }
    public String getDefronte() {
        return defronte;
    }

    @Override
    public String toString() {
        return "> Local Instalado: " + localInstalacao + "\n "
            + "- Números: " + numInicial + " a " + numFinal + " | Data de implantação: " 
            + implantacao.getDayOfMonth() + "/" + implantacao.getMonthValue() + "/" + implantacao.getYear() 
            + " | Cruzamento: " + cruzamento + " | Lado: " + lado + "\n" + " - Descrição: " + descricao 
            + " | Estado: " + estado + " | Defronte: " + defronte + " | Fluxo: " + fluxo + " | Complemento: " + complemento;
    }
}