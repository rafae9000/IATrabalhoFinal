package trabalhofinal.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Variable;

public class Printer {

	//Imprime a tabela de horarios dos funcionarios
	public static void showTable(Assignment<Variable, List<Integer>> schedule) {

		Integer lastWork = Integer.MIN_VALUE;
		List<Variable> variables = schedule.getVariables();
		Integer biggestNameLenght = getBiggestName(variables).length();

		variables.sort(Comparator.comparing(Variable::getName));

		List<Integer> hours = new ArrayList<Integer>(24);
		for (int i = 0; i < 24; i++)
			hours.add(i + 1);

		for (int i = 0; i < biggestNameLenght + 4; i++) {
			System.out.print(" ");
		}

		for (Integer hour : hours) {
			if (hour < 10)
				System.out.print(hour + "  ");
			else
				System.out.print(hour + " ");
		}
		System.out.println("\n");

		for (Variable employeer : variables) {

			List<Integer> tableRow = new ArrayList<Integer>(24);
			List<Integer> employeerSchedule = schedule.getValue(employeer);

			for (int i = 0; i < 24; i++) {
				if (employeerSchedule.contains(i + 1))
					tableRow.add(1);
				else
					tableRow.add(0);
			}

			Integer espacamento = biggestNameLenght - employeer.getName().length() + 4;

			System.out.print(employeer.getName());
			for (int i = 0; i < espacamento; i++) {
				System.out.print(" ");
			}
			for (int i = 0; i < 24; i++) {
				if (i + 1 == 9)
					System.out.print(tableRow.get(i) + "   ");
				else
					System.out.print(tableRow.get(i) + "  ");
			}

			System.out.println("\n");

			// Armazena o ultimo horario de trabalho do funcionario em questão
			Integer employeerLastWork = getMax(schedule.getValue(employeer));

			// Compara se o horario encontrado é mais tarde que o anterior
			if (employeerLastWork > lastWork)
				lastWork = employeerLastWork;
		}

		System.out.println("Último Horário:" + lastWork + "\n");
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

	// Retorna o maior nome entre os funcionarios da empresa
	public static String getBiggestName(List<Variable> variables) {
		String biggestName = "";

		for (Variable employeer : variables) {
			String name = employeer.getName();
			if (name.length() > biggestName.length())
				biggestName = name;
		}

		return biggestName;
	}
}
