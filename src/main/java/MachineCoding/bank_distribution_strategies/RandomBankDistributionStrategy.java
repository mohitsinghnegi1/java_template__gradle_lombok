package MachineCoding.bank_distribution_strategies;


import MachineCoding.banks.Bank;
import MachineCoding.interfaces.IBankDistributionStrategy;

import java.util.Map;
import java.util.Random;

public  class RandomBankDistributionStrategy implements IBankDistributionStrategy {

    private static RandomBankDistributionStrategy INSTANCE;


    private RandomBankDistributionStrategy(){}

    public static synchronized RandomBankDistributionStrategy  getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new RandomBankDistributionStrategy();
        }
        return INSTANCE;
    }


    @Override
    public Bank selectBank(Map<Bank, Integer> distribution, Object metadata) {
        int total = distribution.values().stream().mapToInt(Integer::intValue).sum();
        int random = new Random().nextInt(total);

        int sum = 0;
        for (Map.Entry<Bank, Integer> entry : distribution.entrySet()) {
            sum += entry.getValue();
            if (random < sum) {
                return entry.getKey();
            }
        }
        return null;
    }
}
