package project;

public class Util {
	//constants to be used in place of magic numbers
	public static int DAYS_TO_SURVIVE = 10;
	public enum Status{RUN, STOP}
	public static final int MIN_ZOMBIES=6, MAX_ZOMBIES=10;
	public static final int MIN_GUN_HIT = 1, MAX_GUN_HIT = 2;
	public static final int MIN_GUN_INSTAKILL = 1, MAX_GUN_INSTAKILL = 3;
	public static final int MIN_GUN_DEATH = 1, MAX_GUN_DEATH = 2;
	public static final int MIN_MELEE_HIT = 1, MAX_MELEE_HIT = 3;
	public static final int MIN_MELEE_INSTAKILL = 1, MAX_MELEE_INSTAKILL = 10;
	public static final int MELEE_DIRECT_HIT = 1, MELEE_DIRECT_KILL = 1;
	public static final int GUN_DIRECT_HIT = 1, GUN_DIRECT_KILL = 1;
	public static final int MIN_SCAVENGER_DEATH = 1, MAX_SCAVENGER_DEATH = 15;
	public static final int DEATH = 1;
	public static final int GRENADE_MIN_KILL = 3, GRENADE_MAX_KILL = 10;
	public static final int GRENADE_TOSS = 3;
	public static final int MIN_FIND_FOOD = 1, MAX_FIND_FOOD = 3;
	public static final int MIN_FIND_AMMO = 1, MAX_FIND_AMMO = 5;
	public static final int MIN_FIND_GRENADE = 1, MAX_FIND_GRENADE = 10;
	public static final int MIN_FIND_PEOPLE = 1, MAX_FIND_PEOPLE = 2;
	public static final int MIN_PEOPLE = 5, MAX_PEOPLE = 10;
	public static final int SCAVENGER_SUCCESS = 1;
	public static int SURVIVORS = 25;
	public static int FOOD = 80;
	public static int AMMO = 150;
	public static int GRENADES = 5;
	
	//static methods that will be used in Apocalypse
	public static int generateRandomInRange(int min, int max)
	{
		return min + (int)(Math.random()*((max-min)+1));
	}

}
