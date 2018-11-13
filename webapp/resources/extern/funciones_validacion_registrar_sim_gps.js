$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	        
        var cod = /^([a-zA-Z]+[0-9]{3,3})*$/;
        var intNum = /^(([a-zA-Z]*)+([0-9]*))*$/;
        var intNum1 = /^([0-9]*)*$/;
        
        $(".botonAddSim").click(function(){
		$(".error").fadeOut().remove();		
                $(".error2").fadeOut().remove();		             
                       
            if ($("#num_cel_sim_rec").val() === "") 
            {  
                $("#num_cel_sim_rec").focus().after('<span class="error">Ingrese el numero celular</span>');  
                $("#num_cel_sim_rec").select();
                return false;  
            } 
            
            if ($("#oper_rec").val() === "") 
            {            
                $("#oper_rec").focus().after('<span class="error2">Seleccione el operador</span>');           
                return false;  
            }                        
    });      

$("#oper_rec, #num_cel_sim_rec").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {
			$('.error').fadeOut();
                        $('.error2').fadeOut();
			return false;  
		}  
	});		
});

