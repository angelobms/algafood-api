ALTER TABLE restaurant ADD active TINYINT(1) NOT NULL DEFAULT 1;
UPDATE restaurant SET active = TRUE;
