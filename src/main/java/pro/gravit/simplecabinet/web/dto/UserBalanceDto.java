package pro.gravit.simplecabinet.web.dto;

import pro.gravit.simplecabinet.web.model.UserBalance;

public class UserBalanceDto {
    public final long id;
    public final double balance;
    public final String currency;

    public UserBalanceDto(UserBalance entity) {
        this.id = entity.getId();
        this.balance = entity.getBalance();
        this.currency = entity.getCurrency();
    }
}
