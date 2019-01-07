package util;

import chkr.Chkr;

import java.util.LinkedHashMap;
import java.util.Map;

public class Util {
    public static Map<String, Chkr> parseChkrMap(Object[] args) throws Exception {
        if (args == null || args.length == 0) {
            return null;
        }
        if (args.length % 2 == 1) {
            throw new Exception("params map array size wrong");
        }
        // LinkedHashMap 可以保持插入顺序
        Map<String, Chkr> m = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            if (!(args[i + 1] instanceof Chkr)) throw new Exception(args[i] + " is not Chkr!");
            m.put((String) args[i], (Chkr) args[i + 1]);
        }
        return m;
    }
}
