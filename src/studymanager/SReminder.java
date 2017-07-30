/**
 * 		Filename: SReminder.java
 * 		Purpose: Implements the SReminder class which handles all the reminder functions.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.io.Serializable;
import java.util.Calendar;

public class SReminder implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private Calendar reminderTime;
    private String reminderText;

    // Constructors.
    public SReminder() {

    }

    public SReminder(Calendar reminderTime, String reminderText) {
        this.reminderTime = reminderTime;
        this.reminderText = reminderText;
    }

    // Getters.
    public Calendar getReminderTime() {
        return reminderTime;
    }

    public String getReminderText() {
        return reminderText;
    }

    // Setters.
    public void setReminderTime(Calendar reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setReminderText(String reminderText) {
        this.reminderText = reminderText;
    }

    // Other methods.
    public boolean remind() {
        Calendar currentCal = Calendar.getInstance();
        if(reminderTime.get(Calendar.DAY_OF_MONTH) == currentCal.get(Calendar.DAY_OF_MONTH) &&
        		reminderTime.get(Calendar.MONTH) - 1 == currentCal.get(Calendar.MONTH) &&
        		reminderTime.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR)) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    @Override
    public String toString() {
    	return reminderText;
    }
}
