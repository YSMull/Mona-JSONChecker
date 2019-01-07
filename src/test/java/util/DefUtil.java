package util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static util.ObjChkrUtil.checkArgNum;

public class DefUtil {
    public static Map<String, Object> kv(Object ...args) {
        checkArgNum(args);
        Map<String, Object> m = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            m.put((String) args[i], args[i + 1]);
        }
        return m;
    }

    public  static List list(Object ...args) {
        return List.of(args);
    }
}
