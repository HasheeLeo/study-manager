/**
 * 		Filename: FileNode.java
 * 		Purpose: Defines the FileNode class which is a STreeNode representing a study file.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

public class FileNode extends STreeNode {
	
	private String filepath;
	
	public FileNode(String name, String filepath) {
		super(name);
		setFilepath(filepath);
	}
	
	// Getters/Setters
	public String getFilepath() {
		return filepath;
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}
