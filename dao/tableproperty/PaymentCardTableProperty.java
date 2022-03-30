package by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty;

public enum PaymentCardTableProperty
{
    NAME_OF_TABLE("payment_cards"), NAME_OF_COLUMN_OF_ID("id"), NAME_OF_COLUMN_OF_CARD_NUMBER("card_number"),
    NAME_OF_COLUMN_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE("number_of_month_of_expiration_date"),
    NAME_OF_COLUMN_OF_YEAR_OF_EXPIRATION_DATE("year_of_expiration_date"),
    NAME_OF_COLUMN_OF_PAYMENT_SYSTEM("payment_system"), NAME_OF_COLUMN_OF_ENCRYPTED_CVC("encrypted_cvc"),
    NAME_OF_COLUMN_OF_CLIENT_ID("client_id"), NAME_OF_COLUMN_OF_NAME_OF_BANK("name_of_bank"),
    NAME_OF_COLUMN_OF_ENCRYPTED_PASSWORD("encrypted_password");

    private final String value;

    private PaymentCardTableProperty(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
