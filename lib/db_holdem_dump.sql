--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: cash_hand_player_combinations; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cash_hand_player_combinations (
    id_hand integer,
    id_player integer,
    flg_f_highcard boolean,
    flg_f_1pair boolean,
    flg_f_2pair boolean,
    flg_f_threeoak boolean,
    flg_f_straight boolean,
    flg_f_flush boolean,
    flg_f_fullhouse boolean,
    flg_f_strflush boolean,
    id_f_hand_strength smallint,
    id_f_kicker_strength smallint,
    val_f_hole_cards_used smallint,
    flg_t_highcard boolean,
    flg_t_1pair boolean,
    flg_t_2pair boolean,
    flg_t_threeoak boolean,
    flg_t_straight boolean,
    flg_t_flush boolean,
    flg_t_fullhouse boolean,
    flg_t_fouroak boolean,
    flg_t_strflush boolean,
    id_t_hand_strength smallint,
    id_t_kicker_strength smallint,
    val_t_hole_cards_used smallint,
    flg_r_highcard boolean,
    flg_r_1pair boolean,
    flg_r_2pair boolean,
    flg_r_threeoak boolean,
    flg_r_straight boolean,
    flg_r_flush boolean,
    flg_r_fullhouse boolean,
    flg_r_fouroak boolean,
    flg_r_strflush boolean,
    id_r_hand_strength smallint,
    id_r_kicker_strength smallint,
    val_r_hole_cards_used smallint
);


ALTER TABLE public.cash_hand_player_combinations OWNER TO postgres;

--
-- Name: cash_hand_player_statistics; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cash_hand_player_statistics (
    id_hand integer,
    id_player integer,
    id_holecard integer,
    id_limit integer,
    id_session integer,
    date_played timestamp without time zone,
    "position" smallint,
    cnt_p_raise smallint,
    flg_p_first_raise boolean,
    cnt_p_call smallint,
    flg_p_limp boolean,
    flg_p_fold boolean,
    flg_p_ccall boolean,
    cnt_p_face_limpers smallint,
    flg_vpip boolean,
    flg_f_bet boolean,
    cnt_f_raise smallint,
    flg_f_first_raise boolean,
    cnt_f_call smallint,
    flg_f_check boolean,
    flg_f_check_raise boolean,
    flg_f_fold boolean,
    flg_f_saw boolean,
    flg_t_bet boolean,
    cnt_t_raise smallint,
    flg_t_first_raise boolean,
    cnt_t_call smallint,
    flg_t_check boolean,
    flg_t_check_raise boolean,
    flg_t_fold boolean,
    flg_t_saw boolean,
    flg_r_bet boolean,
    cnt_r_raise smallint,
    flg_r_first_raise boolean,
    cnt_r_call smallint,
    flg_r_check boolean,
    flg_r_check_raise boolean,
    flg_r_fold boolean,
    flg_r_saw boolean,
    enum_allin character varying(1),
    enum_face_allin character varying(1),
    enum_face_allin_action character varying(1),
    flg_blind_s boolean,
    flg_blind_b boolean,
    flg_blind_ds boolean,
    flg_blind_db boolean,
    flg_sb_steal_fold boolean,
    flg_bb_steal_fold boolean,
    flg_blind_def_opp boolean,
    flg_steal_att boolean,
    flg_steal_opp boolean,
    flg_blind_k boolean,
    flg_showdown boolean,
    flg_won_hand boolean,
    amt_won real,
    amt_expected_won real,
    val_equity real,
    id_final_hand smallint,
    flg_showed boolean,
    enum_folded character varying(1),
    flg_p_face_raise boolean,
    flg_p_3bet boolean,
    flg_p_3bet_opp boolean,
    flg_p_3bet_def_opp boolean,
    enum_p_3bet_action character varying(1),
    flg_p_4bet boolean,
    flg_p_4bet_opp boolean,
    flg_p_4bet_def_opp boolean,
    enum_p_4bet_action character varying(1),
    flg_p_squeeze boolean,
    flg_p_squeeze_opp boolean,
    flg_p_squeeze_def_opp boolean,
    enum_p_squeeze_action character varying(1),
    flg_f_face_raise boolean,
    flg_f_3bet boolean,
    flg_f_3bet_opp boolean,
    flg_f_3bet_def_opp boolean,
    enum_f_3bet_action character varying(1),
    flg_f_4bet boolean,
    flg_f_4bet_opp boolean,
    flg_f_4bet_def_opp boolean,
    enum_f_4bet_action character varying(1),
    flg_f_cbet boolean,
    flg_f_cbet_opp boolean,
    flg_f_cbet_def_opp boolean,
    enum_f_cbet_action character varying(1),
    flg_t_face_raise boolean,
    flg_t_3bet boolean,
    flg_t_3bet_opp boolean,
    flg_t_3bet_def_opp boolean,
    enum_t_3bet_action character varying(1),
    flg_t_4bet boolean,
    flg_t_4bet_opp boolean,
    flg_t_4bet_def_opp boolean,
    enum_t_4bet_action character varying(1),
    flg_t_cbet boolean,
    flg_t_cbet_opp boolean,
    flg_t_cbet_def_opp boolean,
    enum_t_cbet_action character varying(1),
    flg_t_float boolean,
    flg_t_float_opp boolean,
    flg_t_float_def_opp boolean,
    enum_t_float_action character varying(1),
    flg_t_donk boolean,
    flg_t_donk_opp boolean,
    flg_t_donk_def_opp boolean,
    enum_t_donk_action character varying(1),
    flg_r_face_raise boolean,
    flg_r_3bet boolean,
    flg_r_3bet_opp boolean,
    flg_r_3bet_def_opp boolean,
    enum_r_3bet_action character varying(1),
    flg_r_4bet boolean,
    flg_r_4bet_opp boolean,
    flg_r_4bet_def_opp boolean,
    enum_r_4bet_action character varying(1),
    flg_r_cbet boolean,
    flg_r_cbet_opp boolean,
    flg_r_cbet_def_opp boolean,
    enum_r_cbet_action character varying(1),
    flg_r_float boolean,
    flg_r_float_opp boolean,
    flg_r_float_def_opp boolean,
    enum_r_float_action character varying(1),
    flg_r_donk boolean,
    flg_r_donk_opp boolean,
    flg_r_donk_def_opp boolean,
    enum_r_donk_action character varying(1),
    seat smallint,
    holecard_1 smallint,
    holecard_2 smallint,
    holecard_3 smallint,
    holecard_4 smallint,
    flg_hero boolean,
    amt_before real,
    amt_blind real,
    amt_ante real,
    amt_bet_p real,
    amt_bet_f real,
    amt_bet_t real,
    amt_bet_r real,
    amt_bet_ttl real,
    id_action_p integer,
    id_action_f integer,
    id_action_t integer,
    id_action_r integer,
    flg_p_open boolean,
    flg_p_open_opp boolean,
    flg_f_first boolean,
    flg_f_open boolean,
    flg_f_open_opp boolean,
    flg_f_has_position boolean,
    flg_t_first boolean,
    flg_t_open boolean,
    flg_t_open_opp boolean,
    flg_t_has_position boolean,
    flg_r_first boolean,
    flg_r_open boolean,
    flg_r_open_opp boolean,
    flg_r_has_position boolean,
    amt_p_raise_facing real,
    val_p_raise_facing_pct real,
    amt_p_2bet_facing real,
    val_p_2bet_facing_pct real,
    amt_p_3bet_facing real,
    val_p_3bet_facing_pct real,
    amt_p_4bet_facing real,
    val_p_4bet_facing_pct real,
    amt_p_5bet_facing real,
    val_p_5bet_facing_pct real,
    val_p_raise_aggressor_pos smallint,
    amt_p_raise_made real,
    val_p_raise_made_pct real,
    amt_p_raise_made_2 real,
    val_p_raise_made_2_pct real,
    amt_f_bet_facing real,
    val_f_bet_facing_pct real,
    val_f_bet_aggressor_pos smallint,
    amt_f_bet_made real,
    val_f_bet_made_pct real,
    amt_f_raise_facing real,
    val_f_raise_facing_pct real,
    amt_f_2bet_facing real,
    val_f_2bet_facing_pct real,
    amt_f_3bet_facing real,
    val_f_3bet_facing_pct real,
    amt_f_4bet_facing real,
    val_f_4bet_facing_pct real,
    val_f_raise_aggressor_pos smallint,
    amt_f_raise_made real,
    val_f_raise_made_pct real,
    amt_f_raise_made_2 real,
    val_f_raise_made_2_pct real,
    amt_t_bet_facing real,
    val_t_bet_facing_pct real,
    val_t_bet_aggressor_pos smallint,
    amt_t_bet_made real,
    val_t_bet_made_pct real,
    amt_t_raise_facing real,
    val_t_raise_facing_pct real,
    amt_t_2bet_facing real,
    val_t_2bet_facing_pct real,
    amt_t_3bet_facing real,
    val_t_3bet_facing_pct real,
    amt_t_4bet_facing real,
    val_t_4bet_facing_pct real,
    val_t_raise_aggressor_pos smallint,
    amt_t_raise_made real,
    val_t_raise_made_pct real,
    amt_t_raise_made_2 real,
    val_t_raise_made_2_pct real,
    amt_r_bet_facing real,
    val_r_bet_facing_pct real,
    val_r_bet_aggressor_pos smallint,
    amt_r_bet_made real,
    val_r_bet_made_pct real,
    amt_r_raise_facing real,
    val_r_raise_facing_pct real,
    amt_r_2bet_facing real,
    val_r_2bet_facing_pct real,
    amt_r_3bet_facing real,
    val_r_3bet_facing_pct real,
    amt_r_4bet_facing real,
    val_r_4bet_facing_pct real,
    val_r_raise_aggressor_pos smallint,
    amt_r_raise_made real,
    val_r_raise_made_pct real,
    amt_r_raise_made_2 real,
    val_r_raise_made_2_pct real
);


ALTER TABLE public.cash_hand_player_statistics OWNER TO postgres;

--
-- Name: cash_hand_summary; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cash_hand_summary (
    id integer NOT NULL,
    id_limit integer,
    id_table integer,
    hand_no bigint,
    date_played timestamp without time zone,
    date_imported timestamp without time zone,
    cnt_players integer,
    cnt_players_f integer,
    cnt_players_t integer,
    cnt_players_r integer,
    amt_p_effective_stack real,
    amt_f_effective_stack real,
    amt_t_effective_stack real,
    amt_r_effective_stack real,
    amt_pot bigint,
    amt_rake integer,
    amt_pot_f bigint,
    amt_pot_t bigint,
    amt_pot_r bigint,
    str_actors_p character varying(10),
    str_actors_f character varying(10),
    str_actors_t character varying(10),
    str_actors_r character varying(10),
    id_win_hand integer,
    id_winner integer,
    button smallint,
    card_1 smallint,
    card_2 smallint,
    card_3 smallint,
    card_4 smallint,
    card_5 smallint
);


ALTER TABLE public.cash_hand_summary OWNER TO postgres;

--
-- Name: cash_hand_summary_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cash_hand_summary_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cash_hand_summary_id_seq OWNER TO postgres;

--
-- Name: cash_hand_summary_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cash_hand_summary_id_seq OWNED BY cash_hand_summary.id;


--
-- Name: cash_hand_summary_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cash_hand_summary_id_seq', 1, false);


--
-- Name: cash_limit; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cash_limit (
    id integer NOT NULL,
    name character varying(32),
    amt_sb integer,
    amt_bb integer
);


ALTER TABLE public.cash_limit OWNER TO postgres;

--
-- Name: cash_limit_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cash_limit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cash_limit_id_seq OWNER TO postgres;

--
-- Name: cash_limit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cash_limit_id_seq OWNED BY cash_limit.id;


--
-- Name: cash_limit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cash_limit_id_seq', 1, false);


--
-- Name: cash_table; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cash_table (
    id integer NOT NULL,
    table_name character varying(32)
);


ALTER TABLE public.cash_table OWNER TO postgres;

--
-- Name: cash_table_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cash_table_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cash_table_id_seq OWNER TO postgres;

--
-- Name: cash_table_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cash_table_id_seq OWNED BY cash_table.id;


--
-- Name: cash_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cash_table_id_seq', 1, false);


--
-- Name: cash_table_session_summary; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cash_table_session_summary (
    id integer NOT NULL,
    id_player integer,
    id_limit integer,
    id_table integer,
    date_start timestamp without time zone,
    date_end timestamp without time zone,
    cnt_minutes integer,
    seat integer,
    amt_pot bigint,
    amt_won bigint,
    amt_rake bigint,
    cnt_hands integer,
    cnt_hands_won integer,
    cnt_ttl_players integer
);


ALTER TABLE public.cash_table_session_summary OWNER TO postgres;

--
-- Name: cash_table_session_summary_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cash_table_session_summary_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cash_table_session_summary_id_seq OWNER TO postgres;

--
-- Name: cash_table_session_summary_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cash_table_session_summary_id_seq OWNED BY cash_table_session_summary.id;


--
-- Name: cash_table_session_summary_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cash_table_session_summary_id_seq', 1, false);


--
-- Name: live_cash_player; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE live_cash_player (
    id_player integer,
    id_live_table integer,
    id_live_hand integer,
    amt_ante integer,
    amt_before integer,
    amt_stack bigint,
    amt_won bigint
);


ALTER TABLE public.live_cash_player OWNER TO postgres;

--
-- Name: live_cash_table; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE live_cash_table (
    id integer NOT NULL,
    id_live_hand integer,
    amt_ante integer,
    amt_sb integer,
    amt_bb bigint,
    amt_avg_stack bigint,
    amt_rake integer,
    amt_pot bigint,
    cnt_players integer,
    cnt_sqw_flop integer,
    winner character varying(32)
);


ALTER TABLE public.live_cash_table OWNER TO postgres;

--
-- Name: live_cash_table_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE live_cash_table_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.live_cash_table_id_seq OWNER TO postgres;

--
-- Name: live_cash_table_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE live_cash_table_id_seq OWNED BY live_cash_table.id;


--
-- Name: live_cash_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('live_cash_table_id_seq', 1, false);


--
-- Name: lookup_actions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE lookup_actions (
    id integer NOT NULL,
    name character varying(20)
);


ALTER TABLE public.lookup_actions OWNER TO postgres;

--
-- Name: lookup_actions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE lookup_actions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lookup_actions_id_seq OWNER TO postgres;

--
-- Name: lookup_actions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE lookup_actions_id_seq OWNED BY lookup_actions.id;


--
-- Name: lookup_actions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lookup_actions_id_seq', 1, false);


--
-- Name: lookup_hand_groups; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE lookup_hand_groups (
    id integer NOT NULL,
    name character varying(20)
);


ALTER TABLE public.lookup_hand_groups OWNER TO postgres;

--
-- Name: lookup_hand_groups_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE lookup_hand_groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lookup_hand_groups_id_seq OWNER TO postgres;

--
-- Name: lookup_hand_groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE lookup_hand_groups_id_seq OWNED BY lookup_hand_groups.id;


--
-- Name: lookup_hand_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lookup_hand_groups_id_seq', 1, false);


--
-- Name: lookup_hand_ranks; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE lookup_hand_ranks (
    id integer NOT NULL,
    id_group integer,
    group_name character varying(20)
);


ALTER TABLE public.lookup_hand_ranks OWNER TO postgres;

--
-- Name: lookup_hand_ranks_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE lookup_hand_ranks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lookup_hand_ranks_id_seq OWNER TO postgres;

--
-- Name: lookup_hand_ranks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE lookup_hand_ranks_id_seq OWNED BY lookup_hand_ranks.id;


--
-- Name: lookup_hand_ranks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lookup_hand_ranks_id_seq', 1, false);


--
-- Name: lookup_hole_cards; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE lookup_hole_cards (
    id integer NOT NULL,
    hole_cards character varying(4),
    enum_pair_type character varying(1)
);


ALTER TABLE public.lookup_hole_cards OWNER TO postgres;

--
-- Name: lookup_hole_cards_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE lookup_hole_cards_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lookup_hole_cards_id_seq OWNER TO postgres;

--
-- Name: lookup_hole_cards_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE lookup_hole_cards_id_seq OWNED BY lookup_hole_cards.id;


--
-- Name: lookup_hole_cards_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lookup_hole_cards_id_seq', 1, false);


--
-- Name: lookup_positions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE lookup_positions (
    cnt_players integer,
    "position" integer,
    absolute_position integer,
    description character varying(20),
    flg_sb boolean,
    flg_bb boolean,
    flg_ep boolean,
    flg_mp boolean,
    flg_co boolean,
    flg_btn boolean
);


ALTER TABLE public.lookup_positions OWNER TO postgres;

--
-- Name: player; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE player (
    id integer NOT NULL,
    name character varying(32)
);


ALTER TABLE public.player OWNER TO postgres;

--
-- Name: player_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE player_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.player_id_seq OWNER TO postgres;

--
-- Name: player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE player_id_seq OWNED BY player.id;


--
-- Name: player_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('player_id_seq', 1, false);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_summary ALTER COLUMN id SET DEFAULT nextval('cash_hand_summary_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_limit ALTER COLUMN id SET DEFAULT nextval('cash_limit_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_table ALTER COLUMN id SET DEFAULT nextval('cash_table_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_table_session_summary ALTER COLUMN id SET DEFAULT nextval('cash_table_session_summary_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY live_cash_table ALTER COLUMN id SET DEFAULT nextval('live_cash_table_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY lookup_actions ALTER COLUMN id SET DEFAULT nextval('lookup_actions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY lookup_hand_groups ALTER COLUMN id SET DEFAULT nextval('lookup_hand_groups_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY lookup_hand_ranks ALTER COLUMN id SET DEFAULT nextval('lookup_hand_ranks_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY lookup_hole_cards ALTER COLUMN id SET DEFAULT nextval('lookup_hole_cards_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY player ALTER COLUMN id SET DEFAULT nextval('player_id_seq'::regclass);


--
-- Data for Name: cash_hand_player_combinations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cash_hand_player_combinations (id_hand, id_player, flg_f_highcard, flg_f_1pair, flg_f_2pair, flg_f_threeoak, flg_f_straight, flg_f_flush, flg_f_fullhouse, flg_f_strflush, id_f_hand_strength, id_f_kicker_strength, val_f_hole_cards_used, flg_t_highcard, flg_t_1pair, flg_t_2pair, flg_t_threeoak, flg_t_straight, flg_t_flush, flg_t_fullhouse, flg_t_fouroak, flg_t_strflush, id_t_hand_strength, id_t_kicker_strength, val_t_hole_cards_used, flg_r_highcard, flg_r_1pair, flg_r_2pair, flg_r_threeoak, flg_r_straight, flg_r_flush, flg_r_fullhouse, flg_r_fouroak, flg_r_strflush, id_r_hand_strength, id_r_kicker_strength, val_r_hole_cards_used) FROM stdin;
\.


--
-- Data for Name: cash_hand_player_statistics; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cash_hand_player_statistics (id_hand, id_player, id_holecard, id_limit, id_session, date_played, "position", cnt_p_raise, flg_p_first_raise, cnt_p_call, flg_p_limp, flg_p_fold, flg_p_ccall, cnt_p_face_limpers, flg_vpip, flg_f_bet, cnt_f_raise, flg_f_first_raise, cnt_f_call, flg_f_check, flg_f_check_raise, flg_f_fold, flg_f_saw, flg_t_bet, cnt_t_raise, flg_t_first_raise, cnt_t_call, flg_t_check, flg_t_check_raise, flg_t_fold, flg_t_saw, flg_r_bet, cnt_r_raise, flg_r_first_raise, cnt_r_call, flg_r_check, flg_r_check_raise, flg_r_fold, flg_r_saw, enum_allin, enum_face_allin, enum_face_allin_action, flg_blind_s, flg_blind_b, flg_blind_ds, flg_blind_db, flg_sb_steal_fold, flg_bb_steal_fold, flg_blind_def_opp, flg_steal_att, flg_steal_opp, flg_blind_k, flg_showdown, flg_won_hand, amt_won, amt_expected_won, val_equity, id_final_hand, flg_showed, enum_folded, flg_p_face_raise, flg_p_3bet, flg_p_3bet_opp, flg_p_3bet_def_opp, enum_p_3bet_action, flg_p_4bet, flg_p_4bet_opp, flg_p_4bet_def_opp, enum_p_4bet_action, flg_p_squeeze, flg_p_squeeze_opp, flg_p_squeeze_def_opp, enum_p_squeeze_action, flg_f_face_raise, flg_f_3bet, flg_f_3bet_opp, flg_f_3bet_def_opp, enum_f_3bet_action, flg_f_4bet, flg_f_4bet_opp, flg_f_4bet_def_opp, enum_f_4bet_action, flg_f_cbet, flg_f_cbet_opp, flg_f_cbet_def_opp, enum_f_cbet_action, flg_t_face_raise, flg_t_3bet, flg_t_3bet_opp, flg_t_3bet_def_opp, enum_t_3bet_action, flg_t_4bet, flg_t_4bet_opp, flg_t_4bet_def_opp, enum_t_4bet_action, flg_t_cbet, flg_t_cbet_opp, flg_t_cbet_def_opp, enum_t_cbet_action, flg_t_float, flg_t_float_opp, flg_t_float_def_opp, enum_t_float_action, flg_t_donk, flg_t_donk_opp, flg_t_donk_def_opp, enum_t_donk_action, flg_r_face_raise, flg_r_3bet, flg_r_3bet_opp, flg_r_3bet_def_opp, enum_r_3bet_action, flg_r_4bet, flg_r_4bet_opp, flg_r_4bet_def_opp, enum_r_4bet_action, flg_r_cbet, flg_r_cbet_opp, flg_r_cbet_def_opp, enum_r_cbet_action, flg_r_float, flg_r_float_opp, flg_r_float_def_opp, enum_r_float_action, flg_r_donk, flg_r_donk_opp, flg_r_donk_def_opp, enum_r_donk_action, seat, holecard_1, holecard_2, holecard_3, holecard_4, flg_hero, amt_before, amt_blind, amt_ante, amt_bet_p, amt_bet_f, amt_bet_t, amt_bet_r, amt_bet_ttl, id_action_p, id_action_f, id_action_t, id_action_r, flg_p_open, flg_p_open_opp, flg_f_first, flg_f_open, flg_f_open_opp, flg_f_has_position, flg_t_first, flg_t_open, flg_t_open_opp, flg_t_has_position, flg_r_first, flg_r_open, flg_r_open_opp, flg_r_has_position, amt_p_raise_facing, val_p_raise_facing_pct, amt_p_2bet_facing, val_p_2bet_facing_pct, amt_p_3bet_facing, val_p_3bet_facing_pct, amt_p_4bet_facing, val_p_4bet_facing_pct, amt_p_5bet_facing, val_p_5bet_facing_pct, val_p_raise_aggressor_pos, amt_p_raise_made, val_p_raise_made_pct, amt_p_raise_made_2, val_p_raise_made_2_pct, amt_f_bet_facing, val_f_bet_facing_pct, val_f_bet_aggressor_pos, amt_f_bet_made, val_f_bet_made_pct, amt_f_raise_facing, val_f_raise_facing_pct, amt_f_2bet_facing, val_f_2bet_facing_pct, amt_f_3bet_facing, val_f_3bet_facing_pct, amt_f_4bet_facing, val_f_4bet_facing_pct, val_f_raise_aggressor_pos, amt_f_raise_made, val_f_raise_made_pct, amt_f_raise_made_2, val_f_raise_made_2_pct, amt_t_bet_facing, val_t_bet_facing_pct, val_t_bet_aggressor_pos, amt_t_bet_made, val_t_bet_made_pct, amt_t_raise_facing, val_t_raise_facing_pct, amt_t_2bet_facing, val_t_2bet_facing_pct, amt_t_3bet_facing, val_t_3bet_facing_pct, amt_t_4bet_facing, val_t_4bet_facing_pct, val_t_raise_aggressor_pos, amt_t_raise_made, val_t_raise_made_pct, amt_t_raise_made_2, val_t_raise_made_2_pct, amt_r_bet_facing, val_r_bet_facing_pct, val_r_bet_aggressor_pos, amt_r_bet_made, val_r_bet_made_pct, amt_r_raise_facing, val_r_raise_facing_pct, amt_r_2bet_facing, val_r_2bet_facing_pct, amt_r_3bet_facing, val_r_3bet_facing_pct, amt_r_4bet_facing, val_r_4bet_facing_pct, val_r_raise_aggressor_pos, amt_r_raise_made, val_r_raise_made_pct, amt_r_raise_made_2, val_r_raise_made_2_pct) FROM stdin;
\.


--
-- Data for Name: cash_hand_summary; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cash_hand_summary (id, id_limit, id_table, hand_no, date_played, date_imported, cnt_players, cnt_players_f, cnt_players_t, cnt_players_r, amt_p_effective_stack, amt_f_effective_stack, amt_t_effective_stack, amt_r_effective_stack, amt_pot, amt_rake, amt_pot_f, amt_pot_t, amt_pot_r, str_actors_p, str_actors_f, str_actors_t, str_actors_r, id_win_hand, id_winner, button, card_1, card_2, card_3, card_4, card_5) FROM stdin;
\.


--
-- Data for Name: cash_limit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cash_limit (id, name, amt_sb, amt_bb) FROM stdin;
\.


--
-- Data for Name: cash_table; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cash_table (id, table_name) FROM stdin;
\.


--
-- Data for Name: cash_table_session_summary; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cash_table_session_summary (id, id_player, id_limit, id_table, date_start, date_end, cnt_minutes, seat, amt_pot, amt_won, amt_rake, cnt_hands, cnt_hands_won, cnt_ttl_players) FROM stdin;
\.


--
-- Data for Name: live_cash_player; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY live_cash_player (id_player, id_live_table, id_live_hand, amt_ante, amt_before, amt_stack, amt_won) FROM stdin;
\.


--
-- Data for Name: live_cash_table; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY live_cash_table (id, id_live_hand, amt_ante, amt_sb, amt_bb, amt_avg_stack, amt_rake, amt_pot, cnt_players, cnt_sqw_flop, winner) FROM stdin;
\.


--
-- Data for Name: lookup_actions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY lookup_actions (id, name) FROM stdin;
\.


--
-- Data for Name: lookup_hand_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY lookup_hand_groups (id, name) FROM stdin;
\.


--
-- Data for Name: lookup_hand_ranks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY lookup_hand_ranks (id, id_group, group_name) FROM stdin;
\.


--
-- Data for Name: lookup_hole_cards; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY lookup_hole_cards (id, hole_cards, enum_pair_type) FROM stdin;
\.


--
-- Data for Name: lookup_positions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY lookup_positions (cnt_players, "position", absolute_position, description, flg_sb, flg_bb, flg_ep, flg_mp, flg_co, flg_btn) FROM stdin;
\.


--
-- Data for Name: player; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY player (id, name) FROM stdin;
\.


--
-- Name: cash_hand_summary_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cash_hand_summary
    ADD CONSTRAINT cash_hand_summary_pkey PRIMARY KEY (id);


--
-- Name: cash_limit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cash_limit
    ADD CONSTRAINT cash_limit_pkey PRIMARY KEY (id);


--
-- Name: cash_table_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cash_table
    ADD CONSTRAINT cash_table_pkey PRIMARY KEY (id);


--
-- Name: cash_table_session_summary_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cash_table_session_summary
    ADD CONSTRAINT cash_table_session_summary_pkey PRIMARY KEY (id);


--
-- Name: live_cash_table_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY live_cash_table
    ADD CONSTRAINT live_cash_table_pkey PRIMARY KEY (id);


--
-- Name: lookup_actions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY lookup_actions
    ADD CONSTRAINT lookup_actions_pkey PRIMARY KEY (id);


--
-- Name: lookup_hand_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY lookup_hand_groups
    ADD CONSTRAINT lookup_hand_groups_pkey PRIMARY KEY (id);


--
-- Name: lookup_hand_ranks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY lookup_hand_ranks
    ADD CONSTRAINT lookup_hand_ranks_pkey PRIMARY KEY (id);


--
-- Name: lookup_hole_cards_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY lookup_hole_cards
    ADD CONSTRAINT lookup_hole_cards_pkey PRIMARY KEY (id);


--
-- Name: player_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY player
    ADD CONSTRAINT player_pkey PRIMARY KEY (id);


--
-- Name: cash_hand_player_combinations_id_hand_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_player_combinations
    ADD CONSTRAINT cash_hand_player_combinations_id_hand_fkey FOREIGN KEY (id_hand) REFERENCES cash_hand_summary(id);


--
-- Name: cash_hand_player_combinations_id_player_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_player_combinations
    ADD CONSTRAINT cash_hand_player_combinations_id_player_fkey FOREIGN KEY (id_player) REFERENCES player(id);


--
-- Name: cash_hand_player_statistics_id_hand_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_player_statistics
    ADD CONSTRAINT cash_hand_player_statistics_id_hand_fkey FOREIGN KEY (id_hand) REFERENCES cash_hand_summary(id);


--
-- Name: cash_hand_player_statistics_id_holecard_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_player_statistics
    ADD CONSTRAINT cash_hand_player_statistics_id_holecard_fkey FOREIGN KEY (id_holecard) REFERENCES lookup_hole_cards(id);


--
-- Name: cash_hand_player_statistics_id_limit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_player_statistics
    ADD CONSTRAINT cash_hand_player_statistics_id_limit_fkey FOREIGN KEY (id_limit) REFERENCES cash_limit(id);


--
-- Name: cash_hand_player_statistics_id_player_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_player_statistics
    ADD CONSTRAINT cash_hand_player_statistics_id_player_fkey FOREIGN KEY (id_player) REFERENCES player(id);


--
-- Name: cash_hand_player_statistics_id_session_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_player_statistics
    ADD CONSTRAINT cash_hand_player_statistics_id_session_fkey FOREIGN KEY (id_session) REFERENCES cash_table_session_summary(id);


--
-- Name: cash_hand_summary_id_limit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_summary
    ADD CONSTRAINT cash_hand_summary_id_limit_fkey FOREIGN KEY (id_limit) REFERENCES cash_limit(id);


--
-- Name: cash_hand_summary_id_table_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_hand_summary
    ADD CONSTRAINT cash_hand_summary_id_table_fkey FOREIGN KEY (id_table) REFERENCES cash_table(id);


--
-- Name: cash_table_session_summary_id_limit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_table_session_summary
    ADD CONSTRAINT cash_table_session_summary_id_limit_fkey FOREIGN KEY (id_limit) REFERENCES cash_limit(id);


--
-- Name: cash_table_session_summary_id_player_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_table_session_summary
    ADD CONSTRAINT cash_table_session_summary_id_player_fkey FOREIGN KEY (id_player) REFERENCES player(id);


--
-- Name: cash_table_session_summary_id_table_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cash_table_session_summary
    ADD CONSTRAINT cash_table_session_summary_id_table_fkey FOREIGN KEY (id_table) REFERENCES cash_table(id);


--
-- Name: live_cash_player_id_live_hand_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY live_cash_player
    ADD CONSTRAINT live_cash_player_id_live_hand_fkey FOREIGN KEY (id_live_hand) REFERENCES cash_hand_summary(id);


--
-- Name: live_cash_player_id_live_table_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY live_cash_player
    ADD CONSTRAINT live_cash_player_id_live_table_fkey FOREIGN KEY (id_live_table) REFERENCES live_cash_table(id);


--
-- Name: live_cash_player_id_player_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY live_cash_player
    ADD CONSTRAINT live_cash_player_id_player_fkey FOREIGN KEY (id_player) REFERENCES player(id);


--
-- Name: live_cash_table_id_live_hand_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY live_cash_table
    ADD CONSTRAINT live_cash_table_id_live_hand_fkey FOREIGN KEY (id_live_hand) REFERENCES cash_hand_summary(id);


--
-- Name: lookup_hand_ranks_id_group_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY lookup_hand_ranks
    ADD CONSTRAINT lookup_hand_ranks_id_group_fkey FOREIGN KEY (id_group) REFERENCES lookup_hand_groups(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

