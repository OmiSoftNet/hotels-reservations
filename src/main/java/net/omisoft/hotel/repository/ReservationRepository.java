package net.omisoft.hotel.repository;

import net.omisoft.hotel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT CASE WHEN COUNT(r.id) > 0 THEN true ELSE false END FROM Reservation AS r WHERE r.room.id = :idRoom AND ((:start >= r.startDate AND :start < r.endDate ) OR ( :end > r.startDate AND :end <= r.endDate) OR (:start < r.startDate AND :end > r.endDate))")
    boolean existsByRoomIdAndDatePeriod(@Param("idRoom") long idRoom, @Param("start") LocalDate start, @Param("end") LocalDate end);

}
