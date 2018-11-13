
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/include/headerHomeNew.jsp" />

<div class="col-lg-8 centered">   
    <section class="boxed padding">
        <form action="#" method="POST">
            <div class="row">
                <div class="controls col-sm-3 col-xs-12">
                    <textarea id="view" rows="20" cols="165" ></textarea>
                </div>
            </div>
            <div class="row ">                
                <div class="controls col-sm-6 col-xs-12 pull-right">
                    <a href="#" role="button"  class="btn btn-primary" id="start">Iniciar</a>                                                
                    <a href="#" role="button"  class="btn btn-primary" id="stop">Detener</a>                                                                    
                    <a href="#" role="button"  class="btn btn-primary" id="clear">Limpiar</a>                                
                </div>                    
            </div>
        </form> 
    </section>
</div>

<jsp:include page="/include/footerHome.jsp" />