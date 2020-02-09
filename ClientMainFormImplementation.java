
package Student_Managment_System;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class ClientMainFormImplementation extends UnicastRemoteObject implements ClientMainFormInterface {

    Connection con;
    PreparedStatement ps;
    //private String balance = "";

    ClientMainFormImplementation() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/studentmanagment_db?user=root");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public class Student implements Serializable {

        int id;
        String studentId;
        String fname;
        String mname;
        String coursename;
        String Grade;

        public Student(int id, String studId) {
            this.id = id;
            this.studentId = studentId;

        }

        public Student(int id, String studId, String firstname, String middl_ename, String course_name, String grade) {
            this.id = id;
            this.studentId = studentId;
            this.fname = firstname;
            this.mname = middl_ename;
            this.coursename = course_name;
            this.Grade = grade;
        }

        public String toString() {
            return studentId;
        }
    }

    public class Course implements Serializable {

        int id;
        String courseName;

        public Course(int id, String courseName) {
            this.id = id;
            this.courseName = courseName;
        }

        public String toString() {
            return courseName;
        }
    }

    public String GradeCalculator(double grade) throws RemoteException {
        String result = "";
        if (grade >= 85 && grade <= 100) {
            result = "A";
        } else if (grade >= 75) {
            result = "B";
        } else if (grade >= 65) {
            result = "C";
        } else if (grade >= 50) {
            result = "D";
        } else if (grade < 50 && grade > 0) {
            result = "F";
        }
        return result;
    }

    @Override
    public List<Student> loadStudent() throws RemoteException {
        List<Student> list = new ArrayList<Student>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select id,stud_id from student ");

            while (rs.next()) {

                int id = rs.getInt(1);
                String studId = rs.getString(2);
                Student cu = new Student(id, studId);
                cu.id = id;
                cu.studentId = studId;
                list.add(cu);
            }
        } catch (Exception ex) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return list;
    }

    @Override
    public List<Student> loadStudentGradeReport() throws RemoteException {
        List<Student> list = new ArrayList<Student>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select s.id,s.stud_id,s.first_name,s.middle_name,c.course_name,sc.Grade from student s, score sc, course c where  s.stud_id=sc.stud_id and c.course_id=sc.course_id ");

            while (rs.next()) {

                int id = rs.getInt(1);
                String studId = rs.getString(2);
                String firstname = rs.getString(3);
                String middl_ename = rs.getString(4);
                String course_name = rs.getString(5);
                String grade = rs.getString(6);
                Student cu = new Student(id, studId, firstname, middl_ename, course_name, grade);
                cu.id = id;
                cu.studentId = studId;
                cu.fname = firstname;
                cu.mname = middl_ename;
                cu.coursename = course_name;
                cu.Grade = grade;
                list.add(cu);
            }
        } catch (Exception ex) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return list;
    }

    @Override
    public List<Course> loadCourse() throws RemoteException {
        List<Course> list = new ArrayList<Course>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from course");

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(3);
                Course br = new Course(id, name);
                br.id = id;
                br.courseName = name;
                list.add(br);
            }
        } catch (Exception ex) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return list;
    }

    @Override
    public boolean addUser(String userName, String userPassword) throws RemoteException {
        try {
            Statement st = con.createStatement();
            ps = con.prepareStatement("insert into users values(?,?,?)");
            ps.setInt(1, 0);
            ps.setString(2, userName);
            ps.setString(3, userPassword);

            int i = ps.executeUpdate();

            if (i != 0) {
                JOptionPane.showMessageDialog(null, "Successfully Inserted", "Message", JOptionPane.INFORMATION_MESSAGE);
                // System.out.println("Inserted");
            } else {
                JOptionPane.showMessageDialog(null, "Not Successfully Inserted", "Message", JOptionPane.INFORMATION_MESSAGE);

            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addCourse(String courseId, String courseName, int courseHours) throws RemoteException {
        try {
            Statement st = con.createStatement();
            ps = con.prepareStatement("insert into course values(?,?,?,?)");
            ps.setInt(1, 0);
            ps.setString(2, courseId);
            ps.setString(3, courseName);
            ps.setInt(4, courseHours);
            int i = ps.executeUpdate();

            if (i != 0) {
                JOptionPane.showMessageDialog(null, "Successfully Inserted", "Message", JOptionPane.INFORMATION_MESSAGE);
                // System.out.println("Inserted");
            } else {
                JOptionPane.showMessageDialog(null, "Not Successfully Inserted", "Message", JOptionPane.INFORMATION_MESSAGE);

            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;//To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public String getCourseId() throws RemoteException {
        String custId;
        try {
            Statement st = con.createStatement();
            ResultSet rsu = st.executeQuery("select count(*) from course");
            int uid = 1;
            if (rsu.next()) {
                uid = rsu.getInt(1) + 1;
            }
            custId = "CorseID-" + uid;
            //mf.txt_customer.setText("Cust-" + uid);
            rsu.close();

            return custId;
        } catch (Exception ex) {
            return "No";// Logger.getLogger(MainFormImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean addStudent(String studentId, String Fname, String Mname, String gender, String city, int mobilenumber, String username, String password) throws RemoteException {
        try {
            Statement st = con.createStatement();
            ps = con.prepareStatement("insert into student values(?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, 0);
            ps.setString(2, studentId);
            ps.setString(3, Fname);
            ps.setString(4, Mname);
            ps.setString(5, gender);
            ps.setString(6, city);
            ps.setInt(7, mobilenumber);
            ps.setString(8, username);
            ps.setString(9, password);
            int i = ps.executeUpdate();

            if (i != 0) {
                JOptionPane.showMessageDialog(null, "Successfully Inserted", "Message", JOptionPane.INFORMATION_MESSAGE);
                // System.out.println("Inserted");
            } else {
                JOptionPane.showMessageDialog(null, "Not Successfully Inserted", "Message", JOptionPane.INFORMATION_MESSAGE);

            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;//To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean addScore(String StudId, String courseId, int result) throws RemoteException {
        String grade;
        try {
            Statement st = con.createStatement();

            ps = con.prepareStatement("select course_id from course where course_name=?");
            ps.setString(1, courseId);
            ResultSet rsc = ps.executeQuery();
            String id = "";
            if (rsc.next()) {
                id = rsc.getString(1);
            }
            rsc.close();
            ps = con.prepareStatement("insert into score values(?,?,?,?)");
            grade = GradeCalculator(result);
            ps.setString(1, StudId);
            ps.setString(2, id);
            ps.setInt(3, result);
            ps.setString(4, grade);
            int i = ps.executeUpdate();

            if (i != 0) {
                JOptionPane.showMessageDialog(null, "Successfully Inserted", "Message", JOptionPane.INFORMATION_MESSAGE);
                // System.out.println("Inserted");
            } else {
                JOptionPane.showMessageDialog(null, "Not Successfully Inserted", "Message", JOptionPane.INFORMATION_MESSAGE);

            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getStudentId() throws RemoteException {
        String custId;
        try {
            Statement st = con.createStatement();
            ResultSet rsu = st.executeQuery("select count(*) from student");
            int uid = 1;
            if (rsu.next()) {
                uid = rsu.getInt(1) + 1;
            }
            custId = "StudID-" + uid;
            //mf.txt_customer.setText("Cust-" + uid);
            rsu.close();

            return custId;
        } catch (Exception ex) {
            return "No";// Logger.getLogger(MainFormImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
