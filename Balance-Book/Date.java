// sirdaniel711
// Date.java
// 6/17/2012
// This class represents a simple date in the form of 3 integers.
// Year is currently represented with 2 digits, and assumes a year in the 21st century.
// For now, validity of date is not checked outside of 12 months, 31 days, 100 years.

public class Date
{
    private int month;
	private int day;
	private int year;
	
	public Date()
	{
	    month = 1;
		day = 1;
		year = 0;
	}
	
	public Date(int m, int d, int y)
	{
	    if (m >= 0 && m <= 12)
		    month = m;
		else
		    month = 1;
		if (d >= 0 && d <= 31)
		    day = d;
		else
		    day = 1;
		if (y >= 0 && y <= 99)
		    year = y;
		else
		    year = 0;
	}
	
	public int getMonth()
	{
	    return month;
	}
	
	public int getDay()
	{ 
	    return day;
	}
	
	public int getYear()
	{
	    return year;
	}
	
	public void setMonth(int m)
	{
	    if (m >= 0 && m <= 12)
		    month = m;
	}
	
	public void setDay(int d)
	{
	    if (d >= 0 && d <= 31)
		    day = d;
	}
	
	public void setYear(int y)
	{
	    if (y >= 0 && y <= 99)
		    year = y;
	}
	
	public void setDate(int m, int d, int y)
	{
	    if (m >= 0 && m <= 12)
		    month = m;
			
	    if (d >= 0 && d <= 31)
		    day = d;
			
	    if (y >= 0 && y <= 99)
		    year = y;
	}
		
	public String toString()
	{
	    return "" + month + "//" + day + "//20" + year;
	}
}
