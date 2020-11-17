package net.omisoft.hotel.mapper;

import net.omisoft.hotel.dto.room.RoomCreateDto;
import net.omisoft.hotel.dto.room.RoomDto;
import net.omisoft.hotel.model.Room;
import net.omisoft.hotel.model.RoomType;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

public final class RoomMapper {

    public static Room toEntity(RoomCreateDto dto) {
        return dto == null ? null : Room.builder()
                .number(dto.getNumber())
                .type(RoomType.valueOf(dto.getType()))
                .build();
    }

    public static RoomDto toDto(Room room) {
        return room == null ? null : RoomDto.builder()
                .id(room.getId())
                .number(room.getNumber())
                .type(room.getType().name())
                .build();
    }

    public static List<RoomDto> toDto(List<Room> rooms) {
        if (rooms == null) {
            return null;
        }
        List<RoomDto> result = new ArrayList<>(rooms.size());
        for (Room room : rooms) {
            result.add(toDto(room));
        }
        return result;
    }

    public static Slice<RoomDto> toDto(Slice<Room> rooms) {
        if (rooms == null) {
            return null;
        }
        return new SliceImpl<>(toDto(rooms.getContent()), rooms.getPageable(), rooms.hasNext());
    }

}
