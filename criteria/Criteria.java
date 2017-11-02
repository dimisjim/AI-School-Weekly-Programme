//DIMITRIS MORAITIDIS, 3100240

package criteria;

import algorithm.Schedule;
import java.util.List;

public abstract class Criteria {
    public abstract double evaluate(Schedule s);
    public abstract List<Schedule> findChildren(Schedule s);
    public abstract void printCriteria(Schedule s);
}
