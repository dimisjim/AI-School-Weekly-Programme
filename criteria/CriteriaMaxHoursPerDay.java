//DIMITRIS MORAITIDIS, 3100240

package criteria;

import algorithm.Schedule;
import java.util.ArrayList;
import java.util.List;
import model.Assignment;
import model.Lesson;
import model.Teacher;

public class CriteriaMaxHoursPerDay extends Criteria {

    public double evaluate(Schedule s) {
        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;
        double score = 0;

        // Check for each teacher, if daily hours are exceeded        
        for (Teacher t : teachers) {

            int hours1 = 0, hours2 = 0, hours3 = 0, hours4 = 0, hours5 = 0;

            for (int i = 0; i < 9; i++) {
                // 1st day
                for (int j = 0; j < 7; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours1++;
                    }
                }
                //2nd day
                for (int j = 7; j < 14; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours2++;
                    }
                }
                //3rd day
                for (int j = 14; j < 21; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours3++;
                    }
                }
                //4th day
                for (int j = 21; j < 28; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours4++;
                    }
                }
                //5th day
                for (int j = 28; j < 35; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours5++;
                    }
                }
            }
            if (hours1 > t.getMax_hours_per_day()) {
                score += 100;
            }
            if (hours2 > t.getMax_hours_per_day()) {
                score += 100;
            }
            if (hours3 > t.getMax_hours_per_day()) {
                score += 100;
            }
            if (hours4 > t.getMax_hours_per_day()) {
                score += 100;
            }
            if (hours5 > t.getMax_hours_per_day()) {
                score += 100;
            }
        }
        return score;
    }

    @Override
    public List<Schedule> findChildren(Schedule s) {
        List<Schedule> children = new ArrayList<>();

        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;

        int day = -1;
        Teacher who = null;
        // Check for each teacher, if daily hours are exceeded        
        for (Teacher t : teachers) {
            int hours1 = 0, hours2 = 0, hours3 = 0, hours4 = 0, hours5 = 0;

            for (int i = 0; i < 9; i++) {
                // 1st day
                for (int j = 0; j < 7; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours1++;
                    }
                }
                //2nd day
                for (int j = 7; j < 14; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours2++;
                    }
                }
                //3rd day
                for (int j = 14; j < 21; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours3++;
                    }
                }
                //4th day
                for (int j = 21; j < 28; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours4++;
                    }
                }
                //5th day
                for (int j = 28; j < 35; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours5++;
                    }
                }
            }
            if (hours1 > t.getMax_hours_per_day()) {
                day = 1;
                who = t;
                break;
            } else if (hours2 > t.getMax_hours_per_day()) {
                day = 2;
                who = t;
                break;
            } else if (hours3 > t.getMax_hours_per_day()) {
                day = 3;
                who = t;
                break;
            } else if (hours4 > t.getMax_hours_per_day()) {
                day = 4;
                who = t;
                break;
            } else if (hours5 > t.getMax_hours_per_day()) {
                day = 5;
                who = t;
                break;
            }
        }
        if (day != -1 && who != null) {
            switch (day) {
                case 1:
                    for (int t1 = 0; t1 < 7; t1++) { // for each hour in the day
                        for (int i = 0; i < 9; i++) { // for each class 
                            if (data[i][t1] != null && data[i][t1].teacher == who) {

                                // change hour of lesson k
                                for (int t2 = 0; t2 < 35; t2++) {
                                    if (data[i][t2].teacher == who || t2 >= 0 && t2 < 7) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    Assignment temp = copy.data[i][t2];
                                    copy.data[i][t2] = copy.data[i][t1];
                                    copy.data[i][t1] = temp;
                                    children.add(copy);
                                }

                                // change the teacher
                                // change teacher
                                Lesson lesson1 = data[i][t1].lesson;
                                ArrayList<Teacher> availableTeachers1 = lesson1.availableTeachers;
                                for (Teacher partner : availableTeachers1) {
                                    if (partner == who) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    for (int q = 0; q < 35; q++) {
                                        Assignment temp = copy.data[i][q];
                                        if (temp != null && !temp.empty && temp.teacher == who && temp.lesson == lesson1) {
                                            copy.data[i][q] = new Assignment(temp.lesson, partner);
                                        }
                                    }
                                    children.add(copy);
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    for (int t1 = 7; t1 < 14; t1++) {
                        for (int i = 0; i < 9; i++) { // for each class 
                            if (data[i][t1] != null && data[i][t1].teacher == who) {

                                // change hour of lesson k
                                for (int t2 = 0; t2 < 35; t2++) {
                                    if (data[i][t2].teacher == who || t2 >= 7 && t2 < 14) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    Assignment temp = copy.data[i][t2];
                                    copy.data[i][t2] = copy.data[i][t1];
                                    copy.data[i][t1] = temp;
                                    children.add(copy);
                                }

                                // change the teacher
                                // change teacher
                                Lesson lesson1 = data[i][t1].lesson;
                                ArrayList<Teacher> availableTeachers1 = lesson1.availableTeachers;
                                for (Teacher partner : availableTeachers1) {
                                    if (partner == who) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    for (int q = 0; q < 35; q++) {
                                        Assignment temp = copy.data[i][q];
                                        if (temp != null && !temp.empty && temp.teacher == who && temp.lesson == lesson1) {
                                            copy.data[i][q] = new Assignment(temp.lesson, partner);
                                        }
                                    }
                                    children.add(copy);
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    for (int t1 = 14; t1 < 21; t1++) {
                        for (int i = 0; i < 9; i++) { // for each class 
                            if (data[i][t1] != null && data[i][t1].teacher == who) {

                                // change hour of lesson k
                                for (int t2 = 0; t2 < 35; t2++) {
                                    if (data[i][t2].teacher == who || t2 >= 14 && t2 < 21) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    Assignment temp = copy.data[i][t2];
                                    copy.data[i][t2] = copy.data[i][t1];
                                    copy.data[i][t1] = temp;
                                    children.add(copy);
                                }

                                // change the teacher
                                // change teacher
                                Lesson lesson1 = data[i][t1].lesson;
                                ArrayList<Teacher> availableTeachers1 = lesson1.availableTeachers;
                                for (Teacher partner : availableTeachers1) {
                                    if (partner == who) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    for (int q = 0; q < 35; q++) {
                                        Assignment temp = copy.data[i][q];
                                        if (temp != null && !temp.empty && temp.teacher == who && temp.lesson == lesson1) {
                                            copy.data[i][q] = new Assignment(temp.lesson, partner);
                                        }
                                    }
                                    children.add(copy);
                                }
                            }
                        }
                    }
                    break;
                case 4:
                    for (int t1 = 21; t1 < 28; t1++) {
                        for (int i = 0; i < 9; i++) { // for each class 
                            if (data[i][t1] != null && data[i][t1].teacher == who) {

                                // change hour of lesson k
                                for (int t2 = 0; t2 < 35; t2++) {
                                    if (data[i][t2].teacher == who || t2 >= 21 && t2 < 28) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    Assignment temp = copy.data[i][t2];
                                    copy.data[i][t2] = copy.data[i][t1];
                                    copy.data[i][t1] = temp;
                                    children.add(copy);
                                }

                                // change the teacher
                                // change teacher
                                Lesson lesson1 = data[i][t1].lesson;
                                ArrayList<Teacher> availableTeachers1 = lesson1.availableTeachers;
                                for (Teacher partner : availableTeachers1) {
                                    if (partner == who) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    for (int q = 0; q < 35; q++) {
                                        Assignment temp = copy.data[i][q];
                                        if (temp != null && !temp.empty && temp.teacher == who && temp.lesson == lesson1) {
                                            copy.data[i][q] = new Assignment(temp.lesson, partner);
                                        }
                                    }
                                    children.add(copy);
                                }
                            }
                        }
                    }
                    break;
                case 5:
                    for (int t1 = 28; t1 < 35; t1++) {
                        for (int i = 0; i < 9; i++) { // for each class 
                            if (data[i][t1] != null && data[i][t1].teacher == who) {

                                // change hour of lesson k
                                for (int t2 = 0; t2 < 35; t2++) {
                                    if (data[i][t2].teacher == who || t2 >= 28 && t2 < 35) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    Assignment temp = copy.data[i][t2];
                                    copy.data[i][t2] = copy.data[i][t1];
                                    copy.data[i][t1] = temp;
                                    children.add(copy);
                                }

                                // change the teacher
                                // change teacher
                                Lesson lesson1 = data[i][t1].lesson;
                                ArrayList<Teacher> availableTeachers1 = lesson1.availableTeachers;
                                for (Teacher partner : availableTeachers1) {
                                    if (partner == who) {
                                        continue;
                                    }

                                    Schedule copy = s.copy();
                                    for (int q = 0; q < 35; q++) {
                                        Assignment temp = copy.data[i][q];
                                        if (temp != null && !temp.empty && temp.teacher == who && temp.lesson == lesson1) {
                                            copy.data[i][q] = new Assignment(temp.lesson, partner);
                                        }
                                    }
                                    children.add(copy);
                                }
                            }
                        }
                    }
                    break;

            }
        }

        return children;
    }

    @Override
    public void printCriteria(Schedule s) {
        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;

        // Check for each teacher, if daily hours are exceeded        
        for (Teacher t : teachers) {
            int hours1 = 0, hours2 = 0, hours3 = 0, hours4 = 0, hours5 = 0;

            for (int i = 0; i < 9; i++) {
                // 1st day
                for (int j = 0; j < 7; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours1++;
                    }
                }
                //2nd day
                for (int j = 7; j < 14; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours2++;
                    }
                }
                //3rd day
                for (int j = 14; j < 21; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours3++;
                    }
                }
                //4th day
                for (int j = 21; j < 28; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours4++;
                    }
                }
                //5th day
                for (int j = 28; j < 35; j++) {
                    if (data[i][j] != null && data[i][j].teacher == t) {
                        hours5++;
                    }
                }
            }

            if (hours1 > t.getMax_hours_per_day()) {
                System.out.println("MaxHoursPerDay Conflict: " + t.getName() + " works more hours than permitted on Monday");
            }
            if (hours2 > t.getMax_hours_per_day()) {
                System.out.println("MaxHoursPerDay Conflict: " + t.getName() + " works more hours than permitted on Tuesday");
            }
            if (hours3 > t.getMax_hours_per_day()) {
                System.out.println("MaxHoursPerDay Conflict: " + t.getName() + " works more hours than permitted on Wednesday");
            }
            if (hours4 > t.getMax_hours_per_day()) {
                System.out.println("MaxHoursPerDay Conflict: " + t.getName() + " works more hours than permitted on Thursday");
            }
            if (hours5 > t.getMax_hours_per_day()) {
                System.out.println("MaxHoursPerDay Conflict: " + t.getName() + " works more hours than permitted on Friday");
            }

        }
    }

}
