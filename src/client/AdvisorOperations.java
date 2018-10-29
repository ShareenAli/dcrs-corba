package client;

import schema.Course;
import server.ServerInterface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class AdvisorOperations {
    private Logger logs;

    public AdvisorOperations(Logger logs) {
        this.logs = logs;
    }

    public void chooseOperation(String id, String deptName, ServerInterface serverinterface) throws RemoteException {
        try {
            Scanner sc = new Scanner(System.in);
            String course_name, course_id, term;
            System.out.println("\n Choose the operation you wish to perform :- \n "
                    + "1. Add Course \n"
                    + "2. Remove Course \n "
                    + "3. List Course Availability \n");
            int operationChoice = Integer.parseInt(sc.nextLine());
            if (operationChoice == 1) {
                System.out.println("Choose the term :- \n" +
                        "-> Fall\n" +
                        "-> Winter\n" +
                        "-> Summer\n");
                term = sc.nextLine().toLowerCase();
                if (term.equalsIgnoreCase("Fall") ||
                        term.equalsIgnoreCase("Winter") ||
                        term.equalsIgnoreCase("Summer")) {
                    addCourse(id, term, serverinterface);
                } else {
                    System.out.println("Please enter valid term name. Try Again!");
                }
            } else if (operationChoice == 2) {
                System.out.println("Choose the term :- \n" +
                        "-> Fall\n" +
                        "-> Winter\n" +
                        "-> Summer\n");
                term = sc.nextLine().toLowerCase();
                if (term.equalsIgnoreCase("Fall") ||
                        term.equalsIgnoreCase("Winter") ||
                        term.equalsIgnoreCase("Summer")) {
                    deleteCourse(id, term, deptName, serverinterface);
                } else {
                    System.out.println("Please enter valid term name. Try Again!");
                }

            } else if (operationChoice == 3) {
                System.out.println("Choose the term :- \n" +
                        "-> Fall\n" +
                        "-> Winter\n" +
                        "-> Summer\n");
                term = sc.nextLine().toLowerCase();
                if (term.equalsIgnoreCase("Fall") ||
                        term.equalsIgnoreCase("Winter") ||
                        term.equalsIgnoreCase("Summer")) {
                    listCourseAvailability(id, term, deptName, serverinterface);
                } else {
                    System.out.println("Please enter valid term name. Try Again!");
                }

            } else {
                System.out.println("Invalid operation selection. Sorry!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter number choices only.");
        }
    }

    public static void addCourse(String clientID, String term, ServerInterface serverinterface) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        String course_name, course_id;
        int course_capacity;
        boolean addCourseResult;
        System.out.println("Course ID : \n");
        course_id = sc.nextLine().toUpperCase();
        System.out.println("Course Name : \n");
        course_name = sc.nextLine();
        try {
            System.out.println("Course Capacity : \n");
            course_capacity = sc.nextInt();
            if (course_id.substring(0, 4).equalsIgnoreCase(clientID.substring(0, 4)))
            /*if (course_id.substring(0, 4).equalsIgnoreCase("COMP") ||
                    course_id.substring(0, 4).equalsIgnoreCase("SOEN") ||
                    course_id.substring(0, 4).equalsIgnoreCase("INSE"))*/ {
                if (course_capacity == (int) course_capacity) {
                    if (course_capacity > 0 && course_capacity <= 5) {
                        try {
                            addCourseResult = serverinterface.addCourse(clientID, course_id, course_name, term, course_capacity);
                            if (addCourseResult) {
                                System.out.println("Congratulations, Course added successfully!");
                            } else {
                                System.out.println("This course already exists for this term. Please try again!");
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Course capacity should be minimum 1 and maximum 5. Kindly check!");
                    }

                } else {
                    System.out.println("Course capacity can be in numbers only.");
                }

            } else {
                // System.out.println("Course ID should be of the pattern COMP1111/SOEN1111/INSE1111.");
                System.out.println("You can only add courses of your department of the pattern " + clientID.substring(0, 4) + "1111.");
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Course capacity can be in numbers only.");
        }
    }


    public static void deleteCourse(String clientID, String term, String department, ServerInterface serverInterface) throws
            RemoteException {
        Scanner sc = new Scanner(System.in);
        String course_id, deleteDecision;
        HashMap<String, Course> courses = serverInterface.displayCourses(term);
        System.out.println("Courses available in " + term + " term are :- \n");
       if(courses != null && !(courses.isEmpty())){
           for (Map.Entry<String, Course> theTerm : courses.entrySet()) {
               String courseID = theTerm.getKey();
               Course courseDetails = theTerm.getValue();
               System.out.println("\n -> Course ID : " + courseID);
           }
           System.out.println("Enter Course ID of the course you want to remove for " + term + " term : \n");
           course_id = sc.nextLine().toUpperCase();
           System.out.println("Are you sure you want to delete " + course_id + " for " + term + " term. (y/n)");
           deleteDecision = sc.nextLine();
           if (deleteDecision.equalsIgnoreCase("y")) {
               boolean courseDeleteResponse = serverInterface.deleteCourse(clientID, course_id, term, department);

               if (courseDeleteResponse) {
                   System.out.println(course_id + " course has been successfully deleted for " + term + " term.");
               } else {
                   System.out.println("There is no course with " + course_id + " Course ID for " + term + " term. \n INFO : Course ID should be of the pattern " + clientID.substring(0,4) + "1111.");
               }
           } else if (!(deleteDecision.equalsIgnoreCase("n"))){
               System.out.println("Please enter 'y' or 'n' only.");
           }
       } else {
           System.out.println("There are no courses in your department.");
       }

    }

    public static void listCourseAvailability(String id, String term, String dept, ServerInterface serverInterface) {
        Scanner sc = new Scanner(System.in);
        try {
            HashMap<String, Integer> courses = serverInterface.listCourseAvailibility(id, term, dept, true);
            //System.out.println("Check list course availability");
            if (courses.size() > 0) {
                System.out.println("CHECK IN CLIENT LIST COURSE AVAILABILITY");
                for (Map.Entry<String, Integer> coursesList : courses.entrySet()) {
                    String courseID = coursesList.getKey();
                    Integer courseDetails = coursesList.getValue();
                    System.out.println("\n -> Course ID : " + courseID + "\n Space available: " + courseDetails);
                }
            } else {
                System.out.println("There are no courses for " + term + " term.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
