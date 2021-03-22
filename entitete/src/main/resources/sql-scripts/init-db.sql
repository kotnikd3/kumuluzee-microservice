INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Petra', 'Kos', 'petrakos', 'petra.kos@hotmail.com');
INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Miha', 'Novak', 'mihanovak', 'miha.novak@gmail.com');
INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Aleks', 'Miklic', 'aleksmiklic', 'aleks.miklic@gmail.com');

INSERT INTO nakupovalni_seznam (naziv, opis, ustvarjen, uporabnik_id) VALUES ('seznam knjig', 'knjige', '2020-05-28T13:13:13.444Z', 1);
INSERT INTO nakupovalni_seznam (naziv, opis, ustvarjen, uporabnik_id) VALUES ('seznam monitorjev', 'monitorji', '2020-05-28T13:13:13.444Z', 2);
INSERT INTO nakupovalni_seznam (naziv, opis, ustvarjen, uporabnik_id) VALUES ('seznam racunalnikov', 'racunalniki', '2020-05-28T13:13:13.444Z', 3);

INSERT INTO artikel (naziv, opis, nakupovalni_seznam_id) VALUES ('trdi disk', 'hdd', 1);
INSERT INTO artikel (naziv, opis, nakupovalni_seznam_id) VALUES ('monitor', 'ips', 2);
INSERT INTO artikel (naziv, opis, nakupovalni_seznam_id) VALUES ('racunalnik', 'apple', 3);

INSERT INTO oznaka (naslov, opis) VALUES ('naslov oznake', 'to je nek opis');
INSERT INTO oznaka (naslov, opis) VALUES ('naslov druge oznake', 'spet nek opis');

INSERT INTO nakupovalni_seznam_oznaka (nakupovalni_seznam_id, oznaka_id) VALUES (1, 1);
INSERT INTO nakupovalni_seznam_oznaka (nakupovalni_seznam_id, oznaka_id) VALUES (2, 2);
