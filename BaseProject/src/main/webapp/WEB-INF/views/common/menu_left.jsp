<%@ taglib prefix="menu" tagdir="/WEB-INF/tags/menu" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="left_column" xmlns:jsp="http://java.sun.com/JSP/Page">
	
	<menu:menu id="menu" z="nZaf43BjUg1iM0v70HJVEsXDopc=">
		<menu:category id="c_guestbook" z="uoTSf/uIZ821ZppidzS9aO776yk=" >
			<sec:authorize access="isAuthenticated()">
				<menu:item id="i_guestbook_create" messageCode="menu_item_guestbook_create_label" url="/guestbook/create" z="Zv1m8u2rG+JFKcvJ+AHVVK0ey24=" />
			</sec:authorize>
			<menu:item id="i_guestbook_search" messageCode="menu_item_guestbook_search_label" url="/guestbook/search" z="4MY4uybxa60Q1G2rlI5bOwg+sm0=" />
		</menu:category>
		
		<sec:authorize access="isAuthenticated()">
			<menu:category id="c_users" z="uoTSf/uIZ821ZppidzS9aO776yk=" >
				<menu:item id="i_users_create" messageCode="menu_item_users_create_label" url="/users/create" z="Zv1m8u2rG+JFKcvJ+AHVVK0ey24=" />
				<menu:item id="i_users_search" messageCode="menu_item_users_search_label" url="/users/search" z="4MY4uybxa60Q1G2rlI5bOwg+sm0=" />
			</menu:category>
		</sec:authorize>
		
		<menu:category id="c_author" z="uoTSf/uIZ821ZppidzS9aO776yk=" >
			<menu:item id="i_author_about" messageCode="menu_item_author_about_label" url="/author/about" z="Zv1m8u2rG+JFKcvJ+AHVVK0ey24=" />
			<menu:item id="i_author_contact" messageCode="menu_item_author_contact_label" url="/author/contact" z="4MY4uybxa60Q1G2rlI5bOwg+sm0=" />
		</menu:category>
	</menu:menu>
	
</div>
