package com.example.schedulerapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDto {
    @NotBlank
    private String title;

    private String content;
}