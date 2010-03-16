create type BettingRoundType as ENUM ('PreFlop', 'Flop', 'Turn', 'River');
create type ActionType as ENUM ('Fold', 'Check', 'Call', 'Raise');

-- TODO: Add more types
create type BetType as ENUM ('NoLimit', 'PotLimit', 'CapNoLimit', 'FixedLimit');

-- TODO: Add more types
create type GameType as ENUM ('Ring', 'Tournament');

create type TournamentType as ENUM ('Sit and Go', 'Single Table', 'MultiTable', 'Deep Stacks');

create type ForcedBetType as ENUM ('SmallBlind', 'BigBlind', 'Post', 'Ante');

create type PokerType as ENUM ('Holdem', 'OmahaHI', 'OmahaNL');

create type WinType as ENUM ('High Card', 'Pair', 'Two Pair', 'Three of a Kind', 'Straight', 'Flush', 'Full House', 'Four of a Kind', 'Straight Flush');


-- drop table Player
create table Player(
	idPlayer SERIAL PRIMARY KEY,
	playerName VARCHAR(100) NOT NULL,
	idDomain INTEGER
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
	nbPlayers INTEGER,
	BBValue NUMERIC,
	SBValue NUMERIC,
	betType BetType,
	gameType GameType,
	pokerType PokerType,
	tournamentType TournamentType,--null if not a tournament
	source VARCHAR(300), -- TODO: Determine the adequate length for the source path
	realMoney BOOLEAN DEFAULT false
);

-- drop table Game
create table Game(
	idGame BIGSERIAL PRIMARY KEY,
	idGameSet INTEGER references GameSet(idGameSet),
	startTime TIMESTAMP, 
	idDealer INTEGER references Player(idPlayer),
	flop1 CHAR(2), -- TODO: Make a seperate table? Can get gameID before knowing all cards on table
	flop2 CHAR(2),
	flop3 CHAR(2),
	turn CHAR(2),
	river CHAR(2)
);

-- drop table DealtCards
-- Removes the necessity of a perspective and integrates the possibility for multiple perspectives for a given game
create table DealtCards (
	idGame INTEGER references Game(idGame),
	idPlayer INTEGER references Player(idPlayer),
	pocket1 CHAR(2),
	pocket2 CHAR(2),
	PRIMARY KEY (idGame, idPlayer)
);

-- drop table BettingRound
-- An amountRaised of 0$ results in either a call or a fold, hence the hasFolded flag
create table BettingRound(
	idGame INTEGER references Game(idGame),
	idPlayer INTEGER references Player(idPlayer),
	round BettingRoundType,
	seq INTEGER, -- sequence of events within a given betting round type
	action ACTIONTYPE,
	amount NUMERIC,
  PRIMARY KEY (idGame, idPlayer)
);

create table ForcedBets(
	idGame INTEGER references Game(idGame),
	idPlayer INTEGER references Player(idPlayer),
	forcedBetType ForcedBetType,
	seq INTEGER, -- TODO; needs to follow the betting round's sequence
	amount NUMERIC,
  PRIMARY KEY (idGame, idPlayer)
);

create table Seats(
	idGame INTEGER references Game(idGame),
	idPlayer INTEGER references Player(idPlayer),
	seatNo INTEGER,
	sittingIn BOOLEAN,
	chips NUMERIC,
  PRIMARY KEY (idGame, idPlayer)
);

-- drop table Showdown
-- TODO: Find a better name
create table Showdown( -- Winner / Summary
	idGame INTEGER references Game(idGame),
	idPlayer INTEGER references Player(idPlayer),
	winningHand WinType, -- TODO: Is the hand necessarily a winning one?
	potAmountWon NUMERIC,
  PRIMARY KEY (idGame, idPlayer)
);
