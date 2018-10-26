package com.lvshou.magic.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lvshou.magic.dao.MenuDao;
import com.lvshou.magic.entity.Menu;
import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.service.MenuService;
import com.lvshou.magic.utils.MainUUID;

@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Menu> findAll(int page, int size) {
		// TODO Auto-generated method stub
		return menuDao.findAll(PageRequest.of(page, size)).getContent();
	}

	@Override
	public List<Menu> findByType(int type) {
		// TODO Auto-generated method stub
		return menuDao.findByType(type);
	}

	@Override
	public Menu update(Menu menu) {
		// TODO Auto-generated method stub
		if(menu.getId()==null||menu.getId().equals("")) {
			throw new CommonException(ResultEnum.ERROR);
		}
		Menu menu2=new Menu();
		menu2.setId(menu.getId());
		if(menu.getTitle()!=null) {
			menu2.setTitle(menu.getTitle());
		}
		if(menu.getType()!=0) {
			menu2.setType(menu.getType());
		}
		if(menu.getIcon()!=null) {
			menu2.setIcon(menu.getIcon());
		}
		if(menu.getCreateTime()!=null) {
			menu2.setCreateTime(menu.getCreateTime());
		}
		menuDao.save(menu2);
		return menu2;
	}

	@Override
	public Menu findById(String id) {
		// TODO Auto-generated method stub
		return menuDao.findById(id).orElse(null);
	}

	@Override
	public Menu insert(Menu menu) {
		// TODO Auto-generated method stub
		if(menu==null) {
			throw new CommonException(ResultEnum.ERROR);
		}
		if(menu.getId()==null||menu.getId().equals("")) {
			menu.setId(MainUUID.getUUID());
		}
		menuDao.save(menu);
		return menu;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		menuDao.deleteById(id);
	}

	@Override
	public int findMenuCount() {
		// TODO Auto-generated method stub
		return menuDao.findMenuAmount();
	}

}
