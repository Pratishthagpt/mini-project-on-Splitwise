package com.miniproject.splitwise.Repositories;

import com.miniproject.splitwise.Models.ExpenseUser;
import com.miniproject.splitwise.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {
    List<ExpenseUser> findAllByUser (User user);
    ExpenseUser save (ExpenseUser expenseUser);

}
