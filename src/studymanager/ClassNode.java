/**
 * 		Filename: ClassNode.java
 * 		Purpose: Defines the ClassNode class which is a STreeNode representing a class directory.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

public class ClassNode extends STreeNode {
	
	private SClass data;
	
	public ClassNode(String name, SClass data) {
		super(name);
		setData(data);
	}
	
	// Getters/Setters
	public SClass getData() {
		return data;
	}
	
	public void setData(SClass data) {
		this.data = data;
	}
}
