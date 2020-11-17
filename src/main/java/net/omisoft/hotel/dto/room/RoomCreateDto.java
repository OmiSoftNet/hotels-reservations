package net.omisoft.hotel.dto.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.omisoft.hotel.dto.validator.annotation.ValidateEnum;
import net.omisoft.hotel.model.RoomType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCreateDto {

    @Positive
    @ApiModelProperty(notes = "Number", value = "1", example = "1", required = true, position = 0)
    private long number;

    @NotBlank
    @ValidateEnum(enumeration = RoomType.class)
    @ApiModelProperty(notes = "Type", value = "STANDARD", example = "STANDARD", allowableValues = "STANDARD, BUSINESS, PRESIDENT", required = true, position = 1)
    private String type;

}
