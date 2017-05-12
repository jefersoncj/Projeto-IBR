
CREATE TABLE movimento_conta (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_movimento DATETIME NOT NULL,
    centro_custo VARCHAR(120) NOT NULL,
    valor DECIMAL(10,2),
    saldo_anterior DECIMAL(10,2),
    tipo VARCHAR(50) NOT NULL,
    codigo_conta BIGINT(20) NOT NULL,
    codigo_usuario BIGINT(20) NOT NULL,
    codigo_movimento BIGINT(20),
    tenant_id VARCHAR(50),
    FOREIGN KEY (codigo_conta) REFERENCES conta(codigo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_movimento) REFERENCES movimento(codigo)
   
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
