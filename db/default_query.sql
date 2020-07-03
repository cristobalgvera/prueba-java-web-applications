-- Inserción de clientes de prueba

INSERT INTO CUSTOMERS (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Cristóbal', 'Gajardo Vera', 'cristobalgajardo.v@gmail.com', '12345', '+56967508137');
INSERT INTO CUSTOMERS (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Soledad', 'Castro Ejivaja', 'soledad.castro.ejivaja@gmail.com', '54321', '+56950395412');
INSERT INTO CUSTOMERS (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Esteban', 'Piro', 'esteban.piro@adalid.cl', '98765', '+5698712345');
INSERT INTO CUSTOMERS (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Valeria', 'Lira', 'valeria.lira@gmail.com', '56789', '+5687659483');
INSERT INTO CUSTOMERS (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Fernando', 'Hernández', 'fernando.hernandez@fullstack.cl', '98765', '+5697906958');

-- Inserción de empleados de prueba

INSERT INTO EMPLOYEES (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('César', 'Bertuline', 'cbertuline@gmail.com', '12345', '+56971402734');
INSERT INTO EMPLOYEES (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Skandar', 'Soler', 'skasoler@gmail.com', '54321', '+56954203472');
INSERT INTO EMPLOYEES (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Daniela', 'Corcuera', 'daniela.corcuera@youtube.com', '98765', '+56984739238');
INSERT INTO EMPLOYEES (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Martín', 'Ríos', 'martin.rios@live.cl', '98765', '+56987960594');
INSERT INTO EMPLOYEES (NAME, LAST_NAME, EMAIL, PASSWORD, PHONE_NUMBER)
VALUES ('Paolo', 'Rivas', 'paolo.rivas@outlook.com', '98765', '+56975648372');

-- Inserción de direcciones de clientes

INSERT INTO ADDRESSES (COUNTRY, CITY, STREET, "number", CUSTOMERS_CUSTOMER_ID)
VALUES ('Chile', 'Temuco', 'Olimpia', 0718, 1);
INSERT INTO ADDRESSES (COUNTRY, CITY, STREET, "number", "BLOCK", CUSTOMERS_CUSTOMER_ID)
VALUES ('Chile', 'Temuco', 'Caupolicán', 1918, 'B', 2);
INSERT INTO ADDRESSES (COUNTRY, CITY, STREET, "number", "BLOCK", CUSTOMERS_CUSTOMER_ID)
VALUES ('Chile', 'Rancagua', 'León Gallo', 0641, '3', 3);
INSERT INTO ADDRESSES (COUNTRY, CITY, STREET, "number", CUSTOMERS_CUSTOMER_ID)
VALUES ('Chile', 'Cañete', 'Anibal Pinto', 32, 4);
INSERT INTO ADDRESSES (COUNTRY, CITY, STREET, "number", CUSTOMERS_CUSTOMER_ID)
VALUES ('Chile', 'Angol', 'Ginebra', 8932, 5);

-- Inserción de visitas solicitadas y realizadas

---- Creación de resúmenes

INSERT INTO SUMMARIES (DESCRIPTION, RATING, "date")
VALUES ('Visita pendiente', -1, TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'));
INSERT INTO SUMMARIES (DESCRIPTION, RATING, "date")
VALUES ('Visita exitosa donde fueron vistos los puntos mencionados en el informe a cabalidad y de manera correcta', 8,
        TO_DATE('02/05/2020 16:02:44', 'dd/mm/yyyy hh24:mi:ss'));
INSERT INTO SUMMARIES (DESCRIPTION, RATING, "date")
VALUES ('Visita pendiente', -1, TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'));
INSERT INTO SUMMARIES (DESCRIPTION, RATING, "date")
VALUES ('Visita concluida con problemas por tal y tal motivo', 3,
        TO_DATE('01/07/2020 12:08:17', 'dd/mm/yyyy hh24:mi:ss'));
INSERT INTO SUMMARIES (DESCRIPTION, RATING, "date")
VALUES ('Visita pendiente', -1, TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'));

-- Creación de cobros

INSERT INTO PAYMENTS ("date", AMOUNT, READY)
VALUES (TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'), 180000, 0);
INSERT INTO PAYMENTS ("date", AMOUNT, READY)
VALUES (TO_DATE('14/04/2020 16:02:44', 'dd/mm/yyyy hh24:mi:ss'), 200000, 1);
INSERT INTO PAYMENTS ("date", AMOUNT, READY)
VALUES (TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'), 120000, 0);
INSERT INTO PAYMENTS ("date", AMOUNT, READY)
VALUES (TO_DATE('28/07/2020 15:08:17', 'dd/mm/yyyy hh24:mi:ss'), 100000, 1);
INSERT INTO PAYMENTS ("date", AMOUNT, READY)
VALUES (TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'), 250000, 0);

-- Creación de visitas

INSERT INTO VISITS (READY, CUSTOMERS_CUSTOMER_ID, "date", EMPLOYEES_EMPLOYEE_ID, ACTIVITIES, SUMMARIES_SUMMARY_ID,
                    PAYMENTS_PAYMENT_ID)
VALUES (0, 3, TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'), 1,
        'Hacer esto;Hacer esto otro;Ahora hacer esto;Luego hacer esto', 1, 1);
INSERT INTO VISITS (READY, CUSTOMERS_CUSTOMER_ID, "date", EMPLOYEES_EMPLOYEE_ID, ACTIVITIES, SUMMARIES_SUMMARY_ID,
                    PAYMENTS_PAYMENT_ID)
VALUES (1, 1, TO_DATE('14/04/2020 16:02:44', 'dd/mm/yyyy hh24:mi:ss'), 2,
        'Haremos algo;Luego ni idea;Pero entonces tendremos esto;Luego terminamos', 2, 2);
INSERT INTO VISITS (READY, CUSTOMERS_CUSTOMER_ID, "date", EMPLOYEES_EMPLOYEE_ID, ACTIVITIES, SUMMARIES_SUMMARY_ID,
                    PAYMENTS_PAYMENT_ID)
VALUES (0, 4, TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'), 5,
        'Asesorar a alguien;Hacer charla;Hacer un asado;Finalizar', 3, 3);
INSERT INTO VISITS (READY, CUSTOMERS_CUSTOMER_ID, "date", EMPLOYEES_EMPLOYEE_ID, ACTIVITIES, SUMMARIES_SUMMARY_ID,
                    PAYMENTS_PAYMENT_ID)
VALUES (1, 5, TO_DATE('28/07/2020 15:08:17', 'dd/mm/yyyy hh24:mi:ss'), 5,
        'Esto es de la visita id 4', 4, 4);
INSERT INTO VISITS (READY, CUSTOMERS_CUSTOMER_ID, "date", EMPLOYEES_EMPLOYEE_ID, ACTIVITIES, SUMMARIES_SUMMARY_ID,
                    PAYMENTS_PAYMENT_ID)
VALUES (0, 2, TO_DATE('03/08/2020 00:08:17', 'dd/mm/yyyy hh24:mi:ss'), 2,
        'Esto es de la visita id 5', 5, 5);

-- Seleccionar los empleados que tienen menos visitas registradas

SELECT EMPLOYEE_ID, COUNT(EMPLOYEE_ID)
FROM EMPLOYEES
         FULL JOIN VISITS V on EMPLOYEES.EMPLOYEE_ID = V.EMPLOYEES_EMPLOYEE_ID
GROUP BY EMPLOYEE_ID
HAVING COUNT(EMPLOYEE_ID) = (
    SELECT MIN(mycount)
    FROM (
             SELECT EMPLOYEE_ID, COUNT(EMPLOYEE_ID) mycount
             FROM EMPLOYEES
                      FULL JOIN VISITS V2 on EMPLOYEES.EMPLOYEE_ID = V2.EMPLOYEES_EMPLOYEE_ID
             GROUP BY EMPLOYEE_ID));

-- Seleccionar clientes que no han pagado sus deudas

SELECT CUSTOMER_ID, NAME, LAST_NAME, AMOUNT
FROM PAYMENTS
         INNER JOIN VISITS V on PAYMENTS.PAYMENT_ID = V.PAYMENTS_PAYMENT_ID
         INNER JOIN CUSTOMERS C2 on V.CUSTOMERS_CUSTOMER_ID = C2.CUSTOMER_ID
WHERE PAYMENTS.READY = 0;