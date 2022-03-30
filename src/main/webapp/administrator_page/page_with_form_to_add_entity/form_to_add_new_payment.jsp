<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.payment.NameOfRequestParameterToAddPayment.*" %>

<!DOCTYPE html>
<html>

<body>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/add_payment" method="post">

        <div>
            <label for="input_field_of_card_number_of_sender_of_added_payment">Card number of sender: </label>
            <input id="input_field_of_card_number_of_sender_of_added_payment" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_SENDER_OF_ADDED_PAYMENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_card_number_of_receiver_of_added_payment">Card number of receiver: </label>
            <input id="input_field_of_card_number_of_receiver_of_added_payment" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_RECEIVER_OF_ADDED_PAYMENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_money_of_added_payment">Money: </label>
            <input id="input_field_of_money_of_added_payment" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_ADDED_PAYMENT.getValue() %>" required="required" />
        </div>

        <div>
            <label for="input_field_of_date_of_added_payment">Date: </label>
            <input id="input_field_of_date_of_added_payment" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_DATE_OF_ADDED_PAYMENT.getValue() %>" required="required" />
        </div>

        <input type="submit" value="add" />
        <input type="reset" value="reset" />

    </form>

</body>

</html>