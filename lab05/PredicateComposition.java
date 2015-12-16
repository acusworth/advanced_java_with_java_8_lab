package lab05;

import java.util.Arrays;
import java.util.function.Predicate;

public class PredicateComposition
{
    public static void main(String[] args)
    {
        Predicate<Double[]> allPassed = l -> {
            for (double next : l)
            {
                if (next < .60)
                {
                    return false;
                }
            }
            return true;
        };
        
        Predicate<Double[]> bAverage = l -> {
            double sum = 0;
            for (double next : l)
            {
                sum += next;
            }

            return sum / l.length >= .80;
        };

        Predicate<Double[]> lastPerfect = l -> l[l.length - 1] == 1;
        
        
        
        Predicate<Double[]> attendedAll = l ->
        {
            for (double next : l)
            {
                if (next == 0)
                {
                    return true;
                }
            }

            return false;
        };

        
        
        
        
        

        Predicate<Double[]> hasPassed = allPassed.and(bAverage).or(lastPerfect).and(attendedAll.negate());

        // Passed all
        Double[] scores = (Double[]) Arrays.asList(.65, .90, .90, .90, .90, .90).toArray();
        System.out.println(hasPassed.test(scores));

        // Not all passed
        scores = (Double[]) Arrays.asList(.59, .90, .90, .90, .90, .9).toArray();
        System.out.println(hasPassed.test(scores));

        // C average - fail
        scores = (Double[]) Arrays.asList(.70, .70, .70, .70, .70, .70).toArray();
        System.out.println(hasPassed.test(scores));

        // C average but aced last
        scores = (Double[]) Arrays.asList(.70, .70, .70, .70, .70, 1d).toArray();
        System.out.println(hasPassed.test(scores));

        // Failed first by scored perfect on last
        scores = (Double[]) Arrays.asList(.59, .90, .90, .90, .90, 1d).toArray();
        System.out.println(hasPassed.test(scores));

        // Perfect but missed last - fail!
        scores = (Double[]) Arrays.asList(1d, 1d, 1d, 1d, 1d, 0d).toArray();
        System.out.println(hasPassed.test(scores));
    }
}