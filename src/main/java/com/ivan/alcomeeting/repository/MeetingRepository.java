package com.ivan.alcomeeting.repository;

import com.ivan.alcomeeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Modifying
    @Query(value = "delete from meetings_users where meeting_id = ?1", nativeQuery = true)
    void deleteMeetingFromMeetingsUsers(Long meetingId);

    @Modifying
    @Query(value = "delete from meetings_beverages where meeting_id = ?1", nativeQuery = true)
    void deleteMeetingFromMeetingsBeverages(Long meetingId);

}
