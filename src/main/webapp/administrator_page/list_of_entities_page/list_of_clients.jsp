<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl-core" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.client.PropertyOfUpdatingClientServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_CLIENT" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.client.PropertyOfDeletingClientServlet.NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_CLIENT" %>
<%@ page import="static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.DELIMITER_OF_ACTIONS" %>

<!DOCTYPE html>
<html>

<body>

    <table border="1">
        <tr>
            <th>Email</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Patronymic</th>
            <th>Phone number</th>
            <th>Number of bank account</th>
            <th>Numbers of payment cards</th>
            <th>Action</th>
        </tr>

        <jsp:useBean id="listed_clients" scope="request" type="java.util.Collection" />
        <jsp:useBean id="clients_and_associated_numbers_of_payment_cards" scope="request" type="java.util.Map" />
        <jstl-core:forEach var="listed_client" items="${listed_clients}">

            <jstl-core:url var="link_to_update_client" value="/update_client">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_CLIENT.getValue() %>"
                                 value="${listed_client.id}" />
            </jstl-core:url>

            <jstl-core:url var="link_to_delete_client" value="/delete_client">
                <jstl-core:param name="<%= NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_CLIENT.getValue() %>"
                                 value="${listed_client.id}" />
            </jstl-core:url>

            <tr>
                <td>${listed_client.email}</td>
                <td>${listed_client.name}</td>
                <td>${listed_client.surname}</td>
                <td>${listed_client.patronymic}</td>
                <td>${listed_client.phoneNumber}</td>
                <td>${listed_client.bankAccount.number}</td>
                <td>
                    <jstl-core:choose>
                        <jstl-core:when test="${clients_and_associated_numbers_of_payment_cards.get(listed_client) != null}">

                            <jstl-core:forEach var="number_of_payment_card_of_current_listed_client"
                                               items="${clients_and_associated_numbers_of_payment_cards.get(listed_client)}">
                                ${number_of_payment_card_of_current_listed_client}
                                <br/>
                            </jstl-core:forEach>

                        </jstl-core:when>
                        <jstl-core:otherwise>
                            -
                        </jstl-core:otherwise>
                    </jstl-core:choose>
                </td>
                <td>
                    <a href="${link_to_update_client}">Update</a>

                    <%= DELIMITER_OF_ACTIONS.getValue() %>

                    <a href="${link_to_delete_client}"
                       onclick="return (confirm('Are you sure you want to delete this client?(You will also delete ' +
                                            'associated payment cards and payments)'))">
                        Delete
                    </a>
                </td>
            </tr>
        </jstl-core:forEach>

    </table>

    <input type="button" value="Add new client" onclick="window.location.href='/add_client'" />

</body>

</html>