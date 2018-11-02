package server;

import CourseRegistrationSystem.CoursePOA;
import schema.Course;
import schema.UdpBody;
import schema.UdpPacket;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CourseOperations extends CoursePOA {
    private Logger logs;

    private HashMap<String, HashMap<String, Course>> courseDetails = new HashMap<>();
    private HashMap<String, HashMap<String, List<String>>> studentTermWiseDetails = new HashMap<>();
    private HashMap<String, Integer> listCourse = new HashMap<>();
    private String advisorID = "";
    int CompPort = 8001, SoenPort = 8002, InsePort = 8003;

    public CourseOperations(String deptName, Logger logs) {
        this.logs = logs;
        advisorID = deptName.toUpperCase() + "A1001";
        for (int i = 1; i < 6; i++) {
            HashMap<String, List<String>> studentCourseDetails = new HashMap<>();
            String studentID = deptName + "S100".concat(String.valueOf(i));
            studentTermWiseDetails.put(studentID, studentCourseDetails);
        }
    }

    @Override
    public boolean validateAdvisor(String clientId) {
        return advisorID.equalsIgnoreCase(clientId);
    }

    @Override
    public boolean validateStudent(String clientId) {
        for (Map.Entry<String, HashMap<String, List<String>>> studentIDlist : this.studentTermWiseDetails.entrySet()) {
            String studentID = studentIDlist.getKey();
            if (studentID.equalsIgnoreCase(clientId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addCourse(String id, String courseId, String courseName, String term, short seatsAvailable) {
        if (courseDetails.containsKey(term)) {
            HashMap<String, Course> termMap = courseDetails.get(term);
            if (termMap.containsKey(courseId)) {
                System.out.println("This course ID already exists for this term. Please try again!");
                return false;
            } else {
                Course newCourse = new Course(courseId, courseName, term, seatsAvailable);
                termMap.put(courseId, newCourse);
                courseDetails.put(term, termMap);
                showCourses();
                return true;
            }
        } else {
            HashMap<String, Course> courseMap = new HashMap<>();
            Course course = new Course(courseId, courseName, term, seatsAvailable);
            courseMap.put(course.getCourse_id(), course);
            courseDetails.put(course.getTerm(), courseMap);
            showCourses();
            return true;
        }
    }

    @Override
    public void showCourses() {
        System.out.println("\n New Operation : \n");
        for (Map.Entry<String, HashMap<String, Course>> term : this.courseDetails.entrySet()) {
            String termName = term.getKey();
            for (Map.Entry<String, Course> course : term.getValue().entrySet()) {
                String courseId = course.getKey();
                Course courseDetails = course.getValue();
                System.out.println("Term : " + termName + "\n Course ID: " + courseId +
                        "\n Course Name : " + courseDetails.getCourse_name() +
                        "\n Total Capacity : " + courseDetails.getCourse_capacity() +
                        "\n Seats Available : " + courseDetails.getSeats_available() +
                        "\n Course Added Successfully!");
            }
        }
    }

    @Override
    public boolean deleteCourse(String id, String courseId, String term, String department) {
        int[] ports = new int[2];
        if (courseDetails.containsKey(term)) {
            HashMap<String, Course> termMap = this.courseDetails.get(term);
            if (termMap.containsKey(courseId)) {
                termMap.remove(courseId);
                courseDetails.put(term, termMap);
                for (Map.Entry<String, HashMap<String, List<String>>> studentTermCourseDetails : this.studentTermWiseDetails.entrySet()) {
                    for (Map.Entry<String, List<String>> termCoursesList : studentTermCourseDetails.getValue().entrySet()) {
                        List<String> courseIDlist = termCoursesList.getValue();
                        courseIDlist.remove(courseId);
                    }
                }
                deleteCourseStudentList(courseId);

                if (id.substring(0, 4).equalsIgnoreCase("COMP")) {
                    ports[0] = SoenPort;
                    ports[1] = InsePort;
                } else if (id.substring(0, 4).equalsIgnoreCase("SOEN")) {
                    ports[0] = CompPort;
                    ports[1] = InsePort;
                } else {
                    ports[0] = CompPort;
                    ports[1] = SoenPort;
                }

                UdpBody udpBody = new UdpBody(id, term, courseId, department);
                UdpPacket udpPacket = new UdpPacket(4, udpBody);
                System.out.println("OPERATION1: " + udpPacket.getOperation());
                String responseObj = (String) udpPacketInfo(udpPacket, ports[0]);
                System.out.println("RESPONSE1: " + responseObj);

                UdpBody udpBody2 = new UdpBody(id, term, courseId, department);
                UdpPacket udpPacket2 = new UdpPacket(4, udpBody2);
                System.out.println("OPERATION2: " + udpPacket.getOperation());
                String responseObj2 = (String) udpPacketInfo(udpPacket2, ports[1]);
                System.out.println("RESPONSE2: " + responseObj2);

                return true;
            } else {
                System.out.println("There is no course with " + courseId + " Course ID.");
                return false;
            }
        } else {
            System.out.println("Please enter valid term name. Try Again!");
        }
        return false;
    }

    @Override
    public String enrollCourse(String id, String term, String department, boolean udpCall) {
        HashMap<String, Course> termMap = courseDetails.get(term);
        HashMap<String, List<String>> studentCourseDetails = studentTermWiseDetails.get(id);
        int port = 0, enrollLimit = 0;

        if (udpCall) {
            System.out.println("UDP details : " + "\n" + id + "\n" + term + "\n" + department + "\n" + id);
            List<String> courses = studentCourseDetails.get(term);

            if (studentCourseDetails.containsKey(term)) {
                if (courses != null) {

                    for(String course : courses){
                        System.out.println("COURSEIDS: " + course);
                        System.out.println("department : " + department);
                        System.out.println("Student ID : " + id.substring(0,4));
                        if(!(course.substring(0,4).equalsIgnoreCase(department))){
                            enrollLimit++;
                        }
                    }
                    if(enrollLimit==2){
                        return "deptLimit";
                    }
                    if (courses.size() == 3) {
                        return "limit";
                    }
                    if (courses.contains(id)) {
                        return "enrolledAlready";
                    }
                }
            }
            if (id.substring(0, 4).equalsIgnoreCase("COMP")) {
                port = CompPort;
            } else if (id.substring(0, 4).equalsIgnoreCase("SOEN")) {
                port = SoenPort;
            } else {
                port = InsePort;
            }
            System.out.println("Port : " + port);
            UdpBody udpBody = new UdpBody(id, term, id, department);
            UdpPacket udpPacket = new UdpPacket(1, udpBody);
            System.out.println("Check in enroll method");
            System.out.println(udpPacket.getOperation());
            String responseObj = (String) udpPacketInfo(udpPacket, port);
            if (responseObj.equalsIgnoreCase("enrolledSuccessfully")) {
                if (studentCourseDetails.containsKey(term)) {
                    courses.add(id);
                    studentCourseDetails.put(term, courses);
                    studentTermWiseDetails.put(id, studentCourseDetails);
                } else {
                    List<String> coursesList = new ArrayList<>();
                    coursesList.add(id);
                    studentCourseDetails.put(term, coursesList);
                    studentTermWiseDetails.put(id, studentCourseDetails);
                }
                displayStudentDetails();
                return "enrolledSuccessfully";
            } else {
                return responseObj;
            }
        } else {
            if (courseDetails.containsKey(term)) {
                Course course = termMap.get(id);
                if (course != null) {
                    List<String> courses = studentCourseDetails.get(term);
                    if (course.courseAvailability()) {
                        return "courseFull";
                    }
                    if (studentCourseDetails.containsKey(term)) {
                        if (courses.size() == 3) {
                            return "limit";
                        }
                        if (courses.contains(id)) {
                            return "enrolledAlready";
                        }
                    }
                    course.setEnrolledStudents(id);
                    termMap.put(id, course);
                    courseDetails.put(term, termMap);
                    if (studentCourseDetails.containsKey(term)) {
                        courses.add(id);
                        studentCourseDetails.put(term, courses);
                        studentTermWiseDetails.put(id, studentCourseDetails);
                    } else {
                        List<String> coursesList = new ArrayList<>();
                        coursesList.add(id);
                        studentCourseDetails.put(term, coursesList);
                        studentTermWiseDetails.put(id, studentCourseDetails);
                    }
                    displayStudentDetails();
                    return "enrolledSuccessfully";
                } else {
                    return "courseNotFound";
                }
            }
            return "termNotFound";
        }
    }

    @Override
    public String displayCourses(String term) {
        HashMap<String, Course> termCourses = this.courseDetails.get(term);
        // TODO::implement parsing
        return "";
    }

    @Override
    public boolean dropCourse(String studentId, String courseId, String term, String department, boolean udpCall) {
        int port = 0;
        if (udpCall) {
            if (courseId.length()<8){
                if (courseId.substring(0, 4).equalsIgnoreCase("COMP")) {
                    port = CompPort;
                } else if (courseId.substring(0, 4).equalsIgnoreCase("SOEN")) {
                    port = SoenPort;
                } else {
                    port = InsePort;
                }
                System.out.println("Port : " + port);
                UdpBody udpBody = new UdpBody(studentId, term, courseId, department);
                UdpPacket udpPacket = new UdpPacket(2, udpBody);
                System.out.println("Check in enroll method");
                System.out.println(udpPacket.getOperation());
                Boolean responseObj = (Boolean) udpPacketInfo(udpPacket, port);

                if (responseObj.booleanValue()) {
                    HashMap<String, List<String>> studentTermCourseDetails = this.studentTermWiseDetails.get(studentId);
                    List<String> course = studentTermCourseDetails.get(term);
                    boolean result = course.remove(courseId);
                    if (result) {
                        studentTermCourseDetails.put(term, course);
                        studentTermWiseDetails.put(studentId, studentTermCourseDetails);
                        System.out.println(courseId + " course has been dropped by " + studentId + " student for " + term + " term.");
                        displayStudentDetails();
                        return true;
                    } else {
                        System.out.println("Couldn't find the course");
                        return false;
                    }
                } else {
                    return false;
                }
            }else {
                System.out.println("Course can only be of the pattern COMP####/SOEN####/INSE####.");
            }

        } else {
            if (courseDetails.containsKey(term)) {
                HashMap<String, Course> termMap = this.courseDetails.get(term);
                if (termMap.containsKey(courseId)) {
                    Course courseDetails = termMap.get(courseId);
                    ArrayList<String> studentIDlist = courseDetails.getEnrolledStudents();
                    studentIDlist.remove(studentId);
                    courseDetails.setEnrolledStudents(studentIDlist);
                    HashMap<String, List<String>> studentTermCourseDetails = this.studentTermWiseDetails.get(studentId);
                    List<String> course = studentTermCourseDetails.get(term);
                    boolean result = course.remove(courseId);
                    if (result) {
                        studentTermCourseDetails.put(term, course);
                        studentTermWiseDetails.put(studentId, studentTermCourseDetails);
                        System.out.println(courseId + " course has been dropped by " + studentId + " student for " + term + " term.");
                        displayStudentDetails();
                        return true;
                    } else {
                        System.out.println("Couldn't find the course");
                        return false;
                    }
                } else {
                    System.out.println("There is no course with " + courseId + " Course ID.");

                }
            } else {
                System.out.println("Please enter valid term name. Try Again!");
            }
        }
        return false;
    }

    @Override
    public String getClassSchedule(String studentId) {
        HashMap<String, List<String>> studentTermCourseDetails = this.studentTermWiseDetails.get(studentId);
        //TODO::implement parsing
        return "";
    }

    @Override
    public String listCourseAvailability(String id, String term, String department, boolean udpCall) {
        System.out.println(term);
        HashMap<String, Course> termMap = courseDetails.get(term);
        listCourse.clear();
        int[] ports = new int[2];

        if (id.substring(0, 4).equalsIgnoreCase("COMP")) {
            ports[0] = SoenPort;
            ports[1] = InsePort;
        } else if (id.substring(0, 4).equalsIgnoreCase("SOEN")) {
            ports[0] = CompPort;
            ports[1] = InsePort;
        } else {
            ports[0] = CompPort;
            ports[1] = SoenPort;
        }

        System.out.println("PORTS 1:" + ports[0]);
        System.out.println("PORTS 2:" + ports[1]);

        UdpBody udpBody = new UdpBody(id, term, "", department);
        UdpPacket udpPacket = new UdpPacket(3, udpBody);
        System.out.println("Check in list course method");
//        System.out.println(udpPacket.getOperation());
        HashMap<String, Integer> responseObj = (HashMap<String, Integer>) udpPacketInfo(udpPacket, ports[0]);
        System.out.println("LIST1: " + responseObj);

        UdpBody udpBody2 = new UdpBody(id, term, "", department);
        UdpPacket udpPacket2 = new UdpPacket(3, udpBody2);
        System.out.println("Check in list course method 2");
//        System.out.println(udpPacket.getOperation());
        HashMap<String, Integer> responseObj2 = (HashMap<String, Integer>) udpPacketInfo(udpPacket2, ports[1]);
        System.out.println("LIST2: " + responseObj2);

        listCourse.putAll(responseObj);
        listCourse.putAll(responseObj2);

        if (termMap != null) {
            for (Map.Entry<String, Course> courseEntry : termMap.entrySet()) {
                Course course = courseEntry.getValue();
                int space = course.getCourse_capacity() - course.getEnrolledStudents().size();
                System.out.println("COURSEID: " + course.getCourse_id() + "SPACE: " + space);
                listCourse.put(course.getCourse_id(), space);
            }
            System.out.println("MAINLIST: " + listCourse);
            //TODO::implement parsing
            return "";
        }

        System.out.println(listCourse.size());

        //TODO::implement parsing
        return "";
    }

    public String udpEnrollCourse(String id, String term, String department, String course_id) {
        HashMap<String, Course> termMap = courseDetails.get(term);
        if (courseDetails.containsKey(term)) {
            Course course = termMap.get(course_id);
            if (course != null) {
                if (course.courseAvailability()) {
                    return "courseFull";
                }
                System.out.println("UDP details from udpenroll: " + "\n" + id + "\n" + term + "\n" + department + "\n" + course_id);
                course.setEnrolledStudents(id);
                termMap.put(course_id, course);
                courseDetails.put(term, termMap);
                return "enrolledSuccessfully";
            } else {
                return "courseNotFound";
            }
        } else {
            return "courseNotFound";
        }
    }

    public boolean udpDropCourse(String studentID, String courseID, String term, String department) {
        if (courseDetails.containsKey(term)) {
            HashMap<String, Course> termMap = this.courseDetails.get(term);
            if (termMap.containsKey(courseID)) {
                Course courseDetails = termMap.get(courseID);
                ArrayList<String> studentIDlist = courseDetails.getEnrolledStudents();
                studentIDlist.remove(studentID);
                courseDetails.setEnrolledStudents(studentIDlist);
                return true;
            }
        }
        return false;
    }

    public HashMap<String, Integer> udpListCourseAvailability(String id, String term, String dept) {
        HashMap<String, Course> termMap = courseDetails.get(term);
        System.out.println("Check in udplistcourse");
        if (termMap != null) {
            for (Map.Entry<String, Course> courseEntry : termMap.entrySet()) {
                Course course = courseEntry.getValue();
                int space = course.getCourse_capacity() - course.getEnrolledStudents().size();
                System.out.println("Course ID: " + course.getCourse_id() + "Course space: " + space);
                listCourse.put(course.getCourse_id(), space);
            }
            return listCourse;
        }
        return listCourse;
    }

    private Object udpPacketInfo(UdpPacket udpPacket, int port) {

        try {
            Object response;
            DatagramSocket socket = new DatagramSocket();

            System.out.println("CHECK FROM UDPPACKET METHOD");
            byte[] requestMessage = serialize(udpPacket);
            DatagramPacket requestPacket = new DatagramPacket(requestMessage, requestMessage.length,
                    InetAddress.getByName("localhost"), port);
            socket.send(requestPacket);

            // incoming
            byte[] responseMessage = new byte[1000];
            DatagramPacket responsePacket = new DatagramPacket(responseMessage, responseMessage.length);
            socket.receive(responsePacket);

            response = deserialize(responsePacket.getData());
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Error in server";
    }

    protected String deleteCourseStudentList(String courseID){
        for (Map.Entry<String, HashMap<String, List<String>>> studentTermCourseDetails : this.studentTermWiseDetails.entrySet()) {
            for (Map.Entry<String, List<String>> termCoursesList : studentTermCourseDetails.getValue().entrySet()) {
                List<String> coursesList = termCoursesList.getValue();
                coursesList.remove(courseID);
            }
        }
        return "Successfully deleted!";
    }

    public void displayStudentDetails() {
        System.out.println("New Operation : ");
        for (Map.Entry<String, HashMap<String, List<String>>> studentTermCourseDetails : this.studentTermWiseDetails.entrySet()) {
            String studentID = studentTermCourseDetails.getKey();
            System.out.println("StudentId: " + studentID);
            for (Map.Entry<String, List<String>> termCoursesList : studentTermCourseDetails.getValue().entrySet()) {
                List<String> courseList = termCoursesList.getValue();
                String term = termCoursesList.getKey();
                System.out.println(studentID + " is enrolled for following courses : " + studentTermWiseDetails.get(studentID));
            }
        }
    }

    private byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return o.readObject();
            }
        }
    }
}
