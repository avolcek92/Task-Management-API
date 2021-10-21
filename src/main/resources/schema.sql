CREATE TABLE TASK(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(30) NOT NULL,
    DESCRIPTION VARCHAR(200) NOT NULL,
    GROUPS ENUM('FRONTEND', 'BACKEND', 'DATABASE', 'ARCHITECTURE', 'BUSINESS', 'OTHER'),
    STATUS ENUM('CREATED', 'IN_PROGRESS', 'IN_TEST', 'DONE'),
    ASSIGNEE VARCHAR(30),
    DURATION BIGINT DEFAULT NULL,
    CREATED_AT DATETIME NOT NULL,
    MODIFIED_AT DATETIME DEFAULT NULL,
    IN_PROGRESS_AT DATETIME DEFAULT NULL,
    IN_TEST_AT DATETIME DEFAULT NULL,
    DONE_AT DATETIME DEFAULT NULL
    );

CREATE TABLE SUBTASK(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(30) NOT NULL,
    DESCRIPTION VARCHAR(200) NOT NULL,
    STATUS ENUM('CREATED', 'IN_PROGRESS', 'IN_TEST', 'DONE'),
    TASK_ID BIGINT NOT NULL,
    CREATED_AT DATETIME NOT NULL,
    MODIFIED_AT DATETIME DEFAULT NULL,
    FOREIGN KEY (TASK_ID) REFERENCES TASK(ID)
);