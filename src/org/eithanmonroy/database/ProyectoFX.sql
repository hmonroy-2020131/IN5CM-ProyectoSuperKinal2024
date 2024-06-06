drop database if exists ProyectoFX;

create database if not exists ProyectoFX;

use ProyectoFX;

create table Clientes(
		clienteId int not null auto_increment,
        nombre varchar (30) not null,	
        apellido varchar (30) not null,
        telefono varchar ( 15) not null,
        direccion varchar (200) not null,
        nit varchar (15) not null,
        primary key PK_clienteId (clienteId)
);

create table Cargos(
		cargoId int not null auto_increment,
        nombreCargo varchar (30) not null,	
        descripcionCargo varchar (100) not null,
        primary key PK_cargoId (cargoId)
);

create table Empleados(
		empleadoId int not null auto_increment,
        nombreEmpleado varchar (30) not null,	
        apellidoEmpleado varchar (30) not null,
        sueldo decimal(10,2) not null,
		horaentrada Time,
        horaSalida time,
        cargoId int not null,
        encargadoId int,
        primary key empleadoId (empleadoId),
        constraint FK_encargadoId_Empleados foreign key (encargadoId)
			references Empleados(empleadoId),
		constraint FK_cargoId_Empleados foreign key (cargoId)
			references Cargos(cargoId)
);

create table Distribuidores(
		distribuidorId int not null auto_increment,
        nombreDistribuidor varchar (30) not null,
        direccionDistribuidor varchar (200) not null,
        nitDistribuidor varchar (15) not null,
        telefonoDistribuidor varchar(15) not null,
        web varchar(50),
        primary key PK_distribuidorId (distribuidorId)
);

create table CategoriaProductos(
		categoriaproductosId int not null auto_increment,
        nombreCategoria varchar(30),
        descripcionCategoria varchar(100),
		primary key PK_categoriaproductosId (categoriaproductosId)
);

create table Compras(
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal (10,2),
    primary key PK_compraId (compraId)

);

create table Facturas(
	facturaId int not null auto_increment,
    fecha date not null,
    hora time not null,
    clienteId int not null,
    empleadoId int not null,
    total decimal(10,2),
    primary key PK_facturaId (facturaId),
    constraint FK_clienteId_Facturas foreign key (clienteId)
			references Clientes(clienteId),
	constraint FK_empleadoId_Facturas foreign key (empleadoId)
			references Empleados(empleadoId)

);

create table TicketSoporte(
    ticketSoporteId int not null auto_increment,
    descripcionTicket varchar(250) not null,
    estatus Varchar(30) not null,
    clienteId int not null,
    facturaId int,
    primary key (ticketSoporteId),
    constraint FK_clienteId_TicketSoporte foreign key (clienteId)
        references Clientes(clienteId),
    constraint FK_facturaId_TicketSoporte foreign key (facturaId)
        references Facturas(facturaId)
);


create table Productos(
	productoId int not null auto_increment,
	nombreProducto varchar(50) not null,
    descripcionProducto varchar(100),
    cantidadStock int not null,
    precioVentaUnitario decimal(10,2),
    precioVentaMayor decimal(10,2),
    precioCompra decimal(10,2),
    imagenProducto longblob,
    distribuidorId int not null,
    categoriaproductosId int not null,
    primary key PK_productoId(productoId),
    constraint FK_distribuidorId_Productos foreign key (distribuidorId)
			references Distribuidores(distribuidorId),
	constraint FK_categoriaproductosId_Productos foreign key (categoriaproductosId)
			references CategoriaProductos(categoriaproductosId)
);

create table detalleCompra(
    detalleCompraId int not null auto_increment,
    cantidadCompra int not null,
    productoId int not null,
    compraId int not null,
    primary key PK_detalleCompraId(detalleCompraId),
    constraint FK_productoId_detalleCompra foreign key (productoId)
            references Productos(productoId),
    constraint FK_compraId_detalleCompra  foreign key (compraId)
            references Compras(compraId)
);

create table detalleFactura(
    detalleFacturaId int not null auto_increment,
	productoId int not null,
    facturaId int not null,
    primary key PK_detalleFacturaId(detalleFacturaId),
	constraint FK_productoId_detalleFactura foreign key (productoId)
            references Productos(productoId),
	constraint FK_facturaId_detalleFactura  foreign key (facturaId)
            references Facturas(facturaId)
);

create table Promociones(
    promocionId int not null auto_increment,
    precioPromocion decimal(10,2) not null,
    descripcionPromocion varchar (100),
    fechaInicio Date not null,
    fechaFinalizacion Date not null,
    productoId int not null,
    primary key promocionId(promocionId),
    constraint FK_productoId_Promociones foreign key (productoId)
            references Productos(productoId)
);

create table NivelesAcceso(
	nivelAccesoId int not null auto_increment,
    nivelAcceso varchar(40) not null,
    primary key PK_nivelAccesoId(nivelAccesoId)

);


create table Usuarios(
    usarioId int not null auto_increment,
    usuario varchar (30) not null,
    contrasenia varchar (100) not null,
    nivelAccesoId int not null,
    empleadoId int not null,
    primary key PK_usarioId(usarioId),
    constraint FK_Usuarios_NivelesAcceso foreign key (nivelAccesoId)
            references NivelesAcceso(nivelAccesoId),
    constraint FK_Usuarios_Empleados foreign key (empleadoId)
            references Empleados(empleadoId)
);

INSERT INTO Clientes (nombre, apellido, telefono, direccion, nit) VALUES
    ('Lewis', 'Hamilton', '5555-6789', 'Ciudad', '1234567890'),
    ('Lionel', 'Messi', '4444-5678', 'Ciudad', '0987654321'),
    ('Serena', 'Williams', '3333-9876', 'Ciudad', '1122334455');

INSERT INTO Cargos(nombreCargo, descripcionCargo) VALUES
    ('Repartidor', 'Repartir prensa');
    
INSERT INTO NivelesAcceso (nivelAcceso) VALUES 
	('Admin'), 
	('Usuario');

select * from detalleFactura
join Facturas on detalleFactura.facturaId = Facturas.facturaId
join Clientes on Facturas.clienteId = Clientes.clienteId
join Productos on DetalleFactura.productoId = Productos.productoId
where Facturas.facturaId = 1;

SET Global time_zone = '-6:00';