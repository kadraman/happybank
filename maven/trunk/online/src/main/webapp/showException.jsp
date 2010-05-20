<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="message" type="java.lang.String" scope="request"></jsp:useBean>
<jsp:useBean id="forward" type="java.lang.String" scope="request"></jsp:useBean>

<!--  include header -->
<%@include file="theme/header.jsp"%>

<!-- begin content -->
<div id="content">
<ul id="sidebar">
	<li>
	<h3>Error Page</h3>
	</li>
	<li><a href="tbd.jsp">FAQ</a></li>
	<li><a href="tbd.jsp">Tutorial</a></li>
</ul>

<div class="main">
<div class="content">
<h1>Error</h1>
<form method="post" action="<%=forward%>">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
	<tbody>
		<tr>
			<td width="100%">
			<p align="justify">We regret to inform you but there has been an
			error in completing your transaction. Please call us on +44 207
			234234 and inform us of the message that is described below:</p>
			</td>
		</tr>
		<tr>
			<td>
			<p align="justify"><i><%=message%></i></p>
			<br />
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<input alt="Continue" name="continue" 
				value="continue" type="submit" /></td>
		</tr>
	</tbody>
</table>
</form>


</div>
</div>
<br class="clr">
</div>
<!-- end content -->

<%@include file="theme/footer.jsp"%>
