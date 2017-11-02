//DIMITRIS MORAITIDIS, 3100240

package criteria;

import algorithm.Schedule;
import java.util.ArrayList;
import java.util.List;
import model.Assignment;
import model.Lesson;
import model.Teacher;

public class CriteriaMaxHoursPerWeek extends Criteria {

    @Override
    public double evaluate(Schedule s) {
        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;
        double score = 0;

        // Check for each teacher, if weekly hours are exceeded        
        for (Teacher t : teachers) {
            int hours = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 35; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours++;
                    }
                }
            }
            if (hours > t.getMax_hours_per_week()) {
                score += 50;
            }
        }
        return score;
    }

    @Override
    public List<Schedule> findChildren(Schedule s) {
        List<Schedule> children = new ArrayList<>();
        
        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;

        for (Teacher t : teachers) {
            
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 35; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {

                        // change the teacher
                        // change teacher
                        Lesson lesson1 = data[i][j].lesson;
                        ArrayList<Teacher> availableTeachers1 = lesson1.availableTeachers;
                        for (Teacher partner : availableTeachers1) {
                            if (partner == t) {
                                continue;
                            }

                            Schedule copy = s.copy();
                            for (int q = 0; q < 35; q++) {
                                Assignment temp = copy.data[i][q];
                                if (temp != null && !temp.empty && temp.teacher == t && temp.lesson == lesson1) {
                                    copy.data[i][q] = new Assignment(temp.lesson, partner);
                                }
                            }
                            children.add(copy);
                        }

                    }
                }
            }
        }

        return children;
    }

    @Override
    public void printCriteria(Schedule s) {
        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;

        // Check for each teacher, if weekly hours are exceeded        
        for (Teacher t : teachers) {
            int hours = 0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 35; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours++;
                    }
                }
            }
            if (hours > t.getMax_hours_per_week()) {
                System.out.println("MaxHoursPerWeek Conflict: " + t.getName() + " works more hours than permitted during a week");
            }
        }
    }

}
