package meals;

import java.util.Scanner;

import java.io.*;

import java.util.*;

import java.lang.*;

public class MealTracker{
	private static String Carb;
	private static String Protein;
	private static String Date;
	private static String Cal;
	private static String Fat;
	private static String MealType;
	private static String Meal;
	private static String[]total;

public static void main(String[] args) throws Exception{
	Scanner sc = new Scanner(System.in);
	if(args.length == 0){
		System.out.print("Welcome, Please enter today's date?");
		Date = sc.next();
		System.out.println("Please choose Breakfast, Lunch or Dinner.");
		MealType = sc.next();
		System.out.println("please enter your meal.");
		Meal = sc.next();
		System.out.println("Please enter amount of calories.");
		Cal = sc.next();
		System.out.println("Please enter amount of protein.");
		Protein = sc.next();
		System.out.println("Please enter amount of carbs.");
		Carb = sc.next();
		System.out.println("Please enter amount of fats.");
		Fat = sc.next();
			addMeal(Date,MealType,Meal,Cal,Protein,Carb,Fat);
			System.out.println("Meal added!");
	}
	else if(args.length == 2){
		if(args[1].equals("total")){
			Date = args[0];
			System.out.println(totalDate(Date));
		}
	}
	else if(args.length == 3){
		if(args[1].equals("to")){
			String d1 = args[0];
			String d2 = args[2];
			System.out.println(totalRange(d1,d2));
		}
		if(args[1].equals("total")){
			Date = args[0];
			if(args[2].equals("calories")){
				System.out.println(totalCal(Date) + "total calories for" + Date);
			}
			else if(args[2].equals("protein")){
				System.out.println(totalPro(Date) + "total grams of protein for " + Date);
			}
			else if(args[2].equals("carbs")){
				System.out.println(totalCarb(Date) + "total grams of carbs for " + Date);
			}
			else if(args[2].equals("fats")){
				System.out.println(totalFat(Date) + "total grams of fat for" + Date);
			}
		}
	}
	else{
		System.out.println("Usage: MealTracker(optional) [date][total](optional:Choose one)[calories][protein][carbs][fats]");
	}
}
public static void addMeal(String date, String mealType, String meal, String cal, String protein, String carb, String fat) throws Exception{
	FileWriter fw = new FileWriter("Meal.csv", true);
	fw.append(date + "," + mealType + "," + meal + "," + cal + "," + protein + "," + carb + "," + fat + "\n");
	fw.flush();
	fw.close();
}
public static String[][]readFile() throws Exception{
	String text = new Scanner(new File("Meals.csv")).useDelimiter("\\A").next();
	String lines[] = text.split("\n");
	String[][] meals = new String[lines.length][];
	for(int counter = 0; counter < lines.length; counter++){
		meals[counter] = lines[counter].split(",");
	}
	return meals;
}

public static String totalRange(String date1, String date2) throws Exception{
	int counter = 0;
	String[][]file = readFile();
	int cal = 0;
	int pro = 0;
	int carb = 0;
	int fat = 0;
	String total = "";
	if(date2Greater(date2,date1)){
		return "Error: thedate cannot be greater than second";
	}
	while(counter<file.length){
		if((file[counter][0].equals(date1)) || (file[counter][0].equals(date2))){
			try{
				cal += Integer.parseInt(file[counter][3]);
				pro += Integer.parseInt(file[counter][4]);
				carb += Integer.parseInt(file[counter][5]);
				fat += Integer.parseInt(file[counter][6].trim());
			}
			catch(NumberFormatException e){
				e.printStackTrace();
			}
		}
		else if(date2Greater(file[counter][0],date2)){
			cal += Integer.parseInt(file[counter][3]);
			pro += Integer.parseInt(file[counter][4]);
			carb += Integer.parseInt(file[counter][5]);
			fat += Integer.parseInt(file[counter][6].trim());
		}
		counter++;
	}
	total += "Total for" + date1 + "to" + date2 + "is" + cal + "calories." + pro + "grams of protein." +carb +"grams of cabs" +fat+"grams of fat.";
	return total;
}
public static boolean date2Greater(String date1, String date2){
	if((Integer.parseInt(date2.substring(0,2))) > (Integer.parseInt(date1.substring(0,2)))){
		return true;
	}
	else if((date2.substring(0,2)).equals((date1.substring(0,2)))){
		if((Integer.parseInt(date2.substring(3,5))) > (Integer.parseInt(date1.substring(3,5)))){
			return true;
		}
	}
	return false;
}
public static String totalDate(String date) throws Exception{
	String total = "";
	total += "Total for" + date+"is"+totalCal(date) + "calories," +totalPro(date)+"grams of protein," +totalCarb(date)+"grams of carbs, and" + totalFat(date) +"grams of fat.";
	return total;
}
public static String totalCal(String date) throws Exception{
	int total = 0;
	int counter = 0;
	String[][]file = readFile();
	while(counter < file.length){
		if((file[counter][0]).equals(date)){
			total += Integer.parseInt(file[counter][3]);
		}
		counter++;

	}
	return Integer.toString(total);
}
public static String totalPro(String date) throws Exception{
	int total = 0;
	int counter = 0;
	String[][]file = readFile();
	while(counter<file.length){
		if((readFile()[counter][0]).equals(date)){
			total += Integer.parseInt(file[counter][4]);
		}
		counter++;
	}
	return Integer.toString(total);
}
public static String totalCarb(String date)throws Exception{
	int total = 0;
	int counter = 0;
	String[][]file =readFile();
	while(counter< file.length){
		if((file[counter][0]).equals(date)){
			total += Integer.parseInt(file[counter][5]);
		}
		counter++;
	}
	return Integer.toString(total);
}
public static String totalFat(String date)throws Exception{
	int total = 0;
	int counter = 0;
	String[][]file=readFile();
	while(counter < file.length){
		if((file[counter][0]).equals(date)){
			total += Integer.parseInt(file[counter][6].trim());
		}
		counter++;
	}
	return Integer.toString(total);
 }
		
}
