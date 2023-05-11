package MTestUtils;

import java.util.List;
import java.util.Random;

public class RandUtils {
    public static Random rand;

    static{
        rand = new Random();
    }

    private RandUtils(){}

    public static <T> T getRandomFromList(List<T> options){
        //[0, size())
        return options.get(rand.nextInt(options.size()));
    }

}
