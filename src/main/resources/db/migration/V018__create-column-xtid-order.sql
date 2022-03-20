ALTER TABLE order_restaurant ADD xtid VARCHAR(36) NOT NULL AFTER id;
UPDATE order_restaurant SET xtid = UUID();
ALTER TABLE order_restaurant ADD CONSTRAINT uk_order_xtid UNIQUE(xtid);