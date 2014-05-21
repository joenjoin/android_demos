package org.youdian.android_demos.json;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
/*
 * Json格式规范 RFC4627
 * Json 格式创建和解析，有两种方式
 * 1.系统类库 org.json.*;
 * 2.使用google的gson库
 */
public class JsonParsers {
	

	public  static List<Person> buildList(){
		List<Person> list=new ArrayList<Person>();
		Person p=new Person();
		p.setName("xiao ming");
		p.setAge(23);
		long[] l={135623333,44556545};
		p.setPhone(l);
		HashMap<String,String> address=new HashMap<String,String>();
		address.put("country", "china");
		address.put("province", "shanghai");
		p.setAddress(address);
		list.add(p);
		p=new Person();
		p.setName("xiao guang");
		p.setAge(23);
		long[] l1={1332134,4432123};
		p.setPhone(l1);
		address=new HashMap<String,String>();
		address.put("country", "zhongguo");
		address.put("province", "beijing");
		p.setAddress(address);
		list.add(p);
		return list;
	}
	public interface Parser<T>{
		
		public List<T> parse(InputStream xml) throws Exception;
		public String serialize(List<T> list) throws Exception;
	}
	
	public static class NormalParser implements Parser<Person>{

		@Override
		public List<Person> parse(InputStream xml) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String serialize(List<Person> list) throws Exception {
			// TODO Auto-generated method stub
			JSONObject persons=new JSONObject();
			for(Person p:list){
				JSONObject person=new JSONObject();
				JSONArray phone=new JSONArray();
				phone.put(p.getPhone()[0]).put(p.getPhone()[1]);
				person.put("phone", phone);
				person.put("name", p.getName());
				person.put("age", p.getAge());
				JSONObject address=new JSONObject();
				address.put("country", p.getAddress().get("country"));
				address.put("province", p.getAddress().get("province"));
				person.put("address", address);
				persons.put("person", person);
			}
			
			return persons.toString(2);
		}
		
		public String serialize2(List<Person> list) throws Exception{
			JSONStringer jsonText=new JSONStringer();
			jsonText.object();
			for(Person p:list){
				jsonText.object();
				jsonText.key("phone");
				jsonText.array();
				jsonText.value(p.getPhone()[0]).value(p.getPhone()[1]);
				jsonText.endArray();
				jsonText.key("name");
				jsonText.value(p.getName());
				jsonText.key("age");
				jsonText.value(p.getAge());
				jsonText.key("address");
				jsonText.object();
				jsonText.key("country");
				jsonText.value(p.getAddress().get("country"));
				jsonText.key("province");
				jsonText.value(p.getAddress().get("province"));
				jsonText.endObject();
				jsonText.endObject();
			}
			jsonText.endObject();
			return jsonText.toString();
			
		}
		
	}

}
