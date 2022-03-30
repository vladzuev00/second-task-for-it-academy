package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.user;

public enum NameOfRequestParameterToAddUser
{
    NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_ADDED_USER("email_of_added_user"),
    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_USER("password_of_added_user");

    private final String value;

    private NameOfRequestParameterToAddUser(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
