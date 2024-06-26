package MachineCoding.interfaces;

import MachineCoding.banks.Bank;
import MachineCoding.enums.PaymentMode;

import java.util.Map;

public interface IBankDistributionRepository {
    void addBankDistribution(PaymentMode paymentMode, Bank bank, int percentage);

    Map<Bank, Integer> showDistribution(PaymentMode paymentMode);
}
