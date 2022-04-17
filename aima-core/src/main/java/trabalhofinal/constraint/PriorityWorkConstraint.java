package trabalhofinal.constraint;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

/*
	Restrição que receber 2 listas de horarios(list1 e list2)
	Todos os valores de list1 devem preceder list2 para que a restrição seja satisfeita
*/

public class PriorityWorkConstraint<VAR extends Variable, VAL> implements Constraint<VAR, VAL> {

	private VAR var1;
	private VAR var2;
	private List<VAR> scope;

	public PriorityWorkConstraint(VAR var1, VAR var2) {
		super();
		this.var1 = var1;
		this.var2 = var2;
		scope = new ArrayList<>(2);
		scope.add(var1);
		scope.add(var2);
	}

	@Override
	public List<VAR> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<VAR, VAL> assignment) {
		List<Integer> list1 = (List<Integer>) assignment.getValue(var1);
		List<Integer> list2 = (List<Integer>) assignment.getValue(var2);

		if (list1 == null || list1.isEmpty() || list2 == null || list2.isEmpty())
			return true;

		for (Integer value1 : list1) {
			for (Integer value2 : list2) {
				if (value1 > value2)
					return false;
			}
		}

		return true;
	}

}
