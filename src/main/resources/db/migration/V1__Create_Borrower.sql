CREATE TABLE borrower (
    borrower_id BIGINT AUTO_INCREMENT NOT NULL,
    entity_state varchar(15) NOT NULL DEFAULT 'Active' CHECK (entity_state IN ('Active', 'Disable')),
    created_date DATETIME NOT NULL,
    updated_date DATETIME NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    PRIMARY KEY (borrower_id)
)
