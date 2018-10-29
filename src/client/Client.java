package client;

import server.ServerInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) throws RemoteException {
        Logger logs = Logger.getLogger("Client");
        String clientID, deptName = "";
        Boolean advisor_id_letter = false, student_id_letter = false, loop = true;
        ServerInterface clientStub = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your unique ID : ");
        clientID = sc.nextLine().toUpperCase();
        try {
            if (clientID.charAt(4) == ('A') && (clientID.substring(0, 4).equalsIgnoreCase("comp") ||
                    clientID.substring(0, 4).equalsIgnoreCase("soen") ||
                    clientID.substring(0, 4).equalsIgnoreCase("inse"))) {
                advisor_id_letter = true;
                deptName = clientID.substring(0, 4);
            } else if (clientID.charAt(4) == ('S') && (clientID.substring(0, 4).equalsIgnoreCase("comp") ||
                    clientID.substring(0, 4).equalsIgnoreCase("soen") ||
                    clientID.substring(0, 4).equalsIgnoreCase("inse"))) {
                student_id_letter = true;
                deptName = clientID.substring(0, 4);
            } else {
                System.out.println("Invalid ID.");
            }
        }
        catch (Exception e){
            System.out.println("Invalid CLient ID.");
        }


        try {
            FileHandler fileHandler = new FileHandler(clientID + ".log", true);
            logs.addHandler(fileHandler);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        AdvisorOperations advOperations = new AdvisorOperations(logs);
        StudentOperations studentOperations = new StudentOperations(logs);

        if (advisor_id_letter || student_id_letter) {
            try {
                if (deptName.equalsIgnoreCase("COMP")) {
                    Registry registry = LocateRegistry.getRegistry(4004);
                    clientStub = (ServerInterface) registry.lookup(deptName);
                    System.out.println("Connected to COMP Server!");
                } else if (deptName.equalsIgnoreCase("SOEN")) {
                    Registry registry = LocateRegistry.getRegistry(4005);
                    clientStub = (ServerInterface) registry.lookup(deptName);
                    System.out.println("Connected to SOEN Server!");
                } else if (deptName.equalsIgnoreCase("INSE")) {
                    Registry registry = LocateRegistry.getRegistry(4006);
                    clientStub = (ServerInterface) registry.lookup(deptName);
                    System.out.println("Connected to INSE Server!");
                } else {
                    System.out.println("Department name NOT FOUND!");
                }
                logs.info("Connected to " + deptName + " server");
            } catch (NotBoundException e) {
                System.out.println("Invalid Client ID. Sorry!");
                e.printStackTrace();
            }
        }

        if (advisor_id_letter) {
            boolean validAdvisor = false;
            try {
                validAdvisor = clientStub.validateAdvisor(clientID);
                logs.info("Advisor has been validated at the server.");
            } catch (Exception e) {
                e.printStackTrace();
                logs.warning("Detected invalid Advisor ID.");
                System.out.println("Invalid ID.");
            }
            while (true) {
                if (validAdvisor) {
                    advOperations.chooseOperation(clientID, deptName, clientStub);
                } else {
                    System.out.println("Invalid Client ID. Sorry!");
                    break;
                }
            }
        } else if (student_id_letter) {
            boolean validStudent = false;
            try {
                validStudent = clientStub.validateStudent(clientID);
                logs.info("Student has been validated at the server.");
            } catch (Exception e) {
                e.printStackTrace();
                logs.warning("Detected invalid Student ID.");
                System.out.println("Invalid ID.");
            }
            while (true) {
                if (validStudent) {
                    studentOperations.chooseOperation(clientID, deptName, clientStub);
                } else {
                    System.out.println("Invalid Client ID. Sorry!");
                    break;
                }
            }

        }
    }
}
