package com.ivan.alcomeeting.repository;

import com.ivan.alcomeeting.entity.Beverage;
import com.ivan.alcomeeting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findById(Long aLong);

    List<User> findUsersByBeverages(Beverage beverage);
}
