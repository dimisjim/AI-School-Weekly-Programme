//DIMITRIS MORAITIDIS, 3100240

package criteria;

import java.util.ArrayList;
import java.util.List;
import algorithm.Schedule;
import algorithm.MyMath;
import model.Assignment;
import model.Lesson;

public class CriteriaVarianceLesson extends Criteria {

    @Override
    public double evaluate(Schedule s) {
        Assignment[][] data = s.data;
        double score = 0;

        //find for each lesson all the j positions that it is present
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Lesson l : s.lessons) {
            ArrayList<Integer> js = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 35; j++) {
                    if (l == data[i][j].lesson) {
                        js.add(j);
                    }
                }
            }
            Lesson le = new Lesson(l.getCode(), l.getName(), l.getYear(), l.getHours(), l.getClassroom(), js);
            lessons.add(le);
        }

        //for each lesson, calculate variance and give score
        for (Lesson l : lessons) {
            int counter1 = 0, counter2 = 0, counter3 = 0, counter4 = 0, counter5 = 0;
            for (Integer j : l.presentAtJs) {
                if (j < 7) {
                    counter1++;
                } else if (j < 14) {
                    counter2++;
                } else if (j < 21) {
                    counter3++;
                } else if (j < 28) {
                    counter4++;
                } else if (j < 35) {
                    counter5++;
                }
            }
            double[] data2 = {counter1, counter2, counter3, counter4, counter5};
            MyMath st = new MyMath();
            double mean = st.getMean(data2, data2.length);
            double var = st.getVariance(data2, data2.length, mean);

            if (var < 0.21) { //10000, for the 1 hour per week lessons, no penalty                              0.2000001
                continue;
            }
            if (var > 0.80 && var < 0.81) {//20000, for the 2 hours per week lessons, 2 penalty                   0.8000001
                score += 2;
            }
            if (var > 0.3 && var < 0.31) {//11000 or 11100, for the 2(or3) hours per week lessons, no penalty     0.3000001
                continue;
            }
            if (var > 1.8 && var < 1.81) {//30000, for the 3 hours per week lessons, 6 penalty                    1.8000001
                score += 6;
            }
            if (var > 0.79 && var < 0.80) {//21000, for the 3 hours per week lessons, 4 penalty                   0.7999998
                score += 4;
            }
            if (var > 3.2 && var < 3.3) {//40000, for the 4 hours per week lessons, 8 penalty                     3.2000001
                score += 8;
            }
            if (var > 1.7 && var < 1.71) {//31000, for the 4 hours per week lessons, 6 penalty                    1.7000006
                score += 6;
            }
            if (var > 1.2 && var < 1.21) {//22000, for the 4 hours per week lessons, 4 penalty                    1.2000006
                score += 4;
            }
            if (var > 0.7 && var < 0.71) {//21100, for the 4 hours per week lessons, 2 penalty                    0.7000001
                score += 2;
            }
            if (var >= 0.2 && var < 0.21) {//11110, for the 4 hours per week lessons, no penalty                  0.2000001
                continue;
            }

        }

        return score;
    }

    @Override
    public List<Schedule> findChildren(Schedule s) {
        List<Schedule> children = new ArrayList<>();

        Assignment[][] data = s.data;

        //find for each lesson all the j positions that it is present
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Lesson l : s.lessons) {
            ArrayList<Integer> js = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 35; j++) {
                    if (l == data[i][j].lesson) {
                        js.add(j);
                    }
                }
            }
            Lesson le = new Lesson(l.getCode(), l.getName(), l.getYear(), l.getHours(), l.getClassroom(), js);
            lessons.add(le);
        }

        //for each lesson, calculate variance and give score
        for (Lesson l : lessons) {
            int counter1 = 0, counter2 = 0, counter3 = 0, counter4 = 0, counter5 = 0;
            for (Integer j : l.presentAtJs) {
                if (j < 7) {
                    counter1++;
                } else if (j < 14) {
                    counter2++;
                } else if (j < 21) {
                    counter3++;
                } else if (j < 28) {
                    counter4++;
                } else if (j < 35) {
                    counter5++;
                }
            }
            double[] data2 = {counter1, counter2, counter3, counter4, counter5};
            MyMath st = new MyMath();
            double mean = st.getMean(data2, data2.length);
            double var = st.getVariance(data2, data2.length, mean);

            if (var > 0.80 && var < 0.81) {//20000, for the 2 hours per week lessons, 2 penalty                   0.8000001
                int day_with_2hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 2) {
                        day_with_2hours = j;
                        break;
                    }
                }
                if (day_with_2hours != -1) {
                    int i = l.getClassroomAsNumber();

                    for (int j : l.presentAtJs) {
                        for (int hour = 0; hour < 35; hour++) {
                            int day = hour / 7;
                            if (day == day_with_2hours) {
                                continue;
                            }

                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][j];
                            copy.data[i][j] = copy.data[i][hour];
                            copy.data[i][hour] = temp;
                            children.add(copy);
                        }
                    }
                }
            }
            if (var > 1.8 && var < 1.81) {//30000, for the 3 hours per week lessons, 6 penalty                    1.8000001
                int day_with_3hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 3) {
                        day_with_3hours = j;
                        break;
                    }
                }
                if (day_with_3hours != -1) {
                    int i = l.getClassroomAsNumber();

                    for (int j : l.presentAtJs) {
                        for (int hour = 0; hour < 35; hour++) {
                            int day = hour / 7;
                            if (day == day_with_3hours) {
                                continue;
                            }

                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][j];
                            copy.data[i][j] = copy.data[i][hour];
                            copy.data[i][hour] = temp;
                            children.add(copy);
                        }
                    }
                }
            }
            if (var > 0.79 && var < 0.80) {//21000, for the 3 hours per week lessons, 4 penalty                   0.7999998
                int day_with_2hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 2) {
                        day_with_2hours = j;
                        break;
                    }
                }
                int day_with_1hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 1) {
                        day_with_1hours = j;
                        break;
                    }
                }
                
                if (day_with_2hours != -1) {
                    int i = l.getClassroomAsNumber();

                    for (int j : l.presentAtJs) {
                        for (int hour = 0; hour < 35; hour++) {
                            int day = hour / 7;
                            if (day == day_with_2hours || day == day_with_1hours) {
                                continue;
                            }

                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][j];
                            copy.data[i][j] = copy.data[i][hour];
                            copy.data[i][hour] = temp;
                            children.add(copy);
                        }
                    }
                }
            }
            if (var > 3.2 && var < 3.3) {//40000, for the 4 hours per week lessons, 8 penalty                     3.2000001
                int day_with_4hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 4) {
                        day_with_4hours = j;
                        break;
                    }
                }
                if (day_with_4hours != -1) {
                    int i = l.getClassroomAsNumber();

                    for (int j : l.presentAtJs) {
                        for (int hour = 0; hour < 35; hour++) {
                            int day = hour / 7;
                            if (day == day_with_4hours) {
                                continue;
                            }

                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][j];
                            copy.data[i][j] = copy.data[i][hour];
                            copy.data[i][hour] = temp;
                            children.add(copy);
                        }
                    }
                }
            }
            if (var > 1.7 && var < 1.71) {//31000, for the 4 hours per week lessons, 6 penalty                    1.7000006
                int day_with_3hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 3) {
                        day_with_3hours = j;
                        break;
                    }
                }
                int day_with_1hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 1) {
                        day_with_1hours = j;
                        break;
                    }
                }
                
                if (day_with_3hours != -1) {
                    int i = l.getClassroomAsNumber();

                    for (int j : l.presentAtJs) {
                        for (int hour = 0; hour < 35; hour++) {
                            int day = hour / 7;
                            if (day == day_with_3hours || day == day_with_1hours) {
                                continue;
                            }

                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][j];
                            copy.data[i][j] = copy.data[i][hour];
                            copy.data[i][hour] = temp;
                            children.add(copy);
                        }
                    }
                }
            }
            if (var > 1.2 && var < 1.21) {//22000, for the 4 hours per week lessons, 4 penalty                    1.2000006
                int day_with_2hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 2) {
                        day_with_2hours = j;
                        break;
                    }
                }
                int day_with_2hours2 = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 2 && data2[j] != day_with_2hours ) {
                        day_with_2hours2 = j;
                        break;
                    }
                }
                
                if (day_with_2hours != -1) {
                    int i = l.getClassroomAsNumber();

                    for (int j : l.presentAtJs) {
                        for (int hour = 0; hour < 35; hour++) {
                            int day = hour / 7;
                            if (day == day_with_2hours || day == day_with_2hours2) {
                                continue;
                            }

                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][j];
                            copy.data[i][j] = copy.data[i][hour];
                            copy.data[i][hour] = temp;
                            children.add(copy);
                        }
                    }
                }
            }
            if (var > 0.7 && var < 0.71) {//21100, for the 4 hours per week lessons, 2 penalty                    0.7000001
                int day_with_2hours = -1;
                for (int j = 0; j < 5; j++) {
                    if (data2[j] == 2) {
                        day_with_2hours = j;
                        break;
                    }
                }
                
                if (day_with_2hours != -1) {
                    int i = l.getClassroomAsNumber();

                    for (int j : l.presentAtJs) {
                        for (int hour = 0; hour < 35; hour++) {
                            int day = hour / 7;
                            if (day == day_with_2hours) {
                                continue;
                            }

                            Schedule copy = s.copy();
                            Assignment temp = copy.data[i][j];
                            copy.data[i][j] = copy.data[i][hour];
                            copy.data[i][hour] = temp;
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

        //find for each lesson all the j positions that it is present
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (Lesson l : s.lessons) {
            ArrayList<Integer> js = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 35; j++) {
                    if (l == data[i][j].lesson) {
                        js.add(j);
                    }
                }
            }
            Lesson le = new Lesson(l.getCode(), l.getName(), l.getYear(), l.getHours(), l.getClassroom(), js);
            lessons.add(le);
        }

        //for each lesson, calculate variance and give score
        for (Lesson l : lessons) {
            int counter1 = 0, counter2 = 0, counter3 = 0, counter4 = 0, counter5 = 0;
            for (Integer j : l.presentAtJs) {
                if (j < 7) {
                    counter1++;
                } else if (j < 14) {
                    counter2++;
                } else if (j < 21) {
                    counter3++;
                } else if (j < 28) {
                    counter4++;
                } else if (j < 35) {
                    counter5++;
                }
            }
            double[] data2 = {counter1, counter2, counter3, counter4, counter5};
            MyMath st = new MyMath();
            double mean = st.getMean(data2, data2.length);
            double var = st.getVariance(data2, data2.length, mean);

            //10000, for the 1 hour per week lessons, no penalty                    0.2000001
            if (var < 0.21) {
                continue;
            }

            //20000, for the 2 hours per week lessons, 2 penalty                    0.8000001
            if (var > 0.80 && var < 0.81) {
                System.out.println("VarianceLesson conflict: " + l.getName() + " of " + l.getClassroom()
                        + " has variance of " + MyMath.round(var, 2) + ", penalty 2, because " + counter1 + counter2 + counter3 + counter4 + counter5);
            }

            //11000 or 11100, for the 2(or3) hours per week lessons, no penalty     0.3000001
            if (var > 0.3 && var < 0.31) {
                continue;
            }

            //30000, for the 3 hours per week lessons, 6 penalty                    1.8000001
            if (var > 1.8 && var < 1.81) {
                System.out.println("VarianceLesson conflict: " + l.getName() + " of " + l.getClassroom()
                        + " has variance of " + MyMath.round(var, 2) + ", penalty 6, because " + counter1 + counter2 + counter3 + counter4 + counter5);
            }

            //21000, for the 3 hours per week lessons, 4 penalty                    0.7999998
            if (var > 0.79 && var < 0.80) {
                System.out.println("VarianceLesson conflict: " + l.getName() + " of " + l.getClassroom()
                        + " has variance of " + MyMath.round(var, 2) + ", penalty 4, because " + counter1 + counter2 + counter3 + counter4 + counter5);
            }

            //40000, for the 4 hours per week lessons, 8 penalty                    3.2000001
            if (var > 3.2 && var < 3.3) {
                System.out.println("VarianceLesson conflict: " + l.getName() + " of " + l.getClassroom()
                        + " has variance of " + MyMath.round(var, 2) + ", penalty 8, because " + counter1 + counter2 + counter3 + counter4 + counter5);
            }

            //31000, for the 4 hours per week lessons, 6 penalty                    1.7000006
            if (var > 1.7 && var < 1.71) {
                System.out.println("VarianceLesson conflict: " + l.getName() + " of " + l.getClassroom()
                        + " has variance of " + MyMath.round(var, 2) + ", penalty 6, because " + counter1 + counter2 + counter3 + counter4 + counter5);
            }

            //22000, for the 4 hours per week lessons, 4 penalty                    1.2000006
            if (var > 1.2 && var < 1.21) {
                System.out.println("VarianceLesson conflict: " + l.getName() + " of " + l.getClassroom()
                        + " has variance of " + MyMath.round(var, 2) + ", penalty 4, because " + counter1 + counter2 + counter3 + counter4 + counter5);
            }

            //21100, for the 4 hours per week lessons, 2 penalty                    0.7000001
            if (var > 0.7 && var < 0.71) {
                System.out.println("VarianceLesson conflict: " + l.getName() + " of " + l.getClassroom()
                        + " has variance of " + MyMath.round(var, 2) + ", penalty 2, because " + counter1 + counter2 + counter3 + counter4 + counter5);
            }

            //11110, for the 4 hours per week lessons, no penalty                   0.2000001
            if (var >= 0.2 && var < 0.21) {
                continue;
            }

        }

    }

}
