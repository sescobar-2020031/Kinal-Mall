Drop database if exists DBKinalMall2020031;
create database DBKinalMall2020031;
use DBKinalMall2020031;
/*CREANDO TABLAS*/
create table Departamentos(
	codigoDepartamento int not null auto_increment,
    nombreDepartamento varchar(45) not null,
    primary key pk_codigoDepartamentos(codigoDepartamento)
);
create table Cargos(
	codigoCargo int not null auto_increment,
    nombreCargo varchar(45) not null,
    primary key pk_codigoCargo(codigoCargo)
);
create table Horarios(	
	codigoHorario int not null auto_increment,
    horarioEntrada varchar(5) not null,
    horarioSalida varchar(5) not null,
    lunes boolean,
    martes boolean,
    miercoles boolean,
    jueves boolean,
    viernes boolean,
    primary key pk_codigoHorarios(codigoHorario)
 );
create table TipoCliente(
	codigoTipoCliente int not null auto_increment,
    descripcion varchar(45) not null,
    primary key pk_codigoTipoCliente(codigoTipoCliente)

);
create table Locales(
	codigoLocal int not null auto_increment,
    saldoFavor double (11,2) default 0.0,
    saldoContra double (11,2) default 0.0,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal double (11,2) not null,
    valorAdministracion double(11,2) not null,
    primary key pk_codigoLocal(codigoLocal)
);
create table Administracion(
	codigoAdministracion int not null auto_increment,
    direccion varchar(45) not null,
    telefono varchar(8) not null,
    primary key pk_codigoAdministracion(codigoAdministracion)
);
create table Empleados(
	codigoEmpleado int not null auto_increment,	
    nombreEmpleado varchar(45) not null,
    apellidoEmpleado varchar(45) not null,
    correoElectronico varchar(45) not null,
    telefonoEmpleado varchar(8) not null,
    fechaContratacion Date not null,
    sueldo double (11,2) not null,
    codigoDepartamento int not null,
    codigoCargo int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
    primary key pk_codigoEmpleado(codigoEmpleado),
    Constraint fk_Empleados_Departamento foreign key (codigoDepartamento)
		references Departamentos(codigoDepartamento),
	constraint fk_Empleados_Cargos foreign key(codigoCargo)
		references Cargos(codigoCargo),
    constraint fk_Empleados_Horarios foreign key(codigoHorario)
		references Horarios(codigoHorario),
    constraint fk_Empleados_Administracion foreign key(codigoAdministracion)
		references Administracion(codigoAdministracion)
);
create table Proveedores(
	codigoProveedor int not null auto_increment,
    NITProveedor varchar(45) not null,
    servicioPrestado varchar(45) not null,
    telefonoProveedor varchar(45) not null,
    direccionProveedor varchar(60) not null,
    sueldoFavor double(11,2) not null,
    sueldoContra double(11,2) not null,
    codigoAdministracion int not null,
    primary key pk_codigoProveedor(codigoProveedor),
    constraint fk_Proveedores_Administracion foreign key(codigoAdministracion)
		references Administracion(codigoAdministracion)
);
create table CuentasPorPagar(
	codigoCuentasPorPagar int not null auto_increment,
    numeroFactura varchar(45) not null,
    fechaLimitePago date not null,
    estadoPago varchar(45) not null,
    valorNetoPago double (11,2) not null,
    codigoAdministracion int not null,
    codigoProveedor int not null,
    primary key pk_codigoCuentasPorPagar(codigoCuentasPorPagar),
    constraint fk_CuentasPorPagar_Administracion foreign key(codigoAdministracion)
		references Administracion(codigoAdministracion),
	constraint fk_CuentasPorPagar_Proveedores foreign key(codigoProveedor)
		references Proveedores(codigoProveedor)
);
create table Clientes(
	codigoCliente int not null auto_increment,
	nombreCliente varchar(45) not null,
    apellidoCliente varchar(45) not null,
    telefonoCliente varchar(8) not null,
    direccionCliente varchar(60) not null,
    email varchar(45) not null,
    codigoLocal int not null,
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
    primary key pk_codigoCliente(codigoCliente),
    constraint fk_Clientes_Locales foreign key(codigoLocal)
		references Locales(codigoLocal),
	constraint fk_Clientes_Administracion foreign key(codigoAdministracion)
		references Administracion(codigoAdministracion),
	constraint fk_Clientes_TipoCliente foreign key(codigoTipoCliente)
		references TipoCliente(codigoTipoCliente)
);
create table CuentasPorCobrar(
	codigoCuentasPorCobrar int not null auto_increment,
    numeroFactura varchar(45) not null,
    anio varchar(4) not null,
    mes varchar(2) not null,
    valorNetoPago double(11,2) not null,
    estadoPago varchar(45) not null,
    codigoAdministracion int not null,
    codigoCliente int not null,
	codigoLocal int not null,
    primary key pk_codigoCuenatasPorCobrar(codigoCuentasPorCobrar),
    constraint fk_CuentasPorCobrar_Administracion foreign key (codigoAdministracion)
		references Administracion(codigoAdministracion),
	constraint fk_CuentasPorCobrar_Clientes foreign key (codigoCliente)
		references Clientes(codigoCliente),
	constraint fk_CuentasPorCobrar_Local foreign key(codigoLocal)
		references Locales(codigoLocal)
);
-- Creando Procedimientos Almacenados
-- Procedimiento Departamentos
Delimiter $$
	Create procedure sp_AgregarDepartamento(in nombreDepartamento varchar(45))
    Begin
		insert into Departamentos(nombreDepartamento) values (nombreDepartamento);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarDepartamentos()
	Begin
		select 
			D.codigoDepartamento, 
            D.nombreDepartamento
		from Departamentos D;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarDepartamento(in codDepartamento int, in nomDepartamento varchar(45) )
    Begin
		update Departamentos
			set
				nombreDepartamento=nomDepartamento
                where
					codigoDepartamento=codDepartamento;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarDepartamento(in codigoDepa int)
    Begin
		select 
			D.codigoDepartamento,
			D.nombreDepartamento
		from Departamentos D
        where codigoDepa=codigoDepartamento;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarDepartamento(in codigoDepa int)
    Begin
		delete from Departamentos where codigoDepartamento = codigoDepa;
	End$$
Delimiter ;

-- Procedimiento Cargos
Delimiter $$
	Create procedure sp_AgregarCargo(in nombreCargo varchar(45))
    Begin
		insert into Cargos(nombreCargo) values (nombreCargo);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarCargos()
	Begin
		select 
			C.codigoCargo,
            C.nombreCargo
		from Cargos C;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarCargo(in codCargo int, in nomCargo varchar(45) )
    Begin
		update Cargos
			set
				nombreCargo=nomCargo
                where
					codigoCargo=codCargo;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarCargo(in codCargo int)
    Begin
		select 
			C.codigoCargo,
			C.nombreCargo
		from Cargos C
        where codCargo =codigoCargo;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarCargo(in codCargo int)
    Begin
		delete from Cargos where codigoCargo = codCargo;
	End$$
Delimiter ;

-- Procedimiento Horarios
Delimiter $$
	Create procedure sp_AgregarHorario(in horarioEntrada varchar(5), in horarioSalida varchar(5), in lunes boolean, in martes boolean, in miercoles boolean, in jueves boolean, in viernes boolean)
    Begin
		insert into Horarios(horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes) values (horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarHorarios()
	Begin
		select 
			H.codigoHorario, 
            H.horarioEntrada, 
            H.horarioSalida, 
            H.lunes, 
            H.martes, 
            H.miercoles, 
            H.jueves,
            H.viernes
		from Horarios H;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarHorario(in codHorario int, in horaEntra varchar(5), in horaSali varchar(5), in lun boolean, in mar boolean, in mierco boolean, in juev boolean, in vier boolean)
    Begin
		update Horarios
			set
               horarioEntrada=horaEntra,
               horarioSalida=horaSali,
               lunes=lun, 
               martes=mar, 
               miercoles=mierco,
               jueves=juev,
               viernes=vier
			where 
				codHorario=codigoHorario;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarHorario(in codHorario int)
    Begin
		select 
			H.codigoHorario,
			H.horarioEntrada, 
            H.horarioSalida,  
            H.lunes, 
            H.martes, 
            H.miercoles, 
            H.jueves, 
            H.viernes
		from Horarios H
        where codHorario =codigoHorario;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarHorario(in codHorario int)
    Begin
		delete from Horarios where codigoHorario = codHorario;
	End$$
Delimiter ;

-- Procedimiento Tipo Cliente
Delimiter $$
	Create procedure sp_AgregarTipoCliente(in descripcion varchar(45))
    Begin
		insert into TipoCliente(descripcion) values (descripcion);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarTipoClientes()
	Begin
		select 
			TC.codigoTipoCliente,
            TC.descripcion
		from TipoCliente TC;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarTipoCliente(in codTipoCliente int, in descrip varchar(45) )
    Begin
		update TipoCliente
			set
				descripcion=descrip
                where
					codigoTipoCliente=codTipoCliente;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarTipoCliente(in codTipoCliente int)
    Begin
		select 
			TC.codigoTipoCliente,
			TC.descripcion
		from TipoCliente TC
        where codTipoCliente = codigoTipoCliente;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarTipoCliente(in codTipoCliente int)
    Begin
		delete from TipoCliente where codigoTipoCliente = codTipoCliente;
	End$$
Delimiter ;

-- Procedimiento Locales
Delimiter $$
	Create procedure sp_AgregarLocal(in disponibilidad boolean, in valorLocal double,in valorAdministracion double)
    Begin
		insert into Locales(disponibilidad, valorLocal, valorAdministracion) values (disponibilidad, valorLocal, valorAdministracion);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarLocales()
	Begin
		select 
			L.codigoLocal,
			L.saldoFavor, 
            L.saldoContra, 
            L.mesesPendientes, 
            L.disponibilidad, 
            L.valorLocal, 
            L.valorAdministracion
		from Locales L;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarLocal(in codLocal int,in disponibi boolean, in valLocal double,in valAdministracion double)
    Begin
		update Locales
			set
                disponibilidad=disponibi, 
                valorLocal=valLocal, 
                valorAdministracion=valAdministracion
                where
					codigoLocal=codLocal;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarLocal(in codLocal int)
    Begin
		select 
			L.codigoLocal, 
            L.saldoFavor, 
            L.saldoContra, 
            L.mesesPendientes, 
            L.disponibilidad, 
            L.valorLocal, 
            L.valorAdministracion
		from Locales L
        where codLocal = codigoLocal;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarLocal(in codLocal int)
    Begin
		delete from Locales where codigoLocal = codLocal;
	End$$
Delimiter ;

-- Procedimiento Administracion
Delimiter $$
	Create procedure sp_AgregarAdministracion(in direccion varchar(45), in telefono varchar(8))
    Begin
		insert into Administracion(direccion, telefono) values (direccion, telefono);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarAdminitraciones()
	Begin
		select 
			A.codigoAdministracion,
			A.telefono, 
            A.direccion
		from Administracion A;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarAdministracion(in codAdmin int, in dire varchar(45),in tel varchar(8))
    Begin
		update Administracion
			set
				telefono=tel,
                direccion=dire
                where
					codigoAdministracion=codAdmin;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarAdminitracion(in codAdmin int)
    Begin
		select 
			A.codigoAdministracion,
			A.direccion,
            A.telefono
		from Administracion A
        where codAdmin = codigoAdministracion;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarAdminitracion(in codAdmin int)
    Begin
		delete from Administracion where codigoAdministracion = codAdmin;
	End$$
Delimiter ;

-- Procedimiento Empleados
Delimiter $$
	Create procedure sp_AgregarEmpleado(in nombreEmpleado varchar(45), in apellidoEmpleado varchar(45), in correoElectronico varchar(45), in telefonoEmpleado varchar(8),in fechaContratacion date, in sueldo double,in codigoDepartamento int,in codigoCargo int,in codigoHorario int,in codigoAdministracion int)
    Begin
		insert into Empleados(nombreEmpleado, apellidoEmpleado, correoElectronico, telefonoEmpleado, fechaContratacion, sueldo, codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion) 
			values (nombreEmpleado, apellidoEmpleado, correoElectronico, telefonoEmpleado, fechaContratacion, sueldo, codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarEmpleados()
	Begin
		select 
			E.codigoEmpleado,
			E.nombreEmpleado, 
            E.apellidoEmpleado, 
            E.correoElectronico, 
            E.telefonoEmpleado, 
            E.fechaContratacion, 
            E.sueldo, 
            E.codigoDepartamento, 
            E.codigoCargo, 
            E.codigoHorario, 
            E.codigoAdministracion
		from Empleados E;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarEmpleado(in codEmpleado int, in nomEmpleado varchar(45), in apeEmpleado varchar(45), in correoElec varchar(45), in telEmpleado varchar(8),in fechContratacion date, in suel double)
    Begin
		update Empleados
			set
				nombreEmpleado=nomEmpleado, 
                apellidoEmpleado=apeEmpleado, 
                correoElectronico=correoElec, 
                telefonoEmpleado=telEmpleado, 
                fechaContratacion=fechContratacion, 
                sueldo=suel
                where
					codigoEmpleado=codEmpleado;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarEmpleado(in codEmpleado int)
    Begin
		select 
			E.nombreEmpleado, 
            E.apellidoEmpleado, 
            E.correoElectronico, 
            E.telefonoEmpleado, 
            E.fechaContratacion, 
            E.sueldo, 
            E.codigoDepartamento, 
            E.codigoCargo, 
            E.codigoHorario, 
            E.codigoAdministracion
		from Empleados E
        where codEmpleado=codigoEmpleado;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarEmpleado(in codEmpleado int)
    Begin
		delete from Empleados where codigoEmpleado=codEmpleado;
	End$$
Delimiter ;

-- Procedimiento Proveedores
Delimiter $$
	Create procedure sp_AgregarProveedor(in NITProveedor varchar(45),in servicioPrestado varchar(45),in telefonoProveedor varchar(45),in direccionProveedor varchar(60),in sueldoFavor double,in sueldoContra double,in codigoAdministracion int)
    Begin
		insert into Proveedores(NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, sueldoFavor, sueldoContra, codigoAdministracion) 
			values (NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, sueldoFavor, sueldoContra, codigoAdministracion);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarProveedores()
	Begin
		select 
			P.codigoProveedor,
			P.NITProveedor, 
            P.servicioPrestado, 
            P.telefonoProveedor, 
            P.direccionProveedor, 
            P.sueldoFavor, 
            P.sueldoContra, 
            P.codigoAdministracion
		from Proveedores P;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarProveedor(in codProveedor int, in NITProv varchar(45),in serviPrestado varchar(45),in telProveedor varchar(45),in direProveedor varchar(60),in suelFavor double,in suelContra double)
    Begin
		update Proveedores
			set
				NITProveedor=NITProv, 
                servicioPrestado=serviPrestado, 
                telefonoProveedor=telProveedor, 
                direccionProveedor=direProveedor, 
                sueldoFavor=suelFavor, 
                sueldoContra=suelContra
                where
					codigoProveedor=codProveedor;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarProveedor(in codProveedor int)
    Begin
		select 
			P.codigoProveedor,
			P.NITProveedor, 
            P.servicioPrestado, 
            P.telefonoProveedor, 
            P.direccionProveedor, 
            P.sueldoFavor, 
            P.sueldoContra, 
            P.codigoAdministracion
		from Proveedores P
        where codProveedor=codigoProveedor;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarProveedor(in codProveedor int)
    Begin
		delete from Proveedores where codigoProveedor=codProveedor;
	End$$
Delimiter ;

-- Procedimiento Cuentas por Pagar
Delimiter $$
	Create procedure sp_AgregarCuentaPorPagar(in numeroFactura varchar(45),in fechaLimitePago date,in estadoPago varchar(45),in valorNetoPago double,in codigoAdministracion int , in codigoProveedor int)
    Begin
		insert into CuentasPorPagar(numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, codigoAdministracion, codigoProveedor) 
			values (numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, codigoAdministracion, codigoProveedor);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarCuentasPorPagar()
	Begin
		select 
			CP.codigoCuentasPorPagar,
			CP.numeroFactura, 
            CP.fechaLimitePago, 
            CP.estadoPago, 
            CP.valorNetoPago, 
            CP.codigoAdministracion, 
            CP.codigoProveedor
		from CuentasPorPagar CP;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarCuentaPorPagar(in codCuentasPorPagar int, in numFactura varchar(45),in fechaLimitPag date,in estatPago varchar(45),in vlrNetPago double)
    Begin
		update CuentasPorPagar
			set
				numeroFactura=numFactura , 
                fechaLimitePago=fechaLimitPag, 
                estadoPago=estatPago, 
                valorNetoPago=vlrNetPago
                where
					codigoCuentasPorPagar=codCuentasPorPagar;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarCuentaPorPagar(in codCuentasPorPagar int)
    Begin
		select 
			CP.codigoCuentasPorPagar,
			CP.numeroFactura, 
            CP.fechaLimitePago, 
            CP.estadoPago, 
            CP.valorNetoPago, 
            CP.codigoAdministracion, 
            CP.codigoProveedor
		from CuentasPorPagar CP
        where codCuentasPorPagar=codigoCuentasPorPagar;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarCuentaPorPagar(in codCuentasPorPagar int)
    Begin
		delete from CuentasPorPagar where codigoCuentasPorPagar=codCuentasPorPagar;
	End$$
Delimiter ;

-- Procedimiento Clientes
Delimiter $$
	Create procedure sp_AgregarCliente(in nombreCliente varchar(45),in apellidoCliente varchar(45),in telefonoCliente varchar(8),in direccionCliente varchar(60),in email varchar(45),in codigoLocal int,in codigoAdministracion int,in codigoTipoCliente int)
    Begin
		insert into Clientes(nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, email, codigoLocal, codigoAdministracion, codigoTipoCliente) 
			values (nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, email, codigoLocal, codigoAdministracion, codigoTipoCliente);            
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarClientes()
	Begin
		select 
			C.codigoCliente,
			C.nombreCliente, 
            C.apellidoCliente, 
            C.telefonoCliente, 
            C.direccionCliente, 
            C.email, 
            C.codigoLocal, 
            C.codigoAdministracion, 
            C.codigoTipoCliente
		from Clientes C;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarCliente(in codCliente int,in nomCliente varchar(45),in apellCliente varchar(45),in teleCliente varchar(8),in direCliente varchar(60),in correo varchar(45))
    Begin
		update Clientes
			set
				nombreCliente=nomCliente,
                apellidoCliente=apellCliente,
                telefonoCliente=teleCliente,
                direccionCliente=direCliente,
                email=correo
                where
					codigoCliente=codCliente;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarCliente(in codCliente int)
    Begin
		select 
			C.codigoCliente,
			C.nombreCliente, 
            C.apellidoCliente, 
            C.telefonoCliente, 
            C.direccionCliente, 
            C.email, 
            C.codigoLocal, 
            C.codigoAdministracion, 
            C.codigoTipoCliente
		from Clientes C
        where codCliente=codigoCliente;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarCliente(in codCliente int)
    Begin
		delete from Clientes where codigoCliente=codCliente;
	End$$
Delimiter ;

-- Procedimiento Cuentas Por Cobrar
Delimiter $$
	Create procedure sp_AgregarCuentaPorCobrar(in numeroFactura varchar(45),in anio varchar(4),in mes varchar(2),in valorNetoPago double,in estadoPago varchar(45),in codigoAdministracion int,in codigoCliente int,in codigoLocal int)
    Begin
		insert into CuentasPorCobrar(numeroFactura, anio, mes, valorNetoPago, estadoPago, codigoAdministracion, codigoCliente, codigoLocal) 
			values (numeroFactura, anio, mes, valorNetoPago, estadoPago, codigoAdministracion, codigoCliente, codigoLocal);
End$$
Delimiter ;
Delimiter $$
	Create procedure sp_ListarCuentasPorCobrar()
	Begin
		select 
			CC.codigoCuentasPorCobrar,
			CC.numeroFactura, 
            CC.anio, 
            CC.mes, 
            CC.valorNetoPago, 
            CC.estadoPago, 
            CC.codigoAdministracion, 
            CC.codigoCliente, 
            CC.codigoLocal
		from CuentasPorCobrar CC;
	End$$
Delimiter ;
Delimiter $$
	create procedure sp_EditarCuentaPorCobrar(in codCuentaPorCobrar int, in numFactura varchar(45),in añ varchar(4),in me varchar(2),in valorNetoPag double,in estadoPag varchar(45))    
	Begin
		update CuentasPorCobrar
			set
				numeroFactura=numFactura, 
                anio=añ, 
                mes=me, 
                valorNetoPago=valorNetoPag, 
                estadoPago=estadoPag 
                where
					codigoCuentasPorCobrar=codCuentaPorCobrar;
	End$$
Delimiter;
Delimiter $$
	Create procedure sp_BuscarCuentaPorCobrar(in codCuentaPorCobrar int)
    Begin
		select 
            CC.codigoCuentasPorCobrar,
			CC.numeroFactura, 
            CC.anio, 
            CC.mes, 
            CC.valorNetoPago, 
            CC.estadoPago, 
            CC.codigoAdministracion, 
            CC.codigoCliente, 
            CC.codigoLocal
		from CuentasPorCobrar CC
        where codCuentaPorCobrar=codigoCuentasPorCobrar;
    End$$
Delimiter ;
Delimiter $$
	create procedure sp_EliminarCuentaPorCobrar(in codCuentaPorCobrar int)
    Begin
		delete from CuentasPorCobrar where codigoCuentasPorCobrar=codCuentaPorCobrar;
	End$$
Delimiter ;

/*EJECUTANDO PROCEDIMIENTOS ALMACENADOS*/
/*PROCEDIMIENTOS Departamentos*/
call sp_AgregarDepartamento('Recursos Humanos');
call sp_AgregarDepartamento('Contabilidad');
call sp_ListarDepartamentos();
call sp_EditarDepartamento(2,'Programadores');
call sp_BuscarDepartamento(1);
-- call sp_EliminarDepartamento(1);
/*PROCEDIMIENTOS Cargos*/
call sp_AgregarCargo('Gerente');
call sp_AgregarCargo('Cargo 2');
call sp_ListarCargos();
call sp_EditarCargo(2,'Cargo Editado');
call sp_BuscarCargo(1);
call sp_EliminarCargo(2);
/*PROCEDIMIENTOS Horarios*/
call sp_AgregarHorario('10:00', '10:00', true, false, true, true, false);
call sp_AgregarHorario('7:00', '8:00', true, true, true, true, false);
call sp_ListarHorarios();
call sp_EditarHorario(1, '8:00', '7:00', true, false, true, true, false);
call sp_BuscarHorario(2);
call sp_EliminarHorario(2);
/*PROCEDIMIENTOS TipoCliente*/
call sp_AgregarTipoCliente('Irresponsable');
call sp_AgregarTipoCliente('Moroso');
call sp_ListarTipoClientes();
call sp_EditarTipoCliente(1,'Puntual');
call sp_BuscarTipoCliente(1);
call sp_EliminarTipoCliente(2);
/*agregar y listar Locales*/
/* Samuel Isaac Escobar Vásquez 2020031 IN5AV*/
call sp_AgregarLocal(false,8000,750);
call sp_AgregarLocal(true,4500,50);
call sp_AgregarLocal(true,5000,430);
call sp_AgregarLocal(false,3500,750);
call sp_AgregarLocal(false,5700,425);
call sp_AgregarLocal(true,3600,970);
call sp_AgregarLocal(true,9100,631);
call sp_AgregarLocal(false,10000,717);
call sp_AgregarLocal(true ,9700,987);
call sp_AgregarLocal(false,8400,310);
call sp_ListarLocales();
-- call sp_EditarLocal(1, false,7000,600);
-- call sp_BuscarLocal(1);
-- call sp_EliminarLocal(2);
/*PROCEDIMIENTOS Administracion*/
call sp_AgregarAdministracion('Boca del monte',48796412);
call sp_AgregarAdministracion('Boca del monte',48796412);
call sp_AgregarAdministracion('Villa Hermosa',55618973);
call sp_AgregarAdministracion('Antigua Guatemala',37415978);
call sp_AgregarAdministracion('Ciudad de Guatemala',41956212);
call sp_ListarAdminitraciones();
call sp_EditarAdministracion(2, 'Santa Catarina Pinula', 47894156);
call sp_BuscarAdminitracion(1);
call sp_EliminarAdminitracion(4);
/*PROCEDIMIENTOS Empleados*/
call sp_AgregarEmpleado('Samuel','Escobar','Samuel@gmail.com',42694599,'2020-10-5',10000,1,1,1,1);
call sp_AgregarEmpleado('Pedro','Armas','armas@gmail.com',34216412,'2020-8-10',8000,1,1,1,1);
call sp_ListarEmpleados();
call sp_EditarEmpleado(2,'Juan','Quijote','Juan@gmail.com',53123452,'2020-5-12',9000);
call sp_BuscarEmpleado(1);
call sp_EliminarEmpleado(2);
/*Samuel Isaac Escobar Vásquez 2020031 IN5AV */
/*Agretar y listar procedimientos de Proveedores*/
call sp_AgregarProveedor('21341234-1', 'Proveedor de leche', '43543223', 'El rosario Boca dle monte', 5983.42, 2456.12,1);
call sp_AgregarProveedor('54321978-5', 'Proveedor de huevos', '7891213', 'mixco', 4621.42, 2456.12,1);
call sp_AgregarProveedor('65461285-7', 'Proveedor de relojs', '4354561', 'Escuintla', 7895.42, 2456.12,1);
call sp_AgregarProveedor('51654112-4', 'Proveedor de frijol', '43543223', 'La florida', 132.42, 786.12,1);
call sp_AgregarProveedor('78945452-2', 'Proveedor de carne', '43543223', 'villa canales', 4543.42, 456.12,1);
call sp_AgregarProveedor('12354687-1', 'Proveedor de crema', '43543223', 'Zona 1', 5983.42, 465.31,1);
call sp_AgregarProveedor('98726421-9', 'Proveedor de teclados', '43543223', 'Villa nuevas', 785.42, 54.12,1);
call sp_AgregarProveedor('32215496-8', 'Proveedor de computadoras', '43543223', 'villa lobos', 78913.42, 7878.12,1);
call sp_AgregarProveedor('78952123-3', 'Proveedor de fideos', '43543223', 'El milagro', 714.42, 61561.12,1);
call sp_AgregarProveedor('97561626-5', 'Proveedor de lentes', '43543223', 'Villa hermosa', 5983.42, 6542.12,1);
call sp_ListarProveedores();
-- call sp_EditarProveedor(1, "65245332-5", "Sanitazisacion", "65139841", "Mixco Guatemala", 9976.12, 4985.14,1);
call sp_BuscarProveedor(1);
-- call sp_EliminarProveedor(2);
/*PROCEDIMIENTOS CuentasPorPagar*/
call sp_AgregarCuentaPorPagar('78951224','2020-10-4', 'Al dia', 5000,1,1);
call sp_AgregarCuentaPorPagar('45562157','2020-9-10', 'Moroso', 7000,1,1);
call sp_ListarCuentasPorPagar();
call sp_EditarCuentaPorPagar(1,'94641234','2020-5-1', 'Al dia', 9000);
call sp_BuscarCuentaPorPagar(2);
call sp_EliminarCuentaPorPagar(2);
/*PROCEDIMIENTOS Clientes*/
call sp_AgregarCliente('Samuel','Escobar','48753146', 'cuidad de Guatemala','Samuelungas@gmail.com', 1,1,1);
call sp_AgregarCliente('Josue','Escobar','86973214', 'Villa Canales','Josuelungas@gmail.com', 1,1,1);
call sp_ListarClientes();
call sp_EditarCliente(1,'Kevin','Alvarado','54127964', 'El pajon','Kevin@gmail.com');
call sp_BuscarCliente(1);
call sp_EliminarCliente(2);
/*PROCEDIMIENTOS CuentasPorCobrar*/
call sp_AgregarCuentaPorCobrar('49783645','2020','10',5000, 'al dia', 1,1,1);
call sp_AgregarCuentaPorCobrar('84712496','2019','09',4000, 'al dia', 1,1,1);
call sp_ListarCuentasPorCobrar();
call sp_EditarCuentaPorCobrar(1, '57841367','2020','05',8000, 'Moroso');
call sp_BuscarCuentaPorCobrar(2);
call sp_EliminarCuentaPorCobrar(2);

Delimiter $$
	Create Procedure sp_CalcularLiquido(in codLocal int)
		Begin 
        select saldoFavor-saldoContra as Liquido from Locales where codLocal=codigoLocal;
        End$$
Delimiter ;
-- call sp_CalcularLiquido(1);

/* Samuel Isaac Escobar Vásquez 2020031 IN5AV */
Delimiter $$
	Create Procedure sp_CalcularLiquidoProveedor(in codProveedor int)
		Begin 
        select *, sueldoFavor-sueldoContra as saldoLiquido from Proveedores where codProveedor=codigoProveedor;
        End$$
Delimiter ;
-- call sp_CalcularLiquidoProveedor(1);

/* Samuel Isaac Escobar Vásquez 2020031 IN5AV Actividad 3*/
Delimiter $$
	Create Procedure sp_TodosLosProveedores()
		Begin 
        select *, sueldoFavor-sueldoContra as saldoLiquido from Proveedores;
        End$$
Delimiter ;
-- call sp_TodosLosProveedores();

/* Samuel Isaac Escobar Vásquez 2020031 IN5AV */
Delimiter $$
	Create Procedure sp_CalcularMesesPendientes(in codLocal int)
		Begin 
        select L.codigoLocal,
			   L.saldoFavor, 
			   L.saldoContra, 
               L.mesesPendientes, 
               L.disponibilidad, 
               L.valorLocal, 
               L.valorAdministracion, 
               (L.valorLocal*L.mesesPendientes)-(L.saldoFavor-L.saldoContra) as totalRenta
               from Locales L where codLocal=codigoLocal;
        End$$
Delimiter ;
-- call sp_CalcularMesesPendientes(1);

/* Samuel Isaac Escobar Vásquez 2020031 IN5AV */
Delimiter $$
	Create Procedure sp_CalcularMesesPendientesDeLocales()
		Begin 
        select L.codigoLocal,
			   L.saldoFavor, 
			   L.saldoContra, 
               L.mesesPendientes, 
               L.disponibilidad, 
               L.valorLocal, 
               L.valorAdministracion, 
               (L.valorLocal*L.mesesPendientes)-(L.saldoFavor-L.saldoContra) as totalRenta
               from Locales L;
        End$$
Delimiter ;
-- call sp_CalcularMesesPendientesDeLocales();

-- Samuel Isaac Escobar Vásquez 2020031 IN5AV
Delimiter $$
	Create Procedure sp_ExistenciaDeLocales()
		Begin 
			select count(L.codigoLocal) as localesExistentes 
					from Locales L;
        End$$
Delimiter ;

-- call sp_ExistenciaDeLocales();

-- Samuel Isaac Escobar Vásquez 2020031 IN5AV
Delimiter $$
	Create Procedure sp_LocalesDisponibles()
		Begin 
			select count(disponibilidad) as LocalesDisponibles 
				from Locales L where disponibilidad=true;
        End$$
Delimiter ;

-- call sp_LocalesDisponibles();

-- Samuel Isaac Escobar Vásquez 2020031 IN5AV
Delimiter $$
	Create Procedure sp_LocalesNoDisponibles()
		Begin 
			select count(disponibilidad) as LocalesNoDisponibles 
				from Locales L where disponibilidad=false;
        End$$
Delimiter ;

call sp_LocalesNoDisponibles();

ALTER USER 'root'@'localhost' identified with mysql_native_password By 'admin';

call sp_listarTipoClientes();
call sp_listarClientes();

select * from TipoCliente TC inner join Clientes C on TC.codigoTipoCliente = C.codigoTipoCliente
	where TC.descripcion = 'Buen Cliente Editado';
    
Create table Usuarios(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50) not null,
    contrasena varchar(50) not null,
    primary key pk_codigoUsuario (codigoUsuario)
);

Delimiter $$
	Create procedure sp_AgregarUsuario(in nombreUsuario varchar(100), in apellidoUsuario varchar(100), 
		in usuarioLogin varchar(50), in contrasena varchar(50))
	Begin
		Insert into Usuarios(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena)
			values (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
    End $$
Delimiter ;

Delimiter $$
	Create procedure sp_listarUsuarios()
	Begin
		Select
			U.codigoUsuario, 
            U.nombreUsuario, 
            U.apellidoUsuario, 
            U.usuarioLogin, 
            U.contrasena
		from Usuarios U;
    End $$
Delimiter ;

call sp_AgregarUsuario('Samuel','Escobar','sescobar','samuel2004');
call sp_listarUsuarios();

Create table Login(
    usuarioMaster varchar(50) not null,
    passwordLogin varchar(50) not null,
    primary key pk_usuarioMaster(usuarioMaster)
);

select * from usuarios;

select * from departamentos;

select * from cargos;

select * from horarios;

select * from empleados;

select * from Departamentos D Inner Join Empleados E 
	on D.codigoDepartamento = E.codigoDepartamento where E.codigoEmpleado = 1;