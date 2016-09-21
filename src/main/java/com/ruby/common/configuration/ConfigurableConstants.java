package com.ruby.common.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 可用Properties文件配置的Constants基类.
 * 本类既保持了Constants的static和final(静态与不可修改)特性,又拥有了可用Properties文件配置的特征,
 * 主要是应用了Java语言中静态初始化代码的特性. <p/> 子类可如下编写
 *
 * <pre>
 * public class Constants extends ConfigurableConstants {
 *  static {
 *    init(&quot;springside.properties&quot;);
 * }
 * &lt;p/&gt;
 * public final static String ERROR_BUNDLE_KEY = getProperty(&quot;constant.error_bundle_key&quot;, &quot;errors&quot;); }
 * </pre>
 *
 * @author liudechao
 * @see
 */
public class ConfigurableConstants {

    protected static Logger logger = LoggerFactory.getLogger(ConfigurableConstants.class);
    protected static Properties p = new Properties();

    /**
     * 静态读入属性文件到Properties p变量中
     */
    protected static void init(String propertyFileName) {
        InputStream in = null;
        try {
            in = ConfigurableConstants.class.getClassLoader()
                    .getResourceAsStream(propertyFileName);
            if (in != null)
                p.load(in);

//            String sys = (String) p.get("sys");
//            File f = new File(sys);
//            InputStream ins = new FileInputStream(f);
//            p.load(ins);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close " + propertyFileName + " error!");
                }
            }
        }
    }

    /**
     * 封装了Properties类的getProperty函数,使p变量对子类透明.
     *
     * @param key
     *            property key.
     * @param defaultValue
     *            当使用property key在properties中取不到值时的默认值.
     */
    protected static String getProperty(String key, String defaultValue) {
        return p.getProperty(key, defaultValue);
    }
}
