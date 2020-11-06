package com.sq;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

public class Test03 {
    String s = "abc {} {} das {}";
    @Test
    public void test1() {
        String dd = StrUtil.format(s, "a", "a", "a");
        System.out.println(dd);
    }
}
