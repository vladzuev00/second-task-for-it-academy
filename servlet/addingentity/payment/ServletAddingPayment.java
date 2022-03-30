package by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.payment;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.AddingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.service.PaymentService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.payment.PropertyOfAddingPaymentServlet.PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_CLIENT;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_PAYMENTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_PAYMENTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_PAYMENTS_AND_ASSOCIATED_DESCRIPTION_OF_DATES;

public final class ServletAddingPayment extends HttpServlet
{
    private PaymentService paymentService;

    public ServletAddingPayment()
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
        final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_CLIENT.getValue());
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    public final void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException        //TODO: во всех сервлетах такого типа в этом методе найти способ вызвать метод doGet сервлета выводящего всех сущностей(чтобы избежать дублирование кода)
    {
        try
        {
            final Payment addedPayment = this.paymentService.createEntity(httpServletRequest);
            this.paymentService.addEntityInDataBase(addedPayment);

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
        catch(final EntityCreatingException | AddingEntityException | OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
