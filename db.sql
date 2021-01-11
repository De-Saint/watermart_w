/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.27 : Database - fdhghdhg_watermartdb
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fdhghdhg_watermartdb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `fdhghdhg_watermartdb`;

/*Table structure for table `address_lga` */

DROP TABLE IF EXISTS `address_lga`;

CREATE TABLE `address_lga` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `lga` varchar(50) NOT NULL,
  `group_id` int(100) NOT NULL,
  KEY `id` (`id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=770 DEFAULT CHARSET=utf8;

/*Data for the table `address_lga` */

insert  into `address_lga`(`id`,`lga`,`group_id`) values (1,'Aba North',1),(2,'Aba South',1),(3,'Arochukwu',1),(4,'Bende',1),(5,'Ikwuano',1),(6,'Isiala-Ngwa North',1),(7,'Isiala-Ngwa South',1),(8,'Isuikwato',1),(9,'Obioma Ngwa',1),(10,'Ohafia',1),(11,'Osisioma Ngwa',1),(12,'Ugwunagbo',1),(13,'Ukwa East',1),(14,'Ukwa West',1),(15,'Umuahia North',1),(16,'Umuahia South',1),(17,'Umu-Neochi',1),(18,'Demsa',2),(19,'Fufore',2),(20,'Ganaye',2),(21,'Gireri',2),(22,'Gombi',2),(23,'Guyuk',2),(24,'Hong',2),(25,'Jada',2),(26,'Lamurde',2),(27,'Madagali',2),(28,'Maiha',2),(29,'Mayo-Belwa',2),(30,'Michika',2),(31,'Mubi North',2),(32,'Mubi South',2),(33,'Numan',2),(34,'Shelleng',2),(35,'Song',2),(36,'Toungo',2),(37,'Yola North',2),(38,'Yola South',2),(39,'Abak',3),(40,'Eastern Obolo',3),(41,'Eket',3),(42,'Esit Eket',3),(43,'Essien Udim',3),(44,'Etim Ekpo',3),(45,'Etinan',3),(46,'Ibeno',3),(47,'Ibesikpo Asutan',3),(48,'Ibiono Ibom',3),(49,'Ika',3),(50,'Ikono',3),(51,'Ikot Abasi',3),(52,'Ikot Ekpene',3),(53,'Ini',3),(54,'Itu',3),(55,'Mbo',3),(56,'Mkpat Enin',3),(57,'Nsit Atai',3),(58,'Nsit Ibom',3),(59,'Nsit Ubium',3),(60,'Obot Akara',3),(61,'Okobo',3),(62,'Onna',3),(63,'Oron',3),(64,'Oruk Anam',3),(65,'Udung Uko',3),(66,'Ukanafun',3),(67,'Uruan',3),(68,'Urue-Offong/Oruko',3),(69,'Uyo',3),(70,'Aguata',4),(71,'Anambra East',4),(72,'Anambra West',4),(73,'Anaocha',4),(74,'Awka North',4),(75,'Awka South',4),(76,'Ayamelum',4),(77,'Dunukofia',4),(78,'Ekwusigo',4),(79,'Idemili North',4),(80,'Idemili south',4),(81,'Ihiala',4),(82,'Njikoka',4),(83,'Nnewi North',4),(84,'Nnewi South',4),(85,'Ogbaru',4),(86,'Onitsha North',4),(87,'Onitsha South',4),(88,'Orumba North',4),(89,'Orumba South',4),(90,'Oyi',4),(91,'Alkaleri',5),(92,'Bauchi',5),(93,'Bogoro',5),(94,'Damban',5),(95,'Darazo',5),(96,'Dass',5),(97,'Ganjuwa',5),(98,'Giade',5),(99,'Itas/Gadau',5),(100,'Jama\'are',5),(101,'Katagum',5),(102,'Kirfi',5),(103,'Misau',5),(104,'Ningi',5),(105,'Shira',5),(106,'Tafawa-Balewa',5),(107,'Toro',5),(108,'Warji',5),(109,'Zaki',5),(110,'Brass',6),(111,'Ekeremor',6),(112,'Kolokuma/Opokuma',6),(113,'Nembe',6),(114,'Ogbia',6),(115,'Sagbama',6),(116,'Southern Jaw',6),(117,'Yenegoa',6),(118,'Ado',7),(119,'Agatu',7),(120,'Apa',7),(121,'Buruku',7),(122,'Gboko',7),(123,'Guma',7),(124,'Gwer East',7),(125,'Gwer West',7),(126,'Katsina-Ala',7),(127,'Konshisha',7),(128,'Kwande',7),(129,'Logo',7),(130,'Makurdi',7),(131,'Obi',7),(132,'Ogbadibo',7),(133,'Oju',7),(134,'Okpokwu',7),(135,'Ohimini',7),(136,'Oturkpo',7),(137,'Tarka',7),(138,'Ukum',7),(139,'Ushongo',7),(140,'Vandeikya',7),(141,' Abadam',8),(142,'Askira/Uba',8),(143,'Bama',8),(144,'Bayo',8),(145,'Biu',8),(146,'Chibok',8),(147,'Damboa',8),(148,'Dikwa',8),(149,'Gubio',8),(150,'Guzamala',8),(151,'Gwoza',8),(152,'Hawul',8),(153,'Jere',8),(154,'Kaga',8),(155,'Kala/Balge',8),(156,'Konduga',8),(157,'Kukawa',8),(158,'Kwaya Kusar',8),(159,'Mafa',8),(160,'Magumeri',8),(161,'Maiduguri',8),(162,'Marte',8),(163,'Mobbar',8),(164,'Monguno',8),(165,'Ngala',8),(166,'Nganzai',8),(167,'Shani',8),(168,'Akpabuyo',9),(169,'Odukpani',9),(170,'Akamkpa',9),(171,'Biase',9),(172,'Abi',9),(173,'Ikom',9),(174,'Yarkur',9),(175,'Odubra',9),(176,'Boki',9),(177,'Ogoja',9),(178,'Yala',9),(179,'Obanliku',9),(180,'Obudu',9),(181,'Calabar South',9),(182,'Etung',9),(183,'Bekwara',9),(184,'Bakassi',9),(185,'Calabar Municipality',9),(186,'Oshimili',10),(187,'Aniocha',10),(188,'Aniocha South',10),(189,'Ika South',10),(190,'Ika North-East',10),(191,'Ndokwa West',10),(192,'Ndokwa East',10),(193,'Isoko south',10),(194,'Isoko North',10),(195,'Bomadi',10),(196,'Burutu',10),(197,'Ughelli South',10),(198,'Ughelli North',10),(199,'Ethiope West',10),(200,'Ethiope East',10),(201,'Sapele',10),(202,'Okpe',10),(203,'Warri North',10),(204,'Warri South',10),(205,'Uvwie',10),(206,'Udu',10),(207,'Warri Central',10),(208,'Ukwani',10),(209,'Oshimili North',10),(210,'Patani',10),(211,'Afikpo South',11),(212,'Afikpo North',11),(213,'Onicha',11),(214,'Ohaozara',11),(215,'Abakaliki',11),(216,'Ishielu',11),(217,'lkwo',11),(218,'Ezza',11),(219,'Ezza South',11),(220,'Ohaukwu',11),(221,'Ebonyi',11),(222,'Ivo',11),(223,'Esan North-East',12),(224,'Esan Central',12),(225,'Esan West',12),(226,'Egor',12),(227,'Ukpoba',12),(228,'Central',12),(229,'Etsako Central',12),(230,'Igueben',12),(231,'Oredo',12),(232,'Ovia SouthWest',12),(233,'Ovia South-East',12),(234,'Orhionwon',12),(235,'Uhunmwonde',12),(236,'Etsako East',12),(237,'Esan South-East',12),(238,'Ado',13),(239,'Ekiti-East',13),(240,'Ekiti-West',13),(241,'Emure/Ise/Orun',13),(242,'Ekiti South-West',13),(243,'Ikare',13),(244,'Irepodun',13),(245,'Ijero,',13),(246,'Ido/Osi',13),(247,'Oye',13),(248,'Ikole',13),(249,'Moba',13),(250,'Gbonyin',13),(251,'Efon',13),(252,'Ise/Orun',13),(253,'Ilejemeje.',13),(254,'Enugu South,',14),(255,'Igbo-Eze South',14),(256,'Enugu North',14),(257,'Nkanu',14),(258,'Udi Agwu',14),(259,'Oji-River',14),(260,'Ezeagu',14),(261,'IgboEze North',14),(262,'Isi-Uzo',14),(263,'Nsukka',14),(264,'Igbo-Ekiti',14),(265,'Uzo-Uwani',14),(266,'Enugu Eas',14),(267,'Aninri',14),(268,'Nkanu East',14),(269,'Udenu',14),(270,'Akko',15),(271,'Balanga',15),(272,'Billiri',15),(273,'Dukku',15),(274,'Kaltungo',15),(275,'Kwami',15),(276,'Shomgom',15),(277,'Funakaye',15),(278,'Gombe',15),(279,'Nafada/Bajoga',15),(280,'Yamaltu/Delta.',15),(281,'Aboh-Mbaise',16),(282,'Ahiazu-Mbaise',16),(283,'Ehime-Mbano',16),(284,'Ezinihitte',16),(285,'Ideato North',16),(286,'Ideato South',16),(287,'Ihitte/Uboma',16),(288,'Ikeduru',16),(289,'Isiala Mbano',16),(290,'Isu',16),(291,'Mbaitoli',16),(292,'Mbaitoli',16),(293,'Ngor-Okpala',16),(294,'Njaba',16),(295,'Nwangele',16),(296,'Nkwerre',16),(297,'Obowo',16),(298,'Oguta',16),(299,'Ohaji/Egbema',16),(300,'Okigwe',16),(301,'Orlu',16),(302,'Orsu',16),(303,'Oru East',16),(304,'Oru West',16),(305,'Owerri-Municipal',16),(306,'Owerri North',16),(307,'Owerri West',16),(308,'Auyo',17),(309,'Babura',17),(310,'Birni Kudu',17),(311,'Biriniwa',17),(312,'Buji',17),(313,'Dutse',17),(314,'Gagarawa',17),(315,'Garki',17),(316,'Gumel',17),(317,'Guri',17),(318,'Gwaram',17),(319,'Gwiwa',17),(320,'Hadejia',17),(321,'Jahun',17),(322,'Kafin Hausa',17),(323,'Kaugama Kazaure',17),(324,'Kiri Kasamma',17),(325,'Kiyawa',17),(326,'Maigatari',17),(327,'Malam Madori',17),(328,'Miga',17),(329,'Ringim',17),(330,'Roni',17),(331,'Sule-Tankarkar',17),(332,'Taura',17),(333,'Yankwashi',17),(334,'Birni-Gwari',18),(335,'Chikun',18),(336,'Giwa',18),(337,'Igabi',18),(338,'Ikara',18),(339,'jaba',18),(340,'Jema\'a',18),(341,'Kachia',18),(342,'Kaduna North',18),(343,'Kaduna South',18),(344,'Kagarko',18),(345,'Kajuru',18),(346,'Kaura',18),(347,'Kauru',18),(348,'Kubau',18),(349,'Kudan',18),(350,'Lere',18),(351,'Makarfi',18),(352,'Sabon-Gari',18),(353,'Sanga',18),(354,'Soba',18),(355,'Zango-Kataf',18),(356,'Zaria',18),(357,'Ajingi',19),(358,'Albasu',19),(359,'Bagwai',19),(360,'Bebeji',19),(361,'Bichi',19),(362,'Bunkure',19),(363,'Dala',19),(364,'Dambatta',19),(365,'Dawakin Kudu',19),(366,'Dawakin Tofa',19),(367,'Doguwa',19),(368,'Fagge',19),(369,'Gabasawa',19),(370,'Garko',19),(371,'Garum',19),(372,'Mallam',19),(373,'Gaya',19),(374,'Gezawa',19),(375,'Gwale',19),(376,'Gwarzo',19),(377,'Kabo',19),(378,'Kano Municipal',19),(379,'Karaye',19),(380,'Kibiya',19),(381,'Kiru',19),(382,'kumbotso',19),(383,'Kunchi',19),(384,'Kura',19),(385,'Madobi',19),(386,'Makoda',19),(387,'Minjibir',19),(388,'Nasarawa',19),(389,'Rano',19),(390,'Rimin Gado',19),(391,'Rogo',19),(392,'Shanono',19),(393,'Sumaila',19),(394,'Takali',19),(395,'Tarauni',19),(396,'Tofa',19),(397,'Tsanyawa',19),(398,'Tudun Wada',19),(399,'Ungogo',19),(400,'Warawa',19),(401,'Wudil',19),(402,'Bakori',20),(403,'Batagarawa',20),(404,'Batsari',20),(405,'Baure',20),(406,'Bindawa',20),(407,'Charanchi',20),(408,'Dandume',20),(409,'Danja',20),(410,'Dan Musa',20),(411,'Daura',20),(412,'Dutsi',20),(413,'Dutsin-Ma',20),(414,'Faskari',20),(415,'Funtua',20),(416,'Ingawa',20),(417,'Jibia',20),(418,'Kafur',20),(419,'Kaita',20),(420,'Kankara',20),(421,'Kankia',20),(422,'Katsina',20),(423,'Kurfi',20),(424,'Kusada',20),(425,'Mai\'Adua',20),(426,'Malumfashi',20),(427,'Mani',20),(428,'Mashi',20),(429,'Matazuu',20),(430,'Musawa',20),(431,'Rimi',20),(432,'Sabuwa',20),(433,'Safana',20),(434,'Sandamu',20),(435,'Zango',20),(436,'Aleiro',21),(437,'Arewa-Dandi',21),(438,'Argungu',21),(439,'Augie',21),(440,'Bagudo',21),(441,'Birnin Kebbi',21),(442,'Bunza',21),(443,'Dandi',21),(444,'Fakai',21),(445,'Gwandu',21),(446,'Jega',21),(447,'Kalgo',21),(448,'Koko/Besse',21),(449,'Maiyama',21),(450,'Ngaski',21),(451,'Sakaba',21),(452,'Shanga',21),(453,'Suru',21),(454,'Wasagu/Danko',21),(455,'Yauri',21),(456,'Zuru',21),(457,'Adavi',22),(458,'Ajaokuta',22),(459,'Ankpa',22),(460,'Bassa',22),(461,'Dekina',22),(462,'Ibaji',22),(463,'Idah',22),(464,'Igalamela-Odolu',22),(465,'Ijumu',22),(466,'Kabba/Bunu',22),(467,'Kogi',22),(468,'Lokoja',22),(469,'Mopa-Muro',22),(470,'Ofu',22),(471,'Ogori/Mangongo',22),(472,'Okehi',22),(473,'Okene',22),(474,'Olamabolo',22),(475,'Omala',22),(476,'Yagba East',22),(477,'Yagba West',22),(478,'Asa',23),(479,'Baruten',23),(480,'Edu',23),(481,'Ekiti',23),(482,'Ifelodun',23),(483,'Ilorin East',23),(484,'Ilorin West',23),(485,'Irepodun',23),(486,'Isin',23),(487,'Kaiama',23),(488,'Moro',23),(489,'Offa',23),(490,'Oke-Ero',23),(491,'Oyun',23),(492,'Pategi',23),(493,'Agege',24),(494,'Ajeromi-Ifelodun',24),(495,'Alimosho',24),(496,'Amuwo-Odofin',24),(497,'Apapa',24),(498,'Badagry',24),(499,'Epe',24),(500,'Eti-Osa',24),(501,'Ibeju/Lekki',24),(502,'Ifako-Ijaye',24),(503,'Ikeja',24),(504,'Ikorodu',24),(505,'Kosofe',24),(506,'Lagos Island',24),(507,'Lagos Mainland',24),(508,'Mushin',24),(509,'Ojo',24),(510,'Oshodi-Isolo',24),(511,'Shomolu',24),(512,'Surulere',24),(513,'Akwanga',25),(514,'Awe',25),(515,'Doma',25),(516,'Karu',25),(517,'Keana',25),(518,'Keffi',25),(519,'Kokona',25),(520,'Lafia',25),(521,'Nasarawa',25),(522,'Nasarawa-Eggon',25),(523,'Obi',25),(524,'Toto',25),(525,'Wamba',25),(526,'Agaie',26),(527,'Agwara',26),(528,'Bida',26),(529,'Borgu',26),(530,'Bosso',26),(531,'Chanchaga',26),(532,'Edati',26),(533,'Gbako',26),(534,'Gurara',26),(535,'Katcha',26),(536,'Kontagora',26),(537,'Lapai',26),(538,'Lavun',26),(539,'Magama',26),(540,'Mariga',26),(541,'Mashegu',26),(542,'Mokwa',26),(543,'Muya',26),(544,'Pailoro',26),(545,'Rafi',26),(546,'Rijau',26),(547,'Shiroro',26),(548,'Suleja',26),(549,'Tafa',26),(550,'Wushishi',26),(551,'Abeokuta North',27),(552,'Abeokuta South',27),(553,'Ado-Odo/Ota',27),(554,'Egbado North',27),(555,'Egbado South',27),(556,'Ewekoro',27),(557,'Ifo',27),(558,'Ijebu East',27),(559,'Ijebu North',27),(560,'Ijebu North East',27),(561,'Ijebu Ode',27),(562,'Ikenne',27),(563,'Imeko-Afon',27),(564,'Ipokia',27),(565,'Obafemi-Owode',27),(566,'Ogun Waterside',27),(567,'Odeda',27),(568,'Odogbolu',27),(569,'Remo North',27),(570,'Shagamu',27),(571,'Akoko North East',28),(572,'Akoko North West',28),(573,'Akoko South Akure East',28),(574,'Akoko South West',28),(575,'Akure North',28),(576,'Akure South',28),(577,'Ese-Odo',28),(578,'Idanre',28),(579,'Ifedore',28),(580,'Ilaje',28),(581,'Ile-Oluji',28),(582,'Okeigbo',28),(583,'Irele',28),(584,'Odigbo',28),(585,'Okitipupa',28),(586,'Ondo East',28),(587,'Ondo West',28),(588,'Ose',28),(589,'Owo',28),(590,'Aiyedade',29),(591,'Aiyedire',29),(592,'Atakumosa East',29),(593,'Atakumosa West',29),(594,'Boluwaduro',29),(595,'Boripe',29),(596,'Ede North',29),(597,'Ede South',29),(598,'Egbedore',29),(599,'Ejigbo',29),(600,'Ife Central',29),(601,'Ife East',29),(602,'Ife North',29),(603,'Ife South',29),(604,'Ifedayo',29),(605,'Ifelodun',29),(606,'Ila',29),(607,'Ilesha East',29),(608,'Ilesha West',29),(609,'Irepodun',29),(610,'Irewole',29),(611,'Isokan',29),(612,'Iwo',29),(613,'Obokun',29),(614,'Odo-Otin',29),(615,'Ola-Oluwa',29),(616,'Olorunda',29),(617,'Oriade',29),(618,'Orolu',29),(619,'Oshogbo',29),(620,'Afijio',30),(621,'Akinyele',30),(622,'Atiba',30),(623,'Atigbo',30),(624,'Egbeda',30),(625,'IbadanCentral',30),(626,'Ibadan North',30),(627,'Ibadan North West',30),(628,'Ibadan South East',30),(629,'Ibadan South West',30),(630,'Ibarapa Central',30),(631,'Ibarapa East',30),(632,'Ibarapa North',30),(633,'Ido',30),(634,'Irepo',30),(635,'Iseyin',30),(636,'Itesiwaju',30),(637,'Iwajowa',30),(638,'Kajola',30),(639,'Lagelu Ogbomosho North',30),(640,'Ogbmosho South',30),(641,'Ogo Oluwa',30),(642,'Olorunsogo',30),(643,'Oluyole',30),(644,'Ona-Ara',30),(645,'Orelope',30),(646,'Ori Ire',30),(647,'Oyo East',30),(648,'Oyo West',30),(649,'Saki East',30),(650,'Saki West',30),(651,'Surulere',30),(652,'Barikin Ladi',31),(653,'Bassa',31),(654,'Bokkos',31),(655,'Jos East',31),(656,'Jos North',31),(657,'Jos South',31),(658,'Kanam',31),(659,'Kanke',31),(660,'Langtang North',31),(661,'Langtang South',31),(662,'Mangu',31),(663,'Mikang',31),(664,'Pankshin',31),(665,'Qua\'an Pan',31),(666,'Riyom',31),(667,'Shendam',31),(668,'Wase',31),(669,'Abua/Odual',32),(670,'Ahoada East',32),(671,'Ahoada West',32),(672,'Akuku Toru',32),(673,'Andoni',32),(674,'Asari-Toru',32),(675,'Bonny',32),(676,'Degema',32),(677,'Emohua',32),(678,'Eleme',32),(679,'Etche',32),(680,'Gokana',32),(681,'Ikwerre',32),(682,'Khana',32),(683,'Obia/Akpor',32),(684,'Ogba/Egbema/Ndoni',32),(685,'Ogu/Bolo',32),(686,'Okrika',32),(687,'Omumma',32),(688,'Opobo/Nkoro',32),(689,'Oyigbo',32),(690,'Port-Harcourt',32),(691,'Tai',32),(692,'Binji',33),(693,'Bodinga',33),(694,'Dange-shnsi',33),(695,'Gada',33),(696,'Goronyo',33),(697,'Gudu',33),(698,'Gawabawa',33),(699,'Illela',33),(700,'Isa',33),(701,'Kware',33),(702,'kebbe',33),(703,'Rabah',33),(704,'Sabon birni',33),(705,'Shagari',33),(706,'Silame',33),(707,'Sokoto North',33),(708,'Sokoto South',33),(709,'Tambuwal',33),(710,'Tangaza',33),(711,'Tureta',33),(712,'Wamako',33),(713,'Wurno',33),(714,'Yabo',33),(715,'Ardo-kola',34),(716,'Bali',34),(717,'Donga',34),(718,'Gashaka',34),(719,'Cassol',34),(720,'Ibi',34),(721,'Jalingo',34),(722,'Karin-Lamido',34),(723,'Kurmi',34),(724,'Lau',34),(725,'Sardauna',34),(726,'Takum',34),(727,'Ussa',34),(728,'Wukari',34),(729,'Yorro',34),(730,'Zing',34),(731,'Bade',35),(732,'Bursari',35),(733,'Damaturu',35),(734,'Fika',35),(735,'Fune',35),(736,'Geidam',35),(737,'Gujba',35),(738,'Gulani',35),(739,'Jakusko',35),(740,'Karasuwa',35),(741,'Karawa',35),(742,'Machina',35),(743,'Nangere',35),(744,'Nguru Potiskum',35),(745,'Tarmua',35),(746,'Yunusari',35),(747,'Yusufari',35),(748,'Anka',36),(749,'Bakura',36),(750,'Birnin Magaji',36),(751,'Bukkuyum',36),(752,'Bungudu',36),(753,'Gummi',36),(754,'Gusau',36),(755,'Kaura',36),(756,'Namoda',36),(757,'Maradun',36),(758,'Maru',36),(759,'Shinkafi',36),(760,'Talata Mafara',36),(761,'Tsafe',36),(762,'Zurmi',36),(763,'Abaji',37),(764,'Amac',37),(765,'Bwari',37),(766,'Gwagwalada',37),(767,'Karshi',37),(768,'Kuje',37),(769,'kwali',37);

/*Table structure for table `address_states` */

DROP TABLE IF EXISTS `address_states`;

CREATE TABLE `address_states` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` varchar(50) NOT NULL,
  `countryid` int(11) NOT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `address_states` */

insert  into `address_states`(`id`,`state`,`countryid`) values (1,'Abia',157),(2,'Adamawa',157),(3,'Akwa Ibom',157),(4,'Anambra',157),(5,'Bauchi',157),(6,'Bayelsa',157),(7,'Benue',157),(8,'Borno',157),(9,'Cross River',157),(10,'Delta',157),(11,'Ebonyi',157),(12,'Edo',157),(13,'Ekiti',157),(14,'Enugu',157),(15,'Gombe',157),(16,'Imo',157),(17,'Jigawa',157),(18,'Kaduna',157),(19,'Kano',157),(20,'Katsina',157),(21,'Kebbi',157),(22,'Kogi',157),(23,'Kwara',157),(24,'Lagos',157),(25,'Nasarawa',157),(26,'Niger',157),(27,'Ogun',157),(28,'Ondo',157),(29,'Osun',157),(30,'Oyo',157),(31,'Plateau',157),(32,'Rivers',157),(33,'Sokoto',157),(34,'Taraba',157),(35,'Yobe',157),(36,'Zamfara',157),(37,'Abuja',157);

/*Table structure for table `admins` */

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `address` text,
  `account_bal` float DEFAULT NULL,
  `transaction_number` int(11) DEFAULT NULL,
  KEY `adminid` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `admins` */

insert  into `admins`(`id`,`userid`,`address`,`account_bal`,`transaction_number`) values (1,1,'22 WaterRoad Ado Rd Ajah Lagos',0,0);

/*Table structure for table `customers` */

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `address` text,
  `status` varchar(20) DEFAULT NULL,
  KEY `customerid` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `customers` */

insert  into `customers`(`id`,`userid`,`address`,`status`) values (1,2,'33 Ogidan Town Ajah, Eti-Osa, Lagos State','UnBlocked');

/*Table structure for table `locations` */

DROP TABLE IF EXISTS `locations`;

CREATE TABLE `locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_userid` int(11) DEFAULT NULL,
  `name` text,
  `fees` text,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `locations` */

insert  into `locations`(`id`,`supplier_userid`,`name`,`fees`) values (1,3,'Ajah','1500'),(3,3,'Ogidan','1250');

/*Table structure for table `messages` */

DROP TABLE IF EXISTS `messages`;

CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `time` time DEFAULT NULL,
  `subject` varchar(255) NOT NULL,
  `is_read` int(1) NOT NULL,
  `from_member_id` int(11) DEFAULT NULL,
  `to_member_id` int(11) DEFAULT NULL,
  `body` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `messages` */

insert  into `messages`(`id`,`date`,`time`,`subject`,`is_read`,`from_member_id`,`to_member_id`,`body`) values (1,'2017-12-08','22:59:34','Account Created',1,1,2,'Deemene your WaterMart User Account has been created. Login Details::: Email: st.deemene@yahoo.com Password: saint2'),(2,'2017-12-08','23:59:43','Account Created',1,1,3,'Jack your WaterMart Supplier Account has been created with the following details, Login Details::: Email nearthirsty@yahoo.com Password: tryagain Supplier Code: IJ85V1KN'),(3,'2017-12-11','14:42:03','Password Reset',1,1,2,'Password has been changed to saint1234');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_userid` int(11) DEFAULT NULL,
  `supplier_userid` int(11) DEFAULT NULL,
  `productdetails` text,
  `status` varchar(50) DEFAULT NULL,
  `substatus` varchar(50) DEFAULT NULL,
  `bookedtime` time DEFAULT NULL,
  `bookeddate` date DEFAULT NULL,
  `deliverytime` time DEFAULT NULL,
  `deliverydate` date DEFAULT NULL,
  `ordernumber` varchar(20) DEFAULT NULL,
  `confirmedby_userid` int(11) DEFAULT NULL,
  `amount` text,
  `deliveryaddress` text,
  `deliveryfee` text,
  `paymentplan` varchar(20) DEFAULT NULL,
  KEY `orderid` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `orders` */

insert  into `orders`(`id`,`customer_userid`,`supplier_userid`,`productdetails`,`status`,`substatus`,`bookedtime`,`bookeddate`,`deliverytime`,`deliverydate`,`ordernumber`,`confirmedby_userid`,`amount`,`deliveryaddress`,`deliveryfee`,`paymentplan`) values (1,2,3,'2,20,2400;','Delivered','Confirmed by Jack Barilule','11:37:29','2017-12-16','11:38:35','2017-12-16','GRILK4NG',3,'3650','33 Ogidan Town Ajah, Eti-Osa, Lagos State','1250',NULL),(2,2,3,'3,12,4800;','Delivered','Confirmed by Deemene Saint','11:38:13','2017-12-16','11:38:43','2017-12-16','1Z0Q63CC',2,'6300','33 Ogidan Town Ajah, Eti-Osa, Lagos State','1500',NULL);

/*Table structure for table `payment_plan` */

DROP TABLE IF EXISTS `payment_plan`;

CREATE TABLE `payment_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `payment_plan` */

insert  into `payment_plan`(`id`,`name`) values (1,'Monthly-Fixed-Rate'),(2,'Percentage-Per-Transaction');

/*Table structure for table `product_categories` */

DROP TABLE IF EXISTS `product_categories`;

CREATE TABLE `product_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `water_catid` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `product_categories` */

insert  into `product_categories`(`id`,`name`,`water_catid`) values (1,'Sachet Water',1),(2,'Bottle Water',1),(3,'Dispenser Water',1),(4,'Tanker Water',2),(5,'Empty Dispenser Bottle',1),(6,'Empty Bottle',1),(7,'Sachet Water Bags',1),(8,'Dispenser',1);

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `price` float DEFAULT NULL,
  `properties` text,
  `product_catid` int(11) DEFAULT NULL,
  `supplier_userid` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `products` */

insert  into `products`(`id`,`name`,`price`,`properties`,`product_catid`,`supplier_userid`) values (1,'Full Bag Sachet Water',100,'Half:Bag,',1,3),(2,'NearThirsty Bottle Water',120,'Capacity Litre:60cl,',2,3),(3,'NearThirsty Dispenser Bottle Water',400,'Litres:20,',3,3),(4,'NearThirsty Empty Dispenser Bottle',140,'Litres:20,',5,3),(5,'NearThirsty Empty Water Bottle',50,'Litres:60cl,',6,3),(6,'NearThirsty Water Bags',20,'Yards:4,',7,3),(7,'NearThirsty Dispenser',23500,'Volt:200,',8,3);

/*Table structure for table `ratings` */

DROP TABLE IF EXISTS `ratings`;

CREATE TABLE `ratings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` float DEFAULT NULL,
  `supplier_userid` int(11) DEFAULT NULL,
  `customer_userid` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `ratings` */

insert  into `ratings`(`id`,`value`,`supplier_userid`,`customer_userid`) values (1,3,3,2),(2,2,3,2),(3,3,3,2),(4,5,3,2),(5,3,3,2),(6,3,3,2);

/*Table structure for table `recovery` */

DROP TABLE IF EXISTS `recovery`;

CREATE TABLE `recovery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `question` text,
  `answer` text,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `recovery` */

insert  into `recovery`(`id`,`userid`,`question`,`answer`) values (1,2,'What is your favorite color?','Blue'),(2,3,'What is your favorite color?','Yellow');

/*Table structure for table `suppliers` */

DROP TABLE IF EXISTS `suppliers`;

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `business_name` varchar(50) DEFAULT NULL,
  `nafdac_number` varchar(50) DEFAULT NULL,
  `address` text,
  `status` varchar(20) DEFAULT NULL,
  `supplier_code` varchar(10) DEFAULT NULL,
  `payment_planid` int(11) DEFAULT NULL,
  `water_catid` int(11) DEFAULT NULL,
  `ratevalue` float DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_duedate` date DEFAULT NULL,
  `account_bal` float DEFAULT NULL,
  `transaction_number` int(11) DEFAULT NULL,
  `admin_account_bal` float DEFAULT NULL,
  KEY `supplierid` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `suppliers` */

insert  into `suppliers`(`id`,`userid`,`business_name`,`nafdac_number`,`address`,`status`,`supplier_code`,`payment_planid`,`water_catid`,`ratevalue`,`payment_date`,`payment_duedate`,`account_bal`,`transaction_number`,`admin_account_bal`) values (1,3,'NearThirsty','C1-464DF','33 Ado Road Ajah , Eti-Osa, Lagos State','Activated','IJ85V1KN',1,1,3,'2017-12-18','2018-01-15',0,0,NULL);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `date_created` date DEFAULT NULL,
  `devicetoken` text,
  PRIMARY KEY (`id`),
  KEY `userid` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`id`,`firstname`,`lastname`,`email`,`phone`,`password`,`type`,`date_created`,`devicetoken`) values (1,'Admin','WaterMart','admin@watermart.com','08119141115','admin','Admin','2017-12-08',NULL),(2,'Saint','Deemene','st.deemene@yahoo.com','07067483120','saint','Customer','2017-12-08',NULL),(3,'Barilule','Jack','nearthirsty@yahoo.com','09088787383','tryagain','Supplier','2017-12-08',NULL);

/*Table structure for table `water_categories` */

DROP TABLE IF EXISTS `water_categories`;

CREATE TABLE `water_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `water_categories` */

insert  into `water_categories`(`id`,`name`) values (1,'Domestic'),(2,'Industrial');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
