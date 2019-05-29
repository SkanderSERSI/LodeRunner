package errors;

public class PostConditionError extends ContractError {

	private static final long serialVersionUID = 1L;
	public PostConditionError(String error) {
		//Récupère le nom de la méthode appelante
		super("PostConditionError ("
			+Thread.currentThread().getStackTrace()[2].getMethodName()+") : "+error);

}
}