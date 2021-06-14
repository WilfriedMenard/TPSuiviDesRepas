package fr.eni.javaee.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.bo.Repas;
import fr.eni.javaee.exception.BusinessException;
import fr.eni.javaee.bo.Aliment;

public class SuiviRepasJdbcImpl implements SuiviRepasDAO {
	private static final String INSERT_REPAS = "INSERT INTO REPAS (dateRepas, heureRepas) VALUES (?,?)";
	private static final String INSERT_ALIMENT = "INSERT INTO ALIMENTS (nomAliment, repas) VALUES (?,?) ";
	private static final String SELECT_ALL = "SELECT * FROM REPAS INNER JOIN ALIMENTS ON idRepas=repas ORDER BY dateRepas DESC, heureRepas DESC";

	// Ajout d'un repas avec INSERT_REPAS
	@Override
	public Repas ajoutRepas(Repas repas) throws BusinessException {
		
		if (repas == null) {
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreurDAL.INSERT_OBJECT_NULL);
			throw be;
		}
		
		try (Connection cnx = JdbcTools.getConnection()) {

			try {
				cnx.setAutoCommit(false);
				
				// Insertion des données dans INSERT_REPAS
				PreparedStatement pStmt1 = cnx.prepareStatement(INSERT_REPAS, PreparedStatement.RETURN_GENERATED_KEYS);
				pStmt1.setDate(1, Date.valueOf(repas.getDate()));
				pStmt1.setTime(2, Time.valueOf(repas.getHeure()));
				pStmt1.executeUpdate();

				// id autogénéré à chaque insertion d'un repas
				ResultSet rs = pStmt1.getGeneratedKeys();
				if (rs.next()) {
					repas.setIdRepas(rs.getInt(1)); // 1 : numéro de la colonne
				}
				
				// Insertion des aliments dans le repas
				for (Aliment chaqueAliment : repas.getListAlimentsRepas()) {
						PreparedStatement pStmt2 = cnx.prepareStatement(INSERT_ALIMENT);
						pStmt2.setString(1, chaqueAliment.getNomAliment());
						pStmt2.setInt(2, repas.getIdRepas());
						pStmt2.executeUpdate();
				}
				cnx.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				BusinessException be = new BusinessException();
				be.ajouterErreur(CodesErreurDAL.INSERT_REPAS_ECHEC);
				throw be;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreurDAL.INSERT_REPAS_ECHEC);
			throw be;
		}
		return repas;
	}
	
	@Override
	public List<Repas> selectAll() throws BusinessException {
		
		List<Repas> listeRepas = new ArrayList<>();
		
		try (Connection cnx = JdbcTools.getConnection()) {

			cnx.setAutoCommit(false);
			// Sélectionner tous les repas en BDD
			Statement stmt1 = cnx.createStatement();
			ResultSet rs = stmt1.executeQuery(SELECT_ALL);
				
			int idPrecedent = 0;
			Repas repas = null;
			while (rs.next()) {
				int idRepasEnCours = rs.getInt("idRepas");
				if (idRepasEnCours != idPrecedent) { // si l'id est différent du précédent on crée un nouveau repas
					LocalDate dateRepas = rs.getDate("dateRepas").toLocalDate();
					LocalTime heureRepas = rs.getTime("heureRepas").toLocalTime();
					repas = new Repas(idRepasEnCours, dateRepas, heureRepas);
					listeRepas.add(repas);
				}
				String nomAliment = rs.getString("nomAliment"); // Le string obtenu par le ResultSet
				int idAliment = rs.getInt("idAliment"); // Le Int obtenu par le ResultSet
				Aliment aliment = new Aliment(idAliment, nomAliment);
				repas.ajouterAliment(aliment);	
			}
				
		
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException be = new BusinessException();
			be.ajouterErreur(CodesErreurDAL.SELECTALL_REPAS_ECHEC);
			throw be;
		}
		return listeRepas;
	}
}
