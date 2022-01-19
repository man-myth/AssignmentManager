/**
 * CS 211 Group Activity 2
 * Group Name: Sleepless Programmers
 * Assigned Application: Google Classroom
 *
 * Professor: Mam Josephine Dela Cruz
 * Due Date: Oct 6, 2021
 *
 */

//DataList Method
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Vector;

public class DataList {
    /**
     * Instance variables
     */
    private ArrayList<ArrayList<DoublyLinkedList<Assignment>>> organizedList = new ArrayList<>();
    Calendar today = Calendar.getInstance();

    /**
     * Reads a csv file
     */
    public DataList(AssignmentList data) {
        DoublyLinkedList<Assignment> assignmentlist = data.getList();
        organizedList.add(initAssigned(assignmentlist));
        organizedList.add(initMissing(assignmentlist));
        organizedList.add(initDone(assignmentlist));
    }

    /**
     * Initializes the ArrayList of assigned assignments
     */
    public ArrayList<DoublyLinkedList<Assignment>> initAssigned(DoublyLinkedList<Assignment>  list){
        Integer[] dueDates = {today.get(Calendar.WEEK_OF_YEAR),today.get(Calendar.WEEK_OF_YEAR)+1,today.get(Calendar.WEEK_OF_YEAR)+2};
        DoublyLinkedList<Assignment> assignedList = getAssigned(list,1);
        ArrayList<DoublyLinkedList<Assignment>> sortedAssignedList = sortByTime(assignedList, dueDates);
        return sortedAssignedList;
    }

    /**
     * Initializes the ArrayList of missing assignments
     */
    public ArrayList<DoublyLinkedList<Assignment>> initMissing(DoublyLinkedList<Assignment> list){
        Integer[] dueDates = {today.get(Calendar.WEEK_OF_YEAR),today.get(Calendar.WEEK_OF_YEAR)-1, today.get(Calendar.WEEK_OF_YEAR)-2 };
        DoublyLinkedList<Assignment> assignedList = getAssigned(list,0);
        ArrayList<DoublyLinkedList<Assignment>> sortedAssignedList = sortByTime(assignedList, dueDates);
        return sortedAssignedList;
    }

    /**
     * Initializes the ArrayList of done assignments
     */
    public ArrayList<DoublyLinkedList<Assignment>> initDone(DoublyLinkedList<Assignment> list){
        Integer[] dueDates = {today.get(Calendar.WEEK_OF_YEAR),today.get(Calendar.WEEK_OF_YEAR)-1, today.get(Calendar.WEEK_OF_YEAR)-2};
        DoublyLinkedList<Assignment> doneList = getDone(list);
        ArrayList<DoublyLinkedList<Assignment>> sortedAssignedList = sortByTime(doneList, dueDates);
        return sortedAssignedList;
    }

    /**
     * Getter methods
     */
    // Returns a DoublyLinkedList of assigned assignments
    // Note: Status 0 represents missing assignments and Status 1 represents assigned assignments
    public DoublyLinkedList<Assignment> getAssigned(DoublyLinkedList<Assignment> list, int status){
        DoublyLinkedList<Assignment> assignedList = new DoublyLinkedList<Assignment>();
        Assignment assignment = list.first();

        while(assignment != null) {
            if (!assignment.isSubmitted()){
                if (status == 1 && (assignment.getDueDate().compareTo(today) >= 0 || assignment.getDueDate().get(Calendar.YEAR) == 1970)) {
                    assignedList.insertLast(assignment);
                } else if(status == 0 && (assignment.getDueDate().compareTo(today) < 0 && assignment.getDueDate().get(Calendar.YEAR) != 1970)) {
                    assignedList.insertLast(assignment);
                }
            }
            assignment = list.getNext(assignment);
        }

        return assignedList;
    }

    // Returns a DoublyLinkedList of finished assignments
    public DoublyLinkedList<Assignment> getDone(DoublyLinkedList<Assignment> list){
        DoublyLinkedList<Assignment> assignedList = new DoublyLinkedList<>();
        Assignment assignment = list.first();
        while(assignment != null) {
            if (assignment.isSubmitted()){
                assignedList.insertLast(assignment);
            }
            assignment = list.getNext(assignment);
        }
        return assignedList;
    }


    /*
    Note:
    status = 0 is Assigned assignments.
    status = 1 is Missed assignments.
    status = 2 is Done assignments.

    date = 0 is no due date.
    date = 1 is This week.
    date = 2 is Next week / Last week.
    date = 3 is Later / Earlier.
     */
    public Vector<Assignment> getAssignments(int status, int date) {
        Vector<Assignment> out = new Vector<>();
        Assignment a = organizedList.get(status).get(date).first();
        while (a != null) {
            out.add(a);
            a = organizedList.get(status).get(date).getNext(a);
        }
        return out;
    }

    public Vector<Assignment> getNotSubmittedAssignments(AssignmentList data){
        Vector<Assignment> assignedList = new Vector<>();
        Assignment assignment = data.getList().first();
        while(assignment != null) {
            if (!assignment.isSubmitted()){
                assignedList.add(assignment);
            }
            assignment = data.getList().getNext(assignment);
        }
        return assignedList;
    }

    public Vector<Assignment> filterByClass(int status, int date, String search) {
        Vector<Assignment> out = new Vector<>();
        Assignment a = organizedList.get(status).get(date).first();
        while (a != null) {
            if (Objects.equals(search, a.getClassName())) {
                out.add(a);
            }
            a = organizedList.get(status).get(date).getNext(a);
        }
        return out;
    }

    public Vector<String> toUFString(Vector<Assignment> data) {
        Vector <String> out = new Vector<>();
        for (Assignment a : data) {
            out.add(a.toUserFriendlyString());
        }
        return out;
    }

    /**
     * Sorts an ArrayList of assignments by time
     */
    public ArrayList<DoublyLinkedList<Assignment>> sortByTime(DoublyLinkedList<Assignment> list, Integer[] dueDates){
        ArrayList<DoublyLinkedList<Assignment>> organizedList = new ArrayList<>();
        DoublyLinkedList<Assignment> noDueAssignments = new DoublyLinkedList<>();
        DoublyLinkedList<Assignment> thisWeekAssignments = new DoublyLinkedList<>();
        DoublyLinkedList<Assignment> nextWeekAssignments = new DoublyLinkedList<>();
        DoublyLinkedList<Assignment> laterWeekAssignments = new DoublyLinkedList<>();

        Assignment assignment = list.first();
        Calendar date = null;
        for (int i = 0; i < list.getSize(); i++){
            if (today.compareTo(assignment.getDueDate()) < 0 && !assignment.isSubmitted()) {
                if(assignment.getDueDate().get(Calendar.YEAR) == 1970) {
                    noDueAssignments.insertLast(assignment);
                } else if(assignment.getDueDate().get(Calendar.WEEK_OF_YEAR) == dueDates[0]){
                    thisWeekAssignments.insertLast(assignment);
                } else if(assignment.getDueDate().get(Calendar.WEEK_OF_YEAR) == dueDates[1]){
                    nextWeekAssignments.insertLast(assignment);
                } else {
                    laterWeekAssignments.insertLast(assignment);
                }
            } else if (!assignment.isSubmitted()) {
                if(assignment.getDueDate().get(Calendar.YEAR) == 1970) {
                    noDueAssignments.insertLast(assignment);
                } else if(assignment.getDueDate().get(Calendar.WEEK_OF_YEAR) == dueDates[0]){
                    thisWeekAssignments.insertLast(assignment);
                } else if(assignment.getDueDate().get(Calendar.WEEK_OF_YEAR) == dueDates[1]){
                    nextWeekAssignments.insertLast(assignment);
                } else {
                    laterWeekAssignments.insertLast(assignment);
                }
            } else {
                if(assignment.getDueDate().get(Calendar.YEAR) == 1970) {
                    noDueAssignments.insertLast(assignment);
                } else if(assignment.getSubmissionDate().get(Calendar.WEEK_OF_YEAR) == dueDates[0]){
                    thisWeekAssignments.insertLast(assignment);
                } else if(assignment.getSubmissionDate().get(Calendar.WEEK_OF_YEAR) == dueDates[1]){
                    nextWeekAssignments.insertLast(assignment);
                } else {
                    laterWeekAssignments.insertLast(assignment);
                }
            }
            assignment = list.getNext(assignment);
        }
        organizedList.add(noDueAssignments);
        organizedList.add(thisWeekAssignments);
        organizedList.add(nextWeekAssignments);
        organizedList.add(laterWeekAssignments);

        return organizedList;
    }

    /**
     * Returns the organizedList variable
     */
    public ArrayList<ArrayList<DoublyLinkedList<Assignment>>> getOrganizedList() {
        return organizedList;
    }

    /**
     * Sets the organizedList variable
     */
    public void setOrganizedList(ArrayList<ArrayList<DoublyLinkedList<Assignment>>> organizedList) {
        this.organizedList = organizedList;
    }

    /**
     * toString method
     */
    public String toString() {
        return "DataList{" +
                "organizedList=" + organizedList +
                '}';
    }
}


