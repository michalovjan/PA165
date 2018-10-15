package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainJavaConfig {
    public static void main(String ... args) {
        springJavaConfigContext();
    }

    private static void springJavaConfigContext() {
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(SpringJavaConfig.class);

        CurrencyConvertor currencyConvertor
                = applicationContext.getBean("currencyConvertor1",CurrencyConvertor.class);

        System.out.println(currencyConvertor.convert(Currency.getInstance("EUR"),Currency.getInstance("CZK"),BigDecimal.ONE));

    }
}
