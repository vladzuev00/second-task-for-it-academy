package by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.service.PaymentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_PAYMENTS_AND_ASSOCIATED_DESCRIPTION_OF_DATES;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_PAYMENTS;

public final class ServletListingPayments extends HttpServlet
{
    private PaymentService paymentService;

    public ServletListingPayments()
    {
        super();
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
            final Collection<Payment> listedPayments = this.paymentService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENTS.getValue(), listedPayments);

            final Map<Payment, String> paymentsAndAssociatedDescriptionOfDates
                    = this.paymentService.findPaymentsAndAssociatedDescriptionOfDates();
            httpServletRequest.setAttribute(
                    NAME_OF_REQUEST_ATTRIBUTE_OF_PAYMENTS_AND_ASSOCIATED_DESCRIPTION_OF_DATES.getValue(),
                    paymentsAndAssociatedDescriptionOfDates);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_PAYMENTS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
