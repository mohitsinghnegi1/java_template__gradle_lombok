package MachineCoding.banks;

import MachineCoding.enums.PaymentMode;
import MachineCoding.enums.TransactionStatus;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Random;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Bank {
    @NotNull String id= UUID.randomUUID().toString();
    @NonNull private String name;

    public TransactionStatus processPayment(PaymentMode paymentMode, Map<String, Object> details) {
        // Perform validation based on payment mode
        // Note: We can use factory pattern to fetch validators based on payment Mode
        // Note: We can use factory pattern to get the payment processor corresponding to provided payment mode to process the payment

        // Mock random success or failure
        return new Random().nextInt(10)<=5 ? TransactionStatus.SUCCESS : TransactionStatus.FAILURE;
    }
}
