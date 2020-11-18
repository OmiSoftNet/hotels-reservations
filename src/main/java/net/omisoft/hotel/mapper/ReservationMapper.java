package net.omisoft.hotel.mapper;

import net.omisoft.hotel.dto.reservation.ReservationCreateDto;
import net.omisoft.hotel.dto.reservation.ReservationDto;
import net.omisoft.hotel.model.Reservation;

public final class ReservationMapper {

    public static Reservation toEntity(ReservationCreateDto dto) {
        return dto == null ? null : Reservation.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    public static ReservationDto toDto(Reservation reservation) {
        return reservation == null ? null : ReservationDto.builder()
                .id(reservation.getId())
                .firstName(reservation.getFirstName())
                .lastName(reservation.getLastName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .room(RoomMapper.toDto(reservation.getRoom()))
                .build();
    }

}
