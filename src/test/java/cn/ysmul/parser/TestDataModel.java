package cn.ysmul.parser;

import cn.ysmul.chkr.core.Chkr;
import cn.ysmul.type.CheckedValue;
import cn.ysmul.util.FileUtil;
import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

import static cn.ysmul.util.ChkrUtil.parseError;

public class TestDataModel {
    public static void main(String[] args) {
        String str = FileUtil.readFile("/Users/ysmull/workstation/Java_Project/fpchkr/src/test/java/cn/resources/dataModel.chkr");
        System.out.println(str);
        ChkrLexer lexer = new ChkrLexer(str);
        ChkrParser parser = new ChkrParser(lexer);
        Chkr o = parser.value(); // begin parsing at rule list


        Object before = Map.of(
            "reports", List.of(
                Map.of(
                    "id", 1,
                    "title", "title",
                    "relatedDataModelIds", List.of(1, 2, 3)
                )
            ),
            "dataModels", List.of(
                Map.of(
                    "status", "extract",
                    "relatedDataConnectionIds", List.of(1, 2, "a")
                )
            )
        );

        System.out.println("\n");
        System.out.println(JSON.toJSONString(before, true));
        CheckedValue after = o.check(before);

        System.out.println(parseError(after.getPath()));

    }
}
