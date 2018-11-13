$(document).ready(function () {
     var letrasYNumeros = /^(([a-zA-Z]*)+([0-9]*))*$/;
     var Numeros = /^([0-9]*)*$/;
        $('.collap1se').collapse();
        /*CARGA EL COMBO DE BUSQUEDA*/
         $.post("/RDW1/consultaConfiguracion", {id: 1},function (result) {                
                $("#name_conf").append(result);
                $("#name_conf").selectpicker("refresh");
            });
            /*CARGA EL COMBO DE DESACTIVAR*/
            $.post("/RDW1/consultaConfiguracion", {id: 1},function (result) {
                $("#name_del").append(result);
                $("#name_del").selectpicker("refresh");
            });
            /*CARGA EL COMBO DE ACTIVAR*/
            $.post("/RDW1/consultaConfiguracion", {id: 0},function (result) {                
                $("#name_act").append(result);
                $("#name_act").selectpicker("refresh");
            });
        /*FUNCION QUE INACTIVA UN CONFIGURACION*/
         $('#name_conf').change(function (){
            var fk_marca = $('#name_conf').val();                    
            $("#titulo").html("EDITAR PARAMETROS DE LA CONFIGURACION: "+$("#name_conf option[value="+fk_marca+"]").html());
            $.post("/RDW1/buscarConfiguracion", {id: fk_marca},
                    function (result) {
                        //console.log("---> "+$.trim(result));
                        var obj = jQuery.parseJSON( $.trim(result) );                          
                        console.log(obj.pctje_ruta);
                        $('#id_conf').val(obj.id);               
                        $('#name_edit').val(obj.nom);
                        $('#p_max_edit').val(obj.p_max);
                        $('#p_ex_vel_edit').val(obj.p_ex_vel);
                        $('#vel_max_edit').val(obj.vel_max);
                        $('#p_ruta_edit').val(obj.p_ruta);
                        $('#por_ruta_edit').val(obj.pctje_ruta);
                        $('#p_dias_no_labor_edit').val(obj.p_dias_no_labor);
                        $('#dias_desc_edit').val(obj.d_desc);
                        $('#p_ipk_mayor_edit').val(obj.ipk_max);
                        $('#p_ipk_menor_edit').val(obj.ipk_menos);                       
                    });
        });    
        
        /*FUNCION QUE ACTIVA UNA CONFIGURACION*/
        $('#name_act').change(function (){
            var id_c = $('#name_act').val();                                
             $.post("/RDW1/activarConfiguracion", {id:id_c},
                    function (result) {                                                
                        var obj = jQuery.parseJSON( $.trim(result) );                                                  
                        if (obj.id === 1){
                             $('#msgs_act').html("<div class='alert alert-success'> <button type='button' class='close' data-dismiss='alert'>&times;</button> <strong>Informacion </strong>Se ha Activado la configuraci&oacute;n</div>");
                             window.setTimeout(function () {
                                                             $(".alert").fadeTo(500, 0).slideUp(500, function () { $(this).remove(); });
                                                           }, 4000);
                         }else{
                             $('#msgs_act').html("<div class='alert alert-danger'> <button type='button' class='close' data-dismiss='alert'>&times;</button> <strong>Informacion </strong>No se logr&oacute; inactivar la configuraci&oacute;n</div>");
                             window.setTimeout(function () {
                                                             $(".alert").fadeTo(500, 0).slideUp(500, function () { $(this).remove(); });
                                                           }, 4000);
                         }                          
                         activos();
                         inactivos();
                    });                    
        });
        /*FUNCION QUE INACTIVA UNA CONFIGURACION*/
        $('#name_del').change(function (){
            var id_c = $('#name_del').val();                                
             $.post("/RDW1/inactivarConfiguracion", {id:id_c},
                    function (result) {                                                
                        var obj = jQuery.parseJSON( $.trim(result) );                                                  
                        if (obj.id === 1){
                             $('#msgs_del').html("<div class='alert alert-warning'> <button type='button' class='close' data-dismiss='alert'>&times;</button> <strong>Informacion </strong>Se ha inactivado la configuraci&oacute;n</div>");
                             window.setTimeout(function () {
                                                             $(".alert").fadeTo(500, 0).slideUp(500, function () { $(this).remove(); });
                                                           }, 4000);
                         }else{
                             $('#msgs_del').html("<div class='alert alert-danger'> <button type='button' class='close' data-dismiss='alert'>&times;</button> <strong>Informacion </strong>No se logr&oacute; inactivar la configuraci&oacute;n</div>");
                             window.setTimeout(function () {
                                                             $(".alert").fadeTo(500, 0).slideUp(500, function () { $(this).remove(); });
                                                           }, 4000);
                         }                          
                         activos();
                         inactivos();
                    });                    
                    
        });
        
        
        
        $('#guardar_edicion').on("click", function (){
            $(".error").fadeOut().remove();		
            $(".error4").fadeOut().remove();
            var nom;
            var ptje_max;
            var ptje_exe;
            var v_max;
            var ptje_rut;
            var p_rut;
            var ptje_dias;
            var dias_d;
            var ptje_ipk_max;
            var ptje_ipk_min;
            if ($("#name_edit").val() === ""){
            $("#name_edit").focus().after('<span class="error4">Ingrese el nombre</span>');              
            return false;  
            }else if ( ($("#name_edit").val() !== "") && (!letrasYNumeros.test($("#name_edit").val())) ){   
            $("#name_edit").focus().after('<span class="error4">No es un nombre valido; Ingrese el nombre sin signos especiales</span>');            
            $("#name_edit").select();
            return false;
            }else{
             nom = $("#name_edit").val();
            }
            /**********************************************************************************/
            if ($("#p_max_edit").val() === ""){  
            $("#p_max_edit").focus().after('<span class="error4">Ingrese un valor de puntaje valido</span>');                          
            return false;  
            }/*else if ( ($("#p_max_edit").val() !== "") && ( parseInt($("#p_max_edit").val()) < 0) ){            
                $("#p_max_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 1000</span>');            
                $("#p_max_edit").select();
                return false;
            }else if ( ($("#p_max_edit").val() !== "") && ( parseInt($("#p_max_edit").val()) > 1000 ) ){            
                $("#p_max_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 1000</span>');            
                $("#p_max_edit").select();
                return false;
            }*/else if ( ($("#p_max_edit").val() !== "") && (!Numeros.test($("#p_max_edit").val())) ){            
                $("#p_max_edit").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');            
                $("#p_max_edit").select();
                return false;
            }else{
                ptje_max = $("#p_max_edit").val();
            }
            /**********************************************************************************/
            if ($("#p_ex_vel_edit").val() === ""){            
            $("#p_ex_vel_edit").focus().after('<span class="error4">Ingrese los puntos por exceso de velocida</span>');           
            return false;  
            }/*else if ( ($("#p_ex_vel_edit").val() !== "") && ( parseInt($("#p_ex_vel_edit").val()) < 0)){               
                $("#p_ex_vel_edit").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
                $("#p_ex_vel_edit").select();
                return false;
            }else if ( ($("#p_ex_vel_edit").val() !== "") && ( parseInt($("#p_ex_vel_edit").val()) > 10)){               
                $("#p_ex_vel_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 10</span>');                          
                $("#p_ex_vel_edit").select();
                return false;
            }*/else if ( ($("#p_ex_vel_edit").val() !== "") && (!Numeros.test($("#p_ex_vel_edit").val())) ){               
                $("#p_ex_vel_edit").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
                $("#p_ex_vel_edit").select();
                return false;
            }else{
                ptje_exe = $("#p_ex_vel_edit").val();
            }
            /***********************************************************************************/
            if ($("#vel_max_edit").val() === ""){            
            $("#vel_max_edit").focus().after('<span class="error4">Ingrese el limite de velocidad permitido</span>');           
            return false;  
            }/*else if ( ($("#vel_max_edit").val() !== "") && ( parseInt($("#vel_max_edit").val()) < 0) ){               
                $("#vel_max_edit").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
                $("#vel_max_edit").select();
                return false;
            }else if ( ($("#vel_max_edit").val() !== "") && ( parseInt($("#vel_max_edit").val()) > 10) ){               
                $("#vel_max_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 10</span>');                          
                $("#vel_max_edit").select();
                return false;
            }*/else if ( ($("#vel_max_edit").val() !== "") && (!Numeros.test($("#vel_max_edit").val())) ){               
                $("#vel_max_edit").focus().after('<span class="error4">No es un limite de velocidad permitido, Ingrese el limite de velocidad permitido</span>');                          
                $("#vel_max_edit").select();
                return false;
            }else{
                v_max = $("#vel_max_edit").val();
            }
            /***********************************************************************************/
            if ($("#p_ruta_edit").val() === ""){            
            $("#p_ruta_edit").focus().after('<span class="error4">Ingrese los puntos por ruta no cumplida</span>');           
            return false;  
            }/*else if ( ($("#p_ruta_edit").val() !== "") && ( parseInt($("#p_ruta_edit").val()) < 0)){               
                $("#p_ruta_edit").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
                $("#p_ruta_edit").select();
                return false;
            }else if ( ($("#p_ruta_edit").val() !== "") && ( parseInt($("#p_ruta_edit").val()) > 10)){               
                $("#p_ruta_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 10</span>');                          
                $("#p_ruta_edit").select();
                return false;
            }*/else if ( ($("#p_ruta_edit").val() !== "") && (!Numeros.test($("#p_ruta_edit").val())) ){               
                $("#p_ruta_edit").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
                $("#p_ruta_edit").select();
                return false;
            }else{
                ptje_rut = $("#p_ruta_edit").val();
            }
            /***********************************************************************************/
            if ($("#por_ruta_edit").val() === ""){            
            $("#por_ruta_edit").focus().after('<span class="error4">Ingrese el porcentaje de la ruta</span>');           
            return false;  
            }/*else if ( ($("#por_ruta_edit").val() !== "") && ( parseInt($("#por_ruta_edit").val()) < 0)){               
                $("#por_ruta_edit").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
                $("#por_ruta_edit").select();
                return false;
            }else if ( ($("#por_ruta_edit").val() !== "") && ( parseInt($("#por_ruta_edit").val()) > 10)){               
                $("#por_ruta_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 10</span>');                          
                $("#por_ruta_edit").select();
                return false;
            }*/else if ( ($("#por_ruta_edit").val() !== "") && (!Numeros.test($("#por_ruta_edit").val())) ){               
                $("#por_ruta_edit").focus().after('<span class="error4">No es un valor valido, Ingrese el valor nuevamente</span>');                          
                $("#por_ruta_edit").select();
                return false;
            }else{
               p_rut = $("#por_ruta_edit").val();
            }
            /***********************************************************************************/
            if ($("#p_dias_no_labor_edit").val() === ""){            
            $("#p_dias_no_labor_edit").focus().after('<span class="error4">Ingrese los puntos por dias no laborados</span>');           
            return false;  
            }/*else if ( ($("#p_dias_no_labor_edit").val() !== "") && ( parseInt($("#p_dias_no_labor_edit").val()) < 0)){               
                $("#p_dias_no_labor_edit").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
                $("#p_dias_no_labor_edit").select();
                return false;
            }else if ( ($("#p_dias_no_labor_edit").val() !== "") && ( parseInt($("#p_dias_no_labor_edit").val()) > 10)){               
                $("#p_dias_no_labor_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 10</span>');                          
                $("#p_dias_no_labor_edit").select();
                return false;
            }*/else if ( ($("#p_dias_no_labor_edit").val() !== "") && (!Numeros.test($("#p_dias_no_labor_edit").val())) ){               
                $("#p_dias_no_labor_edit").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
                $("#p_dias_no_labor_edit").select();
                return false;
            }else{
               ptje_dias = $("#p_dias_no_labor_edit").val();
            }
            /***********************************************************************************/
            
            if ($("#dias_desc_edit").val() === ""){            
            $("#dias_desc_edit").focus().after('<span class="error4">Ingrese los dias de descanso</span>');           
            return false;  
            }/*else if ( ($("#dias_desc_edit").val() !== "") && ( parseInt($("#dias_desc_edit").val()) < 0)){               
                $("#dias_desc_edit").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
                $("#dias_desc_edit").select();
                return false;
            }else if ( ($("#dias_desc_edit").val() !== "") && ( parseInt($("#dias_desc_edit").val()) > 10)){               
                $("#dias_desc_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 10</span>');                          
                $("#dias_desc_edit").select();
                return false;
            }*/else if ( ($("#dias_desc_edit").val() !== "") && (!Numeros.test($("#dias_desc_edit").val())) ){               
                $("#dias_desc_edit").focus().after('<span class="error4">No es un valor valido, Ingrese nuevamente los dias de descanso</span>');                          
                $("#dias_desc_edit").select();
                return false;
            }else{
                dias_d = $("#dias_desc_edit").val();
            }
            /***********************************************************************************/
            if ($("#p_ipk_mayor_edit").val() === ""){            
            $("#p_ipk_mayor_edit").focus().after('<span class="error4">Ingrese los puntos ipk mayor</span>');           
            return false;  
            }/*else if ( ($("#p_ipk_mayor_edit").val() !== "") && ( parseInt($("#p_ipk_mayor_edit").val()) < 0)){               
                $("#p_ipk_mayor_edit").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
                $("#p_ipk_mayor_edit").select();
                return false;
            }else if ( ($("#p_ipk_mayor_edit").val() !== "") && ( parseInt($("#p_ipk_mayor_edit").val()) > 10)){               
                $("#p_ipk_mayor_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 10</span>');                          
                $("#p_ipk_mayor_edit").select();
                return false;
            }*/else if ( ($("#p_ipk_mayor_edit").val() !== "") && (!Numeros.test($("#p_ipk_mayor_edit").val())) ){               
                $("#p_ipk_mayor_edit").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
                $("#p_ipk_mayor_edit").select();
                return false;
            }else{
                ptje_ipk_max = $("#p_ipk_mayor_edit").val();
            }
            /***************************************************************************************/
            if ($("#p_ipk_menor_edit").val() === ""){            
            $("#p_ipk_menor_edit").focus().after('<span class="error4">Ingrese los puntos por ipk menor</span>');           
            return false;  
            }/*else if ( ($("#p_ipk_menor_edit").val() !== "") && ( parseInt($("#p_ipk_menor_edit").val()) < 0)){               
                $("#p_ipk_menor_edit").focus().after('<span class="error4">Ingresar un valor mayor a cero</span>');                          
                $("#p_ipk_menor_edit").select();
                return false;
            }else if ( ($("#p_ipk_menor_edit").val() !== "") && ( parseInt($("#p_ipk_menor_edit").val()) > 10)){               
                $("#p_ipk_menor_edit").focus().after('<span class="error4">Ingresar un valor entre cero y 10</span>');                          
                $("#p_ipk_menor_edit").select();
                return false;
            }*/else if ( ($("#p_ipk_menor_edit").val() !== "") && (!Numeros.test($("#p_ipk_menor_edit").val())) ){               
                $("#p_ipk_menor_edit").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
                $("#p_ipk_menor_edit").select();
                return false;
            }else{
                ptje_ipk_min = $("#p_ipk_menor_edit").val();
            }
            /*ENVÍO POST QUE TRAE LAS VUELTAS A LIQUIDAR Y LAS PONE EN LA PRIMERA PESTAÑA*/            
            $.post("/RDW1/editarConfiguracion?nocache="+new Date().getTime(), {id: $("#id_conf").val(),nombre: nom, puntaje_max: ptje_max, puntos_exceso_vel: ptje_exe,
                                                                               vel_max: v_max, puntos_ruta: ptje_rut, porcentaje_ruta: p_rut, puntos_dias_no_labor: ptje_dias,
                                                                               cantidad_dias_descanso: dias_d, puntos_ipk_mayor: ptje_ipk_max, puntos_ipk_menor: ptje_ipk_min},
                    function (result) {                        
                        var obj = jQuery.parseJSON( $.trim(result) ); 
                        console.log("---> "+obj.id);
                        if (obj.id === 1)
                        {
                            $('#msgs').html("<div class='alert alert-success'> <button type='button' class='close' data-dismiss='alert'>&times;</button> <strong>Informacion </strong>Se ha modificado la informaci&oacute;n correctamente</div>");
                            window.setTimeout(function () {
                                                            $(".alert").fadeTo(500, 0).slideUp(500, function () { $(this).remove(); });
                                                          }, 4000);
                        }
                        else{
                            $('#msgs').html("<div class='alert alert-danger'> <button type='button' class='close' data-dismiss='alert'>&times;</button> <strong>Informacion </strong>No se logr&oacute; modificar correctamente</div>");
                            window.setTimeout(function () {
                                                            $(".alert").fadeTo(500, 0).slideUp(500, function () { $(this).remove(); });
                                                          }, 4000);
                        }
                    });
         });                       
      
    
     
        $(" #name1, #p_max, #p_ex_vel, #vel_max, #p_ruta, #por_ruta, #p_dias_no_labor, #dias_desc, #p_ipk_mayor, #p_ipk_menor").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {
			$('.error4').fadeOut();
                        $('.error4').fadeOut();
			
		}  
	});
        
        
    });    
    
    
    function activos(){
     $.post("/RDW1/consultaConfiguracion", {id:1},function (result) {                                     
                $("#name_del").html($.trim(result));
                $("#name_del").selectpicker("refresh");                
            });  
    }

    function inactivos(){
     $.post("/RDW1/consultaConfiguracion", {id:0},function (result) {                                     
                $("#name_act").html($.trim(result));
                $("#name_act").selectpicker("refresh");                
            });  
    }