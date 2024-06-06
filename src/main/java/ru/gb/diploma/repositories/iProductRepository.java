package ru.gb.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.diploma.model.Product;

import java.util.Optional;

@Repository
public interface  iProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    void deleteProductByName(String name);
}
