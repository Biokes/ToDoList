package task.main.drill;

public class Exercise {
    public static String reverseSentence(String given){
        String[] output = given.split(" ");
        StringBuilder builder =  new StringBuilder();
        for (int counter = output.length-1; counter >= 0 ; counter++){
            builder.append(output[counter]);
        }
        return builder.toString();
    }
}
