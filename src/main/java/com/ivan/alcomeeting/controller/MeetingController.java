package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.exception.ValidationException;
import com.ivan.alcomeeting.service.meetingservice.MeetingCreationService;
import com.ivan.alcomeeting.service.meetingservice.MeetingDeleteService;
import com.ivan.alcomeeting.service.meetingservice.MeetingReadService;
import com.ivan.alcomeeting.service.meetingservice.MeetingUpdateService;
import com.ivan.alcomeeting.service.userservice.UserRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    private final MeetingReadService meetingReadService;
    private final MeetingCreationService meetingCreationService;
    private final MeetingUpdateService meetingUpdateService;
    private final MeetingDeleteService meetingDeleteService;
    private final UserRightService userRightService;


    @Autowired
    public MeetingController(MeetingReadService meetingReadService,
                             MeetingCreationService meetingCreationService,
                             MeetingUpdateService meetingUpdateService,
                             MeetingDeleteService meetingDeleteService,
                             UserRightService userRightService) {
        this.meetingReadService = meetingReadService;
        this.meetingCreationService = meetingCreationService;
        this.meetingUpdateService = meetingUpdateService;
        this.meetingDeleteService = meetingDeleteService;
        this.userRightService = userRightService;
    }

    @GetMapping("/bulk")
    @PreAuthorize("hasAuthority('READ')")
    public List<MeetingDto> getAllMeetings(){
        return meetingReadService.getAllMeetings();
    }

    /**
     * This endpoint retrieves meeting object by meeting id
     * @param meetingId - id of them meeting
     * @return found meeting by id
     */
    @GetMapping("{meetingId}")
    @PreAuthorize("hasAuthority('READ')")
    public MeetingDto getMeetingById(@PathVariable Long meetingId){
       return meetingReadService.getMeetingById(meetingId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE')")
    public MeetingDto createMeeting(@Valid @RequestBody MeetingCreationDto meetingCreationDto, Principal principal){
        // check that principal user == owner or principal is admin
        return meetingCreationService.createMeeting(meetingCreationDto, principal);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE')")
    public MeetingDto updateMeeting(@RequestBody MeetingUpdateDto meetingUpdateDto, Principal principal){
        //userRightService.isAllowed(Principal principal, Long meetingId) check that principal is or owner or admin
        userRightService.isAllowed(principal, meetingUpdateDto.getId());

        return meetingUpdateService.updateMeeting(meetingUpdateDto);
    }

    @DeleteMapping("{meetingId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void deleteMeeting(@PathVariable Long meetingId, Principal principal) throws ValidationException {
        //userRightService.isAllowed(Principal principal, Long meetingId)
        userRightService.isAllowed(principal, meetingId);

        meetingDeleteService.deleteMeeting(meetingId);
    }

    @PutMapping("/{meetingId}/user")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public MeetingDto addUser(@PathVariable("meetingId") Long meetingId,
                                       @RequestParam Long userId){

        return meetingUpdateService.addUser(meetingId, userId);
    }

    @DeleteMapping("/{meetingId}/user")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public MeetingDto deleteUser(@PathVariable("meetingId") Long meetingId,
                                 @RequestParam Long userId){

        return meetingUpdateService.deleteUser(meetingId, userId);
    }

    @GetMapping("/search/beverage")
    @PreAuthorize("hasAuthority('READ')")
    public List<MeetingDto> getAllMeetingsByBeverage(@RequestParam Long beverageId){
        return meetingReadService.getAllMeetingsByBeverage(beverageId);
    }

    @GetMapping("/search/date")
    @PreAuthorize("hasAuthority('READ')")
    public List<MeetingDto> getAllMeetingsByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return meetingReadService.getAllMeetingsByDate(date);
    }

    @GetMapping("/search/address")
    @PreAuthorize("hasAuthority('READ')")
    public List<MeetingDto> getALlMeetingsByPlace(@RequestParam String address){
        return meetingReadService.getALlMeetingsByAddress(address);
    }
}
