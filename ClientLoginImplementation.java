
package Student_Managment_System;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientLoginImplementation extends UnicastRemoteObject implements LoginInterface {

    Connection con;
    PreparedStatement ps;

    ClientLoginImplementation() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/studentmanagment_db?user=root");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean userLogin(String userName, String userPassword) throws RemoteException {
        try {
            ps = con.prepareStatement("select * from users where userName=? and password=?");
            ps.setString(1, userName);
            ps.setString(2, userPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientLoginImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
    }

}
