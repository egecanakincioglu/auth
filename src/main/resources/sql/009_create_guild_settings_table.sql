CREATE TABLE IF NOT EXISTS guild_settings (
    guild_id INTEGER NOT NULL,
    setting_key TEXT NOT NULL,
    setting_value TEXT NOT NULL,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (guild_id) REFERENCES guilds(id),
    PRIMARY KEY (guild_id, setting_key)
);