function body_append_data(data)
{
	$('body').append(data);
}
var big_search_top = 0;
var big_search_left = 0;
function ajax_search(obj) {
	var q = obj.value;
	var big_search_top = getOffset(obj).top + 40;
	var big_search_left = getOffset(obj).left + 2;
	$.post(
		"http://pokerenergy.ru/ajax-search.php",
		{q: q},
		function(data) {
			if(data != '') {
				$('#ajax-search-result').html(data);
				$('#ajax-search-result').css('top', big_search_top);
				$('#ajax-search-result').css('left', big_search_left);
				$('#ajax-search-result').show();
			} else {
				$('#ajax-search-result').hide();
			}
		},
		"html"
	);
}

function show_message(message)
{
	alert(message);
}

var prev_banner = 1;
var banner_hover = 0;

$(document).ready(
function()
{
	$('#banner').hover
	(
		function() {banner_hover = 1},
		function() {banner_hover = 0}
	);
	setInterval("banner_rotate()", 4000);
});
function banner_change(number) {
	if(prev_banner != number) {
		$("#bannernav"+prev_banner).css('background', 'url(http://pokerenergy.ru/css/banner-buttons.png) -12px 0 no-repeat');
		$("#bannernav"+number).css('background', 'url(http://pokerenergy.ru/css/banner-buttons.png) 0 0 no-repeat');

		$("#banner"+prev_banner).fadeOut(250);
		$("#banner"+number).delay(240).fadeIn(250);

		prev_banner = number;
	}
}
function banner_rotate() {
	if(banner_hover == 0) {
		if(prev_banner == 4) {
			banner_change(1);
		} else {
			banner_change(prev_banner + 1);
		}
	}
}
function get_forum_massage(page) {
	$.post(
		"http://pokerenergy.ru/blocks/forum.php",
		{ajax: 1, action: "get_forum_massage", page: page},
		function(data) {
			document.getElementById('forum-last-content').innerHTML = data;
		},
		"html"
	);
}
var menu_open = 0;
var menu_blocker_hover = 0;
function hide_menu_blocker() {
	if(menu_blocker_hover == 1) {
		$("#menu-blocker").hide();
	}
}

$(document).ready(function() {
	var menuLeft = getOffset(document.getElementById('menu')).left;
	var menuTop = getOffset(document.getElementById('menu')).top;
	$("#menu-blocker").css('top', menuTop);
	$("#menu-blocker").css('left', menuLeft);
	$("#menu-blocker").show();
	$("a.with-submenu-a, ul.submenu").hover(
		function () {
			var menu_id = this.id;
			menu_id = menu_id.replace('sub','');
			var submenu_id = 'sub'+menu_id+'';
			document.getElementById(submenu_id).style.display = 'block';
			document.getElementById(menu_id).style.background = '#2c2c2a url(http://pokerenergy.ru/css/menu-arrow-hover.png) 15px 10px no-repeat';
			document.getElementById(menu_id).style.color = '#ffffff';
			document.getElementById(menu_id).style.textDecoration = 'underline';
			document.getElementById('menu').style.background = '#2c2c2a url(http://pokerenergy.ru/css/menu-bg.png) repeat-x';

		},
		function () {
			var menu_id = this.id;
			menu_id = menu_id.replace('sub','');
			var submenu_id = 'sub'+menu_id+'';
			document.getElementById(submenu_id).style.display = 'none';
			document.getElementById(menu_id).style.background = 'url(http://pokerenergy.ru/css/menu-arrow.png) 15px 10px no-repeat';
			document.getElementById(menu_id).style.color = '#544e38';
			document.getElementById(menu_id).style.textDecoration = 'none';
			document.getElementById('menu').style.background = '#ffffff url(http://pokerenergy.ru/css/menu-bg.png) repeat-x';
		}
	);
	$("#menu").hover(
		function () {},
		function () {$("#menu-blocker").show();}
	);
});

function version_change(obj, price, discount, before, after)
{
	if(discount == 0)
		$("#product_page .product_page_right .buy .price .price_old").hide();
	else
		$("#product_page .product_page_right .buy .price .price_old").show();

	newprice = price - discount;

	$("#product_page .product_page_right .buy .price .price_new").html(before + newprice + after);
	$("#product_page .product_page_right .buy .price .price_old").html(before + price + after);

	$("#product_page .product_page_right .buy .versions .selected").removeClass("selected");

	var index = $(obj).closest(".versions").find(".version").index( $(obj).parent() );

	$("#product_page .product_page_right .buy .versions").find(".version:eq(" + index + ")").addClass("selected").find("input[type=radio]").attr("checked", "checked");
}
function news_page_content_load(year) {
	$.post(
		"http://pokerenergy.ru/blocks/news-for-newspage.php",
		{year: year},
		function(data){
			$('#newspage-news-block').html(data);
		},
		"html"
	);
}
function mainpage_news_load(page) {
	$.post(
		"http://pokerenergy.ru/blocks/news-for-main.php",
		{page: page},
		function(data){
			$('#mainpage-news-block').html(data);
		},
		"html"
	);
}
$(document).ready(function() {
	$('#register-form').submit(function()
	{
		$(this).ajaxSubmit({dataType: 'html', success: function(data) { $('body').append(data); } });
		return false;
	});

	$('#login-form').submit(function()
	{
		$(this).ajaxSubmit({dataType: 'html', success: function(data) { $('body').append(data); } });
		return false;
	});

	$('#checkout_form').submit(function()
	{
		$(this).ajaxSubmit({dataType: 'html', success: function(data) { $('body').append(data); } });
		return false;
	});

	$('#message').click(function() {
		$('#message').fadeOut('fast');
	});
});

function get_user_address_edit_form() {
	$.ajax({
		url: 'http://pokerenergy.ru/blocks/user-address-edit-form.php',
		dataType: "html",
		success: function(data) {
			$('#user-page-physical-address').html(data);
		}
	});
}
function save_user_address_result(data) {
	$('#user-page-physical-address').html(data);
}
function get_user_email_edit_form()
{
	$.ajax({ url: 'http://pokerenergy.ru/actions/user-email-edit.php', success: function(data) { $('#user-page-email').html(data); } });
}
function save_user_email(email)
{
	$.post('http://pokerenergy.ru/actions/save-user-email.php', {email: email}, function(data) { $('body').append(data); });
}

function load_updated_cart(obj, cart)
{
   	var obj_value = Math.abs(parseInt(obj.value));

	if(isNaN(obj_value))
		obj_value = 0;

	if(obj_value == 0)
		if(!confirm('Удалить этот товар из корзины?'))
			return false;

	$.post("/actions/edit-cart.php", {cart: cart, new_amount: obj_value}, function(data) { $("body").append(data) } );

	return false;
}
function delete_cart(cart)
{
	if(confirm('Удалить товар из корзины?'))
		$.post("/actions/edit-cart.php", {cart: cart, new_amount: 0}, function(data) { $("body").append(data) } );

	return false;
}
function is_email(email)
{
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	return reg.test(email);
}
function is_url(url)
{
	 return url.match(/^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/);
}
function sent_proposal()
{
	var is_error = 0;
	if(!is_email($("#sent-proposal-email").val())) {
		$("#sent-proposal-email").animate( { color: '#900' }, 500).delay(1000).animate( { color: '#fff' }, 500);
		is_error++;
	}
	if(!is_url($("#sent-proposal-url").val())) {
		$("#sent-proposal-url").animate( { color: '#900' }, 500).delay(1000).animate( { color: '#fff' }, 500);
		is_error++;
	}
	if($("#sent-proposal-text").val() == '' || $("#sent-proposal-text").val() == 'Ваше предложение') {
		$("#sent-proposal-text").animate( { color: '#900' }, 500).delay(1000).animate( { color: '#fff' }, 500);
		is_error++;
	}
	if(is_error == 0) {
		$('#sent-proposal-form').ajaxSubmit({dataType: 'html', success: sent_proposal_result });
	}
}
function sent_proposal_result(data)
{
		$('#message').html(data + '<p><img src="http://pokerenergy.ru/css/close-button.png" alt="Закрыть" /></p>');
		$('#message').fadeIn('fast');
}
function sent_message() {
	var is_error = 0;
	if(!is_email($("#sent-message-email").val())) {
		$("#sent-message-email").animate( { color: '#900' }, 500).delay(1000).animate( { color: '#fff' }, 500);
		is_error++;
	}
	if($("#sent-message-text").val() == '' || $("#sent-message-text").val() == 'Само сообщение') {
		$("#sent-message-text").animate( { color: '#900' }, 500).delay(1000).animate( { color: '#fff' }, 500);
		is_error++;
	}
	if($("#sent-message-name").val() == '' || $("#sent-message-name").val() == 'Ваше имя') {
		$("#sent-message-name").animate( { color: '#900' }, 500).delay(1000).animate( { color: '#fff' }, 500);
		is_error++;
	}
	if(is_error == 0) {
		$.post(
			"http://pokerenergy.ru/actions/message.php",
			{
				name: $("#sent-message-name").val(),
				email: $("#sent-message-email").val(),
				text: $("#sent-message-text").val()
			},
			function(data){
				$('#message').html(data + '<p><img src="http://pokerenergy.ru/css/close-button.png" alt="Закрыть" /></p>');
				$('#sent-massege').fadeOut('fast');
				$('#message').fadeIn('fast');
			}
		);
	}
}
function showstars(starnumber, obj) {
	for(var i = 0; i < 5; i++) {
		obj.parentNode.childNodes[i].src = 'http://pokerenergy.ru/css/star.png';
	}
	starnumber++;
	for(var i = starnumber; i < 5; i++) {
		obj.parentNode.childNodes[i].src = 'http://pokerenergy.ru/css/nostar.png';
	}
}
function set_rating(value, product_id) {
	$.post(
		"http://pokerenergy.ru/actions/set-rating.php",
		{value: value, product_id: product_id},
		function(data){
			$('.rating-stars').html(data);
		}
	);
}
function show_screenshot(number)
{
	$('#screenshot-view-img img').fadeOut(100);
	$('#screenshot-view-load').css('top', (screenSize()['h']/2) - 20);
	$('#screenshot-view-load').css('left', (screenSize()['w']/2));
	$('#screenshot-view-load').fadeIn(200);

	var img = new Image();
	var new_src = $("#gallery a:eq(" + number + ")").attr("href");
	img.onload = function()
	{
		$('#screenshot-view-load').hide();
		$('#screenshot-view-bg').fadeIn(200);
		var img_width = img.width;
		if(img.height > (screenSize()['h'] - 40 - 30 - 44 - 26 - 20)) {
			var img_height = (screenSize()['h'] - 40 - 30 - 44 - 26 - 20);
			var img_width = (img_height * img.width)/img.height;
		} else {
			var img_height = img.height;
		}
		if(img_width < 500) {img_width = 500;}
		var x = Math.round(screenSize()['w'] / 2);
		x = x - Math.round(img_width / 2) - 20;
		var y = Math.round(screenSize()['h'] / 2);
		y = y - Math.round(img_height / 2) - 20 - 15 - 22 - 13;
		$('#screenshot-view-img img').animate({'height': img_height}, 300);
		$('#screenshot-view-img').animate({'width': img_width, 'height': img_height}, 300);
		$('#screenshot-view-img img').attr('src', new_src);
		$('#screenshot-view-body').animate({'top': y, 'left': x}, 300);
		$('#screenshot-view-body').fadeIn(200);
		$('#screenshot-view-img img').fadeIn(0);
		$('#screenshot-view-title').html('Скриншот '+(number + 1)+'/'+ $("#gallery a").length);
		$('#screenshot-view-num').html((number + 1)+'/'+$("#gallery a").length);
		if(number > 0) {
			$('#screenshot-view-prev').css('display', 'block');
			document.getElementById('screenshot-view-prev').onclick = function() {show_screenshot(number-1);};
		} else {
			$('#screenshot-view-prev').css('display', 'none');
		}
		if(number < ($("#gallery a").length - 1)) {
			$('#screenshot-view-next').css('display', 'block');
			document.getElementById('screenshot-view-next').onclick = function() {show_screenshot(number+1);};
		} else {
				$('#screenshot-view-next').css('display', 'none');
		}
	}
	img.src = new_src;
}
function hide_screenshot()
{
	$('#screenshot-view-bg').fadeOut(200);
	$('#screenshot-view-body').fadeOut(200);
}
function product_video_show()
{
	$('#product_video')
		.fadeIn(300, function()
		{
			// Опера не сразу получает размеры видео. Делаем позиционирование только после полной его загрузки.
			$('#product_video .content')
				.css('margin-top', -($('#product_video .content').outerHeight() / 2))
				.css('margin-left', -($('#product_video .content').outerWidth() / 2));
		});

	$('#product_video .content')
		.css('margin-top', -($('#product_video .content').outerHeight() / 2))
		.css('margin-left', -($('#product_video .content').outerWidth() / 2));
}
function product_video_hide()
{
	$('#product_video').css("display", "none");
}
function screenSize() {
	var browserWinWidth = 0, browserWinHeight = 0;
	if( typeof( window.innerWidth ) == 'number' ) {
		browserWinWidth = window.innerWidth;
		browserWinHeight = window.innerHeight;
	} else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		browserWinWidth = document.documentElement.clientWidth;
		browserWinHeight = document.documentElement.clientHeight;
	} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		browserWinWidth = document.body.clientWidth;
		browserWinHeight = document.body.clientHeight;
	}
	var sizeArray = new Array(2);
	sizeArray['w'] = browserWinWidth;
	sizeArray['h'] = browserWinHeight;
	return sizeArray;
}

/* Определение позиции элемента */
function getOffset(elem) {
	if (elem.getBoundingClientRect) {
		return getOffsetRect(elem)
	} else {
		return getOffsetSum(elem)
	}
}
function getOffsetSum(elem) {
	var top=0, left=0
	while(elem) {
		top = top + parseInt(elem.offsetTop)
		left = left + parseInt(elem.offsetLeft)
		elem = elem.offsetParent
	}
	return {top: top, left: left}
}
function getOffsetRect(elem) {
	var box = elem.getBoundingClientRect()
	var body = document.body
	var docElem = document.documentElement
	var scrollTop = window.pageYOffset || docElem.scrollTop || body.scrollTop
	var scrollLeft = window.pageXOffset || docElem.scrollLeft || body.scrollLeft
	var clientTop = docElem.clientTop || body.clientTop || 0
	var clientLeft = docElem.clientLeft || body.clientLeft || 0
	var top  = box.top +  scrollTop - clientTop
	var left = box.left + scrollLeft - clientLeft
	return { top: Math.round(top), left: Math.round(left) }
}
/* Конец определения позиции элемента */

/* Положение курсора */
function mouseX(evt)
{
	if (evt.pageX)
		return evt.pageX;
	else if (evt.clientX)
		return evt.clientX + (document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft);
	else
		return null;
}
function mouseY(evt)
{
	if (evt.pageY)
		return evt.pageY;
	else if (evt.clientY)
	   return evt.clientY + (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop);
	else
		return null;
}




function nosleep()
{
	$.ajax(
	{
		type: "GET",
		url: 'http://pokerenergy.ru/'
	});
	$.ajax(
	{
		type: "GET",
		url: 'http://forum.pokerenergy.ru/'
	});
}
setInterval("nosleep()", 300000);