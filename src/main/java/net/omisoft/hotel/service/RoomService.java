package net.omisoft.hotel.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.omisoft.hotel.configuration.MessageSourceConfiguration;
import net.omisoft.hotel.dto.room.RoomCreateDto;
import net.omisoft.hotel.mapper.RoomMapper;
import net.omisoft.hotel.model.Room;
import net.omisoft.hotel.repository.RoomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class RoomService {

    private final MessageSourceConfiguration message;
    private final RoomRepository roomRepository;

    @Transactional
    public Room create(RoomCreateDto dto) {
        log.debug("Creating Room : {}", dto);

        if (roomRepository.existsByNumber(dto.getNumber())) {
            throw new IllegalArgumentException(message.getMessage("exception.room.exists"));
        }
        Room room = RoomMapper.toEntity(dto);

        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Room get(long id) {
        log.debug("Get Room : {}", id);

        return roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(message.getMessage("exception.room.not_exists", new Object[]{id})));

    }

    @Transactional(readOnly = true)
    public Slice<Room> get(Pageable pageable) {
        log.debug("Get all Rooms");

        return roomRepository.getAll(pageable);
    }

    @Transactional
    public void deleteById(long id) {
        log.debug("Deleting Room : {}", id);

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(message.getMessage("exception.room.not_exists", new Object[]{id})));
        if (room.getReservations().isEmpty()) {
            roomRepository.delete(room);
        } else {
            throw new IllegalArgumentException(message.getMessage("exception.room.fk"));
        }
    }

}
