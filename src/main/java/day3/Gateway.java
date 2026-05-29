package day3;

public sealed abstract class Gateway permits PaymentGateway {
    public abstract void processPayment(Payment payment);
}
