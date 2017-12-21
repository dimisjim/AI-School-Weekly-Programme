
package loaders;

import model.Teacher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LoadTeachers {

    public ArrayList<Teacher> teachers = new ArrayList<Teacher>();

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
            Teacher teacher = null;
            String name = null;
            String code = null;
            ArrayList<String> lessons_codes = new ArrayList<String>();
            int max_hours_per_day = 0;
            int max_hours_per_week = 0;

            boolean foundInitialTag = false;
            boolean hasTeacherTag = false;

            line = reader.readLine();
            while (line != null) {

                if (line.trim().equals("TEACHERS_LIST") || line.trim().equals("teachers_list")) {
                    foundInitialTag = true;
                    line = reader.readLine();
                    while (line != null) {

                        if (line.trim().equals("{")) {
                            line = reader.readLine();
                            while (line != null) {

                                if (line.trim().equals("TEACHER") || line.trim().equals("teacher")) {
                                    hasTeacherTag = true;
                                    line = reader.readLine();

                                    if (line.trim().equals("{")) {
                                        line = reader.readLine();
                                        while (!line.trim().equals("}")) {

                                            StringTokenizer st = new StringTokenizer(line.trim());
                                            st.nextToken();

                                            if (line.trim().startsWith("TEACHER_NAME") || line.trim().startsWith("teacher_name")) {

                                                name = st.nextToken().substring(1);
                                                while (st.hasMoreTokens()) {
                                                    name = name.concat(" ");
                                                    name = name.concat(st.nextToken());
                                                }
                                                name = name.substring(0, name.length() - 1);

                                            } else if (line.trim().startsWith("TEACHER_CODE") || line.trim().startsWith("teacher_code")) {
                                                code = st.nextToken();
                                            } else if (line.trim().startsWith("LESSONS_CODES") || line.trim().startsWith("lessons_codes")) {

                                                lessons_codes = new ArrayList<String>();
                                                while (st.hasMoreTokens()) {
                                                    lessons_codes.add(st.nextToken().substring(0, 3));
                                                }

                                            } else if (line.trim().startsWith("MAX_HOURS_PER_WEEK") || line.trim().startsWith("max_hours_per_week")) {
                                                max_hours_per_week = Integer.parseInt(st.nextToken());
                                            } else if (line.trim().startsWith("MAX_HOURS_PER_DAY") || line.trim().startsWith("max_hours_per_day")) {
                                                max_hours_per_day = Integer.parseInt(st.nextToken());
                                            }

                                            line = reader.readLine();

                                        } //check all tags inside TEACHER tag, loop
                                        counter++;

                                        if ((code != null) && (lessons_codes != null) && (name != null) && (max_hours_per_day != 0)
                                                && (max_hours_per_week != 0)) { //check if tag contains all fields

                                            teacher = new Teacher(code, name, lessons_codes, max_hours_per_day, max_hours_per_week);
                                            teachers.add(teacher);

                                        } else if (name == null) {
                                            System.err.println("Teacher tag #" + counter + " does not contain 'TEACHER_NAME' field.\n");
                                        } else if (max_hours_per_week == 0) {
                                            System.err.println("Teacher tag #" + counter + " does not contain 'MAX_HOURS_PER_WEEK' field.\n");
                                        } else if (max_hours_per_day == 0) {
                                            System.err.println("Teacher tag #" + counter + " does not contain 'MAX_HOURS_PER_DAY' field.\n");
                                        } else if (code == null) {
                                            System.err.println("Teacher tag #" + counter + " does not contain 'TEACHER_CODE' field.\n");
                                        } else if (lessons_codes == null) {
                                            System.err.println("Teacher tag #" + counter + " does not contain 'LESSONS_CODES' field.\n");
                                        }

                                        //re-initialization of vars to default values
                                        name = null;
                                        code = null;
                                        lessons_codes = null;
                                        max_hours_per_day = 0;
                                        max_hours_per_week = 0;
                                        line = reader.readLine();

                                    } //if ({ is found)
                                    else {
                                        line = reader.readLine();
                                    }

                                } //if (TEACHER is found)
                                else {
                                    line = reader.readLine();
                                }

                            } //main while loop (inside TEACHERS_LIST)
                            if (!hasTeacherTag) {
                                System.err.println("There are no individual 'TEACHER' tags inside 'TEACHERS_LIST' tag");
                            }

                        } //first {
                        else {
                            line = reader.readLine();
                        }

                    } //loop until first bracket is found

                } //if (TEACHERS_LIST is found)
                else {
                    line = reader.readLine();
                }

            } //loop until TEACHERS_LIST is found
            if (!foundInitialTag) {
                System.err.println("Initial 'TEACHERS_LIST' tag was not found");
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

    public void print(int x) {
        if (x == 4) {
            for (int i = 0; i < teachers.size(); i++) {
                System.out.println();
                System.out.println(i + 1 + ". " + teachers.get(i).toString());
            }
        } else {
            System.err.println("Wrong input argument");
        }

    }

}
