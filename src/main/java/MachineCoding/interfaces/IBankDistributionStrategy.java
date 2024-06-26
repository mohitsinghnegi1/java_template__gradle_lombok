package MachineCoding.interfaces;

import MachineCoding.banks.Bank;

import java.util.Map;

public interface IBankDistributionStrategy {
    Bank selectBank(Map<Bank, Integer> distribution, Object metadata);
}
