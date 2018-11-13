$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	        
        var cod = /^([a-zA-Z]+[0-9]{3,3})*$/;
        var intNum = /^(([a-zA-Z]*)+([0-9]*))*$/;
        var intNum1 = /^([0-9]*)*$/;
        
        $(".botonAddGps").click(function(){
		$(".error").fadeOut().remove();		
                $(".error2").fadeOut().remove();		             
            if ($("#cod_gps_rec").val() === "") 
            {                      
                $("#cod_gps_rec").focus().after('<span class="error">Ingrese el codigo</span>'); 
                $("#cod_gps_rec").select();
                return false;  
            }
            
            
            if ($("#marca_gps_rec").val() === "") 
            {                      
                $("#marca_gps_rec").focus().after('<span class="error2">Seleccione la marca de GPS</span>');                  
                return false;  
            
            }
            
            if ($("#modelo_gps_rec").val() === "") 
            {                      
                $("#modelo_gps_rec").focus().after('<span class="error2">Seleccione el modelo de GPS</span>');                  
                return false;  
            
            }  
            
            if ($("#num_cel_gps_rec").val() === "") 
            {  
                $("#num_cel_gps_rec").focus().after('<span class="error">Ingrese el numero celular</span>');  
                $("#num_cel_gps_rec").select();
                return false;  
            } 
            
            if ($("#oper_gps_rec").val() === "") 
            {            
                $("#oper_gps_rec").focus().after('<span class="error2">Seleccione el operador</span>');           
                return false;  
            }                        
    });      

$("#cod_gps_rec, #marca_gps_rec, #modelo_gps_rec, #num_cel_gps_rec, #oper_gps_rec").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {
			$('.error').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});

