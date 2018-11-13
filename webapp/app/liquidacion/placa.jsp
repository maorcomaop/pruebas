<!--PAGINA QUE SE IMPRIME CUANDO SE BUSCA LA PLACA DE UN VEHICULO POR MEDIO DEL NUMERO INTERNO-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${placa}" var="matricula">
       <option value="${matricula.id}">${matricula.numeroInterno}</option>
</c:forEach>
