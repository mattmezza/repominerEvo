USE repominer;

-- Crea il progetto
DELETE FROM `changes_for_commit` WHERE id > 0;
DELETE FROM `changes` WHERE id > 0;
DELETE FROM `types` WHERE id > 0;
DELETE FROM `source_containers` WHERE id > 0;
DELETE FROM `import` WHERE id > 0;
DELETE FROM `projects` WHERE id > 0;