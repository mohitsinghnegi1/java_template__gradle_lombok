package extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Driver1 {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src/main/resources/input.txt");

        Scanner sc = new Scanner(file);

        while (sc.hasNext()) {

            String[] cm = sc.nextLine().split(" ");

            System.out.println(cm[0]);

            if (cm[0].equals("exit") ){
                return;
            }

            if(cm[0] instanceof String){

            }
            Set<Integer> set1 = new HashSet<Integer>();
            set1.add(2);
            set1.remove(3);

            set1.contains(2);

            set1.stream().forEach((Integer integer)->{
                System.out.printf("consumer interface"+integer);
            });

            set1.stream().filter((Integer integer)->{
                return integer==2;
            }).sorted((Integer i1 , Integer i2)->{
                return i1<=i2?-1:1;
            });

//            filter
//            sorting
        }
    }
}







