package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.bankaccount;

public enum PropertyOfDeletingBankAccountServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_BANK_ACCOUNT("id_of_deleted_bank_account"),
    URL_TO_LIST_ALL_BANK_ACCOUNTS("/admin/list_bank_accounts");

    private final String value;

    private PropertyOfDeletingBankAccountServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
