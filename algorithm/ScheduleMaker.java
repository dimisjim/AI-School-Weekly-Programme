

package algorithm;

import java.util.ArrayList;
import java.util.Random;
import loaders.LoadLessons;
import loaders.LoadTeachers;
import model.Assignment;
import model.Lesson;
import model.Teacher;

public class ScheduleMaker {

    public Schedule makeRandomSchedule(LoadTeachers loadTeachers, LoadLessons loadLessons) {
        Random random = new Random();
        Schedule s = new Schedule(loadTeachers.teachers, loadLessons.lessons);

        for (int k = 0; k < 9; k++) {
            ArrayList<Lesson> lessonsPerClassroom = null;
            int offset = 0;

            switch (k) {
                case 0:
                    lessonsPerClassroom = loadLessons.lessonsA1;
                    break;
                case 1:
                    lessonsPerClassroom = loadLessons.lessonsA2;
                    break;
                case 2:
                    lessonsPerClassroom = loadLessons.lessonsA3;
                    break;
                case 3:
                    lessonsPerClassroom = loadLessons.lessonsB1;
                    break;
                case 4:
                    lessonsPerClassroom = loadLessons.lessonsB2;
                    break;
                case 5:
                    lessonsPerClassroom = loadLessons.lessonsB3;
                    break;
                case 6:
                    lessonsPerClassroom = loadLessons.lessonsC1;
                    break;
                case 7:
                    lessonsPerClassroom = loadLessons.lessonsC2;
                    break;
                case 8:
                    lessonsPerClassroom = loadLessons.lessonsC3;
                    break;
            }

            //populate availableteachers field for each lesson
            for (Lesson l : lessonsPerClassroom) {
                for (Teacher t : loadTeachers.teachers) {
                    if (t.getLessons_codes().contains(l.getCode())) {
                        l.availableTeachers.add(t);
                    }
                }

                //assign a random teacher to the lesson
                if (!l.availableTeachers.isEmpty()) {
                    int m = l.availableTeachers.size();

                    int x = random.nextInt(m);
                    Teacher who = l.availableTeachers.get(x);

                    Assignment a = new Assignment(l, who);

                    //put the assignment (lesson, teacher) in the Schedule array (effectively a node/state)
                    for (int i = 0; i < l.getHours(); i++) {
                        s.data[k][offset] = a;
                        offset++;
                    }
                } else {
                    System.out.println("#######" + l.getName());
                }
            }
            Assignment aEmpty = new Assignment();
            s.data[k][32] = aEmpty;
            s.data[k][33] = aEmpty;
            s.data[k][34] = aEmpty;
        }

        return s;
    }
}
