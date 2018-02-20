package de.cas.mse.exercise.csvdb.storage;
import java.util.UUID;

public class GuidGenerator {
	public static String generateGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
}
