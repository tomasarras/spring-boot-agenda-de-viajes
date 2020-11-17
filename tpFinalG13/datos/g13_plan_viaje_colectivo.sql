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
-- Table structure for table `plan_viaje_colectivo`
--

DROP TABLE IF EXISTS `plan_viaje_colectivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_viaje_colectivo` (
  `asiento` int NOT NULL,
  `id_plan` int NOT NULL,
  PRIMARY KEY (`id_plan`),
  CONSTRAINT `FK8odw0u5ltnqw9s7w4bl3bhbk0` FOREIGN KEY (`id_plan`) REFERENCES `plan` (`id_plan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_viaje_colectivo`
--

LOCK TABLES `plan_viaje_colectivo` WRITE;
/*!40000 ALTER TABLE `plan_viaje_colectivo` DISABLE KEYS */;
INSERT INTO `plan_viaje_colectivo` VALUES (28,972),(6,974),(24,979),(9,983),(29,986),(18,990),(6,995),(26,998),(1,1004),(22,1007),(22,1011),(11,1014),(17,1018),(23,1022),(20,1025),(1,1027),(25,1028),(22,1031),(14,1034),(17,1036),(3,1037),(29,1039),(27,1043),(25,1049),(3,1051),(26,1054),(4,1057),(14,1058),(18,1061),(23,1063),(9,1066),(29,1068),(18,1074),(29,1075),(4,1080),(25,1081),(6,1085),(20,1086),(18,1091),(16,1093),(12,1096),(5,1100),(7,1102),(18,1104),(6,1108),(29,1111),(16,1113),(23,1115),(7,1117),(2,1119),(27,1121),(10,1123),(24,1124),(6,1127),(14,1133),(22,1139),(18,1141),(29,1144),(6,1145),(14,1149),(26,1153),(3,1155),(20,1158),(29,1160),(3,1161),(13,1165),(14,1166),(25,1170),(2,1174),(2,1177),(6,1180),(1,1183),(16,1186),(9,1191),(16,1195),(30,1198),(6,1201),(11,1205),(21,1209),(4,1212),(1,1215),(24,1217),(11,1220),(25,1223),(22,1228),(3,1234),(11,1235),(23,1236),(3,1237),(29,1239),(14,1243),(15,1245),(13,1249),(4,1254),(5,1257),(22,1261),(19,1264),(3,1266),(3,1269),(6,1272),(8,1276),(22,1277),(11,1281),(29,1283),(12,1286),(11,1288),(8,1290),(23,1292),(19,1296),(2,1297),(4,1301),(30,1304),(8,1307),(19,1310),(8,1311),(17,1315),(22,1318),(16,1321),(28,1324),(19,1326),(5,1329),(28,1332),(28,1333),(3,1336),(9,1338),(18,1341),(17,1345),(15,1348),(28,1351),(27,1355),(25,1356),(4,1360),(24,1365),(6,1367),(28,1370),(5,1372),(2,1376),(10,1378),(18,1380),(2,1383),(9,1385),(17,1388),(9,1392),(11,1394),(15,1398),(3,1403),(9,1407),(7,1410),(12,1414),(22,1418),(23,1422),(6,1426),(30,1430),(1,1433),(21,1435),(15,1438),(7,1443),(13,1446),(3,1448),(9,1451),(6,1454),(24,1456),(14,1460),(15,1462),(17,1464),(16,1465),(9,1467),(8,1468),(14,1470),(23,1474),(8,1476),(24,1482),(20,1485),(23,1487),(29,1489),(23,1492),(15,1494),(15,1495),(16,1497),(6,1499),(29,1502),(14,1505),(9,1508),(29,1510),(28,1512),(23,1513),(29,1515),(3,1517),(30,1520),(8,1523),(29,1526),(7,1528),(21,1531),(19,1534),(18,1538),(18,1539),(3,1541),(26,1543),(2,1545),(19,1546),(16,1547),(13,1550),(24,1553),(19,1556),(11,1558),(16,1559),(30,1561),(12,1567),(24,1570),(29,1572),(3,1575),(3,1578),(17,1582),(2,1585),(9,1589),(19,1594),(28,1598),(13,1601),(4,1604),(16,1606),(2,1611),(3,1614),(27,1617),(10,1622),(5,1624),(22,1626),(5,1629),(23,1631),(16,1635),(1,1638),(7,1640),(4,1641),(12,1644),(7,1648),(29,1650),(12,1653),(18,1655),(2,1656),(30,1657),(19,1660),(19,1666),(30,1670),(10,1673),(28,1677),(12,1680),(2,1682),(9,1684),(18,1687),(16,1690),(3,1693),(14,1695),(16,1698),(16,1700),(24,1702),(9,1705),(16,1708),(28,1710),(13,1713),(29,1717),(16,1719),(11,1722),(4,1723),(11,1726),(6,1730),(21,1733),(15,1735),(29,1737),(22,1743),(12,1748),(10,1751),(18,1755),(30,1759),(30,1762),(16,1764),(3,1768),(16,1769),(3,1773),(20,1777),(1,1778),(25,1782),(3,1786),(19,1790),(11,1793),(28,1795),(7,1796),(30,1797),(10,1801),(16,1803),(22,1806),(17,1809),(29,1815),(12,1818),(15,1819),(9,1823),(21,1829),(5,1832),(21,1833),(30,1836),(6,1837),(28,1841),(28,1846),(15,1847),(19,1850),(3,1852),(19,1855),(14,1858),(27,1861),(20,1863),(22,1866),(16,1869),(10,1870),(28,1874),(2,1875),(18,1880),(10,1882),(8,1885),(7,1887),(4,1890),(23,1895),(18,1896),(1,1900),(18,1903),(6,1907),(7,1908),(22,1910),(9,1913),(3,1916),(6,1920),(13,1924),(24,1927),(5,1930),(10,1933),(14,1936),(21,1940),(11,1942),(23,1948),(4,1950),(28,1953),(15,1959),(30,1962),(20,1968),(19,1970),(30,1973),(12,1975),(26,1977),(17,1982),(20,1984),(13,1987),(12,1992),(6,1995),(21,1997),(29,2003),(9,2008),(8,2012),(18,2013),(21,2014),(5,2018),(15,2020),(22,2024),(1,2028),(9,2032),(12,2035),(5,2038),(15,2040),(28,2042),(10,2045),(22,2047),(8,2051),(16,2057),(12,2058),(2,2060),(21,2061),(13,2065),(26,2067),(10,2070),(6,2073),(14,2079),(29,2082),(14,2087),(29,2090),(18,2093),(25,2097),(2,2101),(22,2104),(16,2105),(5,2111),(16,2113),(29,2118),(15,2122),(2,2125),(7,2128),(10,2132),(18,2136),(21,2139),(7,2142),(19,2145),(16,2150),(11,2153),(22,2155),(30,2161),(29,2163),(15,2166),(19,2169),(12,2173),(30,2175),(9,2176),(29,2179),(8,2183),(10,2185),(4,2188),(27,2190),(27,2194),(27,2197),(18,2200),(15,2202),(9,2204),(8,2207),(7,2209),(27,2213),(8,2219),(18,2223),(17,2225),(6,2229),(27,2233),(7,2236),(30,2241),(12,2245),(1,2246),(12,2249),(29,2253),(14,2257),(30,2261),(13,2265),(19,2266),(30,2269),(16,2271),(6,2274),(3,2276),(17,2279),(11,2284),(5,2287),(25,2293),(15,2296),(9,2297),(29,2301),(22,2302),(12,2308),(26,2310),(10,2314),(3,2315),(29,2317),(4,2319),(3,2322),(12,2323),(19,2325),(19,2326),(8,2328),(27,2332),(3,2334),(30,2337),(20,2339),(16,2343),(5,2345),(27,2348),(14,2350),(18,2352),(10,2356),(20,2359),(23,2361),(10,2364),(9,2366),(19,2368),(26,2370),(7,2373),(30,2375),(11,2379),(15,2384),(28,2387),(23,2391),(17,2395),(14,2398),(27,2400),(26,2403),(10,2405),(25,2409),(28,2414),(14,2416),(19,2419),(30,2422),(15,2425),(10,2427),(6,2429),(16,2432),(9,2436),(3,2437),(12,2443),(24,2447),(16,2448),(16,2453),(16,2457),(12,2459),(2,2462),(20,2465),(4,2467),(23,2473),(11,2474),(7,2476),(24,2481),(2,2484),(22,2490),(5,2493),(19,2497),(12,2500),(20,2502),(1,2507),(25,2509),(20,2513),(16,2516),(3,2519),(28,2522),(13,2524),(5,2527),(2,2530),(10,2533),(16,2537),(11,2540),(4,2542),(29,2543),(2,2546),(4,2551),(10,2553),(9,2556),(28,2558),(14,2560),(23,2561),(4,2566),(19,2569),(28,2571),(17,2574),(25,2577),(11,2580),(26,2582),(25,2586),(13,2588),(22,2592),(24,2597),(25,2601),(8,2604),(14,2605),(30,2608),(14,2612),(17,2614),(22,2616),(3,2618),(5,2623),(30,2625),(21,2629),(13,2633),(20,2636),(25,2639),(15,2645),(25,2646),(19,2649),(18,2653),(11,2654),(19,2655),(7,2660),(8,2663),(14,2665),(1,2669),(4,2671),(8,2674),(20,2680),(25,2681),(28,2684),(24,2687),(25,2689),(21,2695),(17,2696),(15,2698),(19,2701),(1,2704),(15,2710),(28,2715),(21,2719),(18,2723),(20,2727),(27,2732),(19,2734),(9,2738),(8,2740),(24,2742),(8,2745),(18,2748),(3,2750),(15,2753),(3,2759),(28,2761),(9,2766),(1,2769),(26,2773),(1,2775),(8,2777),(20,2779),(2,2782),(3,2785),(17,2788),(5,2789),(24,2792),(6,2795),(14,2797),(14,2798),(24,2799),(24,2804),(16,2808),(8,2811),(23,2813),(19,2817),(9,2820),(11,2822),(24,2824),(8,2826),(30,2830),(27,2834),(26,2836),(22,2838),(3,2839),(21,2843),(10,2847),(3,2850),(6,2852),(5,2856),(27,2860),(30,2863),(26,2865),(23,2866),(12,2869),(19,2873),(20,2875),(6,2878),(12,2880),(2,2884),(17,2887),(14,2890),(24,2892),(5,2895),(7,2899),(11,2904),(8,2908),(19,2910),(4,2913),(30,2916),(3,2920),(18,2922),(17,2924),(12,2926),(21,2928),(10,2930),(16,2932),(19,2936),(17,2941),(12,2944),(8,2947),(23,2951),(16,2953),(4,2956),(8,2962),(27,2965),(20,2968),(13,2970),(12,2975),(23,2978),(30,2981),(27,2985),(29,2988),(30,2991),(2,2996),(4,2998),(9,3002),(28,3007),(7,3009),(11,3010),(4,3014),(12,3019),(27,3021),(14,3025),(22,3028),(19,3030),(7,3034),(14,3035),(5,3039),(20,3043),(12,3045),(7,3049),(25,3051),(8,3053),(22,3057),(28,3058),(4,3061),(26,3065),(29,3069),(1,3072),(27,3074),(20,3079),(28,3080),(19,3083),(15,3087),(25,3091),(13,3093),(12,3095),(27,3097),(19,3098),(3,3102),(6,3104),(13,3106),(22,3109),(6,3111),(24,3115),(14,3116),(18,3120),(15,3121),(11,3124),(29,3127),(25,3130),(10,3133),(3,3136),(5,3137),(8,3140),(26,3141),(12,3143),(14,3144),(13,3149),(5,3154),(20,3157),(20,3159),(22,3163),(13,3167),(22,3170),(30,3172),(24,3175),(10,3176),(20,3178),(2,3180),(25,3183),(24,3186),(14,3189),(4,3193),(4,3194),(12,3198),(28,3201),(14,3202),(28,3207),(27,3209),(3,3212),(18,3214),(22,3216),(5,3220),(29,3223),(1,3226),(10,3230),(6,3232),(25,3236),(13,3240),(30,3241),(26,3245),(5,3250),(7,3255),(23,3259),(30,3263),(12,3265),(8,3269),(20,3270),(14,3272),(23,3278),(19,3281),(18,3283),(26,3287),(8,3289),(25,3294),(30,3297),(10,3301),(21,3303),(26,3305),(23,3306),(7,3310),(25,3314),(21,3318),(17,3320),(17,3324),(30,3328),(12,3330),(14,3334),(25,3335),(23,3341),(26,3344),(27,3347),(10,3350),(27,3354),(16,3358),(26,3361),(27,3365),(21,3369),(26,3372),(20,3377),(13,3381),(2,3384),(24,3386),(5,3389),(16,3391),(26,3395),(26,3397),(11,3398),(24,3400),(28,3404),(19,3410),(19,3413),(21,3416),(20,3419),(6,3422),(14,3424),(24,3426),(19,3427),(13,3432),(2,3434),(29,3437),(28,3441),(26,3445),(17,3450),(30,3455),(21,3459),(3,3463),(16,3467),(26,3470),(12,3471),(17,3475),(10,3479),(30,3483),(7,3486),(13,3489),(11,3494),(8,3498),(10,3500),(26,3502),(29,3503),(26,3507),(5,3510),(30,3514),(23,3517),(4,3522),(29,3525),(26,3528),(25,3531),(18,3534),(29,3535),(16,3537),(12,3538),(14,3541),(13,3542),(4,3544),(6,3545),(1,3547),(27,3552),(20,3555),(8,3557),(23,3560),(2,3564),(26,3565),(5,3566),(19,3570),(6,3574),(27,3577);
/*!40000 ALTER TABLE `plan_viaje_colectivo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-16 20:35:57
