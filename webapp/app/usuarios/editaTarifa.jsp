
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<!--
            <div class="col-md-9 top-space bottom-space">
                <a href="#"><strong>> Tarifas</strong></a>
            </div>-->

            <!-- Body container -->
            <div class="col-lg-7 centered">
                <h1 class="display-3 bg-info text-center">M&Oacute;DULO TARIFAS</h1>    
                <section class="boxed padding">                    
                    <ul class="nav nav-tabs">
                        <li role="presentation"><a href="/RDW1/app/usuarios/nuevaTarifa.jsp">Nueva tarifa</a></li>
                        <li role="presentation" class="active"><a href="#">Editar tarifa</a></li>
                        <li role="presentation"><a href="/RDW1/app/usuarios/consultaTarifa.jsp">Listado tarifas</a></li>                
                    </ul>                
                
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane padding active">                    

                        <form id="form-actualiza-tarifa" class="form-horizontal" action="<c:url value='/editarTarifa' />" method="post">
                            <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>
                            <!-- <div><p>{lnk}</p></div> -->
                            <div class="control-group col-md-7">
                                <label class="control-label">* Nombre</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="nombre" id="nombre" value="${tarifa.nombre}" maxlength="20" required>
                                    <span id="msg_nombre"></span>
                                </div>
                            </div>
                            <div class="control-group col-md-7">
                                <label class="control-label">* Valor</label>
                                <div class="controls">
                                    <input type="text" class="form-control" name="valor" id="valor" value="${tarifa.valor}" maxlength="10" required>
                                    <span id="msg_valor"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7" style="padding-top: 10px; margin-left: 15px;">
                                    <input type="hidden" name="idTarifa" id="idTarifa" value="${tarifa.id}">
                                    <button style="display: none;" type="submit" class="btn"></button>
                                    <button style="width: 126px;" type="button" class="btn" onclick="sendTarifa('act');">Guardar</button>
                                </div>
                            </div>
                        </form>
                        </div>
                    </div>
                </section>                
            </div>

<jsp:include page="/include/footerHome.jsp" />
<jsp:include page="/include/end.jsp" />
