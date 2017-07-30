/**
 * 		Filename: STree.java
 * 		Purpose: Defines the tree component which displays the user's classes along with their
 * 				 study material.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class STree extends TreeView<String> {
	
	private final TreeItem<String> root = new TreeItem<String>("root");
	
	private MainController parentController;
	
	public STree(MainController parentController) {
		this.parentController = parentController;
		setRoot(root);
		
		setOnMouseClicked(event -> {
			
			// Only process double clicks
			if(event.getClickCount() < 2)
				return;
			
			STreeNode clickedNode = (STreeNode)getSelectionModel().getSelectedItem();
			if(clickedNode == null)
				return;
			
			if(clickedNode instanceof ClassNode) {
				try {
					SClass classData = ((ClassNode)clickedNode).getData();
					Stage classProgressDialog = new Stage();
					classProgressDialog.setTitle(classData.getName());
					classProgressDialog.setResizable(false);
					
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource(SMain.classProgressDialogFXMLPath));
					Scene classProgressScene = new Scene(loader.load(), 640, 480);
					
					ClassProgressDialogController controller = (ClassProgressDialogController)loader.getController();
					controller.setClassData(classData);
					
					classProgressDialog.setScene(classProgressScene);
					classProgressDialog.initOwner(getScene().getWindow());
					classProgressDialog.initModality(Modality.APPLICATION_MODAL);
					classProgressDialog.showAndWait();
					parentController.getSettings().serialize();
					
				}
				catch(IOException e) {
					System.err.printf("Failed to open %s\n", SMain.classProgressDialogFXMLPath);
				}
				
			}
			else {
				FileNode fileNode = (FileNode)clickedNode;
				parentController.displayPdf(fileNode.getFilepath());
			}
		});
		
		// Hide the root
		setShowRoot(false);
	}
	
	// This method builds the tree with the given directory and associates with each item a
	// a unique SClass object either loaded from "settings.bin" (if it exists) or by creating
	// new objects.
	public void update(File directory) {
		File[] classes = directory.listFiles();
		
		// This will be true if none of the old classes match the new ones
		// hence we need to get rid of old data completely
		boolean replace = true;
		
		// Destroy all the old nodes
		root.getChildren().clear();
		
		SSettings settings = parentController.getSettings();
		ArrayList<SClass> oldclasses = settings.getClasses();
		ArrayList<SClass> newclasses = new ArrayList<SClass>();
		
		// Add the folders inside the directory as classes
		// and add the .pdf files inside these folders as the study material for those classes
		for(File classFile: classes) {
			
			// Only folders are allowed
			if(!classFile.isDirectory())
				continue;
			
			String name = classFile.getName();
			SClass snew = null;
			boolean oldExists = false;
			for(SClass old: oldclasses) {
				if(name.equals(old.getName())) {
					snew = old;
					oldExists = true;
					replace = false;
					break;
				}
			}
			
			if(!oldExists) {
				snew = new SClass(name);
				newclasses.add(snew);
			}
			
			ClassNode classNode = new ClassNode(name, snew);
			root.getChildren().add(classNode);
			
			// Only include .pdf files
			String[] studyFiles = classFile.list((dir, filename) -> {
				if(filename.contains(".pdf"))
					return true;
				
				else
					return false;
			});
			
			for(String studyFile: studyFiles)
				classNode.getChildren().add(new FileNode(studyFile, classFile.getPath()
						+ File.separatorChar + studyFile));
		}
		
		if(replace) {
			settings.setClasses(newclasses);
		}
		else {
			oldclasses.addAll(newclasses);
			settings.setClasses(oldclasses);
		}
	}
}
