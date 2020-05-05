INSERT INTO USUARIO (USERNAME, PASSWORD, ENABLED, VALIDATED) VALUES
('admin@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('vol1@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('chatbot@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('landing@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('citizen_uno@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('citizen_dos@pmc.mx', '$2a$10$Zah8wiwQ4lo9ZoGubLjQSOsPj7KTahQcTqJV0U6J/iLqOmmYfbG96', 1, 1),
('contacto@pmc.mx', 'p4Ssword', 0, 0);

INSERT INTO USER_ROLE (ID, USERNAME, ROLE) VALUES
(1, 'admin@pmc.mx', 'ADMIN'),
(2, 'admin@pmc.mx', 'CITIZEN'),
(7, 'admin@pmc.mx', 'MANAGER'),
(3, 'citizen_uno@pmc.mx', 'CITIZEN'),
(4, 'citizen_dos@pmc.mx', 'CITIZEN'),
(5, 'vol1@pmc.mx', 'VOLUNTARY'),
(6, 'chatbot@pmc.mx', 'CHATBOT'),
(8, 'landing@pmc.mx', 'LANDING');

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
(2, 'Envios / Traslados', 1),
(3, 'Farmacia', 1),
(4, 'Apoyo Psicologico', 1),
(5, 'Apoyo Legal', 1),
(6, 'Apoyo', 1),
(7, 'Otro', 1),
(8, 'Oferta Laboral', 1);

INSERT INTO DISCIPLINA (ID, NOMBRE, ACTIVE) VALUES
(1, 'Eduación / Capacitación / Asesoría', 1),
(2, 'Diseño Gráfico / Programación', 1),
(3, 'Difusión / Comunicación Social', 1),
(4, 'Apoyo Logístico / Call Center / Transporte)', 1),
(5, 'Medicina / Psicologia / Nutricion', 1),
(6, 'Oficios / Ayudante en General', 1),
(7, 'Otros', 1);

INSERT INTO CIUDADANO (ID, NOMBRE, PATERNO, MATERNO, USERNAME, ACTIVE) VALUES
(1, 'Jorge', 'Cruz', 'Lopez', 'citizen_uno@pmc.mx', 1),
(2, 'Jose', 'Soto', 'Torres', 'citizen_dos@pmc.mx', 1),
(3, 'Voluntario', '1', '', 'vol1@pmc.mx', 1),
(4, 'ChatBot', '1', '', 'chatbot@pmc.mx', 1),
(5, 'Landing', '1', '', 'landing@pmc.mx', 1);


INSERT INTO CIUDADANO_CONTACTO (ID, CIUDADANO_ID, TIPO_CONTACTO, CONTACTO) VALUES
(1, 1, 'TELEFONO_FIJO', '5544332211'),
(2, 1, 'TWITTER', '@Jorge'),
(3, 2, 'TELEFONO_MOVIL', '4477558899'),
(4, 3, 'TELEFONO_MOVIL', '5544332212');

INSERT INTO GEO_LOCATION (ID, CALLE, CODIGO_POSTAL, COLONIA, LATITUDE, LONGITUDE, NO_EXTERIOR, NO_INTERIOR, MUNICIPALITY_ID, DIRECCION) VALUES
(1, 'CALLE VIENA', '06600', 'Col Juarez', 19.430268, -99.157720, '16', '', 273, null),
(2, 'Av. Montevideo', '07300', 'Lindavista', 19.487753, -99.124337, '109', '', 274, null),
(11, NULL, '32000', NULL, 19.0414398, -98.2062727, NULL, NULL, NULL, NULL),
(12, NULL, '23000', NULL, 18.9242095, -99.22156590000002, NULL, NULL, NULL, NULL),
(13, NULL, '37000', NULL, 19.2572314, -99.1029664, NULL, NULL, NULL, NULL),
(15, NULL, '25000', NULL, 22.8905327, -109.9167371, NULL, NULL, NULL, NULL),
(16, NULL, '34000', NULL, 19.4326077, -99.133208, NULL, NULL, NULL, NULL),
(17, NULL, '31000', NULL, 19.4326077, -99.133208, NULL, NULL, NULL, NULL),
(18, NULL, '45000', NULL, 20.9170187, -101.1617356, NULL, NULL, NULL, NULL),
(19, NULL, '48000', NULL, 19.4397218, -99.13928089999999, NULL, NULL, NULL, NULL),
(20, NULL, '33000', NULL, 19.4326077, -99.133208, NULL, NULL, NULL, NULL),
(21, NULL, '43000', NULL, 19.3787857, -99.2085974, NULL, NULL, NULL, NULL),
(22, NULL, '31000', NULL, 19.2643208, -99.0087486, NULL, NULL, NULL, NULL),
(23, NULL, '27000', NULL, 19.3238645, -99.0557268, NULL, NULL, NULL, NULL),
(24, NULL, '57000', NULL, 25.6866142, -100.3161126, NULL, NULL, NULL, NULL),
(25, NULL, '45000', NULL, 19.412346, -99.1627985, NULL, NULL, NULL, NULL),
(26, NULL, '36000', NULL, 19.4326077, -99.133208, NULL, NULL, NULL, NULL),
(27, NULL, '36000', NULL, 19.4264968, -98.9769851, NULL, NULL, NULL, NULL),
(28, NULL, '31000', NULL, 19.5465132, -99.0674757, NULL, NULL, NULL, NULL),
(29, NULL, '31000', NULL, 19.4326077, -99.133208, NULL, NULL, NULL, NULL),
(30, NULL, '38000', NULL, 19.3286743, -99.03179929999999, NULL, NULL, NULL, NULL),
(31, NULL, '45000', NULL, 19.3286743, -99.03179929999999, NULL, NULL, NULL, NULL),
(32, NULL, '41000', NULL, 19.2763904, -99.1802147, NULL, NULL, NULL, NULL),
(33, NULL, '66000', NULL, 19.3103994, -99.0783622, NULL, NULL, NULL, NULL),
(34, NULL, '41000', NULL, 19.2811638, -99.2023662, NULL, NULL, NULL, NULL),
(35, NULL, '41000', NULL, 19.2811638, -99.2023662, NULL, NULL, NULL, NULL),
(36, NULL, '41000', NULL, 19.4326077, -99.133208, NULL, NULL, NULL, NULL),
(37, NULL, '44000', NULL, 19.5511611, -98.8784712, NULL, NULL, NULL, NULL),
(38, NULL, '49000', NULL, 19.4251118, -99.1534423, NULL, NULL, NULL, NULL),
(39, NULL, '27000', NULL, 19.4326077, -99.133208, NULL, NULL, NULL, NULL),
(40, NULL, '27000', NULL, 19.4326077, -99.133208, NULL, NULL, NULL, NULL),
(41, NULL, '55000', NULL, 19.4265083, -99.1450197, NULL, NULL, NULL, NULL),
(42, NULL, '26000', NULL, 19.4515487, -99.13391969999999, NULL, NULL, NULL, NULL),
(43, NULL, '37000', NULL, 19.3292295, -99.0218721, NULL, NULL, NULL, NULL),
(44, NULL, '24000', NULL, 19.6315094, -99.0626377, NULL, NULL, NULL, NULL),
(45, NULL, '26000', NULL, 20.7495089, -105.2477558, NULL, NULL, NULL, NULL),
(46, NULL, '40000', NULL, 19.3284111, -99.2868745, NULL, NULL, NULL, NULL),
(47, NULL, '38000', NULL, 19.4468231, -99.1303962, NULL, NULL, NULL, NULL),
(48, NULL, '50000', NULL, 18.8086564, -99.2037508, NULL, NULL, NULL, NULL),
(49, NULL, '43000', NULL, 18.8447817, -99.1871364, NULL, NULL, NULL, NULL),
(50, NULL, '24000', NULL, 18.854329, -99.1769109, NULL, NULL, NULL, NULL),
(51, NULL, '17000', NULL, 19.4795161, -99.06150249999999, NULL, NULL, NULL, NULL),
(52, NULL, '22000', NULL, 19.6545214, -99.0790569, NULL, NULL, NULL, NULL),
(53, NULL, '63000', NULL, 18.8106107, -99.1976931, NULL, NULL, NULL, NULL),
(54, NULL, '30000', NULL, 18.8882582, -99.15017279999999, NULL, NULL, NULL, NULL),
(55, NULL, '46000', NULL, 18.8139073, -99.2016738, NULL, NULL, NULL, NULL),
(56, NULL, '53000', NULL, 22.8317744, -105.7791327, NULL, NULL, NULL, NULL),
(57, NULL, '24000', NULL, 19.0605764, -98.1324904, NULL, NULL, NULL, NULL),
(58, NULL, '56000', NULL, 19.3110265, -99.145173, NULL, NULL, NULL, NULL),
(59, NULL, '31000', NULL, 17.9714931, -92.90885759999999, NULL, NULL, NULL, NULL),
(60, NULL, '30000', NULL, 19.3418112, -99.13857499999999, NULL, NULL, NULL, NULL),
(61, NULL, '58000', NULL, 18.8652238, -99.17536989999999, NULL, NULL, NULL, NULL);


INSERT INTO AYUDA (ID, DESCRIPCION, CIUDADANO_ID, GEO_LOCATION_ID, TIPO_AYUDA_ID, ORIGEN_AYUDA, FECHA_REGISTRO, ACTIVE, ESTATUS_AYUDA) VALUES
(1, 'Necesito ir a mi cita de hemodiálisis 16:00', 1, 1, 3, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA'),
(2, 'Necesito despensa basica', 2, 2, 1, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'PENDIENTE'),
(3, 'Necesito ir a mi cita de hemodiálisis 16:00', 1, 11, 2, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA'),
(4, 'Necesito despensa basica', 2, 12, 4, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'PENDIENTE'),
(5, 'Necesito ir a mi cita de hemodiálisis 16:00', 1, 13, 5, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA'),
(6, 'Necesito despensa basica', 2, 15, 6, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA'),
(7, 'Necesito ir a mi cita de hemodiálisis 16:00', 1, 16, 3, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'PENDIENTE'),
(8, 'Necesito despensa basica', 2, 17, 1, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA'),
(9, 'Necesito ir a mi cita de hemodiálisis 16:00', 1, 18, 3, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA'),
(10, 'Necesito despensa basica', 2, 19, 1, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA'),
(11, 'Necesito ir a mi cita de hemodiálisis 16:00', 1, 20, 3, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA'),
(12, 'Necesito despensa basica', 2, 21, 1, 'SOLICITA', TIMESTAMP '2020-03-20 00:00:00.000', 1, 'NUEVA');

INSERT INTO PETICION (ID, AYUDA_ID, CIUDADANO_ID, FECHA_PETICION) VALUES
(1, 1, 2, TIMESTAMP '2020-03-22 00:00:00.000');