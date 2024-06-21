package MachineCoding.clients;

import MachineCoding.enums.PaymentMode;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Client {
    @NotNull
    String id= UUID.randomUUID().toString();
    @NonNull private String name;
    private Set<PaymentMode> supportedPaymentModes = new HashSet<>();
}