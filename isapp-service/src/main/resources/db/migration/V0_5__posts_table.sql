CREATE TABLE "posts" (
  "post_id" UUID PRIMARY KEY,
  "owner" UUID,
  "main_photo" UUID,
  "lat" decimal,
  "lng" decimal,
  "description" varchar,
  "price" decimal,
   "title" varchar

);