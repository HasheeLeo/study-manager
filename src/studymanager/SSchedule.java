/**
 * 		Filename: SSchedule.java
 * 		Purpose: Implements the SSchedule class which encapsulates the semester schedule
 * 				 information.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.io.Serializable;
import java.util.Calendar;

public class SSchedule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Calendar semesterStart;
	private Calendar oht1Start;
	private Calendar oht1End;
	private Calendar oht2Start;
	private Calendar oht2End;
	private Calendar eseStart;
	private Calendar eseEnd;
	
	public SSchedule() {
	}
	
	public SSchedule(Calendar semesterStart, Calendar oht1Start, Calendar oht1End,
			Calendar oht2Start, Calendar oht2End, Calendar eseStart, Calendar eseEnd) {
		setAll(semesterStart, oht1Start, oht1End, oht2Start, oht2End, eseStart, eseEnd);
	}
	
	public void setAll(Calendar semesterStart, Calendar oht1Start, Calendar oht1End,
			Calendar oht2Start, Calendar oht2End, Calendar eseStart, Calendar eseEnd) {
		this.semesterStart = semesterStart;
		this.oht1Start = oht1Start;
		this.oht1End = oht1End;
		this.oht2Start = oht2Start;
		this.oht2End = oht2End;
		this.eseStart = eseStart;
		this.eseEnd = eseEnd;
	}
	
	// Getters
	//
	public Calendar getSemesterStart() {
		return semesterStart;
	}

	public Calendar getOht1Start() {
		return oht1Start;
	}

	public Calendar getOht1End() {
		return oht1End;
	}

	public Calendar getOht2Start() {
		return oht2Start;
	}

	public Calendar getOht2End() {
		return oht2End;
	}

	public Calendar getEseStart() {
		return eseStart;
	}

	public Calendar getEseEnd() {
		return eseEnd;
	}
}
