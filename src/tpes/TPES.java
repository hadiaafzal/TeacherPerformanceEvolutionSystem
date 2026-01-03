/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tpes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
public int studentsApp(String id, String fname, String lname, String email, String pno, String pass, String dept, int sem, String cnic){
    
    int status=0;
    String sql="INSERT INTO students (st_id, st_fname, st_lname, st_email, st_pno, st_pass, st_dept, st_semester, st_cnic)VALUES ('"+id+"','"+fname+"','"+lname+"','"+email+"','"+pno+"','"+pass+"','"+dept+"','"+sem+"','"+cnic+"')";
    try{
    st.executeUpdate(sql);
    status =1;
    }catch(Exception e){
    System.out.println(e);
    }
    return status;

}

public ResultSet studentDetails(String id, String pass){

    String sql="select * from students where st_id= '"+id+"'  AND st_pass= '"+pass+"'  ";
    try{
    rs=st.executeQuery(sql);
    }catch(Exception e){
    System.out.print(e);
    }
    return rs;
}
public ResultSet teacherDetails(String id, String pass){

    String sql="select*from teachers where t_id='"+id+"' AND t_pass='"+pass+"'";
    try{

    rs=st.executeQuery(sql);
    }catch(Exception e){
    System.out.print(e);
    }
    return rs;
}
/**/
public ResultSet adminDetails(String id, String pass){

    String sql="select*from admins where a_id='"+id+"' AND a_pass='"+pass+"'";
    try{

    rs=st.executeQuery(sql);
    }catch(Exception e){
    System.out.print(e);
    }
    return rs;
}
public ResultSet studentName(String id){

    String sql="select *from students where st_id='"+id+"'";
    try{
    rs=st.executeQuery(sql);
    }catch(Exception e){
    System.out.print(e);
    }
    return rs;
}
public ResultSet teacher(int sem, String dept, String id){


 
 String sql = "SELECT d.dept_name, s.sub_name, s.sub_id, d.t_id, " +
                 "t.t_fname, t.t_lname, s.semester " + 
                 "FROM subjects s " +
                 "INNER JOIN departments d ON s.sub_id = d.sub_id " +
                 "INNER JOIN teachers t ON d.t_id = t.t_id " +
                 "WHERE s.semester = '" + sem + "' " +
                 "AND d.dept_name = '" + dept + "' " +
                 "AND s.sub_id NOT IN (SELECT sub_id FROM feedback WHERE st_id = '" + id + "')";
        
 
  /*String sql="SELECT teachers.*,subjects.* FROM subjects " +
"INNER JOIN teachers ON subjects.t_id = teachers.t_id" +
"             WHERE subjects.semester = '"+sem+"'" +
"             AND subjects.dept = '"+dept+"'" +
"             AND subjects.sub_id  NOT IN (" +
"                 SELECT sub_id FROM feedback WHERE st_id = '"+id+"');";
*/
    try {
        Statement stLocal = con.createStatement();
        return stLocal.executeQuery(sql);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
public int feedback(String id, int sub_id, String t_id, int q1, int q2,int q3,int q4,int q5,int q6,int q7,int q8,int q9,int q10,int total){
    
    int status=0;
    
    String sql="INSERT INTO feedback(st_id,sub_id ,t_id ,q1 ,q2,q3,q4,q5,q6,q7,q8,q9,q10,total_score) " +
"   VALUES('"+id+"',"+sub_id+",'"+t_id+"',"+q1+","+q2+","+q3+","+q4+","+q5+","+q6+","+q7+","+q8+","+q9+","+q10+","+total+");";
    try{
    st.executeUpdate(sql);
    status =1;
    }catch(Exception e){
    System.out.println(e);
    }
    return status;

}
/*INSERT INTO [dbo].[feedback]
           ([st_id]
           ,[sub_id]
           ,[t_id]
           ,[q1]
           ,[q2]
           ,[q3]
           ,[q4]
           ,[q5]
           ,[q6]
           ,[q7]
           ,[q8]
           ,[q9]
           ,[q10]
           ,[total_score])
     VALUES
           ()*/


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TPES d=new TPES(); // to show the connection
    }
    
}
