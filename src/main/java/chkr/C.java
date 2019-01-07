package chkr;

import com.alibaba.fastjson.JSON;
import type.Maybe;
import util.Util;

import java.util.*;

public class C {

//    public static Chkr Mixin(Chkr... chkrs) {
//        return Chkr.match((objMap, errMsgs) -> {
//            Map objectMap = (Map) objMap;
//            Map<String, Object> filterMap = new HashMap<>();
//            for (Chkr chkr : chkrs) {
//                List<String> subErr = new ArrayList<>();
//                type.Maybe resultMap = chkr.check(objectMap, subErr);
//                if (resultMap != type.Maybe.NOTHING) {
//                    try {
//                        filterMap.putAll(Map<String, Object>) type.Maybe.fromJust(resultMap));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return filterMap;
//        }, Chkr.Any);
//    }

    public static Chkr Or(Chkr... chkrs) {
        return Chkr.match((value, errMsgs) -> {
            boolean matchOne = false;
            Maybe result = null;
            List<String> allSubErrMsgs = new ArrayList<>();
            for (Chkr chkr : chkrs) {
                // filter
                List<String> subMsgs = new ArrayList<>();
                result = chkr.check(value, subMsgs);
                if (result == Maybe.NOTHING) {
                    allSubErrMsgs.addAll(subMsgs);
                } else {
                    matchOne = true;
                    break;
                }
            }
            if (matchOne) {
                return result;
            } else {
                errMsgs.add(String.join(" and ", allSubErrMsgs));
                return Maybe.NOTHING;
            }
        }, T.Any);
    }

    public static Chkr Obj(Object... args) throws Exception {
        Map<String, Chkr> chkrMap = Util.parseChkrMap(args);
        if (chkrMap == null || chkrMap.size() == 0) return null;
        return Chkr.match((objMap, errMsgs) -> {
            if (!(objMap instanceof Map)) {
                errMsgs.add("{{ " + JSON.toJSONString(objMap) + " }} is not an kv");
                return Maybe.NOTHING;
            }
            Map castMap = (Map) objMap;
            Map<String, Object> filterMap = new HashMap<>();
            Set<String> keys = chkrMap.keySet();
            for (String key : keys) {
                Chkr keyChkr = chkrMap.get(key);
                errMsgs.add(key);
                int idx1 = errMsgs.size();
                Maybe result = keyChkr.check(castMap.get(key), errMsgs);
                int idx2 = errMsgs.size();
                if (result == Maybe.NOTHING) {
                    return Maybe.NOTHING;
                } else {
                    if (idx2 == idx1) {
                        errMsgs.remove(errMsgs.size()-1);
                    }
                    filterMap.put(key, Maybe.fromJust(result));
                }
            }
            return Maybe.just(filterMap);
        }, T.Any);
    }

//    public static Chkr Arr(Chkr typeChkr) {
//        String chkrName = "Arr";
//        return T.Any.match(objList -> {
//            if (!(objList instanceof List)) {
//                throw new Exception("{{ " + JSON.toJSONString(objList) + " }} is not an Array");
//            }
//            List objectList = (List) objList;
//            for (int i = 0; i < objectList.size(); i++) {
//                typeChkr.parentChkrName(chkrName).currentIndex(i).check(objectList.get(i));
//            }
//            return objList;
//        }).called(chkrName);
//    }

//    public static Chkr Optional(Chkr typeChkr) {
//        return new Chkr().match(object -> {
//            if (object == null) return null;
//            return typeChkr.parentChkrName("Optional").check(object);
//        });
//    }

//    public static Chkr OrVal(Object... args) {
//        return new Chkr().match(value -> {
//            boolean exist = Arrays.asList(args).contains(value);
//            String argStr = Arrays.stream(args).map(JSON::toJSONString).collect(Collectors.joining(","));
//            if (!exist) throw new Exception("{{ " + JSON.toJSONString(value) + " }} dose not match OrVal(" + argStr + ")");
//            return value;
//        });
//    }
}
