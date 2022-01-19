/**
 * CS 211 Group Activity 2
 * Group Name: Sleepless Programmers
 * Assigned Application: Google Classroom
 *
 * Professor: Mam Josephine Dela Cruz
 * Due Date: Oct 6, 2021
 *
 */

//GUI Method

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public  class ProgramGUI extends JFrame   {
    //Main Frame and Panel
    private JFrame frame;
    private JFrame frame2;
    private JFrame frame3;
    private JPanel panelA;
    private JPanel panelB;
    private JPanel panelC;
    private JPanel panelD;
    private JPanel searchPanel;

    //Primary Buttons
    private final JButton buttonA = new JButton("Assigned");
    private final JButton buttonB = new JButton("Missing");
    private final JButton buttonC= new JButton("Done");
    private final JButton buttonD = new JButton("Search");
    private JButton buttonE = new JButton("Add an Assignment");
    private JButton buttonF = new JButton("Submit an Assignment");

    //Secondary Buttons
    private JButton button1 = new JButton("Add Assignment");
    private JButton button2 = new JButton("Submit Assignment");
    private JButton button3 = new JButton("No due date");
    private JButton button4 = new JButton();

    //Combo box
    private JComboBox comboBoxA = new JComboBox();
    private JComboBox comboBoxB = new JComboBox();
    private JComboBox comboBoxC = new JComboBox();
    private JComboBox comboBoxD = new JComboBox();
    private JComboBox comboBoxE = new JComboBox();
    private JComboBox comboBoxF = new JComboBox();
    private JComboBox<Integer> selMonth = new JComboBox();
    private JComboBox<Integer> selDay = new JComboBox();
    private JComboBox<Integer> selYear = new JComboBox();

    //Text fields
    private JTextField textFieldA = new JTextField();
    private JTextField textFieldB = new JTextField();

    //Labels
    private JLabel labelA = new JLabel("No Due Date");
    private JLabel labelB = new JLabel("This Week");
    private JLabel labelC = new JLabel("Next Week");
    private JLabel labelD = new JLabel("Later");

    //Instance Variables
    AssignmentList data = new AssignmentList();
    DataList list= new DataList(data);
    int currentStatus = 0;

    Font font = new Font("Courier", Font.BOLD,16);

    /**
     * Builds the GUI of the application
     */
    public void initComponents() {
        frame = new JFrame();
        panelA = new JPanel();
        panelB = new JPanel();
        panelC = new JPanel();
        panelD = new JPanel();
        searchPanel = new JPanel();

        //Setting up frame properties
        frame.setTitle("Classroom: To-Do");
        frame.setSize(625, 330);
        frame.add(panelA);
        frame.add(panelB);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Assigning actions to buttons
        buttonA.addActionListener(new mainButtonsAction());
        buttonB.addActionListener(new mainButtonsAction());
        buttonC.addActionListener(new mainButtonsAction());
        buttonD.addActionListener(new mainButtonsAction());
        buttonE.addActionListener(new mainButtonsAction());
        buttonF.addActionListener(new mainButtonsAction());
        button1.addActionListener(new mainButtonsAction());
        button2.addActionListener(new mainButtonsAction());
        button3.addActionListener(new mainButtonsAction());

        //PanelA
        panelA.setLayout(new GridLayout());
        panelA.setBackground(Color.CYAN);
        frame.add(panelA, BorderLayout.NORTH);

        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.CYAN);
        frame.add(searchPanel, BorderLayout.CENTER);

        //PanelB
        panelB.setLayout(new GridLayout(9, 1));
        panelB.setBackground(Color.CYAN);
        frame.add(panelB, BorderLayout.SOUTH);

        // Adding buttons to panelA
        panelA.add(buttonA);
        panelA.add(buttonB);
        panelA.add(buttonC);

        updateAssignmentList(0);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Action listener for buttons that display buttons that execute operations
     */
    private class mainButtonsAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            //Displays the operation buttons for Assigned
            if (source == buttonA) {
                updateAssignmentList(0);
                currentStatus = 0;
            }

            //Displays the operation buttons for Assigned
            if (source == buttonB) {
                updateAssignmentList(1);
                currentStatus = 1;
            }

            //Displays the operation buttons for Assigned
            if (source == buttonC) {
                updateAssignmentList(2);
                currentStatus = 2;
            }

            if (source == buttonD) {
                filterAssignmentList(currentStatus, (String) comboBoxE.getSelectedItem());
            }

            if (source == buttonE) {
                loadAddAssignment();
                frame2.setVisible(true);
            }

            if (source == buttonF) {
                loadSubmitAssignment();
                frame3.setVisible(true);
            }

            if (source == button1) {
                Calendar dueDate = new GregorianCalendar((Integer) selYear.getSelectedItem(), (Integer) selMonth.getSelectedItem() - 1, (Integer) selDay.getSelectedItem());
                try {
                    addAssignment(textFieldA.getText(), textFieldB.getText(), dueDate);
                } catch (ListOverflowException ex) {
                    ex.printStackTrace();
                }
            }

            if (source == button2) {
                submitAssignment(list.getNotSubmittedAssignments(data).get(comboBoxF.getSelectedIndex()));
            }

            if (source == button3) {
                panelC.remove(selMonth);
                panelC.remove(selDay);
                panelC.remove(selYear);
                panelC.remove(button3);

                selMonth.setSelectedItem(1);
                selDay.setSelectedItem(1);
                selYear.addItem(1970);
                selYear.setSelectedIndex(10);

                panelC.revalidate();
                panelC.repaint();
            }

            searchPanel.revalidate();
            searchPanel.repaint();
            panelB.revalidate();
            panelB.repaint();
        }
    }

    //Displays updated remarks of the assignments and their due dates
    public void updateAssignmentList(int status) {
        searchPanel.removeAll();
        panelB.removeAll();
        if (status != 0) {
            labelA = new JLabel("No Due Date");
            labelB = new JLabel("This Week");
            labelC = new JLabel("Last Week");
            labelD = new JLabel("Earlier");
            labelA.setFont(font);
            labelB.setFont(font);
            labelC.setFont(font);
            labelD.setFont(font);
        } else {
            labelA = new JLabel("No Due Date");
            labelB = new JLabel("This Week");
            labelC = new JLabel("Next Week");
            labelD = new JLabel("Later");
            labelA.setFont(font);
            labelB.setFont(font);
            labelC.setFont(font);
            labelD.setFont(font);
        }
        comboBoxE = new JComboBox(data.getClasses());
        comboBoxE.setPreferredSize(new Dimension(200, 20));
        comboBoxA = new JComboBox(list.toUFString(list.getAssignments(status, 0)));
        comboBoxB = new JComboBox(list.toUFString(list.getAssignments(status, 1)));
        comboBoxC = new JComboBox(list.toUFString(list.getAssignments(status, 2)));
        comboBoxD = new JComboBox(list.toUFString(list.getAssignments(status, 3)));
        searchPanel.add(comboBoxE);
        searchPanel.add(buttonD);
        searchPanel.add(buttonE);
        searchPanel.add(buttonF);
        panelB.add(labelA);
        panelB.add(comboBoxA);
        panelB.add(labelB);
        panelB.add(comboBoxB);
        panelB.add(labelC);
        panelB.add(comboBoxC);
        panelB.add(labelD);
        panelB.add(comboBoxD);
    }

    //Searches for a specific assignment
    public void filterAssignmentList(int status, String search) {
        searchPanel.removeAll();
        panelB.removeAll();
        if (status != 0) {
            labelA = new JLabel("No Due Date");
            labelB = new JLabel("This Week");
            labelC = new JLabel("Last Week");
            labelD = new JLabel("Earlier");
            labelA.setFont(font);
            labelB.setFont(font);
            labelC.setFont(font);
            labelD.setFont(font);
        } else {
            labelA = new JLabel("No Due Date");
            labelB = new JLabel("This Week");
            labelC = new JLabel("Next Week");
            labelD = new JLabel("Later");
            labelA.setFont(font);
            labelB.setFont(font);
            labelC.setFont(font);
            labelD.setFont(font);
        }
        comboBoxA = new JComboBox(list.toUFString(list.filterByClass(status, 0, search)));
        comboBoxE.setPreferredSize(new Dimension(200, 20));
        comboBoxB = new JComboBox(list.toUFString(list.filterByClass(status, 1, search)));
        comboBoxC = new JComboBox(list.toUFString(list.filterByClass(status, 2, search)));
        comboBoxD = new JComboBox(list.toUFString(list.filterByClass(status, 3, search)));
        searchPanel.add(comboBoxE);
        searchPanel.add(buttonD);
        searchPanel.add(buttonE);
        searchPanel.add(buttonF);
        panelB.add(labelA);
        panelB.add(comboBoxA);
        panelB.add(labelB);
        panelB.add(comboBoxB);
        panelB.add(labelC);
        panelB.add(comboBoxC);
        panelB.add(labelD);
        panelB.add(comboBoxD);
        if (Objects.equals(search, "All")) {
            updateAssignmentList(currentStatus);
        }
    }

    //Displays assignment to be added together with its respective details
    public void loadAddAssignment() {
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] days = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        int[] year ={2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030};

        panelC.removeAll();
        frame2 = new JFrame();
        //Setting up frame properties
        frame2.setTitle("Add an Assignment");
        frame2.setSize(640, 170);
        frame2.add(panelC);
        panelC.setLayout(new FlowLayout());
        panelC.add(new JLabel("Assignment Name: "));
        panelC.add(textFieldA);
        textFieldA.setPreferredSize(new Dimension(500, 25));
        panelC.add(new JLabel("Class: "));
        panelC.add(textFieldB);
        textFieldB.setPreferredSize(new Dimension(575, 25));
        panelC.add(new JLabel("Due date: "));
        panelC.add(selMonth);
        panelC.add(selDay);
        panelC.add(selYear);
        panelC.add(button3);

        textFieldA.setText("");
        textFieldB.setText("");
        selMonth.removeAllItems();
        selDay.removeAllItems();
        selYear.removeAllItems();

        for (Integer i: months) {
            selMonth.addItem(i);
        }
        for (Integer i: days) {
            selDay.addItem(i);
        }
        for (Integer i: year) {
            selYear.addItem(i);
        }

        selMonth.setPreferredSize(new Dimension(100, 25));
        selDay.setPreferredSize(new Dimension(100, 25));
        selYear.setPreferredSize(new Dimension(100, 25));

        panelC.add(button1);
        button1.setPreferredSize(new Dimension(600, 30));
        frame2.setLocationRelativeTo(null);
        frame2.setResizable(false);
    }

    //Loads the assignments to submit
    public void loadSubmitAssignment() {
        panelD.removeAll();
        frame3 = new JFrame();
        comboBoxF = new JComboBox(list.toUFString(list.getNotSubmittedAssignments(data)));
        frame3.setTitle("Submit an Assignment");
        frame3.setSize(500, 120);
        frame3.add(panelD);
        panelD.setLayout(new FlowLayout());
        panelD.add(comboBoxF);
        panelD.add(button2);
        frame3.setLocationRelativeTo(null);
        frame3.setResizable(false);
    }

    //Displays option to add an assignment
    public void addAssignment(String name, String className, Calendar dueDate) throws ListOverflowException {
        Assignment a = new Assignment(name, className, dueDate);
        data.insertLast(a);
        data.saveData();
        list= new DataList(data);
        updateAssignmentList(currentStatus);
        frame2.dispose();
    }

    //Displays the assignments to submit
    public void submitAssignment(Assignment a) {
        a.setSubmitted(true);
        a.setSubmissionDate(Calendar.getInstance());
        data.saveData();
        list= new DataList(data);
        updateAssignmentList(currentStatus);
        frame3.dispose();
    }

    /**
     * Run method
     */
    public ProgramGUI() {
        initComponents();
    }

    /**
     * Main method
     */
    public static void main(String[] args)  { new ProgramGUI(); }
}
