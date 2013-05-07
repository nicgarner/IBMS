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

/**
 * The journey planner interface for the passenger. User selects an origin and
 * destination area, then bus stops and time and a choice of journeys appear
 * as a result.
 */
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class JourneyPlanner extends javax.swing.JFrame {
    // Define the variables necessary for the inteface
    private Passenger passenger;
    private Area[] allAreas = Area.getNonEmptyAreas();
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
        System.out.println(BusStopInfo.findBusStop("SKP", "Stockport, Bus Station"));
               
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
        resultPanel = new javax.swing.JPanel();
        plannerTableScrollPane = new javax.swing.JScrollPane();
        plannerTable = new javax.swing.JTable();

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
        destinationBusStopBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinationBusStopBoxActionPerformed(evt);
            }
        });

        dateAndTimeLabel.setText("Select the departure date and time:");

        dateLabel.setFont(new java.awt.Font("Tahoma", 2, 13));
        dateLabel.setText("Date:");

        timeLabel.setFont(new java.awt.Font("Tahoma", 2, 13));
        timeLabel.setText("Time:");

        dateTextField.setPreferredSize(new java.awt.Dimension(70, 27));

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

        colonLabel.setText(":");

        minuteTextField.setPreferredSize(new java.awt.Dimension(20, 27));

        resultPanel.setVisible(false);

        javax.swing.GroupLayout resultPanelLayout = new javax.swing.GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 331, Short.MAX_VALUE)
        );
        resultPanelLayout.setVerticalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
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
                                            .addComponent(destinationSelectLabel1))))
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateLabel)
                                    .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(92, 92, 92)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(hourTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(colonLabel)
                                        .addGap(3, 3, 3)
                                        .addComponent(minuteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(timeLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)))
                        .addGap(90, 90, 90))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 232, Short.MAX_VALUE)))
                .addGap(22, 22, 22)
                .addComponent(plannerTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(189, 189, 189)
                .addComponent(resultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateLabel)
                                .addGap(18, 18, 18)
                                .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(timeLabel)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(hourTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(colonLabel)
                                    .addComponent(minuteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(planButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(plannerTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(resultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        //plannerTableScrollPane.setVisible (false);

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
        Network network = new Network();

        BusStopCombo originCombo = (BusStopCombo)originBusStopBox.getSelectedItem();
        BusStop origin = originCombo.getBusStop();
        BusStopCombo destinationCombo = (BusStopCombo)destinationBusStopBox.getSelectedItem();
        BusStop destination = destinationCombo.getBusStop();
        GregorianCalendar day = Timetable.parseDate(dateTextField.getText());
        int time = Timetable.parseTime(Integer.parseInt(hourTextField.getText()),
                                       Integer.parseInt(minuteTextField.getText()));

        PassengerJourney[] journeys = network.journeys(origin, destination, time, day);

        System.out.println("Showing journeys between " + origin.getName() +
                           " and " + destination.getName() + "\nleaving on " +
                           Timetable.dateToString(day) + " between " +
                           Timetable.minutesToTime(time) + " and " +
                           Timetable.minutesToTime(time + 60) + "\n");
        for (int j = 0; j < journeys.length; j++)
        {
          //System.out.println("\n"+journeys[j]+"\n");
          String[][] journey = journeys[j].getJourney();
          for (int l = 0; l < journey.length; l++)
            System.out.println(journey[l][0] +  "\t" + journey[l][1] + " \t" +
                               journey[l][2] + " \t" + journey[l][3] + " \t" +
                               journey[l][4] + " \t" + journey[l][5] + " \t" +
                               journey[l][6]);
          System.out.println("Total duration: " + Timetable.minutesToDuration(journeys[j].getDuration()));
          System.out.println();
        }

        resultPanel.setVisible (true);

    }//GEN-LAST:event_planButtonActionPerformed

    private void originBusStopBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_originBusStopBoxActionPerformed
        // TODO add your handling code here:
        // Obtain the selected bus stop from the list       
    }//GEN-LAST:event_originBusStopBoxActionPerformed

    // User selects an area and the bus stops in the area become available
    // in the origin bus stop selection drowdown.
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

            for (int i = 0; i < busStopsInArea.length; i++)
                originBusStopBox.addItem(new BusStopCombo(busStopsInArea[i]));
        }
    }//GEN-LAST:event_originAreaBoxActionPerformed

    // Works in the same way as origin boxes
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
            
            for (int i = 0; i < busStopsInArea.length; i++)
                destinationBusStopBox.addItem(new BusStopCombo(busStopsInArea[i]));
        }
}//GEN-LAST:event_destinationAreaBoxActionPerformed

    private void destinationBusStopBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinationBusStopBoxActionPerformed
        // TODO add your handling code here:       
    }//GEN-LAST:event_destinationBusStopBoxActionPerformed


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
    private javax.swing.JTextField minuteTextField;
    private javax.swing.JComboBox originAreaBox;
    private javax.swing.JLabel originAreaLabel;
    private javax.swing.JComboBox originBusStopBox;
    private javax.swing.JLabel originLabel1;
    private javax.swing.JButton planButton;
    private javax.swing.JLabel planJourneyLabel;
    private javax.swing.JTable plannerTable;
    private javax.swing.JScrollPane plannerTableScrollPane;
    private javax.swing.JPanel resultPanel;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables

}
