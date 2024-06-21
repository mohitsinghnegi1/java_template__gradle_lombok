package MachineCoding.payment_gateways;

import MachineCoding.banks.Bank;
import MachineCoding.clients.Client;
import MachineCoding.enums.PaymentMode;
import MachineCoding.enums.TransactionStatus;
import MachineCoding.exceptions.BadRequestException;
import MachineCoding.interfaces.IBankDistributionStrategy;
import MachineCoding.interfaces.IPaymentGateway;
import com.sun.istack.internal.NotNull;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class PaymentGateway implements IPaymentGateway {

    // Make these two Map as a repository class to segregate the db layer from business layer and Adher to single Responsibility principle
    private Map<String, Client> clients = new HashMap<>();
    private Map<PaymentMode, Map<Bank, Integer>> bankDistribution = new HashMap<>();


    private final IBankDistributionStrategy bankDistributionStrategy;
    private final String name;

    // Dependency inversion principle
    public PaymentGateway(@NotNull String name, @NotNull IBankDistributionStrategy bankDistributionStrategy){
        this.bankDistributionStrategy = bankDistributionStrategy;
        this.name = name;
    }

    @Override
    public Client addClient(Client client) {
        return clients.put(client.getName(), client);
    }

    @Override
    public Client removeClient(String clientName) {
        if (!hasClient(clientName)) {
            throw new BadRequestException(100,"Client doesn't exist");
        }
        return clients.remove(clientName);
    }

    @Override
    public boolean hasClient(String clientName) {
        return clients.containsKey(clientName);
    }

    @Override
    public Set<PaymentMode> listSupportedPaymodes(String clientName) {
        if (hasClient(clientName)) {
            return clients.get(clientName).getSupportedPaymentModes();
        }
        return Collections.emptySet();
    }

    @Override
    public void addSupportForPaymode(String clientName, PaymentMode paymentMode) throws Exception {
        if (!hasClient(clientName)) {
            throw new BadRequestException(100,"Client doesn't exist");
        }
        clients.get(clientName).getSupportedPaymentModes().add(paymentMode);

    }

    @Override
    public void removePaymode(String clientName, PaymentMode paymentMode) {
        if (!hasClient(clientName)) {
            throw new BadRequestException(100,"Client doesn't exist");
        }

        if (!clients.get(clientName).getSupportedPaymentModes().contains(paymentMode)) {
            throw new BadRequestException(101,paymentMode+" Payment mode is not supported for client "+clientName);
        }

        if (clients.containsKey(clientName)) {
            clients.get(clientName).getSupportedPaymentModes().remove(paymentMode);
        }
    }

    @Override
    public void addBankDistribution(PaymentMode paymentMode, Bank bank, int percentage) {
        bankDistribution.computeIfAbsent(paymentMode, k -> new HashMap<>()).put(bank, percentage);
    }

    @Override
    public Map<Bank, Integer> showDistribution(PaymentMode paymentMode) {
        return bankDistribution.getOrDefault(paymentMode, Collections.emptyMap());
    }

    @Override
    public TransactionStatus makePayment(String clientName, PaymentMode paymentMode, Map<String, String> details) {
        if (!hasClient(clientName) || !clients.get(clientName).getSupportedPaymentModes().contains(paymentMode)) {
            return TransactionStatus.FAILURE;
        }

        Map<Bank, Integer> distribution = bankDistribution.get(paymentMode);
        if (distribution == null || distribution.isEmpty()) {
            return TransactionStatus.FAILURE;
        }

        // Strategy pattern
        Bank selectedBank = bankDistributionStrategy.selectBank(distribution);
        return selectedBank.processPayment(paymentMode);
    }
}
