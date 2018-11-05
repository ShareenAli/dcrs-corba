package CourseRegistrationSystem;


/**
* CourseRegistrationSystem/CourseHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from app.idl
* Sunday, November 4, 2018 at 7:30:27 PM Eastern Standard Time
*/

abstract public class CourseHelper
{
  private static String  _id = "IDL:CourseRegistrationSystem/Course:1.0";

  public static void insert (org.omg.CORBA.Any a, CourseRegistrationSystem.Course that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static CourseRegistrationSystem.Course extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (CourseRegistrationSystem.CourseHelper.id (), "Course");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static CourseRegistrationSystem.Course read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_CourseStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, CourseRegistrationSystem.Course value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static CourseRegistrationSystem.Course narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CourseRegistrationSystem.Course)
      return (CourseRegistrationSystem.Course)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CourseRegistrationSystem._CourseStub stub = new CourseRegistrationSystem._CourseStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static CourseRegistrationSystem.Course unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof CourseRegistrationSystem.Course)
      return (CourseRegistrationSystem.Course)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      CourseRegistrationSystem._CourseStub stub = new CourseRegistrationSystem._CourseStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
