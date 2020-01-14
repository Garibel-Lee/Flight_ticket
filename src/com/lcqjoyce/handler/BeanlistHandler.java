
package com.lcqjoyce.handler;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//结果处理集
//单行封装成类  以类为属性返回list 信息返回结果

public class BeanlistHandler<T> implements IResultSetHandler<List<T>> {

	private Class<T> classType;// 把结果集中的一行数据，封装成什么类型的对象

	public BeanlistHandler(Class<T> classType) {
		this.classType = classType;
	}

	public List<T> handle(ResultSet rs) throws Exception {
		// 处理结果集三步操作
		List<T> list = new ArrayList<>();
		while (rs.next()) {
			// 1 创建对应类的一个对象
			T obj = classType.newInstance();
			list.add(obj);
			// 2 取出结果集当前光标所在行的某一列数据
			BeanInfo beanInfo = Introspector.getBeanInfo(classType, Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				String columnName = pd.getName();
				Object val = rs.getObject(columnName);

				// 3 调用该对象的setter方法，把某一列的数据设置进sql语句
				pd.getWriteMethod().invoke(obj, val);
			}
		}
		return list;
	}

}
