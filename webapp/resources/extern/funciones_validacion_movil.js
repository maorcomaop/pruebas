$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	        
        var cod = /^([a-zA-Z]+[0-9]{3,3})*$/;
        var intNum = /^(([a-zA-Z]*)+([0-9]*))*$/;
        var intNum1 = /^([0-9]*)*$/;
        
        $(".botonMovil").click(function(){
		$(".error").fadeOut().remove();		
                $(".error4").fadeOut().remove();		
        
        if ($("#matricula").val() === "") 
        {
            $("#matricula").focus().after('<span class="error">Ingrese la nueva placa</span>');  
            $("#matricula").select();
            return false;  
	}
        if ($("#number").val() === "") 
        {  
            $("#number").focus().after('<span class="error">Ingrese un n\u00FAmero interno</span>');  
            $("#number").select();
            return false;  
        }
        else if ( ($("#number").val() !== "") && (!intNum.test($("#number").val())) )
        {   
            console.log("entra el sino");
            $("#number").focus().after('<span class="error">No es un n\u00FAmero interno valido, Ingrese el numero interno nuevamente</span>');                          
            $("#number").select();
            return false;
        }
        //-------
         if ( ($("#idgps").val() !== "") && (!intNum.test($("#idgps").val())) )
            {   
                $("#idgps").focus().after('<span class="error">No es un ID valido, \n Ingrese un ID; Se permiten caracteres alfabeticos</span>');                          
                $("#idgps").select();
                return false;
            }
             if ( ($("#n_cel").val() !== "") && (!intNum1.test($("#n_cel").val())) )
            {   
                $("#n_cel").focus().after('<span class="error">No es un numero valido, \n Ingrese un numero celular </span>');                          
                $("#n_cel").select();
                return false;
            }
        if (($("#idgps").val()) && ($("#hrdwr").val() === ""))
            {                      
                alert("Debe seleccionar un dispositivo de hardware compatible");
                return false;
            }
        if (($("#idgps").val()) && ($("#brand").val() === "") && ($("#model").val() === ""))
            {                      
                alert("Debe seleccionar marca y modelo del gps para ser registrado");
                return false;
            }
            
         if (($("#n_cel").val()) && ($("#oper").val() === ""))
            {                      
                alert("Debe seleccionar un operador para registrar el numero celular");
                return false;
            }
    });      
    

$(" #matricula, #number, #hrdwr").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {
			$('.error').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});

