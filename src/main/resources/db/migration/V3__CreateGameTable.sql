CREATE TABLE games (
    id UUID NOT NULL PRIMARY KEY,
    home UUID NOT NULL,
    away UUID NOT NULL,
    scheduled timestamp,
    week smallint,
    spHome real,
    spAway real,
    spHomeOdds smallint,
    spAwayOdds smallint,
    mlHome smallint,
    mlAway smallint,
    totalOver real,
    totalUnder real,
    OverOdds smallint,
    UnderOdds smallint,
    homeResult smallint,
    awayResult smallint
);