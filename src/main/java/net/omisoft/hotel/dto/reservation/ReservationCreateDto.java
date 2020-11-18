package net.omisoft.hotel.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.omisoft.hotel.dto.validator.annotation.ValidateDateRange;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidateDateRange(start = "startDate", end = "endDate", futureOrPresent = true)
public class ReservationCreateDto {

    @Positive
    @JsonProperty(value = "id_room")
    @ApiModelProperty(notes = "Room id", value = "1", example = "1", required = true, position = 0)
    private long idRoom;

    @NotBlank
    @Size(max = 80)
    @JsonProperty(value = "first_name")
    @ApiModelProperty(notes = "First name", value = "Fedir", example = "Fedir", required = true, position = 1)
    private String firstName;

    @NotBlank
    @Size(max = 80)
    @JsonProperty(value = "last_name")
    @ApiModelProperty(notes = "Last name", value = "Pyshnyi", example = "Pyshnyi", required = true, position = 2)
    private String lastName;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "start_date")
    @ApiModelProperty(notes = "Start date", value = "2021-12-01", example = "2021-12-01", required = true, position = 3)
    private LocalDate startDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value = "end_date")
    @ApiModelProperty(notes = "End date", value = "2021-12-02", example = "2021-12-02", required = true, position = 4)
    private LocalDate endDate;

}
