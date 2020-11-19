package net.omisoft.hotel.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.omisoft.ApplicationConstants;
import net.omisoft.hotel.dto.reservation.ReservationCreateDto;
import net.omisoft.hotel.dto.reservation.ReservationDto;
import net.omisoft.hotel.interceptor.DateRangeInterceptor;
import net.omisoft.hotel.mapper.ReservationMapper;
import net.omisoft.hotel.model.Reservation;
import net.omisoft.hotel.pojo.CustomMessage;
import net.omisoft.hotel.repository.specification.ReservationDateRangeSpecification;
import net.omisoft.hotel.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

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

    @GetMapping(value = "{id}")
    @ApiOperation(value = "Get reservation by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class),
            @ApiResponse(code = 404, message = "Not found", response = CustomMessage.class)
    })
    public ReservationDto get(@ApiParam(value = "Id reservation", defaultValue = "1", required = true) @Positive @PathVariable long id) {
        log.debug("REST request to get Reservation : {}", id);

        Reservation reservation = reservationService.get(id);
        return ReservationMapper.toDto(reservation);
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Get reservations (with pagination)")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class)
    })
    public Page<ReservationDto> get(
            @ApiParam(value = "Results page you want to retrieve (0..N)", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) @PositiveOrZero int page,
            @ApiParam(value = "Number of records per page", required = false) @RequestParam(value = "size", required = false) @Positive Integer size,
            @ApiParam(value = DateRangeInterceptor.START_DATE_PARAM, required = false) @RequestParam(value = DateRangeInterceptor.START_DATE_PARAM, required = false) @DateTimeFormat(pattern = DateRangeInterceptor.DATE_FORMAT_PATTERN) LocalDate from,
            @ApiParam(value = DateRangeInterceptor.START_DATE_PARAM, required = false) @RequestParam(value = DateRangeInterceptor.END_DATE_PARAM, required = false) @DateTimeFormat(pattern = DateRangeInterceptor.DATE_FORMAT_PATTERN) LocalDate to,
            @ApiIgnore ReservationDateRangeSpecification specification) {
        log.debug("REST request to get a page of Reservations");

        Pageable pageable = PageRequest.of(page, size == null ? ApplicationConstants.DEFAULT_PAGE_SIZE : size, Sort.Direction.ASC, "startDate");
        Page<Reservation> reservations = reservationService.get(specification, pageable);
        return ReservationMapper.toDto(reservations);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value = "Update reservation by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class),
            @ApiResponse(code = 404, message = "Not found", response = CustomMessage.class)
    })
    public ReservationDto update(@ApiParam(value = "Id reservation", defaultValue = "1", required = true) @PathVariable @Positive long id,
                                 @ApiParam(value = "Reservation data", required = true) @Validated @RequestBody ReservationCreateDto data) {
        log.debug("REST request to update Reservation : {}", id);

        Reservation reservation = reservationService.update(id, data);
        return ReservationMapper.toDto(reservation);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete reservation by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class),
            @ApiResponse(code = 404, message = "Not found", response = CustomMessage.class)
    })
    public void delete(@ApiParam(value = "Id reservation", defaultValue = "1", required = true) @Positive @PathVariable long id) {
        log.debug("REST request to delete Reservation : {}", id);

        reservationService.delete(id);
    }

}
