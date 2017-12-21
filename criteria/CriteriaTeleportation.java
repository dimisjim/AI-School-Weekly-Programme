

package criteria;

import algorithm.Schedule;
import java.util.ArrayList;
import java.util.List;
import model.Assignment;
import model.Lesson;
import model.Teacher;

public class CriteriaTeleportation extends Criteria {

    @Override
    public double evaluate(Schedule s) {
        Assignment[][] data = s.data;
        double score = 0;

        // for each hour of the day...
        for (int t = 0; t < 35; t++) {
            // for each classroom
            for (int i = 0; i < 8; i++) {
                for (int j = i + 1; j < 9; j++) {
                    if ((data[i][t].teacher != null && data[j][t].teacher != null) ) {
                        if (data[i][t].teacher == data[j][t].teacher) {
                            score += 10000;
                        }
                    }
                }
            }
        }
        return score;
    }

    @Override
    public List<Schedule> findChildren(Schedule s) {
        List<Schedule> children = new ArrayList<>();
        Assignment[][] data = s.data;

        Teacher teacher = null;
        Lesson lesson1 = null;
        Lesson lesson2 = null;
        int i = 0, t1 = 0, j = 0;

        // for each hour of the day...
        LOOP:
        for (t1 = 0; t1 < 35 && teacher == null; t1++) {
            // for each classroom
            for (i = 0; i < 8 && teacher == null; i++) {
                // for each other classroom
                for (j = i + 1; j < 9 && teacher == null; j++) {
                    if (data[i][t1].teacher != null && data[j][t1].teacher != null) {
                        if (data[i][t1].teacher == data[j][t1].teacher) {
                            teacher = data[i][t1].teacher;
                            lesson1 = data[i][t1].lesson;
                            lesson2 = data[j][t1].lesson;
                            break LOOP;
                        }
                    }
                }
            }
        }

        

        //System.out.println("improving ... " + teacher.getName() + " on " + lesson1.getName() + " " + i + " " + j + " at " + t1);
        
        //-------fix the problem---------

        // change hour of lesson i
        for (int t2 = 0; t2 < 35; t2++) {
            if (t2 == t1) {
                continue;
            }
            if (data[i][t2].teacher == teacher) {
                continue;
            }

            Schedule copy = s.copy();
            Assignment temp = copy.data[i][t2];
            copy.data[i][t2] = copy.data[i][t1];
            copy.data[i][t1] = temp;
            children.add(copy);
        }

        // change hour of lesson k
        for (int t2 = 0; t2 < 35; t2++) {
            if (t2 == t1) {
                continue;
            }
            if (data[j][t2].teacher == teacher) {
                continue;
            }

            Schedule copy = s.copy();
            Assignment temp = copy.data[j][t2];
            copy.data[j][t2] = copy.data[j][t1];
            copy.data[j][t1] = temp;
            children.add(copy);
        }

        // change teacher
        ArrayList<Teacher> availableTeachers1 = lesson1.availableTeachers;
        for (Teacher partner : availableTeachers1) {
            if (partner == teacher) {
                continue;
            }

            Schedule copy = s.copy();
            for (int q = 0; q < 35; q++) {
                Assignment temp = copy.data[i][q];
                if (temp != null && !temp.empty && temp.teacher == teacher && temp.lesson == lesson1) {
                    copy.data[i][q] = new Assignment( temp.lesson, partner);
                }
            }
            children.add(copy);
        }

        ArrayList<Teacher> availableTeachers2 = lesson2.availableTeachers;

        for (Teacher partner : availableTeachers2) {
            if (partner == teacher) {
                continue;
            }

            Schedule copy = s.copy();
            for (int q = 0; q < 35; q++) {
                Assignment temp = copy.data[j][q];
                if (temp != null && !temp.empty && temp.teacher == teacher && temp.lesson == lesson2) {
                    copy.data[j][q] = new Assignment( temp.lesson, partner);
                }
            }
            children.add(copy);
        }

        return children;
    }

    @Override
    public void printCriteria(Schedule s) {
        Assignment[][] data = s.data;

        // for each hour of the day...
        for (int t = 0; t < 35; t++) {
            // for each classroom
            for (int i = 0; i < 8; i++) {
                for (int j = i + 1; j < 9; j++) {
                    if ((data[i][t].teacher != null && data[j][t].teacher != null) ) {
                        if (data[i][t].teacher == data[j][t].teacher) {
                            System.out.println("Teleportation conflict: " + data[i][t].teacher + " @hour:" +((t%7)+1) + " of day:" + ((t/7)+1));
                        }
                    }
                }
            }
        }
    }

}
