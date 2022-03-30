package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.bankaccount;

public enum NameOfRequestParameterToUpdateBankAccount
{
    NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_UPDATED_BANK_ACCOUNT("money_of_updated_bank_account"),
    NAME_OF_REQUEST_PARAMETER_OF_BLOCKED_OF_UPDATED_BANK_ACCOUNT("blocked_of_updated_bank_account"),
    NAME_OF_REQUEST_PARAMETER_OF_NUMBER_OF_UPDATED_BANK_ACCOUNT("number_of_updated_bank_account");

    private final String value;

    private NameOfRequestParameterToUpdateBankAccount(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
