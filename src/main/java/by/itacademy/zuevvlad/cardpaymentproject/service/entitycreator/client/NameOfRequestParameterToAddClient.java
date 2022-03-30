package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.client;

public enum NameOfRequestParameterToAddClient
{
    NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_ADDED_CLIENT("email_of_added_client"),
    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_CLIENT("password_of_added_client"),
    NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_ADDED_CLIENT("name_of_added_client"),
    NAME_OF_REQUEST_PARAMETER_OF_SURNAME_OF_ADDED_CLIENT("surname_of_added_client"),
    NAME_OF_REQUEST_PARAMETER_OF_PATRONYMIC_OF_ADDED_CLIENT("patronymic_of_added_client"),
    NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_ADDED_CLIENT("phone_number_of_added_client"),
    NAME_OF_REQUEST_PARAMETER_OF_BANK_ACCOUNT_NUMBER_OF_ADDED_CLIENT("bank_account_number_of_added_client");

    private final String value;

    private NameOfRequestParameterToAddClient(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
