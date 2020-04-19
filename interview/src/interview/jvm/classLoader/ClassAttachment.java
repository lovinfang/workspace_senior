package interview.jvm.classLoader;

import java.util.Date;
//父类对象 提升通用域 所以这里Sam老师继承了这个Date
public class ClassAttachment extends Date {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString() {
		return "欢迎大家来到动脑学院！";
	}

}
