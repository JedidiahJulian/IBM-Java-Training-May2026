package day2;
import day2.*;
import java.util.*;

public class Main {
    public static void main(String[] args){
        Book book1 = new Book("Harry Potter - Deathly Hollows", "JK Rowling", false);
        Book book2 = new Book("Harry Potter - Sorcerers Stone", "JK Rowling", true);
        Book book3 = new Book("Harry Potter - Half Blood Prince", "JK Rowling", false);

        Library library = new Library();

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        int num = 0;

        Scanner sc = new Scanner(System.in);

        do{
            library.showAllBooks();
            System.out.println();
            System.out.println("1 - Borrow Book");
            System.out.println("2 - Return Book");
            System.out.println("3 - End program");
            System.out.print("Enter choice: ");
            num = sc.nextInt();

            if (num == 1){
                System.out.print("Enter index of book: ");
                int index = sc.nextInt();

                library.borrowBook(index);
            }else if (num == 2){
                System.out.print("Enter index of book: ");
                int index2 = sc.nextInt();

                library.returnBook(index2);
            }else if (num == 3){
                num = 3;
            }else{
                System.out.println("\nInvalid Input\n");
            }
    
        }while(num != 3);

        System.out.println("Program terminated");

        sc.close();

    }
}
