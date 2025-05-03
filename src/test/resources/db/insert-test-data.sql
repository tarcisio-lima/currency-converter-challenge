-- Script para inserir dois registros na tabela currency_history

INSERT INTO
    currency_history (id, user_id, source_currency, amount, target_currency, exchange_rate, registration_date)
VALUES
    (1, 1, 'USD', 100.50, 'BRL', 5.077, TIMESTAMP '2025-05-03 10:00:00'),
    (2, 1, 'EUR', 50.00, 'USD', 1.0864, TIMESTAMP '2025-05-02 15:30:00');