package trabalhofinal.exception;

/*
 	Exceção indica que algum funcionario não consegue cumprir sua carga horaria 
 	porque sua lista de horarias disponiveis não é grande o bastante
 	
 	Ex: 
 	
 	lista de horarios = [ 6 , 12 , 13 ]
 	carga horaria = 5
 	
 	Funcionario precisa escolher 5 horarios, no entanto ele possui apenas 3 horarios disponiveis!!!!
 	
 */

public class PersonAvalabilityException extends Exception {

	private static final long serialVersionUID = 8968757764835536130L;

	public PersonAvalabilityException(String msg) {
		super(msg);
	}
}
