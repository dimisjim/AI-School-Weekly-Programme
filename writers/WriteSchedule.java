//DIMITRIS MORAITIDIS, 3100240

package writers;

import algorithm.Schedule;
import model.Assignment;
import model.Teacher;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class WriteSchedule {

    public void printToScreen(Schedule solution) {
        for (int i = 0; i < 9; i++) {
            switch (i) {
                case 0:
                    System.out.print("A1 :");
                    break;
                case 1:
                    System.out.print("A2 :");
                    break;
                case 2:
                    System.out.print("A3 :");
                    break;
                case 3:
                    System.out.println();
                    System.out.print("B1 :");
                    break;
                case 4:
                    System.out.print("B2 :");
                    break;
                case 5:
                    System.out.print("B3 :");
                    break;
                case 6:
                    System.out.println();
                    System.out.print("C1 :");
                    break;
                case 7:
                    System.out.print("C2 :");
                    break;
                case 8:
                    System.out.print("C3 :");
                    break;
            }

            int day = 1;
            String s = null;
            for (int j = 0; j < 35; j++) {
            	
                Assignment a = solution.data[i][j];
                if (!a.empty) {
                	s = a.teacher.getName().substring(0,4) + " " + a.lesson.getName().substring(0,2) + " |";
                } else {
                	s = "- --"  + "    |";
                }
                if(day==7) {
                	System.out.print(s+"|");
                	day=0;
                } else {
                	System.out.print(s);
                }
                day++;

            }
            System.out.println();
        }
    }

    public void writeFileClasses(String filename, Schedule solution) {
    	
    	
        File f = null;
        PrintWriter writer = null;

        try {
            f = new File(filename);
        } catch (NullPointerException e) {
            System.err.println("File not found.");
        }

        try {
            writer = new PrintWriter(f, "UTF-8");
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       
        //each slot has 22 char length
        //fill each slot with the given String + the remaining empty spaces needed to add up to 22 in total
        
        int classroom = 1;
        int year = 1;
        
        //make a new array with contents of the chosen schedule (solution)
        //if one slot does not contain an assignment, populate it with 22 white spaces
        String[][] populateLessons = new String[9][35];
        String[][] populateTeachers = new String[9][35];
        for (int i = 0; i<9; i++) {
        	for (int j = 0; j < 35; j++) {
        		Assignment a = solution.data[i][j];
        		if (!a.empty) {
        			populateLessons[i][j] = a.lesson.getName();
                    populateTeachers[i][j] = a.teacher.getName();
        		}
        		else {
        			populateLessons[i][j] = "";
        			populateTeachers[i][j] = "";
        		}
                
        	} 
        }
        
        writer.println("                                   -------------------- Gymnasium Schedule --------------------\n");
        writer.println("                                                 ---------- " + year + "st grade ----------\n");
        
        for (int i = 0; i < 9; i++)    {
        	if (year==2) {
        		writer.println("                                                 ---------- 2nd grade ----------\n");
        		year = 4;
        	}
        	else if(year==5){
        		writer.println("                                                 ---------- 3rd grade ----------\n");
        		year = 6;
        	}
        	
        	String yearLetter = "A";
        	if (year==2 || year==4) yearLetter = "B";
        	if (year==5 || year==6) yearLetter = "C";
        	
            writer.println("                                                         ----- " + yearLetter + classroom + " -----\n");
            writer.println("       |        Monday        |        Tuesday       |       Wednesday      |       Thursday       |        Friday        |");
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 1st -|" + populateTXT(populateLessons, 1, i));
            writer.println(" Prof: |" + populateTXT(populateTeachers, 1, i));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 2nd -|" + populateTXT(populateLessons, 2, i));
            writer.println(" Prof: |" + populateTXT(populateTeachers, 2, i));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 3rd -|" + populateTXT(populateLessons, 3, i));
            writer.println(" Prof: |" + populateTXT(populateTeachers, 3, i));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 4th -|" + populateTXT(populateLessons, 4, i));
            writer.println(" Prof: |" + populateTXT(populateTeachers, 4, i));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 5th -|" + populateTXT(populateLessons, 5, i));
            writer.println(" Prof: |" + populateTXT(populateTeachers, 5, i));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 6th -|" + populateTXT(populateLessons, 6, i));
            writer.println(" Prof: |" + populateTXT(populateTeachers, 6, i));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 7th -|" + populateTXT(populateLessons, 7, i));
            writer.println(" Prof: |" + populateTXT(populateTeachers, 7, i));
            writer.println("        ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n");

            classroom++;
            if (classroom == 4) {
                classroom = 1;
                year++;
            }
            
        }

        writer.close();
    }

    
    public void writeFileTeachers(String filename, Schedule solution) {
    	File f = null;
        PrintWriter writer = null;
        
        ArrayList<Teacher> teachers = solution.teachers;
        
        try {
            f = new File(filename);
        } catch (NullPointerException e) {
            System.err.println("File not found.");
        }

        try {
            writer = new PrintWriter(f, "UTF-8");
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        for (Teacher t : teachers) {
        	String [] lessonsSlots = new String[35];
        	String [] classroomSlots = new String[35];
        	for (int i = 0; i < 9; i++) {
        		for (int j = 0; j < 35; j++) {
        			if(solution.data[i][j].teacher == t) {
        				lessonsSlots[j] = solution.data[i][j].lesson.getName();
        				classroomSlots[j] = solution.data[i][j].lesson.getClassroom();
        			}
        			else if(lessonsSlots[j]==null && classroomSlots[j]== null) {
        				lessonsSlots[j] = "";
        				classroomSlots[j] = "";
        			}
        		}
        	}
        	
        	writer.println("Schedule for Teacher: " +  t.getName()+"\n");
        	
        	writer.println("       |        Monday        |        Tuesday       |       Wednesday      |       Thursday       |        Friday        |");
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 1st -|" + populateTXTTeachers(lessonsSlots, 1));
            writer.println("Class: |" + populateTXTTeachers(classroomSlots, 1));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 2nd -|" + populateTXTTeachers(lessonsSlots, 2));
            writer.println("Class: |" + populateTXTTeachers(classroomSlots, 2));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 3rd -|" + populateTXTTeachers(lessonsSlots, 3));
            writer.println("Class: |" + populateTXTTeachers(classroomSlots, 3));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 4th -|" + populateTXTTeachers(lessonsSlots, 4));
            writer.println("Class: |" + populateTXTTeachers(classroomSlots, 4));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 5th -|" + populateTXTTeachers(lessonsSlots, 5));
            writer.println("Class: |" + populateTXTTeachers(classroomSlots, 5));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 6th -|" + populateTXTTeachers(lessonsSlots, 6));
            writer.println("Class: |" + populateTXTTeachers(classroomSlots, 6));
            writer.println("       |¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯|");
            writer.println("- 7th -|" + populateTXTTeachers(lessonsSlots, 7));
            writer.println("Class: |" + populateTXTTeachers(classroomSlots, 7));
            writer.println("        ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n");

        }
        
        writer.close();
    }
    
    //this returns empty String spaces.
    public String repeat(int count) {
        return repeat(count, " ");
    }
    public String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

    //this populates all the schedule slots using the populateLessons/Teachers arrays.
    public String populateTXT(String s[][], int hour, int row){
		return s[row][hour-1+0*7]+repeat(22-s[row][hour-1+0*7].length())+"|" 
				+ s[row][hour-1+1*7]+repeat(22-s[row][hour-1+1*7].length())+"|"
				 + s[row][hour-1+2*7]+repeat(22-s[row][hour-1+2*7].length())+"|"
				 + s[row][hour-1+3*7]+repeat(22-s[row][hour-1+3*7].length())+"|"
				 + s[row][hour-1+4*7]+repeat(22-s[row][hour-1+4*7].length())+"|";
    	
    }
    public String populateTXTTeachers(String s[], int hour){
		return s[hour-1+0*7]+repeat(22-s[hour-1+0*7].length())+"|" 
				+ s[hour-1+1*7]+repeat(22-s[hour-1+1*7].length())+"|"
				 + s[hour-1+2*7]+repeat(22-s[hour-1+2*7].length())+"|"
				 + s[hour-1+3*7]+repeat(22-s[hour-1+3*7].length())+"|"
				 + s[hour-1+4*7]+repeat(22-s[hour-1+4*7].length())+"|";
    	
    }
}
