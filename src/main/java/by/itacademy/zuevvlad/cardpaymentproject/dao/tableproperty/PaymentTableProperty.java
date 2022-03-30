package by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty;

public enum PaymentTableProperty
{
    NAME_OF_TABLE("payments"), NAME_OF_COLUMN_OF_ID("id"), NAME_OF_COLUMN_OF_CARD_ID_OF_SENDER("card_id_of_sender"),
    NAME_OF_COLUMN_OF_CARD_ID_OF_RECEIVER("card_id_of_receiver"), NAME_OF_COLUMN_OF_MONEY("money"),
    NAME_OF_COLUMN_OF_DATE("date");

    private final String value;

    private PaymentTableProperty(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
