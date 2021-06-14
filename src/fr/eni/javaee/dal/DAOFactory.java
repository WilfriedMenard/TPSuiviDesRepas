package fr.eni.javaee.dal;

public abstract class DAOFactory {
	public static SuiviRepasDAO getSuiviRepasDAO() {
		return new SuiviRepasJdbcImpl();
	}
}
