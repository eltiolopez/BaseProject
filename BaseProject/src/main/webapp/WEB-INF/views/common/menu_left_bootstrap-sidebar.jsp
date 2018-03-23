<%@ taglib prefix="menu" tagdir="/WEB-INF/tags/menu" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	
	<menu:menu-bootstrap>	
		<menu:category-bootstrap id="c_guestbook" icon_name="glyphicon glyphicon-book" z="uoTSf/uIZ821ZppidzS9aO776yk=" >
			<sec:authorize access="isAuthenticated()">
				<menu:item-bootstrap id="i_guestbook_create" icon_name="glyphicon glyphicon-user" messageCode="menu_item_guestbook_create_label" url="/guestbook/create" z="Zv1m8u2rG+JFKcvJ+AHVVK0ey24=" />
			</sec:authorize>
			<menu:item-bootstrap id="i_guestbook_search" icon_name="glyphicon glyphicon-user" messageCode="menu_item_guestbook_search_label" url="/guestbook/search" z="4MY4uybxa60Q1G2rlI5bOwg+sm0=" />
		</menu:category-bootstrap>
			
		<sec:authorize access="isAuthenticated()">
			<menu:category-bootstrap id="c_users" icon_name="glyphicon glyphicon-user" z="uoTSf/uIZ821ZppidzS9aO776yk=" >
				<menu:item-bootstrap id="i_users_create" icon_name="glyphicon glyphicon-user" messageCode="menu_item_users_create_label" url="/users/create" z="Zv1m8u2rG+JFKcvJ+AHVVK0ey24=" />
				<menu:item-bootstrap id="i_users_search" icon_name="glyphicon glyphicon-user" messageCode="menu_item_users_search_label" url="/users/search" z="4MY4uybxa60Q1G2rlI5bOwg+sm0=" />
			</menu:category-bootstrap>
		</sec:authorize>
		
		<menu:item-bootstrap id="i_pictures_portfolio" icon_name="glyphicon glyphicon-user" messageCode="menu_item_pictures_portfolio_label" url="/pictures" z="Zv1m8u2rG+JFKcvJ+AHVVK0ey24=" />
		
		<menu:category-bootstrap id="c_author" icon_name="glyphicon glyphicon-user" z="uoTSf/uIZ821ZppidzS9aO776yk=" >
			<menu:item-bootstrap id="i_author_about" icon_name="glyphicon glyphicon-user" messageCode="menu_item_author_about_label" url="/author/about" z="Zv1m8u2rG+JFKcvJ+AHVVK0ey24=" />
			<menu:item-bootstrap id="i_author_contact" icon_name="glyphicon glyphicon-user" messageCode="menu_item_author_contact_label" url="/author/contact" z="4MY4uybxa60Q1G2rlI5bOwg+sm0=" />
		</menu:category-bootstrap>
	</menu:menu-bootstrap>
