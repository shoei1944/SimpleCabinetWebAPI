package pro.gravit.simplecabinet.web.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.dto.BalanceTransactionDto;
import pro.gravit.simplecabinet.web.dto.PageDto;
import pro.gravit.simplecabinet.web.dto.UserBalanceDto;
import pro.gravit.simplecabinet.web.exception.AuthException;
import pro.gravit.simplecabinet.web.exception.BalanceException;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.exception.InvalidParametersException;
import pro.gravit.simplecabinet.web.service.BalanceService;
import pro.gravit.simplecabinet.web.service.UserService;

@RestController
@RequestMapping("/cabinet/money")
public class CabinetMoneyController {
    @Autowired
    private UserService userService;
    @Autowired
    private BalanceService balanceService;

    @GetMapping("/balance/page/{pageId}")
    public PageDto<UserBalanceDto> getBalancePage(@PathVariable int pageId) {
        var user = userService.getCurrentUser();
        var list = balanceService.findUserBalanceByUser(user.getReference(), PageRequest.of(pageId, 10));
        return new PageDto<>(list.map(UserBalanceDto::new));
    }

    @GetMapping("/balance/id/{id}")
    public UserBalanceDto getBalanceById(@PathVariable long id) {
        var user = userService.getCurrentUser();
        var optional = balanceService.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Balance not found");
        }
        if (optional.get().getUser().getId() != user.getId()) {
            throw new AuthException("Access denied");
        }
        return new UserBalanceDto(optional.get());
    }

    @GetMapping("/balance/id/{id}/transactions/page/{pageId}")
    public PageDto<BalanceTransactionDto> getBalanceById(@PathVariable long id, @PathVariable int pageId) {
        var user = userService.getCurrentUser();
        var optional = balanceService.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Balance not found");
        }
        if (optional.get().getUser().getId() != user.getId()) {
            throw new AuthException("Access denied");
        }
        var list = balanceService.findAllByBalance(optional.get(), PageRequest.of(pageId, 10));
        return new PageDto<>(list.map(BalanceTransactionDto::new));
    }

    @GetMapping("/balance/currency/{currency}")
    public UserBalanceDto getBalanceByCurrency(@PathVariable String currency) {
        var user = userService.getCurrentUser();
        var optional = balanceService.findUserBalanceByUserAndCurrency(user.getReference(), currency);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Balance not found");
        }
        return new UserBalanceDto(optional.get());
    }


    @PostMapping("/transfer/self")
    @Transactional
    public BalanceTransactionDto selfTransfer(@RequestBody SelfTransferRequest request) throws BalanceException {
        var user = userService.getCurrentUser();
        var from = balanceService.findById(request.fromBalanceId);
        var to = balanceService.findById(request.toBalanceId);
        if (from.isEmpty()) {
            throw new InvalidParametersException("From balance not found", 1);
        }
        if (to.isEmpty()) {
            throw new InvalidParametersException("To balance not found", 2);
        }
        if (from.get().getUser().getId() != user.getId() || to.get().getUser().getId() != user.getId()) {
            throw new SecurityException("Access denied");
        }
        var transaction = balanceService.transferMoney(user.getReference(), from.get(), to.get(), request.count, null, true);
        return new BalanceTransactionDto(transaction);
    }

    @PostMapping("/transfer")
    @Transactional
    public BalanceTransactionDto transfer(@RequestBody SelfTransferRequest request) throws BalanceException {
        var user = userService.getCurrentUser();
        var from = balanceService.findById(request.fromBalanceId);
        var to = balanceService.findById(request.toBalanceId);
        if (from.isEmpty()) {
            throw new InvalidParametersException("From balance not found", 1);
        }
        if (to.isEmpty()) {
            throw new InvalidParametersException("To balance not found", 2);
        }
        if (from.get().getUser().getId() != user.getId() || to.get().getUser().getId() != user.getId()) {
            throw new SecurityException("Access denied");
        }
        var transaction = balanceService.transferMoney(user.getReference(), from.get(), to.get(), request.count, null, true);
        return new BalanceTransactionDto(transaction);
    }

    public record SelfTransferRequest(long fromBalanceId, long toBalanceId, long count) {
    }

    public record TransferRequest(long fromBalanceId, long toBalanceId, long count) {
    }
}
