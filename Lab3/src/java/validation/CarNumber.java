/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.*;

/**
 *
 * @author 1
 */
@NotNull
@Pattern.List({
    @Pattern(regexp="^[A-Z]{2}[0-9]{4}[A-Z]{2}$"),
    @Pattern(regexp="^[ABCEHIKMOPTX]{2}......$")
})
@ReportAsSingleViolation
@Constraint(validatedBy={})
@Target({METHOD, FIELD, PARAMETER, TYPE})
@Retention(RUNTIME)
public @interface CarNumber {
    String message() default "Car number should match special pattern.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
