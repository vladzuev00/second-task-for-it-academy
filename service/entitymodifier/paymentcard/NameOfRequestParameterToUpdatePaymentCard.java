package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.paymentcard;

public enum NameOfRequestParameterToUpdatePaymentCard
{
    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_UPDATED_PAYMENT_CARD("card_number_of_updated_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_EXPIRATION_DATE_OF_UPDATED_PAYMENT_CARD("expiration_date_of_updated_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_PAYMENT_SYSTEM_OF_UPDATED_PAYMENT_CARD("payment_system_of_updated_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_CVC_OF_UPDATED_PAYMENT_CARD("cvc_of_updated_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_CLIENT_OF_UPDATED_PAYMENT_CARD("phone_number_of_client_of_updated_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_BANK_OF_UPDATED_PAYMENT_CARD("name_of_bank_of_update_payment_card"),
    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_PAYMENT_CARD("password_of_updated_payment_card");

    private final String value;

    private NameOfRequestParameterToUpdatePaymentCard(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
