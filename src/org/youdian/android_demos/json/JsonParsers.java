package org.youdian.android_demos.json;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/*
 * Json格式规范 RFC4627
 * Json 格式创建和解析，有两种方式
 * 1.系统类库 org.json.*;
 * 2.使用google的gson库
 * 
 * 两种解析方式的区别，当有多个对象时，gson直接生成[]字符串,而不是{"persons":[]}
 */
public class JsonParsers {
	

	public interface Parser<T>{
		
		public List<T> parse(String json) throws Exception;
		public String serialize(List<T> list) throws Exception;
	}
	
	public static class NormalParser implements Parser<Person>{

		@Override
		public List<Person> parse(String json) throws Exception {
			// TODO Auto-generated method stub
			List<Person> list=new ArrayList<Person>();
			JSONTokener tokener=new JSONTokener(json);
			JSONObject personsObject=(JSONObject) tokener.nextValue();
			JSONArray  persons=personsObject.getJSONArray("persons");
			for(int i=0;i<persons.length();i++){
				JSONObject person=(JSONObject) persons.opt(i);
				Person p=new Person();
				String name=person.getString("name");
				p.setName(name);
				int age=person.getInt("age");
				p.setAge(age);
				JSONArray phones=person.getJSONArray("phone");
				long[] phone=new long[2];
				phone[0]=phones.getLong(0);
				phone[1]=phones.getLong(1);
				p.setPhone(phone);
				JSONObject addresses=person.getJSONObject("address");
				HashMap<String,String> address=new HashMap<String,String>();
				String country=addresses.optString("country");
				String province=addresses.optString("province");
				address.put("country", country);
				address.put("province", province);
				p.setAddress(address);
				list.add(p);
				
			}
			return list;
		}

		@Override
		public String serialize(List<Person> list) throws Exception {
			// TODO Auto-generated method stub
			
			JSONObject persons=new JSONObject();
			JSONArray array=new JSONArray();
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
				array.put(person);
			}
			persons.put("persons", array);
			return persons.toString(2);
		}
		
		public String serialize2(List<Person> list) throws Exception{
			JSONStringer jsonText=new JSONStringer();
			jsonText.object();
			jsonText.key("persons");
			jsonText.array();
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
			jsonText.endArray();
			jsonText.endObject();
			return jsonText.toString();
			
		}
		
	}
	
	public static class GsonParser implements Parser<Person>{

		@Override
		public List<Person> parse(String json) throws Exception {
			// TODO Auto-generated method stub
			Gson gson=new Gson();
			Type listType=new TypeToken<List<Person>>(){}.getType();
			List<Person> list=gson.fromJson(json,listType);
			return list;
		}

		@Override
		public String serialize(List<Person> list) throws Exception {
			// TODO Auto-generated method stub
			Gson gson=new Gson();
			Type listType=new TypeToken<List<Person>>(){}.getType();
			String json=gson.toJson(list,listType);
			return json;
		}
		
	}

}
