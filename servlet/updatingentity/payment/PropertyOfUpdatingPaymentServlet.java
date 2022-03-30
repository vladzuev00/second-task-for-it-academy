package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.payment;

public enum PropertyOfUpdatingPaymentServlet
{
    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT("id_of_updated_payment"),
    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_PAYMENT("/administrator_page/page_with_form_to_update_entity/form_to_update_payment.jsp"),
    NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_PAYMENT("updated_payment");

    private final String value;

    private PropertyOfUpdatingPaymentServlet(final String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return this.value;
    }
}
