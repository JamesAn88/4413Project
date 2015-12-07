<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <html>
	  <body>
	    <h2>Order Details</h2>
	    <table> 
	      <tr> 
	        <td><label>Order number:</label></td>
	        <td><xsl:value-of select="orderType/@id"/></td>
	      </tr>
	      <tr> 
	        <td><label>Account number:</label></td>
	        <td><xsl:value-of select="orderType/customer/@account"/></td>
	      </tr>
	      <tr> 
	     <td><label>Order date:</label></td>
	     <td><xsl:value-of select="orderType/@submitted"/></td>
	      </tr>
	    </table>
	    
	    <h2>Items </h2>
	    <table border="2">
	      <tr bgcolor="#9acd32">
	        <th style="text-align:center">Item Number</th>
	        <th style="text-align:center">Name</th>
	        <th style="text-align:center">Price</th>
	        <th style="text-align:center">Quantity</th>
	        <th style="text-align:center">Extended</th>
	      </tr>
	      <xsl:for-each select="orderType/items/item">
	        <tr>
	          <td><xsl:value-of select="@number"/></td>
	          <td><xsl:value-of select="name"/></td>
	          <td><xsl:value-of select="price"/></td>
	          <td><xsl:value-of select="quantity"/></td>
	          <td><xsl:value-of select="extended"/></td>
	        </tr>
	      </xsl:for-each>
	    </table>

	    <hr size="2" width="325px" align="left"/>
	    <table> 
	      <tr> 
	        <td><label>Sub-Total:</label></td>
	        <td><xsl:value-of select="orderType/total"/></td>
	      </tr>
	      <tr> 
	        <td><label>Shipping:</label></td>
	        <td><xsl:value-of select="orderType/shipping"/></td>
	      </tr>
	      <tr> 
	        <td><label>HST:</label></td>
	        <td><xsl:value-of select="orderType/HST"/></td>
	      </tr>
	      <tr> 
	        <td><label>Grand Total:</label></td>
	        <td><xsl:value-of select="orderType/grandTotal"/></td>
	      </tr>
	    </table>
	  </body>
	</html>
  </xsl:template>
</xsl:stylesheet>