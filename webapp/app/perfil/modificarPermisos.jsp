
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-md-9">
    
    <ul class="nav nav-tabs">
        <li role="presentation" ><a href="/RDW1/app/perfil/nuevoPerfil.jsp">Registro</a></li>
        <li role="presentation" class="active"><a href="/RDW1/app/perfil/listadoPerfil.jsp">Listado de Perfiles</a></li>                    
    </ul>
    <section class="boxed padding">

    </section>
</div>

<jsp:include page="../../include/footerHome.jsp" />
<script>
		// Tabla dinamica
		$(document).ready(function() {
			$('#tablePerfil').DataTable({
                                aLengthMenu: [15,25,50,100],
                                scrollY: 500,
                                scrollX: true 
                                }
                                );
		});				
</script>
<jsp:include page="/include/end.jsp" />