
package Student_Managment_System;

import Student_Managment_System.ClientMainFormImplementation.Course;
import Student_Managment_System.ClientMainFormImplementation.Student;
import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientMainFormInterface extends Remote {

    //Add an object abstract method
    public boolean addUser(String userName, String userPassword) throws RemoteException;

    public boolean addCourse(String courseId, String courseName, int courseHours) throws RemoteException;

    public boolean addStudent(String studentId, String Fname,String Mname,String gender,String city, int mobilenumber,String username,String password) throws RemoteException;

    public boolean addScore(String StudId, String courseId, int result) throws RemoteException;

    public String getCourseId() throws RemoteException;
    public String getStudentId() throws RemoteException;
    
        //load list of object name abstract method
    public List<Student> loadStudent() throws RemoteException;
    public List<Student> loadStudentGradeReport() throws RemoteException;

    public List<Course> loadCourse() throws RemoteException;
}
