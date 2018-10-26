package com.lvshou.magic.money.export;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.lvshou.magic.money.entity.Money;
import com.lvshou.magic.money.service.MoneyService;
import com.lvshou.magic.utils.MainUUID;

public class ExportMoney {
	
	
	@Autowired
	private MoneyService moneyService;
	
	public void export(HttpServletRequest request,HttpServletResponse response,String date) throws Exception {
		
		String[] headers= {"用户编号","用户名","用户手机号","余额","总积分","总奖励","选择月份总奖励","创建时间"};
		List<Money> moneys=getData(date);
		System.out.println("size="+moneys.size());
		File file=exportToFile(moneys, headers);
		
		BufferedInputStream buffer=new BufferedInputStream(new FileInputStream(file));
		byte[] ars=new byte[1024];
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		int len=0;
		while ((len=buffer.read(ars))!=-1) {
			out.write(ars, 0, len);
		}
		byte[] tempByte=out.toByteArray();
		buffer.close();
		out.close();
		
		
		//response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment;filename="+ MainUUID.getUUID()+".xls");
		response.setHeader("Content-Length", "" + tempByte.length);
		// 获取响应的对象流
		OutputStream outputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		BufferedOutputStream toOut = new BufferedOutputStream(outputStream);
		toOut.write(tempByte);
		toOut.flush();
		toOut.close();
		
		if(file.exists())
			file.delete();
	}
	
	private List<Money> getData(String date) throws Exception{
		int year=0;
		int month=0;
		if(!StringUtils.isEmpty(date)) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
			Date date2=dateFormat.parse(date);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date2);
			year=calendar.get(Calendar.YEAR);
			month=calendar.get(Calendar.MONTH)+1;
		}
		if(year!=0&&month!=0) return moneyService.findMoneyWithMonthReward(year, month);
		else return moneyService.findAllNoMonthRewardNoPage();
		
	}
	private File exportToFile(List<Money> moneys,String[] headers) throws Exception {
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet();
		CellStyle cellStyle=workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
		
		for (int i = 0; i <= moneys.size(); i++) {
			Row row=sheet.createRow(i);
			for (int j = 0; j < headers.length; j++) {
				Cell cell=row.createCell(j);
				if(i==0) cell.setCellValue(headers[j]);
				else {
					Money money=moneys.get(i-1);//获取第i行的数据
					switch (j) {
					case 0:
						cell.setCellValue(money.getNumId());
						break;
					case 1:
						cell.setCellValue(money.getName());
						break;
					case 2:
						cell.setCellValue(money.getPhone());
						break;
					case 3:
						cell.setCellValue(money.getBalance().toPlainString());
						break;
					case 4:
						cell.setCellValue(money.getTotalIntegral().toPlainString());
						break;
					case 5:
						cell.setCellValue(money.getTotalReward().toPlainString());
						break;
					case 6:
						cell.setCellValue(money.getMonthReward().toPlainString());
						break;
					case 7:
						cell.setCellValue(parseDate(money.getCreateTime()));
						break;
					default:
						break;
					}
				}
			}

		}
		
		File file=new File(MainUUID.getUUID()+".xls");
		if(file.exists()) file.delete();
		file.createNewFile();
		workbook.write(file);
		workbook.close();
		return file;
	}
	
	private String parseDate(Date date) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result=simpleDateFormat.format(date);
		return result;
	}
}
