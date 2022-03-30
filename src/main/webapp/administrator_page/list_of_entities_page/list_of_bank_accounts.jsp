<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl-core" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.bankaccount.PropertyOfUpdatingBankAccountServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_BANK_ACCOUNT" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.bankaccount.PropertyOfDeletingBankAccountServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_BANK_ACCOUNT" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.DELIMITER_OF_ACTIONS" %>

<html>

<body>

    <table border="1">
        <tr>
            <th>Money</th>
            <th>Blocked</th>
            <th>Number</th>
            <th>Action</th>
        </tr>

        <jsp:useBean id="listed_bank_accounts" scope="request" type="java.util.Collection" />
        <jstl-core:forEach var="listed_bank_account" items="${listed_bank_accounts}">

            <jstl-core:url var="link_to_update_bank_account" value="/update_bank_account">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_BANK_ACCOUNT.getValue() %>"
                                 value="${listed_bank_account.id}" />
            </jstl-core:url>

            <jstl-core:url var="link_to_delete_bank_account" value="/delete_bank_account">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_BANK_ACCOUNT.getValue() %>"
                                 value="${listed_bank_account.id}" />
            </jstl-core:url>

            <tr>
                <td>${listed_bank_account.money}</td>
                <td>${listed_bank_account.blocked}</td>
                <td>${listed_bank_account.number}</td>
                <td>
                    <a href="${link_to_update_bank_account}">Update</a>

                    <%= DELIMITER_OF_ACTIONS.getValue() %>

                    <a href="${link_to_delete_bank_account}"
                       onclick="return (confirm('Are you sure you want to delete this bank '
                            + 'account?(You will also delete associated clients, payment cards and '
                            + 'payments)'))">
                        Delete
                    </a>
                </td>
            </tr>
        </jstl-core:forEach>
    </table>

    <input type="button" value="Add new bank account" onclick="window.location.href='/add_bank_account'" />

</body>

</html>