import java.util.*;

public class Assignments {
    public static void main(String[] args){
        int choices = 0;
        Scanner sc = new Scanner(System.in);
        
        do{
            System.out.println("Choose assignment: ");
            System.out.println("1 - Assignment 1 (if - else / blackjack)");
            System.out.println("2 - Assignment 2 (switch / day)");
            System.out.println("3 - Assignment 3 (for, while, do-while)");
            System.out.println("4 - Exit program");
            System.out.println();

            System.out.print("Enter assignment: ");
            choices = sc.nextInt();

            System.out.println();

            if (choices == 1){
                System.out.println(getBlackjack(20, 19));
                System.out.println(getBlackjack(21, 22));
                System.out.println(getBlackjack(10, 11));
                System.out.println(getBlackjack(5, 4));
            }else if (choices == 2){
                System.out.println("Choose implementation: ");
                System.out.println("1 - Traditional Switch Case Implementation");
                System.out.println("2 - Pattern Matching for Switch");
                System.out.print("Enter number: ");
                int implementation = sc.nextInt();

                if (implementation == 1){
                    System.out.print("Enter number (Traditional Switch Case): ");
                    int day = sc.nextInt();

                    System.out.println();
                    System.out.println(getDay1(day));
                    System.out.println();
                }else if (implementation == 2){
                    System.out.print("Enter number (Pattern Matching): ");
                    int day = sc.nextInt();

                    System.out.println();
                    System.out.println(getDay2(day));
                    System.out.println();
                }

            }else if (choices == 3){
                int number = 0;

                while (number <= 0 || number > 21){
                    System.out.print("Enter number: ");
                    number = sc.nextInt();
                }

                getPattern(number);
            }

        }while (choices != 4);

        System.out.println("Program terminated\n");

        sc.close();
    }

    public static void getPattern(int number){
        for (int i = 1; i <= number; i++){
            for (int j = 1; j <= i; j++){
                System.out.print(j);
            }
            System.out.println();
        }

        System.out.println();
    }

    public static String getDay1(int day){
        switch(day){
            case 1:
               return "Monday\n";
            case 2:
                return "Tuesday\n";
            case 3:
                return "Wednesday\n";
            case 4:
                return "Thursday\n";
            case 5:
                return "Friday\n";
            case 6:
                return "Saturday\n";
            case 7:
               return "Sunday\n";
            default:
               return "Invalid day\n";
        }
    }

    public static String getDay2(int day){
       String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

       return switch (day){
        case 1 -> days[0];
        case 2 -> days[1];
        case 3 -> days[2];
        case 4 -> days[3];
        case 5 -> days[4];
        case 6 -> days[5];
        case 7 -> days[6];
        default -> "Invalid day";
       };
    }

    public static int getBlackjack(int num1, int num2){
       if (num1 > 21 && num2 > 21){
        return 0;
       }else if (num1 > 21){
        return num2;
       }else if (num2 > 21){
        return num1;
       }else{
        return Math.max(num1, num2);
       }
    }
}
