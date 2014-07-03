USE repominer;

-- Crea il progetto
INSERT INTO `projects` (`id`, `name`, `versioning_url`) VALUES (1, 'Progetto Burst', 'https://github.com/mattmezza/repominerEvo.git');

-- Crea il packaged con nome vuoto
INSERT INTO `import` (`id`, `name`) VALUES (1, '');
INSERT INTO `source_containers` (`id`, `project`, `import_id`) VALUES (1, 1, 1);

-- Crea la classe
INSERT INTO `types` (`id`, `lines_number`, `src_file_location`, `source_container`) VALUES (1, 0, 'Primo.pdf', 1);
INSERT INTO `types` (`id`, `lines_number`, `src_file_location`, `source_container`) VALUES (2, 0, 'Secondo.pdf', 1);
INSERT INTO `types` (`id`, `lines_number`, `src_file_location`, `source_container`) VALUES (3, 0, 'Terzo.pdf', 1);
INSERT INTO `types` (`id`, `lines_number`, `src_file_location`, `source_container`) VALUES (4, 0, 'Quarto.pdf', 1);


-- Crea la lista di cambiamenti
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(1, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-08-13', 'a@b.it', 'Alfa', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(2, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-08-14', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(3, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-09-15', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(4, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-09-16', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(5, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-09-17', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(6, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-09-18', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(7, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-09-19', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(8, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-09-20', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(9, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-10-15', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(10, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-10-16', 'g@b.it', 'Gam', 'Modificato', 1);
INSERT INTO `changes` 	(`id`, `hash`, `commit_date`, `dev_mail`, `dev_id`, `message`, `project`) VALUES 
						(11, 'd54d3472148d60d0f9685b80144904b8a72e4dc6', '2013-11-17', 'g@b.it', 'Gam', 'Modificato', 1);

INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(1, 1, 'Primo.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(2, 2, 'Secondo.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(3, 3, 'Terzo.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(4, 4, 'Terzo.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(5, 5, 'Quarto.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(6, 6, 'Quarto.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(7, 7, 'Primo.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(8, 8, 'Terzo.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(9, 9, 'Secondo.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(10, 10, 'Secondo.pdf', 0, 0);
INSERT INTO `changes_for_commit` 	(`id`, `change_hash`, `modified_file`, `insertions`, `deletions`) VALUES 
									(11, 11, 'Primo.pdf', 0, 0);
