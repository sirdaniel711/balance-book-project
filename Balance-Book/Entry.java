// sirdaniel711
// Entry.java
// 6/17/2012
// Represents a single entry for a balance book.

import java.text.DecimalFormat;

public class Entry
{
    private Date date;
	private double amount;
	private double estimateAmount;		
	private double accuracy;			// how much my estimate was off
	private Boolean paid;				// if entry is paid
	private Boolean expense;			// if entry is an asset or liability
	private String category;			// Entertainment, Supplies, Food, Bills, Vehicle, Income, Other
	private String comment;
    private int assign;                 // assigned account to entry
	
	public Entry()
	{
	    date = new Date(6,17,12);
		amount = 0;
		estimateAmount = 0;
		accuracy = 0;
		paid = false;
		expense = false;
		category = "Other";
		comment = "_";
        assign = 0;
		// if (expense)
		// 	System.out.println("E: Month init to 6");
		// else
		// 	System.out.println("I: Month init to 6");
	}

	public Entry(Boolean exp)
	{
		this();
	    // date = new Date(6,17,12);
		// amount = 0;
		// estimateAmount = 0;
		// accuracy = 0;
		// paid = false;
		expense = exp;
		// category = "Other";
		// comment = "_";
        // assign = 0;
	}
	
	public Entry(Boolean exp, int m, int y)
	{
		// this();
	    date = new Date(m,0,y);
		amount = 0;
	    estimateAmount = 0;
		accuracy = 0;
		paid = false;
		expense = exp;
		category = "Other";
		comment = "_";
        assign = 0;
		// if (expense)
		// 	System.out.println("E: Month init to " + m);
		// else
		// 	System.out.println("I: Month init to " + m);
	}
	
	public Entry(Date d, double amt, double est, double ac, Boolean pd, Boolean exp, String cat, 
		String com, int as)
	{
		// date = d;
        // Date newD = new Date(d.getMonth(), d.getDay(), d.getYear());
        date = new Date(d.getMonth(), d.getDay(), d.getYear());
		amount = amt;
		estimateAmount = est;
		accuracy = ac;
		paid = pd;
		expense = exp;
		category = cat;
		comment = com;
        assign = as;
		// if (expense)
		// 	System.out.println("*E: Month init to " + d.getMonth());
		// else
		// 	System.out.println("*I: Month init to " + d.getMonth());
	}
	
	public Date getDate()
	{
	    return date;
	}
	
	public int getMonth()
	{
		return date.getMonth();
	}
	
	public int getDay()
	{
		return date.getDay();
	}
	
	public int getYear()
	{
		return date.getYear();
	}
	
	public double getAmount()
	{
		return amount;
	}
	
	public double getEstimateAmount()
	{
	    return estimateAmount;
	}
	
	public double getAccuracy()
	{
		return accuracy;
	}
	
	public Boolean isPaid()
	{
		return paid;
	}
	
	public Boolean isExpense()
	{
		return expense;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public String getComment()
	{
		return comment;
	}
    
    public int getAssign()
    {
        return assign;
    }
	
	public void setDate(int m, int d, int y)
	{
		date.setDate(m, d, y);
		// if (expense)
		// 	System.out.println("E: Month set to " + m + " on month: " + date.getMonth() + ", for: " + comment);
		// else
		// 	System.out.println("I: Month set to " + m + " on month: " + date.getMonth() + ", for: " + comment);			
	}
	
	public void setDay(int d)
	{
		date.setDay(d);
	}
	
	public void setAmount(double amt)
	{
		amount = amt;
	}
	
	public void setEstimateAmount(double est)
	{
		estimateAmount = est;
	}
	
	public void determineAccuracy()
	{
		if (paid)
		{
			if (expense)
			{
				accuracy = estimateAmount - amount;
			}
			else
			{
				accuracy = amount - estimateAmount;
			}
		}
		else
			accuracy = 0;
	}
    
    public void setAccuracy()
    {
        if (expense)
        {
            accuracy = estimateAmount - amount;
        }
        else
        {
            accuracy = amount - estimateAmount;
        }
    }
	
	public void setPaid(Boolean pd)
	{
		paid = pd;
	}
	
	public void setExpense(Boolean exp) 
	{
		expense = exp;
	}
	
	public void setCategory(String cat)
	{
		category = cat;
	}
	
	public void setComment(String com)
	{
		comment = com;
	}
    
    public void setAssign(int as)
    {
        assign = as;
    }
	
	public void paid(int as)
	{
		paid = true;
		determineAccuracy();
        assign = as;
	}
	
	public void update(double amt, int as)
	{
		amount = amt;
		paid(as);
	}
	
	public String toString()
	{
		String result = "";
		DecimalFormat fmt = new DecimalFormat("00");
		DecimalFormat fmt2 = new DecimalFormat("000.00");
		DecimalFormat fmt3 = new DecimalFormat("0000.00");
		
		if (expense)
		{
			result += fmt.format(date.getMonth()) + "/" + fmt.format(date.getDay()) + "/20" 
				+ fmt.format(date.getYear()) + ": $ " + fmt3.format(amount) + " [$ " 
				+ fmt3.format(estimateAmount) + "  $ " + fmt3.format(accuracy) + " ] ";
			if (paid)
				result += "1 ";
			else
				result += "0 ";
			result += category + " " + assign + " : " + comment;
		}
		else
		{
			result += fmt.format(date.getMonth()) + "/" + fmt.format(date.getDay()) + "/20" 
				+ fmt.format(date.getYear()) + ": $ " + fmt2.format(amount) + " [$ " 
				+ fmt2.format(estimateAmount) + "  $ " + fmt2.format(accuracy) + " ] ";
			if (paid)
				result += "1 ";
			else
				result += "0 ";
			result += category + " " + assign + " : " + comment;
		}
		return result;
	}
}

		