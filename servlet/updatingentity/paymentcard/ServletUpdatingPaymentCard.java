package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.paymentcard;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.UpdatingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.PaymentCardService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.paymentcard.PropertyOfUpdatingPaymentCardServlet.*;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENT_CARDS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_PAYMENT_CARDS;

public class ServletUpdatingPaymentCard extends HttpServlet
{
    private PaymentCardService paymentCardService;

    public ServletUpdatingPaymentCard()
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
            final String descriptionOfIdOfUpdatedPaymentCard = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT_CARD.getValue());
            final long idOfUpdatedPaymentCard = Long.parseLong(descriptionOfIdOfUpdatedPaymentCard);
            final Optional<PaymentCard> optionalOfUpdatedPaymentCard = this.paymentCardService.findEntityById(
                    idOfUpdatedPaymentCard);
            if(optionalOfUpdatedPaymentCard.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + PaymentCard.class.getName()
                        + "', because object with id '" + idOfUpdatedPaymentCard + "' doesn't exist.");
            }
            final PaymentCard updatedPaymentCard = optionalOfUpdatedPaymentCard.get();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_PAYMENT_CARD.getValue(),
                    updatedPaymentCard);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_PAYMENT_CARD.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final FindingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }

    @Override
    public final void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final String descriptionOfIdOfUpdatedPaymentCard = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT_CARD.getValue());
            final long idOfUpdatedPaymentCard = Long.parseLong(descriptionOfIdOfUpdatedPaymentCard);
            final Optional<PaymentCard> optionalOfUpdatedPaymentCard = this.paymentCardService.findEntityById(
                    idOfUpdatedPaymentCard);
            if(optionalOfUpdatedPaymentCard.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + PaymentCard.class.getName()
                        + "', because object with id '" + idOfUpdatedPaymentCard + "' doesn't exist.");
            }
            final PaymentCard updatedPaymentCard = optionalOfUpdatedPaymentCard.get();

            this.paymentCardService.modifyEntity(updatedPaymentCard, httpServletRequest);
            this.paymentCardService.updateEntityInDataBase(updatedPaymentCard);

            final Collection<PaymentCard> listedPaymentCards = this.paymentCardService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENT_CARDS.getValue(),
                    listedPaymentCards);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_PAYMENT_CARDS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final FindingEntityException | EntityModifyingException | UpdatingEntityException
                | OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
