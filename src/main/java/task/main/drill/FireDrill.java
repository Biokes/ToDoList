package task.main.drill;

import africa.semoicolon.exceptions.TaskNotFoundException;

import java.util.ArrayList;

public class FireDrill{

    public static int getHighest(int[] ints){
        if(ints.length<=1)
            throw new TaskNotFoundException("Length should be greater than 1");
        int max = Integer.MIN_VALUE;
        for(int count =0; count< ints.length;count++){
            for(int counter =0; counter< ints.length;counter++){
                if(count == counter){continue;}
                if(max < ints[count]* ints[counter]) max = ints[count]* ints[counter];
            }
        }
        return max;
    }
}

