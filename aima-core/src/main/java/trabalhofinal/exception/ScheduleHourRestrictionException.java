package trabalhofinal.exception;

/*
	Exceção indica que com a restrição de horario de funcionamento da empresa,
	foi encontrado um funcionario que não consegue cumprir sua carga horaria 
	porque seu numero de horarios disponiveis que atendem as restriçoes da empresa
	não é grande o bastante
	
	
	Ex:
	
	lista de hoarios padrão = [ 4 , 13 , 19 , 21 , 22 ]
 	carga horaria = 3
	
	Horario de funcionamento: 8 ate 19
	
	lista de horarios validos = [ 13 , 19 ]
	
 	
 	Funcionario precisa escolher 3 horarios, no entanto ele possui apenas 2 horarios validos disponiveis!!!!
	
*/

public class ScheduleHourRestrictionException extends Exception {

	private static final long serialVersionUID = -3640622740196933143L;

	public ScheduleHourRestrictionException(String msg) {
		super(msg);
	}
}
