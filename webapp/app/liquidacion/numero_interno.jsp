<!--PAGINA QUE SE IMPRIME CUANDO SE BUSCA EL NUMERO INTERNO DE UN VEHICULO POR MEDIO DE LA PLACA-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${numeroInterno}" var="numInt">
       <option value="${numInt.id}">${numInt.placa}</option>
</c:forEach>
