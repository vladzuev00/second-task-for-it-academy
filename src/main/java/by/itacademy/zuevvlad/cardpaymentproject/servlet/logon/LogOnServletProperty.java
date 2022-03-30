package by.itacademy.zuevvlad.cardpaymentproject.servlet.logon;

public enum LogOnServletProperty
{
    NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_EMAIL("user_email"),
    NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_PASSWORD("user_password"),
    NAME_OF_ATTRIBUTE_OF_REQUEST_OF_ERROR_MESSAGE("error_message");

    private final String value;

    private LogOnServletProperty(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}

