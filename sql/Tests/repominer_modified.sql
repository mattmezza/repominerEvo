-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Giu 04, 2014 alle 16:43
-- Versione del server: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `repominer`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Struttura della tabella `changes_for_commit`
--

CREATE TABLE IF NOT EXISTS `changes_for_commit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `change_hash` int(11) DEFAULT NULL,
  `modified_file` varchar(1024) DEFAULT NULL,
  `insertions` int(11) NOT NULL,
  `deletions` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `change_hash_fk1` (`change_hash`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
-- Struttura della tabella `methods_change_in_commit`
--

CREATE TABLE IF NOT EXISTS `methods_change_in_commit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modified_method` varchar(1024) DEFAULT NULL,
  `proprietary_file` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `file_changed_fk` (`proprietary_file`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  KEY `import_id` (`import_id`),
  KEY `types_ibfk_2` (`source_container`)
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

--
-- Limiti per le tabelle scaricate
--

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
-- Limiti per la tabella `methods`
--
ALTER TABLE `methods`
  ADD CONSTRAINT `methods_ibfk_2` FOREIGN KEY (`return_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `method_type` FOREIGN KEY (`belonging_type`) REFERENCES `types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `methods_change_in_commit`
--
ALTER TABLE `methods_change_in_commit`
  ADD CONSTRAINT `prop_file_fk` FOREIGN KEY (`proprietary_file`) REFERENCES `changes_for_commit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `metrics_method`
--
ALTER TABLE `metrics_method`
  ADD CONSTRAINT `metrics_method_ibfk_2` FOREIGN KEY (`metric`) REFERENCES `metrics` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `metrics_method_ibfk_1` FOREIGN KEY (`method`) REFERENCES `methods` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `source_containers`
--
ALTER TABLE `source_containers`
  ADD CONSTRAINT `source_containers_ibfk_1` FOREIGN KEY (`import_id`) REFERENCES `import` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `container_project` FOREIGN KEY (`project`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
