var options = {
	responsive : {
		details : true
	},
	bPaginate : true,
	bLengthChange : true,
	bFilter : true,
	bInfo : true,
	bSearch : true,
	"order": [[ 0, 'asc' ], [ 1, 'asc' ]]
};

$(document).ready(function() {
	var table = $('#customerHistory').DataTable(options);
	var table = $('#customerHistoryDetails').DataTable(options);
	$('#customerHistory tbody').on('click', 'td.details-control', function() {
	/*	var tr = $(this).closest('tr');
		var row = table.row(tr);
		
		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(customerHistory()).show();
			$('#customerHistoryDetails').DataTable(options);
			tr.addClass('shown');
		}*/
	});
		
});


$(document).ready(function() {
	var table = $('#deviceHistory').DataTable(options);
	var table = $('#deviceHistoryDetails').DataTable(options);
	$('#deviceHistory tbody').on('click', 'td.details-control', function() {
	/*	var tr = $(this).closest('tr');
		var row = table.row(tr);

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(deviceHistory()).show();
			$('#deviceHistoryDetails').DataTable(options);

			tr.addClass('shown');
		}*/
	});
});
$(document).ready(function() {
	var table = $('#spareHeadOfficeHistory').DataTable(options);
	$('#spareHeadOfficeHistory tbody').on('click', 'td.details-control', function() {
		var tr = $(this).closest('tr');
		var row = table.row(tr);

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			//row.child(spareHeadOfficeHistory()).show();
			//$('#spareHeadOfficeHistoryDetails').DataTable(options);

			tr.addClass('shown');
		}
	});
});


$(document).ready(function() {
	var table = $('#spareHOHistoryDetails').DataTable(options);
});

$(document).ready(function() {
	var table = $('#stockOnSite').DataTable(options);
	$('#stockOnSite tbody').on('click', 'td.details-control', function() {
		var tr = $(this).closest('tr');
		var row = table.row(tr);

		if (row.child.isShown()) {
			// This row is already open - close it
			row.child.hide();
			tr.removeClass('shown');
		} else {
			// Open this row
			row.child(siteStockMovementHistory()).show();
			$('#siteStockMovementHistoryDetails').DataTable(options);

			tr.addClass('shown');
		}
	});
});


