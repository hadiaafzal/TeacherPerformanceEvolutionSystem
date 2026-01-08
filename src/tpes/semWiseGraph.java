/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tpes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel; // To manage the table's data
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Dell
 */
public class semWiseGraph extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(semWiseGraph.class.getName());
private String ID;
    private String FullName;
    private ArrayList department;
    private ArrayList feedbacks;
    /**
     * Creates new form semWiseGraph
     */
    public semWiseGraph() {
        initComponents();
    }
    public semWiseGraph(String ID,String FullName,int sem) {
        initComponents();
        ArrayList<Integer> feedbacks=new ArrayList<>();
        ArrayList<String> department=new ArrayList<>();
        fullname.setText(FullName);
        this.ID = ID;
        this.FullName = FullName;
        semester.setSelectedIndex(sem);
        
         a_id.setText(ID);
        TPES db=new TPES();
        //ResultSet rs = db.adminName(ID);
        ResultSet rs2 =db.allteachers();
        
         
try {
    
    
    
    
   allteachers.removeAllItems();
while(rs2.next()){  
    String id = rs2.getString("t_id"); // Get ID from database
    String f = rs2.getString("t_fname");
    String l = rs2.getString("t_lname");
    allteachers.addItem(new TeacherItem(id, f, l));
}
    if(semester.getSelectedIndex()!=0){ 
    ResultSet rs3 = db.semGraph(sem);
    while (rs3.next()) {
        feedbacks.add(rs3.getInt("avg_score"));
        department.add(rs3.getString("dept_name"));
    }}

     
} catch(Exception e){
            System.out.println(e);
        }
      this.department=department;
      this.feedbacks=feedbacks;
      LoadLineChart(department,feedbacks);
      }
    
    
    public semWiseGraph(String ID,int department, int semester) {
        initComponents();
        a_id.setText(ID);  
        this.ID=ID;
        TPES db=new TPES();
        ResultSet rs = db.adminName(ID);
        sem.setSelectedIndex(semester);
        dept.setSelectedIndex(department);
        
         
try {
    if (rs.next()) {
        String fname = rs.getString("a_fname");
        String lname = rs.getString("a_lname");
        fullname.setText(fname + " " + lname);
    }
    
} catch(Exception e){
            System.out.println(e);
        }
      
        this.FullName=fullname.getText();
    }
   
  private void LoadLineChart(ArrayList<String> department, ArrayList<Integer> feedbacks){
       // 1. Create Data (Using semesters for a trend look)
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (int i = 0; i < department.size(); i++) {
        dataset.addValue(feedbacks.get(i), "Ratings", department.get(i));
    }

    // 2. Create the Line Chart
    JFreeChart lineChart = ChartFactory.createLineChart(
        "Teacher Performance Trend", 
        "Semester", 
        "Marks", 
        dataset,
        PlotOrientation.VERTICAL,
        true, true, false
    );

    // 3. Modern Styling for the Line
    CategoryPlot plot = lineChart.getCategoryPlot();
    plot.setBackgroundPaint(Color.WHITE);
    plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
rangeAxis.setRange(0.0, 100.0);
    // Customizing the line (Thick and Red)
    org.jfree.chart.renderer.category.LineAndShapeRenderer renderer = 
        new org.jfree.chart.renderer.category.LineAndShapeRenderer();
    plot.setRenderer(renderer);
    renderer.setSeriesStroke(0, new java.awt.BasicStroke(3.0f)); // Thickness
    renderer.setSeriesPaint(0, new Color(231, 76, 60));         // Modern Red

    // 4. DISPLAY THE LINE CHART
    // Change "barChart" to "lineChart" here
    ChartPanel chartPanel = new ChartPanel(lineChart); 
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 350));
    
    chartContainer.removeAll();
    chartContainer.setLayout(new BorderLayout());
    chartContainer.add(chartPanel, BorderLayout.CENTER);
    chartContainer.validate();
    chartContainer.repaint();
  }
    /*
private void updateTableData() {
    String selectedSem = sem.getSelectedItem().toString();
    String selectedDept = dept.getSelectedItem().toString(); 

    DefaultTableModel tb = (DefaultTableModel) records.getModel(); 
    tb.setRowCount(0); 

    ResultSet combinedRs = null; 

    try {
        TPES db = new TPES();
        
        combinedRs = db.getCombinedFeedback(selectedSem, selectedDept); 

        while(combinedRs != null && combinedRs.next()) {
            tb.addRow(new String[]{
                combinedRs.getString("f_id"), combinedRs.getString("st_id"), combinedRs.getString("sub_id"),
                combinedRs.getString("t_id"), combinedRs.getString("total_score"), combinedRs.getString("sub_name"),
                combinedRs.getString("course_code"), combinedRs.getString("semester"), combinedRs.getString("dept_name")
            });
        }
        
    } catch (Exception e) {
        System.out.print("Database Error: " + e);
    } finally {
         // 5. Close the ResultSet safely in a finally block
         try { if (combinedRs != null) combinedRs.close(); } catch (Exception e) {}
    }
    tb.fireTableDataChanged(); 
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
        fullname = new javax.swing.JTextField();
        a_id = new javax.swing.JTextField();
        chartContainer = new javax.swing.JPanel();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 589, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel2)))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 110));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(overall, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teawise, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        fullname.setEditable(false);
        fullname.setBackground(new java.awt.Color(57, 77, 120));
        fullname.setFont(new java.awt.Font("Arial Unicode MS", 3, 14)); // NOI18N
        fullname.setForeground(new java.awt.Color(255, 255, 255));
        fullname.setBorder(null);
        fullname.addActionListener(this::fullnameActionPerformed);

        a_id.setBackground(new java.awt.Color(57, 77, 120));
        a_id.setFont(new java.awt.Font("Arial Unicode MS", 3, 14)); // NOI18N
        a_id.setForeground(new java.awt.Color(255, 255, 255));
        a_id.setBorder(null);

        chartContainer.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(chartContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(a_id, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(fullname))
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(a_id, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chartContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1000, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void fullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnameActionPerformed

    private void semesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semesterActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_semesterActionPerformed

    private void allteachersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allteachersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_allteachersActionPerformed

    private void deptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deptActionPerformed
        // TODO add your handling code here:
                  //  updateTableData(); 
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
          //  updateTableData(); 
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
                tea_graph h = new tea_graph(tid, tfname, tlname); 
        h.setVisible(true);
        this.dispose();
    }
    }//GEN-LAST:event_teawiseActionPerformed
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
     }

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
        java.awt.EventQueue.invokeLater(() -> new semWiseGraph().setVisible(true));
    }

    
public class TeacherItem {
    private String id;
    private String firstName;
    private String lastName;

    // Constructor
    public TeacherItem(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;    
        this.lastName = lastName;
    }

    // Getters
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    // Setters (Optional: add if you need to modify the data)
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

   
     @Override
    public String toString() {
        return firstName + " " + lastName; // This is what will show in the combo box
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField a_id;
    private javax.swing.JComboBox<TeacherItem> allteachers;
    private javax.swing.JPanel chartContainer;
    private javax.swing.JComboBox<String> dept;
    private javax.swing.JTextField fullname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JButton overall;
    private javax.swing.JComboBox<String> sem;
    private javax.swing.JComboBox<String> semester;
    private javax.swing.JButton students;
    private javax.swing.JButton teachers;
    private javax.swing.JButton teawise;
    // End of variables declaration//GEN-END:variables



    private void loadBarChartFromDataset(DefaultCategoryDataset dataset) {
    JFreeChart barChart = ChartFactory.createBarChart(
        "Performance Summary", 
        null,                  
        "Score / 5.0",         
        dataset,
        PlotOrientation.VERTICAL, 
        false, true, false
    );

    CategoryPlot plot = barChart.getCategoryPlot();
    plot.setBackgroundPaint(Color.WHITE);
    plot.setOutlineVisible(false);
    plot.setRangeGridlinePaint(new Color(230, 230, 230));
    plot.setRangeGridlineStroke(new BasicStroke(1.0f));

  
    CategoryAxis domainAxis = plot.getDomainAxis();
    java.awt.Font subjectFont = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14);
    domainAxis.setTickLabelFont(subjectFont);
    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    domainAxis.setMaximumCategoryLabelWidthRatio(2.0f);
    domainAxis.setCategoryMargin(0.50); 

    
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setMaximumBarWidth(0.05);
    renderer.setSeriesPaint(0, new Color(41, 128, 185)); 
    renderer.setBarPainter(new StandardBarPainter()); 
    renderer.setShadowVisible(false);
    renderer.setItemMargin(0.0);

  
    barChart.getTitle().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 18));
    barChart.setBackgroundPaint(Color.WHITE);

    
    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
    chartPanel.setBackground(Color.WHITE);

    chartContainer.removeAll();
    chartContainer.setLayout(new java.awt.BorderLayout());
    chartContainer.add(chartPanel, java.awt.BorderLayout.CENTER);
    chartContainer.validate();
    chartContainer.repaint(); 
}

}
