package day3;

public class CreditCardPayment extends Payment implements Verifiable {
    private String cardNumber;

    public CreditCardPayment(String cardNumber, double Amount){
        super(Amount);
        this.cardNumber = cardNumber;
    }

    public void paymentMethod(){
        System.out.println("Processing Credit Card Payment...\n");
    }

    public boolean verifyPayment(){
        if (cardNumber.length() == 16){
            return true;
        }

        System.out.println("\nInvalid Card Number");

        return false;
    }




}
