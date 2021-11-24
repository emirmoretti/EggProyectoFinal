$(document).ready(function(){

	$('#btn-menu').click(function(){

		if( $('.btn-menu span').attr('class') == 'fas fa-bars' ){

			$('.btn-menu span').removeClass('fas fa-bars').addClass('fas fa-times').css({'color':'#000'});
			$('.full-menu').css({'left':'0'});

		}else{
			$('.btn-menu span').removeClass('fas fa-times').addClass('fas fa-bars').css({'color':'#000'});
			$('.full-menu').css({'left':'-100%'});
		}

	});

});