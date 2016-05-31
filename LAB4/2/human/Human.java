package human;

public abstract class Human {

	protected Human(){}

	public static Human create (String ssn) {
	 	// ssn == YYMMDD-NNNN
	 	int num = Character.getNumericValue(ssn.charAt(9));
	 	if ((num & 1) == 0) {return new Woman();}
 		return new Man();
    }
}