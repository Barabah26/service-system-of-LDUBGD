DROP TABLE IF EXISTS userprofile CASCADE;

CREATE TABLE userprofile (
                             id SERIAL PRIMARY KEY,
                             faculty VARCHAR(255) NOT NULL,
                             specialty VARCHAR(255) NOT NULL,
                             degree VARCHAR(255) NOT NULL,
                             group_name VARCHAR(255) NOT NULL
);
