package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {
    public static void main(String ... args) {
        springAnnotationContext();
    }

    public static void springAnnotationContext() {

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");

        CurrencyConvertor currencyConvertor
                = applicationContext.getBean(CurrencyConvertor.class);

        System.err.println(currencyConvertor.convert(Currency.getInstance("EUR"),Currency.getInstance("CZK"),BigDecimal.ONE));


    }
}
