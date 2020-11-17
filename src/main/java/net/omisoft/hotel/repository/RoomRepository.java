package net.omisoft.hotel.repository;

import net.omisoft.hotel.model.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByNumber(long number);

    @Query("SELECT r FROM Room AS r")
    Slice<Room> getAll(Pageable pageable);

}
