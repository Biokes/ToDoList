import java.util.ArrayList;
import java.util.Arrays;

public class FireDrill{

    public static int getHighest(int[] ints){
        int max =Integer.MIN_VALUE;
        ArrayList<Integer> output = new ArrayList<>();
        for(int number : ints){
            for(int count: ints){
                if(number == count)
                    continue;
                output.add(number*count);
            }
        }
        for(int num : output){
            if(num > max)
                max = num;
        }
        return max;
    }
}

