package com.ivan.alcomeeting.repository;


import com.ivan.alcomeeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Modifying
    @Query(value = "delete from meetings_users where meeting_id = ?1", nativeQuery = true)
    void deleteMeetingFromMeetingsUsers(Long meetingId);

    @Modifying
    @Query(value = "delete from meetings_beverages where meeting_id = ?1", nativeQuery = true)
    void deleteMeetingFromMeetingsBeverages(Long meetingId);

    @Query("SELECT m from Meeting m join fetch m.beverages b where b.id = ?1")
    List<Meeting> findAllByBeverage(Long beverage);

    List<Meeting> findAllByDateBetween(LocalDateTime from, LocalDateTime to);

    List<Meeting> findAllByAddress(String place);
}

