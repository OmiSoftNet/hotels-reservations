package net.omisoft.hotel.dto.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {

    @ApiModelProperty(notes = "Id", value = "1", example = "1", required = true, position = 0)
    private long id;

    @ApiModelProperty(notes = "Number", value = "1", example = "1", required = true, position = 1)
    private long number;

    @ApiModelProperty(notes = "Type", value = "STANDARD", example = "STANDARD", allowableValues = "STANDARD, BUSINESS, PRESIDENT", required = true, position = 2)
    private String type;

}
