package devCodes.Zerphyis.ApiMotorsport.Model.Repositorys;

import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Upload.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends JpaRepository<Upload,Long> {
}
