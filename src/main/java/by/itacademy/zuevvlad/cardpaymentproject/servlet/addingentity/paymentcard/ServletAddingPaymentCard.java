package by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.paymentcard;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.AddingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.PaymentCardService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.paymentcard.PropertyOfAddingPaymentCardServlet.PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_PAYMENT_CARD;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENT_CARDS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_PAYMENT_CARDS;

public final class ServletAddingPaymentCard extends HttpServlet
{
    private PaymentCardService paymentCardService;

    public ServletAddingPaymentCard()
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
        final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_PAYMENT_CARD.getValue());
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    public final void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final PaymentCard addedPaymentCard = this.paymentCardService.createEntity(httpServletRequest);
            this.paymentCardService.addEntityInDataBase(addedPaymentCard);

            final Collection<PaymentCard> listedPaymentCards = this.paymentCardService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENT_CARDS.getValue(),
                    listedPaymentCards);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_PAYMENT_CARDS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final EntityCreatingException | AddingEntityException | OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
