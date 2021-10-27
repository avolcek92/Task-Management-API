CREATE TABLE TASK
(
    ID             BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME           VARCHAR(30)  NOT NULL,
    DESCRIPTION    VARCHAR(200) NOT NULL,
    GROUPS         ENUM('FRONTEND', 'BACKEND', 'DATABASE', 'ARCHITECTURE', 'BUSINESS', 'OTHER'),
    STATUS         ENUM('CREATED', 'IN_PROGRESS', 'IN_TEST', 'DONE'),
    ASSIGNEE       VARCHAR(30),
    DURATION       BIGINT   DEFAULT NULL,
    CREATED_AT     DATETIME     NOT NULL,
    MODIFIED_AT    DATETIME DEFAULT NULL,
    IN_PROGRESS_AT DATETIME DEFAULT NULL,
    IN_TEST_AT     DATETIME DEFAULT NULL,
    DONE_AT        DATETIME DEFAULT NULL
);

CREATE TABLE SUBTASK
(
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME        VARCHAR(30)  NOT NULL,
    DESCRIPTION VARCHAR(200) NOT NULL,
    STATUS      ENUM('CREATED', 'IN_PROGRESS', 'IN_TEST', 'DONE'),
    TASK_ID     BIGINT       NOT NULL,
    CREATED_AT  DATETIME     NOT NULL,
    MODIFIED_AT DATETIME DEFAULT NULL,
    FOREIGN KEY (TASK_ID) REFERENCES TASK (ID)
);


INSERT INTO TASK (ID, NAME, DESCRIPTION, GROUPS, STATUS, ASSIGNEE, DURATION, CREATED_AT, MODIFIED_AT, IN_PROGRESS_AT,
                  IN_TEST_AT, DONE_AT)
VALUES (1, 'Create header', 'Need to create header for main page', 'FRONTEND', 'CREATED', 'Kevin Durant', NULL,
        '2021-10-15 10:15:16', NULL, NULL, NULL, NULL),
       (2, 'Create endpoint', 'Need to create endpoint to get currency', 'BACKEND', 'IN_PROGRESS', 'Piter Parker', '2',
        '2021-10-16 11:12:45', '2021-10-17 15:15:20', '2021-10-17 15:15:20', NULL, NULL),
       (3, 'Create table', 'Need to create currency table', 'DATABASE', 'IN_PROGRESS', 'Harry Potter', '2',
        '2021-10-11 08:12:45', '2021-10-17 15:15:25', '2021-10-17 15:15:25', NULL, NULL),
       (4, 'Create footer', 'Need to create footer for main page', 'FRONTEND', 'IN_PROGRESS', 'Kevin Durant', '2',
        '2021-09-11 13:12:35', '2021-10-01 12:36:25', '2021-10-01 12:36:25', NULL, NULL),
       (5, 'Create table', 'Need to create amount table', 'DATABASE', 'IN_PROGRESS', 'Harry Potter', '11',
        '2021-10-11 08:12:45', '2021-10-17 15:15:25', '2021-10-17 15:15:25', NULL, NULL),
       (6, 'Create table', 'Need to create rate table', 'DATABASE', 'DONE', 'Harry Potter', '3', '2021-10-03 07:13:25',
        '2021-10-05 08:32:15', '2021-10-04 11:13:25', '2021-10-05 06:12:25', '2021-10-05 08:32:15'),
       (7, 'Clarify requirements', 'Need to clarify main page mockup', 'BUSINESS', 'IN_PROGRESS', 'Bart Simpson', '4',
        '2021-10-05 06:12:45', '2021-10-06 15:25:25', '2021-10-17 15:15:25', NULL, NULL),
       (8, 'Refactor', 'Refactor currency service', 'BACKEND', 'IN_TEST', 'Napoleon I', '5', '1815-10-03 10:13:25',
        '1820-05-05 17:05:05', '1819-05-05 17:05:05', '1820-05-05 17:05:05', NULL);

INSERT INTO SUBTASK (ID, NAME, DESCRIPTION, STATUS, CREATED_AT, MODIFIED_AT, TASK_ID)
VALUES (1, 'Refactor create', 'Refactor create endpoint', 'DONE', '1815-10-03 10:13:25', '1816-10-03 10:13:25', '8'),
       (2, 'Refactor delete', 'Refactor create endpoint', 'IN_TEST', '1815-10-03 10:13:25', '1816-11-03 10:13:25', '8'),
       (3, 'Create DTO', 'Create currency DTO', 'IN_PROGRESS', '2021-10-16 11:12:45', '2021-10-17 15:12:45', '2'),
       (4, 'Create service', 'Create currency Service', 'DONE', '2021-10-16 11:12:45', '2021-10-18 11:12:45', '2'),
       (5, 'Create DAO', 'Create currency DAO', 'DONE', '2021-10-16 11:12:45', '2021-10-19 11:12:45', '2');

