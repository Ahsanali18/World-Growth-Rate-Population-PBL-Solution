import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CsvFileReader{
	public void readCsvFile(){
		String csvFilePath="C:\\Users\\Lenovo\\Desktop\\DSA PBL work\\World population growth rate by cities 2024 Dataset.csv";
		String line="";
		String columnDelimiter=",";
		
		try(BufferedReader br=new BufferedReader(new FileReader(csvFilePath))){
			while((line=br.readLine())!=null) {
				String[] data=line.split(columnDelimiter);
				//Fetch data row wise
				for(String value:data){
					System.out.print(value+" ");
				}
				System.out.println();
				//count++;
			}
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}
}


class City{
	private String cityName;
	private long population2023;
	private long population2024;
	private double growthRate;
	
	public City(String cityName, long population2023, long population2024, double growthRate){
		this.cityName=cityName;
		this.population2023=population2023;
		this.population2024=population2024;
		this.growthRate=growthRate;
	}
	
	public String getCityName(){
		return cityName;
	}
	public long getPopulation2023(){
		return population2023;
	}
	public long getPopulation2024(){
		return population2024;
	}
	public double getGrowthRate(){
		return growthRate;
	}
	@Override
	public String toString(){
		return "City Name: "+cityName+" Population 2024= "+population2024+" Population 2023= "
	           +population2023+" Growth Rate= "+growthRate;
	}
}


interface Stack{
	public void pushElement(City city);
	public City popElement();
	public City peekElement();
	
	public int stackSize();
	public boolean isStackEmpty();
}

class LinkedList{
	protected static class City_Node{
		City city;
		City_Node next;
		
		public City_Node(City city){
			this.city=city;
			this.next=null;
		}
	}
	
	protected City_Node head;
	
	public LinkedList(){
		this.head=null;
	}
	
	public void addCity(City newCity){
		City_Node newNode=new City_Node(newCity);
		
		if(head==null || head.city.getPopulation2024() > newCity.getPopulation2024()){
			newNode.next=head;
			head=newNode;
			return;
		}
		City_Node tempReference=head;
		
		while(tempReference.next!=null){
			if(tempReference.next.city.getPopulation2024() > newCity.getPopulation2024())
				break;
			tempReference=tempReference.next;
		}
		newNode.next=tempReference.next;
		tempReference.next=newNode;
	}
	
	public void displayCities(){
		City_Node temp=head;
		
		while(temp!=null){
			System.out.println(temp.city);
			temp=temp.next;
		}
	}
	
	public long sumOfPopulations2023(){
		long sum=0;
		City_Node temp=head;
		
		while(temp!=null){
			sum+=temp.city.getPopulation2023();
			temp=temp.next;
		}
		return sum;
	}
	
	public long sumOfPopulation2024(){
		long sum=0;
		City_Node temp=head;
		
		while(temp!=null){
			sum+=temp.city.getPopulation2024();
			temp=temp.next;
		}
		return sum;
	}
}


class ContinentStack implements Stack{
	protected static class City_Node{
		City city;
		City_Node next;
		
		public City_Node(City city){
			this.city=city;
			this.next=null;
		}
	}
	
	protected City_Node top;
	private int size=0;
	
	public ContinentStack(){
		this.top=null;
	}
	
	@Override
	public void pushElement(City newCity){
		size++;
		City_Node newNode=new City_Node(newCity);
		if(top==null){
			top=newNode;
			return;
		}
		newNode.next=top;
		top=newNode;
	}
	
	public City popElement(){
		if(top==null){
			throw new IllegalStateException("Stack underflow!");
		}
		City oldTop=top.city;
		top=top.next;
		--size;
		return oldTop;
	}
	
	public City peekElement(){
		if(top==null){
			System.out.print("Stack is empty: ");
			return null;
		}
		return top.city;
	}
	
	public boolean isStackEmpty(){
		return (size==0);
	}
	
	public int stackSize(){
		return size;
	}
	
	public void displayStack() {
		City_Node temp = top;
	    // Traverse the stack and display the city details
	    if (temp == null) {
	        System.out.println("The stack is empty.");
	        return;
	    }
	    
	    while (temp != null) {
	        System.out.println("| "+temp.city.getCityName()+", Population 2024= "+temp.city.getPopulation2024()+"\t\t|");
	        temp = temp.next;
	    }
	    System.out.println("-------------------------------------------------");
	}

	
	public void storeCities(LinkedList list, ContinentStack stack){
		LinkedList.City_Node temp=list.head;
		
		while(temp!=null){
			stack.pushElement(temp.city);
			temp=temp.next;
		}	
	}
	
	public void middleCitiesOfContinents(){
		City_Node slow=top;
		City_Node fast=top;
		
        while (fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        System.out.println(slow.city.getCityName());
	}
}

class PopulationStack{
	protected static class Population_Node{
		String continentName;
		long population;
		Population_Node next;
		
		public Population_Node(String continentName, long population){
			this.continentName=continentName;
			this.population=population;
			this.next=null;
		}
	}
	
	protected Population_Node top;
	private int size=0;
	
	public PopulationStack(){
		this.top=null;
	}
	
	public void pushPopulation(String continentName, long population){
		size++;
		Population_Node newNode=new Population_Node(continentName,population);
		
		if(top==null || top.population<population){
			newNode.next=top;
			top=newNode;
		}
		else{
		
			Population_Node temp=top;
			while(temp.next!=null){
				if(temp.next.population<population)
					break;
				temp=temp.next;
			}
			newNode.next=temp.next;
			temp.next=newNode;
		}
	}
	
	public void displayPopulationStack(){
		Population_Node temp=top;
		
		if(temp==null){
			System.out.println("Population stack is Empty!");
			return;
		}
	    while (temp != null) {
	        System.out.println("Continent: " + temp.continentName + 
	                           ", Total Population: " + temp.population);
	        temp = temp.next;
	    }
	}
	public int populationStackSize(){
		return size;
	}
	
	public boolean isStackEmpty(){
		return (size==0);
	}
}

class ProblemBasedLearning{
	public void shrunkPopulationCities(LinkedList list){
		LinkedList.City_Node temp=list.head;
		while(temp!=null){
			long pop2023=temp.city.getPopulation2023();
			long pop2024=temp.city.getPopulation2024();
			double shrinkageRate= ((double)(pop2023-pop2024)/pop2023)*100;
			double growthRate=temp.city.getGrowthRate();
			if(growthRate<0){
				System.out.println(temp.city.getCityName()+": "+pop2023+" to "+pop2024+" Shrinkage Rate= "+shrinkageRate+"%");
			}
			temp=temp.next;
		}
		System.out.println("-----------------------------------------------------------------");
	}
	
	public String getContinentWithLowestPopulation(PopulationStack stack){
		if(stack.top==null){
			return "Population Stack is empty!";
		}
		PopulationStack.Population_Node temp=stack.top;
		
		while(temp.next!=null){
			temp=temp.next;
		}
		System.out.println("----------------------------------------");
		return temp.continentName+" with population "+temp.population;
	}
	
	public void middleCityOfContinent(ContinentStack continent){
		ContinentStack.City_Node slow=continent.top;
		ContinentStack.City_Node fast=continent.top;
		
        while (fast!=null&&fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }
        System.out.println(slow.city.getCityName());
	}
	
	public void highestAndLowestGrowthRateCities(LinkedList... lists){
		City highestGrowthCity=null;
		City lowestGrowthCity=null;
		double highestGrowthRate=Double.NEGATIVE_INFINITY;
		double lowestGrowthRate=Double.POSITIVE_INFINITY;
		
		for(LinkedList list:lists){
			LinkedList.City_Node temp=list.head;
			while(temp!=null){
				double growthRate=((double) (temp.city.getPopulation2024()-temp.city.getPopulation2023())/temp.city.getPopulation2023())*100;
				if(growthRate>highestGrowthRate){
					highestGrowthRate=growthRate;
					highestGrowthCity=temp.city;
				}
				if(growthRate<lowestGrowthRate){
					lowestGrowthRate=growthRate;
					lowestGrowthCity=temp.city;
				}
				temp=temp.next;
			}
		}
		System.out.println("City with the highest growth rate: "+highestGrowthCity.getCityName()+" Growth Rate= "+highestGrowthRate+"%");
		System.out.println("City with the lowest growth rate: "+lowestGrowthCity.getCityName()+" Growth Rate= "+lowestGrowthRate+"%");	
	}
}


public class Main{
	public static void main(String[] args) {
		/*To print the complete CSV file data on console
		CsvFileReader readFile=new CsvFileReader();
		readFile.readCsvFile();*/

		/*Step 1: Create sorted linked lists, one for each continent and put city name, population of 2024 and
        population 2023 in that list.*/
		LinkedList asia=new LinkedList();
		LinkedList europe=new LinkedList();
		LinkedList africa=new LinkedList();
		LinkedList northAmerica=new LinkedList();
		LinkedList southAmerica=new LinkedList();
		LinkedList oceania=new LinkedList();
		
		
		String csvFile="C:\\Users\\Lenovo\\Desktop\\DSA PBL work\\World population growth rate by cities 2024 Dataset.csv";
		String line="";
		
		try(BufferedReader br=new BufferedReader(new FileReader(csvFile))){
			String columnDelimiter=",";
			br.readLine(); //Skip the first row of headers
			
			//Read data from the CSV file
			while((line=br.readLine())!=null){
				String[] data=line.split(columnDelimiter);
				
				String cityName=data[0];
				String country=data[1];
				String continent=data[2];
				long population2024=Long.parseLong(data[3].trim());
				long population2023=Long.parseLong(data[4].trim());
				double growthRate=Double.parseDouble(data[5].trim());
				
				City newCity=new City(cityName,population2023,population2024,growthRate);
				
				switch(continent.toLowerCase()){
					case "asia": 
						asia.addCity(newCity);  
						break;
					case "europe": 
						europe.addCity(newCity); 
						break;
					case "africa": 
						africa.addCity(newCity); 
						break;
					case "north america":
						northAmerica.addCity(newCity); 
						break;
					case "south america": 
						southAmerica.addCity(newCity); 
						break;			
					case "oceania":
						oceania.addCity(newCity); 
						break;
					default:
						System.out.println("Unknown Continent"+continent);
						break;
				}
			}
		}catch(IOException ie){
			System.err.print("Error occured.....");
		}
		
		
		System.out.println("\t\t\tCity Located in Aisa Continent");
		asia.displayCities();
		System.out.println("\t\t\tCity Located in Europe Continent");
		europe.displayCities();
		System.out.println("\t\t\tCity Located in Africa Continent");
		africa.displayCities();
		System.out.println("\t\t\tCity Located in North America Continent");
		northAmerica.displayCities();
		System.out.println("\t\t\tCity Located in South America Continent");
		southAmerica.displayCities();
		System.out.println("\t\t\tCity Located in Oceania Continent");
		oceania.displayCities();
		
		
		/*Step 2: Use linked list to make stacks for each continent for the year 2024 with cities having high population
        at top and low population at bottom. The top of stack is the city with the highest population.*/
		ContinentStack asiaStack=new ContinentStack();
		asiaStack.storeCities(asia, asiaStack);
		
		ContinentStack europeStack=new ContinentStack();
		europeStack.storeCities(europe, europeStack);
		
		ContinentStack africaStack=new ContinentStack();
		africaStack.storeCities(africa, africaStack);
		
		ContinentStack northAmericaStack=new ContinentStack();
		africaStack.storeCities(northAmerica, northAmericaStack);
		
		ContinentStack southAmericaStack=new ContinentStack();
		africaStack.storeCities(southAmerica, southAmericaStack);
		
		ContinentStack oceaniaStack=new ContinentStack();
		africaStack.storeCities(oceania, oceaniaStack);
		
		
		System.out.println("-------------------------------------------------------------------------------------------------");
		
		/*
		System.out.println("\t\tAsia continent Stack");
		asiaStack.displayStack();
		System.out.println("\t\tEurope continent Stack");
		europeStack.displayStack();
		System.out.println("\t\tAfrica continent Stack");
		africaStack.displayStack();
		System.out.println("\t\tNorth America continent Stack");
		northAmericaStack.displayStack();
		System.out.println("\t\tSoth America continent Stack");
		southAmericaStack.displayStack();
		System.out.println("\t\tOceania continent Stack");
		oceaniaStack.displayStack();*/
		
		
		/*Step 3: Use linked list to calculate the sum of population for both years of each continent and make a stack
        which stores the sum with continent name having highest population at the top.*/
		PopulationStack continentsPopulation=new PopulationStack();
		
		String asiaContinent="Asia";
		long population2023OfAsia=asia.sumOfPopulations2023();
		long population2024OfAsia=asia.sumOfPopulation2024();
		long totalPopulationOfAsia=population2023OfAsia+population2024OfAsia;
		continentsPopulation.pushPopulation(asiaContinent, totalPopulationOfAsia);
		
		
		String europeContinent="Europe";
		long population2023OfEurope=europe.sumOfPopulations2023();
		long population2024OfEurope=europe.sumOfPopulation2024();
		long totalPopulationOfEurope=population2023OfEurope+population2024OfEurope;
		continentsPopulation.pushPopulation(europeContinent, totalPopulationOfEurope);
		
		
		String africaContinent="Africa";
		long population2023OfAfrica=africa.sumOfPopulations2023();
		long population2024OfAfrica=africa.sumOfPopulation2024();
		long totalPopulationOfAfrica=population2023OfAfrica+population2024OfAfrica;
		continentsPopulation.pushPopulation(africaContinent, totalPopulationOfAfrica);
		
		
		String northAmericaContinent="North America";
		long population2023OfNorthAmerica=northAmerica.sumOfPopulations2023();
		long population2024OfNorthAmerica=northAmerica.sumOfPopulation2024();
		long totalPopulationOfNorthAmerica=population2023OfNorthAmerica+population2024OfNorthAmerica;
		continentsPopulation.pushPopulation(northAmericaContinent, totalPopulationOfNorthAmerica);
		
		
		String southAmericaContinent="South America";
		long population2023OfSouthAmerica=southAmerica.sumOfPopulations2023();
		long population2024OfSouthAmerica=southAmerica.sumOfPopulation2024();
		long totalPopulationOfSouthAmerica=population2023OfSouthAmerica+population2024OfSouthAmerica;
		continentsPopulation.pushPopulation(southAmericaContinent, totalPopulationOfSouthAmerica);
		
		String oceniaContinent="Ocenia";
		long population2023OfOcenia=oceania.sumOfPopulations2023();
		long population2024OfOcenia=oceania.sumOfPopulation2024();
		long totalPopulationOfOcenia=population2023OfOcenia+population2024OfOcenia;
		continentsPopulation.pushPopulation(oceniaContinent, totalPopulationOfOcenia);
		
		continentsPopulation.displayPopulationStack();
		
		// Problem 1: List the cities whose population has shrunk in the year 2024 for all continents (Use step 1 data).
		ProblemBasedLearning pbl=new ProblemBasedLearning();
		
		System.out.println("Cities in Asia with shrinking population in 2024");
		pbl.shrunkPopulationCities(asia);

		System.out.println("Cities in Europe with shrinking population in 2024");
		pbl.shrunkPopulationCities(europe);

		System.out.println("Cities in Africa with shrinking population in 2024");
		pbl.shrunkPopulationCities(africa);

		System.out.println("Cities in North America with shrinking population in 2024");
		pbl.shrunkPopulationCities(northAmerica);
		
		System.out.println("Cities in South America with shrinking population in 2024");
		pbl.shrunkPopulationCities(southAmerica);

		System.out.println("Cities in Oceania with shrinking population in 2024");
		pbl.shrunkPopulationCities(oceania);
	
		
		//Problem 2: Which continent has the lowest population (Use step 3)
		System.out.println("Continent with lowest population is: "+pbl.getContinentWithLowestPopulation(continentsPopulation));
		System.out.println("-----------------------------------------------------------------------------");
		
		/*Problem 3: Which city has the highest growth rate and which city has lowest growth rate regardless of 
		continent (Calculate using the data of step 1)*/
		pbl.highestAndLowestGrowthRateCities(asia,europe,africa,northAmerica,southAmerica,oceania);
		
		//Problem 4: List the cities which are at the middle of the stacks of each continent made in step 2
		System.out.println("Cities located in the middle of each contient");
		pbl.middleCityOfContinent(asiaStack);
		pbl.middleCityOfContinent(europeStack);
		pbl.middleCityOfContinent(africaStack);
		pbl.middleCityOfContinent(southAmericaStack);
		pbl.middleCityOfContinent(northAmericaStack);
		pbl.middleCityOfContinent(oceaniaStack);
	}
}


