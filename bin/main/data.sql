INSERT INTO USUARIO (USERNAME, PASSWORD, ENABLED, VALIDATED) VALUES
('admin@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('citizen_uno@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('citizen_dos@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('contacto@pmc.mx', 'p4Ssword', 0, 0);

INSERT INTO USER_ROLE (ID, USERNAME, ROLE) VALUES
(1, 'admin@pmc.mx', 'ADMIN'),
(2, 'admin@pmc.mx', 'CITIZEN'),
(3, 'citizen_uno@pmc.mx', 'CITIZEN'),
(4, 'citizen_dos@pmc.mx', 'CITIZEN');

INSERT INTO PROVINCE (ID, NOMBRE, ABREVIATURA) VALUES
(1, 'Aguascalientes', 'AGS'),
(2, 'Baja California', 'BCN'),
(3, 'Baja California Sur', 'BCS'),
(4, 'Campeche', 'CAM'),
(5, 'Coahuila de Zaragoza', 'AGS'),
(9, 'Ciudad de México', 'CMX');

INSERT INTO MUNICIPALITY (ID, NOMBRE, NOMBRE_OFICIAL, ID_EXTERNO, PROVINCE_ID) VALUES
(1, 'Aguascalientes', 'Aguascalientes', '001', 01),
(2, 'Asientos', 'Asientos', '002', 01),
(3, 'Calvillo', 'Calvillo', '003', 01),
(4, 'Cosío', 'Cosío', '004', 01),
(5, 'El Llano', 'El Llano', '010', 01),
(6, 'Jesús María', 'Jesús María', '005', 01),
(7, 'Pabellón de Arteaga', 'Pabellón de Arteaga', '006', 01),
(8, 'Rincón de Romos', 'Rincón de Romos', '007', 01),
(9, 'San Francisco de los Romo', 'San Francisco de los Romo', '011', 01),
(10, 'San José de Gracia', 'San José de Gracia', '008', 01),
(11, 'Tepezalá', 'Tepezalá', '009', 01),
(12, 'Ensenada', 'Ensenada', '001', 02),
(13, 'Mexicali', 'Mexicali', '002', 02),
(14, 'Playas de Rosarito', 'Playas de Rosarito', '005', 02),
(15, 'Tecate', 'Tecate', '003', 02),
(16, 'Tijuana', 'Tijuana', '004', 02),
(17, 'Comondú', 'Comondú', '001', 03),
(18, 'La Paz', 'La Paz', '003', 03),
(19, 'Loreto', 'Loreto', '009', 03),
(20, 'Los Cabos', 'Los Cabos', '008', 03),
(21, 'Mulegé', 'Mulegé', '002', 03),
(22, 'Calakmul', 'Calakmul', '010', 04),
(23, 'Calkiní', 'Calkiní', '001', 04),
(24, 'Campeche', 'Campeche', '002', 04),
(25, 'Candelaria', 'Candelaria', '011', 04),
(26, 'Carmen', 'Carmen', '003', 04),
(27, 'Champotón', 'Champotón', '004', 04),
(28, 'Escárcega', 'Escárcega', '009', 04),
(29, 'Hecelchakán', 'Hecelchakán', '005', 04),
(30, 'Hopelchén', 'Hopelchén', '006', 04),
(31, 'Palizada', 'Palizada', '007', 04),
(32, 'Tenabo', 'Tenabo', '008', 04),
(33, 'Abasolo', 'Abasolo', '001', 05),
(34, 'Acuña', 'Acuña', '002', 05),
(35, 'Allende', 'Allende', '003', 05),
(36, 'Arteaga', 'Arteaga', '004', 05),
(37, 'Candela', 'Candela', '005', 05),
(38, 'Castaños', 'Castaños', '006', 05),
(39, 'Cuatro Ciénegas', 'Cuatro Ciénegas', '007', 05),
(40, 'Escobedo', 'Escobedo', '008', 05),
(41, 'Francisco I. Madero', 'Francisco I. Madero', '009', 05),
(42, 'Frontera', 'Frontera', '010', 05),
(43, 'General Cepeda', 'General Cepeda', '011', 05),
(44, 'Guerrero', 'Guerrero', '012', 05),
(45, 'Hidalgo', 'Hidalgo', '013', 05),
(46, 'Jiménez', 'Jiménez', '014', 05),
(47, 'Juárez', 'Juárez', '015', 05),
(48, 'Lamadrid', 'Lamadrid', '016', 05),
(49, 'Matamoros', 'Matamoros', '017', 05),
(50, 'Monclova', 'Monclova', '018', 05),
(51, 'Morelos', 'Morelos', '019', 05),
(52, 'Múzquiz', 'Múzquiz', '020', 05),
(53, 'Nadadores', 'Nadadores', '021', 05),
(54, 'Nava', 'Nava', '022', 05),
(55, 'Ocampo', 'Ocampo', '023', 05),
(56, 'Parras', 'Parras', '024', 05),
(57, 'Piedras Negras', 'Piedras Negras', '025', 05),
(58, 'Progreso', 'Progreso', '026', 05),
(59, 'Ramos Arizpe', 'Ramos Arizpe', '027', 05),
(60, 'Sabinas', 'Sabinas', '028', 05),
(61, 'Sacramento', 'Sacramento', '029', 05),
(62, 'Saltillo', 'Saltillo', '030', 05),
(63, 'San Buenaventura', 'San Buenaventura', '031', 05),
(64, 'San Juan de Sabinas', 'San Juan de Sabinas', '032', 05),
(65, 'San Pedro', 'San Pedro', '033', 05),
(66, 'Sierra Mojada', 'Sierra Mojada', '034', 05),
(67, 'Torreón', 'Torreón', '035', 05),
(68, 'Viesca', 'Viesca', '036', 05),
(69, 'Villa Unión', 'Villa Unión', '037', 05),
(70, 'Zaragoza', 'Zaragoza', '038', 05),
(269, 'Azcapotzalco', 'Azcapotzalco', '002', 09),
(270, 'Benito Juárez', 'Benito Juárez', '014', 09),
(271, 'Coyoacán', 'Coyoacán', '003', 09),
(272, 'Cuajimalpa de Morelos', 'Cuajimalpa de Morelos', '004', 09),
(273, 'Cuauhtémoc', 'Cuauhtémoc', '015', 09),
(274, 'Gustavo A. Madero', 'Gustavo A. Madero', '005', 09),
(275, 'Iztacalco', 'Iztacalco', '006', 09),
(276, 'Iztapalapa', 'Iztapalapa', '007', 09),
(277, 'La Magdalena Contreras', 'La Magdalena Contreras', '008', 09),
(278, 'Miguel Hidalgo', 'Miguel Hidalgo', '016', 09),
(279, 'Milpa Alta', 'Milpa Alta', '009', 09),
(280, 'Tláhuac', 'Tláhuac', '011', 09),
(281, 'Tlalpan', 'Tlalpan', '012', 09),
(282, 'Venustiano Carranza', 'Venustiano Carranza', '017', 09),
(283, 'Xochimilco', 'Xochimilco', '013', 09);

INSERT INTO TIPO_AYUDA (ID, NOMBRE, ACTIVE) VALUES
(1, 'Alimentacion', 1),
(2, 'Envios', 1),
(3, 'Traslados', 1),
(4, 'Apoyo Psicologico', 1),
(5, 'Apoyo Legal', 1);

INSERT INTO CIUDADANO (ID, NOMBRE, PATERNO, MATERNO, USERNAME, ACTIVE) VALUES
(1, 'Jorge', 'Cruz', 'Lopez', 'citizen_uno@pmc.mx', 1),
(2, 'Jose', 'Soto', 'Torres', 'citizen_dos@pmc.mx', 1);

INSERT INTO CIUDADANO_CONTACTO (ID, CIUDADANO_ID, TIPO_CONTACTO, CONTACTO) VALUES
(1, 1, 'TELEFONO_FIJO', '5544332211'),
(2, 1, 'TWITTER', '@Jorge'),
(3, 2, 'TELEFONO_MOVIL', '4477558899');

INSERT INTO GEO_LOCATION (ID, CALLE, NO_EXTERIOR, NO_INTERIOR, COLONIA, CODIGO_POSTAL, MUNICIPALITY_ID, LATITUDE, LONGITUDE) VALUES
(1, 'CALLE VIENA', '16', '', 'Col Juarez', '06600', 273, 19.430268, -99.157720);

INSERT INTO AYUDA (ID, DESCRIPCION, CIUDADANO_ID, GEO_LOCATION_ID, TIPO_AYUDA_ID, ORIGEN_AYUDA, FECHA_REGISTRO, ACTIVE) VALUES
(1, 'Necesito ir a mi cita de hemodiálisis 16:00', 1, 1, 3, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1);

INSERT INTO PETICION (ID, AYUDA_ID, CIUDADANO_ID, FECHA_PETICION) VALUES
(1, 1, 2, TIMESTAMP '2020-03-22 00:00:00.000');