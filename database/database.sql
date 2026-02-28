-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cinema_db
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `film` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(150) NOT NULL,
  `genre` varchar(100) NOT NULL,
  `duree` int NOT NULL,
  `realisateur` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `film_chk_1` CHECK ((`duree` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `film`
--

LOCK TABLES `film` WRITE;
/*!40000 ALTER TABLE `film` DISABLE KEYS */;
INSERT INTO `film` VALUES (1,'Inception','Science Fiction',148,'Christopher Nolan'),(2,'Titanic','Romance',195,'James Cameron'),(3,'The Dark Knight','Action',152,'Christopher Nolan'),(4,'Avatar','Science Fiction',162,'James Cameron'),(5,'Pulp Fiction','Crime',154,'Quentin Tarantino'),(6,'Interstellar','Science Fiction',169,'Christopher Nolan'),(7,'Gladiator','Action',155,'Ridley Scott'),(8,'Forrest Gump','Drame',142,'Robert Zemeckis');
/*!40000 ALTER TABLE `film` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salle`
--

DROP TABLE IF EXISTS `salle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `capacite` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nom` (`nom`),
  CONSTRAINT `salle_chk_1` CHECK ((`capacite` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salle`
--

LOCK TABLES `salle` WRITE;
/*!40000 ALTER TABLE `salle` DISABLE KEYS */;
INSERT INTO `salle` VALUES (1,'Salle A',100),(2,'Salle B',80),(3,'Salle IMAX',150),(4,'Salle 3D',120),(5,'Salle VIP',50);
/*!40000 ALTER TABLE `salle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seance`
--

DROP TABLE IF EXISTS `seance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `film_id` int NOT NULL,
  `salle_id` int NOT NULL,
  `date_projection` datetime NOT NULL,
  `prix` decimal(8,2) NOT NULL,
  `tickets_vendus` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_seance` (`salle_id`,`date_projection`),
  KEY `fk_film` (`film_id`),
  CONSTRAINT `fk_film` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_salle` FOREIGN KEY (`salle_id`) REFERENCES `salle` (`id`) ON DELETE CASCADE,
  CONSTRAINT `seance_chk_1` CHECK ((`prix` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seance`
--

LOCK TABLES `seance` WRITE;
/*!40000 ALTER TABLE `seance` DISABLE KEYS */;
INSERT INTO `seance` VALUES (1,1,1,'2026-03-01 18:00:00',50.00,20),(2,2,2,'2026-03-02 20:00:00',60.00,30),(3,3,3,'2026-03-03 19:00:00',75.00,45),(4,4,4,'2026-03-04 21:00:00',70.00,35),(5,5,5,'2026-03-05 17:00:00',55.00,15),(6,6,1,'2026-03-06 20:30:00',65.00,25),(7,7,2,'2026-03-07 18:30:00',60.00,28),(8,8,3,'2026-03-08 22:00:00',80.00,42),(9,1,2,'2026-04-01 18:00:00',50.00,22),(10,2,3,'2026-04-02 20:00:00',65.00,33),(11,3,4,'2026-04-03 19:00:00',70.00,40),(12,4,5,'2026-04-04 21:00:00',60.00,18),(13,5,1,'2026-04-05 17:00:00',55.00,20),(14,6,2,'2026-04-06 20:30:00',65.00,27),(15,7,3,'2026-04-07 18:30:00',75.00,38),(16,8,4,'2026-04-08 22:00:00',70.00,35),(17,1,3,'2026-05-01 18:00:00',65.00,30),(18,2,4,'2026-05-02 20:00:00',60.00,25),(19,3,5,'2026-05-03 19:00:00',55.00,12),(20,4,1,'2026-05-04 21:00:00',70.00,32),(21,5,2,'2026-05-05 17:00:00',60.00,24),(22,6,3,'2026-05-06 20:30:00',75.00,41),(23,7,4,'2026-05-07 18:30:00',65.00,29),(24,8,5,'2026-05-08 22:00:00',70.00,33),(25,1,1,'2026-06-01 20:00:00',65.00,30),(26,2,2,'2026-06-02 18:30:00',60.00,25),(27,3,3,'2026-06-03 21:00:00',75.00,40),(28,4,4,'2026-06-04 19:00:00',70.00,35),(29,5,5,'2026-06-05 20:30:00',55.00,20),(30,6,1,'2026-06-06 18:00:00',65.00,28),(31,7,2,'2026-06-07 22:00:00',60.00,32),(32,8,3,'2026-06-08 17:30:00',80.00,45),(33,1,4,'2026-07-01 20:00:00',65.00,25),(34,2,5,'2026-07-02 18:30:00',60.00,30),(35,3,1,'2026-07-03 21:00:00',75.00,38),(36,4,2,'2026-07-04 19:00:00',70.00,42),(37,5,3,'2026-07-05 20:30:00',55.00,22),(38,6,4,'2026-07-06 18:00:00',65.00,35),(39,7,5,'2026-07-07 22:00:00',60.00,28),(40,8,1,'2026-07-08 17:30:00',80.00,40);
/*!40000 ALTER TABLE `seance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'chaimaazakia','$2a$10$SoWDYziV5e6.jkCWgNKfnOi5t5O1/TMgRR8EoJ1b3PXQ59sTotNaO');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-28 17:17:11
