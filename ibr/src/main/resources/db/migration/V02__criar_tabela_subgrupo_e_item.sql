CREATE TABLE sub_grupo (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    codigo_grupo_conta BIGINT(20) NOT NULL,
    referencia VARCHAR(20),
    tenant_id VARCHAR(50),
    FOREIGN KEY (codigo_grupo_conta) REFERENCES grupo_conta(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE item (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    codigo_sub_grupo BIGINT(20) NOT NULL,
    referencia VARCHAR(20),
    tipo VARCHAR(50) NOT NULL,
    tenant_id VARCHAR(50),
    FOREIGN KEY (codigo_sub_grupo) REFERENCES sub_grupo(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
