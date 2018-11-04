package client;

import CourseRegistrationSystem.Course;
import server.ServerInterface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

class AdvisorOperations {
    private Logger logs;

    AdvisorOperations(Logger logs) {
        this.logs = logs;
    }

    void chooseOperation(String id, String deptName, Course courseStub) {
        try {
            Scanner sc = new Scanner(System.in);
            String term;
            System.out.println("\n Choose the operation you wish to perform :- \n "
                    + "1. Add CourseData \n"
                    + "2. Remove CourseData \n "
                    + "3. List CourseData Availability \n");
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
                    addCourse(id, term, courseStub);
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
                    deleteCourse(id, term, deptName, courseStub);
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
                    listCourseAvailability(id, term, deptName, courseStub);
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

    private static void addCourse(String clientID, String term, Course courseStub) {
        Scanner sc = new Scanner(System.in);
        String course_name, course_id;
        int course_capacity;
        boolean addCourseResult;
        System.out.println("CourseData ID : \n");
        course_id = sc.nextLine().toUpperCase();
        System.out.println("CourseData Name : \n");
        course_name = sc.nextLine();
        try {
            System.out.println("CourseData Capacity : \n");
            course_capacity = sc.nextInt();
            if (course_id.substring(0, 4).equalsIgnoreCase(clientID.substring(0, 4))) {
                if (course_capacity == (int) course_capacity) {
                    if (course_capacity > 0 && course_capacity <= 5) {
                        addCourseResult = courseStub.addCourse(clientID, course_id, course_name, term, (short) course_capacity);
                        if (addCourseResult) {
                            System.out.println("Congratulations, CourseData added successfully!");
                        } else {
                            System.out.println("This course already exists for this term. Please try again!");
                        }
                    } else {
                        System.out.println("CourseData capacity should be minimum 1 and maximum 5. Kindly check!");
                    }

                } else {
                    System.out.println("CourseData capacity can be in numbers only.");
                }

            } else {
                // System.out.println("CourseData ID should be of the pattern COMP1111/SOEN1111/INSE1111.");
                System.out.println("You can only add courses of your department of the pattern " + clientID.substring(0, 4) + "1111.");
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("CourseData capacity can be in numbers only.");
        }
    }


    private static void deleteCourse(String clientID, String term, String department, Course courseStub) {
        Scanner sc = new Scanner(System.in);
        String course_id, deleteDecision;
        String courses = courseStub.displayCourses(term);
        System.out.println("Courses available in " + term + " term are :- \n");
       if(courses != null && !(courses.isEmpty())){
           System.out.println(courses);
           System.out.println("Enter Course ID of the course you want to remove for " + term + " term : \n");
           course_id = sc.nextLine().toUpperCase();
           System.out.println("Are you sure you want to delete " + course_id + " for " + term + " term. (y/n)");
           deleteDecision = sc.nextLine();
           if (deleteDecision.equalsIgnoreCase("y")) {
               boolean courseDeleteResponse = courseStub.deleteCourse(clientID, course_id, term, department);

               if (courseDeleteResponse) {
                   System.out.println(course_id + " course has been successfully deleted for " + term + " term.");
               } else {
                   System.out.println("There is no course with " + course_id + " CourseData ID for " + term + " term. \n INFO : CourseData ID should be of the pattern " + clientID.substring(0,4) + "1111.");
               }
           } else if (!(deleteDecision.equalsIgnoreCase("n"))){
               System.out.println("Please enter 'y' or 'n' only.");
           }
       } else {
           System.out.println("There are no courses in your department.");
       }
    }

    private static void listCourseAvailability(String id, String term, String dept, Course courseStub) {
        String courses = courseStub.listCourseAvailability(id, term, dept, true);
        System.out.println(courses);
    }

}
