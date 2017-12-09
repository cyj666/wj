sidebarMenu = function(menu) {
  //滑动的速度
  var animationSpeed = 300;
  
  $(menu).on('click', 'li a', function(e) {
  	//取到当前对象
    var $this = $(this);
    //取到下一级菜单
    var checkElement = $this.next();
	//选择器，visible：匹配可见的
    if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
      //以滑动的方式隐藏下一级菜单
      checkElement.slideUp(animationSpeed, function() {
        checkElement.removeClass('menu-open');
      });
      checkElement.parent("li").removeClass("active");
    }

    //If the menu is not visible
    else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
      //Get the parent menu
      var parent = $this.parents('ul').first();
      //Close all open menus within the parent
      var ul = parent.find('ul:visible').slideUp(animationSpeed);
      //Remove the menu-open class from the parent
      ul.removeClass('menu-open');
      //Get the parent li
      var parent_li = $this.parent("li");

      //Open the target menu and add the menu-open class
      checkElement.slideDown(animationSpeed, function() {
        //Add the class active to the parent li
        checkElement.addClass('menu-open');
        parent.find('li.active').removeClass('active');
        parent_li.addClass('active');
      });
    }
    //if this isn't a link, prevent the page from being redirected
//    if (checkElement.is('.treeview-menu')) {
//      e.preventDefault();
//    }
  });
}
