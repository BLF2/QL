package net.blf2.util;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by t_mengxh on 2016/7/24.
 */
@Component
public class GlobalStaticTools {
//    private static Logger logger = Logger.getLogger(GlobalStaticTools.class);
//    private static String CHARACTER_MAP_TABLE = "CharaterMapTable.txt";
//    private static Map<String,String>characterMap = null;
//    static {
//       loadCharacterMapTable(CHARACTER_MAP_TABLE);
//    }
//    public static String dateToChinaStyleDate(Date date){
//        try {
//            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//        }catch (Exception e){
//            logger.error("can't format from date and date is" + date.toString());
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public static String fiterSpecialCharacter(String inputStr){
//        if(characterMap != null && !characterMap.isEmpty()){
//            for(String str : characterMap.keySet()){
//                inputStr = inputStr.replace(str,characterMap.get(str));
//            }
//        }
//        return inputStr;
//    }
//    public static void loadCharacterMapTable(String fileName){
//        URL url = null;
//        try {
//            url = getResource(fileName);
//        } catch (NullPointerException e) {
//            logger.error("can't find the file of " + fileName);
//            e.printStackTrace();
//        }
//        if(url != null) {
//            System.out.println("read url success!");
//            characterMap = new HashMap<String, String>();
//            String line = null;
//            BufferedReader br = null;
//            try {
//                br = new BufferedReader(new InputStreamReader(new FileInputStream(url.getFile())));
//            } catch (FileNotFoundException e) {
//                logger.error("IO error in reading " + fileName);
//                e.printStackTrace();
//            }
//            do {
//                try {
//                    line = br.readLine();
//                } catch (IOException e) {
//                    logger.error("IO error in reading " + fileName);
//                    e.printStackTrace();
//                }
//                if(line != null) {
//                    String[] str = line.split(" ");
//                    characterMap.put(str[0],str[1]);
//                }
//            }while(line != null);
//        }else{
//            logger.error("InputStream is null and fileName = " + fileName);
//        }
//        System.out.println(new Gson().toJson(characterMap));
//    }
    public static Boolean checkStringIsNullOrIsEmpty(String checkedString){
        if(checkedString == null || checkedString.isEmpty())
            return Boolean.TRUE;
        return Boolean.FALSE;
    }
    /*
        权限设定：
        本程序设置31个权限，权限描述由又往左，表现形式为一个int类型的整数
        31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
        从右往左开始，权限分别是
        1 状态码  0为不可用状态  1为可用状态
        2 添加用户信息权限
        3 修改他人用户信息权限
        4 删除他人用户信息权限
        5 查询他人用户信息权限
        6 查看所有维修表权限
        7 添加维修表权限
        8 修改他人维修表权限
        9 删除他人维修表权限
        10查看维修跟踪表权限
        11增加维修跟踪表权限
        12修改所有维修跟踪表权限
        13删除他人维修跟踪表权限
        14查询分类权限
        15增加分类权限
        16修改分类权限
        17删除分类权限
        18教师权限


        学生：7,10,14
        教师:18,7,10,14
        维修人员6,7，10 , 11，14
        超级管理员
     */
}
