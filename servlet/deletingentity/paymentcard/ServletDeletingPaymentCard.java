package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.paymentcard;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.DeletingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.service.PaymentCardService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.paymentcard.PropertyOfDeletingPaymentCardServlet.*;

public final class ServletDeletingPaymentCard extends HttpServlet
{
    private PaymentCardService paymentCardService;

    public ServletDeletingPaymentCard()
    {
        super();
        this.paymentCardService = null;
    }

    @Override
    public final void init()
    {
        this.paymentCardService = PaymentCardService.createPaymentCardService();
    }

    @Override
    public final void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final String descriptionOfIdOfDeletedPaymentCard = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_PAYMENT_CARD.getValue());
            final long idOfDeletedPaymentCard = Long.parseLong(descriptionOfIdOfDeletedPaymentCard);
            this.paymentCardService.deleteEntityById(idOfDeletedPaymentCard);
            httpServletResponse.sendRedirect(URL_TO_LIST_ALL_PAYMENT_CARDS.getValue());
        }
        catch(final DeletingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }
}
