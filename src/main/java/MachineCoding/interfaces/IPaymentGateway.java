package MachineCoding.interfaces;

import MachineCoding.banks.Bank;
import MachineCoding.clients.Client;
import MachineCoding.enums.PaymentMode;
import MachineCoding.enums.TransactionStatus;

import java.util.Map;
import java.util.Set;

public interface IPaymentGateway {
    Client addClient(Client client);

    Client removeClient(String clientName);

    boolean hasClient(String clientName);

    Set<PaymentMode> listSupportedPaymodes(String clientName);

    void addSupportForPaymode(String clientName, PaymentMode paymentMode) throws Exception;

    void removePaymode(String clientName, PaymentMode paymentMode);

    void addBankDistribution(PaymentMode paymentMode, Bank bank, int percentage);

    Map<Bank, Integer> showDistribution(PaymentMode paymentMode);

    TransactionStatus makePayment(String clientName, PaymentMode paymentMode, Map<String, Object> details);

    Map<String, Client> getClients();

    MachineCoding.interfaces.IBankDistributionStrategy getBankDistributionStrategy();

    String getName();
}
