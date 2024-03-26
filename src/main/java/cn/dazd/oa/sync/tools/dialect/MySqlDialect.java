package cn.dazd.oa.sync.tools.dialect;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.MySQLDialect;

@Slf4j
public class MySqlDialect extends MySQLDialect {
    public MySqlDialect() {
        super();
        log.info("{}{}", "MySQLDialect 使用本方言。", super.getTableTypeString());
    }

    /**
     * 此处是解决创建表报错问题
     */
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8m64";
    }
}
