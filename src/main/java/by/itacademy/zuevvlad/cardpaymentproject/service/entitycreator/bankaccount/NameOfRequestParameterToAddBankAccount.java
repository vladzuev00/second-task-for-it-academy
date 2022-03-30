package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.bankaccount;

public enum NameOfRequestParameterToAddBankAccount
{
    NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_ADDED_BANK_ACCOUNT("money_of_added_bank_account"),
    NAME_OF_REQUEST_PARAMETER_OF_BLOCKED_OF_ADDED_BANK_ACCOUNT("blocked_of_added_bank_account"),
    NAME_OF_REQUEST_PARAMETER_OF_NUMBER_OF_ADDED_BANK_ACCOUNT("number_of_added_bank_account");

    private final String value;

    private NameOfRequestParameterToAddBankAccount(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
