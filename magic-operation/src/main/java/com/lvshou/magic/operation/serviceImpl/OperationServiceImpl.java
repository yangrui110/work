package com.lvshou.magic.operation.serviceImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvshou.magic.exception.CommonException;
import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.operation.convert.OperationConvert;
import com.lvshou.magic.operation.dao.OperationDao;
import com.lvshou.magic.operation.entity.Operation;
import com.lvshou.magic.operation.service.OperationService;
import com.lvshou.magic.operation.statics.Constant;
import com.lvshou.magic.operation.vo.OperationVo;
import com.lvshou.magic.utils.MainUUID;

@Service
public class OperationServiceImpl implements OperationService{

	@Autowired
	OperationDao operationDao;
	
	@Override
	public Operation findOne(Operation operation) {
		// TODO Auto-generated method stub
		if(operation==null) {
			return null;
		}
		if(operation.getId()!=null) {
			return operationDao.findById(operation.getId()).orElse(null);
		}
		return null;
	}

	@Override
	public List<Operation> findAll(String userId) {
		// TODO Auto-generated method stub
		return operationDao.findAllByUserId(userId);
	}

	@Override
	public Operation update(Operation operation) {
		// TODO Auto-generated method stub
		if(operation==null) {throw new CommonException(ResultEnum.UPDATE_NOT_EXIT);}
		Operation operation2=findOne(operation);
		if(operation2==null) {throw new CommonException(ResultEnum.UPDATE_NOT_FIND);}
	
		if(operation.getMoney()!=null) {
			operation2.setMoney(operation.getMoney());
		}
		if(operation.getDescribetion()!=null) {
			operation2.setDescribetion(operation.getDescribetion());
		}
		if(operation.getOperate()!=null) {
			operation2.setOperate(operation.getOperate());
		}
		if(operation.getStatus()!=0) {
			operation2.setStatus(operation.getStatus());
		}
		operationDao.save(operation2);
		return operation2;
	}

	@Override
	@Transactional
	public Operation insert(Operation operation) {
		// TODO Auto-generated method stub
		if(operation==null) {return null;}
		if(operation.getId()==null) {
			operation.setId(MainUUID.getUUID());
		}
		if(operation.getMoney()==null) {
			operation.setMoney(new BigDecimal(0));
		}
		operationDao.save(operation);
		return operation;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		try {
			operationDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new CommonException(ResultEnum.ID_NOT_EXIT);
		}
	}

	@Override
	public Operation findById(String id) {
		// TODO Auto-generated method stub
		return operationDao.findById(id).orElse(null);
	}

	@Override
	public List<Operation> consumeList(int page,int size) {
		// TODO Auto-generated method stub
		return operationDao.findByOperateAndStatus(Constant.CONSUME,0, PageRequest.of(page, size)).getContent();
	}

	@Override
	public List<Operation> withList(int status,int page,int size) {
		// TODO Auto-generated method stub
		List<Operation> list=operationDao.findByOperateAndStatus(Constant.WITHDRAW,status, PageRequest.of(page, size)).getContent();
		return list;
	}

	

	@Override
	public int findOperationAmount() {
		// TODO Auto-generated method stub
		return operationDao.findAll().size();
	}

	@Override
	public int findByStatusAmount(int status) {
		// TODO Auto-generated method stub
		return operationDao.findOperationStatusAmount(status);
	}

	@Override
	public List<OperationVo> findMonth(String date, int status,int page,int size) throws ParseException {
		// TODO Auto-generated method stub
		Calendar calendar=OperationConvert.parseCalendar(date);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		return OperationConvert.convertToVo(operationDao.findMonth(status, year, month,PageRequest.of(page-1, size)));
	}

	@Override
	public int countMonth(String date, int status) throws ParseException {
		// TODO Auto-generated method stub
		Calendar calendar=OperationConvert.parseCalendar(date);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		return operationDao.countMonth(status, year, month);
	}

	@Override
	public List<OperationVo> findNames(String name, int status,int page,int size) {
		// TODO Auto-generated method stub
		return OperationConvert.convertToVo(operationDao.findNames(name, status,PageRequest.of(page-1, size)));
	}

	@Override
	public int countNames(String name, int status) {
		// TODO Auto-generated method stub
		return operationDao.countNames(name, status);
	}

	@Override
	public List<OperationVo> findNumId(String numId, int status,int page,int size) {
		// TODO Auto-generated method stub
		return OperationConvert.convertToVo(operationDao.findNumId(numId, status,PageRequest.of(page-1, size)));
	}

	@Override
	public int countNumId(String numId, int status) {
		// TODO Auto-generated method stub
		return operationDao.countNumId(numId, status);
	}

	@Override
	public List<OperationVo> findMonthNoPage(String date, int status) throws ParseException {
		// TODO Auto-generated method stub
		Calendar calendar=OperationConvert.parseCalendar(date);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		return OperationConvert.convertToVo(operationDao.findMonthNoPage(status, year, month));
	}

	@Override
	public List<OperationVo> findsNoPage(int status) {
		// TODO Auto-generated method stub
		return OperationConvert.convertToVo(operationDao.findsNoPage(status));
	}

	@Override
	public List<OperationVo> findPhone(String phone, int status,int page,int size) {
		// TODO Auto-generated method stub
		return OperationConvert.convertToVo(operationDao.findPhone(phone, status,PageRequest.of(page-1, size)));
	}

	@Override
	public int countPhone(String phone, int status) {
		// TODO Auto-generated method stub
		return operationDao.countPhone(phone, status);
	}

	@Override
	public List<OperationVo> finds(int status,int page,int size) {
		// TODO Auto-generated method stub
		return OperationConvert.convertToVo(operationDao.finds(status,PageRequest.of(page-1, size)));
	}

	@Override
	public int counts(int status) {
		// TODO Auto-generated method stub
		return operationDao.counts(status);
	}

}
