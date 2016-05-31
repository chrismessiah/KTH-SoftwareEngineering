package human;

public abstract class Human {
	protected String name;
	protected String gender;

	protected Human(){}

	public static Human create (String name, String ssn) {
	 	// ssn == YYMMDD-NNNN
	 	int num = Character.getNumericValue(ssn.charAt(9));
	 	if ((num & 1) == 0) {return new Woman(name);}
 		return new Man(name);
    }

    public String toString() {
    	return "I'm a " + gender + " and my name is " + name;
    }
}