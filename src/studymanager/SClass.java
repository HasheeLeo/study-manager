/**
 * 		Filename: SClass.java
 * 		Purpose: Defines the SClass class which encapsulates information about a class the user
 * 				 is taking.
 * 
 * 		This file is property of Hashir Ahmad and Daud Akhtar Naveed as part of the project
 * 		"Study Manager" submitted to Dr. Mian M. Hamayun for OOP Class of 2k17, SEECS CS Batch
 * 		2k16, Section 6C, NUST.
 */

package studymanager;

import java.io.Serializable;
import java.util.ArrayList;

public class SClass implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int credits = 3;
	private float quizWeight;
	private float assignWeight;
	private float ohtWeight;
	private float eseWeight;
	private float projectWeight;
	private int oht1Max;
	private int oht2Max;
	private int eseMax;
	private int projMax;
	private float oht1;
	private float oht2;
	private float ese;
	private float proj;
	private double progress;
	private ArrayList<Float> quizzes;
	private int quizMax;
	private ArrayList<Float> assigns;
	private int assignMax;
	
	public SClass() {
	}
	
	public SClass(String name) {
		setName(name);
	}
	
	public void calculateProgress() {
		// Super cool math stuff
		//
		double quizScore = 0.0;
		for(float quiz: quizzes)
			quizScore += quiz;
		
		quizScore = (quizScore / (quizMax * quizzes.size())) * quizWeight;
		
		double assignScore = 0.0;
		for(float assign: assigns)
			assignScore += assign;
		
		assignScore = (assignScore / (assignMax * assigns.size())) * assignWeight;
		
		double ohtsScore = ((oht1 / oht1Max) * ohtWeight) + ((oht2 / oht2Max) * ohtWeight);
		double eseScore = (ese / eseMax) * eseWeight;
		double projScore = (proj / projMax) * projectWeight;
		
		progress = quizScore + assignScore + ohtsScore + eseScore + projScore;
	}
	
	// Getters/Setters
	public void setGrades(ArrayList<Float> quizzes, int quizMax, ArrayList<Float> assigns,
			int assignMax, float oht1, int oht1Max, float oht2, int oht2Max, float ese,
			int eseMax, float proj, int projMax) {
		this.quizzes = quizzes;
		this.quizMax = quizMax;
		this.assigns = assigns;
		this.assignMax = assignMax;
		this.oht1 = oht1;
		this.oht1Max = oht1Max;
		this.oht2 = oht2;
		this.oht2Max = oht2Max;
		this.ese = ese;
		this.eseMax = eseMax;
		this.proj = proj;
		this.projMax = projMax;
	}
	
	public void setWeights(int credits, float quizWeight, float assignWeight, float ohtWeight,
			float eseWeight, float projectWeight) {
		this.credits = credits;
		this.quizWeight = quizWeight;
		this.assignWeight = assignWeight;
		this.ohtWeight = ohtWeight;
		this.eseWeight = eseWeight;
		this.projectWeight = projectWeight;
		
	}
	
	// Getters/Setters
	//
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Float> getQuizzes() {
		return quizzes;
	}
	
	public int getQuizMax() {
		return quizMax;
	}
	
	public ArrayList<Float> getAssigns() {
		return assigns;
	}
	
	public int getAssignMax() {
		return assignMax;
	}
	
	public float getOht1() {
		return oht1;
	}
	
	public int getOht1Max() {
		return oht1Max;
	}
	
	public float getOht2() {
		return oht2;
	}
	
	public int getOht2Max() {
		return oht2Max;
	}
	
	public float getEse() {
		return ese;
	}
	
	public int getEseMax() {
		return eseMax;
	}

	public float getProj() {
		return proj;
	}
	
	public int getProjMax() {
		return projMax;
	}

	public int getCredits() {
		return credits;
	}

	public float getAssignWeight() {
		return assignWeight;
	}

	public float getQuizWeight() {
		return quizWeight;
	}

	public float getOhtWeight() {
		return ohtWeight;
	}

	public float getEseWeight() {
		return eseWeight;
	}

	public float getProjectWeight() {
		return projectWeight;
	}

	public double getProgress() {
		return progress;
	}
}
