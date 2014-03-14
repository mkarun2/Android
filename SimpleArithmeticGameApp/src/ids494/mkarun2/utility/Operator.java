package ids494.mkarun2.utility;

public enum Operator {
    ADDITION("+"), SUBRACTION("-"), MULTIPLICATION("*"), DIVISION("/");
    private String value;

    private Operator(String value) {
            this.value = value;
    }
    
    public static String getOperatorValue(Operator operation){
    	switch(operation){
	    	case ADDITION:
	    		return ADDITION.value;
	    	case SUBRACTION:
	    		return SUBRACTION.value;
	    	case MULTIPLICATION:
	    		return MULTIPLICATION.value;
	    	case DIVISION:
	    		return DIVISION.value;
    	}
    	return null;
    }
};

