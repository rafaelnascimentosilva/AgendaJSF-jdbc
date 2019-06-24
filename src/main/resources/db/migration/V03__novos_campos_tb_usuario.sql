ALTER TABLE ag_contato
DROP CONSTRAINT ag_contato_id_usuario_fkey;

DROP TABLE IF EXISTS ag_usuario;
CREATE TABLE ag_usuario(
id_usuario SERIAL   NOT NULL,
nome 	VARCHAR(45) NOT NULL,
dt_nasc DATE 	    NOT NULL,
sexo    CHAR(2)     NOT NULL,
fone 	VARCHAR(13) NOT NULL,
email 	VARCHAR(45) NOT NULL,
login 	VARCHAR(45) NOT NULL,
senha 	VARCHAR(45) NOT NULL,
PRIMARY KEY(id_usuario)
);

DROP TABLE IF EXISTS ag_contato;
CREATE TABLE ag_contato(
id_contato SERIAL NOT NULL,
id_usuario INT NOT NULL,
nome	VARCHAR(55) NOT NULL,
fone 	VARCHAR(13) NOT NULL,
email 	VARCHAR(45) NOT NULL,
dt_nasc DATE 	    NOT NULL,
PRIMARY KEY(id_contato),
FOREIGN KEY (id_usuario) REFERENCES ag_usuario (id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);
