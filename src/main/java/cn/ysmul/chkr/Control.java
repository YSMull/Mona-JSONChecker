package cn.ysmul.chkr;

import cn.ysmul.chkr.core.Chkr;
import cn.ysmul.util.ChkrUtil;
import com.alibaba.fastjson.JSON;
import cn.ysmul.type.CheckedValue;

import java.util.*;

import static cn.ysmul.type.CheckedValue.*;

public class Control {

    public static Chkr Mixin(Chkr... chkrs) {
        return Chkr.compose(objMap -> {
            Map objectMap = (Map) objMap;
            Map<String, Object> filterMap = new HashMap<>();
            for (Chkr chkr : chkrs) {
                CheckedValue result = chkr.check(objectMap);
                if (isFail(result)) {
                    return result; // todo: fail
                } else {
                    filterMap.putAll((Map<String, Object>) result.getJustValue());
                }
            }
            return pure(filterMap);
        }, BasicType.Any);
    }

    public static Chkr Obj(Object... args) {
        Map<String, Chkr> chkrMap = ChkrUtil.parseChkrMap(args);
        if (chkrMap == null || chkrMap.size() == 0) return null;
        return Chkr.compose(value -> {
            if (!(value instanceof Map)) {
                return fail("{{ " + JSON.toJSONString(value) + " }} is not a KV");
            }
            Map valueMap = (Map) value;
            Map<String, Object> filterMap = new HashMap<>();
            Set<String> keys = chkrMap.keySet();
            for (String key : keys) {
                Chkr keyChkr = chkrMap.get(key);
                CheckedValue result = keyChkr.check(valueMap.get(key));
                if (isFail(result)) {
                    return result.addPath(key);
                } else {
                    filterMap.put(key, result.getJustValue());
                }
            }
            return CheckedValue.pure(filterMap);
        }, BasicType.Any);
    }

    // 必须是同种类的chkr
    public static Chkr Or(Chkr... chkrs) {
        return Chkr.compose(value -> {
            boolean matchOne = false;
            CheckedValue result = null;
            List<String> allSubErrMsgs = new ArrayList<>();
            for (Chkr chkr : chkrs) {
                // filter
                result = chkr.check(value);
                if (CheckedValue.isFail(result)) {
                    allSubErrMsgs.addAll(result.getPath());
                } else {
                    matchOne = true;
                    break;
                }
            }
            if (matchOne) {
                return result;
            } else {
                return fail(String.join("$$$and$$$", allSubErrMsgs));
            }
        }, BasicType.Any);
    }

    public static Chkr Arr(Chkr typeChkr) {
        return Chkr.compose(objList -> {
            if (!(objList instanceof List)) {
                return fail("{{ " + JSON.toJSONString(objList) + " }} is not an Array");
            }
            List objectList = (List) objList;
            for (int i = 0; i < objectList.size(); i++) {
                CheckedValue result = typeChkr.check(objectList.get(i));
                if (isFail(result)) {
                    return result.addPath("[" + i + "]");
                }
            }
            return pure(objList);
        }, Chkr.id);
    }

    public static Chkr Optional(Chkr typeChkr) {
        return Chkr.compose(object -> {
            if (object == null) return pure(null);
            return typeChkr.check(object);
        }, Chkr.id);
    }
}
