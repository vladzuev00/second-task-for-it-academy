package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.client;

public enum NameOfRequestParameterToUpdateClient
{
    NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_UPDATED_CLIENT("email_of_updated_client"),
    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_CLIENT("password_of_updated_client"),
    NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_UPDATED_CLIENT("name_of_updated_client"),
    NAME_OF_REQUEST_PARAMETER_OF_SURNAME_OF_UPDATED_CLIENT("surname_of_updated_client"),
    NAME_OF_REQUEST_PARAMETER_OF_PATRONYMIC_OF_UPDATED_CLIENT("patronymic_of_updated_client"),
    NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_UPDATED_CLIENT("phone_number_of_updated_client"),
    NAME_OF_REQUEST_PARAMETER_OF_BANK_ACCOUNT_NUMBER_OF_UPDATED_CLIENT("bank_account_number_of_updated_client");

    private final String value;

    private NameOfRequestParameterToUpdateClient(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
