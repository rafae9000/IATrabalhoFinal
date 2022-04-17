package trabalhofinal.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import trabalhofinal.exception.PersonAvalabilityException;

public class PersonAvalability {

	// Variable que contem o nome da pessoa
	private Variable person;

	// Lista de disponibidade
	private List<Integer> avalability;

	// Carga horaria
	private Integer workLoad;

	// Dominio do funcionario
	private Domain<List<Integer>> domain;
	
	// Indicador de vacina��o
	private boolean vaccinated;
	
	// Lista de nomes do funcionario que tem precedencia sobre voc�
	private List<String> priorityPersons = new ArrayList<String>();

	public PersonAvalability(String name, List<Integer> avalability, Integer workLoad)
			throws PersonAvalabilityException {

		if (avalability.size() < workLoad)
			throw new PersonAvalabilityException("O empregado " + name + " s� possui " + avalability.size()
					+ " hor�rios disponiveis," + "sendo que sua carga horaria = " + workLoad);

		Collections.sort(avalability);
		this.avalability = avalability;
		this.workLoad = workLoad;
		this.person = new Variable(name);

		List<List<Integer>> aux = new ArrayList<>();
		findDomain(0, 0, new Integer[this.workLoad], aux);

		this.domain = new Domain<>(aux);
	}
	
	public PersonAvalability(String name, List<Integer> avalability, Integer workLoad, boolean vaccinated) throws PersonAvalabilityException {
		this(name,avalability,workLoad);
		this.vaccinated = vaccinated;
	}

	/*
	 * Encontrar todas as combina��es de horario que o funcionario pode escolher de
	 * acordo com sua lista de disponibilidade e carga horaria
	 * 
	 * Ex:
	 * 
	 * lista de horarios = [ 6 , 12 , 13 ] 
	 * carga horaria = 2 
	 * dominio [ [6,12] ,[6,13] , [12,13] ]
	 * 
	 * fonte: https://www.geeksforgeeks.org/print-subsets-given-size-set/
	 */
	public void findDomain(int index, int i, Integer data[], List<List<Integer>> result) {

		// Foi encontrada uma nova combina��o de horario
		if (index == this.workLoad) {

			List<Integer> value = new ArrayList<Integer>();
			for (int j = 0; j < workLoad; j++)
				value.add(data[j]);

			// Adiciona a nova combina��o na lista de resultado
			result.add(value);
			return;
		}

		// Todos o valores da lista de disponibilidade foram usados
		if (i >= this.avalability.size())
			return;

		data[index] = this.avalability.get(i);

		findDomain(index + 1, i + 1, data, result);

		findDomain(index, i + 1, data, result);
	}

	public List<Integer> getAvalability() {
		return avalability;
	}

	public Integer getWorkLoad() {
		return workLoad;
	}

	public Variable getPerson() {
		return person;
	}

	public Domain<List<Integer>> getDomain() {
		return domain;
	}

	public boolean isVaccinated() {
		return vaccinated;
	}

	public List<String> getPriorityPersons() {
		return priorityPersons;
	}

	public void setPriorityPersons(List<String> priorityPersons) {
		this.priorityPersons = priorityPersons;
	}

}
