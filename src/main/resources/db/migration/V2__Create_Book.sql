CREATE TABLE book (
    book_id BIGINT AUTO_INCREMENT NOT NULL,
    entity_state varchar(15) NOT NULL DEFAULT 'Active' CHECK (entity_state IN ('Active', 'Disable')),
    created_date DATETIME NOT NULL,
    updated_date DATETIME NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    isbn varchar(50) NOT NULL,
    title varchar(100) NOT NULL,
    author varchar(255) NOT NULL,
    borrower_id BIGINT,
    PRIMARY KEY (book_id),
    FOREIGN KEY (borrower_id) REFERENCES borrower(borrower_id)
)