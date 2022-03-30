package by.itacademy.zuevvlad.cardpaymentproject.service;

import by.itacademy.zuevvlad.cardpaymentproject.dao.ClientDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.PaymentCardDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.client.ClientCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.client.ClientModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ClientService extends EntityService<Client>
{
    private final ClientDAO clientDAO;
    private final PaymentCardDAO paymentCardDAO;

    public static ClientService createClientService()
    {
        if(ClientService.clientService == null)
        {
            synchronized(ClientService.class)
            {
                if(ClientService.clientService == null)
                {
                    final EntityCreator<Client> clientCreator = ClientCreator.createClientCreator();
                    final EntityModifier<Client> clientModifier = ClientModifier.createClientModifier();
                    ClientService.clientService = new ClientService(clientCreator, clientModifier);
                }
            }
        }
        return ClientService.clientService;
    }

    private static ClientService clientService = null;

    private ClientService(final EntityCreator<Client> clientCreator, final EntityModifier<Client> clientModified)
    {
        super(clientCreator, clientModified);
        this.clientDAO = ClientDAO.createClientDAO();
        this.paymentCardDAO = PaymentCardDAO.createPaymentCardDAO();
    }

    @Override
    public final Collection<Client> findAllEntities()
            throws OffloadingEntitiesException
    {
        return this.clientDAO.offloadEntities();
    }

    @Override
    public final void addEntityInDataBase(final Client addedClient)
            throws AddingEntityException
    {
        this.clientDAO.addEntity(addedClient);
    }

    @Override
    public final Client createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        final EntityCreator<Client> clientCreator = super.getEntityCreator();
        return clientCreator.createEntity(httpServletRequest);
    }

    @Override
    public final Optional<Client> findEntityById(final long idOfFoundClient)
            throws FindingEntityException
    {
        return this.clientDAO.findEntityById(idOfFoundClient);
    }

    @Override
    public final void modifyEntity(final Client modifiedClient, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {
        final EntityModifier<Client> clientModifier = super.getEntityModifier();
        clientModifier.modify(modifiedClient, httpServletRequest);
    }

    @Override
    public final void updateEntityInDataBase(final Client updatedClient)
            throws UpdatingEntityException
    {
        this.clientDAO.updateEntity(updatedClient);
    }

    @Override
    public void deleteEntityById(final long idOfDeletedClient) throws DeletingEntityException
    {
        this.clientDAO.deleteEntityById(idOfDeletedClient);
    }

    public final Optional<Client> findClientByGivenEmail(final String emailOfFoundClient)
            throws FindingEntityException
    {
        return this.clientDAO.findClientByGivenEmail(emailOfFoundClient);
    }

    public final Map<Client, List<String>> findClientsAndAssociatedNumbersOfPaymentCards()
            throws OffloadingEntitiesException
    {
        final Collection<PaymentCard> paymentCards = this.paymentCardDAO.offloadEntities();
        //группируем платежные карты по клиентам, но сгруппированные платежные карты преобразуем в листы номеров этих карт
        return paymentCards.stream().collect(Collectors.groupingBy(PaymentCard::getClient,
                Collectors.mapping(PaymentCard::getCardNumber, Collectors.toList())));
    }
}
