
import writers.WriteSchedule;
import algorithm.Schedule;
import algorithm.Searcher;
import algorithm.ScheduleMaker;
import criteria.Criteria;
import criteria.CriteriaMaxHoursPerDay;
import criteria.CriteriaMaxHoursPerWeek;
import criteria.CriteriaNoHiatus;
import criteria.CriteriaTeleportation;
import criteria.CriteriaTwoHourLimit;
import criteria.CriteriaVarianceClass;
import criteria.CriteriaVarianceLesson;
import loaders.LoadTeachers;
import loaders.LoadLessons;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class mainApp {

    public static void main(String[] args) {
        Schedule optimal = null;
        
        //select number of dives
        Scanner reader = new Scanner(System.in);  
        System.out.println("Insert number of dives: ");
        int k = reader.nextInt(); 
        reader.close();
        
        ArrayList<Schedule> dives = new ArrayList<>();

        // add with priority (from most important to less important)
        List<Criteria> criteriaList = new ArrayList<>(); 
        criteriaList.add(new CriteriaTeleportation());      //10 000 score
        criteriaList.add(new CriteriaNoHiatus());           //1 000 score
        criteriaList.add(new CriteriaMaxHoursPerDay());     //100 score
        criteriaList.add(new CriteriaMaxHoursPerWeek());    //50 score
        criteriaList.add(new CriteriaTwoHourLimit());       //25 score
        criteriaList.add(new CriteriaVarianceClass());      //20,10 score
        criteriaList.add(new CriteriaVarianceLesson());     //8,6,4,2 score
        
        LoadLessons loadLessons = new LoadLessons();
        LoadTeachers loadTeachers = new LoadTeachers();
        loadLessons.loadfile("lessons.txt");
        loadTeachers.loadfile("teachers.txt");

        boolean optimalfound = false;
        double currentBest = 100000;
        
        long start = System.currentTimeMillis();
        
        for (int i = 0; i < k; i++) {

            ScheduleMaker maker = new ScheduleMaker();
            Schedule initialSchedule = maker.makeRandomSchedule(loadTeachers, loadLessons);
            
            Searcher searcher = new Searcher(criteriaList, initialSchedule);

            System.out.println("*******************************************************");
            System.out.println("***********         Dive: " + (i + 1) + "/" + k + " ....         **********");
            System.out.println("*******************************************************");
            Schedule solution = searcher.search();
            

            if(solution.score(criteriaList)<=currentBest) {
            	currentBest = solution.score(criteriaList);
            }
            
            System.out.println("Score of dive " + (i + 1) + " is " + solution.score(criteriaList));
            System.out.println("Current Best is " + currentBest + "\n");
            
            dives.add(solution);
            if (solution.score(criteriaList) <= 10) {
                optimalfound = true;
                optimal = solution; 
                break;
            }
        }

        long end = System.currentTimeMillis();
        
        Schedule finalSolution = null;

        if (!optimalfound) {
            int score = dives.get(0).score(criteriaList);
            finalSolution = dives.get(0);
            
            for (Schedule d : dives) {
                if (d.score(criteriaList) < score) {
                    score = d.score(criteriaList);
                    finalSolution = d;
                }
            }
        } else {
            finalSolution = optimal;
        }

        System.out.println("*******************************************************");
        System.out.println("***********             Solution  .          **********");
        System.out.println("*******************************************************");

        WriteSchedule writeSchedule = new WriteSchedule();
        writeSchedule.printToScreen(finalSolution);

        System.out.println("\nScore of final state is " + finalSolution.score(criteriaList) + ", processed in "+(double)(end - start) / 1000 + " sec." + "\n");

        //print the criteria that could not be fixed after final solution is found
        System.out.println("\n --------- Below are the criteria that could not be fixed ---------\n");
        for (Criteria c : criteriaList) {
            c.printCriteria(finalSolution);
        }

        writeSchedule.writeFileClasses("schedule.txt", finalSolution);
        writeSchedule.writeFileTeachers("schedule_teachers.txt", finalSolution);
    }

}
