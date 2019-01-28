package cn.ysmul.parser;

import cn.ysmul.chkr.core.Chkr;
import cn.ysmul.type.CheckedValue;
import cn.ysmul.util.FileUtil;
import com.alibaba.fastjson.JSON;

import java.util.Map;

import static cn.ysmul.util.ChkrUtil.parseError;
import static cn.ysmul.util.DefUtil.kv;
import static cn.ysmul.util.DefUtil.list;

public class TestMixin {
    public static void main(String[] args) {
        String str = FileUtil.readFile("/Users/ysmull/workstation/Java_Project/fpchkr/src/test/java/cn/resources/mixin.chkr");
        System.out.println(str);
        ChkrLexer lexer = new ChkrLexer(str);
        ChkrParser parser = new ChkrParser(lexer);
        Chkr o = parser.value(); // begin parsing at rule list


        Object before = kv(
            "reports", list(
                kv(
                    "id", 1,
                    "title", "1",
                    "relatedDataModelIds", list(1, 2, 3)
                ),
                kv(
                    "id", 1,
                    "title", "1",
                    "relatedDataModelIds", list(1, 2, 3)
                )
            ),
            "a", "123",
            "b", kv(
                "b1", "123a",
                "b2", "false"
            )
        );

        System.out.println();
        System.out.println(JSON.toJSONString(before, true));
        CheckedValue after = o.check(before);

        System.out.println(parseError(after.getPath()));

    }
}
