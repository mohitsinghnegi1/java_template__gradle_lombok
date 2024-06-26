package MachineCoding.factories;

import MachineCoding.interfaces.IBankDistributionStrategy;
import MachineCoding.interfaces.IClientRepository;
import MachineCoding.interfaces.IPaymentGateway;
import MachineCoding.payment_gateways.PaymentGateway;
import MachineCoding.interfaces.IBankDistributionRepository;


public class PaymentGatewayFactory {
    private static PaymentGatewayFactory INSTANCE;


    private PaymentGatewayFactory(){}

    public static synchronized PaymentGatewayFactory  getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new PaymentGatewayFactory();
        }
        return INSTANCE;
    }

    public IPaymentGateway getPaymentGateway(String identifier, IBankDistributionStrategy distributionStrategy,
                                             IClientRepository clientRepository, IBankDistributionRepository bankDistributionRepository){
        return new PaymentGateway(identifier,distributionStrategy,clientRepository,bankDistributionRepository);
    }
}
