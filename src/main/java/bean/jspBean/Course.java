package bean.jspBean;
import java.util.TreeSet;

public class Course {
    private String type;
    private String course_type;
    private String time;
    private String roll_call;
    private String day;
    private String lesson;
    private String course;
    private String course_num;
    private String teacher;
    private String classroom;
    private String rawWeek;
    private TreeSet week;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse_num() {
        return course_num;
    }

    public void setCourse_num(String course_num) {
        this.course_num = course_num;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getRawWeek() {
        return rawWeek;
    }

    public void setRawWeek(String rawWeek) {
        this.rawWeek = rawWeek;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoll_call() {
        return roll_call;
    }

    public void setRoll_call(String roll_call) {
        this.roll_call = roll_call;
    }

    public TreeSet getWeek() {
        return week;
    }

    public void setWeek(TreeSet week) {
        this.week = week;
    }
}


