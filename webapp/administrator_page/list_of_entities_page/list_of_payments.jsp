<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl-core" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.payment.PropertyOfUpdatingPaymentServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT " %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.payment.PropertyOfDeletingPaymentServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_PAYMENT" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.DELIMITER_OF_ACTIONS" %>


<!DOCTYPE html>
<html>

<body>

    <table border="1">
        <tr>
            <th>Money</th>
            <th>Date</th>
            <th>Card number of sender</th>
            <th>Card number of receiver</th>
            <th>Action</th>
        </tr>

        <jsp:useBean id="listed_payments" scope="request" type="java.util.Collection" />
        <jsp:useBean id="payments_and_associated_description_of_dates" scope="request" type="java.util.Map" />
        <jstl-core:forEach var="listed_payment" items="${listed_payments}">

            <jstl-core:url var="link_to_update_payment" value="/update_payment">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT.getValue() %>"
                                 value="${listed_payment.id}" />
            </jstl-core:url>

            <jstl-core:url var="link_to_delete_payment" value="/delete_payment">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_PAYMENT.getValue() %>"
                                 value="${listed_payment.id}" />
            </jstl-core:url>

            <tr>
                <td>${listed_payment.money}</td>
                <td>${payments_and_associated_description_of_dates.get(listed_payment)}</td>
                <td>${listed_payment.cardOfSender.cardNumber}</td>
                <td>${listed_payment.cardOfReceiver.cardNumber}</td>
                <td>
                    <a href="${link_to_update_payment}">Update</a>

                    <%= DELIMITER_OF_ACTIONS.getValue() %>

                    <a href="${link_to_delete_payment}"
                       onclick="return (confirm('Are you sure you want to delete this payment?'))">
                        Delete
                    </a>
                </td>
            </tr>
        </jstl-core:forEach>
    </table>

    <input type="button" value="Add new payment" onclick="window.location.href='/add_payment'" />

</body>

</html>