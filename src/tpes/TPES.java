/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tpes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author Supreme_Traders
 */




public class TPES {
    
   Connection con;
    Statement st;
    ResultSet rs;



TPES(){

    

     try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver Loaded");

            
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
                                 "databaseName=tpes;" +
                                 "user=sa;" + 
                                 "password=hadiya129;" +
                                 "encrypt=true;" + 
                                 "trustServerCertificate=true;";

            con = DriverManager.getConnection(connectionUrl);
            st = con.createStatement();
            
            System.out.println("CONNECTED TO THE DATABASE");

        } catch (Exception e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
}
public int studentsApp(String id, String fname, String lname, String email, String pno, String pass, String dept){
    
    int status=0;
    String sql="INSERT INTO `students` (`st_id`, `st_fname`, `st_lname`, `st_email`, `st_pno`, `st_pass`, `st_dept`)VALUES ('"+id+"','"+fname+"','"+lname+"','"+email+"','"+pno+"','"+pass+"','"+dept+"')";
    try{
    st.executeUpdate(sql);
    status =1;
    }catch(Exception e){
    System.out.println(e);
    }
    return status;

}
public ResultSet studentDetails(String id, String pass){

    String sql="select*from students where st_id='"+id+"' AND st_pass='"+pass+"'";
    try{
    rs=st.executeQuery(sql);
    }catch(SQLException e){
    System.out.print(e);
    }
    return rs;
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TPES d=new TPES(); // to show the connection
Login n=new Login();
  n.setVisible(true);
    }
    
}
