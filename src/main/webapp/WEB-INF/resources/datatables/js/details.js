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
         	 '<c:forEach var="list" items="${displayCustomers}">'+
	         '<tr>' +
	         '<td>Captured By:</td>' +
	         '<td>' + "${list.clientAddedBy}" + '</td>' +
	         '</tr>' +
		     '<tr>' +
		         '<td>DateTime Captured:</td>' +
		         '<td>' + d.dateTimeClientAdded + '</td>' +
		     '</tr>' +
             '<tr>' +
                 '<td>Updated By:</td>' +
                 '<td>' + d.clientUpdatedBy+ '</td>' +
             '</tr>' +
             '<tr>' +
                 '<td>Last DateTime Updated:</td>' +
                 '<td>' + d.timeClientUpdated + '</td>' +
             '</tr>' +
            '</c:forEach>'+
         '</table>';  
    }

  