package net.omisoft.hotel.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomMessage {

    @NonNull
    @ApiModelProperty(notes = "Message", required = true, position = 0)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(notes = "Property", required = false, position = 1)
    private String property;

}
