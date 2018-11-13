
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<body>   			

    <div class="col-lg-12">            
        <section class="boxed padding">
            <ul class="nav nav-tabs">
                <li role="presentation" ><a href="/RDW1/app/motivo/nuevoMotivo.jsp">Registro</a></li>
                <li role="presentation" class="active"><a href="/RDW1/app/motivo/listadoMotivo.jsp">Listado de Motivos</a></li>                    
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane padding active" id="pestana1">
                    <table id="tableAlarma" class="display compact" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th>C&oacute;digo</th>                                
                                <th>Tabla auditada</th>
                                <th>Descripci&oacute;n</th>
                                <th>Usuario del sistema</th>                                
                                <th>Usuario de la base de datos</th>                                                                
                                <th>Editar</th>
                                <th>Deshabilitar</th>                                                                
                            </tr>    
                        </thead>     
                        <tbody>
                            <c:choose>
                                <c:when test="${select.lstmotivo.size() > 0}">
                            <tr>
                                <c:forEach items="${select.lstmotivo}" var="motivo">    
                                    <td>${motivo.fk_item_a_auditar}</td>                                    
                                    <td>${motivo.nombreTablaAAuditar}</td>
                                    <td>${motivo.descripcionMotivo}</td>
                                    <td>${motivo.usuario}</td>
                                    <td>${motivo.usuarioBD}</td>


                                    <td>
                                        <form action="<c:url value='/verMasMotivo' />" method="post">
                                            <input type="hidden" name="id" value="${motivo.id}">
                                            <input type="submit" class="btn  btn-xs btn-info" value="Editar">
                                        </form>            
                                    </td>

                                </tr>
                            </c:forEach>   
                                </c:when>
                                </c:choose>
                        </tbody>    
                    </table>
                </div>        
            </div>        
        </section>
    </div>        
    <jsp:include page="/include/footerHome.jsp" />
    <script>
        // Tabla dinamica
        $(document).ready(function () {
            $('#tableAlarma').DataTable({
                aLengthMenu: [300, 500, 1000],
                scrollY: 500,
                scrollX: true
                searching: true,
                bAutoWidth:false,
                bInfo: false,
                paging: false,
                language:{url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
            });
        });
    </script>
    <jsp:include page="/include/end.jsp" />