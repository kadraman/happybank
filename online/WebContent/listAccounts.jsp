<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--  include header -->
<%@include file="theme/header.jsp"%>

<%@page import="net.sourceforge.happybank.model.*" session="false"%>
<%@page import="java.math.BigDecimal" session="false"%>
<%@page import="java.util.Locale" session="false"%>
<%@page import="java.util.Currency" session="false"%>
<%@page import="java.util.ArrayList" session="false"%>
<%
    Locale currentLocale = Locale.getDefault();
    Currency currentCurrency = Currency.getInstance(currentLocale);
    String currencySymbol = currentCurrency.getSymbol();
    ArrayList accounts = (ArrayList) request.getAttribute("accounts");
%>

<!-- begin content -->
<div id="content">
<ul id="sidebar">
	<li>
	<h3>Accounts</h3>
	</li>
	<li>&nbsp;</li>
	<li><a href="tbd.jsp">Open account</a></li>
	<li><a href="tbd.jsp">Tutorial</a></li>
	<li>&nbsp;</li>
	<li><a href="LogoutSession">Logout</a></li>
</ul>

<div class="main">
<div class="content">
<h1><jsp:useBean id="customer" scope="request"
	class="net.sourceforge.happybank.model.Customer" /> <jsp:getProperty
	name="customer" property="title" /> <jsp:getProperty name="customer"
	property="firstName" /> <jsp:getProperty name="customer"
	property="lastName" />'s accounts</h1>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
	<tbody>
		<tr>
			<td width="100%">
			<p align="justify">Please, select the account you would like to
			work with:</p>
			</td>
		<tr>
			<td>
			<table border="0" cellpadding="2" cellspacing="1" width="90%">
				<tbody>
					<tr bgcolor="#6098c8" valign="top">
						<td align="left"><font color="white"><b>ID</b></font></td>
						<td align="center"><font color="white"><b>Type</b></font></td>
						<td align="center"><font color="white"><b>Balance</b></font></td>
					</tr>
					<%
					    String[] colors = { "#eeeeee", "#dddddd" };
					    BigDecimal totalBalance = new BigDecimal(0.0D);
					    for (int count = 0; count < accounts.size(); count++) {
					        Account account = (Account) accounts.get(count);
					        totalBalance = totalBalance.add(account.getBalance());
					%>
					<tr bgcolor=<%=colors[count % 2]%>>
						<td align=left><a
							href="AccountDetails?accountNumber=<%=account.getId()%>"><%=account.getId()%></a>
						</td>
						<td align="center"><%=account.getType()%></td>
						<td align="center"><%=currencySymbol%><%=account.getBalance()%></td>
					</tr>
					<%
					    }
					%>
					<tr bgcolor="white" valign="top">
						<td>&nbsp;</td>
						<td align="right"><font color="black"><b>Total
						Balance:</b></font></td>
						<td align="center"><font color="black"><b><%=currencySymbol%><%=totalBalance%></b></font></td>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>
<br class="clr">
</div>
<!-- end content -->

<%@include file="theme/footer.jsp"%>
