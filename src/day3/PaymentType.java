package day3;

public sealed class PaymentType permits OnlinePaymentType, OfflinePaymentType{
    private String typeName;

    public PaymentType(String typeName){
        this.typeName = typeName;
    }

    public String getPaymentType(){
        return typeName;
    }
}