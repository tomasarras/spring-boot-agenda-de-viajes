-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: g13
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `plan_viaje_tren`
--

DROP TABLE IF EXISTS `plan_viaje_tren`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_viaje_tren` (
  `asiento` int NOT NULL,
  `estacion` varchar(255) NOT NULL,
  `id_plan` int NOT NULL,
  PRIMARY KEY (`id_plan`),
  CONSTRAINT `FKkh7f7ecweun63o14cgc6whd8n` FOREIGN KEY (`id_plan`) REFERENCES `plan` (`id_plan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_viaje_tren`
--

LOCK TABLES `plan_viaje_tren` WRITE;
/*!40000 ALTER TABLE `plan_viaje_tren` DISABLE KEYS */;
INSERT INTO `plan_viaje_tren` VALUES (30,'Estacion 3',978),(24,'Estacion 6',985),(21,'Estacion 5',994),(7,'Estacion 3',997),(7,'Estacion 5',1003),(24,'Estacion 2',1006),(2,'Estacion 3',1010),(30,'Estacion 2',1013),(4,'Estacion 1',1021),(13,'Estacion 2',1024),(12,'Estacion 1',1030),(14,'Estacion 1',1033),(12,'Estacion 5',1035),(1,'Estacion 8',1042),(14,'Estacion 6',1048),(4,'Estacion 6',1053),(11,'Estacion 5',1056),(19,'Estacion 6',1062),(22,'Estacion 4',1065),(27,'Estacion 3',1073),(10,'Estacion 2',1079),(30,'Estacion 5',1090),(16,'Estacion 6',1092),(27,'Estacion 2',1095),(27,'Estacion 3',1107),(23,'Estacion 5',1110),(12,'Estacion 1',1112),(28,'Estacion 5',1114),(3,'Estacion 2',1116),(17,'Estacion 8',1118),(17,'Estacion 7',1126),(20,'Estacion 3',1132),(29,'Estacion 3',1138),(8,'Estacion 4',1140),(10,'Estacion 7',1143),(26,'Estacion 4',1148),(23,'Estacion 1',1152),(25,'Estacion 2',1157),(29,'Estacion 6',1164),(25,'Estacion 3',1169),(25,'Estacion 7',1173),(2,'Estacion 3',1176),(11,'Estacion 8',1179),(6,'Estacion 7',1182),(17,'Estacion 4',1185),(8,'Estacion 1',1190),(16,'Estacion 5',1194),(1,'Estacion 3',1197),(11,'Estacion 8',1200),(2,'Estacion 1',1208),(12,'Estacion 3',1211),(17,'Estacion 4',1214),(8,'Estacion 5',1219),(23,'Estacion 3',1222),(10,'Estacion 8',1227),(17,'Estacion 3',1233),(11,'Estacion 6',1238),(21,'Estacion 7',1248),(29,'Estacion 4',1253),(20,'Estacion 1',1256),(18,'Estacion 1',1260),(13,'Estacion 3',1268),(5,'Estacion 7',1275),(6,'Estacion 6',1282),(28,'Estacion 1',1287),(3,'Estacion 7',1289),(17,'Estacion 3',1291),(5,'Estacion 5',1295),(4,'Estacion 2',1300),(9,'Estacion 1',1303),(7,'Estacion 4',1306),(17,'Estacion 4',1309),(6,'Estacion 3',1314),(14,'Estacion 2',1317),(4,'Estacion 4',1320),(11,'Estacion 2',1323),(8,'Estacion 2',1325),(26,'Estacion 3',1328),(23,'Estacion 5',1331),(5,'Estacion 3',1337),(23,'Estacion 3',1340),(16,'Estacion 5',1344),(20,'Estacion 8',1347),(13,'Estacion 1',1359),(17,'Estacion 2',1364),(16,'Estacion 4',1371),(19,'Estacion 8',1375),(30,'Estacion 2',1377),(5,'Estacion 1',1382),(30,'Estacion 6',1384),(23,'Estacion 7',1387),(28,'Estacion 5',1391),(8,'Estacion 6',1397),(20,'Estacion 1',1402),(17,'Estacion 8',1406),(17,'Estacion 5',1409),(20,'Estacion 4',1417),(18,'Estacion 8',1421),(11,'Estacion 2',1425),(12,'Estacion 5',1429),(19,'Estacion 1',1434),(15,'Estacion 7',1437),(17,'Estacion 3',1442),(27,'Estacion 3',1450),(7,'Estacion 7',1453),(14,'Estacion 1',1455),(17,'Estacion 6',1459),(16,'Estacion 1',1463),(29,'Estacion 4',1466),(20,'Estacion 6',1469),(10,'Estacion 8',1473),(19,'Estacion 2',1481),(11,'Estacion 6',1484),(2,'Estacion 7',1486),(8,'Estacion 1',1488),(27,'Estacion 2',1491),(11,'Estacion 3',1493),(26,'Estacion 8',1496),(19,'Estacion 3',1501),(16,'Estacion 2',1504),(6,'Estacion 6',1511),(8,'Estacion 5',1514),(7,'Estacion 4',1516),(30,'Estacion 6',1519),(17,'Estacion 8',1525),(11,'Estacion 3',1527),(25,'Estacion 5',1530),(17,'Estacion 3',1533),(10,'Estacion 5',1537),(11,'Estacion 3',1542),(17,'Estacion 3',1544),(28,'Estacion 3',1549),(15,'Estacion 1',1552),(22,'Estacion 1',1555),(11,'Estacion 1',1557),(25,'Estacion 6',1560),(10,'Estacion 1',1566),(22,'Estacion 4',1574),(25,'Estacion 8',1577),(5,'Estacion 7',1581),(28,'Estacion 5',1584),(9,'Estacion 7',1588),(8,'Estacion 7',1593),(26,'Estacion 2',1597),(25,'Estacion 6',1603),(2,'Estacion 3',1605),(24,'Estacion 5',1610),(22,'Estacion 6',1613),(28,'Estacion 6',1616),(15,'Estacion 6',1621),(17,'Estacion 4',1625),(20,'Estacion 7',1634),(5,'Estacion 7',1643),(2,'Estacion 1',1647),(29,'Estacion 1',1649),(4,'Estacion 4',1652),(4,'Estacion 4',1665),(23,'Estacion 1',1669),(27,'Estacion 2',1672),(22,'Estacion 8',1676),(7,'Estacion 2',1679),(7,'Estacion 7',1681),(25,'Estacion 4',1686),(5,'Estacion 1',1689),(30,'Estacion 1',1692),(12,'Estacion 1',1694),(4,'Estacion 8',1697),(6,'Estacion 1',1699),(7,'Estacion 2',1704),(24,'Estacion 7',1707),(24,'Estacion 7',1709),(10,'Estacion 2',1716),(25,'Estacion 3',1725),(8,'Estacion 1',1729),(26,'Estacion 5',1732),(8,'Estacion 2',1742),(15,'Estacion 5',1747),(13,'Estacion 4',1750),(24,'Estacion 2',1754),(6,'Estacion 8',1761),(12,'Estacion 5',1767),(15,'Estacion 4',1772),(29,'Estacion 4',1776),(19,'Estacion 2',1781),(1,'Estacion 1',1785),(23,'Estacion 3',1789),(3,'Estacion 4',1792),(19,'Estacion 2',1800),(7,'Estacion 5',1802),(3,'Estacion 1',1805),(25,'Estacion 6',1808),(3,'Estacion 5',1814),(9,'Estacion 3',1817),(2,'Estacion 6',1822),(16,'Estacion 4',1828),(27,'Estacion 3',1831),(8,'Estacion 8',1835),(8,'Estacion 8',1840),(8,'Estacion 4',1845),(15,'Estacion 5',1849),(27,'Estacion 1',1851),(6,'Estacion 2',1854),(25,'Estacion 2',1857),(20,'Estacion 1',1860),(23,'Estacion 4',1862),(30,'Estacion 4',1865),(1,'Estacion 8',1879),(27,'Estacion 1',1886),(9,'Estacion 7',1889),(2,'Estacion 3',1894),(18,'Estacion 7',1899),(27,'Estacion 2',1902),(16,'Estacion 6',1906),(25,'Estacion 5',1909),(19,'Estacion 5',1912),(23,'Estacion 3',1915),(14,'Estacion 3',1919),(14,'Estacion 5',1923),(20,'Estacion 8',1926),(25,'Estacion 4',1929),(10,'Estacion 5',1932),(2,'Estacion 7',1935),(12,'Estacion 6',1939),(20,'Estacion 1',1941),(24,'Estacion 3',1947),(20,'Estacion 5',1952),(5,'Estacion 6',1958),(18,'Estacion 6',1961),(5,'Estacion 6',1967),(13,'Estacion 6',1972),(29,'Estacion 7',1974),(25,'Estacion 7',1976),(21,'Estacion 5',1981),(3,'Estacion 2',1991),(22,'Estacion 4',1994),(25,'Estacion 1',1996),(3,'Estacion 2',2002),(17,'Estacion 3',2007),(1,'Estacion 8',2011),(11,'Estacion 2',2017),(8,'Estacion 3',2019),(5,'Estacion 6',2023),(6,'Estacion 4',2027),(27,'Estacion 2',2031),(4,'Estacion 1',2034),(28,'Estacion 3',2037),(9,'Estacion 5',2039),(1,'Estacion 7',2041),(16,'Estacion 2',2044),(27,'Estacion 1',2046),(12,'Estacion 4',2050),(13,'Estacion 8',2056),(28,'Estacion 5',2059),(6,'Estacion 7',2064),(15,'Estacion 7',2069),(16,'Estacion 5',2078),(20,'Estacion 8',2081),(1,'Estacion 5',2086),(30,'Estacion 1',2089),(23,'Estacion 1',2092),(6,'Estacion 8',2096),(10,'Estacion 7',2100),(6,'Estacion 7',2103),(1,'Estacion 2',2110),(16,'Estacion 4',2117),(2,'Estacion 3',2121),(9,'Estacion 3',2124),(28,'Estacion 3',2127),(27,'Estacion 1',2131),(7,'Estacion 2',2135),(15,'Estacion 4',2138),(19,'Estacion 8',2141),(1,'Estacion 3',2144),(10,'Estacion 8',2149),(19,'Estacion 2',2152),(2,'Estacion 3',2160),(3,'Estacion 2',2162),(3,'Estacion 7',2165),(4,'Estacion 5',2168),(17,'Estacion 1',2172),(18,'Estacion 4',2174),(20,'Estacion 4',2178),(23,'Estacion 8',2182),(25,'Estacion 2',2187),(5,'Estacion 3',2193),(15,'Estacion 8',2196),(26,'Estacion 4',2199),(28,'Estacion 6',2203),(14,'Estacion 8',2206),(27,'Estacion 4',2208),(14,'Estacion 2',2212),(8,'Estacion 7',2218),(1,'Estacion 5',2222),(9,'Estacion 5',2224),(21,'Estacion 4',2228),(22,'Estacion 6',2232),(23,'Estacion 2',2235),(23,'Estacion 1',2240),(25,'Estacion 4',2244),(8,'Estacion 5',2248),(6,'Estacion 6',2252),(27,'Estacion 5',2264),(19,'Estacion 8',2268),(14,'Estacion 1',2273),(10,'Estacion 8',2278),(6,'Estacion 8',2283),(24,'Estacion 5',2292),(28,'Estacion 2',2300),(6,'Estacion 7',2307),(22,'Estacion 5',2313),(21,'Estacion 8',2318),(24,'Estacion 6',2321),(7,'Estacion 4',2327),(6,'Estacion 5',2331),(26,'Estacion 6',2333),(22,'Estacion 8',2336),(11,'Estacion 7',2338),(2,'Estacion 4',2347),(29,'Estacion 2',2351),(20,'Estacion 7',2355),(7,'Estacion 6',2358),(22,'Estacion 6',2360),(11,'Estacion 2',2363),(4,'Estacion 1',2365),(23,'Estacion 2',2367),(14,'Estacion 2',2369),(13,'Estacion 8',2372),(22,'Estacion 1',2374),(7,'Estacion 5',2378),(5,'Estacion 6',2383),(21,'Estacion 3',2386),(1,'Estacion 8',2390),(24,'Estacion 1',2394),(12,'Estacion 1',2397),(8,'Estacion 7',2408),(21,'Estacion 7',2413),(26,'Estacion 6',2415),(7,'Estacion 3',2421),(4,'Estacion 6',2424),(12,'Estacion 2',2426),(28,'Estacion 2',2428),(10,'Estacion 4',2431),(28,'Estacion 8',2435),(2,'Estacion 8',2442),(17,'Estacion 8',2446),(29,'Estacion 8',2452),(26,'Estacion 2',2456),(4,'Estacion 6',2461),(20,'Estacion 8',2464),(24,'Estacion 4',2472),(25,'Estacion 3',2480),(1,'Estacion 7',2483),(26,'Estacion 1',2489),(5,'Estacion 4',2492),(20,'Estacion 3',2499),(27,'Estacion 3',2501),(26,'Estacion 6',2506),(26,'Estacion 6',2508),(20,'Estacion 8',2523),(28,'Estacion 3',2526),(21,'Estacion 3',2529),(22,'Estacion 5',2532),(3,'Estacion 4',2536),(15,'Estacion 5',2539),(29,'Estacion 2',2541),(11,'Estacion 6',2545),(29,'Estacion 7',2550),(27,'Estacion 8',2552),(7,'Estacion 2',2555),(7,'Estacion 7',2559),(30,'Estacion 1',2565),(7,'Estacion 7',2568),(30,'Estacion 7',2573),(15,'Estacion 4',2576),(27,'Estacion 2',2579),(7,'Estacion 4',2581),(10,'Estacion 4',2585),(10,'Estacion 8',2587),(19,'Estacion 5',2596),(24,'Estacion 1',2600),(7,'Estacion 4',2603),(30,'Estacion 4',2607),(28,'Estacion 8',2611),(1,'Estacion 3',2613),(5,'Estacion 7',2615),(17,'Estacion 1',2617),(27,'Estacion 8',2622),(10,'Estacion 2',2624),(20,'Estacion 2',2628),(21,'Estacion 4',2632),(1,'Estacion 8',2635),(26,'Estacion 6',2638),(13,'Estacion 8',2644),(26,'Estacion 7',2648),(6,'Estacion 2',2652),(4,'Estacion 4',2659),(10,'Estacion 7',2662),(3,'Estacion 7',2668),(20,'Estacion 5',2673),(7,'Estacion 3',2679),(30,'Estacion 1',2683),(15,'Estacion 6',2688),(16,'Estacion 1',2694),(17,'Estacion 1',2700),(30,'Estacion 7',2703),(5,'Estacion 6',2709),(28,'Estacion 8',2714),(23,'Estacion 2',2722),(7,'Estacion 1',2726),(7,'Estacion 8',2731),(4,'Estacion 3',2737),(6,'Estacion 5',2749),(4,'Estacion 3',2752),(10,'Estacion 2',2758),(4,'Estacion 3',2760),(9,'Estacion 1',2765),(10,'Estacion 8',2768),(5,'Estacion 3',2772),(5,'Estacion 1',2778),(10,'Estacion 5',2781),(22,'Estacion 8',2784),(23,'Estacion 1',2787),(15,'Estacion 1',2791),(19,'Estacion 4',2794),(3,'Estacion 4',2803),(2,'Estacion 7',2810),(28,'Estacion 2',2812),(23,'Estacion 1',2816),(15,'Estacion 5',2819),(24,'Estacion 6',2823),(12,'Estacion 2',2825),(29,'Estacion 7',2829),(6,'Estacion 4',2833),(5,'Estacion 6',2837),(3,'Estacion 4',2842),(28,'Estacion 8',2849),(23,'Estacion 8',2851),(13,'Estacion 1',2855),(29,'Estacion 5',2859),(15,'Estacion 7',2862),(17,'Estacion 7',2868),(8,'Estacion 6',2874),(13,'Estacion 1',2877),(25,'Estacion 2',2879),(11,'Estacion 8',2883),(2,'Estacion 6',2889),(7,'Estacion 2',2894),(8,'Estacion 2',2898),(30,'Estacion 8',2903),(18,'Estacion 3',2907),(12,'Estacion 7',2909),(17,'Estacion 1',2912),(6,'Estacion 5',2915),(22,'Estacion 3',2919),(5,'Estacion 4',2923),(25,'Estacion 6',2927),(25,'Estacion 2',2940),(4,'Estacion 4',2943),(6,'Estacion 2',2950),(24,'Estacion 6',2952),(5,'Estacion 3',2955),(13,'Estacion 1',2961),(20,'Estacion 5',2964),(7,'Estacion 2',2967),(7,'Estacion 1',2969),(4,'Estacion 2',2974),(12,'Estacion 4',2977),(13,'Estacion 5',2980),(7,'Estacion 1',2984),(29,'Estacion 5',2987),(1,'Estacion 3',2990),(5,'Estacion 7',2995),(19,'Estacion 5',2997),(24,'Estacion 5',3001),(30,'Estacion 2',3006),(12,'Estacion 1',3008),(4,'Estacion 6',3013),(25,'Estacion 8',3018),(1,'Estacion 5',3020),(13,'Estacion 4',3024),(1,'Estacion 1',3027),(18,'Estacion 3',3029),(14,'Estacion 7',3033),(21,'Estacion 4',3038),(29,'Estacion 8',3042),(10,'Estacion 3',3044),(6,'Estacion 1',3048),(23,'Estacion 3',3050),(29,'Estacion 2',3052),(27,'Estacion 7',3056),(27,'Estacion 2',3060),(28,'Estacion 4',3068),(11,'Estacion 3',3073),(17,'Estacion 2',3078),(6,'Estacion 3',3082),(23,'Estacion 6',3092),(23,'Estacion 5',3094),(7,'Estacion 8',3108),(9,'Estacion 5',3114),(5,'Estacion 7',3123),(17,'Estacion 1',3126),(4,'Estacion 4',3129),(17,'Estacion 8',3132),(27,'Estacion 1',3135),(9,'Estacion 5',3148),(6,'Estacion 2',3153),(25,'Estacion 7',3156),(15,'Estacion 1',3162),(22,'Estacion 7',3166),(5,'Estacion 2',3171),(7,'Estacion 2',3174),(24,'Estacion 8',3177),(11,'Estacion 1',3179),(22,'Estacion 6',3182),(2,'Estacion 2',3185),(15,'Estacion 6',3188),(28,'Estacion 6',3192),(1,'Estacion 1',3197),(17,'Estacion 3',3200),(12,'Estacion 5',3206),(28,'Estacion 3',3213),(1,'Estacion 7',3219),(13,'Estacion 6',3222),(26,'Estacion 7',3229),(18,'Estacion 5',3235),(29,'Estacion 1',3239),(20,'Estacion 6',3244),(8,'Estacion 1',3249),(13,'Estacion 2',3254),(16,'Estacion 1',3258),(24,'Estacion 6',3262),(2,'Estacion 1',3264),(10,'Estacion 3',3268),(14,'Estacion 6',3271),(6,'Estacion 7',3277),(24,'Estacion 8',3286),(11,'Estacion 7',3293),(23,'Estacion 4',3296),(28,'Estacion 8',3300),(6,'Estacion 8',3309),(18,'Estacion 8',3313),(27,'Estacion 2',3317),(17,'Estacion 5',3319),(20,'Estacion 7',3323),(18,'Estacion 2',3327),(10,'Estacion 2',3329),(8,'Estacion 4',3340),(16,'Estacion 7',3343),(20,'Estacion 7',3346),(12,'Estacion 4',3349),(23,'Estacion 6',3353),(29,'Estacion 2',3357),(6,'Estacion 8',3360),(10,'Estacion 4',3364),(8,'Estacion 7',3371),(19,'Estacion 2',3376),(23,'Estacion 4',3380),(7,'Estacion 7',3383),(8,'Estacion 1',3388),(15,'Estacion 6',3394),(16,'Estacion 3',3399),(16,'Estacion 5',3403),(3,'Estacion 4',3409),(7,'Estacion 7',3412),(20,'Estacion 3',3415),(27,'Estacion 1',3418),(1,'Estacion 3',3421),(22,'Estacion 8',3431),(5,'Estacion 4',3436),(11,'Estacion 4',3444),(1,'Estacion 2',3449),(21,'Estacion 8',3454),(2,'Estacion 7',3458),(11,'Estacion 1',3462),(21,'Estacion 8',3474),(30,'Estacion 5',3478),(29,'Estacion 6',3482),(14,'Estacion 1',3485),(11,'Estacion 8',3493),(13,'Estacion 5',3499),(26,'Estacion 2',3506),(4,'Estacion 2',3509),(18,'Estacion 6',3513),(14,'Estacion 7',3516),(15,'Estacion 8',3521),(30,'Estacion 4',3527),(5,'Estacion 2',3530),(6,'Estacion 3',3533),(26,'Estacion 5',3536),(28,'Estacion 2',3540),(7,'Estacion 5',3543),(18,'Estacion 1',3551),(19,'Estacion 5',3554),(13,'Estacion 3',3559),(10,'Estacion 4',3563),(28,'Estacion 6',3569),(18,'Estacion 2',3573),(23,'Estacion 6',3576);
/*!40000 ALTER TABLE `plan_viaje_tren` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-16 20:35:58
