package net.omisoft.hotel.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rooms")
@EqualsAndHashCode(callSuper = true, exclude = {"number", "type", "reservations"})
@ToString(callSuper = true, exclude = {"reservations"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity {

    @Column(name = "number", nullable = false, unique = true)
    private long number;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RoomType type;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    public Set<Reservation> reservations;

}
