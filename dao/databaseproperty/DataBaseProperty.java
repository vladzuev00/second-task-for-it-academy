package by.itacademy.zuevvlad.cardpaymentproject.dao.databaseproperty;

public enum DataBaseProperty
{
    URL("jdbc:postgresql://localhost:5432/card_payment_database"), NAME_OF_USER("postgres"), PASSWORD("kakawka228"),
    NAME_OF_CLASS_OF_DRIVER("org.postgresql.Driver");

    private final String value;

    private DataBaseProperty(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
