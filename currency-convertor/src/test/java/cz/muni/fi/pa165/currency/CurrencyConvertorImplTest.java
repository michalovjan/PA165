package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

//import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyConvertorImplTest {

    private final ExchangeRateTable exchangeRateTable = mock(ExchangeRateTable.class);
    private final CurrencyConvertorImpl convertor = new CurrencyConvertorImpl(exchangeRateTable);
    private final Currency eur = Currency.getInstance("EUR");
    private final Currency czk = Currency.getInstance("CZK");

    @Before
    public void before() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(eur,czk)).thenReturn(new BigDecimal("25"));
    }

    @Test
    public void testConvert() {
        // Don't forget to test border values and proper rounding.

        //62.5
        assertThat(convertor.convert(eur,czk,new BigDecimal("2.5"))).isEqualTo(new BigDecimal("62.50"));
        //2500
        assertThat(convertor.convert(eur,czk,new BigDecimal("100"))).isEqualTo(new BigDecimal("2500.00"));
        //25.975
        assertThat(convertor.convert(eur,czk,new BigDecimal("1.039"))).isEqualTo(new BigDecimal("25.98"));
        //50.165
        assertThat(convertor.convert(eur,czk,new BigDecimal("2.0066"))).isEqualTo(new BigDecimal("50.16"));
        //50.145
        assertThat(convertor.convert(eur,czk,new BigDecimal("2.0058"))).isEqualTo(new BigDecimal("50.14"));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        assertThatIllegalArgumentException().isThrownBy(() -> convertor.convert(null, czk, new BigDecimal("1")));
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        assertThatIllegalArgumentException().isThrownBy(() -> convertor.convert(eur, null, new BigDecimal("1")));
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        assertThatIllegalArgumentException().isThrownBy(() -> convertor.convert(eur, czk, null));
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(Currency.getInstance("USD"),eur)).thenReturn(null);
        assertThatExceptionOfType(UnknownExchangeRateException.class)
                .isThrownBy(() -> convertor.convert(Currency.getInstance("USD"), czk, new BigDecimal("10")))
                .withMessage("Unknown currency");
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        ExternalServiceFailureException externalServiceFailureException = new ExternalServiceFailureException("Service failed");
        when(exchangeRateTable.getExchangeRate(czk,eur)).thenThrow(externalServiceFailureException);

        assertThatExceptionOfType(UnknownExchangeRateException.class)
                .isThrownBy(() -> convertor.convert(czk, eur, new BigDecimal("10")))
                .withMessage("External service failed")
                .withCause(externalServiceFailureException);
    }

}
