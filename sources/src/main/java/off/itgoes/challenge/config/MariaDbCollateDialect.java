package off.itgoes.challenge.config;

import org.hibernate.dialect.MariaDBDialect;

public class MariaDbCollateDialect extends MariaDBDialect {
    
	@Override
    public String getTableTypeString() {
        return " COLLATE utf8mb4_bin";
	}
}