package com.raipeng.SAXParseXML;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.test.model.Student;

/**
 * 功能描述:采用sax方式解析XML<br>
 * 
 * @author sxyx2008
 * 
 */
public class ParseXML extends DefaultHandler {

	// 存放遍历集合
	private List<Student> list;
	// 构建Student对象
	private Student student;
	// 用来存放每次遍历后的元素名称(节点名称)
	private String tagName;

	public List<Student> getList() {
		return list;
	}

	public void setList(List<Student> list) {
		this.list = list;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	// 只调用一次 初始化list集合
	@Override
	public void startDocument() throws SAXException {
		list = new ArrayList<Student>();
	}

	// 调用多次 开始解析
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("student")) {
			student = new Student();
			// 获取student节点上的id属性值
			student.setId(Integer.parseInt(attributes.getValue(0)));
			// 获取student节点上的group属性值
			student.setGroup(Integer.parseInt(attributes.getValue(1)));
		}
		this.tagName = qName;
	}

	// 调用多次
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("student")) {
			this.list.add(this.student);
		}
		this.tagName = null;
	}

	// 只调用一次
	@Override
	public void endDocument() throws SAXException {
	}

	// 调用多次
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (this.tagName != null) {
			String date = new String(ch, start, length);
			if (this.tagName.equals("name")) {
				this.student.setName(date);
			} else if (this.tagName.equals("sex")) {
				this.student.setSex(date);
			} else if (this.tagName.equals("age")) {
				this.student.setAge(Integer.parseInt(date));
			} else if (this.tagName.equals("email")) {
				this.student.setEmail(date);
			} else if (this.tagName.equals("birthday")) {
				this.student.setBirthday(date);
			} else if (this.tagName.equals("memo")) {
				this.student.setMemo(date);
			}
		}
	}

	// 冒泡排序（从大到小）
	public void sortting(int a[]) {
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] < a[j]) {
					int temp;
					temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			System.out.println("" + a[i] + "");
		}
	}

	// 冒泡排序（从小到大）
	public void sort(int a[]) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length - i - 1; j++) {
				if (a[j] > a[j + 1]) {
					int temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
		for (int item : a) {
			System.out.println(item);
		}
	}

	public static void main(String[] args) {
		SAXParser parser = null;
		try {
			// 构建SAXParser
			parser = SAXParserFactory.newInstance().newSAXParser();
			// 实例化 DefaultHandler对象
			ParseXML parseXml = new ParseXML();
			// 加载资源文件 转化为一个输入流
			InputStream stream = ParseXML.class.getClassLoader()
					.getResourceAsStream("Student.xml");
			// 调用parse()方法
			parser.parse(stream, parseXml);
			// 遍历结果
			List<Student> list = parseXml.getList();

			/*System.out.println(list.get(0).getId());
			System.out.println(list.get(1).getId());
			System.out.println(list.get(2).getId());
			System.out.println(list.get(3).getId());
			System.out.println(list.get(4).getId());*/
			ParseXML parse = new ParseXML();
			int[] a = { list.get(1).getId(), list.get(0).getId(),
					list.get(2).getId(), list.get(3).getId(),
					list.get(4).getId() };
			parse.sortting(a);// 调用冒泡排序的从大到小的方法,对解析出来的id进行从大到小排序
			parse.sort(a);// 调用冒泡排序的从小到大的方法,对解析出来的id进行从小到大排序

			for (Student student : list) {
				System.out.println("id:" + student.getId() + "\tgroup:"
						+ student.getGroup() + "\tname:" + student.getName()
						+ "\tsex:" + student.getSex() + "\tage:"
						+ student.getAge() + "\temail:" + student.getEmail()
						+ "\tbirthday:" + student.getBirthday() + "\tmemo:"
						+ student.getMemo());

			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
