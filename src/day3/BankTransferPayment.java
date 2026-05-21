package day3;

public class BankTransferPayment extends Payment implements Verifiable{
    private String AccountNumber;

    public BankTransferPayment(String AccountNumber, double Amount){
        super(Amount);
        this.AccountNumber = AccountNumber;
    }

    public boolean verifyPayment(){
        if (AccountNumber.length() == 10){
            return true;
        }

        System.out.println("\nInvalid Account Number");

        return false;
    }

    public void paymentMethod(){
       System.out.println("Processing Bank Transfer Payment...\n");
    }
}
