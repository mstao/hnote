package me.mingshan.service.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:58 2018/4/30
 */
public class StringUtil {

    /**
     * String to list.
     * @param ids
     * @return
     */
    public static List<Long> stringToList(String ids) {
        // 将字符串转为字符串数组
        String[] idArray = ids.split(",");
        // 将字符串数组转为整形数组
        Long[] iid = new Long[idArray.length];
        for(int i = 0;i < iid.length; i++){
            iid[i] = Long.parseLong(idArray[i]);
        }
        // 将数组转为list
        List<Long> idList = new ArrayList<Long>();
        Collections.addAll(idList, iid);
        return idList;
    }
}
