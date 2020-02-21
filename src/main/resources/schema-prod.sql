use `championship`;

CREATE TABLE `RESERVATION` (
  `UID` varchar(255) NOT NULL,
  `BOOKING_DATE` datetime DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `FULL_NAME` varchar(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL
);