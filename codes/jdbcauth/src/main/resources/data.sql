INSERT INTO users (username, password, enabled)
  values ('banu', '$2a$12$FmPqlajbdjQxnL/rjOUfb.ZXsGLrJRHeF/wOFRmSXQhSfCS1SNOIa', 1);

INSERT INTO users (username, password, enabled)
  values ('admin', '$2a$12$FmPqlajbdjQxnL/rjOUfb.ZXsGLrJRHeF/wOFRmSXQhSfCS1SNOIa', 1);
    
INSERT INTO authorities (username, authority)
  values ('banu', 'ROLE_USER');
  
INSERT INTO authorities (username, authority)
  values ('admin', 'ROLE_ADMIN');
  
INSERT INTO authorities (username, authority)
  values ('admin', 'ROLE_USER');
  
  