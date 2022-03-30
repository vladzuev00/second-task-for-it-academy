package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.paymentcard;

public enum PropertyOfDeletingPaymentCardServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_PAYMENT_CARD("id_of_deleted_payment_card"),
    URL_TO_LIST_ALL_PAYMENT_CARDS("/admin/list_payment_cards");

    private final String value;

    private PropertyOfDeletingPaymentCardServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
