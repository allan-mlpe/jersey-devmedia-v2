CREATE DATABASE IF NOT EXISTS `db_lembrete`;
USE `db_lembrete`;

CREATE TABLE `lembrete` (
  `ID_NOTA` int(11) NOT NULL,
  `TITULO` varchar(100) NOT NULL,
  `DESCRICAO` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_NOTA`)
)