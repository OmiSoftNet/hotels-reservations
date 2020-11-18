package net.omisoft.hotel.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.omisoft.hotel.configuration.MessageSourceConfiguration;
import net.omisoft.hotel.dto.reservation.ReservationCreateDto;
import net.omisoft.hotel.mapper.ReservationMapper;
import net.omisoft.hotel.model.Reservation;
import net.omisoft.hotel.model.Room;
import net.omisoft.hotel.repository.ReservationRepository;
import net.omisoft.hotel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class ReservationService {

    private final MessageSourceConfiguration message;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public Reservation create(ReservationCreateDto dto) {
        log.debug("Creating Reservation : {}", dto);

        Room room = roomRepository.findById(dto.getIdRoom())
                .orElseThrow(() -> new NoSuchElementException(message.getMessage("exception.room.not_exists", new Object[]{dto.getIdRoom()})));

        if (reservationRepository.existsByRoomIdAndDatePeriod(dto.getIdRoom(), dto.getStartDate(), dto.getEndDate())) {
            throw new IllegalArgumentException(message.getMessage("exception.room.reserved"));
        }

        Reservation reservation = ReservationMapper.toEntity(dto);
        reservation.setRoom(room);

        return reservationRepository.save(reservation);
    }

}
