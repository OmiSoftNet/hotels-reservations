-- liquibase formatted sql

-- changeset Fedir Pyshnyi:create_table_rooms dbms:mysql
CREATE TABLE `rooms`
(
    `id`            INT UNSIGNED                                                     NOT NULL AUTO_INCREMENT,
    `number`        INT UNSIGNED                                                     NOT NULL,
    `type`          ENUM ('STANDARD', 'BUSINESS', 'PRESIDENT')                       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `number` (`number`),
    CONSTRAINT `check_room_number` CHECK (`number` > 0 )
) ENGINE = InnoDB;

-- changeset Fedir Pyshnyi:create_table_reservations dbms:mysql
CREATE TABLE `reservations`
(
    `id`            INT UNSIGNED                                                     NOT NULL AUTO_INCREMENT,
    `id_room`       INT UNSIGNED                                                     NOT NULL,
    `first_name`    VARCHAR(80)                                                      NOT NULL,
    `last_name`     VARCHAR(80)                                                      NOT NULL,
    `start_date`    DATE                                                             NOT NULL,
    `end_date`      DATE                                                             NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `check_reservation_date` CHECK (`end_date` > `start_date`),
    CONSTRAINT `reservations_room` FOREIGN KEY (`id_room`) REFERENCES `rooms` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB;
