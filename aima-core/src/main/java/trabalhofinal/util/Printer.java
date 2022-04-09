package trabalhofinal.util;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;

public class Printer {
	
	// Imprime o agendamento de forma mais visivel no console,
	// sendo que cada linha do agendamento no maximo 12 horarios
	public static void printSchedule(Assignment<Variable, List<Integer>> schedule) {

		Integer lastWork = getLastWork(schedule);
		
		// Lista que mostrará o agendamento de maneira mais clara
		List<String> formatSchedule = new ArrayList<String>(lastWork);

		//Inicialmente colocar todos os horarios como vagos 
		for (int i = 0; i < lastWork; i++)
			formatSchedule.add(i, (i + 1) + " - ####");
		
		//Popular os horarios com seus repectivos funcionarios
		for (Integer i = 0; i < lastWork; i++) {
			for (Variable var : schedule.getVariables()) {
				List<Integer> values = schedule.getValue(var);
				if (values.contains(i + 1))
					formatSchedule.set(i, (i + 1) + " - " + var.getName());
			}
		}
		// Particiona o "formatSchedule" em sublistas
		// Cada sublista pode ter no maximo 12 horarios
		for (List<?> line : Partition.ofSize(formatSchedule, 12)) {
			System.out.println(line + "\n");
		}
	}

	// Encontra o ultimo horario de trabalho do agendamento
	public static Integer getLastWork(Assignment<Variable, List<Integer>> schedule) {

		Integer lastWork = Integer.MIN_VALUE;

		for (Variable employeer : schedule.getVariables()) {

			// Armazena o ultimo horario de trabalho do funcionario em questão
			Integer employeerLastWork = getMax(schedule.getValue(employeer));

			//Compara se o horario encontrado é mais tarde que o anterior
			if (employeerLastWork > lastWork)
				lastWork = employeerLastWork;
		}

		return lastWork;
	}

	// Encontra o maior valor de uma lista
	public static Integer getMax(List<Integer> list) {
		Integer max = Integer.MIN_VALUE;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > max) {
				max = list.get(i);
			}
		}
		return max;
	}
}
