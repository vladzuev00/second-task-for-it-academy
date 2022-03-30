package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.administrator;

public enum PropertyOfDeletingAdministratorServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_ADMINISTRATOR("id_of_deleted_administrator"),
    URL_TO_LIST_ALL_ADMINISTRATORS("/admin/list_administrators");

    private final String value;

    private PropertyOfDeletingAdministratorServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
