
package com.lcqjoyce.handler;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;


/*
 * new BeanHandler(User.class); 把一行数据封装成用户
 * new BeanHandler(Order.class); 把一行数据封装成订单  
 * 
 * 
 * 内省
1.什么是内省
	通过反射的方式操作JavaBean的属性，jdk提供了PropertyDescription类来操作访问JavaBean的属性，Beantils工具基于此来实现。
2.内省怎么用
1).操作一个属性
		Object obj = new Object();
		PropertyDescriptor pd = new PropertyDescriptor(propertyName,Class); //声明属性描述对象，一次只可描述一个属性
		
		Method m = pd.getWriterMethod();//获取setter方法
		m.invoke(obj,value);
		
		Method m = pd.getReaderMethod();//获取getter方法
		Object value = m.invoke(obj);

2).操作多个属性
		BeanInfo bi = Instospector.getBeanInfo(beanClass);//获取Bean描述对象
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();//获取属性描述对象数组
		拿到属性描述对象数组之后再循环数组，剩余的操作就跟"操作一个属性"相同了。
 */
//表示吧结果集中的一行数据，封装成一个对象专门针对结果集中只有一行数据的情况
public class BeanHandler<T> implements IResultSetHandler<T> {
	//把结果集中的一行数据，封装成什么类型的对象
	private Class<T> classType;
	//构造函数调用的时候决定了T的类型
	public BeanHandler(Class<T> classType) {
		this.classType=classType;
	}
	
	/*
	 * 规范
	 * 1.规定表的列名必须和对象的属性相同
	 * 2 规定表的列名类型必须和Java中的类型匹配 decimal-->BigDecimal /  bigint--> Long
	 */
	public T handle(ResultSet rs) throws Exception {
			//处理结果集三步操作
		 	//1 创建对应类的一个对象	
		T obj=classType.newInstance();
			//2 取出结果集当前光标所在行的某一列数据
		BeanInfo beanInfo=Introspector.getBeanInfo(classType,Object.class);
		
		PropertyDescriptor[] pds=beanInfo.getPropertyDescriptors(); 
		
		if(rs.next()) {
			
			for (PropertyDescriptor pd : pds) {
				String columnName=pd.getName();
				Object val= rs.getObject(columnName);
			//3 调用该对象的setter方法，把某一列的数据设置进sql语句
				pd.getWriteMethod().invoke(obj, val);
//				System.out.println(pd.getName());				
			}
		}
		
		return obj;
	}

}
