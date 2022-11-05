package com.ivan.alcomeeting.repository;

import com.ivan.alcomeeting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query(value = "delete from meetings_users where user_id = ?1", nativeQuery = true)
    void deleteUserFromMeetingsUsers(Long userId);

    @Modifying
    @Query(value = "delete from users_beverages where user_id = ?1", nativeQuery = true)
    void deleteUserFromUsersBeverages(Long userId);


}
