package CourseRegistrationSystem;

/**
* CourseRegistrationSystem/CourseHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from app.idl
* Sunday, November 4, 2018 at 7:30:27 PM Eastern Standard Time
*/

public final class CourseHolder implements org.omg.CORBA.portable.Streamable
{
  public CourseRegistrationSystem.Course value = null;

  public CourseHolder ()
  {
  }

  public CourseHolder (CourseRegistrationSystem.Course initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CourseRegistrationSystem.CourseHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CourseRegistrationSystem.CourseHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CourseRegistrationSystem.CourseHelper.type ();
  }

}
