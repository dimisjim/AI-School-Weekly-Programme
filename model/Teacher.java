
package model;

import java.util.ArrayList;

public class Teacher {

    private String code;
    private String name;
    private ArrayList<String> lessons_codes;
    private int max_hours_per_day;
    private int max_hours_per_week;

    private int i, j;

    public Teacher(String code, String name, ArrayList<String> lessons_codes, int max_hours_per_day, int max_hours_per_week) {
        this.name = name;
        this.code = code;
        this.lessons_codes = lessons_codes;
        this.max_hours_per_day = max_hours_per_day;
        this.max_hours_per_week = max_hours_per_week;
    }

    public Teacher(int i, int j, String name) {
        this.i = i;
        this.j = j;
        this.name = name;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
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

    public ArrayList<String> getLessons_codes() {
        return lessons_codes;
    }

    public void setLessons_codes(ArrayList<String> lessons_codes) {
        this.lessons_codes = lessons_codes;
    }

    public int getMax_hours_per_day() {
        return max_hours_per_day;
    }

    public void setMax_hours_per_day(int max_hours_per_day) {
        this.max_hours_per_day = max_hours_per_day;
    }

    public int getMax_hours_per_week() {
        return max_hours_per_week;
    }

    public void setMax_hours_per_week(int max_hours_per_week) {
        this.max_hours_per_week = max_hours_per_week;
    }

    @Override
    public String toString() {
        return "Teacher [code=" + code + ", name=" + name + ", lessons_codes=" + lessons_codes + ", max_hours_per_day="
                + max_hours_per_day + ", max_hours_per_week=" + max_hours_per_week + "]";
    }

}
