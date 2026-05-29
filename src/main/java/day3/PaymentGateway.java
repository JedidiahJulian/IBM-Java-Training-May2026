package day3;

public non-sealed class PaymentGateway extends Gateway {
    public void processPayment(Payment payment){
        payment.paymentMethod();
        System.out.println("Payment processed successfully! Thank you $$$");
    }
}
