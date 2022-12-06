package StudentConsole;

public class Course extends Student {
	private String courseGrade;
	private String courseCode;
	private String courseTitle;
	

	public String getCourseGrade() {
		return courseGrade;
	}

	public void setCourseGrade(String courseGrade) {
		this.courseGrade = courseGrade;
	}

	public Course() {

	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	
	public Course(String courseCode, String courseTitle, String courseGrade) {
		super();
		this.courseCode = courseCode;
		this.courseTitle = courseTitle;
		this.courseGrade = courseGrade;
	}

	public String getCourseCode() {
		return courseCode;
	}

	@Override
	public String toString() {
		String cCodeAndcTitle;
		cCodeAndcTitle = courseCode + " - " + courseTitle;
		return cCodeAndcTitle;
	}
	
}
