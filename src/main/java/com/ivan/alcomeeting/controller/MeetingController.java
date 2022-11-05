package com.ivan.alcomeeting.controller;

import com.ivan.alcomeeting.dto.MeetingCreationDto;
import com.ivan.alcomeeting.dto.MeetingDto;
import com.ivan.alcomeeting.dto.MeetingUpdateDto;
import com.ivan.alcomeeting.service.MeetingService;
import com.ivan.alcomeeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<MeetingDto> getAllMeetings(){
        return meetingService.getAllMeetings();
    }

    @GetMapping("{meetingId}")
    public MeetingDto getMeetingById(@PathVariable Long meetingId){
       return meetingService.getMeetingById(meetingId);
    }

    @PostMapping
    public MeetingDto createMeeting(@RequestBody MeetingCreationDto meetingCreationDto){
        return meetingService.createMeeting(meetingCreationDto);
    }

    /**
     * If this method receive a "meetingUpdateDto" with "null" field, it sets "null" to the field for existing "Meeting".
     */
    @PutMapping
    public MeetingDto updateMeeting(@RequestBody MeetingUpdateDto meetingUpdateDto){
        return meetingService.updateMeeting(meetingUpdateDto);
    }

    @DeleteMapping("{meetingId}")
    public void deleteMeeting(@PathVariable Long meetingId){
        meetingService.deleteMeeting(meetingId);
    }

}
