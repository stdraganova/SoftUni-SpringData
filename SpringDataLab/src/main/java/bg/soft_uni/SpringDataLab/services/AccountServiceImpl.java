package bg.soft_uni.SpringDataLab.services;

import bg.soft_uni.SpringDataLab.entities.Account;
import bg.soft_uni.SpringDataLab.entities.User;
import bg.soft_uni.SpringDataLab.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
       this.accountRepository = accountRepository;
    }


    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Optional<Account> account = accountRepository.findAccountById(id);
        BigDecimal balance = account.get().getBalance();
        User owner = account.get().getUser();

        if (account.isPresent() && (balance.compareTo(money) > -1 ) && owner != null){
            balance = balance.subtract(money);
            account.get().setBalance(balance);
        }
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        Optional<Account> account = accountRepository.findAccountById(id);
        BigDecimal balance = account.get().getBalance();
        User owner = account.get().getUser();

        if (account.isPresent() && (balance.compareTo(money) > -1 ) && owner != null){
            balance = balance.subtract(money);
            account.get().setBalance(balance);
        }
    }
}
