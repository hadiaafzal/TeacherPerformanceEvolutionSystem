/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tpes;

import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import java.awt.Font;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import java.util.ArrayList;
import org.jfree.data.general.DefaultPieDataset;


        /**
 *
 * @author Dell
 */
public class tea_graph extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(tea_graph.class.getName());
     private String ID;
    private String teacherId;
    private String TfullName;
    private String FullName;
    
    public tea_graph(){
        initComponents();
    }
    
    public tea_graph(String ID, String aname,String tid, String tfname, String tlname) {
         initComponents();
         a_id.setText(ID);
         fullname.setText(aname);
         t_name.setText(tfname +" "+tlname);
         t_id.setText(tid);
         this.ID=ID;
    this.teacherId = tid;
    this.TfullName = tfname + " " + tlname;
    TPES db=new TPES();
            ResultSet rs = db.adminName(ID);
       ResultSet rs2 =db.allteachers(); 
       ResultSet rs3 =db.teacherSubject(tid);
        ArrayList<String> subjectName = new ArrayList<>();
        ArrayList<Integer> subjectIds = new ArrayList<>();
        ArrayList<Integer> feedbacks=new ArrayList<>();
       int noOfCourses=0;
       
       
       
                
try {
    if (rs.next()) {
        String fname = rs.getString("a_fname");
        String lname = rs.getString("a_lname");
        fullname.setText(fname + " " + lname);
    }
    
    
    
    allteachers.removeAllItems();
       allteachers.addItem(new TeacherItem("", "Select a teacher", ""));

while(rs2.next()){  
    String id = rs2.getString("t_id"); 
    String f = rs2.getString("t_fname");
    String l = rs2.getString("t_lname");
      System.out.println("Adding teacher: " + f); 

    allteachers.addItem(new TeacherItem(id, f, l));
}

    while(rs3.next()){
        
     int id = rs3.getInt("sub_id");
   String name = rs3.getString("sub_name");
   subjectName.add(rs3.getString("sub_name"));
   subjectIds.add(Integer.parseInt(rs3.getString("sub_id")));
   
   
    
// subjects.addItem(new SubjectItem(id, name));
}
    for (int subId : subjectIds) {
   ResultSet   rs4 = db.tGraph(tid, subId);
   noOfCourses+=1;
    if (rs4 != null && rs4.next()) {
        feedbacks.add(Integer.parseInt(rs4.getString("avg_score")));
    }
     }
     int poor = 0, bad = 0, average = 0, good = 0, excellent = 0;
    ResultSet rs5 = db.pieGraph(tid, 0, 100);
    
    if (rs5 != null) {
        while (rs5.next()) {
            // Get the score from the "total_score" column
            int score = rs5.getInt("total_score");

            // Logic for your specific ranges
            if (score < 50) {
                poor++;
            } else if (score >= 50 && score < 57) {
                bad++;
            } else if (score >= 57 && score < 65) {
                average++;
            } else if (score >= 65 && score < 75) {
                good++;
            } else if (score >= 75 && score <= 100) {
                excellent++;
            }
        } 
    }
    
    ResultSet rs6=db.joindate(tid);
        if (rs6.next()) {
        String joindate=rs6.getString("joiningdate");
        joinDate.setText(joindate);
    }
    
    
        System.out.println(excellent);
        loadFeedbackGraph(excellent, good, average, bad, poor);
} catch(Exception e){
            System.out.println(e);
        }
        this.FullName=fullname.getText();
      int percentage=0;
        int Total=0;
        for(int total : feedbacks){
            Total+=total;
        }
        percentage=Total/feedbacks.size();
        String Percentage=String.valueOf(percentage);
        String courseString=String.valueOf(noOfCourses);
        per.setText(Percentage);
        course.setText(courseString);
       
    
        fetchDataAndGenerateGraph(tid); 
    }
private void loadFeedbackGraph(int excellent, int good, int average, int bad, int poor) {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Excellent", excellent);
    dataset.setValue("Good", good);
    dataset.setValue("Average", average);
    dataset.setValue("Bad",bad);
    dataset.setValue("Poor", poor);

    JFreeChart chart = ChartFactory.createPieChart(
            "Student Feedback Rating", dataset, true, true, false
    );
    
    PiePlot plot = (PiePlot) chart.getPlot();
    
    // 1. ADD PERCENTAGES TO LABELS
    plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0} ({2})"));
    
    // 2. STYLING
    plot.setSectionPaint("Excellent", new Color(0, 192, 157));
    plot.setSectionPaint("Good", new Color(255, 111, 97));
    plot.setSectionPaint("Average", new Color(225, 173, 1));
    plot.setSectionPaint("Poor", new Color(211, 47, 47));
    plot.setBackgroundPaint(Color.WHITE);
    plot.setOutlineVisible(false);

    // 3. ADD TO PANEL & FIX SIZE
    ChartPanel chartPanel = new ChartPanel(chart);
    
    // Crucial sizing fix: prevents the chart from pushing the window boundaries
    chartPanel.setPreferredSize(new java.awt.Dimension(studentPieChart.getWidth(), studentPieChart.getHeight()));
    
    studentPieChart.removeAll();          
    studentPieChart.setLayout(new BorderLayout());
    studentPieChart.add(chartPanel, BorderLayout.CENTER);
    studentPieChart.validate();           
    studentPieChart.repaint();
}

       
     /*
   
    public tea_graph(String ID) {
        initComponents();
        a_id.setText(ID);  
        this.ID=ID;
        TPES db=new TPES();
        ResultSet rs = db.adminName(ID);
        ResultSet rs2 =db.allteachers(); 
        int noOfCourses=0;
         
try {
    if (rs.next()) {
        String fname = rs.getString("a_fname");
        String lname = rs.getString("a_lname");
        fullname.setText(fname + " " + lname);
    }
    
    
    
    allteachers.removeAllItems();
    
while(rs2.next()){  
    String id = rs2.getString("t_id"); 
    String f = rs2.getString("t_fname");
    String l = rs2.getString("t_lname");
            System.out.println("Adding teacher: " + f); 

    allteachers.addItem(new TeacherItem(id, f, l));
}
   int poor = 0, bad = 0, average = 0, good = 0, excellent = 0;
    ResultSet rs4 = db.pieGraph(tid, 0, 100);
    
    if (rs4 != null) {
        while (rs4.next()) {
            // Get the score from the "total_score" column
            int score = rs4.getInt("total_score");

            // Logic for your specific ranges
            if (score < 50) {
                poor++;
            } else if (score >= 50 && score < 57) {
                bad++;
            } else if (score >= 57 && score < 65) {
                average++;
            } else if (score >= 65 && score < 75) {
                good++;
            } else if (score >= 75 && score <= 100) {
                excellent++;
            }
        } 
    }
        System.out.println(excellent);
        loadFeedbackGraph(excellent, good, average, bad, poor);
    
     
} catch(Exception e){
            System.out.println(e);
        }
        this.FullName=fullname.getText();
      
    }
    
    
    public tea_graph(String ID,int department, int semester) {
        initComponents();
        a_id.setText(ID);  
        this.ID=ID;
        TPES db=new TPES();
        ResultSet rs = db.adminName(ID);
        sem.setSelectedIndex(semester);
        dept.setSelectedIndex(department);
           ResultSet rs2 =db.allteachers();  
           int noOfCourses=0;

        
         
try {
    if (rs.next()) {
        String fname = rs.getString("a_fname");
        String lname = rs.getString("a_lname");
        fullname.setText(fname + " " + lname);
    }
    
allteachers.removeAllItems();
       allteachers.addItem(new TeacherItem("", "Select a teacher", ""));

while(rs2.next()){  
    
    String id = rs2.getString("t_id"); 
    String f = rs2.getString("t_fname");
    String l = rs2.getString("t_lname");
        System.out.println("Adding teacher: " + f); 

    allteachers.addItem(new TeacherItem(id, f, l));
}
    int poor = 0, bad = 0, average = 0, good = 0, excellent = 0;
    ResultSet rs4 = db.pieGraph(tid, 0, 100);
    
    if (rs4 != null) {
        while (rs4.next()) {
            // Get the score from the "total_score" column
            int score = rs4.getInt("total_score");

            // Logic for your specific ranges
            if (score < 50) {
                poor++;
            } else if (score >= 50 && score < 57) {
                bad++;
            } else if (score >= 57 && score < 65) {
                average++;
            } else if (score >= 65 && score < 75) {
                good++;
            } else if (score >= 75 && score <= 100) {
                excellent++;
            }
        } 
    }
        System.out.println(excellent);
        loadFeedbackGraph(excellent, good, average, bad, poor);
    
} catch(Exception e){
            System.out.println(e);
        }
      
        this.FullName=fullname.getText();
            fetchDataAndGenerateGraph(ID); 

    }
  
    */
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        fullname = new javax.swing.JTextField();
        a_id = new javax.swing.JTextField();
        chartContainer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        semester = new javax.swing.JComboBox<>();
        dept = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        sem = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        allteachers = new javax.swing.JComboBox<>();
        students = new javax.swing.JButton();
        overall = new javax.swing.JButton();
        teachers = new javax.swing.JButton();
        teawise = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        t_name = new javax.swing.JLabel();
        t_id = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        course = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        joinDate = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        per = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        studentPieChart = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 33, 71));

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TPES");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/Home.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/TPESlogo.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/Male User.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 793, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(0, 33, 71));

        fullname.setEditable(false);
        fullname.setBackground(new java.awt.Color(57, 77, 120));
        fullname.setFont(new java.awt.Font("Arial Unicode MS", 3, 14)); // NOI18N
        fullname.setForeground(new java.awt.Color(255, 255, 255));
        fullname.setBorder(null);
        fullname.addActionListener(this::fullnameActionPerformed);

        a_id.setEditable(false);
        a_id.setBackground(new java.awt.Color(57, 77, 120));
        a_id.setFont(new java.awt.Font("Arial Unicode MS", 3, 14)); // NOI18N
        a_id.setForeground(new java.awt.Color(255, 255, 255));
        a_id.setBorder(null);

        chartContainer.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel5.setBackground(new java.awt.Color(0, 153, 204));
        jLabel5.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DEPT WISE FEEDBACK");

        semester.setBackground(new java.awt.Color(204, 204, 204));
        semester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select---", "First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth" }));
        semester.addActionListener(this::semesterActionPerformed);

        dept.setBackground(new java.awt.Color(204, 204, 204));
        dept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select sem---", "CS", "AI", "DS", "SE", "AI", "IT" }));
        dept.addActionListener(this::deptActionPerformed);

        jLabel8.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("SEM WISE GRAPH");

        jLabel10.setBackground(new java.awt.Color(0, 153, 204));
        jLabel10.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("SEM WISE FEEDBACK");

        sem.setBackground(new java.awt.Color(204, 204, 204));
        sem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Sem---", "1", "2", "3", "4", "5", "6", "7", "8" }));
        sem.addActionListener(this::semActionPerformed);

        jLabel11.setBackground(new java.awt.Color(0, 153, 204));
        jLabel11.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("SHOW ALL TEACHERS");

        jLabel12.setBackground(new java.awt.Color(0, 153, 204));
        jLabel12.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("SHOW ALL STUDSENTS");

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Cambria Math", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("TEACHER WISE GRAPH");

        allteachers.setBackground(new java.awt.Color(204, 204, 204));
        allteachers.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        allteachers.addActionListener(this::allteachersActionPerformed);

        students.setBackground(new java.awt.Color(0, 0, 153));
        students.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        students.setForeground(new java.awt.Color(255, 255, 255));
        students.setText("Show");
        students.addActionListener(this::studentsActionPerformed);

        overall.setBackground(new java.awt.Color(0, 0, 153));
        overall.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        overall.setForeground(new java.awt.Color(255, 255, 255));
        overall.setText("Overall");
        overall.addActionListener(this::overallActionPerformed);

        teachers.setBackground(new java.awt.Color(0, 0, 153));
        teachers.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        teachers.setForeground(new java.awt.Color(255, 255, 255));
        teachers.setText("Show");
        teachers.addActionListener(this::teachersActionPerformed);

        teawise.setBackground(new java.awt.Color(0, 0, 153));
        teawise.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        teawise.setForeground(new java.awt.Color(255, 255, 255));
        teawise.setText("Show");
        teawise.addActionListener(this::teawiseActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(allteachers, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(semester, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45))))
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(overall, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(teawise, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(sem, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dept, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(teachers, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(students, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(9, 9, 9)
                .addComponent(sem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(students, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(teachers, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(semester, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(overall, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allteachers, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(teawise, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));
        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        t_name.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        t_name.setText("Ameen Khwaja");
        jPanel10.add(t_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 140, 30));

        t_id.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        t_id.setText("TID-001");
        jPanel10.add(t_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 82, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Teacher:");
        jPanel10.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 68, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/Person.png"))); // NOI18N
        jPanel10.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 40, 40));

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));
        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        course.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel11.add(course, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 40, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setText("No of Courses:");
        jPanel11.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/course.png"))); // NOI18N
        jPanel11.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 60, 50));

        jPanel12.setBackground(new java.awt.Color(204, 255, 204));
        jPanel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        joinDate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        joinDate.setText("12-01-2016");
        jPanel12.add(joinDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 110, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Joinded Date:");
        jPanel12.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/Calendar.png"))); // NOI18N
        jPanel12.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 60, 50));

        jPanel9.setBackground(new java.awt.Color(255, 204, 255));
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        per.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        per.setText("68.96");
        jPanel9.add(per, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 60, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Performance %");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/Increase.png"))); // NOI18N
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 50, 50));

        studentPieChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(chartContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(studentPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(68, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(a_id, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(a_id, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chartContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .addComponent(studentPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1170, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void semesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semesterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_semesterActionPerformed

    private void allteachersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allteachersActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_allteachersActionPerformed

    private void deptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deptActionPerformed
        // TODO add your handling code here:
                    //updateTableData();
                    if(dept.getSelectedIndex()!=0 && sem.getSelectedIndex()!=0){
        int deptIndex=dept.getSelectedIndex();
        int semIndex=sem.getSelectedIndex();
        
        A_home a=new A_home(ID,deptIndex, semIndex);
        a.setVisible(true);
        dispose();
            }

    }//GEN-LAST:event_deptActionPerformed

    private void semActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semActionPerformed
        // TODO add your handling code here:
           // updateTableData(); 
           if(dept.getSelectedIndex()!=0 && sem.getSelectedIndex()!=0){
        int deptIndex=dept.getSelectedIndex();
        int semIndex=sem.getSelectedIndex();
        
        A_home a=new A_home(ID,deptIndex, semIndex);
        a.setVisible(true);
        dispose();
            }
             
    }//GEN-LAST:event_semActionPerformed

    private void studentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentsActionPerformed
        // TODO add your handling code here:
         studentsData h=new studentsData(ID, FullName);
                h.setVisible(true);
                dispose();
        
    }//GEN-LAST:event_studentsActionPerformed

    private void teachersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teachersActionPerformed
        // TODO add your handling code here:
        
         teachersData h=new teachersData(ID, FullName);
                h.setVisible(true);
                dispose();
    }//GEN-LAST:event_teachersActionPerformed

    
    
    
    
   
    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        
    }//GEN-LAST:event_searchActionPerformed


    private void overallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overallActionPerformed
        // TODO add your handling code here:
        semWiseGraph s=new semWiseGraph(ID,FullName,semester.getSelectedIndex());
        s.setVisible(true);
        dispose();
    }//GEN-LAST:event_overallActionPerformed

    private void teawiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teawiseActionPerformed
      
 TeacherItem selected = (TeacherItem) allteachers.getSelectedItem();
    if (selected != null) {
        String tid = selected.getId(); 
        String tfname = selected.getFirstName(); 
        String tlname = selected.getLastName();
                tea_graph h = new tea_graph(ID, FullName,tid, tfname, tlname); 
        h.setVisible(true);
        this.dispose();
        

}}
     private void fetchDataAndGenerateGraph(String targetTeacherId) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    TPES db = new TPES(); 
    
    try {
        // --- CORRECTED SQL QUERY ---
        // We select average score and subject name by joining feedback and subjects tables
        String sql = "SELECT s.sub_name, AVG(f.total_score) AS avg_score " +
                     "FROM feedback f " +
                     "JOIN subjects s ON f.sub_id = s.sub_id " +
                     "WHERE f.t_id = '" + targetTeacherId + "' " +
                     "GROUP BY s.sub_name, f.sub_id"; // Group by subject to get one average per subject
                     
        java.sql.Statement st = db.con.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            // Read directly from the joined result set
            String subName = rs.getString("sub_name");
            double avgScore = rs.getDouble("avg_score");
            
            // Add directly to JFreeChart dataset
            dataset.addValue(avgScore, "Ratings", subName);
        }
        
        // Pass the filled dataset to your chart method
        loadBarChartFromDataset(dataset);
        
    } catch (Exception e) {
        e.printStackTrace();
        // This JOptionPane will now show the REAL error message if one occurs
        javax.swing.JOptionPane.showMessageDialog(this, "Error loading graph: " + e.getMessage());
    }
    }//GEN-LAST:event_teawiseActionPerformed

    private void fullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnameActionPerformed
     
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new tea_graph().setVisible(true));
    }

public class TeacherItem {
    private String id;
    private String firstName;
    private String lastName;

    public TeacherItem(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;    
        this.lastName = lastName;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }
   
    @Override
    public String toString() {
        return getFullName(); 
    }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField a_id;
    private javax.swing.JComboBox<TeacherItem> allteachers;
    private javax.swing.JPanel chartContainer;
    private javax.swing.JLabel course;
    private javax.swing.JComboBox<String> dept;
    private javax.swing.JTextField fullname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel joinDate;
    private javax.swing.JButton overall;
    private javax.swing.JLabel per;
    private javax.swing.JComboBox<String> sem;
    private javax.swing.JComboBox<String> semester;
    private javax.swing.JPanel studentPieChart;
    private javax.swing.JButton students;
    private javax.swing.JLabel t_id;
    private javax.swing.JLabel t_name;
    private javax.swing.JButton teachers;
    private javax.swing.JButton teawise;
    // End of variables declaration//GEN-END:variables


/*
private void loadBarChartFromDataset(DefaultCategoryDataset dataset) {
    // 1. Chart Creation
    JFreeChart barChart = ChartFactory.createBarChart(
        "Performance Summary", 
        null,                   
        "Score / 5.0",          
        dataset,
        PlotOrientation.VERTICAL, 
        false, true, false
    );

    // --- 2. Overall Chart Styling (Teal Background) ---
    barChart.setBackgroundPaint(new Color(0, 188, 188)); 
    barChart.getTitle().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 18));
    barChart.getTitle().setPaint(Color.BLACK); // Set to black for visibility

    // --- 3. Plot Area Styling (Lavender Background) ---
    CategoryPlot plot = barChart.getCategoryPlot();
    plot.setBackgroundPaint(new Color(204, 204, 255)); 
    plot.setOutlineVisible(false);
    plot.setRangeGridlinePaint(Color.WHITE);
    plot.setRangeGridlineStroke(new BasicStroke(1.0f));

    // --- 4. Domain Axis (X-Axis) Styling & Tilt ---
    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setTickLabelFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11));
    domainAxis.setTickLabelPaint(new Color(0, 102, 102));
    
    // TILT: Apply 45-degree angle
    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    domainAxis.setCategoryMargin(0.20); // Adjust space between bars

    // FIX FOR CUT-OFF TEXT: Explicitly reserve space at the bottom
    AxisSpace space = new AxisSpace();
    space.setBottom(180); // Increased significantly to fit long tilted names
    plot.setFixedDomainAxisSpace(space);

    // --- 5. Range Axis (Y-Axis) Styling & Underline ---
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setTickLabelPaint(new Color(0, 102, 102));
    
    // HTML UNDERLINE
    rangeAxis.setLabel("Score");
    rangeAxis.setLabelFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
    rangeAxis.setLabelPaint(new Color(0, 102, 102));
    
    // Set fixed range so bars don't hit the top
    rangeAxis.setRange(0, 70); 

    // --- 6. Bar Renderer Styling ---
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(41, 128, 185)); 
    renderer.setBarPainter(new StandardBarPainter()); 
    renderer.setShadowVisible(false);
    renderer.setMaximumBarWidth(0.05); 

    // --- 7. Chart Panel & Container ---
    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setBackground(new Color(0, 188, 188));
    chartPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

    chartContainer.removeAll();
    chartContainer.setLayout(new BorderLayout());
    chartContainer.add(chartPanel, BorderLayout.CENTER);
    
    chartContainer.revalidate();
    chartContainer.repaint();
}
*/
    private void loadBarChartFromDataset(DefaultCategoryDataset dataset) {

    JFreeChart barChart = ChartFactory.createBarChart(
        "Performance Summary",
        null,
        "Score / 5.0",
        dataset,
        PlotOrientation.VERTICAL,
        false, true, false
    );

    // --- 2. Overall Chart Styling ---
    barChart.setBackgroundPaint(new Color(0, 188, 188));
    barChart.getTitle().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 18));
    barChart.getTitle().setPaint(Color.BLACK);

    // --- 3. Plot Styling ---
    CategoryPlot plot = barChart.getCategoryPlot();
    plot.setBackgroundPaint(new Color(204, 204, 255));
    plot.setOutlineVisible(false);
    plot.setRangeGridlinePaint(Color.WHITE);
    plot.setRangeGridlineStroke(new BasicStroke(1.0f));

    // --- 4. Domain Axis (X-Axis) – STRAIGHT & FULLY VISIBLE ---
    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setTickLabelFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11));
    domainAxis.setTickLabelPaint(new Color(0, 102, 102));

    // ✅ FORCE STRAIGHT LABELS
    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

    // ✅ PREVENT LABEL CUT-OFF
    domainAxis.setMaximumCategoryLabelWidthRatio(1.0f);
    domainAxis.setLowerMargin(0.02);
    domainAxis.setUpperMargin(0.02);
    domainAxis.setCategoryMargin(0.15);

    // Reduce reserved space (tilt removed)
    AxisSpace space = new AxisSpace();
    space.setBottom(60);
    plot.setFixedDomainAxisSpace(space);

    // --- 5. Range Axis ---
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setTickLabelPaint(new Color(0, 102, 102));
    rangeAxis.setLabel("Score");
    rangeAxis.setLabelFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
    rangeAxis.setLabelPaint(new Color(0, 102, 102));
    rangeAxis.setRange(0, 70);

    // --- 6. Renderer ---
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(41, 128, 185));
    renderer.setBarPainter(new StandardBarPainter());
    renderer.setShadowVisible(false);
    renderer.setMaximumBarWidth(0.05);

    // --- 7. Chart Panel ---
    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setBackground(new Color(0, 188, 188));
    chartPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

    chartContainer.removeAll();
    chartContainer.setLayout(new BorderLayout());
    chartContainer.add(chartPanel, BorderLayout.CENTER);
    chartContainer.revalidate();
    chartContainer.repaint();
}

}
