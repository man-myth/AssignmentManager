/**
 * CS 211 Group Activity 2
 * Group Name: Sleepless Programmers
 * Assigned Application: Google Classroom
 *
 * Professor: Mam Josephine Dela Cruz
 * Due Date: Oct 6, 2021
 *
 */

//ListOverFlowException

public class ListOverflowException extends Exception {

    /**
     * Constructors for the exceptions
     */
    public ListOverflowException() {
        super("The array is already full.");
    }

    public ListOverflowException(String message) {
        super(message);
    }
}
