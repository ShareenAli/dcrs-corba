package client;

import CourseRegistrationSystem.Course;
import CourseRegistrationSystem.CourseHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {
        Logger logs = Logger.getLogger("Client");
        String clientID, deptName = "";
        Boolean advisor_id_letter = false, student_id_letter = false, loop = true;
        Course clientStub = null;
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
                // create and instantiate orb
                ORB orb = ORB.init(args, null);

                // get the root naming context
                Object objectReference = orb.resolve_initial_references("NameService");
                NamingContextExt ref = NamingContextExtHelper.narrow(objectReference);


                if (deptName.equalsIgnoreCase("COMP")) {
                    clientStub = CourseHelper.narrow(ref.resolve_str("COMP"));
                    System.out.println("Connected to COMP Server!");
                } else if (deptName.equalsIgnoreCase("SOEN")) {
                    clientStub = CourseHelper.narrow(ref.resolve_str("SOEN"));
                    System.out.println("Connected to SOEN Server!");
                } else if (deptName.equalsIgnoreCase("INSE")) {
                    clientStub = CourseHelper.narrow(ref.resolve_str("INSE"));
                    System.out.println("Connected to INSE Server!");
                } else {
                    System.out.println("Department name NOT FOUND!");
                }
                logs.info("Connected to " + deptName + " server");
            } catch (InvalidName invalidName) {
                logs.severe("Invalid reference to Name Service. \nMessage: " + invalidName.getMessage());
                return;
            } catch (CannotProceed cannotProceed) {
                logs.severe("CannotProceed exception thrown. \nMessage: " + cannotProceed.getMessage());
                return;
            } catch (org.omg.CosNaming.NamingContextPackage.InvalidName invalidName) {
                logs.severe("Invalid reference to the server. Please check the name. \n Message:" + invalidName.getMessage());
                return;
            } catch (NotFound notFound) {
                logs.severe("Server not found.\nMessage: " + notFound.getMessage());
                return;
            }
        }

        if (clientStub == null)
            return;

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
        } else {
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
