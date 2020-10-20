CREATE TABLE bets (
    id UUID NOT NULL,
    gameId UUID NOT NULL,
    betType smallint,
    betValue smallint,
    result smallint,
    totalWon smallint
);