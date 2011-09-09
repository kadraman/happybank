<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--  include header -->
<%@include file="theme/header.jsp"%>

<%@page import="net.sourceforge.happybank.model.*" session="false"%>
<%@page import="java.util.Locale" session="false"%>
<%@page import="java.util.Currency" session="false"%>
<%@page import="java.util.ArrayList" session="false"%>

<script language="JavaScript">
<!--
window.addEvent('domready', function() {
    
    $('transactionForm').addEvent('submit', function(e) {
        // prevents the default submit event from loading a new page
        new Event(e).stop();
        
        // where we will place the response
        var responseDiv = $('transactionForm').getElement('#response');        
        
        // remove error style from fields
        this.getElements('.error').each(function(input) {
            if (input.hasClass('error')) { 
                input.removeClass('error').morph({ 'border-color': '#ccc', 'background-color': '#fff' }); 
            }
        });
        
        // validate fields  
        if ($('amount').get('value') == "") {
            responseDiv.set('html', 
                "<span class='error'>An <b>amount</b> is required.</span>");
            responseDiv.setStyles({ 'opacity': '0', 'display': 'block' });
            responseDiv.morph({ 'opacity': '1' });
            $("amount").addClass('error').morph({ 'border-color': '#f00', 'background-color': '#ffebe8' });
            $("amount").focus();      
        } else {
        
            // show the waiter when pressing the submit button...
            $('waiting').setStyle('visibility', 'visible');

            // disable the submit button while processing...
            $('submit').set('disabled', true);

            // set the options of the form's Request handler.
            this.set('send', { onComplete: function(response) {
                $('waiting').setStyle('visibility', 'hidden');

                // decode the JSON response
                var status = JSON.decode(response);
                
                // act on the response
                if (status.code) {
                    // successful transfer
                    
                    // enable the submit button
                    $('submit').set('disabled', false);
                    var newBalance = $('balance').get('value') - $('amount').get('value');
                    responseDiv.set('html', 
                            "<span class='success'>The transfer has been initiated and your accounts will be updated shortly.</span>");
                    $('balance').set('value', newBalance);
                } else {
                    // failed transfer
                    
                    // enable the submit button
                    $('submit').set('disabled', false);
                    
                    responseDiv.set('html',
                        "<span class='error'>" + status.message + "</span>");
                    responseDiv.setStyles({ 'opacity': '0', 'display': 'block' });
                    responseDiv.morph({ 'opacity': '1' });
                    if ($(status.field)) {
                        $(status.field).addClass('error').morph({ 'border-color': '#f00', 'background-color': '#ffebe8' });
                        $(status.field).focus();
                    }
                }
            }});

            // send the form.
            this.send();
        }
    });

    // set focus to amount
    $("amount").focus();

});
//-->
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
<h1>Account Details for: <%=account.getId()%></h1>

<%
    Locale currentLocale = Locale.getDefault();
    Currency currentCurrency = Currency.getInstance(currentLocale);
    String currencySymbol = currentCurrency.getSymbol();
    @SuppressWarnings("unchecked")
    ArrayList<Account> accounts = (ArrayList<Account>) request
            .getAttribute("accounts");
%>

<p><b>Current balance: <%=currencySymbol%></b> <input id="balance"
	value="<%=account.getBalance()%>" readonly="readonly" disabled="disabled" ></p>
<p><a href="ListTransactions?accountNumber=<%=account.getId()%>">Display
Transactions</a></p>

<form id="transactionForm" action="TransferFunds" method="post">
<fieldset><legend>Quick Transfer</legend>
<table border="0">
	<tbody>
		<tr>
			<td>Amount:</td>
			<td><input type="text" id="amount" name="amount" size="20"></td>
		</tr>
		<tr>
			<td>Destination account:</td>
			<td><select name="destinationAccount">
				<%
				    for (int count = 0; count < accounts.size(); count++) {
				        Account acc = (Account) accounts.get(count);
				        if (acc.getId().equals(account.getId())) {
				            // ignore
				        } else {
				%>
				<option value="<%=acc.getId()%>"><%=acc.getId()%></option>
				<%
				    }
				    }
				%>
			</select></td>
		</tr>
		<tr>
			<td colspan="2"><!-- ajax submit response -->
			<div id="response"></div>
			</td>
		</tr>
	</tbody>
</table>

<!-- buttons and ajax processing -->
<div><input type="submit" value="Submit" id="submit" class="btn" />
&nbsp; <span id="waiting" style="visibility: hidden"> <img
	align="absmiddle" src="theme/spinner.gif" /> &nbsp;<strong>Processing...<strong></span>
</div>
</fieldset>
</form>
<!--  end content --></div>
</div>
<!-- end main --> <br class="clr">
</div>
<!-- end content -->

<%@include file="theme/footer.jsp"%>