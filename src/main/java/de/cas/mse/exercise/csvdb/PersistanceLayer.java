package de.cas.mse.exercise.csvdb;

import java.util.List;

// insert / read Funktion für Zeilen wären ausreichend gewesen für dieses Interface
// PersistenceLayer ist nicht glücklich, da nicht aussagekräftig genug. Es soll ja nur
// gespeichert und geladen werden. Storage wäre eine nette Alternative.
public interface PersistanceLayer {
		
	public void persist(String data);
	
	public List<String> loadCsVDB();

}
