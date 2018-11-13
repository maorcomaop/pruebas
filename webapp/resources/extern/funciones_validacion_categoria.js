$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	
        var numeroEntero = /^([0-9]*)/;
        var num = /^([0.0-9])*$/;
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ]+\s*)+$/;
        var porcentaje = /^([\d]+[\.]+[\d]{1,3})*$/;
        
        var n = /^([a-záéíóúA-ZÁÉÍÓÚ0-9]+\s*)+$/;
      
	
        $(".botonCategoria").click(function(){  
		$(".error").fadeOut().remove();
                $(".error3").fadeOut().remove();
		
                
        if ($("#name").val() === "") {  
			$("#name").focus().after('<span class="error">Ingrese el nuevo nombre de la categor\u00CDa</span>');  
                        $("#name").select();
			return false;  
	}else if ( ($("#name").val() !== "") && (!n.test($("#name").val())) ){
            $("#name").focus().after('<span class="error">No es un nombre valido, Ingrese el nombre nuevamente</span>');                          
            $("#name").select();
            return false;            
        }
        
        
        
        /*if ($("#description").val() === "") {  
			$("#description").focus().after('<span class="error3">Ingrese la descripcion</span>');  
                        $("#description").select();
			return false;  
	}else if ( ($("#description").val() !== "") && (!n.test($("#description").val())) ){
            $("#description").focus().after('<span class="error"> No es una descripcion valida, Ingrese la descripcion nuevamente</span>');
            $("#description").select();
            return false;            
        }*/
        
        
        /*SI EL CHECK BOX ESTA ACTIVO EN SI*/        
        if( $('#valor').prop('checked') ) 
        {           
                     
            
            try {    
                console.log("entra al try esta en si es moneda");
                if ($("#val").val() === "")
                {
			$("#val").focus().after('<span class="error">Ingrese el valor</span>');  
                        $("#val").select();
			return false;  
                }
                if ($.isNumeric( $("#val").val() ))
                {
                    if( !(parseFloat($("#val").val()) >= 0.0))
                    {   
                        $("#val").focus().after('<span class="error">Ingrese un valor n\u00FAmerico mayor de cero</span>');  
                        $("#val").select();
                        return false;
                    }                    
                }
                else
                {
                    throw $("#val").focus().after('<span class="error">Ingrese un valor n\u00FAmericos</span>');  
                }
            }
            catch(err) 
            {
                console.log(">>>" +err);
                return false;  
            }
        }
        else/*SI EL CHECK TIENE VALOR EN NO*/
        {     
                       
            try {    
                console.log("entra al try esta en no es porcentaje");
              if ($("#val").val() === "")
             {
		$("#val").focus().after('<span class="error">Ingrese el valor</span>'); 
                $("#val").select();
		return false;  
             }
             if ($.isNumeric( $("#val").val() ))
             {
                if ( ( !(parseFloat($("#val").val()) >= 0.0)) || ( !(parseFloat($("#val").val()) <= 100)) )
                {   
                           $("#val").focus().after('<span class="error">No es un valor valido, Ingrese un valor n\u00FAmerico desde 0.0 hasta 100.0</span>');  
                           $("#val").select();
                           return false;
                }
                else
                {}
             }
             else
             {
                 throw $("#val").focus().after('<span class="error">Ingrese un valor n\u00FAmericos</span>'); 
             }
                             
            }
            catch(err) 
            {
                console.log(">>>---" +err);
                return false;  
            }
            //return false;
        }
        
        
        
       
    });  
    
    
	$("#name, #description, #val, #valor").bind('blur keyup', function(){  
        if ($(this).val() !== "") {  			
			$('.error').fadeOut();
                        $(".error3").fadeOut();
			return false;  
		}  
	});
        
        /*$(".email").bind('blur keyup', function(){  
        if ($(".email").val() != "" && emailreg.test($(".email").val())) {	
			$('.error').fadeOut();  
			return false;  
		}  
	});*/
});