$( document ).ready(function() {
                               
   $('.selectpicker').selectpicker({
       style: 'btn-primary',
       size: 4,       
       liveSearch: true});

    /*$.post("/RDW1/movilesDeGrupo", {id_grupo:$("#id_grupo_edit").val()},
                    function (result) {
                            $("#datos").html(result);
                            
                    }); */

  
window.setTimeout(function() {
                $(".alert").fadeTo(500, 0).slideUp(500, function(){
                $(this).remove(); }); }, 4000);
        
        /*FUNCIONES QUE CONTROLAN LA TABLA*/
            var table = $('#tableRelaciones').DataTable({
                            aLengthMenu: [300, 500],
                             scrollY: 500,
                            searching: true,
                            bAutoWidth:false,
                            bInfo: false,
                            paging: false,  
                            select: {
                                style: 'single'
                            },
                            rowReorder: {
                                 enable: true
                                 //update: true,
                                 //selector: 'td'
                             },
                            columnDefs: [
                                { orderable: true, className: 'reorder', targets: 0},
                                { orderable: true, className: 'reorder', targets: 1},
                                /*{ orderable: true, className: 'reorder', targets: 2},
                                { orderable: true, className: 'reorder', targets: 3}*/],
                            language:{url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"}                            
                        });
    
  table.on( 'row-reordered', function ( e, diff, edit ) {
      var data = table.rows().data(); 
      var datos="[";    
                    for ( var i=0; i<data.length; i++ ) {
                        var row = table.rows( i ).data(); 
                        console.log("--> "+row);
                        datos +='{';        
                        datos += '"id":'+row[0][0];
                        datos += ',';
                        datos += '"pk":'+row[0][1];
                        datos += ',';
                        datos += '"placa":'+'"'+row[0][2]+'"';
                        datos += ',';
                        datos += '"num":'+'"'+row[0][3]+'"';
                        datos += '},';        
                    }    
           datos += "]";     
           datos=datos.replace("},]", "}]");    
           
           var objeto = JSON.parse(datos);        
           var datosOrdenados = objeto.sort(function(obj1, obj2) {return obj1.id - obj2.id;});           
           var myJSON = JSON.stringify(datosOrdenados);
           console.log( myJSON );
           $('#lst_vh').val(myJSON);      
      });
      
     

});/*FIN DOCUMENT READY*/
   
   function cargarCombo(dato)
   {console.log("entra cargarcombo");
       if (typeof dato === 'undefined' || dato === null || dato === ''){}
       else{
           $("#company option[value="+dato+"]").attr("selected",true);
       }
   }
   
   function aplicaTiempos(dato){
       console.log("entra aplicar tiempo");
        if (typeof dato === 'undefined' || dato === null || dato === ''){}
       else{
           if(dato === 1){
            $('#aplica1').bootstrapToggle('on');
        }else{
            $('#aplica1').bootstrapToggle('off');
        }/*FIN ELSE VERIFICA SI APARECE O NO LA CAJA*/
       }/*FIN ELSE VERIFICA VARIABLE*/
       
   }