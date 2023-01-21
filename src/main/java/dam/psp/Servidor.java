package dam.psp;
/*
Implementa un servidor de valores de bolsa. Deberá almacenar una colección de precios de
diversos valores (un precio para cada nombre). El servidor tendrá en tod0 momento disponibles
los siguientes servicios:
 Actualización de precio: al enviar al servidor un mensaje UDP conteniendo el nombre
de un valor y su precio, el servidor actualizará su colección de precios, sin enviar
ninguna respuesta. Este servicio se puede usar para dar de alta un valor nuevo, o
actualizar uno existente.
 Consulta de precio: el servidor aceptará conexiones TCP por las cuales recibirá el
nombre de un valor, respondiendo con el precio correspondiente. Esto se repetirá hasta
que el cliente cierre la conexión, permitiendo por tanto consultar varios valores.
Además de implementar este servidor, tendrás que implementar dos clientes, uno para cada
servicio.
Deben poder atenderse a varios clientes a la vez (varios clientes TCP a la vez, y al mismo tiempo
atender los mensajes UDP), sin condiciones de carrera. Se debe informar debidamente de las
excepciones, y el código debe recuperarse de ellas si es posible
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(7000);
        Map<String, Integer> valores = new HashMap<>();
        valores.putAll(Map.of(
                "Amazon", 30000,
                "Ebay", 2000,
                "Spotify", 6000,
                "IntelliJ", 5000,
                "Apple", 60000
        ));

        try {
            EncargadoUDP encargadoUDP = new EncargadoUDP(valores);
            Thread hiloEncargadoUDP = new Thread(encargadoUDP);
            hiloEncargadoUDP.start();

            while (true) {
                Socket socketConectado = serverSocket.accept();
                EncargadoTCP encargadoTCP = new EncargadoTCP(valores, socketConectado);
                Thread hiloEncargadoTCP = new Thread(encargadoTCP);
                hiloEncargadoTCP.start();
            }
        } catch (IOException e) {
            System.err.println("Ha habido una IOException en el main");
        }


    }
}