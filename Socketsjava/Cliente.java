import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        String ipServidor = "127.0.0.1"; // Localhost
        int portaServidor = 5000;

        try {
            System.out.println("Conectando ao servidor...");
            
            Socket socket = new Socket(ipServidor, portaServidor);
            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
            
            Scanner teclado = new Scanner(System.in);

            while (true) {
                System.out.print("Digite um comando: ");
                String comando = teclado.nextLine();

                if (comando.trim().isEmpty()) {
                    continue;
                }

                saida.println(comando);

                String resposta = entrada.readLine();
                System.out.println("Resposta do servidor: " + resposta);

                if (comando.equalsIgnoreCase("EXIT")) {
                    break;
                }
            }

            teclado.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Não foi possível conectar ao servidor.");
        }
    }
}