// sirdaniel711
// Saving.java
// 2/6/2012
// Represents an entry in the form of a long-term saving over several months.
//
// Update 1.0.1
// Adjusted calDate method to correctly display the date "12/2013" instead of "00/2014",
// due to representing the month as 0-11 instead of 1-12
// 
// Update 1.0.2
// Added the inc boolean variable to keep track whether this entry has been incremented or not in order to keep totalMonthlySavings entry updated accurately. totalMonthlySavings entry has been changed.

import java.text.DecimalFormat;

public class Saving
{
    private Date date;					// date for when this saving is due
    private int currentCount;			        // current counter
    private int totalCount;				// total for counter to reach
    private double amount;				// total amount saved sofar
    private double save;				// amount to be saved per month	
    private String category;	
    private String comment;
    private boolean inc;                                // whether entry has been incremented or not within a month, used by Total Monthly Savings variable
	
    public Saving()                     // default constructor
    {
	date = new Date(6,0,13);
	currentCount = 0;
	totalCount = 12;
	amount = 0;
	save = 1;
	category = "Other";
	comment = "12_put_aside";
        inc = false;
    }
	
    public Saving(int m, int y)
    {
	this();
	date = new Date(m, 0, y);
    }
	
    public Saving(int m, int y, int c, int total, double amt, double s, String cat, String com, Boolean i)
    {
	currentCount = c;
	totalCount = total;
	amount = amt;
	save = s;
	category = cat;
	comment = com;
        date = new Date(m, 0, y);
        inc = i;
        
        calDate(m, y);
    }
	
    public Date getDate()                   // getters
    {
	return date;
    }
	
    public int getMonth()
    {
	return date.getMonth();
    }
	
    public int getYear()
    {
	return date.getYear();
    }
	
    public int getCurrentCount()
    {
	return currentCount;
    }
	
    public int getTotalCount()
    {
        return totalCount;
    }
	
    public double getAmount()
    {
        return amount;
    }
	
    public double getSave()
    {
        return save;
    }
	
    public String getCategory()
    {
	return category;
    }
	
    public String getComment()
    {
	return comment;
    }
        
    public boolean isInc()
    {
        return inc;
    }
	
    // calculates the due date for this savings
    public void calDate(int m, int y)
    {
        int val = (totalCount - currentCount) + (m - 1);    // month offset (0-11 instead of 1-12)
	int newM = (val % 12) + 1;                          // displayed month offset (1-12 instead of 0-11)
	int newY = (val / 12) + y;
	date.setMonth(newM);
	date.setDay(0);
	date.setYear(newY);
    }
	
    public void setCurrentCount(int c)          // setters
    {
	currentCount = c;
	if (currentCount > totalCount)
	    totalCount = currentCount;      // update total counter if current counter is greater than it
    }
	
    public void setTotalCount(int total)
    {
	totalCount = total;
	if (totalCount < currentCount)
	    currentCount = totalCount;      // update current counter if total counter is less than it
    }
	
    public void setAmount(double amt)
    {
	amount = amt;
    }
	
    public void setSave(double s)
    {
	save = s;
    }
	
    public void setCategory(String cat)
    {
	category = cat;
    }
	
    public void setComment(String com)
    {
	comment = com;
    }
    
    public void setInc(boolean i)
    {
        inc = i;
    }
	
    // increments current counter by 1
    // indicates a month has passed
    public void incrementCount()
    {
	if (currentCount < totalCount)
	    currentCount++;
    }
	
    // updates saving by incrementing current coutner, and increasing total amount saved by monthly amount
    // being saved
    public void update()
    {
	if (currentCount < totalCount)
	{
	    currentCount++;
	    amount += save;
            inc = true;
	}	
    }
	
    // reset saving
    // useful for when saving has been completed
    public void reset(int m, int y)
    {
        currentCount = 0;
	amount = 0;
        inc = false;
	calDate(m, y);
    }
	
    public String toString()
    {
	String result = "";
	DecimalFormat fmt = new DecimalFormat("00");
	DecimalFormat fmt2 = new DecimalFormat("0000.00");
		
	result += fmt2.format(save) + " ($ " + fmt2.format(amount) + " ) [" + fmt.format(currentCount) 
            + "/" + fmt.format(totalCount) + "] " + fmt.format(date.getMonth()) + "/20" 
            + fmt.format(date.getYear()) + " " + category;
        if (inc)
            result += " 1 ";
        else
            result += " 0 ";
        result += ": " + comment;
	return result;
    }
}