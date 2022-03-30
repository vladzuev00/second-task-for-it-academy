package by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.PaymentCardService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENT_CARDS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_PAYMENT_CARDS;

public final class ServletListingPaymentCards extends HttpServlet
{
    private PaymentCardService paymentCardService;

    public ServletListingPaymentCards()
    {
        super();
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
            final Collection<PaymentCard> listedPaymentCards = this.paymentCardService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENT_CARDS.getValue(),
                    listedPaymentCards);
            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_PAYMENT_CARDS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
