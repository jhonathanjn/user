-- 1) Renomear colunas antigas
ALTER TABLE users
RENAME COLUMN init_time TO plan_start;

ALTER TABLE users
RENAME COLUMN end_time TO plan_end;

-- 2) Ajustar roles antigos
UPDATE users
SET roles = 'USER'
WHERE roles = 'GRATIS';

-- 3) Adicionar relacionamento com planos
ALTER TABLE users
ADD COLUMN plan_id BIGINT;

-- 4) Adicionar data de criação
ALTER TABLE users
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- 5) Criar chave estrangeira
ALTER TABLE users
ADD CONSTRAINT fk_users_plan
FOREIGN KEY (plan_id)
REFERENCES db_plans(id);
