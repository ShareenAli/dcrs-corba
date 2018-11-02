package CourseRegistrationSystem;


/**
* CourseRegistrationSystem/_CourseStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from app.idl
* Thursday, November 1, 2018 at 9:02:28 PM Eastern Daylight Time
*/

public class _CourseStub extends org.omg.CORBA.portable.ObjectImpl implements CourseRegistrationSystem.Course
{

  public boolean validateAdvisor (String clientId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("validateAdvisor", true);
                $out.write_string (clientId);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return validateAdvisor (clientId        );
            } finally {
                _releaseReply ($in);
            }
  } // validateAdvisor

  public boolean validateStudent (String clientId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("validateStudent", true);
                $out.write_string (clientId);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return validateStudent (clientId        );
            } finally {
                _releaseReply ($in);
            }
  } // validateStudent

  public boolean addCourse (String id, String courseId, String courseName, String term, short seatsAvailable)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addCourse", true);
                $out.write_string (id);
                $out.write_string (courseId);
                $out.write_string (courseName);
                $out.write_string (term);
                $out.write_short (seatsAvailable);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addCourse (id, courseId, courseName, term, seatsAvailable        );
            } finally {
                _releaseReply ($in);
            }
  } // addCourse

  public void showCourses ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("showCourses", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                showCourses (        );
            } finally {
                _releaseReply ($in);
            }
  } // showCourses

  public boolean deleteCourse (String id, String courseId, String term, String department)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("deleteCourse", true);
                $out.write_string (id);
                $out.write_string (courseId);
                $out.write_string (term);
                $out.write_string (department);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return deleteCourse (id, courseId, term, department        );
            } finally {
                _releaseReply ($in);
            }
  } // deleteCourse

  public String enrollCourse (String id, String term, String department, boolean udpCall)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("enrollCourse", true);
                $out.write_string (id);
                $out.write_string (term);
                $out.write_string (department);
                $out.write_boolean (udpCall);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return enrollCourse (id, term, department, udpCall        );
            } finally {
                _releaseReply ($in);
            }
  } // enrollCourse

  public String displayCourses (String term)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("displayCourses", true);
                $out.write_string (term);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return displayCourses (term        );
            } finally {
                _releaseReply ($in);
            }
  } // displayCourses

  public boolean dropCourse (String studentId, String courseId, String term, String department, boolean udpCall)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("dropCourse", true);
                $out.write_string (studentId);
                $out.write_string (courseId);
                $out.write_string (term);
                $out.write_string (department);
                $out.write_boolean (udpCall);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return dropCourse (studentId, courseId, term, department, udpCall        );
            } finally {
                _releaseReply ($in);
            }
  } // dropCourse

  public String getClassSchedule (String studentId)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getClassSchedule", true);
                $out.write_string (studentId);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getClassSchedule (studentId        );
            } finally {
                _releaseReply ($in);
            }
  } // getClassSchedule

  public String listCourseAvailability (String id, String term, String department, boolean udpCall)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("listCourseAvailability", true);
                $out.write_string (id);
                $out.write_string (term);
                $out.write_string (department);
                $out.write_boolean (udpCall);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return listCourseAvailability (id, term, department, udpCall        );
            } finally {
                _releaseReply ($in);
            }
  } // listCourseAvailability

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CourseRegistrationSystem/Course:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _CourseStub
