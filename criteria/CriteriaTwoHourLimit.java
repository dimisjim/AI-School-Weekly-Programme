
package criteria;

import algorithm.Schedule;
import java.util.ArrayList;
import java.util.List;
import model.Assignment;
import model.Pair;
import model.Teacher;

public class CriteriaTwoHourLimit extends Criteria {

	//conflict in j slot if teacher is also present at j-1 and j+1
	
    @Override
    public double evaluate(Schedule s) {
        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;
        double score = 0;

        List<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair(1, 6));
        pairs.add(new Pair(8, 13));
        pairs.add(new Pair(15, 20));
        pairs.add(new Pair(22, 27));
        pairs.add(new Pair(29, 34));
      
        for (Teacher t : teachers) {
            for (Pair p : pairs) {
                for (int j = p.min; j < p.max; j++) {
                    boolean before = false;
                    boolean current = false;
                    boolean after = false;

                    for (int i = 0; i < 9; i++) {
                        if (data[i][j - 1] != null && !data[i][j - 1].empty && data[i][j - 1].teacher == t) {
                            before = true;
                            break;
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        if (data[i][j] != null && !data[i][j].empty && data[i][j].teacher == t) {
                            current = true;
                            break;
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        if (data[i][j + 1] != null && !data[i][j + 1].empty && data[i][j + 1].teacher == t) {
                            after = true;
                            break;
                        }
                    }
                    if (before && current && after) {
                        score += 25;
                    }
                }
            }

        }
        return score;
    }

    @Override
    public List<Schedule> findChildren(Schedule s) {
        List<Schedule> children = new ArrayList<>();
        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;

        List<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair(1, 6));
        pairs.add(new Pair(8, 13));
        pairs.add(new Pair(15, 20));
        pairs.add(new Pair(22, 27));
        pairs.add(new Pair(29, 34));

        boolean found = false;
        int j = 0, i1 = 0, i2 = 0, i3 = 0;
        Teacher who = null;
     
        LOOP:
        for (Teacher t : teachers) {
            who = t;
            for (Pair p : pairs) {
                for (j = p.min; j < p.max; j++) {
                    boolean before = false;
                    boolean current = false;
                    boolean after = false;

                    i1 = 0;
                    i2 = 0;
                    i3 = 0;

                    for (int i = 0; i < 9; i++) {
                        if (i1 == 0 && data[i][j - 1] != null && data[i][j - 1].teacher == t) {
                            before = true;
                            i1 = i;
                            break;
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        if (i2 == 0 && data[i][j] != null && data[i][j].teacher == t) {
                            current = true;
                            i2 = i;
                            break;
                        }
                    }

                    for (int i = 0; i < 9; i++) {
                        if (i3 == 0 && data[i][j + 1] != null && data[i][j + 1].teacher == t) {
                            after = true;
                            i3 = i;
                            break;
                        }
                    }

                    if (before && current && after) {
                        found = true;
                        break LOOP;
                    }
                }
            }
        }

        if (found) {
            // change hour of lesson j
            int t1 = j;

            for (int t2 = 0; t2 < 35; t2++) {
                if (t2 == t1) {
                    continue;
                }
                if (!data[i2][t2].empty && data[i2][t2].teacher == who) {
                    continue;
                }

                Schedule copy = s.copy();
                Assignment temp = copy.data[i2][t2];
                copy.data[i2][t2] = copy.data[i2][t1];
                copy.data[i2][t1] = temp;
                children.add(copy);
            }

            // change hour of lesson j-1
            t1 = j - 1;

            for (int t2 = 0; t2 < 35; t2++) {
                if (t2 == t1) {
                    continue;
                }
                if (data[i1][t2].teacher == who) {
                    continue;
                }

                Schedule copy = s.copy();
                Assignment temp = copy.data[i1][t2];
                copy.data[i1][t2] = copy.data[i1][t1];
                copy.data[i1][t1] = temp;
                children.add(copy);
            }

            // change hour of lesson j+1
            t1 = j + 1;

            for (int t2 = 0; t2 < 35; t2++) {
                if (t2 == t1) {
                    continue;
                }
                if (data[i3][t2].teacher == who) {
                    continue;
                }

                Schedule copy = s.copy();
                Assignment temp = copy.data[i3][t2];
                copy.data[i3][t2] = copy.data[i3][t1];
                copy.data[i3][t1] = temp;
                children.add(copy);
            }
            
            //change teacher assignment
            //at lesson j-1
            int x = j-1;
            ArrayList<Teacher> availableTeachers1 = data[i1][x].lesson.availableTeachers;
            for (Teacher partner : availableTeachers1) {
                if (partner == who) {
                    continue;
                }

                Schedule copy = s.copy();
                for (int q = 0; q < 35; q++) {
                    Assignment temp = copy.data[i1][q];
                    if (temp != null && !temp.empty && temp.teacher == who && temp.lesson == data[i1][x].lesson) {
                        copy.data[i1][q] = new Assignment( temp.lesson, partner);
                    }
                }
                children.add(copy);
            }
            //at lesson j
            int y = j;
            ArrayList<Teacher> availableTeachers2 = data[i2][y].lesson.availableTeachers;
            for (Teacher partner : availableTeachers2) {
                if (partner == who) {
                    continue;
                }

                Schedule copy = s.copy();
                for (int q = 0; q < 35; q++) {
                    Assignment temp = copy.data[i2][q];
                    if (temp != null && !temp.empty && temp.teacher == who && temp.lesson == data[i2][y].lesson) {
                        copy.data[i2][q] = new Assignment( temp.lesson, partner);
                    }
                }
                children.add(copy);
            }
            
            
            //at lesson j+1
            int z = j+1;
            ArrayList<Teacher> availableTeachers3 = data[i3][z].lesson.availableTeachers;
            for (Teacher partner : availableTeachers3) {
                if (partner == who) {
                    continue;
                }

                Schedule copy = s.copy();
                for (int q = 0; q < 35; q++) {
                    Assignment temp = copy.data[i3][q];
                    if (temp != null && !temp.empty && temp.teacher == who && temp.lesson == data[i3][z].lesson) {
                        copy.data[i3][q] = new Assignment( temp.lesson, partner);
                    }
                }
                children.add(copy);
            }
            
            
        }
        

        return children;
    }

    @Override
    public void printCriteria(Schedule s) {
        ArrayList<Teacher> teachers = s.teachers;
        Assignment[][] data = s.data;

        List<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair(1, 6));
        pairs.add(new Pair(8, 13));
        pairs.add(new Pair(15, 20));
        pairs.add(new Pair(22, 27));
        pairs.add(new Pair(29, 34));

        // Check for each teacher, if daily hours are exceeded        
        for (Teacher t : teachers) {
            for (Pair p : pairs) {
                for (int j = p.min; j < p.max; j++) {
                    boolean before = false;
                    boolean current = false;
                    boolean after = false;

                    for (int i = 0; i < 9; i++) {
                        if (data[i][j - 1] != null && !data[i][j - 1].empty && data[i][j - 1].teacher == t) {
                            before = true;
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        if (data[i][j] != null && !data[i][j].empty && data[i][j].teacher == t) {
                            current = true;
                        }
                    }
                    for (int i = 0; i < 9; i++) {
                        if (data[i][j + 1] != null && !data[i][j + 1].empty && data[i][j + 1].teacher == t) {
                            after = true;
                        }
                    }
                    if (before && current && after) {
                        System.out.println("CriteriaTwoHourLimit conflict: " + t.getName() + ", hour: " + ((j -(p.min/7)*7)+1) + " of day: " + ((p.min/7)+1));
                    }
                }
            }

        }
    }

}
