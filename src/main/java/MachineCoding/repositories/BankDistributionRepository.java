package MachineCoding.repositories;

import MachineCoding.banks.Bank;
import MachineCoding.enums.PaymentMode;
import MachineCoding.interfaces.IBankDistributionRepository;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
public class BankDistributionRepository implements IBankDistributionRepository {

    private static BankDistributionRepository INSTANCE;


    private BankDistributionRepository(){}

    public static synchronized BankDistributionRepository  getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new BankDistributionRepository();
        }
        return INSTANCE;
    }


    private Map<PaymentMode, Map<Bank, Integer>> bankDistribution = new HashMap<>();

    @Override
    public void addBankDistribution(PaymentMode paymentMode, Bank bank, int percentage) {
        bankDistribution.computeIfAbsent(paymentMode, k -> new HashMap<>()).put(bank, percentage);
    }

    @Override
    public Map<Bank, Integer> showDistribution(PaymentMode paymentMode) {
        return bankDistribution.getOrDefault(paymentMode, Collections.emptyMap());
    }
}
