package client;

import CourseRegistrationSystem.Course;

public class StuOperationThread implements Runnable {
    private Thread thread;
    private Course courseStub;
    private String term, department, studentId, oldCourseId, newCourseId;

    StuOperationThread(Course courseStub, String term, String department, String studentId, String oldCourseId, String newCourseId) {
        this.courseStub = courseStub;
        this.term = term;
        this.department = department;
        this.studentId = studentId;
        this.oldCourseId = oldCourseId;
        this.newCourseId = newCourseId;
    }

    @Override
    public void run() {
        System.out.println(studentId + " has sent request to enroll " + newCourseId + " in place of " + oldCourseId);
        String response = courseStub.swapCourse(studentId, oldCourseId, newCourseId, term, department);
        System.out.println(studentId + ": " + response);
    }

    void start() {
        System.out.println(studentId + " and " + newCourseId);
        if (thread == null) {
            thread = new Thread(this, "multi thread swap course");
            thread.start();
        }
    }
}
