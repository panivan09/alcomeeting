
DROP TABLE IF EXISTS public.users_beverages;
DROP TABLE IF EXISTS public.meetings_users;
DROP TABLE IF EXISTS public.meetings_beverages;
DROP TABLE IF EXISTS public.roles_permissions;
DROP TABLE IF EXISTS public.users_roles;

DROP TABLE IF EXISTS public.permissions;
DROP TABLE IF EXISTS public.roles;
DROP TABLE IF EXISTS public.beverages;
DROP TABLE IF EXISTS public.meetings;
DROP TABLE IF EXISTS public.users;



------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.users
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50)NOT NULL UNIQUE,
    user_name VARCHAR(50)NOT NULL UNIQUE,
    password VARCHAR(255)NOT NULL,
    PRIMARY KEY (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.beverages
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.meetings
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    date timestamp without time zone NOT NULL,
    address VARCHAR(255) NOT NULL,
    name VARCHAR(100)NOT NULL,
    owner_user_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_user_id) REFERENCES public.users (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.roles
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.permissions
(
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.users_roles
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES public.users (id),
    FOREIGN KEY (role_id) REFERENCES public.roles (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.roles_permissions
(
    role_id INTEGER NOT NULL,
    permission_id INTEGER NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (permission_id) REFERENCES public.permissions (id) ,
    FOREIGN KEY (role_id) REFERENCES public.roles (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.users_beverages
(
    user_id INTEGER NOT NULL,
    beverage_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, beverage_id),
    FOREIGN KEY (beverage_id) REFERENCES public.beverages (id),
    FOREIGN KEY (user_id) REFERENCES public.users (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.meetings_users
(
    meeting_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (meeting_id, user_id),
    FOREIGN KEY (meeting_id) REFERENCES public.meetings (id),
    FOREIGN KEY (user_id) REFERENCES public.users (id)
);

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.meetings_beverages
(
    meeting_id INTEGER NOT NULL,
    beverage_id INTEGER NOT NULL,
    PRIMARY KEY (meeting_id, beverage_id),
    FOREIGN KEY (beverage_id) REFERENCES public.beverages (id),
    FOREIGN KEY (meeting_id) REFERENCES public.meetings (id)
);

------------------------------------------------------------------------------------------------------------------------



