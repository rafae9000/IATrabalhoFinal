package trabalhofinal.csp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import trabalhofinal.constraint.NotCommonHoursConstraint;
import trabalhofinal.entity.PersonAvalability;
import trabalhofinal.exception.PersonAvalabilityException;
import trabalhofinal.exception.ScheduleHourRestrictionException;

//Responsavel por montar o problema de agendamento com restrição de horario
public class ScheduleHourRestrictionCSP extends CSP<Variable, List<Integer>> {

	public ScheduleHourRestrictionCSP(List<PersonAvalability> employeers, Integer minHour, Integer maxHour)
			throws ScheduleHourRestrictionException {

		Variable var;
		Domain<List<Integer>> domain;

		int size = employeers.size();

		// Adiciona as variaveis e seus repectivos dominios ao problema
		for (int i = 0; i < size; i++) {

			PersonAvalability employeer = employeers.get(i);
			String name = employeer.getPerson().getName();
			Integer workLoad = employeer.getWorkLoad();

			// Nova lista de disponibilidade do funcionario,estando de acordo com o
			// funcionamento da empresa
			List<Integer> newAvalability = applyHourRestriction(employeer.getAvalability(), minHour, maxHour);

			try {

				// Aqui é verificado se o funcionario consegue cumprir sua carga horaria com a
				// nova lista de disponibilidade
				employeer = new PersonAvalability(employeer.getPerson().getName(), newAvalability,
						employeer.getWorkLoad());

			} catch (PersonAvalabilityException e) {
				throw new ScheduleHourRestrictionException("Com a aplicação de restrições de horario, o empregado "
						+ name + " ficou com apenas " + newAvalability.size() + " horarios disponiveis,"
						+ "sendo que sua carga horaria = " + workLoad);
			}

			var = employeer.getPerson();
			domain = employeer.getDomain();

			addVariable(var);
			setDomain(var, domain);
		}

		// Adiciona as restriçoes ao problema
		for (int i = 0; i < size; i++) {
			Variable var1 = getVariables().get(i);
			for (int j = i + 1; j < size; j++) {
				Variable var2 = getVariables().get(j);
				addConstraint(new NotCommonHoursConstraint<>(var1, var2));
			}
		}
	}

	// Receber a lista de disponibilidade do funcionario e faz uma filtragem,
	// deixando apenas horarios ques estão dentro do funcionamento da empresa
	public List<Integer> applyHourRestriction(List<Integer> availability, Integer minHour, Integer maxHour) {

		List<Integer> removedHours = new ArrayList<Integer>();

		for (Integer hour : availability) {
			if (hour < minHour || hour > maxHour)
				removedHours.add(hour);
		}

		availability.removeAll(removedHours);
		return availability;
	}
}
