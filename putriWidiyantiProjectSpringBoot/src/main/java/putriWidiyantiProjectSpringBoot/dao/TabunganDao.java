package putriWidiyantiProjectSpringBoot.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import putriWidiyantiProjectSpringBoot.model.TabunganModel;
import putriWidiyantiProjectSpringBoot.repository.TabunganRepository;


@Service
public class TabunganDao {

	@Autowired
	TabunganRepository tabunganRepository;
	
	//save
	public TabunganModel save(TabunganModel tabunganModel) {
		TabunganModel tbm=tabunganModel;
		TabunganModel tbng=tabunganRepository.getSaldo(tabunganModel.getNik());
		if(tbng==null) {
			tbm.setSaldo(0-tbm.getDebet()+tbm.getKredit());
			return tabunganRepository.save(tbm);
		}else {
			tbm.setSaldo(tbng.getSaldo()-tbm.getDebet()+tbm.getKredit());
			return tabunganRepository.save(tbm);
		}
	}
	
	//getAll
	public List<TabunganModel> getAll(){
		return tabunganRepository.findAll();
	}
	
	//get By Id
	public TabunganModel getFindOne(Long id) {
		return tabunganRepository.findOne(id);
	}
	
	//get By NIK
	public List<TabunganModel> getFindNik(String nik) {
			return tabunganRepository.getByNik(nik);
	}

	public TabunganModel getFindSaldo(String nik) {
		return tabunganRepository.getSaldo(nik);
	}
	
	//update Saldo
	public TabunganModel ubah(TabunganModel tabungModel) {
		TabunganModel tbng=tabungModel;
		TabunganModel nabung=tabunganRepository.findOne(tbng.getId());
		nabung.setSaldo(nabung.getSaldo()-tbng.getDebet()+tbng.getKredit());
		//nabung.setSaldo(nabung.getSaldo()+nabung.getKredit()-nabung.getDebet()-tbng.getKredit()+tbng.getDebet());
		nabung.setDebet(tbng.getDebet());
		nabung.setKredit(tbng.getKredit());
		int hasil=nabung.getSaldo();
		List<TabunganModel> dataList=tabunganRepository.getByNik(tbng.getNik());
		for (TabunganModel data : dataList) {
			if(data.getId() > tbng.getId()) {
				TabunganModel hasil1=tabunganRepository.findOne(data.getId());
				hasil1.setSaldo(hasil-hasil1.getDebet()+hasil1.getKredit());
				tabunganRepository.save(hasil1);
				hasil=hasil1.getSaldo();
			}
		}
		return tabunganRepository.save(nabung);
	}
	
	
	//hapus Saldo
	public void deleteSaldo(Long id) {
		TabunganModel tnk=tabunganRepository.findOne(id);
		List<TabunganModel> dataList=tabunganRepository.getByNik(tnk.getNik());
		for (TabunganModel data : dataList) {
			if(data.getId() > id) {
				TabunganModel hasil=tabunganRepository.findOne(data.getId());
				hasil.setSaldo(hasil.getSaldo()+tnk.getDebet()-tnk.getKredit());
				tabunganRepository.save(tnk);					
			}
		}
		tabunganRepository.delete(id);
	}
	}