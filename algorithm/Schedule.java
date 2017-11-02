//DIMITRIS MORAITIDIS, 3100240

package algorithm;

import criteria.Criteria;
import java.util.ArrayList;
import java.util.List;
import model.Assignment;
import model.Lesson;
import model.Teacher;

public class Schedule {

    public final Assignment[][] data = new Assignment[9][35];
    public final ArrayList<Teacher> teachers;
    public final ArrayList<Lesson> lessons;

    public Schedule(ArrayList<Teacher> teachers, ArrayList<Lesson> lessons) {
        this.teachers = teachers;
        this.lessons = lessons;
    }

    // minimize objective function: higher priority constraints: increase score by a higher value
    public int score(List<Criteria> criteriaList) {
        int score = 0;

        for (Criteria c : criteriaList) {
            score += c.evaluate(this);
        }

        return score;
    }

    public List<Schedule> getChildren(List<Criteria> criteriaList) {
        for (Criteria c : criteriaList) {
            double score = c.evaluate(this);
            if (score > 0) {
                List<Schedule> children = c.findChildren(this);
                if (children != null && !children.isEmpty()) {
                    return children;
                }
            }
        }
        return null;
    }

    public Schedule copy() {
        Schedule copySchedule = new Schedule(teachers, lessons);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 35; j++) {
                if (data[i][j].empty == false) {
                    copySchedule.data[i][j] = new Assignment(data[i][j].lesson, data[i][j].teacher);
                } else {
                    copySchedule.data[i][j] = new Assignment();
                }
            }
        }
        return copySchedule;
    }
}
