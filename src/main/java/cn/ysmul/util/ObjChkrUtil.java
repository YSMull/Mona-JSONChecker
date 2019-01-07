package cn.ysmul.util;

import cn.ysmul.chkr.core.Chkr;

import java.util.*;

public class ObjChkrUtil {

    public static void checkArgNum(Object[] args) {
        if (args == null || args.length == 0 || args.length % 2 == 1) {
            throw new RuntimeException("params map array size wrong");
        }
    }

    public static Map<String, Chkr> parseChkrMap(Object[] args) {
        checkArgNum(args);
        // LinkedHashMap 可以保持插入顺序
        Map<String, Chkr> m = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            if (!(args[i + 1] instanceof Chkr)) throw new RuntimeException(args[i] + " is not Chkr!");
            m.put((String) args[i], (Chkr) args[i + 1]);
        }
        return m;
    }

    public static String parseError(List<String> path) {
        if (path.size() == 1) {
            String e = path.get(0);
            List<String> p = Arrays.asList(e.split("\\$\\$\\$and\\$\\$\\$"));
            return parseError(p);
        } else {
            List<List<String>> errors = new ArrayList<>();
            List<String> error = new ArrayList<>();
            for (int i = path.size() - 1; i >= 0; i--) {
                if (path.get(i).startsWith("{{")) {
                    error.add(" = " + path.get(i));
                    errors.add(error);
                    error = new ArrayList<>();
                } else {
                    error.add(path.get(i));
                }
            }
            Collections.reverse(errors);

            StringBuilder result = new StringBuilder();
            for (List<String> e : errors) {
                result.append(String.join(".", e).replaceAll("\\.\\[", "[").replaceAll("\\. =", " =").replaceAll("\\$\\$\\$and\\$\\$\\$", " and ")).append("\n");
            }
            return result.toString();
        }
    }

    public static <A> List<A> single(A item) {
        List<A> list = new ArrayList<>();
        list.add(item);
        return list;
    }
}
