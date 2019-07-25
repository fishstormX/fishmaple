package fishmaple.utils;

import java.util.Random;

public class IdentifyingCodeUtil {

    private static char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k','l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String getIdentifyingCodeUtil(){
        StringBuilder str=new StringBuilder();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i <6; i++){
            char num = ch[random.nextInt(ch.length)];
            str .append(num);
        }
        return str.toString();
    }
}
