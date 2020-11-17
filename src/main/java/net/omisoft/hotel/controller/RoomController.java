package net.omisoft.hotel.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.omisoft.ApplicationConstants;
import net.omisoft.hotel.dto.room.RoomCreateDto;
import net.omisoft.hotel.dto.room.RoomDto;
import net.omisoft.hotel.mapper.RoomMapper;
import net.omisoft.hotel.model.Room;
import net.omisoft.hotel.pojo.CustomMessage;
import net.omisoft.hotel.service.RoomService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(value = ApplicationConstants.API_V1_BASE_PATH + "rooms", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Slf4j
@AllArgsConstructor
@Api(tags = "1.ROOM")
public class RoomController {

    private final RoomService roomService;

    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Create room")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class)
    })
    public RoomDto create(@ApiParam(value = "Room data", required = true) @Validated @RequestBody RoomCreateDto data) {
        log.debug("REST request to create Room : {}", data);

        Room room = roomService.create(data);
        return RoomMapper.toDto(room);
    }

    @GetMapping(value = "{id}")
    @ApiOperation(value = "Get room by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class),
            @ApiResponse(code = 404, message = "Not found", response = CustomMessage.class)
    })
    public RoomDto get(@ApiParam(value = "Id room", defaultValue = "1", required = true) @Positive @PathVariable long id) {
        log.debug("REST request to get Room : {}", id);

        Room room = roomService.get(id);
        return RoomMapper.toDto(room);
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Get rooms (with pagination)")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class)
    })
    public Slice<RoomDto> get(@ApiParam(value = "Results page you want to retrieve (0..N)", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) @PositiveOrZero int page,
                              @ApiParam(value = "Number of records per page", required = false) @RequestParam(value = "size", required = false) @Positive Integer size) {
        log.debug("REST request to get a page of Rooms");

        Pageable pageable = PageRequest.of(page, size == null ? ApplicationConstants.DEFAULT_PAGE_SIZE : size, Sort.Direction.ASC, "number");
        Slice<Room> rooms = roomService.get(pageable);
        return RoomMapper.toDto(rooms);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete room by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = CustomMessage.class),
            @ApiResponse(code = 404, message = "Not found", response = CustomMessage.class)
    })
    public void delete(@ApiParam(value = "Id room", defaultValue = "1", required = true) @Positive @PathVariable long id) {
        log.debug("REST request to delete Room : {}", id);

        roomService.deleteById(id);
    }

}
