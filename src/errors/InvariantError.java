package errors;

public class InvariantError extends ContractError {

	private static final long serialVersionUID = 1L;

	public InvariantError(String error) {
		//Récupère le nom de la méthode appelante
		super("InvariantError ("
			+Thread.currentThread().getStackTrace()[3].getMethodName()+") : "+error);
	}

}
