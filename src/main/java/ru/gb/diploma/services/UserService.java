package ru.gb.diploma.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.diploma.model.User;
import ru.gb.diploma.repositories.iUserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final iUserRepository userRepository;

    /**
     * Saves the user into Database
     * @param user
     * @return user
     */
    public User saveUser(User user){
        return userRepository.save(user);
    }

    /**
     * Get list of users from Databes
     * @return List<User>
     */
    public List<User> getListUsers(){
        return userRepository.findAll();
    }


    /**
     * Get User from DataBase by id
     * @param id
     * @return user
     */
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Get User from DataBase by email
     * @param <<code>String</code> email
     * @return <code>User</code> user
     */
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Оверрайд метода из UserDetailsService для корректной работы авторизации
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Метод для изменения данных пользователя, не требует изменения всех полей
     * @param user
     * @param newEmail
     * @param newFirstName
     * @param newLastName
     */

    @Transactional
    public void updateUserProfile(User user, String newEmail, String newFirstName, String newLastName) {
        if (newEmail != null && !newEmail.isEmpty()) {
            user.setEmail(newEmail);
        }
        if (newFirstName != null && !newFirstName.isEmpty()) {
            user.setFirstName(newFirstName);
        }
        if (newLastName != null && !newLastName.isEmpty()) {
            user.setLastName(newLastName);
        }
        userRepository.save(user);
    }

    /**
     * Добавляет средства на счет пользователя
     * @param user
     * @param amount
     */
    @Transactional
    public void addFunds(User user, BigDecimal amount) {
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
    }

    /**
     * Метод обновления баланса после покупки
     * @param user
     * @param totalCost
     * @return
     */
    @Transactional
    public boolean purchase(User user, BigDecimal totalCost) {
        if (user.getBalance().compareTo(totalCost) >= 0) {
            user.setBalance(user.getBalance().subtract(totalCost));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
