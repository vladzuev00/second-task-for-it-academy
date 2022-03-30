package by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.administrator;

public enum PropertyOfAddingAdministratorServlet
{
    PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_ADMINISTRATOR("/administrator_page/page_with_form_to_add_entity/form_to_add_new_administrator.jsp");

    private final String value;

    private PropertyOfAddingAdministratorServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
