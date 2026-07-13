import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//SP25-BCS-034
//SP25-BCS-093

interface ResultCalculator{
    static final double passMarks = 50;
    double calculateTotal();
    void  calculateGrade();
    double calculatePercentage();
}
abstract class Student implements ResultCalculator,Serializable{
    private String studentID;
    private String name;
    private String program;
    private Transcript t;
    static int totalStudent;

    public Student(String studentID, String name, String program, Transcript t) {
        this.studentID = studentID;
        this.name = name;
        this.program = program;
        this.t = t;
        totalStudent++;
    } public Student() {
        this.studentID = "03";
        this.name = "Ali";
        this.program = "Cyber Security";
        this.t = new Transcript();
        totalStudent++;

    }

    public static int getTotalStudent() {
        return totalStudent;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Transcript getT() {
        return t;
    }

    public void setT(Transcript t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "\nStudentID: " + studentID +
                "\nName: " + name +
                "\nProgram: " + program +
                "\n" + t.toString();
    }

    abstract public void addCourse(String courseCode, String title, int creditHours, double marks, CourseInstructor ci);
    abstract public void calculateGpa();
    abstract public void displayResults();
    abstract public double calculateTotal();
    abstract public void calculateGrade();
    abstract public double calculatePercentage();
}
class ScienceStudent extends Student implements Serializable{
    public ScienceStudent(String studentID, String name, String program, Transcript t) {
        super(studentID, name, program, t);
    }
    public ScienceStudent() {
        super();
    }
    public void addCourse(String courseCode, String title, int creditHours, double marks, CourseInstructor ci){
        Course c = new Course(courseCode,title,creditHours,ci);
        ResultEntry re = new ResultEntry(c,marks);
        super.getT().addResultEntry(re);
    }
    public void calculateGpa(){
        System.out.printf("GPA: %.2f\n",super.getT().getGPA());

    }
    public void displayResults(){
        System.out.println("Student ID: "+super.getStudentID()+"\nStudent Name: "+ super.getName()+"\nProgram: "+super.getProgram());
    }
    public double calculateTotal(){
        return super.getT().getTotalMarks();
    }
    public double calculatePercentage() {
        int total_courses = super.getT().getRe().size();
        if (total_courses == 0) {
            return 0;
        }
        else {
            return calculateTotal() / total_courses;
        }
    }


    @Override
    public void calculateGrade() {
        super.getT().getOverallGrade();
    }

    @Override
    public String toString() {
        return "ScienceStudent:  " + super.toString();
    }
}
class ArtsStudent extends Student implements Serializable{
    public ArtsStudent(String studentID, String name, String program, Transcript t) {
        super(studentID, name, program, t);
    }
    public ArtsStudent() {
        super();
    }
    public void addCourse(String courseCode, String title, int creditHours, double marks, CourseInstructor ci){
        Course c = new Course(courseCode,title,creditHours,ci);
        ResultEntry re = new ResultEntry(c,marks);
        super.getT().addResultEntry(re);
    }
    public void calculateGpa(){
        System.out.printf("GPA: %.2f\n",super.getT().getGPA());

    }
    public void displayResults(){
        System.out.println("Student ID: "+super.getStudentID()+"\nStudent Name: "+ super.getName()+"\nProgram: "+super.getProgram());
    }
    public double calculateTotal(){
        return super.getT().getTotalMarks();
    }
    public double calculatePercentage() {
        int total_courses = super.getT().getRe().size();
        if (total_courses == 0) {
            return 0;
        }
        else {
            return calculateTotal() / total_courses;
        }
    }

    @Override
    public void calculateGrade() {
        super.getT().getOverallGrade();
    }

    @Override
    public String toString() {
        return "ArtsStudent: " + super.toString();
    }
}class EngineeringStudent extends Student implements Serializable{
    public EngineeringStudent(String studentID, String name, String program, Transcript t) {
        super(studentID, name, program, t);
    }
    public EngineeringStudent() {
        super();
    }
    public void addCourse(String courseCode, String title, int creditHours, double marks, CourseInstructor ci){
        Course c = new Course(courseCode,title,creditHours,ci);
        ResultEntry re = new ResultEntry(c,marks);
        super.getT().addResultEntry(re);
    }
    public void calculateGpa(){
        System.out.printf("GPA: %.2f\n",super.getT().getGPA());
    }
    public void displayResults(){
        System.out.println("Student ID: "+super.getStudentID()+"\nStudent Name: "+ super.getName()+"\nProgram: "+super.getProgram());
    }
    public double calculateTotal(){
        return super.getT().getTotalMarks();
    }
    public double calculatePercentage() {
        int total_courses = super.getT().getRe().size();
        if (total_courses == 0) {
            return 0;
        }
        else {
            return calculateTotal() / total_courses;
        }

    }

    @Override
    public void calculateGrade() {
        super.getT().getOverallGrade();
    }

    @Override
    public String toString() {
        return "EngineeringStudent: " + super.toString();
    }
}
class Transcript implements Serializable {
    private ArrayList<ResultEntry> re;

    public Transcript(ArrayList<ResultEntry> re) {
        this.re = re;
    }

    public Transcript() {
        this.re = new ArrayList<ResultEntry>();
    }

    public ArrayList<ResultEntry> getRe() {
        return re;
    }

    public void setRe(ArrayList<ResultEntry> re) {
        this.re = re;
    }

    public void addResultEntry(ResultEntry r) {
        re.add(r);
    }

    public double getTotalMarks() {
        double total = 0;
        for (ResultEntry r : re) {
            total += r.getMarksObtained();
        }
        return total;
    }

    public double getGPA() {
        double total_credit = 0;
        double total_qualityPoints = 0;
        for (ResultEntry r : re) {
            double gp = 0.0;
            double percentage = r.getMarksObtained();
            if (percentage >= 85) {
                gp = 4.0;
            } else if (percentage >= 80) {
                gp = 3.7;
            } else if (percentage >= 75) {
                gp = 3.4;
            } else if (percentage >= 70) {
                gp = 3.0;
            } else if (percentage >= 65) {
                gp = 2.7;
            } else if (percentage >= 60) {
                gp = 2.4;
            } else if (percentage >= 55) {
                gp = 2.0;
            } else if (percentage >= ResultCalculator.passMarks) {
                gp = 1.0;
            } else {
                gp = 0.0;
            }
            double quality_points = gp * r.getCourse().getCreditHours();
            total_qualityPoints += quality_points;
            total_credit += r.getCourse().getCreditHours();
        }
        if (total_credit == 0) {
            return 0.0;
        }else {
            double gpa = total_qualityPoints / total_credit;
            return gpa;
        }
    }

    public void getOverallGrade() {
        double gpa = getGPA();
        if (gpa >= 3.7) {
            System.out.println("Grade: A");
        } else if (gpa >= 3.5) {
            System.out.println("Grade: A-");
        } else if (gpa >= 3.3) {
            System.out.println("Grade: B+");
        } else if (gpa >= 3.0) {
            System.out.println("Grade: B");
        } else if (gpa >= 2.7) {
            System.out.println("Grade: B-");
        } else if (gpa >= 2.3) {
            System.out.println("Grade: C+");
        } else if (gpa >= 2.0) {
            System.out.println("Grade: C");
        } else if (gpa >= 1.7) {
            System.out.println("Grade: C-");
        } else if (gpa >= 1.0) {
            System.out.println("Grade: D");
        } else {
            System.out.println("Grade: F");
        }
    }

    @Override
    public String toString() {
        String store = "";

        store += "============================================================\n";
        store += String.format(
                "%-12s | %-25s | %-3s | %-5s %n",
                "Course Code", "Course Title", "CH", "Marks"
        );
        store += "------------------------------------------------------------\n";

        for (ResultEntry r : re) {
            store += String.format(
                    "%-12s | %-25s | %-3d | %-5.0f %n",
                    r.getCourse().getCourseCode(),
                    r.getCourse().getTitle(),
                    r.getCourse().getCreditHours(),
                    r.getMarksObtained()
            );
        }

        store += "============================================================\n";
        return store;
    }

}



class ResultEntry implements Serializable{
    private Course course;
    private double marksObtained;

    public ResultEntry(Course course, double marksObtained) {
        this.course = course;
        this.marksObtained = marksObtained;
    } public ResultEntry() {
        this.course = new Course();
        this.marksObtained = 0.0;
    }


    public double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "\n>>Course " + course.toString() +
                "\nMarksObtained: " + marksObtained;
    }
}
class Course implements Serializable{
    private String courseCode;
    private String title;
    private int creditHours;
    private static int totalCourses;
    private CourseInstructor ci;

    public static int getTotalCourses() {
        return totalCourses;
    }

    public CourseInstructor getCi() {
        return ci;
    }

    public void setCi(CourseInstructor ci) {
        this.ci = ci;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public Course(String courseCode, String title, int creditHours,CourseInstructor ci ) {
        this.courseCode = courseCode;
        this.title = title;
        this.creditHours = creditHours;
        this.ci = ci;
        totalCourses++;
    }public Course() {
        this.courseCode = null;
        this.title = null;
        this.creditHours = 0;
        this.ci = new CourseInstructor();
        totalCourses++;
    }

    public String displayCourseDetails() {
        return "Course: " + "\ncourseCode: " + courseCode +
                "\nTitle: " + title +
                "\nCreditHours: " + creditHours +
                "\nCourse Instructor: " + ci.toString();
    }public String toString() {
        return "\nCourseCode: " + courseCode +
                "\nTitle: " + title +
                "\nCreditHours: " + creditHours +
                "\n>>Course Instructor " + ci.toString();
    }
}
class CourseInstructor implements Serializable{
    private String name;
    private String qualification;

    public CourseInstructor(String name, String qualification) {
        this.name = name;
        this.qualification = qualification;
    } public CourseInstructor() {
        this.name = null;
        this.qualification = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return  "\nInstructor Name: " + name +
                "\nQualification: " + qualification;
    }
}
class RecordList<T>{
    ArrayList<T> items;
    RecordList(ArrayList<T> items){
        this.items = items;
    }
    RecordList(){
        this.items = new ArrayList<>();
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }
    public void add(T t){
        items.add(t);
    }
    public void search(String id){
        boolean check = false;
        for(int k = 0; k < items.size(); k++){
            T i = items.get(k);
            if(i instanceof Student){
                if(((Student) i).getStudentID().equalsIgnoreCase(id)){
                    System.out.println("Student Found");
                    System.out.println(i.toString());
                    check = true;
                    break;
                }
                else {
                    check = false;
                }
            }else if(i instanceof Course){
                if(((Course) i).getCourseCode().equalsIgnoreCase(id)){
                    System.out.println("Course Found");
                    System.out.println(i.toString());
                    check = true;
                    break;
                }
                else {
                    check = false;
                }
            }else if(i instanceof Transcript){
                Transcript tr = (Transcript) i;
                boolean search = false;

                for (int x = 0; x < tr.getRe().size(); x++) {
                    ResultEntry r = tr.getRe().get(x);
                    if (r.getCourse().getCourseCode().equalsIgnoreCase(id)) {
                        System.out.println(tr.getRe().get(x).toString());
                        check = true;
                        break;
                    }
                }
            }
        }
        if(!check){
            System.out.println("ID not Found!");
        }
    }
    public void remove(String id){
        T itemtoremove = null;
        boolean check = false;
        for(int k = 0; k < items.size(); k++) {
            T i = items.get(k);
            if (i instanceof Student) {
                if (((Student) i).getStudentID().equalsIgnoreCase(id)) {
                    itemtoremove = i;
                }
            } else if (i instanceof Course) {
                if(((Course) i).getCourseCode().equalsIgnoreCase(id)){
                    itemtoremove = i;


                }
            } else if (i instanceof Transcript) {
                Transcript tr = (Transcript) i;
                boolean removed = false;

                for (int x = 0; x < tr.getRe().size(); x++) {
                    ResultEntry r = tr.getRe().get(x);
                    if (r.getCourse().getCourseCode().equalsIgnoreCase(id)) {
                        tr.getRe().remove(x);
                        removed = true;
                        break;
                    }
                }

                if (removed) {
                    System.out.println("Course removed from Transcript!");
                } else {
                    System.out.println("Course NOT found in this Transcript");
                }
            }
        }
        if(itemtoremove != null){
            items.remove(itemtoremove);
            check = true;
        }
        if(!check){
            System.out.println("ID not Found");
        }
    }
    public void getAll(){
        for(T i : items){
            System.out.println(i.toString()+"\n");
        }
    }
}
class DataStore<T> {
    private FileOutputStream fis;
    private ObjectOutputStream write;
    private FileInputStream fin;
    private ObjectInputStream read;

    public void saveToFile(String Filename, ArrayList<T> items) {
        try {
            fis = new FileOutputStream(Filename + ".dat");
            write = new ObjectOutputStream(fis);
            if (items != null) {
                for (T i : items) {
                    write.writeObject(i);
                }
            }
            System.out.println("Record Successfully Saved to the File");

            if(write != null){
                write.close();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadFromFile(String fileName){
        try{
            fin = new FileInputStream(fileName+".dat");
            read = new ObjectInputStream(fin);
            System.out.println("Loading form File");
            while(true){
                T object = (T) read.readObject();
                System.out.println(object.toString());
            }
        }
        catch (EOFException e){

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try{
            if(read != null){
                read.close();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
public class Project {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RecordList<Student> students = new RecordList<>();
        RecordList<Course> courses = new RecordList<>();
        DataStore<Student> studentDataStore = new DataStore<>();
        DataStore<Course> courseDataStore = new DataStore<>();

        CourseInstructor ci1 = new CourseInstructor("Dr. Salman", "PhD Physics");
        CourseInstructor ci2 = new CourseInstructor("Prof. Maria", "MA English");

        ArrayList<Course> cc = new ArrayList<>();
        Course c1 = new Course("PHY101", "Mechanics", 3, ci1);
        Course c2 = new Course("ENG201", "English Literature", 3, ci2);
        cc.add(c1);
        cc.add(c2);

        Transcript t1 = new Transcript(new ArrayList<>());
        Transcript t2 = new Transcript(new ArrayList<>());
        t1.getRe().add(new ResultEntry(c1, 85));
        t1.getRe().add(new ResultEntry(c2, 78));

        t2.getRe().add(new ResultEntry(c1, 70));
        t2.getRe().add(new ResultEntry(c2, 88));

        ArrayList<Student> s = new ArrayList<>();
        Student s1 = new ScienceStudent("S111", "Hassan", "BS Physics", t1);
        Student s2 = new ArtsStudent("S112", "Kiran", "BA English", t2);
        s.add(s1);
        s.add(s2);

        students = new RecordList<>(s);
        courses  = new RecordList<>(cc);

        while (true) {
            try {
                System.out.println("\n==============================");
                System.out.println(" STUDENT RESULT MANAGEMENT SYSTEM");
                System.out.println("==============================");
                System.out.println("1. Student Management");
                System.out.println("2. Course Management");
                System.out.println("3. Result & Transcript Management");
                System.out.println("4. Data Management");
                System.out.println("5. System Statistics");
                System.out.println("6. Exit Program");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    boolean studentMenu = true;
                    while (studentMenu) {
                        try {
                            System.out.println("\n--- Student Management ---");
                            System.out.println("1. Add Student");
                            System.out.println("2. View All Students");
                            System.out.println("3. Search Student by ID");
                            System.out.println("4. Remove Student");
                            System.out.println("5. Back to Main Menu");
                            System.out.print("Enter your choice: ");
                            int sChoice = sc.nextInt();
                            sc.nextLine();

                            if (sChoice == 1) {
                                System.out.print("Enter Student Type (1-Science, 2-Arts, 3-Engineering): ");
                                int type = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Enter Student ID: ");
                                String id = sc.nextLine();
                                System.out.print("Enter Name: ");
                                String name = sc.nextLine();
                                System.out.print("Enter Program: ");
                                String program = sc.nextLine();
                                Student ss = null;
                                boolean check = false;
                                for(Student s11 : students.getItems()){
                                    if(s11.getStudentID().equalsIgnoreCase(id)){
                                        System.out.println("Student of Same ID Already Exists!");
                                        check = true;
                                        break;
                                    }
                                    else{
                                        check = false;
                                    }
                                }
                                if(!check) {
                                    if (type == 1) {
                                        ss = new ScienceStudent(id, name, program, new Transcript());
                                    }
                                    else if (type == 2) {
                                        ss = new ArtsStudent(id, name, program, new Transcript());
                                    }
                                    else if (type == 3) {
                                        ss = new EngineeringStudent(id, name, program, new Transcript());
                                    }
                                    else {
                                        System.out.println("Invalid student type!");
                                        continue;
                                    }
                                    students.add(ss);
                                    System.out.println("Student Added!");
                                }
                            } else if (sChoice == 2) {
                                students.getAll();
                            } else if (sChoice == 3) {
                                System.out.print("Enter Student ID to search: ");
                                String searchId = sc.nextLine();
                                boolean found = false;
                                for (Student stu : students.getItems()) {
                                    if (stu.getStudentID().equalsIgnoreCase(searchId)) {
                                        System.out.println(stu);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    System.out.println("Student not found!");
                                }
                            } else if (sChoice == 4) {
                                System.out.print("Enter Student ID to remove: ");
                                String removeId = sc.nextLine();
                                students.remove(removeId);
                                System.out.println("Student Successfully Removed!");
                            } else if (sChoice == 5) {
                                studentMenu = false;
                            } else {
                                System.out.println("Invalid choice!");
                            }
                        } catch (InputMismatchException e){
                            System.out.println("Invalid input! Please enter a number.");
                            sc.nextLine();
                        }
                    }

                } else if (choice == 2) {
                    boolean courseMenu = true;
                    while (courseMenu) {
                        try {
                            System.out.println("\n--- Course Management ---");
                            System.out.println("1. Add Course");
                            System.out.println("2. View All Courses");
                            System.out.println("3. Remove Course");
                            System.out.println("4. Search Course");
                            System.out.println("5. Back to Main Menu");
                            System.out.print("Enter your choice: ");
                            int cChoice = sc.nextInt();
                            sc.nextLine();

                            if (cChoice == 1) {
                                System.out.print("Enter Course Code: ");
                                String code = sc.nextLine();
                                System.out.print("Enter Title: ");
                                String title = sc.nextLine();
                                System.out.print("Enter Credit Hours: ");
                                int ch = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Enter Instructor Name: ");
                                String iname = sc.nextLine();
                                System.out.print("Enter Instructor Qualification: ");
                                String iq = sc.nextLine();
                                CourseInstructor cii = new CourseInstructor(iname, iq);
                                boolean check = false;
                                for(Course c : cc){
                                    if(c.getCourseCode().equalsIgnoreCase(code)){
                                        System.out.println("Course of Same Code Already Exists");
                                        check = true;
                                        break;
                                    }
                                    else{
                                        check = false;
                                    }

                                }
                                if(!check) {
                                    Course course = new Course(code, title, ch, cii);
                                    courses.add(course);
                                    System.out.println("Course Added!");
                                }
                            } else if (cChoice == 2) {
                                courses.getAll();

                            } else if (cChoice == 3) {
                                System.out.print("Enter Course Code to remove: ");
                                String removeCode = sc.nextLine();
                                courses.remove(removeCode);
                                System.out.println("Course Successfully Removed!");

                            }else if (cChoice==4){
                                System.out.print("Enter Course Code to search: ");
                                String searchId = sc.nextLine();
                                boolean found = false;
                                for (Course c : courses.getItems()) {
                                    if (c.getCourseCode().equalsIgnoreCase(searchId)) {
                                        System.out.println(c);
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    System.out.println("Course not found!");
                                }


                            }
                            else if (cChoice == 5) {
                                courseMenu = false;
                            } else {
                                System.out.println("Invalid choice!");
                            }

                        } catch (InputMismatchException e){
                            System.out.println("Invalid input! Please enter a number.");
                            sc.nextLine();
                        }
                    }

                } else if (choice == 3) {
                    boolean resultMenu = true;
                    while (resultMenu) {
                        try {
                            System.out.println("\n--- Result / Transcript Management ---");
                            System.out.println("1. Add Transcript for a Student");
                            System.out.println("2. View Transcript of a Student");
                            System.out.println("3. Search Transcript ");
                            System.out.println("4. Remove Transcript ");
                            System.out.println("5. Calculate GPA, Total Marks & Grade");
                            System.out.println("6. Back to Main Menu");
                            System.out.print("Enter your choice: ");
                            int rChoice = sc.nextInt();
                            sc.nextLine();

                            if (rChoice == 1) {
                                System.out.print("Enter Student ID: ");
                                String sid = sc.nextLine();
                                Student student = null;
                                for (Student stu : students.getItems()) {
                                    if (stu.getStudentID().equalsIgnoreCase(sid)) {
                                        student = stu;
                                        break;
                                    }
                                }
                                if (student == null) {
                                    System.out.println("Student not found!");
                                    continue;
                                }
                                System.out.print("Enter Course Code: ");
                                String ccc = sc.nextLine();
                                Course selectedCourse = null;
                                for (Course crs : courses.getItems()) {
                                    if (crs.getCourseCode().equalsIgnoreCase(ccc)) {
                                        selectedCourse = crs;
                                        break;
                                    }
                                }
                                if (selectedCourse == null) {
                                    System.out.println("Course not found!");
                                    continue;
                                }
                                System.out.print("Enter Marks Obtained: ");
                                double marks = sc.nextDouble();
                                sc.nextLine();
                                ResultEntry re = new ResultEntry(selectedCourse, marks);
                                student.getT().getRe().add(re);


                                System.out.println("Result Added!");
                            } else if (rChoice == 2) {

                                System.out.print("Enter Student ID: ");
                                String tid = sc.nextLine();
                                boolean found = false;
                                for (Student stu : students.getItems()) {


                                    if (stu.getStudentID().equalsIgnoreCase(tid)) {
                                        System.out.println(" <--------- Student Transcript Report  -------->");

                                        String show = "\n" + stu.toString() + "\n" + " GPA   =  " + stu.getT().getGPA() + "\n" + " Total marks " + stu.getT().getTotalMarks();
                                        System.out.println(show);
                                        found = true;

                                        break;
                                    }
                                }
                                if (!found) {
                                    System.out.println("Student not found!");
                                }
                            } else if (rChoice==3){  boolean found = false;
                                boolean foundstudent = false;   System.out.print("Enter Student ID: ");
                                String stuid = sc.nextLine();

                                for (Student x : students.getItems()){

                                    if (x.getStudentID().equalsIgnoreCase(stuid)){
                                        foundstudent = true ;
                                        for (ResultEntry rs : x.getT().getRe()){
                                            if ( rs.getMarksObtained()>0){
                                                found = true;
                                                String show = "\n" + stuid.toString() + "\n" + " GPA   =  " + x.getT().getGPA() + "\n" + " Total marks " + x.getT().getTotalMarks();
                                                System.out.println(show);
                                                System.out.println( "Search Successfully !");
                                                break;

                                            }

                                        }

                                    }
                                }
                                if (foundstudent == true) {
                                    if (found == true) {

                                    } else {
                                        System.out.println( "Transcript not found ");
                                    }
                                }else {
                                    System.out.println( " Student not found ");
                                }
                            }else if (rChoice == 4) {
                                System.out.print("Enter Student ID: ");
                                String stuid = sc.nextLine();
                                boolean found = false;
                                boolean foundstudent = false ;
                                for (Student x : students.getItems()){
                                    if (x.getStudentID().equalsIgnoreCase(stuid)){
                                        foundstudent = true ;
                                        for (ResultEntry rs : x.getT().getRe()){
                                            if ( rs.getMarksObtained() > 0){
                                                found = true;
                                                x.setT(new Transcript());
                                                System.out.println("Transcript Removed !");
                                                break;

                                            }

                                        }

                                    }
                                }
                                if (foundstudent == true) {
                                    if (found == false ) {


                                        System.out.println( " transcript not found ");
                                    }
                                }else {
                                    System.out.println(" Student  not found ");
                                }





                            } else if (rChoice == 5) {
                                System.out.print("Enter Student ID: ");
                                String gid = sc.nextLine();
                                boolean found = false;
                                for (Student stu : students.getItems()) {
                                    if (stu.getStudentID().equalsIgnoreCase(gid)) {
                                        System.out.println("Total Marks: " + stu.calculateTotal());
                                        System.out.println("Percentage: " + stu.calculatePercentage() + "%");
                                        stu.calculateGpa();
                                        stu.calculateGrade();
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) System.out.println("Student not found!");
                            } else if (rChoice == 6) {
                                resultMenu = false;
                            } else {
                                System.out.println("Invalid choice!");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid input! Please try again.");
                            sc.nextLine();
                        }
                    }

                } else if (choice == 4) {
                    boolean fileMenu = true;
                    while (fileMenu) {
                        try {
                            System.out.println("\n--- Data Management ---");
                            System.out.println("1. Save All Students and Transcript to File");
                            System.out.println("2. Retrieve Students and Transcript from File");
                            System.out.println("3. Save All Courses to File");
                            System.out.println("4. Retrieve All Courses from File");
                            System.out.println("5. Back to Main Menu");
                            System.out.print("Enter your choice: ");
                            int fChoice = sc.nextInt();
                            sc.nextLine();

                            if (fChoice == 1) {
                                studentDataStore.saveToFile("Student", students.getItems());
                            }
                            else if (fChoice == 2) {
                                studentDataStore.loadFromFile("Student");
                            }
                            else if (fChoice == 3) {
                                courseDataStore.saveToFile("Course", courses.getItems());
                            }
                            else if (fChoice == 4) {
                                courseDataStore.loadFromFile("Course");
                            }
                            else if (fChoice == 5) {
                                fileMenu = false;
                            }
                            else {
                                System.out.println("Invalid choice!");
                            }
                        } catch (Exception e) {
                            System.out.println("Error in file operation! Please try again.");
                            sc.nextLine();
                        }
                    }
                } else if (choice == 5) {
                    System.out.println("\n--- System Statistics ---");
                    System.out.println("Total Students: " + Student.totalStudent);
                    System.out.println("Total Courses: " + Course.getTotalCourses());
                    System.out.println("Global Pass Marks: " + ResultCalculator.passMarks);

                } else if (choice == 6) {
                    System.out.println("Thank You For Visiting Us!\nExiting the Program...");
                    sc.close();
                    System.exit(0);

                } else {
                    System.out.println("Invalid choice! Please enter 1-6.");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }
    }
}
