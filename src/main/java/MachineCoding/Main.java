package MachineCoding;

import MachineCoding.bank_distribution_strategies.RandomBankDistributionStrategy;
import MachineCoding.banks.Bank;
import MachineCoding.clients.Client;
import MachineCoding.enums.PaymentMode;
import MachineCoding.enums.TransactionStatus;
import MachineCoding.exceptions.BadRequestException;
import MachineCoding.interfaces.IPaymentGateway;
import MachineCoding.payment_gateways.PaymentGateway;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        IPaymentGateway citrus = new PaymentGateway("Citrus", new RandomBankDistributionStrategy());
        IPaymentGateway paymentGateway = new PaymentGateway("Razorpay", new RandomBankDistributionStrategy());
        System.out.println("Created Payment Gateway "+paymentGateway.getName() );

        // Add clients
        Client flipkart = new Client("Flipkart");
        paymentGateway.addClient(flipkart);
        System.out.println("Added Client : "+ flipkart);

        Client myntra = new Client("Myntra");
        paymentGateway.addClient(myntra);
        System.out.println("Added Client : "+ myntra);

        // Remove client
        Client removedClient = paymentGateway.removeClient("Myntra");
        System.out.println("Removed client "+ removedClient);

        // Add banks (Factory pattern can be used here to fetch bank based on name or id)
        Bank hdfc = new Bank("HDFC");
        Bank icici = new Bank("ICICI");

        // Add payment mode support for clients
        paymentGateway.addSupportForPaymode("Flipkart", PaymentMode.CREDIT_CARD);
        paymentGateway.addSupportForPaymode("Flipkart", PaymentMode.NET_BANKING);

        // List supported Payment Mode
        Set<PaymentMode> supportedPaymentModes = paymentGateway.listSupportedPaymodes("Flipkart");
        System.out.println("Supported Payment Modes by client Flipkart "+ supportedPaymentModes);

        // Remove Invalid unsupported mode
        try {
            System.out.println("Tryng to remove Unsupported payment mode");
            paymentGateway.removePaymode("Flipkart", PaymentMode.UPI);
        }catch (BadRequestException ex){
            System.out.println("Status: "+ex.getStatusCode());
            System.out.println("Message: "+ex.getMessage());
        }

        // Add bank distribution
        paymentGateway.addBankDistribution(PaymentMode.CREDIT_CARD, hdfc, 100);
        System.out.println("Added Bank Distribution for bank "+hdfc+" distribution percentage as "+ 100);

        paymentGateway.addBankDistribution(PaymentMode.NET_BANKING, icici, 100);
        System.out.println("Added Bank Distribution for bank "+icici+" distribution percentage as "+ 100);



        // Make a payment
        Map<String, String> details = new HashMap<>();
        details.put("cardNumber", "1234-XXXX-XXXX-XXXX");
        details.put("expiry", "12/24");
        details.put("cvv", "123");

        TransactionStatus status = paymentGateway.makePayment("Flipkart", PaymentMode.CREDIT_CARD, details);
        System.out.println("Made Payment with Transaction detail: " + details);
        System.out.println("Transaction Status: " + status);

        // Show distribution
        System.out.println("Credit Card Distribution: " + paymentGateway.showDistribution(PaymentMode.CREDIT_CARD));
    }
}
