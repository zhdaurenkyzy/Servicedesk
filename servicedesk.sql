CREATE DATABASE `servicedesk` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE servicedesk;
CREATE TABLE `engineer_group` (
  `GROUP_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GROUP_NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`GROUP_ID`),
  UNIQUE KEY `Group_ID_UNIQUE` (`GROUP_ID`),
  UNIQUE KEY `Group_name_UNIQUE` (`GROUP_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

CREATE DATABASE `servicedesk` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;CREATE TABLE `history` (
  `HISTORY_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `REQUEST_ID` bigint(20) NOT NULL,
  `COLUMN_NAME` varchar(45) NOT NULL,
  `COLUMN_VALUE_BEFORE` mediumtext,
  `COLUMN_VALUE_AFTER` mediumtext,
  `DATE_OF_CHANGE` datetime NOT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`HISTORY_ID`),
  KEY `USER_ID_HISTORYID_FK` (`USER_ID`),
  KEY `REQUEST_ID_HISTORY_idx` (`REQUEST_ID`),
  CONSTRAINT `REQUEST_ID_HISTORY` FOREIGN KEY (`REQUEST_ID`) REFERENCES `request` (`REQUEST_ID`),
  CONSTRAINT `USER_ID_HISTORYID_FK` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=336 DEFAULT CHARSET=utf8;

CREATE TABLE `language` (
  `LANGUAGE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LANGUAGE_NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LANGUAGE_LOCAL` varchar(45) NOT NULL,
  PRIMARY KEY (`LANGUAGE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `level` (
  `LEVEL_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LEVEL_NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`LEVEL_ID`),
  UNIQUE KEY `Level_name_UNIQUE` (`LEVEL_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `mode` (
  `MODE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODE_NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`MODE_ID`),
  UNIQUE KEY `Mode_ID_UNIQUE` (`MODE_ID`),
  UNIQUE KEY `Mode_name_UNIQUE` (`MODE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `project` (
  `PROJECT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PROJECT_NAME` varchar(100) NOT NULL,
  `PROJECT_STATE` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`PROJECT_ID`),
  UNIQUE KEY `Projeci_ID_UNIQUE` (`PROJECT_ID`),
  UNIQUE KEY `PROJECT_NAME_UNIQUE` (`PROJECT_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

CREATE TABLE `request` (
  `REQUEST_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `REQUEST_THEME` varchar(255) NOT NULL,
  `REQUEST_DESCRIPTION` mediumtext,
  `REQUEST_STATUS_ID` bigint(20) NOT NULL,
  `REQUEST_LEVEL_ID` bigint(20) NOT NULL,
  `REQUEST_MODE_ID` bigint(20) NOT NULL,
  `REQUEST_PRIORITY_ID` bigint(20) NOT NULL,
  `ENGINEER_GROUP_ID` bigint(20) DEFAULT NULL,
  `ENGINEER_USER_ID` bigint(20) DEFAULT NULL,
  `PROJECT_ID` bigint(20) DEFAULT NULL,
  `CLIENT_USER_ID` bigint(20) DEFAULT NULL,
  `REQUEST_AUTHOR_OF_CREATION` bigint(20) NOT NULL,
  `REQUEST_DATE_OF_CREATION` datetime NOT NULL,
  `REQUEST_DECISION` mediumtext,
  `REQUEST_AUTHOR_OF_DECISION` bigint(20) DEFAULT NULL,
  `REQUEST_DATE_OF_DECISION` datetime DEFAULT NULL,
  PRIMARY KEY (`REQUEST_ID`),
  UNIQUE KEY `Request_ID_UNIQUE` (`REQUEST_ID`),
  KEY `PROJECT_ID_REQUESTID_FK_idx` (`PROJECT_ID`),
  KEY `CLIENT_ID_REQUESTID_FK_idx` (`CLIENT_USER_ID`),
  CONSTRAINT `CLIENT_OD_REQUESTID_FK` FOREIGN KEY (`CLIENT_USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

CREATE TABLE `status` (
  `STATUS_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STATUS_NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`STATUS_ID`),
  UNIQUE KEY `Status_ID_UNIQUE` (`STATUS_ID`),
  UNIQUE KEY `Status_name_UNIQUE` (`STATUS_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(45) NOT NULL,
  `USER_POSITION` varchar(100) DEFAULT NULL,
  `USER_PHONE` varchar(20) DEFAULT NULL,
  `USER_MOBILE` varchar(20) DEFAULT NULL,
  `USER_MAIL` varchar(45) DEFAULT NULL,
  `USER_LOGIN` varchar(45) NOT NULL,
  `USER_PASSWORD` varchar(100) NOT NULL,
  `USER_ROLE` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `User_ID_UNIQUE` (`USER_ID`),
  UNIQUE KEY `User_login_UNIQUE` (`USER_LOGIN`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

CREATE TABLE `user_group` (
  `USER_ID` bigint(20) NOT NULL,
  `GROUP_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`GROUP_ID`),
  KEY `Group_ID_ugroupID_FK_idx` (`GROUP_ID`),
  CONSTRAINT `GROUP_ID_UGROUPID_FK` FOREIGN KEY (`GROUP_ID`) REFERENCES `engineer_group` (`GROUP_ID`),
  CONSTRAINT `USER_ID_UGROUPID_FK` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_project` (
  `USER_ID` bigint(20) NOT NULL,
  `PROJECT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`PROJECT_ID`),
  KEY `PROJECT_ID_UPROJECTID_FK` (`PROJECT_ID`),
  CONSTRAINT `PROJECT_ID_UPROJECTID_FK` FOREIGN KEY (`PROJECT_ID`) REFERENCES `project` (`PROJECT_ID`),
  CONSTRAINT `USER_ID_UPROJECTID_FK` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `servicedesk`.`engineer_group` (`GROUP_ID`, `GROUP_NAME`) VALUES ('1', 'System admin');
INSERT INTO `servicedesk`.`engineer_group` (`GROUP_NAME`) VALUES ('Network engineers');
INSERT INTO `servicedesk`.`engineer_group` (`GROUP_NAME`) VALUES ('Other');

INSERT INTO `servicedesk`.`language` (`LANGUAGE_ID`, `LANGUAGE_NAME`, `LANGUAGE_LOCAL`) VALUES ('1', 'en', 'EN');
INSERT INTO `servicedesk`.`language` (`LANGUAGE_ID`, `LANGUAGE_NAME`, `LANGUAGE_LOCAL`) VALUES ('2', 'ru', 'RU');

INSERT INTO `servicedesk`.`level` (`LEVEL_ID`, `LEVEL_NAME`) VALUES ('1', 'Service request');
INSERT INTO `servicedesk`.`level` (`LEVEL_NAME`) VALUES ('Change request');
INSERT INTO `servicedesk`.`level` (`LEVEL_NAME`) VALUES ('Incident');

INSERT INTO `servicedesk`.`project` (`PROJECT_ID`, `PROJECT_NAME`, `PROJECT_STATE`) VALUES ('1', 'Demeu Company', '1');
INSERT INTO `servicedesk`.`project` (`PROJECT_NAME`, `PROJECT_STATE`) VALUES ('Telecom', '1');
INSERT INTO `servicedesk`.`project` (`PROJECT_NAME`, `PROJECT_STATE`) VALUES ('PetroKazakhstan', '0');
INSERT INTO `servicedesk`.`project` (`PROJECT_NAME`, `PROJECT_STATE`) VALUES ('Kazmed', '0');

INSERT INTO `servicedesk`.`status` (`STATUS_ID`, `STATUS_NAME`) VALUES ('1', 'Open');
INSERT INTO `servicedesk`.`status` (`STATUS_NAME`) VALUES ('In progress');
INSERT INTO `servicedesk`.`status` (`STATUS_NAME`) VALUES ('Waiting for response');
INSERT INTO `servicedesk`.`status` (`STATUS_NAME`) VALUES ('Resolved');

INSERT INTO `servicedesk`.`user` (`USER_ID`, `USER_NAME`, `USER_POSITION`, `USER_PHONE`, `USER_MOBILE`, `USER_MAIL`, `USER_LOGIN`, `USER_PASSWORD`, `USER_ROLE`) VALUES ('1', 'Клиент', '', '', '', 'client@gmail.com', 'client', '62608e08adc29a8d6dbc9754e659f125', '2');
INSERT INTO `servicedesk`.`user` (`USER_ID`, `USER_NAME`, `USER_POSITION`, `USER_PHONE`, `USER_MOBILE`, `USER_MAIL`, `USER_LOGIN`, `USER_PASSWORD`, `USER_ROLE`) VALUES ('', 'Инженер', '', '', '', 'engineer@gmail.com', 'engineer', '9d127ff383d595262c67036f50493133', '1');
INSERT INTO `servicedesk`.`user` (`USER_ID`, `USER_NAME`, `USER_POSITION`, `USER_PHONE`, `USER_MOBILE`, `USER_MAIL`, `USER_LOGIN`, `USER_PASSWORD`, `USER_ROLE`) VALUES ('', 'Admin', '', '', '', 'admin1@gmail.com', 'admin', 'e00cf25ad42683b3df678c61f42c6bda', '0');
INSERT INTO `servicedesk`.`user` (`USER_ID`, `USER_NAME`, `USER_POSITION`, `USER_PHONE`, `USER_MOBILE`, `USER_MAIL`, `USER_LOGIN`, `USER_PASSWORD`, `USER_ROLE`) VALUES ('', 'Engineer2', '', '', '', 'engineer2@gmail.com', 'engineer2', '48959795e9762677fa062985f1e3b9e6', '1');
INSERT INTO `servicedesk`.`user` (`USER_ID`, `USER_NAME`, `USER_POSITION`, `USER_PHONE`, `USER_MOBILE`, `USER_MAIL`, `USER_LOGIN`, `USER_PASSWORD`, `USER_ROLE`) VALUES ('', 'Engineer3', '', '', '', 'engineer3@gmail.com', 'engineer3', '2b3cc30fac25aa7670df0e10c2372c6e', '1');
INSERT INTO `servicedesk`.`user` (`USER_ID`, `USER_NAME`, `USER_POSITION`, `USER_PHONE`, `USER_MOBILE`, `USER_MAIL`, `USER_LOGIN`, `USER_PASSWORD`, `USER_ROLE`) VALUES ('', 'Client2', '', '', '', 'client2@gmail.com', 'client2', '2c66045d4e4a90814ce9280272e510ec', '2');
INSERT INTO `servicedesk`.`user` (`USER_ID`, `USER_NAME`, `USER_POSITION`, `USER_PHONE`, `USER_MOBILE`, `USER_MAIL`, `USER_LOGIN`, `USER_PASSWORD`, `USER_ROLE`) VALUES ('', 'Client3', '', '', '', 'client3@gmail.com', 'client3', 'c27af3f6460eb10979adb366fc7f6856', '2');
INSERT INTO `servicedesk`.`user` (`USER_ID`, `USER_NAME`, `USER_POSITION`, `USER_PHONE`, `USER_MOBILE`, `USER_MAIL`, `USER_LOGIN`, `USER_PASSWORD`, `USER_ROLE`) VALUES ('', 'Operator', '', '', '', 'operator@gmail.com', 'operator', '4b583376b2767b923c3e1da60d10de59', '0');

INSERT INTO `servicedesk`.`user_group` (`USER_ID`, `GROUP_ID`) VALUES ('4', '1');
INSERT INTO `servicedesk`.`user_group` (`USER_ID`, `GROUP_ID`) VALUES ('2', '2');
INSERT INTO `servicedesk`.`user_group` (`USER_ID`, `GROUP_ID`) VALUES ('5', '3');

INSERT INTO `servicedesk`.`user_project` (`USER_ID`, `PROJECT_ID`) VALUES ('1', '1');
INSERT INTO `servicedesk`.`user_project` (`USER_ID`, `PROJECT_ID`) VALUES ('6', '2');
INSERT INTO `servicedesk`.`user_project` (`USER_ID`, `PROJECT_ID`) VALUES ('7', '3');

USE `servicedesk`;
DROP procedure IF EXISTS `searchByUserId`;

DELIMITER $$
USE `servicedesk`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `searchByUserId`(IN column_name varchar(45), IN user_id bigint, IN status_id bigint, IN search_criteria varchar(45), IN searchString varchar(45), IN searchId bigint)
BEGIN
SELECT request.REQUEST_ID, request.REQUEST_THEME, status.STATUS_NAME,
            request.REQUEST_PRIORITY_ID, engineer_group.GROUP_NAME, engineer.USER_NAME as ENGINEER_NAME, project.PROJECT_NAME, client.USER_NAME as CLIENT_NAME,
            author_of_creation.USER_NAME as AUTHOR_OF_CREATION_NAME, request.REQUEST_DATE_OF_CREATION, author_of_decision.USER_NAME as AUTHOR_OF_DECISION_NAME , request.REQUEST_DATE_OF_DECISION
            FROM  request  LEFT OUTER JOIN status
            ON  request.REQUEST_STATUS_ID =status.STATUS_ID 
		    LEFT OUTER JOIN engineer_group 
             ON  request.ENGINEER_GROUP_ID=engineer_group.GROUP_ID 
             LEFT OUTER JOIN user as engineer
             ON request.ENGINEER_USER_ID=engineer.USER_ID 
             LEFT OUTER JOIN project 
              ON  request.PROJECT_ID=project.PROJECT_ID 
             LEFT OUTER JOIN user as client
              ON request.CLIENT_USER_ID=client.USER_ID 
             LEFT OUTER JOIN user as author_of_creation 
              ON request.REQUEST_AUTHOR_OF_CREATION=author_of_creation.USER_ID
              LEFT OUTER JOIN user as author_of_decision
              ON request.REQUEST_AUTHOR_OF_DECISION=author_of_decision.USER_ID where case column_name when
              'request.REQUEST_AUTHOR_OF_CREATION' then request.REQUEST_AUTHOR_OF_CREATION = user_id
              when
              'request.ENGINEER_USER_ID' then request.ENGINEER_USER_ID = user_id
               when
              'request.CLIENT_USER_ID' then request.CLIENT_USER_ID = user_id
               when
              'user' then request.CLIENT_USER_ID=user_id or request.ENGINEER_USER_ID = user_id or request.REQUEST_AUTHOR_OF_CREATION=user_id
               when
              'request.REQUEST_STATUS_ID' then ((request.CLIENT_USER_ID=user_id or request.ENGINEER_USER_ID = user_id or request.REQUEST_AUTHOR_OF_CREATION=user_id) and (request.REQUEST_STATUS_ID=status_id)) 
              end
               and 
             (case search_criteria when 'request.REQUEST_THEME' then request.REQUEST_THEME
             when 'status.STATUS_NAME' then status.STATUS_NAME 
             when 'engineer_group.GROUP_NAME' then engineer_group.GROUP_NAME 
             when 'engineer.USER_NAME' then engineer.USER_NAME
             when 'project.PROJECT_NAME' then project.PROJECT_NAME
             when 'client.USER_NAME' then client.USER_NAME
             when 'author_of_creation.USER_NAME' then author_of_creation.USER_NAME
		     when 'author_of_decision.USER_NAME' then author_of_decision.USER_NAME
             when 'request.REQUEST_DATE_OF_CREATION' then request.REQUEST_DATE_OF_CREATION
             when 'request.REQUEST_DATE_OF_DECISION' then request.REQUEST_DATE_OF_DECISION
             end like concat('%', searchString, '%')
             or case search_criteria when 'request.REQUEST_PRIORITY_ID' then request.REQUEST_PRIORITY_ID
             when 'request.REQUEST_ID' then request.REQUEST_ID
end like concat('%', searchId, '%')) ORDER BY request.REQUEST_ID DESC;
END$$

DELIMITER ;

USE `servicedesk`;
DROP procedure IF EXISTS `searchByOperator`;

DELIMITER $$
USE `servicedesk`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `searchByOperator`(IN search_criteria varchar(45), IN searchString varchar(45), IN searchId bigint)
BEGIN
SELECT request.REQUEST_ID, request.REQUEST_THEME, status.STATUS_NAME,
            request.REQUEST_PRIORITY_ID, engineer_group.GROUP_NAME, engineer.USER_NAME as ENGINEER_NAME, project.PROJECT_NAME, client.USER_NAME as CLIENT_NAME,
            author_of_creation.USER_NAME as AUTHOR_OF_CREATION_NAME, request.REQUEST_DATE_OF_CREATION, author_of_decision.USER_NAME as AUTHOR_OF_DECISION_NAME , request.REQUEST_DATE_OF_DECISION
            FROM  request  LEFT OUTER JOIN status
           
           ON  request.REQUEST_STATUS_ID =status.STATUS_ID 
            LEFT OUTER JOIN engineer_group 
             ON  request.ENGINEER_GROUP_ID=engineer_group.GROUP_ID 
             LEFT OUTER JOIN user as engineer
             ON request.ENGINEER_USER_ID=engineer.USER_ID 
             LEFT OUTER JOIN project 
              ON  request.PROJECT_ID=project.PROJECT_ID 
             LEFT OUTER JOIN user as client
              ON request.CLIENT_USER_ID=client.USER_ID 
             LEFT OUTER JOIN user as author_of_creation 
              ON request.REQUEST_AUTHOR_OF_CREATION=author_of_creation.USER_ID
              LEFT OUTER JOIN user as author_of_decision
              ON request.REQUEST_AUTHOR_OF_DECISION=author_of_decision.USER_ID where 
			case search_criteria when 'request.REQUEST_THEME' then request.REQUEST_THEME
             when 'status.STATUS_NAME' then status.STATUS_NAME 
             when 'engineer_group.GROUP_NAME' then engineer_group.GROUP_NAME 
             when 'engineer.USER_NAME' then engineer.USER_NAME
             when 'project.PROJECT_NAME' then project.PROJECT_NAME
             when 'client.USER_NAME' then client.USER_NAME
             when 'author_of_creation.USER_NAME' then author_of_creation.USER_NAME
		     when 'author_of_decision.USER_NAME' then author_of_decision.USER_NAME
             when 'request.REQUEST_DATE_OF_CREATION' then request.REQUEST_DATE_OF_CREATION
             when 'request.REQUEST_DATE_OF_DECISION' then request.REQUEST_DATE_OF_DECISION
             end like concat('%', searchString, '%')
             or case search_criteria when 'request.REQUEST_PRIORITY_ID' then request.REQUEST_PRIORITY_ID
             when 'request.REQUEST_ID' then request.REQUEST_ID
end like concat('%', searchId, '%') ORDER BY request.REQUEST_ID DESC;
END$$

DELIMITER ;












