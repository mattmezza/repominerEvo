USE repominer;

-- Crea il progetto
INSERT INTO `projects` (`id`, `name`, `versioning_url`) VALUES (1, 'Progetto 11', 'https://github.com/mattmezza/repominerEvo.git');

-- Crea il packaged con nome vuoto
INSERT INTO `import` (`id`, `name`) VALUES (1, '');
INSERT INTO `source_containers` (`id`, `project`, `import_id`) VALUES (1, 1, 1);

-- Crea la classe
INSERT INTO `types` (`id`, `lines_number`, `src_file_location`, `source_container`) VALUES (1, 0, 'Primo.pdf', 1);