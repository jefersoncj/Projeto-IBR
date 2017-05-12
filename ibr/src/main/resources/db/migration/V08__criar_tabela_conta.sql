
CREATE TABLE conta (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50), 
   	numero INTEGER,
   	agencia INTEGER,
    saldo DECIMAL(10,2),
    saldo_inicial DECIMAL(10,2),
    codigo_usuario BIGINT(20) NOT NULL,
    tenant_id VARCHAR(50),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
