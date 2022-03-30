package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.payment;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.UpdatingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.service.PaymentService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.payment.PropertyOfUpdatingPaymentServlet.*;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_PAYMENTS_AND_ASSOCIATED_DESCRIPTION_OF_DATES;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_PAYMENTS;

public final class ServletUpdatingPayment extends HttpServlet
{
    private PaymentService paymentService;

    public ServletUpdatingPayment()
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
            final String descriptionOfIdOfUpdatedPayment = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT.getValue());
            final long idOfUpdatedPayment = Long.parseLong(descriptionOfIdOfUpdatedPayment);
            final Optional<Payment> optionalOfUpdatedPayment = this.paymentService.findEntityById(idOfUpdatedPayment);
            if(optionalOfUpdatedPayment.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + Payment.class.getName()
                    + "', because object with id '" + idOfUpdatedPayment + "' doesn't exist.");
            }
            final Payment updatedPayment = optionalOfUpdatedPayment.get();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_PAYMENT.getValue(), updatedPayment);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_PAYMENT.getValue());
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
            final String descriptionOfIdOfUpdatedPayment = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_PAYMENT.getValue());
            final long idOfUpdatedPayment = Long.parseLong(descriptionOfIdOfUpdatedPayment);
            final Optional<Payment> optionalOfUpdatedPayment = this.paymentService.findEntityById(idOfUpdatedPayment);
            if(optionalOfUpdatedPayment.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + Payment.class.getName()
                        + "', because object with id '" + idOfUpdatedPayment + "' doesn't exist.");
            }
            final Payment updatedPayment = optionalOfUpdatedPayment.get();

            this.paymentService.modifyEntity(updatedPayment, httpServletRequest);
            this.paymentService.updateEntityInDataBase(updatedPayment);

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
        catch(final FindingEntityException | EntityModifyingException | UpdatingEntityException
                | OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
