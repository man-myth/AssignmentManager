/**
 * CS 211 Group Activity 2
 * Group Name: Sleepless Programmers
 * Assigned Application: Google Classroom
 *
 * Professor: Mam Josephine Dela Cruz
 * Due Date: Oct 6, 2021
 *
 */

//AssignmentList method

import java.io.*;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Vector;

public class AssignmentList{
    /**
     * Instance variables
     */
    private DoublyLinkedList<Assignment> list = new DoublyLinkedList<>();
    private Vector<String> classes = new Vector<>();

    /**
     * Constructor
     */
    public AssignmentList(){
        classes.add("All");
        readData();
    }

    /**
     * Returns the list variable
     */
    public DoublyLinkedList<Assignment> getList() {
        return list;
    }

    /**
     * Reads a csv file
     */
    public void readData(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("res/data.csv"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Calendar date = Calendar.getInstance();
                Calendar submissionDate = Calendar.getInstance();
                int year = Integer.parseInt(data[2]);
                int month = Integer.parseInt(data[3]);
                int day = Integer.parseInt(data[4]);
                date.set(year, month, day);
                year = Integer.parseInt(data[6]);
                month = Integer.parseInt(data[7]);
                day = Integer.parseInt(data[8]);
                submissionDate.set(year, month, day);
                Assignment assignment = new Assignment(data[0],data[1],date,Boolean.parseBoolean(data[5]),submissionDate);
                insertLast(assignment);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ListOverflowException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes to a csv file
     */
    public void saveData(){
        PrintWriter outputFile = null;
        try {
            outputFile = new PrintWriter("res/data.csv");
            Assignment assignment = list.first();
            while(assignment != null) {
                outputFile.println(assignment.toString());
                assignment = list.getNext(assignment);
            }
            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the classes vector
     */
    public Vector<String> getClasses() {
        return classes;
    }

    /**
     * Sets the classes vector
     */
    public void setClasses(Vector<String> classes) {
        this.classes = classes;
    }

    /**
     * Methods implemented by the MyList interface
     */
    public int getSize() {
        return list.getSize();
    }

    public void insert(Assignment data) throws ListOverflowException {
        list.insert(data);
        String dataClass = data.getClassName();
        if (!classes.contains(dataClass)){
            classes.add(dataClass);
        }
    }

    public void insertLast(Assignment data) throws ListOverflowException {
        list.insertLast(data);
        String dataClass = data.getClassName();
        if (!classes.contains(dataClass)){
            classes.add(dataClass);
        }
    }

    public Assignment getElement(Assignment data) throws NoSuchElementException {
        return list.getElement(data);
    }

    public boolean delete(Assignment data) {
        return list.delete(data);
    }

    public Assignment search(String assignmentTitle) {
        Assignment assignment = list.first();
        while(assignment != null) {
            if (assignment.getTitle().compareToIgnoreCase(assignmentTitle) == 0){
                return assignment;
            }
            assignment = list.getNext(assignment);
        }
        return null;
    }

    /**
     * toString method
     */
    public String toString() {
        return list.toString();
    }

}
