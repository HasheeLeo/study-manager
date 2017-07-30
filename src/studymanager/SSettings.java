/**
 * 		Filename: SSettings.java
 * 		Purpose: Implements the SSettings class which encapsulates the information received by
 * 				 the Settings dialog.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SSettings implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SSchedule schedule = new SSchedule();
	private ArrayList<SClass> classes = new ArrayList<SClass>();
	private ArrayList<SReminder> reminders = new ArrayList<SReminder>();
	
	public void addClass(SClass sclass) {
		classes.add(sclass);
	}
	
	public void serialize() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("settings.bin")));
		oos.writeObject(this);
		oos.close();
	}
	
	// Getters/Setters
	public SSchedule getSchedule() {
		return schedule;
	}
	
	public void setSchedule(SSchedule schedule) {
		this.schedule = schedule;
	}
	
	public ArrayList<SClass> getClasses() {
		return classes;
	}
	
	public void setClasses(ArrayList<SClass> classes) {
		this.classes = classes;
	}

	public ArrayList<SReminder> getReminders() {
		return reminders;
	}

	public void setReminders(ArrayList<SReminder> reminders) {
		this.reminders = reminders;
	}
}