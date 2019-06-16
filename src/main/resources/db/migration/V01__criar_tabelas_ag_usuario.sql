CREATE TABLE ag_usuario(
id_usuario SERIAL not null ,
nome VARCHAR(55) not null,
fone VARCHAR(13) not null,
PRIMARY KEY(id_usuario)
);

CREATE TABLE ag_contato(
id_contato SERIAL NOT NULL,
id_usuario INT NOT NULL,
nome VARCHAR(55) NOT NULL,
fone VARCHAR(13) NOT NULL,
PRIMARY KEY(id_contato),
FOREIGN KEY (id_usuario) REFERENCES ag_usuario (id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);
