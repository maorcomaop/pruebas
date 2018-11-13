<!--PAGINA QUE SE IMPRIME PARA CARGAR TODAS LAS CATEGORIAS DE DESCUENTO-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:choose>
    <c:when test="${categoriasFijas.size() > 0}">
        <div class="row adicione-categoria">                    
            <div class="col-md-3" ><select class="selectpicker" data-style="btn-primary" name="unaCategoria" id="addCategoria" data-width="180px">
                    <option value="0">Adicione categor&iacute;as</option>
                    <c:forEach items="${select.lstcategorias}" var="cat1">                                                      
                        <option value="${cat1.id}">${cat1.nombre}</option>
                    </c:forEach>
                </select></div>
            <div>
                <c:choose>
                    <c:when test="${placaVehiculoId > 0}">
                        <input type="hidden" id="id_vh_lqN" class="" value="<c:out value="${placaVehiculoId}"></c:out>">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" id="id_vh_lqN" class="" value="0">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>        
        
        <table id="tableDescuentos" class="display compact style_tbl_descuentos" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th>Categor&iacute;a</th>
                    <th>Base</th>
                    <th>Pasajeros</th>
                    <th>Valor</th>
                    <th>Observaciones</th>
                    <th>Liquidar</th>                                
                    <th style="display:none;">Es moneda</th>
                    <th>Des. pasajeros</th>  
                    <th>Eliminar</th>
                </tr>    
            </thead>    
            <tbody>  
                <c:forEach items="${categoriasFijas}" var="cat" begin="0" end="${categoriasFijas.size()}" varStatus="p">
                    <c:choose>
                        <c:when test ="${cat.es_fija == 1}">
                            <c:set var="id">
                                <c:out value="${p.count}-${cat.uniqueId}" />
                            </c:set>
                            <tr id="${id}">
                                <td id="c${id}" class="categoria-descuento">
                                    <select class="selectpicker" data-style="btn-primary" data-width="165px" name="modulo" id="cobx${id}">
                                        <option value="0">Seleccione categoría</option>
                                        <c:forEach items="${select.lstcategorias}" var="cat1">                                                      
                                            <option value="${cat1.id}">${cat1.nombre}</option>                                                         
                                        </c:forEach>
                                    </select>                                            
                                    <script> $("#cobx${id} option[value=" +${cat.id} + "]").attr("selected", true);</script>
                                    <input type="hidden" id="valor_cate${id}" class="category" value="${cat.id}">
                                </td>                                        
                                <td contenteditable="false" id="b${id}" class="valor">
                                    <c:choose>
                                        <c:when test ="${cat.es_valor_moneda == 1}">
                                            ${emp.simboloMoneda} ${cat.valor}
                                        </c:when>
                                        <c:otherwise>
                                            ${cat.valor}%
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td contenteditable="false" id="p${id}" class="pasajeros">0</td>
                                <td contenteditable="false" id="v${id}" class="valorTotal">0</td>
                                <td contenteditable="true" id="o${id}" class="observaciones"></td>
                                <td id="l${id}"><!--Descuentos operativo o admin-->
                                    <c:choose>
                                        <c:when test ="${cat.aplicaDescuento == 1}">
                                            <span class="label label-success">Si</span>
                                        </c:when>
                                        <c:when test ="${cat.aplicaGeneral == 1}">
                                            <span class="label label-danger">No</span>                                            
                                        </c:when> 
                                    </c:choose>
                                </td>
                                <td id="vm${id}" style="display: none;"><!--tipo de valor en moneda o porcentaje-->
                                    <c:choose>
                                        <c:when test ="${cat.es_valor_moneda == 1}">
                                            <span class="label label-success moneda">Si</span>
                                        </c:when>
                                        <c:when test ="${cat.es_valor_porcentaje == 1}">
                                            <span class="label label-danger moneda">No</span>                                            
                                        </c:when>
                                    </c:choose>
                                </td> 

                                <td id="dp${id}"><!--dp=Descuenta pasajeros-->
                                    <c:choose>
                                        <c:when test ="${cat.descuenta_pasajeros == 1}">
                                            <span class="label label-success">Si</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-danger">No</span>                                            
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td id="e${id}"><span class="table-remove glyphicon glyphicon-remove"></span></td>
                            </tr>        
                        </c:when>
                    </c:choose> 
                </c:forEach>
            </tbody>    
        </table>
    </c:when>
    <c:otherwise>
        <h5 class="title-area">NO EXISTE INFORMACION, DEBE ADICIONAR AL MENOS UNA CATEGORIA</h5>
    </c:otherwise>
</c:choose>
        <jsp:include page="/include/footerHomeLiq.jsp" />
<script>        
    $(document).ready(function () {}); //FIN DOCUMENT READER
    var myArray = [
    <c:forEach items="${categoriasFijas}" var="item">
        {id: "${item.id}"},
    </c:forEach>
    ];
</script>
