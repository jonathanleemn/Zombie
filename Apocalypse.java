package project;

import java.security.SecureRandom;
import java.util.Scanner;

public class Apocalypse
{
	// instance data here. what do you need to keep track of?
	// number of days left?
	public static int days;
	private int scavengers, zombies;
	int zombieHP_gun, zombieHP_melee;
	int foodFound, ammoFound, grenadeFound, peopleFound;
	Person group1;
	SecureRandom rng;

	// constructor
	// what will we initialize through constructor?
	public Apocalypse()
	{
		group1 = new Person(Util.SURVIVORS, Util.FOOD, Util.AMMO, Util.GRENADES);
		zombieHP_melee = 3;
		zombieHP_gun = 2;
		days = 1;
		rng = new SecureRandom();

	}

	// public boolean runSimulation() will go here
	// return true if there are survivors and food
	// return false if no survivors and food
	public boolean runSimulation()
	{
		while (Person.food >= 0 && Person.survivors > 0 && days <= Util.DAYS_TO_SURVIVE)
		{
			System.out.println(group1.toString());
			feedPeople();
			// returns false when you run out of food
			if (Person.food < 0)
			{
				System.out.println(
						"\nNot enough rations for everyone. Total chaos breaks out and everyone dies fighting over the remaining food.");
				return false;
			}
			combat();
			// if there are no survivors left after combat, returns false
			if (Person.survivors == 0)
			{
				System.out.println("\nEveryone was eaten by zombies.");
				return false;
			}
			System.out.println();
			scavenge();
			days++;
			// win message, if player survives for 10 days
			if (days > Util.DAYS_TO_SURVIVE)
				System.out.println(
						"After 10 days of surviving against hoards of the undead, the military comes to rescue the remaining survivors.");
		}
		return false;
	}

	// method for scavenging supplies
	private void scavenge()
	{
		// prompts user for a yes/no input
		Scanner input = new Scanner(System.in);
		System.out.print("Do you want to scavenge for supplies? (Y/N) ");
		String response = input.next();

		// if user enters an invalid input, keep asking until user inputs y/n
		while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n"))
		{
			System.out.println("Invalid input");
			System.out.print("Do you want to scavenge for supplies? (Y/N) ");
			response = input.next();
		}

		// if user accepts to scavenge for supplies by pressing "y", prompt user
		// for amount of survivors they want to send out
		if (response.equalsIgnoreCase("y"))
		{

			System.out.print("How many people do you want to send on the supply run? ");
			scavengers = input.nextInt();
			// makes it so that user cannot input more survivors than they
			// already have or a negative amount
			while (scavengers > Person.survivors || scavengers < 0)
			{
				System.out.println("Invalid input");
				System.out.print("How many people do you want to send out on the supply run? ");
				scavengers = input.nextInt();
			}
			// scavengers have a (1/15) chance of dying while looking for
			// supplies.
			// subtract survivors from group upon death
			int scavengerDeath = Util.generateRandomInRange(Util.MIN_SCAVENGER_DEATH, Util.MAX_SCAVENGER_DEATH);
			if (scavengerDeath == Util.DEATH)
			{
				Person.survivors -= scavengers;
				System.out.println("The scavengers died looking for supplies. \n");
			}

			// if scavengers survive supply run, they have a chance of finding
			// food, ammo, grenades, or other people to join
			else
			{

				// methods for finding supplies, more scavengers = potentially
				findFood();
				findAmmo();
				findGrenade();
				// method for finding more people to join the group
				findSurvivors();
				System.out.printf(
						"The scavengers returned safely with: %d food rations, %d ammo, %d grenade & %d new members\n\n",
						foodFound, ammoFound, grenadeFound, peopleFound);
				// reset values to 0 to avoid these values getting stuck on a
				// number
				foodFound = 0;
				ammoFound = 0;
				grenadeFound = 0;
				peopleFound = 0;
			}
		}
		// if user enters "n", nothing is scavenged and the day continues on
		else if (response.equalsIgnoreCase("n"))
		{
			System.out.println();
			return;
		}
	}

	private void findFood()
	{
		// scavengers have a 100% chance of finding food
		// randomly generates amount of food and multiplies that by amount of
		// scavengers
		foodFound = (1 + rng.nextInt(5)) * scavengers;
		Person.food += foodFound;

	}

	private void findAmmo()
	{
		// scavengers have a 1/5 chance of finding a random number of ammo
		int findAmmoChance = Util.generateRandomInRange(Util.MIN_FIND_AMMO, Util.MAX_FIND_AMMO);
		if (findAmmoChance == Util.SCAVENGER_SUCCESS)
		{
			ammoFound = 1 + rng.nextInt(5);
			Person.ammo += ammoFound;
		}
	}

	private void findGrenade()
	{
		// scavengers have a 1/10 chance of finding a random number of grenades
		int findGrenadeChance = Util.generateRandomInRange(Util.MIN_FIND_GRENADE, Util.MAX_FIND_GRENADE);
		if (findGrenadeChance == Util.SCAVENGER_SUCCESS)
		{
			grenadeFound = 1 + rng.nextInt(1);
			Person.grenades += grenadeFound;
		}
	}

	private void findSurvivors()
	{
		// scavengers have a 1/2 chance of finding a random amount of people
		// (between 5-10) to join the group
		int findPeopleChance = Util.generateRandomInRange(Util.MIN_FIND_PEOPLE, Util.MAX_FIND_PEOPLE);
		if (findPeopleChance == Util.SCAVENGER_SUCCESS)
		{
			peopleFound = Util.generateRandomInRange(Util.MIN_PEOPLE, Util.MAX_PEOPLE);
			Person.survivors += peopleFound;
		}
	}

	// method to fight zombies
	private void combat()
	{
		// generates a random amount of zombies each day, survivors will use
		// grenades first and then firearms after they cannot throw anymore
		// grenades.
		// they will use melee weapons if they run out of ammo
		zombies = Util.generateRandomInRange(Util.MIN_ZOMBIES, Util.MAX_ZOMBIES);
		System.out.println("Zombies at beginning of combat: " + zombies);
		grenadeBattle();
		gunBattle();
		meleeBattle();
		System.out.println("Survivors after combat: " + Person.survivors);
	}

	private void grenadeBattle()
	{
		// a survivor will throw a grenade if there are 3 or more zombies
		while (zombies >= Util.GRENADE_TOSS && Person.survivors > 0 && Person.grenades > 0)
		{
			Person.grenades--;
			// grenades will always instakill a random amount of zombies
			// (between 3-10).
			// grenades never miss, so there is no risk of death to the
			// survivors
			int grenadeKill = Util.generateRandomInRange(Util.GRENADE_MIN_KILL, Util.GRENADE_MAX_KILL);
			zombies -= grenadeKill;
		}
	}

	private void meleeBattle()
	{

		while (zombies > 0 && Person.survivors > 0)
		{
			// checks to see if melee hits (1/3)
			int meleeHit = Util.generateRandomInRange(Util.MIN_MELEE_HIT, Util.MAX_MELEE_HIT);

			// if melee hits
			if (meleeHit == Util.MELEE_DIRECT_HIT)
			{// checks for instant kill
				// (1/10)
				int meleeInstaKill = Util.generateRandomInRange(Util.MIN_MELEE_INSTAKILL, Util.MAX_MELEE_INSTAKILL);
				// if a zombie is killed, zombie is subtracted and hp is reset
				// back to full health

				if (meleeInstaKill == Util.MELEE_DIRECT_KILL)
				{
					zombies--;
					zombieHP_melee = 3;
				}

				// if hit and no instakill, zombie lsoes 1 hp. if hp becomes 0,
				// 1 zombie is subtracted and hp is reset back to full health
				else
				{
					zombieHP_melee--;
					if (zombieHP_melee == 0)
					{
						zombies--;
						zombieHP_melee = 3;
					}
				}
			}
			// if melee misses
			else
			{
				Person.survivors--;
			}
		}
	}

	private void gunBattle()
	{
		while (zombies > 0 && Person.survivors > 0 && Person.ammo > 0)
		{
			Person.ammo--;
			// checks to see if gun hits (1/2)
			int gunHit = Util.generateRandomInRange(Util.MIN_GUN_HIT, Util.MAX_GUN_HIT);
			// if gun hits
			if (gunHit == Util.GUN_DIRECT_HIT)
			{// checks for instant kill (1/3)
				int gunInstaKill = Util.generateRandomInRange(Util.MIN_GUN_INSTAKILL, Util.MAX_GUN_INSTAKILL);
				// if a zombie is instakilled, 1 zombie is subtracted and hp is
				// reset back to full health
				if (gunInstaKill == Util.GUN_DIRECT_KILL)
				{
					zombies--;
					zombieHP_gun = 2;
				}
				// if hit and no instakill, zombie loses 1 hp. if hp becomes 0,
				// 1 zombie is subtracted and hp is reset back to full health
				else
				{
					zombieHP_gun--;
					if (zombieHP_gun == 0)
					{
						zombies--;
						zombieHP_gun = 2;
					}
				}
			}
			// if gun misses
			else
			{
				// checks survivors' chances of dying (1/2)
				int gunDeath = Util.generateRandomInRange(Util.MIN_GUN_DEATH, Util.MAX_GUN_DEATH);
				// if survivor dies, subtract a survivor
				if (gunDeath == Util.DEATH)
					Person.survivors--;
			}
		}
	}

	// method to feed people
	public void feedPeople()
	{
		Person.food -= Person.survivors;

	}
}
