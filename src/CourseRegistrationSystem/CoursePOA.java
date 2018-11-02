package CourseRegistrationSystem;


/**
* CourseRegistrationSystem/CoursePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from app.idl
* Thursday, November 1, 2018 at 8:14:02 PM Eastern Daylight Time
*/

public abstract class CoursePOA extends org.omg.PortableServer.Servant
 implements CourseRegistrationSystem.CourseOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("validateAdvisor", new java.lang.Integer (0));
    _methods.put ("validateStudent", new java.lang.Integer (1));
    _methods.put ("addCourse", new java.lang.Integer (2));
    _methods.put ("showCourses", new java.lang.Integer (3));
    _methods.put ("deleteCourse", new java.lang.Integer (4));
    _methods.put ("enrollCourse", new java.lang.Integer (5));
    _methods.put ("displayCourses", new java.lang.Integer (6));
    _methods.put ("dropCourse", new java.lang.Integer (7));
    _methods.put ("getClassSchedule", new java.lang.Integer (8));
    _methods.put ("listCourseAvailability", new java.lang.Integer (9));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CourseRegistrationSystem/Course/validateAdvisor
       {
         String clientId = in.read_string ();
         boolean $result = false;
         $result = this.validateAdvisor (clientId);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // CourseRegistrationSystem/Course/validateStudent
       {
         String clientId = in.read_string ();
         boolean $result = false;
         $result = this.validateStudent (clientId);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // CourseRegistrationSystem/Course/addCourse
       {
         String id = in.read_string ();
         String courseId = in.read_string ();
         String courseName = in.read_string ();
         String term = in.read_string ();
         short seatsAvailable = in.read_short ();
         boolean $result = false;
         $result = this.addCourse (id, courseId, courseName, term, seatsAvailable);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // CourseRegistrationSystem/Course/showCourses
       {
         boolean $result = false;
         $result = this.showCourses ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // CourseRegistrationSystem/Course/deleteCourse
       {
         String id = in.read_string ();
         String courseId = in.read_string ();
         String term = in.read_string ();
         String department = in.read_string ();
         boolean $result = false;
         $result = this.deleteCourse (id, courseId, term, department);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 5:  // CourseRegistrationSystem/Course/enrollCourse
       {
         String id = in.read_string ();
         String term = in.read_string ();
         String department = in.read_string ();
         boolean udpCall = in.read_boolean ();
         String $result = null;
         $result = this.enrollCourse (id, term, department, udpCall);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 6:  // CourseRegistrationSystem/Course/displayCourses
       {
         String term = in.read_string ();
         String $result = null;
         $result = this.displayCourses (term);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 7:  // CourseRegistrationSystem/Course/dropCourse
       {
         String studentId = in.read_string ();
         String courseId = in.read_string ();
         String term = in.read_string ();
         String department = in.read_string ();
         boolean udpCall = in.read_boolean ();
         boolean $result = false;
         $result = this.dropCourse (studentId, courseId, term, department, udpCall);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 8:  // CourseRegistrationSystem/Course/getClassSchedule
       {
         String studentId = in.read_string ();
         String $result = null;
         $result = this.getClassSchedule (studentId);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 9:  // CourseRegistrationSystem/Course/listCourseAvailability
       {
         String id = in.read_string ();
         String term = in.read_string ();
         String department = in.read_string ();
         boolean udpCall = in.read_boolean ();
         String $result = null;
         $result = this.listCourseAvailability (id, term, department, udpCall);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CourseRegistrationSystem/Course:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Course _this() 
  {
    return CourseHelper.narrow(
    super._this_object());
  }

  public Course _this(org.omg.CORBA.ORB orb) 
  {
    return CourseHelper.narrow(
    super._this_object(orb));
  }


} // class CoursePOA
