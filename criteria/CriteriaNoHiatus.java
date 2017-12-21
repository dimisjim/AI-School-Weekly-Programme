

package criteria;

import java.util.ArrayList;
import java.util.List;
import algorithm.Schedule;
import model.Assignment;

public class CriteriaNoHiatus extends Criteria {

    @Override
    public double evaluate(Schedule s) {
        Assignment[][] data = s.data;
        double score = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    score += 1000;
                }
            }
            for (int j = 7; j < 13; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    score += 1000;
                }
            }
            for (int j = 14; j < 20; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    score += 1000;
                }
            }
            for (int j = 21; j < 27; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    score += 1000;
                }
            }
            for (int j = 28; j < 34; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    score += 1000;
                }
            }
        }

        return score;
    }

    @Override
    public List<Schedule> findChildren(Schedule s) {
        List<Schedule> children = new ArrayList<>();
        Assignment[][] data = s.data;

        int i = 0, j = 0;
        int day = 0;

        // ................ find one conflict ..................
        boolean found = false;
        LOOP:
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 6; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    day = 1;
                    found = true;
                    break LOOP;
                }
            }
            for (j = 7; j < 13; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    day = 2;
                    found = true;
                    break LOOP;
                }
            }
            for (j = 14; j < 20; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    day = 3;
                    found = true;
                    break LOOP;
                }
            }
            for (j = 21; j < 27; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    day = 4;
                    found = true;
                    break LOOP;
                }
            }
            for (j = 28; j < 34; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    day = 5;
                    found = true;
                    break LOOP;
                }
            }
        }

        //  ................. find solutions ..................
        if (found) {
            Schedule copy = s.copy();

            int k = 0;

            switch (day) {
                case 1:
                    for (k = 6; k > j; k--) {
                        if (!copy.data[i][k].empty) {
                            break;
                        }
                    }
                    break;
                case 2:
                    for (k = 13; k > j; k--) {
                        if (!copy.data[i][k].empty) {
                            break;
                        }
                    }
                    break;
                case 3:
                    for (k = 20; k > j; k--) {
                        if (!copy.data[i][k].empty) {
                            break;
                        }
                    }
                    break;
                case 4:
                    for (k = 27; k > j; k--) {
                        if (!copy.data[i][k].empty) {
                            break;
                        }
                    }
                    break;
                case 5:
                    for (k = 34 ; k > j; k--) {
                        if (!copy.data[i][k].empty) {
                            break;
                        }
                    }
                    break;
            }

            Assignment lastassignment = copy.data[i][k];

            Assignment temp = copy.data[i][j];
            copy.data[i][j] = lastassignment;
            lastassignment = temp;
            children.add(copy);
        }

        return children;
    }

    @Override
    public void printCriteria(Schedule s) {
        Assignment[][] data = s.data;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    
                    System.out.println("Hiatus found at: " + "i= " + i + ", j= " + j);
                }
            }
            for (int j = 7; j < 13; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    
                    System.out.println("Hiatus found at: " + "i= " + i + ", j= " + j);
                }
            }
            for (int j = 14; j < 20; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                   
                    System.out.println("Hiatus found at: " + "i= " + i + ", j= " + j);
                }
            }
            for (int j = 21; j < 27; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    
                    System.out.println("Hiatus found at: " + "i= " + i + ", j= " + j);
                }
            }
            for (int j = 28; j < 34; j++) {
                if ((data[i][j].empty && data[i][j] != null) && (!data[i][j + 1].empty && data[i][j + 1] != null)) {
                    
                    System.out.println("Hiatus found at: " + "i= " + i + ", j= " + j);
                }
            }
        }

    }

}
