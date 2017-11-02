//DIMITRIS MORAITIDIS, 3100240

package model;

import java.util.ArrayList;

public class Lesson {

    private String code;
    private String name;

    private int hours;

    private String year;     // A B C
    private String classroom; //  A1 A2 A3

    public ArrayList<Teacher> availableTeachers = new ArrayList<>();
    public ArrayList<Integer> presentAtJs = new ArrayList<>();

    public Lesson(String code, String name, String year, int hours) {
        this.code = code;
        this.name = name;
        this.year = year;
        this.hours = hours;
    }

    public Lesson(String code, String name, String year, int hours, String classroom) {
        this.code = code;
        this.name = name;
        this.hours = hours;
        this.year = year;
        this.classroom = classroom;
    }

    public Lesson(String code, String name, String year, int hours, String classroom, ArrayList<Integer> presentAtJs) {
        this.code = code;
        this.name = name;
        this.hours = hours;
        this.year = year;
        this.classroom = classroom;
        this.presentAtJs = presentAtJs;
    }

    public Lesson() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setClass(String year) {
        this.year = year;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getClassroom() {
        return classroom;
    }
    
    public int getClassroomAsNumber() {
        if (classroom.equals("A1")) {
            return 0;
        }
        if (classroom.equals("A2")) {
            return 1;
        }
        if (classroom.equals("A3")) {
            return 2;
        }
        if (classroom.equals("B1")) {
            return 3;
        }
        if (classroom.equals("B2")) {
            return 4;
        }
        if (classroom.equals("B3")) {
            return 5;
        }
        if (classroom.equals("C1")) {
            return 6;
        }
        if (classroom.equals("C2")) {
            return 7;
        }
        if (classroom.equals("C3")) {
            return 8;
        }
        throw new IllegalArgumentException("oime!");
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ArrayList<Integer> getPresentAtJs() {
        return presentAtJs;
    }

    public void setPresentAtJs(ArrayList<Integer> presentAtJs) {
        this.presentAtJs = presentAtJs;
    }

    @Override
    public String toString() {
        return "Lesson{" + "code=" + code + ", name=" + name + ", hours=" + hours + ", year=" + year + ", classroom=" + classroom + '}';
    }
}
