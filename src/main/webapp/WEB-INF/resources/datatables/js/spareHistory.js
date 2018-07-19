var options = {
	responsive : {
		details : true
	},
	bPaginate : true,
	bLengthChange : true,
	bFilter : true,
	bInfo : true,
	bSearch : true
};
$(document).ready(function() {
	var table = $('#spareHistory').DataTable(options);
	$('#spareHistory tbody').on('click', 'td.details-control', function() {
		var tr = $(this).closest('tr');
		var row = table.row(tr);

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(spareHistory()).show();
			$('#spareHistoryDetails').DataTable(options);

			tr.addClass('shown');
		}
	});
});