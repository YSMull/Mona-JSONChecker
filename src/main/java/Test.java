import chkr.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chkr.T.*;

public class Test {
    public static void main(String[] args) {
        List<String> msg = new ArrayList<>();
        Any.check("123", msg);
        Any.check(null, msg);
        Num.check(null, msg);
        Num.check("a", msg);
        Num.check(123, msg);
        Num.check("123", msg);
        Bool.check("true", msg);
        Bool.check("mm", msg);
        System.out.println(Arrays.toString(msg.toArray()));
    }
}
