// sirdaniel711
// Value.java
// 1/1/2014
// This program manages a digital balance book with various entries and totals
// Version 1.2.0
//
// Still in progress, but basic functionality works. Project layout needs to be improved/reworked.
// This program expects 1 input parameter upon launching this program.
// An input file is required as a parameter, even if the file does not exist yet.
// The given filename parameter is expected to be 4 digits long without anything else,
// where the first 2 digits represents the month and the last 2 digits the year.
// For example: Java Value 0612
// This would open the file "0612.txt" for editing, or create a new file if it does not exist yet.
// 0612 would be the file entry for the date June 2012.
// The last step of the program is to update/save data to the file. As a result, the file is not updated until 
// right before the program closes.
//
// Known issues: 
//
// Planned for version 1.2.0:
//
// Version 1.2.0 updates:
// * Added current program version identification. Each file is assigned the current supported version, and the program will now only read in files of the current version. This helps avoid issues in reading in incompatible outdated file formats from older versions.
// * Added various input mismatch exceptions to help prevent the program from crashing when the file being read is in the incorrect format, or if the incorrect input type or format is given. This includes user input given in menu navigation, data input from files, and the initial fileIn input parameter. Example: The program will no longer crash if the input file is empty!
// * Adjusted while loops to accept any month instead of just the current month to help avoid unecessary input errors.
// * Now, the file will not be saved until the last step, instead of before running the user input menu. This prevents the file from being overwritten by an unfinished empty file when the program crashes during the menu portion.
// * Entries are now sorted as they are read in. This prevents an issue where entries are out of order if they are already out of order from the file.
// * Several duplicate variables were removed. Now, most of the data read in from the file are directly stored into the main Value object, instead of being temporary stored into a duplicate variable.
// * Finished the program by removing several unfinished display options. This may be a needed feature in the future, but for now, the files are easily viewable, and most of these total values are already displayed in the main menu. The view menu was replaced by the file menu, which is a work in progress to load, save, import, and backup files. Also updated the options displayed in the main menu.
// * Added 2 more account entries, which can be adjusted by name. Changed the entries to be generic rather than specific in order allow them to be adjusted. This allows support for other types of accounts, such as credit cards and gift cards. For now, there is a specific default, but the default will be more generic in a later update. This might be expanded later to be fully dynamic adjustable entries or arrays. Just like how payments and actualExpenses are currently.
// * Added the ability and option to import futureExpense entries from the previous month for convenience when starting a new file. This might be expanded later to include importing accounts.
// * Updated the transfer system to create actualExpense entries to make the transfer. This allows the tranfers to be recorded. This might be expanded later to an actual array of entries.
// * Tweaked a few variable names: Current Value --> Current Balance, Usable Value --> Usable Balance
// * Changed Total Monthly Savings to only increment when the increment function is called, and is now read in from the file instead of being calculated like other totals. This adds flexibility to allow $ being put aside for future expenses only when it is incremented, rather than happening every month. 
// * ^Changed total monthly savings back to an equation due to recent changes with the futureExpense entries including the Inc variable.
// * In addition, the Saving class has been updated to include the inc boolean variable, which represents whether that future expense entry has been incremented this month or not, to go along with the changes made to totalMonthlySavings variable. This keeps the variable updated when an entry is edited or deleted. Also, as a result of changing the file format, added compatibility to older 1.20 version files missing the increment value. In addition, also added the ability to edit this variable within the edit menu.
// * Fixed the leftover value so that it no longer deducts a future expense entry once the entry becomes due.
// * All totals will now be updated after any edits have been made. In addition, all totals are no longer read from the file, as it was redundant. 
// * Updated the Entry object to now set the accuracy to 0 if the entry is not paid. This allows the accuracy to be reset to 0 if the entry is adjusted from paid to unpaid.
// * Fixed a small bug with the import directory not using the DATAPATH, causing the directory being imported not being found.
// * Fixed an error where a StringIndexOutOfBoundsException occurs if the command line is: java Test 513
// * Changed the way both future expense and total monthly savings variables work. When the future expense entry is updated, the total monthly savings variable is only incremented if the entry has not been incremented yet.  This fixed an issue where if a future expense entry is incremented more than once in a single month, once it was reset or deleted, the total monthly savings entry becomes inaccurate. Cause: total monthly savings entry was decreased by only the monthly saved amount.  Added an Inc variable to futureExpense entries, so that the future expense entries were not incremented if the Inc variable is already true. 
//
// Other planned updates later:
// * Need to consider adding support for a GUI interface. In addition, allow the user to control what files to load, save, whenever and wherever.
// * Overall structural layout needs to be reworked and improved, possibly starting from scratch. For example, UI should be ... (grouped into its own method?)
// grouped together, and both importing and exporting file data should be individual methods. Also, account updates based on entry assign should be grouped.
// * Also, consider adjusting the program to update/export data to the file every time anything is changed or updated (real-time).
// * In addition, consider having the program save a back-up file in addition to current file.
// Furthermore...
// * Consider adjustable dynamic stat entries.
// * Consider support for all previous versions of files, including upconverting them to the current version.
// * Consider reorganizing the file so that dynamic entries are towards the end of the file.
// * Consider adjustable dynamic account entries.
// * Consider upgrading from text file to database usage.
// * Consider expanding importing capability to include importing account info.
// * Consider adjusting various input mismatch exceptions to offer more options instead of just closing the program. For example, maybe offering the option to start a new file?
// * Consider adding array entries for transfers made between accounts.
// * Consider having an entry being paid request for the day it was paid. This saves an extra edit step just to update the day. 
// * Consider adjusting some of the totals, such as: remove total current paid expenses, and add actual difference, actual leftover, and rename difference and leftover to monthly difference and monthly leftover, and rename difference2 and leftover2 to future difference and future leftover
// * Consider adding routine monthly and future income support, just like with expenses
// * For backup, have a copy saved when a new month is created, and when a month is finalized
// * Also, when the totals are too high, the display format is ruined by tabs, alhtough the output file is just fine.
// * Delete [Savings] and [Non-Savings], as well as all General Expenses:
// * Consider expanding current expense totals to include income
// * (Update: fixed) Also, a StringIndexOutOfBoundsException occurs if the command line is: java Test 513
// * (Update: from testing, this does not seem to be the case) Also, the total monthly savings COULD become inaccurate when adding future expense entries 
// * (Update: fixed) When a future expense entry is updated, the total monthly savings variable is only incremented if the entry has not been incremented yet. :: Also, if a future expense entry is incremented more than once in a single month, once it resets or is deleted, the total monthly savings entry becomes no longer accurate. Cause: total monthly savings entry is decreased by only the monthly saved amount. However, since future expense entries are only supposed to be incremented once a month, might not worry about this issue, unless the function is changed to not allow future expense entries to be incremented if the Inc variable is already true. However, then the Inc variable should be easy to reset to false.
// * Also, consider separating file reader and writer to another class/object in order to make it easier to add support for databases later on
// * With some of the ideas above, change the program to read and write to a data file only containing the data needed to be stored, not calculated. At the same time, keep the current output as a report that can be viewed. The program will now depend on the data file instead of the report. This makes it easier to import and export data to/from a file, file format is no longer an issue, and dealing with version revisions will occur less often. Highly recommend this update, along with the one above.
// * Also, adjust the datapath to create any folders if they DNE already. May want to cause the program to close if it failes to create a directory due to an invalid path...

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.io.*;
import java.text.DecimalFormat;

public class Value
{
    // object variables
    public static final int DEFAULT_ENTRY = 10;             // Default of 10 entries for each array
    public static final String EXTENSION = ".txt";
    public static final String DATAPATH = "Data/";
    public static final int MAX_ENTRY = 100;                // Up to 100 entries
    public static final String VERSION = "120";					// Current compatible version
	
    private Entry[] payments;                   // income entries
    private Entry[] actualExpenses;             // expense entries
    private Save[] monthlyExpenses;             // short-term expenses
    private Saving[] futureExpenses;            // long-term expenses
    
    private int c0;             // counters for each array
    private int c1;
    private int c2;
    private int c3;
    
    private int month;              // current month and year
    private int year;
    private double start;           // balance at beginning of month
    private double total;           // total current balance
    private double actVal1;	    // various accounts
    private double actVal2;
    private double actVal3;
    private double actVal4;
    private double actVal5;
    private String act1;
    private String act2;
    private String act3;
    private String act4;
    private String act5;
    private double totalPaidPayments;               // totals for income entries
    private double totalUnpaidPayments;
    private double totalPayments;
    private double totalMonthly;                    // current balance + all unpaid income this month
    private double totalActualPaidExpenses;         // totals for expense entries
    private double totalActualUnpaidExpenses;
    private double totalActualExpenses;
    private double totalMonthlyExpenses;            // total short-term savings
    private double totalLeftoverExpenses;           // total short-term savings left
    private double totalCurrentPaidExpenses;        // totals for expense entries + short-term savings
    private double totalCurrentUnpaidExpenses;
    private double totalCurrentExpenses;
    private double difference;                      // difference b/t monthly income & expenses
    private double leftover;                        // predicted balance at end of month
    private double accuracy;                        // how far off estimate is
    private double totalSavings;                    // total saved for long-term savings
    private double totalMonthlySavings;             // amount saved for long-term savings for current month
    private double totalCurrentExpensesAndSavings;  // total current expenses + total savings
    private double difference2;                     // totals with long-term savings deducted
    private double usableValue;
    private double leftover2;
    private double entertainment;                   // totals for various types of entries for current month
    private double supplies;
    private double food;
    private double bills;
    private double vehicle;
    private double savings;
    private double nonSavings;
    private double gift;
    private double transfer;
    private double entertainment2;                  // totals for various types of entries
    private double supplies2;
    private double food2;
    private double bills2;
    private double vehicle2;
    private double savings2;
    private double nonSavings2;
    private double acc1;
    private double acc2;
    private double acc3;
    private boolean end;
    private boolean flag;							// flag for input mismatch
    
    public static void main(String[] args)              // main
    {
        System.out.println("[Value]Version 1.20");
        // routine: read in file, set up variables, start program, export file
        Scanner scan;
        String fileIn;
        String item = "";
        Boolean newMonth = false;
        File myFile = null;
        File dir;
        int line = 0;
        
        if (args.length != 1)
        {
            System.err.println("[Value][Error] Usage: java Value fileIn");
            System.err.println("[Value][Error] A fileIn parameter required.");
            System.err.println("[Value][Error] fileIn parameter needs to be 4 digits long, with the format MMYY.");
            System.exit(1);
        }
		
        fileIn = args[0];
        scan = new Scanner(System.in);
	
        try
        {
            // if the given month is unrealistic, switch the month to January as default
            if (Integer.parseInt(fileIn.substring(0, 2)) < 1 || Integer.parseInt(fileIn.substring(0, 2)) > 12)
                fileIn = "01" + fileIn.substring(2, 4);
			
            if (fileIn.length() != 4)
            {
                System.err.println("[Value][Error] Usage: java Value fileIn");
                System.err.println("[Value][Error] fileIn parameter needs to be 4 digits long, with the format MMYY.");
                System.exit(1);
            }
			
            myFile = new File(DATAPATH + "20" + fileIn.substring(2, 4) + "/" + fileIn + EXTENSION); 
        }
        catch (NumberFormatException e)
        {
            System.err.println("[Value][Error] Usage: java Value fileIn");
            System.err.println("[Value][Error] Incorrect input type for fileIn parameter.");
            System.err.println("[Value][Error] Needs to be 4 digits long with the format MMYY.");
            System.exit(1);
        }
        catch (StringIndexOutOfBoundsException e)
        {
            System.err.println("[Value][Error] Input has a string Index out of bounds exception.");
            System.err.println("[Value][Error] Terminating program.");
            System.exit(1);
        }
				
        try
        {
            // Note: Currently, only the years 2000-2099 are supported, i.e. 20XX
            // However, the 20 in the 20XX can be adjusted to support other centuries
			
            System.out.println();
            System.out.println("Opening file " + DATAPATH + "20" + fileIn.substring(2, 4) + "/" + fileIn + EXTENSION);
	 
            dir = new File(DATAPATH + "20" + fileIn.substring(2, 4));
            if (!dir.exists())
            {
                if (dir.mkdir())
                    System.out.println("Created a new directory " + dir);
                else
                    System.out.println("[Value][Warning] Failed to create a new directory.");
            }	        
	        if (myFile != null)   
            {
                if (!myFile.exists())
                {
                    System.out.println("File does not exist. Creating new file.");
                    newMonth = true;
                }
                else if (!myFile.canRead())
                {
                    System.err.println("[Value][Error] Read access denied to file. Terminating program.");
                    System.exit(1);
                }
                else if (!myFile.canWrite())
                {
                    System.err.println("[Value][Error] Write access denied to file. Terminating program.");
                    System.exit(1);
                }
            }
            else
            {
                System.err.println("[Value][Error] myFile variable is null! Terminating program.");
                System.exit(1);
            }
 		        
            // initial variables
            int c = 0;
	        
            DecimalFormat fmt = new DecimalFormat("00");
            DecimalFormat fmt2 = new DecimalFormat("000.00");
            DecimalFormat fmt3 = new DecimalFormat("0000.00");
			
            Value obj = new Value(fileIn);							// main object
						
            String input;								
			
            if (newMonth)									// if no file exist, create new file
            {
                boolean flag = false;                                                           // flag for input mismatch
                do {
                    System.out.print("Bank1: $");
                    input = scan.next();
                    try
                    {
                        obj.setActVal1(Double.parseDouble(input));
                    }
                    catch (NumberFormatException e)
		            {
                        System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
			            flag = true;
		            }
                } while (flag);
				
		        do {
		            System.out.print("Bank2: $");
		            input = scan.next();
		            flag = false;
		            try
		            {
	                    obj.setActVal2(Double.parseDouble(input));
		            }
		            catch (NumberFormatException e)
		            {
	                    System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
			            flag = true;
		            }
		        } while (flag);
				
                do {
                    System.out.print("Cash: $");
                    input = scan.next();
                    flag = false;
                    try
                    {
                        obj.setActVal3(Double.parseDouble(input));
                    }
                    catch (NumberFormatException e)
                    {
                        System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
                        flag = true;
                    }
                } while (flag);
						
                do {
                    System.out.print("CreditCard: $");
		            input = scan.next();
                    flag = false;
                    try
                    {
			            obj.setActVal4(Double.parseDouble(input));
                    }
                    catch (NumberFormatException e)
                    {
			            System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
			            flag = true;
                    }
		        } while (flag);
				
		        do {
                    System.out.print("GiftCard: $");
                    input = scan.next();
                    flag = false;
                    try
                    {
			            obj.setActVal5(Double.parseDouble(input));
                    }
                    catch (NumberFormatException e)
                    {
			            System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
			            flag = true;
                    }
		        } while (flag);
						
		        obj.setStart(obj.getActVal1() + obj.getActVal2() + obj.getActVal3() + obj.getActVal4() + obj.getActVal5());
		        // obj.calTotal();
		        // obj.calTotalMonthly();
		        // obj.calLeftover();
		        // obj.calUsableValue();
		        // obj.calLeftover2();				
				
		System.out.print("Import future expenses from previous month? [y,n]: ");
		input = scan.next();
		if (input.toLowerCase().substring(0, 1).equals("y"))
		{
                    boolean imp = true;
                    Scanner prevScan;
                    File prevFile = null;
                    File prevDir;
                    int prevMonth = Integer.parseInt(fileIn.substring(0, 2)) - 1;
                    int prevYear = Integer.parseInt(fileIn.substring(2, 4));
					
                    if (prevMonth == 0)
                    {
			prevMonth = 12;
			prevYear = prevYear - 1;
                    }
					
                    String mo;
                    String yr;
					
                    if (prevMonth < 10)			// Int to String offset for double digit
			mo = "0" + prevMonth;
                    else
			mo = "" + prevMonth;
                    if (prevYear < 10)
			yr = "0" + prevYear;
                    else
			yr = "" + prevYear;					
					
                    try
                    {
		        prevFile = new File(DATAPATH + "20" + yr + "/" + mo + yr + EXTENSION); 
                    }
                    catch (NumberFormatException e)
                    {
			System.out.println("[Value][Error] Number format exception, unable to open file for importing entries.");
			imp = false;
                    }
					
                    System.out.println("Opening file " + DATAPATH + "20" + yr + "/" + mo + yr + EXTENSION + " for importing entries.");
	 
                    prevDir = new File(DATAPATH + "20" + yr);
                    if (!prevDir.exists())
                    {
			System.err.println("[Value][Error] Directory does not exist! Unable to import entries.");
			imp = false;
                    }	        
                    if (prevFile != null)
                    {
                        if (!prevFile.exists())
                        {
                            System.out.println("[Value][Error] File " + mo + yr + EXTENSION + " does not exist!");
                            System.out.println("Unable to open file for importing entries.");
                            imp = false;
                        }
                        else if (!prevFile.canRead())
                        {
                            System.err.println("[Value][Error] Read access denied to file. Unable to open file for importing entries.");
                            imp = false;
                        }
                    }
                    else
                    {
                        System.out.println("[Value][Error] prevFile variable is null, terminating program.");
                        System.exit(1);
                    }
                            
                    if (imp)
                    {
			System.out.println("Access granted. Setting up file...");
			try
			{
                            prevScan = new Scanner(prevFile);
                            System.out.println("Success!");
							
                            try
                            {
				item = prevScan.next();
				if (!item.equals(VERSION))
				{
                                    System.out.println("[Value][Error] File version " + item + " does not match program verison " + VERSION + ".");
                                    System.out.println("Unable to import entries.");
                                    imp = false;
				}
				if (imp)
				{
                                    Saving prevFutureExpense = new Saving();
                                    while (!item.equals("Future"))
					item = prevScan.next();
                                    prevScan.next();
                                    prevScan.next();
                                    item = prevScan.next();
					
                                    while (item.substring(0, 2).equals("01") || item.substring(0, 2).equals("02") || item.substring(0, 2).equals("03")
					|| item.substring(0, 2).equals("04") || item.substring(0, 2).equals("05") || item.substring(0, 2).equals("06")
					|| item.substring(0, 2).equals("07") || item.substring(0, 2).equals("08") || item.substring(0, 2).equals("09")
					|| item.substring(0, 2).equals("10") || item.substring(0, 2).equals("11") || item.substring(0, 2).equals("12"))	// *182-193...*
                                    {
					if (obj.getC3() < MAX_ENTRY)
					{
                                            prevScan.next();
                                            item = prevScan.next();	
                                            prevFutureExpense.setSave(Double.parseDouble(item));
                                            prevScan.next();
                                            item = prevScan.next();			
                                            prevFutureExpense.setAmount(Double.parseDouble(item));
                                            prevScan.next();
                                            item = prevScan.next();		
                                            prevFutureExpense.setCurrentCount(Integer.parseInt(item.substring(1, 3)));
                                            prevFutureExpense.setTotalCount(Integer.parseInt(item.substring(4, 6)));
                                            prevFutureExpense.calDate(obj.getMonth(), obj.getYear());
                                            prevScan.next();
                                            item = prevScan.next();
                                            prevFutureExpense.setCategory(item);
                                            item = prevScan.next();
                                            if (!item.equals(":"))
                                                prevScan.next();
                                            item = prevScan.next();
                                            prevFutureExpense.setComment(item);
                                            item = prevScan.next();
							
                                            obj.addEntry(prevFutureExpense, obj.getMonth(), obj.getYear());          
					}
					else
					{
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            prevScan.next();
                                            System.out.println("[Value][Warning] Reached limit, unable to load futureExpense entry.");                                    
					}
                                    }		
                                }	
                            }
                            catch (InputMismatchException e)
                            {
				System.err.println("[Value][Error] Incorrect input type at item " + item + ".");
				System.err.println("Unable to import entries.");
                            }
				
                            catch (NumberFormatException e)
                            {
				System.err.println("[Value][Error] Incorrect number format at item " + item + ".");
				System.err.println("Unable to import entries.");
                            }
				
                            catch (NoSuchElementException e)
                            {
				System.err.println("[Value][Error] Missing input at line " + line + ".");
				System.err.println("Unable to import entries.");
                            }

                            catch (NullPointerException e)
                            {
                                System.out.println("[Value][Error] Null pointer exception. Unable to import entries.");
                            }
			}
			catch (FileNotFoundException e)
			{
                            System.err.println("[Value][Error] Error reading from file. Unable to import entries.");
                        }
                    }
		}
                obj.update();
            }
            else												// else, load in all data from file
            {	
                Scanner fileScan;
		try
		{	
                    int currentMonth;
                    
                    System.out.println("Access granted. Setting up file...");
                    fileScan = new Scanner(myFile);
                    System.out.println("Success!");
					
                    System.out.println("Importing data from file...");
                    line++;
                    item = fileScan.next();					// *1*
                    if (!item.equals(VERSION))
                    {
			System.out.println("[Value][Error] File version " + item + " does not match program verison " 
                            + VERSION + ".");
			System.out.println("Terminating program.");
			System.exit(1);
                    }
                    line++;
                    item = fileScan.next();					// *2*
                    switch (item) {
                        case "January":
                            currentMonth = 1;
                            break;
                        case "February":
                            currentMonth = 2;
                            break;
                        case "March": 
                            currentMonth = 3;
                            break;
                        case "April":
                            currentMonth = 4;
                            break;
                        case "May":
                            currentMonth = 5;
                            break;
                        case "June":
                            currentMonth = 6;
                            break;
                        case "July":
                            currentMonth = 7;
                            break;
                        case "August":
                            currentMonth = 8;
                            break;
                        case "September":
                            currentMonth = 9;
                            break;
                        case "October":
                            currentMonth = 10;
                            break;
                        case "November":
                            currentMonth = 11;
                            break;
                        case "December":
                            currentMonth = 12;
                            break;
                        default:
                            currentMonth = 1;
                            break;
                    }
                                  					
                    // notify user if month in file does not match month of filename, month in the file will be changed to match
                    if (currentMonth != Integer.parseInt(fileIn.substring(0, 2)))
			            System.out.println("[Value][Warning] Month on file does not match requested month.");
					
                    item = fileScan.next();							
					
                    // same for the year
                    if (Integer.parseInt(item.substring(2, 4)) != Integer.parseInt(fileIn.substring(2, 4)))
			            System.out.println("[Value][Warning] Year on file does not match requested year.");
							            
                    line++;	
                    fileScan.next();						// *3*
                    line++;
                    fileScan.next();						// *4*
                    fileScan.next();
                    item = fileScan.next();
                    obj.setStart(Double.parseDouble(item));
                    line++;line++;
                    fileScan.next();						// *6*
                    fileScan.next();
                    line++;
                    fileScan.next();						// *7*
                    line++;line++;
                    fileScan.next();						// *9*
                    fileScan.next();
                    line++;
                    fileScan.next();						// *10*
                    line++;
                    fileScan.next();						// *11*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();	
                    line++;
                    item = fileScan.next();                                             // *12*
                    obj.setAct1(item);
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    obj.setActVal1(Double.parseDouble(item));
                    line++;
                    item = fileScan.next();						// *13*
                    obj.setAct2(item);
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    obj.setActVal2(Double.parseDouble(item));
                    line++;
                    item = fileScan.next();						// *14*
                    obj.setAct3(item);
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    obj.setActVal3(Double.parseDouble(item));
                    line++;
                    item = fileScan.next();						// *15*
                    obj.setAct4(item);
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    obj.setActVal4(Double.parseDouble(item));
                    line++;
                    item = fileScan.next();						// *16*
                    obj.setAct5(item);
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    obj.setActVal5(Double.parseDouble(item));

                    line++;line++;
                    fileScan.next();						// *18*
                    line++;
                    fileScan.next();						// *19*
                    line++;
                    item = fileScan.next();						// *a*

                    // obj.calTotal();

                    Entry payment = new Entry(false);				

                    // first section
                    while (item.substring(0, 2).equals("01") || item.substring(0, 2).equals("02") || item.substring(0, 2).equals("03")
                        || item.substring(0, 2).equals("04") || item.substring(0, 2).equals("05") || item.substring(0, 2).equals("06")
                        || item.substring(0, 2).equals("07") || item.substring(0, 2).equals("08") || item.substring(0, 2).equals("09")
                        || item.substring(0, 2).equals("10") || item.substring(0, 2).equals("11") || item.substring(0, 2).equals("12"))	// *31-44-57-70-83...*
                    {
		                if (obj.getC0() < MAX_ENTRY)
		                {
		                    payment.setDay(Integer.parseInt(item.substring(3, 5)));
		                	
                            fileScan.next();
                            item = fileScan.next();
                            payment.setAmount(Double.parseDouble(item));
                            fileScan.next();
                            item = fileScan.next();	

                            payment.setEstimateAmount(Double.parseDouble(item));
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            item = fileScan.next();			

                            payment.setPaid(Integer.parseInt(item) == 1);
                            payment.determineAccuracy();
                            item = fileScan.next();

                            payment.setCategory(item);
                            item = fileScan.next();			

                            payment.setAssign(Integer.parseInt(item));
                            fileScan.next();
                            item = fileScan.next();

                            payment.setComment(item);
                            line++;
                            item = fileScan.next();
 
                            obj.addEntry(payment);
 		                }
		                else
		                {
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            item = fileScan.next();
                            System.out.println("[Value][Warning] Reached limit, unable to load payment entry."); 
		                }
                    }
                    line++;										// *A+2* 
					

                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();			
                    // obj.setTotalPaidPayments(Double.parseDouble(item));

                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setAcc1(Double.parseDouble(item));

                    fileScan.next();
                    line++;	
                    fileScan.next();						// *A+3*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setTotalUnpaidPayments(Double.parseDouble(item));

                    line++;
                    fileScan.next();						// *A+4*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setTotalPayments(Double.parseDouble(item));

                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    line++;
                    fileScan.next();						// *A+5*				
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setTotalMonthly(Double.parseDouble(item));

                    line++;line++;line++;
                    fileScan.next();						// *A+8*
                    fileScan.next();
                    line++;
                    fileScan.next();						// *A+9*
                    line++;
                    item = fileScan.next();						// *b*

                    Entry actualExpense = new Entry(true);
					
		            // second section 
                    while (item.substring(0, 2).equals("01") || item.substring(0, 2).equals("02") || item.substring(0, 2).equals("03")
                        || item.substring(0, 2).equals("04") || item.substring(0, 2).equals("05") || item.substring(0, 2).equals("06")
                        || item.substring(0, 2).equals("07") || item.substring(0, 2).equals("08") || item.substring(0, 2).equals("09")
                        || item.substring(0, 2).equals("10") || item.substring(0, 2).equals("11") || item.substring(0, 2).equals("12"))	// *75-88...*
                    {
		        if (obj.getC1() < MAX_ENTRY)
		        {
                            actualExpense.setDay(Integer.parseInt(item.substring(3, 5)));

                            fileScan.next();
                            item = fileScan.next();				
                            actualExpense.setAmount(Double.parseDouble(item));
                            fileScan.next();
                            item = fileScan.next();			
                            actualExpense.setEstimateAmount(Double.parseDouble(item));
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            item = fileScan.next();			
                            actualExpense.setPaid(Integer.parseInt(item) == 1);
                            actualExpense.determineAccuracy();
                            item = fileScan.next();
                            actualExpense.setCategory(item);
                            item = fileScan.next();			
                            actualExpense.setAssign(Integer.parseInt(item));
                            fileScan.next();
                            item = fileScan.next();
                            actualExpense.setComment(item);
                            line++;
                            item = fileScan.next();
                            obj.addEntry(actualExpense);
		        }
		        else
		        {
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            item = fileScan.next();
                            System.out.println("[Value][Warning] Reached limit, unable to load actualExpense entry.");
		        }                
                    }
                    line++;										// *B+2*
					
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setTotalActualPaidExpenses(Double.parseDouble(item));

                    fileScan.next();
                    item = fileScan.next();				
                    // obj.setAcc2(Double.parseDouble(item));

                    fileScan.next();
                    line++;
                    fileScan.next();						// *B+3*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();		
                    // obj.setTotalActualUnpaidExpenses(Double.parseDouble(item));
		            
                    line++;
                    fileScan.next();						// *B+4*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();		
                    // obj.setTotalActualExpenses(Double.parseDouble(item));

                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    line++;line++;line++;
                    fileScan.next();						// *B+7*
                    fileScan.next();
                    line++;
                    fileScan.next();						// *B+8*
                    line++;
                    item = fileScan.next();						// *c*

                    Save monthlyExpense = new Save();
					
		    // third section 
                    while (item.substring(0, 2).equals("01") || item.substring(0, 2).equals("02") || item.substring(0, 2).equals("03")
                        || item.substring(0, 2).equals("04") || item.substring(0, 2).equals("05") || item.substring(0, 2).equals("06")
                        || item.substring(0, 2).equals("07") || item.substring(0, 2).equals("08") || item.substring(0, 2).equals("09")
                        || item.substring(0, 2).equals("10") || item.substring(0, 2).equals("11") || item.substring(0, 2).equals("12"))	// *117-126...*
                    {
                        if (obj.getC2() < MAX_ENTRY)
		        {
                            fileScan.next();
                            item = fileScan.next();			
                            monthlyExpense.setAmount(Double.parseDouble(item));
                            fileScan.next();
                            item = fileScan.next();		
                            monthlyExpense.setAmountLeft(Double.parseDouble(item));
                            fileScan.next();
                            item = fileScan.next();
                            monthlyExpense.setCategory(item);
                            fileScan.next();
                            item = fileScan.next();
                            monthlyExpense.setComment(item);
                            line++;
                            item = fileScan.next();
                            obj.addEntry(monthlyExpense);
		        }
		        else
		        {
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            item = fileScan.next();
                            System.out.println("[Value][Warning] Reached limit, unable to load monthlyExpense entry.");                    
		        }
                    }
                    line++;										// *C+2*
										
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setTotalMonthlyExpenses(Double.parseDouble(item));

                    line++;
                    fileScan.next();						// *C+3*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setTotalLeftoverExpenses(Double.parseDouble(item));
		            
                    line++;line++;
                    fileScan.next();						// *C+5*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();		
                    // obj.setTotalCurrentPaidExpenses(Double.parseDouble(item));
                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setAcc3(Double.parseDouble(item));
                    fileScan.next();
                    line++;
                    fileScan.next();						// *C+6*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();		
                    // obj.setTotalCurrentUnpaidExpenses(Double.parseDouble(item));

                    line++;
                    fileScan.next();						// *C+7*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();	
                    // obj.setTotalCurrentExpenses(Double.parseDouble(item));

                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    line++;line++;
                    fileScan.next();						// *C+9*
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();	
                     // obj.setDifference(Double.parseDouble(item));

                    fileScan.next();
                    item = fileScan.next();		
                    // obj.setAccuracy(Double.parseDouble(item));

                    fileScan.next();
                    line++;
                    fileScan.next();						// *C+10*
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();		
                    // obj.setLeftover(Double.parseDouble(item));

                    line++;
                    fileScan.next();						// *C+11*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    line++;line++;line++;
                    fileScan.next();						// *C+14*
                    fileScan.next();
                    line++;
                    fileScan.next();						// *C+15*
                    line++;
                    item = fileScan.next();						// *d*

                    Saving futureExpense = new Saving();
					
		    // fourth section 
                    while (item.substring(0, 2).equals("01") || item.substring(0, 2).equals("02") || item.substring(0, 2).equals("03")
                        || item.substring(0, 2).equals("04") || item.substring(0, 2).equals("05") || item.substring(0, 2).equals("06")
                        || item.substring(0, 2).equals("07") || item.substring(0, 2).equals("08") || item.substring(0, 2).equals("09")
                        || item.substring(0, 2).equals("10") || item.substring(0, 2).equals("11") || item.substring(0, 2).equals("12"))	// *182-193...*
                    {
                        if (obj.getC3() < MAX_ENTRY)
                        {
                            fileScan.next();
                            item = fileScan.next();	
                            futureExpense.setSave(Double.parseDouble(item));
                            fileScan.next();
                            item = fileScan.next();			
                            futureExpense.setAmount(Double.parseDouble(item));
                            fileScan.next();
                            item = fileScan.next();		
                            futureExpense.setCurrentCount(Integer.parseInt(item.substring(1, 3)));
                            futureExpense.setTotalCount(Integer.parseInt(item.substring(4, 6)));
                            futureExpense.calDate(obj.getMonth(), obj.getYear());
                            fileScan.next();
                            item = fileScan.next();
                            futureExpense.setCategory(item);
                            item = fileScan.next();
                            if (item.equals(":"))           // compatibility with older 1.20 format
                            {
                                System.out.println("[Value][Warning] Converting older 1.2 format to a newer version.");                      
                                futureExpense.setInc(true);
                                item = fileScan.next();
                                futureExpense.setComment(item);
                            }
                            else
                            {
                                futureExpense.setInc(item.equals("1"));
                                fileScan.next();
                                item = fileScan.next();
                                futureExpense.setComment(item);
                            }
                            
                            line++;
                            item = fileScan.next();
                            obj.addEntry(futureExpense, obj.getMonth(), obj.getYear());          
		        }
		        else
		        {
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            fileScan.next();
                            item = fileScan.next();
                            System.out.println("[Value][Warning] Reached limit, unable to load futureExpense entry.");                                    
		        }
                    }
                    line++;										// *D+2*
										
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setTotalSavings(Double.parseDouble(item));

                    line++;
                    fileScan.next();						// *D+3*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setTotalMonthlySavings(Double.parseDouble(item));

                    line++;line++;
                    fileScan.next();						// *D+5*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setTotalCurrentExpensesAndSavings(Double.parseDouble(item));

                    line++;line++;
                    fileScan.next();						// *D+7*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setDifference2(Double.parseDouble(item));

                    line++;
                    fileScan.next();						// *D+8*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setUsableValue(Double.parseDouble(item));

                    line++;
                    fileScan.next();						// *D+9*
                    fileScan.next();
                    fileScan.next();
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setLeftover2(Double.parseDouble(item));

                    line++;line++;
		    item = fileScan.next();						// *D+11*
                    obj.setEnd(Integer.parseInt(item) == 1);
					
		    // last section
		    line++;
                    fileScan.next();						// *D+12*
                    line++;
                    fileScan.next();						// *D+13*
                    line++;
                    fileScan.next();						// *D+14*
                    fileScan.next();
                    line++;
                    fileScan.next();						// *D+15*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setEntertainment(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+16*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setSupplies(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+17*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setFood(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+18*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setBills(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+19*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setVehicle(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+20*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setGift(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+21*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setTransfer(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+22*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setSavings(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+23*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setNonSavings(Double.parseDouble(item));
                    line++;line++;
                    fileScan.next();						// *D+25*
                    fileScan.next();
                    line++;
                    fileScan.next();						// *D+26*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setEntertainment2(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+27*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setSupplies2(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+28*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setFood2(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+29*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setBills2(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+30*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setVehicle2(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+31*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setSavings2(Double.parseDouble(item));
                    line++;
                    fileScan.next();						// *D+32*
                    fileScan.next();
                    item = fileScan.next();
                    // obj.setNonSavings2(Double.parseDouble(item));
                    
                    fileScan.close();									// finished reading in data
                    System.out.println("Finished importing data. Import scanner closed.");            
		}
				
                catch (InputMismatchException e)
                {
                    System.err.println("[Value][Error] Incorrect input type at item " + item + ".");
                    System.err.println("on line " + line + ".");
                    System.err.println("Terminating program.");
                    System.exit(1);
                }

                catch (NumberFormatException e)
                {
                    System.err.println("[Value][Error] Incorrect number format at item " + item + ".");
                    System.err.println("at line " + line + ".");
                    System.err.println("Terminating program.");
                    System.exit(1);
                }

                catch (NoSuchElementException e)
                {
                    System.err.println("[Value][Error] Missing input at line " + line + ".");
                    System.err.println("Terminating program.");
                    System.exit(1);
                }
                
                catch (FileNotFoundException e)
                {
                    System.err.println("[Value][Error] Error reading from file " + myFile + ". Terminating program.");
                    System.exit(1);
                }
            }
			
	    obj.update();       // update any unprocesed changes
	        
            obj.run();											// running menu program 

            obj.update();

            PrintWriter fileOut;								// attempting to open file for export
			
	    System.out.println("Setting up " + myFile + " for writing...");
			
	    fileOut = new PrintWriter(new BufferedWriter(new FileWriter(DATAPATH + "20" + fileIn.substring(2, 4) + "/" + fileIn + EXTENSION)));	
		
            System.out.println("Success!");

            System.out.println("Now exporting all data to file...");

            fileOut.println(VERSION);

            int month = obj.getMonth();								// now, exporting all data to file

            switch (month) {
                case 1:
                    fileOut.println("January 20" + fmt.format(obj.getYear()));
                    fileOut.println("------------");
                    break;
                case 2:
                    fileOut.println("February 20" + fmt.format(obj.getYear()));
                    fileOut.println("-------------");
                    break;
                case 3:
                    fileOut.println("March 20" + fmt.format(obj.getYear()));
                    fileOut.println("----------");
                    break;
                case 4:
                    fileOut.println("April 20" + fmt.format(obj.getYear()));
                    fileOut.println("----------");
                    break;
                case 5:
                    fileOut.println("May 20" + fmt.format(obj.getYear()));
                    fileOut.println("--------");
                    break;
                case 6:
                    fileOut.println("June 20" + fmt.format(obj.getYear()));
                    fileOut.println("---------");
                    break;
                case 7:
                    fileOut.println("July 20" + fmt.format(obj.getYear()));
                    fileOut.println("---------");
                    break;
                case 8:
                    fileOut.println("August 20" + fmt.format(obj.getYear()));
                    fileOut.println("-----------");
                    break;
                case 9:
                    fileOut.println("September 20" + fmt.format(obj.getYear()));
                    fileOut.println("--------------");
                    break;
                case 10:
                    fileOut.println("October 20" + fmt.format(obj.getYear()));
                    fileOut.println("------------");
                    break;
                case 11:
                    fileOut.println("November 20" + fmt.format(obj.getYear()));
                    fileOut.println("-------------");
                    break;
                case 12:
                    fileOut.println("December 20" + fmt.format(obj.getYear()));
                    fileOut.println("-------------");
                    break;
                default:
                    fileOut.println("January 20" + fmt.format(obj.getYear()));
                    fileOut.println("------------");
                    break;                                  
            }

            fileOut.println("Start:  $ " + fmt3.format(obj.getStart()));
            fileOut.println();
            fileOut.println("Actual Income");
            fileOut.println("-------------");
            fileOut.println();
            fileOut.println("Current Balance");
            fileOut.println("***************");
            fileOut.println("Total\t = $ " + fmt3.format(obj.getTotal()));
            fileOut.println(obj.getAct1() + "\t = $ " + fmt3.format(obj.getActVal1()));
            fileOut.println(obj.getAct2() + "\t = $ " + fmt3.format(obj.getActVal2()));
            fileOut.println(obj.getAct3() + "\t = $ " + fmt3.format(obj.getActVal3()));
            fileOut.println(obj.getAct4() + "\t = $ " + fmt3.format(obj.getActVal4()));
            fileOut.println(obj.getAct5() + "\t = $ " + fmt3.format(obj.getActVal5()));
            fileOut.println();
            fileOut.println("Payments");
            fileOut.println("********");

            while (c < obj.getC0())
            {
		fileOut.println(obj.getPayments(c));
		c++;
            }
            c = 0;
			
            fileOut.println();
            fileOut.println("Total Paid Payments =   $ " + fmt3.format(obj.getTotalPaidPayments()) + " [$ " 
	        + fmt2.format(obj.getAcc1()) + " ]");
            fileOut.println("Total Unpaid Payments = $ " + fmt3.format(obj.getTotalUnpaidPayments()));
            fileOut.println("Total Payments =        $ " + fmt3.format(obj.getTotalPayments()) + " [$ " 
	        + fmt2.format(obj.getAcc1()) + " ]");
            fileOut.println("Total Monthly =         $ " + fmt3.format(obj.getTotalMonthly()));
            fileOut.println();
            fileOut.println();
            fileOut.println("Actual Expenses");
            fileOut.println("---------------");

            while (c < obj.getC1())
            {
                fileOut.println(obj.getActualExpenses(c));
		c++;
            }
            c = 0;
			
            fileOut.println();
            fileOut.println("Total Actual Paid Expenses =   $ " + fmt3.format(obj.getTotalActualPaidExpenses()) 
	        + " [$ " + fmt3.format(obj.getAcc2()) + " ]");
            fileOut.println("Total Actual Unpaid Expenses = $ " + fmt3.format(obj.getTotalActualUnpaidExpenses()));
            fileOut.println("Total Actual Expenses =        $ " + fmt3.format(obj.getTotalActualExpenses()) 
	        + " [$ " + fmt3.format(obj.getAcc2()) + " ]");
            fileOut.println();
            fileOut.println();
            fileOut.println("Monthly Expenses");
            fileOut.println("----------------");

            while (c < obj.getC2())
            {
                fileOut.println(fmt.format(obj.getMonth()) + "/20" + fmt.format(obj.getYear()) + ": $ " 
	            + obj.getMonthlyExpenses(c));
		c++;
            }
            c = 0;
			
            fileOut.println();
            fileOut.println("Total Monthly Expenses =  $ " + fmt3.format(obj.getTotalMonthlyExpenses()));
            fileOut.println("Total Leftover Expenses = $ " + fmt3.format(obj.getTotalLeftoverExpenses()));
            fileOut.println();
            fileOut.println("Total Current Paid Expenses =   $ " 
	        + fmt3.format(obj.getTotalCurrentPaidExpenses()) + " [$ " 
	        + fmt3.format(obj.getAcc2() + obj.getAcc3()) + " ]");
            fileOut.println("Total Current Unpaid Expenses = $ " 
	        + fmt3.format(obj.getTotalCurrentUnpaidExpenses()));
            fileOut.println("Total Current Expenses =        $ " + fmt3.format(obj.getTotalCurrentExpenses()) 
	        + " [$ " + fmt3.format(obj.getAcc2() + obj.getAcc3()) + " ]");
            fileOut.println();
            fileOut.println("Difference =                    $ " + fmt3.format(obj.getDifference()) + " [$ " 
	        + fmt3.format(obj.getAccuracy()) + " ]");
            fileOut.println("Leftover =                      $ " + fmt3.format(obj.getLeftover()));
            fileOut.println("Accuracy =                      $ " + fmt3.format(obj.getAccuracy()));
            fileOut.println();
            fileOut.println();
            fileOut.println("Future Expenses");
            fileOut.println("---------------");
			
            while (c < obj.getC3())
            {
		fileOut.println(fmt.format(obj.getMonth()) + "/20" + fmt.format(obj.getYear()) + ": $ " 
	            + obj.getFutureExpenses(c));
		c++;
            }
			
            fileOut.println();
            fileOut.println("Total Savings =         $ " + fmt3.format(obj.getTotalSavings()));
            fileOut.println("Total Monthly Savings = $ " + fmt3.format(obj.getTotalMonthlySavings()));
            fileOut.println();
            fileOut.println("Total Current Expenses and Savings = $ " 
	        + fmt3.format(obj.getTotalCurrentExpensesAndSavings()));
            fileOut.println();
            fileOut.println("Difference 2 =   $ " + fmt3.format(obj.getDifference2()));
            fileOut.println("Usable Balance = $ " + fmt3.format(obj.getUsableValue()));
            fileOut.println("Leftover 2 =     $ " + fmt3.format(obj.getLeftover2()));
            fileOut.println();
	        
	    if (obj.isEnd())
	        fileOut.println("1");
	    else
	        fileOut.println("0");
	        
            fileOut.println("Stats");
            fileOut.println("*****");
            fileOut.println("Current Expenses:");
            fileOut.println("[Entertainment] $ " + fmt3.format(obj.getEntertainment()));
            fileOut.println("[Supplies]      $ " + fmt3.format(obj.getSupplies()));
            fileOut.println("[Food]          $ " + fmt3.format(obj.getFood()));
            fileOut.println("[Bills]         $ " + fmt3.format(obj.getBills()));
            fileOut.println("[Vehicle]       $ " + fmt3.format(obj.getVehicle()));
            fileOut.println("[Gift]          $ " + fmt3.format(obj.getGift()));
            fileOut.println("[Transfer]      $ " + fmt3.format(obj.getTransfer()));
            fileOut.println("[Savings]       $ " + fmt3.format(obj.getSavings()));
            fileOut.println("[Non-Savings]   $ " + fmt3.format(obj.getNonSavings()));
            fileOut.println();
            fileOut.println("General Expenses:");
            fileOut.println("[Entertainment] $ " + fmt3.format(obj.getEntertainment2())) ;
            fileOut.println("[Supplies]      $ " + fmt3.format(obj.getSupplies2()));
            fileOut.println("[Food]          $ " + fmt3.format(obj.getFood2()));
            fileOut.println("[Bills]         $ " + fmt3.format(obj.getBills2()));
            fileOut.println("[Vehicle]       $ " + fmt3.format(obj.getVehicle2()));
            fileOut.println("[Savings]       $ " + fmt3.format(obj.getSavings2()));
            fileOut.println("[Non-Savings]   $ " + fmt3.format(obj.getNonSavings2()));

            fileOut.close();									// done, job completed.

            System.out.println("Export complete.");
            System.out.println();	
	}
	catch (InputMismatchException e)
	{
            System.err.println("[Value][Error] Incorrect input type, terminating program.");
            System.exit(1);
	}	
	catch (NumberFormatException e)
	{
            System.err.println("[Value][Error] Incorrect format type, terminating program.");
            System.exit(1);
	}
        catch (StringIndexOutOfBoundsException e)
        {
            System.err.println("[Value][Error] String Index out of bounds exception, terminating program.");
            System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println("[Value][Error] Could not open file for writing.");
            System.err.println("[Value][Error] Terminating program.");
            System.exit(1);
        }	
    }
	
    public Value(String fileIn)								// main constructor
    {        
        c0 = 0;
        c1 = 0;
        c2 = 0;
        c3 = 0;		

        month = Integer.parseInt(fileIn.substring(0, 2));
        year = Integer.parseInt(fileIn.substring(2, 4));
        start = 0;
        total = 0;
        act1 = "Bank1";
        act2 = "Bank2";
        act3 = "Cash";
        act4 = "CreditCard";
        act5 = "GiftCard";
        actVal1 = 0;
        actVal2 = 0;
        actVal3 = 0;
        actVal4 = 0;
        actVal5 = 0;
        totalPaidPayments = 0;
        totalUnpaidPayments = 0;
        totalPayments = 0;
        totalMonthly = 0;
        totalActualPaidExpenses = 0;
        totalActualUnpaidExpenses = 0;
        totalActualExpenses = 0;
        totalMonthlyExpenses = 0;
        totalLeftoverExpenses = 0;
        totalCurrentPaidExpenses = 0;
        totalCurrentUnpaidExpenses = 0; 
        totalCurrentExpenses = 0;
        difference = 0;
        leftover = 0;
        accuracy = 0;
        totalSavings = 0;
        totalMonthlySavings = 0;
        totalCurrentExpensesAndSavings = 0;
        difference2 = 0;
        usableValue = 0;
        leftover2 = 0;
        entertainment = 0; 
        supplies = 0;
        food = 0;
        bills = 0;
        vehicle = 0;
        gift = 0;
        transfer = 0;
        savings = 0;
        nonSavings = 0;
        entertainment2 = 0;
        supplies2 = 0;
        food2 = 0;
        bills2 = 0;
        vehicle2 = 0;
        savings2 = 0;
        nonSavings2 = 0;
        acc1 = 0;
        acc2 = 0;
        acc3 = 0;
        end = false;
        flag = false;
		
	payments = new Entry[DEFAULT_ENTRY];
	for (int i = 0; i < DEFAULT_ENTRY; i++)
            payments[i] = new Entry(false, month, year);
        
	actualExpenses = new Entry[DEFAULT_ENTRY];
	for (int i = 0; i < DEFAULT_ENTRY; i++)
            actualExpenses[i] = new Entry(true, month, year);
        
	monthlyExpenses = new Save[DEFAULT_ENTRY];
	for (int i = 0; i < DEFAULT_ENTRY; i++)
            monthlyExpenses[i] = new Save();
        
        futureExpenses = new Saving[DEFAULT_ENTRY];
        for (int i = 0; i < DEFAULT_ENTRY; i++)
            futureExpenses[i] = new Saving();		
    }
	
    public void run()											// run method for menu
    {
	String input;
	int action = 0;
	Scanner scan = new Scanner(System.in);
	while (action != 5)
	{
            System.out.println("\n###################################Main Menu###################################\n");
            display(1);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Choose an action:");
            System.out.println("(1) = Entry     (2) = Accounts     (3) = File     (4) = Finalize     (5) = Exit");
            System.out.println("###################################Main Menu###################################\n");
			
            do {
                System.out.print("Command: ");
                input = scan.next();
                flag = false;
                try
                {
                    action = Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
                    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                    flag = true;
		}
            } while (flag);
			
            if (action == 1)
		entry();
            else if (action == 2)
		account();
            else if (action == 3)
		file();
            else if (action == 4)
            {
		function(5);
		System.out.println("Update: End of month set to true.");
            }
        }
    }
	
    public void entry()												// entry menu
    {
        String input;
        int action = 0;
        Scanner scan = new Scanner(System.in);
        while (action != 5)
        {
            System.out.println("\n###################################Entry Menu##################################\n");
            display(2);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Choose a category:");
            System.out.println("(1) = Payment   (2) = Actual    (3) = Monthly   (4) = Future   (5) = Back");
            System.out.println("###################################Entry Menu##################################\n");
			
            do {
                System.out.print("Command: ");
                input = scan.next();
                flag = false;
                try
                {
                    action = Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
                    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                    flag = true;
		}
            } while (flag);			
			
            if (action >= 1 && action < 5)
                entry2(action);
        }	
    }
	
    public void entry2(int cat)										// entry2 menu
    {
        String input;
        int action = 0;
        Scanner scan = new Scanner(System.in);
        while (action != 5)
        {
            System.out.println("\n###################################Entry Menu##################################\n");
            if (cat == 1)
		display(4);
            else if (cat == 2)
		display(5);
            else if (cat == 3)
		display(6);
            else if (cat == 4)
		display(7);
            System.out.println();
            System.out.println("Choose a function or action:");
            if (cat == 1 || cat == 2)
		System.out.println("(1) = Add   (2) = Edit    (3) = Delete   (4) = *Pay   (5) = Back");
            else if (cat == 3)
		System.out.println("(1) = Add   (2) = Edit    (3) = Delete   (4) = *Entry   (5) = Back");
            else
		System.out.println("(1) = Add   (2) = Edit    (3) = Delete   (4) = *Increment   (5) = Back");			
            System.out.println("###################################Entry Menu##################################\n");
			
            do {
                System.out.print("Command: ");
                input = scan.next();
                flag = false;
                try
                {
                    action = Integer.parseInt(input);
                }
		catch (NumberFormatException e)
		{
                    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                    flag = true;
		}
            } while (flag);			
			
            if (action == 1)
            {
		add(cat);
		update();
            }
            else if (action == 2)
            {
		edit(cat);
		update();
            }
            else if (action == 3)
            {
		delete(cat);
		update();
            }
            else if (action == 4)
            {
		function(cat);
		update();
            }
	}		
    }
	
    public void account()												// Account menu
    {
	String input;
	int action = 0;
	Scanner scan = new Scanner(System.in);
	while (action != 3)
	{
            System.out.println("\n##################################Account Menu#################################\n");
            display(3);
            System.out.println();
            System.out.println("Choose a function or category:");
            System.out.println("(1) = *Transfer   (2) = Accounts   (3) = Back");
            System.out.println("##################################Account Menu#################################\n");
			
            do {
                System.out.print("Command: ");
                flag = false;
                try
                {
                    input = scan.next();
                    action = Integer.parseInt(input);
                }			
		catch (NumberFormatException e)
		{
                    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 3.");
                    flag = true;
                }
            } while (flag);			
			
            if (action == 1)
            {
		function(0);
		update();
            }
            else if (action == 2)
		account2(action);
        }	
    }
	
    public void account2(int category)								// Account2 menu
    {
	Scanner newScan = new Scanner(System.in);
        int val = 0;
        int act = 0;
        String input;
        double newVal = 0;
        if (category == 2)
        {
            while (act != 5)
            {
                System.out.println("\n##################################Account Menu#################################\n");
                display(8);
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println("Choose an action:");
                System.out.println("(1) = Increment   (2) = Decrement   (3) = Edit   (4) = Name   (5) = Back");
                System.out.println("##################################Account Menu#################################\n");		
				
		do {
                    System.out.print("Action: ");
                    input = newScan.next();
                    flag = false;
                    try
                    {
                        act = Integer.parseInt(input);
                    }
                    catch (NumberFormatException e)
                    {
			System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
			flag = true;
                    }
                } while (flag);				
				
		if (act == 1)
                {
                    do {
                        System.out.print("Account to add to: ");
                        input = newScan.next();
                        flag = false;
                        try
                        {
                            val = Integer.parseInt(input);
			}
			catch (NumberFormatException e)
			{
                            System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                            flag = true;
			}
                    } while (flag);					
					
                    if (val == 1)
                    {	
                        do {
                            System.out.print(act1 + " += ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
		    		newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
		    	} while (flag);                						
						
			actVal1 += newVal;
			update();
                    }
                    if (val == 2)
                    {
                        do {
                            System.out.print(act2 + " += ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
		    		newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
		    	} while (flag);                						
						
			actVal2 += newVal;
			update();
                    }
                    if (val == 3)
                    {	
		    	do {
                            System.out.print(act3 + " += ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
		    		newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
                        } while (flag);                						
						
			actVal3 += newVal;
			update();
                    }
                    if (val == 4)
                    {
			do {
                            System.out.print(act4 + " += ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
				newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
                                System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
				flag = true;
                            }
			} while (flag);
						
			actVal4 += newVal;
			update();
                    }
                    if (val == 5)
                    {
			do {
                            System.out.print(act5 + " += ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
				newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
				System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
                                flag = true;
                            }
			} while (flag);
						
			actVal5 += newVal;
			update();
                    }
		}
		else if (act == 2)
		{
                    do {
                        System.out.print("Account to subtract from: ");
                        input = newScan.next();
                        flag = false;
                        try
                        {
                            val = Integer.parseInt(input);
			}
                        catch (NumberFormatException e)
			{
                            System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                            flag = true;
			}
                    } while (flag);					
					
                    if (val == 1)
                    {					
                        do {
                            System.out.print(act1 + " -= ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
		    		newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
		    	} while (flag);                						
						
			actVal1 -= newVal;
			update();
                    }
                    if (val == 2)
                    {
                        do {
                            System.out.print(act2 + " -= ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
		    		newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
                        } while (flag);                						
						
			actVal2 -= newVal;
			update();
                    }
                    if (val == 3)
                    {
                        do {
                            System.out.print(act3 + " -= ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
                                newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter anumber.");
		    		flag = true;
                            }
                        } while (flag);                						
						
			actVal3 -= newVal;
			update();
                    }
                    if (val == 4)
                    {
		    	do {
                            System.out.print(act4 + " -= ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
		    		newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter anumber.");
		    		flag = true;
                            }
                        } while (flag);                						
						
			actVal4 -= newVal;
			update();
                    }					
                    if (val == 5)
                    {
                        do {
                            System.out.print(act5 + " -= ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
		    		newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter anumber.");
		    		flag = true;
                            }
		    	} while (flag);                						
						
			actVal5 -= newVal;
			update();
                    }										
                }
                else if (act == 3)
                {				
                    do {
                        System.out.print("Account to adjust: ");
                        input = newScan.next();
                        flag = false;
                        try
                        {
                            val = Integer.parseInt(input);
                        }
			catch (NumberFormatException e)
			{
                            System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                            flag = true;
			}
                    } while (flag);					
					
                    if (val == 1)
                    {
                        do {
                            System.out.print(act1 + " = ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
                                newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
                        } while (flag);                						
						
			actVal1 = newVal;
			update();
                    }
                    if (val == 2)
                    {
                        do {
                            System.out.print(act2 + " = ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
		    		newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
		    	} while (flag);                						
						
                        actVal2 = newVal;
			update();
                    }
                    if (val == 3)
                    {
                        do {
                       	    System.out.print(act3 + " = ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
                                newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
                                System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
                        } while (flag);                						
						
                        actVal3 = newVal;
                        update();
                    }
                    if (val == 4)
                    {
                        do {
                            System.out.print(act4 + " = ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
                                newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
                                System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
                                flag = true;
                            }
                        } while (flag);                						
						
                        actVal4 = newVal;
                        update();
                    }					
                    if (val == 5)
                    {
                        do {
                            System.out.print(act5 + " = ");
                            input = newScan.next();
                            flag = false;
                            try
                            {
                                newVal = Double.parseDouble(input);
                            }
                            catch (NumberFormatException e)
                            {
                                System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
                                flag = true;
                            }
                        } while (flag);                						
						
                        actVal5 = newVal;
                        update();
                    }										
                }
                else if (act == 4)
                {	
                    do {
                        System.out.print("Account name to adjust: ");
                        input = newScan.next();
                        flag = false;
                        try
                        {
                            val = Integer.parseInt(input);
                        }
                        catch (NumberFormatException e)
                        {
                            System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                            flag = true;
                        }
                    } while (flag);					
					
                    System.out.print("Name = ");
                    input = newScan.next();
					
                    if (val == 1)
                    {              						
                        act1 = input;
                    }
                    else if (val == 2)
                    {            						
                        act2 = input;
                    }
                    else if (val == 3)
                    {             						
                        act3 = input;
                    }
                    else if (val == 4)
                    {           						
                        act4 = input;
                    }
                    else if (val == 5)
                    {                        
                        act5 = input;
                    }						
                }
            }
        }
    }
	
    public void file()												// view menu
    {
        // String input = "";
		// int action = 0;
		// Scanner scan = new Scanner(System.in);
		// while (action != 10)
		// {
		// System.out.println("\n###################################View Menu###################################\n");
		// System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		// System.out.println();
        // System.out.println();
        // System.out.println("Choose a type of format to view data:");
        // System.out.println("(1) = Full   (2) = Totals   (3) = Entries   (4) = General   (5) = Payments");
        // System.out.println("(6) = Actual   (7) = Monthly   (8) = Future   (9) = Stats   (10) = Back");
        // System.out.println("###################################View Menu###################################\n");

        // do {
        // System.out.print("Command: ");
        // input = scan.next();
        // flag = false;
        // try
        // {
        // 	action = Integer.parseInt(input);
        // }
        // catch (NumberFormatException e)
        // {
		// System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 10.");
		// flag = true;
		// }
		// } while (flag);				
		// }	
    }
	
    public void update()											// update all totals
    {
	DecimalFormat fmt3 = new DecimalFormat("0000.00");
	total = actVal1 + actVal2 + actVal3 + actVal4 + actVal5;
	totalPaidPayments = 0;
	for (int i = 0; i < c0; i++)
	{
	    if (payments[i].isPaid())
		totalPaidPayments += payments[i].getAmount();
	}
	acc1 = 0;
	for (int i = 0; i < c0; i++)
	{
	    acc1 += payments[i].getAccuracy();
	}
	totalUnpaidPayments = 0;
	for (int i = 0; i < c0; i++)
	{
	    if (!payments[i].isPaid() && payments[i].getAccuracy() == 0)
		totalUnpaidPayments += payments[i].getEstimateAmount();
        }
	totalPayments = totalPaidPayments + totalUnpaidPayments;
	totalMonthly = total + totalUnpaidPayments;
	totalActualPaidExpenses = 0;
	for (int i = 0; i < c1; i++)
	{
	    if (actualExpenses[i].isPaid())
		totalActualPaidExpenses += actualExpenses[i].getAmount();
	}
	acc2 = 0;
	for (int i = 0; i < c1; i++)
	{
	    acc2 += actualExpenses[i].getAccuracy();
	}
	totalActualUnpaidExpenses = 0;
	for (int i = 0; i < c1; i++)
	{
	    if (!actualExpenses[i].isPaid() && actualExpenses[i].getAccuracy() == 0)
		totalActualUnpaidExpenses += actualExpenses[i].getEstimateAmount();
	}
	totalActualExpenses = totalActualPaidExpenses + totalActualUnpaidExpenses;
	totalMonthlyExpenses = 0;
	for (int i = 0; i < c2; i++)
	{
	    totalMonthlyExpenses += monthlyExpenses[i].getAmount();
	}
	totalLeftoverExpenses = 0;
	for (int i = 0; i < c2; i++)
	{
	    if (monthlyExpenses[i].getAmountLeft() >= 0)
		totalLeftoverExpenses += monthlyExpenses[i].getAmountLeft();
	}
        acc3 = 0;
        if (end)
        {
            for (int i = 0; i < c2; i++)
            {
                acc3 += monthlyExpenses[i].getAmountLeft();
            }
        }    
	totalCurrentPaidExpenses = totalActualPaidExpenses;
        if (end)
            totalCurrentUnpaidExpenses = totalActualUnpaidExpenses;
        else
            totalCurrentUnpaidExpenses = totalActualUnpaidExpenses + totalLeftoverExpenses;
	totalCurrentExpenses = totalCurrentPaidExpenses + totalCurrentUnpaidExpenses;
	difference = totalPayments - totalCurrentExpenses;
	accuracy = acc1 + acc2 + acc3;
	leftover = totalMonthly - totalCurrentUnpaidExpenses;
	totalSavings = 0;
        totalMonthlySavings = 0;
	for (int i = 0; i < c3; i++)
	{
	    totalSavings += futureExpenses[i].getAmount();
            if (futureExpenses[i].isInc())
            {
                totalMonthlySavings += futureExpenses[i].getSave();
            }         
	}
        totalSavings = Double.parseDouble(fmt3.format(totalSavings));
        totalMonthlySavings = Double.parseDouble(fmt3.format(totalMonthlySavings));			
        totalCurrentExpensesAndSavings = totalCurrentExpenses + totalMonthlySavings;
        difference2 = difference - totalMonthlySavings;
        usableValue = total - totalSavings;
        leftover2 = leftover - totalSavings;
        entertainment = 0;
        supplies = 0;
        food = 0;
        bills = 0;
        vehicle = 0;
        gift = 0;
        transfer = 0;
        nonSavings = 0;
        savings = 0;
        savings2 = 0;
        for (int i = 0; i < c1; i++)
        {
	    if (actualExpenses[i].isPaid())
	    {
                switch (actualExpenses[i].getCategory()) {
                    case "Entertainment":
                        entertainment += actualExpenses[i].getAmount();
                        break;
                    case "Supplies":
                        supplies += actualExpenses[i].getAmount();
                        break;
                    case "Food":
                        food += actualExpenses[i].getAmount();
                        break;
                    case "Bills":
                        bills += actualExpenses[i].getAmount();
                        break;
                    case "Vehicle":
                        vehicle += actualExpenses[i].getAmount();
                        break;
                    case "Gift":
                        gift += actualExpenses[i].getAmount();
                        break;
                    case "Withdraw":
                        transfer += actualExpenses[i].getAmount();
                }           
                nonSavings += actualExpenses[i].getAmount();
	    }
	}
        
	entertainment2 = entertainment;
	supplies2 = supplies;
	food2 = food;
	bills2 = bills;
	vehicle2 = vehicle;
	nonSavings2 = nonSavings;
        
	// for (int i = 0; i < c3; i++)
	// {
	//     if (futureExpenses[i].getCategory().equals("Entertainment"))
	//     {
	// 	entertainment2 += futureExpenses[i].getAmount();					
	//     }
	//     else if (futureExpenses[i].getCategory().equals("Supplies"))
	//     {
	// 	supplies2 += futureExpenses[i].getAmount();					
	//     }
	//     else if (futureExpenses[i].getCategory().equals("Food"))
	//     {
	// 	food2 += futureExpenses[i].getAmount();					
	//     }
	//     else if (futureExpenses[i].getCategory().equals("Bills"))
	//     {
	// 	bills2 += futureExpenses[i].getAmount();					
	//     }
	//     else if (futureExpenses[i].getCategory().equals("Vehicle"))
	//     {
	// 	vehicle2 += futureExpenses[i].getAmount();					
	//     }	
	//     savings2 += futureExpenses[i].getAmount();
	// }	
        
	savings = Double.parseDouble(fmt3.format(savings));
	savings2 = Double.parseDouble(fmt3.format(savings2));		
    }
	
    public void display(int type)								// display info
    {
        DecimalFormat fmt = new DecimalFormat("00");
        DecimalFormat fmt5 = new DecimalFormat("$0000.00");
        if (type == 1)
        {
            System.out.println("Balance / Income\t\t\t\tExpense / Saving");
            System.out.println("****************\t\t\t\t****************");
            System.out.println(act1 + " = " + fmt5.format(actVal1) + "\t\t\t\tPaid Expenses = " + fmt5.format(totalCurrentPaidExpenses));
            System.out.println(act2 + " = " + fmt5.format(actVal2) + "\t\t\t\tUnpaid Expenses = " + fmt5.format(totalCurrentUnpaidExpenses));
            System.out.println(act3 + " = " + fmt5.format(actVal3) + "\t\t\t\t\tTotal Expenses = " + fmt5.format(totalCurrentExpenses));
            System.out.println(act4 + " = " + fmt5.format(actVal4) + "\t\t\t\tDifference = " + fmt5.format(difference));
            System.out.println(act5 + " = " + fmt5.format(actVal5) + "\t\t\t\tLeftover = " + fmt5.format(leftover));
            System.out.println("Total = " + fmt5.format(total) + "\t\t\t\tAccuracy = " + fmt5.format(accuracy));
            System.out.println("");
            System.out.println("Paid Payments = " + fmt5.format(totalPaidPayments) + "\t\t\tSavings = " + fmt5.format(totalSavings));
            System.out.println("Unpaid Payments = " + fmt5.format(totalUnpaidPayments) + "\t\t\tMonthly Savings = " + fmt5.format(totalMonthlySavings));
            System.out.println("Total Payments = " + fmt5.format(totalPayments) + "\t\t\tCurrent Exp and Save = " + fmt5.format(totalCurrentExpensesAndSavings));
            System.out.println("Total Monthly = " + fmt5.format(totalMonthly) + "\t\t\tDifference 2 = " + fmt5.format(difference2));
            System.out.println("\t\t\t\t\t\tUsable Balance = " + fmt5.format(usableValue));
            System.out.println("\t\t\t\t\t\tLeftover 2 = " + fmt5.format(leftover2));
        }
        else if (type == 2)
        {
            System.out.println("Payments\t\t\t\tMonthly");
            System.out.println("********\t\t\t\t*******");
            System.out.println("Paid = " + fmt5.format(totalPaidPayments) + "\t\t\t\tTotal = " + fmt5.format(totalMonthlyExpenses));
            System.out.println("Unpaid = " + fmt5.format(totalUnpaidPayments) + "\t\t\tLeftover = " + fmt5.format(totalLeftoverExpenses));
            System.out.println("Total = " + fmt5.format(totalPayments));
            System.out.println("Monthly = " + fmt5.format(totalMonthly) + "\t\t\tFuture");
            System.out.println("\t\t\t\t\t******");
            System.out.println("Actual\t\t\t\t\tTotal = " + fmt5.format(totalSavings));
            System.out.println("******\t\t\t\t\tMonthly = " + fmt5.format(totalMonthlySavings));
            System.out.println("Paid = " + fmt5.format(totalActualPaidExpenses));
            System.out.println("Unpaid = " + fmt5.format(totalActualUnpaidExpenses));
            System.out.println("Total = " + fmt5.format(totalActualExpenses));
            System.out.println();
            System.out.println();
            System.out.println();
        }
        else if (type == 3)
        {
            System.out.println("Current Value");
            System.out.println("*************");
            System.out.println("(1)  " + act1 + " = " + fmt5.format(actVal1));
            System.out.println("(2)  " + act2 + " = " + fmt5.format(actVal2));
            System.out.println("(3)  " + act3 + " = " + fmt5.format(actVal3));
            System.out.println("(4)  " + act4 + " = " + fmt5.format(actVal4));
            System.out.println("(5)  " + act5 + " = " + fmt5.format(actVal5));
            System.out.println("Total = " + fmt5.format(total));
            System.out.println("\n\n\n\n\n\n\n\n");
        }
        else if (type == 4)
        {
            System.out.println("Payments");
            System.out.println("********");

            for (int i = 0; i < c0; i++)
            {
                System.out.println(fmt.format(i) + ": " + payments[i]);
            }
            for (int i = 0; i < (15 - c0); i++)         // For spacing if number of entries is less than 15
                System.out.println();
        }
        else if (type == 5)
        {
            System.out.println("Actual Expenses");
            System.out.println("***************");
            for (int i = 0; i < c1; i++)
                System.out.println(fmt.format(i) + ": " + actualExpenses[i]);
            for (int i = 0; i < (15 - c1); i++)
                System.out.println();
        }
        else if (type == 6)
        {
            System.out.println("Monthly Expenses");
            System.out.println("****************");
            for (int i = 0; i < c2; i++)
                System.out.println(fmt.format(i) + ": " + monthlyExpenses[i]);
            for (int i = 0; i < (15 - c2); i++)
                System.out.println();
        }
        else if (type == 7)
        {
            System.out.println("Future Expenses");
            System.out.println("***************");
            for (int i = 0; i < c3; i++)
                System.out.println(fmt.format(i) + ": " + futureExpenses[i]);
            for (int i = 0; i < (15 - c3); i++)
                System.out.println();
        }
        else if (type == 8)
        {
            System.out.println("1: " + act1 + " = " + actVal1);
            System.out.println("2: " + act2 + " = " + actVal2);
            System.out.println("3: " + act3 + " = " + actVal3);
            System.out.println("4: " + act4 + " = " + actVal4);
            System.out.println("5: " + act5 + " = " + actVal5);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();		
        }
    }
	
    public void add(int cat)										// add entry
    {
	Scanner newScan = new Scanner(System.in);
	int spot = 0;
	String input;
		
	if (cat == 1)
	{
            if (c0 < MAX_ENTRY)                
            {
                Entry newObj = new Entry(false);
                int newDay = 0;
                int assign = 0;
                double newAmt = 0;
                double newEstAmt = 0;
                String newPaid;
                String newCat;
                String newCom;
                
    		do {
    		    System.out.print("Day: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newDay = Integer.parseInt(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 31.");
    			flag = true;
    		    }
    		} while (flag);               
                
    		do {
    		    System.out.print("Amount: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
    			flag = true;
    		    }
    	        } while (flag);                
                
    		do {
    		    System.out.print("Estimated Amount: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newEstAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
    			flag = true;
    		    }
    		} while (flag);               
                
                System.out.print("Paid?: ");
                newPaid = newScan.next();             
                
                if (newPaid.substring(0, 1).toLowerCase().equals("t"))
                {   
        	    do {
        	        System.out.print("Assign: ");
        		input = newScan.next();
        		flag = false;
        		try
        		{
        		    assign = Integer.parseInt(input);
        		}
        		catch (NumberFormatException e)
        		{
        		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
        		    flag = true;
        		}
        	    } while (flag);                    
                    
                    if (assign <= 0 || assign > 5)
                        assign = 1;
                }
                System.out.print("Category: ");
                newCat = newScan.next();
                System.out.print("Comment: ");
                newCom = newScan.next();
                newObj.setDate(month, newDay, year);
                newObj.setAmount(newAmt);
                newObj.setEstimateAmount(newEstAmt);
                newObj.setPaid(newPaid.substring(0, 1).toLowerCase().equals("t"));
                newObj.setAssign(assign);
                newObj.setCategory(newCat);
                newObj.setComment(newCom);
                newObj.determineAccuracy();
                if (assign == 1)
                    actVal1 += newAmt;
                else if (assign == 2)
                    actVal2 += newAmt;
                else if (assign == 3)
                    actVal3 += newAmt;
		else if (assign == 4)
		    actVal4 += newAmt;
		else if (assign == 5)
		    actVal5 += newAmt;
                for (int i = 0; i < c0; i++)
                {
                    if (newObj.getDay() >= payments[i].getDay())
                        spot++;
                }
                for (int i = c0; i > spot; i--)
                {
                    payments[i] = payments[i - 1];
                }
                payments[spot] = newObj;
                c0++;
                if (c0 % DEFAULT_ENTRY == 0 && c0 < MAX_ENTRY)      
                {
                    payments = expandEntry(payments, month, year);
                }
            }
            else
            {
                // c0 = 100, need to notify user why entry was not added
                System.out.println("[Value][Warning] Reached limit, unable to add more entries to payments.");               
            }
	}
	else if (cat == 2)
	{
            if (c1 < MAX_ENTRY)            
            {
                Entry newObj = new Entry(true);
                int newDay = 0;
                int assign = 0;
                double newAmt = 0;
                double newEstAmt = 0;
                String newPaid;
                String newCat;
                String newCom;
                
    		do {
    		    System.out.print("Day: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newDay = Integer.parseInt(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 31.");
    			flag = true;
    		    }
    		} while (flag);                
                
    		do {
    		    System.out.print("Amount: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
    			flag = true;
    		    }
    		} while (flag);                
                
    		do {
    		    System.out.print("Estimated Amount: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newEstAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number");
    			flag = true;
    		    }
    		} while (flag);                
                
                System.out.print("Paid?: ");
                newPaid = newScan.next();
                if (newPaid.substring(0, 1).toLowerCase().equals("t"))
                {          
        	    do {
        		System.out.print("Assign: ");
        		input = newScan.next();
        		flag = false;
        		try
        		{
        		    assign = Integer.parseInt(input);
        		}
        		catch (NumberFormatException e)
        		{
        		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
        		    flag = true;
        		}
        	    } while (flag);                                        
                    
                    if (assign <= 0 || assign > 5)
                        assign = 1;
                }
                System.out.print("Category: ");
                newCat = newScan.next();
                System.out.print("Comment: ");
                newCom = newScan.next();
                newObj.setDate(month, newDay, year);
                newObj.setAmount(newAmt);
                newObj.setEstimateAmount(newEstAmt);
                newObj.setPaid(newPaid.substring(0, 1).toLowerCase().equals("t"));
                newObj.setAssign(assign);
                newObj.setCategory(newCat);
                newObj.setComment(newCom);
                newObj.determineAccuracy();
                if (assign == 1)
                    actVal1 -= newAmt;
                else if (assign == 2)
                    actVal2 -= newAmt;
                else if (assign == 3)
                    actVal3 -= newAmt;
		else if (assign == 4)
		    actVal4 -= newAmt;
		else if (assign == 5)
		    actVal5 -= newAmt;
                for (int i = 0; i < c1; i++)
                {
                    if (newObj.getDay() >= actualExpenses[i].getDay())
                        spot++;
                }
                for (int i = c1; i > spot; i--)
                {
                    actualExpenses[i] = actualExpenses[i - 1];
                }
                actualExpenses[spot] = newObj;			
                c1++;
                if (c1 % DEFAULT_ENTRY == 0 && c1 < MAX_ENTRY)       
                {
                    actualExpenses = expandEntry(actualExpenses, month, year);
                }
            }
            else
            {
                // c1 = 100, need to notify user why entry was not added
                System.out.println("[Value][Warning] Reached limit, unable to add more entries to actualExpenses.");        
            }
        }	
        else if (cat == 3)
	{
            if (c2 < MAX_ENTRY)
            {
                Save newObj = new Save();
                double newAmt = 0;
                String newCat;
                String newCom;
 
    		do {
    		    System.out.print("Amount: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number");
    			flag = true;
    		    }
    		} while (flag);                
                
                System.out.print("Category: ");
                newCat = newScan.next();
                System.out.print("Comment: ");
                newCom = newScan.next();
                newObj.setAmount(newAmt);
                newObj.setAmountLeft(newAmt);
                newObj.setCategory(newCat);
                newObj.setComment(newCom);
                monthlyExpenses[c2] = newObj;
                c2++;
                if (c2 % DEFAULT_ENTRY == 0 && c2 < MAX_ENTRY)      
                {
                    monthlyExpenses = expandEntry(monthlyExpenses);
                }
            }
            else
            {
                // c2 = 100, need to notify user why entry was not added
                System.out.println("[Value][Warning] Reached limit, unable to add more entries to monthlyExpenses.");               
            }
	}
	else if (cat == 4)
	{
            if (c3 < MAX_ENTRY)
            {
                Saving newObj = new Saving();
                double newAmt = 0;
                int newTCount = 0;
                String newCat;
                String newCom;
                double newTAmt = 0;
                int newCount = 0;
                
    		do {
    		    System.out.print("Monthly Amount: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
    			flag = true;
    		    }
    		} while (flag);                
                
    		do {
    		    System.out.print("Total Counter: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newTCount = Integer.parseInt(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
    			flag = true;
    		    }
    		} while (flag);                
                
                System.out.print("Category: ");
                newCat = newScan.next();
                System.out.print("Comment: ");
                newCom = newScan.next();
                
    		do {
    		    System.out.print("Total Amount: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newTAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
    			flag = true;
    		    }
    		} while (flag);                
                
    		do {
    		    System.out.print("Current Counter: ");
    		    input = newScan.next();
    		    flag = false;
    		    try
    		    {
    			newCount = Integer.parseInt(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
    			flag = true;
    		    }
    		} while (flag);                
                
                newObj.setSave(newAmt);
                newObj.setTotalCount(newTCount);
                newObj.setCategory(newCat);
                newObj.setComment(newCom);
                newObj.setAmount(newTAmt);
                newObj.setCurrentCount(newCount);
                newObj.calDate(month, year);
                futureExpenses[c3] = newObj;
                c3++;
                if (c3 % DEFAULT_ENTRY == 0 && c3 < MAX_ENTRY)      
                {
                    futureExpenses = expandEntry(futureExpenses, month, year);
                }
            }
            else
            {
                // c3 = 100, need to notify user why entry was not added
                System.out.println("[Value][Warning] Reached limit, unable to add more entries to futureExpenses.");               
            }		
	}
    }
	
    public void edit(int cat)										// edit entry
    {
	Scanner newScan = new Scanner(System.in);
	String input;
	int spot = 0;
	int ind = 0;
		
	do {
	    System.out.print("Entry to edit: ");
	    input = newScan.next();
	    flag = false;
	    try
	    {
		spot = Integer.parseInt(input);
	    }
	    catch (NumberFormatException e)
	    {
		System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
		flag = true;
	    }
	} while (flag);                		
		
	if (cat == 1)
	{
	    if (spot >= 0 && spot < c0)
	    {
		while (ind != 7)
		{
                    System.out.println("\n###################################Entry Menu##################################\n");
                    System.out.println("0: Day = " + payments[spot].getDay());
                    System.out.println("1: Amount = " + payments[spot].getAmount());
                    System.out.println("2: Estimate Amount = " + payments[spot].getEstimateAmount());
                    System.out.println("3: Paid = " + payments[spot].isPaid());
                    System.out.println("4: Category = " + payments[spot].getCategory());
                    System.out.println("5: Assign = " + payments[spot].getAssign());
                    System.out.println("6: Comment = " + payments[spot].getComment());
                    System.out.println("7: Done");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("###################################Entry Menu##################################\n");				
					
	    	    do {
	    		System.out.print("Index to edit: ");
	    		input = newScan.next();
	    		flag = false;
	    		try
	    		{
	    		    ind = Integer.parseInt(input);
	    		}
	    		catch (NumberFormatException e)
	    		{
	    		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 0 and 7.");
	    		    flag = true;
	    		}
	    	    } while (flag);                					
					
		    if (ind == 0)
		    {
			int newDay = 0;
			int newSpot = 0;
						
		    	do {
		    	    System.out.print("Day: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newDay = Integer.parseInt(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 31.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
			payments[spot].setDay(newDay);
			for (int i = 0; i < c0; i++)
			{
			    if (payments[spot].getDay() >= payments[i].getDay() && i != spot)
			        newSpot++;
			}
			if (spot != newSpot)
			{
			    Entry newObj;
			    newObj = payments[spot];
			    if (spot >= 0 && spot < c0)
			    {
				for (int i = spot; i < (c0 - 1); i++)
				{
				    payments[i] = payments[i + 1];
				}
				payments[(c0 - 1)] = null;
			    }
			    for (int i = c0; i > newSpot; i--)
			    {
				payments[i] = payments[i - 1];
			    }
			    payments[newSpot] = newObj;
			    spot = newSpot;
			}
		    }
		    else if (ind == 1)
		    {
			double newAmt = 0;
						
		        do {
		    	    System.out.print("Amount: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newAmt = Double.parseDouble(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
                        if (payments[spot].isPaid())
                        {
                            if (payments[spot].getAssign() == 1)
                                actVal1 += (newAmt - payments[spot].getAmount());
                            else if (payments[spot].getAssign() == 2)
                                actVal2 += (newAmt - payments[spot].getAmount());
                            else if (payments[spot].getAssign() == 3)
                                actVal3 += (newAmt - payments[spot].getAmount());
			    else if (payments[spot].getAssign() == 4)
				actVal4 += (newAmt - payments[spot].getAmount());
			    else if (payments[spot].getAssign() == 5)
				actVal5 += (newAmt - payments[spot].getAmount());
                        }
			payments[spot].setAmount(newAmt);
			payments[spot].determineAccuracy();
		    }
		    else if (ind == 2)
		    {
			double newEstAmt = 0;
						
		        do {
		    	    System.out.print("Estimate Amount: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newEstAmt = Double.parseDouble(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
			payments[spot].setEstimateAmount(newEstAmt);
			payments[spot].determineAccuracy();
	            }
		    else if (ind == 3)
		    {
			String newPaid;
                        boolean oldPaid = payments[spot].isPaid();
			System.out.print("Paid: ");
			newPaid = newScan.next();
			payments[spot].setPaid(newPaid.toLowerCase().substring(0, 1).equals("t"));
			payments[spot].determineAccuracy();
                        if (payments[spot].isPaid() && !oldPaid)
                        {
                            int assign = 1;
                            
                	    do {
                	        System.out.print("Assign: ");
                	        input = newScan.next();
                	        flag = false;
                		try
                		{
                		    assign = Integer.parseInt(input);
                		}
                		catch (NumberFormatException e)
                		{
                		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                		    flag = true;
                		}
                	    } while (flag);  
                			
                            if (assign <= 0 || assign > 5)
                                assign = 1;                			
                            
                            payments[spot].setAssign(assign);
                            if (assign == 1)
                                actVal1 += payments[spot].getAmount();
                            else if (assign == 2)
                                actVal2 += payments[spot].getAmount();
                            else if (assign == 3)
                                actVal3 += payments[spot].getAmount();
			    else if (assign == 4)
				actVal4 += payments[spot].getAmount();
			    else if (assign == 5)
				actVal5 += payments[spot].getAmount();
                        }
                        else if (!payments[spot].isPaid() && oldPaid)
                        {
                            if (payments[spot].getAssign() == 1)
                                actVal1 -= payments[spot].getAmount();
                            else if (payments[spot].getAssign() == 2)
                                actVal2 -= payments[spot].getAmount();
                            else if (payments[spot].getAssign() == 3)
                                actVal3 -= payments[spot].getAmount();
			    else if (payments[spot].getAssign() == 4)
				actVal4 -= payments[spot].getAmount();
			    else if (payments[spot].getAssign() == 5)
				actVal5 -= payments[spot].getAmount();
                            payments[spot].setAssign(0);
                        }
		    }
		    else if (ind == 4)
		    {
			String newCat;
			System.out.print("Category: ");               						
			newCat = newScan.next();
			payments[spot].setCategory(newCat);
		    }
                    else if (ind == 5)
                    {
                        int assign = 0;
                        int oldAssign = payments[spot].getAssign();
                        if (!payments[spot].isPaid())
                        {
                            System.out.println("Assign = 0");
                        }
                        else
                        {
                	    do {
                		System.out.print("Assign: ");
                		input = newScan.next();
                		flag = false;
                		try
                		{
                		    assign = Integer.parseInt(input);
                		}
                		catch (NumberFormatException e)
                		{
                		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                		    flag = true;
                		}
                	    } while (flag);                                           
                            
                            if (assign <= 0 || assign > 5)
                                assign = 1;
                            payments[spot].setAssign(assign);
                            if (oldAssign == 1)
                                actVal1 -= payments[spot].getAmount();
                            else if (oldAssign == 2)
                                actVal2 -= payments[spot].getAmount();
                            else if (oldAssign == 3)
                                actVal3 -= payments[spot].getAmount();
			    else if (oldAssign == 4)
				actVal4 -= payments[spot].getAmount();
			    else if (oldAssign == 5)
				actVal5 -= payments[spot].getAmount();
                            if (assign == 1)
                                actVal1 += payments[spot].getAmount();
                            else if (assign == 2)
                                actVal2 += payments[spot].getAmount();
                            else if (assign == 3)
                                actVal3 += payments[spot].getAmount();
			    else if (assign == 4)
				actVal4 += payments[spot].getAmount();
			    else if (assign == 5)
				actVal5 += payments[spot].getAmount();
                        }
                        
                    }
		    else if (ind == 6)
		    {
			String newCom;
			System.out.print("Comment: ");
			newCom = newScan.next();
			payments[spot].setComment(newCom);
		    }	
	        }
	    }
        }
	else if (cat == 2)
        {
	    if (spot >= 0 && spot < c1)
	    {
		while (ind != 7)
		{
                    System.out.println("\n###################################Entry Menu##################################\n");
                    System.out.println("0: Day = " + actualExpenses[spot].getDay());
                    System.out.println("1: Amount = " + actualExpenses[spot].getAmount());
                    System.out.println("2: Estimate Amount = " + actualExpenses[spot].getEstimateAmount());
                    System.out.println("3: Paid = " + actualExpenses[spot].isPaid());
                    System.out.println("4: Category = " + actualExpenses[spot].getCategory());
                    System.out.println("5: Assign = " + actualExpenses[spot].getAssign());
                    System.out.println("6: Comment = " + actualExpenses[spot].getComment());
                    System.out.println("7: Done");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("###################################Entry Menu##################################\n");				

	    	    do {
	    		System.out.print("Index to edit: ");
	    		input = newScan.next();
	    		flag = false;
	    		try
	    		{
	    		    ind = Integer.parseInt(input);
	    		}
	    		catch (NumberFormatException e)
	    		{
	    		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 0 and 7.");
	    		    flag = true;
	    		}
	    	    } while (flag);                											
					
		    if (ind == 0)
		    {
			int newDay = 0;
			int newSpot = 0;
						
		    	do {
		    	    System.out.print("Day: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newDay = Integer.parseInt(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 31.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
			actualExpenses[spot].setDay(newDay);
			for (int i = 0; i < c1; i++)
			{
			    if (actualExpenses[spot].getDay() >= actualExpenses[i].getDay() && i != spot)
			        newSpot++;
			}
			if (spot != newSpot)
			{
			    Entry newObj;
			    newObj = actualExpenses[spot];
			    if (spot >= 0 && spot < c1)
			    {
			        for (int i = spot; i < (c1 - 1); i++)
			        {
				    actualExpenses[i] = actualExpenses[i + 1];
				}
				actualExpenses[(c1 - 1)] = null;
			    }
			    for (int i = c1; i > newSpot; i--)
			    {
				actualExpenses[i] = actualExpenses[i - 1];
			    }
		            actualExpenses[newSpot] = newObj;
			    spot = newSpot;
			    }
		    }
		    else if (ind == 1)
		    {
			double newAmt = 0;
						
		    	do {
		    	    System.out.print("Amount: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newAmt = Double.parseDouble(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    	        System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
                        if (actualExpenses[spot].isPaid())
                        {
                            if (actualExpenses[spot].getAssign() == 1)
                                actVal1 -= (newAmt - actualExpenses[spot].getAmount());
                            else if (actualExpenses[spot].getAssign() == 2)
                                actVal2 -= (newAmt - actualExpenses[spot].getAmount());
                            else if (actualExpenses[spot].getAssign() == 3)
                                actVal3 -= (newAmt - actualExpenses[spot].getAmount());
                            else if (actualExpenses[spot].getAssign() == 4)
                                actVal4 -= (newAmt - actualExpenses[spot].getAmount());
                            else if (actualExpenses[spot].getAssign() == 5)
                                actVal5 -= (newAmt - actualExpenses[spot].getAmount());								
                        }
			actualExpenses[spot].setAmount(newAmt);
			actualExpenses[spot].determineAccuracy();
	            }
	            else if (ind == 2)
		    {
		        double newEstAmt = 0;
						
                        do {
		    	    System.out.print("Estimate Amount: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    	        newEstAmt = Double.parseDouble(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
		        actualExpenses[spot].setEstimateAmount(newEstAmt);
			actualExpenses[spot].determineAccuracy();
		    }
		    else if (ind == 3)
		    {
			String newPaid;
                        boolean oldPaid = actualExpenses[spot].isPaid();
			System.out.print("Paid: ");
			newPaid = newScan.next();
			actualExpenses[spot].setPaid(newPaid.toLowerCase().substring(0, 1).equals("t"));
		        actualExpenses[spot].determineAccuracy();
                        if (actualExpenses[spot].isPaid() && !oldPaid)
                        {
                            int assign = 1;
                            
                            do {
                		System.out.print("Assign: ");
                		input = newScan.next();
                		flag = false;
                		try
                	        {
                	            assign = Integer.parseInt(input);
                		}
                		catch (NumberFormatException e)
                		{
                	            System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                		    flag = true;
                		}
                	    } while (flag);  
                			
                            if (assign <= 0 || assign > 5)
                		assign = 1;
                            
                            actualExpenses[spot].setAssign(assign);
                            if (assign == 1)
                                actVal1 -= actualExpenses[spot].getAmount();
                            else if (assign == 2)
                                actVal2 -= actualExpenses[spot].getAmount();
                            else if (assign == 3)
                                actVal3 -= actualExpenses[spot].getAmount();
                            else if (assign == 4)
                                actVal4 -= actualExpenses[spot].getAmount();
                            else if (assign == 5)
                                actVal5 -= actualExpenses[spot].getAmount();							
                        }
                        else if (!actualExpenses[spot].isPaid() && oldPaid)
                        {
                            if (actualExpenses[spot].getAssign() == 1)
                                actVal1 += actualExpenses[spot].getAmount();
                            else if (actualExpenses[spot].getAssign() == 2)
                                actVal2 += actualExpenses[spot].getAmount();
                            else if (actualExpenses[spot].getAssign() == 3)
                                actVal3 += actualExpenses[spot].getAmount();
                            else if (actualExpenses[spot].getAssign() == 4)
                                actVal4 += actualExpenses[spot].getAmount();
                            else if (actualExpenses[spot].getAssign() == 5)
                                actVal5 += actualExpenses[spot].getAmount();								
                            actualExpenses[spot].setAssign(0);
                        }
		    }
		    else if (ind == 4)
		    {
			String newCat;
			System.out.print("Category: ");
			newCat = newScan.next();
			actualExpenses[spot].setCategory(newCat);
		    }
                    else if (ind == 5)
                    {
                        int assign = 0;
                        int oldAssign = actualExpenses[spot].getAssign();
                        if (!actualExpenses[spot].isPaid())
                        {
                            System.out.println("Assign = 0");
                        }
                        else
                        {
                	    do {
                	        System.out.print("Assign: ");
                		input = newScan.next();
                		flag = false;
                		try
                		{
                		    assign = Integer.parseInt(input);
                		}
                	        catch (NumberFormatException e)
                		{
                		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                		    flag = true;
                		}
                	    } while (flag);                                            
                            
                            if (assign <= 0 || assign > 5)
                                assign = 1;
                            actualExpenses[spot].setAssign(assign);
                            if (oldAssign == 1)
                                actVal1 += actualExpenses[spot].getAmount();
                            else if (oldAssign == 2)
                                actVal2 += actualExpenses[spot].getAmount();
                            else if (oldAssign == 3)
                                actVal3 += actualExpenses[spot].getAmount();
                            else if (oldAssign == 4)
                                actVal4 += actualExpenses[spot].getAmount();
                            else if (oldAssign == 5)
                                actVal5 += actualExpenses[spot].getAmount();								
                            if (assign == 1)
                                actVal1 -= actualExpenses[spot].getAmount();
                            else if (assign == 2)
                                actVal2 -= actualExpenses[spot].getAmount();
                            else if (assign == 3)
                                actVal3 -= actualExpenses[spot].getAmount();
                            else if (assign == 4)
                                actVal4 -= actualExpenses[spot].getAmount();
                            else if (assign == 5)
                                actVal5 -= actualExpenses[spot].getAmount();								
                        }
                        
                    }
		    else if (ind == 6)
		    {
			String newCom;
			System.out.print("Comment: ");
			newCom = newScan.next();
			actualExpenses[spot].setComment(newCom);
		    }	
		}
	    }	
	}
	else if (cat == 3)
	{
	    if (spot >= 0 && spot < c2)
	    {
		while (ind != 4)
		{
                    System.out.println("\n###################################Entry Menu##################################\n");
                    System.out.println("0: Amount = " + monthlyExpenses[spot].getAmount());
                    System.out.println("1: Amount Left = " + monthlyExpenses[spot].getAmountLeft());
                    System.out.println("2: Category = " + monthlyExpenses[spot].getCategory());
                    System.out.println("3: Comment = " + monthlyExpenses[spot].getComment());
                    System.out.println("4: Done");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("###################################Entry Menu##################################\n");				
					
	    	    do {
	    		System.out.print("Index to edit: ");
	    		input = newScan.next();
	    		flag = false;
	    		try
	    		{
	    		    ind = Integer.parseInt(input);
	    		}
	    		catch (NumberFormatException e)
	    		{
	    		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 0 and 4.");
	    		    flag = true;
	    		}
	    	    } while (flag);                											
					
		    if (ind == 0)
		    {
			double newAmt = 0;
						
		        do {
		    	    System.out.print("Amount: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newAmt = Double.parseDouble(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
                            }
		    	} while (flag);                						
						
			monthlyExpenses[spot].setAmount(newAmt);
		    }
		    else if (ind == 1)
		    {
			double newAmtLft = 0;
						
		        do {
		    	    System.out.print("Amount Left: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newAmtLft = Double.parseDouble(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
			monthlyExpenses[spot].setAmountLeft(newAmtLft);
		    }
		    else if (ind == 2)
                    {
			String newCat;
			System.out.print("Category: ");
			newCat = newScan.next();
			monthlyExpenses[spot].setCategory(newCat);
		    }
		    else if (ind == 3)
		    {
			String newCom;
			System.out.print("Comment: ");
			newCom = newScan.next();
			monthlyExpenses[spot].setComment(newCom);
		    }	
		}
	    }	
	}
	else if (cat == 4)
	{
	    if (spot >= 0 && spot < c3)
	    {
		while (ind != 7)
		{
                    System.out.println("\n###################################Entry Menu##################################\n");
                    System.out.println("0: Save = " + futureExpenses[spot].getSave());
                    System.out.println("1: Total Count = " + futureExpenses[spot].getTotalCount());
                    System.out.println("2: Category = " + futureExpenses[spot].getCategory());
                    System.out.println("3: Comment = " + futureExpenses[spot].getComment());
                    System.out.println("4: Amount = " + futureExpenses[spot].getAmount());
                    System.out.println("5: Current Count = " + futureExpenses[spot].getCurrentCount());
                    System.out.println("6: Increment = " + futureExpenses[spot].isInc());
                    System.out.println("7: Done");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("###################################Entry Menu##################################\n");				
					
	    	    do {
	    		System.out.print("Index to edit: ");
	    		input = newScan.next();
	    		flag = false;
	    		try
	    		{
	    		    ind = Integer.parseInt(input);
	    		}
	    		catch (NumberFormatException e)
	    		{
	    		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 0 and 6.");
	    		    flag = true;
	    		}
	    	    } while (flag);                											
					
		    if (ind == 0)
		    {
		        double newSave = 0;
						
		    	do {
		    	    System.out.print("Save: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newSave = Double.parseDouble(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
			// totalMonthlySavings += (newSave - futureExpenses[spot].getSave());
			futureExpenses[spot].setSave(newSave);
		    }
		    else if (ind == 1)
		    {
			int newTotCount = 0;
						
		    	do {
		    	    System.out.print("Total Count:");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newTotCount = Integer.parseInt(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
			futureExpenses[spot].setTotalCount(newTotCount);
			futureExpenses[spot].calDate(month, year);
		    }
		    else if (ind == 2)
		    {
			String newCat;
			System.out.print("Category: ");
			newCat = newScan.next();
			futureExpenses[spot].setCategory(newCat);
		    }
		    else if (ind == 3)
		    {
			String newCom;
			System.out.print("Comment: ");
			newCom = newScan.next();
			futureExpenses[spot].setComment(newCom);
		    }
		    else if (ind == 4)
		    {
			double newAmt = 0;
						
		        do {
		    	    System.out.print("Amount: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newAmt = Double.parseDouble(input);
		    	    }
		    	    catch (NumberFormatException e)
                            {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a number");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
			futureExpenses[spot].setAmount(newAmt);
		    }
		    else if (ind == 5)
		    {
			int newCurCount = 0;
						
		        do {
		    	    System.out.print("Current Count: ");
		    	    input = newScan.next();
		    	    flag = false;
		    	    try
		    	    {
		    		newCurCount = Integer.parseInt(input);
		    	    }
		    	    catch (NumberFormatException e)
		    	    {
		    		System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
		    		flag = true;
		    	    }
		    	} while (flag);                						
						
			futureExpenses[spot].setCurrentCount(newCurCount);
			futureExpenses[spot].calDate(month, year);
		    }
 		    else if (ind == 6)
		    {
			String newInc;
                        boolean oldInc = futureExpenses[spot].isInc();
			System.out.print("Inc: ");
			newInc = newScan.next();
			futureExpenses[spot].setInc(newInc.toLowerCase().substring(0, 1).equals("t"));
                        // if (oldInc && !futureExpenses[spot].isInc())
                        //     totalMonthlySavings -= futureExpenses[spot].getSave();
                        // else if (!oldInc && futureExpenses[spot].isInc())
                        //     totalMonthlySavings += futureExpenses[spot].getSave();      
		    }  
		}
	    }
	}
	update();
    }
	
    public void delete(int cat)										// delete entry
    {
	Scanner newScan = new Scanner(System.in);
	String input;
	int spot = 0;
		
	do {
	    System.out.print("Index to delete: ");
	    input = newScan.next();
	    flag = false;
	    try
	    {
		spot = Integer.parseInt(input);
	    }
	    catch (NumberFormatException e)
	    {
		System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
		flag = true;
	    }
	} while (flag);                		
		
	if (cat == 1)
	{
	    if (spot >= 0 && spot < c0)
	    {
                if (payments[spot].isPaid())
                {
                    if (payments[spot].getAssign() == 1)
                        actVal1 -= payments[spot].getAmount();
                    else if (payments[spot].getAssign() == 2)
                        actVal2 -= payments[spot].getAmount();
                    else if (payments[spot].getAssign() == 3)
                        actVal3 -= payments[spot].getAmount();
                    else if (payments[spot].getAssign() == 4)
                        actVal4 -= payments[spot].getAmount();
                    else if (payments[spot].getAssign() == 5)
                        actVal5 -= payments[spot].getAmount();						
                }
		c0--;
		for (int i = spot; i < c0; i++)
		{
		    payments[i] = payments[i + 1];
		}
	        payments[c0] = null;
	    }
        }
	else if (cat == 2)
	{
	    if (spot >= 0 && spot < c1)
	    {
                if (actualExpenses[spot].isPaid())
                {
                    if (actualExpenses[spot].getAssign() == 1)
                        actVal1 += actualExpenses[spot].getAmount();
                    else if (actualExpenses[spot].getAssign() == 2)
                        actVal2 += actualExpenses[spot].getAmount();
                    else if (actualExpenses[spot].getAssign() == 3)
                        actVal3 += actualExpenses[spot].getAmount();
                    else if (actualExpenses[spot].getAssign() == 4)
                        actVal4 += actualExpenses[spot].getAmount();
                    else if (actualExpenses[spot].getAssign() == 5)
                        actVal5 += actualExpenses[spot].getAmount();					
                }
		c1--;
		for (int i = spot; i < c1; i++)
		{
		    actualExpenses[i] = actualExpenses[i + 1];
		}
		actualExpenses[c1] = null;
	    }		
	}	
	else if (cat == 3)
	{
	    if (spot >= 0 && spot < c2)
	    {
		c2--;
		for (int i = spot; i < c2; i++)
		{
		    monthlyExpenses[i] = monthlyExpenses[i + 1];
		}
		monthlyExpenses[c2] = null;
	    }
	}
	else if (cat == 4)
	{
	    if (spot >= 0 && spot < c3)
	    {
                // if (futureExpenses[spot].isInc())
                // {
                //     totalMonthlySavings -= futureExpenses[spot].getSave();
                // }
		c3--;
		for (int i = spot; i < c3; i++)
		{
		    futureExpenses[i] = futureExpenses[i + 1];
		}
		futureExpenses[c3] = null;
	    }		
        }
    }
    
    public void function(int type)									// functions
    {
        Scanner scan = new Scanner(System.in);
        String input;
        if (type == 0)
        {
            int source = 1;
            int dest = 1;
            double amt = 0;
            do {
                System.out.print("Source = ");
                input = scan.next();
                flag = false;
                try
                {
                    source = Integer.parseInt(input);
                }
                catch (NumberFormatException e)
                {
                    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
                    flag = true;
                }
            } while (flag);
            if (source < 1 || source > 5)				// minor error checking for now
                source = 1;
			
	    do {
		System.out.print("Destination = ");
		input = scan.next();
		flag = false;
		try
		{
		    dest = Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
		    flag = true;
		}
	    } while (flag);                                    			
	    if (dest < 1 || dest > 5)					// minor error checking for now
	        dest = 1;
				
	    do {
		System.out.print("Amount = ");
		input = scan.next();
		flag = false;
		try
		{
		    amt = Double.parseDouble(input);
		}
		catch (NumberFormatException e)
		{
		    System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
		    flag = true;
		}
	    } while (flag);    
			
	    if (c1 < MAX_ENTRY)
	    {					
		Entry fromObj = new Entry(true);
		Entry toObj = new Entry(true);
		int newDay = 0;
		int spot = 0;
						
		do {
		    System.out.print("Day: ");
		    input = scan.next();
		    flag = false;
		    try
		    {
			newDay = Integer.parseInt(input);
		    }
		    catch (NumberFormatException e)
		    {
			System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 31.");
			flag = true;
		    }
		} while (flag);                                    
						
		fromObj.setDate(month, newDay, year);
		toObj.setDate(month, newDay, year);
					
                fromObj.setAmount(amt);
                fromObj.setEstimateAmount(amt);
                fromObj.setPaid(true);
                fromObj.setAssign(source);
                fromObj.determineAccuracy();
                fromObj.setCategory("Withdraw");
                fromObj.setComment("Transfer_from_source_" + source);
                toObj.setAmount(-amt);
                toObj.setEstimateAmount(-amt);
                toObj.setPaid(true);
                toObj.setAssign(dest);
                toObj.determineAccuracy();
                toObj.setCategory("Deposit");
                toObj.setComment("Transfer_to_destination_" + dest);

                for (int i = 0; i < c1; i++)
                {
		    if (fromObj.getDay() >= actualExpenses[i].getDay())
			spot++;
		}
		for (int i = c1; i > spot; i--)
		{
		    actualExpenses[i] = actualExpenses[i - 1];
		}
		actualExpenses[spot] = fromObj;
		c1++;
		if (c1 % DEFAULT_ENTRY == 0 && c1 < MAX_ENTRY)    
		{
		    actualExpenses = expandEntry(actualExpenses, month, year);
		}
		spot = 0;
		for (int i = 0; i < c1; i++)
		{
		    if (toObj.getDay() >= actualExpenses[i].getDay())
			spot++;
		}
		for (int i = c1; i > spot; i--)
		{
		    actualExpenses[i] = actualExpenses[i - 1];
		}
		actualExpenses[spot] = toObj;
		c1++;
		if (c1 % DEFAULT_ENTRY == 0 && c1 < MAX_ENTRY)    
		{
		    actualExpenses = expandEntry(actualExpenses, month, year);
                }		
	    }
            else
            {
                // c1 = 100, need to notify user why transfer was not made
                System.out.println("[Value][Warning] Reached limit, unable to create transfer.");              
            }
			
	    if (source == 1)
                actVal1 -= amt;
	    else if (source == 2)
		actVal2 -= amt;
	    else if (source == 3)
		actVal3 -= amt;
	    else if (source == 4)
		actVal4 -= amt;
	    else if (source == 5)
		actVal5 -= amt;		
	    if (dest == 1)
		actVal1 += amt;
	    else if (dest == 2)
		actVal2 += amt;
	    else if (dest == 3)
		actVal3 += amt;
	    else if (dest == 4)
		actVal4 += amt;
	    else if (dest == 5)
		actVal5 += amt;		
	}
	else if (type == 1)
	{
	    int index = 0;
            int assign = 1;
	    double newAmt = 0;
			
	    do {
		System.out.print("Entry being paid: ");
		input = scan.next();
		flag = false;
		try
		{
		    index = Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
		    flag = true;
		}
	    } while (flag);                			
			
	    if (index >= 0 && index < c0 && !payments[index].isPaid())
	    {
    	        do {
    		    System.out.print("Amount: ");
    		    input = scan.next();
    		    flag = false;
    		    try
    		    {
    			newAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
    			flag = true;
    		    }
    		} while (flag);                				
                
    		do {
    		    System.out.print("Assign: ");
    		    input = scan.next();
    		    flag = false;
    		    try
    		    {
    			assign = Integer.parseInt(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
    			flag = true;
    		    }
    		} while (flag);                                
                
                if (assign <= 0 || assign > 5)
                    assign = 1;
                if (assign == 1)
                    actVal1 += newAmt;
                else if (assign == 2)
                    actVal2 += newAmt;
                else if (assign == 3)
                    actVal3 += newAmt;
                else if (assign == 4)
                    actVal4 += newAmt;
                else if (assign == 5)
                    actVal5 += newAmt;					
		payments[index].setAmount(newAmt);
		payments[index].paid(assign);
	    }
	}
	else if (type == 2)
	{
	    int index = 0;
            int assign = 1;
	    double newAmt = 0;
			
	    do {
		System.out.print("Entry being paid: ");
		input = scan.next();
		flag = false;
		try
		{
		    index = Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
		    flag = true;
		}
	    } while (flag);                			
			
	    if (index >= 0 && index < c1 && !actualExpenses[index].isPaid())
	    {		
    	        do {
    		    System.out.print("Amount: ");
    		    input = scan.next();
    		    flag = false;
    		    try
    		    {
    			newAmt = Double.parseDouble(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
    			flag = true;
    		    }
    		} while (flag);                				
                
    		do {
    		    System.out.print("Assign: ");
    		    input = scan.next();
    		    flag = false;
    		    try
    		    {
    			assign = Integer.parseInt(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
    			flag = true;
    		    }
    		} while (flag);                                
                
                if (assign <= 0 || assign > 5)
                    assign = 1;
                if (assign == 1)
                    actVal1 -= newAmt;
                else if (assign == 2)
                    actVal2 -= newAmt;
                else if (assign == 3)
                    actVal3 -= newAmt;
                else if (assign == 4)
                    actVal4 -= newAmt;
                else if (assign == 5)
                    actVal5 -= newAmt;					
		actualExpenses[index].setAmount(newAmt);
		actualExpenses[index].paid(assign);		
	    }
	}
        else if (type == 3)
	{
            if (c1 < MAX_ENTRY)     
            {
                int index = 0;
                int assign = 1;
                double newAmt = 0;
                
    		do {
    		    System.out.print("Entry being associated: ");
    		    input = scan.next();
    		    flag = false;
    		    try
    		    {
    			index = Integer.parseInt(input);
    		    }
    		    catch (NumberFormatException e)
    		    {
    			System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
    			flag = true;
    		    }
    		} while (flag);                                
                
                if (index >= 0 && index < c2)
                {
                    Entry newObj = new Entry(true);
                    int newDay = 0;
                    int spot = 0;
                    
        	    do {
        		System.out.print("Day: ");
        		input = scan.next();
        		flag = false;
        		try
        		{
        		    newDay = Integer.parseInt(input);
                        }		
        		catch (NumberFormatException e)
        		{
        		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 31.");
        		    flag = true;
        		}
        	    } while(flag);                                    
                    
                    newObj.setDate(month, newDay, year);
                    
        	    do {
        		System.out.print("Amount: ");
        		input = scan.next();
        		flag = false;
        		try
        		{
        		    newAmt = Double.parseDouble(input);
        		}
        		catch (NumberFormatException e)
        		{
        		    System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
        		    flag = true;
        		}
        	    } while (flag);                                    
                    
        	    do {
        		System.out.print("Assign: ");
        		input = scan.next();
        		flag = false;
        		try
        		{
        		    assign = Integer.parseInt(input);
        		}
        		catch (NumberFormatException e)
        		{
        		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
        		    flag = true;
        		}
        	    } while (flag);                                    
                    
                    if (assign <= 0 || assign > 5)
                        assign = 1;
                    if (assign == 1)
                        actVal1	-= newAmt;
                    else if (assign == 2)
                        actVal2 -= newAmt;
                    else if (assign == 3)
                        actVal3	-= newAmt;
                    else if (assign == 4)
                        actVal4	-= newAmt;
                    else if (assign == 5)
                        actVal5	-= newAmt;						
                    monthlyExpenses[index].update(newAmt);
                    newObj.setAmount(newAmt);
                    newObj.setEstimateAmount(newAmt);
                    newObj.setPaid(true);
                    newObj.setAssign(assign);
                    newObj.determineAccuracy();
                    newObj.setCategory(monthlyExpenses[index].getCategory());
                    newObj.setComment(monthlyExpenses[index].getComment());
                    
                    for (int i = 0; i < c1; i++)
                    {
                        if (newObj.getDay() >= actualExpenses[i].getDay())
                            spot++;
                    }
                    for (int i = c1; i > spot; i--)
                    {
                        actualExpenses[i] = actualExpenses[i - 1];
                    }
                    actualExpenses[spot] = newObj;
                    c1++;
                    if (c1 % DEFAULT_ENTRY == 0 && c1 < MAX_ENTRY)    
                    {
                        actualExpenses = expandEntry(actualExpenses, month, year);
                    }
                }
            }
            else
            {
                // c1 = 100, need to notify user why entry was not associated
                System.out.println("[Value][Warning] Reached limit, unable to associate monthlyExpenses entry into "
                                   + "\n" + "actualExpenses.");              
            }
	}
	else if (type == 4)
	{
            int index = 0;
            int assign = 1;
            double newAmt = 0;
            
	    do {
	        System.out.print("Entry being updated: ");
		input = scan.next();
		flag = false;
		try
		{
		    index = Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
		    System.err.println("\n[Value][Error] Incorrect input type, enter a digit.");
		    flag = true;
		}
	    } while (flag);                            
            
            if (index >= 0 && index < c3)
            {
                if (futureExpenses[index].getCurrentCount() == futureExpenses[index].getTotalCount())   
                {                                   // if future expense is ready to be paid
                    if (c1 < MAX_ENTRY)         
                    {
                        Entry newObj = new Entry(true);
                        int newDay = 0;
                        int spot = 0;
                        
            		do {
            		    System.out.print("Day: ");
            		    input = scan.next();
            		    flag = false;
            		    try
            		    {
            			newDay = Integer.parseInt(input);
            		    }
            		    catch (NumberFormatException e)
            		    {
            			System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 31.");
            			flag = true;
            		    }
            		} while (flag);                                        
                        
                        newObj.setDate(month, newDay, year);
                        
            		do {
            		    System.out.print("Amount: ");
            		    input = scan.next();
            		    flag = false;
            		    try
            		    {
            			newAmt = Double.parseDouble(input);
            		    }
            		    catch (NumberFormatException e)
            		    {
            			System.err.println("\n[Value][Error] Incorrect input type, enter a number.");
            			flag = true;
            		    }
            		} while (flag);                                        
                        
            		do {
            		    System.out.print("Assign: ");
            		    input = scan.next();
            		    flag = false;
            		    try
            		    {
            			assign = Integer.parseInt(input);
            		    }
            		    catch (NumberFormatException e)
            		    {
            			System.err.println("\n[Value][Error] Incorrect input type, enter a digit between 1 and 5.");
            			flag = true;
            		    }
            		} while (flag);                                        
                        
                        if (assign <= 0 || assign > 5)
                            assign = 1;
                        if (assign == 1)
                            actVal1 -= newAmt;
                        else if (assign == 2)
                            actVal2 -= newAmt;
                        else if (assign == 3)
                            actVal3 -= newAmt;
                        else if (assign == 4)
                            actVal4 -= newAmt;
                        else if (assign == 5)
                            actVal5 -= newAmt;							
                        newObj.setAmount(newAmt);
                        newObj.setEstimateAmount(futureExpenses[index].getAmount());
                        newObj.setPaid(true);
                        newObj.setAssign(assign);
                        newObj.determineAccuracy();
                        newObj.setCategory(futureExpenses[index].getCategory());
                        newObj.setComment(futureExpenses[index].getComment());
                        
                        for (int i = 0; i < c1; i++)
                        {
                            if (newObj.getDay() >= actualExpenses[i].getDay())
                                spot++;
                        }
                        for (int i = c1; i > spot; i--)
                        {
                            actualExpenses[i] = actualExpenses[i - 1];
                        }
                        actualExpenses[spot] = newObj;
                        c1++;
                        if (c1 % DEFAULT_ENTRY == 0 && c1 < MAX_ENTRY)     
                        {
                            actualExpenses = expandEntry(actualExpenses, month, year);
                        }          
                        // if (futureExpenses[index].isInc())
                        //     totalMonthlySavings -= futureExpenses[index].getSave();
                        futureExpenses[index].reset(month, year);
                    }
                    else 
                    {
                        // need to notify user why entry was not created / why future expense was not processed and reset
                        System.out.println("[Value][Warning] Reached limit, unable to associate futureExpenses entry into "
                                           + "\n" + "actualExpenses.");                        
                    }
                }
                else            // else, increment counter by 1 mo.
                {
                    // if (!futureExpenses[index].isInc())
                    // {
                    //     totalMonthlySavings += futureExpenses[index].getSave();
                    // }
                    futureExpenses[index].update();
                    futureExpenses[index].calDate(month, year);
                }
            }
	}
	else if (type == 5)
	{
            if (end == false)
            {
                end = true;
                for (int i = 0; i < c0; i++)
                {
                    if (!payments[i].isPaid())
                    {
                        payments[i].setAmount(0);  
                        payments[i].setAccuracy();
                    }
                }
                for (int i = 0; i < c1; i++)
                {
                    if (!actualExpenses[i].isPaid())
                    {
                        actualExpenses[i].setAmount(0);
                        actualExpenses[i].setAccuracy();
                    }
                }
                update();
            }
	}
    }
	
    public Entry getPayments(int c)								// getters...
    {
        int i = 0;
	if (c >= 0 && c < c0)
	    i = c;
	return payments[i];
    }
	
    public Entry getActualExpenses(int c)
    {
	int i = 0;
	if (c >= 0 && c < c1)
	    i = c;
	return actualExpenses[c];
    }
	
    public Save getMonthlyExpenses(int c)
    {
	int i = 0;
	if (c >= 0 && c < c2)
	    i = c;
	return monthlyExpenses[c];
    }
	
    public Saving getFutureExpenses(int c)
    {
	int i = 0;
	if (c >= 0 && c < c3)
	    i = c;
	return futureExpenses[c];
    }
	
    public int getC0()
    {
        return c0;
    }
    
    public int getC1()
    {
	return c1;
    }
    
    public int getC2()
    {
        return c2;
    }

    public int getC3()
    {
        return c3;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }

    public double getStart()
    {
        return start;
    }

    public double getTotal()
    {
        return total;
    }
    
    public double getActVal1()
    {
        return actVal1;
    }

    public double getActVal2()
    {
        return actVal2;
    }

    public double getActVal3()
    {
        return actVal3;
    }

    public double getActVal4()
    {
        return actVal4;
    }

    public double getActVal5()
    {
        return actVal5;
    }	

    public String getAct1()
    {
        return act1;
    }

    public String getAct2()
    {
        return act2;
    }
    
    public String getAct3()
    {
        return act3;
    }

    public String getAct4()
    {
        return act4;
    }

    public String getAct5()
    {
        return act5;
    }

    public double getTotalPaidPayments()
    {
        return totalPaidPayments;
    }

    public double getTotalUnpaidPayments()
    {
        return totalUnpaidPayments;
    }

    public double getTotalPayments()
    {
        return totalPayments;
    }

    public double getTotalMonthly()
    {
        return totalMonthly;
    }
    
    public double getTotalActualPaidExpenses()
    {
        return totalActualPaidExpenses;
    }

    public double getTotalActualUnpaidExpenses()
    {
        return totalActualUnpaidExpenses;
    }

    public double getTotalActualExpenses()
    {
        return totalActualExpenses;
    }

    public double getTotalMonthlyExpenses()
    {
        return totalMonthlyExpenses;
    }

    public double getTotalLeftoverExpenses()
    {
        return totalLeftoverExpenses;
    }

    public double getTotalCurrentPaidExpenses()
    {
        return totalCurrentPaidExpenses;
    }

    public double getTotalCurrentUnpaidExpenses()
    {
        return totalCurrentUnpaidExpenses;
    }
    
    public double getTotalCurrentExpenses()
    {
        return totalCurrentExpenses;
    }

    public double getDifference()
    {
        return difference;
    }

    public double getLeftover()
    {
        return leftover;
    }

    public double getAccuracy()
    {
        return accuracy;
    }

    public double getTotalSavings()
    {
        return totalSavings;
    }

    public double getTotalMonthlySavings()
    {
        return totalMonthlySavings;
    }

    public double getTotalCurrentExpensesAndSavings()
    {
        return totalCurrentExpensesAndSavings;
    }
    
    public double getDifference2()
    {
        return difference2;
    }

    public double getUsableValue()
    {
        return usableValue;
    }

    public double getLeftover2()
    {
        return leftover2;
    }

    public double getEntertainment()
    {
        return entertainment;
    }

    public double getSupplies()
    {
        return supplies;
    }

    public double getFood()
    {
        return food;
    }

    public double getBills()
    {
        return bills;
    }
    
    public double getVehicle()
    {
        return vehicle;
    }

    public double getGift()
    {
        return gift;
    }

    public double getTransfer()
    {
        return transfer;
    }

    public double getSavings()
    {
        return savings;
    }

    public double getNonSavings()
    {
        return nonSavings;
    }

    public double getEntertainment2()
    {
        return entertainment2;
    }

    public double getSupplies2()
    {
        return supplies2;
    }
    
    public double getFood2()
    {
        return food2;
    }

    public double getBills2()
    {
        return bills2;
    }

    public double getVehicle2()
    {
        return vehicle2;
    }

    public double getSavings2()
    {
        return savings2;
    }

    public double getNonSavings2()
    {
        return nonSavings2;
    }

    public double getAcc1()
    {
        return acc1;
    }

    public double getAcc2()
    {
        return acc2;
    }
    
    public double getAcc3()
    {
        return acc3;
    }
    
    public boolean isEnd()
    {
        return end;
    }
    
    public void setMonth(int m)									// setters...
    {
        month = m;
    }

    public void setYear(int y)
    {
        year = y;
    }

    public void setStart(double s)
    {
        start = s;
    }

    public void setTotal(double t)
    {
        total = t;
    }

    public void calTotal()
    {
        total = actVal1 + actVal2 + actVal3 + actVal4 + actVal5;
    }

    public void setAct1(String act)
    {
        act1 = act;
    }

    public void setAct2(String act)
    {
        act2 = act;
    }
    public void setAct3(String act)
    {
        act3 = act;
    }
    public void setAct4(String act)
    {
        act4 = act;
    }
    public void setAct5(String act)
    {
        act5 = act;
    }	

    public void setActVal1(double val)
    {
        actVal1 = val;
    }

    public void setActVal2(double val)
    {
        actVal2 = val;
    }

    public void setActVal3(double val)
    {
        actVal3 = val;
    }

    public void setActVal4(double val)
    {
        actVal4 = val;
    }
    public void setActVal5(double val)
    {
        actVal5 = val;
    }	

    public void setTotalPaidPayments(double t)
    {
        totalPaidPayments = t;
    }

    public void setTotalUnpaidPayments(double t)
    {
        totalUnpaidPayments = t;
    }

    public void calTotalUnpaidPayments()
    {
	totalUnpaidPayments = 0;
	for (int i = 0; i < c0; i++)
	{
	    if (!payments[i].isPaid() && payments[i].getAccuracy() == 0)
	        totalUnpaidPayments += payments[i].getEstimateAmount();
	}
    }
	
    public void setTotalPayments(double t)
    {
        totalPayments = t;
    }

    public void setTotalMonthly(double t)
    {
        totalMonthly = t;
    }

    public void calTotalMonthly()
    {
        totalMonthly = total + totalUnpaidPayments;
    }

    public void setTotalActualPaidExpenses(double t)
    {
        totalActualPaidExpenses = t;
    }

    public void setTotalActualUnpaidExpenses(double t)
    {
        totalActualUnpaidExpenses = t;
    }

    public void setTotalActualExpenses(double t)
    {
        totalActualExpenses = t;
    }

    public void setTotalMonthlyExpenses(double t)
    {
        totalMonthlyExpenses = t;
    }

    public void setTotalLeftoverExpenses(double t)
    {
        totalLeftoverExpenses = t;
    }

    public void setTotalCurrentPaidExpenses(double t)
    {
        totalCurrentPaidExpenses = t;
    }

    public void setTotalCurrentUnpaidExpenses(double t)
    {
        totalCurrentUnpaidExpenses = t;
    }

    public void setTotalCurrentExpenses(double t)
    {
        totalCurrentExpenses = t;
    }

    public void setDifference(double d)
    {
        difference = d;
    }

    public void setLeftover(double l)
    {
        leftover = l;
    }

    public void calLeftover()
    {
        leftover = totalMonthly - totalCurrentUnpaidExpenses;
    }

    public void setAccuracy(double a)
    {
        accuracy = a;		
    }

    public void calAccuracy()
    {
        accuracy = totalMonthly - totalCurrentUnpaidExpenses;
    }

    public void setTotalSavings(double t)
    {
        totalSavings = t;
    }

    public void setTotalMonthlySavings(double t)
    {
        totalMonthlySavings = t;
    }
    
    public void calTotalMonthlySavings()
    {
        totalMonthlySavings = 0;
	for (int i = 0; i < c3; i++)
	{
            if (futureExpenses[i].isInc())
            {
                totalMonthlySavings += futureExpenses[i].getSave();
            }         
	}        
    }

    public void setTotalCurrentExpensesAndSavings(double t)
    {
        totalCurrentExpensesAndSavings = t;
    }

    public void setDifference2(double d)
    {
        difference2 = d;
    }

    public void setUsableValue(double u)
    {
        usableValue = u;
    }

    public void calUsableValue()
    {
        usableValue = total - totalSavings;
    }

    public void setLeftover2(double l)
    {
        leftover2 = l;
    }

    public void calLeftover2()
    {
        leftover2 = leftover - totalSavings;
    }

    public void setEntertainment(double e)
    {
        entertainment = e;
    }

    public void setSupplies(double s)
    {
        supplies = s;
    }

    public void setFood(double f)
    {
        food = f;
    }

    public void setBills(double b)
    {
        bills = b;
    }

    public void setVehicle(double v)
    {
        vehicle = v;
    }

    public void setGift(double g)
    {
        gift = g;
    }

    public void setTransfer(double t)
    {
        transfer = t;
    }

    public void setSavings(double s)
    {
        savings = s;
    }

    public void setNonSavings(double n)
    {
        nonSavings = n;
    }

    public void setEntertainment2(double e)
    {
        entertainment2 = e;
    }

    public void setSupplies2(double s)
    {
        supplies2 = s;
    }

    public void setFood2(double f)
    {
        food2 = f;
    }

    public void setBills2(double b)
    {
        bills2 = b;
    }

    public void setVehicle2(double v)
    {
        vehicle2 = v;
    }

    public void setSavings2(double s)
    {
        savings2 = s;
    }

    public void setNonSavings2(double n)
    {
        nonSavings2 = n;
    }

    public void setAcc1(double a)
    {
        acc1 = a;
    }

    public void setAcc2(double a)
    {
        acc2 = a;
    }
    
    public void setAcc3(double a)
    {
        acc3 = a;  
    }
	
    public void calAcc3(double a)
    {
	acc3 = a;
	if (acc3 != acc2)
	{
	     acc3 = 0;
	    for (int i = 0; i < c2; i++)
	    {
		acc3 += monthlyExpenses[i].getAmountLeft();
	    }
	}
	else
	{
	    acc3 = 0;
	}
    }
	
    public void setC0(int c)
    {
        c0 = c;
    }

    public void setC1(int c)
    {
        c1 = c;
    }

    public void setC2(int c)
    {
        c2 = c;
    }

    public void setC3(int c)
    {
        c3 = c;
    }

    public void setPayments(Entry[] p)
    {
        payments = p;
    }

    public void setActualExpenses(Entry[] a)
    {
        actualExpenses = a;
    }

    public void setMonthlyExpenses(Save[] m)
    {
        monthlyExpenses = m;
    }

    public void setFutureExpenses(Saving[] f)
    {
        futureExpenses = f;
    }
    
    public void setEnd(boolean e)
    {
        end = e;
    }
    
    public void addEntry(Entry item)
    {
    	int spot = 0;
    	
    	if (item.isExpense())
    	{
	    Entry newItem = new Entry(true, month, year);
            for (int i = 0; i < c1; i++)
            {
                if (item.getDay() >= actualExpenses[i].getDay())
                    spot++;
            }
            for (int i = c1; i > spot; i--)
            {
                actualExpenses[i] = actualExpenses[i - 1];
            }
	    newItem.setDay(item.getDay());
            newItem.setAmount(item.getAmount());
            newItem.setEstimateAmount(item.getEstimateAmount());
            newItem.setPaid(item.isPaid());
            newItem.setAssign(item.getAssign());
            newItem.setCategory(item.getCategory());
            newItem.setComment(item.getComment());
            newItem.determineAccuracy();
			
            actualExpenses[spot] = newItem;			
            c1++;
            if (c1 % DEFAULT_ENTRY == 0 && c1 < MAX_ENTRY)       
            {
                actualExpenses = expandEntry(actualExpenses, month, year);
            }    		
    	}
    	else
    	{
	    Entry newItem = new Entry(false, month, year);
	    for (int i = 0; i < c0; i++)
	    {
	        if (item.getDay() >= payments[i].getDay())
	            spot++;
	    }
	    for (int i = c0; i > spot; i--)
	    {
	        payments[i] = payments[i - 1];
	    }
			
	    newItem.setDay(item.getDay());
            newItem.setAmount(item.getAmount());
            newItem.setEstimateAmount(item.getEstimateAmount());
            newItem.setPaid(item.isPaid());
            newItem.setAssign(item.getAssign());
            newItem.setCategory(item.getCategory());
            newItem.setComment(item.getComment());
            newItem.determineAccuracy();
			
	    payments[spot] = newItem;
			
	    c0++; 
	    if (c0 % DEFAULT_ENTRY == 0 && c0 < MAX_ENTRY)      
	    {
	        payments = expandEntry(payments, month, year);
	    }
    	}
    }
	
    public void addEntry(Save item)
    {		
        Save newItem = new Save();
		
        newItem.setAmount(item.getAmount());
        newItem.setAmountLeft(item.getAmountLeft());
        newItem.setCategory(item.getCategory());
        newItem.setComment(item.getComment());		
		
	monthlyExpenses[c2] = newItem;
	c2++;
			
	if (c2 % DEFAULT_ENTRY == 0 && c0 < MAX_ENTRY)
	{
	    monthlyExpenses = expandEntry(monthlyExpenses);
	}
    }

    public void addEntry(Saving item, int m, int y)
    {
	Saving newItem = new Saving();

        newItem.setSave(item.getSave());
        newItem.setTotalCount(item.getTotalCount());
        newItem.setCategory(item.getCategory());
        newItem.setComment(item.getComment());
        newItem.setAmount(item.getAmount());
        newItem.setCurrentCount(item.getCurrentCount());
        newItem.setInc(item.isInc());
        newItem.calDate(m, y);		
		
	futureExpenses[c3] = newItem;
	c3++;
		
	if (c3 % DEFAULT_ENTRY == 0 && c3 < MAX_ENTRY)
	{
	    futureExpenses = expandEntry(futureExpenses, month, year);
	}
    }		
    
    public static Entry[] expandEntry(Entry[] item, int mo, int yr)           
    {
        Entry[] newEntry = new Entry[DEFAULT_ENTRY + item.length];
        // First, copy the current entries
        for (int i = 0; i < item.length; i++)
        {
            newEntry[i] = new Entry(item[i].getDate(), item[i].getAmount(), item[i].getEstimateAmount(), 
                                    item[i].getAccuracy(), item[i].isPaid(), item[i].isExpense(), item[i].getCategory(), item[i].getComment(), 
                                    item[i].getAssign());
        }
        
        // Then, set up the extra entries
        if (item[0].isExpense())
        {
            for (int i = (DEFAULT_ENTRY + item.length - 1); i >= item.length; i--)
            {
                newEntry[i] = new Entry(true);
                newEntry[i].setDate(mo, 1, yr);
            }
        }
        else
        {
            for (int i = (DEFAULT_ENTRY + item.length - 1); i >= item.length; i--)
            {
                newEntry[i] = new Entry(false);
                newEntry[i].setDate(mo, 1, yr);
            }
        }
        
        return newEntry;    
    }
    public static Save[] expandEntry(Save[] item)           
    {
        Save[] newSave = new Save[DEFAULT_ENTRY + item.length];
        // First, copy the current entries
        for (int i = 0; i < item.length; i++)
        {
            newSave[i] = new Save(item[i].getAmount(), item[i].getAmountLeft(), item[i].getCategory(), item[i].getComment());
        }
        
        // Then, set up the extra entries
        for (int i = (DEFAULT_ENTRY + item.length - 1); i >= item.length; i--)
        {
            newSave[i] = new Save();
        }
        
        return newSave;    
    }
    
    public static Saving[] expandEntry(Saving[] item, int mo, int yr)           
    {
        Saving[] newSaving = new Saving[DEFAULT_ENTRY + item.length];
        // First, copy the current entries
        for (int i = 0; i < item.length; i++)
        {
            newSaving[i] = new Saving(mo, yr, item[i].getCurrentCount(), item[i].getTotalCount(), item[i].getAmount(), 
                                      item[i].getSave(), item[i].getCategory(), item[i].getComment(), item[i].isInc());
        }
        
        // Then, set up the extra entries
        for (int i = (DEFAULT_ENTRY + item.length - 1); i >= item.length; i--)
        {
            newSaving[i] = new Saving();
            newSaving[i].calDate(mo, yr);
        }
        
        return newSaving;    
    }      
}