
var TEXT     = 1;
var TEXT_OPTIONAL = 2;
var SELECT   = 3;
var NO_EMPTY = 4;

var nombre  = 0, 
    tipo    = 1, 
    patron  = 2, 
    long    = 3, 
    msg     = 4;

var exp_alfanumerico_esp = /^([a-zA-Z0-9-_]+\s*)+$/;
var exp_alfanumerico = /^([a-zA-Z0-9]+\s*)+$/;
var exp_numerico     = /^[0-9]+$/;
var exp_tarifa       = /^([0-9]*\.)?[0-9]+$/;
var exp_alfabeto     = /^([a-zA-Z]+\s*)+$/;
var exp_numdoc       = /^[0-9]{7,13}$/;
var exp_direccion    = /^([a-zA-Z0-9#-\.\/]+\s*)+$/;
var exp_telefono     = /^[0-9]{7,20}$/;
var exp_movil        = /^[0-9]{10,20}$/;
var exp_email        = /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/;
var exp_email_min    = /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*$/;
var exp_login        = /^\w{4,20}$/;
var exp_pass         = /^[\w!#$%&@\.]{8,20}$/;
var exp_web          = /^([a-zA-Z0-9]+\.{1,1}[a-zA-Z0-9]+)+$/;
var exp_nit          = /^[0-9\.-]+$/;
var exp_entorno      = /^[a-zA-Z0-9#\.\-_~\s]+$/;
var exp_alfanumerico_noesp = /^[a-zA-Z0-9-]+$/;

// Informacion registradora
var exp_motivo      = /^([a-zA-Z\u00C0-\u00FF,#\-:\.0-9]+\s*){10,512}$/;
var exp_numeracion  = /^[0-9]{1,6}$/;
var exp_entsal      = /^[0-9]{1,4}$/;
var exp_fecha       = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
var exp_hora        = /^[0-9]{2}:[0-9]{2}:[0-9]{2}$/;
var exp_fecha_hora  = /^[0-9]{4}-[0-9]{2}-[0-9]{2}\s{1,1}[0-9]{2}:[0-9]{2}:[0-9]{2}$/;

// Valida cada campo especificado para registro/edicion de usuario,
// de ser alguno incorrecto, se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaUsuario() {
    
    var campos = [
        // nombre campo, tipo, patron, longitud, msg error
        ['nombre',      TEXT, exp_alfabeto, 50,     '* &Uacute;nicamente caracteres del alfabeto. No se admiten tildes ni caracteres especiales.'],
        ['apellido',    TEXT, exp_alfabeto, 50,     '* &Uacute;nicamente caracteres del alfabeto. No se admiten tildes ni caracteres especiales.' ],
        ['sempresa',    SELECT, "", null,           '* Especifique una empresa.'],
        ['sperfil',     SELECT, "", null,           '* Especifique un perfil.'],
        ['stipodoc',    SELECT, "", null,           '* Especifique un tipo de documento.'],
        ['numdoc',      TEXT, exp_numdoc, 10,       '* &Uacute;nicamente caracteres num&eacute;ricos.'],
        ['direccion',   TEXT_OPTIONAL, exp_direccion, 50,    '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como /#-.'],
        ['telefono',    TEXT_OPTIONAL, exp_telefono, 20,     '* &Uacute;nicamente caracteres num&eacute;ricos.'],
        ['movil',       TEXT_OPTIONAL, exp_movil, 20,        '* &Uacute;nicamente caracteres num&eacute;ricos.'],
        ['email_data',  TEXT, exp_email, 50,        '* Especifique un correo electr&oacute;nico v&aacute;lido.'],
        ['login',       TEXT, exp_login, 20,        '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolo _.'],
        ['pass',        TEXT, exp_pass, 20,         '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como !#.'],
        ['cpass',       TEXT, exp_pass, 20,         '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como !#.']
    ];
    
    if (vdt_valida(campos)) {
        
        // Validaciones adicionales
        var pass  = document.getElementById("pass");
        var cpass = document.getElementById("cpass");
        
        if (pass  != null && 
            cpass != null && pass.value != cpass.value) {
            var msg = document.getElementById("msg_cpass");
            msg.innerHTML = "* Contrase&ntilde;as no coinciden.";
            msg.setAttribute("class", "msg-error");
            return false;
        }
        return true;
    } 
    return false;
}

// Valida cada campo especificado para registro/edicion de empresa,
// de ser alguno incorrecto, se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaEmpresa() {
    
    var campos = [
        ['nombre',      TEXT, exp_alfanumerico, 50, '* &Uacute;nicamente caracteres alfanum&eacute;ricos.'],
        ['nit',         TEXT, exp_nit, 50,          '* &Uacute;nicamente caracteres n&uacute;mericos y s&iacute;mbolo -.'],
        ['spais',       TEXT, /^[0-9]+$/, null,         '* Especifique un pa&iacute;s.'],
        ['sdpto',       TEXT, /^[0-9]+,[0-9]+$/, null,  '* Especifique un departamento.'],
        ['sciudad',     TEXT, /^[0-9]+,[0-9]+$/, null,  '* Especifique una ciudad.'],
        ['direccion',   TEXT_OPTIONAL, exp_direccion, 50,    '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como -#/.'],
        ['telefono',    TEXT_OPTIONAL, exp_telefono, 20,     '* &Uacute;nicamente caracteres n&uacute;mericos.'],
        ['paginaweb',   TEXT_OPTIONAL, exp_web, 50,     '* Especifique una p&aacute;gina web v&aacute;lida.'],
        ['email',       TEXT_OPTIONAL, exp_email, 50,   '* Especifique un correo electr&oacute;nico v&aacute;lido.'],
        ['smoneda',      TEXT, /^[0-9]+$/, null,     '* Especifique un tipo de moneda.'],
        ['szonahoraria', TEXT, /^[0-9]+$/, null,     '* Especifique un huso horario.']
    ];
    
    return vdt_valida(campos);
}

// Valida cada campo especificado para registro/edicion de ruta,
// de ser alguno incorrecto, se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaRuta() {
    
    var campos = [
        ['sempresa', TEXT, exp_numerico, null, '* Especifique una empresa.'],
        ['nombre',   TEXT, exp_alfanumerico_esp, 50, '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos -_.']
    ];
    
    return vdt_valida(campos);
}

// Valida cada campo especificado para registro/edicion de tarifa,
// de ser alguno incorrecto, se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaTarifa() {
    
    var campos = [
        ['nombre',   TEXT, exp_alfanumerico_esp, 20, '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos -_.'],
        ['valor',    TEXT, exp_tarifa, null, '* &Uacute;nicamente valores num&eacute;ricos no negativos.']
    ];
    
    return vdt_valida(campos);
}

// Valida campo especificado para registro/edicion de entorno,
// si es incorrecto se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaEntorno() {
    
    var campos = [
        ['dirRaiz', TEXT, exp_entorno, 50, '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como #._-~.']
    ];
    
    return vdt_valida(campos);
}

// Valida cada campo especificado para registro/edicion de entorno-web,
// de ser alguno incorrecto, se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaEntornoWeb() {
        
    var email     = [['email_data', TEXT, exp_email, 50,  '* Especifique un correo electr&oacute;nico v&aacute;lido.']];
    var email_min = [['email_data', TEXT, exp_email_min, 50,  '* Especifique un correo electr&oacute;nico v&aacute;lido.']];
    var pass      = [['pass',       SELECT, "", null,     '* Especifique una contrase&ntilde;a v&aacute;lida.']];
    
    if (vdt_validaDominio()) {
        if (vdt_valida(email_min) || vdt_valida(email)) {
            return vdt_valida(pass);
        }
        return false;
    } else {
        var msg = document.getElementById("msg_dom");
        msg.innerHTML = "* Especifique un dominio o host correctamente.";
        msg.setAttribute("class", "msg-error");
        return false;
    }    
}

// Valida dominio de la forma nombre-dominio.subdominio.dominio | Ip:puerto | localhost
function vdt_validaDominio() {
    
    // Valida dominio - host (ip:puerto)
    var dominio = document.getElementById("dominio").value;
    var exp_dominio  = /^[a-zA-Z0-9][a-zA-Z0-9-_]{0,61}[a-zA-Z0-9]{0,1}\.([a-zA-Z]{1,6}|[a-zA-Z0-9-]{1,30}\.[a-zA-Z]{2,3})$/;
    var exp_ippuerto = /^([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}|localhost)(:[0-9]{1,5})*$/; 
    
    if (exp_dominio.test(dominio)) return true;
    if (exp_ippuerto.test(dominio)) {
        var pos_puerto = dominio.indexOf(':');
        if (pos_puerto > 0) {
            var arr = dominio.split(':');
            var ip     = arr[0];
            var puerto = arr[1];
            if (ip.indexOf('.') > 0) return (vdt_validaIP(ip) && vdt_validaPuerto(puerto));
            return vdt_validaPuerto(puerto);
        } else if (pos_puerto < 0) {
            if (dominio.indexOf('.') > 0) return vdt_validaIP(dominio);
            return true;
        }
    }
    return false;
}

// Valida direccion IP en el rango 1.1.1.1 hasta 254.254.254.254
function vdt_validaIP(ip) {
    
    if (ip.indexOf('.') > 0) {
        var arr = ip.split('.');
        if (arr.length == 4) {
            var a = parseInt(arr[0]);
            var b = parseInt(arr[1]);
            var c = parseInt(arr[2]);
            var d = parseInt(arr[3]);
            if (a >= 1 && a <= 254 &&
                b >= 1 && b <= 254 &&
                c >= 1 && c <= 254 &&
                d >= 1 && d <= 254)
            return true;
        }
    }
    return false;
}

// Valida puerto en el rango 1 hasta 65535
function vdt_validaPuerto(puerto) {
    
    var npuerto = parseInt(puerto);
    return (npuerto >= 1 && npuerto <= 65535);
}

// Valida cada campo especificado para registro/edicion de perfil usuario,
// de ser alguno incorrecto, se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaPerfilUsuario() {
    
    var campos = [
        ['nombre',      TEXT, exp_alfabeto, 50, '* &Uacute;nicamente caracteres del alfabeto. No se admiten tildes ni caracteres especiales.'],
        ['apellido',    TEXT, exp_alfabeto, 50, '* &Uacute;nicamente caracteres del alfabeto. No se admiten tildes ni caracteres especiales.'],
        ['numdoc',      TEXT, exp_numdoc, 10,   '* &Uacute;nicamente valores num&eacute;ricos.'],
        ['email_data',  TEXT, exp_email, 50,    '* Especifique un correo electr&oacute;nico v&aacute;lido.'],
        ['login',       TEXT, exp_login, 20,    '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolo _.']
    ];
    
    return vdt_valida(campos);
}

// Valida cada campo especificado para cambio de contraseña,
// de ser alguno incorrecto, se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaCambioContrasena() {
    
    var campos = [
        ['oldpass',     TEXT, exp_pass, 20, '&Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como #!.'],
        ['newpass',     TEXT, exp_pass, 20, '&Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como #!. De al menos 8 caracteres.'],
        ['cnewpass',    TEXT, exp_pass, 20, '&Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como #!. De al menos 8 caracteres.']
    ];
    
    if (vdt_valida(campos)) {
        
        var oldpass  = document.getElementById('oldpass').value;
        var newpass  = document.getElementById('newpass').value;
        var cnewpass = document.getElementById('cnewpass').value;
        
        if (newpass != cnewpass) {
            var msg = document.getElementById('msg_cnewpass');
            msg.innerHTML = '* Contrase&ntilde;as no coinciden.';
            msg.setAttribute('class', 'msg-error');
            return false;
        } 
        return true;
    } 
    return false;
}

// Valida campo especificado, de ser incorrecto, se notifica al usuario 
// y se retorna valor falso. De lo contrario se retorna verdadero.
function vdt_validaCorreo() {
    
    var campos = [
        ['email_data', TEXT, exp_email, 50, '* Especifique un correo electr&oacute;nico v&aacute;lido.']
    ];
    
    return vdt_valida(campos);
}

// Valida cada campo especificado para registro de usuario externo,
// de ser alguno incorrecto, se notifica al usuario y se retorna valor falso.
// De lo contrario se retorna verdadero.
function vdt_validaUsuarioExterno() {
    
    var campos = [
        ['nombre',      TEXT, exp_alfabeto, 50,     '* &Uacute;nicamente caracteres del alfabeto. No se admiten tildes ni caracteres especiales.'],
        ['apellido',    TEXT, exp_alfabeto, 50,     '* &Uacute;nicamente caracteres del alfabeto. No se admiten tildes ni caracteres especiales.' ],
        ['sempresa',    SELECT, "", null,           '* Seleccione una empresa.'],
        ['sperfil',     SELECT, "", null,           '* Especifique un perfil.'],
        ['numdoc',      TEXT, exp_numdoc, 10,       '* &Uacute;nicamente caracteres num&eacute;ricos.'],
        ['email_data',  TEXT, exp_email, 50,        '* Especifique un correo electr&oacute;nico v&aacute;lido.'],
        ['nomusr',      TEXT, exp_login, 20,        '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolo _.'],
        ['pass',        TEXT, exp_pass, 20,         '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como !#.'],
        ['cpass',       TEXT, exp_pass, 20,         '* &Uacute;nicamente caracteres alfanum&eacute;ricos y s&iacute;mbolos como !#.']
    ];
    
    if (vdt_valida(campos)) {
        
        var pass  = document.getElementById("pass").value;
        var cpass = document.getElementById("cpass").value;
        
        if (pass != cpass) {
            var msg = document.getElementById("msg_cpass");
            msg.innerHTML = "* Contrase&ntilde;as no coinciden.";
            msg.setAttribute("class", "msg-error");
            return false;
        }
        return true;
    }
    return false;
}

// Itera sobre cada campo del arreglo especificado (campos), 
// para el que se valida su contenido y tamaño segun
// el tipo que corresponda. De existir alguno incorrecto,
// se notifica al usuario y se retorna falso, de lo contrario
// se retorna verdadero.
function vdt_valida(campos) {    
    
    if (campos.length <= 0)
        return false;
    
    for (var i = 0; i < campos.length; i++) {
        
        var comp = document.getElementById(campos[i][nombre]);  
        
        if (comp != null) {

            var msg_ = document.getElementById("msg_" + campos[i][nombre]);
            var exp  = campos[i][patron];        

            msg_.innerHTML = "";
            msg_.setAttribute("class", "msg-error");            

            if (campos[i][tipo] == TEXT_OPTIONAL) {
                if (comp.value != "" && !exp.test(comp.value)) {
                    msg_.innerHTML = campos[i][msg];
                    return false;
                }
            }

            if (campos[i][tipo] == TEXT) {                
                if (comp.value == "" || !exp.test(comp.value)) {
                    msg_.innerHTML = campos[i][msg];
                    return false;
                }
                if (campos[i][long] != null && comp.value.length > campos[i][long]) {
                    msg_.innerHTML = "* Longitud m&aacute;xima hasta " + campos[i][long] + " caracteres.";
                    return false;
                }
            }

            if (campos[i][tipo] == SELECT) {
                if (comp.value == null || comp.value == exp) {
                    msg_.innerHTML = campos[i][msg];
                    return false;
                }
            }
        }
    } 
    return true;
}