-- phpMyAdmin SQL Dump
-- version 3.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generato il: Dic 19, 2013 alle 11:43
-- Versione del server: 5.5.25a
-- Versione PHP: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sie`
--

DROP DATABASE IF EXISTS `repominer`;
CREATE DATABASE `repominer`;
USE `repominer`;
-- --------------------------------------------------------

--
-- Struttura della tabella `catched_exceptions`
--

CREATE TABLE IF NOT EXISTS `catched_exceptions` (
  `method` int(11) DEFAULT NULL,
  `exception_type` int(11) DEFAULT NULL,
  KEY `method_id` (`method`),
  KEY `type_id` (`exception_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `changes`
--

CREATE TABLE IF NOT EXISTS `changes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hash` varchar(50) DEFAULT NULL,
  `commit_date` date DEFAULT NULL,
  `dev_mail` varchar(100) DEFAULT NULL,
  `dev_id` varchar(100) DEFAULT NULL,
  `message` varchar(1000) DEFAULT NULL,
  `project` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `changes_for_commit`
--

CREATE TABLE IF NOT EXISTS `changes_for_commit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `change_hash` int(11) DEFAULT NULL,
  `modified_file` varchar(1024) DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `change_hash_fk1` (`change_hash`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `methods_change_in_commit`
--

CREATE TABLE IF NOT EXISTS `methods_change_in_commit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified_method` varchar(1024) DEFAULT NULL,
  `proprietary_file` int(11) DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `file_changed_fk` (`proprietary_file`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `class_invocations`
--

CREATE TABLE IF NOT EXISTS `class_invocations` (
  `invoker_class` int(11) DEFAULT NULL,
  `invoked_class` int(11) DEFAULT NULL,
  KEY `invoker_class` (`invoker_class`),
  KEY `invoked_class` (`invoked_class`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` TEXT,
  `belonging_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Type_id` (`belonging_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `extends`
--

CREATE TABLE IF NOT EXISTS `extends` (
  `subclass` int(10) DEFAULT NULL,
  `superclass` int(10) DEFAULT NULL,
  KEY `subclass` (`subclass`),
  KEY `superclass` (`superclass`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `fields`
--

CREATE TABLE IF NOT EXISTS `fields` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `visibility` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `owner_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type`),
  KEY `owner_type` (`owner_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `field_comments`
--

CREATE TABLE IF NOT EXISTS `field_comments` (
  `field` int(11) DEFAULT NULL,
  `comment` int(11) DEFAULT NULL,
  KEY `field_id` (`field`,`comment`),
  KEY `comments_id` (`comment`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Struttura della tabella `import`
--

CREATE TABLE IF NOT EXISTS `import` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `imports`
--

CREATE TABLE IF NOT EXISTS `imports` (
  `importer` int(10) DEFAULT NULL,
  `imported` int(10) DEFAULT NULL,
  KEY `importer` (`importer`),
  KEY `imported` (`imported`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `issues`
--

CREATE TABLE IF NOT EXISTS `issues` (
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
  UNIQUE KEY `projectID_proj` (`project`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `issue_attachments`
--

CREATE TABLE IF NOT EXISTS `issue_attachments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `belonging_issue` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ID_issue` (`belonging_issue`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `issue_comments`
--

CREATE TABLE IF NOT EXISTS `issue_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_id` varchar(100) DEFAULT NULL,
  `dev_mail` varchar(100) DEFAULT NULL,
  `text` varchar(1000) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `belonging_issue` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `issue_id` (`belonging_issue`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `local_variables`
--

CREATE TABLE IF NOT EXISTS `local_variables` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `initialization` varchar(100) DEFAULT NULL,
  `belonging_method` int(11) DEFAULT NULL,
  `variable_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`variable_type`),
  KEY `method` (`belonging_method`),
  KEY `method_2` (`belonging_method`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `methods`
--

CREATE TABLE IF NOT EXISTS `methods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lines_number` int(11) DEFAULT NULL,
  `is_constructor` varchar(1) DEFAULT NULL,
  `belonging_type` int(11) DEFAULT NULL,
  `return_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`belonging_type`),
  KEY `return_type` (`return_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `method_comments`
--

CREATE TABLE IF NOT EXISTS `method_comments` (
  `method` int(11) DEFAULT NULL,
  `comment` int(11) DEFAULT NULL,
  KEY `comment_id` (`comment`),
  KEY `method_id` (`method`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `method_invocations`
--

CREATE TABLE IF NOT EXISTS `method_invocations` (
  `invoker_method` int(11) DEFAULT NULL,
  `invoked_method` int(11) DEFAULT NULL,
  KEY `callee` (`invoked_method`),
  KEY `caller` (`invoker_method`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `metrics`
--

CREATE TABLE IF NOT EXISTS `metrics` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `metrics_method`
--

CREATE TABLE IF NOT EXISTS `metrics_method` (
  `method` int(11) DEFAULT NULL,
  `metric` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  KEY `method` (`method`),
  KEY `metric` (`metric`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `parameters`
--

CREATE TABLE IF NOT EXISTS `parameters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `belonging_method` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type`),
  KEY `method_id` (`belonging_method`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `projects`
--

CREATE TABLE IF NOT EXISTS `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `versioning_url` varchar(255) DEFAULT NULL,
  `bugtracker_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `reviews`
--

CREATE TABLE IF NOT EXISTS `reviews` (
  `versioning_url` varchar(255) NOT NULL,
  `name_app` varchar(150) NOT NULL,
  `author` varchar(150) NOT NULL,
  `title` varchar(150) NOT NULL,
  `review` varchar(800) NOT NULL,
  `rating` varchar(5) NOT NULL,
  `date` varchar(20) NOT NULL,
  UNIQUE KEY `name_app` (`name_app`,`author`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `source_containers`
--

CREATE TABLE IF NOT EXISTS `source_containers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project` int(11) DEFAULT NULL,
  `import_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project`),
  KEY `import_id` (`import_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `throwed_exceptions`
--

CREATE TABLE IF NOT EXISTS `throwed_exceptions` (
  `method` int(11) DEFAULT NULL,
  `exception_type` int(11) DEFAULT NULL,
  KEY `method_id` (`method`),
  KEY `type_id` (`exception_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `types`
--

CREATE TABLE IF NOT EXISTS `types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `import_id` int(11) DEFAULT NULL,
  `lines_number` int(11) DEFAULT NULL,
  `src_file_location` varchar(255) DEFAULT NULL,
  `header_file_location` varchar(255) DEFAULT NULL,
  `source_container` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `header_file_location` (`header_file_location`),
  KEY `import_id` (`import_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `types_metrics`
--

CREATE TABLE IF NOT EXISTS `types_metrics` (
  `type` int(11) DEFAULT NULL,
  `metric` int(11) DEFAULT NULL,
  `value` double DEFAULT NULL,
  KEY `type` (`type`),
  KEY `metric` (`metric`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `type_invocations`
--

CREATE TABLE IF NOT EXISTS `type_invocations` (
  `invoker_class` int(11) DEFAULT NULL,
  `invoked_class` int(11) DEFAULT NULL,
  KEY `caller` (`invoker_class`),
  KEY `callee` (`invoked_class`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `used_fields`
--

CREATE TABLE IF NOT EXISTS `used_fields` (
  `method` int(11) DEFAULT NULL,
  `field_id` int(11) DEFAULT NULL,
  KEY `method_id` (`method`,`field_id`),
  KEY `field_id` (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `catched_exceptions`
--
ALTER TABLE `catched_exceptions`
  ADD CONSTRAINT `catched_exc` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `exc_catch` FOREIGN KEY (`exception_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `changes`
--
ALTER TABLE `changes`
  ADD CONSTRAINT `change_project` FOREIGN KEY (`project`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `changes_for_commit`
--
ALTER TABLE `changes_for_commit`
  ADD CONSTRAINT `change_hash_fk` FOREIGN KEY (`change_hash`) REFERENCES `changes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

  --
-- Limiti per la tabella `changes_for_commit`
--
ALTER TABLE `methods_change_in_commit`
  ADD CONSTRAINT `prop_file_fk` FOREIGN KEY (`proprietary_file`) REFERENCES `changes_for_commit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `class_invocations`
--
ALTER TABLE `class_invocations`
  ADD CONSTRAINT `class_invocations_ibfk_2` FOREIGN KEY (`invoked_class`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `class_invocations_ibfk_1` FOREIGN KEY (`invoker_class`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comment_type` FOREIGN KEY (`belonging_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `extends`
--
ALTER TABLE `extends`
  ADD CONSTRAINT `extends_ibfk_2` FOREIGN KEY (`superclass`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `extends_ibfk_1` FOREIGN KEY (`subclass`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `fields`
--
ALTER TABLE `fields`
  ADD CONSTRAINT `owner_type` FOREIGN KEY (`owner_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `field_type` FOREIGN KEY (`type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `field_comments`
--
ALTER TABLE `field_comments`
  ADD CONSTRAINT `comment_field` FOREIGN KEY (`comment`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `field_comment` FOREIGN KEY (`field`) REFERENCES `fields` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `imports`
--
ALTER TABLE `imports`
  ADD CONSTRAINT `imports_ibfk_2` FOREIGN KEY (`imported`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `imports_ibfk_1` FOREIGN KEY (`importer`) REFERENCES `import` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `issues`
--
ALTER TABLE `issues`
  ADD CONSTRAINT `project_issue` FOREIGN KEY (`project`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `issue_attachments`
--
ALTER TABLE `issue_attachments`
  ADD CONSTRAINT `issue_attachment` FOREIGN KEY (`belonging_issue`) REFERENCES `issues` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `issue_comments`
--
ALTER TABLE `issue_comments`
  ADD CONSTRAINT `attachment_issue` FOREIGN KEY (`belonging_issue`) REFERENCES `issues` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `local_variables`
--
ALTER TABLE `local_variables`
  ADD CONSTRAINT `method_var` FOREIGN KEY (`variable_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `var_method` FOREIGN KEY (`belonging_method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `methods`
--
ALTER TABLE `methods`
  ADD CONSTRAINT `methods_ibfk_2` FOREIGN KEY (`return_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `method_type` FOREIGN KEY (`belonging_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `method_comments`
--
ALTER TABLE `method_comments`
  ADD CONSTRAINT `comment_method` FOREIGN KEY (`comment`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `method_comment` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `method_invocations`
--
ALTER TABLE `method_invocations`
  ADD CONSTRAINT `caller_method` FOREIGN KEY (`invoked_method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `method_caller` FOREIGN KEY (`invoker_method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `metrics_method`
--
ALTER TABLE `metrics_method`
  ADD CONSTRAINT `metrics_method_ibfk_2` FOREIGN KEY (`metric`) REFERENCES `metrics` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `metrics_method_ibfk_1` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `parameters`
--
ALTER TABLE `parameters`
  ADD CONSTRAINT `method_parameter` FOREIGN KEY (`type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `parameter_method` FOREIGN KEY (`belonging_method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `source_containers`
--
ALTER TABLE `source_containers`
  ADD CONSTRAINT `source_containers_ibfk_1` FOREIGN KEY (`import_id`) REFERENCES `import` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `container_project` FOREIGN KEY (`project`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `throwed_exceptions`
--
ALTER TABLE `throwed_exceptions`
  ADD CONSTRAINT `exc_throw` FOREIGN KEY (`exception_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `throw_exc` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `types`
--
ALTER TABLE `types`
  ADD CONSTRAINT `types_ibfk_2` FOREIGN KEY (`source_container`) REFERENCES `source_containers` (`id`),
  ADD CONSTRAINT `types_ibfk_1` FOREIGN KEY (`import_id`) REFERENCES `import` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `types_metrics`
--
ALTER TABLE `types_metrics`
  ADD CONSTRAINT `types_metrics_ibfk_1` FOREIGN KEY (`type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `types_metrics_ibfk_2` FOREIGN KEY (`metric`) REFERENCES `metrics` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `type_invocations`
--
ALTER TABLE `type_invocations`
  ADD CONSTRAINT `callee_type` FOREIGN KEY (`invoked_class`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `caller_type` FOREIGN KEY (`invoker_class`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `used_fields`
--
ALTER TABLE `used_fields`
  ADD CONSTRAINT `field_used_method` FOREIGN KEY (`field_id`) REFERENCES `fields` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `method_use_field` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
