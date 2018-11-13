$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	        
        var cod = /^([a-zA-Z]+[0-9]{3,3})*$/;
        var intNum = /^(([a-zA-Z]*)+([0-9]*))*$/;
        var intNum1 = /^([0-9]*)*$/;
        
        
        
        
        $(".botonMovilEdit").click(function(){
		$(".error").fadeOut().remove();		
                $(".error4").fadeOut().remove();		
                       
        
            if ($("#matricula_edit").val() === "") 
            {
                $("#matricula_edit").focus().after('<span class="error">Ingrese la nueva placa</span>');  
                $("#matricula_edit").select();
                return false;  
            }
            else if ( ($("#matricula_edit").val() !== "") && (!intNum.test($("#matricula_edit").val())) )
            {   
                $("#matricula_edit").focus().after('<span class="error">No es una placa valida, Ingrese la placa nuevamente consta de 3 letras y 3 numeros</span>');                          
                $("#matricula_edit").select();
                return false;
            }                        
            if ($("#number_edit").val() === "") 
            {  
                $("#number_edit").focus().after('<span class="error">Ingrese un n\u00FAmero interno</span>');  
                $("#number_edit").select();
                return false;  
            }
            else if ( ($("#number_edit").val() !== "") && (!intNum.test($("#number_edit").val())) )
            {   
                console.log("entra el sino");
                $("#number_edit").focus().after('<span class="error">No es un n\u00FAmero interno valido, Ingrese el numero interno nuevamente</span>');                          
                $("#number_edit").select();
                return false;
            }/*
            if ($("#prop_edit").val() === "") 
            {            
            $("#prop_edit").focus().after('<span class="error4">Seleccione propietario</span>');           
            return false;  
            }*/
            if ($("#pasenger_edit").val() === "") 
            {  
                $("#pasenger_edit").focus().after('<span class="error">Ingrese la cantidad de pasajeros</span>');  
                $("#pasenger_edit").select();
                return false;  
            }            
            else if  (intNum1.test($("#pasenger_edit").val()))
            {   
                if ( parseInt($("#pasenger_edit").val()) < 0 )
                {
                    $("#pasenger_edit").focus().after('<span class="error">Ingrese la cantidad de pasajeros mayor a cero</span>');                          
                    $("#pasenger_edit").select();
                    return false;
                }                
                if( parseInt($("#pasenger_edit").val()) > 100 )
                {
                    $("#pasenger_edit").focus().after('<span class="error">Ingrese la cantidad de pasajeros menor a 100</span>');                          
                    $("#pasenger_edit").select();
                    return false;
                }  
            }   
            else
            {
                    $("#pasenger_edit").focus().after('<span class="error">No es una cantidad valida</span>');                          
                    $("#pasenger_edit").select();
                    return false;
            }                        
            if ($("#group_edit").val() === "") 
            {            
                $("#group_edit").focus().after('<span class="error4">Seleccione un grupo</span>');           
                return false;                  
            }   
            
            
             if ( ($("#codigo_gps_edit").val() !== "") && (!intNum.test($("#codigo_gps_edit").val())) )
            {   
                $("#codigo_gps_edit").focus().after('<span class="error">No es un ID valido, \n Ingrese un ID; Se permiten caracteres alfabeticos</span>');                          
                $("#codigo_gps_edit").select();
                return false;
            }
            
            if ( ($("#numero_celular_edit").val() !== "") && (!intNum1.test($("#numero_celular_edit").val())) )
            {   
                $("#numero_celular_edit").focus().after('<span class="error">No es un numero valido, \n Ingrese un numero celular </span>');                          
                $("#numero_celular_edit").select();
                return false;
            }
            
            
            if (($("#codigo_gps_edit").val()) && ($("#hrdwr_edit").val() === ""))
            {                      
                alert("Debe seleccionar un dispositivo de hardware compatible");
                return false;
            }
            
            if($("#codigo_gps_edit").val() === ''){
                $("#codigo_gps_edit").val('0');              
            }
            
            if (($("#codigo_gps_edit").val() !== '0') && ($("#marca_gps_rec").val() === null))
            {                      
                alert("Debe seleccionar marca y modelo del gps para ser registrado");
                return false;
            }
            
            if(($("#codigo_gps_edit").val() !== '0') && ($("#marca_gps_rec").val() === '')){
                alert("Debe seleccionar marca y modelo del gps para ser registrado");
                return false;                
            }
            
            if($("#numero_celular_edit").val() === ''){
                $("#numero_celular_edit").val('0');              
            }
            
            if (($("#numero_celular_edit").val() !== "0") && ($("#oper_gps_rec").val() === null))
            {                      
                alert("Debe seleccionar un operador para registrar el numero celular");
                return false;
            }
            
            if (($("#numero_celular_edit").val() !== "0") && ($("#oper_gps_rec").val() === ''))
            {                      
                alert("Debe seleccionar un operador para registrar el numero celular");
                return false;
            }           
    });      

$(" #matricula_edit, #number_edit, #company, #pasenger, #group_edit, #numero_celular_edit").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {
			$('.error').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});

