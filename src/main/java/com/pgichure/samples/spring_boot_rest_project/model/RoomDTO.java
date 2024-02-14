package com.pgichure.samples.spring_boot_rest_project.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoomDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String roomName;

    @NotNull
    @Size(max = 255)
    private String roomNumber;

    @NotNull
    private Long capacity;

}
