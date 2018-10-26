package com.lvshou.magic.user;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lvshou.magic.exception.ResultEnum;
import com.lvshou.magic.result.Result;
import com.lvshou.magic.user.entity.OldVip;
import com.lvshou.magic.user.service.OldVipService;
import com.lvshou.magic.utils.MainUUID;

@Controller
@RequestMapping("oldvip")
public class OldVipController {

	@Autowired
	private OldVipService oldVipService;
	
	/*@PostMapping("upload")
	@ResponseBody
	public Result upload(@RequestParam("file")MultipartFile files) throws IOException {
		BufferedInputStream inputStream=new BufferedInputStream(files.getInputStream());
		List<OldVip> lists=readExcel(inputStream);
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (OldVip oldVip : lists) {
			//System.out.println(oldVip.getName()+"\t"+oldVip.getPhone()+"\t"+dateFormat.format(oldVip.getTradeTime()));
			oldVipService.insert(oldVip);
		}
		return new Result<>(ResultEnum.OK,new ModelMap("src", "null"));
	}*/
	
	@GetMapping("findAll")
	@ResponseBody
	public Result findAll(@RequestParam(value="page",required=false,defaultValue="1")int page,
			@RequestParam(value="size",required=false,defaultValue="10") int rows) {
		return new Result<>(ResultEnum.OK,oldVipService.findAll(page-1, rows),oldVipService.allAmount());
	}
	
	@GetMapping("oldvipList")
	public ModelAndView vipLists() {
		return new ModelAndView("oldvipList");
	}
	
	@GetMapping("add")
	public ModelAndView add() {
		Map map=new HashMap<>();
		map.put("oldvip",new OldVip());
		return new ModelAndView("oldvipEditor",map);
	}
	@GetMapping("editor/{id}")
	public ModelAndView editor(@PathVariable("id")String id,Map<String,Object> map) {
		OldVip vip=oldVipService.findId(id);
		map.put("oldvip",vip );
		return new ModelAndView("oldvipEditor",map);
	}
	@DeleteMapping("delete/{id}")
	@ResponseBody
	public void delete(@PathVariable("id")String id) {
		oldVipService.delete(id);
	}
	@ResponseBody
	@PostMapping("deleteAll")
	public Result deleteAll(@RequestBody List<OldVip> oldVips) {
		for (OldVip oldVip : oldVips) {
			oldVipService.delete(oldVip.getId());
		}
		return new Result<>(ResultEnum.OK);
	}
	@PostMapping("save")
	public ModelAndView save(OldVip oldVip,BindingResult bindingResult) {
		if(StringUtils.isEmpty(oldVip.getId())) {
			oldVip.setId(MainUUID.getUUID());
			oldVipService.insert(oldVip);
			return new ModelAndView("oldvipList");
		}
		oldVipService.update(oldVip);
		return new ModelAndView("oldvipList");
	}
	/*private List<OldVip> readExcel(InputStream inputStream) throws IOException {
    	HSSFWorkbook workbook=new HSSFWorkbook(new POIFSFileSystem(inputStream));
		//XSSFWorkbook workbook=new XSSFWorkbook(new POIFSFileSystem(inputStream));
    	HSSFSheet sheet=workbook.getSheet("Sheet1");
    	List<OldVip> lists=new ArrayList<OldVip>();
    	int lastRowIndex = sheet.getLastRowNum();
        System.out.println(lastRowIndex);
        OldVip oldVip=null;
        for (int i = 0; i <= lastRowIndex; i++) {
        	oldVip=new OldVip();
            HSSFRow row = sheet.getRow(i);
            if (row == null) { break; }
            
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
            	HSSFCell cell=row.getCell(j);
            	if (cell==null) continue;
            	calc(cell, j, oldVip);
            }
            lists.add(oldVip);
            System.out.println();
        }
        inputStream.close();
        return lists;
    }
	
	private OldVip calc(HSSFCell cell,int j,OldVip oldVip) {
		DecimalFormat decimalFormat=new DecimalFormat("#");
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			System.out.print(decimalFormat.format(cell.getNumericCellValue()));
			oldVip.setPhone(decimalFormat.format(cell.getNumericCellValue()));
			return oldVip;
		case HSSFCell.CELL_TYPE_STRING:
			System.out.print(" "+cell.getStringCellValue());
			if(j==0)
				oldVip.setName(cell.getStringCellValue());
			if(j==1)
				oldVip.setPhone(cell.getStringCellValue());
			return oldVip;
		case HSSFCell.CELL_TYPE_BLANK:
			System.out.print(" ");
			return oldVip;
		case HSSFCell.CELL_TYPE_FORMULA:
			System.out.print(" "+cell.getDateCellValue().toLocaleString());
			//oldVip.setTradeTime(cell.getDateCellValue());
			return oldVip;
		default:
			System.out.println("");
			return oldVip;
		}
	}*/
	
	/**
	 * 横向查找，第一级查找没有parent的用户，再根据第一级继续往下查找
	 * */
	@GetMapping("checkError")
	@ResponseBody
	public Result checkError(@RequestParam("check")String check) {
		if(check.equalsIgnoreCase("crossError"))
			return new Result<>(ResultEnum.OK,oldVipService.crossError());
		else if (check.equalsIgnoreCase("verticalError")) {
			return new Result<>(ResultEnum.OK,oldVipService.verticalError());
		}
		return new Result<>(500,"传入的参数不正确");
	}
	@GetMapping("findOlds")
	@ResponseBody
	public Result vips(@RequestParam(name="numId",required=false)String numId,
			@RequestParam(name="name",required=false)String name,
			@RequestParam(name="phone",required=false)String phone) {
		OldVip oldVip=new OldVip();
		oldVip.setNumId(numId);
		oldVip.setName(name);
		oldVip.setPhone(phone);
		List<OldVip> list=oldVipService.finds(oldVip);
		return new Result<>(ResultEnum.OK,list,list.size());
	}
	
	@GetMapping("up")
	public ModelAndView upload() {
		return new ModelAndView("upload");
	}
	
}
