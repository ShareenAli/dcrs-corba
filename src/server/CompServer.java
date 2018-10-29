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

public class CompServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Logger logs = Logger.getLogger("comp server");

        try {
            FileHandler handler = new FileHandler("comp_server.log", true);
            logs.addHandler(handler);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        ServerOperations compOp = new ServerOperations("COMP", logs);
        try {
            Registry registry = LocateRegistry.createRegistry(4004);
            registry.bind("COMP", compOp);
            System.out.println("COMP server has started!!");
        } catch (Exception e) {
            System.out.println(e);
        }

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(8001);

            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                System.out.println("UDP server running on: " + (8001));
                socket.receive(request);

                UdpOperations udpOp = new UdpOperations(socket, request,compOp);
                udpOp.start();
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
}
