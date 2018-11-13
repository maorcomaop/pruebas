
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<!--
<div class="col-md-7 top-space bottom-space">
    <a href="#"><strong>> Perfil usuario</strong></a>
</div>
-->

    <div class="col-lg-7 centered">
        <section class="boxed padding">
            <ul class="nav nav-tabs">
                <li role="presentation"><a href="/RDW1/app/usuarios/perfilUsuario.jsp">Perfil de usuario</a></li>
                <li role="presentation" class="active"><a href="#">Cuenta</a></li>
            </ul>    

            <div class="tab-content">
                <div role="tabpanel" class="tab-pane padding active">                    

                <form id="form-cambio-password" action="<c:url value='/cambiarContrasena' />" method="post" class="form-horizontal">            
                    <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msgChange}</div>
                    <div class="control-group col-md-7">
                        <label class="control-label">* Contrase&ntilde;a actual</label>
                        <div class="controls">                    
                            <input type="password" class="form-control" id="oldpass" name="oldpass" placeholder="De al menos 8 caracteres" maxlength="20" required>
                            <span id="msg_oldpass"></span>
                        </div>                
                    </div>
                    <div class="control-group col-md-7">
                        <label class="control-label">* Contrase&ntilde;a nueva</label>
                        <div class="controls">
                            <input type="password" class="form-control" id="newpass" name="newpass" placeholder="De al menos 8 caracteres" maxlength="20" required>
                            <span id="msg_newpass"></span>
                        </div>                
                    </div>
                    <div class="control-group col-md-7">
                        <label class="control-label">* Confirmar nueva contrase&ntilde;a</label>
                        <div class="controls">
                            <input type="password" class="form-control" id="cnewpass" name="cnewpass" placeholder="De al menos 8 caracteres" maxlength="20" required>
                            <span id="msg_cnewpass"></span>
                        </div>                                
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                            <button style="display: none;" type="submit"></button>
                            <button type="button" class="btn" onclick="sendChangePassword();">Cambiar contrase&ntilde;a</button>
                            <input type="hidden" id="numdoc" name="numdoc" value="${login.numdoc}">
                        </div>
                    </div>            

                    <hr>
                    <div class="control-group col-md-7">
                        <label class="control-label">Eliminar cuenta</label>                
                    </div>        
                    <div class="form-group">                        
                        <div class="col-sm-offset-1 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                            <button style="width: 150px;" type="button" class="btn" data-toggle="modal" data-target="#myModal">Eliminar</button>
                        </div>
                    </div>                                        
                </form>
                </div>
            </div>
        </section>
    </div>
        
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Eliminaci&oacute;n de cuenta</h4>
                </div>
                <div class="modal-body">
                    ¿Est&aacute; seguro que desea eliminar su cuenta?
                </div>
                <div class="modal-footer">
                    <form action="<c:url value='/eliminarUsuario_' />" method="post" class="form-horizontal" style="padding-top: 20px;">            
                        <input type="hidden" id="numdoc" name="numdoc" value="${login.numdoc}">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Eliminar</button>
                    </form>
                </div>
            </div>
        </div>
    </div>        

<jsp:include page="/include/footerHome_.jsp" />
<jsp:include page="/include/end.jsp" />
