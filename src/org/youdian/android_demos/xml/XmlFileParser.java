package org.youdian.android_demos.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

/*
 * 解析 res/xml文件夹下的xml文件
 */
public class XmlFileParser {
	private static final String XML_TAG_TIMEZONE = "timezone";
	private static final String NAME_SPACE = null;
	private static final String WEATHER_ID = "weatherID";
	private static final String GMT_ZONE = "id";
	private Context context;

	public static void parse(Context context, int res) {
		XmlResourceParser parser = null;
		parser = context.getResources().getXml(res);
		try {
			while (parser.next() != XmlResourceParser.START_DOCUMENT)
				continue;
			int eventType = parser.getEventType();
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (eventType == XmlResourceParser.START_TAG) {
					if (parser.getName().equals(XML_TAG_TIMEZONE)) {
						String weatherID = parser.getAttributeValue(NAME_SPACE,
								WEATHER_ID);
						String gmtZone = parser.getAttributeValue(NAME_SPACE,
								GMT_ZONE);
						String cityName = parser.nextText();
						Log.d(XML_TAG_TIMEZONE, "cityName=" + cityName
								+ ",weather id=" + weatherID + ",gmtZone="
								+ gmtZone);
					}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
