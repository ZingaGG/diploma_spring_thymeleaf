package ru.gb.diploma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.diploma.model.AppBalance;

@Repository
public interface iAppBalanceRepository extends JpaRepository<AppBalance, Long> {
}
