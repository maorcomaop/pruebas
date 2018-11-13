
<!-- </div> --> 
<footer>
    <center>
    <div style="width: 200px; height: 250px;"><img src="/RDW1/resources/img/logoregistel.png"></div>
    </center>
</footer>

<script src="/RDW1/resources/extern/bootstrap-files/js/jquery-2.2.4.js"></script>  
<script src="/RDW1/resources/bootstrap-3.3.7/js/bootstrap.js"></script>
<script src="/RDW1/resources/extern/DataTables/media/js/jquery.dataTables.min.js"></script>
<script src="/RDW1/resources/extern/DataTables/media-responsive/js/dataTables.responsive.js"></script>        
<!-------------------------------------------------------------------------------------------->
<script src="/RDW1/resources/extern/DataTables/media/js/dataTables.rowReorder.min.js"></script>
<!-------------------------------------------------------------------------------------------->

<script src="/RDW1/resources/extern/bootstrap-files/js/bootstrap2-toggle.js"></script> 
<script src="/RDW1/resources/extern/bootstrap-files/js/bootstrap-select.js"></script>                                                        
<script src="/RDW1/resources/extern/bootstrap-datetimepicker4/moment.js"></script>
<script src="/RDW1/resources/extern/bootstrap-datetimepicker4/es.js"></script>
<script src="/RDW1/resources/extern/bootstrap-datetimepicker4/js/bootstrap-datetimepicker.js"></script>



<!--<script src="/RDW1/resources/extern/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.es1.js"></script>-->
<script src="/RDW1/resources/extern/menu.js"></script>
<script src="/RDW1/resources/extern/bootbox.min.js"></script>
        

<!--Validaciones-->
<script src="/RDW1/resources/extern/funciones_validacion_alarma.js"></script>          
<script src="/RDW1/resources/extern/funciones_validacion_auditoria.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_conductor.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_evento.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_consulta_liquidacion.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_reportes_liquidacion.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_nueva_liquidacion.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_movil.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_movil_edit.js"></script>
<!--<script src="/RDW1/resources/extern/funciones_validacion_movil_edit_gps.js"></script>-->
<script src="/RDW1/resources/extern/funciones_validacion_motivo.js"></script>            
<script src="/RDW1/resources/extern/funciones_validacion_modulo.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_submodulo.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_submodulo_item.js"></script>            
<script src="/RDW1/resources/extern/funciones_validacion_perfil.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_categoria.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_grupo_movil.js"></script>
<!--<script src="/RDW1/resources/extern/funciones_validacion_grupo_movil.js"></script>-->
<script src="/RDW1/resources/extern/funciones_validacion_moviles_a_grupo.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_relacion_empresa_vehiculo.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_relacion_conductor_vehiculo.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_liquidar_vehiculo.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_registrar_gps_movil.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_asociar_gps_movil.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_asociar_sim_gps.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_config_des.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_soat.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_revision_tecnico_mecanica.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_centro_diagnostico.js"></script> 

<script src="/RDW1/resources/extern/etiquetas_editables.js"></script> 
<script src="/RDW1/resources/extern/etiquetas_editables_fusa.js"></script>
<script src="/RDW1/resources/offline/offline.min.js"></script>
<script src="/RDW1/resources/extern/autocomplete1.3.5/jquery.easy-autocomplete.js"></script>
<!--<script src="/RDW1/resources/js/prueba_conexion.js"></script>-->
<!--<script src="/RDW1/resources/js/consultaLicencia.js"></script>-->
<script src="/RDW1/resources/js/scriptNuevoVehiculo.js"></script>
<script src="/RDW1/resources/js/scriptNuevoGrupoVehiculo.js"></script>
<script src="/RDW1/resources/js/scriptConfigDesempeno.js"></script>
<script src="/RDW1/resources/js/scriptLiquidacion.js"></script>
<!--<script src="/RDW1/resources/js/ejecutar_tareas.js"></script>-->
<!--<script src="/RDW1/resources/extern/cargadearchivo1.js"></script>-->



<!--<script src="/RDW1/resources/extern/validations.js"></script>-->

<script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet.js"></script>        

<script type="text/javascript" src="/RDW1/resources/js/map.js"></script>        
<script type="text/javascript" src="/RDW1/resources/js/util.js"></script>
<script type="text/javascript" src="/RDW1/resources/js/reporteUtil.js"></script>
<script type="text/javascript" src="/RDW1/resources/js/validate.js"></script>

<script src="/RDW1/resources/js/mantenimiento/ejecutarMantenimiento.js" type="text/javascript" charset="UTF-8"></script>
<script src="/RDW1/resources/extern/notify/notify.min.js"></script>

<!-- Plugins -->
<script>
    $('#myTabs a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    // navbar
    $('.sidebar-nav').click(function () {
        $('.sidebar-nav:visible').click();
    });
    $('.sidebar-toggle').click(function () {
        $('#sidebar').removeClass('hidden-xs');
    });
    $(function () {
        $('#slide-submenu').on('click', function () {
            $(this).closest('.list-group').fadeOut('slide', function () {
                $('.mini-submenu').fadeIn();
            });
        });
        $('.mini-submenu').on('click', function () {
            $(this).next('.list-group').toggle('slide');
            $('.mini-submenu').hide();
        });
    });
    

    $('#modalAcerca').on('show.bs.modal', function (e) {    
    /*var loadurl = e.relatedTarget.dataset.loadUrl;    
    $(this).find('.modal-body').load(loadurl);*/
    });
    $('#contact_us').on('show.bs.modal', function (e) {});

    $('#send').on("click", function (){
            //console.log("--> "+$("#msg").val()+"-->"+$("#email").val());
            $.post("/RDW1/guardarContacto", {correo:$("#email").val(), mensaje:$("#msg").val()},
                    function (result) {                                                                        
                        //console.log("***> "+result+" --->> "+$.trim(result));
                        if($.trim(result) === "1")
                        {
                            console.log("se hizo");                  
                            $("#email").val("");
                            $("#msg").val("");
                            $('#contact_us').modal('hide');
                          
                          
                        }
                        else
                        {
                            console.log ("no se pudo");
                        }
                    });
        });


</script>

