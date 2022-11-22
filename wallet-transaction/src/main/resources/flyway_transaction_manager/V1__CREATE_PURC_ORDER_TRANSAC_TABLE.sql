CREATE TABLE PURCHASE_ORDER_TRANSACTION(
    ID              UUID                     PRIMARY KEY,
    PORTFOLIO_ID    UUID                     NOT NULL,
    COIN_SYMBOL     VARCHAR(255)             NOT NULL,
    QUANTITY        DOUBLE PRECISION         NOT NULL,
    FEE             DOUBLE PRECISION         NOT NULL,
    COIN_ATTRIBUTES JSONB                    NOT NULL,
    ORDER_DATE      TIMESTAMP with time zone NOT NULL
)