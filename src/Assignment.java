/**
 * CS 211 Group Activity 2
 * Group Name: Sleepless Programmers
 * Assigned Application: Google Classroom
 *
 * Professor: Mam Josephine Dela Cruz
 * Due Date: Oct 6, 2021
 *
 */

//Assignment Method

import com.sun.source.tree.BreakTree;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Assignment {
    /**
     * Instance variables
     */
    private String title;
    private String className;
    private Calendar dueDate = Calendar.getInstance();
    private boolean isSubmitted= false;
    private Calendar submissionDate = new GregorianCalendar(1970, 0, 1);


    /**
     * Constructors
     */
    public Assignment(String title, String className, Calendar dueDate, boolean isSubmitted, Calendar submissionDate) {
        this.title = title;
        this.className = className;
        this.dueDate = dueDate;
        this.isSubmitted = isSubmitted;
        this.submissionDate = submissionDate;
    }
    public Assignment(String title, String className, Calendar dueDate) {
        this.title = title;
        this.className = className;
        this.dueDate = dueDate;
    }
    public Assignment(String title, String className) {
        this.title = title;
        this.className = className;
        this.dueDate.clear();
    }

    /**
     * Setter methods
     */
    public void setTitle(String title) {
        this.title = title;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }
    public void setSubmissionDate(Calendar submissionDate) {
        this.submissionDate = submissionDate;
    }
    public void setSubmitted(boolean submittede) {
        isSubmitted = submittede;
    }


    /**
     * Getter methods
     */
    public String getTitle() {
        return title;
    }
    public String getClassName() {
        return className;
    }
    public Calendar getDueDate() {
        return dueDate;
    }
    public Calendar getSubmissionDate() {
        return submissionDate;
    }
    public boolean isSubmitted() {
        return isSubmitted;
    }

    public String toStringWeek() {
        switch (dueDate.get(Calendar.DAY_OF_WEEK)) {
            case 1 -> {
                return "Sunday";
            }
            case 2 -> {
                return "Monday";
            }
            case 3 -> {
                return "Tuesday";
            }
            case 4 -> {
                return "Wednesday";
            }
            case 5 -> {
                return "Thursday";
            }
            case 6 -> {
                return "Friday";
            }
            case 7 -> {
                return "Saturday";
            }
            default -> {
                return "Out of bounds";
            }
        }
    }

    public String toStringMonth() {
        switch (dueDate.get(Calendar.MONTH)) {
            case 0 -> {
                return "January";
            }
            case 1 -> {
                return "February";
            }
            case 2 -> {
                return "March";
            }
            case 3 -> {
                return "April";
            }
            case 4 -> {
                return "May";
            }
            case 5 -> {
                return "June";
            }
            case 6 -> {
                return "July";
            }
            case 7 -> {
                return "August";
            }
            case 8 -> {
                return "September";
            }
            case 9 -> {
                return "October";
            }
            case 10 -> {
                return "November";
            }
            case 11 -> {
                return "December";
            }
            default -> {
                return "Out of bounds";
            }
        }
    }

    public String toUserFriendlyString() {
        if (isSubmitted) {
        return title + " "
                + className + "     "
                + "Turned in";
        } else if (dueDate.get(Calendar.YEAR) != 1970 && !isSubmitted()) {
            return title + " "
                    + className + "     "
                    + toStringWeek() + ", "
                    + toStringMonth() + " "
                    + dueDate.get(Calendar.DAY_OF_MONTH);
        } else {
            return title + " "
                    + className + "     "
                    + "No Due Date";
        }
    }

    /**
     * ToString method
     */
    public String toString() {
        return title + ","
                + className + ","
                + dueDate.get(Calendar.YEAR) + ","
                + dueDate.get(Calendar.MONTH) + ","
                + dueDate.get(Calendar.DAY_OF_MONTH) + ","
                + this.isSubmitted + ","
                + submissionDate.get(Calendar.YEAR) + ","
                + submissionDate.get(Calendar.MONTH) + ","
                + submissionDate.get(Calendar.DAY_OF_MONTH);
    }
}
