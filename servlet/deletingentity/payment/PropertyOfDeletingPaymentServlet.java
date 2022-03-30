package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.payment;

public enum PropertyOfDeletingPaymentServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_PAYMENT("id_of_deleted_payment"),
    URL_TO_LIST_ALL_PAYMENTS("/admin/list_payments");

    private final String value;

    private PropertyOfDeletingPaymentServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
