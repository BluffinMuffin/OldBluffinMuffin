create type BettingRoundType as ENUM ('Blind', 'Pre-Flop', 'Pre-Turn', 'Pre-River', 'Pre-Showdown');

-- TODO: Add more types
create type BetType as ENUM ('No limit');

-- TODO: Add more types
create type GameType as ENUM ('Sit and Go');

create type WinType as ENUM ('High Card', 'Pair', 'Two Pair', 'Three of a Kind', 'Straight', 'Flush', 'Full House', 'Four of a Kind', 'Straight Flush');


-- drop table Player
create table Player(
	idPlayer SERIAL PRIMARY KEY,
	playerName VARCHAR(100) NOT NULL,
	idDomain INTEGER REFERENCES DOMAIN(idDomain)
);

-- drop table Domain
create table Domain(
	idDomain SERIAL PRIMARY KEY,
	domainName VARCHAR (100) NOT NULL,
	domainURL VARCHAR (200)
);

insert into Domain (domainName, domainURL) values ('Party Poker', 'www.partypoker.com');
insert into Domain (domainName, domainURL) values ('Full Tilt', 'www.fulltilt.com');
insert into Domain (domainName, domainURL) values ('Poker Stars', 'www.pokerstars.com');
insert into Domain (domainName, domainURL) values ('HOCUS', null);


-- drop table GameSet
create table GameSet(
	idGameSet SERIAL PRIMARY KEY,
	nbPlayers INTEGER NOT NULL,
	BBValue NUMERIC,
	SBValue NUMERIC,
	betType BetType,
	gameType GameType,
	source VARCHAR(100) -- TODO: Determine the adequate length for the source path
);

-- drop table Game
create table Game(
	-- idGame SERIAL PRIMARY KEY, -- TODO: no need, do a ++seq ?
	idGame INTEGER, 
	idGameSet INTEGER references GameSet(idGameSet),
	startTime TIMESTAMP, 
	idDealer INTEGER references Player(idPlayer),
	idSB INTEGER references Player(idPlayer),
	idBB INTEGER references Player(idPlayer),
	flop1 CHAR(2), -- TODO: Make a seperate table? No known advantages 1 ro 1 link
	flop2 CHAR(2),
	flop3 CHAR(2),
	turn CHAR(2),
	river CHAR(2),
	PRIMARY KEY (idGame, idGameSet)
);

-- drop table DealtCards
-- Removes the necessity of a perspective and integrates the possibility for multiple perspectives for a given game
create table DealtCards (
	idGame INTEGER references Game(idGame),
	idGameSet INTEGER references GameSet(idGameSet),
	idPlayer INTEGER references Player(idPlayer),
	pocket1 CHAR(2),
	pocket2 CHAR(2)
);

-- drop table BettingRound
-- An amountRaised of 0$ results in either a call or a fold, hence the hasFolded flag
create table BettingRound(
	idGame INTEGER references Game(idGame),
	idGameSet INTEGER references GameSet(idGameSet),
	idPlayer INTEGER references Player(idPlayer),
	round BettingRoundType,
	seq INTEGER, -- equence of events within a given betting round type
	chipsLeft NUMERIC,
	amoundRaised NUMERIC, -- TODO: Can i only raise in integers?
	hasFolded BOOLEAN -- to avoid going into next round to see if a player has folded in this round
	
);

-- drop table Showdown
-- TODO: Find a better name
create table Showdown( -- Winner / Summary
	idGame INTEGER references Game(idGame),
	idGameSet INTEGER references GameSet(idGameSet),
	idPlayer INTEGER references Player(idPlayer),
	winningHand WinType, -- TODO: Is the hand necessarily a winning one?
	potAmountWon NUMERIC
);