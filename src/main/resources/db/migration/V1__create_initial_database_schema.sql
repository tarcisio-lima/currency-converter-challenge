CREATE TABLE currency_history (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      user_id BIGINT,
      source_currency VARCHAR(3),
      amount DECIMAL,
      target_currency VARCHAR(3),
      exchange_rate DOUBLE,
      registration_date TIMESTAMP
);