import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Gestao {
    static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    static ArrayList<Veiculos> veiculos = new ArrayList<Veiculos>();
    static ArrayList<PostoCarregamento> postos = new ArrayList<PostoCarregamento>();
    static ArrayList<SessaoCarregamento> sessoes = new ArrayList<SessaoCarregamento>();
    static ArrayList<Pagamento> pagamentos = new ArrayList<Pagamento>();

    // Inserir e consultar (através de matrícula) os veículos registados;
    public static void criarVeiculo() {
        String marca, modelo, matricula, tipo_veiculo;
        Date data_registo = null;
        int potencia, capacidade_bateria, autonomia;

        System.out.println("\n***************************************\n");
        System.out.println("\tMenu Criar veiculo\n");
        // tipo de veiculo E (eletrico) ou H (hibrido)
        do {
            tipo_veiculo = Consola.lerString("Tipo de veiculo(E/H): ");
        } while (!tipo_veiculo.equalsIgnoreCase("E") && !tipo_veiculo.equalsIgnoreCase("H"));

        do {
            marca = Consola.lerString("Marca: ");
        } while (marca.isEmpty());
        do {
            modelo = Consola.lerString("Modelo: ");
        } while (modelo.isEmpty());
        do {
            matricula = Consola.lerString("Matricula: ");
        } while (matricula.isEmpty());
        boolean error = false;
        do {
            error = false;
            try {
                data_registo = dateFormat.parse(Consola.lerString("Data de registo do veiculo (dd-mm-yyyy): "));
            } catch (Exception e) {
                System.out.println("Data invalida");
                error = true;
            }
        } while (error);

        do {
            potencia = Consola.lerInt("Potencia do veiculo(cv): ", 1, 999999999);
        } while (potencia == 0);
        do {
            capacidade_bateria = Consola.lerInt("Capacidade da bateria(kwh): ", 1, 999999999);
        } while (capacidade_bateria == 0);
        do {
            autonomia = Consola.lerInt("Autonomia: ", 1, 999999999);
        } while (autonomia == 0);

        int tempo_carregamento;
        double velocidadeCarregamento = 0;

        if (tipo_veiculo.equalsIgnoreCase("E")) {

            do {
                tempo_carregamento = Consola.lerInt("Tempo de carregamento: ", 1, 999999999);
            } while (tempo_carregamento == 0);
            do {
                velocidadeCarregamento = Consola.lerDouble("Velocidade de carregamento: ", 1, 999999999);
            } while (velocidadeCarregamento == 0);
            VeiculosEletricos veiculo = new VeiculosEletricos(marca, modelo, matricula, data_registo, autonomia, velocidadeCarregamento, potencia, tempo_carregamento);
            veiculos.add(veiculo);
        } else {
            double consumo_combustivel;
            do {
                consumo_combustivel = Consola.lerDouble("Consumo de combustivel: ", 1, 999999999);
            } while (consumo_combustivel == 0);
            int cilindrada;
            do {
                cilindrada = Consola.lerInt("Cilindrada: ", 1, 999999999);
            } while (cilindrada == 0);
            double emissao;
            do {
                emissao = Consola.lerDouble("Emissao: ", 1, 999999999);
            } while (emissao == 0);

            VeiculosHibridos veiculo = new VeiculosHibridos(marca, modelo, matricula, data_registo, autonomia, velocidadeCarregamento, potencia, cilindrada, consumo_combustivel, emissao);
            veiculos.add(veiculo);
            System.out.println("Veiculo criado com sucesso");
        }
    }

    public static int procurarVeiculo(String matricula) {
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getMatricula().equals(matricula)) {
                return i;
            }
        }
        return -1;
    }

    public static void consultarVeiculo() {
        String matricula;
        if (veiculos.isEmpty()) {
            System.out.println("Não existem veiculos registados");
            return;
        }
        do {
            matricula = Consola.lerString("Matricula: ");
        } while (matricula.isEmpty());
        // return posicao do veiculo na arraylist
        int posicao = procurarVeiculo(matricula);
        if (posicao == -1) {
            System.out.println("Veiculo não encontrado");
        } else {

            System.out.println("\n" + veiculos.get(posicao).toString());
            System.out.println("Carregue no ENTER voltar ao menu");
            Consola.lerString("\n");
        }
    }

    public static void criarCliente() {
        String morada, email, nome;
        int nif, telemovel;
        Date data_nascimento = null;

        System.out.println("\n***************************************\n");
        System.out.println("\tMenu Criar cliente\n");
        do {
            nome = Consola.lerString("Nome: ");
        } while (nome.isEmpty());
        do {
            telemovel = Consola.lerInt("Telemovel: ", 100000000, 999999999);
        } while (telemovel == 0);
        do {
            nif = Consola.lerInt("NIF: ", 100000000, 999999999);
            // verificar se o nif ja existe
            if (procurarCliente(nif) != -1) {
                System.out.println("NIF já existe");
                nif = 0;
            }
        } while (nif == 0);
        do {
            morada = Consola.lerString("Morada: ");
        } while (morada.isEmpty());
        do {
            email = Consola.lerString("Email: ");
        } while (email.isEmpty());
        boolean error = false;
        do {
            try {
                data_nascimento = dateFormat.parse(Consola.lerString("Data de nascimento: "));
            } catch (Exception e) {
                System.out.println("Data invalida");
                error = true;
            }
        } while (error);
        System.out.println("Cliente criado com sucesso");

        Cliente cliente = new Cliente(nome, nif, morada, telemovel, email, data_nascimento);
        clientes.add(cliente);
    }

    public static void consultarCliente() {
        int nif;
        if (clientes.isEmpty()) {
            System.out.println("Não existem clientes registados");
            return;
        }
        do {
            nif = Consola.lerInt("NIF: ", 100000000, 999999999);
        } while (nif == 0);
        // return posicao do cliente na arraylist
        int posicao = procurarCliente(nif);
        if (posicao == -1) {
            System.out.println("Cliente não encontrado");
        } else {

            System.out.println("\n" + clientes.get(posicao).toString());
            System.out.println("Carregue no ENTER voltar ao menu");
            Consola.lerString("\n");
        }
    }

    public static int procurarCliente(int nif) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNif() == nif) {
                return i;
            }
        }
        return -1;
    }

    public void alterarDadosCliente() {
        System.out.println("Inisira o NIF do cliente que pretende alterar os dados: ");
        int nif = Consola.lerInt("", 100000000, 999999999);
        int posicao = procurarCliente(nif);
        if (posicao == -1) {
            System.out.println("Cliente não encontrado");
        } else {
            System.out.println("O que pretende alterar?");
            System.out.println("1 - Nome");
            System.out.println("2 - Telemovel");
            System.out.println("3 - NIF");
            System.out.println("4 - Morada");
            System.out.println("5 - Email");
            System.out.println("6 - Data de nascimento");
            int opcao = Consola.lerInt("", 1, 6);
            switch (opcao) {
                case 1:
                    String nome = Consola.lerString("Nome: ");
                    clientes.get(posicao).setNome(nome);
                    break;
                case 2:
                    int telemovel = Consola.lerInt("Telemovel: ", 100000000, 999999999);
                    clientes.get(posicao).setTelemovel(telemovel);
                    break;
                case 3:
                    int nif2 = Consola.lerInt("NIF: ", 100000000, 999999999);
                    clientes.get(posicao).setNif(nif2);
                    break;
                case 4:
                    String morada = Consola.lerString("Morada: ");
                    clientes.get(posicao).setMorada(morada);
                    break;
                case 5:
                    String email = Consola.lerString("Email: ");
                    clientes.get(posicao).setEmail(email);
                    break;
                case 6:
                    Date data_nascimento = null;
                    boolean error = false;
                    do {
                        try {
                            data_nascimento = dateFormat.parse(Consola.lerString("Data de nascimento: "));
                        } catch (Exception e) {
                            System.out.println("Data invalida");
                            error = true;
                        }
                    } while (error);
                    clientes.get(posicao).setData_nascimento(data_nascimento);
                    break;
                default:
                    break;
            }
        }
    }

    public void removerCliente() {
        System.out.println("Inisira o NIF do cliente que pretende remover: ");
        int nif = Consola.lerInt("", 100000000, 999999999);
        int posicao = procurarCliente(nif);
        if (posicao == -1) {
            System.out.println("Cliente não encontrado");
        } else {
            clientes.remove(posicao);
            System.out.println("Cliente removido com sucesso");
        }
    }

    public static void criarPostoCarregamento() {
        int codigo_posto, numero_veiculos;
        String localizacao, tipo_posto;
        double custo_kwh;

        System.out.println("\n***************************************\n");
        System.out.println("\tMenu Criar posto de carregamento\n");
        do {
            codigo_posto = Consola.lerInt("Codigo do posto: ", 1, 999999999);
        } while (codigo_posto == 0);
        do {
            localizacao = Consola.lerString("Localização(morada): ");
        } while (localizacao.isEmpty());
        do {
            tipo_posto = Consola.lerString("Tipo de posto: ");
        } while (tipo_posto.isEmpty());
        do {
            custo_kwh = Consola.lerDouble("Custo por kWh(E): ", 0, 999999999);
        } while (custo_kwh == 0);
        do {
            numero_veiculos = Consola.lerInt("Numero de veiculos que podem carregar em simultaneo: ", 1, 999999999);
        } while (numero_veiculos == 0);

        System.out.println("Posto de carregamento criado com sucesso");

        PostoCarregamento posto = new PostoCarregamento(codigo_posto, localizacao, tipo_posto, custo_kwh,
                numero_veiculos);
        postos.add(posto);
    }


    public static void consultarPostoCarregamento() {
        int codigo_posto;
        if (postos.isEmpty()) {
            System.out.println("Não existem postos de carregamento registados");
            return;
        }
        do {
            codigo_posto = Consola.lerInt("Codigo do posto: ", 1, 999999999);
        } while (codigo_posto == 0);
        // return posicao do cliente na arraylist
        int posicao = procurarPosto(codigo_posto);
        if (posicao == -1) {
            System.out.println("Posto de carregamento não encontrado");
        } else {

            System.out.println("\n" + postos.get(posicao).toString());
            // TODO carregue em qualquer tecla para voltar ao menu
            System.out.println("Carregue no ENTER voltar ao menu");
            Consola.lerString("\n");
        }
    }

    public static int procurarPosto(int codigo_posto) {
        for (int i = 0; i < postos.size(); i++) {
            if (postos.get(i).getCodigo_posto() == codigo_posto) {
                return i;
            }
        }
        return -1;
    }

    public static void registrarPagamento(SessaoCarregamento sessao, String metodoPagamento, LocalDateTime dataTransacao, LocalDateTime horaTransacao, boolean pago) {
        Pagamento pagamento = new Pagamento(sessao, metodoPagamento, dataTransacao, horaTransacao, pago);
        pagamentos.add(pagamento);
    }

    public static Pagamento consultarPagamentoPorSessao(String codigoSessao) {
        for (Pagamento pagamento : pagamentos) {
            if (pagamento.getSessao().getCodigo_sessao().equals(codigoSessao)) {
                return pagamento;
            }
        }
        return null;
    }

    // TODO Listagem dos 3 postos de carregamento com maior valor faturado
    // (liquidado);

    // TODO Listagem de sessões de carregamento cujo custo é superior a n
    // euros.Sendo o valor de n solicitado ao utilizador;

    // TODO Total de sessões de carregamento realizados (por cliente);

    // TODO Média de energia consumida por posto de carregamento e por tipo
    // deveículo (híbridos/elétricos);

    // TODO Listagem de pagamentos por efetuar (por cliente);

    // TODO Histórico de sessões de carregamento (por posto de carregamento).

}