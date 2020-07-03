-- Generado por Oracle SQL Developer Data Modeler 19.4.0.350.1424
--   en:        2020-07-02 13:27:33 CLT
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



CREATE TABLE addresses (
    address_id             INTEGER NOT NULL,
    country                VARCHAR2(25) NOT NULL,
    city                   VARCHAR2(40) NOT NULL,
    street                 VARCHAR2(50) NOT NULL,
    "number"               INTEGER NOT NULL,
    block                  VARCHAR2(3),
    customers_customer_id  INTEGER NOT NULL
);

ALTER TABLE addresses ADD CONSTRAINT addresses_pk PRIMARY KEY ( address_id );

CREATE TABLE customers (
    customer_id   INTEGER NOT NULL,
    name          VARCHAR2(20),
    last_name     VARCHAR2(30),
    email         VARCHAR2(35) NOT NULL,
    password      VARCHAR2(20) NOT NULL,
    phone_number  VARCHAR2(15) NOT NULL
);

ALTER TABLE customers ADD CONSTRAINT customers_pk PRIMARY KEY ( customer_id );

CREATE TABLE employees (
    employee_id   INTEGER NOT NULL,
    name          VARCHAR2(20),
    last_name     VARCHAR2(30),
    email         VARCHAR2(35) NOT NULL,
    password      VARCHAR2(20) NOT NULL,
    phone_number  VARCHAR2(15) NOT NULL
);

ALTER TABLE employees ADD CONSTRAINT employees_pk PRIMARY KEY ( employee_id );

CREATE TABLE payments (
    payment_id             INTEGER NOT NULL,
    "date"                 DATE NOT NULL,
    amount                 NUMBER NOT NULL,
    ready                  CHAR(1) NOT NULL,
    customers_customer_id  INTEGER NOT NULL
);

ALTER TABLE payments ADD CONSTRAINT payments_pk PRIMARY KEY ( payment_id );

CREATE TABLE summaries (
    summary_id   INTEGER NOT NULL,
    description  VARCHAR2(200),
    rating       INTEGER NOT NULL,
    "date"       DATE NOT NULL
);

ALTER TABLE summaries ADD CONSTRAINT summaries_pk PRIMARY KEY ( summary_id );

CREATE TABLE visits (
    visit_id               INTEGER NOT NULL,
    ready                  CHAR(1) NOT NULL,
    customers_customer_id  INTEGER NOT NULL,
    "date"                 DATE NOT NULL,
    employees_employee_id  INTEGER,
    activities             VARCHAR2(200),
    summaries_summary_id   INTEGER NOT NULL
);

ALTER TABLE visits
    ADD CHECK ( ( employees_employee_id IS NULL
                  AND activities IS NULL )
                OR ( employees_employee_id IS NOT NULL
                     AND activities IS NOT NULL ) );

CREATE UNIQUE INDEX visits__idx ON
    visits (
        summaries_summary_id
    ASC );

ALTER TABLE visits ADD CONSTRAINT visits_pk PRIMARY KEY ( visit_id );

ALTER TABLE addresses
    ADD CONSTRAINT addresses_customers_fk FOREIGN KEY ( customers_customer_id )
        REFERENCES customers ( customer_id )
            ON DELETE CASCADE;

ALTER TABLE payments
    ADD CONSTRAINT payments_customers_fk FOREIGN KEY ( customers_customer_id )
        REFERENCES customers ( customer_id )
            ON DELETE CASCADE;

ALTER TABLE visits
    ADD CONSTRAINT visits_customers_fk FOREIGN KEY ( customers_customer_id )
        REFERENCES customers ( customer_id )
            ON DELETE CASCADE;

ALTER TABLE visits
    ADD CONSTRAINT visits_employees_fk FOREIGN KEY ( employees_employee_id )
        REFERENCES employees ( employee_id )
            ON DELETE CASCADE;

ALTER TABLE visits
    ADD CONSTRAINT visits_summaries_fk FOREIGN KEY ( summaries_summary_id )
        REFERENCES summaries ( summary_id )
            ON DELETE CASCADE;
            
-- Sequences and triggers

CREATE SEQUENCE customers_id_seq START WITH 1 INCREMENT BY 1;

CREATE TRIGGER customers_id_tri
BEFORE INSERT ON customers
FOR EACH ROW
BEGIN
SELECT customers_id_seq.NEXTVAL INTO :NEW.customer_id FROM DUAL;
END;

CREATE SEQUENCE employees_id_seq START WITH 1 INCREMENT BY 1;

CREATE TRIGGER employees_id_tri
BEFORE INSERT ON employees
FOR EACH ROW
BEGIN
SELECT employees_id_seq.NEXTVAL INTO :NEW.employee_id FROM DUAL;
END;

CREATE SEQUENCE visits_id_seq START WITH 1 INCREMENT BY 1;

CREATE TRIGGER visits_id_tri
BEFORE INSERT ON visits
FOR EACH ROW
BEGIN
SELECT visits_id_seq.NEXTVAL INTO :NEW.visit_id FROM DUAL;
END;

CREATE SEQUENCE addresses_id_seq START WITH 1 INCREMENT BY 1;

CREATE TRIGGER addresses_id_tri
BEFORE INSERT ON addresses
FOR EACH ROW
BEGIN
SELECT addresses_id_seq.NEXTVAL INTO :NEW.address_id FROM DUAL;
END;

CREATE SEQUENCE payments_id_seq START WITH 1 INCREMENT BY 1;

CREATE TRIGGER payments_id_tri
BEFORE INSERT ON payments
FOR EACH ROW
BEGIN
SELECT payments_id_seq.NEXTVAL INTO :NEW.payment_id FROM DUAL;
END;

CREATE SEQUENCE summaries_id_seq START WITH 1 INCREMENT BY 1;

CREATE TRIGGER summaries_id_tri
BEFORE INSERT ON summaries
FOR EACH ROW
BEGIN
SELECT summaries_id_seq.NEXTVAL INTO :NEW.summary_id FROM DUAL;
END;



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             6
-- CREATE INDEX                             1
-- ALTER TABLE                             12
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
