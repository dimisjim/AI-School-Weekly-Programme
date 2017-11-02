//DIMITRIS MORAITIDIS, 3100240

package algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyMath {
	
	public double getMean(double []data, double size)
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }
	
	//Variance of sample
	public double getVariance(double []data, double size, double mean)
    {
		
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }
	
	//rounds a double
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
