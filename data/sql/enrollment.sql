CREATE TABLE `enrollment` (
  `id_enrollment` mediumint(11) unsigned NOT NULL AUTO_INCREMENT,
  `year` year(4) NOT NULL,
  `school_id` int(4) unsigned NOT NULL,
  `class` int(11) unsigned DEFAULT NULL,
  `enrolled_male` int(11) unsigned DEFAULT NULL,
  `enrolled_female` int(11) unsigned DEFAULT NULL,
  `repeater_male` int(11) unsigned DEFAULT NULL,
  `repeater_female` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id_enrollment`),
  UNIQUE KEY `id_enrollment` (`id_enrollment`)
) ENGINE=InnoDB AUTO_INCREMENT=193975 DEFAULT CHARSET=latin1;
