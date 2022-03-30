package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.client;

public enum PropertyOfDeletingClientServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_CLIENT("id_of_deleted_client"),
    URL_TO_LIST_ALL_CLIENTS("/admin/list_clients");

    private final String value;

    private PropertyOfDeletingClientServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
