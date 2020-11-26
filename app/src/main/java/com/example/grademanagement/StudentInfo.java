package com.example.grademanagement;
//实体类,提供get,set方法
public class StudentInfo {
	private int ID;

	public String Name;
	public String SCORE;
	public String CLASS;
	public String GRADE;

	public StudentInfo(String name,String Score,String Class,String Grade)	{
		this.Name=name;
		this.SCORE=Score;
		this.CLASS=Class;
		this.GRADE=Grade;

	}

	public String getSCORE() {
		return SCORE;
	}

	public void setSCORE(String Score) {
		SCORE =Score;
	}

	public String getCLASS() {
		return CLASS;
	}

	public void setCLASS(String Class) {
		CLASS= Class;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getGRADE() {
		return GRADE;
	}

	public void setGRADE(String Grade) {
		GRADE = Grade;
	}

}
