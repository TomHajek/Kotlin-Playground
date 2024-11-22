INSERT INTO task(`id`, `description`, `is_reminder_set`, `is_task_open`, `created_on`, `priority`)
VALUES (111, "first test to do", false, false, CURRENT_TIME(), `LOW`);

INSERT INTO task(`id`, `description`, `is_reminder_set`, `is_task_open`, `created_on`, `priority`)
VALUES (112, "second test to do", true, false, CURRENT_TIME(), `MEDIUM`);

INSERT INTO task(`id`, `description`, `is_reminder_set`, `is_task_open`, `created_on`, `priority`)
VALUES (113, "third test to do", true, true, CURRENT_TIME(), `HIGH`);