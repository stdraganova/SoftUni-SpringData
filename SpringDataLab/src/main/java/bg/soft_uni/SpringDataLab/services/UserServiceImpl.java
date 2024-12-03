package bg.soft_uni.SpringDataLab.services;

import bg.soft_uni.SpringDataLab.entities.User;
import bg.soft_uni.SpringDataLab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        Optional<User> userFound = userRepository.getUserByUsername(user.getUsername());

        if (!userFound.isPresent()) {
            userRepository.save(user);
        }
    }
}
