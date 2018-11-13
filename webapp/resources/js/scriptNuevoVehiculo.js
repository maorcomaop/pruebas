
$(document).ready(function () {    
    //var cod = /^([a-zA-Z]+[0-9]{3,3})*$/;    
    var intNum = /^(([a-zA-Z]*)+([0-9]*))*$/;
    //$('#codigo_gps_edit').on('blur');
    $('.collap1se').collapse();
    
    $('#f_v_').datetimepicker({format: 'YYYY-MM-DD', useCurrent: true, locale: 'es', defaultDate: 'now'});        
    $('#f_v_to').datetimepicker({format: 'YYYY-MM-DD', useCurrent: true, locale: 'es', defaultDate: 'now'});            
    $('#fv_rtm_ed').datetimepicker({format: 'YYYY-MM-DD', useCurrent: true, locale: 'es', defaultDate: 'now'});            
    $('#fv_to_ed').datetimepicker({format: 'YYYY-MM-DD', useCurrent: true, locale: 'es', defaultDate: 'now'});            
    /*******************************************/
    
    /*window.setTimeout(function () {
        $(".alert").fadeTo(500, 0).slideUp(500, function () {
            //$(this).remove();
            $(this).removeClass("alert");
        });
    }, 4000);*/
    
    
    //CARGA LOS CENTROS DE DIAGNOSTICO
     cargarCDA();  
     //CARGA LOS CENTROS DE EXPEDICION
     cargarCE();
    
    
    /******CARGA LOS PROPIETARIOS*****************/
    $.post("/RDW1/consultarPerfilPropietario", {perfil: 'Propietario'},function (result) {

                $.post("/RDW1/consultarPropietario", {id_perfil: $.trim(result)},
                        function (result) {
                            $("#propietario").append(result);
                            $("#propietario").selectpicker("refresh");
                             $.post("/RDW1/consultarPropietarioDefecto", {nombre: 'Propietario'},
                                    function (result) {                
                                        var obj = jQuery.parseJSON( $.trim(result) );                                        
                                        $('select[name=propietario]').val(obj.id);
                                        $("#propietario").selectpicker("refresh");
                                    });/*POST BUSCA PROPIETARIO POR DEFECTO*/
                        });/*POST BUSCA PROPIETARIO CONSULTA*/                
            });/*POST BUSCA EL PERFIL*/
    /*******CARGA TODOS LOS PRODUCTOS DE HARWARE**************/
    $.post("/RDW1/consultaHardware", {id: 1},function (result) {                
                $("#hrdwr").append($.trim(result));
                $("#hrdwr").selectpicker("refresh");
            });
    /***********CARGA TODOS LOS OPERADORES********************/
    $.post("/RDW1/consultaOperadores", {id: 1},function (result) {
                //console.log(result);
                $("#oper").append(result);
                $("#oper").selectpicker("refresh");
            });    
    /***********CARGA LA MARCA DE GPS*************************/
    $.post("/RDW1/consultaMarcaGPS", {id: 1},function (result) {                
                $("#brand").append(result);
                $("#brand").selectpicker("refresh");
            });
    /***********CARGA LAS SIMCARD*****************************/
    $.post("/RDW1/consultaTarjetasSim", {id: 1},function (result) {                
                $("#cel").append(result);
                $("#cel").selectpicker("refresh");
            });
    /***********CONSULTA EL GRUPO POR DEFECTO*****************/
    $.post("/RDW1/consultarGrupoDefecto", {gp: 'Grupo por defecto'},function (result) {                
                var obj = jQuery.parseJSON( $.trim(result) );
                //console.log($.trim(result));  
                $('select[name=grupo]').val(obj.id);
                $("#group").selectpicker("refresh");
            });                            
    /*limita la matricula del vehiculo*/
    $('#matricula').keyup(function () {
        $(this).val($(this).val().toUpperCase());        
    });    
    $("#matricula").blur(function(){
        if ($("#matricula").val() !== ''){
           if (intNum.test($("#matricula").val())){
              /*verifica si tiene 3 letras y 3 numeros*/
              $.post("/RDW1/placaRepetida", {placa: $(this).val()}, function (result) {                
                var obj = jQuery.parseJSON( $.trim(result) );                
                    if (obj.id >= 1){
                        
                        $('#number').prop('disabled', true);
                        
                        $('#prop').prop('disabled', true);
                        $('#prop').selectpicker("refresh");                        
                        
                        $('#pasenger').prop('disabled', true);
                        $('#pasenger').selectpicker("refresh");
                        
                        $('#oper_edit').prop('disabled', true);
                        $("#oper_edit").selectpicker("refresh");
                        
                        $('#group').prop('disabled', true);
                        $("#group").selectpicker("refresh");
                        
                        
                        $('#dayPico').prop('disabled', true);
                        $("#dayPico").selectpicker("refresh");
                        
                        $('#dayholiday').prop('disabled', true);
                        $("#dayholiday").selectpicker("refresh");
                        
                        
                        $('#hrdwr').prop('disabled', true);
                        $("#hrdwr").selectpicker("refresh");
                        
                        $("#idgps").prop('disabled', true);
                        
                        $('#brand').prop('disabled', true);
                        $("#brand").selectpicker("refresh");
                        
                        $('#model').prop('disabled', true);
                        $("#model").selectpicker("refresh");
                        
                        $("#n_cel").prop('disabled', true);
                        
                        $('#oper').prop('disabled', true);
                        $("#oper").selectpicker("refresh");
                        
                        alert("El VEH\u00cdCULO: "+$("#matricula").val()+" ya fue REGISTRADO");
                    }
                    else{
                        $('#number').prop('disabled', false);
                        
                        $('#prop').prop('disabled', false);
                        $('#prop').selectpicker("refresh");                        
                        
                        $('#pasenger').prop('disabled', false);
                        $('#pasenger').selectpicker("refresh");
                        
                        $('#oper_edit').prop('disabled', false);
                        $("#oper_edit").selectpicker("refresh");
                        
                        $('#group').prop('disabled', false);
                        $("#group").selectpicker("refresh");
                        
                        
                        $('#dayPico').prop('disabled', false);
                        $("#dayPico").selectpicker("refresh");
                        
                        $('#dayholiday').prop('disabled', false);
                        $("#dayholiday").selectpicker("refresh");
                        
                        
                        $('#hrdwr').prop('disabled', false);
                        $("#hrdwr").selectpicker("refresh");
                        
                        $("#idgps").prop('disabled', false);
                        
                        $('#brand').prop('disabled', false);
                        $("#brand").selectpicker("refresh");
                        
                        $('#model').prop('disabled', false);
                        $("#model").selectpicker("refresh");
                        
                        $("#n_cel").prop('disabled', false);
                        
                        $('#oper').prop('disabled', false);
                        $("#oper").selectpicker("refresh");
                    }
            });/*FIN POST*/
          }else{                 
                 alert("NO ES UNA MATRICULA VALIDA");                 
          }/*FIN SI REVISA PLACA*/
        }//FIN SI CAJA VACIA
          
        
        
    });
      /*AL CAMBIAR LA MARCA TRAE EL MODELO*/
    $('#brand').change(function (){
        var fk_marca = $('#brand').val();        
        $.post("/RDW1/consultaModeloGPS", {id: fk_marca},
                function (result) {                    
                    $('#model').empty();
                    $("#model").append(result);
                    $("#model").selectpicker("refresh");
                });
    });    
    /*ENCUENTRA EL OPERADOR DE LA SIM CARD PARA SELECCIONAR*/
    $('#cel').change(function (){
        var id_op = $('#cel').val();        
        $.post("/RDW1/consultaOperador", {id: id_op},
                function (result) {
                    console.log($.trim(result));  
                    var obj = jQuery.parseJSON( $.trim(result) );
                    $("#oper option[value="+obj.id+"]").attr("selected",true);                                
                    $("#oper").selectpicker("refresh");
                });
    });
     
    /********************COMBOS DEL FORMULARIO DE EDICION*************************/
    $.post("/RDW1/consultaGpsSinAsociar", {id:0},function (result) {
                //console.log(result);
                $("#cod_gps_asociar_rec").append(result);
                $("#cod_gps_asociar_rec").selectpicker("refresh");
            });        
    /*ENCUENTRA EL OPERADOR DE LA SIM CARD PARA SELECCIONAR*/
    $('#cel_edit').change(function (){
        var id_op = $('#cel_edit').val();        
        $.post("/RDW1/consultaOperador", {id: id_op},
                function (result) {
                    console.log($.trim(result));  
                    var obj = jQuery.parseJSON( $.trim(result) );
                    $("#oper_edit option[value="+obj.id+"]").attr("selected",true);                                
                    $("#oper_edit").selectpicker("refresh");
                });
    });
    /*FUNCION QUE PERMITE MOSTRAR LOS DATOS DEL GPS EN REGISTRO*/
    $('#hrdwr').change(function (){
        var id = $('#hrdwr').val();
        if (id === '3'){
            console.log("quitar cajas");
            $('#idgps').hide();            
            $('#etq_idgps').hide();
            
            $('#etq_model').hide();            
            $('#model').selectpicker('hide'); 
            
            $('#etq_brand').hide();            
            $('#brand').selectpicker('hide'); 
            
            $('#etq_n_cel').hide();          
            $('#n_cel').hide();            
            
            $('#etq_oper').hide();                        
            $('#oper').selectpicker('hide');
        }
        else{
            console.log("adicionar cajas");
            $('#idgps').show();
            $('#etq_idgps').show();            
            
            $('#etq_model').show();            
            $('#model').selectpicker('show'); 
            
            $('#etq_brand').show();                        
            $('#brand').selectpicker('show'); 
            
            $('#etq_n_cel').show();                        
            $('#n_cel').show();            
            
            $('#etq_oper').show();            
            $('#oper').selectpicker('show');            
        }
    });    
    /***************************************************************/
    /*FUNCION QUE PERMITE MOSTRAR LOS DATOS DEL GPS EN EDITAR REGISTRO*/
    $('#hrdwr_edit').change(function (){
        var id = $('#hrdwr_edit').val();
        if (id === '3'){
            
            
            $('#mas').hide();      
            $('#update_sim').hide();                  
            $('#asociar').hide();
            
            $('#etq_cod_gps_edit').hide();            
            $('#cod_gps_edit').hide();            
            
            $('#etq_brand_edit').hide();            
            $('#brand_edit').selectpicker('hide'); 

            $('#etq_model_edit').hide();                        
            $('#model_edit').selectpicker('hide'); 
            
            $('#etq_cel_edit').hide();                      
            $('#cel_edit').selectpicker('hide'); 
            
            $('#etq_oper_edit').hide();                        
            $('#oper_edit').selectpicker('hide');
        }
        else{
            
            $('#mas').show(); 
            $('#update_sim').show();            
            $('#etq_cod_gps_edit').show();
            $('#cod_gps_edit').show();            
            
            $('#etq_brand_edit').show();            
            $('#brand_edit').selectpicker('show'); 

            $('#etq_model_edit').show();                        
            $('#model_edit').selectpicker('show'); 
            
            $('#etq_cel_edit').show();          
            $('#cel_edit').selectpicker('show');             
            
            $('#etq_oper_edit').show();                        
            $('#oper_edit').selectpicker('show');          
        }
    });                   
    $('#marca_gps_rec').change(function () {
        var fk_marca = $('#marca_gps_rec').val();        
        $.post("/RDW1/consultaModeloGPS", {id: fk_marca},
                function (result) {                    
                    $('#modelo_gps_rec').empty();
                    $("#modelo_gps_rec").append(result);
                    $("#modelo_gps_rec").selectpicker("refresh");
                });
    });   
   
    /*******************BUSCA SI EL GPS YA SE ENCUENTRA ASOCIADO en el registro*******************************************************/        
    $("#idgps").focusin(function(){
              if ($('#hrdwr').val() === ''){
                   $('#msjRec').html("<div class='alert alert-info' id='mensajeRec' role='alert'></div>");
                   $('#mensajeRec').html("NO ha seleccionado un dispositivo <strong>REGISBUS</strong>; Por favor seleccione un dispositivo.");
                   $('#mensajeRec').removeClass("alert-info");
                   $('#mensajeRec').addClass("alert-danger");                            
                   $('#mensajeRec').show().fadeOut(10000);                   
              }
     });
     $("#idgps").blur(function(){                                  
              if ($(this).val() ===''){
                  $("#hrdwr").val(undefined);
                  $("#hrdwr").selectpicker("refresh");
              }else{
                  $.post("/RDW1/gpsRepetido", {id: $(this).val()}, function (result) {                
                  //console.log("--->"+$.trim(result));
                    var obj = jQuery.parseJSON( $.trim(result) );                

                        if (obj.id >= 1){                        
                            $('#brand').prop('disabled', true);
                            $('#brand').val(obj.fk_marca);
                            $('#brand').selectpicker("refresh");                        

                            $('#model').prop('disabled', true);
                            $('#model').val(obj.fk_modelo);
                            $('#model').selectpicker("refresh");
                            $('#msjRec').html("<div class='alert alert-info' id='mensajeRec' role='alert'> <button type='button' class='close fade' data-dismiss='alert'>&times;</button></div>");
                            $('#mensajeRec').html("El GPS <strong>"+$("#idgps").val()
                                                +"</strong> ya se encuentra <strong>ASOCIADO.</strong>;Por favor ingrese un nuevo ID GPS");
                            $('#mensajeRec').removeClass("alert-info");
                            $('#mensajeRec').addClass("alert-danger");                            
                            $('#mensajeRec').show().fadeOut(12500);    
                            //alert("El GPS "+$("#codigo_gps_edit").val()+" ya se encuentra asociado\n Ingrese un id diferente");

                        }
                        else{                  
                            $('#brand').prop('disabled', false);
                            $('#brand').val(obj.fk_marca);
                            $('#brand').selectpicker("refresh");                        

                            $('#model').prop('disabled', false);
                            $('#model').val(obj.fk_modelo);
                            $('#model').selectpicker("refresh");
                             $('#msjRec').html("<div class='alert alert-info' id='mensajeRec' role='alert'> <button type='button' class='close fade' data-dismiss='alert'>&times;</button></div>");
                            $('#mensajeRec').html("El GPS <strong>"+$("#idgps").val()
                                               +"</strong> NO se ha <strong>REGISTRADO</strong> y NO se encuentra <strong>ASOCIADO.</strong>; Por favor seleccione una marca y modelo para registrar y/o asociar este GPS");
                            $('#mensajeRec').removeClass("alert-info");
                            $('#mensajeRec').addClass("alert-success");                            
                            $('#mensajeRec').show().fadeOut(12500);
                            //alert("El GPS "+$("#idgps").val()+" No se ha registrado Y NO se encuentra asociado. \n Por favor seleccione una marca y modelo para registrar y/o asociar este GPS");
                        }
                    });/*FIN POST*/
            
              }//FIN SINO CAJA VACIA              
              
        
    });
     
    /*******************BUSCA SI LA SIMCARD YA SE ENCUENTRA ASOCIADA A OTRO GPS EN EL REGISTRO*******************************************************/        
     $("#n_cel").blur(function(){                    
              $.post("/RDW1/simRepetida", {numero: $(this).val()}, function (result) { 
                  console.log($.trim(result));
                var obj = jQuery.parseJSON( $.trim(result) );                                
                    if (obj.id >= 1){            
                        $('#oper').prop('disabled', true);
                        $("#oper").selectpicker("refresh");                        
                        $('#id_sim').val("");  
                        $('#msjRec').html("<div class='alert alert-info' id='mensajeRec' role='alert'> <button type='button' class='close fade' data-dismiss='alert'>&times;</button></div>");
                        $('#mensajeRec').html("El n\u00FAmero celular <strong>"+$("#n_cel").val()+"</strong> ya se encuentra asociado a un GPS; Ingrese un numero diferente.");
                        $('#mensajeRec').removeClass("alert-info");
                        $('#mensajeRec').addClass("alert-danger");                            
                        $('#mensajeRec').show().fadeOut(12500);   
                        //alert("El n\u00FAmero celular "+$("#n_cel").val()+" ya se encuentra asociado a un GPS, Ingrese un numero diferente.");
                    }
                    else{  
                        $('#id_sim').val(obj.id_sim); 
                        $('#oper').prop('disabled', false);
                        $('#oper').val(obj.fk_operador);
                        $("#oper").selectpicker("refresh");     
                        $('#codigo_gps_edit').on('blur');
                        $('#msjRec').html("<div class='alert alert-info' id='mensajeRec' role='alert'> <button type='button' class='close fade' data-dismiss='alert'>&times;</button></div>");
                        $('#mensajeRec').html("El n\u00FAmero celular <strong>"+$("#n_cel").val()+" </strong>NO se encuentra <strong>REGISTRADO</strong> y NO se encuentra <strong>ASOCIADO</strong> a un GPS; Seleccione un <strong>OPERADOR</strong> para registrar y/o asignar este numero celular");
                        $('#mensajeRec').removeClass("alert-info");
                        $('#mensajeRec').addClass("alert-success");                            
                        $('#mensajeRec').show().fadeOut(12500);  
                        //alert("El n\u00FAmero celular "+$("#n_cel").val()+" NO se encuentra registrado y NO se encuentra asociado a un GPS\n Seleccione un operador para registrar y/o asignar este numero celular");
                    }
            });/*FIN POST*/        
    });       
    //    
     $("#codigo_gps_edit").focusin(function(){
              if ($('#hrdwr_edit').val() === ''){
                   $('#msj').html("<div class='alert alert-info' id='mensaje' role='alert'></div>");
                   $('#mensaje').html("NO ha seleccionado un dispositivo <strong>REGISBUS</strong> \n Por favor seleccione un dispositivo.");
                   $('#mensaje').removeClass("alert-info");
                   $('#mensaje').addClass("alert-danger");                            
                   $('#mensaje').show().fadeOut(10000);                   
              }
     });
    /*******************BUSCA SI EL GPS YA SE ENCUENTRA ASOCIADO EN LA EDICION*******************************************************/        
     $("#codigo_gps_edit").blur(function(){                           
              if ($(this).val() ===''){
                  $("#hrdwr_edit").val(undefined);
                  $("#hrdwr_edit").selectpicker("refresh");
              }else{
                  $.post("/RDW1/gpsRepetido", {id: $(this).val()}, function (result) {
                        
                        var obj = jQuery.parseJSON($.trim(result));
                        if (obj.id >= 1) {
                            $('#marca_gps_rec').prop('disabled', true);
                            $('#marca_gps_rec').val(obj.fk_marca);
                            $('#marca_gps_rec').selectpicker("refresh");

                            $('#modelo_gps_rec').prop('disabled', true);
                            $('#modelo_gps_rec').val(obj.fk_modelo);
                            $('#modelo_gps_rec').selectpicker("refresh");
                            
                            $('#msj').html("<div class='alert alert-info' id='mensaje' role='alert'> <button type='button' class='close fade' data-dismiss='alert'>&times;</button></div>");
                            $('#mensaje').html("El GPS <strong>"+$("#codigo_gps_edit").val()
                                                +"</strong> ya se encuentra <strong>ASOCIADO.</strong> \n Por favor ingrese un nuevo ID GPS");
                            $('#mensaje').removeClass("alert-info");
                            $('#mensaje').addClass("alert-danger");                            
                            $('#mensaje').show().fadeOut(12500);
                        } else {
                            $('#marca_gps_rec').prop('disabled', false);
                            $('#marca_gps_rec').val(obj.fk_marca);
                            $('#marca_gps_rec').selectpicker("refresh");

                            $('#modelo_gps_rec').prop('disabled', false);
                            $('#modelo_gps_rec').val(obj.fk_modelo);
                            $('#modelo_gps_rec').selectpicker("refresh");                            
                            
                            $('#msj').html("<div class='alert alert-info' id='mensaje' role='alert'> <button type='button' class='close fade' data-dismiss='alert'>&times;</button></div>");
                            $('#mensaje').html("El GPS <strong>"+$("#codigo_gps_edit").val()
                                               +"</strong> NO se ha <strong>REGISTRADO</strong> y NO se encuentra <strong>ASOCIADO.</strong> \n Por favor seleccione una marca y modelo para registrar y/o asociar este GPS");
                            $('#mensaje').removeClass("alert-info");
                            $('#mensaje').addClass("alert-success");                            
                            $('#mensaje').show().fadeOut(12500);
                        }
                    });//FIN POST
              }//FIN ELSE
             
    });     
     /*******************BUSCA SI LA SIMCARD YA SE ENCUENTRA ASOCIADA A OTRO GPS EN LA EDICION*******************************************************/        
     $("#numero_celular_edit").blur(function(){   
            
            if ($(this).val() ===''){
                  $("#oper_gps_rec").val(undefined);
                  $("#oper_gps_rec").selectpicker("refresh");
              }else{
                  $.post("/RDW1/simRepetida", {numero: $(this).val()}, function (result) { 
                  console.log($.trim(result));
                     var obj = jQuery.parseJSON( $.trim(result) );                                
                    if (obj.id >= 1){            
                        $('#oper_gps_rec').prop('disabled', true);
                        $("#oper_gps_rec").selectpicker("refresh");                        
                        $('#id_sim_edit').val("");
                        $('#msj').html("<div class='alert alert-info' id='mensaje' role='alert'> <button type='button' class='close fade' data-dismiss='alert'>&times;</button></div>");
                        $('#mensaje').html("El n\u00FAmero celular <strong>"+$("#numero_celular_edit").val()+"</strong> ya se encuentra asociado a un GPS, Ingrese un numero diferente.");
                        $('#mensaje').removeClass("alert-info");
                        $('#mensaje').addClass("alert-danger");                            
                        $('#mensaje').show().fadeOut(12500);                        
                    }
                    else{
                        $('#id_sim_edit').val(obj.id_sim); 
                        $('#oper_gps_rec').prop('disabled', false);
                        $('#oper_gps_rec').val(obj.fk_operador);
                        $("#oper_gps_rec").selectpicker("refresh");
                        $('#msj').html("<div class='alert alert-info' id='mensaje' role='alert'> <button type='button' class='close fade' data-dismiss='alert'>&times;</button></div>");
                        $('#mensaje').html("El n\u00FAmero celular <strong>"+$("#numero_celular_edit").val()+" </strong>NO se encuentra <strong>REGISTRADO</strong> y NO se encuentra <strong>ASOCIADO</strong> a un GPS\n Seleccione un operador para registrar y/o asignar este numero celular");
                        $('#mensaje').removeClass("alert-info");
                        $('#mensaje').addClass("alert-success");                            
                        $('#mensaje').show().fadeOut(12500);                              
                    }
                   });/*FIN POST*/                  
              }//FIN ELSE VACIO
    });           
    
    /*****************FUNCIONES GPS**********************************************/               
        $('#asociar_gps_rec').on("click", function (){            
            //var id_vhc = $("#vh2").val();
            var id_vhc_form = $("#id_car").val();
            var id_hd=$("#hrdwr_edit").val();
            var id_gp= $("#cod_gps_asociar_rec").val();
            var id_s = $("#numero_celular_gps").val();
            //var placa = $("#vh2 option[value="+id_vhc_form+"]").html();
            var placa = $("#placa_edit").val();
            
            /******************************************************/
             /*var si;
            if ($("#asociar_gps_vh").prop('checked') === true){
               si=1;
               var id_vhc_form = $("#id_car").val();
               console.log("si");
            }else{                
                si = 0;
                var id_vhc = $("#vh2").val();
                console.log("no");
            }    */ 
            /******************************************************/
              $.post("/RDW1/consultaVehiculoTieneGps", {id: $("#id_car").val()}, function (result) {                                  
                var obj = jQuery.parseJSON( $.trim(result) );  
                if (obj.id_gps === "no_aplica"){
                    if ($("#hrdwr_edit").val()){    
                            $.post("/RDW1/asociarGps", {id_vh:id_vhc_form, id_gps:id_gp, id_hrd:id_hd, id_sim:id_s}, function (result) { 
                                var obj = jQuery.parseJSON( $.trim(result) ); 
                                //console.log("-->>>"+obj.id+" >>>> "+$.trim(result)+"id seleccionado: "+id_vhc+" id cargado en el form "+id_vhc_form);
                                    if(obj.id === 0){                                        
                                        alert("La unidad NO se pudo ASOCIAR");
                                    }else{//compara si el vehiculo que se registro con el gps es el mismo que tiene informacion cargada en el formulario
                                            
                                            if (obj.id === parseInt(id_vhc_form)){
                                                
                                                  $('#cod_gps_edit').val(obj.cod);
                                                  $('#cod_gps_edit').prop('disabled', true);                                                  
                                                  $('#brand_edit').val(obj.marca);
                                                  $('#brand_edit').prop('disabled', true);                                                  
                                                  $('#model_edit').val(obj.modelo);
                                                  $('#model_edit').prop('disabled', true);
                                                  
                                                  $('#cel_edit').val(obj.celular);
                                                  $('#cel_edit').prop('disabled', true);                                                  
                                                  $('#oper_edit').val(obj.operador);
                                                  $('#oper_edit').prop('disabled', true);
                                                  
                                                  //cajas ocultas del form
                                                  $('#id_gps_a').val(obj.cod);
                                                  $('#id_har').val(id_hd);
                                                  $('#id_rel_hgv').val("");
                                                  $('#id_cel').val(obj.fk_sim);
                                                  $('#id_op').val(obj.fk_operador);  
                                                  
                                                  $("#cod_gps_asociar_rec").val('default');                                                        
                                                  $("#cod_gps_asociar_rec").selectpicker("refresh");
                                                  
                                                  $("#numero_celular_gps").val('default');                                                        
                                                  $("#numero_celular_gps").selectpicker("refresh");
                                                  
                                                  $("#vh2").val('default');                                                        
                                                  $("#vh2").selectpicker("refresh");
                                                  $("#asociar_gps").hide();
                                                  $("#registrar_gps").hide(); 
                                                  $("#asociar_sim").hide(); 
                                                  $("#registrar_sim").hide(); 
                                                  
                                                  alert("la Unidad GPS "+obj.cod+" se ha asociado al veh\u00EDculo "+placa+" Correctamente");
                                                }else{
                                                    alert("la Unidad GPS "+obj.cod+" se ha asociado al veh\u00EDculo "+placa+" Correctamente");
                                            }
                                            console.log("la Unidad GPS "+obj.cod+" se ha asociado al veh\u00ED"+placa+" Correctamente");   
                                        }
                            });   
                    }else{ //FIN SI TIENE HARDWARE
                        //$('#asociar_gps').modal('hide');
                        alert("Debe seleccionar un dispositivo o tecnolog\u00EDa Regisbus");
                    }//FIN ELSE TIENE HARDWARE
                }else{//FIN SI TIENE GPS ASIGNADO
                    alert("El veh\u00EDculo ya cuenta con el gps: "+obj.id_gps+"; no se puede ASOCIAR mas de un GPS a un vehiculo");                        
                }//FIN ELSE TIENE GPS ASIGNADAO
              });
            
            
            
           
            
        });                                  
        /*FUNCION QUE SE ENCARGA DE REGISTRAR EL GPS CUANDO NO SE HA ASIGNADO*/
        $('#guardar_gps').on("click", function (){ 
            var si;
            if ($("#aplicar_gps").prop('checked') === true)
            {
                si = 1;
            }else{
                si = 0;                
            } 
                /*VERIFICA EL COMBO HARWARE SI TIENE UN VALOR*/
             if ($("#hrdwr_edit").val()){                     
                $.post("/RDW1/consultaVehiculoTieneGps", {id: $("#id_car").val()}, function (result) {                
                var obj = jQuery.parseJSON( $.trim(result) );  
                    //console.log("---> "+$.trim(result)+"***"+obj.id_gps);
                    if (obj.id_gps === "no_aplica"){  
                        //REGISTRA EL INVENTARIO
                    $.post("/RDW1/guardarInventario", {id_gps:$("#codigo_gps_edit").val(),marca:$("#marca_gps_rec").val(),modelo:$("#modelo_gps_rec").val(),num_cel:$("#numero_celular_edit").val(),oper_cel:$("#oper_gps_rec").val(),id_vh:$("#id_car").val(),id_har:$("#hrdwr_edit").val(),hayRelacion:si}, function (result) {                                              
                        var obj = jQuery.parseJSON( $.trim(result) ); 
                        //console.log($.trim(result));                        
                        if(obj.asignado === 1){
                             $('#id_gps_a').val(obj.id);
                            $('#id_rel_hgv').val(obj.idR);                             
                            $('#id_har').val(obj.fk_hard);                            
                            
                            
                            //SE ADICIONA ID'S A LOS COMPONENTES DEL FORM                            
                            $("#cod_gps_edit").val(obj.id);
                            $('#cod_gps_edit').prop('disabled', true);
                            $("#brand_edit").val(obj.marca);
                            $('#brand_edit').prop('disabled', true);
                            $("#model_edit").val(obj.modelo);
                            $('#model_edit').prop('disabled', true);
                            //                           
                            $("#codigo_gps_edit").val("");
                            $("#marca_gps_rec").val('default');                                                        
                            $("#marca_gps_rec").selectpicker("refresh");
                            $("#modelo_gps_rec").val('default');   
                            $("#modelo_gps_rec").selectpicker("refresh");
                            //
                            $("#cel_edit").val(obj.celular);
                            $('#cel_edit').prop('disabled', true);                            
                            $("#oper_edit").val(obj.operador);
                            $('#oper_edit').prop('disabled', true);
                            //
                            $('#id_cel').val(obj.fk_sim);
                            $("#numero_celular_edit").val("");                            
                            //
                            $('#id_op').val(obj.fk_operador);
                            $("#nom_oper_cel_rec").val('default');
                            $("#nom_oper_cel_rec").selectpicker("refresh");
                            
                            //mostrarMasoMenos();                                                        
                            $('#registro_gps').modal('hide');
                            alert("La unidad GPS se ha registrado");
                        }else if(obj.asignado === 0){   //SE ADICIONAN ID'S A LAS CAJAS OCULTAS DEL FORMULARIO
                            //mostrarMasoMenos();                                                        
                            //$('#registro_gps').modal('hide');
                            alert("La unidad GPS se ha asignado correctamente");
                        }
                    });            
                    }else{//FIN SI NO TIENE GPS
                        alert("El veh\u00EDculo ya cuenta con el gps: "+obj.id_gps+"; no se puede REGISTRAR mas de un GPS a un veh\u00EDculo");                        
                    }//FIN ELSE SI TIENE UN GPS ASOCIADO
            });/*FIN POST*/
            }else{
                alert('Seleccione un dispositivo o tecnologia regisbus');
                return false;
            }            
            
            });
        /*FUNCION QUE SE ENCARGA DE ELIMINAR EL GPS CUANDO SE HA ASIGNADO*/
        $('#delete_gps').on("click", function (){   
                        
            if ($("#cod_gps_del").val()){
                var id_gp= $("#cod_gps_del").val();            
                var placa = $("#placa_edit").val();
                    $.post("/RDW1/eliminarInventario", {cod:$("#cod_gps_del").val()},function (result) { 
                        var obj = jQuery.parseJSON( $.trim(result) );                         
                        console.log($.trim(result));
                        if((obj.id1 === 1) && (obj.id2===1))                        {                            
                            $('#cod_gps_edit').val("");
                            $('#brand_edit').val("");
                            $('#model_edit').val("");
                            $('#num_cel_gps_edit').val("");
                            
                            $('#oper_cel_gps_edit').val("");
                            $('#cel_edit').val("");                           
                            $('#oper_edit').val("");                           
                            
                            $('#cod_gps_del').val("");  
                            //campos del formulario
                            $('#id_gps_a').val("");
                            $('#id_har').val("");
                            $('#id_rel_hgv').val("");
                            $('#id_cel').val("");
                            $('#id_op').val("");   
                            cargarGpsNoAsociadoVh();
                            cargarSimNoAsociadaGps1();
                            /***************************************/                          
                            $("#asociar_gps").show();
                            $("#registrar_gps").show(); 
                            $("#asociar_sim").show(); 
                            $("#registrar_sim").show(); 
                            alert("La unidad GPS "+id_gp+" se ha desvinculado del veh\u00EDculo "+placa+" Correctamente");
                        }
                        else
                        {                         
                            $("#asociar_gps").hide();
                            $("#registrar_gps").hide(); 
                            alert("La unidad GPS NO se encuentra relacionada con el veh\u00EDculo actual");
                        }
                    });
            }
            else{
                alert("Debe ingresar un codigo GPS valido");
            }

        });        
        
    
    /***************************FUNCIONES SIM CARD****************/  
    /*ELIMINA LA RELACION ENTRE GPS Y SIM*/
        $('#delete_sim_gps').on("click", function (){                
            var celular =$("#num_cel_sim_del ").val(); 
            $.post("/RDW1/eliminarRelacionSimGps", {cel:$("#num_cel_sim_del").val()},
                    function (result) { 
                        var obj = jQuery.parseJSON( $.trim(result) );                                                 
                        if(obj.id === 1){                            
                            $("#cel_edit").val('');                            
                            $("#oper_edit").val('');
                            $("#num_cel_sim_del").val('');
                            cargarGpsNoAsociadoSim();                            
                            cargarSimNoAsociadaGps();
                            //$("#asociar_gps").show();
                            //$("#registrar_gps").show();    
                            $("#asociar_sim").show(); 
                            $("#registrar_sim").show(); 
                            alert("El numero celular "+celular+" NO pertenece a un GPS");
                        }
                        else
                        {
                            $("#asociar_sim").hide(); 
                            $("#registrar_sim").hide(); 
                            alert("El numero celular NO se encuentra relacionado ");
                        }
                    });
        });
        /*** **GUARDA UNA TARJETA SIM*******/        
        $('#aplicar_sim').change(function (){
            if ($("#aplicar_sim").prop('checked') === true)
            {  
               $("#id_gps_sim_rec").selectpicker('show');                
               $("#etq_aplicar_gps").show();
               console.log("si");
            }else{
               $("#id_gps_sim_rec").selectpicker('hide'); 
               $("#etq_aplicar_gps").hide();
                console.log("no");
            } 
        });        
        
        /*SELECCIONA EL TIPO DE DOCUMENTO*/
         $('#tipo_tarjeta').on("change", function (){
            if ($("#tipo_tarjeta").prop('checked') === true)
            {  
               console.log("si REVISIN");
            }else{               
                console.log("no TARJETA DE OPERACION");
            } 
        }); 
         /*SELECCIONA EL TIPO DE DOCUMENTO*/
         /*$('#tipo_trjta').on("change", function (){
            if ($("#tipo_trjta").prop('checked') === true)
            {  
               console.log("si REVISIN");
               
               $('#etq_exp').hide();
               $('#ce_e_').selectpicker('hide');     
               $('#etq_m').hide();
               $('#m_').hide();
               $('#etq_cda').show();
               $('#ce_d_').selectpicker('show');
               
            }else{               
                console.log("no TARJETA DE OPERACION");
                $('#etq_exp').show();
                 $('#ce_e_').selectpicker('show');     
                 $('#etq_m').show();
                 $('#m_').show();
                 
                 $('#etq_cda').hide();
                 $('#ce_d_').selectpicker('hide');     
            } 
        });*/ 
        
        
    
        
        $('#guardar_sim').on("click", function (){            
            var id;
            var si;
            if ($("#aplicar_sim").prop('checked') === true){                
               id = $("#id_gps_sim_rec").val();             
               si=1;
               console.log("si");
            }else{
                id = 0;
                si = 0;
                console.log("no");
            }        
            $.post("/RDW1/guardarTarjetaSim", {id_gps:id, num_cel:$("#num_cel_sim_rec").val(), id_oper:$("#oper_rec").val(), aplicar:si}, function (result) { 
                        var obj = jQuery.parseJSON( $.trim(result) ); 
                        console.log($.trim(result));
                        if(obj.id === 0){
                            $("#num_cel_sim_rec").val("");
                            $("#id_gps_sim_rec").val('default');
                            $("#id_gps_sim_rec").selectpicker("refresh");
                            $("#oper_rec").val('default');
                            $("#oper_rec").selectpicker("refresh");
                            cargarGpsNoAsociadoSim1();
                            cargarSimNoAsociadaGps1();
                            cargarSimNoAsociadaGps(); 
                            //mostrarMasoMenos(); 
                            $('#registrar_sim').modal('hide');
                            alert("La tarjeta SIM se ha registrado correctamente");
                        }else if(obj.id === 1){
                            /*SE ORGANIZA EL FORMULARIO*/
                            $("#num_cel_sim_rec").val("");                                
                            $("#id_gps_sim_rec").val('default');                                                        
                            $("#id_gps_sim_rec").selectpicker("refresh");                            
                            $("#oper_rec").val('default');                                                        
                            $("#oper_rec").selectpicker("refresh");
                            cargarGpsNoAsociadoSim1();                             
                            cargarSimNoAsociadaGps1();
                            cargarSimNoAsociadaGps(); 
                            //mostrarMasoMenos(); 
                            /************************************/                            
                            $('#id_gps_edit').val(obj.id_gps);
                            //cargarModeloGps(obj.fk_modelo);
                            //cargarMarcaGps(obj.fk_marca);
                            //cargarNumCelularSim(obj.fk_sim);
                            //cargarOperadorCelular(obj.fk_operador);
                            $('#registrar_sim').modal('hide');
                            alert("La tarjeta SIM se ha registrado y asociado correctamente");
                        }
                        else if(obj.id === -1){
                            $('#registrar_sim').modal('hide');
                            alert("La tarjeta SIM NO se logra registrar");
                        }
                    });
        });
        /*ASOCIAR UNA SIM A UN GPS REGISTRADO*/
        $('#asociar_sim_gps_btn').on("click", function (){
            var id_cel = $("#numero_celular").val();
            var id_g=$("#id_gps_asociar_sim_rec").val();
            var gp =$("#id_gps_asociar_sim_rec option[value="+id_g+"]").html(); 
            var celular =$("#numero_celular option[value="+id_cel+"]").html(); 
            
            $.post("/RDW1/asociarTarjetaSim", {id_gps:$("#id_gps_asociar_sim_rec").val(), id_sim:$("#numero_celular").val()}, function (result) { 
                        var obj = jQuery.parseJSON( $.trim(result) ); 
                        console.log($.trim(result));
                        if(obj.id === 0){                            
                            alert("El numero celular "+celular+" NO se logro asociar");
                        }else if(obj.id > 0){
                            $("#id_gps_asociar_sim_rec").val('default');                                                        
                            $("#id_gps_asociar_sim_rec").selectpicker("refresh");
                            $("#numero_celular").val('default');
                            $("#numero_celular").selectpicker("refresh");
                            $("#cel_edit").val(obj.celular);                            
                            $("#oper_edit").val(obj.operador); 
                            cargarGpsNoAsociadoSim();                            
                            cargarSimNoAsociadaGps();                                
                            $("#asociar_sim").hide(); 
                            $("#registrar_sim").hide(); 
                            alert("El numero celular "+celular+" se ha asociado a la unidad GPS # "+gp+" Correctamente");
                        }
                    });
        });


 if ( $('#aplicar').prop('checked') === false){
        $('#info_gps').toggle("hide");
    }
 $('#aplicar').change(function (){ 
            if ($(this).prop('checked') === false)
            {
                $('#info_gps').toggle("hide");          
            } else
            {
                $('#info_gps').toggle("slow");                                
            }
        });
        
   cargarSimNoAsociadaGps();
   cargarSimNoAsociadaGps1();
   cargarGpsNoAsociadoSim();     
   cargarVehiculosSinAsociar();
   cargarGpsNoAsociadoVh();
   cargarSimNoAsociadaGps();
});/*FIN DOCUMENT READY*/
 
 
 
 
 
 
    
 /***************************************************************/
 function aplicaTiempos(dato){
       console.log("entra aplicar tiempo");
        if (typeof dato === 'undefined' || dato === null || dato === ''){}
       else{
           if(dato === 1){
            $('#aplica1').bootstrapToggle('on');
        }else{
            $('#aplica1').bootstrapToggle('off');
        }/*FIN ELSE VERIFICA SI APARECE O NO LA CAJA*/
       }/*FIN ELSE VERIFICA VARIABLE*/
       
   }
 /*****************FUNCIONES PARA CARGAR COMBOS ASOCIAR GPS**********************************************/
function cargarGpsNoAsociadoVh(){
    $.post("/RDW1/consultaGpsSinAsociar", {id:0},function (result) {
                    //console.log(result);
                    $("#cod_gps_asociar_rec").html(result);
                    $("#cod_gps_asociar_rec").selectpicker("refresh");
                });
            } 
function cargarSimNoAsociadaGps1(){
     $.post("/RDW1/consultaTarjetaSimNoAsociada", {id:0},function (result) {                
                //console.log("numero_celular_gps>>>"+result);
                $("#numero_celular_gps").html(result);
                $("#numero_celular_gps").selectpicker("refresh");                
            });  
}
function cargarVehiculosSinAsociar(){
     $.post("/RDW1/consultaVehiculosSinRelacionar", {id:1}, function (result) {
                    //console.log("Consulta vh");
                    $("#vh2").html(result);
                    $("#vh2").selectpicker("refresh");
                });
}  
 /*****************FUNCIONES PARA CARGAR COMBOS ASOCIAR SIM**********************************************/
function cargarGpsNoAsociadoSim(){
    $.post("/RDW1/consultaGpsNoSim", {id:0},
                function (result) {
                    //console.log(result);
                    $("#id_gps_asociar_sim_rec").html(result);
                    $("#id_gps_asociar_sim_rec").selectpicker("refresh");
    });
}
function cargarSimNoAsociadaGps(){
     $.post("/RDW1/consultaTarjetaSimNoAsociada", {id: 0},
            function (result) {                
                //console.log("numero_celular>>>"+result);
                $("#numero_celular").html(result);
                $("#numero_celular").selectpicker("refresh");
            });  
}
/***********************FUNCIONES PARA CARGAR COMBO GPS DE REGISTRO DE SIM*******************************/
function cargarGpsNoAsociadoSim1(){
    $.post("/RDW1/consultaGpsNoSim", {id:0},
                function (result) {
                    //console.log(result);
                    $("#id_gps_sim_rec").html(result);
                    $("#id_gps_sim_rec").selectpicker("refresh");
    });
}
/*FUNCION QUE PERMITE MOSTRAR LOS BOTONES DE ADICIONAR O ELIMINAR PARA LA SIM*/
function mostrarMasoMenosSim(){               
        if ($('#id_har').val() === '3'){/*CUANDO TIENE VALOR EN LA CAJA*/
           $('#update_sim').hide();
           $('#menos_sim').hide();
           $('#mas_sim').hide();
           
        }
        else{
                if ($('#cel_edit').val()){/*CUANDO TIENE VALOR EL COMBO DE SIM CARD*/
                    $('#mas_sim').hide();
                    $('#update_sim').hide();
                    $('#menos_sim').show();
                }   
                else{
                    
                        /*CUANDO TIENE VALOR EN LA CAJA DE NUMERO GPS QUE TRAE DE CONSULTAR*/
                        $('#mas_sim').show();
                        $('#update_sim').hide();
                        $('#menos_sim').hide();                                            
                }          
        }
}
/*FUNCIONES PARA PASAR PARAMETROS*/
function pasarIdPicoDescanso(dato1, dato2){
    //console.log("-->"+dato1+", "+dato2);
        if (typeof dato1 === 'undefined' || dato1 === null || dato1 === ''){}
        else{
             //console.log("No ");            
            var cdn1 = dato1.split(',');
            var cdn2 = dato2.split(',');        
         }
          $.each(cdn1, function( index, value) {  
            $("#dayPico_edit option[value="+value+"]").attr("selected",true);            
        });    
        
        $.each(cdn2, function( index, value ) {  
            $("#dayholiday_edit option[value="+value+"]").attr("selected",true);           
        });  
}
function pasarIdGrupo(dato){
    //console.log("id grupo -->"+dato);
    if (typeof dato === 'undefined' || dato === null || dato === ''){}
         else{             
             var cdn1 = dato.split(',');
         }
          $.each(cdn1, function( index, value) {  
            $("#group_edit option[value="+value+"]").attr("selected",true);            
        });    
}
function pasarIdPropietario(dato){
    //console.log("id prop-->"+dato);        
    if (typeof dato === 'undefined' || dato === null || dato === ''){}
    else{$("#prop_edit option[value="+dato+"]").attr("selected",true);}
}
function pasarIdHardware(dato1){
    //console.log("id hard-->"+dato1);    
    if (typeof dato1 === 'undefined' || dato1 === null || dato1 === ''){}
    else{$("#hrdwr_edit option[value="+dato1+"]").attr("selected",true);}
}
function pasarMarcaModelo(dato2, dato3){
    //console.log("id marca modelo:---->"+dato2+", "+dato3);            
    if (typeof dato2 === 'undefined' || dato2 === null || dato2 === ''){}
    else{$("#brand_edit").val( dato2);}
    
    if (typeof dato3 === 'undefined' || dato3 === null || dato3 === ''){}
    else{$("#model_edit").val( dato3);}
}
function pasarMarcaModelo1(dato2, dato3){
    //console.log("id marca modelo:---->"+dato2+", "+dato3);            
    if (typeof dato2 === 'undefined' || dato2 === null || dato2 === ''){
        $("#marca_gps_rec").val('default');
        $("#marca_gps_rec").selectpicker("refresh");                     
    }
    else{//$("#marca_gps_rec").val( dato2);
        $("#marca_gps_rec option[value="+dato2+"]").attr("selected",true);
         $("#marca_gps_rec").selectpicker("refresh");                        
    }
    
    if (typeof dato3 === 'undefined' || dato3 === null || dato3 === ''){
        $("#modelo_gps_rec").val('default');
        $("#modelo_gps_rec").selectpicker("refresh");  
    }
    else{//$("#modelo_gps_rec").val( dato3);
        $("#modelo_gps_rec option[value="+dato3+"]").attr("selected",true);
        $("#modelo_gps_rec").selectpicker("refresh");
    }
}
function pasarIdCel(dato){
    //console.log("id cel-->"+dato);
    if (typeof dato === 'undefined' || dato === null || dato === ''){}
    else{$("#cel_edit").val( dato );}
}
function pasarIdOp(dato){
    //console.log("id op-->"+dato);
    if (typeof dato === 'undefined' || dato === null || dato === ''){}
    else{$("#oper_edit").val( $("#oper_rec option[value="+dato+"]").html() );}    
}
function pasarIdOp1(dato){    
    //console.log("id ope-->"+dato);
    if (typeof dato === 'undefined' || dato === null || dato === ''){}    
    else{
        //$("#oper_gps_rec").val(dato);
        $("#oper_gps_rec option[value="+dato+"]").attr("selected",true);
        $("#oper_gps_rec").selectpicker("refresh");
    }    
}
/*
function tieneGPs(idGps){
     if (typeof idGps === 'undefined' || idGps === null || idGps === ''){     
     $('#cod_gps_edit').prop('disabled', true);
     $('#brand_edit').prop('disabled', true);
     $('#model_edit').prop('disabled', true);     
     $("#aplicar_gps").attr('checked',true);             
     console.log("tiene un gps --->"+idGps);
    }else{        
        $('#cod_gps_edit').prop('disabled', true);
        $('#brand_edit').prop('disabled', true);
        $('#model_edit').prop('disabled', true);        
        $("#aplicar_gps").attr('checked',false);        
        console.log("NO tiene un gps --->"+idGps);
    }
}

function GpsTieneSim(celular){
     if (typeof celular === 'undefined' || celular === null || celular === ''){
     
     $('#cel_edit').prop('disabled', true);
     $('#oper_edit').prop('disabled', true);
     $("#aplicar_sim").attr('checked',true);
     $("#id_gps_sim_rec").selectpicker('show');                
     $("#etq_aplicar_gps").show();     
     console.log("tiene una sim asociada --->"+celular);
    }else{        
        $('#cel_edit').prop('disabled', true);
        $('#oper_edit').prop('disabled', true);
        $("#aplicar_sim").attr('checked',false);        
        $("#id_gps_sim_rec").selectpicker('hide');                
        $("#etq_aplicar_gps").hide();
        console.log("NO tiene una sim asociada --->"+celular);
      }
}
*/
function tipoHardware(dato){ 
    //console.log(">> "+dato);
        if (dato === '3'){
            //console.log("quitar cajas");
            
            $('#etq_cod_gps_edit').hide();            
            $('#cod_gps_edit').hide();            
            
            $('#etq_brand_edit').hide();            
            $('#brand_edit').selectpicker('hide'); 

            $('#etq_model_edit').hide();                        
            $('#model_edit').selectpicker('hide'); 
            
            $('#etq_cel_edit').hide();                      
            $('#cel_edit').selectpicker('hide'); 
            
            $('#etq_oper_edit').hide();                        
            $('#oper_edit').selectpicker('hide');
            //mostrarMasoMenos();
        }
        else{
            //console.log("adicionar cajas");
                      
           $('#etq_cod_gps_edit').show();
            $('#cod_gps_edit').show();            
            
            $('#etq_brand_edit').show();            
            $('#brand_edit').selectpicker('show'); 

            $('#etq_model_edit').show();                        
            $('#model_edit').selectpicker('show'); 
            
            $('#etq_cel_edit').show();          
            $('#cel_edit').selectpicker('show');             
            
            $('#etq_oper_edit').show();                        
            $('#oper_edit').selectpicker('show');  
            
        }   
}

function tipoTarjetaEdicion(dato){
       console.log("entra a seleccionar");
        if (typeof dato === 'undefined' || dato === null || dato === ''){}
       else{
           if(dato === 1){
            $('#tipo_tarjeta').bootstrapToggle('on');
        }else{
            $('#tipo_tarjeta').bootstrapToggle('off');
        }/*FIN ELSE VERIFICA SI APARECE O NO LA CAJA*/
       }/*FIN ELSE VERIFICA VARIABLE*/
       
   }
   
   
   function tipoTarjetaRegistro(dato){
       console.log("entra a seleccionar");
        if (typeof dato === 'undefined' || dato === null || dato === ''){}
       else{
           if(dato === 1){
            $('#tipo_trjta').bootstrapToggle('on');
        }else{
            $('#tipo_trjta').bootstrapToggle('off');
        }/*FIN ELSE VERIFICA SI APARECE O NO LA CAJA*/
       }/*FIN ELSE VERIFICA VARIABLE*/
       
   }
   
   
   
function cargarCDA(){
    $.post("/RDW1/consultaTodosCentroDiagnostico", {id:1},
                function (result) {
                    //console.log("--->>"+$.trim(result));
                    $("#ce_d_").html($.trim(result));
                    $("#ce_d_").selectpicker("refresh");
    });
}
function cargarCE(){
    $.post("/RDW1/consultaTodosCentroExpedicion", {id:1},
                function (result) {
                    //console.log("--->>***"+$.trim(result));
                    $("#ce_e_to").html($.trim(result));
                    $("#ce_e_to").selectpicker("refresh");
    });
}


function cargarCDA_Edit(dato){
    
    $.post("/RDW1/consultaTodosCentroDiagnostico", {id:1},
                function (result) {
                    console.log("--->>"+$.trim(result));
                    
                    if (typeof dato === 'undefined' || dato === null || dato === ''){
                        $("#fk_cda_rtm").append($.trim(result));
                    }
                    else{
                        $("#fk_cda_rtm").html($.trim(result));
                        $("#fk_cda_rtm option[value="+dato+"]").attr("selected",true);                    
                    }
                    $("#fk_cda_rtm").selectpicker("refresh");
    });
}
function cargarCE_Edit(dato){
    $.post("/RDW1/consultaTodosCentroExpedicion", {id:1},
                function (result) {
                    //console.log("--->>***"+$.trim(result));
                    $("#fk_ce_to").html($.trim(result));
                    if (typeof dato === 'undefined' || dato === null || dato === ''){}
                    else{
                        $("#fk_ce_to option[value="+dato+"]").attr("selected",true);                    
                    }
                    $("#fk_ce_to").selectpicker("refresh");
    });
}