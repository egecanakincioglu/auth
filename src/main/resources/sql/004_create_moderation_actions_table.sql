CREATE TABLE IF NOT EXISTS moderation_actions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    action_type TEXT NOT NULL,
    target_user_id INTEGER NOT NULL,
    moderator_user_id INTEGER NOT NULL,
    reason TEXT,
    action_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (target_user_id) REFERENCES users(id),
    FOREIGN KEY (moderator_user_id) REFERENCES users(id)
);