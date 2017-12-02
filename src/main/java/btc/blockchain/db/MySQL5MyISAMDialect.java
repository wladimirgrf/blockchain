package btc.blockchain.db;

import org.hibernate.dialect.MySQL5Dialect;

public class MySQL5MyISAMDialect extends MySQL5Dialect {

    public String getTableTypeString() {
        return " ENGINE=MyISAM DEFAULT CHARSET=utf8";
    }
}