package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.client;

public enum PropertyOfUpdatingClientServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_CLIENT("id_of_updated_client"),
    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_CLIENT("/administrator_page/page_with_form_to_update_entity/form_to_update_client.jsp"),
    NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_CLIENT("updated_client");

    private final String value;

    private PropertyOfUpdatingClientServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
