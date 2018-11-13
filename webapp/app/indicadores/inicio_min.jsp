
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-10 centered">
    <!--
    <section class="padding">
        <div>
            <ul>
                <li><a href="/RDW1/app/indicadores/indicadorProduccion.jsp">Indice de productividad</a></li>
                <li><a href="/RDW1/app/indicadores/indicadorCapacidadTransportadora.jsp">Indice de capacidad transportadora</a></li>
                <li><a href="/RDW1/app/indicadores/indicadorCumplimientoRuta.jsp">Indice de cumplimiento de ruta</a></li>
                <li><a href="/RDW1/app/indicadores/indicadorPasajeroHora.jsp">Indice de pasajeros por hora</a></li>
            </ul>
        </div>
    </section>
    -->
    <main class="mitgliederliste">
        <figure class="einzel">
            <a href="/RDW1/app/indicadores/indicadorProduccion.jsp">
                <div class="panel-icon horizontal-figure"></div>
                <div class="panel-text">Productividad</div>
            </a>
        </figure>
        <figure class="einzel">            
            <a href="/RDW1/app/indicadores/indicadorCapacidadTransportadora.jsp">
                <div class="panel-icon table-figure"></div>
                <div class="panel-text">Capacidad transportadora</div>
            </a>
        </figure>        
        <figure class="einzel">            
            <a href="/RDW1/app/indicadores/indicadorCumplimientoRuta.jsp">
                <div class="panel-icon line-figure"></div>
                <div class="panel-text">Cumplimiento ruta</div>
            </a>
        </figure>        
        <figure class="einzel">            
            <a href="/RDW1/app/indicadores/indicadorPasajeroHora.jsp">
                <div class="panel-icon line-figure"></div>
                <div class="panel-text">Pasajero hora</div>
            </a>
        </figure>        
        <figure class="einzel">            
            <a href="/RDW1/app/indicadores/indicadorDescuentoDePasajeros.jsp">
                <div class="panel-icon pie-figure"></div>
                <div class="panel-text">Descuento de pasajeros</div>
            </a>
        </figure>        
    </main>
</div>

<jsp:include page="/include/footerHome_.jsp" />
<jsp:include page="/include/end.jsp" />