package com.practoapp.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class DoctorWithTimeSlotsDto {
    private long doctorId;
    private String doctorName; // Add any other doctor details you want to return
    private List<TimeSlot> availableTimeSlots;
}
