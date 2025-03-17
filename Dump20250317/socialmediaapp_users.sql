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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` text NOT NULL,
  `last_name` text NOT NULL,
  `email` text NOT NULL,
  `password` text NOT NULL,
  `ip_address` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL,
  `is_verified` int NOT NULL DEFAULT '-1',
  `email_verificationHash` text NOT NULL,
  `role` int NOT NULL DEFAULT '0' COMMENT '0 for users and 1 for admin',
  `disabled` int NOT NULL DEFAULT '0',
  `blocked` int NOT NULL DEFAULT '0',
  `gender` int NOT NULL DEFAULT '0' COMMENT '-1 for LGBTQ 0 for male and 1 for female',
  `address` text,
  `status` text,
  `phone` text,
  `profile_photo` text,
  `last_login` timestamp NULL DEFAULT NULL,
  `profile_type` int NOT NULL DEFAULT '0' COMMENT '0 for public 1 for private.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Inderneel','Minhas','test123@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2024-11-09 17:03:07','2024-12-25 13:12:52',1,'89e4c29d489dbb66df0313c57696f2b72f7ff11b3a66499eb756841dc8c3cfd8',0,0,0,0,'India',NULL,'8130929768','images/profilePhotos/67dca4ea-d256-4b58-b49b-88d27988d7d7_2024-12-12_Photo.jpg','2025-03-16 16:12:59',1),(2,'Dani','Daniels','test234@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2024-12-08 09:58:22','2025-01-19 16:23:42',1,'5eb11cfdeb6ad227237f7ca1317143a980f45284b7f45db6476850eae2e75eae',0,0,0,0,'',NULL,'','images/profilePhotos/8b9f86eb-35bd-4588-809f-474402b252cc_2025-02-01_feed-2.jpg','2025-03-09 10:58:04',1),(3,'Mia','Khalifa','test345@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2024-12-08 10:00:13','2024-12-08 10:00:13',1,'5b1caa4e1c9e2de94a58d7cb9f4b73b66a4ee5b4934c6b3f376bf9ba7568b76a',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/52f33968-8c4b-4024-bc19-34aefa040c99_2025-02-01_profile-8.jpg','2025-03-05 16:37:54',1),(4,'Johnny','Depp','johnny@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2024-12-08 12:28:41','2024-12-08 12:28:41',1,'f12e0d50d3566ec47644fa82442a1704848f597e04a207e902c869f8e9381af9',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/69ef4269-94f6-4ace-b703-6187ceeac710_2025-02-01_profile-12.jpg','2025-03-02 06:38:26',1),(5,'Johnny','Mankoda','test567@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2024-12-08 12:53:57','2025-01-31 19:11:54',1,'097fea579d938cb53f235c4c43b7b4e0095dcc39377ee55f45763ff340dd6fde',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/6eaf2d21-50a7-41b4-9f9e-fdce8c54cce9_2025-02-01_story-6.jpg','2025-03-14 08:27:19',1),(6,'TestABC','IOPAS','test666@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-01-19 13:07:39','2025-02-01 10:48:07',1,'8c79467dc15062aaf07e376c094854094fad31d822bad9d375d256d170beac4b',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/91b66136-b626-4458-afe9-70e9ad03dee4_2025-02-01_profile-20.jpg','2025-03-15 17:10:21',1),(7,'OAPS','PAOIS','test888@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-01-19 13:08:32','2025-02-01 10:49:13',1,'b1cf6cdc0f4d50dda84cfb89bfd6f4da143df91c511b98ce68223abd62509c1f',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/0b2e54ab-7475-4d18-b388-928af601eae0_2025-02-01_profile-10.jpg','2025-02-05 13:28:43',0),(8,'ABCDEFGH','IJKLMNOPQ','test999@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-01-19 13:09:17','2025-02-01 10:50:37',1,'0481842d3b449ebfe7db55b827f26d22a8885f330b7056e6595cfc15b19cdc01',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/0441b4ff-eddc-496c-87dc-3471d61ff2ca_2025-02-01_profile-19.jpg','2025-02-05 13:29:03',0),(9,'POKI','MON','test00123@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-01-19 13:09:46','2025-02-01 10:54:43',1,'57a770735ccc477672bb04e778f93c692baf1ada5f6419728437e42d8e4cccb1',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/900c63fa-81b7-40ab-b361-3d3626928cdf_2025-02-01_story-3.jpg','2025-02-23 09:35:35',0),(10,'Pollos','Iopas','test9988@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-01-19 13:10:40','2025-02-01 10:57:27',1,'0cc35b870f29546c2af126d1779cd740db5fae60737fb37f284a206ac5aeef5c',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/fd647e12-b6b8-49e7-9ba8-944e3d51d211_2025-02-01_story-2.jpg','2025-02-05 13:30:01',0),(11,'FAN','IOPA','test1010@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-01-19 13:13:53','2025-02-01 10:58:39',1,'ddb8d1eee71d51a649909253ef87d0cb8e3f50671b23442c69054138032f18aa',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/6719937a-96bc-48ce-8c4c-ca9f5cf62a7e_2025-02-01_profile-7.jpg','2025-02-05 13:30:24',0),(12,'PUTTY','RIYADH','test332@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-01-19 13:14:58','2025-02-01 11:02:20',1,'759f82df1a08810f41f92115591fd784d0a2bde78bf48f73458be00b23e02d93',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/bf9d4304-c590-42f9-8c21-e5d85daea7d3_2025-02-01_profile-4.jpg','2025-03-02 07:07:54',0),(13,'Tina','Sera','test448@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-02-08 10:00:23','2025-02-08 10:00:45',1,'f24e37ec88968b59da14ec1cd344240627a6684e540c34b7b3a3ae277ac0cc00',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/8966e322-7ee7-438f-99cd-8bb4e0495e37_2025-02-08_profile-16.jpg','2025-03-02 06:40:14',0),(14,'jimmmy','carter','test9990@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-02-08 10:02:34','2025-02-08 10:02:49',1,'6b184bed9313f0dceab756cb10e77bc825b1d2a93ecfc0febbc7affa3418711c',0,0,0,0,NULL,NULL,NULL,'images/profilePhotos/1a0d5326-d18b-4fd4-90d3-d5e4e15e95a6_2025-02-08_feed-7.jpg','2025-03-09 11:02:50',0),(15,'assdada','ADAAsd','test1992@yopmail.com','f6bb1e14ff95d9c116707f983a6e66d3e9d1fe85a95b4e0ca6209e6d22d6b41b','0:0:0:0:0:0:0:1','2025-02-23 09:37:05','2025-02-23 09:37:22',1,'36f0b04a35a6434c0a6e112826e9fbb291493bc628e4eee3860e198a74bb1c64',0,0,0,0,NULL,NULL,NULL,NULL,'2025-02-23 09:37:40',0),(16,'testsdd','dsffs','test88819@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-02-23 10:30:56','2025-02-23 10:31:16',1,'e6a36b047f1cad3bddb0c44ed0ffc3dc0799a80f23e28c89af6a03e41b2a01b7',0,0,0,0,NULL,NULL,NULL,NULL,'2025-02-23 10:31:34',0),(17,'sdffsf','dfsfsf','test098@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-02-23 14:38:31','2025-02-23 14:38:51',1,'4847e3d21d3d0f4b06a4a7bb196ed03c17a53935d15b205282c060b336038b6a',0,0,0,0,NULL,NULL,NULL,NULL,NULL,0),(18,'Abcdefgh','Ijklmnopq','90012@yopmail.com','5a5d3e1115b0bae8e32a610d20390f818a5ec81c90f6cf8c1a0be1b3c626b975','0:0:0:0:0:0:0:1','2025-03-13 10:41:37','2025-03-13 10:42:44',1,'da85864ef920de5fee7c2e4219fc4a7db2102b813bee5d5955bed3cba86b307c',0,0,0,0,NULL,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-17 22:54:36
