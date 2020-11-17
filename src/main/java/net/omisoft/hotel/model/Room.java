package net.omisoft.hotel.model;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Immutable
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
    @LazyCollection(value = LazyCollectionOption.EXTRA)
    public Set<Reservation> reservations;

}
