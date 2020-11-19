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
import net.omisoft.hotel.repository.specification.ReservationDateRangeSpecification;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Reservation get(long id) {
        log.debug("Get Reservation : {}", id);

        return reservationRepository.getByIdJoinRoom(id)
                .orElseThrow(() -> new NoSuchElementException(message.getMessage("exception.reservation.not_exists", new Object[]{id})));
    }

    @Transactional(readOnly = true)
    public Page<Reservation> get(ReservationDateRangeSpecification specification, Pageable pageable) {
        log.debug("Get all Reservations");

        return reservationRepository.findAll(specification, pageable);
    }

    @Transactional
    public Reservation update(long id, ReservationCreateDto dto) {
        log.debug("Updating Reservation : {}", dto);

        Reservation reservation = reservationRepository.getByIdJoinRoom(id)
                .orElseThrow(() -> new NoSuchElementException(message.getMessage("exception.reservation.not_exists", new Object[]{id})));

        if (dto.getIdRoom() != reservation.getRoom().getId()) {
            roomRepository.findById(dto.getIdRoom())
                    .orElseThrow(() -> new NoSuchElementException(message.getMessage("exception.room.not_exists", new Object[]{dto.getIdRoom()})));
        }

        if (isChangeRoomOrDate(dto, reservation)) {
            if (reservationRepository.existsByIdNotAndRoomIdAndDatePeriod(id, dto.getIdRoom(), dto.getStartDate(), dto.getEndDate())) {
                throw new IllegalArgumentException(message.getMessage("exception.room.reserved"));
            }
        }

        reservation.setFirstName(dto.getFirstName());
        reservation.setLastName(dto.getLastName());
        reservation.setStartDate(dto.getStartDate());
        reservation.setEndDate(dto.getEndDate());
        reservation.setRoom(roomRepository.getOne(dto.getIdRoom()));

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void delete(long id) {
        log.debug("Deleting Reservation : {}", id);

        try {
            reservationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchElementException(message.getMessage("exception.reservation.not_exists", new Object[]{id}));
        }
    }

    private boolean isChangeRoomOrDate(ReservationCreateDto dto, Reservation reservation) {
        if (dto.getIdRoom() != reservation.getRoom().getId()) {
            return true;
        }
        if (!dto.getStartDate().isEqual(reservation.getStartDate())) {
            return true;
        }
        if (!dto.getEndDate().isEqual(reservation.getEndDate())) {
            return true;
        }
        return false;
    }

}
