import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//SP25-BCS-034
//SP25-BCS-093

class resultMenuListener implements  ActionListener {
    MainGUI gui ;

    public resultMenuListener(MainGUI gui) {
        this.gui = gui;
    }

    public void actionPerformed (ActionEvent e ){

        if(e.getActionCommand().equalsIgnoreCase("add")){
            gui.addResultForm();
        } else if (e.getActionCommand().equalsIgnoreCase("view")) {
            gui.viewTranscriptForm();

        }else if (e.getActionCommand().equalsIgnoreCase("calculate")) {
            gui.calculateStatsForm();
        }else if (e.getActionCommand().equalsIgnoreCase("search")) {
            gui.searchtrnscrip();

        }else if (e.getActionCommand().equalsIgnoreCase("remove")) {
            gui.removettrnscrippt();}


    }
}

class  transcriptRemoveListener implements   ActionListener{
    MainGUI gui  ;

    public transcriptRemoveListener(MainGUI gui,  JTextField studentid, JFrame f ) {
        this.gui = gui;
        this.f = f;
        this.studentid = studentid;
    }

    JFrame f ;
    JTextField studentid ;

    @Override
    public void actionPerformed(ActionEvent e) {boolean foundstudent= false;
        boolean found = false;
        for (Student x : gui.studentList.getItems()){
            if (x.getStudentID().equalsIgnoreCase(studentid.getText())){
                foundstudent = true ;
                for (ResultEntry rs : x.getT().getRe()){
                    if ( rs.getMarksObtained()>0){
                        found = true;
                        rs.setMarksObtained(0);
                    }

                } JOptionPane.showMessageDialog(f, "Transcript Removed!");
                break;

            }
        }
        if (foundstudent==true) {
            if (found == false ) {
                JOptionPane.showMessageDialog(f, "Transcript Not Found ");
            }
        }else {
            JOptionPane.showMessageDialog(f, " Student Not Found ");
        }

    }
}

class transcriptSearchListener implements  ActionListener {
    MainGUI gui;
    JFrame f;
    JTextField studentid;

    public transcriptSearchListener(MainGUI gui, JTextField studentid, JFrame f) {
        this.gui = gui;
        this.f = f;
        this.studentid = studentid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean found = false;
        boolean foundstudent = false;
        for (Student x : gui.studentList.getItems()){
            if (x.getStudentID().equalsIgnoreCase(studentid.getText())){
                foundstudent = true ;
                for (ResultEntry rs : x.getT().getRe()){
                    if ( rs.getMarksObtained() > 0){
                        found = true;
                        JOptionPane.showMessageDialog(f, "Search Successfully!");
                        break;

                    }

                }

            }
        }
        if (foundstudent == true) {
            if (found == true) {
                gui.viewTranscriptForm();
            } else {
                JOptionPane.showMessageDialog(f, "Transcript Not Found ");
            }
        }else {
            JOptionPane.showMessageDialog(f, "Student Not Found ");
        }
    }
}

class transcriptAdd implements ActionListener {
    MainGUI gui;
    JTextField coursecode;
    JTextField studentid;
    JTextField marks;
    JFrame f;

    public transcriptAdd(MainGUI gui, JTextField coursecode, JTextField marks, JTextField studentid, JFrame f) {
        this.gui = gui;
        this.coursecode = coursecode;
        this.marks = marks;
        this.studentid = studentid;
        this.f = f;
    }


    @Override

    public void actionPerformed(ActionEvent e) {
        boolean checkstudent = false;
        boolean checkcourse = false;
        Course savecourse = null;
        for (Student st : gui.studentList.getItems()) {
            if (st.getStudentID().equalsIgnoreCase(studentid.getText())) {
                checkstudent = true;
                for (Course x : gui.courseList.getItems()) {
                    if (x.getCourseCode().equalsIgnoreCase(coursecode.getText())) {
                        checkcourse = true;
                        savecourse = x;
                        ResultEntry r = new ResultEntry(savecourse, Double.parseDouble(marks.getText()));
                        st.getT().getRe().add(r);
                        Transcript t = new Transcript(st.getT().getRe());
                        gui.transcriptList.add(t);
                        JOptionPane.showMessageDialog(f, "Transcript Added!");

                    }
                }
            }
        }
        if (!checkstudent) {
            JOptionPane.showMessageDialog(f, "student not found ");
        }
        if (checkstudent && !checkcourse) {
            JOptionPane.showMessageDialog(f, "Course not found ");
        }


    }
}

class transcriptCalculatorListener implements  ActionListener {
    MainGUI gui;
    JTextField idf;
    JFrame f;

    public transcriptCalculatorListener(MainGUI gui, JTextField idf, JFrame f) {
        this.gui = gui;
        this.idf = idf;
        this.f = f;
    }


    public void actionPerformed(ActionEvent e) {
        for (Student s : gui.studentList.getItems()) {
            if (s.getStudentID().equalsIgnoreCase(idf.getText())) {
                double total = s.calculateTotal();
                double percent = s.calculatePercentage();
                double gpa = s.getT().getGPA();

                JOptionPane.showMessageDialog(f,
                        "Total Marks: " + total +
                                "\nPercentage: " + percent +
                                "\nGPA: " + gpa);
                return;
            }
        }
        JOptionPane.showMessageDialog(f, "Student Not Found!");
    }
}

class transcriptViewListener  implements   ActionListener {
    MainGUI gui;
    JTextField idF;
    JFrame f;

    public transcriptViewListener(MainGUI gui, JTextField idF, JFrame f) {
        this.gui = gui;
        this.idF = idF;
        this.f = f;
    }

    public void actionPerformed(ActionEvent e) {

        String id = idF.getText().trim();
        boolean found = false;

        for (Student s : gui.studentList.getItems()) {
            if (s.getStudentID().equalsIgnoreCase(id)) {

                found = true;

                gui.showTranscriptWindow(s);

                return;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "Student Not Found!");
        }
    }



}

class fileOperationListener implements  ActionListener{
    MainGUI gui ;
    JFrame f;

    public fileOperationListener(MainGUI gui , JFrame f ) {
        this.gui = gui;
        this.f = f ;
    }

    public void  actionPerformed(ActionEvent e){
        if(e.getActionCommand().equalsIgnoreCase("saveStudent")){
            DataStore<Student> ds = new DataStore<>();
            ds.saveToFile("StudentsGUI.dat", gui.studentList.getItems());
            JOptionPane.showMessageDialog(f, "Students and Transcript Saved !");



        } else if (e.getActionCommand().equalsIgnoreCase("loadStudents")) {
            gui.StudentloadFromFile("StudentsGUI.dat");



        }else if (e.getActionCommand().equalsIgnoreCase("saveCourses")) {
            DataStore<Course> ds = new DataStore<>();
            ds.saveToFile("CoursesGUI.dat", gui.courseList.getItems());
            JOptionPane.showMessageDialog(f, "Courses Saved!");
        }else if (e.getActionCommand().equalsIgnoreCase("loadCourses")) {
            gui.CourseloadFromFile("CoursesGUI.dat");

        }

    }

}

class  systemListener implements ActionListener {
    MainGUI gui;
    JFrame f;

    public systemListener(MainGUI gui, JFrame f) {
        this.gui = gui;
        this.f = f;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("TotalStudents")) {
            JOptionPane.showMessageDialog(f, "Total Students: " + Student.totalStudent);


        } else if (e.getActionCommand().equalsIgnoreCase("TotalCourses")) {
            JOptionPane.showMessageDialog(f, "Total Courses: " + Course.getTotalCourses());


        } else if (e.getActionCommand().equalsIgnoreCase("Global")) {
            JOptionPane.showMessageDialog(f, "Global Pass Marks: "+Student.passMarks);

        }
    }

    class StudentMenuListener implements ActionListener {
        MainGUI gui;

        public StudentMenuListener(MainGUI gui) {
            this.gui = gui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("add")) {
                gui.addStudent();
            } else if (e.getActionCommand().equalsIgnoreCase("view")) {
                gui.viewStudents();

            } else if (e.getActionCommand().equalsIgnoreCase("search")) {
                gui.searchStudent();
            } else if (e.getActionCommand().equalsIgnoreCase("remove")) {
                gui.removeStudent();
            }

        }

    }


    class AddStudentListener implements ActionListener {
        MainGUI gui;
        JTextField idStudent;
        JTextField nameStudent;
        JTextField programmeStudent;
        JTextField typeStudent;
        JFrame f;

        public AddStudentListener(MainGUI gui, JTextField id, JTextField name, JTextField programme, JTextField type, JFrame f) {
            this.gui = gui;
            this.idStudent = id;
            this.nameStudent = name;
            this.programmeStudent = programme;
            this.typeStudent = type;
            this.f = f;
        }

        public void actionPerformed(ActionEvent e) {
            String id = idStudent.getText();
            String name = nameStudent.getText();
            String prog = programmeStudent.getText();
            String type = typeStudent.getText().trim().toLowerCase();

            Transcript t = new Transcript(); // this is needed because of composition

            Student s = null;

            if (type.equalsIgnoreCase("science"))
                s = new ScienceStudent(id, name, prog, t);
            else if (type.equalsIgnoreCase("arts"))
                s = new ArtsStudent(id, name, prog, t);
            else if (type.equalsIgnoreCase("engineering"))
                s = new EngineeringStudent(id, name, prog, t);
            else {
                JOptionPane.showMessageDialog(f, "Invalid Student Type!");
                return;
            }

            gui.studentList.add(s);
            JOptionPane.showMessageDialog(f, "Student Added!");
        }
    }
}

class StudentMenuListener implements ActionListener {
    MainGUI gui;

    public StudentMenuListener(MainGUI gui) {
        this.gui = gui;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("add")) {
            gui.addStudent();

        } else if (e.getActionCommand().equalsIgnoreCase("view")) {
            gui.viewStudents();

        } else if (e.getActionCommand().equalsIgnoreCase("search")) {
            gui.searchStudent();
        } else if (e.getActionCommand().equalsIgnoreCase("remove")) {
            gui.removeStudent();
        }
    }
}

class AddStudentListener implements ActionListener {
    MainGUI gui;
    JTextField idStudent;
    JTextField nameStudent;
    JTextField programmeStudent;
    JTextField typeStudent;
    JFrame f;

    public AddStudentListener(MainGUI gui, JTextField id, JTextField name, JTextField programme, JTextField type,JFrame f) {
        this.gui = gui;
        this.idStudent = id;
        this.nameStudent = name;
        this.programmeStudent = programme;
        this.typeStudent = type;
        this.f = f;

    }

    public void actionPerformed(ActionEvent e) {
        String id = idStudent.getText();
        String name = nameStudent.getText();
        String prog = programmeStudent.getText();
        String type = typeStudent.getText().trim().toLowerCase();

        Transcript t = new Transcript();
        Student s = null;
        boolean check = false;
        for (Student s2 : gui.studentList.getItems()) {
            if (s2.getStudentID().equalsIgnoreCase(id)) {
                check = true;
                break;
            } else {
                check = false;
            }
        }
        if (!check) {
            if (type.equalsIgnoreCase("science"))
                s = new ScienceStudent(id, name, prog, t);
            else if (type.equalsIgnoreCase("arts"))
                s = new ArtsStudent(id, name, prog, t);
            else if (type.equalsIgnoreCase("engineering"))
                s = new EngineeringStudent(id, name, prog, t);
            else {
                JOptionPane.showMessageDialog(f, "Invalid Student Type!");
                return;
            }
            gui.studentList.add(s);
            JOptionPane.showMessageDialog(f, "Student Added!");
        }
        else{
            JOptionPane.showMessageDialog(f, "Student of Same ID Already Register!");

        }
    }

}

class SearchStudentListener implements ActionListener {
    MainGUI gui;
    JTextField id;
    JFrame f;

    public SearchStudentListener(MainGUI gui, JTextField id, JFrame f) {
        this.gui = gui;
        this.id = id;
        this.f = f;
    }

    public void actionPerformed(ActionEvent e) {
        boolean check = false;
        for (Student s : gui.studentList.getItems()) {
            if (s.getStudentID().equalsIgnoreCase(id.getText())) {
                JOptionPane.showMessageDialog(f, s.toString());
                check = true;
                return;
            } else {
                check = false;
            }
        }
        if(!check){
            JOptionPane.showMessageDialog(f, "Student Not Found");
            return;
        }
    }
}

class RemoveStudentListener implements ActionListener {
    MainGUI gui;
    JTextField id;
    JFrame f;

    public RemoveStudentListener(MainGUI gui, JTextField id, JFrame f) {
        this.gui = gui;
        this.id = id;
        this.f = f;

    }

    public void actionPerformed(ActionEvent e) {
        Boolean check = false;
        for (Student s : gui.studentList.getItems()) {
            if (s.getStudentID().equalsIgnoreCase(id.getText())) {
                gui.studentList.remove(id.getText());
                check = true;
                JOptionPane.showMessageDialog(f, "Student Successfully Removed!");
                return;
            }
            else{
                check = false;
            }
        }
        if(!check){
            JOptionPane.showMessageDialog(f, "Student Not Found!");
            return;
        }
    }

}

class CourseMenuListener implements ActionListener{
    MainGUI gui;

    public CourseMenuListener(MainGUI gui) {
        this.gui = gui;

    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("add")){
            gui.addCourse();

        } else if (e.getActionCommand().equalsIgnoreCase("view")) {
            gui.viewCourses();

        }else if (e.getActionCommand().equalsIgnoreCase("remove")) {
            gui.removeCourse();

        }else if (e.getActionCommand().equalsIgnoreCase("search")) {
            gui.searchCourse();

        }
    }
}

class AddCourseListener implements ActionListener{
    MainGUI gui;
    JTextField code;
    JTextField title;
    JTextField hours;
    JTextField instructoreName;
    JTextField instructorQualifcation;
    JFrame f;
    public AddCourseListener(MainGUI gui, JTextField code, JTextField title, JTextField hours, JTextField instructoreName, JTextField instructorQualifcation, JFrame f) {
        this.gui = gui;
        this.code = code;
        this.title = title;
        this.hours = hours;
        this.instructoreName = instructoreName;
        this.instructorQualifcation = instructorQualifcation;
        this.f = f;

    }
    public void actionPerformed(ActionEvent e) {
        CourseInstructor ci = new CourseInstructor(instructoreName.getText(), instructorQualifcation.getText());
        boolean check = false;
        for (Course c2 : gui.courseList.getItems()) {
            if (c2.getCourseCode().equalsIgnoreCase(code.getText())) {
                check = true;
                break;
            } else {
                check = false;
            }
        }
        if (!check) {
            Course c = new Course(code.getText(), title.getText(), Integer.parseInt(hours.getText()), ci);
            gui.courseList.add(c);
            JOptionPane.showMessageDialog(f, "Course Added!");
        }
        else{
            JOptionPane.showMessageDialog(f, "Course of Same Code Already Register!");

        }
    }

}

class RemoveCourseListener implements ActionListener{
    MainGUI gui;
    JTextField id;
    JFrame f;
    public RemoveCourseListener(MainGUI gui, JTextField id, JFrame f) {
        this.gui = gui;
        this.id = id;
        this.f = f;
    }
    public void actionPerformed(ActionEvent e) {
        Boolean check = false;
        for (Course s : gui.courseList.getItems()) {
            if (s.getCourseCode().equalsIgnoreCase(id.getText())) {
                gui.courseList.remove(id.getText());
                check = true;
                JOptionPane.showMessageDialog(f, "Course Removed Successfully!");
                return;
            }
            else{
                check = false;
            }
        }
        if(!check){
            JOptionPane.showMessageDialog(f, "Student Not Found!");
            return;
        }
    }

}

class SearchCourseListener implements ActionListener{
    MainGUI gui;
    JTextField id;
    JFrame f;

    public SearchCourseListener(MainGUI gui, JTextField id, JFrame f) {
        this.gui = gui;
        this.id = id;
        this.f = f;
    }
    public void actionPerformed(ActionEvent e) {
        boolean check = false;
        for (Course c : gui.courseList.getItems()) {
            if (c.getCourseCode().equalsIgnoreCase(id.getText())) {
                JOptionPane.showMessageDialog(f, c.toString());
                check = true;
                return;

            } else {
                check = false;
            }

        }
        if(!check){
            JOptionPane.showMessageDialog(f, "Course Not Found");
            return;
        }
    }
}

class MainGUIListener implements ActionListener{
    MainGUI gui;

    public MainGUIListener(MainGUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("Student Management")){
            gui.studentManagementMenu();

        } else if (e.getActionCommand().equalsIgnoreCase("Course Management")) {
            gui.courseManagementMenu();

        }else if (e.getActionCommand().equalsIgnoreCase("Result / Transcript Management")) {
            gui.resultManagementMenu();

        }else if (e.getActionCommand().equalsIgnoreCase("File Operations")) {
            gui.fileOperationsMenu();
        }else if (e.getActionCommand().equalsIgnoreCase("System Reports")) {
            gui.systemReportsMenu();
        }

    }

}

public class MainGUI extends JFrame {
RecordList<Student> studentList = new RecordList<>();
RecordList<Course> courseList = new RecordList<>();
RecordList<Transcript> transcriptList = new RecordList<>();

    public MainGUI() {
        AlreadyExistedData();

        setTitle("STUDENT MANAGEMENT SYSTEM");
        setSize(500, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 139, 139));
        JLabel title = new JLabel("STUDENT MANAGEMENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton stManage = new JButton("1. Student Management");
        JButton courseManage = new JButton("2. Course Management");
        JButton resultManage = new JButton("3. Result & Transcript Management");
        JButton fileOps = new JButton("4. Data Management");
        JButton reports = new JButton("5. System Statistics");

        JButton[] allButtons = {stManage, courseManage, resultManage, fileOps, reports};

        for (JButton b : allButtons) {
            b.setFocusPainted(false);
            b.setBackground(new Color(220, 235, 250));

            b.setFont(new Font("Arial", Font.PLAIN, 14));
            b.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
            buttonPanel.add(b);
        }

        add(buttonPanel, BorderLayout.CENTER);

        stManage.setActionCommand("Student Management");
        courseManage.setActionCommand("Course Management");
        resultManage.setActionCommand("Result / Transcript Management");
        fileOps.setActionCommand("File Operations");
        reports.setActionCommand("System Reports");

        MainGUIListener mgl = new MainGUIListener(this);
        stManage.addActionListener(mgl);
        courseManage.addActionListener(mgl);
        resultManage.addActionListener(mgl);
        fileOps.addActionListener(mgl);
        reports.addActionListener(mgl);
        setVisible(true);
    }

    public void AlreadyExistedData() {
        Transcript t1 = new Transcript();
        Transcript t2 = new Transcript();

        Student s1 = new ScienceStudent("S111", "Hassan", "BS Physics", t1);
        Student s2 = new ArtsStudent("S112", "Kiran", "BA English", t2);

        studentList.add(s1);
        studentList.add(s2);

        CourseInstructor ci1 = new CourseInstructor("Dr. Salman", "PhD Physics");
        CourseInstructor ci2 = new CourseInstructor("Prof. Maria", "MA English");

        Course c1 = new Course("PHY101", "Mechanics", 3, ci1);
        Course c2 = new Course("ENG201", "English Literature", 3, ci2);

        courseList.add(c1);
        courseList.add(c2);

        t1.getRe().add(new ResultEntry(c1, 85));
        t1.getRe().add(new ResultEntry(c2, 78));

        t2.getRe().add(new ResultEntry(c1, 70));
        t2.getRe().add(new ResultEntry(c2, 88));

        transcriptList.add(t1);
        transcriptList.add(t2);

    }


    public void studentManagementMenu() {
        JFrame f = new JFrame("Student Management");
        f.setSize(490, 490);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 139, 139));
        JLabel title = new JLabel("STUDENT MANAGEMENT MENU");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(title);

        f.add(titlePanel, BorderLayout.NORTH);

        JButton add = new JButton("1.1 Add Student");
        JButton view = new JButton("1.2 View All Students");
        JButton search = new JButton("1.3 Search Student by ID");
        JButton remove = new JButton("1.4 Remove Student");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JButton[] allButtons = {add, view, search, remove};

        for (JButton b : allButtons) {
            b.setFocusPainted(false);
            b.setBackground(new Color(220, 235, 250));

            b.setFont(new Font("Arial", Font.PLAIN, 14));
            b.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
            buttonPanel.add(b);
        }
        f.add(buttonPanel, BorderLayout.CENTER);

        add.setActionCommand("add");
        view.setActionCommand("view");
        search.setActionCommand("search");
        remove.setActionCommand("remove");
        StudentMenuListener sml = new StudentMenuListener(this);
        add.addActionListener(sml);
        view.addActionListener(sml);
        search.addActionListener(sml);
        remove.addActionListener(sml);
        f.setVisible(true);

    }


    public void addStudent() {
        JFrame f = new JFrame("Add Student");
        f.setSize(350, 350);
        f.setLayout(new GridLayout(6, 2));

        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField prog = new JTextField();
        JTextField type = new JTextField();

        f.add(new JLabel("Student ID:"));
        f.add(id);

        f.add(new JLabel("Name:"));
        f.add(name);

        f.add(new JLabel("Program:"));
        f.add(prog);

        f.add(new JLabel("Student Type (Science/Arts/Engineering):"));
        f.add(type);

        JButton add = new JButton("Add");
        add.setFocusPainted(false);
        add.setBackground(new Color(220, 235, 250));

        add.setFont(new Font("Arial", Font.PLAIN, 14));
        add.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(add);

        AddStudentListener asl = new AddStudentListener(this, id, name, prog, type, f);
        add.addActionListener(asl);
        f.setVisible(true);

    }


    public void viewStudents() {
        JFrame f = new JFrame("All Students");
        f.setSize(500, 500);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        for (Student s : studentList.getItems()) {
            textArea.append(s.toString() + "\n----------------------\n");
        }

        JScrollPane scroll = new JScrollPane(textArea);
        f.add(scroll);
        f.setVisible(true);

    }


    public void searchStudent() {
        JFrame f = new JFrame("Search Student by ID");
        f.setSize(300, 200);
        f.setLayout(new GridLayout(3, 1));
        JTextField id = new JTextField();
        f.add(new JLabel("Enter Student ID:"));
        f.add(id);

        JButton search = new JButton("Search");
        search.setFocusPainted(false);
        search.setBackground(new Color(220, 235, 250));

        search.setFont(new Font("Arial", Font.PLAIN, 14));
        search.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));

        f.add(search);

        SearchStudentListener ssl = new SearchStudentListener(this, id, f);
        search.addActionListener(ssl);
        f.setVisible(true);

    }


    public void removeStudent() {
        JFrame f = new JFrame("Remove Student");
        f.setSize(300, 200);
        f.setLayout(new GridLayout(3, 1));

        JTextField id = new JTextField();
        f.add(new JLabel("Enter Student ID to Remove:"));
        f.add(id);

        JButton remove = new JButton("Remove");
        remove.setFocusPainted(false);
        remove.setBackground(new Color(220, 235, 250));

        remove.setFont(new Font("Arial", Font.PLAIN, 14));
        remove.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));

        f.add(remove);

        RemoveStudentListener ssl = new RemoveStudentListener(this, id, f);
        remove.addActionListener(ssl);
        f.setVisible(true);
    }


    public void courseManagementMenu() {
        JFrame f = new JFrame("Course Management");
        f.setSize(490, 490);
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 139, 139));
        JLabel title = new JLabel("COURSE MANAGEMENT MENU");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(title);

        f.add(titlePanel, BorderLayout.NORTH);


        JButton add = new JButton("2.1 Add Course");
        JButton view = new JButton("2.2 View All Courses");
        JButton search = new JButton("2.3 Search Course");
        JButton remove = new JButton("2.4 Remove Course");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JButton[] allButtons = {add, view, search, remove};

        for (JButton b : allButtons) {
            b.setFocusPainted(false);
            b.setBackground(new Color(220, 235, 250));

            b.setFont(new Font("Arial", Font.PLAIN, 14));
            b.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
            buttonPanel.add(b);
        }

        f.add(buttonPanel, BorderLayout.CENTER);

        add.setActionCommand("add");
        view.setActionCommand("view");
        search.setActionCommand("search");
        remove.setActionCommand("remove");

        CourseMenuListener cml = new CourseMenuListener(this);
        add.addActionListener(cml);
        view.addActionListener(cml);
        search.addActionListener(cml);
        remove.addActionListener(cml);
        f.setVisible(true);

    }


    public void addCourse() {
        JFrame f = new JFrame("Add Course");
        f.setSize(350, 350);

        f.setLayout(new GridLayout(6, 2, 5, 5));

        JTextField code = new JTextField();
        JTextField title = new JTextField();
        JTextField hours = new JTextField();
        JTextField instructorName = new JTextField();
        JTextField instructorQualification = new JTextField();

        f.add(new JLabel("Course Code:"));
        f.add(code);

        f.add(new JLabel("Title:"));
        f.add(title);

        f.add(new JLabel("Credit Hours:"));
        f.add(hours);

        f.add(new JLabel("Instructor Name:"));
        f.add(instructorName);

        f.add(new JLabel("Instructor Qualification:"));
        f.add(instructorQualification);

        JButton add = new JButton("Add");
        add.setFocusPainted(false);
        add.setBackground(new Color(220, 235, 250));

        add.setFont(new Font("Arial", Font.PLAIN, 14));
        add.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(add);

        AddCourseListener asl = new AddCourseListener(this, code, title, hours, instructorName, instructorQualification, f);
        add.addActionListener(asl);
        f.setVisible(true);

    }


    public void viewCourses() {
        JFrame f = new JFrame("All Courses");
        f.setSize(500, 500);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        for (Course c : courseList.getItems()) {
            area.append(c.toString() + "\n----------------------\n");
        }
        f.add(new JScrollPane(area));
        f.setVisible(true);
    }


    public void removeCourse() {
        JFrame f = new JFrame("Remove Course");
        f.setSize(300, 200);
        f.setLayout(new GridLayout(3, 1));

        JTextField id = new JTextField();
        f.add(new JLabel("Enter Course Code:"));
        f.add(id);

        JButton remove = new JButton("Remove");
        remove.setFocusPainted(false);
        remove.setBackground(new Color(220, 235, 250));

        remove.setFont(new Font("Arial", Font.PLAIN, 14));
        remove.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(remove);

        RemoveCourseListener rcl = new RemoveCourseListener(this, id, f);
        remove.addActionListener(rcl);
        f.setVisible(true);
    }


    public void searchCourse() {
        JFrame f = new JFrame("Search Course by ID");
        f.setSize(300, 200);
        f.setLayout(new GridLayout(3, 1));
        JTextField id = new JTextField();

        f.add(new JLabel("Enter Course ID:"));
        f.add(id);

        JButton search = new JButton("Search");
        search.setFocusPainted(false);
        search.setBackground(new Color(220, 235, 250));
        search.setFont(new Font("Arial", Font.PLAIN, 14));
        search.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(search);

        SearchCourseListener scl = new SearchCourseListener(this, id, f);
        search.addActionListener(scl);
        f.setVisible(true);

    }


    public void resultManagementMenu() {
        JFrame f = new JFrame("Result Management");
        f.setSize(490, 490);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 139, 139)); //Dark Cyan
        JLabel title = new JLabel("TRANSCRIPT MANAGEMENT MENU");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(title);

        f.add(titlePanel, BorderLayout.NORTH);

        JButton add = new JButton("3.1 Add Course Result for Student");
        JButton view = new JButton("3.2 View Transcript");
        JButton search = new JButton("3.3 Search Transcript ");
        JButton remove = new JButton("3.4 Remove Transcript ");
        JButton calc = new JButton("3.5 Calculate GPA, Total & Grade");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JButton[] allButtons = {add, view, search, remove, calc};

        for (JButton b : allButtons) {
            b.setFocusPainted(false);
            b.setBackground(new Color(220, 235, 250));

            b.setFont(new Font("Arial", Font.PLAIN, 14));
            b.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
            buttonPanel.add(b);
        }

        f.add(buttonPanel, BorderLayout.CENTER);

        add.setActionCommand("add");
        view.setActionCommand("view");
        search.setActionCommand("search");
        remove.setActionCommand("remove");
        calc.setActionCommand("calculate");

        resultMenuListener tsml = new resultMenuListener(this);
        add.addActionListener(tsml);
        view.addActionListener(tsml);
        search.addActionListener(tsml);
        remove.addActionListener(tsml);
        calc.addActionListener(tsml);

        f.setVisible(true);
    }


    public void addResultForm() {
        JFrame f = new JFrame("Add Result");
        f.setSize(400, 400);
        f.setLayout(new GridLayout(6, 1));

        JTextField stid = new JTextField();
        JTextField code = new JTextField();
        JTextField marks = new JTextField();

        f.add(new JLabel("Student ID:"));
        f.add(stid);
        f.add(new JLabel("Course Code:"));
        f.add(code);
        f.add(new JLabel("Marks Obtained:"));
        f.add(marks);

        JButton add = new JButton("Add Result");
        add.setFocusPainted(false);
        add.setBackground(new Color(220, 235, 250));
        add.setFont(new Font("Arial", Font.PLAIN, 14));
        add.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(add);

        transcriptAdd tal = new transcriptAdd(this, code, marks, stid, f);
        add.addActionListener(tal);
        f.setVisible(true);
    }


    public void viewTranscriptForm() {
        JFrame f = new JFrame("View Transcript");
        f.setSize(400, 300);
        f.setLayout(new GridLayout(3, 1));

        JTextField idF = new JTextField();
        f.add(new JLabel("Enter Student ID:"));
        f.add(idF);

        JButton view = new JButton("View Transcript");
        view.setFocusPainted(false);
        view.setBackground(new Color(220, 235, 250));
        view.setFont(new Font("Arial", Font.PLAIN, 14));
        view.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(view);

        transcriptViewListener rvl = new transcriptViewListener(this, idF, f);
        view.addActionListener(rvl);

        f.setVisible(true);
    }


    public void showTranscriptWindow(Student s) {

        JFrame frame = new JFrame("Transcript");
        frame.setSize(650, 750);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        Color panelColor = new Color(220, 235, 250);
        Color headerColor = new Color(0, 128, 128);
        Font headerFont = new Font("Arial", Font.BOLD, 18);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(headerColor);
        titlePanel.setBounds(0, 0, 650, 50);

        JLabel title = new JLabel("STUDENT TRANSCRIPT", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(headerFont);
        titlePanel.add(title);
        frame.add(titlePanel);

        JPanel info = new JPanel();
        info.setLayout(new GridLayout(3, 1));
        info.setBackground(panelColor);
        info.setBounds(50, 70, 550, 90);

        info.add(new JLabel("  Student ID: " + s.getStudentID()));
        info.add(new JLabel("  Name: " + s.getName()));
        info.add(new JLabel("  Program: " + s.getProgram()));

        frame.add(info);
        Transcript t = s.getT();

        String[] columns = {"Course Code", "Course Title", "CH", "Marks"};
        String[][] data = new String[t.getRe().size()][4];

        int i = 0;
        for (ResultEntry r : t.getRe()) {
            data[i][0] = r.getCourse().getCourseCode();
            data[i][1] = r.getCourse().getTitle();
            data[i][2] = String.valueOf(r.getCourse().getCreditHours());
            data[i][3] = String.valueOf(r.getMarksObtained());
            i++;
        }

        JTable table = new JTable(data, columns);
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 180, 550, 300);

        JPanel MARKS = new JPanel();
        MARKS.setLayout(new GridLayout(2, 1));
        MARKS.setBackground(panelColor);
        MARKS.setBounds(50, 180 + 300 + 10, 550, 60);


        MARKS.add(new JLabel("  Total Marks  " + t.getTotalMarks()));
        MARKS.add(new JLabel("  GPA: " + t.getGPA()));

        frame.add(MARKS);

        frame.add(scroll);
        frame.setVisible(true);
    }


    public void calculateStatsForm() {

        JFrame f = new JFrame("Calculate GPA & Stats");
        f.setSize(400, 300);
        f.setLayout(new GridLayout(3, 1));

        JTextField idF = new JTextField();
        f.add(new JLabel("Enter Student ID:"));
        f.add(idF);

        JButton calc = new JButton("Calculate");
        calc.setFocusPainted(false);
        calc.setBackground(new Color(220, 235, 250));
        calc.setFont(new Font("Arial", Font.PLAIN, 14));
        calc.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(calc);

        transcriptCalculatorListener rcl = new transcriptCalculatorListener(this, idF, f);
        calc.addActionListener(rcl);

        f.setVisible(true);
    }


    public void searchtrnscrip() {
        JFrame f = new JFrame("Search  Transcript");
        f.setSize(400, 300);
        f.setLayout(new GridLayout(3, 1));

        JTextField idF = new JTextField();
        f.add(new JLabel("Enter Student ID:"));
        f.add(idF);

        JButton search = new JButton(" Search");
        search.setFocusPainted(false);
        search.setBackground(new Color(220, 235, 250));
        search.setFont(new Font("Arial", Font.PLAIN, 14));
        search.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(search);

        transcriptSearchListener tsl = new transcriptSearchListener(this, idF, f);
        search.addActionListener(tsl);
        f.setVisible(true);
    }


    public void removettrnscrippt() {
        JFrame f = new JFrame("Remove  Transcript");
        f.setSize(400, 300);
        f.setLayout(new GridLayout(3, 1));

        JTextField idF = new JTextField();
        f.add(new JLabel("Enter Student ID:"));
        f.add(idF);

        JButton remove = new JButton(" Remove");
        remove.setFocusPainted(false);
        remove.setBackground(new Color(220, 235, 250));
        remove.setFont(new Font("Arial", Font.PLAIN, 14));
        remove.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
        f.add(remove);

        transcriptRemoveListener trl = new transcriptRemoveListener(this, idF, f);
        remove.addActionListener(trl);
        f.setVisible(true);
    }


    public void fileOperationsMenu() {
        JFrame f = new JFrame("Data Management");
        f.setSize(400, 400);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 139, 139));
        JLabel title = new JLabel("DATA MANAGEMENT");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(title);
        f.add(titlePanel, BorderLayout.NORTH);

        JButton saveStudents = new JButton("4.1 Save All Students & Transcripts");
        JButton loadStudents = new JButton("4.2 Retrieve Students & Transcripts");
        JButton saveCourses = new JButton("4.3 Save All Courses");
        JButton loadCourses = new JButton("4.4 Retrieve All Courses");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JButton[] allButtons = {saveStudents, loadStudents, saveCourses, loadCourses};

        for (JButton b : allButtons) {
            b.setFocusPainted(false);
            b.setBackground(new Color(220, 235, 250));

            b.setFont(new Font("Arial", Font.PLAIN, 14));
            b.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
            buttonPanel.add(b);
        }
        f.add(buttonPanel, BorderLayout.CENTER);

        saveStudents.setActionCommand("saveStudent");
        loadStudents.setActionCommand("loadStudents");
        saveCourses.setActionCommand("saveCourses");
        loadCourses.setActionCommand("loadCourses");

        fileOperationListener fl = new fileOperationListener(this, f);
        saveStudents.addActionListener(fl);
        loadStudents.addActionListener(fl);
        saveCourses.addActionListener(fl);
        loadCourses.addActionListener(fl);

        f.setVisible(true);
    }


    public void StudentloadFromFile(String fileName) {

        FileOutputStream fis;
        JFrame f = new JFrame();
        ObjectOutputStream write;
        FileInputStream fin;
        ObjectInputStream read = null;
        try {


            fin = new FileInputStream(fileName + ".dat");
            read = new ObjectInputStream(fin);
            String save = "";
            int count = 1;
            while (true) {
                try {

                    Student object = (Student) read.readObject();
                    save += "Student   #  " + count + "\n";
                    save += object.toString();
                    save += " \n";
                    count++;
                } catch (EOFException e) {
                    count--;
                    break;
                }


            }
            JOptionPane.showMessageDialog(f, save);

        } catch (EOFException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if (read != null) {
                read.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void CourseloadFromFile(String fileName) {

        FileOutputStream fis;
        JFrame f = new JFrame();
        ObjectOutputStream write;
        FileInputStream fin;
        ObjectInputStream read = null;
        try {
            fin = new FileInputStream(fileName + ".dat");
            read = new ObjectInputStream(fin);
            String save = "";
            int count = 1;

            while (true) {
                try {
                    save += "Course   #  " + count + "\n";

                    Course object = (Course) read.readObject();
                    save += object.toString();
                    save += " \n";
                    count++;
                } catch (EOFException e) {
                    break;
                }
            }
            JOptionPane.showMessageDialog(f, save);
        } catch (EOFException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            if (read != null) {
                read.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void systemReportsMenu() {
        JFrame f = new JFrame("System Statistics");
        f.setSize(400, 400);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 139, 139));
        JLabel title = new JLabel("SYSTEM STATISTICS");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(title);
        f.add(titlePanel, BorderLayout.NORTH);

        JButton stCount = new JButton("5.1 Total Students");
        JButton cCount = new JButton("5.2 Total Courses");
        JButton passBtn = new JButton("5.3 Global Pass Marks");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        JButton[] allButtons = {stCount, cCount, passBtn};

        for (JButton b : allButtons) {
            b.setFocusPainted(false);
            b.setBackground(new Color(220, 235, 250));

            b.setFont(new Font("Arial", Font.PLAIN, 14));
            b.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237)));
            buttonPanel.add(b);
        }

        f.add(buttonPanel, BorderLayout.CENTER);

        stCount.setActionCommand("TotalStudents");
        cCount.setActionCommand("TotalCourses");
        passBtn.setActionCommand("Global");

        systemListener sl = new systemListener(this, f);
        stCount.addActionListener(sl);
        cCount.addActionListener(sl);
        passBtn.addActionListener(sl);
        f.setVisible(true);
    }


    public static void main(String[] args) {
        new MainGUI();
    }
}
