CREATE TABLE grupo_conta (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    referencia BIGINT(20),
    tenant_id VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;