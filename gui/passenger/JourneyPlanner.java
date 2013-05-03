package gui.passenger;
import objects.*;
import wrapper.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JourneyPlanner.java
 *
 * Created on 22-Apr-2013, 11:50:59
 */

/**
 *
 * @author Adam Nogradi
 */
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JourneyPlanner extends javax.swing.JFrame {
    // Define the variables necessary for the inteface
    private Passenger passenger;
    private Area[] allAreas = Area.getAllAreas();
    private String[] allAreaNames = new String[allAreas.length];
    private String selectedArea;

    private Date date = new Date();
    private Calendar cal = Calendar.getInstance();
    private DateFormat dateFormatDate = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat dateFormatTime = new SimpleDateFormat("HH:mm");
    private String currentTime = dateFormatTime.format (cal.getTime());
    private String[] splitTime = currentTime.split(":");



    /**
     * Creates new form JourneyPlanner, given the passenger parameter
     * from the menu so its visibility can be set to true if the user
     * presses the 'back' button.
     * @param pass the Passenger instance from the menu
     */
    public JourneyPlanner(Passenger pass) {
       
        for (int i = 0; i < allAreas.length; i++)
        {
            allAreaNames[i] = allAreas[i].getName();
        }
            //System.out.println(splitTime[0] + ":" + splitTime[1]);
               
        initComponents();
        passenger = pass;
        
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backButton = new javax.swing.JButton();
        planButton = new javax.swing.JButton();
        destinationSelectLabel1 = new javax.swing.JLabel();
        destinationBusStopBox = new javax.swing.JComboBox();
        dateAndTimeLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        dateTextField = new javax.swing.JTextField();
        destinationAreaLabel = new javax.swing.JLabel();
        destinationAreaBox = new javax.swing.JComboBox();
        planJourneyLabel = new javax.swing.JLabel();
        instructionLabel2 = new javax.swing.JLabel();
        originAreaBox = new javax.swing.JComboBox();
        originAreaLabel = new javax.swing.JLabel();
        originLabel1 = new javax.swing.JLabel();
        originBusStopBox = new javax.swing.JComboBox();
        hourTextField = new javax.swing.JTextField();
        colonLabel = new javax.swing.JLabel();
        minuteTextField = new javax.swing.JTextField();
        tablePanel = new javax.swing.JPanel();
        plannerTableScrollPane = new javax.swing.JScrollPane();
        plannerTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Journey Planner");

        backButton.setText("Back to menu");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        planButton.setText("Plan journey");
        planButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                planButtonActionPerformed(evt);
            }
        });

        destinationSelectLabel1.setFont(new java.awt.Font("Tahoma", 2, 13));
        destinationSelectLabel1.setText("Destination bus stop:");

        destinationBusStopBox.setModel(new javax.swing.DefaultComboBoxModel());
        destinationBusStopBox.addItem("(Select an area...)");

        dateAndTimeLabel.setText("Select the departure date and time:");

        dateLabel.setFont(new java.awt.Font("Tahoma", 2, 13));
        dateLabel.setText("Date:");

        timeLabel.setFont(new java.awt.Font("Tahoma", 2, 13));
        timeLabel.setText("Time:");

        dateTextField.setText(dateFormatDate.format(date));
        dateTextField.setPreferredSize(new java.awt.Dimension(80, 27));

        destinationAreaLabel.setFont(new java.awt.Font("Tahoma", 2, 13));
        destinationAreaLabel.setText("Destination area:");

        destinationAreaBox.setModel(new javax.swing.DefaultComboBoxModel());
        destinationAreaBox.addItem("Select...");
        for (int i = 0; i < allAreaNames.length; i++)
        {
            destinationAreaBox.addItem(allAreaNames[i]);
            //System.out.println (allAreaNames[i]);
        }
        destinationAreaBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinationAreaBoxActionPerformed(evt);
            }
        });

        planJourneyLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 24));
        planJourneyLabel.setText("Plan a journey");

        instructionLabel2.setText("Select the origin and destination bus stop or area:");

        originAreaBox.setModel(new javax.swing.DefaultComboBoxModel());
        originAreaBox.addItem("Select...");
        for (int i = 0; i < allAreaNames.length; i++)
        {
            originAreaBox.addItem(allAreaNames[i]);
            //System.out.println (allAreaNames[i]);
        }
        originAreaBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                originAreaBoxActionPerformed(evt);
            }
        });

        originAreaLabel.setFont(new java.awt.Font("DejaVu Sans", 2, 13));
        originAreaLabel.setText("Origin area:");

        originLabel1.setFont(new java.awt.Font("DejaVu Sans", 2, 13));
        originLabel1.setText("Origin bus stop:");

        originBusStopBox.setModel(new javax.swing.DefaultComboBoxModel());
        originBusStopBox.addItem("(Select an area...)");
        originBusStopBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                originBusStopBoxActionPerformed(evt);
            }
        });

        hourTextField.setPreferredSize(new java.awt.Dimension(20, 27));
        hourTextField.setText(splitTime[0]);

        colonLabel.setText(":");

        minuteTextField.setPreferredSize(new java.awt.Dimension(20, 27));
        minuteTextField.setText(splitTime[1]);

        tablePanel.setVisible(false);

        plannerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Departure Stop", "Departure time", "Arrival stop", "Arrival time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        plannerTableScrollPane.setViewportView(plannerTable);
        plannerTable.getColumnModel().getColumn(0).setResizable(false);
        plannerTable.getColumnModel().getColumn(1).setResizable(false);
        plannerTable.getColumnModel().getColumn(2).setResizable(false);
        plannerTable.getColumnModel().getColumn(3).setResizable(false);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
            .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tablePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(plannerTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
            .addGroup(tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tablePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(plannerTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
        );

        //plannerTableScrollPane.setVisible (false);

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 2, 13)); // NOI18N
        jLabel1.setText("dd/mm/yyyy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(planJourneyLabel)
                            .addComponent(instructionLabel2)
                            .addComponent(dateAndTimeLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(originAreaBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(originAreaLabel))
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(originBusStopBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(originLabel1)))
                            .addComponent(planButton, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(destinationAreaLabel)
                                    .addComponent(destinationAreaBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(destinationBusStopBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(destinationSelectLabel1)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dateLabel)
                                            .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(72, 72, 72)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(hourTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)
                                                .addComponent(colonLabel)
                                                .addGap(3, 3, 3)
                                                .addComponent(minuteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(timeLabel)))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(backButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(planJourneyLabel)
                .addGap(18, 18, 18)
                .addComponent(instructionLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(originAreaLabel)
                    .addComponent(originLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(originAreaBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(originBusStopBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(destinationAreaLabel)
                    .addComponent(destinationSelectLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(destinationAreaBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(destinationBusStopBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(dateAndTimeLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateLabel)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(timeLabel)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hourTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(colonLabel)
                            .addComponent(minuteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(planButton)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(349, Short.MAX_VALUE)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-786)/2, (screenSize.height-546)/2, 786, 546);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * If the user presses the 'back' button, go back to the menu
     */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        passenger.setVisible(true);
        dispose();
        
    }//GEN-LAST:event_backButtonActionPerformed

    /**
     * Plan the journey if the button is pressed
     */
    private void planButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_planButtonActionPerformed
        // TODO add your handling code here:
        tablePanel.setVisible (true);
    }//GEN-LAST:event_planButtonActionPerformed

    private void originBusStopBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_originBusStopBoxActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_originBusStopBoxActionPerformed

    // Do the same for the destination area and collection of bus stops
    private void originAreaBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_originAreaBoxActionPerformed
        // TODO add your handling code here:
        // Obtain the selected item from the list
        selectedArea = (String) originAreaBox.getSelectedItem();

        // If no item is selected, don't display any relevant bus stops
        if (originAreaBox.getSelectedItem() == "Select...") {
            originBusStopBox.removeAllItems();
            originBusStopBox.addItem("(Select an area...)");
        }

        // Else display the bus stops of the selected area
        else {
            Area area = new Area(selectedArea);
            BusStop[] busStopsInArea = area.getUniqueStops();

            originBusStopBox.removeAllItems();
            //busStopsInArea = BusStopInfo.getBusStopsInArea(BusStopInfo.findAreaByName(selectedArea));
            

            if (busStopsInArea == null)
                originBusStopBox.addItem("(No stops in this area)");
            else
            {
                String[] busStopNamesInArea = new String[busStopsInArea.length];
                //System.out.println(busStopNamesInArea.length);
                for (int i = 0; i < busStopNamesInArea.length; i++) {
                //int stopID = busStopsInArea[i];
                //System.out.println(stopID);
                busStopNamesInArea[i] = busStopsInArea[i].getName();
                originBusStopBox.addItem(busStopNamesInArea[i]);
            }
            }
            
        }
    }//GEN-LAST:event_originAreaBoxActionPerformed

    private void destinationAreaBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinationAreaBoxActionPerformed
        // TODO add your handling code here:
        selectedArea = (String) destinationAreaBox.getSelectedItem();

        if (destinationAreaBox.getSelectedItem() == "Select...") {
            destinationBusStopBox.removeAllItems();
            destinationBusStopBox.addItem("(Select an area...)");
        }

        else
        {
            Area area = new Area(selectedArea);
            BusStop[] busStopsInArea = area.getUniqueStops();           

            destinationBusStopBox.removeAllItems();
            //busStopsInArea = BusStopInfo.getBusStopsInArea(BusStopInfo.findAreaByName(selectedArea));


            if (busStopsInArea == null)
                destinationBusStopBox.addItem("(No stops in this area)");
            else
            {
                String[] busStopNamesInArea = new String[busStopsInArea.length];
                //System.out.println(busStopNamesInArea.length);
                for (int i = 0; i < busStopNamesInArea.length; i++)
                {
                //int stopID = busStopsInArea[i];
                System.out.println(busStopsInArea[i].getId());
                busStopNamesInArea[i] = busStopsInArea[i].getName();
                destinationBusStopBox.addItem(busStopNamesInArea[i]);
                }
            }
        }
}//GEN-LAST:event_destinationAreaBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel colonLabel;
    private javax.swing.JLabel dateAndTimeLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JTextField dateTextField;
    private javax.swing.JComboBox destinationAreaBox;
    private javax.swing.JLabel destinationAreaLabel;
    private javax.swing.JComboBox destinationBusStopBox;
    private javax.swing.JLabel destinationSelectLabel1;
    private javax.swing.JTextField hourTextField;
    private javax.swing.JLabel instructionLabel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField minuteTextField;
    private javax.swing.JComboBox originAreaBox;
    private javax.swing.JLabel originAreaLabel;
    private javax.swing.JComboBox originBusStopBox;
    private javax.swing.JLabel originLabel1;
    private javax.swing.JButton planButton;
    private javax.swing.JLabel planJourneyLabel;
    private javax.swing.JTable plannerTable;
    private javax.swing.JScrollPane plannerTableScrollPane;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables

}
