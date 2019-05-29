package errors;

public class PreConditionError extends ContractError{

	private static final long serialVersionUID = 1L;

	public PreConditionError(String error) {
		//Récupère le nom de la méthode appelante
		super("PreConditionError ("
				+Thread.currentThread().getStackTrace()[2].getMethodName()+") : "+error);
	}

}

