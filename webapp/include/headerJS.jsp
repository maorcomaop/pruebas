
<script src="/RDW1/resources/extern/bootstrap-files/js/jquery-2.2.4.js"></script>                
<script src="/RDW1/resources/bootstrap-3.3.7/js/bootstrap.js"></script>
<script src="/RDW1/resources/extern/DataTables/media/js/jquery.dataTables.js"></script>
<script src="/RDW1/resources/extern/DataTables/media-responsive/js/dataTables.responsive.js"></script>        
<script src="/RDW1/resources/extern/bootstrap-files/js/bootstrap2-toggle.js"></script> 
<script src="/RDW1/resources/extern/bootstrap-files/js/bootstrap-select.js"></script>                                                        
<script src="/RDW1/resources/extern/bootstrap-datetimepicker4/moment.js"></script>
<script src="/RDW1/resources/extern/bootstrap-datetimepicker4/es.js"></script>
<script src="/RDW1/resources/extern/bootstrap-datetimepicker4/js/bootstrap-datetimepicker.js"></script>        
<!--<script src="/RDW1/resources/extern/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.es1.js"></script>-->
<script src="/RDW1/resources/extern/menu.js"></script>

<link href="/RDW1/resources/offline/themes/offline-theme-chrome.css" rel="stylesheet">                        
<link href="/RDW1/resources/offline/themes/offline-language-spanish.css" rel="stylesheet">
        

<!--Validaciones-->
<script src="/RDW1/resources/extern/funciones_validacion_alarma.js"></script>          
<script src="/RDW1/resources/extern/funciones_validacion_auditoria.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_conductor.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_evento.js"></script> 
<script src="/RDW1/resources/extern/funciones_validacion_consulta_liquidacion.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_reportes_liquidacion.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_nueva_liquidacion.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_movil.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_motivo.js"></script>            
<script src="/RDW1/resources/extern/funciones_validacion_modulo.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_submodulo.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_submodulo_item.js"></script>            
<script src="/RDW1/resources/extern/funciones_validacion_perfil.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_categoria.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_grupo_movil.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_grupo_movil.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_moviles_a_grupo.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_relacion_empresa_vehiculo.js"></script>
<script src="/RDW1/resources/extern/funciones_validacion_relacion_conductor_vehiculo.js"></script>          
<!--<script src="/RDW1/resources/extern/cargadearchivo1.js"></script>          -->
        



<!--<script src="/RDW1/resources/extern/validations.js"></script>-->

<script src="https://unpkg.com/leaflet@1.0.1/dist/leaflet.js"></script>        

<script type="text/javascript" src="/RDW1/resources/js/map.js"></script>        
<script type="text/javascript" src="/RDW1/resources/js/util.js"></script>
<script type="text/javascript" src="/RDW1/resources/js/reporteUtil.js"></script>
<script type="text/javascript" src="/RDW1/resources/js/validate.js"></script>

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

</script>
