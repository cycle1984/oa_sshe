package cycle.oa_sshe.utils;

public class MyUtils {

	//字符串转换成整型数组
	public static Integer[] string2Integer(String ids){
		if(ids!=null&&ids!=""){
			String[] s = ids.split(",");
			Integer[] aa = new Integer[s.length];
			int index = 0;
			for(String string:s){
				aa[index] = Integer.valueOf(string);
				index++;
			}
			return aa;
		}else{
			return null;
		}
		
	}
}
