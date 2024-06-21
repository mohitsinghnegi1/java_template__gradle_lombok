package extra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src/main/resources/input.txt");
        Scanner sc = new Scanner(file);

        while(sc.hasNext()){

            String[] cm = sc.nextLine().split(" ");
            if(cm[0].equals("command")){
                System.out.println(""+cm[0]);

                // How to parse
                System.out.println(Integer.parseInt("9")); // Sting to int
                System.out.println(String.valueOf(9.0)); // Any number to string
                System.out.println(Double.parseDouble("9.0002"));

            }

            if(cm[0].equals("exit")){
                System.exit(0);
            }

        }

    }
}
