package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.administrator;

public enum NameOfRequestParameterToAddAdministrator
{
    NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_ADDED_ADMINISTRATOR("email_of_added_administrator"),
    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_ADMINISTRATOR("password_of_added_administrator"),
    NAME_OF_REQUEST_PARAMETER_OF_LEVEL_OF_ADDED_ADMINISTRATOR("level_of_added_administrator");

    private final String value;

    private NameOfRequestParameterToAddAdministrator(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
