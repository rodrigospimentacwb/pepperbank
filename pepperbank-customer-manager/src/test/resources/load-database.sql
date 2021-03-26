DELETE FROM phone;
DELETE FROM customer;

INSERT INTO pepperbank.customer (id, name, cpf, email, birthdate) VALUES('57d0169a-587f-4971-b603-8b7c3fe52739', 'Galileu Galilei', '44265852017', 'galileu.galilei@astronomia.org', '1984-03-08');
INSERT INTO pepperbank.customer (id, name, cpf, email, birthdate) VALUES('55da4b84-b983-49d2-8844-b61ff7c679a2', 'Isaac Newton', '17193944070', 'isaac.newton@gravitacao.com', '1990-06-07');

INSERT INTO pepperbank.phone (id, ddd, phone, customer_id) VALUES('6195421c-85e7-11eb-8dcd-0242ac130003', '41', '99998888', '57d0169a-587f-4971-b603-8b7c3fe52739');
INSERT INTO pepperbank.phone (id, ddd, phone, customer_id) VALUES('b2fd8af6-85e7-11eb-8dcd-0242ac130003', '45', '32324343', '57d0169a-587f-4971-b603-8b7c3fe52739');

INSERT INTO pepperbank.phone (id, ddd, phone, customer_id) VALUES('22ddc34a-85e8-11eb-8dcd-0242ac130003', '41', '99998888', '55da4b84-b983-49d2-8844-b61ff7c679a2');
INSERT INTO pepperbank.phone (id, ddd, phone, customer_id) VALUES('2b00c50e-85e8-11eb-8dcd-0242ac130003', '45', '32324343', '55da4b84-b983-49d2-8844-b61ff7c679a2');

