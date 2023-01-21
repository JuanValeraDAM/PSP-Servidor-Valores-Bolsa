package dam.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClienteTCP {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Socket socket = new Socket("localhost", 7000);
        while (true) {
            System.out.println("Introduce el nombre del valor cuyo precio quieres saber, o (s) para salir");
            String valor = sc.next();
            if (Objects.equals(valor, "s")) {
                socket.close();
                return;
            }
            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream());

                pw.println(valor);
                pw.flush();

                int respuesta = Integer.parseInt(br.readLine());
                System.out.printf("El valor de %s es de %d\n", valor, respuesta);


            } catch (IOException e) {
                System.err.println("Ha ocurrido un error: " + e.getMessage());
            }
        }

    }
}
