/*$(document).ready(
	function() {
		var loginError = ${error};
		if(loginError) {
			//$('#myModal').modal({keyboard: true})
			$('#myModal').modal('show');
		}				
	}
);*/


/*$(function() {
   //  Submit form using Ajax
   $('#ajaxbtn').attr("onclick",function(e) {
   
      //Prevent default submission of form
      //e.preventDefault();
      
      //Remove all errors
      //$('input').next().remove();
      
      $.post({
         url : actionurl,
         data : $('form[name=loginForm]').serialize(),
         success : function(res) {
        	 alert("OK");
            if(res.validated){
               //Set response
               alert("Validado");
            
            }else{
              //Set error messages
              $.each(res.errorMessages,function(key,value){
            	  alert("Error: " + value);
  	            //$('input[name='+key+']').after('<span class="error">'+value+'</span>');
              });
            }
         }
      })
   });
});*/


$(function() {
   /*  Submit form using Ajax */
   $('#loginForm').submit(function( event ) {
	   
	   // Stop form from submitting normally
	   event.preventDefault();
	  
	   // Get some values from elements on the page:
	   var $form = $( this ),
	     term = $form.find( "input[name='username']" ).val(),
	     pass = $form.find( "input[name='password']" ).val(),
	     url = $form.attr( "action" );
	  
	   // Send the data using post
	   var posting = $.post( url, { username: term, password : pass } );
	   
	   alert(posting);
	  
	   // Put the results in a div
	   
	 });
});

