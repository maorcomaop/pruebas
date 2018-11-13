$(function() { 
	var emailreg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;	                
        var letrasYNumeros = /^(([a-zA-Z]*)+([0-9]*))*$/;
        var Numeros = /^([0-9]*)*$/;
        
        $(".botonConfigDes").click(function(){
		$(".error").fadeOut().remove();		
                $(".error4").fadeOut().remove();		
        
        if ($("#nom_conf").val() === ""){
            $("#nom_conf").focus().after('<span class="error4">Ingrese el nombre</span>');              
            return false;  
	}
        else if ( ($("#nom_conf").val() !== "") && (!letrasYNumeros.test($("#nom_conf").val())) ){   
            $("#nom_conf").focus().after('<span class="error4">No es un nombre valido; Ingrese el nombre sin signos especiales</span>');            
            $("#nom_conf").select();
            return false;
        }
        
            
        if ($("#pto_max").val() === ""){  
            $("#pto_max").focus().after('<span class="error4">Ingrese un valor de puntaje valido</span>');                          
            return false;  
        }/*else if ( ($("#pto_max").val() !== "") && ( parseInt($("#pto_max").val()) < 0)){            
            $("#pto_max").focus().after('<span class="error4">Ingresar un valor mayor a cero</span>');            
            $("#pto_max").select();
            return false;
        }else if ( ($("#pto_max").val() !== "") && ( parseInt($("#pto_max").val()) > 10)){            
            $("#pto_max").focus().after('<span class="error4">Ingresar un valor mayor 10</span>');            
            $("#pto_max").select();
            return false;
        }*/else if ( ($("#pto_max").val() !== "") && (!Numeros.test($("#pto_max").val())) ){            
            $("#pto_max").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');            
            $("#pto_max").select();
            return false;
        }        
        
            
        if ($("#pto_e_v").val() === ""){            
            $("#pto_e_v").focus().after('<span class="error4">Ingrese los puntos por exceso de velocida</span>');           
            return false;  
        }/*else if ( ($("#pto_e_v").val() !== "") && ( parseInt($("#pto_e_v").val()) < 0)){               
            $("#pto_e_v").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#pto_e_v").select();
            return false;
        }else if ( ($("#pto_e_v").val() !== "") && ( parseInt($("#pto_e_v").val()) > 10)){               
            $("#pto_e_v").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#pto_e_v").select();
            return false;
        }*/else if ( ($("#pto_e_v").val() !== "") && (!Numeros.test($("#pto_e_v").val())) ){               
            $("#pto_e_v").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
            $("#pto_e_v").select();
            return false;
        }
        
        
        if ($("#v_max").val() === ""){            
            $("#v_max").focus().after('<span class="error4">Ingrese el limite de velocidad permitido</span>');           
            return false;  
        }/*else if ( ($("#v_max").val() !== "") && ( parseInt($("#v_max").val()) < 0) ){               
            $("#v_max").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#v_max").select();
            return false;
        }else if ( ($("#v_max").val() !== "") && ( parseInt($("#v_max").val()) > 10) ){               
            $("#v_max").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#v_max").select();
            return false;
        }*/else if ( ($("#v_max").val() !== "") && (!Numeros.test($("#v_max").val())) ){               
            $("#v_max").focus().after('<span class="error4">No es un limite de velocidad permitido, Ingrese el limite de velocidad permitido</span>');                          
            $("#v_max").select();
            return false;
        }
        
        
        if ($("#pto_ruta").val() === ""){            
            $("#pto_ruta").focus().after('<span class="error4">Ingrese los puntos por ruta no cumplida</span>');           
            return false;  
        }/*else if ( ($("#pto_ruta").val() !== "") && ( parseInt($("#pto_ruta").val()) === 0)){               
            $("#pto_ruta").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#pto_ruta").select();
            return false;
        }else if ( ($("#pto_ruta").val() !== "") && ( parseInt($("#pto_ruta").val()) === 0)){               
            $("#pto_ruta").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#pto_ruta").select();
            return false;
        }*/else if ( ($("#pto_ruta").val() !== "") && (!Numeros.test($("#pto_ruta").val())) ){               
            $("#pto_ruta").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
            $("#pto_ruta").select();
            return false;
        }
        
            
        if ($("#por_ruta").val() === ""){            
            $("#por_ruta").focus().after('<span class="error4">Ingrese el porcentaje de la ruta</span>');           
            return false;  
        }/*else if ( ($("#por_ruta").val() !== "") && ( parseInt($("#por_ruta").val()) < 0)){               
            $("#por_ruta").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#por_ruta").select();
            return false;
        }else if ( ($("#por_ruta").val() !== "") && ( parseInt($("#por_ruta").val()) > 10)){               
            $("#por_ruta").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#por_ruta").select();
            return false;
        }*/else if ( ($("#por_ruta").val() !== "") && (!Numeros.test($("#por_ruta").val())) ){               
            $("#por_ruta").focus().after('<span class="error4">No es un valor valido, Ingrese el valor nuevamente</span>');                          
            $("#por_ruta").select();
            return false;
        }
            
            
         if ($("#pto_dias").val() === ""){            
            $("#pto_dias").focus().after('<span class="error4">Ingrese los puntos por dias no laborados</span>');           
            return false;  
        }/*else if ( ($("#pto_dias").val() !== "") && ( parseInt($("#pto_dias").val()) === 0)){               
            $("#pto_dias").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#pto_dias").select();
            return false;
        }else if ( ($("#pto_dias").val() !== "") && ( parseInt($("#pto_dias").val()) === 0)){               
            $("#pto_dias").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#pto_dias").select();
            return false;
        }*/else if ( ($("#pto_dias").val() !== "") && (!Numeros.test($("#pto_dias").val())) ){               
            $("#pto_dias").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
            $("#pto_dias").select();
            return false;
        }
        
            
        if ($("#d_des").val() === ""){            
            $("#d_des").focus().after('<span class="error4">Ingrese los dias de descanso</span>');           
            return false;  
        }/*else if ( ($("#d_des").val() !== "") && ( parseInt($("#d_des").val()) < 0)){               
            $("#d_des").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#d_des").select();
            return false;
        }else if ( ($("#d_des").val() !== "")  && ( parseInt($("#d_des").val()) > 10)){               
            $("#d_des").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#d_des").select();
            return false;
        }*/else if ( ($("#d_des").val() !== "") && (!Numeros.test($("#d_des").val())) ){               
            $("#d_des").focus().after('<span class="error4">No es un valor valido, Ingrese nuevamente los dias de descanso</span>');                          
            $("#d_des").select();
            return false;
        }
            
            
        if ($("#pto_ipk_max").val() === ""){            
            $("#pto_ipk_max").focus().after('<span class="error4">Ingrese los puntos ipk mayor</span>');           
            return false;  
        }/*else if ( ($("#pto_ipk_max").val() !== "") && ( parseInt($("#pto_ipk_max").val()) === 0)){               
            $("#pto_ipk_max").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#pto_ipk_max").select();
            return false;
        }else if ( ($("#pto_ipk_max").val() !== "") && ( parseInt($("#pto_ipk_max").val()) === 0)){               
            $("#pto_ipk_max").focus().after('<span class="error4">Ingrese un valor mayor a cero</span>');                          
            $("#pto_ipk_max").select();
            return false;
        }*/else if ( ($("#pto_ipk_max").val() !== "") && (!Numeros.test($("#pto_ipk_max").val())) ){               
            $("#pto_ipk_max").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
            $("#pto_ipk_max").select();
            return false;
        }
        
        
        if ($("#pto_ipk_men").val() === ""){            
            $("#pto_ipk_men").focus().after('<span class="error4">Ingrese los puntos por ipk menor</span>');           
            return false;  
        }/*else if ( ($("#pto_ipk_men").val() !== "") && ( parseInt($("#pto_ipk_men").val()) < 0)){               
            $("#pto_ipk_men").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
            $("#pto_ipk_men").select();
            return false;
        }else if ( ($("#pto_ipk_men").val() !== "") && ( parseInt($("#pto_ipk_men").val()) > 10)){               
            $("#pto_ipk_men").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
            $("#pto_ipk_men").select();
            return false;
        }*/else if ( ($("#pto_ipk_men").val() !== "") && (!Numeros.test($("#pto_ipk_men").val())) ){               
            $("#pto_ipk_men").focus().after('<span class="error4">No es un puntaje valido, Ingrese el puntaje nuevamente</span>');                          
            $("#pto_ipk_men").select();
            return false;
        }
        
    });      
    

$(" #nom_conf, #pto_max, #pto_e_v, #v_max, #pto_ruta, #por_ruta, #pto_dias, #d_des, #pto_ipk_max, #pto_ipk_men").bind('blur keyup change', function(){  
        if ($(this).val() !== "") {
			$('.error4').fadeOut();
                        $('.error4').fadeOut();
			return false;  
		}  
	});		
});

