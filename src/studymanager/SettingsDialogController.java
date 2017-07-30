/**
 * 		Filename: SettingsDialogController.java
 * 		Purpose: Implements the controller functions for the Settings dialog. Corresponding FXML
 * 				 file is SettingsDialog.fxml.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SettingsDialogController {
	
	@FXML private DatePicker dpSemStart;
	@FXML private DatePicker dpOht1Start;
	@FXML private DatePicker dpOht1End;
	@FXML private DatePicker dpOht2Start;
	@FXML private DatePicker dpOht2End;
	@FXML private DatePicker dpEseStart;
	@FXML private DatePicker dpEseEnd;
	
	@FXML private TextField fieldClasses;
	
	@FXML private DatePicker remindersDp;
	@FXML private TextField remindersField;
	
	private MainController mainController;
	
	@FXML protected void initialize() {	
		try {
			loadClassesPath();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@FXML protected void remindersAddButtonPressed() {
		LocalDate reminderTime = remindersDp.getValue();
		if(reminderTime == null)
			return;
		
		Calendar reminderTimeCal = Calendar.getInstance();
		reminderTimeCal.set(reminderTime.getYear(), reminderTime.getMonthValue(),
				reminderTime.getDayOfMonth());
		
		String reminderText = remindersField.getText();
		mainController.getSettings().getReminders().add(new SReminder(reminderTimeCal, reminderText));
		
		remindersDp.setValue(null);
		remindersField.setText("");
	}
	
	@FXML protected void classesBrowseButtonPressed() {
		
		// Show the directory chooser and use it to set the text of the fieldClasses text field
		//
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Classes");
		fieldClasses.setText((chooser.showDialog(dpSemStart.getScene().getWindow()).getPath()));
	}
	
	@FXML protected void saveButtonPressed() {
		try {
			setSemesterSchedule();
			setClassData();
			saveClassesPath();
			
			mainController.getSettings().serialize();
		
			// Close the dialog
			Stage dialog = (Stage)dpSemStart.getScene().getWindow();
			dialog.close();
		}
		catch(FileNotFoundException e) {
			Alert error = new Alert(AlertType.ERROR, "The directory " + e.getMessage() + " does not exist."
					+ " Please enter a valid directory.");
			error.showAndWait();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(NullPointerException e) {
			Alert error = new Alert(AlertType.ERROR, "One or more fields are incorrect. Please recheck.");
			error.showAndWait();
		}
	}
	
	private void setClassData() throws FileNotFoundException {
		String directory = fieldClasses.getText();
		File classesDirectory = new File(directory);
		if(!classesDirectory.exists()) {
			throw new FileNotFoundException(directory);
		}
		
		mainController.getTreeView().update(classesDirectory);
		
		// Add the tree to the side pane.
		//mainController.getSidePane().setContent(tree);
	}
	
	private void setSemesterSchedule() {
		
		// DatePicker returns a LocalDate object, so first we need to convert it into a Calendar
		// object before we can pass it to the SSchedule class.
		//
		Calendar semStartCalendar = Calendar.getInstance();
		LocalDate semStartLd = dpSemStart.getValue();
		semStartCalendar.set(semStartLd.getYear(), semStartLd.getMonthValue() - 1,
				semStartLd.getDayOfMonth());
		
		Calendar oht1StartCalendar = Calendar.getInstance();
		LocalDate oht1StartLd = dpOht1Start.getValue();
		oht1StartCalendar.set(oht1StartLd.getYear(), oht1StartLd.getMonthValue() - 1,
				oht1StartLd.getDayOfMonth());
		
		// Add reminder 15 days before OHT-1
		Calendar oht1ReminderCalendar = Calendar.getInstance();
		oht1ReminderCalendar.set(oht1StartLd.getYear(), oht1StartLd.getMonthValue(),
				oht1StartLd.getDayOfMonth());
		oht1ReminderCalendar.add(Calendar.DAY_OF_YEAR, -15);
		mainController.getSettings().getReminders().add(new SReminder(oht1ReminderCalendar, "You have OHTs in 15 days!!!"));
		
		Calendar oht1EndCalendar = Calendar.getInstance();
		LocalDate oht1EndLd = dpOht1End.getValue();
		oht1EndCalendar.set(oht1EndLd.getYear(), oht1EndLd.getMonthValue() - 1,
				oht1EndLd.getDayOfMonth());
		
		Calendar oht2StartCalendar = Calendar.getInstance();
		LocalDate oht2StartLd = dpOht2Start.getValue();
		oht2StartCalendar.set(oht2StartLd.getYear(), oht2StartLd.getMonthValue() - 1,
				oht2StartLd.getDayOfMonth());
		
		Calendar oht2ReminderCalendar = Calendar.getInstance();
		oht2ReminderCalendar.set(oht2StartLd.getYear(), oht2StartLd.getMonthValue(),
				oht2StartLd.getDayOfMonth());
		oht2ReminderCalendar.add(Calendar.DAY_OF_YEAR, -15);
		mainController.getSettings().getReminders().add(new SReminder(oht2ReminderCalendar, "You have OHTs in 15 days!!!"));
		
		Calendar oht2EndCalendar = Calendar.getInstance();
		LocalDate oht2EndLd = dpOht2End.getValue();
		oht2EndCalendar.set(oht2EndLd.getYear(), oht2EndLd.getMonthValue() - 1,
				oht2EndLd.getDayOfMonth());
		
		Calendar eseStartCalendar = Calendar.getInstance();
		LocalDate eseStartLd = dpEseStart.getValue();
		eseStartCalendar.set(eseStartLd.getYear(), eseStartLd.getMonthValue() - 1,
				eseStartLd.getDayOfMonth());
		
		Calendar eseReminderCalendar = Calendar.getInstance();
		eseReminderCalendar.set(eseStartLd.getYear(), eseStartLd.getMonthValue(),
				eseStartLd.getDayOfMonth());
		eseReminderCalendar.add(Calendar.DAY_OF_YEAR, -15);
		mainController.getSettings().getReminders().add(new SReminder(eseReminderCalendar, "You have ESEs in 15 days!!!"));
		
		Calendar eseEndCalendar = Calendar.getInstance();
		LocalDate eseEndLd = dpEseEnd.getValue();
		eseEndCalendar.set(eseEndLd.getYear(), eseEndLd.getMonthValue() - 1,
				eseEndLd.getDayOfMonth());
		
		mainController.getSettings().getSchedule().setAll(semStartCalendar, oht1StartCalendar,
				oht1EndCalendar, oht2StartCalendar, oht2EndCalendar, eseStartCalendar,
				eseEndCalendar);
	}
	
	private void loadClassesPath() throws FileNotFoundException {
		File file = new File("cpath.stex");
		if(!file.exists())
			return;
		
		Scanner input = new Scanner(file);
		fieldClasses.setText(input.nextLine());
		input.close();
	}
	
	private void saveClassesPath() throws FileNotFoundException {
		Formatter format = new Formatter("cpath.stex");
		format.format(fieldClasses.getText());
		format.close();
	}
	
	private void loadDatePickers() {
		Calendar semStartCal = mainController.getSettings().getSchedule().getSemesterStart();
		Calendar oht1StartCal = mainController.getSettings().getSchedule().getOht1Start();
		Calendar oht1EndCal = mainController.getSettings().getSchedule().getOht1End();
		Calendar oht2StartCal = mainController.getSettings().getSchedule().getOht2Start();
		Calendar oht2EndCal = mainController.getSettings().getSchedule().getOht2End();
		Calendar eseStartCal = mainController.getSettings().getSchedule().getEseStart();
		Calendar eseEndCal = mainController.getSettings().getSchedule().getEseEnd();
		
		// Convert Calendar to LocalDate and set the corresponding DatePicker control
		if(semStartCal != null)
			dpSemStart.setValue(semStartCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		if(oht1StartCal != null)
			dpOht1Start.setValue(oht1StartCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		if(oht1EndCal != null)
			dpOht1End.setValue(oht1EndCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		if(oht2StartCal != null)
			dpOht2Start.setValue(oht2StartCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		if(oht2EndCal != null)
			dpOht2End.setValue(oht2EndCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		if(eseStartCal != null)
			dpEseStart.setValue(eseStartCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
		if(eseEndCal != null)
			dpEseEnd.setValue(eseEndCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}
	
	// Setter
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
		loadDatePickers();
	}
}
