<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.attribute name="items" type="java.util.List" required="true" rtexprvalue="true" description="The list of elements to be shown" />
<jsp:directive.attribute name="numColumns" type="java.lang.Integer" required="true" rtexprvalue="true" description="Number of columns in each row" />
<jsp:directive.attribute name="render" type="java.lang.Boolean" required="false" rtexprvalue="true" description="Indicate if the contents of this tag and all enclosed tags should be rendered (default 'true')" />


<c:set var="total" value="${fn:length(items)}" />
<c:set var="counter" value="0" />
<c:set var="calculatedRows" value="${(total + (numColumns-1)) / numColumns}" />
<c:set var="calculatedColumns" value="0" />

<c:forEach begin="1" end="${calculatedRows}" varStatus="rows">
  	<c:choose>
	    <c:when test="${(total - counter) >= numColumns }">
   			<c:set var="calculatedColumns" value="${numColumns}" />
   		</c:when>
   		<c:otherwise>
   			<c:set var="calculatedColumns" value="${(total - counter) % numColumns}" />
   		</c:otherwise>
   	</c:choose>			    		

	<div class="row">
    	<c:forEach begin="1" end="${calculatedColumns}" varStatus="cols">
    		<c:set var="img" value="${resultAndMetainfo.listaResultados[counter]}" />
    		<div class="col-xs-3 col-md-3">
				<a href="#" class="thumbnail">
					<spring:url var="imgPath" value="/pictures/${img.filekey}.${img.fileextension}" />
					<img src="${imgPath}" alt="no-image"/>
				</a>
			</div>
    		<c:set var="counter" value="${counter + 1}" />
    	</c:forEach>
   	</div>
  </c:forEach>