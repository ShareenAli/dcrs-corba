package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SoenServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Logger logs = Logger.getLogger("SOEN Server");

        try {
            FileHandler handler = new FileHandler("soen_server.log", true);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        ServerOperations operations = new ServerOperations("SOEN", logs);
        try {
            Registry registry = LocateRegistry.createRegistry(4005);
            registry.bind("SOEN", operations);
            System.out.println("SOEN server has started!!");
        } catch (Exception e) {
            System.out.println(e);
        }

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(8002);

            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                System.out.println("UDP server running on: " + (8002));
                socket.receive(request);

                UdpOperations udpOp = new UdpOperations(socket, request,operations);
                udpOp.start();
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
}
