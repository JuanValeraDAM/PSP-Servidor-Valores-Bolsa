package dam.psp;

import java.io.IOException;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        DatagramSocket datagramSocket = new DatagramSocket();
        SocketAddress socketAddress = new InetSocketAddress("localhost", 7000);

        while (true) {
            System.out.println("Introduce el valor que deseas modificar o añadir, o (s) para salir:");
            String valor = sc.next();
            if (Objects.equals(valor, "s")) {
                return;
            }
            System.out.println("Introduce el precio:");
            int precio = sc.nextInt();

            String mensajeUDP = String.format("%s %d", valor, precio);
            byte[] buffer = mensajeUDP.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length, socketAddress);
            datagramSocket.send(datagramPacket);
        }
    }


}
