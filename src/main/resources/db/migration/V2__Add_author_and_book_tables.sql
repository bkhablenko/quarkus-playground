CREATE TABLE IF NOT EXISTS "author" (
    "id"        uuid DEFAULT uuid_generate_v4(),
    "full_name" text NOT NULL,

    "created_at"  timestamp without time zone NOT NULL DEFAULT now(),
    "updated_at"  timestamp without time zone NOT NULL DEFAULT now(),

    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "book" (
    "id"        uuid DEFAULT uuid_generate_v4(),
    "title"     text NOT NULL,
    "author_id" uuid NOT NULL REFERENCES "author" ("id"),

    "created_at"  timestamp without time zone NOT NULL DEFAULT now(),
    "updated_at"  timestamp without time zone NOT NULL DEFAULT now(),

    PRIMARY KEY ("id")
);

CREATE INDEX "book_title_idx" ON "book" ("title");
