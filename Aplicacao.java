import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Aplicacao {
    private Scanner scan;
    private ListaDeRuas listaRuas;
    private boolean executa;
    Rua current;

    public Aplicacao() {
        scan = new Scanner(System.in);
        listaRuas = new ListaDeRuas(); 
        this.executa = true;
    }

    public void run() {
        listaRuas.reset();
        current = listaRuas.current();
        
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
                    System.out.println("Quantidade de sinalizações: " + rua.getListaSinalizacoes().size());
                    break;
                case 4:
                    System.out.println(">>> Saindo...");
                    executa = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        }while(executa);
    }

    //MENU DE OPÇÕES
    public void menu() {
        System.out.println("============== MENU ===============");
        System.out.println("1. Modo de navegação");
        System.out.println("2. Mês com mais sinalizações implementadas");
        System.out.println("3. Rua com mais sinalizações registradas");
        System.out.println("4. Sair");
        System.out.print("> ");
    }

    //MODOS DE NAVEGAÇÃO
    public void navegacao() {
        Rua aux = null;

        do{
            System.out.println();
            ruaAtual(); //header.next
            System.out.println("============ MENU DE NAVEGAÇÃO ============");
            System.out.println("1. Retroceder");
            System.out.println("2. Avançar");
            System.out.println("3. Retornar ao Menu inicial");
            System.out.print("> ");
            int opcao = scan.nextInt();
            if(opcao == 3) {
                break;
            }else if(opcao == 1) {
                aux = listaRuas.prev();
                if(aux == null) {
                    System.out.println("ERRO: impossível retroceder!");
                    listaRuas.reset(); //se cair em null (header)
                    aux = listaRuas.current();
                }else{
                    if(listaRuas.current() == null) {
                        System.out.println("ERRO: impossível retroceder!");
                        listaRuas.reset();
                        current = listaRuas.current();
                        aux = current;
                    }else{
                        current = listaRuas.current();
                    }
                }
            }else if(opcao == 2) {
                aux = listaRuas.next();
                if(aux == null) {
                    System.out.println("ERRO: impossível avançar!");
                    listaRuas.prev(); //se cair em null (trailer)
                    aux = listaRuas.current(); 
                }else{
                    if(listaRuas.current() == null) {
                        System.out.println("ERRO: impossível avançar!");
                        listaRuas.prev(); //se cair em null (trailer)
                        current = listaRuas.current(); 
                        aux = current;
                    }else{
                        current = listaRuas.current();
                    }
                }
            }else{
                System.out.println("ERRO: opção inválida!");
            }
        }while(true);
    }
    //IMPRIME INFORMAÇÕES DA RUA ATUAL DO MENU DE NAVEGAÇÃO
    public void ruaAtual() {
        System.out.println(">>> Rua atual: " + current.toString());
        System.out.println(">> Sinalizações da rua:");
        System.out.println(current.getListaSinalizacoes().toString());
        System.out.println("-----------------------------------------------------------------------------");
        int tam = current.getListaSinalizacoes().size();
        System.out.println("Quantidade de sinalizações: " + tam);
        if(tam == 1) {
            System.out.println("Primeira e última sinalização implementada: " 
                + "\n" + current.getListaSinalizacoes().getMenorData().toString() + "\n");
        }else{
            System.out.println("Primeira sinalização implementada: " 
                + "\n" + current.getListaSinalizacoes().getMenorData().toString());
            System.out.println("Última sinalização implementada: " 
                + "\n" + current.getListaSinalizacoes().getMaiorData().toString() + "\n");
        }
    }

    //LEITURA DO CSV
    public void leituraCSV() {
        String linhas[] = new String[91708];
        int numLinhas = 0;
        
        try(BufferedReader reader = new BufferedReader(new FileReader("dataEditado.csv", Charset.forName("UTF-8")))) {
            String line = reader.readLine(); //pula o header da planilha
            line = reader.readLine();

            while (line != null) {
                linhas[numLinhas] = line; //armezena a info inteira da linha
                numLinhas++;
                line = reader.readLine();
            }
        }catch(Exception e) {
            System.err.format("Erro na leitura do arquivo: ", e.getMessage());
        }

        for(int i = 0; i < numLinhas; i++) {
            String[] campos = linhas[i].split(";"); //quebra as linhas em palavras

            String descricao = campos[1];
            String estado = campos[2];
            String complemento = campos[3];

            DateTimeFormatter formatter = null;       
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
            ListaDeSinalizacoes listaSinalizacoes = new ListaDeSinalizacoes(); //reseta a lista de sinalizações

            Rua rua = listaRuas.contains(nomeLog);

            if(rua == null) {
                rua = new Rua(logradouro, nomeLog, listaSinalizacoes);

                Sinalizacao sinalizacao = listaSinalizacoes.contains(
                    new Sinalizacao(
                        numInicial, numFinal, localInstalacao, date, cruzamento, lado, 
                        descricao, fluxo, defronte, complemento, estado
                    )
                );

                if(sinalizacao == null) {
                    sinalizacao = new Sinalizacao(
                        numInicial, numFinal, localInstalacao, date, cruzamento, 
                        lado, descricao, fluxo, defronte, complemento, estado
                    );

                    rua.getListaSinalizacoes().add(sinalizacao);
                }
                listaRuas.orderedAdd(rua);
            }else{
                Sinalizacao sinalizacao = rua.getListaSinalizacoes().contains(
                    new Sinalizacao(
                        numInicial, numFinal, localInstalacao, date, cruzamento, lado, 
                        descricao, fluxo, defronte, complemento, estado
                    )
                );

                if(sinalizacao == null) {
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


