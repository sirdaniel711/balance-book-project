// sirdaniel711
// Save.java
// 6/17/2012
// Represents an entry in the form of a short-term saving this month.

import java.text.DecimalFormat;

public class Save
{
	private double amount;
	private double amountLeft;				// amount left over
	private String category;	
	private String comment;
	
	public Save()
	{
		amount = 10;
		amountLeft = 10;
		category = "Other";
		comment = "10_put_aside";
	}
	
	public Save(double amt, double left, String cat, String com)
	{
		amount = amt;
		amountLeft = left;
		category = cat;
		comment = com;
	}
	
	public double getAmount()
	{
		return amount;
	}
	
	public double getAmountLeft()
	{
		return amountLeft;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public String getComment()
	{
		return comment;
	}
	
	public void setAmount(double amt)
	{
		amount = amt;
		if (amount < amountLeft)
			amountLeft = amount;
	}
	
	public void setAmountLeft(double left)
	{
		amountLeft = left;
		if (amount < amountLeft)
			amount = amountLeft;
	}
	
	public void setCategory(String cat)
	{
		category = cat;
	}
	
	public void setComment(String com)
	{
		comment = com;
	}
	
	public void resetAmountLeft()
	{
		amountLeft = amount;
	}
	
	public void update(double amt)
	{
		amountLeft -= amt;
	}
	
	public String toString()
	{
		String result = "";
		DecimalFormat fmt = new DecimalFormat("0000.00");
		result += fmt.format(amount) + " ($ " + fmt.format(amountLeft) + " ) " + category + " : " 
			+ comment;
		return result;
	}
}