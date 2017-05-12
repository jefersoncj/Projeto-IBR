INSERT INTO permissao VALUES (1, 'ROLE_CADASTRAR_SAIDAS');
INSERT INTO permissao VALUES (2, 'ROLE_CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 2);

INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo,tenant_id) VALUES (
	(SELECT codigo FROM usuario WHERE email = 'admin@ibr.com'), 1,1);
