package net.omisoft.hotel.mapper;

import net.omisoft.hotel.dto.reservation.ReservationCreateDto;
import net.omisoft.hotel.dto.reservation.ReservationDto;
import net.omisoft.hotel.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ReservationDto> toDto(List<Reservation> reservations) {
        if (reservations == null) {
            return null;
        }
        List<ReservationDto> result = new ArrayList<>(reservations.size());
        for (Reservation reservation : reservations) {
            result.add(toDto(reservation));
        }
        return result;
    }

    public static Page<ReservationDto> toDto(Page<Reservation> reservations) {
        if (reservations == null) {
            return null;
        }
        return new PageImpl<>(toDto(reservations.getContent()), reservations.getPageable(), reservations.getTotalElements());
    }

}
