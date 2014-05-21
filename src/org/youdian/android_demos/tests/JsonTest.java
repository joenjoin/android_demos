package org.youdian.android_demos.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.youdian.android_demos.MainActivity;
import org.youdian.android_demos.json.JsonParsers;
import org.youdian.android_demos.json.JsonParsers.GsonParser;
import org.youdian.android_demos.json.Person;

import android.test.ActivityInstrumentationTestCase2;

public class JsonTest extends ActivityInstrumentationTestCase2<MainActivity> {
	public static String json="{\"persons\":[{\"phone\":[135623333,44556545],\"name\":\"xiao ming\"," +
			"\"age\":23,\"address\":{\"country\":\"china\",\"province\":\"shanghai\"}}," +
			"{\"phone\":[1332134,4432123],\"name\":\"xiao guang\",\"age\":24,\"address\":" +
			"{\"country\":\"zhongguo\",\"province\":\"beijing\"}}]}";
	public static String jsonForGson="[{\"phone\":[135623333,44556545],\"name\":\"xiao ming\"," +
			"\"age\":23,\"address\":{\"country\":\"china\",\"province\":\"shanghai\"}}," +
			"{\"phone\":[1332134,4432123],\"name\":\"xiao guang\",\"age\":24,\"address\":" +
			"{\"country\":\"zhongguo\",\"province\":\"beijing\"}}]";
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
		p.setAge(24);
		long[] l1={1332134,4432123};
		p.setPhone(l1);
		address=new HashMap<String,String>();
		address.put("country", "zhongguo");
		address.put("province", "beijing");
		p.setAddress(address);
		list.add(p);
		return list;
	}
	public JsonTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	MainActivity mActivity;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		mActivity=getActivity();
	}
	public void testNormalJsonSerializer(){
		JsonParsers.NormalParser parser=new JsonParsers.NormalParser();
		List<Person> list=buildList();
		try {
			//String json=parser.serialize(list);
			String json=parser.serialize2(list);
			System.out.println(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void testNormalJsonParser(){
		JsonParsers.NormalParser parser=new JsonParsers.NormalParser();
		try {
			List<Person> list=parser.parse(json);
			for(Person p:list){
				System.out.println(p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testGsonSerializer(){
		JsonParsers.GsonParser parser=new GsonParser();
		List<Person> list=buildList();
		try {
			String json=parser.serialize(list);
			System.out.println(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testGsonParser(){
		JsonParsers.GsonParser parser=new GsonParser();
		try {
			List<Person> list=parser.parse(jsonForGson);
			assertNotNull(list);
			for(Person p:list){
				System.out.println(p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
