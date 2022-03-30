package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.payment;

public enum NameOfRequestParameterToAddPayment
{
    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_SENDER_OF_ADDED_PAYMENT("card_number_of_sender_of_added_payment"),
    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_RECEIVER_OF_ADDED_PAYMENT("card_number_of_receiver_of_added_payment"),
    NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_ADDED_PAYMENT("money_of_added_payment"),
    NAME_OF_REQUEST_PARAMETER_OF_DATE_OF_ADDED_PAYMENT("date_of_added_payment");

    private final String value;

    private NameOfRequestParameterToAddPayment(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
