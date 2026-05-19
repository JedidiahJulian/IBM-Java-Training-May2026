package day2;
import day2.Book;
import java.util.*;

public class Library {
    private ArrayList<Book> Books;

    public Library(){
        this.Books = new ArrayList<>();
    }

    public void addBook(Book b){
        Books.add(b);
    };

    public void showAllBooks(){
        for (int i = 0; i < Books.size(); i++){
            System.out.println((i + 1) + " - " + Books.get(i).getInfo());
        }
    }

    public void returnBook(int title){
        Books.get(title - 1).returnBook();
        
    }


    public void borrowBook(int title){
        Books.get(title - 1).borrowBook();
    }
}
