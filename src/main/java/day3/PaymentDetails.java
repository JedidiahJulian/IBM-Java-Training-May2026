package day3;

public class PaymentDetails {
    private final String transactionID;
    private final double Amount;
    private final PaymentType paymentType;
    private final String timeStamp;

    public PaymentDetails(String transactionID, double Amount, PaymentType paymentType, String timeStamp){
        this.transactionID = transactionID;
        this.Amount = Amount;
        this.paymentType = paymentType;
        this.timeStamp = timeStamp;
    }

    public String toString() {
        return "Transaction ID: " + transactionID +
               "\nAmount: " + Amount +
               "\nPayment Type: " + paymentType.getPaymentType() +
               "\nTimestamp: " + timeStamp +
               "\n";
    }
}
