create table topicos(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(600) not null,
    fecha_creacion datetime not null,
    estado varchar(100) not null,
    curso_id bigint not null,
    usuario_id bigint not null,

    primary key(id)

);