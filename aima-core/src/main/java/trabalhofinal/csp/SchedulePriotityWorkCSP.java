package trabalhofinal.csp;

import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import trabalhofinal.constraint.NotCommonHoursConstraint;
import trabalhofinal.constraint.PriorityWorkConstraint;
import trabalhofinal.entity.PersonAvalability;

//Responsavel por montar o problema de agendamento com prioridade de trabalho
public class SchedulePriotityWorkCSP extends CSP<Variable, List<Integer>> {

	public SchedulePriotityWorkCSP(List<PersonAvalability> employeers) {

		int size = employeers.size();

		// Adiciona as variaveis e seus repectivos dominios ao problema
		for (int i = 0; i < size; i++) {

			Variable var = employeers.get(i).getPerson();
			Domain<List<Integer>> domain = employeers.get(i).getDomain();

			addVariable(var);
			setDomain(var, domain);
		}

		// Adiciona as restri�oes de choque de horario
		for (int i = 0; i < size; i++) {
			Variable var1 = getVariables().get(i);
			for (int j = i + 1; j < size; j++) {

				Variable var2 = getVariables().get(j);
				addConstraint(new NotCommonHoursConstraint<>(var1, var2));
			}
		}

		// Adiciona as restri��es de precedencia de trabalho
		for (int i = 0; i < size; i++) {
			
			Variable var1 = getVariables().get(i);
			String name1 = var1.getName();
			
			for (int j = 0; j < size; j++) {
				if (i != j) {
					
					Variable var2 = getVariables().get(j);
					List<String> priorities = employeers.get(j).getPriorityPersons();

					if (verifyPriority(name1, priorities))
						//restri��o estabelece que var1 tem precedencia sobre var2
						addConstraint(new PriorityWorkConstraint<>(var1, var2));

				}

			}
		}

	}

	// Verificar se o funcionario de nome "nameEmployer" est� presente na lista de
	// prioridade "priorities"
	private boolean verifyPriority(String nameEmployee, List<String> priorities) {

		if (priorities.isEmpty())
			return false;

		for (String name : priorities) {
			if (name.equals(nameEmployee))
				return true;
		}

		return false;
	}
}
