DROP VIEW IF EXISTS public.vw_clientes;
DROP TABLE IF EXISTS public.clientes;
DROP SEQUENCE IF EXISTS clientes_seq;

create table public.clientes(
  i_cliente bigint,
  nome varchar(100),
  tenant varchar(100) default current_setting('context.tenant'::text) not null
);

create or replace view public.vw_clientes as
select * from public.clientes where tenant = current_setting('context.tenant'::text);

create sequence clientes_seq;