package putriWidiyantiProjectSpringBoot.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import putriWidiyantiProjectSpringBoot.dao.TabunganDao;
import putriWidiyantiProjectSpringBoot.model.TabunganModel;


@RestController
@RequestMapping("/nabung")
public class TabunganController {

	@Autowired
	TabunganDao tabunganDAO;
	
	//save
	@PostMapping("/save")
	public TabunganModel saveNabung(@Valid @RequestBody TabunganModel tabungan) {
		return tabunganDAO.save(tabungan);
	}
	
	//getAll
	@GetMapping("/get")
	public List<TabunganModel> getAll(){
		return tabunganDAO.getAll();
	}
	
	//get BY Id
	@GetMapping("/get1/{id}")
	public ResponseEntity<TabunganModel> getById(@PathVariable(value="id") Long id){
		TabunganModel tb=tabunganDAO.getFindOne(id);
		
		if(tb==null) {
			return ResponseEntity.notFound().build();
		}else {
			
			return ResponseEntity.ok().body(tb);
		}
	}
	
	
	//getByNIK
	@GetMapping("/get2/{nik}")
	public List<TabunganModel> getByNIk(@PathVariable(value="nik") String nik){
		return tabunganDAO.getFindNik(nik);
	}
	@GetMapping("/saldo/{nik}")
	public ResponseEntity<TabunganModel> getNabung(@PathVariable(value="nik") String nik){
		TabunganModel t=tabunganDAO.getFindSaldo(nik);
		
		if(t==null) {
			return ResponseEntity.notFound().build();
		}else {
			
			return ResponseEntity.ok().body(t);
		}
	}
	
	@PutMapping("ubahSaldo/{id}")
	public ResponseEntity<TabunganModel> updateSaldo (@PathVariable(value="id") Long id,@Valid @RequestBody TabunganModel tabungan){
		TabunganModel tm=tabunganDAO.getFindOne(id);
		if(tm==null) {
			return ResponseEntity.notFound().build();
		}else {
			tm.setSaldo(tm.getSaldo()+tm.getDebet()-tm.getKredit());
			tm.setKredit(tabungan.getKredit());
			tm.setDebet(tabungan.getDebet());
			TabunganModel Hasil=tabunganDAO.ubah(tm);
			return ResponseEntity.ok().body(Hasil);
		}

	}
	
	
	@DeleteMapping("hapusSaldo/{id}")
	public ResponseEntity<TabunganModel> deleteSaldo(@PathVariable(value="id")Long id){
		TabunganModel tm=tabunganDAO.getFindOne(id);
		if(tm==null) {
			return ResponseEntity.notFound().build();
		}else {
			tabunganDAO.deleteSaldo(id);
			return  ResponseEntity.ok().build();
		}
	}
	
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<TabunganModel> hapus(@PathVariable(value="id") Long id){
//		TabunganModel tb=tabunganDAO.getFindOne(id);
//		
//		if(tb==null) {
//			return ResponseEntity.notFound().build();
//		}else {
//			tabunganDAO.delete(id);
//			return ResponseEntity.ok().build();
//		}
//	}
//	

}

