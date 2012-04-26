-- 
-- set up the tables for the graph structure on MySQL
-- 

-- DROP TABLE IF EXISTS `pages`;

CREATE TABLE IF NOT EXISTS
    `pages`
    ( `page_id` int(8) unsigned NOT NULL DEFAULT '0'
    , `title` varbinary(255) NOT NULL DEFAULT ''
    , `score` DOUBLE NOT NULL
    , PRIMARY KEY (`page_id`)
    ) ENGINE=InnoDB;
    
-- DROP TABLE IF EXISTS `links`;
    
CREATE TABLE IF NOT EXISTS
    `links`
    ( `from_id` int(8) unsigned NOT NULL DEFAULT '0'
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
