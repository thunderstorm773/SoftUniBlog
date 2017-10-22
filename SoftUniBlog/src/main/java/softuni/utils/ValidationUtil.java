package softuni.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationUtil<T> {

    private Set<ConstraintViolation<T>> constraints;

    public ValidationUtil(T target) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.constraints = factory.getValidator().validate(target);
    }

    public List<String> getInvalidParamsMessages() {
        List<String> invalidParamMessages = new ArrayList<>();
        for (ConstraintViolation<T> constraint : this.constraints) {
            String invalidParamMessage = constraint.getMessage();
            invalidParamMessages.add(invalidParamMessage);
        }

        return invalidParamMessages;
    }
}
