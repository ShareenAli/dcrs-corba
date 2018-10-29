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

public class InseServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Logger logs = Logger.getLogger("Inse Server");

        try {
            FileHandler handler = new FileHandler("inse_server.log", true);
            logs.addHandler(handler);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        ServerOperations inseOp = new ServerOperations("INSE", logs);
        try {
            Registry registry = LocateRegistry.createRegistry(4006);
            registry.bind("INSE", inseOp);
            System.out.println("INSE server has started!!");
            logs.info("INSE server has started!");
        } catch (Exception e) {
            System.out.println(e);
        }

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(8003);

            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                System.out.println("UDP server running on: " + (8003));
                socket.receive(request);

                UdpOperations udpOp = new UdpOperations(socket, request,inseOp);
                udpOp.start();
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }

}
