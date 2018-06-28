$(document).ready(function () {

         var table = $('#example').DataTable({
        	 responsive: true,
             "order": [[1, 'asc']],
          });

         // Add event listener for opening and closing details
         $('#example tbody').on('click', 'td.details-control', function () {
             var tr = $(this).closest('tr');
             var tdi = tr.find("i.fa");
             var row = table.row(tr);

             if (row.child.isShown()) {
                 // This row is already open - close it
                 row.child.hide();
                 tr.removeClass('shown');
                
             }
             else {
                 // Open this row
                 row.child(format(row.data())).show();
                 tr.addClass('shown');
                
             }
         });

         table.on("user-select", function (e, dt, type, cell, originalEvent) {
             if ($(cell.node()).hasClass("details-control")) {
                 e.preventDefault();
             }
         });
     });

    function format(d){
        
         // `d` is the original data object for the row
         return '<table  border="0">' +
             '<tr>' +
                 '<td>Updated By:</td>' +
                 '<td>' + "Patra" + '</td>' +
             '</tr>' +
             '<tr>' +
                 '<td>Last Date Updated:</td>' +
                 '<td>' + "26-06-2018 15:31:39" + '</td>' +
             '</tr>' +
             '<tr>' +
                 '<td>Date Captured:</td>' +
                 '<td>' +"21-03-2018 12:21:34"+ '</td>' +
             '</tr>' +
         '</table>';  
    }

  