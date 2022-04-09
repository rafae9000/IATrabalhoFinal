package trabalhofinal.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.solver.CspHeuristics;
import aima.core.search.csp.solver.CspListener;
import aima.core.search.csp.solver.CspSolver;
import aima.core.search.csp.solver.FlexibleBacktrackingSolver;
import aima.core.search.csp.solver.inference.AC3Strategy;
import trabalhofinal.csp.ScheduleCSP;
import trabalhofinal.entity.PersonAvalability;
import trabalhofinal.exception.PersonAvalabilityException;
import trabalhofinal.util.Printer;

//Resolução do problema de agendamento basico
public class ScheduleCspDemo {

	public static void main(String[] args) {

		CSP<Variable, List<Integer>> csp;
		CspListener.StepCounter<Variable, List<Integer>> stepCounter = new CspListener.StepCounter<>();
		CspSolver<Variable, List<Integer>> solver;
		Optional<Assignment<Variable, List<Integer>>> solution;
		
		//Listas de disponibilidades
		List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(21, 13, 22, 4, 19));
		List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(10, 9, 6, 21, 15, 14));
		List<Integer> list3 = new ArrayList<Integer>(Arrays.asList(5, 23, 10, 8, 22, 21, 14, 13));
		List<Integer> list4 = new ArrayList<Integer>(Arrays.asList(1, 23, 4, 7, 6, 5, 19, 3));
		List<Integer> list5 = new ArrayList<Integer>(Arrays.asList(2, 14, 7, 10, 21, 13, 4, 15, 18, 11));

		
		try {
			
			//Instanciando funcionario (nome, lista de disponibilidade, carga horaria)
			//Pode lançar exceção do tipo PersonAvalabilityException
			PersonAvalability p1 = new PersonAvalability("Alice", list1, 2);
			PersonAvalability p2 = new PersonAvalability("Bob", list2, 3);
			PersonAvalability p3 = new PersonAvalability("Charlie", list3, 1);
			PersonAvalability p4 = new PersonAvalability("David", list4, 2);
			PersonAvalability p5 = new PersonAvalability("Eve", list5, 4);
			
			//Lista de funcionarios
			List<PersonAvalability> employeers = new ArrayList<PersonAvalability>(Arrays.asList(p4,p2,p3,p1,p5));
			
			System.out.println("PROBLEMA BASICO\n");
			
			//Construção do problema de agendamento basico
			csp = new ScheduleCSP(employeers);
			
			//Definição do algoritmo de busca, heuristicas e inferencias utilizados
			solver = new FlexibleBacktrackingSolver<Variable, List<Integer>>().set(CspHeuristics.mrvDeg())
					.set(new AC3Strategy<Variable, List<Integer>>());
			
			solver.addCspListener(stepCounter);
			stepCounter.reset();
			solution = solver.solve(csp);
			if (solution.isPresent())
				Printer.printSchedule(solution.get());
			
		} catch (PersonAvalabilityException e) {
			//e.printStackTrace();
		}

		System.out.println(stepCounter.getResults() + "\n");
		

	}



}
