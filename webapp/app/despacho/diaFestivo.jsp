
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew_.jsp" />

<div class="col-lg-8 centered">
    <h1 class="display-3 bg-info text-center">M&Oacute;DULO DESPACHO</h1>
    <c:choose>
        <c:when test="${permissions.check(['Despacho','RegistrarDespachos'])}">
            
            <section class="boxed padding-smin">
                <ul class="nav nav-tabs">                                   
                    <li role="presentation" class="active"><a href="#">D&iacute;as festivo</a></li>                
                    <li role="presentation"><a href="/RDW1/app/despacho/nuevoDespacho.jsp">Configuraci&oacute;n</a></li>                
                </ul>

                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane padding-smin active" style="padding-left: 20px;">                            

                        <div id="msg" class="form-msg bottom-space ${msgType}" role="alert">${msg}</div>

                        <form action="<c:url value='/adicionarDiaFestivo' />" method="post">
                            <div class="row">
                                <div class="control-group">
                                    <label class="col-sm-2">Fecha d&iacute;a festivo</label>
                                    <div class="col-sm-3">
                                        <input id="fecha-festivo" name="fecha-festivo" type="text" readonly="readonly">
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="submit" class="btn" style="width: 115px;" value="Guardar">
                                    </div>
                                </div>
                            </div>
                        </form>                                            

                        <div style="margin-top: 10px;"></div>

                        <table id="tablaDiaFestivo" class="display" cellspacing="0" width="100%">                           
                            <thead>
                                <tr>
                                    <td>Fecha</td>
                                    <td>Descripici&oacute;n</td>
                                    <td>Desactivar</td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${select.lst_diasfestivo}" var="item">
                                    <tr>
                                        <td>${item.fecha}</td>
                                        <td>${item.descripcion}</td>
                                        <td>   
                                            <form id="form-delete-dia-${item.id}" class="form-in-table" action="<c:url value='/eliminarDiaFestivo' />" method="post">
                                                <input type="hidden" id="fecha" name="fecha" value="${item.fecha}">
                                                <button type="button" class="btn btn-danger btn-xs" onclick="confirmar_eliminacion('${item.id}', '${item.fecha}');" 
                                                        style="width: 80px;">Eliminar</button>
                                            </form>
                                        </td>                                    
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
        </c:when>
        <c:otherwise>
            <jsp:include page="/include/accesoDenegado.jsp" />
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/include/footerHome_.jsp" />
<script>

    $('document').ready(function () {
        
        var fecha = $('#fecha-festivo').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            language: 'es'
        }).on('changeDate', function (e) {
            fecha.datepicker('hide');
        });
        
        $('#tablaDiaFestivo').DataTable({
            "aLengthMenu": [5, 7, 10, 25],
            responsive: true,
            language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
        });
    });

    function confirmar_eliminacion(id, objeto) {

        var sform = "#form-delete-dia-" + id;
        var msg = "¿Est&aacute; seguro de eliminar el d&iacute;a festivo <strong>" + objeto + "</strong>?";

        bootbox.confirm({
            title: "Eliminaci&oacute;n de registro",
            message: msg,
            buttons: {
                confirm: {
                    label: 'Aceptar',
                    className: 'btn btn-primary'
                },
                cancel: {
                    label: 'Cancelar',
                    className: 'btn'
                }
            },
            callback: function (rs) {
                if (rs) {
                    $(sform).submit();
                }
            }
        });
    }

</script>
<jsp:include page="/include/end.jsp" />
