package com.mobaopay.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ruby on 16/5/23.
 * Email:liyufeng_23@163.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = {"classpath*:/context/*-test.xml"} )
public class BaseSpringTestRunner {

}
