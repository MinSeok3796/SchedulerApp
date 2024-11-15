package com.example.schedulerapp.controller;

import com.example.schedulerapp.dto.ScheduleDto;
import com.example.schedulerapp.entity.Schedule;
import com.example.schedulerapp.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute("userId") != null;
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDto, HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleDto.getTitle());
        schedule.setContent(scheduleDto.getContent());
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return new ResponseEntity<>(scheduleDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id, HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        return schedule.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto, HttpServletRequest request) {
        if (!isAuthenticated(request)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Schedule> existingSchedule = scheduleService.getScheduleById(id);
        if (existingSchedule.isPresent()) {
            Schedule schedule = existingSchedule.get();
            schedule.setTitle(scheduleDto.getTitle());
            schedule.setContent(scheduleDto.getContent());
            Schedule savedSchedule = scheduleService.saveSchedule(schedule);
            return ResponseEntity.ok(savedSchedule);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
