package com.lcqjoyce.handler;
import java.sql.ResultSet;
/**
 * 
 * @author 13059
 *	通用结果处理集
 *	使用这个处理接口
 *
 * @param <T>
 */
public interface IResultSetHandler<T> {
	//处理结果集
	T handle(ResultSet rs) throws Exception;
}
