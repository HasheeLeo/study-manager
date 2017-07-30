/**
 * 		Filename: ClassProgressDialogController.java
 * 		Purpose: Implements the controller functions for the Class Progress dialog. Corresponding
 * 				 FXML file is ClassProgressDialog.fxml.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClassProgressDialogController {
	
	@FXML private ListView<Float> gradesListQuizzes;
	ObservableList<Float> quizzesList = FXCollections.observableArrayList();
	@FXML private ListView<Float> gradesListAssigns;
	ObservableList<Float> assignsList = FXCollections.observableArrayList();
	
	@FXML private Label progressText;
	@FXML private Label progressPercent;
	
	@FXML private TextField gradesFieldQuiz;
	@FXML private TextField gradesFieldQuizMax;
	@FXML private TextField gradesFieldAssign;
	@FXML private TextField gradesFieldAssignMax;
	@FXML private TextField gradesFieldOht1;
	@FXML private TextField gradesFieldOht1Max;
	@FXML private TextField gradesFieldOht2;
	@FXML private TextField gradesFieldOht2Max;
	@FXML private TextField gradesFieldEse;
	@FXML private TextField gradesFieldEseMax;
	@FXML private TextField gradesFieldProj;
	@FXML private TextField gradesFieldProjMax;
	
	@FXML private TextField weightageFieldClassCredit;
	@FXML private TextField weightageFieldQuizzes;
	@FXML private TextField weightageFieldAssigns;
	@FXML private TextField weightageFieldOhts;
	@FXML private TextField weightageFieldEse;
	@FXML private TextField weightageFieldProj;
	
	private SClass classData;
	
	@FXML protected void initialize() {
		gradesListQuizzes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		gradesListQuizzes.setItems(quizzesList);
		gradesListAssigns.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		gradesListAssigns.setItems(assignsList);
	}
	
	@FXML protected void gradesQuizAddButtonPressed() {
		try {
			String quiz = gradesFieldQuiz.getText();
			if(quiz == null)
				return;
			
			quizzesList.add(Float.parseFloat(quiz));
			gradesFieldQuiz.setText("");
		}
		catch(NumberFormatException e) {
			Alert error = new Alert(AlertType.ERROR, "Quiz field cannot be a string.");
			error.showAndWait();
		}
	}
	
	@FXML protected void gradesQuizRemoveButtonPressed() {
		ObservableList<Float> quizzes = gradesListQuizzes.getSelectionModel().getSelectedItems();
		quizzesList.removeAll(quizzes);
	}
	
	@FXML protected void gradesAssignAddButtonPressed() {
		try {
			String assign = gradesFieldAssign.getText();
			if(assign == null)
				return;
			
			assignsList.add(Float.parseFloat(assign));
			gradesFieldAssign.setText("");
		}
		catch(NumberFormatException e) {
			Alert error = new Alert(AlertType.ERROR, "Assignment field cannot be a string.");
			error.showAndWait();
		}
	}
	
	@FXML protected void gradesAssignRemoveButtonPressed() {
		ObservableList<Float> assigns = gradesListAssigns.getSelectionModel().getSelectedItems();
		assignsList.removeAll(assigns);
	}
	
	@FXML protected void saveButtonPressed() {
		try {
			setGrades();
			setWeights();
			
			classData.calculateProgress();
			
			Stage dialog = (Stage)gradesListQuizzes.getScene().getWindow();
			dialog.close();
		}
		catch(SClassInvalidWeightageException e) {
			Alert error = new Alert(AlertType.ERROR, "Total weight does not add to 100%. Please validate.");
			error.showAndWait();
		}
		catch(NumberFormatException e) {
			Alert error = new Alert(AlertType.ERROR, "One or more fields are incorrect. Please recheck.");
			error.showAndWait();
		}
	}
	
	private void setGrades() {
		int quizMax = Integer.parseInt(gradesFieldQuizMax.getText());
		if(quizMax == 0)
			throw new NumberFormatException();
		
		// Ensure no quiz score is greater than the max quiz
		for(float quiz: quizzesList) {
			if(quiz > quizMax)
				throw new NumberFormatException();
		}
		
		int assignMax = Integer.parseInt(gradesFieldAssignMax.getText());
		if(assignMax == 0)
			throw new NumberFormatException();
		
		for(float assign: assignsList) {
			if(assign > assignMax)
				throw new NumberFormatException();
		}
		
		float oht1 = Float.parseFloat(gradesFieldOht1.getText());
		int oht1Max = Integer.parseInt(gradesFieldOht1Max.getText());
		
		// Unless the instructor is really generous, this is definitely an exception.
		if(oht1 > oht1Max || oht1Max == 0)
			throw new NumberFormatException();
		
		float oht2 = Float.parseFloat(gradesFieldOht2.getText());
		int oht2Max = Integer.parseInt(gradesFieldOht2Max.getText());
		if(oht2 > oht2Max || oht2Max == 0)
			throw new NumberFormatException();
		
		float ese = Float.parseFloat(gradesFieldEse.getText());
		int eseMax = Integer.parseInt(gradesFieldEseMax.getText());
		if(ese > eseMax || eseMax == 0)
			throw new NumberFormatException();
		
		float proj = Float.parseFloat(gradesFieldProj.getText());
		int projMax = Integer.parseInt(gradesFieldProjMax.getText());
		if(proj > projMax || projMax == 0)
			throw new NumberFormatException();
		
		classData.setGrades(new ArrayList<Float>(quizzesList), quizMax,
				new ArrayList<Float>(assignsList), assignMax, oht1, oht1Max, oht2, oht2Max, ese,
				eseMax, proj, projMax);
	}
	
	private void setWeights() {
		int credits = Integer.parseInt(weightageFieldClassCredit.getText());
		float quizWeight = Float.parseFloat(weightageFieldQuizzes.getText());
		float assignWeight = Float.parseFloat(weightageFieldAssigns.getText());
		float ohtWeight = Float.parseFloat(weightageFieldOhts.getText());
		float eseWeight = Float.parseFloat(weightageFieldEse.getText());
		float projWeight = Float.parseFloat(weightageFieldProj.getText());
		
		if(credits <= 0 || credits > 4)
			throw new NumberFormatException(Integer.toString(credits));
		
		// (2 * ohtWeight) because there are two OHTs per class.
		if(quizWeight + assignWeight + (2 * ohtWeight) + eseWeight + projWeight != 100)
			throw new SClassInvalidWeightageException();
		
		classData.setWeights(credits, quizWeight, assignWeight, ohtWeight, eseWeight, projWeight);
		
	}
	
	// Setter
	public void setClassData(SClass classData) {
		this.classData = classData;
		
		// set default field values (or last if settings were loaded)
		ArrayList<Float> quizzes = classData.getQuizzes();
		if(quizzes != null) {
			for(float quiz: quizzes)
				quizzesList.add(quiz);
		}
		
		gradesFieldQuizMax.setText(Integer.toString(classData.getQuizMax()));
		
		ArrayList<Float> assigns = classData.getAssigns();
		if(assigns != null) {
			for(float assign: assigns)
				assignsList.add(assign);
		}
		
		progressText.setText("Your overall score for " + classData.getName() + " is:");
		progressPercent.setText(classData.getProgress() + "%");
		
		gradesFieldQuizMax.setText(Integer.toString(classData.getQuizMax()));
		gradesFieldAssignMax.setText(Integer.toString(classData.getAssignMax()));
		gradesFieldOht1.setText(Float.toString(classData.getOht1()));
		gradesFieldOht1Max.setText(Integer.toString(classData.getOht1Max()));
		gradesFieldOht2.setText(Float.toString(classData.getOht2()));
		gradesFieldOht2Max.setText(Integer.toString(classData.getOht2Max()));		
		gradesFieldEse.setText(Float.toString(classData.getEse()));
		gradesFieldEseMax.setText(Integer.toString(classData.getEseMax()));
		gradesFieldProj.setText(Float.toString(classData.getProj()));
		gradesFieldProjMax.setText(Integer.toString(classData.getProjMax()));
		
		weightageFieldClassCredit.setText(Integer.toString(classData.getCredits()));
		weightageFieldQuizzes.setText(Float.toString(classData.getQuizWeight()));
		weightageFieldAssigns.setText(Float.toString(classData.getAssignWeight()));
		weightageFieldOhts.setText(Float.toString(classData.getOhtWeight()));
		weightageFieldEse.setText(Float.toString(classData.getEseWeight()));
		weightageFieldProj.setText(Float.toString(classData.getProjectWeight()));
	}
}
