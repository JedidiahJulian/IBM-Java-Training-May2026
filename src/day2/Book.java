package day2;

public class Book {
    private String title;
    private String author;
    private boolean available;

    public Book(String title, String author, boolean available){
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public String getTitle(){
        return this.title;
    }

    public void borrowBook(){
        if (this.available == true){
            this.available = false;
            System.out.println("\nYou are now borrowing the book\n");
        }else{
            System.out.println("\nBook is already borrowed\n");
        }
    }

    public void returnBook(){
        if (this.available == false){
            this.available = true;
            System.out.println("\nBook returned\n");
        }else{
            System.out.println("\nBooks is already returned\n");
        }
    }

    public String getInfo(){
        return "Title: " + this.title + " | Author: " + this.author + " | Availability " + this.available;
    }
}
