-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: socialmediaapp
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `senderId` bigint NOT NULL,
  `recieverId` bigint NOT NULL,
  `messageSend` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  `seen` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,1,5,'Heelo','2025-03-09 04:51:16','2025-03-09 04:51:16',0),(2,1,5,'Hi','2025-03-09 04:51:21','2025-03-09 04:51:21',0),(3,1,5,'Bye','2025-03-09 04:51:24','2025-03-09 04:51:24',0),(4,2,1,'Hello','2025-03-09 05:25:10','2025-03-09 05:25:10',0),(5,2,1,'Hi','2025-03-09 05:25:12','2025-03-09 05:25:12',0),(6,2,1,'TeaTea','2025-03-09 05:25:17','2025-03-09 05:25:17',0),(7,2,14,'Hello','2025-03-09 05:28:20','2025-03-09 05:28:20',0),(8,2,14,'Tinder','2025-03-09 05:28:24','2025-03-09 05:28:24',0),(9,2,14,'Pikeman','2025-03-09 05:28:28','2025-03-09 05:28:28',0),(10,14,1,'Hello','2025-03-09 05:30:38','2025-03-09 05:30:38',0),(11,14,1,'Hi','2025-03-09 05:30:41','2025-03-09 05:30:41',0),(12,14,1,'BBye','2025-03-09 05:30:47','2025-03-09 05:30:47',0),(13,14,1,'Kinder','2025-03-09 05:30:51','2025-03-09 05:30:51',0),(14,14,2,'Hey','2025-03-09 05:33:02','2025-03-09 05:33:02',0),(15,14,2,'How are u?','2025-03-09 05:33:07','2025-03-09 05:33:07',0),(16,14,2,'Kinked','2025-03-09 05:33:13','2025-03-09 05:33:13',0),(17,1,2,'Hey','2025-03-13 12:51:34','2025-03-13 12:51:34',0),(18,1,2,'I wanna come to your place.','2025-03-13 12:51:44','2025-03-13 12:51:44',0),(19,1,2,'For tonigh we can have some fun.','2025-03-13 12:51:55','2025-03-13 12:51:55',0),(20,5,1,'Teracotta.','2025-03-14 08:27:40','2025-03-14 08:27:40',0),(21,5,1,'Tanks','2025-03-14 08:27:45','2025-03-14 08:27:45',0),(22,5,1,'Pinkball','2025-03-14 08:27:53','2025-03-14 08:27:53',0),(23,6,1,'Hi Inderneel how are you nowadays?','2025-03-15 17:13:53','2025-03-15 17:13:53',0);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-17 22:54:37
