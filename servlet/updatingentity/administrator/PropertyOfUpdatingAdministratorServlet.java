package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.administrator;

public enum PropertyOfUpdatingAdministratorServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_ADMINISTRATOR("id_of_updated_administrator"),
    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_ADMINISTRATOR("/administrator_page/page_with_form_to_update_entity/form_to_update_administrator.jsp"),
    NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_ADMINISTRATOR("updated_administrator");

    private final String value;

    private PropertyOfUpdatingAdministratorServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
