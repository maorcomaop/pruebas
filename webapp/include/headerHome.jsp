<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Pestaa / devel_hector -->
        <!--<link rel="icon" type="image/png" href="img/favicon.png"/>-->
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
        <!-- CDN -->
        <!-- Fuentes -->
        <!-- CSS -->

        <link href="/RDW1/resources/extern/DataTables/media/css/jquery.dataTables.css" rel="stylesheet" />
        <link href="/RDW1/resources/extern/DataTables/media-responsive/css/responsive.dataTables.css" rel="stylesheet">                                    
        <link href="/RDW1/resources/extern/multiple-select/multiple-select.css" rel="stylesheet">     
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-select.min.css" rel="stylesheet">                                
        <link href="/RDW1/resources/extern/jquery-ui/jquery-ui.css" rel="stylesheet">                        
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-toggle.css" rel="stylesheet">                            
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-datepicker-1.6.4.css" rel="stylesheet"> 
        <link href="/RDW1/resources/extern/estilo_error.css" rel="stylesheet">        
        <link href="/RDW1/resources/css/style.css" rel="stylesheet">
        <link href="/RDW1/resources/css/style1.css" rel="stylesheet">        
        <link href="/RDW1/resources/css/styleEditTable.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/validator2.css" rel="stylesheet">
        <link href="/RDW1/resources/bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet">        
                
        <link href="/RDW1/resources/extern/bootstrap-datetimepicker4/css/bootstrap-datetimepicker.css" rel="stylesheet">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
                
        <script src="/RDW1/resources/extern/bootstrap-files/js/jquery-2.2.4.js"></script>                
        <script src="/RDW1/resources/bootstrap-3.3.7/js/bootstrap.js"></script>                
        <script src="/RDW1/resources/extern/DataTables/media/js/jquery.dataTables.js"></script>
        <script src="/RDW1/resources/extern/DataTables/media-responsive/js/dataTables.responsive.js"></script>        
        <script src="/RDW1/resources/extern/multiple-select/multiple-select.js"></script>            
        <script src="/RDW1/resources/extern/bootstrap-files/js/bootstrap2-toggle.js"></script> 
        <script src="/RDW1/resources/extern/bootstrap-files/js/bootstrap-select.js"></script>                                                        
        <script src="/RDW1/resources/extern/bootstrap-datetimepicker4/moment.js"></script>
        <script src="/RDW1/resources/extern/bootstrap-datetimepicker4/js/bootstrap-datetimepicker.js"></script>
        
        <link href="/RDW1/resources/offline/themes/offline-theme-chrome.css" rel="stylesheet">                        
        <link href="/RDW1/resources/offline/themes/offline-language-spanish.css" rel="stylesheet">
        
              
        
        
        




    </head>
    <body>

        <header>
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

                        <!-- Menu RDW -->                    

                        <!--<jsp:include page="/include/leftMenu.jsp" />-->
                                              

                        <!-- Logo -->
                        <ul class="nav navbar-nav">
                            <li><h3>REGISDATAWEB</h3></li>
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
                                   aria-haspopup="true" aria-expanded="false">Usted esta conectado como: ${login.login} <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="/RDW1/app/usuarios/perfilUsuario.jsp">Perfil: ${login.perfilusuario}</a></li>                                
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
        </header>

        <!-- <div class="container"> -->
