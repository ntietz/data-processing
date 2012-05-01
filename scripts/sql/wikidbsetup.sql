-- 
-- set up the tables for the graph structure on MySQL
-- 

-- DROP TABLE IF EXISTS `pages`;

CREATE TABLE IF NOT EXISTS
    `pages`
    ( `pages_id` int(8) unsigned NOT NULL AUTO_INCREMENT
    , `page_id` int(8) unsigned NOT NULL DEFAULT '0'
    , `title` varbinary(255) NOT NULL DEFAULT ''
    , PRIMARY KEY (`page_id`)
    ) ENGINE=InnoDB;

-- DROP TABLE IF EXISTS `page_scores`;

CREATE TABLE IF NOT EXISTS
    `page_scores`
    (`page_scores_id` int(8) unsigned NOT NULL AUTO_INCREMENT
    ,`page_id` int(8) unsigned NOT NULL DEFAULT '0'
    , `score` DOUBLE NOT NULL
    , PRIMARY KEY (`page_id`)
    ) ENGINE=InnoDB;

-- DROP TABLE IF EXISTS `links`;
    
CREATE TABLE IF NOT EXISTS
    `links`
    (`links_id` int(8) unsigned NOT NULL AUTO_INCREMENT 
    ,`from_id` int(8) unsigned NOT NULL DEFAULT '0'
    , `to_id` int(8) unsigned NOT NULL DEFAULT '0'
    , PRIMARY KEY (`from_id`, `to_id`)
    ) ENGINE=InnoDB;

-- DROP TABLE IF EXISTS `bfs`;

CREATE TABLE IF NOT EXISTS
    `bfs`
    ( `from_id` int (8) unsigned NOT NULL DEFAULT '0'
    , `to_id` int(8) unsigned NOT NULL DEFAULT '0'
    , `hops` tinyint(1) unsigned NOT NULL DEFAULT '0'
    , PRIMARY KEY (`from_id`, `to_id`)
    ) ENGINE=InnoDB;
