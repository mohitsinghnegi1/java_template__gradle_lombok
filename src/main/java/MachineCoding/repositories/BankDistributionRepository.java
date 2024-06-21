package MachineCoding.repositories;

import MachineCoding.banks.Bank;
import MachineCoding.enums.PaymentMode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BankDistributionRepository {
    private Map<PaymentMode, Map<Bank, Integer>> bankDistribution = new HashMap<>();
}
