CREATE TABLE IF NOT EXISTS users (
    user_id UUID PRIMARY KEY,
    name TEXT,
    password TEXT,
    role TEXT ,
    email TEXT NOT NULL,
    phone TEXT,
    profile_photo UUID ,
    faculty_id UUID,
    description TEXT
);