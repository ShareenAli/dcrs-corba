package server;

import CourseRegistrationSystem.Course;
import CourseRegistrationSystem.CourseHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

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

    public static void main(String[] args) {
        Logger logs = Logger.getLogger("Inse Server");

        try {
            FileHandler handler = new FileHandler("inse_server.log", true);
            logs.addHandler(handler);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        CourseOperations courseOperations = new CourseOperations("INSE", logs);

        // initialize CORBA server
        try {
            ORB orb = ORB.init(args, null);

            // reference to root POA and activate the manager
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // reference from the implementation class
            Object ref = rootPOA.servant_to_reference(courseOperations);
            Course href = CourseHelper.narrow(ref);

            // root naming context
            Object objectReference = orb.resolve_initial_references("NameService");
            NamingContextExt ncExtRef = NamingContextExtHelper.narrow(objectReference);

            // make the path and bind
            NameComponent path[] = ncExtRef.to_name("INSE");
            ncExtRef.rebind(path, href);

            logs.info("The INSE ORB has been started");
        } catch (InvalidName invalidName) {
            logs.severe("Invalid reference to the Portable Object Adapter. The server can not initialize POA. \nMessage: " + invalidName.getMessage());
            return;
        } catch (AdapterInactive adapterInactive) {
            logs.severe("The Portable Object Adapter is inactive. \nMessage: " + adapterInactive.getMessage());
            return;
        } catch (ServantNotActive servantNotActive) {
            logs.severe("The implementation class (servant) is either not initialized or inactive. \nMessage: " + servantNotActive.getMessage());
            return;
        } catch (WrongPolicy wrongPolicy) {
            logs.severe("The implementation class (servant) is initialized with wrong policy. \nMessage: " + wrongPolicy.getMessage());
            return;
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
            logs.severe("Invalid name for the NameService. \nMessage: " + invalidName.getMessage());
            return;
        } catch (CannotProceed cannotProceed) {
            logs.severe("CannotProceed Exception thrown. \nMessage: " + cannotProceed.getMessage());
            return;
        } catch (NotFound notFound) {
            logs.severe("Naming context not found. \nMessage: " + notFound.getMessage());
            return;
        }

        try {
            DatagramSocket socket = new DatagramSocket(8003);

            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                System.out.println("UDP server running on: " + (8003));
                socket.receive(request);

                UdpOperations udpOp = new UdpOperations(socket, request, courseOperations);
                udpOp.start();
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }

    }

}
