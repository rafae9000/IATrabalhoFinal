package trabalhofinal.csp;

import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import trabalhofinal.constraint.NotCommonElementsListConstraint;
import trabalhofinal.entity.PersonAvalability;

public class ScheduleNewNormalCSP extends CSP<Variable, List<Integer>> {

	public ScheduleNewNormalCSP(List<PersonAvalability> employeers) {
		
		int size = employeers.size();

		//Adiciona as variaveis e seus repectivos dominios ao problema
		for (int i = 0; i < size; i++) {
			
			Variable var = employeers.get(i).getPerson();
			Domain<List<Integer>> domain = employeers.get(i).getDomain();

			addVariable(var);
			setDomain(var, domain);
		}
		
		//Adiciona as restri�oes ao problema
		//somente funcionarios n�o vacinados ter�o restri�oes
		for (int i = 0; i < size; i++) {
			
			if(!employeers.get(i).isVaccinated()) { 
				Variable var1 = getVariables().get(i);
				for (int j = i+1; j < size; j++) {
					
					if(!employeers.get(j).isVaccinated()) {
						Variable var2 = getVariables().get(j);
						addConstraint(new NotCommonElementsListConstraint<>(var1, var2));
					}
				}
			}
		}
	}
}
