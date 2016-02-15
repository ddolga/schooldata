CREATE TABLE `dropouts` (
  `id_dropouts` mediumint(11) NOT NULL AUTO_INCREMENT,
  `year` year(4) NOT NULL,
  `school_id` int(10) NOT NULL,
  `class` int(11) NOT NULL,
  `drops_male` int(11) NOT NULL,
  `drops_female` int(11) NOT NULL,
  PRIMARY KEY (`id_dropouts`),
  UNIQUE KEY `iddropouts_UNIQUE` (`id_dropouts`)
) ENGINE=InnoDB AUTO_INCREMENT=89036 DEFAULT CHARSET=latin1;
