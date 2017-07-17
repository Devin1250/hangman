import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class hangman {

	/**
	 * @param args
	 */
	static int namechek;
	static int wincheck;
	static int totalcheck;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//Devin Samaranayake
		Scanner getname = new Scanner(System.in);
		System.out.println("Please enter your name");
		String name = getname.next();
		getstats(name);
		String cword = stringlist()[getRandom(amount())].toLowerCase().replace(' ', '~');
		//Gets a random word from the file and replaces any spaces in the word with a ~
		char[] carray = cword.toCharArray();
		// an array of letters of the correct word
		int wins = wincheck;
		int total = totalcheck;
		int wrong = 0;
		// number of incorrect guesses
		boolean gover = false;
		// is the game over or not
		gui(wrong);
		//Displays the post where the man is hung
		String dash ="";
		for(int x = 0;x<cword.length(); x++)
			dash = dash+"_ ";
		System.out.println(dash);
		String dashnos = dash.replaceAll(" ","");
		char[] dashar = dashnos.toCharArray();
		// an array of the dashes that are displayed
		Scanner s = new Scanner(System.in);
		ArrayList guessed = new ArrayList();
		while(gover == false)
		{
			System.out.println("You have already guessed: "+ guessed);
		System.out.println("Please guess a letter");
		System.out.println("Spaces are represented with ~");
		char guess = s.next().charAt(0);
		guessed.add(guess+"");
		int count = 0;
		//used to check if the letter was in the correct word or not
		for(int x = 0; x<carray.length;x++)
		{
			
			if (carray[x]==guess)
			{
				dashar[x]=guess;
				count = count+1;
			}
			else if(x==carray.length-1&& count ==0)
			{
				wrong = wrong+1;
				
			}
		}
		if (wrong==6)
		{
			gover=true;
			System.out.println("The correct word is "+ cword);
			total= total+1;
		}
		gui(wrong);
		String checks = "";
			for (int x = 0; x<dashar.length;x++)
			{
				checks= checks+dashar[x]+" ";
			}
			if(checks.replaceAll(" ","").equals(cword))
			{
			gover = true;
			System.out.println("YOU HAVE WON!");
			wins=wins+1;
			total = total+1;
			}
		
		System.out.println(checks);
		
		}
		updatestats(name, wins, total);
		displaystats();
	}
	static void displaystats()
	{
		try{
		Scanner s = new Scanner(new File("stats.txt"));
		System.out.println("Player		Wins	Total");
		System.out.println("======================================");
			while(s.hasNext())
			{
				
				System.out.println(s.next()+"		"+s.nextInt()+"	 "+s.nextInt());
				
			}
		}
		catch(IOException e)
		{
			System.out.println("No Word File Found");
		}
		
	}
	static void updatestats(String name, int wins, int total)
	{
		try
		{
			String output= "";
			ArrayList lists = new ArrayList();
				Scanner s = new Scanner(new File("stats.txt"));
				while(s.hasNext())
				{
					lists.add(s.next());
				}
				boolean check = false;
				if(lists.size()>=3)
				{
					for (int x = 0; x<lists.size();x++)
					{
						if(name.equals(lists.get(x)))
						{
							lists.set(x+1, ""+wins);
							lists.set(x+2, ""+total);
							check = true;
						}
						if(!check && x==lists.size()-1)
						{
							lists.add(name);
							lists.add(""+wins);
							lists.add(""+total);
						}
					
					}
				}
				else
				{
					lists.add(name);
					lists.add(""+wins);
					lists.add(""+total);
				}
				for (int x = 0; x<lists.size();x++)
				{
				output = output + "\n" + lists.get(x);
				}
				FileWriter writer = new FileWriter("stats.txt");
				
		        writer.write(output);
		        writer.close();

		}
		catch(IOException e)
		{
			System.out.println("No Stats File Found");
		}
	}
	static void getstats(String name)
	{
		try
		{
			
			Scanner s = new Scanner(new File("stats.txt"));
			while(s.hasNext())
			{
				if(name.equals(s.next()))
				{
					wincheck = s.nextInt();
					totalcheck = s.nextInt();
					break;
				}
			}
			
			
		}
		catch (IOException e)
		{
			System.out.println("No Stats file found");
		}
	}
	static int getRandom(int amount)
	{
		Random rand = new Random();
		int pick = rand.nextInt(amount);
		return pick;
		//creates a random number to determine which word gets picked
	}
	static void gui(int wrong)
	{
		System.out.println("Welcome to Hangman");
		System.out.println("	 +------+");
		System.out.println("	 |	|");
		if (wrong ==0)
		{
			System.out.println("		|\n		|\n	 	|\n	 	|");
		}
		else if (wrong==1)
			System.out.println(" 	 O	|\n	 	|\n	 	|\n	 	|");
		else if (wrong == 2)
		{
			System.out.println(" 	 O	|\n	 |	|\n	 	|\n	 	|");
		}
		else if (wrong ==3)
			System.out.println(" 	 O	|\n	/|	|\n	 	|\n	 	|");
		else if (wrong==4)
			System.out.println(" 	 O	|\n	/|\\	|\n	 	|\n	 	|");
		else if(wrong==5)
			System.out.println(" 	 O	|\n	/|\\	|\n	/ 	|\n	 	|");
		else if (wrong ==6)
			System.out.println(" 	 O	|\n	/|\\	|\n	/ \\ 	|\n	*DEAD*	|");
		System.out.println("========================");
		//creates the hanging platform
		//creates the man depending on how many you got wrong
	}
	static int amount()
	{
		int amount = 0;
		try
		{
			Scanner filescan = new Scanner(new File("file.txt"));
			while(filescan.hasNextLine()&&!filescan.nextLine().equals(""))
				amount++;
		}
		catch (IOException e)
		{
			System.out.println("No file found");
		}
		return amount;
		//tells how many words are in the file
	}
	static String[] stringlist()
	{
		String[] posswords= new String[amount()];
		try
		{
			Scanner wordscan = new Scanner(new File("file.txt"));
			for(int x = 0; x<amount();x++)		
				posswords[x]= wordscan.nextLine();	
			//This for loop adds the words in the file to the Array posswords
		}
		catch (IOException e)
		{
			System.out.println("No Word list file found");
		}
		return posswords;
		//creates an array filled with the words in the file
	}

}
