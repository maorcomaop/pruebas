<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Pestaña / devel_hector -->
        <link href='/RDW1/resources/img/favicon.png' rel='shortcut icon' type='image/png'>
        <title>Regisdata Web</title>
        <!-- Metatags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
        <meta http-equiv='X-UA-Compatible' content='IE=Edge'>
        <meta name="robots" content="index, follow"/>
        <meta name="description" content="">
        <meta name="keywords" content="">
        <meta name="author" content="">
        <!-- Style -->

        <link href="/RDW1/resources/extern/DataTables/media/css/jquery.dataTables.css" rel="stylesheet">			        
        <link href="/RDW1/resources/extern/DataTables/media-responsive/css/responsive.dataTables.css" rel="stylesheet">            
        <link href="/RDW1/resources/extern/multiple-select/multiple-select.css" rel="stylesheet">
        <link href="/RDW1/resources/bootstrap-select/css/bootstrap-select.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-toggle.css" rel="stylesheet">        
        <link href="/RDW1/resources/extern/bootstrap-datetimepicker4/css/bootstrap-datetimepicker.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/jquery-ui/jquery-ui.css" rel="stylesheet">
        <link href="/RDW1/resources/css/style.css" rel="stylesheet">
        <link href="/RDW1/resources/css/style1.css" rel="stylesheet">        
        <link href="/RDW1/resources/css/multipleSelect.css" rel="stylesheet">        

        <link href="/RDW1/resources/bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet">        
        <link href="/RDW1/resources/css/styleMenu.css" rel="stylesheet">               

        <!--
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">            
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-responsive.css" rel="stylesheet">
        -->

        <!-- OSM 
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" /> -->

        <link href="/RDW1/resources/extern/leaflet-label/leaflet.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/leaflet-label/leaflet.label.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/leaflet-easybutton/easy-button.css" rel="stylesheet">

        <link href="/RDW1/resources/offline/themes/offline-theme-chrome.css" rel="stylesheet">                        
        <link href="/RDW1/resources/offline/themes/offline-language-spanish.css" rel="stylesheet">

    </head>        
    <body onload="javascript:onload();">        
        <header>
            <nav class="navbar navbar-default">
                <div class="container-fluid">  


                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <!-- Logo -->
                        <ul class="nav navbar-nav">                            
                            <li><img src="/RDW1/resources/img/logo.png" class="inicial"/></li>
                        </ul> 

                        <!-- Busqueda
                        <form class="navbar-form navbar-left">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Search">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                        -->

                        <!-- Info de sesion -->

                    </div> <!-- /.navbar-collapse -->
                </div> <!-- /.container-fluid -->
            </nav>


            <div id="modalAcerca" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header bg-primary">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title">Acerca de</h4>
                        </div>
                        <div class="modal-body">




                            <div class="col-lg-10 centered">    
                                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">                        
                                    <ul class="nav navbar-nav">                            
                                        <li><img src="/RDW1/resources/img/logo.png" class="inicial"/></li>
                                    </ul>                                       
                                </div>
                                <div class="panel panel-default panel-primary">
                                    <div class="panel-heading">
                                        Version 1.0 Beta
                                    </div>
                                    <div class="panel-body">
                                        Software de gestion operativa en transporte p&uacute;blico.
                                    </div>
                                    <div class="panel-footer ">Departamento de Tecnolog&iacute;a e Informatica. Todos los derechos reservados</div>
                                </div>    
                            </div>  



                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <!--<button type="submit" class="btn btn-primary">Save changes</button>-->
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="/include/barMenu.jsp" />
        </header>