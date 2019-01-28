package cn.ysmul.util;

import cn.ysmul.chkr.core.Chkr;

import java.util.*;
import java.util.function.Predicate;

public class ChkrUtil {

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

    /*
    todo: 需要有分裂逻辑在里面，每一个item，只要有$$$and$$$就应该分裂，并且复用之前的部分
     */
    public static String parseError(List<String> path) {
        Collections.reverse(path);
        List<List<String>> errors = new ArrayList<>();
        List<String> error = new ArrayList<>();
        for (var p : path) {
            if (p.startsWith("{{")) {
                if (p.contains("$$$and$$$")) {
                    var result = parseError(Arrays.asList(p.split("\\$\\$\\$and\\$\\$\\$")));
                    var partR = reverse(result.split("\n"));
                    String result1 = "";
                    for (String part: partR) {
                        result1 += String.join(".", tail(path)) + "." + part + "\n";
                    }
                    return result1.replaceAll("\\.\\[", "[").replaceAll("\\. =", " =").replaceAll("^\\.", "").replaceAll("\\n\\.", "\n");
                } else {
                    error.add(" = " + p);
                    errors.add(error);
                    error = new ArrayList<>();
                }
            } else {
                error.add(p);
            }
        }
//        Collections.reverse(errors);

        List<String> result = new ArrayList<>();

        for (List<String> e : errors) {
            result.add(String.join(".", e).replaceAll("\\.\\[", "[").replaceAll("\\. =", " =").replaceAll("\\$\\$\\$and\\$\\$\\$", " and "));
        }
        return String.join("\n",result);

    }

    public static <A> boolean all(List<A> list, Predicate<A> p) {
        for (A item: list) {
            if (!p.test(item)) {
                return false;
            }
        }
        return true;
    }

    public static <A> List<A> tail(List<A> list) {
        var newList = new ArrayList<A>();
        for (int i = 0; i < list.size()-1; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public static String[] reverse(String[] arr) {
        List<String> list = Arrays.asList(arr);
        Collections.reverse(list);
        return list.toArray(new String[0]);
    }

    public static <A> List<A> single(A item) {
        List<A> list = new ArrayList<>();
        list.add(item);
        return list;
    }
}
