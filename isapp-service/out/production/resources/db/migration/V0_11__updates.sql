
ALTER TABLE "users" ADD FOREIGN KEY ("profile_photo") REFERENCES "files" ("file_id");
ALTER TABLE "users" ADD FOREIGN KEY ("faculty_id") REFERENCES "faculties" ("faculty_id");

ALTER TABLE "faculties" ADD FOREIGN KEY ("university_id") REFERENCES "universities" ("id");

ALTER TABLE "posts" ADD FOREIGN KEY ("owner") REFERENCES "users" ("user_id");

ALTER TABLE "posts" ADD FOREIGN KEY ("main_photo") REFERENCES "files" ("file_id");

ALTER TABLE "users_posts_junction" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");
ALTER TABLE "users_posts_junction" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("post_id");

ALTER TABLE "users_favorite_posts_junction" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");
ALTER TABLE "users_favorite_posts_junction" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("post_id");

--ALTER TABLE "files" ADD FOREIGN KEY ("id") REFERENCES "posts" ("post_id");

ALTER TABLE "faculties_posts_junction" ADD FOREIGN KEY ("faculty_id") REFERENCES "faculties" ("faculty_id");
ALTER TABLE "faculties_posts_junction" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("post_id");

ALTER TABLE "posts_files_junction" ADD FOREIGN KEY ("file_id") REFERENCES "files" ("file_id");
ALTER TABLE "posts_files_junction" ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("post_id");