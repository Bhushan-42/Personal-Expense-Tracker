package com.expensetracker.PersonalExpenceTrackerAPI.jpa;

import com.expensetracker.PersonalExpenceTrackerAPI.DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
