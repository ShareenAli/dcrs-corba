package server;

import schema.Course;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ServerInterface extends Remote{

	public boolean validateAdvisor(String clientID) throws RemoteException;
	public boolean validateStudent(String clientID) throws RemoteException;
	public boolean addCourse(String id, String course_id, String course_name, String term, int seats_available) throws RemoteException;
	public void showCourses() throws RemoteException;
	public boolean deleteCourse(String id, String course_id, String term, String department) throws RemoteException;
	public String enrollCourse(String id, String term, String department, String course_id, boolean udpCall)throws RemoteException;
	public HashMap<String, Course> displayCourses(String term)throws RemoteException;
	public boolean dropCourse(String studentID, String course_id, String term, String department, boolean udpCall) throws RemoteException;
	public HashMap<String, List<String>> getClassSchedule(String studentID) throws RemoteException;

	public HashMap<String, Integer> listCourseAvailibility(String id, String term, String dept, boolean udpCall)throws RemoteException;
}
