package com.lvshou.magic.operation.service;

import java.text.ParseException;
import java.util.List;

import com.lvshou.magic.operation.entity.Operation;
import com.lvshou.magic.operation.vo.OperationVo;

public interface OperationService {

	public int findOperationAmount();
	public Operation findById(String id);
	public Operation findOne(Operation operation);
	public List<Operation> findAll(String userId);
	public Operation update(Operation operation);
	public Operation insert(Operation operation);
	public void delete(String id);
	public List<Operation> consumeList(int page,int size);
	public List<Operation> withList(int status,int page,int size);
	public int findByStatusAmount(int status);
	/**按照月份查询
	 * @throws ParseException */
	public List<OperationVo> findMonth(String date,int status,int page,int size) throws ParseException;
	public int countMonth(String date,int status) throws ParseException;
	public List<OperationVo> findMonthNoPage(String date,int status) throws ParseException;
	/**按照名字查询*/
	public List<OperationVo> findNames(String name,int status,int page,int size);
	public int countNames(String name,int status);
	/**按照编号查询*/
	public List<OperationVo> findNumId(String numId,int status,int page,int size);
	public int countNumId(String numId,int status);
	/**按照手机号查询*/
	public List<OperationVo> findPhone(String phone,int status,int page,int size);
	public int countPhone(String phone,int status);
	/**查找所有*/
	public List<OperationVo> finds(int status,int page,int size);
	public int counts(int status);
	public List<OperationVo> findsNoPage(int status);
}
