package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.administrator;

public enum NameOfRequestParameterToUpdateAdministrator
{
    NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_UPDATED_ADMINISTRATOR("email_of_updated_administrator"),
    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_ADMINISTRATOR("password_of_updated_administrator"),
    NAME_OF_REQUEST_PARAMETER_OF_LEVEL_OF_UPDATED_ADMINISTRATOR("level_of_updated_administrator");

    private final String value;

    private NameOfRequestParameterToUpdateAdministrator(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
