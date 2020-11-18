package net.omisoft.hotel.dto.reservation;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.omisoft.hotel.dto.room.RoomDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    @ApiModelProperty(notes = "Id", value = "1", example = "1", required = true, position = 0)
    private long id;

    @ApiModelProperty(notes = "Room", required = true, position = 1)
    private RoomDto room;

    @ApiModelProperty(notes = "First name", value = "Fedir", example = "Fedir", required = true, position = 2)
    private String firstName;

    @ApiModelProperty(notes = "Last name", value = "Pyshnyi", example = "Pyshnyi", required = true, position = 3)
    private String lastName;

    @ApiModelProperty(notes = "Start date", value = "2021-12-01", example = "2021-12-01", required = true, position = 4)
    private LocalDate startDate;

    @ApiModelProperty(notes = "End date", value = "2021-12-02", example = "2021-12-02", required = true, position = 5)
    private LocalDate endDate;

}
