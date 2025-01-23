CREATE KEYSPACE IF NOT EXISTS xmaswishes
WITH replication = {
  'class': 'SimpleStrategy',
  'replication_factor': 1
};

USE xmaswishes;

CREATE TABLE IF NOT EXISTS wishlists (
  wishlist_id UUID PRIMARY KEY,
  name TEXT
);

CREATE TABLE IF NOT EXISTS wishes (
  wish_id UUID PRIMARY KEY,
  wishlist_id UUID,
  wish TEXT,
  status TEXT
);
