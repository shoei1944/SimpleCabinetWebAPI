package pro.gravit.simplecabinet.web.model;

import javax.persistence.*;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchange_rates_generator")
    @SequenceGenerator(name = "exchange_rates_generator", sequenceName = "exchange_rates_seq", allocationSize = 1)
    private long id;
    private String fromCurrency;
    private String toCurrency;
    private double value;

    public long getId() {
        return id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
