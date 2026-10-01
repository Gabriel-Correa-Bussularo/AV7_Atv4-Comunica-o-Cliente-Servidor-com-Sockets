import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Servidor {

    public static void main(String[] args) {
        int porta = 5000;

        try {
            ServerSocket servidor = new ServerSocket(porta);
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                System.out.println("\nAguardando um cliente se conectar...");
                
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);

                String mensagemCliente;

                while ((mensagemCliente = entrada.readLine()) != null) {
                    System.out.println("Comando recebido: " + mensagemCliente);

                    String resposta = "";

                    // pega a hora atual
                    if (mensagemCliente.equalsIgnoreCase("TIME")) {
                        LocalTime horaAtual = LocalTime.now();
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
                        resposta = "Hora atual: " + horaAtual.format(formato);
                    } 
                    // status do server
                    else if (mensagemCliente.equalsIgnoreCase("STATUS")) {
                        resposta = "Servidor ativo e aguardando conexões";
                    } 
                    // devolve o texto
                    else if (mensagemCliente.toUpperCase().startsWith("ECHO ")) {
                        resposta = mensagemCliente.substring(5);
                    } 
                    // encerra a conexao
                    else if (mensagemCliente.equalsIgnoreCase("EXIT")) {
                        resposta = "Conexão encerrada";
                        saida.println(resposta);
                        System.out.println("Cliente encerrou a conexão.");
                        break;
                    } 
                    else {
                        resposta = "Erro: comando não reconhecido";
                    }

                    saida.println(resposta);
                }

                cliente.close();
            }

        } catch (Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}