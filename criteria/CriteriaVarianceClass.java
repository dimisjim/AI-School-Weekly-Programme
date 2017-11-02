//DIMITRIS MORAITIDIS, 3100240

package criteria;

import java.util.ArrayList;
import java.util.List;
import algorithm.Schedule;
import model.Assignment;
import algorithm.MyMath;

public class CriteriaVarianceClass extends Criteria {

    @Override
    public double evaluate(Schedule s) {
        Assignment[][] data = s.data;
        double score = 0;

        for (int i = 0; i < 9; i++) {
            int empty1 = 0, empty2 = 0, empty3 = 0, empty4 = 0, empty5 = 0;
            double mean = 0, var = 0;
            for (int j = 0; j < 7; j++) {
                if (!data[i][j].empty) {
                    empty1++;
                }
            }
            for (int j = 7; j < 14; j++) {
                if (!data[i][j].empty) {
                    empty2++;
                }
            }
            for (int j = 14; j < 21; j++) {
                if (!data[i][j].empty) {
                    empty3++;
                }
            }
            for (int j = 21; j < 28; j++) {
                if (!data[i][j].empty) {
                    empty4++;
                }
            }
            for (int j = 28; j < 35; j++) {
                if (!data[i][j].empty) {
                    empty5++;
                }
            }
            double[] data2 = {empty1, empty2, empty3, empty4, empty5};
            MyMath st = new MyMath();
            mean = st.getMean(data2, data2.length);
            var = st.getVariance(data2, data2.length, mean);

            if (var <= 0.31) { //77666
                continue;
            } else if (var > 0.31 && var <= 0.8) { //77765
                score += 10;
            } else { //77774
                score += 20;
            }
            

        }
        return score;
    }

    @Override
    public List<Schedule> findChildren(Schedule s) {
        Assignment[][] data = s.data;
        List<Schedule> children = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            int counter1 = 0, counter2 = 0, counter3 = 0, counter4 = 0, counter5 = 0;
            double mean = 0, var = 0;
            for (int j = 0; j < 7; j++) {
                if (!data[i][j].empty) {
                    counter1++;
                }
            }
            for (int j = 7; j < 14; j++) {
                if (!data[i][j].empty) {
                    counter2++;
                }
            }
            for (int j = 14; j < 21; j++) {
                if (!data[i][j].empty) {
                    counter3++;
                }
            }
            for (int j = 21; j < 28; j++) {
                if (!data[i][j].empty) {
                    counter4++;
                }
            }
            for (int j = 28; j < 35; j++) {
                if (!data[i][j].empty) {
                    counter5++;
                }
            }
            double[] data2 = {counter1, counter2, counter3, counter4, counter5};
            MyMath st = new MyMath();
            mean = st.getMean(data2, data2.length);
            var = st.getVariance(data2, data2.length, mean);

            if (var <= 0.31) { //77666
                continue;
            } else if (var > 0.31 && var <= 0.8) { //77765
                int day_with_5hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 5) {
                        day_with_5hours = j;
                        break;
                    }
                }
                if (day_with_5hours != -1) {
                    for (int j = 0; j < 5; j++) {
                        if (data2[j] == 7) {
                            int day = j;
                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][(day * 7) + 6];
                            copy.data[i][(day * 7) + 6] = copy.data[i][(day_with_5hours * 7) + 5];
                            copy.data[i][(day_with_5hours * 7) + 5] = temp;
                            children.add(copy);
                        }
                    }
                }
            } else { //77774
                int day_with_4hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 4) {
                        day_with_4hours = j;
                        break;
                    }
                }
                
                if (day_with_4hours != -1) {
                    for (int j = 0; j < 5; j++) {
                        if (data2[j] == 7) {
                            int day = j;                            
                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][(day * 7) + 6];
                            copy.data[i][(day * 7) + 6] = copy.data[i][(day_with_4hours * 7) + 4];
                            copy.data[i][(day_with_4hours * 7) + 4 ] = temp;
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
        Assignment[][] data = s.data;

        for (int i = 0; i < 9; i++) {
            int empty1 = 0, empty2 = 0, empty3 = 0, empty4 = 0, empty5 = 0;
            double mean = 0, var = 0;
            for (int j = 0; j < 7; j++) {
                if (!data[i][j].empty) {
                    empty1++;
                }
            }
            for (int j = 7; j < 14; j++) {
                if (!data[i][j].empty) {
                    empty2++;
                }
            }
            for (int j = 14; j < 21; j++) {
                if (!data[i][j].empty) {
                    empty3++;
                }
            }
            for (int j = 21; j < 28; j++) {
                if (!data[i][j].empty) {
                    empty4++;
                }
            }
            for (int j = 28; j < 35; j++) {
                if (!data[i][j].empty) {
                    empty5++;
                }
            }
            double[] data2 = {empty1, empty2, empty3, empty4, empty5};
            MyMath st = new MyMath();
            mean = st.getMean(data2, data2.length);
            var = st.getVariance(data2, data2.length, mean);

            if (var <= 0.31) { //77666
                continue;
            } else if (var > 0.31 && var <= 0.8) { //77765
                System.out.println("VarianceClass conflict: Class " + (i + 1)
                        + " has variance of " + var + ", penalty 10, because " + empty1 + empty2 + empty3 + empty4 + empty5);
            } else if (var >= 0.8) { //77774
                System.out.println("VarianceClass conflict: Class " + (i + 1)
                        + " has variance of " + var + ", penalty 20, because " + empty1 + empty2 + empty3 + empty4 + empty5);
            }

        }
    }

}
