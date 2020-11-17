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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL,
  `admin` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,_binary '\0','cbrickwood0@histats.com','$2a$10$xt.vPOUxG5ocYJRIF6DLCOdqD1EC8XzJsYbtfaFrbVAzNpZxAkD.u','jwildish0'),(2,_binary '\0','dnardoni1@yellowbook.com','$2a$10$4NRJtUWLSQtlWsVxfPwz6uWaiGbhh4KuAKJQ1HtgOS12tyL3yJ516','cmcwilliam1'),(3,_binary '\0','frapi2@indiegogo.com','$2a$10$UeT/xI6yR25UjWwLe2rcN.P/xdQY.jpPkvgLjdJSu774J8CKmdiQy','pclemes2'),(4,_binary '\0','nmcewan3@hostgator.com','$2a$10$UB7cRcBwIzJjUYdi117tr.RLh8Fi5YAXQeiFzVlsqqj1u7PX/pRiG','sstquentin3'),(5,_binary '\0','verington4@blogspot.com','$2a$10$Wz31pECu2eiHfgyDY1StDudGFoynE4nPdwDZENbxmm6INIOfp98pK','fmarriner4'),(6,_binary '\0','wreihm5@imgur.com','$2a$10$9c5TnGEfpf33IgWGUX62ze6M/9Ml2rQnKpTzYUhktaxTqHfPjQCw.','jzima5'),(7,_binary '\0','cmeikle6@aboutads.info','$2a$10$5wRE3z9oRoMgcLTwymY0iuIJrE3kPvi6PU4E9rpnSGBy.97wmkpQS','kmeaker6'),(8,_binary '\0','fcolcutt7@soundcloud.com','$2a$10$DFPvlmPz9O2aT7ek8oyw3OTPmuBQaoYwlapCDfkxCsy.7eWKVsTr.','achaloner7'),(9,_binary '\0','kcuzen8@google.cn','$2a$10$cy3mqzJ1qdjBAOZpniC2c.LFeiGPMw.Xc5RRSX3gn6cZp2FombAoW','dbittleson8'),(10,_binary '\0','ccromarty9@blogs.com','$2a$10$phacGE9/1p5YRla.jrmPYeeEGuZVHHlfLQZW32pl5RY9VW09pJdKG','bbeckles9'),(11,_binary '\0','vstainfielda@boston.com','$2a$10$OzI4RWt9ZUJ0F7gKf6zojOrx46WYkEwUrawwKYNf56ZZii262IDuq','walbrechta'),(12,_binary '\0','kcrevaghb@who.int','$2a$10$/gNVZmwa5.n2INzDxVVqHOzbd35UQL5TGb/0FnUpn3xpNjLGtKltu','aantonettib'),(13,_binary '\0','fcussonsc@ed.gov','$2a$10$3fVoHUTU.mid2Q1vzaBVu.SaTWws1XJ5t8pi0oIKjnG5E/djDQrXG','cchamberlenc'),(14,_binary '\0','mtettleyd@buzzfeed.com','$2a$10$n3qKKOePqNhOozyEmaReLuS9PMDbTRQMcnx7cTuaDMPaZoY1DQUIi','vsarlld'),(15,_binary '\0','adunye@addthis.com','$2a$10$mT2ZWRZzioz5t.D/bBUzSOAWdJfPBL/WJ5QddhmCULFqasIneT.Lu','gmctiernane'),(16,_binary '\0','aboatwrightf@linkedin.com','$2a$10$oL1iMs3wq1WYZt9Wrl875uYkXACRLgEyKJsEoWHV8MjRdlSOvt6jO','eoruanef'),(17,_binary '\0','adenisardg@gov.uk','$2a$10$0HmX1RKKuT9h6oRMKTGXiO.s.j5GI/fl3RieV8Ft5VUtpshSgZ0S2','hvalenting'),(18,_binary '\0','icreaneh@bigcartel.com','$2a$10$A6H0BrcrxaHM6jb.2r/ieOFU2wtcIf44l//eO.f47/izleoV2HaiG','awildgooseh'),(19,_binary '\0','jstickellsi@tinypic.com','$2a$10$FN.C5Sf3yE9ZBE50VQcv4e14WIR2yqxNyyZsxIS/jKjFD21u6aez.','llishmani'),(20,_binary '\0','geckersallj@typepad.com','$2a$10$S.Mq8LngIsfBruxPwHndiuswvPgr.YL6JOrFOJfSdVu7OiLg0UP3G','slepruvostj'),(21,_binary '\0','yspieckk@xinhuanet.com','$2a$10$/VlU4PEjbeFSDQCw1drK5.aVfRcEn0/Sg6QPcYzOtlU6StX8lMMKa','wolenechank'),(22,_binary '\0','spendleburyl@buzzfeed.com','$2a$10$8A1GS9CuExyOfn.qSl8IeuGQAb2RejQgLbgCNdI7ztoz/GSIQFehW','sarondell'),(23,_binary '\0','bcurlm@google.com.br','$2a$10$kOYz/fARjeAE9kah/zIukOCVJf4uEvJ87Ezyd1vKQ83zrhgtizV3G','amcilhattonm'),(24,_binary '\0','bloraitn@hhs.gov','$2a$10$SzL3wyZe1fb4m1/J23QOJe.QilmE4TC/KiVg4IFTzgiVG/gxgInwO','dpetrushankon'),(25,_binary '\0','estoffelso@hp.com','$2a$10$X/AMumoAD/ll5VfBlivzZuE4JnUEq.jDyC.rqreT8MgxUYkPTTHRq','egronowo'),(26,_binary '\0','alurrimanp@cdbaby.com','$2a$10$mnaWvbVMa.fR4ijsxYTMtOqtbbtnLEOj11FatlHtot5qOd39MlUqy','spetrishchevp'),(27,_binary '\0','qsmeathq@bbb.org','$2a$10$wcYW8Xa14.OTPZ8ZvcXNoOZ3MgvDlZmBmTqut1uPwpd3qFnk1T2AO','lmaggioriq'),(28,_binary '\0','cnowakowskir@amazon.com','$2a$10$6kVkpUedRgtu0cnWjoBNceYrcp2r1q6/M96q1cAQnDZabHuWWF/NW','cvosser'),(29,_binary '\0','bjarrells@google.co.jp','$2a$10$5E7Tx3dcnv6dWbrnnGogrupyIuXO7yLtnLmjAxdmxMFFo4GZW5fNe','alowres'),(30,_binary '\0','bcarslaket@wiley.com','$2a$10$GK077nInREmXZTQNG0mzrO/00VKXefrH0XU9E6jjKLQwNwCsCjPbO','olorenzot'),(31,_binary '\0','glamondu@exblog.jp','$2a$10$H4WwAzzDAh8OPcfbIcHrG.VPQ5N8PrH7oWv5dY1wbt3kD.57Yl0tO','rcremeru'),(32,_binary '\0','wtootalv@sciencedaily.com','$2a$10$A.WXJ47GussojjiCUrv3QeiaR6Aoq5HkcXrkHfdoXGgYQzMcYpygG','kdacombev'),(33,_binary '\0','nkorneichikw@simplemachines.org','$2a$10$dcPdUKR4gFLnMkiU0RU1XexCJ8a3Fg.Ay5N7Z5WCaGo/gt097xdTW','aboydlew'),(34,_binary '\0','cjegerx@psu.edu','$2a$10$OVjjYi2tM3FCVPDx.W7KYODzO/VGjrnkR1Wuo1cz4NO9XGZWkdeDe','jcollidgex'),(35,_binary '\0','mfancyy@php.net','$2a$10$Nd.7awu6oUv3N4QeTATWQuLVzYaPI2ZqcQO1LKnoDOOgw5XxHhvMi','shaimey'),(36,_binary '\0','napplefordz@cmu.edu','$2a$10$mvUvABVxUJKZVZRnLB.zmuXVi.4xweKgtKPfdzJdEMWROpg1hZ8ni','tstittlez'),(37,_binary '\0','abufton10@joomla.org','$2a$10$069lC/Hya0eCl66AvyMLRegZVRtUtUnIfNKWuIETR6oJtjkp4JXuW','jcoltart10'),(38,_binary '\0','fzumbusch11@parallels.com','$2a$10$wogwvdv4XFOEIOGYsubXPOZeFooj.wxxqdG7kFro5fW20CDFTl8ji','jkelston11'),(39,_binary '\0','hjanning12@stumbleupon.com','$2a$10$pzDRWsxtnKG3/VxV0bhmLeb3glNnPDnkBGH.DZPBOP2vAHtAplrlK','cdeclercq12'),(40,_binary '\0','dgreenshiels13@netlog.com','$2a$10$C5FN8cmICRcx4Bp3Q5OPSOR226O2Kaq/O9.gBZmnHRaqmK7H9nIg.','kreddish13'),(41,_binary '\0','kolivetta14@toplist.cz','$2a$10$we6BynUnAIQsGYQsJT5TruXWxxrUWe9ug8FbAop7BiKhRLXOwXuX6','ahatrick14'),(42,_binary '\0','hlehr15@webeden.co.uk','$2a$10$8Bno7y1hVgGaTyNK1.deIO4D.FPD9miA8x/ze2HR3JL3rkVFRBRkG','ckelledy15'),(43,_binary '\0','fregenhardt16@lycos.com','$2a$10$NsC1zGjtYuKjsbX2Bq97VOl8uykofaSood3c39sy8ML2k4ytYb3AK','bballaam16'),(44,_binary '\0','scammidge17@mozilla.com','$2a$10$jMZBM7CJPCyYLPdlCepBn.rV1yJRq/89ECNQV2N54XWSO73NxGHRW','wandrichak17'),(45,_binary '\0','dlampart18@list-manage.com','$2a$10$DQTbzw67rEZ81GPXJcsb7u8vUyYYPB4f.cClr6f0xCfJ/nA4RxSc2','dcoode18'),(46,_binary '\0','afronek19@nba.com','$2a$10$S979K4Vqvb44wAzlks33.OF2bw0.QI6rFKpyoxIwcHBS0F8OGSZ9m','zpaling19'),(47,_binary '\0','rmcparlin1a@macromedia.com','$2a$10$a9Nuy2TkHOGzV.P5UhIiaOyA5cwIite2tYQkupkZpLp/CaCmrBU3O','nhacquoil1a'),(48,_binary '\0','pgilardi1b@shop-pro.jp','$2a$10$.1wICCBNJ7x5vi89ew1ou.7tnGbWmwwMq8UhlEp52zfw1xcW5Khpq','bklemencic1b'),(49,_binary '\0','tbanks1c@noaa.gov','$2a$10$YDyWL47GlkkQmY4xR5q5l.saXRxe6mxYD3ulNx7lxbowUpIlW1o5O','mblything1c'),(50,_binary '\0','creadshaw1d@odnoklassniki.ru','$2a$10$7m7wvK02h6mQiTBc.5T3yOoqZX36fzbW1Dvwzh4EzjjiWDu2tBu6y','bshadrack1d'),(51,_binary '\0','togbourne1e@google.es','$2a$10$RDpJdZ1rv2Hv.ph/a/P0i.3q3Cq6DwNzEQpzkw06M9ExJ.A0yPtXm','dstodit1e'),(52,_binary '\0','mreace1f@t-online.de','$2a$10$JMs7LHXqVPd.7KNUY/suxeRSnZL2KmD1K.9Z20LC86fv8skrZHmeC','rmatoshin1f'),(53,_binary '\0','bbrockett1g@hud.gov','$2a$10$bZdMy3XDoOzEgKNTxDuxoeEZGVhdb.PHNWZoHY4JJZbV7jhDGEmAG','lpascall1g'),(54,_binary '\0','rpietersen1h@fda.gov','$2a$10$TT9p2A6T.uA2d3KzUkvVSekWkPLbdN9vIzxR0jJ7XcLgy9pOSehPa','wmolfino1h'),(55,_binary '\0','cmcchruiter1i@adobe.com','$2a$10$2m5UYJ8KTWRuUB5wIdnW/OtpD0j58.KqH746I.HkjlypodtHVMieS','opoat1i'),(56,_binary '\0','gparadis1j@stanford.edu','$2a$10$GJLUV7Y4ErIHDRKpmWYoFOekWJtFQkoLTEiEN0iRn1EB9k1jbv23y','mmawne1j'),(57,_binary '\0','itregoning1k@mysql.com','$2a$10$gEQpsgiI7tKSpVyzXyGx8..ZCfWCTMrXQsb34BZvUyGRRsEf4Qt0O','eortler1k'),(58,_binary '\0','sshakspeare1l@biblegateway.com','$2a$10$bE9KpxDU516Ex3F28/EUV.qfI6Sk/SXIEHDy/j4rYm4oOD9XFing.','gstrodder1l'),(59,_binary '\0','chearnden1m@irs.gov','$2a$10$nwfAxNk8BlgGOwUxi6XgmOBqsyzixnVrWdY9Ht5mwZX2igbkxdrau','cfausch1m'),(60,_binary '\0','ecuddy1n@google.de','$2a$10$y3sUnvXNTrItL49I6erFZu4Ziy5SumcnHHXD149R0FAkLJ2b6AX7G','mmackibbon1n'),(61,_binary '\0','tyansons1o@jiathis.com','$2a$10$Iawauwvt7kcs5LZfMnahNufz1vfWyw4aASJJ/Wmvn1vudSCqzm2gy','tennor1o'),(62,_binary '\0','jeliasen1p@google.de','$2a$10$dYg3fN8SFaE..idcuyHFwuP3tArO1dZty.aIHiBMnk8ssfAbnPbkO','sandover1p'),(63,_binary '\0','mrenol1q@netvibes.com','$2a$10$7Bv6qGg.4ZM/bEIuA97BBOXXZofHBRboNB1XdRZ/qCAy7h/6kEKGu','rbottrill1q'),(64,_binary '\0','ctrenouth1r@wunderground.com','$2a$10$3czyAOoKFs7yYdvammoSROjLgK8byx5pRUqTkSh0DqqS2kYGqlwuC','gmaudling1r'),(65,_binary '\0','mhook1s@acquirethisname.com','$2a$10$OJh8oK1DgzI7Y.8n006ZVujduM83kRob8IL4mnC0FHDSLUiDm2rJK','ckinner1s'),(66,_binary '\0','jharpham1t@eepurl.com','$2a$10$8t3jyKxYNn3m/UA6IfD1CevyOYl6f4BrTmvIVUtOm0FP.1bSMroM6','bstiegers1t'),(67,_binary '\0','scoppledike1u@hexun.com','$2a$10$rk5gSms9JrmyEjRc1hcWHO3cgbFfwcbdEizJZPGLVNgXiRC0Lh3Ae','bmullan1u'),(68,_binary '\0','gfrangello1v@shop-pro.jp','$2a$10$5Vf7MPqzoyo6H7tqwDcwNOTgPamEDilMQGJ/Wc2wHKbaS767yBLnq','eborres1v'),(69,_binary '\0','dcaines1w@elegantthemes.com','$2a$10$qYTWHli0kytKg7BcSEqpee.Egk9yQWHXn8R/.n0Q05pYHDdJcTX2i','ahoulson1w'),(70,_binary '\0','lachrameev1x@godaddy.com','$2a$10$qJya4UG0kRa97eQVD1Aznu75BGktowMeMSVeC7SL2SHLZWjg5RbgC','gdeeble1x'),(71,_binary '\0','tjamison1y@yellowbook.com','$2a$10$8xB/ONx/ngYTF0WvEebbbO78HKQLGloBecz5wcXyrNl4snv/BER/S','cocurrine1y'),(72,_binary '\0','lallsebrook1z@wikimedia.org','$2a$10$yozoC9zx5RWuq/v7MzP4jeKMgrqpmauqa9m3gYzmFOo2uqNBPpt1e','ahurley1z'),(73,_binary '\0','remmerson20@mail.ru','$2a$10$PzFddUThQFPjG5l0liC0R.yBF7iP4ulzwFCn06o0IP64su8UAFaH.','brivitt20'),(74,_binary '\0','seydel21@ucsd.edu','$2a$10$kbh65KmORvNBn7/d6E8zS.FJFZzdNYVkari.GaQBXcX9sRhVAFvDS','tmechic21'),(75,_binary '\0','kfeild22@livejournal.com','$2a$10$IrDmr7jrsw3OZpIHtS/LZu8ywgz4TlDCoVQRB9slKOiB8.7lV2msG','fosanne22'),(76,_binary '\0','cwrightson23@wikipedia.org','$2a$10$ZFr5EHATRmojtPmt3rcWPuMVT.IdnHQ51w9mwu8g14TfnZeSjW0oe','kdjurkovic23'),(77,_binary '\0','fsymes24@huffingtonpost.com','$2a$10$gPtT8X/jwMvef8Pdv.abfuPr/CCaZN5O9JDYZSfoQfBHOGySV.vj2','mvoller24'),(78,_binary '\0','tstockill25@ovh.net','$2a$10$lXd9Ie2Y7s8cC.PQurB.0e2idCba1f/2LpAwNSBmgSoLc28rgJ502','jlackham25'),(79,_binary '\0','crenoden26@homestead.com','$2a$10$HvukVF86EI4Sbbf9AFGiGOBiqr/ZUhm32lyNoWi7oI6F9JZkgubxi','asimeon26'),(80,_binary '\0','selnaugh27@disqus.com','$2a$10$e.gvYoPTxChAYpFcoFHov.xrsz2wNJiIBEZDEIfvP2.QOp2PkeqG2','dcareswell27'),(81,_binary '\0','dmeier28@friendfeed.com','$2a$10$GM8f7P89oqUfowbHslhhqOknMY1kmSYsiu41JXW4sY/G2rQJtglGa','cfranciotti28'),(82,_binary '\0','jhorribine29@stumbleupon.com','$2a$10$t5XuFf3/b/4CUjI6qIrl6eF8g/FdoujOEZ8baLMJ.HxsDUTjCwKZi','lstuther29'),(83,_binary '\0','pchasle2a@tamu.edu','$2a$10$1mg8CLiKnU1Pkgp90LFzPesMSiAtherE8FTyYoChJQaTEXWnaYQye','cgrahamslaw2a'),(84,_binary '\0','ncoleson2b@shop-pro.jp','$2a$10$bNYKW3haEfRnj9mlc5kX.eFubfKI6HvBx5O3Hx0gYKPrL.Kn.ALp2','fphilcox2b'),(85,_binary '\0','fghilardi2c@upenn.edu','$2a$10$ZuF1uoBEaEnKnbNfSRF.eeGLq1Am.4Jy95mtJx.UTZRqAatbJkhmq','hcockerton2c'),(86,_binary '\0','tmcmillam2d@netvibes.com','$2a$10$jjD8bYDmRMSSXXKeens2AONl6SuxTQ9Xqu0c0ITuT9ibRKpIqpfFm','agilbody2d'),(87,_binary '\0','njoire2e@webeden.co.uk','$2a$10$de1TKukhKGL261TMICyRX.q/UiH0WW6/HB4OyNo7uoy698NxOciHe','jbrastead2e'),(88,_binary '\0','fjewitt2f@usnews.com','$2a$10$w6xfSs0PFJQgD5TwnrxdKemVHmRWudt4see7psnNBf6I9B69/gReq','fspatig2f'),(89,_binary '\0','hchivrall2g@yellowbook.com','$2a$10$CtBVLk43f64NiqY5A3widuKdtdVXQDCV1ZQMsO6m.pkPGQAs/Htqe','bduxbury2g'),(90,_binary '\0','dpendleton2h@oaic.gov.au','$2a$10$lsAYlc7I.tTw6YgKZQCJ7erZxksd59T42eiFx98AATET9rA2Ixjy2','kdeambrosis2h'),(91,_binary '\0','cstroder2i@google.co.jp','$2a$10$xNcPO4EyaReblYnJhRZoWezFbBGdmANH2CSfVPEOuI9BrB8f4ljlS','chalvosen2i'),(92,_binary '\0','rtuerena2j@squidoo.com','$2a$10$0Er2b1hd8e4Puvraqc1zcuY2C2U/mH/zFqoZGOS9AxBXazBOvw/n.','cformby2j'),(93,_binary '\0','sdolohunty2k@blogs.com','$2a$10$sKjLR7kP4dVBpFC.APVBhOUcDLcU3vIAuv8ehCFUTOlnLr8T535Ga','cdigregorio2k'),(94,_binary '\0','amaybury2l@ucla.edu','$2a$10$gWHrUVbcFdE2qI7EO4sRlOhP4NyOpVugIUhOhNh8LjoZo8lznjCyO','gjery2l'),(95,_binary '\0','melliss2m@fotki.com','$2a$10$brXedP7rwqWnFFqqymsApumHc26BRu0LqS7lb/x5QXpBCDh3JjeU6','ymckeating2m'),(96,_binary '\0','scops2n@sitemeter.com','$2a$10$9DxcyN1Bhib3gT4X/7QIK.r5drf2/AbgbDjyFOqdGBzmYcTORRA.O','cyabsley2n'),(97,_binary '\0','hboldock2o@squarespace.com','$2a$10$GuNZcWNqtzXLHP/1GjcOVeWgcE/YB7creZ4lGPO/dLJovOK3mrG0.','nzmitrichenko2o'),(98,_binary '\0','kedinboro2p@g.co','$2a$10$k7sHEkw0Xdk3lIBh8BgjrurPEeirgB.k0kMdP9l/nRRdxvBw5mgPa','gknotte2p'),(99,_binary '\0','dmerioth2q@google.com.br','$2a$10$BQ.4R/8FO4LnoLTHpIT2IOzrwhqIWAuq0LFp5QE51B8tiOOgznPde','pdunrige2q'),(100,_binary '\0','aheeron2r@purevolume.com','$2a$10$YI2lSnTJmSnwnmsHH9Bb/.u/iKnOyCmwsbPYsTIa12vSs8X2U0H1q','shinsche2r'),(3578,_binary '','admin','$2a$10$T6wU5LC4kqizlULlJP8pl.BFBFLBKiTjocRHe/AAFX5Lo4MlETZGe','admin');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
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
