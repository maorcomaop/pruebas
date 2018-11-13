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
        <link href='/RDW1/resources/img/favicon.png' rel='shortcut icon' type='image/png'>
        <link href="/RDW1/resources/extern/DataTables/media/css/jquery.dataTables.css" rel="stylesheet" />        
        <link href="/RDW1/resources/extern/DataTables/media-responsive/css/responsive.dataTables.css" rel="stylesheet">
        <!------------------------------------------------------------------------------------------------------------>
        <link href="/RDW1/resources/extern/DataTables/media/css/rowReorder.dataTables.min.css" rel="stylesheet">                                                    
        <!------------------------------------------------------------------------------------------------------------>
        <link href="/RDW1/resources/extern/jquery-ui/jquery-ui.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-select.min.css" rel="stylesheet">                                
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-toggle.css" rel="stylesheet">                            
        <link href="/RDW1/resources/extern/bootstrap-files/css/bootstrap-datepicker-1.6.4.css" rel="stylesheet"> 
        <link href="/RDW1/resources/bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet">                
        <link href="/RDW1/resources/extern/estilo_error.css" rel="stylesheet">        
        <link href="/RDW1/resources/css/style.css" rel="stylesheet">
        <link href="/RDW1/resources/css/style1.css" rel="stylesheet">                         
        <link href="/RDW1/resources/extern/validator2.css" rel="stylesheet">
        <link href="/RDW1/resources/css/grid.css" rel="stylesheet">
        <!--autocompleta-->
        <link href="/RDW1/resources/extern/autocomplete1.3.5/easy-autocomplete.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/autocomplete1.3.5/easy-autocomplete.themes.css" rel="stylesheet">
        <link href="/RDW1/resources/extern/bootstrap-datetimepicker4/css/bootstrap-datetimepicker.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">  
        <link href="/RDW1/resources/css/styleMenu.css" rel="stylesheet">        
        <link href="/RDW1/resources/offline/themes/offline-theme-chrome.css" rel="stylesheet">                        
        <link href="/RDW1/resources/offline/themes/offline-language-spanish.css" rel="stylesheet">
        
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        
        
        
    </head>
    <body>
        <header>            
            <nav class="navbar navbar-default" role="navigation">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#"></a>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">                        
                        <ul class="nav navbar-nav">                            
                             <li><img src="/RDW1/resources/img/logo.png" class="inicial"/></li>
                        </ul>                        
                    </div>
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
                                        <div>ID:${clave}</div> 
                                        <c:if test="${tipo_clave == 1}">
                                            <div>Software de prueba por 30 d&iacute;as</div> 
                                        </c:if>
                                        <c:if test="${tipo_clave == 2}">
                                            <div>Software de por 3 meses, fecha de renovacion 00/00/0000</div> 
                                        </c:if>
                                        <c:if test="${tipo_clave == 3}">
                                            <div>Software por 12 meses, fecha de renovacion 00/00/0000</div> 
                                        </c:if>
                                        <c:if test="${tipo_clave == 4}">
                                            <div>Software por 5 años, fecha de renovacion 00/00/0000</div> 
                                        </c:if>
                                        
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
    <!--*****************************************************************************************-->
    
         <div class="modal fade" id="contact_us" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title" id="exampleModalLabel">Contactenos</h2>
        <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>-->
      </div>
      <div class="modal-body">
          <form action="#" method="POST" id="" name="">
          <div class="form-group">            
            <input type="text" class="form-control" placeholder="Direcci&oacute;n de correo" id="email" name="correo">
          </div>
          <div class="form-group">            
            <textarea class="form-control" placeholder="Mensaje" id="msg" name="mensaje"></textarea>
          </div>              
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" id="send" class="btn btn-primary">Enviar</button>
      </div>
    </div>
  </div>
</div>
    
    
            
            
            <jsp:include page="/include/barMenu.jsp" />
           

        </header>

        <!-- <div class="container"> -->
