/**
 * 		Filename: MainController.java
 * 		Purpose: Implements the controller functions for the main GUI. Corresponding FXML file is
 * 				 Main.fxml.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Formatter;
import java.util.Scanner;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	
	@FXML private ScrollPane sidePane;
	@FXML private WebView pdfView;
	
	private STree treeView;;
	
	private SSettings settings = new SSettings();
	
	private String lastPdfPath;
	
	public MainController() {
		treeView = new STree(this);
	}
	
	@FXML protected void initialize() {
		try {
			loadSettings();
			loadClasses();
			loadLastPdfPath();
			
			ArrayList<SReminder> reminders = settings.getReminders();
			if(reminders != null) {
				for(SReminder reminder: reminders) {
					if(reminder.remind()) {
						Alert areminder = new Alert(AlertType.INFORMATION, reminder.getReminderText());
						areminder.showAndWait();
					}
				}
			}
		}
		catch(FileNotFoundException e) {
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		sidePane.setContent(treeView);
		
		// The following lines of code initialize the WebView with the PDF.js library.
		// Courtesy of enzo from https://stackoverflow.com/questions/18207116/displaying-pdf-in-javafx
		//
		WebEngine engine = pdfView.getEngine();
		String url = getClass().getResource("resources/web/viewer.html").toExternalForm();
		
		// Add our stylesheet for removing the non-functional buttons.
		engine.setUserStyleSheetLocation(getClass().getResource("resources/web.css").toExternalForm());
		engine.setJavaScriptEnabled(true);
		engine.load(url);
		try {
			String pdfPath;
			
			// Load the last pdf file the user had opened if this isn't the first time using
			// this app
			if(lastPdfPath != null)
				pdfPath = lastPdfPath;
			else
				pdfPath = "welcome.pdf";
			
			// Read our PDF file to a byte array
			byte[] data = Files.readAllBytes(new File(pdfPath).toPath());
			String base64 = Base64.getEncoder().encodeToString(data);
			
			// Call our JS function
			engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
				if(newState == Worker.State.SUCCEEDED)
					engine.executeScript("openFileFromBase64('" + base64 + "')");
			});
		}
		catch (IOException e) {
			System.err.println("Could not find welcome.pdf");
		}
	}
	
	@FXML protected void btnSettingsPressed() {
		
		// Show the settings dialog.
		try {
			Stage settingsDialog = new Stage();
			settingsDialog.setTitle("Settings");
			settingsDialog.setResizable(false);
			
			// Create the dialog scene with the SettingsDialog fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(SMain.settingsDialogFXMLPath));
			Scene settingsScene = new Scene(loader.load(), 640, 480);
			
			// Pass the reference of this controller to the SettingsDialog controller so they
			// can work together (SettingsDialog controller needs access to MainController's
			// sidePane).
			SettingsDialogController controller = (SettingsDialogController) loader.getController();
			controller.setMainController(this);
			
			settingsDialog.setScene(settingsScene);
			settingsDialog.initOwner(pdfView.getScene().getWindow());
			settingsDialog.initModality(Modality.APPLICATION_MODAL);
			settingsDialog.showAndWait();
		}
		catch (IOException e) {
			System.err.printf("Failed to load %s\n", SMain.settingsDialogFXMLPath);
		}
	}
	
	private void loadClasses() throws FileNotFoundException {
		File file = new File("cpath.stex");
		if(!file.exists())
			return;
		
		Scanner input = new Scanner(file);
		treeView.update(new File(input.nextLine()));
		input.close();
	}
	
	private void loadSettings() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("settings.bin")));
		settings = (SSettings)ois.readObject();
		ois.close();
	}
	
	private void loadLastPdfPath() throws FileNotFoundException {
		Scanner input = new Scanner(new File("lastpdf.stex"));
		lastPdfPath = input.nextLine();
		input.close();
	}
	
	public void displayPdf(String pdfPath) {
		WebEngine engine = pdfView.getEngine();
		try {
			// Read our PDF file to a byte array
			byte[] data = Files.readAllBytes(new File(pdfPath).toPath());
			String base64 = Base64.getEncoder().encodeToString(data);
			
			// Call our JS function
			engine.executeScript("openFileFromBase64('" + base64 + "')");
			
			// Save so we know which was the last pdf file opened by the user
			Formatter file = new Formatter("lastpdf.stex");
			file.format(pdfPath);
			file.close();
		}
		catch (IOException e) {
			System.err.println("Could not open the PDF file.");
		}
	}
	
	// Getters
	public ScrollPane getSidePane() {
		return sidePane;
	}
	
	public STree getTreeView() {
		return treeView;
	}

	public SSettings getSettings() {
		return settings;
	}
}
