/**
 * 		Filename: SMain.java
 * 		Purpose: Contains the entry point main() function.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SMain extends Application {
	
	public static final String appName = "Study Manager";
	
	public static final String mainFXMLPath = "Main.fxml";
	public static final String settingsDialogFXMLPath = "SettingsDialog.fxml";
	public static final String classProgressDialogFXMLPath = "ClassProgressDialog.fxml";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(SMain.mainFXMLPath));
			Scene scene = new Scene(root, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle(appName);
			primaryStage.setMaximized(true);
			primaryStage.show();
		}
		catch (IOException e) {
			System.err.printf("Failed to load %s\n", mainFXMLPath);
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
