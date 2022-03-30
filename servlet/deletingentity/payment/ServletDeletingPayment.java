package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.payment;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.DeletingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.service.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.payment.PropertyOfDeletingPaymentServlet.*;

public final class ServletDeletingPayment extends HttpServlet
{
    private PaymentService paymentService;

    public ServletDeletingPayment()
    {
        super();
        this.paymentService = null;
    }

    @Override
    public final void init()
    {
        this.paymentService = PaymentService.createPaymentService();
    }

    @Override
    public final void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final String descriptionOfIdOfDeletedPayment = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_PAYMENT.getValue());
            final long idOfDeletedPayment = Long.parseLong(descriptionOfIdOfDeletedPayment);
            this.paymentService.deleteEntityById(idOfDeletedPayment);
            httpServletResponse.sendRedirect(URL_TO_LIST_ALL_PAYMENTS.getValue());
        }
        catch(final DeletingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }
}
