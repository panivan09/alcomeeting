package com.ivan.alcomeeting.repository;

import com.ivan.alcomeeting.entity.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    @Modifying
    @Query(value = "delete from meetings_beverages mb where mb.beverage_id = ?1", nativeQuery = true)
    void removeMeetingsBeverages(Long beverageId);

    @Modifying
    @Query(value = "delete from users_beverages ub where beverage_id = ?1", nativeQuery = true)
    void removeUsersBeverages(Long beverageId);

    @Modifying
    @Query(value = "delete from beverages where beverages.id = ?1", nativeQuery = true)
    void removeBeverage(Long beverageId);

    Optional<Beverage> findByName(String beverageName);
}
