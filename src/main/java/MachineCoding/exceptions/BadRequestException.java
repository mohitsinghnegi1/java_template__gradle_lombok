package MachineCoding.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public
class BadRequestException extends RuntimeException{

    private int statusCode;
    private String message;
}
