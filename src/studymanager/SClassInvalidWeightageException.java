/**
 * 		Filename: SClassInvalidWeightageException.java
 * 		Purpose: Defines the SClassInvalidWeightageException which is thrown when the total
 * 				 of the weights provided to an SClass class do not add up to 100.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

public class SClassInvalidWeightageException extends RuntimeException {
	
	public SClassInvalidWeightageException() {
	}
	
	public SClassInvalidWeightageException(String message) {
		super(message);
	}
}
