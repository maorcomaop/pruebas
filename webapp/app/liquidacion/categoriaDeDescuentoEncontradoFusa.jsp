<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tr id="${idFila}">
    <td id="c${idFila}" class="categoria-descuento">
        <select class="selectpicker" data-width="165px" data-style="btn-primary" name="modulo" id="cobx${idFila}">
            <option value="0">Seleccione categoria</option>
            <c:forEach items="${select.lstcategorias}" var="cat1">                                                      
                <option value="${cat1.id}">${cat1.nombre}</option>                                                         
            </c:forEach>
        </select>        
        <script> $("#cobx${idFila} option[value=" +${categoriaEncontrada.id} + "]").attr("selected", true);</script> 
        <input type="hidden" id="valor_cate${idFila}" value="${categoriaEncontrada.id}">
    </td>
    <td contenteditable="false" id="b${idFila}" class="valor">
        <c:choose>
            <c:when test ="${categoriaEncontrada.es_valor_moneda == 1}">                                            
                $${categoriaEncontrada.valor}
            </c:when>
            <c:otherwise>
                ${categoriaEncontrada.valor}%
            </c:otherwise>
        </c:choose>  
    </td>
    <td contenteditable="false" id="p${idFila}" class="pasajeros">0</td>
    <td contenteditable="false" id="v${idFila}" class="valorTotal">0</td>
    <td contenteditable="true" id="o${idFila}" class="obervaciones"></td>

    <td id="l${idFila}">
        <c:choose>           
            <c:when test ="${categoriaEncontrada.aplicaDescuento == 1}">
                <span class="label label-success">Si</span>
            </c:when>
            <c:when test ="${categoriaEncontrada.aplicaGeneral == 1}">
                <span class="label label-danger">No</span>                                            
            </c:when>                                            
            <c:when test ="${categoriaEncontrada.descuenta_del_total == 1}">
                <span class="label label-danger">-</span>                                            
            </c:when>            
        </c:choose>
    </td>    
    <td id="vm${idFila}" style="display: none;">
        <c:choose>
            <c:when test ="${categoriaEncontrada.es_valor_moneda == 1}">
                <span class="label label-success">Si</span>
            </c:when>
            <c:otherwise>
                <span class="label label-danger">No</span>                                            
            </c:otherwise>
        </c:choose>
    </td>
    <td id="dp${idFila}">
        <c:choose>
            <c:when test ="${categoriaEncontrada.descuenta_pasajeros == 1}">
                <span class="label label-success">Si</span>
            </c:when>
            <c:otherwise>
                <span class="label label-danger">No</span>                                            
            </c:otherwise>
        </c:choose>
    </td>
    <td id="dnp${idFila}"><!--dnp=Descuento neto pasajeros-->
        <c:choose>
            <c:when test ="${categoriaEncontrada.descuenta_del_total == 1}">
                <span class="label label-primary">Si</span>
            </c:when>
            <c:otherwise>
                <span class="label label-danger">No</span>                                            
            </c:otherwise>
        </c:choose>
    </td>
    <td id="e${idFila}"><span class="table-remove glyphicon glyphicon-remove"></span></td>

<script>
    $(document).ready(function () {
        console.log("categoria de descuento encontrado");
        calculoInicialFusa();
        configuarEventosTablaDescuentosFusa();
        /*Controla al combo que modifica la fila*/
        $("#cobx${idFila}").change(function () {
            if ($(this).val() > 0)
            {
                $.post("/RDW1/encontrarCategoriasDeDescuentoFusa", {id: $(this).val()}, function (result) {
                    if ($.fn.dataTable.isDataTable('#tableDescuentosF')) {
                        var oTable = $('#tableDescuentosF').DataTable();
                        oTable.row($("#cobx${idFila}").parents('tr')).remove().draw();
                        oTable.row.add($(result)[0]).draw();
                    }
                });
            }
        });
    });
</script>

</tr>

