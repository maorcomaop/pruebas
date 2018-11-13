<jsp:include page="/include/headerOnlyMap.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div id="map" style="
            width: 100%;
            max-height: 1080px; 
            min-height: 446px;
            height: 446px;">
    </div>
    
    <footer style="padding: 4px;">
        <center>
            <div style="width: 200px; height: 20px;"><img src="/RDW1/resources/img/logoregistel.png"></div>
        </center>
    </footer>

<jsp:include page="/include/footerappMapMin.jsp" />
<script>
    $(document).ready(function() {
        
        iniciarMapaSoloLectura();
        
        /*
        var url = window.location.href;
        var params_url = url.split("?")[1];
        var latlon_params = params_url.split("&");
        var lat = latlon_params[0].split("=")[1];
        var lon = latlon_params[1].split("=")[1];
        
        addMark(lat, lon); 
        */
       
       var url = window.location.href;
       var param_url  = url.split("?")[1];
       var param_str  = param_url.split("=")[1];
       var decode_str = decodeURIComponent(param_str);
       var array_str  = decode_str.split("$");
       
       // lat $ lon $ fecha $ placa $ msg $ localizacion
       if (array_str != null && array_str.length == 6) {
            var lat   = array_str[0];
            var lon   = array_str[1];
            var fecha = array_str[2];
            var placa = array_str[3];
            var msg   = array_str[4];
            var loc   = array_str[5];
            var info  = 
                    "<b>Fecha:</b> "  + fecha + "<br>" +
                    "<b>Evento:</b> " + msg + "<br>" +                    
                    "<b>Localizaci&oacute;n:</b> " + loc + "<br>" +
                    "<b>Placa:</b> "  + placa;
            addMark(lat, lon, info);
        }
       
    });
</script>
<jsp:include page="/include/end.jsp" />