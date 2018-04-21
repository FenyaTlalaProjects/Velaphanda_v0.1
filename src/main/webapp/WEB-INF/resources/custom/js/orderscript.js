/**
 * Purpose: Interact with the html pages and create communication between server
 * and client side 
 * Author : Fenya Tlala 
 * Client: Velaphanda
 * Created Date:21-01-2018
 * Last Date Modified:21-01-2018
 */

//Order available head office stock-->
            
    var partNumberList = [];
    var quantityList = [];
    //move selected line items to table 2
    $('#stockForOrder').on('click', '.addLineItem', function() {
    
       var quantity;
       row = $(this).closest("tr").clone(); 
       quantity = $(this).closest('tr').find('td:eq(4)').find('input').val();
       console.log("Check the grapped quantity on table of Selected Line Items to Order : ",quantity);
      
       if(quantity == '' || quantity == 0){
           alert("Quantity can not be zero.\n Please enter quantity which is less than available quantity"); 
       }      
       if (quantity > 0){
            var items = [];
            row = $(this).closest("tr").clone();
            var partNumber = $(this).closest('tr').find('td:eq(0)').text();
            var quantity = $(this).closest('tr').find('td:eq(4)').find('input').val();
            
            document.getElementById("quantityList").value = quantityList;
            document.getElementById("partNumberList").value = partNumberList;
                                    
            items.push(row);
            row.appendTo($("#toOrder"));
            //debugger;
            $(this).closest('tr').remove();
            $('input[type="button"]', row).removeClass('AddNew').addClass('RemoveRow').val('Remove');
        }
     });
    //remove selected line items from table 1 to table 2
    $('#toOrder').on('click', '.RemoveRow', function(){
    	 //debugger;
    	row = $(this).closest("tr").clone();
        row.appendTo($("#stockForOrder"));
         $(this).closest('tr').remove();
        $('input[type="button"]', row).removeClass('RemoveRow').addClass('addLineItem').val('Add');
         //hide the all of the element class oderSumbmit
        
    });
     
    //send selected items when user clicks submit button
     $('#putorder').on('click', function(){
           var row;
           $('#toOrder tr').each(function(row, tr){
             partNumberList[row]=[$(tr).find('td:eq(0)').text()];
             quantityList[row]=[$(this).closest('tr').find('td:eq(4)').find('input').val()];
      }); 
      partNumberList.shift();
      quantityList.shift();
      document.getElementById("quantityList").value = quantityList;
      document.getElementById("partNumberList").value = partNumberList;
    });
     
     $(document).ready(function(){
    	    $('.orderSubmit').attr('disabled',true);
    	    
    	    $('input[name$=quantity]').keyup(function(){
    	        if($(this).val().length !=0){
    	            $('.orderSubmit').attr('disabled', false);
    	        }
    	        else
    	        {
    	            $('.orderSubmit').attr('disabled', true);        
    	        }
    	    })
    	});	
			