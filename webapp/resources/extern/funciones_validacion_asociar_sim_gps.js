$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	        
        var cod = /^([a-zA-Z]+[0-9]{3,3})*$/;
        var intNum = /^(([a-zA-Z]*)+([0-9]*))*$/;
        var intNum1 = /^([0-9]*)*$/;
        
        $(".botonMapSim").click(function(){
		$(".error").fadeOut().remove();		
                $(".error2").fadeOut().remove();		             
                       
            if ($("#id_gps_asociar_sim_rec").val() === "") 
            {  
                $("#id_gps_asociar_sim_rec").focus().after('<span class="error">Ingrese el numero celular</span>');                  
                return false;  
            } 
            
            if ($("#numero_celular").val() === "") 
            {            
                $("#numero_celular").focus().after('<span class="error2">Seleccione el operador</span>');           
                return false;  
            }                        
    });      

$("#id_gps_asociar_sim_rec, #numero_celular").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {
			$('.error').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});

