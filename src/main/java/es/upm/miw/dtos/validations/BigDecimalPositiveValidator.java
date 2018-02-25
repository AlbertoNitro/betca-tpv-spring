package es.upm.miw.dtos.validations;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BigDecimalPositiveValidator implements ConstraintValidator<BigDecimalPositive, BigDecimal> {

    @Override
    public void initialize(BigDecimalPositive constraint) {
        // Empty, not operation
    }

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext context) {
        return bigDecimal != null && bigDecimal.signum() != -1;
    }

}
