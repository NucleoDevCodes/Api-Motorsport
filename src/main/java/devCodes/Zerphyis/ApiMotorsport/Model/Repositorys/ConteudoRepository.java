package devCodes.Zerphyis.ApiMotorsport.Model.Repositorys;

import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Conteudo.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConteudoRepository extends JpaRepository<Conteudo,Long> {
}
