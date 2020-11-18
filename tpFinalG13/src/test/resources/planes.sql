insert into usuario(id_usuario,email,password,username,admin) values (1,'user1@gmail.com','$2a$10$vQDheJgpbwoxe/NYzU7VZO.pE3jMBS.NE0mfSlPFwaRLukw0W5CDO','user1',0)
insert into usuario(id_usuario,email,password,username,admin) values (2,'user2@gmail.com','$2a$10$vQDheJgpbwoxe/NYzU7VZO.pE3jMBS.NE0mfSlPFwaRLukw0W5CDO','user2',0)
insert into viaje(id_viaje,ciudad_destino,descripcion,fecha_inicio,fecha_fin,nombre_viaje,id_usuario) values (4,'ciudad1','descripcion1','2020-01-01 01:01:00.000000','2020-01-16 01:01:00.000000','nombre1',1)
insert into viaje(id_viaje,ciudad_destino,descripcion,fecha_inicio,fecha_fin,nombre_viaje,id_usuario) values (5,'ciudad2','descripcion1','2020-01-01 01:01:00.000000','2020-01-16 01:01:00.000000','nombre2',1)
insert into viaje(id_viaje,ciudad_destino,descripcion,fecha_inicio,fecha_fin,nombre_viaje,id_usuario) values (6,'ciudad3','descripcion1','2020-01-01 01:01:00.000000','2020-01-16 01:01:00.000000','nombre3',2)
insert into viaje(id_viaje,ciudad_destino,descripcion,fecha_inicio,fecha_fin,nombre_viaje,id_usuario) values (7,'ciudad4','descripcion1','2020-01-01 01:01:00.000000','2020-01-16 01:01:00.000000','nombre4',2)

insert into plan (compania, fecha_fin, fecha_inicio, nombre_plan, id_viaje, id_plan) values ('compania1', '2020-01-01 01:01:00.000000', '2020-01-01 01:01:00.000000', 'nombre1', 4, 10)
insert into plan_reserva_hotel (direccion, habitacion, id_plan) values ('direccion1', 'habitacion1', 10)

insert into plan (compania, fecha_fin, fecha_inicio, nombre_plan, id_viaje, id_plan) values ('compania2', '2020-01-01 01:01:00.000000', '2020-01-01 01:01:00.000000', 'nombre2', 4, 11)
insert into plan_viaje_tren (asiento, estacion, id_plan) values (15, 'estacion2', 11)

insert into plan (compania, fecha_fin, fecha_inicio, nombre_plan, id_viaje, id_plan) values ('compania3', '2020-01-01 01:01:00.000000', '2020-01-01 01:01:00.000000', 'nombre3', 7, 12)
insert into plan_viaje_tren (asiento, estacion, id_plan) values (10, 'estacion3', 12)

insert into plan (compania, fecha_fin, fecha_inicio, nombre_plan, id_viaje, id_plan) values ('compania4', '2020-01-01 01:01:00.000000', '2020-01-01 01:01:00.000000', 'nombre4', 5, 13)
insert into plan_viaje_tren (asiento, estacion, id_plan) values (9, 'estacion4', 13)