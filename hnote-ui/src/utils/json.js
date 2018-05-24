/** 
 * json格式转树状结构 
 * @param   {json}      json数据 
 * @param   {String}    id的字符串 
 * @param   {String}    父id的字符串 
 * @param   {String}    children的字符串 
 * @return  {Array}     数组 
 */  
export function toJsonTree(a, idStr, pidStr, childrenStr) {  
    var r = [], hash = {}, id = idStr, pid = pidStr, children = childrenStr, i = 0, j = 0, len = a.length;  
    for(; i < len; i++){  
        hash[a[i][id]] = a[i];  
    }  
    for(; j < len; j++){  
        var aVal = a[j], hashVP = hash[aVal[pid]];  
        if(hashVP){  
            !hashVP[children] && (hashVP[children] = []);  
            hashVP[children].push(aVal);  
        }else{  
            r.push(aVal);  
        }  
    }  
    return r;  
} 
