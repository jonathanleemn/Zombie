package project;

public class Person {
	/*instance variables, what do you need to keep track of?
	 * number of people, food, ammo?
	 */
	
	public static int survivors;
	public static int food;
	public static int ammo;
	public static int grenades;

	public Person(int survivors, int food, int ammo, int grenades)
	{
		Person.survivors=survivors;
		Person.food=food;
		Person.ammo=ammo;
		Person.grenades=grenades;
	}
	
	//methods
	//getters, setters and tostring
	
	//sets survivors
	public void setSurvivors(int survivors)
	{
		Person.survivors = survivors;
	}	
	//gets survivors
	public int getSurvivors(){
		return survivors;
	}

	//sets food
	public void setFood(int food){
		Person.food = food;
	}
	//gets food
	public int getFood()
	{
		return food;
	}
	
	//sets ammo
	public void setAmmo(int ammo)
	{
		Person.ammo=ammo;
	}
	//gets ammo
	public int getAmmo(){
		return ammo;
	}
	
	//sets grenades
	public void setGrenades(int grenades)
	{
		Person.grenades=grenades;
	}
	//gets grenades
	public int getGrenades(){
		return grenades;
	}
	public String toString(){
		return "Day: " + Apocalypse.days + "\nSurvivors: " + Person.survivors + "\nRations: " + Person.food + "\nAmmo: " + Person.ammo + "\nGrenades: " + Person.grenades;
	}
}
