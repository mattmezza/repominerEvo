USE repominer;

-- Crea il progetto
INSERT INTO `projects` (`id`, `name`, `versioning_url`) VALUES (1, 'Progetto 26', 'https://github.com/mattmezza/repominerEvo.git');

-- Crea il packaged con nome vuoto
INSERT INTO `import` (`id`, `name`) VALUES (1, '');
INSERT INTO `source_containers` (`id`, `project`, `import_id`) VALUES (1, 1, 1);

-- Crea la classe
INSERT INTO `types` (`id`, `lines_number`, `src_file_location`, `source_container`) VALUES (1, 12, 'Primo.java', 1);
INSERT INTO `types` (`id`, `lines_number`, `src_file_location`, `source_container`) VALUES (2, 24, 'Secondo.java', 1);
INSERT INTO `types` (`id`, `lines_number`, `src_file_location`, `source_container`) VALUES (3, 432, 'Terzo.java', 1);

-- Crea la lista di cambiamenti
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(1, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-08-13', 'a@b.it', 'Alfa', 'Bug-fix', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES
						(2, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-08-15', 'a@b.it', 'Alfa', 'Refactoring', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES
						(3, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-08-23', 'a@b.it', 'Alfa', 'FI', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES
						(4, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-08-30', 'a@b.it', 'Alfa', 'Refactoring', 1);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(1, 1, 'Primo.java', 12, 13);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(2, 2, 'Secondo.java', 24, 12);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(3, 3, 'Terzo.java', 233, 12);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(4, 4, 'Terzo.java', 233, 34);