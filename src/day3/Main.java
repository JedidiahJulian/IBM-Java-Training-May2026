package day3;
import java.time.LocalDateTime;
import java.util.*;

public class Main{
    public static void main(String[] args){
        ArrayList<Payment> payments = new ArrayList<>();
        ArrayList<PaymentDetails> transactions = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        Gateway gateway = new PaymentGateway();

        int choice;

        int transactionNumber = 1;

        System.out.println("===== Payment Processing System =====");

        do{
            System.out.println("\nChoose Payment Method:");
            System.out.println("1. Credit Card");
            System.out.println("2. PayPal");
            System.out.println("3. Bank Transfer");
            System.out.println("4. End program");

            System.out.print("\nEnter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            Payment payment = null;
            PaymentType paymentType = null;

            if (choice == 4){
                break;
            }

            switch (choice){
                case 1:
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter card number: ");
                    String cardNumber = sc.nextLine();

                    payment = new CreditCardPayment(cardNumber, amount);
                    paymentType = new OnlinePaymentType();
                    break;
                case 2:
                    System.out.print("Enter amount: ");
                    double amount1 = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter PayPal email: ");
                    String email = sc.nextLine();

                    payment = new PayPalPayment(email, amount1);
                    paymentType = new OnlinePaymentType();
                    break;
                case 3:
                    System.out.print("Enter amount: ");
                    double amount2 = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter account number: ");
                    String accountNumber = sc.nextLine();

                    payment = new BankTransferPayment(accountNumber, amount2);
                    paymentType = new OfflinePaymentType();
                    break;
                default: 
                    System.out.print("Invalid choice.");
                    continue;
            }

            System.out.println("\n===== Processing Payment =====");
            payment.displayAmount();

            Verifiable verifyPayment = (Verifiable) payment;

            if (verifyPayment.verifyPayment()){
                System.out.println("\n$$$ Verification successful $$$\n");

                gateway.processPayment(payment);

                PaymentDetails details = new PaymentDetails("TRNSCTION - " + transactionNumber, payment.getAmount(), paymentType, LocalDateTime.now().toString());
                transactions.add(details);

                transactionNumber++;
            }else{
                System.out.println("\n*** Verification Failed ***");
            }

        }while (true);

        System.out.println("\n===== Successful Transactions =====");

        if (transactions.isEmpty()){
            System.out.println("No Successful Transactions");
        }else{
            for (PaymentDetails paymentDetails : transactions){
                System.out.println(paymentDetails.toString());
            }
        }

        System.out.println("\n===== Program Terminated =====");

        sc.close();

    }
}