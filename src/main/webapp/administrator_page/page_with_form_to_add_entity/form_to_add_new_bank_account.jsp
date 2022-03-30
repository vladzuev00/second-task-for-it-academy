<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.bankaccount.NameOfRequestParameterToAddBankAccount.*" %>
<!DOCTYPE html>
<html>

<body>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/add_bank_account" method="post">

        <div>
            <label for="input_field_of_money_of_added_bank_account">Money: </label>
            <input id="input_field_of_money_of_added_bank_account" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_ADDED_BANK_ACCOUNT.getValue() %>" required="required" />
        </div>

        <div>
            <label>Blocked:
                <input type="radio" name="<%= NAME_OF_REQUEST_PARAMETER_OF_BLOCKED_OF_ADDED_BANK_ACCOUNT.getValue() %>" value="true" />
                <input type="radio" name="<%= NAME_OF_REQUEST_PARAMETER_OF_BLOCKED_OF_ADDED_BANK_ACCOUNT.getValue() %>" value="false" hidden="hidden" checked="checked" />
            </label>
        </div>

        <div>
            <label for="input_field_of_number_of_added_bank_account">Number: </label>
            <input id="input_field_of_number_of_added_bank_account" type="text" name="<%= NAME_OF_REQUEST_PARAMETER_OF_NUMBER_OF_ADDED_BANK_ACCOUNT.getValue() %>" required="required" />
        </div>

        <input type="submit" value="add" />

        <input type="reset" value="reset" />

    </form>

</body>

</html>