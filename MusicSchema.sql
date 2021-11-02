SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `music`;
CREATE DATABASE IF NOT EXISTS `music` ;
USE `music`;

CREATE TABLE IF NOT EXISTS `SONG` (
  `song_id` int NOT NULL,
  `length` int DEFAULT NULL,
  `release_year` year(4) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`song_id`));
  
  CREATE TABLE IF NOT EXISTS `ALBUM` (
  `album_id` int NOT NULL,
  `album_name` varchar(45) NOT NULL,
  `song_id` int DEFAULT NULL,
  PRIMARY KEY (`album_id`),
  KEY `song_id_idx` (`song_id`),
  CONSTRAINT `fk_song_id` FOREIGN KEY (`song_id`) REFERENCES `SONG` (`song_id`) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS `ARTIST` (
  `artist_id` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  PRIMARY KEY (`artist_id`));

CREATE TABLE IF NOT EXISTS `GENRE` (
  `genre_id` int NOT NULL,
  `genre_name` varchar(45) NOT NULL,
  PRIMARY KEY (`genre_id`));

CREATE TABLE IF NOT EXISTS `ARTIST_ALBUM` (
  `artist_id` int NOT NULL,
  `album_id` int NOT NULL,
  PRIMARY KEY (`album_id`,`artist_id`),
  KEY `a_artist_id_idx` (`artist_id`),
  CONSTRAINT `fk_album_id_aa` FOREIGN KEY (`album_id`) REFERENCES `ALBUM` (`album_id`) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `fk_artist_id_aa` FOREIGN KEY (`artist_id`) REFERENCES `ARTIST` (`artist_id`) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS `GENRE_SONG` (
  `song_id` int NOT NULL,
  `genre_id` int NOT NULL,
  PRIMARY KEY (`song_id`,`genre_id`),
  KEY `id_genre_idx` (`genre_id`),
  CONSTRAINT `fk_genre_id_gs` FOREIGN KEY (`genre_id`) REFERENCES `GENRE` (`genre_id`) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `fk_song_id_gs` FOREIGN KEY (`song_id`) REFERENCES `SONG` (`song_id`) ON UPDATE CASCADE ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS `SONG_ARTIST` (
  `song_id` int NOT NULL,
  `artist_id` int NOT NULL,
  PRIMARY KEY (`song_id`,`artist_id`),
  KEY `artist_id_idx` (`artist_id`),
  CONSTRAINT `fk_artist_id_sa` FOREIGN KEY (`artist_id`) REFERENCES `ARTIST` (`artist_id`) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `fk_song_id_sa` FOREIGN KEY (`song_id`) REFERENCES `SONG` (`song_id`) ON UPDATE CASCADE ON DELETE CASCADE);

INSERT INTO ARTIST (artist_id, first_name, last_name) VALUES 
(1, 'MICHAEL', 'JACKSON'),
(2, 'LADY', 'GAGA'),
(3, 'BRITNEY', 'SPEARS'),
(4, 'JUSTIN', 'BIEBER'),
(5, 'JUSTIN', 'TIMBERLAKE'),
(6, 'JANET', 'JACKSON'),
(7, 'ARIANA', 'GRANDE'),
(8, 'SELENA', 'GOMEZ'),
(9, 'MILEY', 'CYRUS'),
(10, 'ELTON', 'JOHN');

INSERT INTO SONG (song_id, length, release_year, title) VALUES 
(1, 258, 1982, 'BEAT IT'),
(2, 358, 1982, 'THRILLER'),
(3, 238, 2008, 'POKER FACE'),
(4, 229, 2007, 'RADAR'),
(5, 234, 2012, 'ONE LOVE'),
(6, 300, 2002, 'CRY ME A RIVER'),
(7, 300, 1982, 'YOUNG LOVE'),
(8, 193, 2014, 'PROBLEM'),
(9, 188, 2014, 'LOVE YOU LIKE A LOVE SONG'),
(10, 229, 2013, 'WE CANT STOP'),
(11, 377, 1971, 'TINY DANCER'),
(12, 300, 2013, 'THRILLER');

INSERT INTO ALBUM (album_id, album_name, song_id) VALUES 
(1, 'THRILLER', 1),
(2, 'THE FAME', 3),
(3, 'CIRCUS', 4),
(4, 'BELIEVE', 5),
(5, 'JUSTIFIED', 6),
(6, 'JANET JACKSON', 7),
(7, 'MY EVERYTHING', 8),
(8, 'FOR YOU', 9),
(9, 'BANGERZ', 10),
(10, 'MADMAN ACROSS THE WATER', 11),
(11, 'THRILLER', 2),
(12, 'BELIEVE', 12);

INSERT INTO GENRE (genre_id, genre_name) VALUES 
(1, 'POP'),
(2, 'R&B'),
(3, 'ROCK');


INSERT INTO ARTIST_ALBUM ( artist_id, album_id) VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(1, 11);

INSERT INTO GENRE_SONG ( song_id, genre_id) VALUES 
(1, 1),
(1, 3),
(2, 1),
(2, 2),
(3, 1),
(4, 1),
(5, 1),
(6, 2),
(7, 1),
(7, 2),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(11,3),
(12, 1);


INSERT INTO SONG_ARTIST (song_id, artist_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 4),
(6, 5),
(7, 6),
(8, 7),
(9, 8),
(10, 9),
(11, 10),
(12, 4),
(12, 5);

SET FOREIGN_KEY_CHECKS=1;