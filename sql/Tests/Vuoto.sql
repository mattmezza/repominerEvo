USE repominer;

-- Crea il progetto
INSERT INTO `projects` (`id`, `name`, `versioning_url`) VALUES (1, 'Vuoto', 'https://github.com/mattmezza/repominerEvo.git');

-- Crea il packaged con nome vuoto
INSERT INTO `import` (`id`, `name`) VALUES (1, '');
INSERT INTO `source_containers` (`id`, `project`, `import_id`) VALUES (1, 1, 1);