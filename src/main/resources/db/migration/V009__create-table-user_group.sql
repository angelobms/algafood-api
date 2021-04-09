CREATE TABLE user_group (
  user_id bigint NOT NULL,
  group_id bigint NOT NULL,
  PRIMARY KEY (user_id, group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE user_group ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES user_system (id);

ALTER TABLE user_group ADD CONSTRAINT fk_user_group_group FOREIGN KEY (group_id) REFERENCES group_system (id);
