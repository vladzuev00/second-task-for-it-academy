<%@ page import="by.itacademy.zuevvlad.cardpaymentproject.entity.Payment" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.payment.PropertyOfUpdatingPaymentServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_PAYMENT" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.payment.PropertyOfUpdatingPaymentServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.payment.NameOfRequestParameterToUpdatePayment.*" %>
<%@ page import="by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer.DateTransformer" %>

<!DOCTYPE html>
<html>

<body>

    <%
        final Payment updatedPayment = (Payment)request.getAttribute(
                NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_PAYMENT.getValue());
        final DateTransformer dateTransformer = new DateTransformer();
    %>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/update_payment" method="post">

        <input type="hidden" name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT.getValue() %>"
               value="<%= updatedPayment.getId() %>" />

        <div>
            <label for="input_field_of_card_number_of_sender_of_updated_payment">Card number of new sender: </label>
            <input id="input_field_of_card_number_of_sender_of_updated_payment" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_SENDER_OF_UPDATED_PAYMENT.getValue() %>"
                   required="required" value="<%= updatedPayment.getCardOfSender().getCardNumber() %>" />
        </div>

        <div>
            <label for="input_field_of_card_number_of_receiver_of_updated_payment">Card number of new receiver: </label>
            <input id="input_field_of_card_number_of_receiver_of_updated_payment" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_RECEIVER_OF_UPDATED_PAYMENT.getValue() %>"
                   required="required" value="<%= updatedPayment.getCardOfReceiver().getCardNumber() %>" />
        </div>

        <div>
            <label for="input_field_of_money_of_updated_payment">New money: </label>
            <input id="input_field_of_money_of_updated_payment" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_UPDATED_PAYMENT.getValue() %>"
                   required="required" value="<%= updatedPayment.getMoney() %>" />
        </div>

        <div>
            <label for="input_field_of_date_of_updated_payment">New date: </label>
            <input id="input_field_of_date_of_updated_payment" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_DATE_OF_UPDATED_PAYMENT.getValue() %>"
                   required="required" value="<%= dateTransformer.findDescription(updatedPayment.getDate()) %>" />
        </div>

        <input type="submit" value="update" />

        <input type="reset" value="reset" />

    </form>

</body>

</html>