package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.payment;

public enum NameOfRequestParameterToUpdatePayment
{
    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_SENDER_OF_UPDATED_PAYMENT("card_number_of_sender_of_updated_payment"),
    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_RECEIVER_OF_UPDATED_PAYMENT("card_number_of_receiver_of_updated_payment"),
    NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_UPDATED_PAYMENT("money_of_updated_payment"),
    NAME_OF_REQUEST_PARAMETER_OF_DATE_OF_UPDATED_PAYMENT("date_of_updated_payment");

    private final String value;

    private NameOfRequestParameterToUpdatePayment(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
