package dam.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class EncargadoTCP implements Runnable {

    private final Map<String, Integer> valores;
    private final Socket socket;

    public EncargadoTCP(Map<String, Integer> valores, Socket socketConectado) throws IOException {
        this.valores = valores;
        this.socket = socketConectado;
    }

    @Override
    public void run() {
        Integer precio;
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            while (true) {
                String valor = br.readLine();
                synchronized (valores) {
                    precio = valores.get(valor);
                }
                if (precio != null) {
                    pw.println(precio);
                    pw.flush();
                } else {
                    pw.println("-1");
                    pw.flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}

