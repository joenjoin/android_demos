package org.youdian.android_demos.tests;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.youdian.android_demos.MainActivity;
import org.youdian.android_demos.R;
import org.youdian.android_demos.xml.Person;
import org.youdian.android_demos.xml.XmlFileParser;
import org.youdian.android_demos.xml.XmlParsers;
import org.youdian.android_demos.xml.XmlParsers.Parser;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class XmlTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public XmlTest(Class<MainActivity> activityClass) {
		super(activityClass);
		// TODO Auto-generated constructor stub
	}

	public XmlTest() {
		super(MainActivity.class);
	}

	String xml = "<persons>"
			+ "<person><id>1</id><name>xiaoming</name><age>23</age></person>"
			+ "<person><id>2</id><name>xiaoqiang</name><age>34</age></person>"
			+ "<person><id>3</id><name>xiaodong</name><age>15</age></person>"
			+ "<person><id>4</id><name>xiaoguang</name><age>56</age></person>"
			+ "</persons>";

	MainActivity mActivity;
	Button button;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		mActivity = getActivity();
		// button=(Button)mActivity.findViewById(R.id.btn1);
		// button.performClick();
	}

	public void testXmlResourceParser() {
		XmlFileParser.parse(mActivity, R.xml.xml_timezone_parse);
	}

	public void testSAX() {
		Parser<Person> parser = new XmlParsers.SAXParser();
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		try {
			List<Person> list = parser.parse(is);
			for (Person p : list) {
				System.out.println("id=" + p.getId() + ",name=" + p.getName()
						+ ",age=" + p.getAge());
			}
			String newXml = parser.serialize(list);
			System.out.println("new xml =" + newXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
