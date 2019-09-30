package putriWidiyantiProjectSpringBoot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import putriWidiyantiProjectSpringBoot.model.TabunganModel;

public interface TabunganRepository extends JpaRepository<TabunganModel, Long>{

	@Query(value="SELECT * FROM tabungan_tbl WHERE nik = :nik", nativeQuery = true)
	  List<TabunganModel> getByNik( @Param("nik")String nik );
	
	
	@Query(value="SELECT * FROM tabungan_tbl WHERE nik=:nik ORDER BY id DESC LIMIT 1", nativeQuery=true)
	TabunganModel getSaldo(@Param("nik") String nik);
}
