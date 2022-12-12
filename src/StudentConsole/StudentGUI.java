package StudentConsole;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JLabel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class StudentGUI extends JFrame {

	private JPanel contentPane;
	private JList<Course> courseList;
	private JLabel courseLBL;
	private JComboBox gradeComboBox;
	private JButton saveBtn;
	private JComboBox nameIDComboBox;
	private JLabel CourseLBL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					StudentGUI frame = new StudentGUI();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setupComponents(ArrayList<Student> studentRecords) {
		// Summon objects that will populate console
	
		Student student = new Student();


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		nameIDComboBox = new JComboBox();
		nameIDComboBox.setBounds(17, 17, 199, 24);
		nameIDComboBox.insertItemAt("", 0);

		courseList = new JList();
		courseList.setBounds(17, 59, 358, 187);

		courseLBL = new JLabel("");
		courseLBL.setFont(new Font("Dialog", Font.BOLD, 14));
		courseLBL.setBounds(416, 0, -136, 36);

		gradeComboBox = new JComboBox();
		gradeComboBox.setBounds(419, 52, 116, 28);
		gradeComboBox.setFont(new Font("Dialog", Font.BOLD, 16));
		gradeComboBox.setEnabled(false);
		gradeComboBox.setModel(new DefaultComboBoxModel(Grade.values()));
		gradeComboBox.setSelectedIndex(6);

		saveBtn = new JButton("Save");
		saveBtn.setBounds(434, 101, 68, 25);
		saveBtn.setEnabled(false);

		

		// Get objects for nameIDComboBox = name(sID)
		studentRecords = student.cleanStudentRecords(studentRecords);
		for (int i = 0; i < studentRecords.size(); i++) {
			nameIDComboBox.addItem(studentRecords.get(i));
		}
		
		contentPane.setLayout(null);
		contentPane.add(nameIDComboBox);
		contentPane.add(courseList);
		contentPane.add(courseLBL);
		contentPane.add(saveBtn);
		contentPane.add(gradeComboBox);
		
		CourseLBL = new JLabel("");
		CourseLBL.setBounds(397, 11, 218, 37);
		contentPane.add(CourseLBL);

	}

	public void createEvents() {
		Student student = new Student();

		courseList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// when list item is selected label will change to course title and grade button
				// will take on object value


				Course c = courseList.getSelectedValue();
				CourseLBL.setText(c.getCourseTitle());


				// disable grade combobox if grade != NA
				String selectedGrade = c.getCourseGrade();
				String gradeUpdate = "P";
				if (selectedGrade.equals("A")) {
					gradeComboBox.setSelectedIndex(0);
					gradeUpdate = "A";
				} else if (selectedGrade.equals("B")) {
					gradeComboBox.setSelectedIndex(1);
					gradeUpdate = "B";
				} else if (selectedGrade.equals("C")) {
					gradeComboBox.setSelectedIndex(2);
					gradeUpdate = "C";
				} else if (selectedGrade.equals("D")) {
					gradeComboBox.setSelectedIndex(3);
					gradeUpdate = "D";
				} else if (selectedGrade.equals("P")) {
					gradeComboBox.setSelectedIndex(4);
					gradeUpdate = "P";
				} else if (selectedGrade.equals("F")) {
					gradeComboBox.setSelectedIndex(5);
					gradeUpdate = "F";
				} else if (selectedGrade.equals("NA")) {
					gradeComboBox.setSelectedIndex(6);
					gradeUpdate = "NA";
				}

				if (selectedGrade.equals("NA")) {
					gradeComboBox.setEnabled(true);
					saveBtn.setEnabled(true);
					saveBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(null, "Grade Updated!");
							Student studentToUpdate = (Student) nameIDComboBox.getSelectedItem();
							Course courseToUpdate = courseList.getSelectedValue();
							String cCodeUpdate = courseToUpdate.getCourseCode();
							String sIDUpdate = studentToUpdate.getsID();
							// update record
							String gradeUpdate = gradeComboBox.getSelectedItem().toString();
							student.updateStudentRecords(cCodeUpdate, sIDUpdate, gradeUpdate);
							// refresh GUI
							if (gradeUpdate.equals("NA")){
								//nothing happens because NA grades can still be edited
							}else {
								gradeComboBox.setEnabled(false);
								saveBtn.setEnabled(false);
							}
							

							
						}

					});

				} else {
					gradeComboBox.setEnabled(false);
					saveBtn.setEnabled(false);
				}

			}
		});

		// The List must populate with the selected objects Courses when combobox item
		// is
		// selected
		nameIDComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<Course> model = new DefaultListModel<>();

				courseList.setModel(model);
				Student selectedStudent = (Student) nameIDComboBox.getSelectedItem();
				ArrayList<Course> selectedStudentCourses = selectedStudent.studentCoursesRecord;

				for (int x = 0; x < selectedStudentCourses.size(); x++) {
					model.addElement(selectedStudentCourses.get(x));
				}

			}
		});

	}

	/**
	 * Create the frame.
	 */
	public StudentGUI() {
		Student student = new Student();
		ArrayList<Student> studentRecords = student.readDB();
		setupComponents(studentRecords);
		createEvents();

	}
}
