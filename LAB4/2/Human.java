public abstract class Human {

	 public static Human create (String ssn) {
	 	// ssn == YYMMDD-NNNN
	 	int num = Integer.parseInt(ssn.charAt(9));
	 	if ( (num & 1) == 0 ) {
	 		return new Man();
	 	} else {
	 		return new Woman();
	 	}
    }

}

private class Man {}
private class Woman {}