package bg.soft_uni.SpringDataLab;

import bg.soft_uni.SpringDataLab.entities.Account;
import bg.soft_uni.SpringDataLab.entities.User;
import bg.soft_uni.SpringDataLab.services.AccountService;
import bg.soft_uni.SpringDataLab.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private UserService userService;
    private AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Pesho", 20);

        Account account = new Account(new BigDecimal(25000));
        Set<Account> accounts = user.getAccounts();
        accounts.add(account);

        user.setAccounts(accounts);

        userService.registerUser(user);

        accountService.withdrawMoney(new BigDecimal(20000), account.getId());
        accountService.transferMoney(new BigDecimal(30000), account.getId());

    }
}
