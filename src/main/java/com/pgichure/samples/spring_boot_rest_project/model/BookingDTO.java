package com.pgichure.samples.spring_boot_rest_project.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingDTO {

    private Long id;

    @NotNull
    private LocalDate bookingDate;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime startTime;

    @Schema(type = "string", example = "18:30")
    private LocalTime endTime;

    @NotNull
    private Long bookingId;

    @NotNull
    private Long rootId;

}
