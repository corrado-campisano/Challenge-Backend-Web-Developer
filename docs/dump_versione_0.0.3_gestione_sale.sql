-- MariaDB dump 10.19  Distrib 10.11.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: challenge
-- ------------------------------------------------------
-- Server version	10.11.6-MariaDB-0+deb12u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `film`
--

DROP TABLE IF EXISTS `film`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `film` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `titolo` varchar(255) NOT NULL,
  `tecnologia_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb9vuqa9mtbcqy5sxqelr3s2mc` (`tecnologia_id`),
  CONSTRAINT `FKb9vuqa9mtbcqy5sxqelr3s2mc` FOREIGN KEY (`tecnologia_id`) REFERENCES `tecnologia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=437 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `film`
--

LOCK TABLES `film` WRITE;
/*!40000 ALTER TABLE `film` DISABLE KEYS */;
INSERT INTO `film` VALUES
(431,'Il dottor Stranamore',176),
(432,'2001: odissea nello spazio',176),
(433,'Barry Lyndon',176),
(434,'Arancia meccanica',176),
(435,'Full Metal Jacket',176),
(436,'Il dottor Stranamore',177);
/*!40000 ALTER TABLE `film` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proiezione`
--

DROP TABLE IF EXISTS `proiezione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proiezione` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fine_programmazione` date NOT NULL,
  `inizio_programmazione` date NOT NULL,
  `film_id` bigint(20) NOT NULL,
  `sala_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmb04irqgc4qpd9ytbd8nhky79` (`film_id`),
  KEY `FK7q1bj24ow0vppejvkd6a9m7nm` (`sala_id`),
  CONSTRAINT `FK7q1bj24ow0vppejvkd6a9m7nm` FOREIGN KEY (`sala_id`) REFERENCES `sala` (`id`),
  CONSTRAINT `FKmb04irqgc4qpd9ytbd8nhky79` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proiezione`
--

LOCK TABLES `proiezione` WRITE;
/*!40000 ALTER TABLE `proiezione` DISABLE KEYS */;
INSERT INTO `proiezione` VALUES
(106,'2025-03-31','2025-03-18',431,147),
(107,'2025-04-07','2025-03-25',432,148),
(108,'2025-04-07','2025-04-01',433,147),
(109,'2025-04-14','2025-03-25',434,149),
(110,'2025-04-14','2025-04-01',435,150);
/*!40000 ALTER TABLE `proiezione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sala`
--

DROP TABLE IF EXISTS `sala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sala` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `posti` bigint(20) DEFAULT NULL CHECK (`posti` <= 250 and `posti` >= 50),
  `tecnologia_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj4h9leecvv7q094ycsmiic7ss` (`tecnologia_id`),
  CONSTRAINT `FKj4h9leecvv7q094ycsmiic7ss` FOREIGN KEY (`tecnologia_id`) REFERENCES `tecnologia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sala`
--

LOCK TABLES `sala` WRITE;
/*!40000 ALTER TABLE `sala` DISABLE KEYS */;
INSERT INTO `sala` VALUES
(147,'sala 1',50,176),
(148,'sala 2',50,176),
(149,'sala 3',50,176),
(150,'sala 4',50,176);
/*!40000 ALTER TABLE `sala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tecnologia`
--

DROP TABLE IF EXISTS `tecnologia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tecnologia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tecnologia`
--

LOCK TABLES `tecnologia` WRITE;
/*!40000 ALTER TABLE `tecnologia` DISABLE KEYS */;
INSERT INTO `tecnologia` VALUES
(176,'IMAX'),
(177,'Dolby');
/*!40000 ALTER TABLE `tecnologia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'challenge'
--
