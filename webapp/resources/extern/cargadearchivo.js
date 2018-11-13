$(document).ready(function() {
        function datosIngresados(){
                //$.post("/RDW1/cargarDatos", {valor: 1}, function (result) {  $("#view").val($("#view").val() + result);});                
                $.post("/RDW1/cargarDatos", {valor: 1}, function (result) {  $("#view").val($("#view").val());});                
                 var $textarea = $('#view');  $textarea.scrollTop($textarea[0].scrollHeight);
        }            
        
        var timer = null;
        function startSetInterval() {
               timer = setInterval(datosIngresados, 3000);
        }
        
        startSetInterval();
        
        $('#start').on("click", function () {startSetInterval();})
        
        $("#stop").on("click", function () {clearInterval(timer);});
        
        $("#clear").on("click", function () {
            $("#view").val("");
        });
    });
    