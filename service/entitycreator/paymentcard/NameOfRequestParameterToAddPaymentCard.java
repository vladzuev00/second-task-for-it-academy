package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.paymentcard;

public enum NameOfRequestParameterToAddPaymentCard
{
    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_ADDED_PAYMENT_CARD("card_number_of_added_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_EXPIRATION_DATE_OF_ADDED_PAYMENT_CARD("expiration_date_of_added_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_PAYMENT_SYSTEM_OF_ADDED_PAYMENT_CARD("payment_system_of_added_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_CVC_OF_ADDED_PAYMENT_CARD("cvc_of_added_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_CLIENT_OF_ADDED_PAYMENT_CARD("phone_number_of_client_of_added_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_BANK_OF_ADDED_PAYMENT_CARD("name_of_bank_of_added_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_PAYMENT_CARD("password_of_added_payment_card");

    private final String value;

    private NameOfRequestParameterToAddPaymentCard(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
