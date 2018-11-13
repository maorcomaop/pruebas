-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.6.28-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para bd_coomotorneiva_rdtow1
DROP DATABASE IF EXISTS `rdw_bd`;
CREATE DATABASE IF NOT EXISTS `rdw_bd` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `rdw_bd`;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_consulta_liquidacion
DROP PROCEDURE IF EXISTS `proc_consulta_liquidacion`;
DELIMITER //
CREATE DEFINER=`soporte_registel`@`%` PROCEDURE `proc_consulta_liquidacion`(IN `fecha_liquidacion` DATE)
BEGIN

 -- BETWEEN CONCAT(fecha_liquidacion,' 00:00:00') AND CONCAT(fecha_liquidacion,' 23:59:59')
 -- DECLARE consulta text;

    IF fecha_liquidacion IS NOT NULL
    THEN

 (SELECT
  tmp_tir.FECHA_INGRESO AS Fecha,
  (SELECT
    COUNT(*) AS Ncarros
  FROM (SELECT DISTINCT
    tir6.FK_VEHICULO
  FROM tbl_informacion_registradora tir6
  WHERE tir6.FECHA_INGRESO = fecha_liquidacion) r) AS `V Reportados`,
  (SELECT
    COUNT(*) AS NcarrosL
  FROM (SELECT
    tlg.FK_VEHICULO
  FROM tbl_liquidacion_general tlg
  WHERE tlg.FECHA_MODIFICACION BETWEEN CONCAT(fecha_liquidacion,' 00:00:00') AND CONCAT(fecha_liquidacion,' 23:59:59')
  GROUP BY tlg.FK_VEHICULO) nl) AS `V Liquidados`,
  tmp_tir.nom_empresa AS Empresa,
  tmp_tir.PLACA AS Placa,
  tmp_tir.NUM_INTERNO AS Interno,
  IFNULL(tmp_tir.NOMBRE, 'No Ruta') AS Ruta,
  COUNT(*) AS `N° V Reconoce Ruta`,
  (SELECT
    COUNT(lv.PK_LIQUIDACION_VUELTAS) AS expr1
  FROM tbl_liquidacion_vueltas lv
    INNER JOIN tbl_informacion_registradora ir
      ON lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA
  WHERE ir.FK_VEHICULO = tmp_tir.FK_VEHICULO AND lv.FECHA_MODIFICACION BETWEEN CONCAT(fecha_liquidacion,' 00:00:00') AND CONCAT(fecha_liquidacion,' 23:59:59')) AS `vueltas liquidadas`,
  tmp_tir.TOTAL_PASAJEROS_LIQUIDADOS AS `Pasjeros Liquidados`
FROM (SELECT
  tbl_informacion_registradora.FK_VEHICULO,
  tbl_informacion_registradora.FK_RUTA,
  tbl_ruta.NOMBRE,
  tbl_vehiculo.PLACA,
  tbl_vehiculo.NUM_INTERNO,
  tbl_empresa.NOMBRE AS nom_empresa,
  tbl_liquidacion_general.TOTAL_PASAJEROS_LIQUIDADOS,
  tbl_informacion_registradora.FECHA_INGRESO
FROM tbl_informacion_registradora
  LEFT OUTER JOIN tbl_ruta
    ON tbl_informacion_registradora.FK_RUTA = tbl_ruta.PK_RUTA AND tbl_informacion_registradora.FK_RUTA = tbl_ruta.PK_RUTA
  LEFT OUTER JOIN tbl_vehiculo
    ON tbl_informacion_registradora.FK_VEHICULO = tbl_vehiculo.PK_VEHICULO
  LEFT OUTER JOIN tbl_empresa
    ON tbl_vehiculo.FK_EMPRESA = tbl_empresa.PK_EMPRESA
  LEFT OUTER JOIN tbl_liquidacion_vueltas
    ON tbl_liquidacion_vueltas.FK_INFORMACION_REGISTRADORA = tbl_informacion_registradora.PK_INFORMACION_REGISTRADORA
  LEFT OUTER JOIN tbl_liquidacion_general
    ON tbl_liquidacion_general.FK_VEHICULO = tbl_vehiculo.PK_VEHICULO AND tbl_liquidacion_vueltas.FK_LIQUIDACION_GENERAL = tbl_liquidacion_general.PK_LIQUIDACION_GENERAL AND tbl_liquidacion_general.PK_LIQUIDACION_GENERAL = tbl_liquidacion_vueltas.FK_LIQUIDACION_GENERAL
WHERE tbl_informacion_registradora.FECHA_INGRESO = fecha_liquidacion AND tbl_informacion_registradora.PK_INFORMACION_REGISTRADORA = tbl_liquidacion_vueltas.FK_INFORMACION_REGISTRADORA AND tbl_vehiculo.PK_VEHICULO = tbl_liquidacion_general.FK_VEHICULO AND tbl_liquidacion_general.FECHA_MODIFICACION BETWEEN CONCAT(fecha_liquidacion,' 00:00:00') AND CONCAT(fecha_liquidacion,' 23:59:59') AND tbl_liquidacion_vueltas.FECHA_MODIFICACION BETWEEN CONCAT(fecha_liquidacion,' 00:00:00') AND CONCAT(fecha_liquidacion,' 23:59:59')
GROUP BY tbl_informacion_registradora.PK_INFORMACION_REGISTRADORA,
         tbl_informacion_registradora.FK_VEHICULO,
         tbl_informacion_registradora.FK_RUTA,
         tbl_ruta.NOMBRE,
         tbl_vehiculo.PLACA,
         tbl_vehiculo.NUM_INTERNO,
         tbl_empresa.NOMBRE,
         tbl_liquidacion_general.TOTAL_PASAJEROS_LIQUIDADOS,
         tbl_informacion_registradora.FECHA_INGRESO) tmp_tir
GROUP BY tmp_tir.FK_RUTA,
         tmp_tir.FK_VEHICULO,
         tmp_tir.PLACA,
         tmp_tir.NUM_INTERNO,
         tmp_tir.nom_empresa,
         tmp_tir.TOTAL_PASAJEROS_LIQUIDADOS,
         tmp_tir.FECHA_INGRESO
ORDER BY tmp_tir.NUM_INTERNO)

UNION
  -- espacio entre consultas
  (SELECT IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''))
UNION
  -- encabezado segunda consulta
  (SELECT IFNULL(NULL,'Fecha'),IFNULL(NULL,'Empresa'),IFNULL(NULL,'Placa'),IFNULL(NULL,'Interno'),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''),IFNULL(NULL,''))
UNION

  -- Consulta Internos que no Liquidaron 
(SELECT
  tblIr.FECHA_INGRESO AS Fecha,
  tblEm.NOMBRE AS Nombre,
  tblVi.PLACA AS Placa,
  tblVi.NUM_INTERNO AS Interno,
  IFNULL(NULL,''),
  IFNULL(NULL,''),
  IFNULL(NULL,''),
  IFNULL(NULL,''),
  IFNULL(NULL,''),
  IFNULL(NULL,'')
FROM tbl_informacion_registradora tblIr
  INNER JOIN tbl_vehiculo tblVi
    ON tblIr.FK_VEHICULO = tblVi.PK_VEHICULO AND tblIr.FK_VEHICULO NOT IN (SELECT
      tlg.FK_VEHICULO
    FROM tbl_liquidacion_general tlg
    WHERE tlg.FECHA_MODIFICACION BETWEEN CONCAT(fecha_liquidacion,' 00:00:00') AND CONCAT(fecha_liquidacion,' 23:59:59'))
  INNER JOIN tbl_empresa tblEm
    ON tblVi.FK_EMPRESA = tblEm.PK_EMPRESA
WHERE tblIr.FECHA_INGRESO = fecha_liquidacion
GROUP BY tblIr.FK_VEHICULO,
         tblIr.FECHA_INGRESO,
         tblEm.NOMBRE);

    END IF;
 END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_consulta_liquidacion_x_empresa
DROP PROCEDURE IF EXISTS `proc_consulta_liquidacion_x_empresa`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_consulta_liquidacion_x_empresa`(IN `inu_id_empresa` INT, IN `idt_fecha_inicio` DATE, IN `idt_fecha_fin` DATE, IN `inu_orden_proceso` INT)
BEGIN

DECLARE orc_retorno   VARCHAR(20) DEFAULT 'OPCION NO VALIDA';      



IF inu_orden_proceso = 0       

THEN           

	BLOCK_RETORNO_QUERY_BUILDER:           

	BEGIN                 

		DECLARE sb_consulta   TEXT;                 

		DECLARE sb_query_execute   varchar(100);                 

		SET orc_retorno = 'e';                 

		
		IF inu_id_empresa IS NOT NULL AND idt_fecha_inicio IS NOT NULL AND idt_fecha_fin IS NOT NULL

		THEN                     

			SET sb_consulta = "	SELECT v.PK_VEHICULO,  v.PLACA, v.NUM_INTERNO ";
		
			SET sb_consulta = CONCAT(sb_consulta, ", (SELECT COUNT(lv.PK_LIQUIDACION_VUELTAS) FROM tbl_liquidacion_vueltas lv INNER JOIN tbl_informacion_registradora ir ON lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA WHERE ir.FK_VEHICULO = v.PK_VEHICULO AND (lv.FECHA_MODIFICACION BETWEEN '", idt_fecha_inicio, " 00:00:00' AND '", idt_fecha_fin , " 23:59:59')) AS VUELTAS_LIQUIDADAS  ");
	      SET sb_consulta = CONCAT(sb_consulta, ", (SELECT COUNT(ir.pk_informacion_registradora) FROM tbl_informacion_registradora ir WHERE ir.FK_VEHICULO = v.PK_VEHICULO AND (ir.FECHA_INGRESO BETWEEN '", idt_fecha_inicio, " 00:00:00' AND '",idt_fecha_fin ," 23:59:59')) AS VUELTAS_REPORTADAS");
	      SET sb_consulta = CONCAT(sb_consulta, ", SUM(TOTAL_PASAJEROS_LIQUIDADOS) AS TOTAL_PASAJEROS_LIQUIDADOS");
	      SET sb_consulta = CONCAT(sb_consulta, ", (SELECT SUM(lv.PASAJEROS_DESCUENTO) FROM tbl_liquidacion_vueltas lv INNER JOIN tbl_informacion_registradora ir ON lv.FK_INFORMACION_REGISTRADORA = ir.PK_INFORMACION_REGISTRADORA WHERE ir.FK_VEHICULO = v.PK_VEHICULO AND lv.FK_LIQUIDACION_GENERAL = lg.PK_LIQUIDACION_GENERAL) AS TOTAL_PASAJEROS_DESCONTADOS");
	      SET sb_consulta = CONCAT(sb_consulta, ", COUNT(lg.PK_LIQUIDACION_GENERAL) AS CANTIDAD_LIQUIDACIONES");
	      SET sb_consulta = CONCAT(sb_consulta, ", SUM(lg.TOTAL_VALOR_VUELTAS - lg.TOTAL_VALOR_DESCUENTOS_PASAJEROS - lg.TOTAL_VALOR_DESCUENTOS_ADICIONAL) AS TOTAL_LIQUIDACION");
	      SET sb_consulta = CONCAT(sb_consulta, ", SUM(lg.TOTAL_VALOR_DESCUENTOS_PASAJEROS) AS TOTAL_VALOR_DESCUENTOS_PSJ");      
	      SET sb_consulta = CONCAT(sb_consulta, ", SUM(lg.TOTAL_VALOR_DESCUENTOS_ADICIONAL) AS TOTAL_VALOR_DESCUENTOS_ADC");

			SET sb_consulta = CONCAT(sb_consulta, " FROM tbl_liquidacion_general lg INNER JOIN tbl_vehiculo v ON lg.FK_VEHICULO = v.PK_VEHICULO ");
			
			SET sb_consulta = CONCAT(sb_consulta, " WHERE lg.estado = 1 ");
	      SET sb_consulta = CONCAT(sb_consulta, " AND (lg.FECHA_LIQUIDACION BETWEEN '",idt_fecha_inicio," 00:00:00' AND '",idt_fecha_fin," 23:59:59') ");
	      SET sb_consulta = CONCAT(sb_consulta, " AND v.ESTADO = 1 "); 
	   	SET sb_consulta = CONCAT(sb_consulta, " AND v.FK_EMPRESA =",inu_id_empresa);
	      SET sb_consulta = CONCAT(sb_consulta, " GROUP BY lg.FK_VEHICULO ");
	      SET sb_consulta = CONCAT(sb_consulta, " ORDER BY v.NUM_INTERNO ;"); 
	      
	      SET @sb_query_execute = sb_consulta;                 

			PREPARE stmt FROM @sb_query_execute;                 

			EXECUTE stmt; 
		
		ELSE
		
			SET orc_retorno = 'NINGUNO DE LOS PARAMETROS DEBE SER VACIO O NULO.'; 
			SELECT orc_retorno;     
		END IF; 

		                  
	END BLOCK_RETORNO_QUERY_BUILDER;       

END IF;       



IF inu_orden_proceso <> 0  OR inu_orden_proceso IS NULL

THEN          

	SELECT orc_retorno;       

END IF;    

END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_acceso
DROP PROCEDURE IF EXISTS `proc_tbl_acceso`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_acceso`(   IN  `inu_pk_acceso`           int,   IN  `inu_fk_grupo`            int,   IN  `isb_nombre_acceso`       varchar(50),   IN  `isb_codigo_acceso`       varchar(50),   IN  `isb_nombre_largo`        varchar(200),   IN  `isb_tecla_acceso`        varchar(50),   IN  `isb_ubicacion`           varchar(80),   IN  `isb_imagen`              varchar(100),   IN  `isb_subgrupo`            varchar(100),   IN  `inu_posicion`            int,   IN  `inu_posicionsubgrupo`    int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_acceso(FK_GRUPO, NOMBRE_ACCESO, CODIGO_ACCESO,NOMBRE_LARGO, TECLA_ACCESO, UBICACION, Imagen, SUBGRUPO,POSICION, POSICIONSUBGRUPO, ESTADO , MODIFICACION_LOCAL, PK_UNICA)          VALUES (inu_fk_grupo, isb_nombre_acceso, isb_codigo_acceso, isb_nombre_largo, isb_tecla_acceso, isb_ubicacion, isb_imagen, isb_subgrupo, inu_posicion, inu_posicionsubgrupo, ibo_estado, ibo_modificacion_local, isb_pk_unica);          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_acceso IS NOT NULL          THEN             UPDATE tbl_acceso             SET     FK_GRUPO = inu_fk_grupo,                     NOMBRE_ACCESO = isb_nombre_acceso,                     CODIGO_ACCESO = isb_codigo_acceso,                      NOMBRE_LARGO = isb_nombre_largo,                     TECLA_ACCESO = isb_tecla_acceso,                     UBICACION = isb_ubicacion,                     Imagen = isb_imagen,                     SUBGRUPO = isb_subgrupo,                     POSICION = inu_posicion,                     POSICIONSUBGRUPO = inu_posicionsubgrupo,                     ESTADO = ibo_estado,                     MODIFICACION_LOCAL = ibo_modificacion_local,                     PK_UNICA = isb_pk_unica             WHERE PK_ACCESO = inu_pk_acceso;             SET orc_retorno = inu_pk_acceso;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_acceso IS NOT NULL          THEN             DELETE FROM tbl_acceso             WHERE PK_ACCESO = inu_pk_acceso;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_acceso WHERE tbl_acceso.PK_ACCESO > 0";                 IF inu_pk_acceso IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.PK_ACCESO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_acceso);                 END IF;                 IF inu_fk_grupo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.FK_GRUPO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_grupo);                 END IF;                 IF isb_nombre_acceso IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.NOMBRE_ACCESO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre_acceso);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF isb_codigo_acceso IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.CODIGO_ACCESO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_codigo_acceso);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF isb_tecla_acceso IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.TECLA_ACCESO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_tecla_acceso);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF isb_ubicacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.UBICACION like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_ubicacion);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF isb_imagen IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.Imagen like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_imagen);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF isb_subgrupo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.SUBGRUPO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_subgrupo);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                  IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso.PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_acceso               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                      AND tbl_acceso.MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_acceso_perfil
DROP PROCEDURE IF EXISTS `proc_tbl_acceso_perfil`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_acceso_perfil`(   IN  `inu_pk_acceso_perfil`    int,   IN  `inu_fk_perfil`           int,   IN  `inu_pk_acceso`           int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT            INTO tbl_acceso_perfil(FK_PERFIL, PK_ACCESO, ESTADO , MODIFICACION_LOCAL, PK_UNICA)          VALUES (inu_fk_perfil, inu_pk_acceso, ibo_estado , ibo_modificacion_local, isb_pk_unica);          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_acceso_perfil IS NOT NULL          THEN             UPDATE tbl_acceso_perfil                SET FK_PERFIL = inu_fk_perfil,                    PK_ACCESO = inu_pk_acceso,                    ESTADO = ibo_estado,                    MODIFICACION_LOCAL = ibo_modificacion_local,                    PK_UNICA = isb_pk_unica              WHERE PK_ACCESO_PERFIL = inu_pk_acceso_perfil;             SET orc_retorno = inu_pk_acceso_perfil;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_acceso_perfil IS NOT NULL          THEN             DELETE FROM tbl_acceso_perfil              WHERE PK_ACCESO_PERFIL = inu_pk_acceso_perfil;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN         BLOCK_RETORNO_QUERY_BUILDER:          BEGIN             DECLARE sb_consulta        TEXT;             DECLARE sb_query_execute   varchar(100);             SET orc_retorno = 'e';             SET sb_consulta =                    "SELECT * FROM tbl_acceso_perfil WHERE tbl_acceso_perfil.PK_ACCESO_PERFIL > 0";             IF inu_pk_acceso_perfil IS NOT NULL             THEN                SET sb_consulta =                       CONCAT(sb_consulta,                              " AND tbl_acceso_perfil.PK_ACCESO_PERFIL = ");                SET sb_consulta = CONCAT(sb_consulta, inu_pk_acceso_perfil);             END IF;             IF inu_fk_perfil IS NOT NULL             THEN                SET sb_consulta =                       CONCAT(sb_consulta,                              " AND tbl_acceso_perfil.FK_PERFIL = ");                SET sb_consulta = CONCAT(sb_consulta, inu_fk_perfil);             END IF;             IF inu_pk_acceso IS NOT NULL             THEN                SET sb_consulta =                       CONCAT(sb_consulta,                              " AND tbl_acceso_perfil.PK_ACCESO = ");                SET sb_consulta = CONCAT(sb_consulta, inu_pk_acceso);             END IF;             IF ibo_estado IS NOT NULL             THEN                SET sb_consulta =                       CONCAT(sb_consulta,                              " AND tbl_acceso_perfil.ESTADO = ");                SET sb_consulta = CONCAT(sb_consulta, ibo_estado);             END IF;             IF ibo_modificacion_local IS NOT NULL             THEN                SET sb_consulta =                       CONCAT(sb_consulta,                              " AND tbl_acceso_perfil.MODIFICACION_LOCAL = ");                SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);             END IF;             IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_acceso_perfil.PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;             SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;          END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN          SET orc_retorno = 'e';          IF idt_sincronizacion IS NOT NULL          THEN             SELECT *               FROM tbl_acceso_perfil               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                    AND tbl_acceso_perfil.MODIFICACION_LOCAL = 1;          END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_acople
DROP PROCEDURE IF EXISTS `proc_tbl_acople`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_acople`(   IN  `inu_pk_acople`       int,   IN  `isb_nombre_empresa`  varchar(100),   IN  `isb_codigo`          varchar(100),   IN  `ibo_estado`          boolean,   IN  `inu_orden_proceso`   int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_acople( NOMBRE_EMPRESA, CODIGO, ESTADO )          VALUES ( isb_nombre_empresa, isb_codigo, ibo_estado );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_acople IS NOT NULL          THEN             UPDATE tbl_acople             SET     NOMBRE_EMPRESA = isb_nombre_empresa ,                     CODIGO = isb_codigo ,                     ESTADO = ibo_estado              WHERE PK_ACOPLE = inu_pk_acople;             SET orc_retorno = inu_pk_acople;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_acople IS NOT NULL          THEN             DELETE FROM tbl_acople             WHERE PK_ACOPLE = inu_pk_acople;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_acople WHERE PK_ACOPLE > 0";                 IF inu_pk_acople IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_ACOPLE = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_acople);                 END IF;                 IF isb_codigo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  CODIGO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_codigo);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_nombre_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE_EMPRESA like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre_empresa);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_acople               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_acople_empresa
DROP PROCEDURE IF EXISTS `proc_tbl_acople_empresa`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_acople_empresa`(   IN  `inu_pk_acople_empresa`  int,   IN  `inu_fk_empresa`         int,   IN  `inu_fk_acople`          int,   IN  `ibo_estado`             boolean,   IN  `inu_orden_proceso`      int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_acople_empresa( FK_EMPRESA, FK_ACOPLE, ESTADO )          VALUES ( inu_fk_empresa, inu_fk_acople, ibo_estado );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_acople_empresa IS NOT NULL          THEN             UPDATE tbl_acople_empresa             SET     FK_EMPRESA = inu_fk_empresa ,                     FK_ACOPLE = inu_fk_acople ,                     ESTADO = ibo_estado             WHERE PK_ACOPLE_EMPRESA = inu_pk_acople_empresa;             SET orc_retorno = inu_pk_acople_empresa;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_acople_empresa IS NOT NULL          THEN             DELETE FROM tbl_acople_empresa             WHERE PK_ACOPLE_EMPRESA = inu_pk_acople_empresa;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_acople_empresa WHERE PK_ACOPLE_EMPRESA > 0";                 IF inu_pk_acople_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_ACOPLE_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_acople_empresa);                 END IF;                 IF inu_fk_acople IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_ACOPLE = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_acople);                 END IF;                 IF inu_fk_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_empresa);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_acople_empresa               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_agrupacion
DROP PROCEDURE IF EXISTS `proc_tbl_agrupacion`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_agrupacion`(   IN  `inu_pk_agrupacion`       int,   IN  `isb_nombre`              varchar(100),   IN  `inu_fk_empresa`          int,   IN  `ibo_aplicar_tiempos`     boolean,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_agrupacion( NOMBRE, FK_EMPRESA, APLICARTIEMPOS, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( isb_nombre, inu_fk_empresa,ibo_aplicar_tiempos, ibo_estado, ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_agrupacion IS NOT NULL          THEN             UPDATE tbl_agrupacion             SET     NOMBRE = isb_nombre ,                     FK_EMPRESA = inu_fk_empresa ,                     APLICARTIEMPOS = ibo_aplicar_tiempos,                     ESTADO = ibo_estado ,                                         MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_AGRUPACION = inu_pk_agrupacion;             SET orc_retorno = inu_pk_agrupacion;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_agrupacion IS NOT NULL          THEN             DELETE FROM tbl_agrupacion             WHERE PK_AGRUPACION = inu_pk_agrupacion;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_agrupacion WHERE PK_AGRUPACION > 0";                 IF inu_pk_agrupacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_AGRUPACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_agrupacion);                 END IF;                 IF inu_fk_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_empresa);                 END IF;                 IF isb_nombre IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_aplicar_tiempos IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND APLICARTIEMPOS = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_aplicar_tiempos);                 END IF;                  SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_agrupacion               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_agrupacion_ruta
DROP PROCEDURE IF EXISTS `proc_tbl_agrupacion_ruta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_agrupacion_ruta`(   IN  `inu_pk_agrupacion_ruta`  int,   IN  `inu_fk_agrupacion`       int,   IN  `inu_fk_ruta`             int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_agrupacion_ruta( FK_AGRUPACION, FK_RUTA, ESTADO, MODIFICACION_LOCAL, PK_UNICA)          VALUES ( inu_fk_agrupacion, inu_fk_ruta, ibo_estado, ibo_modificacion_local, isb_pk_unica);          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_agrupacion_ruta IS NOT NULL          THEN             UPDATE tbl_agrupacion_ruta             SET     FK_AGRUPACION = inu_fk_agrupacion ,                     FK_RUTA = inu_fk_ruta ,                     ESTADO = ibo_estado, 					MODIFICACION_LOCAL = ibo_modificacion_local , 					PK_UNICA = isb_pk_unica             WHERE PK_AGRUPACION_RUTA = inu_pk_agrupacion_ruta;             SET orc_retorno = inu_pk_agrupacion_ruta;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_agrupacion_ruta IS NOT NULL          THEN             DELETE FROM tbl_agrupacion_ruta             WHERE PK_AGRUPACION_RUTA = inu_pk_agrupacion_ruta;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_agrupacion_ruta WHERE PK_AGRUPACION_RUTA > 0";                 IF inu_pk_agrupacion_ruta IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_AGRUPACION_RUTA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_agrupacion_ruta);                 END IF;                 IF inu_fk_ruta IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_RUTA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_ruta);                 END IF;                 IF inu_fk_agrupacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_AGRUPACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_agrupacion);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF; 				IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_agrupacion_ruta               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 6       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_agrupacion_vehiculo
DROP PROCEDURE IF EXISTS `proc_tbl_agrupacion_vehiculo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_agrupacion_vehiculo`(   IN  `inu_pk_agrupacion_vehiculo`  int,   IN  `inu_fk_agrupacion`           int,   IN  `inu_fk_vehiculo`             int,   IN  `ibo_estado`                  boolean,   IN  `inu_orden_proceso`           int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_agrupacion_vehiculo( FK_AGRUPACION, FK_VEHICULO, ESTADO )          VALUES ( inu_fk_agrupacion, inu_fk_vehiculo, ibo_estado );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_agrupacion_vehiculo IS NOT NULL          THEN             UPDATE tbl_agrupacion_vehiculo             SET     FK_AGRUPACION = inu_fk_agrupacion ,                     FK_VEHICULO = inu_fk_vehiculo ,                     ESTADO = ibo_estado             WHERE PK_AGRUPACION_VEHICULO = inu_pk_agrupacion_vehiculo;             SET orc_retorno = inu_pk_agrupacion_vehiculo;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_agrupacion_vehiculo IS NOT NULL          THEN             DELETE FROM tbl_agrupacion_vehiculo             WHERE PK_AGRUPACION_VEHICULO = inu_pk_agrupacion_vehiculo;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_agrupacion_vehiculo WHERE PK_AGRUPACION_VEHICULO > 0";                 IF inu_pk_agrupacion_vehiculo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_AGRUPACION_VEHICULO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_agrupacion_vehiculo);                 END IF;                 IF inu_fk_vehiculo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_VEHICULO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo);                 END IF;                 IF inu_fk_agrupacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_AGRUPACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_agrupacion);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_agrupacion_vehiculo               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_alarma
DROP PROCEDURE IF EXISTS `proc_tbl_alarma`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_alarma`(   
IN  `inu_pk_alarma`           int,   
IN  `inu_codigo_alarma`       int,   
IN  `isb_nombre`              text,   
IN  `isb_tipo`                varchar(40),   
IN  `isb_unidad_medicion`     varchar(40),   
IN  `inu_prioridad`           int,   
IN  `ibo_estado`              boolean,   
IN  `ibo_modificacion_local`  boolean,   
IN  `isb_pk_unica`            varchar(20),   
IN  `idt_sincronizacion`      datetime,   
IN  `idt_sincronizacion_end`  datetime,   
IN  `inu_orden_proceso`       int )
BEGIN              
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              

IF inu_orden_proceso = 0       
THEN          
SET orc_retorno = '0';          
	INSERT INTO tbl_alarma(CODIGO_ALARMA ,NOMBRE, TIPO, UNIDAD_MEDICION, PRIORIDAD, ESTADO, MODIFICACION_LOCAL, PK_UNICA)          
	VALUES (inu_codigo_alarma, isb_nombre , isb_tipo , isb_unidad_medicion, inu_prioridad, ibo_estado , ibo_modificacion_local, isb_pk_unica);          
COMMIT;       
END IF;              

IF inu_orden_proceso = 1       
THEN          
	SET orc_retorno = 'e';          
	IF inu_pk_alarma IS NOT NULL          
	THEN             
		UPDATE tbl_alarma             
		SET     CODIGO_ALARMA = inu_codigo_alarma,                     
				NOMBRE = isb_nombre,                      
				TIPO = isb_tipo,                      
				UNIDAD_MEDICION = isb_unidad_medicion,                      
				PRIORIDAD = inu_prioridad,                      
				ESTADO = ibo_estado,                     
				MODIFICACION_LOCAL = ibo_modificacion_local,                     
				PK_UNICA = isb_pk_unica             
		WHERE PK_ALARMA = inu_pk_alarma;             
	SET orc_retorno = inu_pk_alarma;          
	END IF;       
END IF;              

IF inu_orden_proceso = 2       THEN          
	IF inu_pk_alarma IS NOT NULL          
	THEN             
		DELETE FROM tbl_alarma             
		WHERE PK_ALARMA = inu_pk_alarma;             
		COMMIT;             
		SET orc_retorno = '1';          
	END IF;       
END IF;       

IF inu_orden_proceso = 3       THEN           
	BLOCK_RETORNO_QUERY_BUILDER:           
	BEGIN                 
		DECLARE sb_consulta   TEXT;                 
		DECLARE sb_query_execute   varchar(100);                 
		SET orc_retorno = 'e';                 
		SET sb_consulta = "SELECT * FROM tbl_alarma WHERE tbl_alarma.PK_ALARMA > 0";                 
		
		IF inu_pk_alarma IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma.PK_ALARMA = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_pk_alarma);                 
		END IF;                 
		IF inu_codigo_alarma IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma.CODIGO_ALARMA = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_codigo_alarma);                 
		END IF;                 
		IF isb_nombre IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma.NOMBRE like '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                  
		IF isb_tipo IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma.TIPO like '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_tipo);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                  
		IF isb_unidad_medicion IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma.UNIDAD_MEDICION like '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_unidad_medicion);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                  
		IF inu_prioridad IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma.PRIORIDAD = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_prioridad);                 
		END IF;                 
		IF ibo_estado IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma.ESTADO = ");                     
			SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
		END IF;                 
		IF ibo_modificacion_local IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma.MODIFICACION_LOCAL = ");                     
			SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
		END IF;                  
		IF isb_pk_unica IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		SET @sb_query_execute = sb_consulta;                 
		PREPARE stmt FROM @sb_query_execute;                 
		EXECUTE stmt;                   
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;    
          
IF inu_orden_proceso = 4       
THEN            
	SET orc_retorno = 'e';           
	IF idt_sincronizacion IS NOT NULL           
	THEN               
		SELECT *                
		FROM tbl_alarma               
		WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     
		AND tbl_alarma.MODIFICACION_LOCAL = 1;           
	END IF;       
END IF;              

IF inu_orden_proceso <> 4       
THEN          
	SELECT orc_retorno;       
END IF;    
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_alarma_info_regis
DROP PROCEDURE IF EXISTS `proc_tbl_alarma_info_regis`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_alarma_info_regis`(   
IN  `inu_pk_air`                       int,   
IN  `inu_fk_alarma`                    int,   
IN  `inu_fk_informacion_registradora`  int,   
IN  `idt_fecha_alarma`                 date,   
IN  `idt_hora_alarma`                  time,   
IN  `inu_cantidad_alarma`              int,   
IN  `ibo_estado`                       boolean,   
IN  `ibo_modificacion_local`           boolean,   
IN  `isb_pk_unica`                     varchar(20),   
IN  `idt_fecha_inicio`                 datetime,   
IN  `idt_fecha_fin`                    datetime,   
IN  `idt_sincronizacion`               datetime,   
IN  `idt_sincronizacion_end`           datetime,   
IN  `inu_orden_proceso`                int )
BEGIN              
DECLARE orc_retorno   TEXT DEFAULT '-1';              

IF inu_orden_proceso = 0       
THEN          
	SET orc_retorno = '0';          
	INSERT INTO tbl_alarma_info_regis(FK_ALARMA, FK_INFORMACION_REGISTRADORA, FECHA_ALARMA, HORA_ALARMA, CANTIDAD_ALARMA, ESTADO , MODIFICACION_LOCAL, PK_UNICA)          
	VALUES ( inu_fk_alarma , inu_fk_informacion_registradora , idt_fecha_alarma , idt_hora_alarma , inu_cantidad_alarma , ibo_estado , ibo_modificacion_local, isb_pk_unica);          
	COMMIT;       
END IF;                 

IF inu_orden_proceso = 1       
THEN          
	SET orc_retorno = 'e';          
	IF inu_pk_air IS NOT NULL          
	THEN             
		UPDATE tbl_alarma_info_regis             
		SET     FK_ALARMA = inu_fk_alarma,                      
				FK_INFORMACION_REGISTRADORA = inu_fk_informacion_registradora,                      
				FECHA_ALARMA = idt_fecha_alarma,                      
				HORA_ALARMA = idt_hora_alarma,                      
				CANTIDAD_ALARMA = inu_cantidad_alarma,                     
				ESTADO = ibo_estado ,                      
				MODIFICACION_LOCAL = ibo_modificacion_local             
		WHERE PK_AIR = inu_pk_air;             
		SET orc_retorno = inu_pk_air;          
	END IF;       
END IF;              

IF inu_orden_proceso = 2       
THEN          
	IF inu_pk_air IS NOT NULL          
	THEN             
		DELETE FROM tbl_alarma_info_regis             
		WHERE PK_AIR = inu_pk_air;             
		COMMIT;             
		SET orc_retorno = '1';          
	END IF;       
END IF;       

IF inu_orden_proceso = 3       
THEN           
	BLOCK_RETORNO_QUERY_BUILDER:           
	BEGIN                 
		DECLARE sb_consulta   TEXT;                 
		DECLARE sb_query_execute   varchar(100);                 
		SET orc_retorno = 'e';                 
		SET sb_consulta = "SELECT * FROM tbl_alarma_info_regis WHERE tbl_alarma_info_regis.PK_AIR > 0";                 
		IF inu_pk_air IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.PK_AIR = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_pk_air);                 
		END IF;                 
		IF inu_fk_alarma IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.FK_ALARMA = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_fk_alarma);                 
		END IF;                  
		IF inu_fk_informacion_registradora IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.FK_INFORMACION_REGISTRADORA = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_fk_informacion_registradora);                 
		END IF;                  
		IF idt_fecha_alarma IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.FECHA_ALARMA like '");                     
			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_alarma);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                  
		IF idt_hora_alarma IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.HORA_ALARMA like '");                     
			SET sb_consulta = CONCAT(sb_consulta, idt_hora_alarma);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                  
		IF inu_cantidad_alarma IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.CANTIDAD_ALARMA = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_cantidad_alarma);                 
		END IF;                                  
		IF ibo_estado IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.ESTADO = ");                     
			SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
		END IF;                  
		IF ibo_modificacion_local IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.MODIFICACION_LOCAL = ");                     
			SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
		END IF;                  
		IF isb_pk_unica IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		IF idt_fecha_inicio IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND tbl_alarma_info_regis.FECHA_ALARMA BETWEEN '");                     
			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_inicio);                     
			SET sb_consulta = CONCAT(sb_consulta, "' AND '");                     
			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_fin);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		SET @sb_query_execute = sb_consulta;                 
		PREPARE stmt FROM @sb_query_execute;                 
		EXECUTE stmt;                   
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;              

IF inu_orden_proceso = 4       
THEN            
SET orc_retorno = 'e';           
IF idt_sincronizacion IS NOT NULL           
THEN               
SELECT *                
FROM tbl_alarma_info_regis               
WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                      
AND tbl_alarma_info_regis.MODIFICACION_LOCAL = 1;           
END IF;       
END IF;              

IF inu_orden_proceso <> 4       
THEN          
	SELECT orc_retorno;       
END IF;    
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_asignacion_manual_ruta
DROP PROCEDURE IF EXISTS `proc_tbl_asignacion_manual_ruta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_asignacion_manual_ruta`(
  IN  `inu_pk_asignacion_manual_ruta`      int,
  IN  `inu_fk_vehiculo`  int,
  IN  `inu_fk_ruta`  int,
  IN  `inu_fk_conductor`  int,
  IN  `inu_fk_usuario`	int,
  IN  `idt_fecha_modificacion`  datetime,
  IN  `isb_pk_unica`            varchar(20),
  IN  `inu_orden_proceso`       int
)
BEGIN 
      
       
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';      

       
IF inu_orden_proceso = 0       
THEN          
	SET orc_retorno = '0';          
	INSERT INTO tbl_asignacion_manual_ruta (FK_VEHICULO, FK_RUTA, FK_CONDUCTOR, FK_USUARIO, PK_UNICA)          
	VALUES (inu_fk_vehiculo , inu_fk_ruta, inu_fk_conductor , inu_fk_usuario, isb_pk_unica);          
	COMMIT;       
END IF;      

   
IF inu_orden_proceso = 1       
THEN          
	SET orc_retorno = 'e';          
	IF inu_pk_asignacion_manual_ruta IS NOT NULL          
	THEN             
		UPDATE tbl_asignacion_manual_ruta
		SET     
			FK_VEHICULO = inu_fk_vehiculo,                      
			FK_RUTA = inu_fk_ruta,                     
			FK_CONDUCTOR = inu_fk_conductor,                     
			FK_USUARIO = inu_fk_usuario            
		WHERE PK_ASIGNACION_MANUAL_RUTA = inu_pk_asignacion_manual_ruta;             
		SET orc_retorno = inu_pk_asignacion_manual_ruta;          
	END IF;       
END IF;       

       
IF inu_orden_proceso = 2       
THEN          
	IF inu_pk_asignacion_manual_ruta IS NOT NULL          
	THEN             
		DELETE FROM tbl_asignacion_manual_ruta
		WHERE PK_ASIGNACION_MANUAL_RUTA = inu_pk_asignacion_manual_ruta;   
		
		DELETE FROM tbl_conductor_vehiculo
		WHERE FK_CONDUCTOR = inu_fk_conductor AND FK_VEHICULO = inu_fk_vehiculo;
		
        UPDATE tbl_vehiculos_perimetro
        SET RUTA_ASIGNADA = NULL
        WHERE FK_VEHICULO = inu_fk_vehiculo;
		
		COMMIT;             
		SET orc_retorno = '1';          
	END IF;       
END IF;      
	 

IF inu_orden_proceso = 3       
THEN          
	BLOCK_RETORNO_QUERY_BUILDER:           
	BEGIN                 
		DECLARE sb_consulta   TEXT;                 
		DECLARE sb_query_execute   varchar(100);                 
		SET orc_retorno = 'e';                 
		SET sb_consulta = "SELECT * FROM tbl_asignacion_manual_ruta WHERE PK_ASIGNACION_MANUAL_RUTA > 0";      

		IF inu_pk_asignacion_manual_ruta IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND PK_ASIGNACION_MANUAL_RUTA = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_pk_asignacion_manual_ruta);                 
		END IF;                 
		IF inu_fk_vehiculo IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND FK_VEHICULO = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo);  			
		END IF;                 
		IF inu_fk_ruta IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND FK_RUTA = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_fk_ruta);  			
		END IF; 
		IF inu_fk_conductor IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND FK_CONDUCTOR = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_fk_conductor);  			
		END IF; 
		IF inu_fk_usuario IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND FK_USUARIO = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_fk_usuario);                 
		END IF; 
		IF idt_fecha_modificacion IS NOT NULL                 
		THEN
		SET sb_consulta = CONCAT(sb_consulta, " AND (FECHA_MODIFICACION BETWEEN ");                     
			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_modificacion);
			SET sb_consulta = CONCAT(sb_consulta, " 00:00:00");
			SET sb_consulta = CONCAT(sb_consulta, " AND ");
			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_modificacion);
			SET sb_consulta = CONCAT(sb_consulta, " 23:59:59) ");
		END IF;
		IF isb_pk_unica IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;    
		
		SET @sb_query_execute = sb_consulta;                 
		PREPARE stmt FROM @sb_query_execute;                 
	EXECUTE stmt;                   
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;       
   

       
IF inu_orden_proceso <> 3      
THEN          
	SELECT orc_retorno;       
END IF;    
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_base
DROP PROCEDURE IF EXISTS `proc_tbl_base`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_base`(IN `inu_pk_base` int, IN `inu_identificador_base` int, IN `isb_nombre` varchar(50), IN `inu_codigo_base` int, IN `isb_latitud` varchar(20), IN `isb_longitud` varchar(20), IN `isb_direccion_latitud` varchar(20), IN `isb_direccion_longitud` varchar(20), IN `inu_radio` int, IN `inu_direccion` int, IN `ibo_estado` boolean, IN `ibo_modificacion_local` boolean, IN `isb_pk_unica` varchar(20), IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `inu_orden_proceso` int )
BEGIN              
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              

IF inu_orden_proceso = 0       THEN          
	SET orc_retorno = '0';          
	INSERT INTO tbl_base( IDENTIFICADOR_BASE, NOMBRE, CODIGO_BASE, LATITUD, LONGITUD, DIRECCION_LATITUD, DIRECCION_LONGITUD, RADIO, DIRECCION, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          
	VALUES ( inu_identificador_base, isb_nombre, inu_codigo_base, isb_latitud , isb_longitud , isb_direccion_latitud, isb_direccion_longitud, inu_radio, inu_direccion, ibo_estado, ibo_modificacion_local, isb_pk_unica );       
	COMMIT;       
END IF;              

IF inu_orden_proceso = 1       THEN          
SET orc_retorno = 'e';          
	IF inu_pk_base IS NOT NULL          THEN             
		UPDATE tbl_base SET     
							IDENTIFICADOR_BASE = inu_identificador_base,                     
							NOMBRE = isb_nombre,                      
							CODIGO_BASE = inu_codigo_base,                     
							LATITUD = isb_latitud,                      
							LONGITUD = isb_longitud,                      
							DIRECCION_LATITUD = isb_direccion_latitud,                      
							DIRECCION_LONGITUD = isb_direccion_longitud,                      
							RADIO = inu_radio,                       
							DIRECCION = inu_direccion,                       
							ESTADO = ibo_estado,                     
							MODIFICACION_LOCAL = ibo_modificacion_local             
		WHERE PK_BASE = inu_pk_base;
		
		
		UPDATE tbl_permiso_empresa SET ESTADO = ibo_estado 
		WHERE FK_BASE = inu_pk_base;
		             
		SET orc_retorno = inu_pk_base;         
	END IF;       
END IF;              

IF inu_orden_proceso = 2       THEN          
	IF inu_pk_base IS NOT NULL          THEN             
		DELETE FROM tbl_base             
		WHERE PK_BASE = inu_pk_base;             
		COMMIT;             
		SET orc_retorno = '1';          
	END IF;       
END IF;       

IF inu_orden_proceso = 3       THEN           
BLOCK_RETORNO_QUERY_BUILDER:           
BEGIN                 
	DECLARE sb_consulta   TEXT;                 
	DECLARE sb_query_execute   varchar(100);                 
	SET orc_retorno = 'e';                 
	SET sb_consulta = "SELECT * FROM tbl_base use index(primary) WHERE tbl_base.PK_BASE > 0";                 
	
	IF inu_pk_base IS NOT NULL                 THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND tbl_base.PK_BASE = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_pk_base);                 
	END IF;                 
	IF inu_identificador_base IS NOT NULL                 THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND tbl_base.IDENTIFICADOR_BASE = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_identificador_base);                 
	END IF;                 
	IF isb_nombre IS NOT NULL                 THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND tbl_base.NOMBRE like '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;                  
	IF inu_codigo_base <> 0                 THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND tbl_base.CODIGO_BASE = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_codigo_base);                 
	END IF;                 
	IF ibo_estado IS NOT NULL                 THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND tbl_base.ESTADO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
	END IF;                 
	IF ibo_modificacion_local IS NOT NULL                 THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND tbl_base.MODIFICACION_LOCAL = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
	END IF;                  
	IF isb_pk_unica IS NOT NULL                 THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;     
            
	SET @sb_query_execute = sb_consulta;                 
	PREPARE stmt FROM @sb_query_execute;                 
	EXECUTE stmt;                   
END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;              

IF inu_orden_proceso = 4       THEN            
SET orc_retorno = 'e';           
	IF idt_sincronizacion IS NOT NULL           THEN               
		SELECT *                
		FROM tbl_base               
		WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     
			AND tbl_base.MODIFICACION_LOCAL = 1;           
	END IF;       
END IF;              

IF inu_orden_proceso <> 4       THEN          
	SELECT orc_retorno;       
END IF;    

END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_categoria_descuento
DROP PROCEDURE IF EXISTS `proc_tbl_categoria_descuento`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_categoria_descuento`(IN `inu_orden_proceso` INT, IN `inu_pk_categoria` BIGINT, IN `isb_nombre` VARCHAR(50), IN `isb_descripcion` VARCHAR(1000), IN `inu_valor` DOUBLE, IN `inu_aplica_descuento` INT, IN `inu_aplica_general` INT, IN `inu_es_valor_moneda` INT, IN `inu_es_porcentaje` INT, IN `inu_es_fija` INT, IN `inu_descuenta_pasajeros` INT, IN `inu_estado` INT, IN `idt_sincronizacion` TIMESTAMP)
    COMMENT 'procedimiento almacenado que  permite gestionar las operaciones hacia la tabla categoria'
BEGIN
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';
/*esta opcion permite insertar una nueva categoria en el sistema*/

IF inu_orden_proceso = 0 THEN          

		SET orc_retorno = '0';          
		INSERT INTO tbl_categoria_descuento(NOMBRE, DESCRIPCION, VALOR, APLICA_DESCUENTO, APLICA_GENERAL, ES_VALOR_MONEDA, ES_PORCENTAJE, ES_FIJA, DESCUENTA_PASAJEROS, ESTADO) 
		VALUES (isb_nombre, isb_descripcion, inu_valor, inu_aplica_descuento, inu_aplica_general, inu_es_valor_moneda, inu_es_porcentaje, inu_es_fija, inu_descuenta_pasajeros, inu_estado);
		COMMIT;

END IF; 


/*esta opcion permite actualizar una categoria en el sistema*/	
IF inu_orden_proceso = 1 THEN
		SET orc_retorno = 'e';
		
		IF inu_pk_categoria IS NOT NULL THEN
			UPDATE tbl_categoria_descuento  SET NOMBRE = isb_nombre, 
															DESCRIPCION = isb_descripcion, 
															VALOR = inu_valor, 
															APLICA_DESCUENTO = inu_aplica_descuento, 
															APLICA_GENERAL = inu_aplica_general, 
															ES_VALOR_MONEDA = inu_es_valor_moneda, 
															ES_PORCENTAJE = inu_es_porcentaje ,
															ES_FIJA = inu_es_fija ,
															DESCUENTA_PASAJEROS = inu_descuenta_pasajeros ,
															ESTADO = inu_estado 
															WHERE PK_CATEGORIAS_DESCUENTOS = inu_pk_categoria;
		SET orc_retorno = inu_pk_categoria;
		END IF;
	
END IF;



/*esta opcion permite eliminar una categoria en el sistema*/
IF inu_orden_proceso = 2 THEN          
	  IF inu_pk_categoria IS NOT NULL THEN             
		  DELETE FROM tbl_categoria_descuento WHERE PK_CATEGORIAS_DESCUENTOS = inu_pk_categoria;             
		  COMMIT;             
		  SET orc_retorno = '1';          
	  END IF;       
  END IF;  




IF inu_orden_proceso = 3 THEN           
		BLOCK_RETORNO_QUERY_BUILDER:           
		BEGIN                 
			DECLARE sb_consulta   TEXT;                 
			DECLARE sb_query_execute   varchar(100);
			                 
			SET orc_retorno = 'e';                 
			SET sb_consulta = "SELECT * FROM tbl_categoria_descuento WHERE pk_categorias_descuentos > 0 ";                 
			
			IF inu_pk_categoria IS NOT NULL THEN                    
				SET sb_consulta = CONCAT(sb_consulta, " AND PK_CATEGORIAS_DESCUENTOS = ");                     
				SET sb_consulta = CONCAT(sb_consulta, inu_pk_categoria);                 
			END IF;		
			
			IF isb_nombre IS NOT NULL THEN                     
				SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '%");                    
				SET sb_consulta = CONCAT(sb_consulta, isb_nombre );                     
				SET sb_consulta = CONCAT(sb_consulta, "%'" );                 
			END IF;
			
			IF isb_descripcion IS NOT NULL THEN                     
				SET sb_consulta = CONCAT(sb_consulta, " AND DESCRIPCION like '%");                    
				SET sb_consulta = CONCAT(sb_consulta, isb_descripcion );                     
				SET sb_consulta = CONCAT(sb_consulta, "%'" );                 
			END IF;
			
						
			SET @sb_query_execute = sb_consulta;                 
			PREPARE stmt FROM @sb_query_execute;                 
			EXECUTE stmt;                   
		END BLOCK_RETORNO_QUERY_BUILDER;       
	END IF;             
	

IF inu_orden_proceso = 4 THEN            
	 	SET orc_retorno = 'e';
	 	
		IF idt_sincronizacion IS NOT NULL THEN               
			SELECT *  FROM tbl_motivo  WHERE FECHA_MODIFICACION > idt_sincronizacion ;           
		END IF;

END IF;              


IF inu_orden_proceso = 5 THEN           
		BLOCK_RETORNO_QUERY_BUILDER:           
		BEGIN                 
			DECLARE sb_consulta   TEXT;                 
			DECLARE sb_query_execute   varchar(100);
			                 
			SET orc_retorno = 'e';                 
			SET sb_consulta = "SELECT PK_CATEGORIAS_DESCUENTOS, NOMBRE, DESCRIPCION, VALOR, APLICA_DESCUENTO, APLICA_GENERAL, ES_VALOR_MONEDA, ES_PORCENTAJE, ES_FIJA, DESCUENTA_PASAJEROS, FECHA_MODIFICACION, ESTADO FROM tbl_categoria_descuento WHERE (pk_categorias_descuentos > 0) AND (ESTADO=1) ORDER BY(NOMBRE)";
			SET @sb_query_execute = sb_consulta;                 
			PREPARE stmt FROM @sb_query_execute;                 
			EXECUTE stmt;                   
		END BLOCK_RETORNO_QUERY_BUILDER;       
	END IF;    	 
		
/*retorna un valor*/
IF inu_orden_proceso <> 6 THEN          
	 	SELECT orc_retorno;       
END IF;
/*FIN PROCEDURE*/
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_categoria_descuento_liquidacion
DROP PROCEDURE IF EXISTS `proc_tbl_categoria_descuento_liquidacion`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_categoria_descuento_liquidacion`(IN `inu_orden_proceso` INT, IN `inu_pk_categoria` INT, IN `isb_fecha_inicio` DATETIME, IN `isb_fecha_fin` DATETIME)
BEGIN
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';

IF inu_orden_proceso = 0 THEN           
		BLOCK_RETORNO_QUERY_BUILDER:           
		BEGIN                 
			DECLARE sb_consulta   TEXT;                 
			DECLARE sb_query_execute   varchar(100);
			                 
			SET orc_retorno = 'e';                 
			SET sb_consulta = "SELECT cast(lg.FECHA_LIQUIDACION as date) AS FECHA, vh.PLACA, la.VALOR_DESCUENTO, lg.PK_LIQUIDACION_GENERAL FROM tbl_liquidacion_general as lg INNER JOIN tbl_liquidacion_adicional as la ON lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL INNER JOIN tbl_categoria_descuento as cd ON la.FK_CATEGORIAS = cd.PK_CATEGORIAS_DESCUENTOS INNER JOIN tbl_vehiculo as vh ON vh.PK_VEHICULO= lg.FK_VEHICULO ";

			SET sb_consulta = CONCAT(sb_consulta, " WHERE (lg.ESTADO =  ");                     
			SET sb_consulta = CONCAT(sb_consulta, 1);                 
			SET sb_consulta = CONCAT(sb_consulta, " ) ");

			SET sb_consulta = CONCAT(sb_consulta, " AND (cd.PK_CATEGORIAS_DESCUENTOS =  ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_pk_categoria);                 
			SET sb_consulta = CONCAT(sb_consulta, " ) ");

			SET sb_consulta = CONCAT(sb_consulta, " AND (lg.FECHA_LIQUIDACION BETWEEN '");
			SET sb_consulta = CONCAT(sb_consulta, isb_fecha_inicio);
			SET sb_consulta = CONCAT(sb_consulta, "' AND '");
			SET sb_consulta = CONCAT(sb_consulta, isb_fecha_fin);
			SET sb_consulta = CONCAT(sb_consulta, " ') ");
			
			SET sb_consulta = CONCAT(sb_consulta, " ORDER BY lg.FECHA_LIQUIDACION;");
			
			SET @sb_query_execute = sb_consulta;                 
			PREPARE stmt FROM @sb_query_execute;                 
			EXECUTE stmt;                   
		END BLOCK_RETORNO_QUERY_BUILDER;       
	END IF;    	 






IF inu_orden_proceso = 1 THEN           
		BLOCK_RETORNO_QUERY_BUILDER:           
		BEGIN                 
			DECLARE sb_consulta   TEXT;                 
			DECLARE sb_query_execute   varchar(100);
			                 
			SET orc_retorno = 'e';                 
			SET sb_consulta = "SELECT cd.PK_CATEGORIAS_DESCUENTOS as ID, cd.NOMBRE, SUM(la.VALOR_DESCUENTO) as TOTAL_DESCUENTO, ";
			/*esta parte es para obtener el total de cada categoria*/
			SET sb_consulta = CONCAT(sb_consulta, " ( SELECT SUM(T) FROM");

			SET sb_consulta = CONCAT(sb_consulta, " ( ");
			SET sb_consulta = CONCAT(sb_consulta, " SELECT SUM(la.VALOR_DESCUENTO) as T ");
			SET sb_consulta = CONCAT(sb_consulta, " FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON la.FK_CATEGORIAS = cd.PK_CATEGORIAS_DESCUENTOS INNER JOIN tbl_liquidacion_general as lg ON lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL ");
			SET sb_consulta = CONCAT(sb_consulta, " WHERE lg.FECHA_LIQUIDACION BETWEEN '");
			SET sb_consulta = CONCAT(sb_consulta, isb_fecha_inicio);
			SET sb_consulta = CONCAT(sb_consulta, "' AND '");
			SET sb_consulta = CONCAT(sb_consulta, isb_fecha_fin);
			SET sb_consulta = CONCAT(sb_consulta, "')");
			
			
			SET sb_consulta = CONCAT(sb_consulta, " AS S");

			SET sb_consulta = CONCAT(sb_consulta, " )");			
			SET sb_consulta = CONCAT(sb_consulta, " AS TOTAL");

			/*esta parte de la consulta total*/
			SET sb_consulta = CONCAT(sb_consulta, " FROM tbl_liquidacion_adicional AS la INNER JOIN tbl_categoria_descuento as cd ON la.FK_CATEGORIAS = cd.PK_CATEGORIAS_DESCUENTOS INNER JOIN tbl_liquidacion_general as lg ON lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL WHERE lg.FECHA_LIQUIDACION BETWEEN '");
			SET sb_consulta = CONCAT(sb_consulta, isb_fecha_inicio);
			SET sb_consulta = CONCAT(sb_consulta, "' AND '");
			SET sb_consulta = CONCAT(sb_consulta, isb_fecha_fin);			
			SET sb_consulta = CONCAT(sb_consulta, "' GROUP BY (cd.NOMBRE)");	
			SET sb_consulta = CONCAT(sb_consulta, " ORDER BY (TOTAL_DESCUENTO); ");	
			
			SET @sb_query_execute = sb_consulta;                 
			PREPARE stmt FROM @sb_query_execute;                 
			EXECUTE stmt;                   
		END BLOCK_RETORNO_QUERY_BUILDER;       
	END IF;    	 
	
	
	
	
	IF inu_orden_proceso = 2 THEN           
		BLOCK_RETORNO_QUERY_BUILDER:           
		BEGIN                 
			DECLARE sb_consulta   TEXT;                 
			DECLARE sb_query_execute   varchar(100);
			                 
			SET orc_retorno = 'e';                 
			SET sb_consulta = "SELECT lg.PK_LIQUIDACION_GENERAL, v.PLACA, v.NUM_INTERNO, cd.NOMBRE, la.CANTIDAD_PASAJEROS_DESCONTADOS, lg.FECHA_LIQUIDACION FROM tbl_liquidacion_general as lg INNER JOIN tbl_liquidacion_adicional as la ON lg.PK_LIQUIDACION_GENERAL = la.FK_LIQUIDACION_GENERAL INNER JOIN tbl_categoria_descuento as cd ON la.FK_CATEGORIAS = cd.PK_CATEGORIAS_DESCUENTOS INNER JOIN tbl_vehiculo as v ON lg.FK_VEHICULO = v.PK_VEHICULO ";

			SET sb_consulta = CONCAT(sb_consulta, " WHERE (lg.ESTADO =  ");                     
			SET sb_consulta = CONCAT(sb_consulta, 1);                 
			SET sb_consulta = CONCAT(sb_consulta, " ) ");

			SET sb_consulta = CONCAT(sb_consulta, " AND (cd.DESCUENTA_PASAJEROS = ");                     
			SET sb_consulta = CONCAT(sb_consulta, 1);                 
			SET sb_consulta = CONCAT(sb_consulta, " ) ");

			SET sb_consulta = CONCAT(sb_consulta, " AND (lg.FECHA_LIQUIDACION BETWEEN '");
			SET sb_consulta = CONCAT(sb_consulta, isb_fecha_inicio);
			SET sb_consulta = CONCAT(sb_consulta, "' AND '");
			SET sb_consulta = CONCAT(sb_consulta, isb_fecha_fin);
			SET sb_consulta = CONCAT(sb_consulta, " ') ");
			
			SET sb_consulta = CONCAT(sb_consulta, " ORDER BY lg.FECHA_LIQUIDACION;");
			
			SET @sb_query_execute = sb_consulta;                 
			PREPARE stmt FROM @sb_query_execute;                 
			EXECUTE stmt;                   
		END BLOCK_RETORNO_QUERY_BUILDER;       
	END IF;   

	
	
			
/*retorna un valor*/
IF inu_orden_proceso <> 6 THEN          
	 	SELECT orc_retorno;       
END IF;
/*FIN PROCEDURE*/
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_ciudad
DROP PROCEDURE IF EXISTS `proc_tbl_ciudad`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_ciudad`(   IN  `inu_pk_ciudad`        int,   IN  `inu_fk_departamento`  int,   IN  `isb_nombre`           varchar(50),   IN  `inu_orden_proceso`    int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_ciudad WHERE tbl_ciudad.PK_CIUDAD > 0";                 IF inu_pk_ciudad IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_ciudad.PK_CIUDAD = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_ciudad);                 END IF;                 IF inu_fk_departamento IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_ciudad.FK_DEPARTAMENTO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_departamento);                 END IF;                 IF isb_nombre IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_ciudad.NOMBRE like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_conductor
DROP PROCEDURE IF EXISTS `proc_tbl_conductor`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_conductor`(IN `inu_pk_conductor` int, IN `inu_fk_empresa` int, IN `isb_nombre` varchar(50), IN `isb_apellido` varchar(50), IN `isb_cedula` varchar(50), IN `ibo_estado` boolean, IN `ibo_modificacion_local` boolean, IN `isb_pk_unica` varchar(20), IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `inu_orden_proceso` int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              

IF inu_orden_proceso = 0       
THEN          SET orc_retorno = '0';          
INSERT INTO tbl_conductor( FK_EMPRESA, NOMBRE, APELLIDO, CEDULA, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          
VALUES ( inu_fk_empresa, isb_nombre, isb_apellido, isb_cedula, ibo_estado, ibo_modificacion_local, isb_pk_unica );         
COMMIT;       END IF;             

IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_conductor IS NOT NULL          THEN             
UPDATE tbl_conductor             
SET     FK_EMPRESA = inu_fk_empresa ,                     
NOMBRE = isb_nombre ,                     
APELLIDO = isb_apellido ,                     
CEDULA = isb_cedula ,                     
ESTADO = ibo_estado ,                     
MODIFICACION_LOCAL = ibo_modificacion_local              
WHERE PK_CONDUCTOR = inu_pk_conductor;             
SET orc_retorno = inu_pk_conductor;          END IF;       END IF;              

IF inu_orden_proceso = 2       THEN          
IF inu_pk_conductor IS NOT NULL          THEN            
 DELETE FROM tbl_conductor             WHERE PK_CONDUCTOR = inu_pk_conductor;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       
 
 
 IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 
 DECLARE sb_consulta   TEXT;                 
 DECLARE sb_query_execute   varchar(100);                 
 SET orc_retorno = 'e';                 
 SET sb_consulta = "SELECT * FROM tbl_conductor WHERE PK_CONDUCTOR > 0";                 
 IF inu_pk_conductor IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_CONDUCTOR = ");                     
 SET sb_consulta = CONCAT(sb_consulta, inu_pk_conductor);                 END IF;                 
 IF inu_fk_empresa IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND  FK_EMPRESA = ");                    
 SET sb_consulta = CONCAT(sb_consulta, inu_fk_empresa);                 END IF;                 
 IF isb_nombre IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '");                     
 SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     
 SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 
 IF isb_apellido IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND APELLIDO like '");                     
 SET sb_consulta = CONCAT(sb_consulta, isb_apellido);                     
 SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 
 IF isb_cedula IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND CEDULA like '");                     
 SET sb_consulta = CONCAT(sb_consulta, isb_cedula);                     
 SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 
 IF ibo_estado IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     
 SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 
 IF ibo_modificacion_local IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
 SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  
 IF isb_pk_unica IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
 SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
 SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;    
              
 SET sb_consulta = CONCAT(sb_consulta, " ORDER BY APELLIDO, NOMBRE");  
 
 SET @sb_query_execute = sb_consulta;                 
 
 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   
 END BLOCK_RETORNO_QUERY_BUILDER;       END IF;             
 
 IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_conductor               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_conductor_vehiculo
DROP PROCEDURE IF EXISTS `proc_tbl_conductor_vehiculo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_conductor_vehiculo`(IN `inu_pk_conductor_vehiculo` int, IN `inu_fk_conductor` int, IN `inu_fk_vehiculo` int, IN `idt_fecha_asignacion` datetime, IN `ibo_estado` boolean, IN `ibo_modificacion_local` boolean, IN `isb_pk_unica` varchar(20), IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `inu_orden_proceso` int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              

IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          

UPDATE tbl_conductor_vehiculo SET ESTADO = 0
WHERE FK_CONDUCTOR = inu_fk_conductor OR FK_VEHICULO = inu_fk_vehiculo;


INSERT INTO tbl_conductor_vehiculo( FK_CONDUCTOR, FK_VEHICULO, FECHA_ASIGNACION, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          
VALUES (  inu_fk_conductor,inu_fk_vehiculo, idt_fecha_asignacion, ibo_estado , ibo_modificacion_local, isb_pk_unica );          
COMMIT;   
    
END IF;              

IF inu_orden_proceso = 1       THEN          
	SET orc_retorno = 'e';          
	IF inu_pk_conductor_vehiculo IS NOT NULL          
	THEN             
		UPDATE tbl_conductor_vehiculo            
		SET     
			FK_CONDUCTOR = inu_fk_conductor ,                     
			FK_VEHICULO = inu_fk_vehiculo ,                     
			FECHA_ASIGNACION = idt_fecha_asignacion ,                     
			ESTADO = ibo_estado,                     
			MODIFICACION_LOCAL = ibo_modificacion_local              
	
	WHERE PK_CONDUCTOR_VECHICULO = inu_pk_conductor_vehiculo;             
		SET orc_retorno = inu_pk_conductor_vehiculo;          
	
	END IF;       
END IF;              

IF inu_orden_proceso = 2       
THEN          
IF inu_pk_conductor_vehiculo IS NOT NULL          
THEN             
DELETE FROM tbl_conductor_vehiculo             
WHERE PK_CONDUCTOR_VECHICULO = inu_pk_conductor_vehiculo;             
COMMIT;             
SET orc_retorno = '1';          
END IF;       
END IF;       

IF inu_orden_proceso = 3       
THEN           
BLOCK_RETORNO_QUERY_BUILDER:           
BEGIN                 
DECLARE sb_consulta   TEXT;                 
DECLARE sb_query_execute   varchar(100);                 
SET orc_retorno = 'e';                 
SET sb_consulta = "SELECT * FROM tbl_conductor_vehiculo WHERE PK_CONDUCTOR_VECHICULO > 0";                 
IF inu_pk_conductor_vehiculo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_CONDUCTOR_VECHICULO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_conductor_vehiculo);                 END IF;                 IF inu_fk_conductor IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_CONDUCTOR = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_conductor);                 END IF;                 IF inu_fk_vehiculo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_VEHICULO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo);                 END IF;                 IF idt_fecha_asignacion <> '0000/00/00 00:00:00'                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FECHA_ASIGNACION = '");                     SET sb_consulta = CONCAT(sb_consulta, idt_fecha_asignacion);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                  IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_conductor_vehiculo               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_conteo_perimetro
DROP PROCEDURE IF EXISTS `proc_tbl_conteo_perimetro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_conteo_perimetro`(   
		IN  `inu_pk_conteo_perimetro`          int,   
		IN  `inu_fk_vehiculo`                  int,   
		IN  `inu_fk_informacion_registradora`  int,   
		IN  `idt_fecha_conteo`                 datetime,   
		IN  `idt_hora_ingreso`                 time,   
		IN  `inu_num_inicial`                  int,   
		IN  `inu_num_final`                    int,   
		IN  `inu_diferencia`                   int,   
		IN  `inu_entradas`                     int,   
		IN  `inu_salidas`                      int,   
		IN  `ibo_estado`                       boolean,   
		IN  `ibo_modificacion_local`           boolean,   
		IN  `isb_pk_unica`                     varchar(20),   
		IN  `idt_sincronizacion`               datetime,   
		IN  `idt_sincronizacion_end`           datetime,   
		IN  `idt_fecha_inicio`                 datetime,   
		IN  `idt_fecha_fin`                    datetime,   
		IN  `inu_orden_proceso`                int )
BEGIN         
     
DECLARE orc_retorno   TEXT DEFAULT '-1';      
        
IF inu_orden_proceso = 0       
THEN          
	SET orc_retorno = '0';          
	INSERT INTO tbl_conteo_perimetro(FK_VEHICULO, FK_INFORMACION_REGISTRADORA, FECHA_CONTEO, HORA_INGRESO, NUM_INICIAL, NUM_FINAL, DIFERENCIA, ENTRADAS, SALIDAS, ESTADO, MODIFICACION_LOCAL, PK_UNICA)          
	VALUES ( inu_fk_vehiculo , inu_fk_informacion_registradora,idt_fecha_conteo, idt_hora_ingreso, inu_num_inicial, inu_num_final, inu_diferencia, inu_entradas, inu_salidas ,ibo_estado, ibo_modificacion_local, isb_pk_unica );          
	COMMIT;       
END IF;              

IF inu_orden_proceso = 1       
THEN          
	SET orc_retorno = 'e';          
	IF inu_pk_conteo_perimetro IS NOT NULL          
	THEN             
		UPDATE tbl_conteo_perimetro             
		SET     FK_VEHICULO = inu_fk_vehiculo,                      
				FK_INFORMACION_REGISTRADORA = inu_fk_informacion_registradora,                      
				FECHA_CONTEO = idt_fecha_conteo,                      
				HORA_INGRESO = idt_hora_ingreso,                      
				NUM_INICIAL = inu_num_inicial,                      
				NUM_FINAL = inu_num_final,                      
				DIFERENCIA = inu_diferencia,                      
				ENTRADAS = inu_entradas,                      
				SALIDAS = inu_salidas,                     
				ESTADO = ibo_estado ,                     
				MODIFICACION_LOCAL = ibo_modificacion_local              
		WHERE PK_CONTEO_PERIMETRO = inu_pk_conteo_perimetro;             
	SET orc_retorno = inu_pk_conteo_perimetro;          
	END IF;       
END IF;       
       
IF inu_orden_proceso = 2       
THEN          
	IF inu_pk_conteo_perimetro IS NOT NULL          
	THEN             
		DELETE FROM tbl_conteo_perimetro             
		WHERE PK_CONTEO_PERIMETRO = inu_pk_conteo_perimetro;             
		COMMIT;             
		SET orc_retorno = '1';          
	END IF;       
END IF;       

IF inu_orden_proceso = 3       
THEN           
	BLOCK_RETORNO_QUERY_BUILDER:           
	BEGIN                 
		DECLARE sb_consulta   TEXT;                 
		DECLARE sb_query_execute   varchar(100);                 
		SET orc_retorno = 'e';                 
		SET sb_consulta = "SELECT * FROM tbl_conteo_perimetro use index(fk_informacion_registradora_vehiculo) WHERE PK_CONTEO_PERIMETRO > 0";                 
		IF inu_pk_conteo_perimetro IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_CONTEO_PERIMETRO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_pk_conteo_perimetro);                 
		END IF;                 
		IF inu_fk_vehiculo IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FK_VEHICULO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo );                 
		END IF;                 
		IF inu_fk_informacion_registradora IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FK_INFORMACION_REGISTRADORA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_informacion_registradora );                 
		END IF;                 
		IF idt_hora_ingreso IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND HORA_INGRESO = '");                     
		SET sb_consulta = CONCAT(sb_consulta, idt_hora_ingreso );                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		IF idt_fecha_conteo IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FECHA_CONTEO = '");                     
		SET sb_consulta = CONCAT(sb_consulta, idt_fecha_conteo);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		IF inu_num_inicial IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NUM_INICIAL = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_num_inicial );                 
		END IF;                 
		IF inu_num_final IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NUM_FINAL = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_num_final );                 
		END IF;                 
		IF inu_diferencia IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND DIFERENCIA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_diferencia );                 
		END IF;                 
		IF inu_entradas IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND ENTRADAS = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_entradas );                 
		END IF;                 
		IF inu_salidas IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND SALIDAS = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_salidas );                 
		END IF;                                 
		IF ibo_estado IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
		END IF;                 
		IF ibo_modificacion_local IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
		END IF;                 
		IF isb_pk_unica IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		IF idt_fecha_inicio IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FECHA_CONTEO BETWEEN '");                     
		SET sb_consulta = CONCAT(sb_consulta, idt_fecha_inicio);                     
		SET sb_consulta = CONCAT(sb_consulta, "' AND '");                     
		SET sb_consulta = CONCAT(sb_consulta, idt_fecha_fin);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		SET @sb_query_execute = sb_consulta;                 
		PREPARE stmt FROM @sb_query_execute;                 
		EXECUTE stmt;                   
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;              

IF inu_orden_proceso = 4       
THEN            
	SET orc_retorno = 'e';           
	IF idt_sincronizacion IS NOT NULL           
	THEN               
		SELECT *                
		FROM tbl_conteo_perimetro               
		WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     
		AND MODIFICACION_LOCAL = 1;           
	END IF;       
END IF;              

IF inu_orden_proceso <> 4      
THEN          
	SELECT orc_retorno;       
END IF;    

END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_departamento
DROP PROCEDURE IF EXISTS `proc_tbl_departamento`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_departamento`(   IN  `inu_pk_departamento`  int,   IN  `inu_fk_pais`          int,   IN  `isb_nombre`           varchar(50),   IN  `inu_orden_proceso`    int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_departamento WHERE tbl_departamento.PK_DEPARTAMENTO > 0";                 IF inu_pk_departamento IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_departamento.PK_DEPARTAMENTO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_departamento);                 END IF;                 IF inu_fk_pais IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_departamento.FK_PAIS = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_pais);                 END IF;                 IF isb_nombre IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_departamento.NOMBRE like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_empresa
DROP PROCEDURE IF EXISTS `proc_tbl_empresa`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_empresa`(IN inu_pk_empresa INT, IN inu_fk_ciudad INT, IN isb_nombre VARCHAR(50), IN isb_nit VARCHAR(50), IN ibo_estado BOOLEAN, IN ibo_modificacion_local BOOLEAN, IN isb_pk_unica VARCHAR(20), IN idt_sincronizacion DATETIME, IN idt_sincronizacion_end DATETIME, IN inu_orden_proceso INT)
BEGIN              
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';   
           
IF inu_orden_proceso = 0       THEN          
	SET orc_retorno = '0';          
	INSERT INTO tbl_empresa( FK_CIUDAD, NOMBRE, NIT, ESTADO, MODIFICACION_LOCAL, PK_UNICA  )          
	VALUES ( inu_fk_ciudad, isb_nombre, isb_nit, ibo_estado, ibo_modificacion_local, isb_pk_unica );          
	COMMIT;       
END IF;              

IF inu_orden_proceso = 1       THEN          
SET orc_retorno = 'e';          
	IF inu_pk_empresa IS NOT NULL          
	THEN             
		UPDATE tbl_empresa            
		SET     FK_CIUDAD = inu_fk_ciudad ,                     
				NOMBRE = isb_nombre ,                     
				NIT = isb_nit ,                     
				ESTADO = ibo_estado ,                     
				MODIFICACION_LOCAL = ibo_modificacion_local             
		WHERE PK_EMPRESA = inu_pk_empresa;             
		SET orc_retorno = inu_pk_empresa;          
	END IF;       
END IF;              

IF inu_orden_proceso = 2       THEN          
	IF inu_pk_empresa IS NOT NULL          THEN             
		DELETE FROM tbl_empresa             
		WHERE PK_EMPRESA = inu_pk_empresa;             
		COMMIT;             
		SET orc_retorno = '1';          
	END IF;       
END IF;       

IF inu_orden_proceso = 3       THEN           
	BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 
	DECLARE sb_consulta   TEXT;                 
	DECLARE sb_query_execute   varchar(100);                 
	SET orc_retorno = 'e';                 
	SET sb_consulta = "SELECT * FROM tbl_empresa WHERE PK_EMPRESA > 0";                 
	
	IF inu_pk_empresa IS NOT NULL                 
	THEN                    
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_EMPRESA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_pk_empresa);                 
	END IF;                  
	IF inu_fk_ciudad IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FK_CIUDAD = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_ciudad);                 
	END IF;                 
	IF isb_nombre IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;                 
	IF isb_nit IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NIT like '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_nit);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;                 
	IF ibo_estado IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
	END IF;                 
	IF ibo_modificacion_local IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
	END IF;                  
	IF isb_pk_unica IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;                 
	
	SET sb_consulta = CONCAT(sb_consulta, " ORDER BY NOMBRE ASC");  

	SET @sb_query_execute = sb_consulta;                 
	PREPARE stmt FROM @sb_query_execute;                 
	EXECUTE stmt;                   
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;      
        
IF inu_orden_proceso = 4       
THEN            
	SET orc_retorno = 'e';           
	IF idt_sincronizacion IS NOT NULL           
	THEN               
		SELECT *                
		FROM tbl_empresa               
		WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     
				AND MODIFICACION_LOCAL = 1;           
	END IF;       
END IF;              

IF inu_orden_proceso <> 4       
THEN          
SELECT orc_retorno;       
END IF;    
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_evento
DROP PROCEDURE IF EXISTS `proc_tbl_evento`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_evento`(   IN  `inu_pk_evento`           int,   IN  `isb_codigo`              varchar(50),   IN  `isb_nombre_generico`     varchar(50),   IN  `isb_descripcion`         text,   IN  `inu_prioridad`           int,   IN  `inu_cantidad`            int,   IN  `isb_tipo_evento`         varchar(50),   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_evento( CODIGO, NOMBRE_GENERICO, DESCRIPCION, PRIORIDAD, CANTIDAD, TIPO_EVENTO, ESTADO , MODIFICACION_LOCAL , PK_UNICA)          VALUES ( isb_codigo, isb_nombre_generico , isb_descripcion , inu_prioridad , inu_cantidad , isb_tipo_evento , ibo_estado ,ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_evento IS NOT NULL          THEN             UPDATE tbl_evento             SET   CODIGO = isb_codigo,                   NOMBRE_GENERICO = isb_nombre_generico ,                   DESCRIPCION = isb_descripcion ,                   PRIORIDAD = inu_prioridad ,                   CANTIDAD = inu_cantidad ,                   TIPO_EVENTO = isb_tipo_evento ,                   ESTADO = ibo_estado ,                   MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_EVENTO = inu_pk_evento;             SET orc_retorno = inu_pk_evento;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_evento IS NOT NULL          THEN             DELETE FROM tbl_evento             WHERE PK_EVENTO = inu_pk_evento;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_evento WHERE PK_EVENTO > 0";                 IF inu_pk_evento IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_EVENTO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_evento);                 END IF;                 IF isb_codigo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND CODIGO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_codigo);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF isb_nombre_generico IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE_GENERICO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre_generico);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF isb_descripcion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND DESCRIPCION like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_descripcion);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  IF inu_prioridad IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PRIORIDAD = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_prioridad);                 END IF;                 IF inu_cantidad IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND CANTIDAD = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_cantidad);                 END IF;                 IF isb_tipo_evento IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND TIPO_EVENTO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_tipo_evento);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                  IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_evento               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_evento_generado
DROP PROCEDURE IF EXISTS `proc_tbl_evento_generado`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_evento_generado`(   IN  `inu_pk_evento_generado`  int,   IN  `inu_fk_evento`           int,   IN  `inu_fk_base`             int,   IN  `isb_detalle`             text,   IN  `idt_fecha_evento`        datetime,   IN  `ibo_estado_enviado`      boolean,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_evento_generado( FK_EVENTO, FK_BASE, DETALLE, FECHA_EVENTO, ESTADO_ENVIADO )          VALUES ( inu_fk_evento, inu_fk_base, isb_detalle ,idt_fecha_evento, ibo_estado_enviado );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_evento_generado IS NOT NULL          THEN             UPDATE tbl_evento_generado             SET     FK_EVENTO = inu_fk_evento ,                     FK_BASE = inu_fk_base ,                     DETALLE = isb_detalle,                     FECHA_EVENTO = idt_fecha_evento ,                     ESTADO_ENVIADO = ibo_estado_enviado             WHERE PK_EVENTO_GENERADO = inu_pk_evento_generado;             SET orc_retorno = inu_pk_evento_generado;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_evento_generado IS NOT NULL          THEN             DELETE FROM tbl_evento_generado             WHERE PK_EVENTO_GENERADO = inu_pk_evento_generado;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_evento_generado WHERE PK_EVENTO_GENERADO > 0";                 IF inu_pk_evento_generado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_EVENTO_GENERADO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_evento_generado);                 END IF;                 IF inu_fk_evento IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_EVENTO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_evento);                 END IF;                 IF inu_fk_base IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_BASE = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_base);                 END IF;                 IF idt_fecha_evento IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FECHA_EVENTO = '");                     SET sb_consulta = CONCAT(sb_consulta, idt_fecha_evento);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado_enviado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO_ENVIADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado_enviado);                 END IF;                  SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_evento_generado               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_evento_usuario
DROP PROCEDURE IF EXISTS `proc_tbl_evento_usuario`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_evento_usuario`(   IN  `inu_pk_evento_usuario`   int,   IN  `inu_fk_evento`           int,   IN  `inu_fk_usuario_email`    int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_evento_usuario( FK_EVENTO, FK_USUARIO_EMAIL, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( inu_fk_evento, inu_fk_usuario_email, ibo_estado ,ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_evento_usuario IS NOT NULL          THEN             UPDATE tbl_evento_usuario             SET     FK_EVENTO = inu_fk_evento ,                     FK_USUARIO_EMAIL = inu_fk_usuario_email ,                     ESTADO = ibo_estado ,                      MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_EVENTO_USUARIO = inu_pk_evento_usuario;             SET orc_retorno = inu_pk_evento_usuario;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_evento_usuario IS NOT NULL          THEN             DELETE FROM tbl_evento_usuario             WHERE PK_EVENTO_USUARIO = inu_pk_evento_usuario;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_evento_usuario WHERE PK_EVENTO_USUARIO > 0";                 IF inu_pk_evento_usuario IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_EVENTO_USUARIO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_evento_usuario);                 END IF;                 IF inu_fk_evento IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_EVENTO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_evento);                 END IF;                 IF inu_fk_usuario_email IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_USUARIO_EMAIL = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_usuario_email);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                  IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_evento_usuario               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_grupo
DROP PROCEDURE IF EXISTS `proc_tbl_grupo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_grupo`(   IN  `inu_pk_grupo`            int,   IN  `isb_nombre`              varchar(60),   IN  `isb_tecla_acceso`        varchar(60),   IN  `isb_imagen`              varchar(100),   IN  `inu_posicion`            int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_grupo( NOMBRE, TECLA_ACCESO, IMAGEN, POSICION, ESTADO ,Modificacion_Local, PK_UNICA )          VALUES ( isb_nombre , isb_tecla_acceso, isb_imagen , inu_posicion, ibo_estado, ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_grupo IS NOT NULL          THEN             UPDATE tbl_grupo             SET     NOMBRE = isb_nombre ,                     TECLA_ACCESO = isb_tecla_acceso ,                     IMAGEN = isb_imagen ,                     POSICION = inu_posicion,                     ESTADO = ibo_estado,                     MODIFICACION_LOCAL = ibo_modificacion_local               WHERE PK_GRUPO = inu_pk_grupo;             SET orc_retorno = inu_pk_grupo;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_grupo IS NOT NULL          THEN             DELETE FROM tbl_grupo             WHERE PK_GRUPO = inu_pk_grupo;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_grupo WHERE PK_GRUPO > 0";                 IF inu_pk_grupo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_GRUPO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_grupo);                 END IF;                 IF isb_nombre IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_tecla_acceso IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND TECLA_ACCESO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_tecla_acceso);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_imagen IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND IMAGEN like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_imagen);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET sb_consulta = CONCAT(sb_consulta, " ORDER BY POSICION ASC ");                                                             SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_grupo               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_grupo_vehiculos_permitido
DROP PROCEDURE IF EXISTS `proc_tbl_grupo_vehiculos_permitido`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_grupo_vehiculos_permitido`(   IN  `inu_pk_grupo_vehiculos_permitido`  int,   IN  `inu_fk_agrupacion`                 int,   IN  `inu_fk_base`                       int,   IN  `idt_fecha_inicio`                  date,   IN  `idt_fecha_fin`                     date,   IN  `inu_estado`                        int,   IN  `idt_fecha_auditada`                datetime,   IN  `inu_tipo`                          int,   IN  `inu_orden_proceso`                 int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_grupo_vehiculos_permitido( FK_AGRUPACION, FK_BASE, FECHA_INICIO, FECHA_FIN , ESTADO, TIPO )          VALUES ( inu_fk_agrupacion, inu_fk_base, idt_fecha_inicio, idt_fecha_fin, inu_estado,inu_tipo  );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_grupo_vehiculos_permitido IS NOT NULL          THEN             UPDATE tbl_grupo_vehiculos_permitido             SET     FK_AGRUPACION = inu_fk_agrupacion,                      FK_BASE = inu_fk_base,                      FECHA_INICIO = idt_fecha_inicio,                      FECHA_FIN = idt_fecha_fin,                     ESTADO = inu_estado,                      FECHA_AUDITADA = idt_fecha_auditada             WHERE PK_GRUPO_VEHICULOS_PERMITIDO = inu_pk_grupo_vehiculos_permitido;             SET orc_retorno = inu_pk_grupo_vehiculos_permitido;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_grupo_vehiculos_permitido IS NOT NULL          THEN             DELETE FROM tbl_grupo_vehiculos_permitido             WHERE PK_GRUPO_VEHICULOS_PERMITIDO = inu_pk_grupo_vehiculos_permitido;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_grupo_vehiculos_permitido WHERE PK_GRUPO_VEHICULOS_PERMITIDO > 0";                 IF inu_pk_grupo_vehiculos_permitido IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_GRUPO_VEHICULOS_PERMITIDO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_grupo_vehiculos_permitido);                 END IF;                 IF inu_fk_agrupacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_AGRUPACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_agrupacion);                 END IF;                 IF inu_fk_base IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_BASE = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_base);                 END IF;                 IF inu_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_estado);                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_grupo_vehiculos_permitido               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_informacion_registradora
DROP PROCEDURE IF EXISTS `proc_tbl_informacion_registradora`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_informacion_registradora`(IN `inu_pk_informacion_registradora` int, IN `inu_fk_vehiculo` int, IN `inu_fk_ruta` int, IN `inu_fk_usuario` int, IN `inu_fk_base` int, IN `inu_fk_conductor` int, IN `idt_fecha_ingreso` date, IN `idt_hora_ingreso` time, IN `inu_numero_vuelta` int, IN `inu_num_vuelta_ant` int, IN `inu_num_llegada` int, IN `inu_diferencia_num` int, IN `inu_entradas` int, IN `inu_diferencia_entrada` int, IN `inu_salidas` int, IN `inu_diferencia_salida` int, IN `inu_total_dia` int, IN `inu_fk_base_salida` int, IN `idt_fecha_salida_base_salida` date, IN `idt_hora_salida_base_salida` time, IN `inu_numeracion_base_salida` int, IN `inu_entradas_base_salida` int, IN `inu_salidas_base_salida` int, IN `isb_firmware` varchar(11), IN `inu_version_puntos` int, IN `inu_estado_creacion` int, IN `inu_historial` int, IN `idb_porcentaje_ruta` double, IN `ibo_estado` tinyint, IN `ibo_modificacion_local` boolean, IN `isb_pk_unica` varchar(20), IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `idt_fecha_inicio` datetime, IN `idt_fecha_fin` datetime, IN `inu_orden_proceso` int )
BEGIN              
DECLARE orc_retorno   text DEFAULT '-1';              
IF inu_orden_proceso = 0       
THEN          
	SET orc_retorno = '0';   
	
		UPDATE tbl_informacion_registradora  SET ESTADO = 0 
		WHERE ENTRADAS 					= inu_entradas AND
				SALIDAS 						= inu_salidas AND
				NUM_LLEGADA 				= inu_num_llegada AND
				NUMERACION_BASE_SALIDA 	= inu_numeracion_base_salida AND
				FECHA_INGRESO 				= idt_fecha_ingreso AND
				FECHA_SALIDA_BASE_SALIDA = idt_fecha_salida_base_salida AND
				HORA_SALIDA_BASE_SALIDA = idt_hora_salida_base_salida AND
				FK_VEHICULO 				= inu_fk_vehiculo AND
				TOTAL_DIA 					= inu_total_dia;

	      
		INSERT INTO tbl_informacion_registradora( FK_VEHICULO, FK_RUTA, FK_USUARIO, FK_BASE, FK_CONDUCTOR, FECHA_INGRESO, HORA_INGRESO, NUMERO_VUELTA, NUM_VUELTA_ANT, NUM_LLEGADA, DIFERENCIA_NUM, ENTRADAS, DIFERENCIA_ENTRADA, SALIDAS, DIFERENCIA_SALIDA, TOTAL_DIA, FK_BASE_SALIDA, FECHA_SALIDA_BASE_SALIDA, HORA_SALIDA_BASE_SALIDA, NUMERACION_BASE_SALIDA, ENTRADAS_BASE_SALIDA, SALIDAS_BASE_SALIDA, FIRMWARE, VERSION_PUNTOS, ESTADO_CREACION, HISTORIAL, ESTADO, MODIFICACION_LOCAL, PK_UNICA, PORCENTAJE_RUTA )          
		VALUES ( inu_fk_vehiculo, inu_fk_ruta, inu_fk_usuario, inu_fk_base, inu_fk_conductor, idt_fecha_ingreso, idt_hora_ingreso, inu_numero_vuelta, inu_num_vuelta_ant, inu_num_llegada, inu_diferencia_num, inu_entradas, inu_diferencia_entrada, inu_salidas, inu_diferencia_salida, inu_total_dia, inu_fk_base_salida, idt_fecha_salida_base_salida, idt_hora_salida_base_salida , inu_numeracion_base_salida, inu_entradas_base_salida, inu_salidas_base_salida, isb_firmware, inu_version_puntos, inu_estado_creacion, inu_historial, ibo_estado, ibo_modificacion_local, isb_pk_unica, idb_porcentaje_ruta );          
	COMMIT;       
END IF;              

IF inu_orden_proceso = 1       
THEN          
SET orc_retorno = 'e';          
	IF inu_pk_informacion_registradora IS NOT NULL          
	THEN              
		IF inu_fk_ruta IS NOT NULL              
		THEN               
			UPDATE tbl_informacion_registradora               
			SET   	FK_VEHICULO = inu_fk_vehiculo,                     
					FK_RUTA = inu_fk_ruta,                     
					FK_USUARIO = inu_fk_usuario,                      
					FK_BASE = inu_fk_base,                      
					FK_CONDUCTOR = inu_fk_conductor,                     
					FECHA_INGRESO = idt_fecha_ingreso,                      
					HORA_INGRESO = idt_hora_ingreso,                     
					NUMERO_VUELTA = inu_numero_vuelta,                      
					NUM_VUELTA_ANT = inu_num_vuelta_ant,                     
					NUM_LLEGADA = inu_num_llegada,                     
					DIFERENCIA_NUM = inu_diferencia_num,                     
					ENTRADAS = inu_entradas,                      
					DIFERENCIA_ENTRADA = inu_diferencia_entrada,                      
					SALIDAS = inu_salidas,                      
					DIFERENCIA_SALIDA = inu_diferencia_salida,                      
					TOTAL_DIA = inu_total_dia,                      
					FK_BASE_SALIDA = inu_fk_base_salida,                     
					FECHA_SALIDA_BASE_SALIDA = idt_fecha_salida_base_salida,                      
					HORA_SALIDA_BASE_SALIDA = idt_hora_salida_base_salida,                      
					NUMERACION_BASE_SALIDA = inu_numeracion_base_salida,                      
					ENTRADAS_BASE_SALIDA = inu_entradas_base_salida,                      
					SALIDAS_BASE_SALIDA = inu_salidas_base_salida,                      
					FIRMWARE = isb_firmware,                      
					VERSION_PUNTOS = inu_version_puntos,                     
					ESTADO_CREACION = inu_estado_creacion,                      
					HISTORIAL = inu_historial,                     
					ESTADO = ibo_estado,                     
					MODIFICACION_LOCAL = ibo_modificacion_local,                     
					PORCENTAJE_RUTA = idb_porcentaje_ruta             

			WHERE PK_INFORMACION_REGISTRADORA = inu_pk_informacion_registradora;              
		END IF;              

		IF inu_fk_ruta IS NULL              
		THEN              
			UPDATE tbl_informacion_registradora              
			SET    	FK_VEHICULO = inu_fk_vehiculo,                     
					FK_USUARIO = inu_fk_usuario,                      
					FK_BASE = inu_fk_base,                      
					FK_CONDUCTOR = inu_fk_conductor,                     
					FECHA_INGRESO = idt_fecha_ingreso,                      
					HORA_INGRESO = idt_hora_ingreso,                     
					NUMERO_VUELTA = inu_numero_vuelta,                      
					NUM_VUELTA_ANT = inu_num_vuelta_ant,                     
					NUM_LLEGADA = inu_num_llegada,                     
					DIFERENCIA_NUM = inu_diferencia_num,                     
					ENTRADAS = inu_entradas,                      
					DIFERENCIA_ENTRADA = inu_diferencia_entrada,                      
					SALIDAS = inu_salidas,                      
					DIFERENCIA_SALIDA = inu_diferencia_salida,                      
					TOTAL_DIA = inu_total_dia,                      
					FK_BASE_SALIDA = inu_fk_base_salida,                     
					FECHA_SALIDA_BASE_SALIDA = idt_fecha_salida_base_salida,                      
					HORA_SALIDA_BASE_SALIDA = idt_hora_salida_base_salida,                      
					NUMERACION_BASE_SALIDA = inu_numeracion_base_salida,                      
					ENTRADAS_BASE_SALIDA = inu_entradas_base_salida,                      
					SALIDAS_BASE_SALIDA = inu_salidas_base_salida,                      
					FIRMWARE = isb_firmware,                      
					VERSION_PUNTOS = inu_version_puntos,                     
					ESTADO_CREACION = inu_estado_creacion,                      
					HISTORIAL = inu_historial,                     
					ESTADO = ibo_estado,                     
					MODIFICACION_LOCAL = ibo_modificacion_local,                     
					PORCENTAJE_RUTA = idb_porcentaje_ruta             
			WHERE PK_INFORMACION_REGISTRADORA = inu_pk_informacion_registradora;             
		END IF;             

	SET orc_retorno = inu_pk_informacion_registradora;          
	END IF;       
END IF;              


IF inu_orden_proceso = 2       
THEN          
IF inu_pk_informacion_registradora IS NOT NULL          
THEN             
DELETE FROM tbl_informacion_registradora             
WHERE PK_INFORMACION_REGISTRADORA = inu_pk_informacion_registradora;             
COMMIT;             
SET orc_retorno = '1';          
END IF;       
END IF;       

IF inu_orden_proceso = 3       
THEN           
BLOCK_RETORNO_QUERY_BUILDER:           
	BEGIN                 
	DECLARE sb_consulta   TEXT;                 
	DECLARE sb_query_execute   varchar(100);                 
	SET orc_retorno = 'e';                 
	SET sb_consulta = "SELECT * FROM tbl_informacion_registradora WHERE PK_INFORMACION_REGISTRADORA > 0";                 
	IF inu_pk_informacion_registradora IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_INFORMACION_REGISTRADORA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_pk_informacion_registradora);                 
	END IF;                 
	IF inu_fk_vehiculo IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FK_VEHICULO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo);                 
	END IF;                 
	IF inu_fk_ruta IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FK_RUTA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_ruta );                 
	END IF;                 
	IF inu_fk_usuario IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FK_USUARIO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_usuario);                 
	END IF;                 
	IF inu_fk_base IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FK_BASE = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_base );                 
	END IF;                 
	IF inu_fk_conductor IS NOT NULL                 
		THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FK_CONDUCTOR = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_conductor );                 
	END IF;                 
	IF idt_fecha_ingreso IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FECHA_INGRESO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, "'" );                     
		SET sb_consulta = CONCAT(sb_consulta, idt_fecha_ingreso );                     
		SET sb_consulta = CONCAT(sb_consulta, " 00:00:00'" );                 
	END IF;                 
	IF idt_hora_ingreso IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND HORA_INGRESO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, "'" );                     
		SET sb_consulta = CONCAT(sb_consulta, idt_hora_ingreso );                     
		SET sb_consulta = CONCAT(sb_consulta, "'" );                 
	END IF;                 
	IF inu_numero_vuelta IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NUMERO_VUELTA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_numero_vuelta );                 
	END IF;                 
	IF inu_num_vuelta_ant IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NUM_VUELTA_ANT = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_num_vuelta_ant );                 
	END IF;                 
	IF inu_num_llegada IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NUM_LLEGADA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_num_llegada );                 
	END IF;                 
	IF inu_diferencia_num IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND DIFERENCIA_NUM = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_diferencia_num );                 
	END IF;                 
	IF inu_entradas IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND ENTRADAS = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_entradas );                 
	END IF;                 
	IF inu_diferencia_entrada IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND DIFERENCIA_ENTRADA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_diferencia_entrada );                 
	END IF;                 
	IF inu_salidas IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND SALIDAS = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_salidas );                 
	END IF;                 
	IF inu_diferencia_salida IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND DIFERENCIA_SALIDA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_diferencia_salida );                 
	END IF;                 
	IF inu_total_dia IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND TOTAL_DIA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_total_dia );                 
	END IF;                 
	IF inu_estado_creacion IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO_CREACION = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_estado_creacion );                 
	END IF;                 
	IF inu_historial IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND HISTORIAL = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_historial );                 
	END IF;                 
	IF inu_estado_creacion IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO_CREACION = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_estado_creacion);                 
	END IF;                   
	IF ibo_estado IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
	END IF;                    
	IF inu_numeracion_base_salida IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NUMERACION_BASE_SALIDA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_numeracion_base_salida);                 
	END IF;                                    
	IF ibo_modificacion_local IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
	END IF;                  
	IF isb_pk_unica IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;                 
	IF idt_fecha_inicio IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND FECHA_INGRESO BETWEEN '");                     
		SET sb_consulta = CONCAT(sb_consulta, idt_fecha_inicio);                     
		SET sb_consulta = CONCAT(sb_consulta, "' AND '");                     
		SET sb_consulta = CONCAT(sb_consulta, idt_fecha_fin);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                     
		/*SET sb_consulta = CONCAT(sb_consulta, " ORDER BY FECHA_INGRESO ASC, HORA_INGRESO ASC ");                 */
	END IF;      
  		SET sb_consulta = CONCAT(sb_consulta, " ORDER BY HORA_SALIDA_BASE_SALIDA ASC ");
	SET @sb_query_execute = sb_consulta;                                 
	PREPARE stmt FROM @sb_query_execute;                 
	EXECUTE stmt;           
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;              

IF inu_orden_proceso = 4       
THEN            
SET orc_retorno = 'e';           
IF idt_sincronizacion IS NOT NULL           
THEN               
SELECT *                
FROM tbl_informacion_registradora              
WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     
AND MODIFICACION_LOCAL = 1;           
END IF;       
END IF;              

IF inu_orden_proceso = 5       
THEN           
	IF inu_fk_vehiculo IS NOT NULL           
	THEN             
		SELECT *             
		FROM tbl_informacion_registradora             
		WHERE FK_VEHICULO = inu_fk_vehiculo              
		ORDER BY FECHA_INGRESO DESC, HORA_INGRESO DESC          
		LIMIT 1;           
	END IF;       
END IF;              

IF inu_orden_proceso = 6       
THEN           
	SELECT *           
	FROM tbl_informacion_registradora           
	ORDER BY PK_INFORMACION_REGISTRADORA DESC          
	LIMIT 30;       
END IF;              

IF inu_orden_proceso = 7       
THEN           
	SELECT *            
	FROM tbl_informacion_registradora           
	WHERE FECHA_MODIFICACION > idt_sincronizacion ;       
END IF;    


IF inu_orden_proceso = 8      
THEN           
	IF inu_pk_informacion_registradora IS NOT NULL          
	THEN                 
		UPDATE tbl_informacion_registradora               
		SET   	                 
				FK_BASE = inu_fk_base,                                          
				FECHA_INGRESO = idt_fecha_ingreso,                      
				HORA_INGRESO = idt_hora_ingreso,                     
				NUMERO_VUELTA = inu_numero_vuelta,                      
				NUM_VUELTA_ANT = inu_num_vuelta_ant,                     
				NUM_LLEGADA = inu_num_llegada,                     
				DIFERENCIA_NUM = inu_diferencia_num,                     
				ENTRADAS = inu_entradas,                      
				DIFERENCIA_ENTRADA = inu_diferencia_entrada,                      
				SALIDAS = inu_salidas,                      
				DIFERENCIA_SALIDA = inu_diferencia_salida,                      
				TOTAL_DIA = inu_total_dia,                      
				FK_BASE_SALIDA = inu_fk_base_salida,                     
				FECHA_SALIDA_BASE_SALIDA = idt_fecha_salida_base_salida,                      
				HORA_SALIDA_BASE_SALIDA = idt_hora_salida_base_salida,                      
				NUMERACION_BASE_SALIDA = inu_numeracion_base_salida,                      
				ENTRADAS_BASE_SALIDA = inu_entradas_base_salida,                      
				SALIDAS_BASE_SALIDA = inu_salidas_base_salida,                     
				ESTADO = ibo_estado,
				FK_USUARIO = inu_fk_usuario

		WHERE PK_INFORMACION_REGISTRADORA = inu_pk_informacion_registradora;     
		         
		SET orc_retorno = inu_pk_informacion_registradora;          
		
	END IF;      
END IF; 

          
IF inu_orden_proceso <> 9      
THEN          
	SELECT orc_retorno;       
END IF;   
 
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_liquidacion_adicional
DROP PROCEDURE IF EXISTS `proc_tbl_liquidacion_adicional`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_liquidacion_adicional`(IN `inu_pk_liquidacion_adicional` int, IN `inu_fk_liquidacion_general` int, IN `ibo_estado` TINYINT, IN `inu_valor_descuento` double, IN `isb_motivo_descuento` varchar(250), IN `idt_fecha_descuento` datetime, IN `idt_fecha_modificacion` datetime, IN `isb_pk_unica` varchar(20), IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `inu_orden_proceso` int
)
BEGIN 

      

       

DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';      



       

IF inu_orden_proceso = 0       

THEN          

	SET orc_retorno = '0';          

	INSERT INTO tbl_liquidacion_adicional (FK_LIQUIDACION_GENERAL, ESTADO,  VALOR_DESCUENTO, MOTIVO_DESCUENTO, FECHA_DESCUENTO, PK_UNICA)          

	VALUES ( inu_fk_liquidacion_general, 1, inu_valor_descuento, isb_motivo_descuento, idt_fecha_descuento, isb_pk_unica);          

	COMMIT;       

END IF;      



   

IF inu_orden_proceso = 1       

THEN          

	SET orc_retorno = 'e';          

	IF inu_pk_liquidacion_adicional IS NOT NULL          

	THEN             

		UPDATE tbl_liquidacion_adicional

		SET                         

			FK_LIQUIDACION_GENERAL = inu_fk_liquidacion_general, 

			ESTADO = ibo_estado,                                    

			VALOR_DESCUENTO = inu_valor_descuento , 

			MOTIVO_DESCUENTO = isb_motivo_descuento         

		WHERE PK_LIQUIDACION_ADICIONAL = inu_pk_liquidacion_adicional;             

		SET orc_retorno = inu_pk_liquidacion_adicional;          

	END IF;       

END IF;       



       

IF inu_orden_proceso = 2       

THEN          

	IF inu_pk_liquidacion_adicional IS NOT NULL          

	THEN             

		DELETE FROM tbl_liquidacion_adicional

		WHERE FK_LIQUIDACION_GENERAL = inu_fk_liquidacion_general;

		COMMIT;             

		SET orc_retorno = '1';          

	END IF;       

END IF;      

	 



IF inu_orden_proceso = 3       

THEN           

	BLOCK_RETORNO_QUERY_BUILDER:           

	BEGIN                 

		DECLARE sb_consulta   TEXT;                 

		DECLARE sb_query_execute   varchar(100);                 

		SET orc_retorno = 'e';                 

		SET sb_consulta = "SELECT * FROM tbl_liquidacion_adicional WHERE PK_LIQUIDACION_ADICIONAL > 0";      



		IF inu_pk_liquidacion_adicional IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND PK_LIQUIDACION_ADICIONAL = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_pk_liquidacion_adicional);                 

		END IF; 

		IF inu_fk_liquidacion_general IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND FK_LIQUIDACION_GENERAL = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_fk_liquidacion_general);  			

		END IF;          

		IF ibo_estado IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     

			SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 

		END IF;        

		IF inu_valor_descuento IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND VALOR_DESCUENTO = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_valor_descuento);                 

		END IF;                 

		IF isb_motivo_descuento IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND MOTIVO_DESCUENTO = '");                     

			SET sb_consulta = CONCAT(sb_consulta, isb_motivo_descuento);                     

			SET sb_consulta = CONCAT(sb_consulta, "'");                 

		END IF; 

		IF idt_fecha_descuento IS NOT NULL                 

		THEN

		SET sb_consulta = CONCAT(sb_consulta, " AND (FECHA_DESCUENTO BETWEEN ");                     

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_descuento);

			SET sb_consulta = CONCAT(sb_consulta, " 00:00:00");

			SET sb_consulta = CONCAT(sb_consulta, " AND ");

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_descuento);

			SET sb_consulta = CONCAT(sb_consulta, " 23:59:59) ");

		END IF;		

		IF idt_fecha_modificacion IS NOT NULL                 

		THEN

		SET sb_consulta = CONCAT(sb_consulta, " AND (FECHA_MODIFICACION BETWEEN ");                     

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_modificacion);

			SET sb_consulta = CONCAT(sb_consulta, " 00:00:00");

			SET sb_consulta = CONCAT(sb_consulta, " AND ");

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_modificacion);

			SET sb_consulta = CONCAT(sb_consulta, " 23:59:59) ");

		END IF;

		IF isb_pk_unica IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     

			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     

			SET sb_consulta = CONCAT(sb_consulta, "'");                 

		END IF;                  

		SET @sb_query_execute = sb_consulta;                 

		PREPARE stmt FROM @sb_query_execute;                 

	EXECUTE stmt;                   

	END BLOCK_RETORNO_QUERY_BUILDER;       

END IF;       



       

IF inu_orden_proceso = 4       

THEN            

	SET orc_retorno = 'e';           

	IF idt_sincronizacion IS NOT NULL           

	THEN               

		SELECT *                

		FROM tbl_liquidacion_adicional              

		WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end);                     

		           

	END IF;       

END IF;       



       

IF inu_orden_proceso <> 4       

THEN          

	SELECT orc_retorno;       

END IF;    

END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_liquidacion_general
DROP PROCEDURE IF EXISTS `proc_tbl_liquidacion_general`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_liquidacion_general`(IN `inu_pk_liquidacion_general` int, IN `inu_fk_tarifa_fija` int, IN `inu_fk_vehiculo` int, IN `inu_fk_conductor` int, IN `inu_total_pasajeros_liquidados` int, IN `inu_total_valor_vueltas` double, IN `inu_total_valor_descuentos_pasajeros` double, IN `inu_total_valor_descuentos_adicional` double, IN `ibo_estado` boolean, IN `idt_fecha_modificacion` datetime, IN `ibo_modificacion_local` boolean, IN `isb_pk_unica` varchar(20), IN `inu_pk_usuario` int, IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `inu_orden_proceso` int
)
BEGIN 

      

       

DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';      



       

IF inu_orden_proceso = 0       

THEN          

	SET orc_retorno = '0';          

	INSERT INTO tbl_liquidacion_general (FK_TARIFA_FIJA, FK_VEHICULO, FK_CONDUCTOR, TOTAL_PASAJEROS_LIQUIDADOS, TOTAL_VALOR_VUELTAS, TOTAL_VALOR_DESCUENTOS_PASAJEROS, TOTAL_VALOR_DESCUENTOS_ADICIONAL, ESTADO, MODIFICACION_LOCAL, PK_UNICA, USUARIO)          

	VALUES ( inu_fk_tarifa_fija , inu_fk_vehiculo, inu_fk_conductor,inu_total_pasajeros_liquidados , inu_total_valor_vueltas, inu_total_valor_descuentos_pasajeros, inu_total_valor_descuentos_adicional, ibo_estado, ibo_modificacion_local, isb_pk_unica, inu_pk_usuario);          

	COMMIT;       

END IF;      



   

IF inu_orden_proceso = 1       

THEN          

	SET orc_retorno = 'e';          

	IF inu_pk_liquidacion_general IS NOT NULL          

	THEN             

		UPDATE tbl_liquidacion_general

		SET     

			FK_TARIFA_FIJA = inu_fk_tarifa_fija,                      

			FK_VEHICULO = inu_fk_vehiculo,                     

			TOTAL_PASAJEROS_LIQUIDADOS = inu_total_pasajeros_liquidados ,                     

			TOTAL_VALOR_VUELTAS = inu_total_valor_vueltas , 

			TOTAL_VALOR_DESCUENTOS_PASAJEROS = inu_total_valor_descuentos_pasajeros , 

			TOTAL_VALOR_DESCUENTOS_ADICIONAL = inu_total_valor_descuentos_adicional , 

			ESTADO = ibo_estado , 

			MODIFICACION_LOCAL = ibo_modificacion_local              

		WHERE PK_LIQUIDACION_GENERAL = inu_pk_liquidacion_general;  
		
		IF ibo_estado = 0
		THEN
			UPDATE tbl_liquidacion_vueltas SET ESTADO = ibo_estado WHERE fk_liquidacion_general = inu_pk_liquidacion_general;
			UPDATE tbl_liquidacion_adicional SET ESTADO = ibo_estado WHERE fk_liquidacion_general = inu_pk_liquidacion_general;
		END IF;            

		SET orc_retorno = inu_pk_liquidacion_general;          

	END IF;       

END IF;       



       

IF inu_orden_proceso = 2       

THEN          

	IF inu_pk_liquidacion_general IS NOT NULL          

	THEN             

		DELETE FROM tbl_liquidacion_adicional

		WHERE FK_LIQUIDACION_GENERAL = inu_pk_liquidacion_general;



        DELETE FROM tbl_liquidacion_vueltas

		WHERE FK_LIQUIDACION_GENERAL = inu_pk_liquidacion_general;

         

		DELETE FROM tbl_liquidacion_general

		WHERE PK_LIQUIDACION_GENERAL = inu_pk_liquidacion_general;    

    

		COMMIT;             

		SET orc_retorno = '1';          

	END IF;       

END IF;      

	 



IF inu_orden_proceso = 3       

THEN           

	BLOCK_RETORNO_QUERY_BUILDER:           

	BEGIN                 

		DECLARE sb_consulta   TEXT;                 

		DECLARE sb_query_execute   varchar(100);                 

		SET orc_retorno = 'e';                 

		SET sb_consulta = "SELECT * FROM tbl_liquidacion_general WHERE PK_LIQUIDACION_GENERAL > 0";      



		IF inu_pk_liquidacion_general IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND PK_LIQUIDACION_GENERAL = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_pk_liquidacion_general);                 

		END IF;                 

		IF inu_fk_tarifa_fija IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND FK_TARIFA_FIJA = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_fk_tarifa_fija);  			

		END IF;                 

		IF inu_fk_vehiculo IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND FK_VEHICULO = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo);  			

		END IF; 

		IF ibo_estado IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     

			SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 

		END IF;                 

		IF ibo_modificacion_local IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     

			SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 

		END IF; 

		IF idt_fecha_modificacion IS NOT NULL                 

		THEN

		SET sb_consulta = CONCAT(sb_consulta, " AND (FECHA_MODIFICACION BETWEEN ");                     

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_modificacion);

			SET sb_consulta = CONCAT(sb_consulta, " 00:00:00");

			SET sb_consulta = CONCAT(sb_consulta, " AND ");

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_modificacion);

			SET sb_consulta = CONCAT(sb_consulta, " 23:59:59) ");

		END IF;

		IF isb_pk_unica IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     

			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     

			SET sb_consulta = CONCAT(sb_consulta, "'");                 

		END IF;    

		IF inu_pk_usuario IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND USUARIO = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_pk_usuario);                 

		END IF; 

		SET @sb_query_execute = sb_consulta;                 

		PREPARE stmt FROM @sb_query_execute;                 

	EXECUTE stmt;                   

	END BLOCK_RETORNO_QUERY_BUILDER;       

END IF;       



       

IF inu_orden_proceso = 4       

THEN            

	SET orc_retorno = 'e';           

	IF idt_sincronizacion IS NOT NULL           

	THEN               

		SELECT *                

		FROM tbl_liquidacion_general              

		WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     

		AND MODIFICACION_LOCAL = 1;           

	END IF;       

END IF;       



       

IF inu_orden_proceso <> 4       

THEN          

	SELECT orc_retorno;       

END IF;    

END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_liquidacion_vueltas
DROP PROCEDURE IF EXISTS `proc_tbl_liquidacion_vueltas`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_liquidacion_vueltas`(IN `inu_pk_liquidacion_vueltas` int, IN `inu_fk_informacion_registradora` int, IN `inu_fk_liquidacion_general` int, IN `ibo_estado` TINYINT, IN `inu_pasajeros_descuento` int, IN `inu_valor_descuento` double, IN `isb_motivo_descuento` varchar(250), IN `idt_fecha_descuento` datetime, IN `idt_fecha_modificacion` datetime, IN `isb_pk_unica` varchar(20), IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `inu_orden_proceso` int
)
BEGIN 

      

       

DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';      



       

IF inu_orden_proceso = 0       

THEN          

	SET orc_retorno = '0';          

	INSERT INTO tbl_liquidacion_vueltas (FK_INFORMACION_REGISTRADORA, FK_LIQUIDACION_GENERAL, ESTADO, PASAJEROS_DESCUENTO,VALOR_DESCUENTO, MOTIVO_DESCUENTO, FECHA_DESCUENTO, PK_UNICA)          

	VALUES ( inu_fk_informacion_registradora , inu_fk_liquidacion_general, 1, inu_pasajeros_descuento, inu_valor_descuento, isb_motivo_descuento, idt_fecha_descuento, isb_pk_unica);          

	COMMIT;       

END IF;      



   

IF inu_orden_proceso = 1       

THEN          

	SET orc_retorno = 'e';          

	IF inu_pk_liquidacion_vueltas IS NOT NULL          

	THEN             

		UPDATE tbl_liquidacion_vueltas

		SET     

			FK_INFORMACION_REGISTRADORA = inu_fk_informacion_registradora,                      

			FK_LIQUIDACION_GENERAL = inu_fk_liquidacion_general, 

			ESTADO = ibo_estado,                               

			VALOR_DESCUENTO = inu_valor_descuento , 

			MOTIVO_DESCUENTO = isb_motivo_descuento         

		WHERE PK_LIQUIDACION_VUELTAS = inu_pk_liquidacion_vueltas;             

		SET orc_retorno = inu_pk_liquidacion_vueltas;          

	END IF;       

END IF;       



       

IF inu_orden_proceso = 2       

THEN          

	IF inu_pk_liquidacion_vueltas IS NOT NULL          

	THEN             

		DELETE FROM tbl_liquidacion_vueltas

		WHERE FK_LIQUIDACION_GENERAL = inu_fk_liquidacion_general;

		COMMIT;             

		SET orc_retorno = '1';          

	END IF;       

END IF;      

	 



IF inu_orden_proceso = 3       

THEN           

	BLOCK_RETORNO_QUERY_BUILDER:           

	BEGIN                 

		DECLARE sb_consulta   TEXT;                 

		DECLARE sb_query_execute   varchar(100);                 

		SET orc_retorno = 'e';                 

		SET sb_consulta = "SELECT * FROM tbl_liquidacion_vueltas WHERE PK_LIQUIDACION_VUELTAS > 0";      



		IF inu_pk_liquidacion_vueltas IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND PK_LIQUIDACION_VUELTAS = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_pk_liquidacion_vueltas);                 

		END IF; 

		IF inu_fk_informacion_registradora IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND FK_INFORMACION_REGISTRADORA = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_fk_informacion_registradora);  			

		END IF;

		IF inu_fk_liquidacion_general IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND FK_LIQUIDACION_GENERAL = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_fk_liquidacion_general);  			

		END IF; 

		IF ibo_estado IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     

			SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 

		END IF;                  

		IF inu_valor_descuento IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND VALOR_DESCUENTO = ");                     

			SET sb_consulta = CONCAT(sb_consulta, inu_valor_descuento);                 

		END IF;                 

		IF isb_motivo_descuento IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND MOTIVO_DESCUENTO = '");                     

			SET sb_consulta = CONCAT(sb_consulta, isb_motivo_descuento);                     

			SET sb_consulta = CONCAT(sb_consulta, "'");                 

		END IF; 

		IF idt_fecha_descuento IS NOT NULL                 

		THEN

		SET sb_consulta = CONCAT(sb_consulta, " AND (FECHA_DESCUENTO BETWEEN ");                     

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_descuento);

			SET sb_consulta = CONCAT(sb_consulta, " 00:00:00");

			SET sb_consulta = CONCAT(sb_consulta, " AND ");

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_descuento);

			SET sb_consulta = CONCAT(sb_consulta, " 23:59:59) ");

		END IF;		

		IF idt_fecha_modificacion IS NOT NULL                 

		THEN

		SET sb_consulta = CONCAT(sb_consulta, " AND (FECHA_MODIFICACION BETWEEN ");                     

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_modificacion);

			SET sb_consulta = CONCAT(sb_consulta, " 00:00:00");

			SET sb_consulta = CONCAT(sb_consulta, " AND ");

			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_modificacion);

			SET sb_consulta = CONCAT(sb_consulta, " 23:59:59) ");

		END IF;

		IF isb_pk_unica IS NOT NULL                 

		THEN                     

			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     

			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     

			SET sb_consulta = CONCAT(sb_consulta, "'");                 

		END IF;                  

		SET @sb_query_execute = sb_consulta;                 

		PREPARE stmt FROM @sb_query_execute;                 

	EXECUTE stmt;                   

	END BLOCK_RETORNO_QUERY_BUILDER;       

END IF;       



       

IF inu_orden_proceso = 4       

THEN            

	SET orc_retorno = 'e';           

	IF idt_sincronizacion IS NOT NULL           

	THEN               

		SELECT *                

		FROM tbl_liquidacion_vueltas              

		WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end);                     

		           

	END IF;       

END IF;       



       

IF inu_orden_proceso <> 4       

THEN          

	SELECT orc_retorno;       

END IF;    

END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_macro_ruta
DROP PROCEDURE IF EXISTS `proc_tbl_macro_ruta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_macro_ruta`(   IN  `inu_pk_macro_ruta`       int,   IN  `inu_fk_empresa`          int,   IN  `isb_nombre`              varchar(50),   IN  `inu_historial`           int,   IN  `inu_estado_creacion`     int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_macro_ruta( FK_EMPRESA, NOMBRE, HISTORIAL, ESTADO_CREACION, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( inu_fk_empresa , isb_nombre ,inu_historial, inu_estado_creacion, ibo_estado , ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_macro_ruta IS NOT NULL          THEN             UPDATE tbl_macro_ruta             SET     FK_EMPRESA = inu_fk_empresa,                      NOMBRE = isb_nombre,                     HISTORIAL = inu_historial,                      ESTADO_CREACION = inu_estado_creacion,                     ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_MACRO_RUTA = inu_pk_macro_ruta;             SET orc_retorno = inu_pk_macro_ruta;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_macro_ruta IS NOT NULL          THEN             DELETE FROM tbl_macro_ruta             WHERE PK_MACRO_RUTA = inu_pk_macro_ruta;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_macro_ruta WHERE PK_MACRO_RUTA > 0";                 IF inu_pk_macro_ruta IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_MACRO_RUTA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_macro_ruta);                 END IF;                 IF inu_fk_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_empresa);                 END IF;                 IF isb_nombre IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                   IF inu_historial IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND HISTORIAL = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_historial);                 END IF;                 IF inu_estado_creacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO_CREACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_estado_creacion);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_macro_ruta               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_macro_subrutas
DROP PROCEDURE IF EXISTS `proc_tbl_macro_subrutas`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_macro_subrutas`(   IN  `inu_pk_macro_subrutas`   int,   IN  `inu_fk_macro_ruta`       int,   IN  `inu_fk_ruta`             int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_macro_subrutas ( FK_MACRO_RUTA,FK_RUTA, ESTADO , MODIFICACION_LOCAL, PK_UNICA)          VALUES ( inu_fk_macro_ruta , inu_fk_ruta , ibo_estado , ibo_modificacion_local, isb_pk_unica);          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_macro_subrutas IS NOT NULL          THEN             UPDATE tbl_macro_subrutas             SET     FK_MACRO_RUTA = inu_fk_macro_ruta,                      FK_RUTA = inu_fk_ruta,                      ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_MACRO_SUBRUTAS = inu_pk_macro_subrutas;             SET orc_retorno = inu_pk_macro_subrutas;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_macro_subrutas IS NOT NULL          THEN             DELETE FROM tbl_macro_subrutas             WHERE PK_MACRO_SUBRUTAS = inu_pk_macro_subrutas;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_macro_subrutas WHERE PK_MACRO_SUBRUTAS > 0";                 IF inu_pk_macro_subrutas IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_MACRO_SUBRUTAS = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_macro_subrutas);                 END IF;                 IF inu_fk_macro_ruta IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_MACRO_RUTA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_macro_ruta);                 END IF;                 IF inu_fk_ruta IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_RUTA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_ruta);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_macro_subrutas               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_motivo
DROP PROCEDURE IF EXISTS `proc_tbl_motivo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_motivo`(IN `inu_pk_motivo` int, IN `inu_fk_auditoria` int, IN `isb_tabla_auditoria` varchar(50), IN `isb_descripcion_motivo` text, IN `ibo_modificacion_local` boolean, IN `idt_sincronizacion` datetime, IN `inu_orden_proceso` int )
BEGIN        
      
	DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';
	
	IF inu_orden_proceso = 0       
	THEN          
		SET orc_retorno = '0';          
		INSERT INTO tbl_motivo( FK_AUDITORIA, TABLA_AUDITORIA, DESCRIPCION_MOTIVO , MODIFICACION_LOCAL)          
		VALUES ( inu_fk_auditoria , isb_tabla_auditoria , isb_descripcion_motivo, ibo_modificacion_local );          
		COMMIT;
	END IF; 
 
 
	IF inu_orden_proceso = 1       
	THEN          
		SET orc_retorno = 'e';          
		IF inu_pk_motivo IS NOT NULL          
		THEN             
			UPDATE tbl_motivo             
			SET   FK_AUDITORIA = inu_fk_auditoria,                      
					TABLA_AUDITORIA = isb_tabla_auditoria,                      
					DESCRIPCION_MOTIVO =  isb_descripcion_motivo,                     
					MODIFICACION_LOCAL = ibo_modificacion_local              
			WHERE PK_MOTIVO = inu_pk_motivo;            
			SET orc_retorno = inu_pk_motivo;          
		END IF; 
	END IF; 
  
  
  IF inu_orden_proceso = 2       
  THEN          
	  IF inu_pk_motivo IS NOT NULL          
	  THEN             
		  DELETE FROM tbl_motivo WHERE PK_MOTIVO = inu_pk_motivo;             
		  COMMIT;             
		  SET orc_retorno = '1';          
	  END IF;       
  END IF;       
  
  
	IF inu_orden_proceso = 3       
	THEN           
		BLOCK_RETORNO_QUERY_BUILDER:           
		BEGIN                 
			DECLARE sb_consulta   TEXT;                 
			DECLARE sb_query_execute   varchar(100);                 
			SET orc_retorno = 'e';                 
			SET sb_consulta = "SELECT * FROM tbl_motivo WHERE PK_MOTIVO > 0";                 
			
			IF inu_pk_motivo IS NOT NULL                 THEN                    
				SET sb_consulta = CONCAT(sb_consulta, " AND PK_MOTIVO = ");                     
				SET sb_consulta = CONCAT(sb_consulta, inu_pk_motivo);                 
			END IF;                 
			IF inu_fk_auditoria IS NOT NULL                 THEN                     
				SET sb_consulta = CONCAT(sb_consulta, " AND FK_AUDITORIA = ");                     
				SET sb_consulta = CONCAT(sb_consulta, inu_fk_auditoria );                 
			END IF;                 
			IF isb_tabla_auditoria IS NOT NULL                 THEN                     
				SET sb_consulta = CONCAT(sb_consulta, " AND TABLA_AUDITORIA = '");                     
				SET sb_consulta = CONCAT(sb_consulta, isb_tabla_auditoria );                     
				SET sb_consulta = CONCAT(sb_consulta, "'" );                 
			END IF;                 
			IF isb_descripcion_motivo IS NOT NULL                 THEN                     
				SET sb_consulta = CONCAT(sb_consulta, " AND DESCRIPCION_MOTIVO = '");                    
				SET sb_consulta = CONCAT(sb_consulta, isb_descripcion_motivo );                     
				SET sb_consulta = CONCAT(sb_consulta, "'" );                 
			END IF;                 
			IF ibo_modificacion_local IS NOT NULL                 THEN                     
				SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
				SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
			END IF;                  
			
			SET @sb_query_execute = sb_consulta;                 
			PREPARE stmt FROM @sb_query_execute;                 
			EXECUTE stmt;                   
		END BLOCK_RETORNO_QUERY_BUILDER;       
	END IF;              
	 
	 
	 IF inu_orden_proceso = 4       
	 THEN            
	 	SET orc_retorno = 'e';           
		IF idt_sincronizacion IS NOT NULL           
		THEN               
			SELECT *  FROM tbl_motivo  WHERE FECHA_MODIFICACION > idt_sincronizacion  AND MODIFICACION_LOCAL = 1;           
		END IF;       
	 END IF;              
	 
	 IF inu_orden_proceso <> 4       
	 THEN          
	 	SELECT orc_retorno;       
	 END IF;    
	 
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_pais
DROP PROCEDURE IF EXISTS `proc_tbl_pais`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_pais`(   IN  `inu_pk_pais`        int,   IN  `isb_nombre`         varchar(50),   IN  `isb_codigo_area`    varchar(50),   IN  `inu_orden_proceso`  int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_pais WHERE tbl_pais.PK_PAIS > 0";                 IF inu_pk_pais IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_pais.PK_PAIS = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_pais);                 END IF;                 IF isb_nombre IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_pais.NOMBRE like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_codigo_area IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND tbl_pais.CODIGO_AREA like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_codigo_area);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_pantalla_informacion
DROP PROCEDURE IF EXISTS `proc_tbl_pantalla_informacion`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_pantalla_informacion`(   IN  `inu_pk_pantalla_informacion`  int,   IN  `isb_columna`                  varchar(50),   IN  `inu_posicion`                 int,   IN  `ibo_estado`                   boolean,   IN  `inu_tipo`                     int,   IN  `inu_orden_proceso`            int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_pantalla_informacion( COLUMNA, POSICION, ESTADO, TIPO )          VALUES ( isb_columna, inu_posicion, ibo_estado, inu_tipo );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_pantalla_informacion IS NOT NULL          THEN             UPDATE tbl_pantalla_informacion             SET     COLUMNA = isb_columna,                      POSICION = inu_posicion,                      ESTADO = ibo_estado,                      TIPO = inu_tipo             WHERE PK_PANTALLA_INFORMACION = inu_pk_pantalla_informacion;             SET orc_retorno = inu_pk_pantalla_informacion;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_pantalla_informacion IS NOT NULL          THEN             DELETE FROM tbl_pantalla_informacion             WHERE PK_PANTALLA_INFORMACION = inu_pk_pantalla_informacion;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_pantalla_informacion WHERE PK_PANTALLA_INFORMACION > 0";                 IF inu_pk_pantalla_informacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_PANTALLA_INFORMACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_pantalla_informacion);                 END IF;                 IF inu_tipo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND TIPO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_tipo);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 SET sb_consulta = CONCAT(sb_consulta, " ORDER BY POSICION");                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso <> 3       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_perfil
DROP PROCEDURE IF EXISTS `proc_tbl_perfil`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_perfil`(   IN  `inu_pk_perfil`           int,   IN  `isb_nombre_perfil`       varchar(50),   IN  `isb_descripcion`         text,   IN  `ibo_sync`                boolean,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_perfil( NOMBRE_PERFIL, DESCRIPCION, SYNC, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( isb_nombre_perfil , isb_descripcion , ibo_sync , ibo_estado , ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_perfil IS NOT NULL          THEN             UPDATE tbl_perfil             SET     NOMBRE_PERFIL = isb_nombre_perfil ,                     DESCRIPCION = isb_descripcion,                      SYNC = ibo_sync,                     ESTADO = ibo_estado,                      MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_PERFIL = inu_pk_perfil;             SET orc_retorno = inu_pk_perfil;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_perfil IS NOT NULL          THEN             DELETE FROM tbl_perfil             WHERE PK_PERFIL = inu_pk_perfil;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_perfil WHERE PK_PERFIL > 0";                 IF inu_pk_perfil IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_PERFIL = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_perfil);                 END IF;                 IF isb_nombre_perfil IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE_PERFIL like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre_perfil);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_descripcion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND DESCRIPCION like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_descripcion);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_perfil               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_permiso_empresa
DROP PROCEDURE IF EXISTS `proc_tbl_permiso_empresa`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_permiso_empresa`(   IN  `inu_pk_permiso_empresa`  int,   IN  `inu_fk_empresa`          int,   IN  `inu_fk_base`             int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `idt_sincronizacion`      datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_permiso_empresa( FK_BASE, FK_EMPRESA, ESTADO, MODIFICACION_LOCAL )          VALUES ( inu_fk_base, inu_fk_empresa, ibo_estado , ibo_modificacion_local );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_permiso_empresa IS NOT NULL          THEN             UPDATE tbl_permiso_empresa             SET     FK_EMPRESA = inu_fk_empresa ,                     FK_BASE = inu_fk_base ,                     ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_PERMISO_EMPRESA = inu_pk_permiso_empresa;             SET orc_retorno = inu_pk_permiso_empresa;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_permiso_empresa IS NOT NULL          THEN             DELETE FROM tbl_permiso_empresa             WHERE PK_PERMISO_EMPRESA = inu_pk_permiso_empresa;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_permiso_empresa WHERE PK_PERMISO_EMPRESA > 0";                 IF inu_pk_permiso_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_PERMISO_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_permiso_empresa);                 END IF;                 IF inu_fk_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_empresa);                 END IF;                 IF inu_fk_base IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_BASE = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_base);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                  IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_permiso_empresa               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_planificacion
DROP PROCEDURE IF EXISTS `proc_tbl_planificacion`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_planificacion`(   IN  `inu_pk_planificacion`    int,   IN  `inu_fk_base`             int,   IN  `inu_fk_vehiculo`         int,   IN  `inu_fk_ruta`             int,   IN  `idt_hora_salida`         time,   IN  `idt_hora_llegada`        time,   IN  `idt_fecha_planificada`   date,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';           insert into tbl_planificacion (FK_BASE ,FK_VEHICULO, FK_RUTA, HORA_SALIDA, HORA_LLEGADA, FECHA_PLANIFICADA,ESTADO, MODIFICACION_LOCAL, PK_UNICA)           VALUES ( inu_fk_base , inu_fk_vehiculo , inu_fk_ruta ,  idt_hora_salida, idt_hora_llegada, idt_fecha_planificada, ibo_estado, ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_planificacion IS NOT NULL          THEN             UPDATE tbl_planificacion             SET     ESTADO = ibo_estado             WHERE PK_PLANIFICACION = inu_pk_planificacion;             SET orc_retorno = inu_pk_planificacion;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_planificacion IS NOT NULL          THEN             DELETE FROM tbl_planificacion             WHERE PK_PLANIFICACION = inu_pk_planificacion;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_planificacion WHERE PK_PLANIFICACION > 0";                 IF inu_pk_planificacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_PLANIFICACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_planificacion);                 END IF;                 IF inu_fk_vehiculo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_VEHICULO  = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo);                 END IF;                 IF inu_fk_ruta IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_RUTA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_ruta);                 END IF;                 IF idt_fecha_planificada IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FECHA_PLANIFICADA = '");                     SET sb_consulta = CONCAT(sb_consulta, idt_fecha_planificada);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_planificacion               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso = 5       THEN            SET orc_retorno = 'e';           SELECT *            FROM tbl_planificacion           WHERE FECHA_PLANIFICADA >= idt_fecha_planificada                  AND ESTADO = 1 ORDER BY FECHA_PLANIFICADA ASC, FK_VEHICULO ASC;       END IF;              IF inu_orden_proceso <> 5       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_prestamo_vehiculo
DROP PROCEDURE IF EXISTS `proc_tbl_prestamo_vehiculo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_prestamo_vehiculo`(   IN  `inu_pk_prestamo_vehiculo`   int,   IN  `inu_fk_vehiculo`            int,   IN  `inu_fk_base`                int,   IN  `idt_fecha_prestamo_inicio`  date,   IN  `idt_fecha_prestamo_fin`     date,   IN  `inu_estado`                 int,   IN  `idt_fecha_auditada`         timestamp,   IN  `inu_orden_proceso`          int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_prestamo_vehiculo( FK_VEHICULO, FK_BASE, FECHA_PRESTAMO_INICIO, FECHA_PRESTAMO_FIN, ESTADO )          VALUES ( inu_fk_vehiculo, inu_fk_base, idt_fecha_prestamo_inicio, idt_fecha_prestamo_fin, inu_estado );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_prestamo_vehiculo IS NOT NULL          THEN             UPDATE tbl_prestamo_vehiculo             SET     FK_VEHICULO = inu_fk_vehiculo,                      FK_BASE = inu_fk_base,                      FECHA_PRESTAMO_INICIO = idt_fecha_prestamo_inicio,                      FECHA_PRESTAMO_FIN = idt_fecha_prestamo_fin,                     ESTADO = inu_estado,                      FECHA_AUDITADA = idt_fecha_auditada             WHERE PK_PRESTAMO_VEHICULO = inu_pk_prestamo_vehiculo;             SET orc_retorno = inu_pk_prestamo_vehiculo;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_prestamo_vehiculo IS NOT NULL          THEN             DELETE FROM tbl_prestamo_vehiculo             WHERE PK_PRESTAMO_VEHICULO = inu_pk_prestamo_vehiculo;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_prestamo_vehiculo WHERE PK_PRESTAMO_VEHICULO > 0";                 IF inu_pk_prestamo_vehiculo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_PRESTAMO_VEHICULO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_prestamo_vehiculo);                 END IF;                 IF inu_fk_vehiculo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_VEHICULO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo);                 END IF;                 IF inu_fk_base IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_BASE = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_base);                 END IF;                 IF inu_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_estado);                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_prestamo_vehiculo               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_punto
DROP PROCEDURE IF EXISTS `proc_tbl_punto`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_punto`(   IN  `inu_pk_punto`            int,   IN  `isb_nombre`              varchar(50),   IN  `isb_descripcion`         text,   IN  `isb_latitud`             varchar(20),   IN  `isb_longitud`            varchar(20),   IN  `inu_codigo_punto`        int,   IN  `isb_direccion_latitud`   varchar(20),   IN  `isb_direccion_longitud`  varchar(20),   IN  `inu_radio`               int,   IN  `inu_direccion`           int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_punto( NOMBRE, DESCRIPCION, LATITUD, LONGITUD, CODIGO_PUNTO,DIRECCION_LATITUD, DIRECCION_LONGITUD, RADIO, DIRECCION, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( isb_nombre , isb_descripcion , isb_latitud , isb_longitud , inu_codigo_punto , isb_direccion_latitud, isb_direccion_longitud, inu_radio, inu_direccion, ibo_estado , ibo_modificacion_local, isb_pk_unica);          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_punto IS NOT NULL          THEN             UPDATE tbl_punto             SET     NOMBRE = isb_nombre,                      DESCRIPCION = isb_descripcion,                      LATITUD = isb_latitud,                      LONGITUD = isb_longitud,                      CODIGO_PUNTO = inu_codigo_punto,                     DIRECCION_LATITUD = isb_direccion_latitud,                      DIRECCION_LONGITUD = isb_direccion_longitud,                      RADIO = inu_radio,                       DIRECCION = inu_direccion,                     ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_PUNTO = inu_pk_punto;             SET orc_retorno = inu_pk_punto;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_punto IS NOT NULL          THEN             DELETE FROM tbl_punto             WHERE PK_PUNTO = inu_pk_punto;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_punto WHERE PK_PUNTO > 0";                 IF inu_pk_punto IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_PUNTO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_punto);                 END IF;                 IF isb_nombre IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_descripcion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND DESCRIPCION like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_descripcion);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_latitud IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND LATITUD like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_latitud);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_longitud IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND LONGITUD like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_longitud);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF inu_codigo_punto IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND CODIGO_PUNTO like '");                     SET sb_consulta = CONCAT(sb_consulta, inu_codigo_punto);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                 END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_punto               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_punto_control
DROP PROCEDURE IF EXISTS `proc_tbl_punto_control`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_punto_control`(   IN  `inu_pk_punto_control`    int,   IN  `inu_fk_punto`            int,   IN  `inu_fk_info_regis`       int,   IN  `idt_hora_pto_control`    time,   IN  `idt_fecha_pto_control`   datetime,   IN  `inu_numeracion`          int,   IN  `inu_entradas`            int,   IN  `inu_salidas`             int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              
DECLARE orc_retorno   TEXT DEFAULT '-1';   
           
IF inu_orden_proceso = 0       THEN          
SET orc_retorno = '0';          
	INSERT INTO tbl_punto_control( FK_PUNTO, FK_INFO_REGIS, HORA_PTO_CONTROL, FECHA_PTO_CONTROL, NUMERACION, ENTRADAS, SALIDAS ,ESTADO, MODIFICACION_LOCAL, PK_UNICA)          
	VALUES ( inu_fk_punto , inu_fk_info_regis , idt_hora_pto_control , idt_fecha_pto_control , inu_numeracion , inu_entradas , inu_salidas,  ibo_estado, ibo_modificacion_local , isb_pk_unica);          
COMMIT;       
END IF;              

IF inu_orden_proceso = 1       THEN          
SET orc_retorno = 'e';          
	IF inu_pk_punto_control IS NOT NULL          THEN             
		UPDATE tbl_punto_control             
		SET     FK_PUNTO = inu_fk_punto,                      
				FK_INFO_REGIS = inu_fk_info_regis,                      
				HORA_PTO_CONTROL = idt_hora_pto_control,                     
				FECHA_PTO_CONTROL = idt_fecha_pto_control,                     
				NUMERACION = inu_numeracion,                      
				ENTRADAS = inu_entradas,                     
				SALIDAS  = inu_salidas,                     
				ESTADO = ibo_estado ,                     
				MODIFICACION_LOCAL = ibo_modificacion_local              
		WHERE 	PK_PUNTO_CONTROL = inu_pk_punto_control;             
		SET orc_retorno = inu_pk_punto_control;          
	END IF;       
END IF;              

IF inu_orden_proceso = 2       
THEN          
	IF inu_pk_punto_control IS NOT NULL          
	THEN             
		DELETE FROM tbl_punto_control             
		WHERE PK_PUNTO_CONTROL = inu_pk_punto_control;             
		COMMIT;             
		SET orc_retorno = '1';          
	END IF;       
END IF;       

IF inu_orden_proceso = 3       THEN           
	BLOCK_RETORNO_QUERY_BUILDER:           
	BEGIN                 
		DECLARE sb_consulta   TEXT;                 
		DECLARE sb_query_execute   varchar(100);                 
		SET orc_retorno = 'e';                 
		SET sb_consulta = "SELECT * FROM tbl_punto_control use index(FK_PUNTO_CONTROL_INFORMACION_REGISTRADORA) WHERE PK_PUNTO_CONTROL > 0";                 
		
		IF inu_pk_punto_control IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND PK_PUNTO_CONTROL = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_pk_punto_control);                 
		END IF;                 
		IF inu_fk_punto IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND FK_PUNTO = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_fk_punto );                 
		END IF;                 
		IF inu_fk_info_regis IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND FK_INFO_REGIS = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_fk_info_regis );                 
		END IF;                 
		IF idt_hora_pto_control IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND HORA_PTO_CONTROL = '");                     
			SET sb_consulta = CONCAT(sb_consulta, idt_hora_pto_control );                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		IF idt_fecha_pto_control IS NOT NULL                
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND FECHA_PTO_CONTROL = '");                     
			SET sb_consulta = CONCAT(sb_consulta, idt_fecha_pto_control);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		IF inu_numeracion IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND NUMERACION = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_numeracion );                 
		END IF;                 
		IF inu_entradas IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND ENTRADAS = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_entradas);                 
		END IF;                 
		IF inu_salidas IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND SALIDAS = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_salidas );                 
		END IF;                 
		IF ibo_estado IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     
			SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
		END IF;                 
		IF ibo_modificacion_local IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
			SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
		END IF;                  
		IF isb_pk_unica IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                 
		
		SET @sb_query_execute = sb_consulta;                 
		PREPARE stmt FROM @sb_query_execute;                 
		EXECUTE stmt;                   
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;              

IF inu_orden_proceso = 4       THEN            
SET orc_retorno = 'e';           
IF idt_sincronizacion IS NOT NULL          
THEN               
SELECT *                
FROM tbl_punto_control               
WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     
AND MODIFICACION_LOCAL = 1;           
END IF;       
END IF;              

IF inu_orden_proceso <> 4       
THEN          
SELECT orc_retorno;       
END IF;    
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_ruta
DROP PROCEDURE IF EXISTS `proc_tbl_ruta`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_ruta`(   IN  `inu_pk_ruta`             int,   IN  `inu_fk_empresa`          int,   IN  `isb_nombre`              varchar(50),   IN  `inu_historial`           int,   IN  `inu_estado_creacion`     int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_ruta( FK_EMPRESA, NOMBRE, HISTORIAL, ESTADO_CREACION, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( inu_fk_empresa , isb_nombre ,inu_historial, inu_estado_creacion, ibo_estado , ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_ruta IS NOT NULL          THEN             UPDATE tbl_ruta             SET     FK_EMPRESA = inu_fk_empresa,                      NOMBRE = isb_nombre,                     HISTORIAL = inu_historial,                      ESTADO_CREACION = inu_estado_creacion,                     ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_RUTA = inu_pk_ruta;             SET orc_retorno = inu_pk_ruta;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_ruta IS NOT NULL          THEN             DELETE FROM tbl_ruta             WHERE PK_RUTA = inu_pk_ruta;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_ruta WHERE PK_RUTA > 0";                 IF inu_pk_ruta IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_RUTA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_ruta);                 END IF;                 IF inu_fk_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_empresa);                 END IF;                 IF isb_nombre IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                   IF inu_historial IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND HISTORIAL = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_historial);                 END IF;                 IF inu_estado_creacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO_CREACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_estado_creacion);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_ruta               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_ruta_punto
DROP PROCEDURE IF EXISTS `proc_tbl_ruta_punto`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_ruta_punto`(IN `inu_pk_ruta_punto` int, IN `inu_fk_punto` int, IN `inu_fk_ruta` int, IN `inu_promedio_minutos` int, IN `inu_holgura_minutos` int, IN `inu_valor_punto` int, IN `inu_tipo` int, IN `inu_fk_base` int, IN `ibo_estado` boolean, IN `ibo_modificacion_local` boolean, IN `isb_pk_unica` varchar(20), IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `inu_orden_proceso` int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';             
 IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          
 INSERT INTO tbl_ruta_punto( FK_PUNTO, FK_RUTA, PROMEDIO_MINUTOS, HOLGURA_MINUTOS, VALOR_PUNTO, TIPO, FK_BASE, ESTADO , MODIFICACION_LOCAL, PK_UNICA)          
 VALUES ( inu_fk_punto , inu_fk_ruta , inu_promedio_minutos , inu_holgura_minutos , inu_valor_punto, inu_tipo, inu_fk_base, ibo_estado , ibo_modificacion_local, isb_pk_unica);          
 COMMIT;       
 END IF;              
 IF inu_orden_proceso = 1       THEN          
 SET orc_retorno = 'e';          
 IF inu_pk_ruta_punto IS NOT NULL          THEN             
 UPDATE tbl_ruta_punto             
 SET     FK_PUNTO = inu_fk_punto,                      
 FK_RUTA = inu_fk_ruta,                      
 PROMEDIO_MINUTOS = inu_promedio_minutos,                     
 HOLGURA_MINUTOS = inu_holgura_minutos,                     
 VALOR_PUNTO = inu_valor_punto,                      
 TIPO = inu_tipo,                    
 FK_BASE = inu_fk_base ,                     
 ESTADO = ibo_estado ,                     
 MODIFICACION_LOCAL = ibo_modificacion_local              
 WHERE PK_RUTA_PUNTO = inu_pk_ruta_punto;             
 SET orc_retorno = inu_pk_ruta_punto;          END IF;       
 END IF;              
 
 IF inu_orden_proceso = 2       THEN          
 IF inu_pk_ruta_punto IS NOT NULL          THEN             
 DELETE FROM tbl_ruta_punto             
 WHERE PK_RUTA_PUNTO = inu_pk_ruta_punto;             
 COMMIT;             
 SET orc_retorno = '1';          
 END IF;       
 END IF;       
 
 IF inu_orden_proceso = 3       
 THEN           BLOCK_RETORNO_QUERY_BUILDER:           
 BEGIN                 
 DECLARE sb_consulta   TEXT;                 
 DECLARE sb_query_execute   varchar(100);                 
 SET orc_retorno = 'e';                 
 SET sb_consulta = "SELECT * FROM tbl_ruta_punto WHERE PK_RUTA_PUNTO > 0";                 
 IF inu_pk_ruta_punto IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND PK_RUTA_PUNTO = ");                     
 SET sb_consulta = CONCAT(sb_consulta, inu_pk_ruta_punto);                 
 END IF;                 
 IF inu_fk_punto IS NOT NULL                 
 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND FK_PUNTO = ");                     
 SET sb_consulta = CONCAT(sb_consulta, inu_fk_punto);                 
 END IF;                 
 IF inu_fk_ruta IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND FK_RUTA = ");                     
 SET sb_consulta = CONCAT(sb_consulta, inu_fk_ruta);                 
 END IF;                 
 IF inu_tipo IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND TIPO = ");                     
 SET sb_consulta = CONCAT(sb_consulta, inu_tipo);                 
 END IF;                 
 IF inu_fk_base IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND FK_BASE = ");                     
 SET sb_consulta = CONCAT(sb_consulta, inu_fk_base);                 
 END IF;                  
 IF inu_promedio_minutos IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND PROMEDIO_MINUTOS = ");                     
 SET sb_consulta = CONCAT(sb_consulta, inu_promedio_minutos);                 END IF;                
 IF inu_holgura_minutos IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND HOLGURA_MINUTOS = ");                     
 SET sb_consulta = CONCAT(sb_consulta, inu_holgura_minutos);                 END IF;                 
 IF ibo_estado IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     
 SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 
 IF ibo_modificacion_local IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
 SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  
 IF isb_pk_unica IS NOT NULL                 THEN                     
 SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
 SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
 SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 
 SET sb_consulta = CONCAT(sb_consulta, " ORDER BY TIPO,PROMEDIO_MINUTOS, FK_PUNTO");                                                             
 SET @sb_query_execute = sb_consulta;                 
 
 PREPARE stmt FROM @sb_query_execute;                 
 EXECUTE stmt;                   
 END BLOCK_RETORNO_QUERY_BUILDER;       
 END IF;                     
 
 IF inu_orden_proceso = 4       THEN            
 SET orc_retorno = 'e';           
 IF idt_sincronizacion IS NOT NULL           THEN               
 SELECT *                FROM tbl_ruta_punto               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_tarifa
DROP PROCEDURE IF EXISTS `proc_tbl_tarifa`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_tarifa`(   IN  `inu_pk_tarifa`           int,   IN  `inu_fk_ruta`             int,   IN  `inu_valor_prom_km`       double,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_tarifa ( FK_RUTA,VALOR_PROM_KM, ESTADO , MODIFICACION_LOCAL, PK_UNICA)          VALUES ( inu_fk_ruta , inu_valor_prom_km, ibo_estado , ibo_modificacion_local, isb_pk_unica);          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_tarifa IS NOT NULL          THEN             UPDATE tbl_tarifa             SET     FK_RUTA = inu_fk_ruta,                      VALOR_PROM_KM = inu_valor_prom_km,                     ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_TARIFA = inu_pk_tarifa;             SET orc_retorno = inu_pk_tarifa;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_tarifa IS NOT NULL          THEN             DELETE FROM tbl_tarifa             WHERE PK_TARIFA = inu_pk_tarifa;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_tarifa WHERE PK_TARIFA > 0";                 IF inu_pk_tarifa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_TARIFA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_tarifa);                 END IF;                 IF inu_fk_ruta IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_RUTA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_ruta);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                  SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_tarifa               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_tarifa_fija
DROP PROCEDURE IF EXISTS `proc_tbl_tarifa_fija`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_tarifa_fija`(
  IN  `inu_pk_tarifa_fija`      int,
  IN  `isb_nombre_tarifa`       varchar(20),
  IN  `inu_valor_tarifa`        double,
  IN  `ibo_estado`              boolean,
  IN  `ibo_modificacion_local`  boolean,
  IN  `isb_pk_unica`            varchar(20),
  IN  `idt_sincronizacion`      datetime,
  IN  `idt_sincronizacion_end`  datetime,
  IN  `inu_orden_proceso`       int
)
BEGIN 
      
       
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';      

       
IF inu_orden_proceso = 0       
THEN          
	SET orc_retorno = '0';          
	INSERT INTO tbl_tarifa_fija (NOMBRE_TARIFA, VALOR_TARIFA, ESTADO, MODIFICACION_LOCAL, PK_UNICA)          
	VALUES ( isb_nombre_tarifa , inu_valor_tarifa, ibo_estado , ibo_modificacion_local, isb_pk_unica);          
	COMMIT;       
END IF;       

       
IF inu_orden_proceso = 1       
THEN          
	SET orc_retorno = 'e';          
	IF inu_pk_tarifa_fija IS NOT NULL          
	THEN             
		UPDATE tbl_tarifa_fija
		SET     
			NOMBRE_TARIFA = isb_nombre_tarifa,                      
			VALOR_TARIFA = inu_valor_tarifa,                     
			ESTADO = ibo_estado ,                     
			MODIFICACION_LOCAL = ibo_modificacion_local              
		WHERE PK_TARIFA_FIJA = inu_pk_tarifa_fija;             
		SET orc_retorno = inu_pk_tarifa_fija;          
	END IF;       
END IF;       

       
IF inu_orden_proceso = 2       
THEN          
	IF inu_pk_tarifa_fija IS NOT NULL          
	THEN             
		DELETE FROM tbl_tarifa_fija
		WHERE PK_TARIFA_FIJA = inu_pk_tarifa_fija;             
		COMMIT;             
		SET orc_retorno = '1';          
	END IF;       
END IF;       


IF inu_orden_proceso = 3       
THEN           
	BLOCK_RETORNO_QUERY_BUILDER:           
	BEGIN                 
		DECLARE sb_consulta   TEXT;                 
		DECLARE sb_query_execute   varchar(100);                 
		SET orc_retorno = 'e';                 
		SET sb_consulta = "SELECT * FROM tbl_tarifa_fija WHERE PK_TARIFA_FIJA > 0";                 
		IF inu_pk_tarifa_fija IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND PK_TARIFA_FIJA = ");                     
			SET sb_consulta = CONCAT(sb_consulta, inu_pk_tarifa_fija);                 
		END IF;                 
		IF isb_nombre_tarifa IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE_TARIFA = '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_nombre_tarifa);  
			SET sb_consulta = CONCAT(sb_consulta, "'"); 			
		END IF;                 
		IF ibo_estado IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     
			SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
		END IF;                 
		IF ibo_modificacion_local IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
			SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
		END IF;                  
		IF isb_pk_unica IS NOT NULL                 
		THEN                     
			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
			SET sb_consulta = CONCAT(sb_consulta, "'");                 
		END IF;                  
		SET @sb_query_execute = sb_consulta;                 
		PREPARE stmt FROM @sb_query_execute;                 
	EXECUTE stmt;                   
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;       

       
IF inu_orden_proceso = 4       
THEN            
	SET orc_retorno = 'e';           
	IF idt_sincronizacion IS NOT NULL           
	THEN               
		SELECT *                
		FROM tbl_tarifa_fija              
		WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     
		AND MODIFICACION_LOCAL = 1;           
	END IF;       
END IF;       

       
IF inu_orden_proceso <> 4       
THEN          
	SELECT orc_retorno;       
END IF;    
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_tarifa_km
DROP PROCEDURE IF EXISTS `proc_tbl_tarifa_km`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_tarifa_km`(   IN  `inu_pk_tarifa_km`                 int,   IN  `inu_fk_informacion_registradora`  int,   IN  `inu_kilometro`                    int,   IN  `inu_entradas`                     int,   IN  `inu_salidas`                      int,   IN  `ibo_estado`                       boolean,   IN  `ibo_modificacion_local`           boolean,   IN  `isb_pk_unica`                     varchar(20),   IN  `idt_sincronizacion`               datetime,   IN  `idt_sincronizacion_end`  		 datetime,   IN  `inu_orden_proceso`                int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_tarifa_km (FK_INFORMACION_REGISTRADORA, KILOMETRO, ENTRADAS, SALIDAS,ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES (inu_fk_informacion_registradora, inu_kilometro, inu_entradas, inu_salidas, ibo_estado, ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_tarifa_km IS NOT NULL          THEN             UPDATE tbl_tarifa_km             SET   FK_INFORMACION_REGISTRADORA = inu_fk_informacion_registradora,                    KILOMETRO = inu_kilometro,                    ENTRADAS = inu_entradas,                    SALIDAS = inu_salidas,                   ESTADO = ibo_estado,                    MODIFICACION_LOCAL = ibo_modificacion_local               WHERE PK_TARIFA_KM = inu_pk_tarifa_km;             SET orc_retorno = inu_pk_tarifa_km;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_tarifa_km IS NOT NULL          THEN             DELETE FROM tbl_tarifa_km             WHERE PK_TARIFA_KM = inu_pk_tarifa_km;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_tarifa_km WHERE PK_TARIFA_KM > 0";                 IF inu_pk_tarifa_km IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_TARIFA_KM = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_tarifa_km);                 END IF;                 IF inu_fk_informacion_registradora IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_INFORMACION_REGISTRADORA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_informacion_registradora);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                  IF inu_entradas IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ENTRADAS = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_entradas);                 END IF;                 IF inu_salidas IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND SALIDAS = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_salidas);                 END IF;                 IF inu_kilometro IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND KILOMETRO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_kilometro);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_tarifa_km               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_usuario
DROP PROCEDURE IF EXISTS `proc_tbl_usuario`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_usuario`(   IN  `inu_pk_usuario`          int,   IN  `inu_fk_perfil`           int,   IN  `inu_cedula`              int,   IN  `isb_nombre`              varchar(50),   IN  `isb_apellido`            varchar(50),   IN  `isb_email`               varchar(50),   IN  `isb_login`               varchar(50),   IN  `isb_contrasena`          varchar(50),   IN  `ibo_estado`              boolean,   IN  `inu_tipo`                int,   IN  `ibo_estado_conexion`     boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN        	        	DECLARE orc_retorno    	VARCHAR(20) DEFAULT '-1';        	        	IF inu_orden_proceso = 0       	 THEN           		SET orc_retorno = '0';           		INSERT INTO tbl_usuario( FK_PERFIL, CEDULA, NOMBRE, APELLIDO, EMAIL, LOGIN, CONTRASENA, ESTADO, TIPO, USUARIOBD, ESTADO_CONEXION, MODIFICACION_LOCAL, PK_UNICA )           		VALUES ( inu_fk_perfil , inu_cedula , isb_nombre , isb_apellido , isb_email , isb_login , isb_contrasena , ibo_estado, inu_tipo, CURRENT_USER(), 0 , ibo_modificacion_local, isb_pk_unica);           		COMMIT;        	END IF;        	         	 IF inu_orden_proceso = 1       	 THEN           		 SET orc_retorno = 'e';          		 IF inu_pk_usuario IS NOT NULL           		 THEN              			 UPDATE tbl_usuario              			 SET      			 FK_PERFIL = inu_fk_perfil ,                      			 CEDULA = inu_cedula ,                      			 NOMBRE = isb_nombre ,                     			 APELLIDO = isb_apellido ,                      			 EMAIL = isb_email ,                     			 LOGIN = isb_login ,                      			 CONTRASENA = isb_contrasena ,                     			 ESTADO_CONEXION = ibo_estado_conexion ,                      			 USUARIOBD = CURRENT_USER() ,                      			 ESTADO = ibo_estado ,                      			 MODIFICACION_LOCAL = ibo_modificacion_local              			 WHERE PK_USUARIO = inu_pk_usuario;              			 SET orc_retorno = inu_pk_usuario;           		 END IF;        	 END IF; 	         	 IF inu_orden_proceso = 2        	 THEN           		IF inu_pk_usuario IS NOT NULL           		THEN              			 DELETE FROM tbl_usuario              			 WHERE PK_USUARIO = inu_pk_usuario;              			 COMMIT;              			SET orc_retorno = '1';           		END IF;        	END IF;  	 IF inu_orden_proceso = 3        	 THEN            		 BLOCK_RETORNO_QUERY_BUILDER:            		 BEGIN                  		 DECLARE sb_consulta   TEXT;                  		 DECLARE sb_query_execute   varchar(100);                  		 SET orc_retorno = 'e';                  		 SET sb_consulta = "SELECT * FROM tbl_usuario WHERE PK_USUARIO > 0";                  		 IF inu_pk_usuario IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND PK_USUARIO = ");                      			SET sb_consulta = CONCAT(sb_consulta, inu_pk_usuario);                  		 END IF;                  		 IF inu_fk_perfil IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND  FK_PERFIL = ");                      			SET sb_consulta = CONCAT(sb_consulta, inu_fk_perfil);                  		 END IF;                  		 IF inu_cedula IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND CEDULA = ");                      			SET sb_consulta = CONCAT(sb_consulta, inu_cedula);                  		 END IF;                  		 IF isb_nombre IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE like '");                      			SET sb_consulta = CONCAT(sb_consulta, isb_nombre);                      			SET sb_consulta = CONCAT(sb_consulta, "'");                  		 END IF;                  		 IF isb_apellido IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND APELLIDO like '");                      			SET sb_consulta = CONCAT(sb_consulta, isb_apellido);                      			SET sb_consulta = CONCAT(sb_consulta, "'");                  		 END IF;                  		 IF isb_email IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND EMAIL like '");                      			SET sb_consulta = CONCAT(sb_consulta, isb_email);                      			SET sb_consulta = CONCAT(sb_consulta, "'");                  		 END IF;                  		 IF isb_login IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND LOGIN like '");                      			SET sb_consulta = CONCAT(sb_consulta, isb_login);                      			SET sb_consulta = CONCAT(sb_consulta, "'");                  		 END IF;                  		 IF isb_contrasena IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND CONTRASENA like '");                      			SET sb_consulta = CONCAT(sb_consulta, isb_contrasena);                      			SET sb_consulta = CONCAT(sb_consulta, "'");                  		 END IF;                  		 IF ibo_estado IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                      			SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                  		 END IF;                  		 IF inu_tipo IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND TIPO = ");                      			SET sb_consulta = CONCAT(sb_consulta, inu_tipo);                  		 END IF;                  		 IF ibo_estado_conexion IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO_CONEXION = ");                      			SET sb_consulta = CONCAT(sb_consulta, ibo_estado_conexion);                  		 END IF;                  		 IF ibo_modificacion_local IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                      			SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                  		 END IF;                   		 IF isb_pk_unica IS NOT NULL                  		 THEN                      			SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                      			SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                      			SET sb_consulta = CONCAT(sb_consulta, "'");                  		 END IF;                  		 SET @sb_query_execute = sb_consulta;                  		 PREPARE stmt FROM @sb_query_execute;                  		 EXECUTE stmt;                    		 END BLOCK_RETORNO_QUERY_BUILDER;        	 END IF;                  IF inu_orden_proceso = 4         THEN             	SET orc_retorno = 'e';            	IF idt_sincronizacion IS NOT NULL            	 THEN                		 SELECT *                 		 FROM tbl_usuario                		 WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)  		 AND MODIFICACION_LOCAL = 1;            	 END IF;         END IF;                  IF inu_orden_proceso <> 4         THEN            SELECT orc_retorno;         END IF;      END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_usuario_email
DROP PROCEDURE IF EXISTS `proc_tbl_usuario_email`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_usuario_email`(   IN  `inu_pk_usuario_email`    int,   IN  `isb_nombre_completo`     varchar(100),   IN  `isb_email`               varchar(100),   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_usuario_email( NOMBRE_COMPLETO, EMAIL, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( isb_nombre_completo, isb_email, ibo_estado, ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_usuario_email IS NOT NULL          THEN             UPDATE tbl_usuario_email             SET     NOMBRE_COMPLETO = isb_nombre_completo ,                     EMAIL = isb_email,                     ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_USUARIO_EMAIL = inu_pk_usuario_email;             SET orc_retorno = inu_pk_usuario_email;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_usuario_email IS NOT NULL          THEN             DELETE FROM tbl_usuario_email             WHERE PK_USUARIO_EMAIL = inu_pk_usuario_email;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_usuario_email WHERE PK_USUARIO_EMAIL > 0";                 IF inu_pk_usuario_email IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_USUARIO_EMAIL = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_usuario_email);                 END IF;                 IF isb_nombre_completo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND NOMBRE_COMPLETO like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_nombre_completo);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF isb_email IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND EMAIL like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_email);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_usuario_email              WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_usuario_permiso_empresa
DROP PROCEDURE IF EXISTS `proc_tbl_usuario_permiso_empresa`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_usuario_permiso_empresa`(   IN  `inu_pk_usuario_permiso_empresa`  int,   IN  `inu_fk_empresa`                  int,   IN  `inu_fk_usuario`                  int,   IN  `ibo_estado`                      boolean,   IN  `ibo_modificacion_local`          boolean,   IN  `isb_pk_unica`                    varchar(20),   IN  `idt_sincronizacion`              datetime,   IN  `idt_sincronizacion_end`  		datetime,   IN  `inu_orden_proceso`               int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_usuario_permiso_empresa( FK_EMPRESA, FK_USUARIO, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( inu_fk_empresa, inu_fk_usuario, ibo_estado ,ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_usuario_permiso_empresa IS NOT NULL          THEN             UPDATE tbl_usuario_permiso_empresa             SET     FK_EMPRESA = inu_fk_empresa ,                     FK_USUARIO = inu_fk_usuario ,                     ESTADO = ibo_estado ,                      MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_USUARIO_PERMISO_EMPRESA = inu_pk_usuario_permiso_empresa;             SET orc_retorno = inu_pk_usuario_permiso_empresa;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_usuario_permiso_empresa IS NOT NULL          THEN             DELETE FROM tbl_usuario_permiso_empresa             WHERE PK_USUARIO_PERMISO_EMPRESA = inu_pk_usuario_permiso_empresa;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_usuario_permiso_empresa WHERE PK_USUARIO_PERMISO_EMPRESA > 0";                 IF inu_pk_usuario_permiso_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_USUARIO_PERMISO_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_usuario_permiso_empresa);                 END IF;                 IF inu_fk_empresa IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND  FK_EMPRESA = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_empresa);                 END IF;                 IF inu_fk_usuario IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_USUARIO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_usuario);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                  IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                 IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_usuario_permiso_empresa               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_vehiculo
DROP PROCEDURE IF EXISTS `proc_tbl_vehiculo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_vehiculo`(IN `inu_pk_vehiculo` int, IN `inu_fk_empresa` int, IN `isb_placa` varchar(50), IN `isb_num_interno` varchar(50), IN `ibo_estado` boolean, IN `ibo_modificacion_local` boolean, IN `isb_pk_unica` varchar(20), IN `idt_sincronizacion` datetime, IN `idt_sincronizacion_end` datetime, IN `inu_orden_proceso` int )
BEGIN              
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              
IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          
INSERT INTO tbl_vehiculo( FK_EMPRESA, PLACA, NUM_INTERNO, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          
VALUES ( inu_fk_empresa, isb_placa, isb_num_interno,  ibo_estado, ibo_modificacion_local, isb_pk_unica );          
COMMIT;       END IF;              
IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          
IF inu_pk_vehiculo IS NOT NULL          THEN             
UPDATE tbl_vehiculo             
SET     FK_EMPRESA = inu_fk_empresa ,                     
PLACA = isb_placa ,                     
NUM_INTERNO = isb_num_interno,                     
ESTADO = ibo_estado ,                     
MODIFICACION_LOCAL = ibo_modificacion_local              
WHERE PK_VEHICULO = inu_pk_vehiculo;     


UPDATE tbl_vehiculos_perimetro
SET ESTADO = ibo_estado   
WHERE FK_VEHICULO = inu_pk_vehiculo;  
        
SET orc_retorno = inu_pk_vehiculo;          
END IF;       
END IF;    
          
IF inu_orden_proceso = 2       THEN          
IF inu_pk_vehiculo IS NOT NULL          THEN             
DELETE FROM tbl_vehiculo             
WHERE PK_VEHICULO = inu_pk_vehiculo;   

DELETE FROM tbl_vehiculos_perimetro             
WHERE FK_VEHICULO = inu_pk_vehiculo; 
          
COMMIT;             
SET orc_retorno = '1';          
END IF;       
END IF;       

IF inu_orden_proceso = 3       THEN           
	BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 
	DECLARE sb_consulta   TEXT;                 
	DECLARE sb_query_execute   varchar(100);                 
	SET orc_retorno = 'e';                 
	SET sb_consulta = "SELECT * FROM tbl_vehiculo use index(primary) WHERE PK_VEHICULO > 0";                 
	IF inu_pk_vehiculo IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_VEHICULO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_pk_vehiculo);                 
	END IF;                 
	IF inu_fk_empresa IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND  FK_EMPRESA = ");                     
		SET sb_consulta = CONCAT(sb_consulta, inu_fk_empresa);                 
	END IF;                 
	IF isb_placa IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PLACA like '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_placa);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;                 
	IF isb_num_interno IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND NUM_INTERNO like '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_num_interno);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;                 
	IF ibo_estado IS NOT NULL                
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 
	END IF;                 
	IF ibo_modificacion_local IS NOT NULL                 
	THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     
		SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 
	END IF;                  
	IF isb_pk_unica IS NOT NULL                 THEN                     
		SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     
		SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     
		SET sb_consulta = CONCAT(sb_consulta, "'");                 
	END IF;                 

	SET sb_consulta = CONCAT(sb_consulta, " ORDER BY PLACA, NUM_INTERNO"); 

	SET @sb_query_execute = sb_consulta;                 
	PREPARE stmt FROM @sb_query_execute;                 
	EXECUTE stmt;                   
	END BLOCK_RETORNO_QUERY_BUILDER;       
END IF;              

IF inu_orden_proceso = 4       THEN            
SET orc_retorno = 'e';           
IF idt_sincronizacion IS NOT NULL           THEN               
SELECT *                FROM tbl_vehiculo               
WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     
AND MODIFICACION_LOCAL = 1;           
END IF;       
END IF;              

IF inu_orden_proceso <> 4       THEN          
SELECT orc_retorno;       END IF;    
END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_vehiculos_perimetro
DROP PROCEDURE IF EXISTS `proc_tbl_vehiculos_perimetro`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_vehiculos_perimetro`(   IN  `inu_pk_vehiculos_perimetro`  int,   IN  `inu_fk_vehiculo`             int,   IN  `inu_fk_base`                 int,   IN  `isb_ruta_asignada`           varchar(50),   IN  `idt_hora_llegada`            varchar(30),   IN  `idt_hora_salida`             varchar(30),   IN  `ibo_estado`                  boolean,   IN  `ibo_modificacion_local`      boolean,   IN  `idt_sincronizacion`          datetime,   IN  `idt_sincronizacion_end`  	datetime,   IN  `inu_orden_proceso`           int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_vehiculos_perimetro( FK_VEHICULO, FK_BASE, RUTA_ASIGNADA, HORA_LLEGADA, HORA_SALIDA, ESTADO, MODIFICACION_LOCAL )          VALUES ( inu_fk_vehiculo, inu_fk_base , isb_ruta_asignada , idt_hora_llegada  , idt_hora_salida , ibo_estado , ibo_modificacion_local );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_vehiculos_perimetro IS NOT NULL          THEN             UPDATE tbl_vehiculos_perimetro             SET     FK_VEHICULO = inu_fk_vehiculo,                      FK_BASE = inu_fk_base,                      RUTA_ASIGNADA = isb_ruta_asignada,                      HORA_LLEGADA = idt_hora_llegada,                      HORA_SALIDA = idt_hora_salida,                      ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_VEHICULOS_PERIMETRO = inu_pk_vehiculos_perimetro;             SET orc_retorno = inu_pk_vehiculos_perimetro;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_vehiculos_perimetro IS NOT NULL          THEN             DELETE FROM tbl_vehiculos_perimetro             WHERE PK_VEHICULOS_PERIMETRO = inu_pk_vehiculos_perimetro;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_vehiculos_perimetro WHERE PK_VEHICULOS_PERIMETRO > 0";                 IF inu_pk_vehiculos_perimetro IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_VEHICULOS_PERIMETRO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_vehiculos_perimetro);                 END IF;                 IF inu_fk_vehiculo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_VEHICULO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_vehiculo);                 END IF;                 IF inu_fk_base IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_BASE = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_base);                 END IF;                 IF isb_ruta_asignada IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND RUTA_ASIGNADA like '");                     SET sb_consulta = CONCAT(sb_consulta, isb_ruta_asignada);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF idt_hora_llegada IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND HORA_LLEGADA like '");                     SET sb_consulta = CONCAT(sb_consulta, idt_hora_llegada);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF idt_hora_salida IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND HORA_SALIDA like '");                     SET sb_consulta = CONCAT(sb_consulta, idt_hora_salida);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_vehiculos_perimetro               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_version_puntos
DROP PROCEDURE IF EXISTS `proc_tbl_version_puntos`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_version_puntos`(   IN  `inu_pk_version_puntos`   int,   IN  `inu_version_actual`      int,   IN  `ibo_estado`              boolean,   IN  `ibo_modificacion_local`  boolean,   IN  `isb_pk_unica`            varchar(20),   IN  `idt_sincronizacion`      datetime,   IN  `idt_sincronizacion_end`  datetime,   IN  `inu_orden_proceso`       int,   IN  `inu_limit`               int )
BEGIN              DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          INSERT INTO tbl_version_puntos( VERSION_ACTUAL, ESTADO, MODIFICACION_LOCAL, PK_UNICA )          VALUES ( inu_version_actual , ibo_estado , ibo_modificacion_local, isb_pk_unica );          COMMIT;       END IF;              IF inu_orden_proceso = 1       THEN          SET orc_retorno = 'e';          IF inu_pk_version_puntos IS NOT NULL          THEN             UPDATE tbl_version_puntos             SET     VERSION_ACTUAL = inu_version_actual,                     ESTADO = ibo_estado ,                     MODIFICACION_LOCAL = ibo_modificacion_local              WHERE PK_VERSION_PUNTOS = inu_pk_version_puntos;             SET orc_retorno = inu_pk_version_puntos;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_version_puntos IS NOT NULL          THEN             DELETE FROM tbl_version_puntos             WHERE PK_VERSION_PUNTOS = inu_pk_version_puntos;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_version_puntos WHERE PK_VERSION_PUNTOS > 0";                 IF inu_pk_version_puntos IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_VERSION_PUNTOS = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_version_puntos);                 END IF;                 IF inu_version_actual IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND VERSION_ACTUAL = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_version_actual);                 END IF;                            IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF;                 IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                 IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 IF inu_limit IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " ORDER BY PK_VERSION_PUNTOS DESC LIMIT ");                     SET sb_consulta = CONCAT(sb_consulta, inu_limit);                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_version_puntos               WHERE (FECHA_MODIFICACION BETWEEN idt_sincronizacion AND idt_sincronizacion_end)                     AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 4       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para procedimiento bd_coomotorneiva_rdtow1.proc_tbl_version_puntos_agrupacion
DROP PROCEDURE IF EXISTS `proc_tbl_version_puntos_agrupacion`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_tbl_version_puntos_agrupacion`(IN inu_pk_version_puntos_agrupacion INT, IN inu_fk_agrupacion INT, IN inu_version_grupo INT, IN ibo_estado BOOLEAN, IN ibo_modificacion_local BOOLEAN, IN isb_pk_unica VARCHAR(20), IN inu_orden_proceso INT)
BEGIN              
DECLARE orc_retorno   VARCHAR(20) DEFAULT '-1';              
IF inu_orden_proceso = 0       THEN          SET orc_retorno = '0';          
INSERT INTO tbl_version_puntos_agrupacion( FK_AGRUPACION, VERSION_GRUPO, ESTADO, MODIFICACION_LOCAL, PK_UNICA)          
VALUES ( inu_fk_agrupacion, inu_version_grupo, ibo_estado, ibo_modificacion_local, isb_pk_unica);          
COMMIT;       END IF;              

IF inu_orden_proceso = 1       THEN          
SET orc_retorno = 'e';          
IF inu_pk_version_puntos_agrupacion IS NOT NULL          
THEN             UPDATE tbl_version_puntos_agrupacion             
SET    FK_AGRUPACION = inu_fk_agrupacion ,                    VERSION_GRUPO = inu_version_grupo ,                    
ESTADO = ibo_estado, 				   
MODIFICACION_LOCAL = ibo_modificacion_local , 				   
PK_UNICA = isb_pk_unica             
WHERE PK_VERSION_PUNTOS_AGRUPACION = inu_pk_version_puntos_agrupacion;             
SET orc_retorno = inu_pk_version_puntos_agrupacion;          END IF;       END IF;              IF inu_orden_proceso = 2       THEN          IF inu_pk_version_puntos_agrupacion IS NOT NULL          THEN             DELETE FROM tbl_version_puntos_agrupacion             WHERE PK_VERSION_PUNTOS_AGRUPACION = inu_pk_version_puntos_agrupacion;             COMMIT;             SET orc_retorno = '1';          END IF;       END IF;       IF inu_orden_proceso = 3       THEN           BLOCK_RETORNO_QUERY_BUILDER:           BEGIN                 DECLARE sb_consulta   TEXT;                 DECLARE sb_query_execute   varchar(100);                 SET orc_retorno = 'e';                 SET sb_consulta = "SELECT * FROM tbl_version_puntos_agrupacion WHERE PK_VERSION_PUNTOS_AGRUPACION > 0";                 IF inu_pk_version_puntos_agrupacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_VERSION_PUNTOS_AGRUPACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_pk_version_puntos_agrupacion);                 END IF;                 IF inu_version_grupo IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND VERSION_GRUPO = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_version_grupo);                 END IF;                 IF inu_fk_agrupacion IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND FK_AGRUPACION = ");                     SET sb_consulta = CONCAT(sb_consulta, inu_fk_agrupacion);                 END IF;                 IF ibo_estado IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND ESTADO = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_estado);                 END IF; 				IF ibo_modificacion_local IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND MODIFICACION_LOCAL = ");                     SET sb_consulta = CONCAT(sb_consulta, ibo_modificacion_local);                 END IF;                  IF isb_pk_unica IS NOT NULL                 THEN                     SET sb_consulta = CONCAT(sb_consulta, " AND PK_UNICA = '");                     SET sb_consulta = CONCAT(sb_consulta, isb_pk_unica);                     SET sb_consulta = CONCAT(sb_consulta, "'");                 END IF;                 SET @sb_query_execute = sb_consulta;                 PREPARE stmt FROM @sb_query_execute;                 EXECUTE stmt;                   END BLOCK_RETORNO_QUERY_BUILDER;       END IF;              IF inu_orden_proceso = 4       THEN            SET orc_retorno = 'e';           IF idt_sincronizacion IS NOT NULL           THEN               SELECT *                FROM tbl_version_puntos_agrupacion               WHERE FECHA_MODIFICACION > idt_sincronizacion                      AND MODIFICACION_LOCAL = 1;           END IF;       END IF;              IF inu_orden_proceso <> 6       THEN          SELECT orc_retorno;       END IF;    END//
DELIMITER ;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_acceso
DROP TABLE IF EXISTS `tbl_acceso`;
CREATE TABLE IF NOT EXISTS `tbl_acceso` (
  `PK_ACCESO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_GRUPO` int(11) DEFAULT NULL,
  `NOMBRE_ACCESO` varchar(50) DEFAULT NULL,
  `CODIGO_ACCESO` varchar(50) DEFAULT NULL,
  `NOMBRE_LARGO` varchar(200) DEFAULT NULL,
  `TECLA_ACCESO` varchar(50) DEFAULT NULL,
  `UBICACION` varchar(80) DEFAULT NULL,
  `Imagen` varchar(100) DEFAULT NULL,
  `SUBGRUPO` varchar(100) DEFAULT NULL,
  `POSICION` int(11) DEFAULT NULL,
  `POSICIONSUBGRUPO` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_ACCESO`),
  KEY `FK_GRUPO_ACCESO` (`FK_GRUPO`),
  CONSTRAINT `FK_GRUPO_ACCESO` FOREIGN KEY (`FK_GRUPO`) REFERENCES `tbl_grupo` (`PK_GRUPO`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_acceso: ~89 rows (aproximadamente)
DELETE FROM `tbl_acceso`;
/*!40000 ALTER TABLE `tbl_acceso` DISABLE KEYS */;
INSERT INTO `tbl_acceso` (`PK_ACCESO`, `FK_GRUPO`, `NOMBRE_ACCESO`, `CODIGO_ACCESO`, `NOMBRE_LARGO`, `TECLA_ACCESO`, `UBICACION`, `Imagen`, `SUBGRUPO`, `POSICION`, `POSICIONSUBGRUPO`, `ESTADO`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`, `PK_UNICA`) VALUES
	(1, 2, 'Grupos', 'CGP', 'Administracion de Grupos', 'F', '', 'icons/menu.png', 'Sistema', 1, 3, 1, 1, '2011-05-21 02:05:00', '1-R5bOXEIvSgdcT97'),
	(2, 2, 'Perfiles', 'CPFL', 'Administracion de Perfiles', 'P', '', 'icons/perfil.png', 'Perfil', 1, 1, 1, 1, '2011-06-18 02:50:58', '1-ueuFMT6zKyyehgH'),
	(3, 2, 'Usuario', 'CUSER', 'Administracion de Usuarios', 'U', '', 'icons/user.png', 'Cuentas', 1, 2, 1, 1, '2011-06-18 02:50:58', '1-blWIopOvqfPVRHK'),
	(4, 3, 'Ventana Comunicacion', 'CPRTO', 'Ventana Comunicacion WIFI', '', '', 'icons/serial.png', 'WIFI', 1, 1, 1, 1, '2014-12-02 18:40:28', '1-Nn9J5FmZbzHVM21'),
	(6, 2, 'Funciones', 'CFN', 'Administracion de Funciones del Sistema', 'F', '', 'icons/function.png', 'Sistema', 2, 3, 1, 1, '2011-05-21 02:05:53', '1-6kREvPp96SyTWib'),
	(10, 1, 'Vehiculo', 'CVEH', 'Administracion de Vehiculo', 'v', '', 'icons/bus.png', 'Vehiculo', 1, 2, 1, 1, '2011-06-09 03:08:52', '1-cPJKEKchYH9KkZF'),
	(11, 1, 'Empresa', 'CEMP', 'Administracion de Empresa', 'e', '', 'icons/bussines.png', 'Empresa', 1, 1, 1, 1, '2011-04-04 18:33:08', '1-so0Wc0OYj409U3M'),
	(12, 1, 'Conductor', 'CCDT', 'Administracion de Conductores', 'c', '', 'icons/driver.png', 'Empresa', 3, 1, 1, 1, '2011-06-18 03:49:35', '1-HtMFMyggIe5yqrS'),
	(13, 1, 'Punto', 'CPTO', 'Administracion de Puntos', 'a', '', 'icons/punto.png', 'Ruta', 1, 3, 1, 1, '2011-06-09 03:20:17', '1-mls78cNPkJ7BVUe'),
	(14, 1, 'Ruta', 'CRUTA', 'Administracion de Ruta', '', '', 'icons/route.png', 'Ruta', 2, 3, 1, 1, '2011-06-09 03:20:17', '1-YsNznPdPHW5Z9Fg'),
	(15, 1, 'Crear Ruta', 'CRP', 'Creacion de una Ruta', '', '', 'icons/crearRuta.png', 'Ruta', 3, 3, 1, 1, '2011-06-09 03:20:17', '1-e3l3Gu8TvQYcdK7'),
	(16, 1, 'Base', 'CBASE', 'Administracion de Bases', '', '', 'icons/base.png', 'Empresa', 2, 1, 1, 1, '2011-04-02 02:05:32', '1-SbfOuEpQidUKJ9j'),
	(17, 1, 'Alarmas Vehiculo', 'CALR', 'Administracion de Alarmas', '', '', 'icons/alarm.png', 'Alarmas', 1, 5, 1, 1, '2011-06-09 03:21:25', '1-eYoHqaBj81LNM31'),
	(18, 1, 'Eventos Software', 'CEVT', 'Administracion de Eventos', '', '', 'icons/event.png', 'Eventos', 2, 4, 1, 1, '2011-06-09 03:21:01', '1-VgywMppyXAUautU'),
	(19, 1, 'Usuario Email', 'CUE', 'Administracion Usuarios Destinatarios', '', '', 'icons/user_mail.png', 'Eventos', 1, 4, 1, 1, '2011-06-09 03:21:01', '1-N3dRsBuysaEZpU9'),
	(20, 1, 'Vehiculo - Conductor', 'AVC', 'Asignacion Vehiculo - Conductor', '', '', 'icons/dirver_bus.png', 'Empresa', 4, 1, 1, 1, '2011-06-18 03:49:35', '1-dsxAQp8mJxKUGSO'),
	(21, 4, 'Conductor', 'ACDT', 'Registro de Auditorias sobre Conductor', 't', '', 'icons/audit_conductor.png', 'Auditorias', 1, 1, 1, 1, '2011-04-08 02:50:04', '1-6FNVLkrsTo5BD4V'),
	(22, 4, 'Vehiculo', 'AVEH', 'Registro de Auditorias sobre Vehiculo', 'a', '', 'icons/audit_vehiculo.png', 'Auditorias', 2, 2, 1, 1, '2011-06-09 03:09:39', '1-vxBLNN6ABvn6V8Y'),
	(23, 4, 'Empresa', 'AEMP', 'Registro de Auditorias sobre Empresa', 'a', '', 'icons/audit_empresa.png', 'Auditorias', 3, 1, 1, 1, '2011-04-08 20:34:45', '1-axkamh7ZpyLRxua'),
	(24, 4, 'Ruta', 'ARUTA', 'Registro de Auditorias sobre Ruta', 'a', '', 'icons/audit_ruta.png', 'Auditorias', 4, 1, 1, 1, '2011-04-08 20:39:03', '1-C7Ydlw3j3XScL7e'),
	(25, 4, 'Usuario', 'AUSER', 'Registro de Auditorias sobre Usuario', 'a', '', 'icons/audit_usuario.png', 'Auditorias', 5, 1, 1, 1, '2011-04-08 20:44:43', '1-oecQUJDZdmfYqJS'),
	(26, 4, 'Perfil', 'APFL', 'Registros de Auditoria sobre Perfil', 'a', '', 'icons/audit_perfil.png', 'Auditorias', 6, 1, 1, 1, '2011-04-08 20:47:28', '1-YeAhFJ7a18PuMis'),
	(27, 4, 'Punto', 'APTO', 'Registros de Auditoria sobre Punto', 'a', '', 'icons/audit_punto.png', 'Auditorias', 7, 1, 1, 1, '2011-04-08 20:48:41', '1-kGfTW8ova3sQW9j'),
	(28, 4, 'Alarma', 'AALR', 'Registros de Auditoria sobre Alarma', 'a', '', 'icons/audit_alarma.png', 'Auditorias', 8, 1, 1, 1, '2011-04-08 20:54:30', '1-wxMjgqW91RAjo40'),
	(30, 1, 'Tarifa', 'CTRF', 'Creacion de una Tarifa para una Ruta', 'a', '', 'icons/tarifa.png', 'Ruta', 6, 3, 1, 1, '2011-06-09 03:20:17', '1-Le6VKRrqwzdwDnr'),
	(31, 1, 'Macro Ruta', 'CMR', 'Administracion de las Macro Rutas', 'l', '', 'icons/macro_ruta.png', 'Ruta', 4, 3, 1, 1, '2011-06-09 03:20:17', '1-hCeYWgHpfgqEk6a'),
	(32, 1, 'Crear Macro Ruta', 'CCMR', 'Creacion de Macro Ruta', 's', '', 'icons/crear_macro_ruta.png', 'Ruta', 5, 3, 1, 1, '2011-06-09 03:20:17', '1-GI8OAzXRNPvMsld'),
	(33, 5, 'Informacion Registradora', 'CIR', 'Consultar Informacion Registradora', 'c', '', 'icons/inforegis.png', 'Consulta', 1, 1, 1, 1, '2011-05-09 22:14:03', '1-qr3KbTFyevo1192'),
	(34, 5, 'Editar Registro Vuelta', 'EIR', 'Editar un Registro de Informacion Registradora', 'a', '', 'icons/inforegis_edit.png', 'Editar', 1, 2, 1, 1, '2011-05-21 01:23:21', '1-OstVApPPBM8nayn'),
	(35, 4, 'Informacion Registradora', 'AIR', 'Auditoria de Registros de Informacion Registradora', 'a', '', 'icons/audit_info_regis.png', 'Auditorias', 9, 1, 1, 1, '2011-05-21 20:52:24', '1-3PilYVHcBPIQddn'),
	(36, 6, 'Configuracion', 'CPE', 'Configuracion Perimetro', '', '', 'icons/config_perimetro.png', 'Configuracion', 1, 1, 1, 1, '2011-06-08 18:59:35', '1-tikFhuJmYuOCvSe'),
	(37, 1, 'Grupos', 'AGPV', 'Administracion de Grupos de Vehiculos', '', '', 'icons/grupo.png', 'Vehiculo', 2, 2, 1, 1, '2011-06-09 03:09:23', '1-wqqJdBMupeRhwZT'),
	(38, 1, 'Grupo de Vehiculos', 'CGV', 'Creacion de Grupos de Vehiculos', '', '', 'icons/grupo_bus.png', 'Vehiculo', 3, 2, 1, 1, '2011-06-09 22:00:03', '1-l21xXgXSUnUtHey'),
	(39, 1, 'Prestamo de Vehiculos', 'PVH', 'Prestamos de Vehiculos de Empresas', '', '', 'icons/prestamo.png', 'Vehiculo', 5, 2, 1, 1, '2011-06-14 02:14:39', '1-v9pUnKCPlmlQIHX'),
	(40, 1, 'Grupos Permitidos', 'AGP', 'Grupo de Vehiculos Permitidos', '', '', 'icons/grupo_permitido.png', 'Vehiculo', 4, 2, 1, 1, '2011-06-14 02:14:25', '1-6ziMrM0P7lJIGk4'),
	(41, 5, 'Ordenar Vueltas', 'OVC', 'Ordenar Vueltas Cronologicamente', '', '', 'icons/inforegis_ordenar.png', 'Opciones', 1, 3, 1, 1, '2011-06-14 21:01:07', '1-S2z7wu1PNitt8Uz'),
	(42, 6, 'Visualizador Perimetro', 'VPE', 'Visualizador de Perimetro', '', '', 'icons/perimetro.png', 'Visualizacion', 1, 2, 1, 1, '2011-06-16 19:38:07', '1-kx4IF3nnLoRsuCK'),
	(43, 5, 'Unir Vueltas', 'UVIR', 'Unir Vueltas de Informacion Registradora', '', '', 'icons/inforegis_unir.png', 'Opciones', 2, 3, 1, 1, '2011-06-17 03:10:13', '1-2kQpJ0UkNp2eySw'),
	(44, 5, 'Desunir Vueltas', 'DVIR', 'Desunir Vueltas de Informacion Registradora', '', '', 'icons/inforegis_desunir.png', 'Opciones', 3, 3, 1, 1, '2011-06-17 03:11:34', '1-uMvb84mwnhWCzRr'),
	(45, 11, 'Ordenar Menu', 'ORDM', 'Ordenar Distribuicion del Menu Principal', '', '', 'icons/menu_orden1.png', 'Ordenar Menu', 1, 1, 1, 1, '2011-06-18 18:44:52', '1-G9vmT6euh1KbPUb'),
	(46, 11, 'Ordenar Submenues', 'ORDSM', 'Ordenar Distribucion de los Submenues', '', '', 'icons/menu_orden2.png', 'Ordenar Menu', 2, 1, 1, 1, '2011-06-18 18:46:56', '1-VEEMCcuaTQPMxfy'),
	(47, 11, 'Ordenar Funciones', 'ORDF', 'Ordenar Disposicion de las Funciones', '', '', 'icons/menu_orden3.png', 'Ordenar Menu', 3, 1, 1, 1, '2011-06-18 18:47:52', '1-ojw7L5eldXEg5Us'),
	(48, 11, 'Permiso de Acople', 'CACP', 'Creacion de un Permiso para Acceder al Acople', '', '', 'icons/acople.png', 'Acople', 1, 4, 1, 1, '2011-06-21 00:16:03', '1-jo999L7bDAvrOee'),
	(49, 11, 'Columnas Pantalla Externa', 'CCPE', 'Seleccion de Columnas para la Pantalla Externa', '', '', 'icons/pantalla_externa.png', 'Pantalla Externa', 1, 3, 1, 1, '2011-06-23 20:23:48', '1-JWKIbJuxLKDTEfn'),
	(50, 8, 'Puntos de Control', 'RPTPC', 'Reporte de Puntos con Control por Vehiculo en Rango', '', '', 'icons/reporte_puntos_control.png', 'Por Vehiculo', 1, 1, 1, 1, '2011-07-29 00:55:07', '1-pfd1RPem0LYs9Tp'),
	(51, 8, 'Produccion', 'RPTPV', 'Reporte Consolidado Produccion Vehiculo', '', '', 'icons/reporte_produccion_vehiculo.png', 'Por Vehiculo', 2, 1, 1, 1, '2011-07-28 10:00:00', '1-GMB9flBk6EakpbL'),
	(52, 8, 'Consolidado', 'RPTCE', 'Reporte Consolidado por Empresa', '', '', 'icons/reporte_consolidado_empresa.png', 'Por Empresa', 1, 2, 1, 1, '2011-07-29 22:08:55', '1-4W0qySwh7ezT77n'),
	(53, 8, 'Ruta', 'RPTRT', 'Reporte Ruta por Vehiculo', '', '', 'icons/reporte_ruta.png', 'Por Vehiculo', 4, 1, 1, 1, '2011-07-30 00:55:17', '1-nzrifWfA1ADJutO'),
	(54, 8, 'Produccion', 'RPTPR', 'Reporte de Produccion por Ruta', '', '', 'icons/reporte_produccion_ruta.png', 'Por Ruta', 1, 3, 1, 1, '2011-07-30 20:06:54', '1-IOt6L8QZaUcnEMW'),
	(55, 8, 'Alarmas', 'RPTAV', 'Reporte de Alarmas por Vehiculo', '', '', 'icons/reporte_alarmas.png', 'Por Vehiculo', 5, 1, 1, 1, '2011-07-30 21:31:40', '1-Dtni8SEVDPrCkou'),
	(56, 8, 'Vehiculos', 'RPTVA', 'Reporte Vehiculos por Alarma', '', '', 'icons/reporte_vehiculos_alarmas.png', 'Por Alarma', 1, 4, 1, 1, '2011-07-30 22:54:45', '1-PbYyDl0TFy8frO8'),
	(57, 8, 'Comparativo', 'RPTCR', 'Reporte Comparativo de Rutas', '', '', 'icons/reporte_comparativo_ruta.png', 'Por Ruta', 2, 3, 1, 1, '2011-08-01 19:45:04', '1-oaj1IOkjXQm7zuF'),
	(58, 8, 'Comparativo Produccion', 'RPTCPE', 'Reporte Comparativo de Produccion de Empresa', '', '', 'icons/reporte_comparativo_empresa.png', 'Por Empresa', 2, 2, 1, 1, '2011-08-01 20:23:50', '1-zj8GG8GRqYzesRY'),
	(59, 8, 'Nivel de Ocupacion', 'RPTNO', 'Reporte Nivel de Ocupacion', '', '', 'icons/reporte_nivel_ocupacion.png', 'Por Vehiculo', 6, 1, 1, 1, '2011-08-01 20:53:05', '1-Vi8X0dBtDHf8wM8'),
	(60, 8, 'Personas Punto Control', 'RPTPPC', 'Reporte de Personas por Punto de Control', '', '', 'icons/reporte_personas_punto_control.png', 'Por Vehiculo', 7, 1, 1, 1, '2011-08-01 21:23:54', '1-RCnOGnuFwhSTV7J'),
	(61, 8, 'Consolidado entre Puntos', 'RPTCEP', 'Reporte Consolidado Entre Puntos', '', '', 'icons/reporte_consolidado_puntos_ruta.png', 'Por Ruta', 3, 3, 1, 1, '2011-08-01 22:12:21', '1-WWClhq2L67TX77S'),
	(62, 8, 'Tarifa', 'RPTTV', 'Reporte por Tarifa ', '', '', 'icons/reporte_tarifa.png', 'Por Tarifa', 1, 5, 1, 1, '2011-08-02 01:57:26', '1-9bEH8b2zOlsu3dE'),
	(63, 9, 'Estadisticas', 'RPTEST', 'Reporte de Estadisticas', '', '', 'icons/reporte_estadisticas.png', 'General', 1, 1, 1, 1, '2011-08-02 02:47:33', '1-tD0MK3JAieeUVUR'),
	(64, 9, 'Propietario', 'RPTP', 'Reporte de Propietario', '', '', 'icons/reporte_propietario.png', 'Propietario', 1, 2, 1, 1, '2011-08-02 03:43:19', '1-GgX4gnaNsmS6laB'),
	(65, 9, 'Gerencia', 'RPTG', 'Reporte de Gerencia', '', '', 'icons/reporte_gerencia.png', 'Gerencia', 1, 3, 1, 1, '2011-08-02 03:51:49', '1-n8vxMiMJlnP6VAy'),
	(66, 9, 'Despachador', 'RPTD', 'Reporte Despachador', '', '', 'icons/reporte_despachador.png', 'Despachador', 1, 4, 1, 1, '2011-08-02 21:32:06', '1-EsQD76wOZ9QlcKt'),
	(67, 11, 'Paneles Flotantes', 'CNFFLOAT', 'Configuracion de los Paneles Flotantes', '', '', 'icons/panel_flotate.png', 'Flotantes', 2, 2, 1, 1, '2011-10-03 22:05:40', '1-h61Oy3TIHs45PNu'),
	(68, 1, 'Planificacion de Vehiculos', 'CNFPLAN', 'Planificacion de Horario de Vehiculo', '', '', 'icons/planificacion.png', 'Vehiculo', 6, 2, 1, 1, '2011-10-05 03:11:12', '1-BhnyOFnfFId65kM'),
	(69, 8, 'Horario Planificacion', 'RPTHP', 'Reporte de Horario Planificado vs Real ', '', '', 'icons/reporte_horario_planificado.png', 'Por Vehiculo', 8, 1, 1, 1, '2011-10-05 03:54:37', '1-64nRJ8nvQpsuIxp'),
	(70, 10, 'Visor de Logs', 'HVLOG', 'Visualizador de Log de Eventos', '', '', 'icons/log.png', 'Herramienta', 1, 1, 1, 1, '2011-10-11 03:42:06', '1-daOOPCJ13B3UTLH'),
	(71, 10, 'Verificar Procesos', 'CNFVP', 'Configurar los Procesos a Verificar', '', '', 'icons/verificar_procesos.png', 'Configuracion del Sistema', 1, 1, 1, 1, '2011-10-21 03:07:37', '1-0Pi8Cj814UOQtmp'),
	(72, 9, 'Exportar Excel', 'RPTER', 'Exportar Vehiculo en Rango de Fecha a Excel', '', '', 'icons/exportar_excel.png', 'Excel', 1, 5, 1, 1, '2011-10-22 02:48:50', '1-OXU94D5QVJMh7fO'),
	(73, 9, 'Datos Maestros', 'RPTEDM', 'Exportar Datos Maestros a Excel', '', '', 'icons/exportar_excel_maestros.png', 'Excel', 2, 5, 1, 1, '2011-10-22 22:38:54', '1-yUJc0as6Ih5iscs'),
	(74, 11, 'Configuracion', 'CNF', 'Configiuracion General de la Aplicacion', '', '', 'icons/config.png', 'Sistema', 1, 5, 1, 1, '2011-10-28 19:27:14', '1-JoJ9gP1OZqgBiCR'),
	(75, 11, 'Iniciar Pantalla Externa', 'CNFPE', 'Iniciar Pantalla de Informacion Externa', '', '', 'icons/pantalla_externa_iniciar.png', 'Pantalla Externa', 2, 3, 1, 1, '2011-11-17 21:47:21', '44-8dDXsqgMlJ0KNLY'),
	(76, 6, 'Enviar Estado', 'PEEV', 'Enviar Vehiculo a Estado en Perimetro', '', '', 'icons/enviar_estado.png', 'Visualizacion', 2, 2, 1, 1, '2011-11-22 18:49:05', '44-FFwfpkh5s8k7Nkj'),
	(77, 8, 'Eventos por Base', 'RPTEB', 'Reporte de Eventos por Base', 'b', '', 'icons/eventos.png', 'Por Eventos', 1, 6, 1, 1, '2011-12-13 21:26:30', '44-BoU3olocIJtG4wV'),
	(78, 8, 'Bases por Evento', 'RPTBE', 'Reporte de Bases por Evento generado', 'g', '', 'icons/eventoBase.png', 'Por Eventos', 2, 6, 1, 1, '2011-12-13 21:27:20', '44-TZnJFQNLSmhnG7x'),
	(79, 9, 'Descripcion Ruta', 'RPTDR', 'Descripcion General de Rutas', '', '', 'icons/reporte_ruta.png', 'General', 2, 1, 1, 1, '2012-01-28 02:03:06', '44-zrV3jB5LKoCSCFF'),
	(80, 8, 'Produccion Vehiculo General', 'RPTPVG', 'Reporte Produccion Vehiculo General', '', '', 'icons/reporte_produccion_vehiculo.png', 'Por Vehiculo', 3, 1, 1, 1, '2012-02-06 12:52:05', '44-CXReLXaFpashZKQ'),
	(81, 8, 'Perimetro', 'RPTPMT', 'Reporte de Perimetro', '', '', 'icons/reporte_perimetro.png', 'Por Vehiculo', 10, 1, 1, 0, '2013-02-08 19:25:43', '44-aV6WRaEL8zJzHCR'),
	(82, 8, 'Bitacora', 'RPTBPV', 'Reporte Bitacora Por vehiculo', '', '', 'icons/reporte_bitacora_vehiculo.png', 'Por Vehiculo', 9, 1, 1, 0, '2013-02-11 20:29:42', '44-aY76MBaEL8zJzHTY'),
	(83, 8, 'Vehiculos Por Ruta', 'RPTVPR', 'Reporte Vehiculos Por Ruta', '', '', 'icons/reporte_vehiculos_ruta.png', 'Por Ruta', 1, 4, 1, 1, '2013-03-22 18:39:34', '44-91IJ9r8c1HkIQMU'),
	(84, 12, 'Liquidacion Por Vehiculo', 'LPV', 'Liquidacion de Vehiculo', '', '', 'icons/liquidacion_vehiculo.png', 'Por Vehiculo', 1, 1, 1, 1, '2015-08-20 18:12:30', '44-NibBos38V32qPON'),
	(85, 12, 'Liquidacion Total Vehiculos', 'RPTLTV', 'Reporte Liquidacion Total de Vehiculo', '', '', 'icons/reporte_liquidacion_total_vehiculos.png', 'Por Vehiculo', 2, 1, 1, 1, '2013-07-03 01:47:45', '44-oCXpcIETnbBqOzG'),
	(86, 1, 'Asignacion Manual Ruta', 'AMR', 'Asignacion Manual Ruta Vehiculo', '', '', 'icons/asignacion_manual_ruta.png', 'Ruta', 1, 6, 1, 1, '2013-07-06 03:40:26', '44-KkUo2NMh9rN4TuD'),
	(87, 1, 'Eliminar Asignacion Ruta', 'AMRC', 'Eliminar Asignacion Ruta Manual', '', '', 'icons/asignacion_manual_ruta.png', 'Ruta', 1, 7, 1, 1, '2013-07-06 03:41:47', '44-1S2OFg0e3c3teXI'),
	(88, 12, 'Consolidado Liquidacion', 'RPTCLV', 'Reporte Consolidado Liquidacion', NULL, NULL, 'icons/liquidacion-consolidado.png', 'Por Empresa', 1, 2, 1, 1, '2013-05-09 19:32:41', '44-VPXkfF2vMiM8c4i'),
	(89, 12, 'Consulta Liquidacion X Vehiculo', 'LPVC', 'Consulta Liquidacion X Vehiculo', '', '', 'icons/liquidacion_vehiculo.png', 'Por Vehiculo', 1, 1, 1, 1, '2015-05-14 22:13:34', '44-FmZbwEk8td2GYT8'),
	(90, 12, 'Reporte Liquidacion X Liquidador', 'LPLC', 'Reporte Liquidacion X Liquidador', '', '', 'icons/reporte_despachador.png', 'Por Liquidador', 1, 3, 1, 1, '2016-02-17 15:59:17', '44-7sezZ4iAc0HK64E'),
	(91, 1, 'Categorías', 'DSTOCAT', 'Descuentos por categorias', '', '', 'icons/reporte_despachador.png', 'Liquidacion', 1, 4, 1, 1, '2016-04-20 11:32:01', '44-iqy11CaxH41K2oO'),
	(92, 12, 'Reporte Consolidado Liquidación Categorias', 'RPTLCV', 'Reporte Consolidado de Liquidación por Categorías por Vehiculo Panel', '', 'Liquidacion', 'icons/reporte_liquidacion_total_vehiculos.png', 'Por Empresa', 1, 5, 1, 1, '2016-05-17 14:36:41', '44-vfJOCZ07NvMwt4d'),
	(93, 12, 'Reporte Descuento por Categorias', 'RPTLCC', 'Reporte Descuento Liquidacion por Categoria', '', 'Liquidacion', 'icons/reporte_liquidacion_total_vehiculos.png', 'Por Empresa', 3, 6, 1, 1, '2016-05-17 14:39:39', '44-Nql2kIbZ7i1Xudg'),
	(94, 12, 'Reporte Descuento de Pasajeros por Categoria', 'RPTPCA', 'Reporte Descuento Pasajeros por Categoria', '', 'Liquidacion', 'icons/reporte_liquidacion_total_vehiculos.png', 'Por Empresa', 3, 6, 1, 1, '2016-07-01 17:05:00', '44-Nql2kIbZ7i1Xuds');
/*!40000 ALTER TABLE `tbl_acceso` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_acceso_perfil
DROP TABLE IF EXISTS `tbl_acceso_perfil`;
CREATE TABLE IF NOT EXISTS `tbl_acceso_perfil` (
  `PK_ACCESO_PERFIL` int(11) NOT NULL AUTO_INCREMENT,
  `FK_PERFIL` int(11) DEFAULT NULL,
  `PK_ACCESO` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_ACCESO_PERFIL`),
  KEY `FK_ACCESO_PERFIL` (`FK_PERFIL`),
  KEY `FK_PERFIL_ACCESO` (`PK_ACCESO`),
  CONSTRAINT `FK_ACCESO_PERFIL` FOREIGN KEY (`FK_PERFIL`) REFERENCES `tbl_perfil` (`PK_PERFIL`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_PERFIL_ACCESO` FOREIGN KEY (`PK_ACCESO`) REFERENCES `tbl_acceso` (`PK_ACCESO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=478 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_acceso_perfil: ~440 rows (aproximadamente)
DELETE FROM `tbl_acceso_perfil`;
/*!40000 ALTER TABLE `tbl_acceso_perfil` DISABLE KEYS */;
INSERT INTO `tbl_acceso_perfil` (`PK_ACCESO_PERFIL`, `FK_PERFIL`, `PK_ACCESO`, `ESTADO`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`, `PK_UNICA`) VALUES
	(1, 2, 1, 0, 1, '2016-03-04 07:43:19', '44-A4CkgD5OxDDscdZ'),
	(2, 2, 2, 0, 1, '2016-03-04 07:43:19', '44-hP6pSYJstRfRWoO'),
	(3, 2, 3, 0, 1, '2016-03-04 07:43:19', '44-qRLQdqVGcJBIl1v'),
	(4, 2, 4, 1, 1, '2013-06-08 02:38:53', '44-n48j3jfdSH9oqAT'),
	(5, 2, 6, 0, 1, '2016-03-04 07:43:19', '44-2zids55DMDmOw6Y'),
	(6, 2, 10, 1, 1, '2013-06-08 02:38:53', '44-0RaLGUQxAwmnMd1'),
	(7, 2, 11, 1, 1, '2013-06-08 02:38:53', '44-OVOWWGDD9LWQF1c'),
	(8, 2, 12, 1, 1, '2013-06-08 02:38:53', '44-2daYp3LUF7GdGkB'),
	(9, 2, 13, 1, 1, '2013-06-08 02:38:54', '44-GbQKwuGp1zPSJax'),
	(10, 2, 14, 1, 1, '2013-06-08 02:38:54', '44-PMnyJGkXd2nge2v'),
	(11, 2, 15, 1, 1, '2013-06-08 02:38:54', '44-RHWCq042pI4Y3mo'),
	(12, 2, 16, 1, 1, '2013-06-08 02:38:54', '44-20ibDzGbdHYAurd'),
	(13, 2, 17, 1, 1, '2013-06-08 02:38:54', '44-6IbjEQaXlFZ1ldb'),
	(14, 2, 18, 1, 1, '2016-02-29 19:46:49', '44-ASeaTcfLMpUTvbz'),
	(15, 2, 19, 1, 1, '2016-02-29 19:46:49', '44-HG42Sf2YeaBsG7s'),
	(16, 2, 20, 1, 1, '2013-06-08 02:38:55', '44-wPvXrsiymVKkR2v'),
	(17, 2, 21, 1, 1, '2016-02-29 19:46:49', '44-Y6ljWD0lLiw2WNC'),
	(18, 2, 22, 1, 1, '2016-02-29 19:46:49', '44-VF8ew8ZZ9uJ46yO'),
	(19, 2, 23, 1, 1, '2016-02-29 19:46:50', '44-kkAagZNtpHZHilI'),
	(20, 2, 24, 1, 1, '2016-02-29 19:46:50', '44-WByJ0QCTd5uu0X2'),
	(21, 2, 25, 1, 1, '2016-02-29 19:46:50', '44-j42Oi5F6aPyJrpU'),
	(22, 2, 26, 1, 1, '2016-02-29 19:46:50', '44-R0Yp8BJbUyt0fxh'),
	(23, 2, 27, 1, 1, '2016-02-29 19:46:50', '44-ntTz5t5eq2pjT2Q'),
	(24, 2, 28, 1, 1, '2016-02-29 19:46:50', '44-pulZiu6zkiOGFsa'),
	(25, 2, 30, 1, 1, '2013-09-26 01:58:36', '44-hVVVqb6fTWkNNmF'),
	(26, 2, 31, 1, 1, '2016-02-29 19:46:50', '44-QiWLKF1xab3Eka1'),
	(27, 2, 32, 1, 1, '2016-02-29 19:46:50', '44-GhyqXY8HJE2qtr6'),
	(28, 2, 33, 1, 1, '2013-06-08 02:38:57', '44-ZxnHNPJRR6qN7dd'),
	(29, 2, 34, 1, 1, '2013-06-08 02:38:58', '44-cXDHZUjmZ9qmEco'),
	(30, 2, 35, 1, 1, '2016-02-29 19:46:50', '44-EUyR35M7yRBOT6h'),
	(31, 2, 36, 1, 1, '2013-06-08 02:38:58', '44-d4VbwKcDljP7pzF'),
	(32, 2, 37, 1, 1, '2013-06-08 02:38:58', '44-e0TqzhO9P0UPLaz'),
	(33, 2, 38, 1, 1, '2013-06-08 02:38:58', '44-T5DbJtPSko0jjfU'),
	(34, 2, 39, 1, 1, '2016-02-29 19:46:50', '44-D3putk3D7FBkU5D'),
	(35, 2, 40, 1, 1, '2013-06-08 02:38:59', '44-wKwwzMw5HlHfwvg'),
	(36, 2, 41, 1, 1, '2013-06-08 02:38:59', '44-55aUkmQ2Ki76g4q'),
	(37, 2, 42, 1, 1, '2013-06-08 02:38:59', '44-rkyUhAItQtIx2II'),
	(38, 2, 43, 0, 1, '2016-03-04 07:43:19', '44-f6gCVP2CTNRLQFX'),
	(39, 2, 44, 0, 1, '2016-03-04 07:43:20', '44-RKH2epbWKfrru2L'),
	(40, 2, 45, 1, 1, '2013-06-08 02:39:00', '44-TDw4rGAXlpPT9Lc'),
	(41, 2, 46, 1, 1, '2013-06-08 02:39:00', '44-jBqohJQgzokpDqi'),
	(42, 2, 47, 1, 1, '2013-06-08 02:39:00', '44-yjJ8Hy82uB49xVJ'),
	(43, 2, 48, 1, 1, '2013-06-08 02:39:00', '44-qLxqFw3EHVpdBqw'),
	(44, 2, 49, 1, 1, '2013-06-08 02:39:00', '44-XEfM7jB4rkBC6v5'),
	(45, 2, 50, 1, 1, '2013-06-08 02:39:01', '44-SPuTW0IQqQPqnzm'),
	(46, 2, 51, 1, 1, '2013-06-08 02:39:01', '44-72csyIq4YJLOSa9'),
	(47, 2, 52, 1, 1, '2013-06-08 02:39:01', '44-8aUotVmlbQsQZZo'),
	(48, 2, 53, 1, 1, '2013-06-08 02:39:01', '44-TGwL5gDEqfCy2tt'),
	(49, 2, 54, 1, 1, '2013-06-08 02:39:01', '44-JwYAqdkdq043DRM'),
	(50, 2, 55, 1, 1, '2013-06-08 02:39:02', '44-JcmWaOUyqVS47Ew'),
	(51, 2, 56, 1, 1, '2013-06-08 02:39:02', '44-AtrBptq3TtLx0Zo'),
	(52, 2, 57, 1, 1, '2013-06-08 02:39:02', '44-Yp5J0STYU2lcA38'),
	(53, 2, 58, 1, 1, '2013-06-08 02:39:02', '44-90lwWb9wzb2hD0m'),
	(54, 2, 59, 1, 1, '2013-06-08 02:39:02', '44-QPAnyJsNFashci2'),
	(55, 2, 60, 1, 1, '2013-06-08 02:39:03', '44-r7xReYiVwNQpw36'),
	(56, 2, 61, 1, 1, '2013-06-08 02:39:03', '44-KgKtsLi0dgu6nZU'),
	(57, 2, 62, 1, 1, '2016-02-29 19:46:50', '44-MoPL2HylS1iFFfP'),
	(58, 2, 63, 1, 1, '2013-06-08 02:39:03', '44-XKK2RgYI4YDAkAZ'),
	(59, 2, 64, 1, 1, '2013-06-08 02:39:03', '44-ajdvWVTYvchCmBH'),
	(60, 2, 65, 1, 1, '2013-06-08 02:39:03', '44-vfOrglqsGzDPFYm'),
	(61, 2, 66, 1, 1, '2013-06-08 02:39:04', '44-KmvHqFqX2tfyssM'),
	(62, 2, 67, 1, 1, '2013-06-08 02:39:04', '44-IV5kM0heLqQPGD2'),
	(63, 2, 68, 1, 1, '2016-02-29 19:46:51', '44-8GiZe3JrsbBZuZ7'),
	(64, 2, 69, 1, 1, '2016-02-29 19:46:51', '44-UyK3qRrXaiKeNhb'),
	(65, 2, 70, 1, 1, '2013-06-08 02:39:04', '44-LQjtIRfGi10Wyvp'),
	(66, 2, 71, 1, 1, '2016-02-29 19:46:51', '44-vb9r8KCBSK5zRii'),
	(67, 2, 72, 1, 1, '2013-06-08 02:39:05', '44-OKBlWpPGEhgSQ17'),
	(68, 2, 73, 1, 1, '2016-02-29 19:46:51', '44-VCE9HkhrHEmFPKD'),
	(69, 2, 74, 1, 1, '2013-06-08 02:39:05', '44-3naG3hDqJRNU0Og'),
	(70, 2, 75, 1, 1, '2013-06-08 02:39:05', '44-QLHFMBjYzeSv6Wm'),
	(71, 2, 76, 1, 1, '2013-06-08 02:39:05', '44-KgcuWTW2j04jWc9'),
	(72, 2, 77, 1, 1, '2016-02-29 19:46:51', '44-4j3tJDjU0EeLzgC'),
	(73, 2, 78, 1, 1, '2016-02-29 19:46:51', '44-RhH7YY6u10UUlDl'),
	(74, 2, 79, 1, 1, '2013-06-08 02:39:06', '44-MkRqW9NTZBHix0E'),
	(75, 2, 80, 1, 1, '2013-06-08 02:39:06', '44-U4hlta5RauH8TqJ'),
	(76, 2, 81, 1, 1, '2013-06-08 02:39:06', '44-j0VY8pLoMMZJWDX'),
	(77, 2, 82, 1, 1, '2013-06-08 02:39:07', '44-zXiS5lntqQmGu0P'),
	(78, 2, 83, 1, 1, '2013-06-08 02:39:07', '44-fxGQcTxuYv9UlHT'),
	(79, 3, 4, 0, 1, '2013-09-06 02:01:41', '44-nCLdFi5euc0Tddl'),
	(80, 3, 10, 0, 1, '2014-02-10 20:35:00', '44-IDCQcXe7FFZ8tZR'),
	(81, 3, 11, 0, 1, '2014-02-10 20:35:00', '44-M8v5PSFE1e0Mtps'),
	(82, 3, 12, 0, 1, '2014-02-10 20:35:00', '44-SApv2rNsLsuPlZw'),
	(83, 3, 13, 0, 1, '2014-02-10 20:35:00', '44-7nbKkmkSNyV5ap6'),
	(84, 3, 14, 0, 1, '2014-02-10 20:35:00', '44-ZuTCHROb6KGGsi3'),
	(85, 3, 15, 0, 1, '2014-02-10 20:35:00', '44-qeK3EkJdkktAEDd'),
	(86, 3, 16, 0, 1, '2014-02-10 20:35:00', '44-A8yn09ZpMHxd3MA'),
	(87, 3, 17, 0, 1, '2014-02-10 20:35:00', '44-yQU6C5k6pqDRjlD'),
	(88, 3, 18, 0, 1, '2014-02-10 20:35:00', '44-WZxqTqusPCiZVdo'),
	(89, 3, 19, 0, 1, '2014-02-10 20:35:00', '44-HMtI8zHYAoxHfny'),
	(90, 3, 20, 1, 1, '2013-08-09 18:58:22', '44-o2NNJBabXM2Nfci'),
	(91, 3, 21, 0, 1, '2016-02-18 10:44:54', '44-bhnO09I3NgfOkD0'),
	(92, 3, 22, 0, 1, '2016-02-18 10:44:55', '44-bRmoDedmGiO1zRr'),
	(93, 3, 23, 0, 1, '2016-02-18 10:44:55', '44-FAv5b6Dxwj4Wltw'),
	(94, 3, 24, 0, 1, '2016-02-18 10:44:56', '44-4EoRynJ0szBD4iT'),
	(95, 3, 25, 0, 1, '2016-02-18 10:44:56', '44-CLrw72dOCXQPb9Z'),
	(96, 3, 26, 0, 1, '2016-02-18 10:44:56', '44-eFkRf2BiGGS8Dys'),
	(97, 3, 27, 0, 1, '2016-02-18 10:44:57', '44-QAVqclrtrCURTab'),
	(98, 3, 28, 0, 1, '2016-02-18 10:44:57', '44-Uuc2rWHx6ZmtaFm'),
	(99, 3, 30, 0, 1, '2014-02-10 20:35:01', '44-lXzWXDvEXO2Ou2V'),
	(100, 3, 31, 0, 1, '2014-02-10 20:35:01', '44-5mMTVJV8USRXqzf'),
	(101, 3, 32, 0, 1, '2014-02-10 20:35:01', '44-KgDeZjhJFYa1IFk'),
	(102, 3, 33, 1, 1, '2013-08-09 00:53:35', '44-CjClTBkNgDPLGKn'),
	(103, 3, 34, 0, 1, '2016-02-17 16:03:16', '44-vwYp48WmVydAS5P'),
	(104, 3, 35, 0, 1, '2016-02-18 10:44:58', '44-L3WK0utIQYtUsHl'),
	(105, 3, 36, 0, 1, '2016-02-18 10:44:58', '44-5qsZM2P5RoIfhuP'),
	(106, 3, 37, 0, 1, '2014-02-10 20:35:01', '44-LLTvQeumZoBBkNH'),
	(107, 3, 38, 0, 1, '2014-02-10 20:35:01', '44-cibVZiCeOMi62ch'),
	(108, 3, 39, 0, 1, '2014-02-10 20:35:01', '44-Grg9E7KTLpajOMA'),
	(109, 3, 40, 0, 1, '2014-02-10 20:35:01', '44-eubvsGDi1TBhq2m'),
	(110, 3, 41, 1, 1, '2013-08-09 00:53:36', '44-DQ3GbKecT5xgEXY'),
	(111, 3, 42, 1, 1, '2013-06-08 02:39:56', '44-zy9rCRb5KzW1bu4'),
	(112, 3, 43, 0, 1, '2016-02-17 16:03:16', '44-eN6mrNdLKi8Ul7M'),
	(113, 3, 44, 0, 1, '2016-02-17 16:03:16', '44-jxvyvQ6JNYnOUol'),
	(114, 3, 45, 0, 1, '2016-02-18 10:45:00', '44-nOuoh3WYxyAYRm1'),
	(115, 3, 46, 0, 1, '2016-02-18 10:45:00', '44-AOXPWOJRWVjA7Zy'),
	(116, 3, 47, 0, 1, '2016-02-18 10:45:00', '44-ORaBgxWnKoACCT2'),
	(117, 3, 48, 0, 1, '2016-02-18 10:45:01', '44-JiuqZFyt3CtGSPt'),
	(118, 3, 49, 1, 1, '2013-08-09 00:53:36', '44-tUuNHcE9OLzlF4r'),
	(119, 3, 50, 1, 1, '2013-08-09 00:53:36', '44-IFcY1gTphcvjoNz'),
	(120, 3, 51, 1, 1, '2013-08-09 00:53:37', '44-PCmTcox5pKkOUw3'),
	(121, 3, 52, 1, 1, '2013-08-09 00:53:37', '44-M49JotzgqcA5zxE'),
	(122, 3, 53, 1, 1, '2013-08-09 00:53:37', '44-DvnjNFOnyjcLneC'),
	(123, 3, 54, 1, 1, '2013-08-09 00:53:37', '44-DlvGD1YecIUDoPj'),
	(124, 3, 55, 1, 1, '2013-08-09 00:53:37', '44-GPFphE9tPF5NCKe'),
	(125, 3, 56, 1, 1, '2013-08-09 00:53:37', '44-RrXL9aUs4uRlqRB'),
	(126, 3, 57, 1, 1, '2013-08-09 00:53:37', '44-jcGv22OQcmlWkOT'),
	(127, 3, 58, 1, 1, '2013-08-09 00:53:37', '44-RjcQn88ZVPvwGGS'),
	(128, 3, 59, 1, 1, '2013-08-09 00:53:37', '44-90YaISolWLi9qqE'),
	(129, 3, 60, 1, 1, '2013-08-09 00:53:37', '44-kYusp6H2iRaiZZH'),
	(130, 3, 61, 1, 1, '2013-08-09 00:53:38', '44-KvWCAkIMG0yyPFs'),
	(131, 3, 62, 0, 1, '2016-02-18 10:45:03', '44-UTKy1b41yYfxSdO'),
	(132, 3, 63, 1, 1, '2013-08-09 00:53:38', '44-biPvBVyqmhvhcGk'),
	(133, 3, 64, 1, 1, '2013-08-09 00:53:38', '44-EYtrR4HPCvj59Fv'),
	(134, 3, 65, 1, 1, '2013-08-09 00:53:38', '44-DB4XOTN9pb5nGw3'),
	(135, 3, 66, 1, 1, '2013-08-09 00:53:38', '44-oJUT80UgK3oBbKT'),
	(136, 3, 67, 1, 1, '2013-08-09 00:53:38', '44-e0MAvcgNoEH4tpn'),
	(137, 3, 68, 0, 1, '2014-02-10 20:35:02', '44-IoxiHoOotwtosgr'),
	(138, 3, 69, 0, 1, '2016-02-18 10:45:04', '44-Vuw8QqQEptZvSAj'),
	(139, 3, 70, 0, 1, '2016-02-18 10:45:05', '44-J8boyljBV6grPe1'),
	(140, 3, 71, 0, 1, '2016-02-18 10:45:05', '44-kieE4OOrVN3cxiu'),
	(141, 3, 72, 1, 1, '2013-08-09 00:53:38', '44-4Eo6G0cedCXYN36'),
	(142, 3, 73, 0, 1, '2016-02-18 10:45:06', '44-YPjFoOHnhyCIixp'),
	(143, 3, 74, 0, 1, '2015-08-19 20:58:07', '44-9vibV6hq88nMyCX'),
	(144, 3, 75, 1, 1, '2013-08-09 00:53:39', '44-ZOCsdSTREUzQbcq'),
	(145, 3, 76, 0, 1, '2016-02-18 10:45:07', '44-wH3hSChMMIkeEp5'),
	(146, 3, 77, 0, 1, '2016-02-18 10:45:07', '44-qSTpa3XaryshvJf'),
	(147, 3, 78, 1, 1, '2013-08-09 00:53:39', '44-xuhEiXGpYzM7gW7'),
	(148, 3, 79, 1, 1, '2013-08-09 00:53:39', '44-5vNEpkSwYGyMvlq'),
	(149, 3, 80, 1, 1, '2013-08-09 00:53:39', '44-NasU0XKMFtgYEOm'),
	(150, 3, 81, 1, 1, '2013-08-09 00:53:39', '44-sAOYwxOEPEuFZfz'),
	(151, 3, 82, 1, 1, '2013-08-09 00:53:39', '44-u3EqkD5cmGPnPJV'),
	(152, 3, 83, 1, 1, '2013-08-09 00:53:39', '44-g5JP46CWzCvZHMj'),
	(153, 2, 84, 0, 1, '2016-03-04 07:39:48', '44-QB5ihSGaI07Du9e'),
	(154, 2, 85, 1, 1, '2013-06-28 00:20:12', '44-tRFpUCDHbO7yznq'),
	(155, 3, 84, 0, 1, '2016-02-18 10:45:08', '44-0eAaOhJg6u8LAZS'),
	(156, 3, 85, 1, 1, '2013-08-09 18:58:24', '44-IZqApUxpKgAlOp1'),
	(157, 2, 86, 1, 1, '2016-02-29 19:46:51', '44-6oIqIoiFpYKrnS3'),
	(158, 2, 87, 1, 1, '2016-02-29 19:46:51', '44-V6Z9ljI5lTGMwFU'),
	(159, 3, 86, 0, 1, '2016-02-18 10:45:09', '44-tnHp9iW6b63iMkJ'),
	(160, 3, 87, 0, 1, '2016-02-18 10:45:09', '44-Z4YoocdDWOteMux'),
	(161, 2, 88, 1, 1, '2013-08-24 02:39:15', '44-UxQ0NJamJKsShBo'),
	(162, 3, 88, 1, 1, '2013-09-06 02:01:42', '44-5bGWh26DcgOalsA'),
	(200, 2, 89, 1, 1, '2015-05-14 22:13:56', '44-Z3eWdHHevIvOdJr'),
	(201, 4, 4, 0, 1, '2016-02-18 10:52:48', '44-MkGtqoyaJjSMVxz'),
	(202, 4, 10, 1, 1, '2015-08-27 02:24:53', '44-GqXXk3IzxXISL8g'),
	(203, 4, 11, 1, 1, '2015-08-27 02:24:53', '44-QzOntz2754ySZR5'),
	(204, 4, 12, 1, 1, '2015-08-27 02:24:54', '44-SeFsq3uvCKM0FS3'),
	(205, 4, 13, 1, 1, '2015-08-27 02:24:54', '44-1mCrOAbiBMV8vAk'),
	(206, 4, 14, 1, 1, '2015-08-27 02:24:54', '44-weATXFljS5DglU3'),
	(207, 4, 15, 1, 1, '2015-08-27 02:24:54', '44-eu8YfJOAD4rGhD0'),
	(208, 4, 16, 1, 1, '2015-08-27 02:24:54', '44-QnIXXwClzhxe8Mz'),
	(209, 4, 17, 1, 1, '2015-08-27 02:24:55', '44-6XAl7eCloPBALke'),
	(210, 4, 18, 1, 1, '2015-08-27 02:24:55', '44-iU196Up9Bgxv0GS'),
	(211, 4, 19, 1, 1, '2015-08-27 02:24:55', '44-EozrBSFKpjwFbIP'),
	(212, 4, 20, 1, 1, '2015-08-27 02:24:55', '44-wm0ryb51okWVmg7'),
	(213, 4, 21, 1, 1, '2015-08-27 02:24:56', '44-JrGDMaZWWjkmEqC'),
	(214, 4, 22, 1, 1, '2015-08-27 02:24:56', '44-XGhsh6gUnn3wBxs'),
	(215, 4, 23, 1, 1, '2015-08-27 02:24:56', '44-z15qQOUSpPPd0Vh'),
	(216, 4, 24, 1, 1, '2015-08-27 02:24:56', '44-qcT5mqqNok5RUHA'),
	(217, 4, 25, 1, 1, '2015-08-27 02:24:56', '44-7USp2wFH1B1dpF7'),
	(218, 4, 26, 1, 1, '2015-08-27 02:24:57', '44-Z4qhG1uZ9XSwzE2'),
	(219, 4, 27, 1, 1, '2015-08-27 02:24:57', '44-Skix41WlMjadakH'),
	(220, 4, 28, 1, 1, '2015-08-27 02:24:57', '44-OMdvfcprSYuOiiH'),
	(221, 4, 30, 1, 1, '2015-08-27 02:24:57', '44-FXVqPWLGPBe7FDw'),
	(222, 4, 31, 0, 1, '2015-08-27 02:31:31', '44-zlfcP2Cwn1yG3ZU'),
	(223, 4, 32, 0, 1, '2015-08-27 02:31:31', '44-o4ndtj8UE2iHwmh'),
	(224, 4, 33, 1, 1, '2015-08-27 02:24:58', '44-FwGlg9BcmMrforD'),
	(225, 4, 34, 1, 1, '2015-08-27 02:24:58', '44-zhjMO772eD6yKZc'),
	(226, 4, 35, 1, 1, '2015-08-27 02:24:59', '44-lfumaOf03Fe43aI'),
	(227, 4, 36, 1, 1, '2015-08-27 02:24:59', '44-S9qjGspcC2HvpgP'),
	(228, 4, 37, 1, 1, '2015-08-27 02:24:59', '44-wr8j0YPvi73q1v9'),
	(229, 4, 38, 1, 1, '2015-08-27 02:24:59', '44-7NXuIE2ZSHooXCd'),
	(230, 4, 39, 1, 1, '2015-08-27 02:24:59', '44-DCNDyUZKq30DlIN'),
	(231, 4, 40, 1, 1, '2015-08-27 02:25:00', '44-MidTdrdFDNLLXbJ'),
	(232, 4, 42, 1, 1, '2015-08-27 02:25:00', '44-raf6bozbmOQmOnl'),
	(233, 4, 45, 1, 1, '2015-08-27 02:25:00', '44-PgnxddbDjWuh0PF'),
	(234, 4, 46, 1, 1, '2015-08-27 02:25:00', '44-1jc5R9LoirYrFD3'),
	(235, 4, 47, 1, 1, '2015-08-27 02:25:01', '44-LPAdtWq6roTvo06'),
	(236, 4, 48, 1, 1, '2015-08-27 02:25:01', '44-Zq7n6tWyXOxmwxa'),
	(237, 4, 49, 1, 1, '2015-08-27 02:25:01', '44-JnTvfSFcEuKztRy'),
	(238, 4, 50, 1, 1, '2015-08-27 02:25:01', '44-oASjdLOzliWM5wb'),
	(239, 4, 51, 1, 1, '2015-08-27 02:25:02', '44-Bmc8JkoXdf0Vfzp'),
	(240, 4, 52, 1, 1, '2015-08-27 02:25:02', '44-cw9V31AVtIHYXZu'),
	(241, 4, 53, 1, 1, '2015-08-27 02:25:02', '44-i7tdoUjHSr1TIHU'),
	(242, 4, 54, 1, 1, '2015-08-27 02:25:02', '44-tQLQscqKfLOdNqO'),
	(243, 4, 55, 1, 1, '2015-08-27 02:25:02', '44-CqqtCLlcnbw2HZF'),
	(244, 4, 56, 1, 1, '2015-08-27 02:25:03', '44-30vkPiVSc0VgXGr'),
	(245, 4, 57, 1, 1, '2015-08-27 02:25:03', '44-15cqXOH6E3EQezt'),
	(246, 4, 58, 1, 1, '2015-08-27 02:25:03', '44-1GGGUpyEiIfzXUe'),
	(247, 4, 59, 1, 1, '2015-08-27 02:25:03', '44-HU7RonNYUMHCgQ9'),
	(248, 4, 60, 1, 1, '2015-08-27 02:25:04', '44-WG5JIM23yHw8yW0'),
	(249, 4, 61, 1, 1, '2015-08-27 02:25:04', '44-GskTwH7HzpwXF64'),
	(250, 4, 62, 0, 1, '2016-02-18 10:53:00', '44-fhV91a7GJSEa36F'),
	(251, 4, 63, 1, 1, '2015-08-27 02:25:04', '44-ZFV7GSH4BwdNK9Q'),
	(252, 4, 64, 1, 1, '2015-08-27 02:25:04', '44-ajsZqpm1psD7w8m'),
	(253, 4, 65, 1, 1, '2015-08-27 02:25:09', '44-429ZiBizug78V1M'),
	(254, 4, 66, 1, 1, '2015-08-27 02:25:09', '44-nyLfr65ofXZSJZu'),
	(255, 4, 67, 1, 1, '2015-08-27 02:25:09', '44-MpbYe3rXjeizBjU'),
	(256, 4, 68, 1, 1, '2015-08-27 02:25:09', '44-nKbmWU7AW6umvJe'),
	(257, 4, 69, 0, 1, '2016-02-18 10:53:02', '44-xVPTcG0poJCz8Om'),
	(258, 4, 70, 1, 1, '2015-08-27 02:25:10', '44-7SpqefDb9pZn6qn'),
	(259, 4, 72, 1, 1, '2015-08-27 02:25:10', '44-oWgyOqg4bc4iJOK'),
	(260, 4, 73, 0, 1, '2016-02-18 10:53:03', '44-9jc93B7w4mwktWn'),
	(261, 4, 74, 1, 1, '2015-08-27 02:25:11', '44-y3ZeI5r9gpXIgw9'),
	(262, 4, 75, 1, 1, '2015-08-27 02:25:11', '44-5rzeRZpbKeIP3dO'),
	(263, 4, 76, 1, 1, '2015-08-27 02:25:11', '44-Ri7e0DZ2YrP0MlZ'),
	(264, 4, 77, 0, 1, '2016-02-18 10:53:04', '44-A3s789ekNRz6Nfk'),
	(265, 4, 78, 0, 1, '2016-02-18 10:53:04', '44-wIhNDO3PpH3k8GV'),
	(266, 4, 79, 1, 1, '2015-08-27 02:25:12', '44-zSipasFw5QkX3f6'),
	(267, 4, 80, 1, 1, '2015-08-27 02:25:12', '44-ZpeO3tWodPGCwra'),
	(268, 4, 81, 1, 1, '2015-08-27 02:25:12', '44-TExPeE5y2nJcy6c'),
	(269, 4, 82, 1, 1, '2015-08-27 02:25:12', '44-UPicLlo3kqGWexy'),
	(270, 4, 83, 1, 1, '2015-08-27 02:25:13', '44-7ZKHiTJATLKijKa'),
	(271, 4, 84, 1, 1, '2015-08-27 02:25:13', '44-jpLAV7IvpkjqRNI'),
	(272, 4, 85, 1, 1, '2015-08-27 02:25:13', '44-fMWD99RFMSzHC77'),
	(273, 4, 88, 1, 1, '2015-08-27 02:31:32', '44-3hn3pZ2TE6axvpO'),
	(274, 4, 89, 1, 1, '2015-08-27 02:31:33', '44-peydgIj9wNuUHe9'),
	(275, 4, 41, 1, 1, '2015-09-24 14:28:06', '44-vuq2RZlk9sLdGjk'),
	(276, 4, 43, 1, 1, '2015-09-24 14:28:06', '44-Yxfcw5PjkToCQes'),
	(277, 4, 44, 1, 1, '2015-09-24 14:28:06', '44-S0KPPc6L0pypxEB'),
	(278, 5, 20, 1, 1, '2015-10-23 18:58:10', '44-VLaZBV0ahXycVWn'),
	(279, 5, 21, 0, 1, '2016-02-18 11:03:09', '44-H5dJxAgMKE6tJ5s'),
	(280, 5, 22, 0, 1, '2016-02-18 11:03:09', '44-LlcKRT243MlU36Y'),
	(281, 5, 23, 0, 1, '2016-02-18 11:03:10', '44-4HkvGJSX9ssM4lc'),
	(282, 5, 24, 0, 1, '2016-02-18 11:03:10', '44-50OeegymTkYSAlp'),
	(283, 5, 25, 0, 1, '2016-02-18 11:03:11', '44-sgi4BYwN98BgCa6'),
	(284, 5, 26, 0, 1, '2016-02-18 11:03:11', '44-a4ArshG7sHQ2MMd'),
	(285, 5, 27, 0, 1, '2016-02-18 11:03:12', '44-hFhFIEZlc4S138A'),
	(286, 5, 28, 0, 1, '2016-02-18 11:03:12', '44-SQUxELYYRCER4F4'),
	(287, 5, 33, 1, 1, '2015-10-23 18:58:12', '44-MIr4T2bjKXHiN23'),
	(288, 5, 35, 0, 1, '2016-02-18 11:03:13', '44-mPlkcZL1kGoTfOV'),
	(289, 5, 45, 0, 1, '2016-02-18 11:03:14', '44-HTVL4KyheYhMzY1'),
	(290, 5, 46, 0, 1, '2016-02-18 11:03:14', '44-10m7pOKh77zc3gC'),
	(291, 5, 47, 0, 1, '2016-02-18 11:03:15', '44-KM6VMDq2dXGCZgN'),
	(292, 5, 48, 0, 1, '2016-02-18 11:03:15', '44-5QaB1HQkvi7uXj0'),
	(293, 5, 49, 1, 1, '2015-10-23 18:58:13', '44-Ka5Y7pPVRbUDAou'),
	(294, 5, 50, 1, 1, '2015-10-23 18:58:13', '44-Dy2nojAGgU5hOq7'),
	(295, 5, 51, 1, 1, '2015-10-23 18:58:14', '44-y9pExFRd6tLyfTq'),
	(296, 5, 52, 1, 1, '2015-10-23 18:58:14', '44-XcLxdAKwifjJD1F'),
	(297, 5, 53, 1, 1, '2015-10-23 18:58:14', '44-qQL1B4Hwoskoh5f'),
	(298, 5, 54, 1, 1, '2015-10-23 18:58:14', '44-dbEesGi7BboQdJG'),
	(299, 5, 55, 1, 1, '2015-10-23 18:58:14', '44-TloyxJb2rYu6VJm'),
	(300, 5, 56, 1, 1, '2015-10-23 18:58:15', '44-P9GBlt2P4n7vGYb'),
	(301, 5, 57, 1, 1, '2015-10-23 18:58:15', '44-lvkKEaRO06Kys0m'),
	(302, 5, 58, 1, 1, '2015-10-23 18:58:15', '44-taaLuDC8IhMDQUj'),
	(303, 5, 59, 1, 1, '2015-10-23 18:58:15', '44-IxUNgmboBoFXUO6'),
	(304, 5, 60, 1, 1, '2015-10-23 18:58:15', '44-HTUcXWGhxjI04lj'),
	(305, 5, 61, 1, 1, '2015-10-23 18:58:16', '44-wq73e0VwFi4Cfh6'),
	(306, 5, 62, 0, 1, '2016-02-18 11:03:18', '44-c7M5qDv4SDXzXNl'),
	(307, 5, 63, 0, 1, '2016-02-18 11:03:18', '44-XUTH1X6SAGJxXsY'),
	(308, 5, 64, 0, 1, '2016-02-18 11:03:18', '44-K7CNrVKUzxb0vdb'),
	(309, 5, 65, 0, 1, '2016-02-18 11:03:19', '44-v1Fv2nKddV0i1Ze'),
	(310, 5, 66, 0, 1, '2016-02-18 11:03:19', '44-6sbM4zAsOC6sVTA'),
	(311, 5, 67, 1, 1, '2015-10-23 18:58:17', '44-HoQPLfS74Sq8d4L'),
	(312, 5, 69, 0, 1, '2016-02-18 11:03:20', '44-awLKMJvUTLo7pUq'),
	(313, 5, 72, 0, 1, '2016-02-18 11:03:20', '44-QD0WwdsLM3DxeUt'),
	(314, 5, 73, 0, 1, '2016-02-18 11:03:21', '44-7XYOZlDcorh2YeA'),
	(315, 5, 75, 1, 1, '2015-10-23 18:58:18', '44-h4miCQkEu887PnS'),
	(316, 5, 77, 0, 1, '2016-02-18 11:03:21', '44-HPMWJPs8m7LTIyE'),
	(317, 5, 78, 0, 1, '2016-02-18 11:03:22', '44-4F88ZihoFAptSFk'),
	(318, 5, 79, 0, 1, '2016-02-18 11:03:22', '44-ff332zhlDmkDYJZ'),
	(319, 5, 80, 1, 1, '2015-10-23 18:58:18', '44-KbHMDjrjOhNg2Fr'),
	(320, 5, 81, 1, 1, '2015-10-23 18:58:19', '44-yUtGPmAD5BgROHe'),
	(321, 5, 82, 1, 1, '2015-10-23 18:58:19', '44-pesiN3i2Pu5y7qH'),
	(322, 5, 83, 1, 1, '2015-10-23 18:58:19', '44-V9rHwfiJwwCRLkt'),
	(323, 5, 84, 1, 1, '2015-10-23 18:58:19', '44-rDHgMxcdg2AzQbc'),
	(324, 5, 85, 1, 1, '2015-10-23 18:58:19', '44-8tTZlFrnweGRnE8'),
	(325, 5, 88, 1, 1, '2015-10-23 18:58:19', '44-uYH8QEsORsh7wQM'),
	(326, 5, 89, 1, 1, '2015-10-23 18:58:20', '44-mWpr5TqxFR1qFPx'),
	(327, 2, 90, 1, 1, '2016-02-17 16:02:34', '44-OwqU8DWdfIQO8Wz'),
	(328, 3, 90, 0, 1, '2016-02-18 10:45:10', '44-kJVJ0jU34OoX9Bu'),
	(329, 4, 90, 1, 1, '2016-02-17 16:03:39', '44-yMxZOpv68woH9Yi'),
	(330, 5, 90, 1, 1, '2016-02-17 16:04:01', '44-NOSTvuXwyxi6SIH'),
	(331, 6, 10, 0, 1, '2016-03-02 11:09:29', '44-5nD5fv6phtMtApz'),
	(332, 6, 11, 0, 1, '2016-03-02 11:07:51', '44-azgJ2MwzOEzL9d4'),
	(333, 6, 12, 1, 1, '2016-02-25 17:07:23', '44-fawnEREl9p7FsfN'),
	(334, 6, 13, 0, 1, '2016-03-02 11:09:29', '44-hviPyW9lsqV3szN'),
	(335, 6, 14, 0, 1, '2016-03-02 11:09:30', '44-X14A0PkIMwYzXCi'),
	(336, 6, 15, 0, 1, '2016-03-02 11:09:30', '44-2nLjJO3Fu4Ak9aP'),
	(337, 6, 16, 0, 1, '2016-03-02 11:09:30', '44-8qhCOb0rGyMAdMP'),
	(338, 6, 20, 1, 1, '2016-02-25 17:07:23', '44-aGJkAxkQ0CUsAos'),
	(339, 6, 21, 1, 1, '2016-02-25 17:07:23', '44-c4QfPyxRFeCwHLV'),
	(340, 6, 22, 1, 1, '2016-02-25 17:07:23', '44-HN7v8iZstjU9Y3J'),
	(341, 6, 23, 1, 1, '2016-02-25 17:07:23', '44-6t3YXkpI3tr6yKX'),
	(342, 6, 24, 1, 1, '2016-02-25 17:07:23', '44-ccYHEMbRqCPAhhF'),
	(343, 6, 25, 1, 1, '2016-02-25 17:07:24', '44-TfqbPkocWuViLlC'),
	(344, 6, 26, 1, 1, '2016-02-25 17:07:24', '44-ht9MCsl5ZBv3ABO'),
	(345, 6, 27, 1, 1, '2016-02-25 17:07:24', '44-Ap31dYR0cPMwGnc'),
	(346, 6, 28, 1, 1, '2016-02-25 17:07:24', '44-jiXYcGlSnQaqbeT'),
	(347, 6, 30, 1, 1, '2016-02-25 17:07:24', '44-tpQiTYgs0yvfD9G'),
	(348, 6, 33, 1, 1, '2016-02-25 17:07:24', '44-TIU2pxhEw9Kyiie'),
	(349, 6, 34, 0, 1, '2016-03-02 11:07:51', '44-qGZ6M3F8zOBhoRs'),
	(350, 6, 35, 1, 1, '2016-02-25 17:07:24', '44-xvoB4F85l4IWmx0'),
	(351, 6, 37, 0, 1, '2016-03-02 11:09:30', '44-96dD8JFEXhz2k6g'),
	(352, 6, 38, 0, 1, '2016-03-02 11:09:30', '44-pBa08bZCQkzDkEX'),
	(353, 6, 40, 0, 1, '2016-03-02 11:09:30', '44-jb04Il5Ie9UtiFu'),
	(354, 6, 41, 1, 1, '2016-02-25 17:07:25', '44-m65vL2jAcUYr5jN'),
	(355, 6, 42, 0, 1, '2016-03-02 11:07:51', '44-wtr1B4nWvemw7hM'),
	(356, 6, 49, 1, 1, '2016-02-25 17:07:25', '44-Dnz0eJvHuMEjGmN'),
	(357, 6, 50, 1, 1, '2016-02-25 17:07:25', '44-TtZSavxYc0gzmXn'),
	(358, 6, 51, 1, 1, '2016-02-25 17:07:25', '44-Z92EoKNVWdP9O3h'),
	(359, 6, 52, 1, 1, '2016-02-25 17:07:25', '44-4liAcZQK4apN9ED'),
	(360, 6, 53, 1, 1, '2016-02-25 17:07:25', '44-FeMQqI7WQFM1pFH'),
	(361, 6, 54, 1, 1, '2016-02-25 17:07:25', '44-hf3UIu6HAFKOUE0'),
	(362, 6, 55, 1, 1, '2016-02-25 17:07:26', '44-fZMgTQsbIHRcudX'),
	(363, 6, 56, 1, 1, '2016-02-25 17:07:26', '44-ElOH83yuKN3zpRN'),
	(364, 6, 57, 1, 1, '2016-02-25 17:07:26', '44-ndEhdozCVrbaiJG'),
	(365, 6, 58, 1, 1, '2016-02-25 17:07:26', '44-HTLyvzeKYfiXtBj'),
	(366, 6, 59, 1, 1, '2016-02-25 17:07:26', '44-3vdKvmy2g0IfiyX'),
	(367, 6, 60, 1, 1, '2016-02-25 17:07:26', '44-hIIX5hiR4sgLm20'),
	(368, 6, 61, 1, 1, '2016-02-25 17:07:26', '44-WXgXRnKY55zpSaH'),
	(369, 6, 63, 1, 1, '2016-02-25 17:07:26', '44-IYOpiso2eCVy2nN'),
	(370, 6, 64, 1, 1, '2016-02-25 17:07:26', '44-wKz4b8nU0U6vM9y'),
	(371, 6, 65, 1, 1, '2016-02-25 17:07:27', '44-Tcdaobnzn3dNKqF'),
	(372, 6, 66, 1, 1, '2016-02-25 17:07:27', '44-k52shQNInWD2pDV'),
	(373, 6, 67, 1, 1, '2016-02-25 17:07:27', '44-w7cvnJfvukcCmqP'),
	(374, 6, 72, 1, 1, '2016-02-25 17:07:27', '44-EPmEd0ImxQ56ylB'),
	(375, 6, 75, 1, 1, '2016-02-25 17:07:27', '44-KfsGOTEFPspQ2IA'),
	(376, 6, 79, 1, 1, '2016-02-25 17:07:27', '44-NFgUm5BaOUZE2bH'),
	(377, 6, 80, 1, 1, '2016-02-25 17:07:27', '44-uhQSNNB3H5BguU8'),
	(378, 6, 81, 1, 1, '2016-02-25 17:07:27', '44-mNl4ON6MyP7EMNc'),
	(379, 6, 82, 1, 1, '2016-02-25 17:07:27', '44-9FenC1PetPpwSGd'),
	(380, 6, 83, 1, 1, '2016-02-25 17:07:27', '44-ztePSSzV4P6Nfo3'),
	(381, 6, 85, 1, 1, '2016-02-25 17:07:28', '44-cZ2gZyLk2UTbkCH'),
	(382, 6, 88, 1, 1, '2016-02-25 17:07:28', '44-PfVjzJtIpVm43yf'),
	(383, 6, 89, 1, 1, '2016-02-25 17:07:28', '44-1gIKnqVSDTXSvrA'),
	(384, 6, 90, 1, 1, '2016-02-25 17:07:28', '44-2YZVhKBzufnDcKJ'),
	(385, 7, 1, 1, 1, '2016-03-11 14:31:35', '44-9lGc8glByPkXTa9'),
	(386, 7, 2, 1, 1, '2016-03-11 14:31:35', '44-xkkL3Y3ope7igFt'),
	(387, 7, 3, 1, 1, '2016-03-11 14:31:35', '44-KaypAH1AFWXWiOD'),
	(388, 7, 4, 1, 1, '2016-03-11 14:31:35', '44-93HgEhqAUi5Wbvy'),
	(389, 7, 6, 1, 1, '2016-03-11 14:31:35', '44-VuCCjTCRVNYerg1'),
	(390, 7, 10, 0, 1, '2016-03-11 14:35:07', '44-lYTYMf1E5Zbdsbe'),
	(391, 7, 11, 0, 1, '2016-03-11 14:35:07', '44-NJgetdMjRfMueUf'),
	(392, 7, 12, 1, 1, '2016-03-11 14:31:35', '44-5E3CxlvmAi186fk'),
	(393, 7, 13, 0, 1, '2016-03-11 14:35:07', '44-2Kn3ONkabItP5EV'),
	(394, 7, 14, 0, 1, '2016-03-11 14:35:07', '44-70zg5q5asVLcp4C'),
	(395, 7, 15, 0, 1, '2016-03-11 14:35:07', '44-dCkTn8kPFj6iyeU'),
	(396, 7, 16, 0, 1, '2016-03-11 14:35:07', '44-ID5m3Uk5uyJhSJb'),
	(397, 7, 17, 0, 1, '2016-03-11 14:35:07', '44-JKfPXwNIJinKXXS'),
	(398, 7, 18, 1, 1, '2016-03-11 14:31:36', '44-w4AWxTukQMfZbOS'),
	(399, 7, 19, 1, 1, '2016-03-11 14:31:36', '44-YyFsuJgNNoZeMnb'),
	(400, 7, 20, 1, 1, '2016-03-11 14:31:36', '44-xJxPcG2pCOhW819'),
	(401, 7, 21, 1, 1, '2016-03-11 14:31:36', '44-VlbA9NcVtLp1GQ2'),
	(402, 7, 22, 1, 1, '2016-03-11 14:31:36', '44-Il0K23ycSh7Ljer'),
	(403, 7, 23, 1, 1, '2016-03-11 14:31:37', '44-Wl03xL8fBK6R8vj'),
	(404, 7, 24, 1, 1, '2016-03-11 14:31:37', '44-IPJSElEoHainrQs'),
	(405, 7, 25, 1, 1, '2016-03-11 14:31:37', '44-Sh8hxKUXKOx7rRP'),
	(406, 7, 26, 1, 1, '2016-03-11 14:31:37', '44-0Wq3dNPOQSIItAJ'),
	(407, 7, 27, 1, 1, '2016-03-11 14:31:37', '44-6roUSYsqBeuXwGI'),
	(408, 7, 28, 1, 1, '2016-03-11 14:31:37', '44-Obke5iJSexUArx9'),
	(409, 7, 30, 1, 1, '2016-03-11 14:31:37', '44-A05VBF4R90yKttA'),
	(410, 7, 31, 1, 1, '2016-03-11 14:31:37', '44-2K7VpSXBqDsLbYN'),
	(411, 7, 32, 1, 1, '2016-03-11 14:31:38', '44-1kOtRvzbL0ZZkIv'),
	(412, 7, 33, 1, 1, '2016-03-11 14:31:38', '44-jsPMgxjMbXJhFZL'),
	(413, 7, 34, 1, 1, '2016-03-11 14:31:38', '44-3nlLIo8nsP1Esrl'),
	(414, 7, 35, 1, 1, '2016-03-11 14:31:38', '44-NyAAJgIED2rHGXM'),
	(415, 7, 36, 1, 1, '2016-03-11 14:31:38', '44-dGdLOJujVFyHjfI'),
	(416, 7, 37, 0, 1, '2016-03-11 14:35:07', '44-7taFejB3OlCW9o2'),
	(417, 7, 38, 0, 1, '2016-03-11 14:35:08', '44-PMx2AcXQ1K2nJv4'),
	(418, 7, 39, 0, 1, '2016-03-11 14:35:08', '44-EuE7o5NoxMtcjDh'),
	(419, 7, 40, 0, 1, '2016-03-11 14:35:08', '44-DuecXREF2Nf4vJs'),
	(420, 7, 41, 1, 1, '2016-03-11 14:31:38', '44-E3L0aGdc2xKY7EX'),
	(421, 7, 42, 1, 1, '2016-03-11 14:31:38', '44-V0ReMLNIQezkHls'),
	(422, 7, 43, 1, 1, '2016-03-11 14:31:39', '44-lSk0xtE38sqgB0e'),
	(423, 7, 44, 1, 1, '2016-03-11 14:31:39', '44-ayPEYw1TZVu6wW7'),
	(424, 7, 45, 1, 1, '2016-03-11 14:31:39', '44-A1iWT1W6OzGu8U4'),
	(425, 7, 46, 1, 1, '2016-03-11 14:31:39', '44-OnxwztlLjDaKl33'),
	(426, 7, 47, 1, 1, '2016-03-11 14:31:39', '44-AYqUeCUAjvFqor7'),
	(427, 7, 48, 1, 1, '2016-03-11 14:31:39', '44-UklBVIqL0Uy24Gp'),
	(428, 7, 49, 1, 1, '2016-03-11 14:31:39', '44-uuhOQAmASI647T6'),
	(429, 7, 50, 1, 1, '2016-03-11 14:31:39', '44-2btQL9iYmv7ZEtU'),
	(430, 7, 51, 1, 1, '2016-03-11 14:31:39', '44-25hBgabpCUJ95LC'),
	(431, 7, 52, 1, 1, '2016-03-11 14:31:39', '44-LWxpGswY7sG2sCl'),
	(432, 7, 53, 1, 1, '2016-03-11 14:31:40', '44-RFZWllaC15B5FJk'),
	(433, 7, 54, 1, 1, '2016-03-11 14:31:40', '44-byeiQ97FIYlp7hw'),
	(434, 7, 55, 1, 1, '2016-03-11 14:31:40', '44-o2mW138F2S7vIit'),
	(435, 7, 56, 1, 1, '2016-03-11 14:31:40', '44-DjZtm4WKDSYryVW'),
	(436, 7, 57, 1, 1, '2016-03-11 14:31:40', '44-s4H6sHP671nrNLR'),
	(437, 7, 58, 1, 1, '2016-03-11 14:31:40', '44-Vf3OIoqzDLNZoY6'),
	(438, 7, 59, 1, 1, '2016-03-11 14:31:40', '44-gjKhdirwKw3uNqo'),
	(439, 7, 60, 1, 1, '2016-03-11 14:31:40', '44-fjbUO8vjv0Uop7I'),
	(440, 7, 61, 1, 1, '2016-03-11 14:31:41', '44-KJGvBqr5XROD6gq'),
	(441, 7, 62, 1, 1, '2016-03-11 14:31:41', '44-ctYxcnDyRHpwjz1'),
	(442, 7, 63, 1, 1, '2016-03-11 14:31:41', '44-IbfmyxhljLQQLuw'),
	(443, 7, 64, 1, 1, '2016-03-11 14:31:41', '44-YjW3ght75BUCKrQ'),
	(444, 7, 65, 1, 1, '2016-03-11 14:31:41', '44-N4kTSzEoUsUXGqH'),
	(445, 7, 66, 1, 1, '2016-03-11 14:31:41', '44-ANhcX5XJyy7sZgq'),
	(446, 7, 67, 1, 1, '2016-03-11 14:31:41', '44-CPdrguAMbYJ42DR'),
	(447, 7, 68, 1, 1, '2016-03-11 14:31:41', '44-9hgo4IVNuvaZcqX'),
	(448, 7, 69, 1, 1, '2016-03-11 14:31:41', '44-X1XAm4o03nsWwep'),
	(449, 7, 70, 1, 1, '2016-03-11 14:31:41', '44-RQRu39Z2quSNB1M'),
	(450, 7, 71, 1, 1, '2016-03-11 14:31:42', '44-OfwxinyDXfCGP3b'),
	(451, 7, 72, 1, 1, '2016-03-11 14:31:42', '44-Bod62sf8aFehGIW'),
	(452, 7, 73, 1, 1, '2016-03-11 14:31:42', '44-SlXZ5ZBKiUXLb0q'),
	(453, 7, 74, 1, 1, '2016-03-15 16:31:20', '44-fMmD3oTqsselOYi'),
	(454, 7, 75, 1, 1, '2016-03-11 14:31:42', '44-ojNNCITdBuA2tBA'),
	(455, 7, 76, 1, 1, '2016-03-11 14:31:42', '44-sZxIpSAR9X7Xwea'),
	(456, 7, 77, 1, 1, '2016-03-11 14:31:42', '44-rgU0NkVySpdhZmc'),
	(457, 7, 78, 1, 1, '2016-03-11 14:31:42', '44-L6vQGNgw1ONC2JD'),
	(458, 7, 79, 1, 1, '2016-03-11 14:31:42', '44-0KyvSKfE488pxo8'),
	(459, 7, 80, 1, 1, '2016-03-11 14:31:42', '44-andUBXfEGRDzzwI'),
	(460, 7, 81, 1, 1, '2016-03-11 14:31:42', '44-o4I2tWpqRp1dBdk'),
	(461, 7, 82, 1, 1, '2016-03-11 14:31:43', '44-Nfk3spW3HmSq8Ng'),
	(462, 7, 83, 1, 1, '2016-03-11 14:31:43', '44-giezVIOQO1OI7oG'),
	(463, 7, 84, 1, 1, '2016-03-15 16:13:33', '44-6OGX5i6PSHEBMvx'),
	(464, 7, 85, 1, 1, '2016-03-11 14:31:43', '44-AD0jet1L2zSmKeX'),
	(465, 7, 86, 1, 1, '2016-03-11 14:31:43', '44-JuhAIpxZW4HfmiG'),
	(466, 7, 87, 1, 1, '2016-03-11 14:31:43', '44-ggP4Ru6SRB5S2DM'),
	(467, 7, 88, 1, 1, '2016-03-11 14:31:43', '44-fFlRvil8NxWZ9uO'),
	(468, 7, 89, 1, 1, '2016-03-11 14:31:43', '44-HedeInLAQkD7HNS'),
	(469, 7, 90, 1, 1, '2016-03-11 14:31:44', '44-fab4lIdUlF0XHIS'),
	(470, 5, 91, 0, 1, '2016-07-14 14:24:38', '44-hlZcmt2WjFkBFem'),
	(471, 5, 92, 1, 1, '2016-05-04 15:59:01', '44-L4bxsQAn0lEYtR2'),
	(472, 5, 93, 1, 1, '2016-03-28 10:31:38', '44-hlZcmt2WjFkBFea'),
	(473, 5, 94, 0, 1, '2016-07-22 15:01:49', '44-hlZcmt2WjFkBFea'),
	(474, 2, 91, 1, 1, '2016-04-25 11:02:16', '44-V8LZ2zt0GUSG8Fw'),
	(475, 2, 92, 1, 1, '2016-05-04 15:57:37', '44-Dv99gK4Z5tB77ez'),
	(476, 2, 93, 1, 1, '2016-03-28 10:31:38', '44-hlZcmt2WjFkBFem'),
	(477, 2, 94, 0, 1, '2016-07-22 15:01:43', '44-hlZcmt2WjFkBFea');
/*!40000 ALTER TABLE `tbl_acceso_perfil` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_acople
DROP TABLE IF EXISTS `tbl_acople`;
CREATE TABLE IF NOT EXISTS `tbl_acople` (
  `PK_ACOPLE` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_EMPRESA` varchar(40) DEFAULT NULL,
  `CODIGO` varchar(40) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`PK_ACOPLE`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_acople: ~0 rows (aproximadamente)
DELETE FROM `tbl_acople`;
/*!40000 ALTER TABLE `tbl_acople` DISABLE KEYS */;
INSERT INTO `tbl_acople` (`PK_ACOPLE`, `NOMBRE_EMPRESA`, `CODIGO`, `ESTADO`) VALUES
	(1, 'SIem', '100', 1);
/*!40000 ALTER TABLE `tbl_acople` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_acople_empresa
DROP TABLE IF EXISTS `tbl_acople_empresa`;
CREATE TABLE IF NOT EXISTS `tbl_acople_empresa` (
  `PK_ACOPLE_EMPRESA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EMPRESA` int(11) DEFAULT NULL,
  `FK_ACOPLE` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`PK_ACOPLE_EMPRESA`),
  KEY `FK_FK_ACOPLE_EMPRESA` (`FK_EMPRESA`),
  KEY `FK_FK_EMPRESA_ACOPLE` (`FK_ACOPLE`),
  CONSTRAINT `FK_FK_ACOPLE_EMPRESA` FOREIGN KEY (`FK_EMPRESA`) REFERENCES `tbl_empresa` (`PK_EMPRESA`),
  CONSTRAINT `FK_FK_EMPRESA_ACOPLE` FOREIGN KEY (`FK_ACOPLE`) REFERENCES `tbl_acople` (`PK_ACOPLE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_acople_empresa: ~0 rows (aproximadamente)
DELETE FROM `tbl_acople_empresa`;
/*!40000 ALTER TABLE `tbl_acople_empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_acople_empresa` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_agrupacion
DROP TABLE IF EXISTS `tbl_agrupacion`;
CREATE TABLE IF NOT EXISTS `tbl_agrupacion` (
  `PK_AGRUPACION` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(100) DEFAULT NULL,
  `FK_EMPRESA` int(11) DEFAULT NULL,
  `APLICARTIEMPOS` tinyint(4) DEFAULT NULL,
  `ESTADO` int(11) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_AGRUPACION`),
  KEY `FK_FK_EMPRESA_AGRUPACION` (`FK_EMPRESA`),
  CONSTRAINT `FK_FK_EMPRESA_AGRUPACION` FOREIGN KEY (`FK_EMPRESA`) REFERENCES `tbl_empresa` (`PK_EMPRESA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_agrupacion: ~0 rows (aproximadamente)
DELETE FROM `tbl_agrupacion`;
/*!40000 ALTER TABLE `tbl_agrupacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_agrupacion` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_agrupacion_ruta
DROP TABLE IF EXISTS `tbl_agrupacion_ruta`;
CREATE TABLE IF NOT EXISTS `tbl_agrupacion_ruta` (
  `PK_AGRUPACION_RUTA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_AGRUPACION` int(11) DEFAULT NULL,
  `FK_RUTA` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_AGRUPACION_RUTA`),
  KEY `FK_AGRUPACION_RUTA` (`FK_AGRUPACION`),
  KEY `FK_RUTA_AGRUPACION` (`FK_RUTA`),
  CONSTRAINT `FK_AGRUPACION_RUTA` FOREIGN KEY (`FK_AGRUPACION`) REFERENCES `tbl_agrupacion` (`PK_AGRUPACION`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_RUTA_AGRUPACION` FOREIGN KEY (`FK_RUTA`) REFERENCES `tbl_ruta` (`PK_RUTA`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_agrupacion_ruta: ~0 rows (aproximadamente)
DELETE FROM `tbl_agrupacion_ruta`;
/*!40000 ALTER TABLE `tbl_agrupacion_ruta` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_agrupacion_ruta` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_agrupacion_vehiculo
DROP TABLE IF EXISTS `tbl_agrupacion_vehiculo`;
CREATE TABLE IF NOT EXISTS `tbl_agrupacion_vehiculo` (
  `PK_AGRUPACION_VEHICULO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_AGRUPACION` int(11) DEFAULT NULL,
  `FK_VEHICULO` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`PK_AGRUPACION_VEHICULO`),
  KEY `FK_FK_AGRUPACION_VEHICULO` (`FK_AGRUPACION`),
  KEY `FK_FK_VEHICULO_AGRUPACION` (`FK_VEHICULO`),
  CONSTRAINT `FK_FK_AGRUPACION_VEHICULO` FOREIGN KEY (`FK_AGRUPACION`) REFERENCES `tbl_agrupacion` (`PK_AGRUPACION`),
  CONSTRAINT `FK_FK_VEHICULO_AGRUPACION` FOREIGN KEY (`FK_VEHICULO`) REFERENCES `tbl_vehiculo` (`PK_VEHICULO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_agrupacion_vehiculo: ~0 rows (aproximadamente)
DELETE FROM `tbl_agrupacion_vehiculo`;
/*!40000 ALTER TABLE `tbl_agrupacion_vehiculo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_agrupacion_vehiculo` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_alarma
DROP TABLE IF EXISTS `tbl_alarma`;
CREATE TABLE IF NOT EXISTS `tbl_alarma` (
  `PK_ALARMA` int(11) NOT NULL AUTO_INCREMENT,
  `CODIGO_ALARMA` int(11) DEFAULT NULL,
  `NOMBRE` text,
  `TIPO` varchar(40) DEFAULT NULL,
  `UNIDAD_MEDICION` varchar(40) DEFAULT NULL,
  `PRIORIDAD` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_ALARMA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_alarma: ~0 rows (aproximadamente)
DELETE FROM `tbl_alarma`;
/*!40000 ALTER TABLE `tbl_alarma` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_alarma` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_alarma_info_regis
DROP TABLE IF EXISTS `tbl_alarma_info_regis`;
CREATE TABLE IF NOT EXISTS `tbl_alarma_info_regis` (
  `PK_AIR` int(11) NOT NULL AUTO_INCREMENT,
  `FK_ALARMA` int(11) DEFAULT NULL,
  `FK_INFORMACION_REGISTRADORA` int(11) DEFAULT NULL,
  `FECHA_ALARMA` date DEFAULT NULL,
  `HORA_ALARMA` time DEFAULT NULL,
  `CANTIDAD_ALARMA` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_AIR`),
  KEY `FK_ALARMA_INFORMACION_REGISTRADORA` (`FK_ALARMA`),
  KEY `FK_INFORMACION_REGISTRADORA_ALARMA` (`FK_INFORMACION_REGISTRADORA`),
  CONSTRAINT `FK_ALARMA_INFORMACION_REGISTRADORA` FOREIGN KEY (`FK_ALARMA`) REFERENCES `tbl_alarma` (`PK_ALARMA`),
  CONSTRAINT `FK_INFORMACION_REGISTRADORA_ALARMA` FOREIGN KEY (`FK_INFORMACION_REGISTRADORA`) REFERENCES `tbl_informacion_registradora` (`PK_INFORMACION_REGISTRADORA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_alarma_info_regis: ~0 rows (aproximadamente)
DELETE FROM `tbl_alarma_info_regis`;
/*!40000 ALTER TABLE `tbl_alarma_info_regis` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_alarma_info_regis` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_asignacion_manual_ruta
DROP TABLE IF EXISTS `tbl_asignacion_manual_ruta`;
CREATE TABLE IF NOT EXISTS `tbl_asignacion_manual_ruta` (
  `PK_ASIGNACION_MANUAL_RUTA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_VEHICULO` int(11) NOT NULL,
  `FK_RUTA` int(11) NOT NULL,
  `FK_CONDUCTOR` int(11) NOT NULL,
  `FK_USUARIO` int(11) NOT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) NOT NULL,
  PRIMARY KEY (`PK_ASIGNACION_MANUAL_RUTA`),
  KEY `FK_TBL_CONDUCTOR` (`FK_CONDUCTOR`),
  KEY `FK_TBL_USUARIO` (`FK_USUARIO`),
  KEY `FK_TBL_RUTA` (`FK_RUTA`),
  KEY `FK_TBL_VEHICULO` (`FK_VEHICULO`),
  CONSTRAINT `FK_TBL_CONDUCTOR` FOREIGN KEY (`FK_CONDUCTOR`) REFERENCES `tbl_conductor` (`PK_CONDUCTOR`),
  CONSTRAINT `FK_TBL_RUTA` FOREIGN KEY (`FK_RUTA`) REFERENCES `tbl_ruta` (`PK_RUTA`),
  CONSTRAINT `FK_TBL_USUARIO` FOREIGN KEY (`FK_USUARIO`) REFERENCES `tbl_usuario` (`PK_USUARIO`),
  CONSTRAINT `FK_TBL_VEHICULO` FOREIGN KEY (`FK_VEHICULO`) REFERENCES `tbl_vehiculo` (`PK_VEHICULO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_asignacion_manual_ruta: ~0 rows (aproximadamente)
DELETE FROM `tbl_asignacion_manual_ruta`;
/*!40000 ALTER TABLE `tbl_asignacion_manual_ruta` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_asignacion_manual_ruta` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_alarma
DROP TABLE IF EXISTS `tbl_auditoria_alarma`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_alarma` (
  `PK_AUDITORIA_ALARMA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_ALARMA` int(11) NOT NULL,
  `NOMBRE_OLD` varchar(60) DEFAULT NULL,
  `NOMBRE_NEW` varchar(60) DEFAULT NULL,
  `TIPO_OLD` varchar(40) DEFAULT NULL,
  `TIPO_NEW` varchar(40) DEFAULT NULL,
  `UNIDAD_MEDICION_OLD` varchar(40) DEFAULT NULL,
  `UNIDAD_MEDICION_NEW` varchar(40) DEFAULT NULL,
  `PRIORIDAD_OLD` int(11) DEFAULT NULL,
  `PRIORIDAD_NEW` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_ALARMA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_alarma: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_alarma`;
/*!40000 ALTER TABLE `tbl_auditoria_alarma` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_alarma` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_categoria_descuentos
DROP TABLE IF EXISTS `tbl_auditoria_categoria_descuentos`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_categoria_descuentos` (
  `PK_AUDITORIA_CATEGORIA_DESCUENTOS` bigint(20) NOT NULL AUTO_INCREMENT,
  `FK_TBL_CATEGORIA` bigint(20) NOT NULL DEFAULT '0',
  `FK_TBL_MOTIVO` int(11) NOT NULL DEFAULT '0',
  `NOMBRE_OLD` varchar(255) NOT NULL DEFAULT '0',
  `NOMBRE_NEW` varchar(255) NOT NULL DEFAULT '0',
  `DESCRIPCION_OLD` varchar(1000) NOT NULL DEFAULT '0',
  `DESCRIPCION_NEW` varchar(1000) NOT NULL DEFAULT '0',
  `APLICA_DESCUENTO_OLD` tinyint(4) NOT NULL DEFAULT '0',
  `APLICA_DESCUENTO_NEW` tinyint(4) NOT NULL DEFAULT '0',
  `ES_VALOR_MONEDA_OLD` tinyint(4) NOT NULL DEFAULT '0',
  `ES_VALOR_MONEDA_NEW` tinyint(4) NOT NULL DEFAULT '0',
  `ES_PORCENTAJE_OLD` tinyint(4) NOT NULL DEFAULT '0',
  `ES_PORCENTAJE_NEW` tinyint(4) NOT NULL DEFAULT '0',
  `ES_FIJA_OLD` tinyint(4) NOT NULL DEFAULT '0',
  `ES_FIJA_NEW` tinyint(4) NOT NULL DEFAULT '0',
  `VALOR_OLD` float NOT NULL DEFAULT '0',
  `VALOR_NEW` float NOT NULL DEFAULT '0',
  `APLICA_GENERAL_OLD` tinyint(4) NOT NULL DEFAULT '0',
  `APLICA_GENERAL_NEW` tinyint(4) NOT NULL DEFAULT '0',
  `ESTADO` smallint(6) NOT NULL DEFAULT '0',
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIOBD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`PK_AUDITORIA_CATEGORIA_DESCUENTOS`),
  KEY `FK_tbl_categoria` (`FK_TBL_CATEGORIA`),
  KEY `FK_TBL_MOTIVO` (`FK_TBL_MOTIVO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='tabla que audita la tabla categorias_descuentos';

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_categoria_descuentos: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_categoria_descuentos`;
/*!40000 ALTER TABLE `tbl_auditoria_categoria_descuentos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_categoria_descuentos` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_conductor
DROP TABLE IF EXISTS `tbl_auditoria_conductor`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_conductor` (
  `PK_AUDITORIA_CONDUCTOR` int(11) NOT NULL AUTO_INCREMENT,
  `FK_CONDUCTOR` int(11) NOT NULL,
  `NOMBRE_OLD` varchar(50) DEFAULT NULL,
  `NOMBRE_NEW` varchar(50) DEFAULT NULL,
  `APELLIDO_OLD` varchar(50) DEFAULT NULL,
  `APELLIDO_NEW` varchar(50) DEFAULT NULL,
  `CEDULA_OLD` varchar(50) DEFAULT NULL,
  `CEDULA_NEW` varchar(50) DEFAULT NULL,
  `ESTADO` int(11) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_CONDUCTOR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_conductor: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_conductor`;
/*!40000 ALTER TABLE `tbl_auditoria_conductor` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_conductor` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_empresa
DROP TABLE IF EXISTS `tbl_auditoria_empresa`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_empresa` (
  `PK_AUDITORIA_EMPRESA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EMPRESA` int(11) NOT NULL,
  `FK_CIUDAD` int(11) DEFAULT NULL,
  `FK_CIUDAD_NEW` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `NOMBRE_NEW` varchar(50) DEFAULT NULL,
  `NIT` varchar(30) DEFAULT NULL,
  `NIT_NEW` varchar(30) DEFAULT NULL,
  `ESTADO` int(11) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_EMPRESA`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_empresa: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_empresa`;
/*!40000 ALTER TABLE `tbl_auditoria_empresa` DISABLE KEYS */;
INSERT INTO `tbl_auditoria_empresa` (`PK_AUDITORIA_EMPRESA`, `FK_EMPRESA`, `FK_CIUDAD`, `FK_CIUDAD_NEW`, `NOMBRE`, `NOMBRE_NEW`, `NIT`, `NIT_NEW`, `ESTADO`, `FECHA_EVENTO`, `USUARIO`, `USUARIO_BD`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`) VALUES
	(1, 1, 1020, 16373, 'mi_empresa', 'mi_empresa', '1000', '1000', 1, '2016-10-14 13:59:47', NULL, 'root@localhost', 1, '2016-10-14 13:59:47');
/*!40000 ALTER TABLE `tbl_auditoria_empresa` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_informacion_registradora
DROP TABLE IF EXISTS `tbl_auditoria_informacion_registradora`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_informacion_registradora` (
  `PK_AUDITORIA_INFORMACION_REGISTRADORA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_INFORMACION_REGISTRADORA` int(11) DEFAULT NULL,
  `NUMERO_VUELTA_OLD` int(11) DEFAULT NULL,
  `NUMERO_VUELTA_NEW` int(11) DEFAULT NULL,
  `NUM_VUELTA_ANT_OLD` int(11) DEFAULT NULL,
  `NUM_VUELTA_ANT_NEW` int(11) DEFAULT NULL,
  `NUM_LLEGADA_OLD` int(11) DEFAULT NULL,
  `NUM_LLEGADAL_NEW` int(11) DEFAULT NULL,
  `DIFERENCIA_NUM_OLD` int(11) DEFAULT NULL,
  `DIFERENCIA_NUM_NEW` int(11) DEFAULT NULL,
  `ENTRADAS_OLD` int(11) DEFAULT NULL,
  `ENTRADAS_NEW` int(11) DEFAULT NULL,
  `DIFERENCIA_ENTRADA_OLD` int(11) DEFAULT NULL,
  `DIFERENCIA_ENTRADA_NEW` int(11) DEFAULT NULL,
  `SALIDAS_OLD` int(11) DEFAULT NULL,
  `SALIDAS_NEW` int(11) DEFAULT NULL,
  `DIFERENCIA_SALIDA_OLD` int(11) DEFAULT NULL,
  `DIFERENCIA_SALIDA_NEW` int(11) DEFAULT NULL,
  `fk_ruta_Old` int(11) DEFAULT NULL,
  `fk_ruta_New` int(11) DEFAULT NULL,
  `NUMERACION_BASE_SALIDA_OLD` int(11) DEFAULT NULL,
  `NUMERACION_BASE_SALIDA_NEW` int(11) DEFAULT NULL,
  `ENTRADAS_BASE_SALIDA_OLD` int(11) DEFAULT NULL,
  `ENTRADAS_BASE_SALIDA_NEW` int(11) DEFAULT NULL,
  `SALIDAS_BASE_SALIDA_OLD` int(11) DEFAULT NULL,
  `SALIDAS_BASE_SALIDAS_NEW` int(11) DEFAULT NULL,
  `FECHA_INGRESO_OLD` date DEFAULT NULL,
  `FECHA_INGRESO_NEW` date DEFAULT NULL,
  `HORA_INGRESO_OLD` time DEFAULT NULL,
  `HORA_INGRESO_NEW` time DEFAULT NULL,
  `FECHA_SALIDA_OLD` date DEFAULT NULL,
  `FECHA_SALIDA_NEW` date DEFAULT NULL,
  `HORA_SALIDA_OLD` time DEFAULT NULL,
  `HORA_SALIDA_NEW` time DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `TOTAL_DIA_OLD` int(11) DEFAULT NULL,
  `TOTAL_DIA_NEW` int(11) DEFAULT NULL,
  PRIMARY KEY (`PK_AUDITORIA_INFORMACION_REGISTRADORA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_informacion_registradora: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_informacion_registradora`;
/*!40000 ALTER TABLE `tbl_auditoria_informacion_registradora` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_informacion_registradora` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_macro_ruta
DROP TABLE IF EXISTS `tbl_auditoria_macro_ruta`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_macro_ruta` (
  `PK_AUDITORIA_MACRO_RUTA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_MACRO_RUTA` int(11) NOT NULL,
  `NOMBRE_OLD` varchar(50) DEFAULT NULL,
  `NOMBRE_NEW` varchar(50) DEFAULT NULL,
  `ESTADO` int(11) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FFECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_MACRO_RUTA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_macro_ruta: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_macro_ruta`;
/*!40000 ALTER TABLE `tbl_auditoria_macro_ruta` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_macro_ruta` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_perfil
DROP TABLE IF EXISTS `tbl_auditoria_perfil`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_perfil` (
  `PK_AUDITORIA_PERFIL` int(11) NOT NULL AUTO_INCREMENT,
  `FK_PERFIL` int(11) NOT NULL,
  `NOMBRE_PERFIL_OLD` varchar(50) DEFAULT NULL,
  `NOMBRE_PERFIL_NEW` varchar(50) DEFAULT NULL,
  `DESCRIPCION_OLD` text,
  `DESCRIPCION_NEW` text,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_PERFIL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_perfil: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_perfil`;
/*!40000 ALTER TABLE `tbl_auditoria_perfil` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_perfil` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_punto
DROP TABLE IF EXISTS `tbl_auditoria_punto`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_punto` (
  `PK_AUDITORIA_PUNTO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_PUNTO` int(11) NOT NULL,
  `NOMBRE_OLD` varchar(50) DEFAULT NULL,
  `NOMBRE_NEW` varchar(50) DEFAULT NULL,
  `DESCRIPCION_OLD` text,
  `DESCRIPCION_NEW` text,
  `LATITUD_OLD` varchar(20) DEFAULT NULL,
  `LATITUD_NEW` varchar(20) DEFAULT NULL,
  `LONGITUD_OLD` varchar(20) DEFAULT NULL,
  `LONGITUD_NEW` varchar(20) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_PUNTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_punto: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_punto`;
/*!40000 ALTER TABLE `tbl_auditoria_punto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_punto` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_punto_control
DROP TABLE IF EXISTS `tbl_auditoria_punto_control`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_punto_control` (
  `PK_AUDITORIA_PUNTO_CONTROL` int(11) NOT NULL AUTO_INCREMENT,
  `FK_PUNTO_CONTROL` int(11) NOT NULL,
  `HORA_PTO_CONTROL_OLD` time DEFAULT NULL,
  `FECHA_PTO_CONTROL_OLD` date DEFAULT NULL,
  `NUMERACION_OLD` int(11) DEFAULT NULL,
  `ENTRADAS_OLD` int(11) DEFAULT NULL,
  `SALIDAS_OLD` int(11) DEFAULT NULL,
  `HORA_PTO_CONTROL_NEW` time DEFAULT NULL,
  `FECHA_PTO_CONTROL_NEW` date DEFAULT NULL,
  `NUMERACION_NEW` int(11) DEFAULT NULL,
  `ENTRADAS_NEW` int(11) DEFAULT NULL,
  `SALIDAS_NEW` int(11) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_PUNTO_CONTROL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_punto_control: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_punto_control`;
/*!40000 ALTER TABLE `tbl_auditoria_punto_control` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_punto_control` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_ruta
DROP TABLE IF EXISTS `tbl_auditoria_ruta`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_ruta` (
  `PK_AUDITORIA_RUTA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_RUTA` int(11) NOT NULL,
  `NOMBRE_OLD` varchar(50) DEFAULT NULL,
  `NOMBRE_NEW` varchar(50) DEFAULT NULL,
  `ESTADO` int(11) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_RUTA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_ruta: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_ruta`;
/*!40000 ALTER TABLE `tbl_auditoria_ruta` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_ruta` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_ruta_punto
DROP TABLE IF EXISTS `tbl_auditoria_ruta_punto`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_ruta_punto` (
  `PK_AUDITORIA_RUTA_PUNTO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_RUTA_PUNTO` int(11) NOT NULL,
  `PROMEDIO_MINUTOS_OLD` int(11) DEFAULT NULL,
  `HOLGURA_MINUTOS_OLD` int(11) DEFAULT NULL,
  `PROMEDIO_MINUTOS_NEW` int(11) DEFAULT NULL,
  `HOLGURA_MINUTOS_NEW` int(11) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_RUTA_PUNTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_ruta_punto: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_ruta_punto`;
/*!40000 ALTER TABLE `tbl_auditoria_ruta_punto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_ruta_punto` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_tarifa
DROP TABLE IF EXISTS `tbl_auditoria_tarifa`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_tarifa` (
  `PK_AUDITORIA_TARIFA` int(11) NOT NULL AUTO_INCREMENT,
  `Fk_TARIFA` int(11) NOT NULL,
  `FK_RUTA` int(11) NOT NULL,
  `VALOR_PROM_KM_OLD` double DEFAULT NULL,
  `VALOR_PROM_KM_NEW` double DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `FK_RUTA_OLD` int(11) DEFAULT NULL,
  PRIMARY KEY (`PK_AUDITORIA_TARIFA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_tarifa: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_tarifa`;
/*!40000 ALTER TABLE `tbl_auditoria_tarifa` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_tarifa` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_usuario
DROP TABLE IF EXISTS `tbl_auditoria_usuario`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_usuario` (
  `PK_AUDITORIA_USUARIO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_USUARIO` int(11) DEFAULT NULL,
  `FK_PERFIL_OLD` int(11) DEFAULT NULL,
  `FK_PERFIL_NEW` int(11) DEFAULT NULL,
  `CEDULA_OLD` int(11) DEFAULT NULL,
  `CEDULA_NEW` int(11) DEFAULT NULL,
  `NOMBRE_OLD` varchar(50) DEFAULT NULL,
  `NOMBRE_NEW` varchar(50) DEFAULT NULL,
  `APELLIDO_OLD` varchar(50) DEFAULT NULL,
  `APELLIDO_NEW` varchar(50) DEFAULT NULL,
  `EMAIL_OLD` varchar(30) DEFAULT NULL,
  `EMAIL_NEW` varchar(30) DEFAULT NULL,
  `LOGIN_OLD` varchar(20) DEFAULT NULL,
  `LOGIN_NEW` varchar(20) DEFAULT NULL,
  `CONTRASENA_OLD` varchar(20) DEFAULT NULL,
  `CONTRASENA_NEW` varchar(20) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_usuario: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_usuario`;
/*!40000 ALTER TABLE `tbl_auditoria_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_usuario` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_auditoria_vehiculo
DROP TABLE IF EXISTS `tbl_auditoria_vehiculo`;
CREATE TABLE IF NOT EXISTS `tbl_auditoria_vehiculo` (
  `PK_AUDITORIA_VECHICULO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_VEHICULO` int(11) NOT NULL,
  `PLACA_OLD` varchar(10) DEFAULT NULL,
  `PLACA_NEW` varchar(10) DEFAULT NULL,
  `NUM_INTERNO_OLD` varchar(10) DEFAULT NULL,
  `NUM_INTERNO_NEW` varchar(10) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_EVENTO` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USUARIO` int(11) DEFAULT NULL,
  `USUARIO_BD` varchar(30) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_AUDITORIA_VECHICULO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_auditoria_vehiculo: ~0 rows (aproximadamente)
DELETE FROM `tbl_auditoria_vehiculo`;
/*!40000 ALTER TABLE `tbl_auditoria_vehiculo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_auditoria_vehiculo` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_base
DROP TABLE IF EXISTS `tbl_base`;
CREATE TABLE IF NOT EXISTS `tbl_base` (
  `PK_BASE` int(11) NOT NULL AUTO_INCREMENT,
  `IDENTIFICADOR_BASE` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `CODIGO_BASE` int(11) NOT NULL,
  `LATITUD` varchar(20) DEFAULT NULL,
  `RADIO` int(11) DEFAULT NULL,
  `LONGITUD` varchar(20) DEFAULT NULL,
  `DIRECCION_LONGITUD` varchar(20) DEFAULT NULL,
  `DIRECCION` int(11) DEFAULT NULL,
  `DIRECCION_LATITUD` varchar(20) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_BASE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_base: ~0 rows (aproximadamente)
DELETE FROM `tbl_base`;
/*!40000 ALTER TABLE `tbl_base` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_base` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_categoria_descuento
DROP TABLE IF EXISTS `tbl_categoria_descuento`;
CREATE TABLE IF NOT EXISTS `tbl_categoria_descuento` (
  `PK_CATEGORIAS_DESCUENTOS` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(255) NOT NULL DEFAULT '0',
  `DESCRIPCION` varchar(1000) DEFAULT '0',
  `VALOR` float NOT NULL DEFAULT '0',
  `APLICA_DESCUENTO` smallint(6) DEFAULT '0',
  `APLICA_GENERAL` bigint(20) DEFAULT '0',
  `ES_VALOR_MONEDA` smallint(6) DEFAULT '0',
  `ES_PORCENTAJE` smallint(6) DEFAULT '0',
  `ES_FIJA` smallint(6) DEFAULT '0',
  `DESCUENTA_PASAJEROS` smallint(6) DEFAULT '0',
  `ESTADO` smallint(6) DEFAULT '0',
  `FECHA_CREACION` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `FECHA_MODIFICACION` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_CATEGORIAS_DESCUENTOS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='tabla que relaciona los descuentos con una categoria.';

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_categoria_descuento: ~0 rows (aproximadamente)
DELETE FROM `tbl_categoria_descuento`;
/*!40000 ALTER TABLE `tbl_categoria_descuento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_categoria_descuento` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_ciudad
DROP TABLE IF EXISTS `tbl_ciudad`;
CREATE TABLE IF NOT EXISTS `tbl_ciudad` (
  `PK_CIUDAD` int(11) NOT NULL,
  `FK_DEPARTAMENTO` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PK_CIUDAD`),
  KEY `FK_CIUDAD_DEPARTAMENTO` (`FK_DEPARTAMENTO`),
  CONSTRAINT `FK_CIUDAD_DEPARTAMENTO` FOREIGN KEY (`FK_DEPARTAMENTO`) REFERENCES `tbl_departamento` (`PK_DEPARTAMENTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_ciudad: ~1.092 rows (aproximadamente)
DELETE FROM `tbl_ciudad`;
/*!40000 ALTER TABLE `tbl_ciudad` DISABLE KEYS */;
INSERT INTO `tbl_ciudad` (`PK_CIUDAD`, `FK_DEPARTAMENTO`, `NOMBRE`) VALUES
	(16373, 553, 'ABEJORRAL'),
	(16374, 553, 'ABRIAQUI'),
	(16375, 553, 'ALEJANDRIA'),
	(16376, 553, 'AMAGA'),
	(16377, 553, 'AMALFI'),
	(16378, 553, 'ANDES'),
	(16379, 553, 'ANGELOPOLIS'),
	(16380, 553, 'ANGOSTURA'),
	(16381, 553, 'ANORI'),
	(16382, 553, 'ANTIOQUIA'),
	(16383, 553, 'ANZA'),
	(16384, 553, 'APARTADO'),
	(16385, 553, 'ARBOLETES'),
	(16386, 553, 'ARGELIA'),
	(16387, 553, 'ARMENIA'),
	(16388, 553, 'BARBOSA'),
	(16389, 553, 'BELLO'),
	(16390, 553, 'BELMIRA'),
	(16391, 553, 'BETANIA'),
	(16392, 553, 'BETULIA'),
	(16393, 553, 'BOLIVAR'),
	(16394, 553, 'BRICENO'),
	(16395, 553, 'BURITICA'),
	(16396, 553, 'CANASGORDAS'),
	(16397, 553, 'CACERES'),
	(16398, 553, 'CAICEDO'),
	(16399, 553, 'CALDAS'),
	(16400, 553, 'CAMPAMENTO'),
	(16401, 553, 'CARACOLI'),
	(16402, 553, 'CARAMANTA'),
	(16403, 553, 'CAREPA'),
	(16404, 553, 'CARMEN DE VIBORAL'),
	(16405, 553, 'CAROLINA'),
	(16406, 553, 'CAUCASIA'),
	(16407, 553, 'CHIGORODO'),
	(16408, 553, 'CISNEROS'),
	(16409, 553, 'COCORNA'),
	(16410, 553, 'CONCEPCION'),
	(16411, 553, 'CONCORDIA'),
	(16412, 553, 'COPACABANA'),
	(16413, 553, 'DABEIBA'),
	(16414, 553, 'DON MATIAS'),
	(16415, 553, 'EBEJICO'),
	(16416, 553, 'EL BAGRE'),
	(16417, 553, 'ENTRERRIOS'),
	(16418, 553, 'ENVIGADO'),
	(16419, 553, 'FREDONIA'),
	(16420, 553, 'FRONTINO'),
	(16421, 553, 'GIRALDO'),
	(16422, 553, 'GIRARDOTA'),
	(16423, 553, 'GOMEZ PLATA'),
	(16424, 553, 'GRANADA'),
	(16425, 553, 'GUADALUPE'),
	(16426, 553, 'GUARNE'),
	(16427, 553, 'GUATAPE'),
	(16428, 553, 'HELICONIA'),
	(16429, 553, 'HISPANIA'),
	(16430, 553, 'ITUANGO'),
	(16431, 553, 'JARDIN'),
	(16432, 553, 'JERICO'),
	(16433, 553, 'LA CEJA'),
	(16434, 553, 'LA ESTRELLA'),
	(16435, 553, 'LA PINTADA'),
	(16436, 553, 'LA UNION'),
	(16437, 553, 'LIBORINA'),
	(16438, 553, 'MACEO'),
	(16439, 553, 'MARINILLA'),
	(16440, 553, 'MEDELLIN'),
	(16441, 553, 'MONTEBELLO'),
	(16442, 553, 'MURINDO'),
	(16443, 553, 'MUTATA'),
	(16444, 553, 'NARINO'),
	(16445, 553, 'NECHI'),
	(16446, 553, 'NECOCLI'),
	(16447, 553, 'OLAYA'),
	(16448, 553, 'PENOL'),
	(16449, 553, 'PEQUE'),
	(16450, 553, 'PUEBLORRICO'),
	(16451, 553, 'PUERTO BERRIO'),
	(16452, 553, 'PUERTO NARE'),
	(16453, 553, 'PUERTO TRIUNFO'),
	(16454, 553, 'REMEDIOS'),
	(16455, 553, 'RETIRO'),
	(16456, 553, 'RIONEGRO'),
	(16457, 553, 'SABANALARGA'),
	(16458, 553, 'SABANETA'),
	(16459, 553, 'SALGAR'),
	(16460, 553, 'SAN ANDRES'),
	(16461, 553, 'SAN CARLOS'),
	(16462, 553, 'SAN FRANCISCO'),
	(16463, 553, 'SAN JERONIMO'),
	(16464, 553, 'SAN JOSE DE LA MONTANA'),
	(16465, 553, 'SAN JUAN DE URABA'),
	(16466, 553, 'SAN LUIS'),
	(16467, 553, 'SAN PEDRO'),
	(16468, 553, 'SAN PEDRO DE URABA'),
	(16469, 553, 'SAN RAFAEL'),
	(16470, 553, 'SAN ROQUE'),
	(16471, 553, 'SAN VICENTE'),
	(16472, 553, 'SANTA BARBARA'),
	(16473, 553, 'SANTA ROSA DE OSOS'),
	(16474, 553, 'SANTO DOMINGO'),
	(16475, 553, 'SANTUARIO'),
	(16476, 553, 'SEGOVIA'),
	(16477, 553, 'SONSON'),
	(16478, 553, 'SOPETRAN'),
	(16479, 553, 'TAMESIS'),
	(16480, 553, 'TARAZA'),
	(16481, 553, 'TARSO'),
	(16482, 553, 'TITIRIBI'),
	(16483, 553, 'TOLEDO'),
	(16484, 553, 'TURBO'),
	(16485, 553, 'URAMITA'),
	(16486, 553, 'URRAO'),
	(16487, 553, 'VALDIVIA'),
	(16488, 553, 'VALPARAISO'),
	(16489, 553, 'VEGACHI'),
	(16490, 553, 'VENECIA'),
	(16491, 553, 'VIGIA DEL FUERTE'),
	(16492, 553, 'YALI'),
	(16493, 553, 'YARUMAL'),
	(16494, 553, 'YOLOMBO'),
	(16495, 553, 'YONDO'),
	(16496, 553, 'ZARAGOZA'),
	(16497, 554, 'ARAUCA'),
	(16498, 554, 'ARAUQUITA'),
	(16499, 554, 'CRAVO NORTE'),
	(16500, 554, 'FORTUL'),
	(16501, 554, 'PUERTO RONDON'),
	(16502, 554, 'SARAVENA'),
	(16503, 554, 'TAME'),
	(16504, 555, 'BARANOA'),
	(16505, 555, 'BARRANQUILLA'),
	(16506, 555, 'CAMPO DE LA CRUZ'),
	(16507, 555, 'CANDELARIA'),
	(16508, 555, 'GALAPA'),
	(16509, 555, 'JUAN DE ACOSTA'),
	(16510, 555, 'LURUACO'),
	(16511, 555, 'MALAMBO'),
	(16512, 555, 'MANATI'),
	(16513, 555, 'PALMAR DE VARELA'),
	(16514, 555, 'PIOJO'),
	(16515, 555, 'POLO NUEVO'),
	(16516, 555, 'PONEDERA'),
	(16517, 555, 'PUERTO COLOMBIA'),
	(16518, 555, 'REPELON'),
	(16519, 555, 'SABANAGRANDE'),
	(16520, 555, 'SABANALARGA'),
	(16521, 555, 'SANTA LUCIA'),
	(16522, 555, 'SANTO TOMAS'),
	(16523, 555, 'SOLEDAD'),
	(16524, 555, 'SUAN'),
	(16525, 555, 'TUBARA'),
	(16526, 555, 'USIACURI'),
	(16527, 565, 'BOGOTA'),
	(16528, 557, 'ACHI'),
	(16529, 557, 'ALTOS DEL ROSARIO'),
	(16530, 557, 'ARENAL'),
	(16531, 557, 'ARJONA'),
	(16532, 557, 'ARROYOHONDO'),
	(16533, 557, 'BARRANCO DE LOBA'),
	(16534, 557, 'CALAMAR'),
	(16535, 557, 'CANTAGALLO'),
	(16536, 557, 'CARTAGENA'),
	(16537, 557, 'CICUCO'),
	(16538, 557, 'CLEMENCIA'),
	(16539, 557, 'CORDOBA'),
	(16540, 557, 'EL CARMEN DE BOLIVAR'),
	(16541, 557, 'EL GUAMO'),
	(16542, 557, 'EL PENON'),
	(16543, 557, 'HATILLO DE LOBA'),
	(16544, 557, 'MAGANGUE'),
	(16545, 557, 'MAHATES'),
	(16546, 557, 'MARGARITA'),
	(16547, 557, 'MARIA LA BAJA'),
	(16548, 557, 'MOMPOS'),
	(16549, 557, 'MONTECRISTO'),
	(16550, 557, 'MORALES'),
	(16551, 557, 'PINILLOS'),
	(16552, 557, 'REGIDOR'),
	(16553, 557, 'RIO VIEJO'),
	(16554, 557, 'SAN CRISTOBAL'),
	(16555, 557, 'SAN ESTANISLAO'),
	(16556, 557, 'SAN FERNANDO'),
	(16557, 557, 'SAN JACINTO'),
	(16558, 557, 'SAN JACINTO DEL CAUCA'),
	(16559, 557, 'SAN JUAN NEPOMUCENO'),
	(16560, 557, 'SAN MARTIN DE LOBA'),
	(16561, 557, 'SAN PABLO'),
	(16562, 557, 'SANTA CATALINA'),
	(16563, 557, 'SANTA ROSA'),
	(16564, 557, 'SANTA ROSA DEL SUR'),
	(16565, 557, 'SIMITI'),
	(16566, 557, 'SOPLAVIENTO'),
	(16567, 557, 'TALAIGUA'),
	(16568, 557, 'TIQUISIO'),
	(16569, 557, 'TURBACO'),
	(16570, 557, 'TURBANA'),
	(16571, 557, 'VILLANUEVA'),
	(16572, 557, 'ZAMBRANO'),
	(16573, 558, 'ALMEIDA'),
	(16574, 558, 'AQUITANIA'),
	(16575, 558, 'ARCABUCO'),
	(16576, 558, 'BELEN'),
	(16577, 558, 'BERBEO'),
	(16578, 558, 'BETEITIVA'),
	(16579, 558, 'BOAVITA'),
	(16580, 558, 'BOYACA'),
	(16581, 558, 'BRICENO'),
	(16582, 558, 'BUENAVISTA'),
	(16583, 558, 'BUSBANZA'),
	(16584, 558, 'CALDAS'),
	(16585, 558, 'CAMPOHERMOSO'),
	(16586, 558, 'CERINZA'),
	(16587, 558, 'CHINAVITA'),
	(16588, 558, 'CHIQUINQUIRA'),
	(16589, 558, 'CHIQUIZA'),
	(16590, 558, 'CHISCAS'),
	(16591, 558, 'CHITA'),
	(16592, 558, 'CHITARAQUE'),
	(16593, 558, 'CHIVATA'),
	(16594, 558, 'CHIVOR'),
	(16595, 558, 'CIENEGA'),
	(16596, 558, 'COMBITA'),
	(16597, 558, 'COPER'),
	(16598, 558, 'CORRALES'),
	(16599, 558, 'COVARACHIA'),
	(16600, 558, 'CUBARA'),
	(16601, 558, 'CUCAITA'),
	(16602, 558, 'CUITIVA'),
	(16603, 558, 'DUITAMA'),
	(16604, 558, 'EL COCUY'),
	(16605, 558, 'EL ESPINO'),
	(16606, 558, 'FIRAVITOBA'),
	(16607, 558, 'FLORESTA'),
	(16608, 558, 'GACHANTIVA'),
	(16609, 558, 'GAMEZA'),
	(16610, 558, 'GARAGOA'),
	(16611, 558, 'GUACAMAYAS'),
	(16612, 558, 'GUATEQUE'),
	(16613, 558, 'GUAYATA'),
	(16614, 558, 'IZA'),
	(16615, 558, 'JENESANO'),
	(16616, 558, 'JERICO'),
	(16617, 558, 'LA CAPILLA'),
	(16618, 558, 'LA UVITA'),
	(16619, 558, 'LA VICTORIA'),
	(16620, 558, 'LABRANZAGRANDE'),
	(16621, 558, 'LEIVA'),
	(16622, 558, 'MACANAL'),
	(16623, 558, 'MARIPI'),
	(16624, 558, 'MIRAFLORES'),
	(16625, 558, 'MONGUA'),
	(16626, 558, 'MONGUI'),
	(16627, 558, 'MONIQUIRA'),
	(16628, 558, 'MOTAVITA'),
	(16629, 558, 'MUZO'),
	(16630, 558, 'NOBSA'),
	(16631, 558, 'NUEVO COLON'),
	(16632, 558, 'OICATA'),
	(16633, 558, 'OTANCHE'),
	(16634, 558, 'PACHAVITA'),
	(16635, 558, 'PAEZ'),
	(16636, 558, 'PAIPA'),
	(16637, 558, 'PAJARITO'),
	(16638, 558, 'PANQUEBA'),
	(16639, 558, 'PAUNA'),
	(16640, 558, 'PAYA'),
	(16641, 558, 'PAZ DEL RIO'),
	(16642, 558, 'PESCA'),
	(16643, 558, 'PISBA'),
	(16644, 558, 'PUERTO BOYACA'),
	(16645, 558, 'QUIPAMA'),
	(16646, 558, 'RAMIRIQUI'),
	(16647, 558, 'RAQUIRA'),
	(16648, 558, 'RONDON'),
	(16649, 558, 'SABOYA'),
	(16650, 558, 'SACHICA'),
	(16651, 558, 'SAMACA'),
	(16652, 558, 'SAN EDUARDO'),
	(16653, 558, 'SAN JOSE DE PARE'),
	(16654, 558, 'SAN LUIS DE GACENO'),
	(16655, 558, 'SAN MATEO'),
	(16656, 558, 'SAN MIGUEL DE SEMA'),
	(16657, 558, 'SAN PABLO DE BORBUR'),
	(16658, 558, 'SANTA MARIA'),
	(16659, 558, 'SANTA ROSA DE VITERBO'),
	(16660, 558, 'SANTA SOFIA'),
	(16661, 558, 'SANTANA'),
	(16662, 558, 'SATIVANORTE'),
	(16663, 558, 'SATIVASUR'),
	(16664, 558, 'SIACHOQUE'),
	(16665, 558, 'SOATA'),
	(16666, 558, 'SOCHA'),
	(16667, 558, 'SOCOTA'),
	(16668, 558, 'SOGAMOSO'),
	(16669, 558, 'SOMONDOCO'),
	(16670, 558, 'SORA'),
	(16671, 558, 'SORACA'),
	(16672, 558, 'SOTAQUIRA'),
	(16673, 558, 'SUSACON'),
	(16674, 558, 'SUTAMARCHAN'),
	(16675, 558, 'SUTATENZA'),
	(16676, 558, 'TASCO'),
	(16677, 558, 'TENZA'),
	(16678, 558, 'TIBANA'),
	(16679, 558, 'TIBASOSA'),
	(16680, 558, 'TINJACA'),
	(16681, 558, 'TIPACOQUE'),
	(16682, 558, 'TOCA'),
	(16683, 558, 'TOPAGA'),
	(16684, 558, 'TOTA'),
	(16685, 558, 'TUNJA'),
	(16686, 558, 'TUNUNGUA'),
	(16687, 558, 'TURMEQUE'),
	(16688, 558, 'TUTA'),
	(16689, 558, 'TUTASA'),
	(16690, 558, 'UMBITA'),
	(16691, 558, 'VENTAQUEMADA'),
	(16692, 558, 'VIRACACHA'),
	(16693, 558, 'ZETAQUIRA'),
	(16694, 559, 'AGUADAS'),
	(16695, 559, 'ANSERMA'),
	(16696, 559, 'ARANZAZU'),
	(16697, 559, 'BELALCAZAR'),
	(16698, 559, 'CHINCHINA'),
	(16699, 559, 'FILADELFIA'),
	(16700, 559, 'LA DORADA'),
	(16701, 559, 'LA MERCED'),
	(16702, 559, 'MANIZALES'),
	(16703, 559, 'MANZANARES'),
	(16704, 559, 'MARMATO'),
	(16705, 559, 'MARQUETALIA'),
	(16706, 559, 'MARULANDA'),
	(16707, 559, 'NEIRA'),
	(16708, 559, 'NORCASIA'),
	(16709, 559, 'PACORA'),
	(16710, 559, 'PALESTINA'),
	(16711, 559, 'PENSILVANIA'),
	(16712, 559, 'RIOSUCIO'),
	(16713, 559, 'RISARALDA'),
	(16714, 559, 'SALAMINA'),
	(16715, 559, 'SAMANA'),
	(16716, 559, 'SAN JOSE'),
	(16717, 559, 'SUPIA'),
	(16718, 559, 'VICTORIA'),
	(16719, 559, 'VILLAMARIA'),
	(16720, 559, 'VITERBO'),
	(16721, 560, 'ALBANIA'),
	(16722, 560, 'BELEN ANDAQUIES'),
	(16723, 560, 'CARTAGENA DEL CHAIRA'),
	(16724, 560, 'CURILLO'),
	(16725, 560, 'EL DONCELLO'),
	(16726, 560, 'EL PAUJIL'),
	(16727, 560, 'FLORENCIA'),
	(16728, 560, 'LA MONTANITA'),
	(16729, 560, 'MILAN'),
	(16730, 560, 'MORELIA'),
	(16731, 560, 'PUERTO RICO'),
	(16732, 560, 'SAN JOSE DE FRAGUA'),
	(16733, 560, 'SAN VICENTE DEL CAGUAN'),
	(16734, 560, 'SOLANO'),
	(16735, 560, 'SOLITA'),
	(16736, 560, 'VALPARAISO'),
	(16737, 561, 'AGUAZUL'),
	(16738, 561, 'CHAMEZA'),
	(16739, 561, 'HATO COROZAL'),
	(16740, 561, 'LA SALINA'),
	(16741, 561, 'MANI'),
	(16742, 561, 'MONTERREY'),
	(16743, 561, 'NUNCHIA'),
	(16744, 561, 'OROCUE'),
	(16745, 561, 'PAZ DE ARIPORO'),
	(16746, 561, 'PORE'),
	(16747, 561, 'RECETOR'),
	(16748, 561, 'SABANALARGA'),
	(16749, 561, 'SACAMA'),
	(16750, 561, 'SAN LUIS DE PALENQUE'),
	(16751, 561, 'TAMARA'),
	(16752, 561, 'TAURAMENA'),
	(16753, 561, 'TRINIDAD'),
	(16754, 561, 'VILLANUEVA'),
	(16755, 561, 'YOPAL'),
	(16756, 562, 'ALMAGUER'),
	(16757, 562, 'ARGELIA'),
	(16758, 562, 'BALBOA'),
	(16759, 562, 'BOLIVAR'),
	(16760, 562, 'BUENOS AIRES'),
	(16761, 562, 'CAJIBIO'),
	(16762, 562, 'CALDONO'),
	(16763, 562, 'CALOTO'),
	(16764, 562, 'CORINTO'),
	(16765, 562, 'EL BORDO'),
	(16766, 562, 'EL TAMBO'),
	(16767, 562, 'FLORENCIA'),
	(16768, 562, 'GUAPI'),
	(16769, 562, 'INZA'),
	(16770, 562, 'JAMBALO'),
	(16771, 562, 'LA SIERRA'),
	(16772, 562, 'LA VEGA'),
	(16773, 562, 'LOPEZ'),
	(16774, 562, 'MERCADERES'),
	(16775, 562, 'MIRANDA'),
	(16776, 562, 'MORALES'),
	(16777, 562, 'PADILLA'),
	(16778, 562, 'PAEZ'),
	(16779, 562, 'PIAMONTE'),
	(16780, 562, 'PIENDAMO'),
	(16781, 562, 'POPAYAN'),
	(16782, 562, 'PUERTO TEJADA'),
	(16783, 562, 'PURACE'),
	(16784, 562, 'ROSAS'),
	(16785, 562, 'SAN SEBASTIAN'),
	(16786, 562, 'SANTA ROSA'),
	(16787, 562, 'SANTANDER DE QUILICHAO'),
	(16788, 562, 'SILVIA'),
	(16789, 562, 'SOTARA'),
	(16790, 562, 'SUAREZ'),
	(16791, 562, 'SUCRE'),
	(16792, 562, 'TIMBIO'),
	(16793, 562, 'TIMBIQUI'),
	(16794, 562, 'TORIBIO'),
	(16795, 562, 'TOTORO'),
	(16796, 562, 'VILLA RICA'),
	(16797, 563, 'AGUACHICA'),
	(16798, 563, 'AGUSTIN CODAZZI'),
	(16799, 563, 'ASTREA'),
	(16800, 563, 'BECERRIL'),
	(16801, 563, 'BOSCONIA'),
	(16802, 563, 'CHIMICHAGUA'),
	(16803, 563, 'CHIRIGUANA'),
	(16804, 563, 'CURUMANI'),
	(16805, 563, 'EL COPEY'),
	(16806, 563, 'EL PASO'),
	(16807, 563, 'GAMARRA'),
	(16808, 563, 'GONZALEZ'),
	(16809, 563, 'LA GLORIA'),
	(16810, 563, 'LA JAGUA IBIRICO'),
	(16811, 563, 'MANAURE'),
	(16812, 563, 'PAILITAS'),
	(16813, 563, 'PELAYA'),
	(16814, 563, 'PUEBLO BELLO'),
	(16815, 563, 'RIO DE ORO'),
	(16816, 563, 'ROBLES LA PAZ'),
	(16817, 563, 'SAN ALBERTO'),
	(16818, 563, 'SAN DIEGO'),
	(16819, 563, 'SAN MARTIN'),
	(16820, 563, 'TAMALAMEQUE'),
	(16821, 563, 'VALLEDUPAR'),
	(16822, 564, 'ACANDI'),
	(16823, 564, 'ALTO BAUDO'),
	(16824, 564, 'ATRATO'),
	(16825, 564, 'BAGADO'),
	(16826, 564, 'BAJO BAUDO'),
	(16827, 564, 'BOJAYA'),
	(16828, 564, 'CANTON DE SAN PABLO'),
	(16829, 564, 'CARMEN DEL DARIEN'),
	(16830, 564, 'CERTEGUI'),
	(16831, 564, 'CONDOTO'),
	(16832, 564, 'EL CARMEN'),
	(16833, 564, 'ISTMINA'),
	(16834, 564, 'JURADO'),
	(16835, 564, 'LITORAL DEL SAN JUAN'),
	(16836, 564, 'LLORO'),
	(16837, 564, 'MEDIO ATRATO'),
	(16838, 564, 'MEDIO BAUDO'),
	(16839, 564, 'MEDIO SAN JUAN'),
	(16840, 564, 'MUTIS'),
	(16841, 564, 'NOVITA'),
	(16842, 564, 'NUQUI'),
	(16843, 564, 'QUIBDO'),
	(16844, 564, 'RIO IRO'),
	(16845, 564, 'RIO QUITO'),
	(16846, 564, 'RIOSUCIO'),
	(16847, 564, 'SAN JOSE DEL PALMAR'),
	(16848, 564, 'SIPI'),
	(16849, 564, 'TADO'),
	(16850, 564, 'UNGUIA'),
	(16851, 564, 'UNION PANAMERICANA'),
	(16852, 565, 'AGUA DE DIOS'),
	(16853, 565, 'ALBAN'),
	(16854, 565, 'ANAPOIMA'),
	(16855, 565, 'ANOLAIMA'),
	(16856, 565, 'ARBELAEZ'),
	(16857, 565, 'BELTRAN'),
	(16858, 565, 'BITUIMA'),
	(16859, 565, 'BOJACA'),
	(16860, 565, 'CABRERA'),
	(16861, 565, 'CACHIPAY'),
	(16862, 565, 'CAJICA'),
	(16863, 565, 'CAPARRAPI'),
	(16864, 565, 'CAQUEZA'),
	(16865, 565, 'CARMEN DE CARUPA'),
	(16866, 565, 'CHAGUANI'),
	(16867, 565, 'CHIA'),
	(16868, 565, 'CHIPAQUE'),
	(16869, 565, 'CHOACHI'),
	(16870, 565, 'CHOCONTA'),
	(16871, 565, 'COGUA'),
	(16872, 565, 'COTA'),
	(16873, 565, 'CUCUNUBA'),
	(16874, 565, 'EL COLEGIO'),
	(16875, 565, 'EL PENON'),
	(16876, 565, 'EL ROSAL'),
	(16877, 565, 'FACATATIVA'),
	(16878, 565, 'FOMEQUE'),
	(16879, 565, 'FOSCA'),
	(16880, 565, 'FUNZA'),
	(16881, 565, 'FUQUENE'),
	(16882, 565, 'FUSAGASUGA'),
	(16883, 565, 'GACHALA'),
	(16884, 565, 'GACHANCIPA'),
	(16885, 565, 'GACHETA'),
	(16886, 565, 'GAMA'),
	(16887, 565, 'GIRARDOT'),
	(16888, 565, 'GRANADA'),
	(16889, 565, 'GUACHETA'),
	(16890, 565, 'GUADUAS'),
	(16891, 565, 'GUASCA'),
	(16892, 565, 'GUATAQUI'),
	(16893, 565, 'GUATAVITA'),
	(16894, 565, 'GUAYABAL DE SIQUIMA'),
	(16895, 565, 'GUAYABETAL'),
	(16896, 565, 'GUTIERREZ'),
	(16897, 565, 'JERUSALEN'),
	(16898, 565, 'JUNIN'),
	(16899, 565, 'LA CALERA'),
	(16900, 565, 'LA MESA'),
	(16901, 565, 'LA PALMA'),
	(16902, 565, 'LA PENA'),
	(16903, 565, 'LA VEGA'),
	(16904, 565, 'LENGUAZAQUE'),
	(16905, 565, 'MACHETA'),
	(16906, 565, 'MADRID'),
	(16907, 565, 'MANTA'),
	(16908, 565, 'MEDINA'),
	(16909, 565, 'MOSQUERA'),
	(16910, 565, 'NARINO'),
	(16911, 565, 'NEMOCON'),
	(16912, 565, 'NILO'),
	(16913, 565, 'NIMAIMA'),
	(16914, 565, 'NOCAIMA'),
	(16915, 565, 'OSPINA PEREZ'),
	(16916, 565, 'PACHO'),
	(16917, 565, 'PAIME'),
	(16918, 565, 'PANDI'),
	(16919, 565, 'PARATEBUENO'),
	(16920, 565, 'PASCA'),
	(16921, 565, 'PUERTO SALGAR'),
	(16922, 565, 'PULI'),
	(16923, 565, 'QUEBRADANEGRA'),
	(16924, 565, 'QUETAME'),
	(16925, 565, 'QUIPILE'),
	(16926, 565, 'RAFAEL REYES'),
	(16927, 565, 'RICAURTE'),
	(16928, 565, 'SAN ANTONIO DEL TEQUENDAMA'),
	(16929, 565, 'SAN BERNARDO'),
	(16930, 565, 'SAN CAYETANO'),
	(16931, 565, 'SAN FRANCISCO'),
	(16932, 565, 'SAN JUAN DE RIOSECO'),
	(16933, 565, 'SASAIMA'),
	(16934, 565, 'SESQUILE'),
	(16935, 565, 'SIBATE'),
	(16936, 565, 'SILVANIA'),
	(16937, 565, 'SIMIJACA'),
	(16938, 565, 'SOACHA'),
	(16939, 565, 'SOPO'),
	(16940, 565, 'SUBACHOQUE'),
	(16941, 565, 'SUESCA'),
	(16942, 565, 'SUPATA'),
	(16943, 565, 'SUSA'),
	(16944, 565, 'SUTATAUSA'),
	(16945, 565, 'TABIO'),
	(16946, 565, 'TAUSA'),
	(16947, 565, 'TENA'),
	(16948, 565, 'TENJO'),
	(16949, 565, 'TIBACUY'),
	(16950, 565, 'TIBIRITA'),
	(16951, 565, 'TOCAIMA'),
	(16952, 565, 'TOCANCIPA'),
	(16953, 565, 'TOPAIPI'),
	(16954, 565, 'UBALA'),
	(16955, 565, 'UBAQUE'),
	(16956, 565, 'UBATE'),
	(16957, 565, 'UNE'),
	(16958, 565, 'UTICA'),
	(16959, 565, 'VERGARA'),
	(16960, 565, 'VIANI'),
	(16961, 565, 'VILLAGOMEZ'),
	(16962, 565, 'VILLAPINZON'),
	(16963, 565, 'VILLETA'),
	(16964, 565, 'VIOTA'),
	(16965, 565, 'YACOPI'),
	(16966, 565, 'ZIPACON'),
	(16967, 565, 'ZIPAQUIRA'),
	(16968, 566, 'INIRIDA'),
	(16969, 567, 'CALAMAR'),
	(16970, 567, 'EL RETORNO'),
	(16971, 567, 'MIRAFLORES'),
	(16972, 567, 'SAN JOSE DEL GUAVIARE'),
	(16973, 568, 'ACEVEDO'),
	(16974, 568, 'AGRADO'),
	(16975, 568, 'AIPE'),
	(16976, 568, 'ALGECIRAS'),
	(16977, 568, 'ALTAMIRA'),
	(16978, 568, 'BARAYA'),
	(16979, 568, 'CAMPOALEGRE'),
	(16980, 568, 'COLOMBIA'),
	(16981, 568, 'ELIAS'),
	(16982, 568, 'GARZON'),
	(16983, 568, 'GIGANTE'),
	(16984, 568, 'GUADALUPE'),
	(16985, 568, 'HOBO'),
	(16986, 568, 'ISNOS'),
	(16987, 568, 'LA ARGENTINA'),
	(16988, 568, 'LA PLATA'),
	(16989, 568, 'NATAGA'),
	(16990, 568, 'NEIVA'),
	(16991, 568, 'OPORAPA'),
	(16992, 568, 'PAICOL'),
	(16993, 568, 'PALERMO'),
	(16994, 568, 'PALESTINA'),
	(16995, 568, 'PITAL'),
	(16996, 568, 'PITALITO'),
	(16997, 568, 'RIVERA'),
	(16998, 568, 'SALADOBLANCO'),
	(16999, 568, 'SAN AGUSTIN'),
	(17000, 568, 'SANTA MARIA'),
	(17001, 568, 'SUAZA'),
	(17002, 568, 'TARQUI'),
	(17003, 568, 'TELLO'),
	(17004, 568, 'TERUEL'),
	(17005, 568, 'TESALIA'),
	(17006, 568, 'TIMANA'),
	(17007, 568, 'VILLAVIEJA'),
	(17008, 568, 'YAGUARA'),
	(17009, 569, 'ALBANIA'),
	(17010, 569, 'BARRANCAS'),
	(17011, 569, 'DIBULLA'),
	(17012, 569, 'DISTRACCION'),
	(17013, 569, 'EL MOLINO'),
	(17014, 569, 'FONSECA'),
	(17015, 569, 'HATONUEVO'),
	(17016, 569, 'LA JAGUA DEL PILAR'),
	(17017, 569, 'MAICAO'),
	(17018, 569, 'MANAURE'),
	(17019, 569, 'RIOHACHA'),
	(17020, 569, 'SAN JUAN DEL CESAR'),
	(17021, 569, 'URIBIA'),
	(17022, 569, 'URUMITA'),
	(17023, 569, 'VILLANUEVA'),
	(17024, 570, 'ALGARROBO'),
	(17025, 570, 'ARACATACA'),
	(17026, 570, 'ARIGUANI'),
	(17027, 570, 'CERRO SAN ANTONIO'),
	(17028, 570, 'CHIVOLO'),
	(17029, 570, 'CIENAGA'),
	(17030, 570, 'CONCORDIA'),
	(17031, 570, 'EL BANCO'),
	(17032, 570, 'EL PINON'),
	(17033, 570, 'EL RETEN'),
	(17034, 570, 'FUNDACION'),
	(17035, 570, 'GUAMAL'),
	(17036, 570, 'NUEVA GRANADA'),
	(17037, 570, 'PEDRAZA'),
	(17038, 570, 'PIJINO'),
	(17039, 570, 'PIVIJAY'),
	(17040, 570, 'PLATO'),
	(17041, 570, 'PUEBLO VIEJO'),
	(17042, 570, 'REMOLINO'),
	(17043, 570, 'SABANAS DE SAN ANGEL'),
	(17044, 570, 'SALAMINA'),
	(17045, 570, 'SAN SEBASTIAN'),
	(17046, 570, 'SAN ZENON'),
	(17047, 570, 'SANTA ANA'),
	(17048, 570, 'SANTA BARBARA DE PINTO'),
	(17049, 570, 'SANTA MARTA'),
	(17050, 570, 'SITIONUEVO'),
	(17051, 570, 'TENERIFE'),
	(17052, 570, 'ZAPAYAN'),
	(17053, 570, 'ZONA BANANERA'),
	(17054, 571, 'ACACIAS'),
	(17055, 571, 'BARRANCA DE UPIA'),
	(17056, 571, 'CABUYARO'),
	(17057, 571, 'CASTILLA LA NUEVA'),
	(17058, 571, 'CUBARRAL'),
	(17059, 571, 'CUMARAL'),
	(17060, 571, 'EL CALVARIO'),
	(17061, 571, 'EL CASTILLO'),
	(17062, 571, 'EL DORADO'),
	(17063, 571, 'FUENTE DE ORO'),
	(17064, 571, 'GRANADA'),
	(17065, 571, 'GUAMAL'),
	(17066, 571, 'LA MACARENA'),
	(17067, 571, 'LA URIBE'),
	(17068, 571, 'LEJANIAS'),
	(17069, 571, 'MAPIRIPAN'),
	(17070, 571, 'MESETAS'),
	(17071, 571, 'PUERTO CONCORDIA'),
	(17072, 571, 'PUERTO GAITAN'),
	(17073, 571, 'PUERTO LLERAS'),
	(17074, 571, 'PUERTO LOPEZ'),
	(17075, 571, 'PUERTO RICO'),
	(17076, 571, 'RESTREPO'),
	(17077, 571, 'SAN CARLOS GUAROA'),
	(17078, 571, 'SAN JUAN DE ARAMA'),
	(17079, 571, 'SAN JUANITO'),
	(17080, 571, 'SAN MARTIN'),
	(17081, 571, 'VILLAVICENCIO'),
	(17082, 571, 'VISTA HERMOSA'),
	(17083, 572, 'ALDANA'),
	(17084, 572, 'ANCUYA'),
	(17085, 572, 'ARBOLEDA'),
	(17086, 572, 'BARBACOAS'),
	(17087, 572, 'BELEN'),
	(17088, 572, 'BUESACO'),
	(17089, 572, 'COLON'),
	(17090, 572, 'CONSACA'),
	(17091, 572, 'CONTADERO'),
	(17092, 572, 'CORDOBA'),
	(17093, 572, 'CUASPUD'),
	(17094, 572, 'CUMBAL'),
	(17095, 572, 'CUMBITARA'),
	(17096, 572, 'EL CHARCO'),
	(17097, 572, 'EL PENOL'),
	(17098, 572, 'EL ROSARIO'),
	(17099, 572, 'EL TABLON'),
	(17100, 572, 'EL TAMBO'),
	(17101, 572, 'FUNES'),
	(17102, 572, 'GUACHUCAL'),
	(17103, 572, 'GUAITARILLA'),
	(17104, 572, 'GUALMATAN'),
	(17105, 572, 'ILES'),
	(17106, 572, 'IMUES'),
	(17107, 572, 'IPIALES'),
	(17108, 572, 'LA CRUZ'),
	(17109, 572, 'LA FLORIDA'),
	(17110, 572, 'LA LLANADA'),
	(17111, 572, 'LA TOLA'),
	(17112, 572, 'LA UNION'),
	(17113, 572, 'LEIVA'),
	(17114, 572, 'LINARES'),
	(17115, 572, 'LOS ANDES'),
	(17116, 572, 'MALLAMA'),
	(17117, 572, 'MOSQUERA'),
	(17119, 572, 'OLAYA HERRERA'),
	(17120, 572, 'OSPINA'),
	(17121, 572, 'PASTO'),
	(17122, 572, 'PIZARRO'),
	(17123, 572, 'POLICARPA'),
	(17124, 572, 'POTOSI'),
	(17125, 572, 'PROVIDENCIA'),
	(17126, 572, 'PUERRES'),
	(17127, 572, 'PUPIALES'),
	(17128, 572, 'RICAURTE'),
	(17129, 572, 'ROBERTO PAYAN'),
	(17130, 572, 'SAMANIEGO'),
	(17131, 572, 'SAN BERNARDO'),
	(17132, 572, 'SAN JOSE'),
	(17133, 572, 'SAN LORENZO'),
	(17134, 572, 'SAN PABLO'),
	(17135, 572, 'SAN PEDRO DE CARTAGO'),
	(17136, 572, 'SANDONA'),
	(17137, 572, 'SANTA BARBARA'),
	(17138, 572, 'SANTACRUZ'),
	(17139, 572, 'SAPUYES'),
	(17140, 572, 'TAMINANGO'),
	(17141, 572, 'TANGUA'),
	(17142, 572, 'TUMACO'),
	(17143, 572, 'TUQUERRES'),
	(17144, 572, 'YACUANQUER'),
	(17145, 573, 'ABREGO'),
	(17146, 573, 'ARBOLEDAS'),
	(17147, 573, 'BOCHALEMA'),
	(17148, 573, 'BUCARASICA'),
	(17149, 573, 'CACHIRA'),
	(17150, 573, 'CACOTA'),
	(17151, 573, 'CHINACOTA'),
	(17152, 573, 'CHITAGA'),
	(17153, 573, 'CONVENCION'),
	(17154, 573, 'CUCUTA'),
	(17155, 573, 'CUCUTILLA'),
	(17156, 573, 'DURANIA'),
	(17157, 573, 'EL CARMEN'),
	(17158, 573, 'EL TARRA'),
	(17159, 573, 'EL ZULIA'),
	(17160, 573, 'GRAMALOTE'),
	(17161, 573, 'HACARI'),
	(17162, 573, 'HERRAN'),
	(17163, 573, 'LA ESPERANZA'),
	(17164, 573, 'LA PLAYA'),
	(17165, 573, 'LABATECA'),
	(17166, 573, 'LOS PATIOS'),
	(17167, 573, 'LOURDES'),
	(17168, 573, 'MUTISCUA'),
	(17169, 573, 'OCANA'),
	(17170, 573, 'PAMPLONA'),
	(17171, 573, 'PAMPLONITA'),
	(17172, 573, 'PUERTO SANTANDER'),
	(17173, 573, 'RAGONVALIA'),
	(17174, 573, 'SALAZAR'),
	(17175, 573, 'SAN CALIXTO'),
	(17176, 573, 'SAN CAYETANO'),
	(17177, 573, 'SANTIAGO'),
	(17178, 573, 'SARDINATA'),
	(17179, 573, 'SILOS'),
	(17180, 573, 'TEORAMA'),
	(17181, 573, 'TIBU'),
	(17182, 573, 'TOLEDO'),
	(17183, 573, 'VILLA DEL ROSARIO'),
	(17184, 573, 'VILLACARO'),
	(17185, 574, 'COLON'),
	(17186, 574, 'MOCOA'),
	(17187, 574, 'ORITO'),
	(17188, 574, 'PUERTO ASIS'),
	(17189, 574, 'PUERTO CAYCEDO'),
	(17190, 574, 'PUERTO GUZMAN'),
	(17191, 574, 'PUERTO LEGUIZAMO'),
	(17192, 574, 'SAN FRANCISCO'),
	(17193, 574, 'SAN MIGUEL'),
	(17194, 574, 'SANTIAGO'),
	(17195, 574, 'SIBUNDOY'),
	(17196, 574, 'VALLE DEL GUAMUEZ'),
	(17197, 574, 'VILLAGARZON'),
	(17198, 575, 'ARMENIA'),
	(17199, 575, 'BUENAVISTA'),
	(17200, 575, 'CALARCA'),
	(17201, 575, 'CIRCASIA'),
	(17202, 575, 'CORDOBA'),
	(17203, 575, 'FILANDIA'),
	(17204, 575, 'GENOVA'),
	(17205, 575, 'LA TEBAIDA'),
	(17206, 575, 'MONTENEGRO'),
	(17207, 575, 'PIJAO'),
	(17208, 575, 'QUIMBAYA'),
	(17209, 575, 'SALENTO'),
	(17210, 576, 'APIA'),
	(17211, 576, 'BALBOA'),
	(17212, 576, 'BELEN DE UMBRIA'),
	(17213, 576, 'DOS QUEBRADAS'),
	(17214, 576, 'GUATICA'),
	(17215, 576, 'LA CELIA'),
	(17216, 576, 'LA VIRGINIA'),
	(17217, 576, 'MARSELLA'),
	(17218, 576, 'MISTRATO'),
	(17219, 576, 'PEREIRA'),
	(17220, 576, 'PUEBLO RICO'),
	(17221, 576, 'QUINCHIA'),
	(17222, 576, 'SANTA ROSA DE CABAL'),
	(17223, 576, 'SANTUARIO'),
	(17224, 577, 'PROVIDENCIA'),
	(17225, 577, 'SAN ANDRES'),
	(17226, 578, 'AGUADA'),
	(17227, 578, 'ALBANIA'),
	(17228, 578, 'ARATOCA'),
	(17229, 578, 'BARBOSA'),
	(17230, 578, 'BARICHARA'),
	(17231, 578, 'BARRANCABERMEJA'),
	(17232, 578, 'BETULIA'),
	(17233, 578, 'BOLIVAR'),
	(17234, 578, 'BUCARAMANGA'),
	(17235, 578, 'CABRERA'),
	(17236, 578, 'CALIFORNIA'),
	(17237, 578, 'CAPITANEJO'),
	(17238, 578, 'CARCASI'),
	(17239, 578, 'CEPITA'),
	(17240, 578, 'CERRITO'),
	(17241, 578, 'CHARALA'),
	(17242, 578, 'CHARTA'),
	(17243, 578, 'CHIMA'),
	(17244, 578, 'CHIPATA'),
	(17245, 578, 'CIMITARRA'),
	(17246, 578, 'CONCEPCION'),
	(17247, 578, 'CONFINES'),
	(17248, 578, 'CONTRATACION'),
	(17249, 578, 'COROMORO'),
	(17250, 578, 'CURITI'),
	(17251, 578, 'EL CARMEN'),
	(17252, 578, 'EL GUACAMAYO'),
	(17253, 578, 'EL PENON'),
	(17254, 578, 'EL PLAYON'),
	(17255, 578, 'ENCINO'),
	(17256, 578, 'ENCISO'),
	(17257, 578, 'FLORIAN'),
	(17258, 578, 'FLORIDABLANCA'),
	(17259, 578, 'GALAN'),
	(17260, 578, 'GAMBITA'),
	(17261, 578, 'GIRON'),
	(17262, 578, 'GUACA'),
	(17263, 578, 'GUADALUPE'),
	(17264, 578, 'GUAPOTA'),
	(17265, 578, 'GUAVATA'),
	(17266, 578, 'HATO'),
	(17267, 578, 'JESUS MARIA'),
	(17268, 578, 'JORDAN'),
	(17269, 578, 'LA BELLEZA'),
	(17270, 578, 'LA PAZ'),
	(17271, 578, 'LANDAZURI'),
	(17272, 578, 'LEBRIJA'),
	(17273, 578, 'LOS SANTOS'),
	(17274, 578, 'MACARAVITA'),
	(17275, 578, 'MALAGA'),
	(17276, 578, 'MATANZA'),
	(17277, 578, 'MOGOTES'),
	(17278, 578, 'MOLAGAVITA'),
	(17279, 578, 'OCAMONTE'),
	(17280, 578, 'OIBA'),
	(17281, 578, 'ONZAGA'),
	(17282, 578, 'PALMAR'),
	(17283, 578, 'PALMAS DEL SOCORRO'),
	(17284, 578, 'PARAMO'),
	(17285, 578, 'PIEDECUESTA'),
	(17286, 578, 'PINCHOTE'),
	(17287, 578, 'PUENTE NACIONAL'),
	(17288, 578, 'PUERTO PARRA'),
	(17289, 578, 'PUERTO WILCHES'),
	(17290, 578, 'RIONEGRO'),
	(17291, 578, 'SABANA DE TORRES'),
	(17292, 578, 'SAN ANDRES'),
	(17293, 578, 'SAN BENITO'),
	(17294, 578, 'SAN GIL'),
	(17295, 578, 'SAN JOAQUIN'),
	(17296, 578, 'SAN JOSE DE MIRANDA'),
	(17297, 578, 'SAN MIGUEL'),
	(17298, 578, 'SAN VICENTE DE CHUCURI'),
	(17299, 578, 'SANTA BARBARA'),
	(17300, 578, 'SANTA HELENA'),
	(17301, 578, 'SIMACOTA'),
	(17302, 578, 'SOCORRO'),
	(17303, 578, 'SUAITA'),
	(17304, 578, 'SUCRE'),
	(17305, 578, 'SURATA'),
	(17306, 578, 'TONA'),
	(17307, 578, 'VALLE SAN JOSE'),
	(17308, 578, 'VELEZ'),
	(17309, 578, 'VETAS'),
	(17310, 578, 'VILLANUEVA'),
	(17311, 578, 'ZAPATOCA'),
	(17312, 579, 'ALPUJARRA'),
	(17313, 579, 'ALVARADO'),
	(17314, 579, 'AMBALEMA'),
	(17315, 579, 'ANZOATEGUI'),
	(17316, 579, 'ATACO'),
	(17317, 579, 'CAJAMARCA'),
	(17318, 579, 'CARMEN DE APICALA'),
	(17319, 579, 'CASABIANCA'),
	(17320, 579, 'CHAPARRAL'),
	(17321, 579, 'COELLO'),
	(17322, 579, 'COYAIMA'),
	(17323, 579, 'CUNDAY'),
	(17324, 579, 'DOLORES'),
	(17325, 579, 'ESPINAL'),
	(17326, 579, 'FALAN'),
	(17327, 579, 'FLANDES'),
	(17328, 579, 'FRESNO'),
	(17329, 579, 'GUAMO'),
	(17330, 579, 'GUAYABAL'),
	(17331, 579, 'HERVEO'),
	(17332, 579, 'HONDA'),
	(17333, 579, 'IBAGUE'),
	(17334, 579, 'ICONONZO'),
	(17335, 579, 'LERIDA'),
	(17336, 579, 'LIBANO'),
	(17337, 579, 'MARIQUITA'),
	(17338, 579, 'MELGAR'),
	(17339, 579, 'MURILLO'),
	(17340, 579, 'NATAGAIMA'),
	(17341, 579, 'ORTEGA'),
	(17342, 579, 'PALOCABILDO'),
	(17343, 579, 'PIEDRAS'),
	(17344, 579, 'PLANADAS'),
	(17345, 579, 'PRADO'),
	(17346, 579, 'PURIFICACION'),
	(17347, 579, 'RIOBLANCO'),
	(17348, 579, 'RONCESVALLES'),
	(17349, 579, 'ROVIRA'),
	(17350, 579, 'SALDANA'),
	(17351, 579, 'SAN ANTONIO'),
	(17352, 579, 'SAN LUIS'),
	(17353, 579, 'SANTA ISABEL'),
	(17354, 579, 'SUAREZ'),
	(17355, 579, 'VALLE DE SAN JUAN'),
	(17356, 579, 'VENADILLO'),
	(17357, 579, 'VILLAHERMOSA'),
	(17358, 579, 'VILLARRICA'),
	(17359, 580, 'ALCALA'),
	(17360, 580, 'ANDALUCIA'),
	(17361, 580, 'ANSERMANUEVO'),
	(17362, 580, 'ARGELIA'),
	(17363, 580, 'BOLIVAR'),
	(17364, 580, 'BUENAVENTURA'),
	(17365, 580, 'BUGA'),
	(17366, 580, 'BUGALAGRANDE'),
	(17367, 580, 'CAICEDONIA'),
	(17368, 580, 'CALI'),
	(17369, 580, 'CANDELARIA'),
	(17370, 580, 'CARTAGO'),
	(17371, 580, 'DAGUA'),
	(17372, 580, 'DARIEN'),
	(17373, 580, 'EL AGUILA'),
	(17374, 580, 'EL CAIRO'),
	(17375, 580, 'EL CERRITO'),
	(17376, 580, 'EL DOVIO'),
	(17377, 580, 'FLORIDA'),
	(17378, 580, 'GINEBRA'),
	(17379, 580, 'GUACARI'),
	(17380, 580, 'JAMUNDI'),
	(17381, 580, 'LA CUMBRE'),
	(17382, 580, 'LA UNION'),
	(17383, 580, 'LA VICTORIA'),
	(17384, 580, 'OBANDO'),
	(17385, 580, 'PALMIRA'),
	(17386, 580, 'PRADERA'),
	(17387, 580, 'RESTREPO'),
	(17388, 580, 'RIOFRIO'),
	(17389, 580, 'ROLDANILLO'),
	(17390, 580, 'SAN PEDRO'),
	(17391, 580, 'SEVILLA'),
	(17392, 580, 'TORO'),
	(17393, 580, 'TRUJILLO'),
	(17394, 580, 'TULUA'),
	(17395, 580, 'ULLOA'),
	(17396, 580, 'VERSALLES'),
	(17397, 580, 'VIJES'),
	(17398, 580, 'YOTOCO'),
	(17399, 580, 'YUMBO'),
	(17400, 580, 'ZARZAL'),
	(17401, 581, 'ACARICUARA'),
	(17402, 581, 'MITU'),
	(17403, 581, 'PAPUNAUA'),
	(17404, 581, 'TARAIRA'),
	(17405, 581, 'VILLA FATIMA'),
	(17406, 581, 'YAVARATE'),
	(17407, 582, 'CUMARIBO'),
	(17408, 582, 'LA PRIMAVERA'),
	(17409, 582, 'PUERTO CARRENO'),
	(17410, 582, 'SANTA ROSALIA'),
	(17411, 2661, 'LETICIA'),
	(17412, 2661, 'PUERTO NARINO'),
	(17413, 2764, 'AYAPEL'),
	(17414, 2764, 'BUENAVISTA'),
	(17415, 2764, 'CANALETE'),
	(17416, 2764, 'CERETE'),
	(17417, 2764, 'CHIMA'),
	(17418, 2764, 'CHINU'),
	(17419, 2764, 'CIENAGA DE ORO'),
	(17420, 2764, 'COTORRA'),
	(17421, 2764, 'LA APARTADA'),
	(17422, 2764, 'LORICA'),
	(17423, 2764, 'LOS CORDOBAS'),
	(17424, 2764, 'MONITOS'),
	(17425, 2764, 'MOMIL'),
	(17426, 2764, 'MONTELIBANO'),
	(17427, 2764, 'MONTERIA'),
	(17428, 2764, 'PLANETA RICA'),
	(17429, 2764, 'PUEBLO NUEVO'),
	(17430, 2764, 'PUERTO ESCONDIDO'),
	(17431, 2764, 'PUERTO LIBERTADOR'),
	(17432, 2764, 'PURISIMA'),
	(17433, 2764, 'SAHAGUN'),
	(17434, 2764, 'SAN ANDRES SOTAVENTO'),
	(17435, 2764, 'SAN ANTERO'),
	(17436, 2764, 'SAN BERNARDO VIENTO'),
	(17437, 2764, 'SAN CARLOS'),
	(17438, 2764, 'SAN PELAYO'),
	(17439, 2764, 'TIERRALTA'),
	(17440, 2764, 'VALENCIA'),
	(17441, 2765, 'BUENAVISTA'),
	(17442, 2765, 'CAIMITO'),
	(17443, 2765, 'CHALAN'),
	(17444, 2765, 'COLOSO'),
	(17445, 2765, 'COROZAL'),
	(17446, 2765, 'EL ROBLE'),
	(17447, 2765, 'GALERAS'),
	(17448, 2765, 'GUARANDA'),
	(17449, 2765, 'LA UNION'),
	(17450, 2765, 'LOS PALMITOS'),
	(17451, 2765, 'MAJAGUAL'),
	(17452, 2765, 'MORROA'),
	(17453, 2765, 'OVEJAS'),
	(17454, 2765, 'PALMITO'),
	(17455, 2765, 'SAMPUES'),
	(17456, 2765, 'SAN BENITO ABAD'),
	(17457, 2765, 'SAN JUAN DE BETULIA'),
	(17458, 2765, 'SAN MARCOS'),
	(17459, 2765, 'SAN ONOFRE'),
	(17460, 2765, 'SAN PEDRO'),
	(17461, 2765, 'SINCE'),
	(17462, 2765, 'SINCELEJO'),
	(17463, 2765, 'SUCRE'),
	(17464, 2765, 'TOLU'),
	(17465, 2765, 'TOLUVIEJO');
/*!40000 ALTER TABLE `tbl_ciudad` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_conductor
DROP TABLE IF EXISTS `tbl_conductor`;
CREATE TABLE IF NOT EXISTS `tbl_conductor` (
  `PK_CONDUCTOR` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EMPRESA` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `APELLIDO` varchar(50) DEFAULT NULL,
  `CEDULA` varchar(50) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_CONDUCTOR`),
  KEY `FK_CONDUCTOR_EMPRESA` (`FK_EMPRESA`),
  CONSTRAINT `FK_CONDUCTOR_EMPRESA` FOREIGN KEY (`FK_EMPRESA`) REFERENCES `tbl_empresa` (`PK_EMPRESA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_conductor: ~0 rows (aproximadamente)
DELETE FROM `tbl_conductor`;
/*!40000 ALTER TABLE `tbl_conductor` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_conductor` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_conductor_vehiculo
DROP TABLE IF EXISTS `tbl_conductor_vehiculo`;
CREATE TABLE IF NOT EXISTS `tbl_conductor_vehiculo` (
  `PK_CONDUCTOR_VECHICULO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_CONDUCTOR` int(11) DEFAULT NULL,
  `FK_VEHICULO` int(11) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_ASIGNACION` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_CONDUCTOR_VECHICULO`),
  KEY `FK_CONDUCTOR_CV` (`FK_CONDUCTOR`),
  KEY `FK_VEHICULO_CV` (`FK_VEHICULO`),
  CONSTRAINT `FK_CONDUCTOR_CV` FOREIGN KEY (`FK_CONDUCTOR`) REFERENCES `tbl_conductor` (`PK_CONDUCTOR`),
  CONSTRAINT `FK_VEHICULO_CV` FOREIGN KEY (`FK_VEHICULO`) REFERENCES `tbl_vehiculo` (`PK_VEHICULO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_conductor_vehiculo: ~0 rows (aproximadamente)
DELETE FROM `tbl_conductor_vehiculo`;
/*!40000 ALTER TABLE `tbl_conductor_vehiculo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_conductor_vehiculo` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_conteo_perimetro
DROP TABLE IF EXISTS `tbl_conteo_perimetro`;
CREATE TABLE IF NOT EXISTS `tbl_conteo_perimetro` (
  `PK_CONTEO_PERIMETRO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_VEHICULO` int(11) DEFAULT NULL,
  `FK_INFORMACION_REGISTRADORA` int(11) DEFAULT NULL,
  `FECHA_CONTEO` datetime DEFAULT NULL,
  `HORA_INGRESO` time DEFAULT NULL,
  `NUM_INICIAL` int(11) DEFAULT NULL,
  `NUM_FINAL` int(11) DEFAULT NULL,
  `DIFERENCIA` int(11) DEFAULT NULL,
  `ENTRADAS` int(11) DEFAULT NULL,
  `SALIDAS` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_CONTEO_PERIMETRO`),
  KEY `FK_INFORMACION_REGISTRADORA_VEHICULO` (`FK_INFORMACION_REGISTRADORA`),
  KEY `FK_VEHICULO_INFORMACION_REGISTRADORA` (`FK_VEHICULO`),
  CONSTRAINT `FK_INFORMACION_REGISTRADORA_VEHICULO` FOREIGN KEY (`FK_INFORMACION_REGISTRADORA`) REFERENCES `tbl_informacion_registradora` (`PK_INFORMACION_REGISTRADORA`),
  CONSTRAINT `FK_VEHICULO_INFORMACION_REGISTRADORA` FOREIGN KEY (`FK_VEHICULO`) REFERENCES `tbl_vehiculo` (`PK_VEHICULO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_conteo_perimetro: ~0 rows (aproximadamente)
DELETE FROM `tbl_conteo_perimetro`;
/*!40000 ALTER TABLE `tbl_conteo_perimetro` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_conteo_perimetro` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_departamento
DROP TABLE IF EXISTS `tbl_departamento`;
CREATE TABLE IF NOT EXISTS `tbl_departamento` (
  `PK_DEPARTAMENTO` int(11) NOT NULL,
  `FK_PAIS` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PK_DEPARTAMENTO`),
  KEY `FK_DEPARTAMENTO_CIUDAD` (`FK_PAIS`),
  CONSTRAINT `FK_DEPARTAMENTO_CIUDAD` FOREIGN KEY (`FK_PAIS`) REFERENCES `tbl_pais` (`PK_PAIS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_departamento: ~32 rows (aproximadamente)
DELETE FROM `tbl_departamento`;
/*!40000 ALTER TABLE `tbl_departamento` DISABLE KEYS */;
INSERT INTO `tbl_departamento` (`PK_DEPARTAMENTO`, `FK_PAIS`, `NOMBRE`) VALUES
	(553, 45, 'ANTIOQUIA'),
	(554, 45, 'ARAUCA'),
	(555, 45, 'ATLANTICO'),
	(557, 45, 'BOLIVAR'),
	(558, 45, 'BOYACA'),
	(559, 45, 'CALDAS'),
	(560, 45, 'CAQUETA'),
	(561, 45, 'CASANARE'),
	(562, 45, 'CAUCA'),
	(563, 45, 'CESAR'),
	(564, 45, 'CHOCO'),
	(565, 45, 'CUNDINAMARCA'),
	(566, 45, 'GUAINIA'),
	(567, 45, 'GUAVIARE'),
	(568, 45, 'HUILA'),
	(569, 45, 'LA GUAJIRA'),
	(570, 45, 'MAGDALENA'),
	(571, 45, 'META'),
	(572, 45, 'NARINO'),
	(573, 45, 'NORTE DE SANTANDER'),
	(574, 45, 'PUTUMAYO'),
	(575, 45, 'QUINDIO'),
	(576, 45, 'RISARALDA'),
	(577, 45, 'SAN ANDRES Y PROVIDE'),
	(578, 45, 'SANTANDER'),
	(579, 45, 'TOLIMA'),
	(580, 45, 'VALLE DEL CAUCA'),
	(581, 45, 'VAUPES'),
	(582, 45, 'VICHADA'),
	(2661, 45, 'AMAZONAS'),
	(2764, 45, 'CORDOBA'),
	(2765, 45, 'SUCRE');
/*!40000 ALTER TABLE `tbl_departamento` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_empresa
DROP TABLE IF EXISTS `tbl_empresa`;
CREATE TABLE IF NOT EXISTS `tbl_empresa` (
  `PK_EMPRESA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_CIUDAD` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `NIT` varchar(50) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_EMPRESA`),
  KEY `FK_EMPRESA_CUIDAD` (`FK_CIUDAD`),
  CONSTRAINT `FK_EMPRESA_CUIDAD` FOREIGN KEY (`FK_CIUDAD`) REFERENCES `tbl_ciudad` (`PK_CIUDAD`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_empresa: ~1 rows (aproximadamente)
DELETE FROM `tbl_empresa`;
/*!40000 ALTER TABLE `tbl_empresa` DISABLE KEYS */;
INSERT INTO `tbl_empresa` (`PK_EMPRESA`, `FK_CIUDAD`, `NOMBRE`, `NIT`, `ESTADO`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`, `PK_UNICA`) VALUES
	(1, 16373, 'mi_empresa', '1000', 1, 1, '2016-10-14 13:59:47', NULL);
/*!40000 ALTER TABLE `tbl_empresa` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_evento
DROP TABLE IF EXISTS `tbl_evento`;
CREATE TABLE IF NOT EXISTS `tbl_evento` (
  `PK_EVENTO` int(11) NOT NULL AUTO_INCREMENT,
  `CODIGO` varchar(50) DEFAULT NULL,
  `NOMBRE_GENERICO` varchar(50) DEFAULT NULL,
  `DESCRIPCION` text,
  `PRIORIDAD` int(11) DEFAULT NULL,
  `CANTIDAD` int(11) DEFAULT NULL,
  `TIPO_EVENTO` varchar(50) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_EVENTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_evento: ~0 rows (aproximadamente)
DELETE FROM `tbl_evento`;
/*!40000 ALTER TABLE `tbl_evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_evento` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_evento_generado
DROP TABLE IF EXISTS `tbl_evento_generado`;
CREATE TABLE IF NOT EXISTS `tbl_evento_generado` (
  `PK_EVENTO_GENERADO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EVENTO` int(11) DEFAULT NULL,
  `FK_BASE` int(11) DEFAULT NULL,
  `FECHA_EVENTO` datetime DEFAULT '0000-00-00 00:00:00',
  `ESTADO_ENVIADO` tinyint(4) DEFAULT NULL,
  `DETALLE` text,
  PRIMARY KEY (`PK_EVENTO_GENERADO`),
  KEY `FK_BASE_EVENTO` (`FK_BASE`),
  KEY `FK_EVENTO_GENERADO` (`FK_EVENTO`),
  CONSTRAINT `FK_BASE_EVENTO` FOREIGN KEY (`FK_BASE`) REFERENCES `tbl_base` (`PK_BASE`),
  CONSTRAINT `FK_EVENTO_GENERADO` FOREIGN KEY (`FK_EVENTO`) REFERENCES `tbl_evento` (`PK_EVENTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_evento_generado: ~0 rows (aproximadamente)
DELETE FROM `tbl_evento_generado`;
/*!40000 ALTER TABLE `tbl_evento_generado` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_evento_generado` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_evento_usuario
DROP TABLE IF EXISTS `tbl_evento_usuario`;
CREATE TABLE IF NOT EXISTS `tbl_evento_usuario` (
  `PK_EVENTO_USUARIO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EVENTO` int(11) DEFAULT NULL,
  `FK_USUARIO_EMAIL` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_EVENTO_USUARIO`),
  KEY `FK_EVENTO_USUARIO` (`FK_EVENTO`),
  KEY `FK_USUARIO_EVENTO` (`FK_USUARIO_EMAIL`),
  CONSTRAINT `FK_EVENTO_USUARIO` FOREIGN KEY (`FK_EVENTO`) REFERENCES `tbl_evento` (`PK_EVENTO`),
  CONSTRAINT `FK_USUARIO_EVENTO` FOREIGN KEY (`FK_USUARIO_EMAIL`) REFERENCES `tbl_usuario_email` (`PK_USUARIO_EMAIL`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_evento_usuario: ~0 rows (aproximadamente)
DELETE FROM `tbl_evento_usuario`;
/*!40000 ALTER TABLE `tbl_evento_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_evento_usuario` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_grupo
DROP TABLE IF EXISTS `tbl_grupo`;
CREATE TABLE IF NOT EXISTS `tbl_grupo` (
  `PK_GRUPO` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(60) DEFAULT NULL,
  `TECLA_ACCESO` varchar(60) DEFAULT NULL,
  `IMAGEN` varchar(100) DEFAULT NULL,
  `POSICION` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_GRUPO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_grupo: ~0 rows (aproximadamente)
DELETE FROM `tbl_grupo`;
/*!40000 ALTER TABLE `tbl_grupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_grupo` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_grupo_vehiculos_permitido
DROP TABLE IF EXISTS `tbl_grupo_vehiculos_permitido`;
CREATE TABLE IF NOT EXISTS `tbl_grupo_vehiculos_permitido` (
  `PK_GRUPO_VEHICULOS_PERMITIDO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_AGRUPACION` int(11) DEFAULT NULL,
  `FK_BASE` int(11) DEFAULT NULL,
  `FECHA_INICIO` date DEFAULT NULL,
  `FECHA_FIN` date DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_AUDITADA` datetime DEFAULT NULL,
  `TIPO` int(11) DEFAULT NULL COMMENT '1- Permitido 0- No Permitido',
  PRIMARY KEY (`PK_GRUPO_VEHICULOS_PERMITIDO`),
  KEY `FK_FK_GRUPO_PERMITIDO_BASE` (`FK_BASE`),
  CONSTRAINT `FK_FK_GRUPO_PERMITIDO_BASE` FOREIGN KEY (`FK_BASE`) REFERENCES `tbl_base` (`PK_BASE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_grupo_vehiculos_permitido: ~0 rows (aproximadamente)
DELETE FROM `tbl_grupo_vehiculos_permitido`;
/*!40000 ALTER TABLE `tbl_grupo_vehiculos_permitido` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_grupo_vehiculos_permitido` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_informacion_registradora
DROP TABLE IF EXISTS `tbl_informacion_registradora`;
CREATE TABLE IF NOT EXISTS `tbl_informacion_registradora` (
  `PK_INFORMACION_REGISTRADORA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_VEHICULO` int(11) DEFAULT NULL,
  `FK_RUTA` int(11) DEFAULT NULL,
  `FK_USUARIO` int(11) DEFAULT NULL,
  `FK_BASE` int(11) DEFAULT NULL,
  `FK_CONDUCTOR` int(11) DEFAULT NULL,
  `PORCENTAJE_RUTA` double DEFAULT NULL,
  `FECHA_INGRESO` date DEFAULT NULL,
  `HORA_INGRESO` time DEFAULT NULL,
  `NUMERO_VUELTA` int(11) DEFAULT NULL,
  `NUM_VUELTA_ANT` int(11) DEFAULT NULL,
  `NUM_LLEGADA` int(11) DEFAULT NULL,
  `DIFERENCIA_NUM` int(11) DEFAULT NULL,
  `ENTRADAS` int(11) DEFAULT NULL,
  `DIFERENCIA_ENTRADA` int(11) DEFAULT NULL,
  `SALIDAS` int(11) DEFAULT NULL,
  `DIFERENCIA_SALIDA` int(11) DEFAULT NULL,
  `TOTAL_DIA` int(11) DEFAULT NULL,
  `FK_BASE_SALIDA` int(11) DEFAULT NULL,
  `FECHA_SALIDA_BASE_SALIDA` date DEFAULT NULL,
  `HORA_SALIDA_BASE_SALIDA` time DEFAULT NULL,
  `NUMERACION_BASE_SALIDA` int(11) DEFAULT NULL,
  `ENTRADAS_BASE_SALIDA` int(11) DEFAULT NULL,
  `SALIDAS_BASE_SALIDA` int(11) DEFAULT NULL,
  `FIRMWARE` varchar(11) DEFAULT NULL,
  `VERSION_PUNTOS` int(11) DEFAULT NULL,
  `ESTADO_CREACION` int(11) DEFAULT NULL COMMENT '1 - RF-Sincro 2 - Unida 3 - Creada por Union  4 - Eliminada por desunion',
  `HISTORIAL` int(11) DEFAULT NULL COMMENT '0- Original # id vuelta creada al unir',
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_INFORMACION_REGISTRADORA`),
  KEY `FK_INFORMACION_REGISTRADORA_RUTA` (`FK_RUTA`),
  KEY `FK_INFO_REGIS_BASE` (`FK_BASE`),
  KEY `FK_INFO_REGIS_CONDUCTOR` (`FK_CONDUCTOR`),
  KEY `FK_INFO_REGIS_VEHICULO` (`FK_VEHICULO`),
  KEY `IDX_FECHA_INGRESO_HORA_INGRESO` (`FECHA_INGRESO`,`HORA_INGRESO`),
  CONSTRAINT `FK_INFORMACION_REGISTRADORA_RUTA` FOREIGN KEY (`FK_RUTA`) REFERENCES `tbl_ruta` (`PK_RUTA`),
  CONSTRAINT `FK_INFO_REGIS_BASE` FOREIGN KEY (`FK_BASE`) REFERENCES `tbl_base` (`PK_BASE`),
  CONSTRAINT `FK_INFO_REGIS_CONDUCTOR` FOREIGN KEY (`FK_CONDUCTOR`) REFERENCES `tbl_conductor` (`PK_CONDUCTOR`),
  CONSTRAINT `FK_INFO_REGIS_VEHICULO` FOREIGN KEY (`FK_VEHICULO`) REFERENCES `tbl_vehiculo` (`PK_VEHICULO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_informacion_registradora: ~0 rows (aproximadamente)
DELETE FROM `tbl_informacion_registradora`;
/*!40000 ALTER TABLE `tbl_informacion_registradora` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_informacion_registradora` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_liquidacion_adicional
DROP TABLE IF EXISTS `tbl_liquidacion_adicional`;
CREATE TABLE IF NOT EXISTS `tbl_liquidacion_adicional` (
  `PK_LIQUIDACION_ADICIONAL` int(11) NOT NULL AUTO_INCREMENT,
  `FK_LIQUIDACION_GENERAL` int(11) NOT NULL,
  `FK_CATEGORIAS` bigint(20) DEFAULT NULL,
  `ESTADO` tinyint(4) NOT NULL DEFAULT '0',
  `VALOR_DESCUENTO` double NOT NULL DEFAULT '0',
  `MOTIVO_DESCUENTO` varchar(250) DEFAULT NULL,
  `FECHA_DESCUENTO` datetime DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  `CANTIDAD_PASAJEROS_DESCONTADOS` int(11) DEFAULT '0',
  PRIMARY KEY (`PK_LIQUIDACION_ADICIONAL`),
  KEY `FK_LIQUIDACION_GENERAL_DA` (`FK_LIQUIDACION_GENERAL`),
  KEY `FK_CATEGORIAS_DESCUENTO` (`FK_CATEGORIAS`),
  CONSTRAINT `FK_CATEGORIAS_DESCUENTO` FOREIGN KEY (`FK_CATEGORIAS`) REFERENCES `tbl_categoria_descuento` (`PK_CATEGORIAS_DESCUENTOS`),
  CONSTRAINT `FK_LIQUIDACION_GENERAL_DA` FOREIGN KEY (`FK_LIQUIDACION_GENERAL`) REFERENCES `tbl_liquidacion_general` (`PK_LIQUIDACION_GENERAL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_liquidacion_adicional: ~0 rows (aproximadamente)
DELETE FROM `tbl_liquidacion_adicional`;
/*!40000 ALTER TABLE `tbl_liquidacion_adicional` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_liquidacion_adicional` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_liquidacion_general
DROP TABLE IF EXISTS `tbl_liquidacion_general`;
CREATE TABLE IF NOT EXISTS `tbl_liquidacion_general` (
  `PK_LIQUIDACION_GENERAL` int(20) NOT NULL AUTO_INCREMENT,
  `FK_TARIFA_FIJA` int(11) DEFAULT NULL,
  `FK_VEHICULO` int(11) NOT NULL,
  `FK_CONDUCTOR` int(11) NOT NULL,
  `TOTAL_PASAJEROS_LIQUIDADOS` int(11) DEFAULT NULL,
  `TOTAL_VALOR_VUELTAS` double DEFAULT NULL,
  `TOTAL_VALOR_DESCUENTOS_PASAJEROS` double DEFAULT NULL,
  `TOTAL_VALOR_DESCUENTOS_ADICIONAL` double DEFAULT NULL,
  `ESTADO` tinyint(4) NOT NULL DEFAULT '1',
  `MODIFICACION_LOCAL` tinyint(4) NOT NULL,
  `FECHA_LIQUIDACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `FECHA_MODIFICACION` timestamp NULL DEFAULT NULL,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  `USUARIO` int(11) DEFAULT NULL,
  PRIMARY KEY (`PK_LIQUIDACION_GENERAL`),
  KEY `FK_TARIFA_FIJA` (`FK_TARIFA_FIJA`),
  KEY `FK_VEHICULO` (`FK_VEHICULO`),
  KEY `FK_USUARIO` (`USUARIO`),
  KEY `FK_CONDUCTOR` (`FK_CONDUCTOR`),
  CONSTRAINT `FK_CONDUCTOR` FOREIGN KEY (`FK_CONDUCTOR`) REFERENCES `tbl_conductor` (`PK_CONDUCTOR`),
  CONSTRAINT `FK_TARIFA_FIJA` FOREIGN KEY (`FK_TARIFA_FIJA`) REFERENCES `tbl_tarifa_fija` (`PK_TARIFA_FIJA`),
  CONSTRAINT `FK_USUARIO` FOREIGN KEY (`USUARIO`) REFERENCES `tbl_usuario` (`PK_USUARIO`),
  CONSTRAINT `FK_VEHICULO` FOREIGN KEY (`FK_VEHICULO`) REFERENCES `tbl_vehiculo` (`PK_VEHICULO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_liquidacion_general: ~0 rows (aproximadamente)
DELETE FROM `tbl_liquidacion_general`;
/*!40000 ALTER TABLE `tbl_liquidacion_general` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_liquidacion_general` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_liquidacion_vueltas
DROP TABLE IF EXISTS `tbl_liquidacion_vueltas`;
CREATE TABLE IF NOT EXISTS `tbl_liquidacion_vueltas` (
  `PK_LIQUIDACION_VUELTAS` int(11) NOT NULL AUTO_INCREMENT,
  `FK_INFORMACION_REGISTRADORA` int(11) NOT NULL,
  `FK_LIQUIDACION_GENERAL` int(11) NOT NULL,
  `ESTADO` tinyint(4) NOT NULL DEFAULT '1',
  `PASAJEROS_DESCUENTO` int(11) DEFAULT NULL,
  `VALOR_DESCUENTO` double NOT NULL,
  `MOTIVO_DESCUENTO` varchar(250) NOT NULL,
  `FECHA_DESCUENTO` datetime NOT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_LIQUIDACION_VUELTAS`),
  KEY `FK_LIQUIDACION_GENERAL` (`FK_LIQUIDACION_GENERAL`),
  KEY `FK_INFORMACION_REGISTRADORA` (`FK_INFORMACION_REGISTRADORA`),
  CONSTRAINT `FK_INFORMACION_REGISTRADORA` FOREIGN KEY (`FK_INFORMACION_REGISTRADORA`) REFERENCES `tbl_informacion_registradora` (`PK_INFORMACION_REGISTRADORA`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_LIQUIDACION_GENERAL` FOREIGN KEY (`FK_LIQUIDACION_GENERAL`) REFERENCES `tbl_liquidacion_general` (`PK_LIQUIDACION_GENERAL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_liquidacion_vueltas: ~0 rows (aproximadamente)
DELETE FROM `tbl_liquidacion_vueltas`;
/*!40000 ALTER TABLE `tbl_liquidacion_vueltas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_liquidacion_vueltas` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_macro_ruta
DROP TABLE IF EXISTS `tbl_macro_ruta`;
CREATE TABLE IF NOT EXISTS `tbl_macro_ruta` (
  `PK_MACRO_RUTA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EMPRESA` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `HISTORIAL` int(11) DEFAULT NULL,
  `ESTADO_CREACION` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_MACRO_RUTA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_macro_ruta: ~0 rows (aproximadamente)
DELETE FROM `tbl_macro_ruta`;
/*!40000 ALTER TABLE `tbl_macro_ruta` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_macro_ruta` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_macro_subrutas
DROP TABLE IF EXISTS `tbl_macro_subrutas`;
CREATE TABLE IF NOT EXISTS `tbl_macro_subrutas` (
  `PK_MACRO_SUBRUTAS` int(11) NOT NULL AUTO_INCREMENT,
  `FK_MACRO_RUTA` int(11) DEFAULT NULL,
  `FK_RUTA` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_MACRO_SUBRUTAS`),
  KEY `FK_FK_MACRO_RUTA_RUTA` (`FK_RUTA`),
  KEY `FK_FK_RUTA_MACRO_RUTA` (`FK_MACRO_RUTA`),
  CONSTRAINT `FK_FK_MACRO_RUTA_RUTA` FOREIGN KEY (`FK_RUTA`) REFERENCES `tbl_ruta` (`PK_RUTA`),
  CONSTRAINT `FK_FK_RUTA_MACRO_RUTA` FOREIGN KEY (`FK_MACRO_RUTA`) REFERENCES `tbl_macro_ruta` (`PK_MACRO_RUTA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_macro_subrutas: ~0 rows (aproximadamente)
DELETE FROM `tbl_macro_subrutas`;
/*!40000 ALTER TABLE `tbl_macro_subrutas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_macro_subrutas` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_motivo
DROP TABLE IF EXISTS `tbl_motivo`;
CREATE TABLE IF NOT EXISTS `tbl_motivo` (
  `PK_MOTIVO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_AUDITORIA` int(11) DEFAULT NULL,
  `TABLA_AUDITORIA` varchar(50) DEFAULT NULL,
  `DESCRIPCION_MOTIVO` text,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PK_MOTIVO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_motivo: ~0 rows (aproximadamente)
DELETE FROM `tbl_motivo`;
/*!40000 ALTER TABLE `tbl_motivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_motivo` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_pais
DROP TABLE IF EXISTS `tbl_pais`;
CREATE TABLE IF NOT EXISTS `tbl_pais` (
  `PK_PAIS` int(11) NOT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `CODIGO_AREA` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PK_PAIS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_pais: ~0 rows (aproximadamente)
DELETE FROM `tbl_pais`;
/*!40000 ALTER TABLE `tbl_pais` DISABLE KEYS */;
INSERT INTO `tbl_pais` (`PK_PAIS`, `NOMBRE`, `CODIGO_AREA`) VALUES
	(45, 'COLOMBIA', '0');
/*!40000 ALTER TABLE `tbl_pais` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_pantalla_informacion
DROP TABLE IF EXISTS `tbl_pantalla_informacion`;
CREATE TABLE IF NOT EXISTS `tbl_pantalla_informacion` (
  `PK_PANTALLA_INFORMACION` int(11) NOT NULL AUTO_INCREMENT,
  `COLUMNA` varchar(50) DEFAULT NULL,
  `TIPO` int(11) DEFAULT NULL COMMENT '1 - Infomracion Registradora\r\n            2 - Perimetro',
  `POSICION` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`PK_PANTALLA_INFORMACION`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_pantalla_informacion: ~11 rows (aproximadamente)
DELETE FROM `tbl_pantalla_informacion`;
/*!40000 ALTER TABLE `tbl_pantalla_informacion` DISABLE KEYS */;
INSERT INTO `tbl_pantalla_informacion` (`PK_PANTALLA_INFORMACION`, `COLUMNA`, `TIPO`, `POSICION`, `ESTADO`) VALUES
	(1, 'PLACA', 2, 1, 1),
	(2, 'NÂº INTERNO', 2, 2, 1),
	(3, 'HORA LLEGADA', 2, 3, 1),
	(4, 'FECHA', 1, 1, 1),
	(5, 'HORA', 1, 2, 1),
	(6, 'PLACA', 1, 3, 1),
	(7, 'NÂº INTERNO', 1, 4, 1),
	(8, 'DIF. NUMERACION', 1, 5, 1),
	(9, 'NÂº VUELTA', 1, 6, 1),
	(10, 'RUTA', 1, 7, 1),
	(11, 'TOTAL DIA', 1, 8, 1);
/*!40000 ALTER TABLE `tbl_pantalla_informacion` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_perfil
DROP TABLE IF EXISTS `tbl_perfil`;
CREATE TABLE IF NOT EXISTS `tbl_perfil` (
  `PK_PERFIL` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_PERFIL` varchar(50) DEFAULT NULL,
  `DESCRIPCION` text,
  `SYNC` tinyint(4) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_PERFIL`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_perfil: ~7 rows (aproximadamente)
DELETE FROM `tbl_perfil`;
/*!40000 ALTER TABLE `tbl_perfil` DISABLE KEYS */;
INSERT INTO `tbl_perfil` (`PK_PERFIL`, `NOMBRE_PERFIL`, `DESCRIPCION`, `SYNC`, `ESTADO`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`, `PK_UNICA`) VALUES
	(1, 'SuperUsuario', 'Administrador Registel', 1, 1, 1, '2011-03-18 21:46:00', '98iu7yuh654dfgjk'),
	(2, 'ADMINISTRADOR', '', 0, 1, 1, '2016-02-18 11:38:35', '44-kXNH3eJ6Gm4cPal'),
	(3, 'CLIENTE', '', 0, 1, 1, '2016-02-18 11:36:13', '44-PPUxW3W3R33pRf4'),
	(4, 'CST', 'Perfil para los cst ', 0, 1, 1, '2016-02-18 10:47:17', '44-AtAIfTrAK8FTBe4'),
	(5, 'LIQUIDADOR', '', 0, 1, 1, '2016-02-18 11:07:45', '44-jZIgLfOc5UktCMB'),
	(6, 'ADMINISTRADORCLIENTE', '', 0, 1, 1, '2016-02-25 17:08:03', '44-LLJFarJc7HDoN94'),
	(7, 'ADMINISTRADORCOOMOTOR', 'Encargado de crear permisos a los \nliquidaores', 0, 1, 1, '2016-03-16 07:29:27', '44-azZhxUHeUPIuZYg');
/*!40000 ALTER TABLE `tbl_perfil` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_permiso_empresa
DROP TABLE IF EXISTS `tbl_permiso_empresa`;
CREATE TABLE IF NOT EXISTS `tbl_permiso_empresa` (
  `PK_PERMISO_EMPRESA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_BASE` int(11) DEFAULT NULL,
  `FK_EMPRESA` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_PERMISO_EMPRESA`),
  KEY `FK_BASE_EMPRESA` (`FK_BASE`),
  KEY `FK_EMPRESA_BASE` (`FK_EMPRESA`),
  CONSTRAINT `FK_BASE_EMPRESA` FOREIGN KEY (`FK_BASE`) REFERENCES `tbl_base` (`PK_BASE`),
  CONSTRAINT `FK_EMPRESA_BASE` FOREIGN KEY (`FK_EMPRESA`) REFERENCES `tbl_empresa` (`PK_EMPRESA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_permiso_empresa: ~0 rows (aproximadamente)
DELETE FROM `tbl_permiso_empresa`;
/*!40000 ALTER TABLE `tbl_permiso_empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_permiso_empresa` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_planificacion
DROP TABLE IF EXISTS `tbl_planificacion`;
CREATE TABLE IF NOT EXISTS `tbl_planificacion` (
  `PK_PLANIFICACION` int(11) NOT NULL AUTO_INCREMENT,
  `FK_BASE` int(11) DEFAULT NULL,
  `FK_VEHICULO` int(11) DEFAULT NULL,
  `FK_RUTA` int(11) DEFAULT NULL,
  `HORA_SALIDA` time DEFAULT NULL,
  `HORA_LLEGADA` time DEFAULT NULL,
  `FECHA_PLANIFICADA` date DEFAULT NULL,
  `ESTADO` tinyint(11) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_PLANIFICACION`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_planificacion: ~0 rows (aproximadamente)
DELETE FROM `tbl_planificacion`;
/*!40000 ALTER TABLE `tbl_planificacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_planificacion` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_prestamo_vehiculo
DROP TABLE IF EXISTS `tbl_prestamo_vehiculo`;
CREATE TABLE IF NOT EXISTS `tbl_prestamo_vehiculo` (
  `PK_PRESTAMO_VEHICULO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_VEHICULO` int(11) DEFAULT NULL,
  `FK_BASE` int(11) DEFAULT NULL,
  `FECHA_PRESTAMO_INICIO` date DEFAULT NULL,
  `FECHA_PRESTAMO_FIN` date DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `FECHA_AUDITADA` datetime DEFAULT NULL,
  PRIMARY KEY (`PK_PRESTAMO_VEHICULO`),
  KEY `FK_FK_PRESTAMO_VEHICULO_BASE_` (`FK_BASE`),
  CONSTRAINT `FK_FK_PRESTAMO_VEHICULO_BASE_` FOREIGN KEY (`FK_BASE`) REFERENCES `tbl_base` (`PK_BASE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_prestamo_vehiculo: ~0 rows (aproximadamente)
DELETE FROM `tbl_prestamo_vehiculo`;
/*!40000 ALTER TABLE `tbl_prestamo_vehiculo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_prestamo_vehiculo` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_punto
DROP TABLE IF EXISTS `tbl_punto`;
CREATE TABLE IF NOT EXISTS `tbl_punto` (
  `PK_PUNTO` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `DESCRIPCION` text,
  `LATITUD` varchar(20) DEFAULT NULL,
  `LONGITUD` varchar(20) DEFAULT NULL,
  `CODIGO_PUNTO` int(50) DEFAULT NULL,
  `DIRECCION_LATITUD` varchar(20) DEFAULT NULL,
  `DIRECCION_LONGITUD` varchar(20) DEFAULT NULL,
  `RADIO` int(11) DEFAULT NULL,
  `DIRECCION` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_PUNTO`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_punto: ~49 rows (aproximadamente)
DELETE FROM `tbl_punto`;
/*!40000 ALTER TABLE `tbl_punto` DISABLE KEYS */;
INSERT INTO `tbl_punto` (`PK_PUNTO`, `NOMBRE`, `DESCRIPCION`, `LATITUD`, `LONGITUD`, `CODIGO_PUNTO`, `DIRECCION_LATITUD`, `DIRECCION_LONGITUD`, `RADIO`, `DIRECCION`, `ESTADO`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`, `PK_UNICA`) VALUES
	(100, 'Base LLegada', 'llegada', '123.36', '1231.3', 100, 'Sur', 'Oeste', NULL, NULL, NULL, 1, '2011-11-01 21:35:41', '44-ji986h7fc543wsd'),
	(101, 'Base Salida', 'Salida', '563.32', '236.35', 101, 'Norte', 'Este', NULL, NULL, NULL, 1, '2011-11-01 21:37:36', '44-o09ikju876yhgb6'),
	(102, 'P1-San Bernardo', 'Coomotor', '0257.066', '07515.011', 1, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:32:50', '44-Ub4RbG5rB12SSdw'),
	(103, 'P2-Oasis', 'Coomotor', '0254.299', '07515.962', 2, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:33:01', '44-7kiUUp53SrUg8oM'),
	(104, 'P3-los comuneros', 'Coomotor', '0255.551', '07517.491', 3, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:33:12', '44-ZMSlRBFkxZATuxo'),
	(105, 'P4-coofamiliar cll 11', 'coomotor', '0255.787', '07517.381', 4, 'Norte', 'Oeste', 20, 1, 0, 1, '2015-09-25 12:26:57', '44-pD9Nl5afyHHJT37'),
	(106, 'P5-centro de convencion', 'Coomotor', '0256.182', '07517.542', 5, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:33:23', '44-bVeYVqLQWiDZuB0'),
	(107, 'P6-Limonar', 'coomotor', '0254.480', '07515.715', 6, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-14 08:29:22', '44-z1e3ud7PmyxOSSP'),
	(108, 'P7-Cai Pastrana', 'coomotor', '0256.435', '07514.969', 7, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:33:48', '44-D93R412ZtNdKHsG'),
	(109, 'P8-edi profecionales', 'Coomotor	', '0255.614', '07517.192', 8, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:33:59', '44-GvvJnTPiUZVfXR1'),
	(110, 'P9-presentacion', 'Coomotor', '0255.670', '07517.209', 9, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:34:07', '44-qsSps9eeyGYoA8o'),
	(111, 'P11-Sur Abastos', 'Coomotor', '0253.723', '07516.914', 11, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:34:24', '44-Ty8QP7i9HDRfxDn'),
	(112, 'P12-peter pan crr15', 'Coomotor', '0255.379', '07516.760', 12, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:34:36', '44-LFBPDbZ0OP7nuJz'),
	(113, 'P14-sur orientales', 'Coomotor', '0255.525', '07515.592', 14, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:34:45', '44-t8RXsEBFujciBzq'),
	(114, 'P15-parque andino', 'Coomotor', '0255.792', '07516.802', 15, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:34:55', '44-MePTB3gvtZGd1X8'),
	(115, 'P16-uni antonio nariÃ±o', 'Coomotor', '0256.405', '07515.404', 16, 'Norte', 'Oeste', 50, 1, 0, 1, '2015-09-22 12:24:43', '44-JLzjytMprAs13oV'),
	(116, 'P17-machines', 'Coomotor', '0256.901', '07515.003', 17, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:35:04', '44-FAU3obVocWvVl1P'),
	(117, 'P18-uni sur colombiana', 'Coomotor', '0256.508', '07517.853', 18, 'Norte', 'Oeste', 10, 1, 0, 1, '2015-09-25 12:26:02', '44-uePVVNMd4T6Q5T2'),
	(118, 'P19-medilaser', 'Coomotor', '0255.873', '07517.274', 19, 'Norte', 'Oeste', 50, 1, 0, 1, '2015-09-22 12:24:27', '44-pSVF8hmRte1YxD4'),
	(119, 'P20-cai timanco', 'Coomotor', '0254.758', '07516.461', 20, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:35:23', '44-v8HjpJ9XG87qWrb'),
	(120, 'P21-bosques de san luis', 'Coomotor', '0253.791', '07515.873', 21, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:35:33', '44-V2ia5D5Pdyxtxv8'),
	(121, 'P22-los parques', 'Coomotor', '0255.418', '07516.017', 22, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:35:43', '44-dITkUKxu16atduQ'),
	(122, 'P23-eds terpel chicala', 'Coomotor', '0257.796', '07517.773', 23, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:35:53', '44-LzDioBHKbf8p13R'),
	(123, 'p66-estacion', 'COOMOTOR', '0255.777', '07516.777', 66, 'Norte', 'Oeste', 50, 1, 0, 1, '2015-09-15 13:58:10', '44-iEd2L8wVsaKPcvq'),
	(124, 'P24-glindo cll95 crr7', 'Coomotor', '0258.914', '07516.969', 67, 'Norte', 'Oeste', 50, 1, 0, 1, '2015-09-16 09:14:17', '44-JewKSfQtwTi2pLe'),
	(125, 'P24-panorama', 'Coomotor', '0255.148', '07515.852', 24, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:36:09', '44-ikHEKJ2maD2kSVo'),
	(126, 'P25-policia, cll21,crr9', 'Coomotor', '0256.365', '07517.110', 25, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:36:20', '44-Sdwe9dleYS7hHhN'),
	(127, 'P26-galindo', 'Coomotor', '0258.788', '07517.014', 26, 'Norte', 'Oeste', 50, 1, 0, 1, '2015-09-25 12:26:27', '44-aTx1k0Bemok2bOh'),
	(128, 'p27-terminal transporte', 'coomotor', '0255.024', '07516.986', 27, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:36:30', '44-ntRad83QNdQYpcb'),
	(129, 'p28-la rioja', 'coomotor', '0256.702', '07515.186', 28, 'Norte', 'Oeste', 30, 1, 1, 1, '2015-09-22 09:12:48', '44-hIVBnaLxAISdJlD'),
	(130, 'p16-peno redondo', 'Coomotor', '0255.287', '07516.139', 16, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:37:02', '44-i22ryBfijVdiYDl'),
	(131, 'p19- acacias 1 etapa', 'coomotor', '0255.207', '07516.374', 19, 'Norte', 'Oeste', 30, 1, 1, 1, '2015-09-22 15:01:30', '44-Sz0badQRQy6pbqp'),
	(132, 'p29-estadio urdanet', 'coomotor', '0255.418', '07517.078', 29, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:29:15', '44-Yeyiu2ma5T8EW8N'),
	(133, 'p32-limonar crr38 -cll22', 'coomotor', '0254.470', '07515.732', 32, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:30:23', '44-EWxcBCiAKp1UUGZ'),
	(134, 'p33-cll12 con av toma', 'coomotor', '0255.886', '07517.248', 33, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 08:31:55', '44-6T67n708XWky7CG'),
	(135, 'P41-manhatan', 'coomotor', '0255.814', '07517.596', 41, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 16:04:36', '44-419IMKN0OZnUjS4'),
	(136, 'p42 - exito', 'coomotor', '0256.996', '07517.354', 42, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-05 16:17:27', '44-jBKWtZ31wAtpTOw'),
	(137, 'p43-calamari', 'Coomotor', '0258.333', '07517.780', 43, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 15:48:08', '44-ldCFlCUf82i8dHT'),
	(138, 'p44 - los dujos', 'coomotor', '0256.422', '07517.821', 44, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 16:13:20', '44-x4zhwNncWTFs3z5'),
	(139, 'p45-ferrecastill', 'coomotor', '0255.453', '07517.144', 45, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 15:42:37', '44-AvjfSIGV0vWERtC'),
	(140, 'p47-monserrate', 'coomotor', '0256.089', '07516.531', 47, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 15:44:33', '44-IqTVmpzIUlUprZk'),
	(141, 'p46-hospita', 'coomotor', '0255.940', '07516.804', 46, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 15:46:37', '44-IjLylOboOyWIbFT'),
	(142, 'p48-la libertad calle18', 'coomotor', '0256.287', '07516.521', 48, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 16:06:25', '44-8zo3eEHR5NJF9tB'),
	(143, 'p49 - coofamiliar crr5', 'coomotor', '0255.780', '07517.396', 49, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 16:31:49', '44-9Le0YvhG5UfABml'),
	(144, 'p50 -  sena de la quinta', 'coomotor', '0256.075', '07517.505', 50, 'Norte', 'Oeste', 30, 1, 0, 1, '2016-10-13 17:39:40', '44-asflkSgjMceigoZ'),
	(145, 'p51-san martin', 'coomotor', '0255.187', '07516.610', 51, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-13 17:49:05', '44-HNxDa8RoXRn2NMR'),
	(146, 'p50-iglesia sanjose', 'coomotor', '0255.615', '07516.721', 50, 'Norte', 'Oeste', 20, 1, 1, 1, '2016-10-14 09:31:35', '44-H9vHyAYeCtlUpCi'),
	(147, 'p53-las americas', 'coomotor', '0255.539', '07516.310', 53, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-14 09:51:59', '44-5M1ZP0aFO22UIUZ'),
	(148, 'p52-alejandria', 'coomootot', '0256.998', '07515.228', 52, 'Norte', 'Oeste', 30, 1, 1, 1, '2016-10-14 11:03:31', '44-HAXV0MAOHz8Lkff');
/*!40000 ALTER TABLE `tbl_punto` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_punto_control
DROP TABLE IF EXISTS `tbl_punto_control`;
CREATE TABLE IF NOT EXISTS `tbl_punto_control` (
  `PK_PUNTO_CONTROL` int(11) NOT NULL AUTO_INCREMENT,
  `FK_PUNTO` int(11) DEFAULT NULL,
  `FK_INFO_REGIS` int(11) DEFAULT NULL,
  `HORA_PTO_CONTROL` time DEFAULT NULL,
  `FECHA_PTO_CONTROL` date DEFAULT NULL,
  `NUMERACION` int(11) DEFAULT NULL,
  `ENTRADAS` int(11) DEFAULT NULL,
  `SALIDAS` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_PUNTO_CONTROL`),
  KEY `FK_PUNTO_CONTROL_INFORMACION_REGISTRADORA` (`FK_INFO_REGIS`),
  KEY `FK_PUNTO_CONTROL_PUNTO` (`FK_PUNTO`),
  CONSTRAINT `FK_PUNTO_CONTROL_INFORMACION_REGISTRADORA` FOREIGN KEY (`FK_INFO_REGIS`) REFERENCES `tbl_informacion_registradora` (`PK_INFORMACION_REGISTRADORA`),
  CONSTRAINT `FK_PUNTO_CONTROL_PUNTO` FOREIGN KEY (`FK_PUNTO`) REFERENCES `tbl_punto` (`PK_PUNTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_punto_control: ~0 rows (aproximadamente)
DELETE FROM `tbl_punto_control`;
/*!40000 ALTER TABLE `tbl_punto_control` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_punto_control` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_ruta
DROP TABLE IF EXISTS `tbl_ruta`;
CREATE TABLE IF NOT EXISTS `tbl_ruta` (
  `PK_RUTA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EMPRESA` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `HISTORIAL` int(11) DEFAULT NULL,
  `ESTADO_CREACION` int(4) DEFAULT NULL COMMENT '1-Creado sin Puntos 2- Creado - 3 Modificado',
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_RUTA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_ruta: ~0 rows (aproximadamente)
DELETE FROM `tbl_ruta`;
/*!40000 ALTER TABLE `tbl_ruta` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_ruta` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_ruta_punto
DROP TABLE IF EXISTS `tbl_ruta_punto`;
CREATE TABLE IF NOT EXISTS `tbl_ruta_punto` (
  `PK_RUTA_PUNTO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_PUNTO` int(11) DEFAULT NULL,
  `FK_RUTA` int(11) DEFAULT NULL,
  `PROMEDIO_MINUTOS` int(11) DEFAULT NULL,
  `HOLGURA_MINUTOS` int(11) DEFAULT NULL,
  `VALOR_PUNTO` int(11) DEFAULT NULL,
  `TIPO` int(11) DEFAULT NULL COMMENT '1- Base Salida 2- Punto 3 - Base Llegada',
  `FK_BASE` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_RUTA_PUNTO`),
  KEY `FK_RUTA_PUNTO_PUNTO` (`FK_PUNTO`),
  KEY `FK_RUTA_RUTA_PUNTO` (`FK_RUTA`),
  CONSTRAINT `FK_RUTA_PUNTO_PUNTO` FOREIGN KEY (`FK_PUNTO`) REFERENCES `tbl_punto` (`PK_PUNTO`),
  CONSTRAINT `FK_RUTA_RUTA_PUNTO` FOREIGN KEY (`FK_RUTA`) REFERENCES `tbl_ruta` (`PK_RUTA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_ruta_punto: ~0 rows (aproximadamente)
DELETE FROM `tbl_ruta_punto`;
/*!40000 ALTER TABLE `tbl_ruta_punto` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_ruta_punto` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_tarifa
DROP TABLE IF EXISTS `tbl_tarifa`;
CREATE TABLE IF NOT EXISTS `tbl_tarifa` (
  `PK_TARIFA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_RUTA` int(11) DEFAULT NULL,
  `VALOR_PROM_KM` double DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_TARIFA`),
  KEY `FK_RUTA_TARIFA` (`FK_RUTA`),
  CONSTRAINT `FK_RUTA_TARIFA` FOREIGN KEY (`FK_RUTA`) REFERENCES `tbl_ruta` (`PK_RUTA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_tarifa: ~0 rows (aproximadamente)
DELETE FROM `tbl_tarifa`;
/*!40000 ALTER TABLE `tbl_tarifa` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_tarifa` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_tarifa_fija
DROP TABLE IF EXISTS `tbl_tarifa_fija`;
CREATE TABLE IF NOT EXISTS `tbl_tarifa_fija` (
  `PK_TARIFA_FIJA` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_TARIFA` varchar(20) DEFAULT 'Desconocida',
  `VALOR_TARIFA` double DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) NOT NULL DEFAULT '1',
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_TARIFA_FIJA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_tarifa_fija: ~0 rows (aproximadamente)
DELETE FROM `tbl_tarifa_fija`;
/*!40000 ALTER TABLE `tbl_tarifa_fija` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_tarifa_fija` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_tarifa_km
DROP TABLE IF EXISTS `tbl_tarifa_km`;
CREATE TABLE IF NOT EXISTS `tbl_tarifa_km` (
  `PK_TARIFA_KM` int(11) NOT NULL AUTO_INCREMENT,
  `FK_INFORMACION_REGISTRADORA` int(11) DEFAULT NULL,
  `KILOMETRO` int(11) DEFAULT NULL,
  `ENTRADAS` int(11) DEFAULT NULL,
  `SALIDAS` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_TARIFA_KM`),
  KEY `FK_TARIFA_INFORMACION_REGISTRADORA` (`FK_INFORMACION_REGISTRADORA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_tarifa_km: ~0 rows (aproximadamente)
DELETE FROM `tbl_tarifa_km`;
/*!40000 ALTER TABLE `tbl_tarifa_km` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_tarifa_km` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_usuario
DROP TABLE IF EXISTS `tbl_usuario`;
CREATE TABLE IF NOT EXISTS `tbl_usuario` (
  `PK_USUARIO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_PERFIL` int(11) DEFAULT NULL,
  `CEDULA` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `APELLIDO` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `LOGIN` varchar(20) DEFAULT NULL,
  `CONTRASENA` varchar(20) DEFAULT NULL,
  `USUARIOBD` varchar(50) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `ESTADO_CONEXION` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  `TIPO` int(11) DEFAULT NULL COMMENT '1 - Sincronizacion\r\n2 - Regisdata',
  /* Nuevos campos */
  `TOKEN` varchar(64) DEFAULT NULL,
  `EXPIRE_TOKEN` bigint UNSIGNED DEFAULT 0,
  PRIMARY KEY (`PK_USUARIO`),
  KEY `FK_USUARIO_PERFIL` (`FK_PERFIL`),
  CONSTRAINT `FK_USUARIO_PERFIL` FOREIGN KEY (`FK_PERFIL`) REFERENCES `tbl_perfil` (`PK_PERFIL`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_usuario: ~1 rows (aproximadamente)
DELETE FROM `tbl_usuario`;
/*!40000 ALTER TABLE `tbl_usuario` DISABLE KEYS */;
INSERT INTO `tbl_usuario` (`PK_USUARIO`, `FK_PERFIL`, `CEDULA`, `NOMBRE`, `APELLIDO`, `EMAIL`, `LOGIN`, `CONTRASENA`, `USUARIOBD`, `ESTADO`, `ESTADO_CONEXION`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`, `PK_UNICA`, `TIPO`) VALUES
	(1, 1, 1111111111, 'Administrador', 'Registel', 'admin@regismyadmin.com', 'adminregistel', 'diseno&desarrollo', 'root@localhost', 1, 0, 1, '2016-09-21 11:39:45', 'o9ikli86yt5r4dedf6h', 1);
INSERT INTO `tbl_usuario` (`PK_USUARIO`, `FK_PERFIL`, `CEDULA`, `NOMBRE`, `APELLIDO`, `EMAIL`, `LOGIN`, `CONTRASENA`, `USUARIOBD`, `ESTADO`, `ESTADO_CONEXION`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`, `PK_UNICA`, `TIPO`) VALUES
	(2, 1, 1001, 'Administrador', 'Registel', 'admin@regismyadmin.com', 'admin', 'admin', 'root@localhost', 1, 0, 1, '2016-09-21 11:39:45', 'o9ikli86yt5r4dedf6h', 1);
/*!40000 ALTER TABLE `tbl_usuario` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_usuario_email
DROP TABLE IF EXISTS `tbl_usuario_email`;
CREATE TABLE IF NOT EXISTS `tbl_usuario_email` (
  `PK_USUARIO_EMAIL` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_COMPLETO` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_USUARIO_EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_usuario_email: ~0 rows (aproximadamente)
DELETE FROM `tbl_usuario_email`;
/*!40000 ALTER TABLE `tbl_usuario_email` DISABLE KEYS */;
INSERT INTO `tbl_usuario_email` (`PK_USUARIO_EMAIL`, `NOMBRE_COMPLETO`, `EMAIL`, `ESTADO`, `MODIFICACION_LOCAL`, `FECHA_MODIFICACION`, `PK_UNICA`) VALUES
	(1, 'Soporte', 'soporte@registelcolombia.com', 1, 1, '2013-07-17 21:55:13', '44-zrEpGRCaMHXGCM7');
/*!40000 ALTER TABLE `tbl_usuario_email` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_usuario_permiso_empresa
DROP TABLE IF EXISTS `tbl_usuario_permiso_empresa`;
CREATE TABLE IF NOT EXISTS `tbl_usuario_permiso_empresa` (
  `PK_USUARIO_PERMISO_EMPRESA` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EMPRESA` int(11) DEFAULT NULL,
  `FK_USUARIO` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_USUARIO_PERMISO_EMPRESA`),
  KEY `FK_EMPRESA_USUARIO` (`FK_EMPRESA`),
  KEY `FK_USUARIO_EMPRESA` (`FK_USUARIO`),
  CONSTRAINT `FK_EMPRESA_USUARIO` FOREIGN KEY (`FK_EMPRESA`) REFERENCES `tbl_empresa` (`PK_EMPRESA`),
  CONSTRAINT `FK_USUARIO_EMPRESA` FOREIGN KEY (`FK_USUARIO`) REFERENCES `tbl_usuario` (`PK_USUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_usuario_permiso_empresa: ~0 rows (aproximadamente)
DELETE FROM `tbl_usuario_permiso_empresa`;
/*!40000 ALTER TABLE `tbl_usuario_permiso_empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_usuario_permiso_empresa` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_vehiculo
DROP TABLE IF EXISTS `tbl_vehiculo`;
CREATE TABLE IF NOT EXISTS `tbl_vehiculo` (
  `PK_VEHICULO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_EMPRESA` int(11) DEFAULT NULL,
  `PLACA` varchar(50) DEFAULT NULL,
  `NUM_INTERNO` varchar(50) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_VEHICULO`),
  KEY `FK_VEHICULO_EMPRESA` (`FK_EMPRESA`),
  CONSTRAINT `FK_VEHICULO_EMPRESA` FOREIGN KEY (`FK_EMPRESA`) REFERENCES `tbl_empresa` (`PK_EMPRESA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_vehiculo: ~0 rows (aproximadamente)
DELETE FROM `tbl_vehiculo`;
/*!40000 ALTER TABLE `tbl_vehiculo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_vehiculo` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_vehiculos_perimetro
DROP TABLE IF EXISTS `tbl_vehiculos_perimetro`;
CREATE TABLE IF NOT EXISTS `tbl_vehiculos_perimetro` (
  `PK_VEHICULOS_PERIMETRO` int(11) NOT NULL AUTO_INCREMENT,
  `FK_VEHICULO` int(11) DEFAULT NULL,
  `FK_BASE` int(11) DEFAULT NULL,
  `RUTA_ASIGNADA` varchar(50) DEFAULT NULL,
  `HORA_LLEGADA` varchar(30) DEFAULT NULL,
  `HORA_SALIDA` varchar(30) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_VEHICULOS_PERIMETRO`),
  KEY `FK_VEHICULO_VI` (`FK_VEHICULO`),
  CONSTRAINT `FK_VEHICULO_VI` FOREIGN KEY (`FK_VEHICULO`) REFERENCES `tbl_vehiculo` (`PK_VEHICULO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_vehiculos_perimetro: ~0 rows (aproximadamente)
DELETE FROM `tbl_vehiculos_perimetro`;
/*!40000 ALTER TABLE `tbl_vehiculos_perimetro` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_vehiculos_perimetro` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_version_puntos
DROP TABLE IF EXISTS `tbl_version_puntos`;
CREATE TABLE IF NOT EXISTS `tbl_version_puntos` (
  `PK_VERSION_PUNTOS` int(11) NOT NULL AUTO_INCREMENT,
  `VERSION_ACTUAL` int(11) DEFAULT NULL,
  `ESTADO` tinyint(4) DEFAULT NULL,
  `MODIFICACION_LOCAL` tinyint(4) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_VERSION_PUNTOS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_version_puntos: ~0 rows (aproximadamente)
DELETE FROM `tbl_version_puntos`;
/*!40000 ALTER TABLE `tbl_version_puntos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_version_puntos` ENABLE KEYS */;


-- Volcando estructura para tabla bd_coomotorneiva_rdtow1.tbl_version_puntos_agrupacion
DROP TABLE IF EXISTS `tbl_version_puntos_agrupacion`;
CREATE TABLE IF NOT EXISTS `tbl_version_puntos_agrupacion` (
  `PK_VERSION_PUNTOS_AGRUPACION` int(11) NOT NULL AUTO_INCREMENT,
  `FK_AGRUPACION` int(11) DEFAULT NULL,
  `VERSION_GRUPO` int(11) NOT NULL,
  `ESTADO` int(11) NOT NULL,
  `MODIFICACION_LOCAL` int(11) DEFAULT NULL,
  `FECHA_MODIFICACION` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PK_UNICA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`PK_VERSION_PUNTOS_AGRUPACION`),
  KEY `FK_AGRUPACION_VERSION_PUNTOS` (`FK_AGRUPACION`),
  CONSTRAINT `FK_AGRUPACION_VERSION_PUNTOS` FOREIGN KEY (`FK_AGRUPACION`) REFERENCES `tbl_agrupacion` (`PK_AGRUPACION`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla bd_coomotorneiva_rdtow1.tbl_version_puntos_agrupacion: ~0 rows (aproximadamente)
DELETE FROM `tbl_version_puntos_agrupacion`;
/*!40000 ALTER TABLE `tbl_version_puntos_agrupacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_version_puntos_agrupacion` ENABLE KEYS */;


-- Volcando estructura para vista bd_coomotorneiva_rdtow1.view_tbl_informacion_registradora
DROP VIEW IF EXISTS `view_tbl_informacion_registradora`;
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `view_tbl_informacion_registradora` (
	`PK_INFORMACION_REGISTRADORA` INT(11) NOT NULL,
	`FK_VEHICULO` INT(11) NULL,
	`FK_RUTA` INT(11) NULL,
	`FK_USUARIO` INT(11) NULL,
	`FK_BASE` INT(11) NULL,
	`FK_CONDUCTOR` INT(11) NULL,
	`PORCENTAJE_RUTA` DOUBLE NULL,
	`FECHA_INGRESO` DATE NULL,
	`HORA_INGRESO` TIME NULL,
	`NUMERO_VUELTA` INT(11) NULL,
	`NUM_VUELTA_ANT` INT(11) NULL,
	`NUM_LLEGADA` INT(11) NULL,
	`DIFERENCIA_NUM` INT(11) NULL,
	`ENTRADAS` INT(11) NULL,
	`DIFERENCIA_ENTRADA` INT(11) NULL,
	`SALIDAS` INT(11) NULL,
	`DIFERENCIA_SALIDA` INT(11) NULL,
	`TOTAL_DIA` INT(11) NULL,
	`FK_BASE_SALIDA` INT(11) NULL,
	`FECHA_SALIDA_BASE_SALIDA` DATE NULL,
	`HORA_SALIDA_BASE_SALIDA` TIME NULL,
	`NUMERACION_BASE_SALIDA` INT(11) NULL,
	`ENTRADAS_BASE_SALIDA` INT(11) NULL,
	`SALIDAS_BASE_SALIDA` INT(11) NULL,
	`FIRMWARE` VARCHAR(11) NULL COLLATE 'latin1_swedish_ci',
	`VERSION_PUNTOS` INT(11) NULL,
	`ESTADO_CREACION` INT(11) NULL COMMENT '1 - RF-Sincro 2 - Unida 3 - Creada por Union  4 - Eliminada por desunion',
	`HISTORIAL` INT(11) NULL COMMENT '0- Original # id vuelta creada al unir',
	`ESTADO` TINYINT(4) NULL,
	`MODIFICACION_LOCAL` TINYINT(4) NULL,
	`FECHA_MODIFICACION` TIMESTAMP NOT NULL,
	`PK_UNICA` VARCHAR(20) NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_alarma_b_up
DROP TRIGGER IF EXISTS `tgr_alarma_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_alarma_b_up` BEFORE UPDATE ON `tbl_alarma` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT into tbl_auditoria_alarma (                 FK_ALARMA                 , NOMBRE_OLD                 , NOMBRE_NEW                 , TIPO_OLD                 , TIPO_NEW                 , UNIDAD_MEDICION_OLD                 , UNIDAD_MEDICION_NEW                 , PRIORIDAD_OLD                 , PRIORIDAD_NEW                 , ESTADO                 , FECHA_EVENTO                 , USUARIO                 , USUARIO_BD                 , MODIFICACION_LOCAL                            ) VALUES (                 OLD.PK_ALARMA                 , OLD.NOMBRE                 , NEW.NOMBRE                 , OLD.TIPO                 , NEW.TIPO                 , OLD.UNIDAD_MEDICION                 , NEW.UNIDAD_MEDICION                 , OLD.PRIORIDAD                 , NEW.PRIORIDAD                 , NEW.ESTADO                 , NOW()                 , user_id                 , CURRENT_USER()                 , 1             );                  CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_conductor_b_up
DROP TRIGGER IF EXISTS `tgr_conductor_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_conductor_b_up` BEFORE UPDATE ON `tbl_conductor` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT into tbl_auditoria_conductor (                 FK_CONDUCTOR                 , NOMBRE_OLD                 , NOMBRE_NEW                 , APELLIDO_OLD                 , APELLIDO_NEW                 , CEDULA_OLD                 , CEDULA_NEW                 , ESTADO                 , FECHA_EVENTO                 , USUARIO                 , USUARIO_BD                 , MODIFICACION_LOCAL                             ) VALUES (                 OLD.PK_CONDUCTOR                 , OLD.NOMBRE                 , NEW.NOMBRE                 , OLD.APELLIDO                 , NEW.APELLIDO                 , OLD.CEDULA                 , NEW.CEDULA                 , NEW.ESTADO                 , NOW()                 , user_id                 , CURRENT_USER()                 , 1             );                  CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_empresa_b_up
DROP TRIGGER IF EXISTS `tgr_empresa_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_empresa_b_up` BEFORE UPDATE ON `tbl_empresa` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;             INSERT INTO tbl_auditoria_empresa (                 FK_EMPRESA                  ,FK_CIUDAD                  ,FK_CIUDAD_NEW                  ,NOMBRE                  ,NOMBRE_NEW                  ,NIT                  ,NIT_NEW                  ,ESTADO                  ,FECHA_EVENTO                  ,USUARIO                 ,USUARIO_BD                  ,MODIFICACION_LOCAL             ) VALUES (                 OLD.PK_EMPRESA                  ,OLD.FK_CIUDAD                 ,NEW.FK_CIUDAD                 ,OLD.NOMBRE                 ,NEW.NOMBRE                  ,OLD.NIT                 ,NEW.NIT                 ,NEW.ESTADO                 ,NOW()                 ,user_id                 ,CURRENT_USER()                 ,1                        );     CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_info_registradora_respaldo_ins
DROP TRIGGER IF EXISTS `tgr_info_registradora_respaldo_ins`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_info_registradora_respaldo_ins` AFTER INSERT ON `tbl_informacion_registradora` FOR EACH ROW BEGIN
 
 
   
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_macro_ruta_b_up
DROP TRIGGER IF EXISTS `tgr_macro_ruta_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_macro_ruta_b_up` BEFORE UPDATE ON `tbl_macro_ruta` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT into tbl_auditoria_macro_ruta (       FK_MACRO_RUTA       ,NOMBRE_OLD       ,NOMBRE_NEW       ,ESTADO       ,FECHA_EVENTO       ,USUARIO       ,USUARIO_BD       ,MODIFICACION_LOCAL     ) VALUES (        OLD.PK_MACRO_RUTA       ,OLD.NOMBRE       ,NEW.NOMBRE       ,NEW.ESTADO       ,NOW()       ,user_id       ,CURRENT_USER()       ,1     );                  CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_perfil_b_up
DROP TRIGGER IF EXISTS `tgr_perfil_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_perfil_b_up` BEFORE UPDATE ON `tbl_perfil` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT into tbl_auditoria_perfil (                 FK_PERFIL                 , NOMBRE_PERFIL_OLD                 , NOMBRE_PERFIL_NEW                 , DESCRIPCION_OLD                 , DESCRIPCION_NEW                 , ESTADO                 , FECHA_EVENTO                 , USUARIO                 , USUARIO_BD                 , MODIFICACION_LOCAL                            ) VALUES (                 OLD.PK_PERFIL                 , OLD.NOMBRE_PERFIL                 , NEW.NOMBRE_PERFIL                 , OLD.DESCRIPCION                 , NEW.DESCRIPCION                 , NEW.ESTADO                 , NOW()                 , user_id                 , CURRENT_USER()                 , 1             );                  CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_punto_b_up
DROP TRIGGER IF EXISTS `tgr_punto_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_punto_b_up` BEFORE UPDATE ON `tbl_punto` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT into tbl_auditoria_punto (                 FK_PUNTO                 , NOMBRE_OLD                 , NOMBRE_NEW                 , DESCRIPCION_OLD                 , DESCRIPCION_NEW                 , LATITUD_OLD                 , LATITUD_NEW                 , LONGITUD_OLD                 , LONGITUD_NEW                 , ESTADO                 , FECHA_EVENTO                 , USUARIO                 , USUARIO_BD                 , MODIFICACION_LOCAL                            ) VALUES (                 OLD.PK_PUNTO                 , OLD.NOMBRE                 , NEW.NOMBRE                 , OLD.DESCRIPCION                 , NEW.DESCRIPCION                 , OLD.LATITUD                 , NEW.LATITUD                 , OLD.LONGITUD                 , NEW.LONGITUD                 , NEW.ESTADO                 , NOW()                 , user_id                 , CURRENT_USER()                 , 1             );                  CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_punto_control_b_up
DROP TRIGGER IF EXISTS `tgr_punto_control_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_punto_control_b_up` BEFORE UPDATE ON `tbl_punto_control` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT INTO tbl_auditoria_punto_control (                 FK_PUNTO_CONTROL                 , HORA_PTO_CONTROL_OLD                 , HORA_PTO_CONTROL_NEW                 , FECHA_PTO_CONTROL_OLD                 , FECHA_PTO_CONTROL_NEW                 , NUMERACION_OLD                 , NUMERACION_NEW                 , ENTRADAS_OLD                 , ENTRADAS_NEW                 , SALIDAS_OLD                 , SALIDAS_NEW                 , FECHA_EVENTO                 , USUARIO                 , USUARIO_BD                 , MODIFICACION_LOCAL            ) VALUES (                 OLD.PK_PUNTO_CONTROL                 , OLD.HORA_PTO_CONTROL                 , NEW.HORA_PTO_CONTROL                 , OLD.FECHA_PTO_CONTROL                 , NEW.FECHA_PTO_CONTROL                 , OLD.NUMERACION                 , NEW.NUMERACION                 , OLD.ENTRADAS                 , NEW.ENTRADAS                 , OLD.SALIDAS                 , NEW.SALIDAS                 , NOW()                 , user_id                 , CURRENT_USER()                 , 1             );     CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_ruta_b_up
DROP TRIGGER IF EXISTS `tgr_ruta_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_ruta_b_up` BEFORE UPDATE ON `tbl_ruta` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT into tbl_auditoria_ruta (              FK_RUTA              , NOMBRE_OLD              , NOMBRE_NEW              , ESTADO              , FECHA_EVENTO              , USUARIO              , USUARIO_BD              , MODIFICACION_LOCAL                ) VALUES (                 OLD.PK_RUTA                 , OLD.NOMBRE                 , NEW.NOMBRE                 , NEW.ESTADO                 , NOW()                 , user_id                 , CURRENT_USER()                 ,1              );       CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_tarifa_b_up
DROP TRIGGER IF EXISTS `tgr_tarifa_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_tarifa_b_up` BEFORE UPDATE ON `tbl_tarifa` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT into tbl_auditoria_tarifa (       FK_TARIFA       ,FK_RUTA       , FK_RUTA_OLD       ,VALOR_PROM_KM_OLD       ,VALOR_PROM_KM_NEW       ,ESTADO       ,FECHA_EVENTO       ,USUARIO       ,USUARIO_BD       ,MODIFICACION_LOCAL     ) VALUES (        OLD.PK_TARIFA       ,NEW.FK_RUTA       ,OLD.FK_RUTA          ,OLD.VALOR_PROM_KM       ,NEW.VALOR_PROM_KM       ,NEW.ESTADO       ,NOW()       ,user_id       ,CURRENT_USER()       ,1     );     CLOSE cu_user; END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_usuario_b_up
DROP TRIGGER IF EXISTS `tgr_usuario_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_usuario_b_up` BEFORE UPDATE ON `tbl_usuario` FOR EACH ROW BEGIN     DECLARE user_id           int;     DECLARE nu_actualizar     int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     SET nu_actualizar = 0;     IF OLD.CEDULA <> NEW.CEDULA     THEN         set nu_actualizar = 1;     END IF;     IF OLD.NOMBRE <> NEW.NOMBRE     THEN         set nu_actualizar = 1;     END IF;     IF OLD.APELLIDO <> NEW.APELLIDO     THEN         set nu_actualizar = 1;     END IF;     IF OLD.EMAIL <> NEW.EMAIL     THEN         set nu_actualizar = 1;     END IF;     IF OLD.LOGIN <> NEW.LOGIN     THEN         set nu_actualizar = 1;     END IF;     IF OLD.CONTRASENA <> NEW.CONTRASENA     THEN         set nu_actualizar = 1;     END IF;     IF OLD.ESTADO <> NEW.ESTADO     THEN         set nu_actualizar = 1;     END IF;     IF nu_actualizar = 1     THEN       OPEN cu_user;       FETCH cu_user INTO user_id;       INSERT into tbl_auditoria_usuario (                   FK_USUARIO                   ,FK_PERFIL_OLD                   ,FK_PERFIL_NEW                   ,CEDULA_OLD                   ,CEDULA_NEW                   ,NOMBRE_OLD                   ,NOMBRE_NEW                   ,APELLIDO_OLD                   ,APELLIDO_NEW                   ,EMAIL_OLD                   ,EMAIL_NEW                   ,LOGIN_OLD                   ,LOGIN_NEW                   ,CONTRASENA_OLD                   ,CONTRASENA_NEW                   ,ESTADO                   ,FECHA_EVENTO                   ,USUARIO                   ,USUARIO_BD                   ,MODIFICACION_LOCAL               ) VALUES (                   OLD.PK_USUARIO                   , OLD.FK_PERFIL                   , NEW.FK_PERFIL                   , OLD.CEDULA                   , NEW.CEDULA                   , OLD.NOMBRE                   , NEW.NOMBRE                   , OLD.APELLIDO                   , NEW.APELLIDO                   , OLD.EMAIL                   , NEW.EMAIL                   , OLD.LOGIN                   , NEW.LOGIN                   , OLD.CONTRASENA                   , NEW.CONTRASENA                   , NEW.ESTADO                   , NOW()                   , user_id                   , CURRENT_USER()                   , 1                );         CLOSE cu_user;      END IF; END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.tgr_vehiculo_b_up
DROP TRIGGER IF EXISTS `tgr_vehiculo_b_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tgr_vehiculo_b_up` AFTER UPDATE ON `tbl_vehiculo` FOR EACH ROW BEGIN     DECLARE user_id int;     DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where ESTADO_CONEXION = 1              AND USUARIOBD = CURRENT_USER();     DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     OPEN cu_user;     FETCH cu_user INTO user_id;     INSERT into tbl_auditoria_vehiculo (                 FK_VEHICULO                 ,PLACA_OLD                 ,PLACA_NEW                 ,NUM_INTERNO_OLD                 ,NUM_INTERNO_NEW                 ,ESTADO                 ,FECHA_EVENTO                 ,USUARIO                 ,USUARIO_BD                 ,MODIFICACION_LOCAL             ) VALUES (                 OLD.PK_VEHICULO                 ,OLD.PLACA                 ,NEW.PLACA                 ,OLD.NUM_INTERNO                 ,NEW.NUM_INTERNO                 ,NEW.ESTADO                 ,NOW()                 ,user_id                 ,CURRENT_USER()                 ,1              );       CLOSE cu_user;   END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.trg_descuentoXcategoria
DROP TRIGGER IF EXISTS `trg_descuentoXcategoria`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `trg_descuentoXcategoria` BEFORE UPDATE ON `tbl_categoria_descuento` FOR EACH ROW BEGIN     
DECLARE user_id int;
DECLARE cu_user CURSOR FOR SELECT PK_USUARIO from tbl_usuario where ESTADO_CONEXION = 1 AND USUARIOBD = CURRENT_USER();
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000'
BEGIN END;

OPEN cu_user;
FETCH cu_user INTO user_id; 
INSERT INTO tbl_auditoria_categoria_descuentos (FK_TBL_CATEGORIA, 
																NOMBRE_OLD, 
																NOMBRE_NEW, 
																DESCRIPCION_OLD, 
																DESCRIPCION_NEW, 
																APLICA_DESCUENTO_OLD, 
																APLICA_DESCUENTO_NEW, 
																APLICA_GENERAL_OLD, 
																APLICA_GENERAL_NEW,
																ES_VALOR_MONEDA_OLD, 
																ES_VALOR_MONEDA_NEW, 
																ES_PORCENTAJE_OLD, 
																ES_PORCENTAJE_NEW, 
																VALOR_OLD, 
																VALOR_NEW, 																 
																ES_FIJA_OLD,
																ES_FIJA_NEW,
																ESTADO, 
																FECHA_EVENTO, 
																USUARIO, 
																USUARIOBD, 
																MODIFICACION_LOCAL) 
VALUES (OLD.PK_CATEGORIAS_DESCUENTOS, 
			OLD.NOMBRE, 
			NEW.NOMBRE, 
			OLD.DESCRIPCION, 
			NEW.DESCRIPCION, 
			OLD.APLICA_DESCUENTO, 
			NEW.APLICA_DESCUENTO, 
			OLD.APLICA_GENERAL, 
			NEW.APLICA_GENERAL, 
			OLD.ES_VALOR_MONEDA, 
			NEW.ES_VALOR_MONEDA, 
			OLD.ES_PORCENTAJE, 
			NEW.ES_PORCENTAJE, 
			OLD.VALOR, 
			NEW.VALOR, 
			OLD.ES_FIJA, 
			NEW.ES_FIJA, 
			NEW.ESTADO, 
			NOW() , 
			user_id , 
			CURRENT_USER(), 
			1);
CLOSE cu_user;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.trg_informacion_registradora_a_up
DROP TRIGGER IF EXISTS `trg_informacion_registradora_a_up`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
DELIMITER //
CREATE TRIGGER `trg_informacion_registradora_a_up` AFTER UPDATE ON `tbl_informacion_registradora` FOR EACH ROW BEGIN     
DECLARE user_id int;     
DECLARE cu_user CURSOR FOR SELECT PK_USUARIO             from tbl_usuario              where PK_USUARIO = NEW.FK_USUARIO;     

DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' BEGIN END;     
OPEN cu_user;     FETCH cu_user INTO user_id;     
INSERT INTO tbl_auditoria_informacion_registradora (                  FK_INFORMACION_REGISTRADORA                 ,NUMERO_VUELTA_OLD                 ,NUMERO_VUELTA_NEW                 ,NUM_VUELTA_ANT_OLD                 ,NUM_VUELTA_ANT_NEW                 ,NUM_LLEGADA_OLD                 ,NUM_LLEGADAL_NEW                 ,DIFERENCIA_NUM_OLD                 ,DIFERENCIA_NUM_NEW                 ,ENTRADAS_OLD                 ,ENTRADAS_NEW                 ,DIFERENCIA_ENTRADA_OLD                 ,DIFERENCIA_ENTRADA_NEW                 ,SALIDAS_OLD                 ,SALIDAS_NEW                 ,DIFERENCIA_SALIDA_OLD                 ,DIFERENCIA_SALIDA_NEW                 ,TOTAL_DIA_OLD                 ,TOTAL_DIA_NEW                 ,fk_ruta_Old                 ,fk_ruta_New                 ,NUMERACION_BASE_SALIDA_OLD                 ,NUMERACION_BASE_SALIDA_NEW                 ,ENTRADAS_BASE_SALIDA_OLD                 ,ENTRADAS_BASE_SALIDA_NEW                 ,SALIDAS_BASE_SALIDA_OLD                 ,SALIDAS_BASE_SALIDAS_NEW,   			FECHA_INGRESO_OLD, 			 FECHA_INGRESO_NEW, 			HORA_INGRESO_OLD, 			HORA_INGRESO_NEW,  			FECHA_SALIDA_OLD,   						FECHA_SALIDA_NEW, 								HORA_SALIDA_OLD,   					HORA_SALIDA_NEW, 							FECHA_EVENTO          	,USUARIO                 	,USUARIO_BD                 ,MODIFICACION_LOCAL                        ) 
													  VALUES (                 OLD.PK_INFORMACION_REGISTRADORA                 , OLD.NUMERO_VUELTA                , NEW.NUMERO_VUELTA                , OLD.NUM_VUELTA_ANT                , NEW.NUM_VUELTA_ANT                , OLD.NUM_LLEGADA                , NEW.NUM_LLEGADA                 , OLD.DIFERENCIA_NUM                , NEW.DIFERENCIA_NUM                , OLD.ENTRADAS                , NEW.ENTRADAS                , OLD.DIFERENCIA_ENTRADA                , NEW.DIFERENCIA_ENTRADA                , OLD.SALIDAS                , NEW.SALIDAS                , OLD.DIFERENCIA_SALIDA                , NEW.DIFERENCIA_SALIDA                , OLD.TOTAL_DIA                , NEW.TOTAL_DIA                , OLD.FK_RUTA                , NEW.FK_RUTA                 , OLD.NUMERACION_BASE_SALIDA               , NEW.NUMERACION_BASE_SALIDA                , OLD.ENTRADAS_BASE_SALIDA                , NEW.ENTRADAS_BASE_SALIDA                 , OLD.SALIDAS_BASE_SALIDA                 , NEW.SALIDAS_BASE_SALIDA,          OLD.FECHA_INGRESO, 			 NEW.FECHA_INGRESO, 			OLD.HORA_INGRESO, 			NEW.HORA_INGRESO, 			OLD.FECHA_SALIDA_BASE_SALIDA, 		NEW.FECHA_SALIDA_BASE_SALIDA, 				OLD.HORA_SALIDA_BASE_SALIDA, 		NEW.HORA_SALIDA_BASE_SALIDA         , NOW()                 , NEW.FK_USUARIO                 , USER()                 , 1              );       
													  
CLOSE cu_user;      
													  
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para disparador bd_coomotorneiva_rdtow1.trg_liquidacion_before_insert
DROP TRIGGER IF EXISTS `trg_liquidacion_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='';
DELIMITER //
CREATE TRIGGER `trg_liquidacion_before_insert` BEFORE INSERT ON `tbl_liquidacion_general` FOR EACH ROW BEGIN
      SET NEW.FECHA_MODIFICACION = NOW();
  END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;


-- Volcando estructura para vista bd_coomotorneiva_rdtow1.view_tbl_informacion_registradora
DROP VIEW IF EXISTS `view_tbl_informacion_registradora`;
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `view_tbl_informacion_registradora`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_tbl_informacion_registradora` AS select `tir`.`PK_INFORMACION_REGISTRADORA` AS `PK_INFORMACION_REGISTRADORA`,`tir`.`FK_VEHICULO` AS `FK_VEHICULO`,`tir`.`FK_RUTA` AS `FK_RUTA`,`tir`.`FK_USUARIO` AS `FK_USUARIO`,`tir`.`FK_BASE` AS `FK_BASE`,`tir`.`FK_CONDUCTOR` AS `FK_CONDUCTOR`,`tir`.`PORCENTAJE_RUTA` AS `PORCENTAJE_RUTA`,`tir`.`FECHA_INGRESO` AS `FECHA_INGRESO`,`tir`.`HORA_INGRESO` AS `HORA_INGRESO`,`tir`.`NUMERO_VUELTA` AS `NUMERO_VUELTA`,`tir`.`NUM_VUELTA_ANT` AS `NUM_VUELTA_ANT`,`tir`.`NUM_LLEGADA` AS `NUM_LLEGADA`,`tir`.`DIFERENCIA_NUM` AS `DIFERENCIA_NUM`,`tir`.`ENTRADAS` AS `ENTRADAS`,`tir`.`DIFERENCIA_ENTRADA` AS `DIFERENCIA_ENTRADA`,`tir`.`SALIDAS` AS `SALIDAS`,`tir`.`DIFERENCIA_SALIDA` AS `DIFERENCIA_SALIDA`,`tir`.`TOTAL_DIA` AS `TOTAL_DIA`,`tir`.`FK_BASE_SALIDA` AS `FK_BASE_SALIDA`,`tir`.`FECHA_SALIDA_BASE_SALIDA` AS `FECHA_SALIDA_BASE_SALIDA`,`tir`.`HORA_SALIDA_BASE_SALIDA` AS `HORA_SALIDA_BASE_SALIDA`,`tir`.`NUMERACION_BASE_SALIDA` AS `NUMERACION_BASE_SALIDA`,`tir`.`ENTRADAS_BASE_SALIDA` AS `ENTRADAS_BASE_SALIDA`,`tir`.`SALIDAS_BASE_SALIDA` AS `SALIDAS_BASE_SALIDA`,`tir`.`FIRMWARE` AS `FIRMWARE`,`tir`.`VERSION_PUNTOS` AS `VERSION_PUNTOS`,`tir`.`ESTADO_CREACION` AS `ESTADO_CREACION`,`tir`.`HISTORIAL` AS `HISTORIAL`,`tir`.`ESTADO` AS `ESTADO`,`tir`.`MODIFICACION_LOCAL` AS `MODIFICACION_LOCAL`,`tir`.`FECHA_MODIFICACION` AS `FECHA_MODIFICACION`,`tir`.`PK_UNICA` AS `PK_UNICA` from `tbl_informacion_registradora` `tir` FORCE INDEX (`IDX_FECHA_INGRESO_HORA_INGRESO`) where (`tir`.`ESTADO` = 1) order by `tir`.`PK_INFORMACION_REGISTRADORA` desc;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
