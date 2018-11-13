<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>

<html>
    <head>
        <meta charset="utf-8">
        <title>Regisdata Web</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">			        
        <link href='/RDW1/resources/img/favicon.png' rel='shortcut icon' type='image/png'>
        <link href="/RDW1/resources/bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet">        
               
        <link href="/RDW1/resources/extern/DataTables/media/css/jquery.dataTables.css" rel="stylesheet" />  
        <link href="/RDW1/resources/extern/DataTables/media/css/responsive.dataTables.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/DataTables/media-responsive/css/responsive.dataTables.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/multiple-select/multiple-select.css" rel="stylesheet">             
        <link href="/RDW1/resources/extern/jquery-ui/jquery-ui.css" rel="stylesheet">        
        <link href="/RDW1/resources/css/style.css" rel="stylesheet">
        <link href="/RDW1/resources/css/style1.css" rel="stylesheet">                        
        <link href="/RDW1/resources/css/styleMenu.css" rel="stylesheet">
                
        <!-- OSM -->
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" />	
        
        <link href="/RDW1/resources/offline/themes/offline-theme-chrome.css" rel="stylesheet">                                
        <link href="/RDW1/resources/offline/themes/offline-language-spanish.css" rel="stylesheet">
	
	<style type="text/css">
            body {
                /*
              padding-top: 60px;
              padding-bottom: 40px; */
            }
            .sidebar-nav {
              padding: 9px 0;
            }

            @media (max-width: 980px) {
              /* Enable use of floated navbar text */
              .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
              }
            }
        </style>
        
        <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
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