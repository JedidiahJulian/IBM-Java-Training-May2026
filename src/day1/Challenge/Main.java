package Challenge;
import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int number = 0;

        System.out.print("Enter a number: ");
        number = scan.nextInt();

        getZigZag(number);

        scan.close();

    }

    public static void getZigZag(int num){
        int counter = 1;
       
        for (int i = 0; i < num; i++){
            ArrayList<Integer> numbers = new ArrayList<>();

            for (int j = 0; j < num; j++){
                numbers.add(counter++);
            }

            if (i % 2 == 0){
                for (int k = 0; k < numbers.size(); k++){
                    System.out.print(numbers.get(k) + " ");
                }
            }else{
                for (int l = numbers.size() - 1; l >= 0; l--){
                    System.out.print(numbers.get(l) + " ");
                }
            }

            System.out.println();
        }
                    
    }
}
