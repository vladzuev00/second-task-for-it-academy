package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.paymentcard;

public enum PropertyOfUpdatingPaymentCardServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT_CARD("id_of_updated_payment_card"),
    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_PAYMENT_CARD("/administrator_page/page_with_form_to_update_entity/form_to_update_payment_card.jsp"),
    NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_PAYMENT_CARD("updated_payment_card");

    private final String value;

    private PropertyOfUpdatingPaymentCardServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
