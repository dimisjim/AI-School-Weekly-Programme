//DIMITRIS MORAITIDIS, 3100240

package algorithm;

import criteria.Criteria;
import java.util.List;

public class Searcher {
    List<Criteria> criteriaList;
    Schedule initialSchedule;

    public Searcher(List<Criteria> criteriaList, Schedule initialSchedule) {
        this.criteriaList = criteriaList;
        this.initialSchedule = initialSchedule;
    }
    
    public Schedule search() {
        Schedule currentSchedule = initialSchedule;
                
        while (true) {
            double currentScore = currentSchedule.score(criteriaList);
            
            List<Schedule> children = currentSchedule.getChildren(criteriaList);
            
            if (children == null || children.isEmpty()) {
                break;
            }
            
            double score = Double.MAX_VALUE;
            Schedule next = null;
            
            for (Schedule s : children) {
                double childScore = s.score(criteriaList);
                
                if (childScore < score) {
                    next = s;
                    score = childScore;
                }
            }
            
            if (score >= currentScore) {
                break;
            }                        
            
            currentSchedule = next;
        }
        
        return currentSchedule;
    }
}
