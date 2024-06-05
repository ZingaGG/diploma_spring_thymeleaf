package ru.gb.diploma.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.diploma.model.AppBalance;
import ru.gb.diploma.model.utils.exceptions.AppBalanceException;
import ru.gb.diploma.repositories.iAppBalanceRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AppBalanceService {

    private final iAppBalanceRepository balanceRepository;
    @Transactional
    public AppBalance getAppBalance() throws AppBalanceException {
        return balanceRepository.findById(1L).orElseThrow(() -> new AppBalanceException("App Balance not Found"));
    }
    @Transactional
    public AppBalance updateAppBalance(BigDecimal profit) throws AppBalanceException {
        AppBalance appBalance = getAppBalance();
        appBalance.setBalance(appBalance.getBalance().add(profit));
        return balanceRepository.save(appBalance);
    }
}
