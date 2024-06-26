package MachineCoding.payment_gateways;

import MachineCoding.banks.Bank;
import MachineCoding.clients.Client;
import MachineCoding.enums.PaymentMode;
import MachineCoding.enums.TransactionStatus;
import MachineCoding.exceptions.BadRequestException;
import MachineCoding.interfaces.IBankDistributionStrategy;
import MachineCoding.interfaces.IClientRepository;
import MachineCoding.interfaces.IPaymentGateway;
import MachineCoding.interfaces.IBankDistributionRepository;
import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Data
public class PaymentGateway implements IPaymentGateway {

    private final IClientRepository clientRepository;
    private final IBankDistributionRepository bankDistributionRepository;


    private final IBankDistributionStrategy bankDistributionStrategy;
    private final String name;

    // Dependency inversion principle
    public PaymentGateway(@NotNull String name, @NotNull IBankDistributionStrategy bankDistributionStrategy,
                          IClientRepository clientRepository, IBankDistributionRepository bankDistributionRepository){
        this.bankDistributionStrategy = bankDistributionStrategy;
        this.clientRepository = clientRepository;
        this.bankDistributionRepository = bankDistributionRepository;
        this.name = name;
    }

    @Override
    public Client addClient(Client client) {
        return clientRepository.addClient(client);
    }

    @Override
    public Client removeClient(String clientName) {
        if (!hasClient(clientName)) {
            throw new BadRequestException(100,"Client doesn't exist");
        }
        return clientRepository.removeClient(clientName);
    }

    @Override
    public boolean hasClient(String clientName) {
        return clientRepository.hasClient(clientName);
    }

    @Override
    public Set<PaymentMode> listSupportedPaymodes(String clientName) {
        if (hasClient(clientName)) {
            return clientRepository.getClient(clientName).getSupportedPaymentModes();
        }
        return Collections.emptySet();
    }

    @Override
    public void addSupportForPaymode(String clientName, PaymentMode paymentMode) throws Exception {
        if (!hasClient(clientName)) {
            throw new BadRequestException(100,"Client doesn't exist");
        }
        clientRepository.getClient(clientName).getSupportedPaymentModes().add(paymentMode);

    }

    @Override
    public void removePaymode(String clientName, PaymentMode paymentMode) {
        if (!hasClient(clientName)) {
            throw new BadRequestException(100,"Client doesn't exist");
        }

        if (!clientRepository.getClient(clientName).getSupportedPaymentModes().contains(paymentMode)) {
            throw new BadRequestException(101,paymentMode+" Payment mode is not supported for client "+clientName);
        }

        if (hasClient(clientName)) {
            clientRepository.getClient(clientName).getSupportedPaymentModes().remove(paymentMode);
        }
    }

    @Override
    public void addBankDistribution(PaymentMode paymentMode, Bank bank, int percentage) {
        bankDistributionRepository.addBankDistribution(paymentMode,bank,percentage);
    }

    @Override
    public Map<Bank, Integer> showDistribution(PaymentMode paymentMode) {
        return bankDistributionRepository.showDistribution(paymentMode);
    }

    @Override
    public TransactionStatus makePayment(String clientName, PaymentMode paymentMode, Map<String, Object> details) {

        if (!hasClient(clientName)) {
            throw new BadRequestException(100,"Client doesn't exist");
        }

        if (!clientRepository.getClient(clientName).getSupportedPaymentModes().contains(paymentMode)) {
            throw new BadRequestException(101,paymentMode+" Payment mode is not supported for client "+clientName);
        }

        Map<Bank, Integer> distribution = bankDistributionRepository.showDistribution(paymentMode);
        if (distribution == null || distribution.isEmpty()) {
            return TransactionStatus.FAILURE;
        }

        // Strategy pattern
        Bank selectedBank = bankDistributionStrategy.selectBank(distribution,null);
        return selectedBank.processPayment(paymentMode, details);
    }

    @Override
    public Map<String, Client> getClients() {
        return clientRepository.getClients();
    }
}
