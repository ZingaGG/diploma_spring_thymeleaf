package ru.gb.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.diploma.model.Cart;
import ru.gb.diploma.model.User;

import java.util.Optional;

@Repository
public interface iCartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long id);

    Optional<Cart> findByUser(User user);
}
