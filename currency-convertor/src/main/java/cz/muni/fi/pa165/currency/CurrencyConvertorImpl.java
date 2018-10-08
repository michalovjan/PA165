package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace("convert method called");
        if (sourceCurrency == null || targetCurrency == null || sourceAmount == null) {
            throw new IllegalArgumentException("One of arguments is null");
        }
        BigDecimal exchangeRate;
        try {
            exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
        } catch (ExternalServiceFailureException e){
            logger.error("Exchange rate table failed",e);
            throw new UnknownExchangeRateException("External service failed",e);
        }
        if (exchangeRate == null) {
            logger.warn("Exchange rate is unknown for currencies: ",sourceAmount,targetCurrency);
            throw new UnknownExchangeRateException("Unknown currency");
        }

        return exchangeRate.multiply(sourceAmount).setScale(2,RoundingMode.HALF_EVEN);
    }

}
