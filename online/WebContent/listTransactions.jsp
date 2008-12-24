<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--  include header -->
<%@include file="theme/header.jsp"%>

<%@page import="net.sourceforge.happybank.model.*" session="false"%>
<%@page import="java.util.Locale" session="false"%>
<%@page import="java.util.Currency;" session="false"%>
<%
    Locale currentLocale = Locale.getDefault();
    Currency currentCurrency = Currency.getInstance(currentLocale);
    String currencySymbol = currentCurrency.getSymbol();
	TransRecord[] transactionSet = (TransRecord[]) request
			.getAttribute("transactionSet");
%>

<!-- begin content -->
<div id="content">
<ul id="sidebar">
	<li>
	<h3>Transactions</h3>
	</li>
	<li>&nbsp;</li>
	<li><a href="ListAccounts">Accounts</a></li>
	<li><a href="tbd.jsp">Query</a></li>
	<li><a href="tbd.jsp">Tutorial</a></li>
	<li>&nbsp;</li>
    <li><a href="LogoutSession">Logout</a></li>
</ul>

<div class="main">
<div class="content">
<h1>Account Transactions</h1>
<form method="post" action="AccountDetails">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
	<tbody>
		<tr>
			<td>
			<p align="justify">Transactions sorted in order of occurence:</p>
			</td>
		</tr>
		<tr>
			<td>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#6098c8" valign="top">
					<td align="center"><font color="white"><b>ID</b></font></td>
					<td align="center"><font color="white"><b>Date</b></font></td>
					<td align="center"><font color="white"><b>Type</b></font></td>
					<td align="right"><font color="white"><b>Amount</b></font></td>
				</tr>
				<%
					String[] colors = { "#eeeeee", "#dddddd" };

					for (int count = 0; count < transactionSet.length; count++) {
						TransRecord transaction = transactionSet[count];
				%>
				<tr bgcolor=<%=colors[count%2]%>>
					<td align="center"><a href="tbd.jsp"><%=count + 1%></a></td>
					<td align="center"><%=transaction.getTimeStamp().getTime()%></td>
					<td align="center"><%=transaction.getTransType()%></td>
					<td align="right"><%=currencySymbol%><%=transaction.getTransAmt()%></td>
				</tr>
				<%
					//count ++;
					}
				%>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3"><br />
			<br />
			<input alt="Continue" type="submit" name="continue"
				value="Back"></td>
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
