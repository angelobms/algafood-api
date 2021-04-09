CREATE TABLE group_permission (
  group_id bigint NOT NULL,
  permission_id bigint NOT NULL,
  PRIMARY KEY (group_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE group_permission ADD CONSTRAINT fk_group_permission_group FOREIGN KEY (group_id) REFERENCES group_system (id);

ALTER TABLE group_permission ADD CONSTRAINT fk_group_permission_permission FOREIGN KEY (permission_id) REFERENCES permission (id);

