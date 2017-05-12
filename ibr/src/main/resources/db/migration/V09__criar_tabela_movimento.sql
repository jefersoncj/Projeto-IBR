
CREATE TABLE movimento (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_movimento DATETIME NOT NULL,
    centro_custo VARCHAR(120) NOT NULL,
    valor DECIMAL(10,2),
    observacao VARCHAR(200), 
    codigo_item BIGINT(20) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    codigo_conta BIGINT(20) NOT NULL,
    codigo_usuario BIGINT(20) NOT NULL,
    tenant_id VARCHAR(50),
    FOREIGN KEY (codigo_item) REFERENCES item(codigo),
    FOREIGN KEY (codigo_conta) REFERENCES conta(codigo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
   
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
