package by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.client;

public enum PropertyOfAddingClientServlet
{
    PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_CLIENT("/administrator_page/page_with_form_to_add_entity/form_to_add_new_client.jsp");

    private final String value;

    private PropertyOfAddingClientServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
