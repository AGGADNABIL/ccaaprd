package org.capvalue.recrute.dao;


import org.capvalue.recrute.domaine.Offre;
import org.capvalue.recrute.domaine.TypeContrat;
import org.capvalue.recrute.domaine.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OffreRepository extends JpaRepository<Offre, Long> {
	
	
	@Query("select c from OffreCompetence c where c.competence.codeCompetence=:id")
	List<Offre> OffreByCompetence(@Param("id") Long id);
	
	@Query("select c from Offre c where c.ville.codeVille=:id")
	List<Offre> OffreByVille(@Param("id") Long id);
	
	@Query("select c from Offre c where c.typeContrat.codeTypeContrat=:id")
	List<Offre> OffreByTypeContrat(@Param("id") Long id);
	
	
	@Query("SELECT o FROM Offre o join o.postulers p join p.candidat c  WHERE c.username=:username GROUP BY o.codeOffre")
	List<Offre> offresByIdCandidat(@Param("username") String username);

    @Query(value =
			"select * from offre where offre.code_ville = (select code_ville from ville where nom_ville like %:b%)  or offre.code_type_contrat = (select code_type_contrat from type_contrat where titre like %:a%) or offre.code_offre in (SELECT offre_competence.offre from offre_competence where offre_competence.competence in (select competence.code_competence from competence where competence.titre like %:c%))",
			nativeQuery = true)
/*
 * @Query(value =
			"select * from offre where offre.code_ville = (select code_ville from ville where nom_ville = :b)  or offre.code_type_contrat = (select code_type_contrat from type_contrat where titre = :a) or offre.code_offre in (SELECT offre_competence.offre from offre_competence where offre_competence.competence in (select competence.code_competence from competence where competence.titre=:c))",
			nativeQuery = true)*/    
    //@Query("select o from Offre o join o.competences c where o.typeContrat.titre = :a or o.ville.nomVille = :b ")
    List<Offre> offresSearch(@Param("a") String typeContrat, @Param("b") String ville, @Param("c") String competence);

    @Query("SELECT o FROM Offre o WHERE o.stat=true")
	List<Offre> offresByStat();
    
    @Query("SELECT o FROM Offre o WHERE o.emailSended=false")
   	List<Offre> offresByIsNotSended();
    
    @Query("SELECT o FROM Offre o WHERE o.emailSended=false and o.etat=true  and o.stat=false  ORDER BY o.dateExpiration DESC")
    List<Offre> findNotExpired();
    
    @Query("SELECT o FROM Offre o GROUP BY o.ville.codeVille")
    List<Offre> findAllNotDuplicateVille();
    
    @Query("SELECT o FROM Offre o GROUP BY o.typeContrat.codeTypeContrat")
    List<Offre> findAllNotDuplicateContrat();
    
    
}
