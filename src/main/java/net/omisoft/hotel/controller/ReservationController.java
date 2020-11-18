package net.omisoft.hotel.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.omisoft.ApplicationConstants;
import net.omisoft.hotel.dto.reservation.ReservationCreateDto;
import net.omisoft.hotel.dto.reservation.ReservationDto;
import net.omisoft.hotel.mapper.ReservationMapper;
import net.omisoft.hotel.model.Reservation;
import net.omisoft.hotel.pojo.CustomMessage;
import net.omisoft.hotel.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApplicationConstants.API_V1_BASE_PATH + "reservations", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Slf4j
@AllArgsConstructor
@Api(tags = "2.RESERVATION")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Create reservation")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class),
            @ApiResponse(code = 404, message = "Not found", response = CustomMessage.class)
    })
    public ReservationDto create(@ApiParam(value = "Reservation data", required = true) @Validated @RequestBody ReservationCreateDto data) {
        log.debug("REST request to create Reservation : {}", data);

        Reservation reservation = reservationService.create(data);
        return ReservationMapper.toDto(reservation);
    }

}
