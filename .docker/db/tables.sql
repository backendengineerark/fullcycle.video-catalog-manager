USE video_catalog_db;

CREATE TABLE categories(
    id BINARY(16) NOT NULL, 
    name VARCHAR(100) NOT NULL, 
    description VARCHAR(255), 
    is_active TINYINT(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;