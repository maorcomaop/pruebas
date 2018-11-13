/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var espaciosEnBlanco = /^\s*$/;

// Tabla dinamica
$(document).ready(function ()
{
    $('.display').DataTable({
        aLengthMenu: [600, 1000],
        scrollY: 500,
        scrollX: true,
        searching: true,
        bAutoWidth: false,
        bInfo: false,
        paging: false,
        language: {url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}
    });
});


/*Controla el acceso al boton eliminar del listado*/
$(function () {
    $(".eliminar").click(function () {
        confirmarEliminacion();
    });
});


function confirmarEliminacion() {
    var mensaje = confirm("¿Desea desactivar el registro?");

    if (mensaje) {
        return true;
    } else {
        return false;
    }
}


$(function () {
    $("#btnGuardar").click(function () {
        var errorFkModulo = document.getElementById("errorFkModulo");
        
        if (errorFkModulo != null) {
            errorFkModulo.parentNode.removeChild(errorFkModulo);
        }
        
        var moduloRdw = new Number($("#moduloRdw").val());
        
        if (moduloRdw <= 0) {
            $("#moduloRdw").focus().after('<span class="error2" id="errorFkModulo">Seleccione un módulo...</span>');  
            $("#moduloRdw").select();
            return false;
        } 
        
        var errorPregunta = document.getElementById("errorPregunta");
        
        if (errorPregunta != null) {
            errorPregunta.parentNode.removeChild(errorPregunta);
        }
        
        var preguntaFrecuente = $("#preguntaFrecuente").val();
        
        if (preguntaFrecuente == null || espaciosEnBlanco.test(preguntaFrecuente)) {
            $("#preguntaFrecuente").focus().after('<span class="error2" id="errorPregunta">Ingrese una pregunta frecuente válida.</span>');  
            $("#preguntaFrecuente").select();
            return false;
        } else if (preguntaFrecuente.length > 500) {
            $("#preguntaFrecuente").focus().after('<span class="error2" id="errorPregunta">Supera los 500 caracteres permitidos.</span>');  
            $("#preguntaFrecuente").select();
            return false;
        }
        
        var errorRespuesta = document.getElementById("errorRespuesta");
        
        if (errorRespuesta != null) {
            errorRespuesta.parentNode.removeChild(errorRespuesta);
        }
        
        var respuestaPF = $("#respuestaPF").val();
        
        if (respuestaPF == null || espaciosEnBlanco.test(respuestaPF)) {
            $("#respuestaPF").focus().after('<span class="error2" id="errorRespuesta">Ingrese una respuesta válida.</span>');  
            $("#respuestaPF").select();
            return false;
        } else if (respuestaPF.length > 5000) {
            $("#respuestaPF").focus().after('<span class="error2" id="errorRespuesta">Supera los 5000 caracteres permitidos.</span>');  
            $("#respuestaPF").select();
            return false;
        }
        
        return true;
    });
});

/*Se utiliza para cambiar a mayúscula los valores de los input:text*/
$("input:text").blur(function(){
    $("#" + $(this).attr("id")).val($("#" + $(this).attr("id")).val().trim().toUpperCase());
});



// Select all links with hashes
$('a[href*="#"]')
  // Remove links that don't actually link to anything
  .not('[href="#"]')
  .not('[href="#0"]')
  .click(function(event) {
    // On-page links
    if (
      location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') 
      && 
      location.hostname == this.hostname
    ) {
      // Figure out element to scroll to
      var target = $(this.hash);
      target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
      // Does a scroll target exist?
      if (target.length) {
        // Only prevent default if animation is actually gonna happen
        event.preventDefault();
        $('html, body').animate({
          scrollTop: target.offset().top
        }, 1000, function() {
          // Callback after animation
          // Must change focus!
          var $target = $(target);
          $target.focus();
          if ($target.is(":focus")) { // Checking if the target was focused
            return false;
          } else {
            $target.attr('tabindex','-1'); // Adding tabindex for elements not focusable
            $target.focus(); // Set focus again
          };
        });
      }
    }
  });