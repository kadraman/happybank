<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--  include header -->
<%@include file="theme/header.jsp"%>

<%@page import="java.util.Locale" session="false"%>
<%@page import="java.util.Currency;" session="false"%>

<script language="JavaScript">
    // Set whether specific fields are editable or not
    function setFormState() {
        // The amount input state maintenance
        if (document.forms.transactions.elements.transaction[0].checked == false) {
            document.forms.transactions.elements.amount.disabled = false;
            document.forms.transactions.elements.amount.value = "";
        } else {
            document.forms.transactions.elements.amount.disabled = true;
            document.forms.transactions.elements.amount.value = "N/A";
        }

        // The destination account input state maintenance
        if (document.forms.transactions.elements.transaction[3].checked == true) {
            document.forms.transactions.elements.destinationAccount.disabled = false;
            document.forms.transactions.elements.destinationAccount.value = "";
        } else {
            document.forms.transactions.elements.destinationAccount.disabled = true;
            document.forms.transactions.elements.destinationAccount.value = "N/A";
        }
    }

    function cancel() {
        window.navigate("ListAccounts");
    }
</script>

<jsp:useBean id="account" type="net.sourceforge.happybank.model.Account"
	scope="request"></jsp:useBean>

<!-- begin content -->
<div id="content">
<ul id="sidebar">
	<li>
	<h3>Details</h3>
	</li>
	<li>&nbsp;</li>
	<li><a href="ListAccounts">Accounts</a></li>
	<li><a href="tbd.jsp">Open account</a></li>
	<li><a href="tbd.jsp">Tutorial</a></li>
	<li>&nbsp;</li>
	<li><a href="LogoutSession">Logout</a></li>
</ul>

<div class="main">
<div class="content">
<h1>Account Details</h1>
<%
    Locale currentLocale = Locale.getDefault();
    Currency currentCurrency = Currency.getInstance(currentLocale);
    String currencySymbol = currentCurrency.getSymbol();
%>
<p><b>Current balance: <%=currencySymbol%><%=account.getBalance()%></b></p>
<p><a href="ListTransactions?accountNumber=<%=account.getId()%>">Display
Transactions</a></p>
<p>Transfer Funds:</p>
<form name="transactions" action="TransferFunds" method="post">
	
<table border="0">
	<tbody>
		<tr>
			<td>Amount:</td>
			<td><input type="text" name="amount" size="20" 
				></td>
		</tr>
		<tr>
			<td>Destination account:</td>
			<td><input type="text" name="destinationAccount" size="20"
				></td>
		</tr>
	</tbody>
</table>
<br />
<input alt="Submit" type="submit" name="submit" value="Submit">
</form>
</div>
</div>
<!-- end midcol --> <br class="clr">
</div>
<!-- end content -->

<%@include file="theme/footer.jsp"%>
