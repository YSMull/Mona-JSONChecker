package cn.ysmul.parser;

import cn.ysmul.chkr.core.Chkr;
import cn.ysmul.type.CheckedValue;
import cn.ysmul.util.FileUtil;
import com.alibaba.fastjson.JSON;

import java.util.Map;

import static cn.ysmul.util.ChkrUtil.parseError;

public class Test {
    public static void main(String[] args) {
        String str = FileUtil.readFile("/Users/ysmull/workstation/Java_Project/fpchkr/src/test/java/cn/resources/obj1.chkr");
        System.out.println(str);
        ChkrLexer lexer = new ChkrLexer(str);
        ChkrParser parser = new ChkrParser(lexer);
        Chkr o = parser.value(); // begin parsing at rule list


        Object before = Map.of(
            "userId", 123,
            "userName", "haha",
            "phone", "aa",
            "address", Map.of(
                "id", 123,
                "person", Map.of(
                    "id", "1",
                    "age", 123,
                    "t", "a"
                )
            )
        );

        System.out.println();
        System.out.println(JSON.toJSONString(before, true));
        CheckedValue after = o.check(before);

        System.out.println(parseError(after.getPath()));

    }
}
