<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Pestaña / devel_hector -->
        <link rel="icon" type="image/png" href="img/favicon.png"/>
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
        <!--
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-responsive.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap3.css" rel="stylesheet"> -->
        <link href="/RDW1/resources/extern/DataTables/media/css/jquery.dataTables.css" rel="stylesheet">			
        <!-- <link href="/RDW1/resources/extern/DataTables/media/css/responsive.dataTables.css" rel="stylesheet"> -->
        <link href="/RDW1/resources/extern/DataTables/media-responsive/css/responsive.dataTables.css" rel="stylesheet">            
        <link href="/RDW1/resources/extern/multiple-select/multiple-select.css" rel="stylesheet">
        <link href="/RDW1/resources/bootstrap-select/css/bootstrap-select.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-toggle.css" rel="stylesheet">                            
        <link href="/RDW1/resources/extern/bootstrap-files/css/datepicker.css" rel="stylesheet"> 
        
        
        
        <!--<link href="/RDW1/resources/extern/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet">-->
        
        
        
        
        <link href="/RDW1/resources/extern/jquery-ui/jquery-ui.css" rel="stylesheet">
        <link href="/RDW1/resources/css/style.css" rel="stylesheet">
        <link href="/RDW1/resources/css/style1.css" rel="stylesheet">
        <link href="/RDW1/resources/css/multipleSelect.css" rel="stylesheet">        

        <!--<link href="/RDW1/resources/bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet">-->   
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/bootstrap-datetimepicker4/css/bootstrap-datetimepicker.css" rel="stylesheet">
        
        <link href="/RDW1/resources/offline/themes/offline-theme-chrome.css" rel="stylesheet">                        
        <link href="/RDW1/resources/offline/themes/offline-language-spanish.css" rel="stylesheet">
        <!--
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">            
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-responsive.css" rel="stylesheet">
        -->

        <!-- OSM -->
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.1/dist/leaflet.css" />	                    
    </head>        
    <body onload="javascript:onload();" class="track">        
        <nav class="navbar navbar-default">
            <div class="container-fluid">  

                <!-- Menu responsive -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>                        
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

                    <jsp:include page="/include/leftMenu.jsp" />

                    <!-- Logo -->
                    <ul class="nav navbar-nav">
                        <li class="company-title">Registel</li>
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
                    <ul class="nav navbar-nav navbar-right">                        
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" 
                               aria-haspopup="true" aria-expanded="false">Usuario <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#"><strong>${login.login}</strong></a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="/RDW1/app/usuarios/perfilUsuario.jsp">Perfil</a></li>
                                <li role="separator" class="divider"></li>
                                <li>
                                    <a href="#" onclick="closeApp();">Salir</a>
                                    <form id="form-close" class="form-in-table" action="<c:url value='/cerrarSesion'/>" method="post"></form>
                                </li>
                            </ul>
                        </li>
                    </ul>                        
                </div> <!-- /.navbar-collapse -->
            </div> <!-- /.container-fluid -->
        </nav>