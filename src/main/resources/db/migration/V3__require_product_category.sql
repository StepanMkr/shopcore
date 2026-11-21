WITH fallback_category AS (
INSERT
INTO categories (name)
SELECT 'Без категории' WHERE EXISTS (SELECT 1 FROM products WHERE category_id IS NULL)
    RETURNING id
)
UPDATE products
SET category_id = (SELECT id FROM fallback_category)
WHERE category_id IS NULL;

ALTER TABLE products
    ALTER COLUMN category_id SET NOT NULL;
