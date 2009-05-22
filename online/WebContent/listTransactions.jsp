<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--  include header -->
<%@include file="theme/header.jsp"%>

<%@page import="net.sourceforge.happybank.model.*" session="false"%>
<%@page import="java.util.Locale" session="false"%>
<%@page import="java.util.Currency" session="false"%>
<%@page import="java.math.BigDecimal" session="false"%>
<%@page import="java.util.ArrayList" session="false"%>
<%@page import="org.joda.time.format.DateTimeFormat" session="false"%>
<%@page import="org.joda.time.format.DateTimeFormatter" session="false"%>
<%
    Locale currentLocale = Locale.getDefault();
    Currency currentCurrency = Currency.getInstance(currentLocale);
    String currencySymbol = currentCurrency.getSymbol();
    @SuppressWarnings("unchecked")
    ArrayList<TransRecord> transactionSet = (ArrayList<TransRecord>) request
            .getAttribute("transactionSet");
    DateTimeFormatter fmt = DateTimeFormat.forPattern("E MMMM-dd, yyyy HH:mm:ss a");
%>

<jsp:useBean id="account" type="net.sourceforge.happybank.model.Account"
    scope="request"></jsp:useBean>
    
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
<h1>Account Transactions for: <%=account.getId()%></h1>
<form method="post" action="AccountDetails">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
	<tbody>
		<tr>
			<td>
			<p align="justify">Transactions sorted in order of occurence (latest first):</p>
			</td>
		</tr>
		<tr>
			<td>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr bgcolor="#6098c8" valign="top">
					<td align="center"><font color="white"><b>Date</b></font></td>
					<td align="center"><font color="white"><b>Description</b></font></td>
					<td align="center"><font color="white"><b>Credit</b></font></td>
					<td align="center"><font color="white"><b>Debit</b></font></td>
				</tr>
				<%
				    BigDecimal totalCredit = new BigDecimal(0.0D);
				    BigDecimal totalDebit = new BigDecimal(0.0D);
				    String[] colors = { "#eeeeee", "#dddddd" };
				    
				    if (transactionSet.size() == 0) {
				%>
				    <tr><td colspan="4">No transactions found</td></tr>
				<%
				        
				    } else {
				        for (int count = 0; count < transactionSet.size(); count++) {
				            TransRecord transaction = (TransRecord) transactionSet
				                .get(count);
				%>
				    <tr bgcolor=<%=colors[count % 2]%>>					
					    <td align="center" style="padding-left:5px"><%=fmt.print(transaction.getTimeStamp())%></td>
					    <td align="center"></td>
					    <% if (transaction.getTransType().equals("C")) { 
					           totalCredit = totalCredit.add(transaction.getTransAmt());
					    %>
					        <td align="right"><%=currencySymbol%><%=transaction.getTransAmt()%></td>
					        <td></td>
					    <% } else { 
					        totalDebit = totalDebit.add(transaction.getTransAmt());
					    %>
					        <td></td>
                            <td align="right" style="padding-right:5px"><%=currencySymbol%><%=transaction.getTransAmt()%></td>
					    <% } %>
				    </tr>
				    <%
				        }
				    %>
				    <tr bgcolor="white" valign="top">
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td align="right"><font color="black"><b><%=currencySymbol%><%=totalCredit%></b></font></td>
                        <td align="right"><font color="black"><b><%=currencySymbol%><%=totalDebit%></b></font></td>
                    </tr>
                <%
                    }
                %>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<input alt="Continue" type="submit" name="continue" value="Back"></td>
		</tr>
	</tbody>
</table>
</form>
</div>
</div>
</div>
<!-- end content -->

<%@include file="theme/footer.jsp"%>
