CREATE TABLE TASK(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(30) NOT NULL,
    DESCRIPTION VARCHAR(200) NOT NULL,
    GROUPS ENUM('ADMINISTRATION', 'SECURITY', 'DEVELOPMENT', 'ARCHITECTURE', 'OTHER'),
    STATUS ENUM('CREATED', 'IN_PROGRESS', 'IN_TEST', 'DONE'),
    ASSIGNEE VARCHAR(30),
    DURATION BIGINT NOT NULL
    );

CREATE TABLE SUBTASK(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(30) NOT NULL,
    DESCRIPTION VARCHAR(200) NOT NULL,
    STATUS ENUM('CREATED', 'IN_PROGRESS', 'IN_TEST', 'DONE'),
    TASK_ID BIGINT NOT NULL,
    FOREIGN KEY (TASK_ID) REFERENCES TASK(ID)
);