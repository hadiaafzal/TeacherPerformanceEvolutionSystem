/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tpes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    }catch(SQLException e){
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
public ResultSet adminName(String id){

    String sql="select *from admins where a_id='"+id+"'";
    try{
    rs=st.executeQuery(sql);
    }catch(Exception e){
    System.out.print(e);
    }
    return rs;
}



/*
 
public ResultSet deptfeedback(String dept) {
    // Fixed: Added comma after s.semester and spaces before JOINs
    String sql = "SELECT f.f_id, s.sub_id, f.st_id, f.t_id, f.total_score, s.sub_name, s.course_code, s.semester, d.dept_name " +
                 "FROM feedback f " +
                 "INNER JOIN subjects s ON s.sub_id = f.sub_id " +
                "INNER JOIN departments d ON d.sub_id = s.sub_id " +
                 "WHERE d.dept_name = '" + dept + "'";

    try {
        Statement localSt = con.createStatement(); 
        return localSt.executeQuery(sql);
    } catch (Exception e) {
        System.out.print("Database Error in deptfeedback: " + e);
        return null; // Return null if it fails
    }
}*/


public ResultSet searchstd(String search){
        String sql = "SELECT * FROM students WHERE st_id LIKE '%"+search+"%'  or st_fname like '%"+search+"%' or st_lname like '%"+search+"%' or st_dept like '%"+search+"%' or st_email like '%"+search+"%'  or CAST(st_semester as varchar) like '"+search+"'  or st_cnic LIKE '%"+search+"%' or st_pno LIKE '%"+search+"%'   ";
        try{
            rs = st.executeQuery(sql);
        }catch(Exception e){
            System.out.println(e);
        }
        return rs;
    }


public ResultSet searchtea(String search){
        String sql = "SELECT * FROM teachers WHERE t_id Like '%"+search+"%' or t_fname like '%"+search+"%' or t_lname like '%"+search+"%'or t_email like '%"+search+"%'  or t_pno LIKE '%"+search+"%'   ";
        try{
            rs = st.executeQuery(sql);
        }catch(Exception e){
            System.out.println(e);
        }
        return rs;
    }

public ResultSet stdData(String ID){
        String sql = "select st_id,st_fname,st_lname,st_email,st_pno,st_dept,st_cnic,st_semester from students";
        try{
            rs = st.executeQuery(sql);
        }catch(Exception e){
            System.out.println(e);
        }
        return rs;
    }

public ResultSet allteachers(String ID) {
    String sql = "SELECT  t_fname, t_lname from teachers";
                
      try {
        Statement localSt = con.createStatement(); 
        return localSt.executeQuery(sql);
    } catch (Exception e) {
        System.out.print("Database Error in deptfeedback: " + e);
        return null;
    }
}

public ResultSet teaData(String ID) {
    String sql = "SELECT t_id, t_fname, t_lname, t_email, t_pno  from teachers";
                
    try {
        rs = st.executeQuery(sql);
    } catch (Exception e) {
        System.out.println(e);
    }
    return rs;
}

public ResultSet getCombinedFeedback(String semester, String department) {
    String sql = "SELECT f.f_id, f.st_id, s.sub_id, f.t_id, f.total_score, s.sub_name, s.course_code, s.semester, d.dept_name " +
                 "FROM feedback f " +
                 "INNER JOIN subjects s ON s.sub_id = f.sub_id " +
                 "INNER JOIN departments d ON d.sub_id = s.sub_id " +
                 "WHERE s.semester = '" + semester + "' AND d.dept_name = '" + department + "'"; 

    try {
        Statement localSt = con.createStatement();
        return localSt.executeQuery(sql);
    } catch (Exception e) {
        System.out.println("Database Error in getCombinedFeedback: " + e);
        return null;
    }
}
/*

public ResultSet semfeedback(String sem){
   String sql = "SELECT f.f_id, f.st_id, s.sub_id, f.t_id, f.total_score, s.sub_name, s.course_code, s.semester, d.dept_name " +
             "FROM feedback f " +
             "INNER JOIN subjects s ON s.sub_id = f.sub_id " +
             "INNER JOIN departments d ON d.dept_id = s.dept_id " + 
             "WHERE s.semester = '" + sem + "'";

try{
            Statement localSt = con.createStatement(); 
        return localSt.executeQuery(sql);
        }
catch(Exception e){
        System.out.print("Database Error: " + e);
        } 
       return null;
}
*/

public ResultSet teacherName(String id){

    String sql="select *from teachers where t_id='"+id+"'";
    try{
    rs=st.executeQuery(sql);
    }catch(Exception e){
    System.out.print(e);
    }
    return rs;
}

public ResultSet subjects(int sem, String dept, String id){
 
 String sql = "SELECT  d.dept_name, s.sub_name, s.sub_id, d.t_id, " +
                 "t.t_fname, t.t_lname, s.semester " + 
                 "FROM subjects s " +
                 "INNER JOIN departments d ON s.sub_id = d.sub_id " +
                 "INNER JOIN teachers t ON d.t_id = t.t_id " +
                 "WHERE s.semester = '" + sem + "' " +
                 "AND d.dept_name = '" + dept + "' " +
                 "AND s.sub_id NOT IN (SELECT sub_id FROM feedback WHERE st_id = '" + id + "')";
         try {
        Statement stLocal = con.createStatement();
        return stLocal.executeQuery(sql);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}





public ResultSet teachers(String id){
 
 String sql = "SELECT  s.sub_name, s.sub_id, s.semester " +
                 "FROM teachers t " +
                 "INNER JOIN departments d ON t.t_id = d.t_id " +
                 "INNER JOIN subjects s ON d.sub_id = s.sub_id " +
                 "WHERE t.t_id = '" + id + "' " +
                 "ORDER BY s.semester ASC, s.sub_name ASC";
      
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
   
    }
    return status;

}
public ResultSet SearchFeedback(String semester, String department,String search) {
    String sql = "SELECT f.f_id, f.st_id, s.sub_id, f.t_id, f.total_score, s.sub_name, s.course_code, s.semester, d.dept_name " +
                 "FROM feedback f " +
                 "INNER JOIN subjects s ON s.sub_id = f.sub_id " +
                 "INNER JOIN departments d ON d.sub_id = s.sub_id " +
                 "WHERE (s.semester = '" + semester + "' AND d.dept_name = '" + department + "') "+
                 "AND (CAST(f.f_id as VARCHAR) like '%"+search+"%' OR f.st_id LIKE'%"+search+"%' OR CAST(s.sub_id as VARCHAR) LIKE '%"+search+"%' OR f.t_id like '%"+search+"%' OR CAST(f.total_score as varchar) like '%"+search+"%' OR s.sub_name like '%"+search+"%' OR s.course_code like '%"+search+"%' OR s.semester like '%"+search+"%' OR d.dept_name like '%"+search+"%' )"; 

    try {
        Statement localSt = con.createStatement();
        return localSt.executeQuery(sql);
    } catch (Exception e) {
        System.out.println("Database Error in SearchFeedback: " + e);
        return null;
    }
}
public ResultSet tGraph(String t_id,int sub_id){
 
 String sql = "select AVG(total_score) AS avg_score from feedback " +
"where t_id='"+t_id+"' and sub_id="+sub_id+"";
      
    try {
        Statement stLocal = con.createStatement();
        return stLocal.executeQuery(sql);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
public ResultSet semGraph(int sem){
 
 String sql = "select avg(f.total_score) as avg_score, d.dept_name from feedback f" +
"INNER JOIN subjects s ON s.sub_id = f.sub_id " +
"INNER JOIN departments d ON d.sub_id = s.sub_id " +
"WHERE s.semester = '"+sem+"'" +
"GROUP BY d.dept_name;";
      
    try {
        Statement stLocal = con.createStatement();
        return stLocal.executeQuery(sql);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

/*
select AVG(total_score) from feedback 
where t_id='tid-001' and sub_id=61
*/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        TPES d=new TPES(); // to show the connection
    }
    
}
