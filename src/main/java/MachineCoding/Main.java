package MachineCoding;

import MachineCoding.bank_distribution_strategies.RandomBankDistributionStrategy;
import MachineCoding.banks.Bank;
import MachineCoding.clients.Client;
import MachineCoding.enums.PaymentMode;
import MachineCoding.enums.TransactionStatus;
import MachineCoding.exceptions.BadRequestException;
import MachineCoding.factories.PaymentGatewayFactory;
import MachineCoding.interfaces.IPaymentGateway;
import MachineCoding.repositories.BankDistributionRepository;
import MachineCoding.repositories.ClientRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create payment gateways using Factory pattern. (Liskov Substitution )
        IPaymentGateway citrus = PaymentGatewayFactory.getInstance().getPaymentGateway("citrus", RandomBankDistributionStrategy.getInstance(), ClientRepository.getInstance(), BankDistributionRepository.getInstance());
        System.out.println("Created Payment Gateway: "+citrus.getName() );
        IPaymentGateway paymentGateway = PaymentGatewayFactory.getInstance().getPaymentGateway("Razorpay", RandomBankDistributionStrategy.getInstance(),ClientRepository.getInstance(), BankDistributionRepository.getInstance());
        System.out.println("Created Payment Gateway: "+paymentGateway.getName() );

        System.out.println("_________________________________");

        // Add banks (Factory pattern can be used here to fetch bank based on name or id)
        Bank hdfc = new Bank("HDFC");
        Bank icici = new Bank("ICICI");

        // Add bank distribution
        paymentGateway.addBankDistribution(PaymentMode.CREDIT_CARD, hdfc, 50);
        System.out.println("Added Bank Distribution for payment Mode "+PaymentMode.CREDIT_CARD+" for bank "+hdfc.getName()+" distribution percentage as "+ 50);
        paymentGateway.addBankDistribution(PaymentMode.CREDIT_CARD, icici, 50);
        System.out.println("Added Bank Distribution for payment Mode "+PaymentMode.CREDIT_CARD+" for bank "+icici.getName()+" distribution percentage as "+ 50);

        paymentGateway.addBankDistribution(PaymentMode.NET_BANKING, icici, 100);
        System.out.println("Added Bank Distribution for payment Mode "+icici.getName()+" for bank "+icici+" distribution percentage as "+ 100);

        System.out.println("_________________________________");

        // Show distribution
        System.out.println("Credit Card Distribution: " + paymentGateway.showDistribution(PaymentMode.CREDIT_CARD));
        System.out.println("Net banking Distribution: " + paymentGateway.showDistribution(PaymentMode.NET_BANKING));

        System.out.println("_________________________________");
        // Add clients
        Client flipkart = new Client("Flipkart");
        paymentGateway.addClient(flipkart);
        System.out.println("Added Client: "+ flipkart);

        Client myntra = new Client("Myntra");
        paymentGateway.addClient(myntra);
        System.out.println("Added Client: "+ myntra);

        System.out.println("_________________________________");

        // Remove client
        Client removedClient = paymentGateway.removeClient("Myntra");
        System.out.println("Removed client: "+ removedClient);

        System.out.println("_________________________________");

        Map<String,Client> supportedClient = paymentGateway.getClients();
        System.out.println("supported client by "+paymentGateway.getName()+": "+ supportedClient);

        System.out.println("_________________________________");

        // Add payment mode support for clients
        paymentGateway.addSupportForPaymode("Flipkart", PaymentMode.CREDIT_CARD);
        paymentGateway.addSupportForPaymode("Flipkart", PaymentMode.NET_BANKING);
        System.out.println("Added Support for payment mode for Flipkart client");
        // List supported Payment Mode
        Set<PaymentMode> supportedPaymentModes = paymentGateway.listSupportedPaymodes("Flipkart");
        System.out.println("Supported Payment Modes by client Flipkart "+ supportedPaymentModes);

        System.out.println("_________________________________");
        // Remove Invalid unsupported mode
        try {
            System.out.println("Trying to remove Unsupported payment mode");
            paymentGateway.removePaymode("Flipkart", PaymentMode.UPI);
        }catch (BadRequestException ex){
            System.out.println("Status: "+ex.getStatusCode());
            System.out.println("Message: "+ex.getMessage());
        }

        System.out.println("_________________________________");

        // Make a payment
        Map<String, Object> details = new HashMap<>();
        details.put("cardNumber", "1234-XXXX-XXXX-XXXX");
        details.put("expiry", "12/24");
        details.put("cvv", "123");
        details.put("amount","5000");

        TransactionStatus status = paymentGateway.makePayment("Flipkart", PaymentMode.CREDIT_CARD, details);
        System.out.println("Attempted Payment with Transaction detail: " + details);
        System.out.println("Transaction Status: " + status);

        System.out.println("_________________________________");
        try{
            System.out.println("Attempting to make payment using Unsupported payment mode");
            status = paymentGateway.makePayment("Flipkart", PaymentMode.UPI, details);
            System.out.println("Made Payment with Transaction detail: " + details);
            System.out.println("Transaction Status: " + status);
        }catch (BadRequestException ex){
            System.out.println("Status: "+ex.getStatusCode());
            System.out.println("Message: "+ex.getMessage());
        }

        System.out.println("");
    }
}
