package pro.gravit.simplecabinet.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.dto.BalanceTransactionDto;
import pro.gravit.simplecabinet.web.dto.UserBalanceDto;
import pro.gravit.simplecabinet.web.exception.BalanceException;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.exception.InvalidParametersException;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.service.BalanceService;
import pro.gravit.simplecabinet.web.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/admin/money")
public class AdminMoneyController {
    @Autowired
    private UserService userService;
    @Autowired
    private BalanceService balanceService;

    @GetMapping("/userbalance/id/{id}")
    public UserBalanceDto getUserBalanceById(@PathVariable long id) {
        var balance = balanceService.findById(id);
        if (balance.isEmpty()) {
            throw new EntityNotFoundException("Balance not found");
        }
        return new UserBalanceDto(balance.get());
    }

    @PostMapping("/addmoney")
    public BalanceTransactionDto addMoney(@RequestBody AddMoneyRequest request) throws BalanceException {
        var balanceTo = balanceService.findById(request.balanceId);
        if (balanceTo.isEmpty()) {
            throw new InvalidParametersException("Balance not found", 1);
        }
        var transaction = balanceService.addMoney(balanceTo.get(), request.count, request.comment);
        return new BalanceTransactionDto(transaction);
    }

    @PostMapping("/removemoney")
    public BalanceTransactionDto removeMoney(@RequestBody AddMoneyRequest request) throws BalanceException {
        var balanceTo = balanceService.findById(request.balanceId);
        if (balanceTo.isEmpty()) {
            throw new InvalidParametersException("Balance not found", 1);
        }
        var transaction = balanceService.removeMoney(balanceTo.get(), request.count, request.comment);
        return new BalanceTransactionDto(transaction);
    }

    @GetMapping("/userbalance/userid/{userId}/{currency}")
    public UserBalanceDto getUserBalanceByUserIdAndCurrency(@PathVariable long userId, @PathVariable String currency) {
        var userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var user = userOptional.get();
        var balance = balanceService.findOrCreateUserBalanceByUserAndCurrency(user, currency);
        return new UserBalanceDto(balance);
    }

    @GetMapping("/userbalance/uuid/{userUuid}/{currency}")
    public UserBalanceDto getUserBalanceByUuidAndCurrency(@PathVariable UUID userUuid, @PathVariable String currency) {
        var userOptional = userService.findByUUID(userUuid);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var user = userOptional.get();
        var balance = balanceService.findOrCreateUserBalanceByUserAndCurrency(user, currency);
        return new UserBalanceDto(balance);
    }

    @PostMapping("/transfer")
    public BalanceTransactionDto transfer(@RequestBody TransferMoneyRequest request) throws BalanceException {
        var fromOptional = balanceService.findById(request.fromBalanceId);
        var toOptional = balanceService.findById(request.toBalanceId);
        if (fromOptional.isEmpty()) {
            throw new InvalidParametersException("From not found", 2);
        }
        if (toOptional.isEmpty()) {
            throw new InvalidParametersException("To not found", 3);
        }
        var from = fromOptional.get();
        var to = toOptional.get();
        User user;
        if (!request.selfUser) {
            user = from.getUser();
        } else {
            user = userService.getCurrentUser().getReference();
        }
        var transaction = balanceService.transferMoney(user, from, to, request.count, request.comment, request.multicurrency);
        return new BalanceTransactionDto(transaction);
    }

    public static record AddMoneyRequest(long balanceId, double count, String comment) {

    }

    public static record TransferMoneyRequest(long fromBalanceId, long toBalanceId, double count, boolean selfUser,
                                              String comment, boolean multicurrency) {
    }
}
