/**
 * 		Filename: STreeNode.java
 * 		Purpose: Defines the top level abstract STreeNode class which encapsulates information
 * 				 about a tree item added to a STree.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import javafx.scene.control.TreeItem;

public abstract class STreeNode extends TreeItem<String> {
	
	public STreeNode(String name) {
		super(name);
	}
}
