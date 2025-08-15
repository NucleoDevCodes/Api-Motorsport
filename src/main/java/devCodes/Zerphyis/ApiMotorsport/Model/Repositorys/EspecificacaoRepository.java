package devCodes.Zerphyis.ApiMotorsport.Model.Repositorys;

import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Especificao.EspecificacaoTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecificacaoRepository extends JpaRepository<EspecificacaoTecnica,Long> {
}
