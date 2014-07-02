# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.13)
# Database: repominer
# Generation Time: 2014-07-02 12:17:29 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table catched_exceptions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `catched_exceptions`;

CREATE TABLE `catched_exceptions` (
  `method` int(11) DEFAULT NULL,
  `exception_type` int(11) DEFAULT NULL,
  KEY `method_id` (`method`),
  KEY `type_id` (`exception_type`),
  CONSTRAINT `catched_exc` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `exc_catch` FOREIGN KEY (`exception_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table changes
# ------------------------------------------------------------

DROP TABLE IF EXISTS `changes`;

CREATE TABLE `changes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hash` varchar(50) DEFAULT NULL,
  `commit_date` date DEFAULT NULL,
  `dev_mail` varchar(100) DEFAULT NULL,
  `dev_id` varchar(100) DEFAULT NULL,
  `message` varchar(1000) DEFAULT NULL,
  `project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project`),
  CONSTRAINT `change_project` FOREIGN KEY (`project`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table changes_for_commit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `changes_for_commit`;

CREATE TABLE `changes_for_commit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `change_hash` int(11) DEFAULT NULL,
  `modified_file` varchar(1024) DEFAULT NULL,
  `insertions` int(11) DEFAULT NULL,
  `deletions` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `change_hash_fk1` (`change_hash`),
  CONSTRAINT `change_hash_fk` FOREIGN KEY (`change_hash`) REFERENCES `changes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table class_invocations
# ------------------------------------------------------------

DROP TABLE IF EXISTS `class_invocations`;

CREATE TABLE `class_invocations` (
  `invoker_class` int(11) DEFAULT NULL,
  `invoked_class` int(11) DEFAULT NULL,
  KEY `invoker_class` (`invoker_class`),
  KEY `invoked_class` (`invoked_class`),
  CONSTRAINT `class_invocations_ibfk_1` FOREIGN KEY (`invoker_class`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_invocations_ibfk_2` FOREIGN KEY (`invoked_class`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table comments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` text,
  `belonging_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Type_id` (`belonging_type`),
  CONSTRAINT `comment_type` FOREIGN KEY (`belonging_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table extends
# ------------------------------------------------------------

DROP TABLE IF EXISTS `extends`;

CREATE TABLE `extends` (
  `subclass` int(10) DEFAULT NULL,
  `superclass` int(10) DEFAULT NULL,
  KEY `subclass` (`subclass`),
  KEY `superclass` (`superclass`),
  CONSTRAINT `extends_ibfk_1` FOREIGN KEY (`subclass`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `extends_ibfk_2` FOREIGN KEY (`superclass`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table field_comments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `field_comments`;

CREATE TABLE `field_comments` (
  `field` int(11) DEFAULT NULL,
  `comment` int(11) DEFAULT NULL,
  KEY `field_id` (`field`,`comment`),
  KEY `comments_id` (`comment`),
  CONSTRAINT `comment_field` FOREIGN KEY (`comment`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `field_comment` FOREIGN KEY (`field`) REFERENCES `fields` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table fields
# ------------------------------------------------------------

DROP TABLE IF EXISTS `fields`;

CREATE TABLE `fields` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `visibility` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `owner_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type`),
  KEY `owner_type` (`owner_type`),
  CONSTRAINT `field_type` FOREIGN KEY (`type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `owner_type` FOREIGN KEY (`owner_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table import
# ------------------------------------------------------------

DROP TABLE IF EXISTS `import`;

CREATE TABLE `import` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table imports
# ------------------------------------------------------------

DROP TABLE IF EXISTS `imports`;

CREATE TABLE `imports` (
  `importer` int(10) DEFAULT NULL,
  `imported` int(10) DEFAULT NULL,
  KEY `importer` (`importer`),
  KEY `imported` (`imported`),
  CONSTRAINT `imports_ibfk_1` FOREIGN KEY (`importer`) REFERENCES `import` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `imports_ibfk_2` FOREIGN KEY (`imported`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table issue_attachments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `issue_attachments`;

CREATE TABLE `issue_attachments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `belonging_issue` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ID_issue` (`belonging_issue`),
  CONSTRAINT `issue_attachment` FOREIGN KEY (`belonging_issue`) REFERENCES `issues` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table issue_comments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `issue_comments`;

CREATE TABLE `issue_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_id` varchar(100) DEFAULT NULL,
  `dev_mail` varchar(100) DEFAULT NULL,
  `text` varchar(1000) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `belonging_issue` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `issue_id` (`belonging_issue`),
  CONSTRAINT `attachment_issue` FOREIGN KEY (`belonging_issue`) REFERENCES `issues` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table issues
# ------------------------------------------------------------

DROP TABLE IF EXISTS `issues`;

CREATE TABLE `issues` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL,
  `priority` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `resolution` varchar(50) DEFAULT NULL,
  `affected_version` varchar(50) DEFAULT NULL,
  `component` varchar(50) DEFAULT NULL,
  `fix_version` varchar(50) DEFAULT NULL,
  `assignee` varchar(100) DEFAULT NULL,
  `reporter` varchar(100) DEFAULT NULL,
  `updated` date DEFAULT NULL,
  `created` date DEFAULT NULL,
  `closed` date DEFAULT NULL,
  `project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `projectID_proj` (`project`),
  CONSTRAINT `project_issue` FOREIGN KEY (`project`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table local_variables
# ------------------------------------------------------------

DROP TABLE IF EXISTS `local_variables`;

CREATE TABLE `local_variables` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `initialization` varchar(100) DEFAULT NULL,
  `belonging_method` int(11) DEFAULT NULL,
  `variable_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`variable_type`),
  KEY `method` (`belonging_method`),
  KEY `method_2` (`belonging_method`),
  CONSTRAINT `method_var` FOREIGN KEY (`variable_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `var_method` FOREIGN KEY (`belonging_method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table method_comments
# ------------------------------------------------------------

DROP TABLE IF EXISTS `method_comments`;

CREATE TABLE `method_comments` (
  `method` int(11) DEFAULT NULL,
  `comment` int(11) DEFAULT NULL,
  KEY `comment_id` (`comment`),
  KEY `method_id` (`method`),
  CONSTRAINT `comment_method` FOREIGN KEY (`comment`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `method_comment` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table method_invocations
# ------------------------------------------------------------

DROP TABLE IF EXISTS `method_invocations`;

CREATE TABLE `method_invocations` (
  `invoker_method` int(11) DEFAULT NULL,
  `invoked_method` int(11) DEFAULT NULL,
  KEY `callee` (`invoked_method`),
  KEY `caller` (`invoker_method`),
  CONSTRAINT `caller_method` FOREIGN KEY (`invoked_method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `method_caller` FOREIGN KEY (`invoker_method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table methods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `methods`;

CREATE TABLE `methods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lines_number` int(11) DEFAULT NULL,
  `is_constructor` varchar(1) DEFAULT NULL,
  `belonging_type` int(11) DEFAULT NULL,
  `return_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`belonging_type`),
  KEY `return_type` (`return_type`),
  CONSTRAINT `methods_ibfk_2` FOREIGN KEY (`return_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `method_type` FOREIGN KEY (`belonging_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table methods_change_in_commit
# ------------------------------------------------------------

DROP TABLE IF EXISTS `methods_change_in_commit`;

CREATE TABLE `methods_change_in_commit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified_method` varchar(1024) DEFAULT NULL,
  `proprietary_file` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `file_changed_fk` (`proprietary_file`),
  CONSTRAINT `prop_file_fk` FOREIGN KEY (`proprietary_file`) REFERENCES `changes_for_commit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table metrics
# ------------------------------------------------------------

DROP TABLE IF EXISTS `metrics`;

CREATE TABLE `metrics` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table metrics_method
# ------------------------------------------------------------

DROP TABLE IF EXISTS `metrics_method`;

CREATE TABLE `metrics_method` (
  `method` int(11) DEFAULT NULL,
  `metric` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  KEY `method` (`method`),
  KEY `metric` (`metric`),
  CONSTRAINT `metrics_method_ibfk_1` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `metrics_method_ibfk_2` FOREIGN KEY (`metric`) REFERENCES `metrics` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table package_metrics
# ------------------------------------------------------------

DROP TABLE IF EXISTS `package_metrics`;

CREATE TABLE `package_metrics` (
  `source_container` int(11) NOT NULL,
  `metric` int(11) NOT NULL,
  `value` double NOT NULL,
  `start` date NOT NULL,
  `end` date NOT NULL,
  PRIMARY KEY (`source_container`,`metric`),
  KEY `package_metrics_ibfk_2` (`metric`),
  CONSTRAINT `package_metrics_ibfk_1` FOREIGN KEY (`source_container`) REFERENCES `source_containers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `package_metrics_ibfk_2` FOREIGN KEY (`metric`) REFERENCES `metrics` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table parameters
# ------------------------------------------------------------

DROP TABLE IF EXISTS `parameters`;

CREATE TABLE `parameters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `belonging_method` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type`),
  KEY `method_id` (`belonging_method`),
  CONSTRAINT `method_parameter` FOREIGN KEY (`type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `parameter_method` FOREIGN KEY (`belonging_method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table project_metrics
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project_metrics`;

CREATE TABLE `project_metrics` (
  `project` int(11) NOT NULL,
  `metric` int(11) NOT NULL,
  `value` double NOT NULL,
  `start` date NOT NULL,
  `end` date NOT NULL,
  PRIMARY KEY (`project`,`metric`,`start`,`end`),
  KEY `project_metrics_ibfk_2` (`metric`),
  CONSTRAINT `project_metrics_ibfk_2` FOREIGN KEY (`metric`) REFERENCES `metrics` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table projects
# ------------------------------------------------------------

DROP TABLE IF EXISTS `projects`;

CREATE TABLE `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `versioning_url` varchar(255) DEFAULT NULL,
  `bugtracker_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table reviews
# ------------------------------------------------------------

DROP TABLE IF EXISTS `reviews`;

CREATE TABLE `reviews` (
  `versioning_url` varchar(255) NOT NULL,
  `name_app` varchar(150) NOT NULL,
  `author` varchar(150) NOT NULL,
  `title` varchar(150) NOT NULL,
  `review` varchar(800) NOT NULL,
  `rating` varchar(5) NOT NULL,
  `date` varchar(20) NOT NULL,
  UNIQUE KEY `name_app` (`name_app`,`author`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table source_containers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `source_containers`;

CREATE TABLE `source_containers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project` int(11) DEFAULT NULL,
  `import_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project`),
  KEY `import_id` (`import_id`),
  CONSTRAINT `container_project` FOREIGN KEY (`project`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `source_containers_ibfk_1` FOREIGN KEY (`import_id`) REFERENCES `import` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table throwed_exceptions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `throwed_exceptions`;

CREATE TABLE `throwed_exceptions` (
  `method` int(11) DEFAULT NULL,
  `exception_type` int(11) DEFAULT NULL,
  KEY `method_id` (`method`),
  KEY `type_id` (`exception_type`),
  CONSTRAINT `exc_throw` FOREIGN KEY (`exception_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `throw_exc` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table type_invocations
# ------------------------------------------------------------

DROP TABLE IF EXISTS `type_invocations`;

CREATE TABLE `type_invocations` (
  `invoker_class` int(11) DEFAULT NULL,
  `invoked_class` int(11) DEFAULT NULL,
  KEY `caller` (`invoker_class`),
  KEY `callee` (`invoked_class`),
  CONSTRAINT `callee_type` FOREIGN KEY (`invoked_class`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `caller_type` FOREIGN KEY (`invoker_class`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table types
# ------------------------------------------------------------

DROP TABLE IF EXISTS `types`;

CREATE TABLE `types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `import_id` int(11) DEFAULT NULL,
  `lines_number` int(11) DEFAULT NULL,
  `src_file_location` varchar(255) DEFAULT NULL,
  `header_file_location` varchar(255) DEFAULT NULL,
  `source_container` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `header_file_location` (`header_file_location`),
  KEY `import_id` (`import_id`),
  KEY `types_ibfk_2` (`source_container`),
  CONSTRAINT `types_ibfk_1` FOREIGN KEY (`import_id`) REFERENCES `import` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `types_ibfk_2` FOREIGN KEY (`source_container`) REFERENCES `source_containers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table types_metrics
# ------------------------------------------------------------

DROP TABLE IF EXISTS `types_metrics`;

CREATE TABLE `types_metrics` (
  `type` int(11) DEFAULT NULL,
  `metric` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  KEY `type` (`type`),
  KEY `metric` (`metric`),
  CONSTRAINT `types_metrics_ibfk_1` FOREIGN KEY (`type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `types_metrics_ibfk_2` FOREIGN KEY (`metric`) REFERENCES `metrics` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table used_fields
# ------------------------------------------------------------

DROP TABLE IF EXISTS `used_fields`;

CREATE TABLE `used_fields` (
  `method` int(11) DEFAULT NULL,
  `field_id` int(11) DEFAULT NULL,
  KEY `method_id` (`method`,`field_id`),
  KEY `field_id` (`field_id`),
  CONSTRAINT `field_used_method` FOREIGN KEY (`field_id`) REFERENCES `fields` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `method_use_field` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
