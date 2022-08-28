package com.epam.finalproject.framework.validation.validators;


import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource;
public class PhoneNumberValidator implements
                                    ConstraintValidator<PhoneNumber, String> {

    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public boolean isValid(String contactField,
      ConstraintValidatorContext cxt) {
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(contactField,
                    CountryCodeSource.UNSPECIFIED.name());
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }

}