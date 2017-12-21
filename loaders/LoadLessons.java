

package loaders;

import model.Lesson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LoadLessons {

    public ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsA1 = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsA2 = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsA3 = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsB1 = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsB2 = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsB3 = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsC1 = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsC2 = new ArrayList<Lesson>();
    public ArrayList<Lesson> lessonsC3 = new ArrayList<Lesson>();

    public void loadfile(String data) {

        int counter = 0;

        File f = null;
        BufferedReader reader = null;
        String line;

        try {
            f = new File(data);
        } catch (NullPointerException e) {
            System.err.println("File not found.");
        }

        try {
            reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        }

        try {

            //fields
            String name = null;
            int hours = 0;
            String code = null;
            String year = null;

            boolean foundInitialTag = false;
            boolean hasLessonTag = false;

            line = reader.readLine();
            while (line != null) {

                if (line.trim().equals("LESSONS_LIST") || line.trim().equals("lessons_list")) {
                    foundInitialTag = true;
                    line = reader.readLine();
                    while (line != null) {

                        if (line.trim().equals("{")) {
                            line = reader.readLine();
                            while (line != null) {

                                if (line.trim().equals("LESSON") || line.trim().equals("lesson")) {
                                    hasLessonTag = true;
                                    line = reader.readLine();

                                    if (line.trim().equals("{")) {
                                        line = reader.readLine();
                                        while (!line.trim().equals("}")) {

                                            StringTokenizer st = new StringTokenizer(line.trim());
                                            st.nextToken();

                                            if (line.trim().startsWith("LESSON_NAME") || line.trim().startsWith("lesson_name")) {

                                                name = st.nextToken().substring(1);
                                                while (st.hasMoreTokens()) {
                                                    name = name.concat(" ");
                                                    name = name.concat(st.nextToken());
                                                }
                                                name = name.substring(0, name.length() - 1);

                                            } else if (line.trim().startsWith("LESSON_CODE") || line.trim().startsWith("lesson_code")) {
                                                code = st.nextToken();
                                            } else if (line.trim().startsWith("CLASS") || line.trim().startsWith("class")) {
                                                year = st.nextToken();
                                            } else if (line.trim().startsWith("HOURS_PER_WEEK") || line.trim().startsWith("hours_per_week")) {
                                                hours = Integer.parseInt(st.nextToken());
                                            }

                                            line = reader.readLine();

                                        } //check all tags inside LESSON tag, loop
                                        counter++;

                                        if ((code != null) && (year != null) && (name != null) && (hours != 0)) { //check if tag contains all fields

                                            Lesson lesson1 = new Lesson(code, name, year, hours, year+"1");
                                            Lesson lesson2 = new Lesson(code, name, year, hours, year+"2");
                                            Lesson lesson3 = new Lesson(code, name, year, hours, year+"3");
                                            lessons.add(lesson1);
                                            lessons.add(lesson2);
                                            lessons.add(lesson3);
                                            if (year.equals("A")) {
                                                lessonsA1.add(lesson1);
                                                lessonsA2.add(lesson2);
                                                lessonsA3.add(lesson3);
                                            } else if (year.equals("B")) {
                                                lessonsB1.add(lesson1);
                                                lessonsB2.add(lesson2);
                                                lessonsB3.add(lesson3);
                                            } else if (year.equals("C")) {
                                                lessonsC1.add(lesson1);
                                                lessonsC2.add(lesson2);
                                                lessonsC3.add(lesson3);
                                            }

                                        } else if (name == null) {
                                            System.err.println("Lesson tag #" + counter + " does not contain 'LESSON_NAME' field.\n");
                                        } else if (hours == 0) {
                                            System.err.println("Lesson tag #" + counter + " does not contain 'HOURS_PER_WEEK' field.\n");
                                        } else if (code == null) {
                                            System.err.println("Lesson tag #" + counter + " does not contain 'LESSON_CODE' field.\n");
                                        } else if (year == null) {
                                            System.err.println("Lesson tag #" + counter + " does not contain 'CLASS' field.\n");
                                        }

                                        //re-initialization of vars to default values
                                        name = null;
                                        hours = 0;
                                        code = null;
                                        year = null;
                                        line = reader.readLine();

                                    } //if ({ is found)
                                    else {
                                        line = reader.readLine();
                                    }

                                } //if (LESSON is found)
                                else {
                                    line = reader.readLine();
                                }

                            } //main while loop (inside LESSONS_LIST)
                            if (!hasLessonTag) {
                                System.err.println("There are no individual 'LESSON' tags inside 'LESSONS_LIST' tag");
                            }

                        } //first {
                        else {
                            line = reader.readLine();
                        }

                    } //loop until first bracket is found

                } //if (LESSONS_LIST is found)
                else {
                    line = reader.readLine();
                }

            } //loop until LESSONS_LIST is found
            if (!foundInitialTag) {
                System.err.println("Initial 'LESSONS_LIST' tag was not found");
            }

        } //try
        catch (IOException e) {
            System.err.println("Error reading line " + counter + ".");
        }

        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Error closing file.");
        }

    }

    /*
	 * x=1 print LessonsA
	 * x=2 print LessonsB
	 * x=3 print LessonsC
	 * x=4 print all
     */
    public void print(int x) {
        if (x == 4) {
            for (int i = 0; i < lessons.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessons.get(i).toString());
            }
        } else if (x == 1) {
            for (int i = 0; i < lessonsA1.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsA1.get(i).toString());
            }
            for (int i = 0; i < lessonsA2.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsA2.get(i).toString());
            }
            for (int i = 0; i < lessonsA3.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsA3.get(i).toString());
            }
        } else if (x == 2) {
            for (int i = 0; i < lessonsB1.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsB1.get(i).toString());
            }
            for (int i = 0; i < lessonsB2.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsB2.get(i).toString());
            }
            for (int i = 0; i < lessonsB3.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsB3.get(i).toString());
            }
        } else if (x == 3) {
            for (int i = 0; i < lessonsC1.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsC1.get(i).toString());
            }
            for (int i = 0; i < lessonsC2.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsC2.get(i).toString());
            }
            for (int i = 0; i < lessonsC3.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + lessonsC3.get(i).toString());
            }
        } else {
            System.err.println("Wrong input argument");
        }

    }

}
