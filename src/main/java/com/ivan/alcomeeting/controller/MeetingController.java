package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.MeetingService;
import com.ivan.alcomeeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final UserService userService;

    @Autowired
    public MeetingController(MeetingService meetingService,
                             UserService userService) {
        this.meetingService = meetingService;
        this.userService = userService;
    }
    // CRUD endpoints
    // get all meetings -> List<MeetingDto> +
    // get meeting by id -> MeetingDto +
    // create meeting -> MeetingDto ???????????????????????????????
    // remove meeting -> void +
    // update meeting -> MeetingDto +
    // find meetings by values -> List<MeetingDto> ASK ANDRII HOW TO DO IT

    @GetMapping("/bulk")
    @PreAuthorize("hasAuthority('READ')")
    public List<MeetingDto> getAllMeetings(){
        return meetingService.getAllMeetings();
    }

    /**
     * This endpoint retrieves meeting object by meeting id
     * @param meetingId - id of them meeting
     * @return found meeting by id
     */
    @GetMapping("{meetingId}")
    @PreAuthorize("hasAuthority('READ')")
    public MeetingDto getMeetingById(@PathVariable Long meetingId){
       return meetingService.getMeetingById(meetingId);
    }

//    @PostMapping
//    public MeetingDto createMeeting(@RequestBody MeetingCreationDto meetingCreationDto, Principal principal){
//        return meetingService.createMeeting(meetingCreationDto, principal);
//    }

    /**
     * If this method receive a "meetingUpdateDto" with "null" field, it sets "null" to the field for existing "Meeting".
     */
    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE')")
    public MeetingDto updateMeeting(@RequestBody MeetingUpdateDto meetingUpdateDto){
        return meetingService.updateMeeting(meetingUpdateDto);
    }

    @DeleteMapping("{meetingId}")
    public void deleteMeeting(@PathVariable Long meetingId) throws ValidationException {
        meetingService.deleteMeeting(meetingId);
    }

    @PutMapping("/{meetingId}/user")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public MeetingDto addUser(@PathVariable("meetingId") Long meetingId,
                                       @RequestParam Long userId){

        return meetingService.addUser(meetingId, userId);
    }

    @DeleteMapping("/{meetingId}/user")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public MeetingDto deleteUser(@PathVariable("meetingId") Long meetingId,
                                 @RequestParam Long userId){

        return meetingService.deleteUser(meetingId, userId);
    }

    @GetMapping("/search/beverage")
    @PreAuthorize("hasAuthority('READ')")
    public List<MeetingDto> getAllMeetingsByBeverage(@RequestParam Long beverageId){
        return meetingService.getAllMeetingsByBeverage(beverageId);
    }

    //TODO: change "LocalDate" on "LocalDateTime"
    @GetMapping("/search/date")
    @PreAuthorize("hasAuthority('READ')")
    public List<MeetingDto> getAllMeetingsByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
//            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime date){
        return meetingService.getAllMeetingsByDate(date);
    }

    @GetMapping("/search/address")
    @PreAuthorize("hasAuthority('READ')")
    public List<MeetingDto> getALlMeetingsByPlace(@RequestParam String address){
        return meetingService.getALlMeetingsByAddress(address);
    }
}
