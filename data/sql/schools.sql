CREATE TABLE `schools` (
  `id_schools` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `year` year(4) NOT NULL,
  `region` varchar(100) NOT NULL DEFAULT '',
  `municipality` varchar(100) NOT NULL DEFAULT '',
  `metro_area` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '',
  `school_id` int(10) unsigned NOT NULL,
  `type` varchar(100) NOT NULL DEFAULT '',
  `zip` varchar(10) NOT NULL DEFAULT '',
  `address` varchar(255) NOT NULL DEFAULT '',
  `email` varchar(50) DEFAULT NULL,
  `something1` int(11) unsigned DEFAULT NULL,
  `something2` int(11) unsigned NOT NULL,
  `class` int(11) unsigned NOT NULL,
  `num_classes` double unsigned DEFAULT NULL,
  PRIMARY KEY (`id_schools`)
) ENGINE=InnoDB AUTO_INCREMENT=15965 DEFAULT CHARSET=latin1;
