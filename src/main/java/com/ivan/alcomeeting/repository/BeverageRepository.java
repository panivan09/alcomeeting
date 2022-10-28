package com.ivan.alcomeeting.repository;

import com.ivan.alcomeeting.entity.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Long> {


    List<Beverage> findAll();
}
