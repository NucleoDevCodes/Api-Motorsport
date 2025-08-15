package devCodes.Zerphyis.ApiMotorsport.Model.Repositorys;

import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarroRepository extends JpaRepository<Carro,Long> {
}
