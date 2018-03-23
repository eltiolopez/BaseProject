<jsp:root xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:spring="http://www.springframework.org/tags" xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
  <jsp:output omit-xml-declaration="yes" />

  <jsp:directive.attribute name="theme" type="java.lang.String" required="true" rtexprvalue="true" description="The name for the theme to be added." />
  <jsp:directive.attribute name="target" type="java.lang.String" required="true" rtexprvalue="true" description="The target utl." />
  <jsp:directive.attribute name="label" type="java.lang.String" required="true" rtexprvalue="true" description="The country label for the language to be added." />
  <jsp:directive.attribute name="render" type="java.lang.Boolean" required="false" rtexprvalue="true" description="Indicate if the contents of this tag and all enclosed tags should be rendered (default 'true')" />

  <c:if test="${empty render or render}">
    <spring:url var="url" value="${target}">
      <spring:param name="theme" value="${theme}" />
    </spring:url>
    <a href="${url}">${label}</a>
  </c:if>
</jsp:root>
