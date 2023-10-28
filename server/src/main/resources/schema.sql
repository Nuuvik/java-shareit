

CREATE TABLE IF NOT EXISTS users
(
    id    bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name  varchar(255)                                        NOT NULL,
    email varchar(512) UNIQUE                                 NOT NULL
);

CREATE TABLE IF NOT EXISTS requests
(
    id           bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description  varchar(512)                NOT NULL,
    requester_id bigint                      NOT NULL,
    created_date timestamp without time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS proposals
(
    id           bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    request_id   bigint NOT NULL,
    requester_id bigint NOT NULL,
    item_id      bigint NOT NULL

);

CREATE TABLE IF NOT EXISTS items
(
    id           bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         varchar(255) NOT NULL,
    description  varchar(255) NOT NULL,
    is_available boolean      NOT NULL,
    owner_id     bigint,
    request_id   bigint
);

CREATE TABLE IF NOT EXISTS bookings
(
    id         bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    start_date timestamp without time zone NOT NULL,
    end_date   timestamp without time zone NOT NULL,
    item_id    bigint,
    booker_id  bigint,
    status     varchar(12)
);

CREATE TABLE IF NOT EXISTS comments
(
    id           bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text         varchar(512)                NOT NULL,
    item_id      bigint,
    author_id    bigint,
    created_date timestamp without time zone NOT NULL
);

ALTER TABLE items
    DROP CONSTRAINT IF EXISTS fk_items_to_users;

ALTER TABLE items
    ADD CONSTRAINT fk_items_to_users FOREIGN KEY (owner_id) REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE bookings
    DROP CONSTRAINT IF EXISTS fk_bookings_to_items;

ALTER TABLE bookings
    ADD CONSTRAINT fk_bookings_to_items FOREIGN KEY (item_id) REFERENCES items (id)
        ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE bookings
    DROP CONSTRAINT IF EXISTS fk_bookings_to_users;

ALTER TABLE bookings
    ADD CONSTRAINT fk_bookings_to_users FOREIGN KEY (booker_id) REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE comments
    DROP CONSTRAINT IF EXISTS fk_comments_to_items;

ALTER TABLE comments
    ADD CONSTRAINT fk_comments_to_items FOREIGN KEY (item_id) REFERENCES items (id)
        ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE comments
    DROP CONSTRAINT IF EXISTS fk_comments_to_users;

ALTER TABLE comments
    ADD CONSTRAINT fk_comments_to_users FOREIGN KEY (author_id) REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE requests
    DROP CONSTRAINT IF EXISTS fk_request_to_users;

ALTER TABLE requests
    ADD CONSTRAINT fk_request_to_users FOREIGN KEY (requester_id) REFERENCES users (id)
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE proposals
    DROP CONSTRAINT IF EXISTS fk_item_proposal_to_request;

ALTER TABLE proposals
    ADD CONSTRAINT fk_item_proposal_to_request FOREIGN KEY (request_id) REFERENCES requests (id)
        ON DELETE CASCADE ON UPDATE CASCADE;