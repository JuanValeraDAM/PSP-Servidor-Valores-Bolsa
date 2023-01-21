package dam.psp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EncargadoUDP implements Runnable {
    private final DatagramSocket datagramSocket;

    private final Map<String, Integer> valores;

    public EncargadoUDP(Map<String, Integer> valores) throws SocketException {
        this.valores = valores;
        this.datagramSocket = new DatagramSocket(7000);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        while (true) {
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);
            try {
                datagramSocket.receive(datagramPacket);
            } catch (IOException e) {
                System.err.println("Error al recibir datagram: " + e.getMessage());
            }
            String UDPrecibido = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), StandardCharsets.UTF_8);
            String[] valorYprecio = UDPrecibido.split(" ");
            synchronized (valores) {
                valores.put(valorYprecio[0], Integer.parseInt(valorYprecio[1]));
            }
        }
    }
}
