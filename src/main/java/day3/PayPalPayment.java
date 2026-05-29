package day3;

public class PayPalPayment extends Payment implements Verifiable{
    private String email;

    public PayPalPayment(String email, double Amount){
        super(Amount);
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public boolean verifyPayment(){
       if (email.contains("@")){
            return true;
        }

        System.out.println("\nInvalid Email");

        return false;
    }

    public void paymentMethod(){
        System.out.println("Processing PayPal Method...\n");
    }

}
