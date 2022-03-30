<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl-core" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.bankaccount.PropertyOfUpdatingBankAccountServlet.*" %>
<%@ page import="by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.bankaccount.NameOfRequestParameterToUpdateBankAccount.*" %>

<!DOCTYPE html>
<html>

<body>

    <%
        final BankAccount updatedBankAccount = (BankAccount)request.getAttribute(
                NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_BANK_ACCOUNT.getValue());
    %>

    <form accept-charset="UTF-8" action="${pageContext.request.contextPath}/update_bank_account" method="post">

        <input type="hidden" name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_BANK_ACCOUNT.getValue() %>"
               value="<%= updatedBankAccount.getId() %>" />

        <div>
            <label for="input_field_of_money_of_updated_bank_account">New money: </label>
            <input id="input_field_of_money_of_updated_bank_account" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_UPDATED_BANK_ACCOUNT.getValue() %>"
                   required="required" value="<%= updatedBankAccount.getMoney() %>" />
        </div>

        <div>
            <label>New blocked:
                <input type="radio" name="<%= NAME_OF_REQUEST_PARAMETER_OF_BLOCKED_OF_UPDATED_BANK_ACCOUNT.getValue() %>"
                       value="true" />
                <input type="radio" name="<%= NAME_OF_REQUEST_PARAMETER_OF_BLOCKED_OF_UPDATED_BANK_ACCOUNT.getValue() %>"
                       value="false" hidden="hidden" checked="checked" />
            </label>
        </div>

        <div>
            <label for="input_field_of_number_of_updated_bank_account">New number: </label>
            <input id="input_field_of_number_of_updated_bank_account" type="text"
                   name="<%= NAME_OF_REQUEST_PARAMETER_OF_NUMBER_OF_UPDATED_BANK_ACCOUNT.getValue() %>"
                   required="required" value="<%= updatedBankAccount.getNumber() %>" />
        </div>

        <input type="submit" value="update" />

        <input type="reset" value="reset" />
    </form>

</body>

</html>