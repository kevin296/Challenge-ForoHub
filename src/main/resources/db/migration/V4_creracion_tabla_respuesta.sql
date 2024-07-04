create table respuestas(

    id bigint not null auto_increment,
    mensaje varchar(500) not null,
    fecha_creacion datetime not null,
    solucion boolean not null,
    usuario_id bigint not null,
    topico_id bigint not null,

    primary key(id)

);