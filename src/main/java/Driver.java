import java.io.*;
import java.util.Scanner;


public class Driver {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src/main/resources/input.txt");

        Scanner sc = new Scanner(file);

        while(sc.hasNext()){

            String[] cm = sc.nextLine().split(" ");

            System.out.println(cm[0]);

            if(cm[0]=="exit"){
                return;
            }

        }

    }

}
