<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl-core" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.paymentcard.PropertyOfUpdatingPaymentCardServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT_CARD" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.paymentcard.PropertyOfDeletingPaymentCardServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_PAYMENT_CARD" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.DELIMITER_OF_ACTIONS" %>

<!DOCTYPE html>
<html>

<body>

    <table border="1">
        <tr>
            <th>Card number</th>
            <th>Expiration date</th>
            <th>Payment system</th>
            <th>Name of bank</th>
            <th>Client's email</th>
            <th>Client's phone number</th>
            <th>Action</th>
        </tr>

        <jsp:useBean id="listed_payment_cards" scope="request" type="java.util.Collection" />
        <jstl-core:forEach var="listed_payment_card" items="${listed_payment_cards}">

            <jstl-core:url var="link_to_update_payment_card" value="/update_payment_card">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT_CARD.getValue() %>"
                                 value="${listed_payment_card.id}" />
            </jstl-core:url>

            <jstl-core:url var="link_to_delete_payment_card" value="/delete_payment_card">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_PAYMENT_CARD.getValue() %>"
                                 value="${listed_payment_card.id}" />
            </jstl-core:url>

            <tr>
                <td>${listed_payment_card.cardNumber}</td>
                <td>${listed_payment_card.expirationDate.month.value}/${listed_payment_card.expirationDate.year.value}</td>
                <td>${listed_payment_card.paymentSystem}</td>
                <td>${listed_payment_card.nameOfBank}</td>
                <td>${listed_payment_card.client.email}</td>
                <td>${listed_payment_card.client.phoneNumber}</td>
                <td>

                    <a href="${link_to_update_payment_card}">Update</a>

                    <%= DELIMITER_OF_ACTIONS.getValue() %>

                    <a href="${link_to_delete_payment_card}"
                       onclick="return (confirm('Are you sure you want to delete this payment card?(You will also delete '
                            + 'associated payments)'))">
                        Delete
                    </a>
                </td>
            </tr>
        </jstl-core:forEach>
    </table>

    <input type="button" value="Add new payment card" onclick="window.location.href='/add_payment_card'" />

</body>

</html>