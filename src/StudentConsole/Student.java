package StudentConsole;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Student {
	private int ID;
	private String sName;
	private String sID;
	private String cCode;
	private String cTitle;
	private String grade;
	public Course course;
	public ArrayList<Course> studentCoursesRecord = new ArrayList<Course>();

	public ArrayList<Student> studentRecords = new ArrayList<Student>();

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsID() {
		return sID;
	}

	public void setsID(String sID) {
		this.sID = sID;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public String getcTitle() {
		return cTitle;
	}

	public void setcTitle(String cTitle) {
		this.cTitle = cTitle;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public ArrayList<Course> getStudentCourses() {
		return studentCoursesRecord;
	}

	public void setStudentCourses(ArrayList<Course> studentCourses) {
		this.studentCoursesRecord = studentCourses;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student(String sName, String sId, String cCode, String cTitle, String grade) {
		Course studentCourse = new Course();
		studentCourse.setCourseCode(cCode);
		studentCourse.setCourseTitle(cTitle);
		studentCourse.setCourseGrade(grade);

		this.sName = sName;
		this.sID = sId;
	}

	public Student(String sName, String sId, String cCode, String cTitle, String grade,
			ArrayList<Course> studentCourses) {
		Course studentCourse = new Course();
		studentCourse.setCourseCode(cCode);
		studentCourse.setCourseTitle(cTitle);
		studentCourse.setCourseGrade(grade);

		studentCourses.add(studentCourse);

		this.sName = sName;
		this.sID = sId;

		this.cCode = cCode;
		this.cTitle = cTitle;
		this.grade = grade;
	}

//	public Student(String sName, String sId, String cCode, String cTitle, String grade,
//			Course course) {
//		this.sName = sName;
//		this.sID = sId;
//		this.cCode = cCode;
//		this.cTitle = cTitle;
//		this.grade = grade;
//		this.course = course;
//		this.studentCoursesRecord.add(course);
//	}

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Student> readDB() {
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		// Load driver
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem loading driver");
			e.printStackTrace();
		}

		String msAcc = "Studentcourse.accdb";
		String dbURL = "jdbc:ucanaccess://" + msAcc;

		try {
			conn = DriverManager.getConnection(dbURL);
			sm = conn.createStatement();
			rs = sm.executeQuery("SELECT * FROM Grades ");
			String dbName = "";
			String checkName = "";
			ArrayList<Course> studentCourseRecords = new ArrayList<Course>();
			int counter = 0;
			Student student;

			while (rs.next()) {

				dbName = rs.getString("sName");
				String dbID = rs.getString("sId");
				String dbCode = rs.getString("cCode");
				String dbTitle = rs.getString("cTitle");
				String dbGrade = rs.getString("grade");
				Course newCourse = new Course(dbCode, dbTitle, dbGrade);
				student = new Student(dbName, dbID, dbCode, dbTitle, dbGrade, studentCourseRecords);

				studentRecords.add(student);
				student.studentCoursesRecord.add(newCourse);

//					if current db entry's name matches the last entries name, then just add the new Course to the old entry
//						if(dbName.equals(checkName)) {
//							studentRecords.get(counter-1).studentCoursesRecord.add(newCourse);
//						}
//						else {
//							studentRecords.add(student);
//							counter++;
//						}

				checkName = dbName;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problem reading database");
		}

		if (conn != null) {
			try {
				rs.close();
				sm.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Problem closing DB");
			}
		}
		return studentRecords;

	}

	
	public void updateStudentRecords(String cCodeUpdate, String sIDUpdate, String selectedGrade) {

		Connection conn = null;
		Statement sm = null;

		// Load driver
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem loading driver");
			e.printStackTrace();
		}

		String msAcc = "Studentcourse.accdb";
		String dbURL = "jdbc:ucanaccess://" + msAcc;

		try {
			conn = DriverManager.getConnection(dbURL);
			sm = conn.createStatement();

			String insertSQL = "UPDATE GRADES SET GRADE ='" + selectedGrade + "' WHERE SID='" + sIDUpdate
					+ "' AND CCODE='" + cCodeUpdate + "'";
			
			sm.executeUpdate(insertSQL);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problem reading database");
		}

		if (conn != null) {
			try {

				sm.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Problem closing DB");
			}
		}

	}

	@Override
	public String toString() {
		String nameAndID;
		nameAndID = sName + " (" + sID + ")";
		return nameAndID;
	}

	public ArrayList<Student> cleanStudentRecords(ArrayList<Student> studentRecords) {
		//get rid of duplicate student records and combine their courses
		for (int counter = 0; counter < studentRecords.size(); counter++) {
			for (int counter2 = counter + 1; counter2 < studentRecords.size(); counter2++)
				if (studentRecords.get(counter).getsName().equalsIgnoreCase(studentRecords.get(counter2).getsName())) {
					studentRecords.get(counter).studentCoursesRecord
							.addAll(studentRecords.get(counter2).getStudentCourses());
					studentRecords.remove(counter2);
				}
			for (int counter2 = counter + 1; counter2 < studentRecords.size(); counter2++)
				if (studentRecords.get(counter).getsName().equalsIgnoreCase(studentRecords.get(counter2).getsName())) {
					studentRecords.get(counter).studentCoursesRecord
							.addAll(studentRecords.get(counter2).getStudentCourses());
					studentRecords.remove(counter2);
				}
		}
		return studentRecords;

	}

}
