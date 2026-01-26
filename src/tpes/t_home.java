/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tpes;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.AxisLocation;


import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import java.awt.Font; 
import org.jfree.ui.RectangleEdge;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Dell
 */
public class t_home extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(t_home.class.getName());
  private String ID;
    private String FullName;
    private ArrayList subjectName;
    private ArrayList feedbacks;

    /**
     * Creates new form t_home
     */
    public t_home() {
        initComponents();
    }
     public t_home(String ID) {
        initComponents();
        t_id.setText(ID);  
        this.ID=ID;
        this.FullName=fullname.getText();
        TPES db=new TPES();
        ResultSet rs = db.teacherName(ID);
        ResultSet rs2 =db.teacherSubject(ID);
        ArrayList<String> subjectName = new ArrayList<>();
        ArrayList<Integer> subjectIds = new ArrayList<>();
        ArrayList<Integer> feedbacks=new ArrayList<>();
       
       int noOfCourses=0;
    
     
     
try {
    if (rs.next()) {
        String fname = rs.getString("t_fname");
        String lname = rs.getString("t_lname");
        String jDate = rs.getString("joiningdate");

        fullname.setText(fname + " " + lname);
        joinDate.setText(jDate);
    }

   subjects.removeAllItems();
     while(rs2.next()){
        
     int id = rs2.getInt("sub_id");
   String name = rs2.getString("sub_name");
    subjects.addItem(new SubjectItem(id, name));
   subjectName.add(rs2.getString("sub_name"));
   subjectIds.add(Integer.parseInt(rs2.getString("sub_id")));
   noOfCourses+=1;
   
    
// subjects.addItem(new SubjectItem(id, name));
}
         
     for (int subId : subjectIds) {
   ResultSet   rs3 = db.tGraph(ID, subId);
    if (rs3 != null && rs3.next()) {
        feedbacks.add(Integer.parseInt(rs3.getString("avg_score")));
    }
     }
    int poor = 0, bad = 0, average = 0, good = 0, excellent = 0;
    ResultSet rs4 = db.pieGraph(ID, 0, 100);
    
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
        this.subjectName=subjectName;
        this.feedbacks=feedbacks;
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
       
        
    }
     
private void loadBarChart(ArrayList<String> subjects, ArrayList<Integer> feedbacks) {
    // 1. Data Population with Notification Suppression for Efficiency
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.setNotify(false); // Prevents redundant chart updates during loop
    int limit = Math.min(subjects.size(), feedbacks.size());
    for (int i = 0; i < limit; i++) {
        dataset.addValue(feedbacks.get(i), "Ratings", subjects.get(i));
    }
    dataset.setNotify(true); // Triggers a single redraw after data is ready

    // 2. Chart Creation
    JFreeChart barChart = ChartFactory.createBarChart(
        "Performance Summary", null, "Score / 5.0", dataset, 
        PlotOrientation.VERTICAL, false, true, false
    );

    // --- 3. Overall Chart Styling ---
    barChart.setBackgroundPaint(new Color(0, 188, 188)); // Teal background
    barChart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
    barChart.getTitle().setPaint(Color.WHITE);

    // --- 4. Plot Area Styling ---
    CategoryPlot plot = barChart.getCategoryPlot();
    plot.setBackgroundPaint(new Color(204, 204, 255)); // Lavender inner area
    plot.setOutlineVisible(false);
    plot.setRangeGridlinePaint(Color.WHITE);
    plot.setRangeGridlineStroke(new BasicStroke(1.0f));

    // --- 5. Domain Axis (X-Axis) Styling ---
    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 14));
    domainAxis.setTickLabelPaint(new Color(0, 102, 102));
    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); //
    domainAxis.setCategoryMargin(0.35); // Gap between categories

    // Automatic space calculation (replaces fixed setBottom for responsiveness)
    plot.setFixedDomainAxisSpace(null); 

    // --- 6. Range Axis (Y-Axis) Styling & Underlining ---
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setTickLabelPaint(new Color(0, 102, 102));
    
    // Formatting the label with HTML for a true underline
    //rangeAxis.setLabel("<html><u>Score / 5.0</u></html>");
    //rangeAxis.setLabelFont(new Font("SansSerif", Font.BOLD, 12));
    //rangeAxis.setLabelPaint(new Color(0, 102, 102));

    // --- 7. Bar Renderer Styling ---
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setSeriesPaint(0, new Color(41, 128, 185)); // Professional blue
    renderer.setBarPainter(new StandardBarPainter()); // Removes glossy gradient
    renderer.setShadowVisible(false);
    renderer.setMaximumBarWidth(0.1); // Ensures bars don't get too thick

    // --- 8. Panel Integration & Transparency ---
    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setOpaque(false); // Allows the chart background to be visible
    chartPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

    // UI Thread Safety for 2026 environments
    chartContainer.removeAll();
    chartContainer.setLayout(new BorderLayout());
    chartContainer.add(chartPanel, BorderLayout.CENTER);
    chartContainer.revalidate();
    chartContainer.repaint();
}
  

/*
private void loadFeedbackGraph() {
    // 1. Create dataset and chart (same code as above)
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Excellent", 45);
    dataset.setValue("Good", 35);
    dataset.setValue("Average", 15);
    dataset.setValue("Poor", 5);

    JFreeChart chart = ChartFactory.createPieChart(
            "Student Feedback Rating", dataset, true, true, false
    );
    
    // (Add the styling code from the previous step here if you want colors)
    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setSectionPaint("Excellent", new Color(0, 192, 157));
    plot.setSectionPaint("Good", new Color(255, 111, 97));
    plot.setSectionPaint("Average", new Color(225, 173, 1));
    plot.setSectionPaint("Poor", new Color(211, 47, 47));
    plot.setBackgroundPaint(Color.WHITE);

    // 2. Add to your existing panel
    ChartPanel chartPanel = new ChartPanel(chart);

// --- ADD THIS LINE ---
chartPanel.setPreferredSize(new java.awt.Dimension(200, 200)); 
// ---------------------

studentPieChart.removeAll();          
studentPieChart.setLayout(new java.awt.BorderLayout()); // Ensure BorderLayout is used
studentPieChart.add(chartPanel, java.awt.BorderLayout.CENTER);
studentPieChart.validate();  // Use validate() instead of revalidate() for dynamic swing updates
studentPieChart.repaint();
}
*/

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

     public class SubjectItem {
    private int subId;
    private String subName;

    public SubjectItem(int id, String name) {
        this.subId = id;
        this.subName = name;
    }

    public int getSubId() { return subId; }

    @Override
    public String toString() {
        return subName; 
    }
}
     
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
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        show = new javax.swing.JButton();
        subjects = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        chartContainer = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        fullname = new javax.swing.JLabel();
        t_id = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        course = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        joinDate = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 664, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(29, 29, 29)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel2)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 110));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Show a Graph");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );

        show.setBackground(new java.awt.Color(51, 0, 153));
        show.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        show.setForeground(new java.awt.Color(255, 255, 255));
        show.setText("SHOW");
        show.addActionListener(this::showActionPerformed);

        subjects.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("My Subjects");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(subjects, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(show, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 28, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(show, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subjects, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(276, Short.MAX_VALUE))
        );

        chartContainer.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fullname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        fullname.setText("Ameen Khwaja");
        jPanel5.add(fullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 140, 30));

        t_id.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        t_id.setText("TID-001");
        jPanel5.add(t_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 82, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Teacher:");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 68, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/Person.png"))); // NOI18N
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 40, 40));

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        course.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        course.setText("8");
        jPanel7.add(course, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 40, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("No of Courses:");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/course.png"))); // NOI18N
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 60, 50));

        jPanel8.setBackground(new java.awt.Color(204, 255, 204));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        joinDate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        joinDate.setText("12-01-2016");
        jPanel8.add(joinDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 110, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Joinded Date:");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logos/Calendar.png"))); // NOI18N
        jPanel8.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 60, 50));

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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(chartContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(studentPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chartContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                    .addComponent(studentPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 1090, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void showActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showActionPerformed
        // TODO add your handling code here:
        loadBarChart(subjectName,feedbacks);
    }//GEN-LAST:event_showActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new t_home().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartContainer;
    private javax.swing.JLabel course;
    private javax.swing.JLabel fullname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel joinDate;
    private javax.swing.JLabel per;
    private javax.swing.JButton show;
    private javax.swing.JPanel studentPieChart;
    private javax.swing.JComboBox<SubjectItem> subjects;
    private javax.swing.JLabel t_id;
    // End of variables declaration//GEN-END:variables
}
