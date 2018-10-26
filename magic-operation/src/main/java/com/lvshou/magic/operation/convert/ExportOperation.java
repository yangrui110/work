package com.lvshou.magic.operation.convert;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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

import com.lvshou.magic.operation.statics.Constant;
import com.lvshou.magic.operation.vo.OperationVo;
import com.lvshou.magic.utils.MainUUID;

public class ExportOperation {
	
	public void export(HttpServletRequest request,HttpServletResponse response,List<OperationVo> lists) throws Exception {
		
		String[] headers= {"用户编号","用户名","用户手机号","操作","金额","描述","发起审核时间","审核完毕时间"};
		File file=exportToFile(lists, headers);
		
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
	
	private File exportToFile(List<OperationVo> users,String[] headers) throws Exception {
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet();
		CellStyle cellStyle=workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
		
		for (int i = 0; i <= users.size(); i++) {
			Row row=sheet.createRow(i);
			for (int j = 0; j < headers.length; j++) {
				Cell cell=row.createCell(j);
				if(i==0) cell.setCellValue(headers[j]);
				else {
					OperationVo user=users.get(i-1);//获取第i行的数据
					switch (j) {
					case 0:
						cell.setCellValue(user.getNumId());
						break;
					case 1:
						cell.setCellValue(user.getName());
						break;
					case 2:
						cell.setCellValue(user.getPhone());
						break;
					case 3:
						cell.setCellValue(getOperate(user.getOperate()));
						break;
					case 4:
						cell.setCellValue(user.getMoney().toPlainString());
						break;
					case 5:
						cell.setCellValue(user.getDescribetion());
						break;
					case 6:
						cell.setCellValue(parseDate(user.getCreateTime()));
						break;
					case 7:
						cell.setCellValue(parseDate(user.getUpdateTime()));
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
	
	private String getOperate(String operate) {
		if(operate.equalsIgnoreCase(Constant.WITHDRAW)) {
			return "提现";
		}else if(operate.equalsIgnoreCase(Constant.CONSUME))
			return "消费";
		else if(operate.equalsIgnoreCase(Constant.DUIHUAN))
			return "兑换";
		else {
			return "未知操作";
		}
	}
	private String parseDate(Date date) {
		String result=null;
		if(date!=null) {
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result=simpleDateFormat.format(date);
		}else 
			result=null;
		return result;
	}
}
