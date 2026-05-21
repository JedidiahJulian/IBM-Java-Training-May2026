package day3;

public abstract class Payment {
    private double Amount;

    public abstract void paymentMethod();

    public Payment(double Amount){
       this.Amount = Amount;
    }

    public double getAmount(){
        return this.Amount;
    }

    public void displayAmount(){
        System.out.println("Amount: " + Amount);
    }
}
