package bean;

import java.util.Date;

import org.springframework.jdbc.core.PreparedStatementSetter;

public class ClassList {
	private String className;
	private Date classStartDate;
	private Date classEndDate;
	private String classTeacher;
	private String classStat;
	private int classStudents;
	
	public ClassList(){}
	public ClassList(String className, Date classStartDate, Date classEndDate, String classTeacher, String classStat,
			int classStudents) {
		super();
		this.className = className;
		this.classStartDate = classStartDate;
		this.classEndDate = classEndDate;
		this.classTeacher = classTeacher;
		this.classStat = classStat;
		this.classStudents = classStudents;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Date getClassStartDate() {
		return classStartDate;
	}
	public void setClassStartDate(Date classStartDate) {
		this.classStartDate = classStartDate;
	}
	public Date getClassEndDate() {
		return classEndDate;
	}
	public void setClassEndDate(Date classEndDate) {
		this.classEndDate = classEndDate;
	}
	public String getClassTeacher() {
		return classTeacher;
	}
	public void setClassTeacher(String classTeacher) {
		this.classTeacher = classTeacher;
	}
	public String getClassStat() {
		return classStat;
	}
	public void setClassStat(String classStat) {
		this.classStat = classStat;
	}
	public int getClassStudents() {
		return classStudents;
	}
	public void setClassStudents(int classStudents) {
		this.classStudents = classStudents;
	}
	public PreparedStatementSetter getNum() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
