import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Aplicacao {
    private final Scanner scan;
    private final ListaDeRuas listaRuas;
    private boolean executa;
    private Rua current;

    public Aplicacao() {
        scan = new Scanner(System.in);
        listaRuas = new ListaDeRuas(); 
        this.executa = true;
    }
    // FUNÇÃO PRINCIPAL
    public void run() {
        listaRuas.reset();
        current = listaRuas.getCurrent();
        
        do {
            menu();

            switch(scan.nextInt()) {
                case 1:  
                    navegacao();
                    break;
                case 2: 
                    Month mes = Month.of(listaRuas.getMesComMaisSinalizacoes());
                    System.out.println(">>> Mês com mais sinalizações implementadas: " + mes.getValue() + " - " + mes.name());
                    break;
                case 3: 
                    Rua rua = listaRuas.getRuaComMaisSinalizacoes();
                    System.out.println(">>> Rua com mais sinalizações: " + rua.toString());
                    System.out.println(">> Quantidade de sinalizações: " + rua.getListaSinalizacoes().size());
                    break;
                case 4:
                    System.out.println(">>> Saindo...");
                    executa = false;
                    break;
                default:
                    System.out.println("ERRO: Opção inválida!");
            }

        }while(executa);
    }

    // MENU DE OPÇÕES
    public void menu() {
        System.out.println("============== MENU ===============");
        System.out.println("1. Modo de navegação");
        System.out.println("2. Mês com mais sinalizações implementadas");
        System.out.println("3. Rua com mais sinalizações registradas");
        System.out.println("4. Sair");
        System.out.print("> ");
    }

    // MODOS DE NAVEGAÇÃO
    public void navegacao() {
        Rua aux;

        do{
            System.out.println();
            ruaAtual(); 
            System.out.println("============ MENU DE NAVEGAÇÃO ============");
            System.out.println("1. Retroceder");
            System.out.println("2. Avançar");
            System.out.println("3. Retornar ao Menu");
            System.out.print("> ");
            int opcao = scan.nextInt();
            if(opcao == 3) {
                break;
            }else if(opcao == 1) {
                aux = listaRuas.prev();
                if(aux == null) { // Se aux cair em NullPointerExecption (header)
                    System.out.println("ERRO: impossível retroceder!");
                    listaRuas.reset(); // Reseta current
                    aux = listaRuas.getCurrent(); // aux recebe current
                }else{
                    if(listaRuas.getCurrent() == null) { // Se current cair em NullPointerException (header)
                        System.out.println("ERRO: impossível retroceder!");
                        listaRuas.reset(); // Reseta current
                        current = listaRuas.getCurrent(); // Variável current recebe o current da lista
                        aux = current; // aux recebe current
                    }else{
                        current = listaRuas.getCurrent();
                    }
                }
            }else if(opcao == 2) {
                aux = listaRuas.next();
                if(aux == null) { // Se aux cair em NullPointerExecption (trailer)
                    System.out.println("ERRO: impossível avançar!");
                    listaRuas.prev();  // current retorna na lista
                    aux = listaRuas.getCurrent(); // aux recebe current
                }else{
                    if(listaRuas.getCurrent() == null) { // Se current cair em NullPointerException (trailer)
                        System.out.println("ERRO: impossível avançar!");
                        listaRuas.prev(); // current retorna na lista
                        current = listaRuas.getCurrent();  // Variável current recebe o current da lista
                        aux = current;
                    }else{
                        current = listaRuas.getCurrent();
                    }
                }
            }else{
                System.out.println("ERRO: opção inválida!");
            }
        }while(true);
    }
    // IMPRIME INFORMAÇÕES DA RUA ATUAL DO MENU DE NAVEGAÇÃO
    public void ruaAtual() {
        ListaDeSinalizacoes listaSinalizacoes = current.getListaSinalizacoes();

        int tam = listaSinalizacoes.size();
        System.out.println(">>> Rua atual: " + current.toString());
        System.out.println(">> Quantidade de sinalizações: " + tam);
        System.out.println(">> Sinalizações da rua:");
        System.out.println(listaSinalizacoes.toString());
        System.out.println("-------------------------------------------------------------------------->>>");
        int menor = listaSinalizacoes.getMenorData().size();
        int maior = listaSinalizacoes.getMaiorData().size();
        if(tam == 1) { // Se a Rua possui apenas uma Sinalização
            System.out.println("Primeira e última sinalização implementada: " 
                + "\n" + listaSinalizacoes.getMenorData().toString() + "\n");
        }else{ // Se possui mais de uma Sinalização
            if(menor == 1) { // Se existe mais de uma Sinalização com a mesma data
                if(maior == 1) {
                    System.out.println("Primeira sinalização implementada: " 
                        + "\n" + listaSinalizacoes.getMenorData().toString());
                    System.out.println("Última sinalização implementada: " 
                        + "\n" + listaSinalizacoes.getMaiorData().toString() + "\n");
                }else{
                    System.out.println("Primeira sinalização implementada: " 
                        + "\n" + listaSinalizacoes.getMenorData().toString());
                    System.out.println("Últimas sinalizações implementadas: " 
                        + "\n" + listaSinalizacoes.getMaiorData().toString() + "\n");
                }
            }else{
                if(maior == 1) {
                    System.out.println("Primeiras sinalizações implementadas: " 
                        + "\n" + listaSinalizacoes.getMenorData().toString());
                    System.out.println("Última sinalização implementada: " 
                        + "\n" + listaSinalizacoes.getMaiorData().toString() + "\n");
                }else{
                    System.out.println("Primeiras sinalizações implementadas: " 
                        + "\n" + listaSinalizacoes.getMenorData().toString());
                    System.out.println("Últimas sinalizações implementadas: " 
                        + "\n" + listaSinalizacoes.getMaiorData().toString() + "\n");
                }
            }
        }
    }
    // LEITURA DO ARQUIVO CSV
    public void leituraCSV() {
        String[] linhas = new String[91708];
        int numLinhas = 0;
        
        try(BufferedReader reader = new BufferedReader(new FileReader("dataEditado.csv", StandardCharsets.UTF_8))) {
            String line = reader.readLine(); // Pula o header da planilha
            line = reader.readLine();

            while(line != null) {
                linhas[numLinhas] = line; // Armezena as infos de uma linha inteira
                numLinhas++;
                line = reader.readLine();
            }
        }catch(FileNotFoundException e) {
            System.err.format("CSV não encontrado: " + e.getMessage());
        }catch(IOException e) {
            System.err.format(e.getMessage());
        }

        for(int i = 0; i < numLinhas; i++) {
            String[] campos = linhas[i].split(";"); // Quebra as linhas em palavras (campos)

            String descricao = campos[1];
            String estado = campos[2];
            String complemento = campos[3];

            DateTimeFormatter formatter;
            LocalDate date = null;
            if(!campos[4].equals("")) {
                if(campos[4].contains("-")) {
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }else{
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                } 

                date = LocalDate.parse(campos[4], formatter);
            }
            

            String logradouro = campos[5].split(" ", 2)[0];
            String nomeLog = campos[5].split(" ", 2)[1];

            double numInicial;
            if(campos[6].equals("")) numInicial = 0;
            else numInicial = Double.parseDouble(campos[6]);
            
            double numFinal;
            if(campos[7].equals("")) numFinal = 0;
            else numFinal = Double.parseDouble(campos[7]);

            String defronte = campos[8];
            String cruzamento = campos[9];
            String lado = campos[10];

            String fluxo = "";
            if(campos.length>=12) fluxo = campos[11];

            String localInstalacao = "";
            if(campos.length>=13) localInstalacao = campos[12];

            // INSTANCIA DOS OBJETOS
            ListaDeSinalizacoes listaSinalizacoes = new ListaDeSinalizacoes(); // Reseta a lista de sinalizações

            Rua rua = listaRuas.contains(nomeLog); // Verifica se a Rua já não existe

            if(rua == null) { // Senão, instancia a mesma
                rua = new Rua(logradouro, nomeLog, listaSinalizacoes);
                // Verifica se a Sinalização já não existe
                Sinalizacao sinalizacao = listaSinalizacoes.contains(
                    new Sinalizacao(
                        numInicial, numFinal, localInstalacao, date, cruzamento, lado, 
                        descricao, fluxo, defronte, complemento, estado
                    )
                );

                if(sinalizacao == null) { // Senão, instancia ela e adiciona na lista da Rua
                    sinalizacao = new Sinalizacao(
                        numInicial, numFinal, localInstalacao, date, cruzamento, 
                        lado, descricao, fluxo, defronte, complemento, estado
                    );

                    rua.getListaSinalizacoes().add(sinalizacao);
                }
                listaRuas.orderedAdd(rua); // Finaliza adicionando a Rua na lista
            }else{ // Se a Rua já existe, verifica se a Sinalização já existe na lista de sinalizações da Rua
                Sinalizacao sinalizacao = rua.getListaSinalizacoes().contains(
                    new Sinalizacao(
                        numInicial, numFinal, localInstalacao, date, cruzamento, lado, 
                        descricao, fluxo, defronte, complemento, estado
                    )
                );

                if(sinalizacao == null) { // Senão, instancia a Sinalização e adiciona na lista de sinalizações da Rua
                    sinalizacao = new Sinalizacao(
                        numInicial, numFinal, localInstalacao, date, cruzamento, lado, 
                        descricao, fluxo, defronte, complemento, estado
                    );

                    rua.getListaSinalizacoes().add(sinalizacao);
                }
            }
        }
    }
}


