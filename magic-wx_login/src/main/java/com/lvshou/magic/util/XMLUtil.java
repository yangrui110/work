package com.lvshou.magic.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.dom4j.Document;
import org.dom4j.Element;

public class XMLUtil {
	
	public static String parseToString(SortedMap<String,String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)|| "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	public static String setXML(String code,String msg) {
		return "<xml><return_code><![CDATA["+code+"]]></return_code><return_msg><![CDATA["+msg+"]]></return_msg></xml>";
	}
	
     public static Map<String, Object> Dom2Map(Document doc) {
    	 
         Map<String, Object> map = new HashMap<String, Object>();
         if (doc == null)
             return map;
         Element root = doc.getRootElement();
         for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
             Element e = (Element) iterator.next();
             List list = e.elements();
             if (list.size() > 0) {
                 map.put(e.getName(), Dom2Map(e));
             } else
                 map.put(e.getName(), e.getText());
         }
         return map;
     }
 
 
     public static Map Dom2Map(Element e) {
         Map map = new HashMap();
         List list = e.elements();
         if (list.size() > 0) {
             for (int i = 0; i < list.size(); i++) {
                 Element iter = (Element) list.get(i);
                 List mapList = new ArrayList();
 
                 if (iter.elements().size() > 0) {
                     Map m = Dom2Map(iter);
                     if (map.get(iter.getName()) != null) {
                         Object obj = map.get(iter.getName());
                         if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                             mapList = new ArrayList();
                             mapList.add(obj);
                             mapList.add(m);
                        }
                         if (obj.getClass().getName().equals("java.util.ArrayList")) {
                             mapList = (List) obj;
                             mapList.add(m);
                             }
                        map.put(iter.getName(), mapList);
                     } else
                         map.put(iter.getName(), m);
                 } else {
                     if (map.get(iter.getName()) != null) {
                         Object obj = map.get(iter.getName());
                         if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                             mapList = new ArrayList();
                             mapList.add(obj);
                             mapList.add(iter.getText());
                         }
                         if (obj.getClass().getName().equals("java.util.ArrayList")) {
                             mapList = (List) obj;
                             mapList.add(iter.getText());
                         }
                         map.put(iter.getName(), mapList);
                     } else
                         map.put(iter.getName(), iter.getText());
                 }
             }
        } else
             map.put(e.getName(), e.getText());
         return map;
    }
 }