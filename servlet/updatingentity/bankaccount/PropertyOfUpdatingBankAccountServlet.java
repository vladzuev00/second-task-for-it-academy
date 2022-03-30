package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.bankaccount;

public enum PropertyOfUpdatingBankAccountServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_BANK_ACCOUNT("id_of_updated_bank_account"),
    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_BANK_ACCOUNT("/administrator_page/page_with_form_to_update_entity/form_to_update_bank_account.jsp"),
    NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_BANK_ACCOUNT("updated_bank_account");

    private final String value;

    PropertyOfUpdatingBankAccountServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
