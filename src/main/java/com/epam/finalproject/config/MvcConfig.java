package com.epam.finalproject.config;

import com.epam.finalproject.currency.servlet.CurrencyUnitChangeInterceptor;
import com.epam.finalproject.currency.servlet.CurrencyUnitContextHolderInterceptor;
import com.epam.finalproject.currency.servlet.CurrencyUnitContextResolver;
import com.epam.finalproject.currency.servlet.SessionCurrencyUnitResolver;
import com.epam.finalproject.framework.beans.annotation.Bean;
import com.epam.finalproject.framework.beans.annotation.Configuration;
import com.epam.finalproject.framework.web.interceptor.*;

import javax.money.Monetary;
import java.util.Locale;

@Configuration
public class MvcConfig {

    @Bean(clazz = SessionLocaleResolver.class)
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("uk","UA"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(LocaleContextResolver resolver) {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        lci.setLocaleResolver(resolver);
        return lci;
    }
    @Bean
    public LocaleContextHolderInterceptor localeContextHolderInterceptor(LocaleContextResolver resolver) {
        LocaleContextHolderInterceptor lchi = new LocaleContextHolderInterceptor();
        lchi.setLocaleContextResolver(resolver);
        return lchi;
    }

    @Bean(clazz = SessionCurrencyUnitResolver.class)
    public CurrencyUnitContextResolver currencyUnitContextResolver(){
        SessionCurrencyUnitResolver resolver = new  SessionCurrencyUnitResolver();
        resolver.setDefaultCurrencyUnit(Monetary.getCurrency("USD"));
        return resolver;
    }
    @Bean
    public CurrencyUnitChangeInterceptor currencyUnitChangeInterceptor(CurrencyUnitContextResolver resolver) {
        CurrencyUnitChangeInterceptor interceptor = new CurrencyUnitChangeInterceptor();
        interceptor.setCurrencyUnitResolver(resolver);
        return interceptor;
    }
    @Bean
    public CurrencyUnitContextHolderInterceptor currencyUnitContextPutInHolderInterceptor(CurrencyUnitContextResolver resolver) {
        CurrencyUnitContextHolderInterceptor interceptor = new CurrencyUnitContextHolderInterceptor();
        interceptor.setCurrencyUnitContextResolver(resolver);
        return interceptor;
    }
}
