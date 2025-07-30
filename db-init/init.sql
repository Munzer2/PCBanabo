--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9 (Ubuntu 16.9-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.9 (Ubuntu 16.9-0ubuntu0.24.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: benchmark; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.benchmark (
    cpu_id bigint,
    gpu_id bigint,
    cinebench_single integer,
    cinebench_multi integer,
    blender integer,
    geekbench integer,
    photoshop integer,
    premiere_pro integer,
    lightroom integer,
    davinci integer,
    horizon_zero_dawn integer,
    f1_2024 integer,
    valorant integer,
    overwatch integer,
    csgo integer,
    fc2025 integer,
    black_myth_wukong integer
);


ALTER TABLE public.benchmark OWNER TO new_user;

--
-- Name: casing; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.casing (
    id bigint NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    motherboard_support character varying(255) NOT NULL,
    psu_clearance integer NOT NULL,
    gpu_clearance integer NOT NULL,
    cpu_clearance integer NOT NULL,
    top_rad_support character varying(255),
    bottom_rad_support character varying(255),
    side_rad_support character varying(255),
    included_fan integer,
    rgb boolean,
    display boolean,
    color character varying(255) NOT NULL,
    avg_price double precision NOT NULL,
    official_product_url character varying(255)
);


ALTER TABLE public.casing OWNER TO new_user;

--
-- Name: casing_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.casing_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.casing_id_seq OWNER TO new_user;

--
-- Name: casing_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.casing_id_seq OWNED BY public.casing.id;


--
-- Name: cpu; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.cpu (
    id bigint NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    sku_number character varying(255),
    socket character varying(255),
    tdp integer,
    cache_size character varying(255),
    overclockable boolean DEFAULT false,
    average_price double precision,
    official_product_url character varying(255)
);


ALTER TABLE public.cpu OWNER TO new_user;

--
-- Name: cpu_cooler; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.cpu_cooler (
    id bigint NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    socket_support character varying(255) NOT NULL,
    cooler_type character varying(255) NOT NULL,
    tower_height integer,
    radiator_size integer,
    cooling_capacity integer,
    ram_clearance integer NOT NULL,
    color character varying(255),
    rgb boolean,
    display boolean,
    avg_price double precision NOT NULL,
    official_product_url character varying(255)
);


ALTER TABLE public.cpu_cooler OWNER TO new_user;

--
-- Name: cpu_cooler_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.cpu_cooler_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cpu_cooler_id_seq OWNER TO new_user;

--
-- Name: cpu_cooler_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.cpu_cooler_id_seq OWNED BY public.cpu_cooler.id;


--
-- Name: cpu_new_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.cpu_new_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cpu_new_id_seq OWNER TO new_user;

--
-- Name: cpu_new_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.cpu_new_id_seq OWNED BY public.cpu.id;


--
-- Name: gpu; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.gpu (
    id bigint NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    gpu_core character varying(255) NOT NULL,
    vram integer NOT NULL,
    tdp integer NOT NULL,
    power_connector_type character varying(255) NOT NULL,
    power_connector_count integer NOT NULL,
    card_length integer NOT NULL,
    card_height integer NOT NULL,
    card_thickness integer NOT NULL,
    rgb boolean,
    avg_price double precision NOT NULL,
    official_product_url character varying(255)
);


ALTER TABLE public.gpu OWNER TO new_user;

--
-- Name: gpu_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.gpu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.gpu_id_seq OWNER TO new_user;

--
-- Name: gpu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.gpu_id_seq OWNED BY public.gpu.id;


--
-- Name: motherboard; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.motherboard (
    id bigint NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    chipset character varying(255) NOT NULL,
    socket character varying(255) NOT NULL,
    form_factor character varying(255) NOT NULL,
    mem_type character varying(255) NOT NULL,
    mem_slot integer NOT NULL,
    max_mem_speed integer,
    pcie_slot_x16 integer NOT NULL,
    pcie_clot_x4 integer NOT NULL,
    nvme_slots integer NOT NULL,
    nvme_version character varying(20) NOT NULL,
    sata_ports integer NOT NULL,
    lan_speed character varying(255) NOT NULL,
    wifi_version character varying(255),
    bluetooth_version character varying(255),
    usb_2_ports integer NOT NULL,
    usb_3_ports integer NOT NULL,
    usb_c_ports integer NOT NULL,
    thunderbolt_ports integer,
    max_power integer,
    opt_feature character varying(255),
    avg_price double precision,
    official_product_url character varying(255)
);


ALTER TABLE public.motherboard OWNER TO new_user;

--
-- Name: motherboard_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.motherboard_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.motherboard_id_seq OWNER TO new_user;

--
-- Name: motherboard_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.motherboard_id_seq OWNED BY public.motherboard.id;


--
-- Name: psu; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.psu (
    id bigint NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    form_factor character varying(255) NOT NULL,
    wattage integer NOT NULL,
    psu_length integer NOT NULL,
    eps_connectors integer NOT NULL,
    pcie_8pin_connectors integer NOT NULL,
    pcie_16pin_connectors integer NOT NULL,
    certification character varying(255) NOT NULL,
    rgb boolean,
    avg_price double precision NOT NULL,
    official_product_url character varying(255)
);


ALTER TABLE public.psu OWNER TO new_user;

--
-- Name: psu_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.psu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.psu_id_seq OWNER TO new_user;

--
-- Name: psu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.psu_id_seq OWNED BY public.psu.id;


--
-- Name: ram; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.ram (
    id bigint NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    mem_type character varying(255) NOT NULL,
    capacity character varying(255) NOT NULL,
    speed integer NOT NULL,
    timings character varying(255),
    rgb boolean,
    avg_price double precision NOT NULL,
    official_product_url character varying(255)
);


ALTER TABLE public.ram OWNER TO new_user;

--
-- Name: ram_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.ram_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ram_id_seq OWNER TO new_user;

--
-- Name: ram_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.ram_id_seq OWNED BY public.ram.id;


--
-- Name: saved_builds; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.saved_builds (
    id integer NOT NULL,
    user_id integer NOT NULL,
    cpu_id integer,
    motherboard_id integer,
    ram_id integer,
    ssd_id integer,
    gpu_id integer,
    psu_id integer,
    casing_id integer,
    cpu_cooler_id integer,
    build_name character varying(255),
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    public boolean
);


ALTER TABLE public.saved_builds OWNER TO new_user;

--
-- Name: saved_builds_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.saved_builds_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.saved_builds_id_seq OWNER TO new_user;

--
-- Name: saved_builds_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.saved_builds_id_seq OWNED BY public.saved_builds.id;


--
-- Name: ssd; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.ssd (
    id bigint NOT NULL,
    brand_name character varying(255) NOT NULL,
    model_name character varying(255) NOT NULL,
    capacity character varying(255) NOT NULL,
    form_factor character varying(255) NOT NULL,
    pcie_gen character varying(255) NOT NULL,
    seq_read integer,
    seq_write integer,
    dram_cache boolean,
    avg_price double precision NOT NULL,
    official_procduct_url text,
    official_product_url character varying(255)
);


ALTER TABLE public.ssd OWNER TO new_user;

--
-- Name: ssd_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.ssd_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ssd_id_seq OWNER TO new_user;

--
-- Name: ssd_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.ssd_id_seq OWNED BY public.ssd.id;


--
-- Name: user_info; Type: TABLE; Schema: public; Owner: new_user
--

CREATE TABLE public.user_info (
    id integer NOT NULL,
    user_name character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    enc_password character varying(255) NOT NULL
);


ALTER TABLE public.user_info OWNER TO new_user;

--
-- Name: user_info_id_seq; Type: SEQUENCE; Schema: public; Owner: new_user
--

CREATE SEQUENCE public.user_info_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_info_id_seq OWNER TO new_user;

--
-- Name: user_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: new_user
--

ALTER SEQUENCE public.user_info_id_seq OWNED BY public.user_info.id;


--
-- Name: casing id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.casing ALTER COLUMN id SET DEFAULT nextval('public.casing_id_seq'::regclass);


--
-- Name: cpu id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.cpu ALTER COLUMN id SET DEFAULT nextval('public.cpu_new_id_seq'::regclass);


--
-- Name: cpu_cooler id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.cpu_cooler ALTER COLUMN id SET DEFAULT nextval('public.cpu_cooler_id_seq'::regclass);


--
-- Name: gpu id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.gpu ALTER COLUMN id SET DEFAULT nextval('public.gpu_id_seq'::regclass);


--
-- Name: motherboard id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.motherboard ALTER COLUMN id SET DEFAULT nextval('public.motherboard_id_seq'::regclass);


--
-- Name: psu id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.psu ALTER COLUMN id SET DEFAULT nextval('public.psu_id_seq'::regclass);


--
-- Name: ram id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.ram ALTER COLUMN id SET DEFAULT nextval('public.ram_id_seq'::regclass);


--
-- Name: saved_builds id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds ALTER COLUMN id SET DEFAULT nextval('public.saved_builds_id_seq'::regclass);


--
-- Name: ssd id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.ssd ALTER COLUMN id SET DEFAULT nextval('public.ssd_id_seq'::regclass);


--
-- Name: user_info id; Type: DEFAULT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.user_info ALTER COLUMN id SET DEFAULT nextval('public.user_info_id_seq'::regclass);


--
-- Data for Name: benchmark; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.benchmark VALUES (1, 0, 1420, 16843, 24890, 12589, 1323, 1087, 1579, 1602, 158, 123, 205, 112, 191, 165, 187);
INSERT INTO public.benchmark VALUES (2, 0, 1687, 22314, 29412, 14201, 1538, 1337, 1698, 1730, 170, 139, 217, 122, 198, 176, 200);
INSERT INTO public.benchmark VALUES (3, 0, 1109, 12508, 21033, 11076, 1140, 1023, 1440, 1301, 132, 97, 186, 108, 172, 152, 165);
INSERT INTO public.benchmark VALUES (4, 0, 1998, 40201, 36844, 17304, 1899, 1556, 2100, 1902, 180, 156, 229, 138, 211, 192, 224);
INSERT INTO public.benchmark VALUES (5, 0, 850, 6534, 11345, 9633, 893, 710, 1200, 1152, 101, 88, 153, 91, 141, 120, 138);
INSERT INTO public.benchmark VALUES (6, 0, 1234, 15709, 19783, 13221, 1287, 1003, 1455, 1482, 145, 112, 199, 108, 177, 155, 184);
INSERT INTO public.benchmark VALUES (7, 0, 2010, 45123, 38912, 19633, 1945, 1683, 2222, 2083, 187, 159, 238, 147, 221, 199, 229);
INSERT INTO public.benchmark VALUES (8, 0, 999, 8920, 15043, 10433, 1020, 840, 1350, 1235, 117, 101, 173, 94, 160, 138, 154);
INSERT INTO public.benchmark VALUES (9, 0, 1745, 31129, 32455, 15698, 1678, 1402, 1802, 1780, 176, 144, 213, 131, 204, 181, 213);
INSERT INTO public.benchmark VALUES (10, 0, 900, 7123, 12044, 9821, 951, 785, 1225, 1120, 108, 94, 149, 87, 139, 118, 133);
INSERT INTO public.benchmark VALUES (11, 0, 1888, 38901, 36490, 18233, 1804, 1601, 2184, 1967, 182, 150, 230, 143, 215, 190, 225);
INSERT INTO public.benchmark VALUES (12, 0, 1367, 17450, 23340, 13833, 1402, 1135, 1601, 1500, 152, 120, 200, 114, 184, 163, 192);
INSERT INTO public.benchmark VALUES (13, 0, 1601, 26400, 28800, 14989, 1587, 1312, 1755, 1722, 169, 138, 212, 128, 202, 178, 208);
INSERT INTO public.benchmark VALUES (14, 0, 1055, 10987, 18400, 12210, 1180, 970, 1400, 1309, 123, 102, 180, 100, 168, 146, 159);
INSERT INTO public.benchmark VALUES (15, 0, 1450, 19034, 25050, 13567, 1429, 1165, 1650, 1550, 160, 125, 205, 118, 190, 168, 197);
INSERT INTO public.benchmark VALUES (16, 0, 1802, 33900, 35600, 17111, 1759, 1502, 2050, 1899, 178, 148, 227, 142, 210, 188, 221);
INSERT INTO public.benchmark VALUES (17, 0, 1110, 11709, 19333, 12580, 1203, 1000, 1430, 1333, 125, 104, 184, 106, 170, 149, 162);
INSERT INTO public.benchmark VALUES (18, 0, 1303, 16299, 22120, 13401, 1355, 1090, 1588, 1460, 149, 118, 197, 112, 181, 158, 188);
INSERT INTO public.benchmark VALUES (19, 0, 1540, 24388, 27000, 14400, 1498, 1240, 1701, 1683, 165, 135, 210, 124, 198, 176, 205);
INSERT INTO public.benchmark VALUES (20, 0, 950, 8213, 13300, 9900, 983, 815, 1250, 1200, 112, 93, 160, 90, 147, 126, 140);
INSERT INTO public.benchmark VALUES (0, 1, 810, 14890, 310, 12050, 860, 770, 790, 780, 92, 98, 255, 240, 230, 210, 215);
INSERT INTO public.benchmark VALUES (0, 2, 790, 14300, 290, 11500, 830, 740, 770, 750, 89, 95, 248, 238, 220, 205, 210);
INSERT INTO public.benchmark VALUES (0, 3, 820, 14980, 320, 12130, 870, 790, 810, 785, 93, 100, 260, 245, 235, 215, 218);
INSERT INTO public.benchmark VALUES (0, 4, 765, 14020, 280, 11240, 810, 720, 750, 730, 87, 92, 240, 225, 215, 200, 208);
INSERT INTO public.benchmark VALUES (0, 5, 800, 14560, 305, 11780, 840, 750, 780, 760, 91, 97, 252, 235, 225, 208, 212);
INSERT INTO public.benchmark VALUES (0, 6, 845, 15230, 330, 12400, 890, 800, 820, 790, 96, 102, 265, 250, 240, 218, 222);
INSERT INTO public.benchmark VALUES (0, 7, 750, 13800, 270, 11000, 800, 710, 740, 720, 85, 90, 235, 220, 210, 195, 205);
INSERT INTO public.benchmark VALUES (0, 8, 805, 14720, 315, 11980, 855, 765, 795, 775, 90, 96, 250, 230, 220, 207, 214);
INSERT INTO public.benchmark VALUES (0, 9, 830, 15050, 325, 12260, 875, 785, 805, 780, 94, 99, 258, 242, 232, 213, 219);
INSERT INTO public.benchmark VALUES (0, 10, 770, 14150, 285, 11380, 820, 730, 760, 740, 88, 93, 245, 228, 218, 202, 209);
INSERT INTO public.benchmark VALUES (0, 11, 815, 14830, 310, 12020, 860, 770, 790, 765, 91, 97, 255, 239, 229, 209, 216);
INSERT INTO public.benchmark VALUES (0, 12, 785, 14200, 295, 11490, 835, 745, 770, 750, 89, 94, 247, 233, 221, 204, 211);
INSERT INTO public.benchmark VALUES (0, 13, 825, 15090, 325, 12310, 880, 790, 815, 785, 93, 99, 260, 244, 236, 216, 220);
INSERT INTO public.benchmark VALUES (0, 14, 760, 13900, 275, 11150, 805, 715, 745, 725, 86, 91, 238, 223, 213, 197, 206);
INSERT INTO public.benchmark VALUES (0, 15, 795, 14450, 300, 11690, 845, 755, 785, 765, 90, 95, 250, 232, 224, 206, 213);
INSERT INTO public.benchmark VALUES (0, 16, 840, 15190, 335, 12500, 895, 805, 825, 795, 97, 103, 267, 252, 242, 219, 223);
INSERT INTO public.benchmark VALUES (0, 17, 755, 13700, 265, 10900, 795, 705, 735, 715, 84, 89, 230, 218, 208, 193, 203);
INSERT INTO public.benchmark VALUES (0, 18, 810, 14690, 310, 11950, 855, 765, 795, 770, 91, 96, 253, 236, 226, 208, 215);
INSERT INTO public.benchmark VALUES (0, 19, 835, 15140, 328, 12400, 885, 795, 820, 790, 95, 101, 262, 246, 238, 217, 221);
INSERT INTO public.benchmark VALUES (0, 20, 780, 14090, 290, 11280, 825, 725, 755, 735, 88, 93, 243, 227, 217, 201, 210);
INSERT INTO public.benchmark VALUES (0, 21, 820, 14900, 320, 12110, 870, 780, 810, 782, 92, 98, 257, 241, 231, 211, 217);
INSERT INTO public.benchmark VALUES (0, 22, 770, 14100, 285, 11320, 820, 730, 760, 742, 87, 93, 244, 229, 219, 203, 210);
INSERT INTO public.benchmark VALUES (0, 23, 805, 14760, 315, 12010, 855, 765, 795, 773, 91, 96, 254, 237, 227, 209, 214);
INSERT INTO public.benchmark VALUES (0, 24, 830, 15030, 326, 12300, 878, 785, 812, 788, 94, 100, 259, 243, 234, 214, 220);
INSERT INTO public.benchmark VALUES (0, 25, 765, 13980, 276, 11180, 810, 720, 748, 728, 86, 92, 239, 224, 212, 198, 207);
INSERT INTO public.benchmark VALUES (0, 26, 800, 14670, 305, 11890, 850, 760, 790, 765, 90, 95, 250, 234, 224, 207, 213);
INSERT INTO public.benchmark VALUES (0, 27, 830, 15120, 325, 12330, 880, 790, 810, 785, 94, 99, 259, 242, 232, 214, 219);
INSERT INTO public.benchmark VALUES (0, 28, 770, 14110, 285, 11310, 820, 730, 760, 740, 87, 92, 245, 228, 218, 202, 209);
INSERT INTO public.benchmark VALUES (0, 29, 845, 15280, 330, 12420, 890, 800, 820, 795, 96, 102, 265, 250, 240, 218, 222);
INSERT INTO public.benchmark VALUES (0, 30, 750, 13760, 270, 10970, 800, 710, 740, 720, 85, 90, 235, 220, 210, 195, 205);
INSERT INTO public.benchmark VALUES (0, 31, 815, 14810, 310, 12030, 860, 770, 790, 768, 91, 96, 255, 239, 229, 209, 216);
INSERT INTO public.benchmark VALUES (0, 32, 780, 14210, 295, 11470, 835, 745, 770, 750, 89, 94, 247, 233, 221, 204, 211);
INSERT INTO public.benchmark VALUES (0, 33, 825, 15080, 325, 12340, 880, 790, 815, 785, 93, 99, 260, 244, 236, 216, 220);
INSERT INTO public.benchmark VALUES (0, 34, 760, 13910, 275, 11150, 805, 715, 745, 725, 86, 91, 238, 223, 213, 197, 206);
INSERT INTO public.benchmark VALUES (0, 35, 795, 14470, 300, 11690, 845, 755, 785, 765, 90, 95, 250, 232, 224, 206, 213);
INSERT INTO public.benchmark VALUES (0, 36, 840, 15210, 335, 12530, 895, 805, 825, 795, 97, 103, 267, 252, 242, 219, 223);
INSERT INTO public.benchmark VALUES (0, 37, 755, 13710, 265, 10950, 795, 705, 735, 715, 84, 89, 230, 218, 208, 193, 203);
INSERT INTO public.benchmark VALUES (0, 38, 810, 14680, 310, 11960, 855, 765, 795, 770, 91, 96, 253, 236, 226, 208, 215);
INSERT INTO public.benchmark VALUES (0, 39, 835, 15130, 328, 12410, 885, 795, 820, 790, 95, 101, 262, 246, 238, 217, 221);
INSERT INTO public.benchmark VALUES (0, 40, 780, 14100, 290, 11280, 825, 725, 755, 735, 88, 93, 243, 227, 217, 201, 210);
INSERT INTO public.benchmark VALUES (0, 41, 820, 14910, 320, 12120, 870, 780, 810, 782, 92, 98, 257, 241, 231, 211, 217);
INSERT INTO public.benchmark VALUES (0, 42, 770, 14120, 285, 11320, 820, 730, 760, 742, 87, 93, 244, 229, 219, 203, 210);
INSERT INTO public.benchmark VALUES (0, 43, 805, 14770, 315, 12030, 855, 765, 795, 773, 91, 96, 254, 237, 227, 209, 214);
INSERT INTO public.benchmark VALUES (0, 44, 830, 15020, 326, 12300, 878, 785, 812, 788, 94, 100, 259, 243, 234, 214, 220);
INSERT INTO public.benchmark VALUES (0, 45, 765, 13970, 276, 11190, 810, 720, 748, 728, 86, 92, 239, 224, 212, 198, 207);
INSERT INTO public.benchmark VALUES (0, 46, 810, 14730, 310, 12010, 860, 770, 790, 770, 91, 97, 255, 240, 230, 210, 215);
INSERT INTO public.benchmark VALUES (0, 47, 790, 14320, 290, 11500, 830, 740, 770, 750, 89, 95, 248, 238, 220, 205, 210);
INSERT INTO public.benchmark VALUES (0, 48, 820, 14970, 320, 12130, 870, 790, 810, 785, 93, 100, 260, 245, 235, 215, 218);
INSERT INTO public.benchmark VALUES (0, 49, 765, 14030, 280, 11240, 810, 720, 750, 730, 87, 92, 240, 225, 215, 200, 208);
INSERT INTO public.benchmark VALUES (0, 50, 800, 14570, 305, 11780, 840, 750, 780, 760, 91, 97, 252, 235, 225, 208, 212);
INSERT INTO public.benchmark VALUES (0, 51, 815, 14850, 308, 12070, 865, 775, 800, 770, 92, 97, 256, 239, 229, 211, 217);
INSERT INTO public.benchmark VALUES (0, 52, 770, 14090, 284, 11300, 820, 730, 755, 735, 87, 92, 243, 228, 218, 202, 209);
INSERT INTO public.benchmark VALUES (0, 53, 785, 14420, 297, 11630, 835, 745, 770, 750, 89, 94, 248, 232, 222, 204, 211);
INSERT INTO public.benchmark VALUES (0, 54, 840, 15190, 330, 12490, 890, 800, 825, 795, 96, 102, 265, 250, 240, 218, 222);
INSERT INTO public.benchmark VALUES (0, 55, 760, 13820, 273, 11090, 805, 715, 740, 720, 85, 90, 236, 221, 210, 195, 206);
INSERT INTO public.benchmark VALUES (0, 56, 825, 15030, 325, 12380, 880, 790, 815, 785, 94, 99, 261, 244, 235, 216, 221);
INSERT INTO public.benchmark VALUES (0, 57, 790, 14380, 295, 11590, 835, 740, 765, 745, 88, 93, 246, 230, 220, 203, 210);
INSERT INTO public.benchmark VALUES (0, 58, 810, 14620, 310, 11910, 855, 765, 790, 770, 91, 96, 254, 237, 227, 209, 215);
INSERT INTO public.benchmark VALUES (0, 59, 765, 13930, 275, 11140, 810, 720, 748, 728, 86, 91, 239, 224, 213, 197, 207);
INSERT INTO public.benchmark VALUES (0, 60, 835, 15160, 327, 12450, 888, 798, 822, 792, 95, 101, 264, 248, 239, 217, 221);
INSERT INTO public.benchmark VALUES (0, 61, 780, 14190, 289, 11290, 825, 730, 755, 735, 88, 93, 243, 227, 217, 201, 210);
INSERT INTO public.benchmark VALUES (0, 62, 800, 14510, 304, 11710, 845, 755, 780, 760, 90, 95, 251, 234, 224, 206, 213);
INSERT INTO public.benchmark VALUES (0, 63, 820, 14920, 319, 12110, 870, 780, 805, 778, 92, 98, 258, 241, 231, 212, 217);
INSERT INTO public.benchmark VALUES (0, 64, 775, 14020, 282, 11210, 818, 728, 750, 732, 87, 91, 241, 225, 216, 200, 208);
INSERT INTO public.benchmark VALUES (0, 65, 810, 14690, 311, 11950, 858, 770, 792, 769, 91, 96, 255, 238, 228, 209, 214);
INSERT INTO public.benchmark VALUES (0, 66, 830, 15070, 326, 12360, 880, 790, 814, 784, 94, 99, 260, 243, 233, 215, 219);
INSERT INTO public.benchmark VALUES (0, 67, 785, 14400, 296, 11610, 834, 742, 768, 748, 89, 94, 247, 231, 221, 204, 211);
INSERT INTO public.benchmark VALUES (0, 68, 845, 15250, 332, 12510, 893, 803, 827, 797, 96, 102, 266, 251, 241, 219, 223);
INSERT INTO public.benchmark VALUES (0, 69, 765, 13910, 274, 11120, 807, 717, 744, 724, 86, 90, 238, 223, 212, 196, 205);
INSERT INTO public.benchmark VALUES (0, 70, 800, 14540, 306, 11750, 842, 752, 782, 762, 90, 96, 252, 235, 225, 207, 213);
INSERT INTO public.benchmark VALUES (0, 71, 825, 15040, 324, 12390, 879, 789, 816, 786, 94, 100, 261, 244, 234, 216, 221);
INSERT INTO public.benchmark VALUES (0, 72, 780, 14140, 288, 11280, 822, 732, 756, 736, 88, 93, 244, 227, 217, 202, 210);
INSERT INTO public.benchmark VALUES (0, 73, 795, 14490, 298, 11670, 838, 748, 772, 752, 89, 95, 249, 232, 222, 205, 212);
INSERT INTO public.benchmark VALUES (0, 74, 840, 15180, 331, 12480, 891, 801, 826, 796, 96, 102, 265, 249, 240, 218, 222);
INSERT INTO public.benchmark VALUES (0, 75, 760, 13810, 272, 11080, 804, 714, 738, 718, 84, 89, 234, 220, 209, 194, 204);
INSERT INTO public.benchmark VALUES (0, 76, 1600, 15800, 2600, 13000, 1390, 1360, 1250, 1290, 130, 137, 342, 313, 303, 146, 118);
INSERT INTO public.benchmark VALUES (0, 77, 1650, 16200, 2680, 13400, 1415, 1380, 1275, 1315, 132, 140, 350, 320, 310, 150, 124);
INSERT INTO public.benchmark VALUES (0, 78, 1690, 16600, 2750, 13800, 1440, 1400, 1300, 1335, 134, 143, 358, 326, 316, 153, 127);
INSERT INTO public.benchmark VALUES (0, 79, 1620, 16000, 2620, 13150, 1395, 1370, 1260, 1300, 130, 136, 344, 316, 306, 147, 121);
INSERT INTO public.benchmark VALUES (0, 80, 1550, 15400, 2480, 12600, 1360, 1340, 1230, 1260, 127, 133, 337, 308, 298, 143, 113);
INSERT INTO public.benchmark VALUES (0, 81, 1580, 15600, 2550, 12850, 1375, 1350, 1245, 1275, 128, 135, 340, 310, 300, 145, 115);
INSERT INTO public.benchmark VALUES (0, 82, 1700, 16800, 2780, 14100, 1450, 1420, 1320, 1360, 135, 145, 364, 335, 325, 158, 132);
INSERT INTO public.benchmark VALUES (0, 83, 1630, 15900, 2650, 13300, 1400, 1375, 1270, 1310, 131, 138, 346, 317, 307, 149, 122);
INSERT INTO public.benchmark VALUES (0, 84, 1510, 14900, 2400, 12000, 1320, 1300, 1180, 1210, 122, 128, 326, 295, 285, 138, 105);
INSERT INTO public.benchmark VALUES (0, 85, 1590, 15650, 2580, 12950, 1380, 1360, 1250, 1280, 129, 135, 343, 314, 304, 146, 119);
INSERT INTO public.benchmark VALUES (0, 86, 1720, 17100, 2840, 14350, 1460, 1430, 1330, 1370, 136, 146, 367, 338, 328, 160, 134);
INSERT INTO public.benchmark VALUES (0, 87, 1670, 16300, 2720, 13550, 1420, 1390, 1290, 1320, 133, 141, 354, 324, 314, 152, 126);
INSERT INTO public.benchmark VALUES (0, 88, 1640, 16100, 2680, 13350, 1400, 1370, 1275, 1305, 131, 138, 348, 319, 309, 148, 123);
INSERT INTO public.benchmark VALUES (0, 89, 1570, 15500, 2520, 12700, 1370, 1345, 1240, 1270, 128, 134, 339, 309, 299, 144, 116);
INSERT INTO public.benchmark VALUES (0, 90, 1680, 16500, 2760, 13750, 1430, 1400, 1300, 1340, 134, 143, 359, 329, 319, 154, 130);
INSERT INTO public.benchmark VALUES (0, 91, 1610, 15850, 2630, 13050, 1390, 1365, 1255, 1295, 130, 137, 345, 315, 305, 147, 120);
INSERT INTO public.benchmark VALUES (0, 92, 1500, 14800, 2380, 11900, 1315, 1290, 1170, 1200, 121, 127, 324, 294, 284, 137, 104);
INSERT INTO public.benchmark VALUES (0, 93, 1530, 15100, 2450, 12100, 1340, 1320, 1200, 1230, 124, 130, 332, 302, 292, 141, 109);
INSERT INTO public.benchmark VALUES (0, 94, 1600, 15700, 2600, 13000, 1390, 1360, 1250, 1290, 130, 137, 342, 313, 303, 146, 118);
INSERT INTO public.benchmark VALUES (0, 95, 1620, 15900, 2640, 13150, 1400, 1375, 1265, 1300, 131, 138, 346, 317, 307, 148, 122);
INSERT INTO public.benchmark VALUES (0, 96, 1740, 17300, 2920, 14650, 1480, 1455, 1340, 1380, 138, 148, 372, 342, 332, 162, 136);
INSERT INTO public.benchmark VALUES (0, 97, 1710, 17000, 2850, 14300, 1460, 1430, 1320, 1355, 135, 144, 364, 334, 324, 158, 131);
INSERT INTO public.benchmark VALUES (0, 98, 1590, 15600, 2570, 12900, 1380, 1350, 1240, 1275, 128, 134, 341, 311, 301, 145, 114);
INSERT INTO public.benchmark VALUES (0, 99, 1510, 14850, 2440, 12200, 1340, 1320, 1210, 1240, 125, 131, 334, 304, 294, 142, 110);
INSERT INTO public.benchmark VALUES (0, 100, 1660, 16300, 2740, 13600, 1420, 1390, 1295, 1325, 133, 141, 356, 327, 317, 151, 125);
INSERT INTO public.benchmark VALUES (0, 101, 1530, 15050, 2500, 12500, 1340, 1320, 1200, 1235, 126, 132, 330, 300, 290, 140, 111);
INSERT INTO public.benchmark VALUES (0, 102, 1640, 16100, 2680, 13350, 1400, 1375, 1275, 1305, 131, 138, 348, 319, 309, 148, 123);
INSERT INTO public.benchmark VALUES (0, 103, 1710, 16850, 2840, 14150, 1450, 1425, 1315, 1350, 135, 144, 364, 334, 324, 158, 130);
INSERT INTO public.benchmark VALUES (0, 104, 1650, 16150, 2720, 13550, 1420, 1390, 1280, 1315, 132, 139, 351, 321, 311, 150, 126);
INSERT INTO public.benchmark VALUES (0, 105, 1620, 15900, 2650, 13150, 1400, 1370, 1260, 1295, 130, 137, 345, 315, 305, 147, 120);
INSERT INTO public.benchmark VALUES (0, 106, 1670, 16400, 2750, 13700, 1430, 1400, 1295, 1330, 134, 141, 355, 325, 315, 153, 128);
INSERT INTO public.benchmark VALUES (0, 107, 1520, 14950, 2450, 12250, 1335, 1310, 1200, 1230, 125, 131, 333, 303, 293, 141, 110);
INSERT INTO public.benchmark VALUES (0, 108, 1680, 16550, 2780, 13950, 1440, 1410, 1305, 1340, 135, 143, 360, 330, 320, 155, 131);
INSERT INTO public.benchmark VALUES (0, 109, 1630, 16000, 2680, 13300, 1410, 1385, 1275, 1310, 132, 139, 349, 319, 309, 149, 124);
INSERT INTO public.benchmark VALUES (0, 110, 1540, 15100, 2500, 12550, 1350, 1330, 1210, 1245, 127, 133, 336, 306, 296, 143, 112);
INSERT INTO public.benchmark VALUES (0, 111, 1600, 15750, 2600, 12900, 1380, 1355, 1240, 1275, 129, 135, 340, 310, 300, 145, 117);
INSERT INTO public.benchmark VALUES (0, 112, 1590, 15600, 2570, 12800, 1370, 1340, 1235, 1270, 128, 134, 338, 308, 298, 144, 116);
INSERT INTO public.benchmark VALUES (0, 113, 1620, 15950, 2630, 13100, 1390, 1365, 1250, 1290, 130, 137, 345, 315, 305, 147, 121);
INSERT INTO public.benchmark VALUES (0, 114, 1610, 15800, 2610, 13050, 1385, 1360, 1245, 1280, 129, 136, 342, 312, 302, 146, 119);
INSERT INTO public.benchmark VALUES (0, 115, 1630, 16050, 2650, 13200, 1400, 1370, 1265, 1300, 131, 138, 347, 317, 307, 149, 123);
INSERT INTO public.benchmark VALUES (0, 116, 1640, 16100, 2680, 13350, 1410, 1380, 1270, 1305, 132, 139, 348, 318, 308, 149, 124);
INSERT INTO public.benchmark VALUES (0, 117, 1660, 16300, 2720, 13500, 1425, 1395, 1280, 1320, 133, 140, 352, 322, 312, 151, 127);
INSERT INTO public.benchmark VALUES (0, 118, 1580, 15500, 2550, 12750, 1365, 1340, 1225, 1260, 128, 134, 338, 307, 297, 144, 115);
INSERT INTO public.benchmark VALUES (0, 119, 1690, 16600, 2800, 14000, 1450, 1420, 1315, 1350, 136, 144, 362, 332, 322, 156, 132);
INSERT INTO public.benchmark VALUES (0, 120, 1650, 16200, 2700, 13450, 1420, 1390, 1285, 1320, 134, 141, 356, 326, 316, 153, 127);
INSERT INTO public.benchmark VALUES (0, 121, 1670, 16450, 2740, 13650, 1435, 1400, 1290, 1330, 134, 141, 357, 327, 317, 154, 128);
INSERT INTO public.benchmark VALUES (0, 122, 1700, 16750, 2820, 13900, 1450, 1425, 1310, 1350, 135, 144, 363, 333, 323, 157, 131);
INSERT INTO public.benchmark VALUES (0, 123, 1720, 16950, 2860, 14150, 1465, 1440, 1325, 1360, 137, 145, 365, 335, 325, 158, 133);
INSERT INTO public.benchmark VALUES (0, 124, 1690, 16600, 2800, 13950, 1450, 1415, 1305, 1340, 135, 143, 360, 330, 320, 155, 130);
INSERT INTO public.benchmark VALUES (0, 125, 1600, 15700, 2590, 12950, 1380, 1350, 1240, 1280, 129, 135, 340, 309, 299, 145, 117);
INSERT INTO public.benchmark VALUES (0, 126, 1650, 16500, 2700, 13500, 1420, 1380, 1280, 1320, 132, 140, 350, 320, 310, 150, 125);
INSERT INTO public.benchmark VALUES (0, 127, 1680, 17000, 2800, 14000, 1440, 1410, 1300, 1350, 134, 143, 360, 330, 320, 155, 130);
INSERT INTO public.benchmark VALUES (0, 128, 1620, 16200, 2650, 13200, 1400, 1370, 1260, 1300, 130, 138, 345, 315, 305, 148, 120);
INSERT INTO public.benchmark VALUES (0, 129, 1580, 15800, 2550, 12800, 1380, 1350, 1240, 1270, 128, 135, 340, 310, 300, 145, 115);
INSERT INTO public.benchmark VALUES (0, 130, 1700, 17200, 2850, 14200, 1450, 1420, 1320, 1360, 135, 144, 365, 335, 325, 158, 132);
INSERT INTO public.benchmark VALUES (0, 131, 1600, 16000, 2600, 13000, 1390, 1360, 1250, 1290, 129, 137, 342, 313, 303, 146, 118);
INSERT INTO public.benchmark VALUES (0, 132, 1690, 16900, 2780, 13900, 1445, 1405, 1310, 1345, 133, 141, 355, 325, 315, 153, 127);
INSERT INTO public.benchmark VALUES (0, 133, 1720, 17400, 2900, 14400, 1460, 1430, 1330, 1370, 136, 146, 368, 338, 328, 160, 134);
INSERT INTO public.benchmark VALUES (0, 134, 1550, 15500, 2500, 12500, 1360, 1340, 1220, 1250, 126, 133, 336, 307, 298, 142, 110);
INSERT INTO public.benchmark VALUES (0, 135, 1630, 16400, 2680, 13300, 1410, 1385, 1270, 1315, 131, 139, 347, 318, 308, 149, 123);
INSERT INTO public.benchmark VALUES (0, 136, 1510, 15000, 2450, 12200, 1345, 1320, 1200, 1230, 124, 130, 330, 303, 293, 140, 108);
INSERT INTO public.benchmark VALUES (0, 137, 1740, 17600, 2920, 14600, 1475, 1445, 1340, 1380, 138, 148, 372, 342, 332, 162, 136);
INSERT INTO public.benchmark VALUES (0, 138, 1710, 17300, 2880, 14300, 1455, 1425, 1325, 1365, 137, 145, 366, 336, 326, 159, 133);
INSERT INTO public.benchmark VALUES (0, 139, 1560, 15600, 2520, 12600, 1370, 1345, 1235, 1265, 127, 134, 338, 309, 299, 143, 112);
INSERT INTO public.benchmark VALUES (0, 140, 1640, 16600, 2700, 13550, 1420, 1390, 1280, 1320, 132, 140, 350, 320, 310, 150, 125);
INSERT INTO public.benchmark VALUES (0, 141, 1490, 14800, 2400, 12000, 1320, 1300, 1180, 1210, 122, 128, 326, 295, 285, 138, 105);
INSERT INTO public.benchmark VALUES (0, 142, 1730, 17500, 2910, 14500, 1470, 1440, 1335, 1375, 137, 147, 370, 340, 330, 161, 135);
INSERT INTO public.benchmark VALUES (0, 143, 1650, 16700, 2730, 13600, 1430, 1400, 1290, 1330, 133, 141, 355, 325, 315, 152, 128);
INSERT INTO public.benchmark VALUES (0, 144, 1690, 17000, 2800, 13950, 1440, 1415, 1305, 1350, 134, 142, 358, 328, 318, 154, 129);
INSERT INTO public.benchmark VALUES (0, 145, 1500, 14900, 2430, 12150, 1330, 1310, 1190, 1225, 123, 129, 328, 298, 288, 139, 107);
INSERT INTO public.benchmark VALUES (0, 146, 1620, 16100, 2620, 13100, 1395, 1370, 1265, 1305, 130, 136, 344, 316, 306, 147, 121);
INSERT INTO public.benchmark VALUES (0, 147, 1750, 17700, 2940, 14700, 1480, 1450, 1350, 1390, 139, 149, 374, 344, 334, 163, 137);
INSERT INTO public.benchmark VALUES (0, 148, 1590, 15900, 2580, 12900, 1385, 1355, 1245, 1285, 129, 135, 342, 312, 302, 144, 117);
INSERT INTO public.benchmark VALUES (0, 149, 1520, 15100, 2470, 12350, 1350, 1330, 1210, 1240, 125, 131, 334, 305, 295, 141, 111);
INSERT INTO public.benchmark VALUES (0, 150, 1660, 16800, 2750, 13700, 1435, 1410, 1295, 1340, 134, 143, 360, 330, 320, 156, 131);
INSERT INTO public.benchmark VALUES (0, 151, 1700, 16700, 2810, 13900, 1450, 1420, 1305, 1345, 135, 143, 361, 331, 321, 156, 130);
INSERT INTO public.benchmark VALUES (0, 152, 1610, 15800, 2610, 13050, 1385, 1355, 1245, 1285, 129, 136, 343, 313, 303, 146, 118);
INSERT INTO public.benchmark VALUES (0, 153, 1625, 15950, 2640, 13200, 1395, 1370, 1255, 1295, 130, 137, 346, 316, 306, 148, 121);
INSERT INTO public.benchmark VALUES (0, 154, 1600, 15700, 2590, 12950, 1375, 1350, 1240, 1275, 128, 134, 340, 309, 299, 145, 117);
INSERT INTO public.benchmark VALUES (0, 155, 1580, 15450, 2540, 12700, 1360, 1335, 1225, 1255, 127, 132, 336, 305, 295, 143, 114);
INSERT INTO public.benchmark VALUES (0, 156, 1710, 16800, 2840, 14150, 1460, 1430, 1320, 1360, 136, 144, 364, 334, 324, 157, 132);
INSERT INTO public.benchmark VALUES (0, 157, 1635, 16100, 2680, 13400, 1410, 1385, 1275, 1310, 132, 139, 349, 319, 309, 149, 124);
INSERT INTO public.benchmark VALUES (0, 158, 1690, 16550, 2780, 13950, 1445, 1410, 1300, 1340, 135, 143, 360, 330, 320, 155, 131);
INSERT INTO public.benchmark VALUES (0, 159, 1600, 15700, 2590, 12950, 1375, 1350, 1240, 1280, 129, 135, 340, 310, 300, 145, 117);
INSERT INTO public.benchmark VALUES (0, 160, 1670, 16450, 2740, 13650, 1435, 1405, 1290, 1335, 134, 142, 357, 327, 317, 154, 128);
INSERT INTO public.benchmark VALUES (0, 161, 1630, 16000, 2670, 13350, 1405, 1375, 1265, 1300, 132, 138, 348, 318, 308, 149, 123);
INSERT INTO public.benchmark VALUES (0, 162, 1550, 15200, 2520, 12600, 1345, 1325, 1215, 1240, 126, 131, 332, 302, 292, 140, 111);
INSERT INTO public.benchmark VALUES (0, 163, 1710, 16850, 2840, 14150, 1460, 1435, 1320, 1360, 136, 145, 365, 335, 325, 157, 132);
INSERT INTO public.benchmark VALUES (0, 164, 1720, 16950, 2860, 14200, 1465, 1440, 1325, 1365, 137, 145, 366, 336, 326, 158, 133);
INSERT INTO public.benchmark VALUES (0, 165, 1620, 15900, 2630, 13150, 1390, 1360, 1250, 1285, 130, 136, 345, 314, 304, 147, 120);
INSERT INTO public.benchmark VALUES (0, 166, 1650, 16200, 2690, 13400, 1410, 1380, 1270, 1305, 132, 139, 349, 319, 309, 149, 124);
INSERT INTO public.benchmark VALUES (0, 167, 1585, 15500, 2550, 12750, 1365, 1345, 1225, 1265, 128, 134, 338, 307, 297, 144, 115);
INSERT INTO public.benchmark VALUES (0, 168, 1700, 16700, 2810, 13900, 1450, 1420, 1305, 1345, 135, 143, 361, 331, 321, 156, 130);
INSERT INTO public.benchmark VALUES (0, 169, 1590, 15600, 2570, 12800, 1370, 1340, 1235, 1270, 128, 134, 338, 308, 298, 144, 116);
INSERT INTO public.benchmark VALUES (0, 170, 1570, 15400, 2530, 12650, 1355, 1330, 1220, 1255, 127, 132, 335, 304, 294, 142, 114);
INSERT INTO public.benchmark VALUES (0, 171, 1565, 15350, 2520, 12600, 1345, 1320, 1215, 1245, 126, 131, 333, 303, 293, 141, 112);
INSERT INTO public.benchmark VALUES (0, 172, 1685, 16500, 2770, 13900, 1440, 1410, 1295, 1330, 134, 142, 355, 325, 315, 152, 127);
INSERT INTO public.benchmark VALUES (0, 173, 1600, 15700, 2590, 12950, 1375, 1350, 1240, 1280, 129, 135, 340, 309, 299, 145, 117);
INSERT INTO public.benchmark VALUES (0, 174, 1645, 16150, 2690, 13400, 1410, 1385, 1275, 1310, 132, 139, 349, 319, 309, 149, 124);
INSERT INTO public.benchmark VALUES (0, 175, 1690, 16600, 2800, 13950, 1450, 1415, 1305, 1340, 135, 143, 360, 330, 320, 155, 130);
INSERT INTO public.benchmark VALUES (0, 176, 1610, 15800, 2610, 13050, 1385, 1360, 1245, 1285, 129, 136, 343, 313, 303, 146, 118);
INSERT INTO public.benchmark VALUES (0, 177, 1605, 15750, 2600, 13000, 1380, 1355, 1240, 1280, 129, 135, 342, 312, 302, 146, 117);
INSERT INTO public.benchmark VALUES (0, 178, 1580, 15500, 2550, 12750, 1365, 1340, 1225, 1265, 127, 133, 337, 306, 296, 144, 114);
INSERT INTO public.benchmark VALUES (0, 179, 1700, 16700, 2810, 13900, 1450, 1420, 1305, 1345, 135, 143, 361, 331, 321, 156, 130);
INSERT INTO public.benchmark VALUES (0, 180, 1650, 16200, 2700, 13450, 1420, 1390, 1285, 1320, 134, 140, 356, 326, 316, 153, 127);
INSERT INTO public.benchmark VALUES (0, 181, 1695, 16650, 2820, 13950, 1450, 1420, 1305, 1345, 135, 143, 361, 331, 321, 156, 131);
INSERT INTO public.benchmark VALUES (0, 182, 1600, 15700, 2590, 12950, 1375, 1350, 1240, 1280, 129, 135, 340, 309, 299, 145, 117);
INSERT INTO public.benchmark VALUES (0, 183, 1580, 15500, 2540, 12750, 1360, 1335, 1225, 1255, 127, 132, 336, 305, 295, 143, 114);
INSERT INTO public.benchmark VALUES (0, 184, 1710, 16800, 2840, 14150, 1460, 1430, 1320, 1360, 136, 144, 364, 334, 324, 157, 132);
INSERT INTO public.benchmark VALUES (0, 185, 1590, 15600, 2570, 12800, 1370, 1340, 1235, 1270, 128, 134, 338, 308, 298, 144, 116);
INSERT INTO public.benchmark VALUES (0, 186, 1600, 15700, 2590, 12950, 1380, 1350, 1240, 1280, 129, 135, 340, 309, 299, 145, 117);
INSERT INTO public.benchmark VALUES (0, 187, 1640, 16100, 2680, 13350, 1410, 1385, 1270, 1305, 132, 139, 348, 318, 308, 149, 123);
INSERT INTO public.benchmark VALUES (0, 188, 1585, 15500, 2550, 12750, 1365, 1345, 1225, 1265, 128, 134, 338, 307, 297, 144, 115);
INSERT INTO public.benchmark VALUES (0, 189, 1710, 16800, 2840, 14150, 1460, 1430, 1320, 1360, 136, 144, 364, 334, 324, 157, 132);
INSERT INTO public.benchmark VALUES (0, 190, 1560, 15300, 2510, 12550, 1340, 1315, 1210, 1235, 126, 130, 330, 300, 290, 140, 110);
INSERT INTO public.benchmark VALUES (0, 191, 1730, 17050, 2870, 14250, 1470, 1445, 1330, 1370, 137, 145, 367, 337, 327, 158, 134);
INSERT INTO public.benchmark VALUES (0, 192, 1580, 15500, 2540, 12750, 1360, 1335, 1225, 1255, 127, 132, 336, 305, 295, 143, 114);
INSERT INTO public.benchmark VALUES (0, 193, 1600, 15700, 2590, 12950, 1380, 1350, 1240, 1280, 129, 135, 340, 309, 299, 145, 117);
INSERT INTO public.benchmark VALUES (0, 194, 1590, 15600, 2570, 12800, 1370, 1340, 1235, 1270, 128, 134, 338, 308, 298, 144, 116);
INSERT INTO public.benchmark VALUES (0, 195, 1575, 15450, 2530, 12650, 1355, 1330, 1220, 1260, 127, 132, 335, 304, 294, 142, 114);
INSERT INTO public.benchmark VALUES (0, 196, 1650, 16200, 2700, 13450, 1420, 1390, 1285, 1320, 134, 140, 356, 326, 316, 153, 127);
INSERT INTO public.benchmark VALUES (0, 197, 1660, 16300, 2720, 13500, 1430, 1400, 1290, 1330, 134, 141, 357, 327, 317, 154, 128);
INSERT INTO public.benchmark VALUES (0, 198, 1670, 16400, 2750, 13600, 1435, 1405, 1295, 1335, 134, 141, 357, 327, 317, 154, 129);
INSERT INTO public.benchmark VALUES (0, 199, 1550, 15200, 2510, 12550, 1345, 1320, 1215, 1240, 126, 130, 330, 300, 290, 140, 111);
INSERT INTO public.benchmark VALUES (0, 200, 1700, 16700, 2810, 13900, 1450, 1420, 1305, 1345, 135, 143, 361, 331, 321, 156, 130);


--
-- Data for Name: casing; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.casing VALUES (1, 'NZXT', 'Casing-328', 'ATX', 193, 379, 163, '280mm', NULL, NULL, 3, false, true, 'Silver', 282.08, 'https://example.com/casing');
INSERT INTO public.casing VALUES (2, 'NZXT', 'Casing-895', 'Micro-ATX', 185, 318, 151, '360mm', '360mm', '360mm', 1, false, true, 'Black', 267.96, 'https://example.com/casing');
INSERT INTO public.casing VALUES (3, 'Lian Li', 'Casing-375', 'ATX', 198, 317, 176, '240mm', '360mm', '240mm', 4, false, false, 'White', 75.51, 'https://example.com/casing');
INSERT INTO public.casing VALUES (4, 'Corsair', 'Casing-762', 'Micro-ATX', 198, 307, 177, '240mm', NULL, '280mm', 3, false, true, 'White', 195.45, 'https://example.com/casing');
INSERT INTO public.casing VALUES (5, 'Corsair', 'Casing-923', 'Mini-ITX', 199, 290, 175, '240mm', '280mm', '240mm', 1, true, false, 'Silver', 93.59, 'https://example.com/casing');
INSERT INTO public.casing VALUES (6, 'Fractal Design', 'Casing-977', 'Mini-ITX', 205, 353, 150, '280mm', NULL, NULL, 2, true, false, 'Silver', 238.64, 'https://example.com/casing');
INSERT INTO public.casing VALUES (7, 'Cooler Master', 'Casing-472', 'Mini-ITX', 209, 395, 176, '240mm', '240mm', '240mm', 3, true, false, 'Silver', 222.39, 'https://example.com/casing');
INSERT INTO public.casing VALUES (8, 'Fractal Design', 'Casing-327', 'Mini-ITX', 169, 379, 168, '240mm', '360mm', NULL, 2, true, false, 'Black', 59, 'https://example.com/casing');
INSERT INTO public.casing VALUES (9, 'Fractal Design', 'Casing-191', 'Micro-ATX', 199, 315, 178, '240mm', NULL, '280mm', 2, false, true, 'Silver', 92.35, 'https://example.com/casing');
INSERT INTO public.casing VALUES (10, 'Lian Li', 'Casing-301', 'ATX', 206, 370, 165, '360mm', NULL, '360mm', 2, true, true, 'Silver', 83.24, 'https://example.com/casing');
INSERT INTO public.casing VALUES (11, 'Corsair', 'Casing-416', 'Mini-ITX', 168, 301, 172, '360mm', '280mm', '240mm', 1, false, false, 'Silver', 89.72, 'https://example.com/casing');
INSERT INTO public.casing VALUES (12, 'Lian Li', 'Casing-849', 'Micro-ATX', 213, 327, 165, '280mm', NULL, '280mm', 2, true, true, 'White', 157.16, 'https://example.com/casing');
INSERT INTO public.casing VALUES (13, 'Corsair', 'Casing-595', 'Micro-ATX', 192, 354, 176, '240mm', '240mm', NULL, 1, false, false, 'White', 69.38, 'https://example.com/casing');
INSERT INTO public.casing VALUES (14, 'Fractal Design', 'Casing-271', 'Micro-ATX', 215, 341, 174, NULL, '240mm', NULL, 4, false, true, 'Black', 214.6, 'https://example.com/casing');
INSERT INTO public.casing VALUES (15, 'Corsair', 'Casing-241', 'ATX', 194, 324, 173, NULL, '360mm', NULL, 2, false, true, 'Black', 186.1, 'https://example.com/casing');
INSERT INTO public.casing VALUES (16, 'Fractal Design', 'Casing-691', 'ATX', 220, 350, 159, NULL, '280mm', '360mm', 3, true, true, 'Black', 161.28, 'https://example.com/casing');
INSERT INTO public.casing VALUES (17, 'Lian Li', 'Casing-487', 'Micro-ATX', 172, 367, 173, '240mm', '360mm', '280mm', 2, false, true, 'Silver', 223.26, 'https://example.com/casing');
INSERT INTO public.casing VALUES (18, 'Fractal Design', 'Casing-669', 'Micro-ATX', 193, 356, 180, '360mm', '240mm', '360mm', 4, false, false, 'Silver', 135.07, 'https://example.com/casing');
INSERT INTO public.casing VALUES (19, 'Corsair', 'Casing-528', 'Micro-ATX', 168, 318, 173, '240mm', '240mm', NULL, 2, false, true, 'White', 67.15, 'https://example.com/casing');
INSERT INTO public.casing VALUES (20, 'Lian Li', 'Casing-900', 'Mini-ITX', 198, 360, 153, '280mm', '240mm', NULL, 3, false, true, 'Black', 60.44, 'https://example.com/casing');
INSERT INTO public.casing VALUES (21, 'Corsair', 'Casing-239', 'Mini-ITX', 187, 322, 161, '240mm', '360mm', '280mm', 3, false, true, 'Black', 208.39, 'https://example.com/casing');
INSERT INTO public.casing VALUES (22, 'Lian Li', 'Casing-305', 'Micro-ATX', 219, 328, 177, '360mm', '240mm', '280mm', 1, false, false, 'Black', 281.16, 'https://example.com/casing');
INSERT INTO public.casing VALUES (23, 'Corsair', 'Casing-329', 'Mini-ITX', 173, 362, 163, NULL, NULL, '360mm', 1, true, true, 'Black', 259.97, 'https://example.com/casing');
INSERT INTO public.casing VALUES (24, 'NZXT', 'Casing-433', 'Mini-ITX', 199, 351, 166, '280mm', '280mm', NULL, 1, true, true, 'White', 83.84, 'https://example.com/casing');
INSERT INTO public.casing VALUES (25, 'Lian Li', 'Casing-474', 'Micro-ATX', 207, 382, 164, '360mm', '240mm', NULL, 1, true, true, 'Silver', 284.61, 'https://example.com/casing');
INSERT INTO public.casing VALUES (26, 'Corsair', 'Casing-552', 'Micro-ATX', 220, 281, 178, NULL, NULL, NULL, 2, true, false, 'Black', 184.49, 'https://example.com/casing');
INSERT INTO public.casing VALUES (27, 'Corsair', 'Casing-625', 'ATX', 194, 366, 151, '280mm', '360mm', NULL, 3, false, false, 'Silver', 179.54, 'https://example.com/casing');
INSERT INTO public.casing VALUES (28, 'Fractal Design', 'Casing-757', 'Mini-ITX', 195, 301, 174, NULL, '240mm', NULL, 3, true, true, 'Silver', 241.07, 'https://example.com/casing');
INSERT INTO public.casing VALUES (29, 'Corsair', 'Casing-234', 'Mini-ITX', 174, 387, 170, NULL, '280mm', '280mm', 4, true, false, 'Black', 150.44, 'https://example.com/casing');
INSERT INTO public.casing VALUES (30, 'Cooler Master', 'Casing-292', 'Mini-ITX', 210, 359, 173, '240mm', '360mm', '280mm', 3, true, false, 'White', 246.38, 'https://example.com/casing');
INSERT INTO public.casing VALUES (31, 'Corsair', 'Casing-343', 'ATX', 168, 397, 166, '280mm', '280mm', '360mm', 2, true, false, 'Black', 121.68, 'https://example.com/casing');
INSERT INTO public.casing VALUES (32, 'Fractal Design', 'Casing-662', 'ATX', 213, 326, 164, '280mm', '280mm', '240mm', 1, false, false, 'White', 121.74, 'https://example.com/casing');
INSERT INTO public.casing VALUES (33, 'NZXT', 'Casing-533', 'Mini-ITX', 204, 303, 165, NULL, '240mm', '360mm', 4, false, false, 'Silver', 147.84, 'https://example.com/casing');
INSERT INTO public.casing VALUES (34, 'NZXT', 'Casing-835', 'Micro-ATX', 185, 308, 167, '360mm', '360mm', NULL, 1, false, true, 'Silver', 178.9, 'https://example.com/casing');
INSERT INTO public.casing VALUES (35, 'NZXT', 'Casing-834', 'Mini-ITX', 184, 375, 163, '360mm', '280mm', '240mm', 2, true, false, 'Black', 112.59, 'https://example.com/casing');
INSERT INTO public.casing VALUES (36, 'Lian Li', 'Casing-652', 'Mini-ITX', 165, 317, 170, '240mm', '240mm', '280mm', 4, true, false, 'Black', 281.84, 'https://example.com/casing');
INSERT INTO public.casing VALUES (37, 'Cooler Master', 'Casing-853', 'Mini-ITX', 174, 289, 175, '240mm', NULL, '360mm', 4, true, true, 'Black', 295.4, 'https://example.com/casing');
INSERT INTO public.casing VALUES (38, 'Fractal Design', 'Casing-526', 'ATX', 160, 290, 178, '360mm', '360mm', '280mm', 2, true, false, 'White', 241.71, 'https://example.com/casing');
INSERT INTO public.casing VALUES (39, 'Fractal Design', 'Casing-773', 'ATX', 214, 367, 160, '280mm', NULL, '280mm', 3, false, true, 'Silver', 232.23, 'https://example.com/casing');
INSERT INTO public.casing VALUES (40, 'Cooler Master', 'Casing-579', 'Micro-ATX', 196, 311, 168, '280mm', '280mm', '280mm', 1, false, true, 'Black', 96.87, 'https://example.com/casing');
INSERT INTO public.casing VALUES (41, 'Cooler Master', 'Casing-480', 'Micro-ATX', 182, 308, 176, NULL, '240mm', '280mm', 2, true, true, 'Silver', 155.65, 'https://example.com/casing');
INSERT INTO public.casing VALUES (42, 'NZXT', 'Casing-947', 'ATX', 161, 352, 156, '240mm', NULL, '240mm', 1, false, true, 'Silver', 142.8, 'https://example.com/casing');
INSERT INTO public.casing VALUES (43, 'Fractal Design', 'Casing-599', 'Micro-ATX', 185, 347, 171, NULL, '360mm', '240mm', 2, true, false, 'Black', 274.03, 'https://example.com/casing');
INSERT INTO public.casing VALUES (44, 'Corsair', 'Casing-244', 'ATX', 206, 296, 175, '240mm', '360mm', '280mm', 2, true, true, 'White', 67.22, 'https://example.com/casing');
INSERT INTO public.casing VALUES (45, 'Corsair', 'Casing-572', 'Mini-ITX', 166, 323, 175, '280mm', '280mm', '280mm', 4, false, true, 'Silver', 234.72, 'https://example.com/casing');
INSERT INTO public.casing VALUES (46, 'NZXT', 'Casing-661', 'Micro-ATX', 187, 327, 177, '240mm', '240mm', '240mm', 1, false, true, 'White', 187.49, 'https://example.com/casing');
INSERT INTO public.casing VALUES (47, 'Fractal Design', 'Casing-350', 'ATX', 218, 293, 177, '240mm', NULL, '280mm', 1, true, true, 'White', 75, 'https://example.com/casing');
INSERT INTO public.casing VALUES (48, 'Corsair', 'Casing-884', 'Mini-ITX', 214, 307, 176, '280mm', '360mm', '360mm', 4, false, false, 'White', 299.92, 'https://example.com/casing');
INSERT INTO public.casing VALUES (49, 'Fractal Design', 'Casing-713', 'ATX', 165, 350, 167, '360mm', '360mm', '240mm', 4, true, true, 'Silver', 130.36, 'https://example.com/casing');
INSERT INTO public.casing VALUES (50, 'Corsair', 'Casing-425', 'Mini-ITX', 186, 351, 179, '240mm', '280mm', '280mm', 3, false, true, 'Silver', 279.56, 'https://example.com/casing');
INSERT INTO public.casing VALUES (51, 'Lian Li', 'Casing-412', 'Mini-ITX', 189, 333, 153, NULL, NULL, NULL, 2, true, false, 'Silver', 134.52, 'https://example.com/casing');
INSERT INTO public.casing VALUES (52, 'Cooler Master', 'Casing-848', 'ATX', 204, 391, 154, '280mm', '360mm', '280mm', 4, false, true, 'Black', 247.36, 'https://example.com/casing');
INSERT INTO public.casing VALUES (53, 'NZXT', 'Casing-607', 'ATX', 170, 354, 150, NULL, NULL, '360mm', 2, true, true, 'Black', 122.38, 'https://example.com/casing');
INSERT INTO public.casing VALUES (54, 'Corsair', 'Casing-402', 'ATX', 192, 303, 159, '360mm', '360mm', '360mm', 3, true, false, 'Silver', 230.07, 'https://example.com/casing');
INSERT INTO public.casing VALUES (55, 'NZXT', 'Casing-673', 'Mini-ITX', 191, 383, 150, '360mm', '280mm', NULL, 1, true, true, 'Black', 198.14, 'https://example.com/casing');
INSERT INTO public.casing VALUES (56, 'Corsair', 'Casing-117', 'Mini-ITX', 217, 389, 161, '280mm', '360mm', '280mm', 2, true, false, 'Silver', 131.75, 'https://example.com/casing');
INSERT INTO public.casing VALUES (57, 'Corsair', 'Casing-809', 'Micro-ATX', 179, 300, 157, '240mm', '360mm', '240mm', 2, false, true, 'Silver', 130.18, 'https://example.com/casing');
INSERT INTO public.casing VALUES (58, 'Corsair', 'Casing-928', 'ATX', 207, 370, 171, NULL, '240mm', NULL, 4, false, false, 'White', 282.86, 'https://example.com/casing');
INSERT INTO public.casing VALUES (59, 'NZXT', 'Casing-629', 'ATX', 181, 322, 150, '360mm', '240mm', NULL, 2, false, false, 'Silver', 58.18, 'https://example.com/casing');
INSERT INTO public.casing VALUES (60, 'Fractal Design', 'Casing-440', 'Mini-ITX', 207, 306, 166, NULL, '360mm', '280mm', 1, true, false, 'White', 218.09, 'https://example.com/casing');
INSERT INTO public.casing VALUES (61, 'Corsair', 'Casing-437', 'Mini-ITX', 179, 367, 163, '360mm', NULL, '360mm', 2, true, false, 'Silver', 129.49, 'https://example.com/casing');
INSERT INTO public.casing VALUES (62, 'Cooler Master', 'Casing-725', 'Micro-ATX', 173, 332, 163, '240mm', '240mm', NULL, 3, true, true, 'Black', 73.36, 'https://example.com/casing');
INSERT INTO public.casing VALUES (63, 'Cooler Master', 'Casing-291', 'Mini-ITX', 197, 310, 164, '280mm', '360mm', '240mm', 4, false, false, 'Black', 65.09, 'https://example.com/casing');
INSERT INTO public.casing VALUES (64, 'Cooler Master', 'Casing-299', 'Mini-ITX', 163, 389, 166, '240mm', '240mm', '360mm', 1, true, true, 'Black', 183.79, 'https://example.com/casing');
INSERT INTO public.casing VALUES (65, 'Corsair', 'Casing-992', 'ATX', 188, 335, 175, '280mm', '240mm', '240mm', 4, true, true, 'Black', 217.23, 'https://example.com/casing');
INSERT INTO public.casing VALUES (66, 'Lian Li', 'Casing-594', 'Mini-ITX', 208, 382, 159, '280mm', '360mm', '360mm', 4, true, false, 'Silver', 164.32, 'https://example.com/casing');
INSERT INTO public.casing VALUES (67, 'NZXT', 'Casing-981', 'Mini-ITX', 177, 347, 178, '360mm', NULL, NULL, 4, false, false, 'Silver', 263.52, 'https://example.com/casing');
INSERT INTO public.casing VALUES (68, 'Lian Li', 'Casing-467', 'ATX', 197, 378, 165, NULL, NULL, '280mm', 1, true, true, 'Silver', 64.96, 'https://example.com/casing');
INSERT INTO public.casing VALUES (69, 'Lian Li', 'Casing-163', 'Micro-ATX', 213, 330, 160, '240mm', '240mm', '240mm', 2, false, true, 'Silver', 297.9, 'https://example.com/casing');
INSERT INTO public.casing VALUES (70, 'Fractal Design', 'Casing-948', 'ATX', 205, 309, 178, NULL, '280mm', '240mm', 2, true, true, 'Silver', 97.47, 'https://example.com/casing');
INSERT INTO public.casing VALUES (71, 'Fractal Design', 'Casing-807', 'Micro-ATX', 180, 301, 164, NULL, '240mm', '360mm', 1, true, false, 'Black', 62.56, 'https://example.com/casing');
INSERT INTO public.casing VALUES (72, 'Cooler Master', 'Casing-289', 'Micro-ATX', 176, 309, 170, '280mm', '240mm', '240mm', 4, false, true, 'White', 107.56, 'https://example.com/casing');
INSERT INTO public.casing VALUES (73, 'Fractal Design', 'Casing-800', 'ATX', 172, 399, 167, '280mm', '280mm', NULL, 3, false, false, 'White', 158.79, 'https://example.com/casing');
INSERT INTO public.casing VALUES (74, 'NZXT', 'Casing-876', 'Mini-ITX', 220, 400, 173, '360mm', '280mm', '360mm', 4, true, true, 'Black', 107.9, 'https://example.com/casing');
INSERT INTO public.casing VALUES (75, 'Cooler Master', 'Casing-210', 'ATX', 195, 372, 165, '280mm', '240mm', NULL, 1, false, false, 'Silver', 162.88, 'https://example.com/casing');
INSERT INTO public.casing VALUES (76, 'Fractal Design', 'Casing-573', 'Micro-ATX', 179, 346, 152, '240mm', '240mm', NULL, 4, false, false, 'White', 243.41, 'https://example.com/casing');
INSERT INTO public.casing VALUES (77, 'NZXT', 'Casing-826', 'ATX', 205, 392, 152, '360mm', '280mm', '280mm', 1, false, false, 'Black', 91.25, 'https://example.com/casing');
INSERT INTO public.casing VALUES (78, 'Corsair', 'Casing-412', 'ATX', 202, 386, 153, NULL, NULL, NULL, 4, false, true, 'Silver', 140.37, 'https://example.com/casing');
INSERT INTO public.casing VALUES (79, 'NZXT', 'Casing-322', 'Mini-ITX', 216, 336, 160, NULL, '240mm', '360mm', 1, false, true, 'Black', 164.39, 'https://example.com/casing');
INSERT INTO public.casing VALUES (80, 'Corsair', 'Casing-470', 'ATX', 203, 302, 169, '280mm', '280mm', '280mm', 4, true, true, 'Silver', 240.65, 'https://example.com/casing');
INSERT INTO public.casing VALUES (81, 'Cooler Master', 'Casing-638', 'Mini-ITX', 180, 305, 150, '240mm', '360mm', NULL, 2, false, true, 'Silver', 162.24, 'https://example.com/casing');
INSERT INTO public.casing VALUES (82, 'Cooler Master', 'Casing-237', 'ATX', 182, 309, 162, NULL, NULL, '240mm', 3, true, false, 'White', 281.73, 'https://example.com/casing');
INSERT INTO public.casing VALUES (83, 'Corsair', 'Casing-227', 'ATX', 205, 353, 159, '280mm', '240mm', NULL, 3, false, true, 'White', 162.39, 'https://example.com/casing');
INSERT INTO public.casing VALUES (84, 'Corsair', 'Casing-521', 'ATX', 174, 324, 180, '240mm', NULL, NULL, 1, true, true, 'White', 204.67, 'https://example.com/casing');
INSERT INTO public.casing VALUES (85, 'Lian Li', 'Casing-113', 'Micro-ATX', 163, 390, 153, '280mm', '240mm', '360mm', 1, true, false, 'Black', 67.56, 'https://example.com/casing');
INSERT INTO public.casing VALUES (86, 'Corsair', 'Casing-196', 'Mini-ITX', 162, 335, 179, NULL, '240mm', '240mm', 3, false, false, 'Silver', 299.37, 'https://example.com/casing');
INSERT INTO public.casing VALUES (87, 'Lian Li', 'Casing-512', 'Micro-ATX', 198, 336, 159, '240mm', '280mm', '240mm', 4, false, true, 'Black', 86.35, 'https://example.com/casing');
INSERT INTO public.casing VALUES (88, 'Lian Li', 'Casing-319', 'Micro-ATX', 185, 324, 173, NULL, '360mm', '280mm', 3, false, false, 'Black', 190.9, 'https://example.com/casing');
INSERT INTO public.casing VALUES (89, 'Cooler Master', 'Casing-748', 'ATX', 181, 322, 155, '280mm', '240mm', '240mm', 4, true, false, 'Black', 120.9, 'https://example.com/casing');
INSERT INTO public.casing VALUES (90, 'NZXT', 'Casing-855', 'ATX', 181, 307, 165, '360mm', '360mm', '240mm', 2, true, false, 'Silver', 199.74, 'https://example.com/casing');
INSERT INTO public.casing VALUES (91, 'Cooler Master', 'Casing-170', 'ATX', 160, 371, 178, '360mm', '360mm', '360mm', 4, true, true, 'White', 287.57, 'https://example.com/casing');
INSERT INTO public.casing VALUES (92, 'Corsair', 'Casing-544', 'ATX', 175, 323, 176, '360mm', '280mm', '280mm', 3, false, true, 'Black', 144.81, 'https://example.com/casing');
INSERT INTO public.casing VALUES (93, 'Cooler Master', 'Casing-586', 'Micro-ATX', 202, 300, 179, '280mm', '280mm', '240mm', 2, true, true, 'Black', 106.83, 'https://example.com/casing');
INSERT INTO public.casing VALUES (94, 'Cooler Master', 'Casing-446', 'Micro-ATX', 171, 284, 169, NULL, '280mm', NULL, 1, false, false, 'White', 132.78, 'https://example.com/casing');
INSERT INTO public.casing VALUES (95, 'Cooler Master', 'Casing-609', 'Micro-ATX', 180, 375, 161, '240mm', '240mm', '240mm', 2, false, true, 'Silver', 170.32, 'https://example.com/casing');
INSERT INTO public.casing VALUES (96, 'Cooler Master', 'Casing-506', 'Mini-ITX', 214, 390, 162, '360mm', NULL, '360mm', 2, false, true, 'Black', 84.89, 'https://example.com/casing');
INSERT INTO public.casing VALUES (97, 'NZXT', 'Casing-247', 'ATX', 216, 325, 156, '240mm', '240mm', NULL, 3, false, true, 'Silver', 207.56, 'https://example.com/casing');
INSERT INTO public.casing VALUES (98, 'Lian Li', 'Casing-884', 'Mini-ITX', 164, 390, 166, '360mm', '360mm', '360mm', 2, true, false, 'White', 208.86, 'https://example.com/casing');
INSERT INTO public.casing VALUES (99, 'NZXT', 'Casing-848', 'ATX', 177, 289, 164, '280mm', '280mm', '280mm', 1, true, false, 'Black', 160.62, 'https://example.com/casing');
INSERT INTO public.casing VALUES (100, 'NZXT', 'Casing-522', 'ATX', 193, 389, 173, '240mm', '360mm', '360mm', 3, false, true, 'Black', 188.68, 'https://example.com/casing');
INSERT INTO public.casing VALUES (101, 'Cooler Master', 'Casing-835', 'ATX', 180, 352, 180, NULL, '240mm', NULL, 1, false, true, 'Black', 171.37, 'https://example.com/casing');
INSERT INTO public.casing VALUES (102, 'Lian Li', 'Casing-436', 'Mini-ITX', 179, 361, 155, '360mm', '240mm', '240mm', 4, true, true, 'White', 108.9, 'https://example.com/casing');
INSERT INTO public.casing VALUES (103, 'NZXT', 'Casing-961', 'ATX', 206, 396, 177, '360mm', '360mm', '360mm', 1, true, false, 'Black', 67.27, 'https://example.com/casing');
INSERT INTO public.casing VALUES (104, 'Lian Li', 'Casing-485', 'ATX', 205, 297, 164, '240mm', NULL, '280mm', 2, false, true, 'White', 173.88, 'https://example.com/casing');
INSERT INTO public.casing VALUES (105, 'Lian Li', 'Casing-992', 'Mini-ITX', 161, 376, 168, '240mm', NULL, NULL, 4, false, true, 'Black', 288.36, 'https://example.com/casing');
INSERT INTO public.casing VALUES (106, 'Corsair', 'Casing-378', 'Micro-ATX', 200, 301, 162, NULL, '240mm', '240mm', 2, false, false, 'Silver', 253.71, 'https://example.com/casing');
INSERT INTO public.casing VALUES (107, 'Cooler Master', 'Casing-274', 'Micro-ATX', 207, 280, 171, '360mm', '240mm', NULL, 3, true, false, 'Black', 266.62, 'https://example.com/casing');
INSERT INTO public.casing VALUES (108, 'Lian Li', 'Casing-642', 'Micro-ATX', 217, 301, 178, '280mm', '240mm', NULL, 3, false, true, 'Black', 53.93, 'https://example.com/casing');
INSERT INTO public.casing VALUES (109, 'Corsair', 'Casing-371', 'ATX', 167, 317, 151, '280mm', '280mm', '280mm', 1, true, true, 'Silver', 235.56, 'https://example.com/casing');
INSERT INTO public.casing VALUES (110, 'Cooler Master', 'Casing-662', 'ATX', 171, 345, 155, NULL, NULL, '280mm', 2, true, false, 'Black', 246.76, 'https://example.com/casing');
INSERT INTO public.casing VALUES (111, 'Lian Li', 'Casing-903', 'ATX', 173, 380, 158, '280mm', '280mm', NULL, 1, false, false, 'Black', 276.59, 'https://example.com/casing');
INSERT INTO public.casing VALUES (112, 'Fractal Design', 'Casing-988', 'ATX', 167, 392, 155, '360mm', '280mm', NULL, 2, false, false, 'Black', 194.63, 'https://example.com/casing');
INSERT INTO public.casing VALUES (113, 'NZXT', 'Casing-483', 'Mini-ITX', 215, 327, 153, '240mm', '360mm', NULL, 4, true, true, 'White', 140.38, 'https://example.com/casing');
INSERT INTO public.casing VALUES (114, 'Cooler Master', 'Casing-928', 'ATX', 213, 338, 153, '280mm', '280mm', NULL, 1, true, true, 'Black', 163.37, 'https://example.com/casing');
INSERT INTO public.casing VALUES (115, 'Fractal Design', 'Casing-412', 'ATX', 181, 314, 161, '280mm', NULL, '360mm', 2, false, false, 'Black', 130.65, 'https://example.com/casing');
INSERT INTO public.casing VALUES (116, 'Corsair', 'Casing-220', 'Mini-ITX', 174, 304, 162, '280mm', '280mm', NULL, 2, true, true, 'Black', 104.35, 'https://example.com/casing');
INSERT INTO public.casing VALUES (117, 'Corsair', 'Casing-220', 'Mini-ITX', 166, 294, 180, NULL, '280mm', NULL, 3, true, false, 'Silver', 211.74, 'https://example.com/casing');
INSERT INTO public.casing VALUES (118, 'Lian Li', 'Casing-522', 'Mini-ITX', 198, 307, 179, '280mm', '360mm', NULL, 2, false, true, 'Black', 255.78, 'https://example.com/casing');
INSERT INTO public.casing VALUES (119, 'Cooler Master', 'Casing-930', 'Micro-ATX', 160, 368, 168, '360mm', '280mm', '240mm', 1, false, false, 'White', 144.15, 'https://example.com/casing');
INSERT INTO public.casing VALUES (120, 'Fractal Design', 'Casing-266', 'Mini-ITX', 211, 304, 162, '280mm', '240mm', '280mm', 4, false, false, 'Silver', 147.28, 'https://example.com/casing');
INSERT INTO public.casing VALUES (121, 'NZXT', 'Casing-661', 'ATX', 187, 306, 174, '360mm', '360mm', '360mm', 4, true, true, 'Silver', 194.74, 'https://example.com/casing');
INSERT INTO public.casing VALUES (122, 'Fractal Design', 'Casing-750', 'ATX', 167, 317, 156, '280mm', '360mm', '360mm', 2, false, true, 'Silver', 295.06, 'https://example.com/casing');
INSERT INTO public.casing VALUES (123, 'Fractal Design', 'Casing-249', 'Micro-ATX', 165, 382, 155, '360mm', '360mm', '280mm', 4, true, false, 'Black', 270.61, 'https://example.com/casing');
INSERT INTO public.casing VALUES (124, 'Lian Li', 'Casing-105', 'Micro-ATX', 193, 299, 151, '240mm', NULL, NULL, 4, false, false, 'Black', 144.21, 'https://example.com/casing');
INSERT INTO public.casing VALUES (125, 'Lian Li', 'Casing-174', 'Mini-ITX', 210, 380, 176, '280mm', '360mm', NULL, 3, true, false, 'Black', 246.16, 'https://example.com/casing');
INSERT INTO public.casing VALUES (126, 'Corsair', 'Casing-634', 'Micro-ATX', 195, 383, 165, '240mm', '240mm', '240mm', 3, false, true, 'Black', 98.99, 'https://example.com/casing');
INSERT INTO public.casing VALUES (127, 'Lian Li', 'Casing-508', 'ATX', 180, 342, 179, NULL, NULL, '240mm', 4, true, false, 'White', 88.4, 'https://example.com/casing');
INSERT INTO public.casing VALUES (128, 'Lian Li', 'Casing-330', 'Micro-ATX', 186, 352, 166, NULL, '280mm', '280mm', 3, true, false, 'Silver', 154.78, 'https://example.com/casing');
INSERT INTO public.casing VALUES (129, 'Cooler Master', 'Casing-846', 'Mini-ITX', 168, 301, 172, '240mm', '280mm', '280mm', 3, true, true, 'Black', 67.64, 'https://example.com/casing');
INSERT INTO public.casing VALUES (130, 'Corsair', 'Casing-986', 'Micro-ATX', 206, 363, 173, NULL, '360mm', '240mm', 3, false, true, 'Black', 98.11, 'https://example.com/casing');
INSERT INTO public.casing VALUES (131, 'NZXT', 'Casing-208', 'Mini-ITX', 185, 284, 167, '280mm', '240mm', '280mm', 3, true, true, 'Silver', 172.97, 'https://example.com/casing');
INSERT INTO public.casing VALUES (132, 'Fractal Design', 'Casing-674', 'Mini-ITX', 220, 366, 174, NULL, NULL, NULL, 2, true, true, 'Black', 224.7, 'https://example.com/casing');
INSERT INTO public.casing VALUES (133, 'Lian Li', 'Casing-538', 'Micro-ATX', 194, 319, 160, NULL, '240mm', '280mm', 3, false, true, 'Silver', 248.13, 'https://example.com/casing');
INSERT INTO public.casing VALUES (134, 'Cooler Master', 'Casing-961', 'Micro-ATX', 160, 386, 173, '280mm', NULL, '280mm', 3, false, false, 'Silver', 259.96, 'https://example.com/casing');
INSERT INTO public.casing VALUES (135, 'Cooler Master', 'Casing-137', 'ATX', 207, 386, 150, '280mm', NULL, '360mm', 1, false, false, 'Silver', 266.16, 'https://example.com/casing');
INSERT INTO public.casing VALUES (136, 'Fractal Design', 'Casing-880', 'ATX', 168, 345, 166, '360mm', '360mm', '280mm', 1, true, true, 'White', 163.3, 'https://example.com/casing');
INSERT INTO public.casing VALUES (137, 'Cooler Master', 'Casing-468', 'Mini-ITX', 194, 284, 177, '360mm', '360mm', NULL, 4, true, true, 'Black', 185.96, 'https://example.com/casing');
INSERT INTO public.casing VALUES (138, 'NZXT', 'Casing-445', 'ATX', 185, 330, 176, '240mm', '280mm', '360mm', 3, true, false, 'Black', 194.75, 'https://example.com/casing');
INSERT INTO public.casing VALUES (139, 'Corsair', 'Casing-566', 'ATX', 182, 329, 153, NULL, '360mm', '360mm', 1, true, false, 'Black', 116.5, 'https://example.com/casing');
INSERT INTO public.casing VALUES (140, 'Fractal Design', 'Casing-399', 'ATX', 168, 311, 151, '280mm', NULL, '360mm', 1, true, true, 'Black', 85.85, 'https://example.com/casing');
INSERT INTO public.casing VALUES (141, 'NZXT', 'Casing-960', 'Mini-ITX', 181, 321, 161, '280mm', '360mm', NULL, 2, true, true, 'Silver', 206.96, 'https://example.com/casing');
INSERT INTO public.casing VALUES (142, 'Lian Li', 'Casing-744', 'ATX', 191, 356, 178, NULL, '360mm', '360mm', 3, false, true, 'Black', 63.59, 'https://example.com/casing');
INSERT INTO public.casing VALUES (143, 'Lian Li', 'Casing-920', 'Micro-ATX', 163, 398, 171, '240mm', '240mm', NULL, 1, true, false, 'White', 241.56, 'https://example.com/casing');
INSERT INTO public.casing VALUES (144, 'NZXT', 'Casing-824', 'Micro-ATX', 201, 320, 180, '360mm', '280mm', '240mm', 4, false, true, 'Silver', 155.87, 'https://example.com/casing');
INSERT INTO public.casing VALUES (145, 'Lian Li', 'Casing-113', 'Mini-ITX', 202, 392, 156, '280mm', '240mm', '360mm', 3, false, false, 'White', 157.39, 'https://example.com/casing');
INSERT INTO public.casing VALUES (146, 'Cooler Master', 'Casing-153', 'ATX', 219, 395, 161, '360mm', NULL, '280mm', 2, false, true, 'White', 111.67, 'https://example.com/casing');
INSERT INTO public.casing VALUES (147, 'Lian Li', 'Casing-355', 'ATX', 218, 301, 179, '360mm', '360mm', '240mm', 4, false, true, 'Black', 186.15, 'https://example.com/casing');
INSERT INTO public.casing VALUES (148, 'NZXT', 'Casing-550', 'Micro-ATX', 160, 284, 180, NULL, '240mm', '280mm', 3, false, false, 'White', 63.76, 'https://example.com/casing');
INSERT INTO public.casing VALUES (149, 'NZXT', 'Casing-658', 'Mini-ITX', 213, 396, 155, '360mm', '360mm', '240mm', 1, true, true, 'White', 285.44, 'https://example.com/casing');
INSERT INTO public.casing VALUES (150, 'Cooler Master', 'Casing-701', 'Mini-ITX', 187, 293, 171, '360mm', '240mm', '360mm', 2, true, true, 'Silver', 51.93, 'https://example.com/casing');
INSERT INTO public.casing VALUES (151, 'Corsair', 'Casing-214', 'ATX', 206, 292, 158, '280mm', '240mm', '360mm', 2, false, false, 'Silver', 91.23, 'https://example.com/casing');
INSERT INTO public.casing VALUES (152, 'NZXT', 'Casing-596', 'ATX', 178, 285, 165, NULL, NULL, NULL, 2, false, true, 'Silver', 199.11, 'https://example.com/casing');
INSERT INTO public.casing VALUES (153, 'Fractal Design', 'Casing-211', 'ATX', 188, 375, 180, '360mm', '280mm', '280mm', 2, true, false, 'White', 192.78, 'https://example.com/casing');
INSERT INTO public.casing VALUES (154, 'NZXT', 'Casing-714', 'Mini-ITX', 184, 313, 152, NULL, '240mm', '240mm', 1, false, false, 'Black', 179.33, 'https://example.com/casing');
INSERT INTO public.casing VALUES (155, 'Lian Li', 'Casing-513', 'Micro-ATX', 167, 280, 170, NULL, NULL, NULL, 3, false, false, 'White', 260.39, 'https://example.com/casing');
INSERT INTO public.casing VALUES (156, 'Cooler Master', 'Casing-183', 'Micro-ATX', 189, 330, 173, '360mm', NULL, '360mm', 1, false, true, 'White', 184.98, 'https://example.com/casing');
INSERT INTO public.casing VALUES (157, 'NZXT', 'Casing-624', 'Micro-ATX', 202, 387, 162, '280mm', '280mm', '360mm', 4, false, true, 'Black', 272.85, 'https://example.com/casing');
INSERT INTO public.casing VALUES (158, 'Cooler Master', 'Casing-630', 'Mini-ITX', 175, 396, 180, '240mm', '280mm', '360mm', 1, true, true, 'Black', 93.96, 'https://example.com/casing');
INSERT INTO public.casing VALUES (159, 'Fractal Design', 'Casing-352', 'ATX', 204, 360, 171, '240mm', NULL, '240mm', 3, false, false, 'Black', 276.23, 'https://example.com/casing');
INSERT INTO public.casing VALUES (160, 'Cooler Master', 'Casing-432', 'Mini-ITX', 176, 363, 172, NULL, '280mm', NULL, 3, true, true, 'Black', 206.97, 'https://example.com/casing');
INSERT INTO public.casing VALUES (161, 'Corsair', 'Casing-627', 'Micro-ATX', 187, 365, 154, '280mm', NULL, '360mm', 2, true, true, 'Black', 187.68, 'https://example.com/casing');
INSERT INTO public.casing VALUES (162, 'Fractal Design', 'Casing-984', 'Mini-ITX', 178, 393, 150, '280mm', '280mm', '240mm', 2, true, false, 'White', 206.26, 'https://example.com/casing');
INSERT INTO public.casing VALUES (163, 'Cooler Master', 'Casing-837', 'Micro-ATX', 181, 293, 162, NULL, '280mm', '360mm', 3, true, true, 'Silver', 270.34, 'https://example.com/casing');
INSERT INTO public.casing VALUES (164, 'Lian Li', 'Casing-407', 'Micro-ATX', 198, 329, 153, '280mm', NULL, '280mm', 2, false, false, 'Silver', 186.36, 'https://example.com/casing');
INSERT INTO public.casing VALUES (165, 'NZXT', 'Casing-774', 'Mini-ITX', 195, 381, 151, '280mm', '240mm', '240mm', 3, false, false, 'White', 273.8, 'https://example.com/casing');
INSERT INTO public.casing VALUES (166, 'NZXT', 'Casing-589', 'Mini-ITX', 211, 349, 154, NULL, '280mm', '280mm', 1, true, false, 'White', 123.06, 'https://example.com/casing');
INSERT INTO public.casing VALUES (167, 'Cooler Master', 'Casing-421', 'Micro-ATX', 184, 317, 157, '240mm', NULL, '240mm', 2, true, true, 'Silver', 66.44, 'https://example.com/casing');
INSERT INTO public.casing VALUES (168, 'Cooler Master', 'Casing-578', 'Mini-ITX', 184, 311, 170, '240mm', '360mm', '280mm', 1, true, true, 'Black', 176.61, 'https://example.com/casing');
INSERT INTO public.casing VALUES (169, 'Cooler Master', 'Casing-700', 'ATX', 180, 354, 162, NULL, NULL, '360mm', 2, true, false, 'White', 121.36, 'https://example.com/casing');
INSERT INTO public.casing VALUES (170, 'NZXT', 'Casing-346', 'ATX', 198, 343, 175, '280mm', '360mm', '360mm', 1, false, true, 'White', 284.47, 'https://example.com/casing');
INSERT INTO public.casing VALUES (171, 'Corsair', 'Casing-356', 'Micro-ATX', 162, 393, 162, '360mm', '360mm', NULL, 4, false, false, 'Silver', 272.67, 'https://example.com/casing');
INSERT INTO public.casing VALUES (172, 'Corsair', 'Casing-752', 'Micro-ATX', 204, 359, 154, '280mm', '360mm', '360mm', 2, false, false, 'Silver', 210.1, 'https://example.com/casing');
INSERT INTO public.casing VALUES (173, 'Cooler Master', 'Casing-709', 'ATX', 202, 356, 169, '240mm', NULL, NULL, 3, true, true, 'Black', 280.08, 'https://example.com/casing');
INSERT INTO public.casing VALUES (174, 'Lian Li', 'Casing-973', 'Micro-ATX', 174, 296, 162, '280mm', '360mm', '280mm', 4, true, true, 'Silver', 135.58, 'https://example.com/casing');
INSERT INTO public.casing VALUES (175, 'Lian Li', 'Casing-829', 'ATX', 164, 400, 176, '240mm', '240mm', '240mm', 1, true, true, 'Silver', 279.05, 'https://example.com/casing');
INSERT INTO public.casing VALUES (176, 'NZXT', 'Casing-800', 'Micro-ATX', 183, 373, 156, '240mm', '360mm', '360mm', 4, true, true, 'White', 247.92, 'https://example.com/casing');
INSERT INTO public.casing VALUES (177, 'Fractal Design', 'Casing-291', 'Micro-ATX', 219, 320, 174, '240mm', NULL, NULL, 4, false, false, 'Black', 293.78, 'https://example.com/casing');
INSERT INTO public.casing VALUES (178, 'NZXT', 'Casing-718', 'ATX', 215, 352, 156, '280mm', NULL, '240mm', 1, false, true, 'Black', 201.02, 'https://example.com/casing');
INSERT INTO public.casing VALUES (179, 'Corsair', 'Casing-161', 'Mini-ITX', 168, 334, 179, '240mm', '360mm', NULL, 2, false, true, 'Silver', 228.37, 'https://example.com/casing');
INSERT INTO public.casing VALUES (180, 'Cooler Master', 'Casing-719', 'Mini-ITX', 163, 358, 152, '360mm', '240mm', '360mm', 4, false, false, 'White', 173.86, 'https://example.com/casing');
INSERT INTO public.casing VALUES (181, 'Corsair', 'Casing-968', 'Mini-ITX', 204, 368, 172, '280mm', '280mm', '280mm', 3, false, true, 'White', 244.57, 'https://example.com/casing');
INSERT INTO public.casing VALUES (182, 'Lian Li', 'Casing-137', 'Micro-ATX', 177, 350, 153, NULL, NULL, '240mm', 3, true, false, 'White', 226.34, 'https://example.com/casing');
INSERT INTO public.casing VALUES (183, 'Fractal Design', 'Casing-769', 'ATX', 165, 303, 150, '360mm', '360mm', NULL, 3, true, false, 'White', 143.79, 'https://example.com/casing');
INSERT INTO public.casing VALUES (184, 'Corsair', 'Casing-615', 'ATX', 205, 358, 154, '280mm', '280mm', '240mm', 1, false, true, 'Silver', 108.33, 'https://example.com/casing');
INSERT INTO public.casing VALUES (185, 'Cooler Master', 'Casing-841', 'Mini-ITX', 210, 366, 154, '280mm', NULL, '240mm', 4, true, false, 'Silver', 287.6, 'https://example.com/casing');
INSERT INTO public.casing VALUES (186, 'Cooler Master', 'Casing-588', 'Mini-ITX', 160, 340, 156, '280mm', '280mm', '360mm', 2, true, false, 'Black', 260.24, 'https://example.com/casing');
INSERT INTO public.casing VALUES (187, 'Cooler Master', 'Casing-580', 'Micro-ATX', 178, 316, 171, '360mm', '240mm', '240mm', 4, false, false, 'White', 107.69, 'https://example.com/casing');
INSERT INTO public.casing VALUES (188, 'Cooler Master', 'Casing-127', 'Micro-ATX', 168, 310, 154, '360mm', '240mm', '280mm', 1, false, true, 'White', 190.43, 'https://example.com/casing');
INSERT INTO public.casing VALUES (189, 'Fractal Design', 'Casing-775', 'Mini-ITX', 211, 361, 178, '240mm', NULL, '360mm', 2, false, false, 'Black', 104.1, 'https://example.com/casing');
INSERT INTO public.casing VALUES (190, 'Cooler Master', 'Casing-991', 'ATX', 213, 328, 150, '360mm', '280mm', '240mm', 1, true, true, 'Silver', 161.01, 'https://example.com/casing');
INSERT INTO public.casing VALUES (191, 'Lian Li', 'Casing-987', 'Micro-ATX', 169, 342, 151, '280mm', NULL, '360mm', 1, true, false, 'White', 99.73, 'https://example.com/casing');
INSERT INTO public.casing VALUES (192, 'Cooler Master', 'Casing-398', 'Mini-ITX', 198, 351, 176, '240mm', '240mm', NULL, 3, false, false, 'Black', 287.93, 'https://example.com/casing');
INSERT INTO public.casing VALUES (193, 'Cooler Master', 'Casing-670', 'ATX', 186, 398, 167, NULL, '280mm', NULL, 2, true, true, 'White', 255.1, 'https://example.com/casing');
INSERT INTO public.casing VALUES (194, 'Fractal Design', 'Casing-369', 'Micro-ATX', 209, 367, 167, '280mm', '240mm', '240mm', 1, false, true, 'White', 256.2, 'https://example.com/casing');
INSERT INTO public.casing VALUES (195, 'NZXT', 'Casing-798', 'Mini-ITX', 164, 350, 159, '240mm', NULL, '360mm', 4, true, true, 'Black', 241.52, 'https://example.com/casing');
INSERT INTO public.casing VALUES (196, 'Corsair', 'Casing-965', 'ATX', 181, 367, 166, '360mm', '280mm', '280mm', 3, true, false, 'Silver', 163.49, 'https://example.com/casing');
INSERT INTO public.casing VALUES (197, 'Cooler Master', 'Casing-379', 'ATX', 200, 290, 159, '280mm', '360mm', '360mm', 3, true, true, 'Black', 202.72, 'https://example.com/casing');
INSERT INTO public.casing VALUES (198, 'Lian Li', 'Casing-500', 'ATX', 199, 286, 160, '360mm', '280mm', NULL, 1, false, true, 'Black', 158.52, 'https://example.com/casing');
INSERT INTO public.casing VALUES (199, 'Corsair', 'Casing-721', 'Mini-ITX', 219, 353, 160, '360mm', '360mm', NULL, 4, true, false, 'Silver', 186.11, 'https://example.com/casing');
INSERT INTO public.casing VALUES (200, 'Cooler Master', 'Casing-506', 'ATX', 208, 391, 158, '360mm', '280mm', NULL, 4, false, false, 'White', 231.69, 'https://example.com/casing');


--
-- Data for Name: cpu; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.cpu VALUES (1, 'AMD', 'Ryzen 9 7950X', '100-100000514WOF', 'AM5', 170, '80 MB', true, 649.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-9-7950x');
INSERT INTO public.cpu VALUES (2, 'AMD', 'Ryzen 9 7900X', '100-100000589WOF', 'AM5', 170, '76 MB', true, 489.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-9-7900x');
INSERT INTO public.cpu VALUES (3, 'AMD', 'Ryzen 7 7700X', '100-100000591WOF', 'AM5', 105, '40 MB', true, 379.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-7-7700x');
INSERT INTO public.cpu VALUES (4, 'AMD', 'Ryzen 5 7600X', '100-100000593WOF', 'AM5', 105, '38 MB', true, 269.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-5-7600x');
INSERT INTO public.cpu VALUES (5, 'AMD', 'Ryzen 7 5800X3D', '100-100000651WOF', 'AM4', 105, '100 MB', false, 329.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-7-5800x3d');
INSERT INTO public.cpu VALUES (6, 'AMD', 'Ryzen 5 5600X', '100-100000065BOX', 'AM4', 65, '35 MB', true, 189.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-5-5600x');
INSERT INTO public.cpu VALUES (7, 'AMD', 'Ryzen 9 5950X', '100-100000059WOF', 'AM4', 105, '72 MB', true, 549.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-9-5950x');
INSERT INTO public.cpu VALUES (8, 'Intel', 'Core i9-13900K', 'BX8071513900K', 'LGA1700', 125, '36 MB', true, 599.99, 'https://www.intel.com/content/www/us/en/products/sku/230500/intel-core-i913900k-processor-36m-cache-up-to-5-80-ghz');
INSERT INTO public.cpu VALUES (9, 'Intel', 'Core i7-13700K', 'BX8071513700K', 'LGA1700', 125, '30 MB', true, 429.99, 'https://www.intel.com/content/www/us/en/products/sku/230497/intel-core-i713700k-processor-30m-cache-up-to-5-40-ghz');
INSERT INTO public.cpu VALUES (10, 'Intel', 'Core i5-13600K', 'BX8071513600K', 'LGA1700', 125, '24 MB', true, 319.99, 'https://www.intel.com/content/www/us/en/products/sku/230496/intel-core-i513600k-processor-24m-cache-up-to-5-10-ghz');
INSERT INTO public.cpu VALUES (11, 'Intel', 'Core i5-13400F', 'BX8071513400F', 'LGA1700', 65, '20 MB', false, 199.99, 'https://www.intel.com/content/www/us/en/products/sku/232132/intel-core-i513400f-processor-20m-cache-up-to-4-60-ghz');
INSERT INTO public.cpu VALUES (12, 'Intel', 'Core i3-13100F', 'BX8071513100F', 'LGA1700', 58, '12 MB', false, 109.99, 'https://www.intel.com/content/www/us/en/products/sku/232134/intel-core-i313100f-processor-12m-cache-up-to-4-50-ghz');
INSERT INTO public.cpu VALUES (13, 'Intel', 'Core i9-12900K', 'BX8071512900K', 'LGA1700', 125, '30 MB', true, 529.99, 'https://www.intel.com/content/www/us/en/products/sku/134599/intel-core-i912900k-processor-30m-cache-up-to-5-20-ghz');
INSERT INTO public.cpu VALUES (14, 'AMD', 'Ryzen 5 7600', '100-100000992BOX', 'AM5', 65, '38 MB', false, 259.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-5-7600');
INSERT INTO public.cpu VALUES (15, 'AMD', 'Ryzen 7 7800X3D', '100-100000910WOF', 'AM5', 120, '104 MB', false, 449.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-7-7800x3d');
INSERT INTO public.cpu VALUES (16, 'Intel', 'Core i7-12700K', 'BX8071512700K', 'LGA1700', 125, '25 MB', true, 409.99, 'https://www.intel.com/content/www/us/en/products/sku/134597/intel-core-i712700k-processor-25m-cache-up-to-5-00-ghz');
INSERT INTO public.cpu VALUES (17, 'Intel', 'Core i5-12400', 'BX8071512400', 'LGA1700', 65, '18 MB', false, 199.99, 'https://www.intel.com/content/www/us/en/products/sku/134594/intel-core-i512400-processor-18m-cache-up-to-4-40-ghz');
INSERT INTO public.cpu VALUES (18, 'AMD', 'Ryzen 5 5500', '100-100000457BOX', 'AM4', 65, '19 MB', false, 109.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-5-5500');
INSERT INTO public.cpu VALUES (19, 'AMD', 'Ryzen 3 4100', '100-100000510BOX', 'AM4', 65, '6 MB', false, 89.99, 'https://www.amd.com/en/products/cpu/amd-ryzen-3-4100');
INSERT INTO public.cpu VALUES (20, 'Intel', 'Core i9-14900K', 'BX8071514900K', 'LGA1700', 125, '36 MB', true, 629.99, 'https://www.intel.com/content/www/us/en/products/sku/234735/intel-core-i914900k-processor-36m-cache-up-to-6-00-ghz');


--
-- Data for Name: cpu_cooler; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.cpu_cooler VALUES (1, 'NZXT', 'Cooler-250', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 360, 237, 55, 'Silver', true, false, 139.23, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (2, 'Corsair', 'Cooler-209', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 280, 185, 56, 'Silver', true, false, 82.25, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (3, 'be quiet!', 'Cooler-760', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, NULL, 165, 44, 'White', true, true, 151.4, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (4, 'be quiet!', 'Cooler-287', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 240, 229, 51, 'Black', true, false, 61.03, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (5, 'Cooler Master', 'Cooler-505', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 240, 236, 57, 'White', false, true, 123.88, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (6, 'NZXT', 'Cooler-445', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 360, 201, 41, 'Silver', true, false, 113.82, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (7, 'NZXT', 'Cooler-363', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 280, 196, 48, 'Silver', true, false, 214.08, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (8, 'Cooler Master', 'Cooler-407', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 360, 173, 45, 'Black', false, true, 216.73, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (9, 'NZXT', 'Cooler-253', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 240, 204, 51, 'Silver', false, true, 195.7, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (10, 'Noctua', 'Cooler-220', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 360, 198, 48, 'Silver', true, true, 98.78, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (11, 'Corsair', 'Cooler-805', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 240, 212, 55, 'Silver', true, false, 218.2, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (12, 'be quiet!', 'Cooler-254', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 360, 242, 41, 'White', false, true, 60.42, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (13, 'be quiet!', 'Cooler-607', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 151, 43, 'Silver', true, false, 154.58, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (14, 'Cooler Master', 'Cooler-950', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, 240, 184, 58, 'Silver', false, true, 122.25, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (15, 'Corsair', 'Cooler-169', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 240, 229, 41, 'White', true, false, 100.2, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (16, 'NZXT', 'Cooler-430', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 280, 182, 56, 'Silver', false, true, 54.49, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (17, 'Cooler Master', 'Cooler-115', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 240, 158, 46, 'Black', true, true, 162.42, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (18, 'Noctua', 'Cooler-139', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 360, 165, 60, 'Black', true, true, 153.69, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (19, 'Cooler Master', 'Cooler-764', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 240, 214, 48, 'White', true, true, 52.51, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (20, 'NZXT', 'Cooler-538', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 280, 213, 42, 'Silver', true, true, 202.95, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (21, 'Noctua', 'Cooler-990', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 240, 223, 54, 'Black', true, false, 130.82, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (22, 'Cooler Master', 'Cooler-540', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, NULL, 196, 49, 'Black', false, false, 193.03, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (23, 'Noctua', 'Cooler-332', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 198, 48, 'White', false, true, 246.51, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (24, 'Cooler Master', 'Cooler-100', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, 280, 206, 53, 'Silver', true, true, 122.5, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (25, 'be quiet!', 'Cooler-424', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 280, 177, 40, 'White', true, true, 225.63, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (26, 'Noctua', 'Cooler-878', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 360, 214, 59, 'Black', true, false, 85.96, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (27, 'Corsair', 'Cooler-536', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, NULL, 193, 58, 'White', false, true, 140.6, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (28, 'Cooler Master', 'Cooler-479', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 240, 153, 53, 'Silver', false, false, 213.48, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (29, 'Cooler Master', 'Cooler-118', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 280, 220, 48, 'Silver', false, true, 151.82, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (30, 'Noctua', 'Cooler-111', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, NULL, 153, 54, 'Black', false, true, 158.43, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (31, 'Cooler Master', 'Cooler-709', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 360, 194, 56, 'Silver', false, true, 163.65, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (32, 'Corsair', 'Cooler-567', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 240, 214, 55, 'Silver', false, false, 40.91, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (33, 'Cooler Master', 'Cooler-845', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 280, 183, 44, 'Black', true, true, 127.14, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (34, 'be quiet!', 'Cooler-164', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 280, 228, 53, 'White', false, true, 202.72, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (35, 'NZXT', 'Cooler-378', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 240, 238, 60, 'Silver', true, false, 65.45, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (36, 'Corsair', 'Cooler-679', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 173, 58, 'Black', false, true, 60.51, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (37, 'Noctua', 'Cooler-595', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, NULL, 247, 48, 'White', false, false, 121.52, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (38, 'be quiet!', 'Cooler-579', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 360, 188, 43, 'White', false, true, 224.67, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (39, 'Noctua', 'Cooler-230', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 360, 171, 57, 'Black', true, false, 183.69, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (40, 'NZXT', 'Cooler-727', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 240, 232, 56, 'Black', false, true, 92.57, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (41, 'be quiet!', 'Cooler-996', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, NULL, 221, 48, 'Silver', false, false, 166.53, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (42, 'be quiet!', 'Cooler-435', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 360, 169, 57, 'Silver', true, false, 44.97, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (43, 'Noctua', 'Cooler-325', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 280, 165, 40, 'Silver', false, false, 194.35, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (44, 'Cooler Master', 'Cooler-674', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 360, 202, 45, 'White', false, true, 233.03, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (45, 'Corsair', 'Cooler-271', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 360, 201, 50, 'Black', false, true, 57.64, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (46, 'be quiet!', 'Cooler-894', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, NULL, 207, 43, 'White', false, true, 177.84, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (47, 'Noctua', 'Cooler-810', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 240, 161, 54, 'White', true, true, 223.7, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (48, 'NZXT', 'Cooler-930', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 240, 185, 44, 'Silver', true, false, 137.53, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (49, 'Cooler Master', 'Cooler-509', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, NULL, 189, 48, 'White', false, false, 84.62, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (50, 'Cooler Master', 'Cooler-382', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 360, 185, 45, 'Silver', true, false, 42.81, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (51, 'Noctua', 'Cooler-360', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 280, 165, 47, 'Black', false, true, 145.92, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (52, 'NZXT', 'Cooler-339', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 280, 185, 53, 'Silver', false, false, 175.17, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (53, 'Noctua', 'Cooler-191', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 360, 233, 56, 'Silver', true, true, 188.47, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (54, 'Corsair', 'Cooler-642', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 360, 180, 45, 'Silver', true, true, 185.01, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (55, 'NZXT', 'Cooler-673', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 360, 176, 52, 'Black', true, true, 237.99, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (56, 'Cooler Master', 'Cooler-795', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 240, 200, 44, 'Black', false, true, 172.73, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (57, 'NZXT', 'Cooler-279', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 280, 166, 50, 'Black', true, true, 243.44, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (58, 'Noctua', 'Cooler-161', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 360, 167, 50, 'Black', true, true, 116.04, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (59, 'NZXT', 'Cooler-408', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 360, 182, 51, 'Silver', false, false, 91.85, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (60, 'NZXT', 'Cooler-279', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, NULL, 188, 43, 'Black', true, false, 197.27, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (61, 'Noctua', 'Cooler-946', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 246, 40, 'Black', false, false, 102.88, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (62, 'be quiet!', 'Cooler-887', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 240, 246, 43, 'Silver', true, true, 149.72, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (63, 'NZXT', 'Cooler-766', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 280, 184, 40, 'Silver', true, false, 126.67, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (64, 'be quiet!', 'Cooler-427', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 240, 184, 56, 'White', true, false, 187.34, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (65, 'Corsair', 'Cooler-320', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 280, 172, 43, 'Black', false, true, 58.32, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (66, 'Cooler Master', 'Cooler-649', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 240, 197, 57, 'White', false, true, 160.5, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (67, 'be quiet!', 'Cooler-989', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 194, 50, 'Silver', false, false, 132.05, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (68, 'Cooler Master', 'Cooler-844', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 360, 197, 42, 'Black', true, false, 63.15, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (69, 'Noctua', 'Cooler-882', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 360, 193, 47, 'Silver', true, false, 192.43, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (70, 'be quiet!', 'Cooler-727', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 360, 158, 41, 'Black', false, true, 125.94, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (71, 'be quiet!', 'Cooler-776', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 240, 250, 55, 'Black', false, true, 186.94, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (72, 'Noctua', 'Cooler-738', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 360, 165, 48, 'White', true, true, 118.14, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (73, 'Corsair', 'Cooler-572', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, 280, 186, 42, 'Silver', true, false, 216.41, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (74, 'Cooler Master', 'Cooler-348', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, NULL, 229, 44, 'Black', true, true, 102.35, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (75, 'Cooler Master', 'Cooler-727', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, NULL, 202, 59, 'Silver', true, false, 66.71, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (76, 'Corsair', 'Cooler-223', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 280, 224, 49, 'Black', true, false, 123.64, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (77, 'Corsair', 'Cooler-606', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 240, 242, 50, 'Black', true, true, 218.35, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (78, 'NZXT', 'Cooler-798', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, NULL, 155, 49, 'Silver', false, false, 237.47, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (79, 'be quiet!', 'Cooler-110', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 280, 160, 41, 'White', true, true, 85.42, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (80, 'Noctua', 'Cooler-570', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, NULL, 240, 41, 'White', false, true, 136.34, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (81, 'Corsair', 'Cooler-622', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 240, 238, 53, 'Silver', true, false, 69.18, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (82, 'Cooler Master', 'Cooler-826', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 280, 203, 40, 'White', true, false, 235, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (83, 'NZXT', 'Cooler-586', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 240, 151, 54, 'Black', false, true, 76.93, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (84, 'be quiet!', 'Cooler-920', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 280, 157, 46, 'Black', false, true, 154.81, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (85, 'Corsair', 'Cooler-640', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 360, 249, 42, 'Silver', true, true, 97.17, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (86, 'Noctua', 'Cooler-975', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 227, 40, 'White', false, true, 47.94, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (87, 'be quiet!', 'Cooler-663', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 240, 196, 43, 'Black', false, false, 204.47, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (88, 'be quiet!', 'Cooler-886', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, NULL, 204, 40, 'White', false, false, 92.67, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (89, 'Noctua', 'Cooler-211', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, NULL, 158, 40, 'Black', true, true, 206.83, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (90, 'Corsair', 'Cooler-939', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, NULL, 200, 60, 'White', false, false, 41.36, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (91, 'NZXT', 'Cooler-302', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 206, 60, 'Silver', true, true, 108.49, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (92, 'Noctua', 'Cooler-100', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 280, 243, 57, 'White', true, true, 167.34, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (93, 'NZXT', 'Cooler-485', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, NULL, 205, 45, 'Black', true, false, 160.14, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (94, 'be quiet!', 'Cooler-224', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, 360, 244, 58, 'White', false, true, 112.33, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (95, 'Noctua', 'Cooler-214', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, NULL, 230, 54, 'White', false, true, 149.62, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (96, 'NZXT', 'Cooler-309', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 240, 228, 43, 'White', true, true, 89.99, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (97, 'Corsair', 'Cooler-190', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 280, 219, 45, 'White', true, false, 224.29, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (98, 'Cooler Master', 'Cooler-368', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 360, 155, 59, 'White', false, true, 43.21, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (99, 'Noctua', 'Cooler-668', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 240, 168, 49, 'Black', true, true, 212.62, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (100, 'Cooler Master', 'Cooler-802', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, NULL, 241, 60, 'Black', false, true, 232.97, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (101, 'NZXT', 'Cooler-579', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, NULL, 179, 42, 'Silver', true, true, 135.32, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (102, 'Cooler Master', 'Cooler-282', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, NULL, 151, 48, 'Silver', false, true, 82.89, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (103, 'Cooler Master', 'Cooler-930', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 240, 241, 42, 'Black', true, false, 70.41, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (104, 'NZXT', 'Cooler-844', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 212, 53, 'Black', false, false, 158.92, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (105, 'be quiet!', 'Cooler-198', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, NULL, 209, 56, 'Black', false, false, 206.65, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (106, 'Noctua', 'Cooler-725', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 280, 172, 41, 'Black', true, false, 177.99, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (107, 'Cooler Master', 'Cooler-732', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 280, 181, 41, 'White', true, false, 83.32, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (108, 'be quiet!', 'Cooler-261', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 280, 181, 47, 'Silver', false, false, 123.45, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (109, 'Noctua', 'Cooler-752', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 168, 46, 'Black', true, false, 103.79, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (110, 'NZXT', 'Cooler-177', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, NULL, 216, 59, 'Silver', false, false, 87.48, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (111, 'Noctua', 'Cooler-955', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, NULL, 168, 50, 'Silver', true, true, 67.25, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (112, 'Cooler Master', 'Cooler-525', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, NULL, 242, 48, 'White', true, true, 63.69, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (113, 'Cooler Master', 'Cooler-906', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 360, 152, 58, 'Silver', true, true, 181.66, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (114, 'NZXT', 'Cooler-753', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 192, 56, 'Silver', false, true, 92.42, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (115, 'Corsair', 'Cooler-590', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 360, 234, 57, 'White', false, true, 70.41, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (116, 'Corsair', 'Cooler-518', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 360, 151, 43, 'Black', true, false, 203.27, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (117, 'be quiet!', 'Cooler-244', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, NULL, 219, 57, 'Black', true, false, 164.92, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (118, 'Noctua', 'Cooler-627', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 201, 47, 'White', true, false, 204.71, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (119, 'Cooler Master', 'Cooler-298', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 240, 181, 41, 'White', true, true, 127.58, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (120, 'be quiet!', 'Cooler-857', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 280, 169, 40, 'White', true, true, 227.46, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (121, 'NZXT', 'Cooler-377', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 240, 216, 45, 'White', false, true, 243.28, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (122, 'be quiet!', 'Cooler-601', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 280, 151, 52, 'White', false, false, 193.83, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (123, 'Cooler Master', 'Cooler-990', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 168, 40, 'Black', false, false, 48.33, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (124, 'Cooler Master', 'Cooler-516', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 240, 191, 52, 'Silver', true, false, 98.05, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (125, 'Corsair', 'Cooler-807', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 280, 208, 41, 'Silver', true, true, 132.9, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (126, 'be quiet!', 'Cooler-267', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, 360, 152, 59, 'White', true, true, 139.82, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (127, 'NZXT', 'Cooler-189', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 360, 179, 45, 'Black', true, false, 120.49, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (128, 'NZXT', 'Cooler-651', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, NULL, 222, 59, 'Black', false, false, 234.57, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (129, 'NZXT', 'Cooler-741', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 280, 180, 40, 'White', false, false, 47.61, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (130, 'NZXT', 'Cooler-197', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 240, 159, 55, 'Silver', false, false, 218.83, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (131, 'Noctua', 'Cooler-326', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, NULL, 173, 48, 'Black', true, false, 111.87, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (132, 'Cooler Master', 'Cooler-499', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 360, 175, 54, 'Black', false, false, 241.21, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (133, 'Cooler Master', 'Cooler-608', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 360, 195, 58, 'Silver', false, true, 204.04, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (134, 'be quiet!', 'Cooler-252', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 280, 191, 45, 'Black', true, false, 227.11, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (135, 'Corsair', 'Cooler-170', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, NULL, 171, 50, 'White', false, false, 177.57, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (136, 'Corsair', 'Cooler-423', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, NULL, 169, 59, 'Silver', false, false, 76.38, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (137, 'Noctua', 'Cooler-843', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, NULL, 191, 59, 'Black', true, true, 167.2, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (138, 'NZXT', 'Cooler-591', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 360, 238, 50, 'Silver', true, true, 115.17, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (139, 'Corsair', 'Cooler-266', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 240, 163, 57, 'Black', true, true, 153.43, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (140, 'be quiet!', 'Cooler-284', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 240, 162, 42, 'Black', true, false, 50.08, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (141, 'Cooler Master', 'Cooler-538', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, NULL, 222, 56, 'Silver', true, false, 183.25, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (142, 'Cooler Master', 'Cooler-585', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 240, 242, 54, 'Silver', true, true, 155.24, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (143, 'NZXT', 'Cooler-655', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 240, 175, 59, 'Black', true, false, 43.6, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (144, 'be quiet!', 'Cooler-457', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 240, 174, 51, 'Black', true, true, 208.8, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (145, 'Noctua', 'Cooler-485', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 240, 184, 44, 'Black', true, false, 116.27, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (146, 'be quiet!', 'Cooler-140', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 280, 240, 48, 'Silver', false, true, 224.31, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (147, 'Corsair', 'Cooler-911', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 240, 224, 43, 'Black', true, false, 172.28, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (148, 'Cooler Master', 'Cooler-565', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 240, 239, 50, 'Silver', false, true, 181.6, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (149, 'NZXT', 'Cooler-250', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 360, 164, 47, 'Silver', false, true, 68.78, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (150, 'be quiet!', 'Cooler-280', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 208, 41, 'Silver', true, false, 243.18, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (151, 'be quiet!', 'Cooler-653', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, NULL, 211, 50, 'White', true, true, 150.63, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (152, 'NZXT', 'Cooler-709', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 360, 229, 57, 'Black', false, false, 179, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (153, 'be quiet!', 'Cooler-852', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 280, 237, 45, 'Silver', false, true, 138.41, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (154, 'Noctua', 'Cooler-202', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, NULL, 236, 45, 'Silver', true, false, 207.42, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (155, 'be quiet!', 'Cooler-283', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 240, 196, 60, 'White', true, false, 184.75, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (156, 'Corsair', 'Cooler-112', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 240, 185, 44, 'Silver', true, true, 43.04, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (157, 'NZXT', 'Cooler-681', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 240, 227, 59, 'Silver', true, false, 136.6, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (158, 'Cooler Master', 'Cooler-638', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 150, 280, 191, 40, 'Silver', true, false, 117.32, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (159, 'Noctua', 'Cooler-763', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, NULL, 171, 50, 'White', false, false, 162.84, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (160, 'Corsair', 'Cooler-364', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, NULL, 223, 55, 'Silver', false, true, 240.35, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (161, 'Noctua', 'Cooler-499', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, NULL, 218, 56, 'White', true, false, 120.44, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (162, 'be quiet!', 'Cooler-221', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 222, 54, 'Black', false, false, 177.62, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (163, 'Corsair', 'Cooler-570', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, 280, 183, 57, 'Silver', true, false, 160.7, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (164, 'be quiet!', 'Cooler-823', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 360, 246, 59, 'Black', true, true, 75.75, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (165, 'be quiet!', 'Cooler-172', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 240, 200, 49, 'Silver', false, true, 71.12, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (166, 'Noctua', 'Cooler-402', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 240, 239, 46, 'Silver', false, true, 224.07, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (167, 'Noctua', 'Cooler-789', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, NULL, 226, 53, 'Black', false, false, 196.73, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (168, 'Noctua', 'Cooler-209', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 201, 42, 'Black', false, false, 121.9, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (169, 'be quiet!', 'Cooler-345', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 360, 226, 49, 'Black', true, false, 221.47, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (170, 'NZXT', 'Cooler-480', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 280, 206, 44, 'White', true, true, 71.85, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (171, 'Cooler Master', 'Cooler-342', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 280, 214, 58, 'Black', false, true, 205.25, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (172, 'NZXT', 'Cooler-528', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 280, 195, 56, 'Black', false, false, 179.41, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (173, 'Noctua', 'Cooler-722', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 240, 206, 58, 'Silver', false, false, 249.9, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (174, 'Corsair', 'Cooler-318', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 240, 183, 41, 'Silver', false, false, 60.74, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (175, 'Corsair', 'Cooler-327', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 160, 280, 244, 43, 'White', false, false, 112.56, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (176, 'Noctua', 'Cooler-828', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, NULL, 226, 51, 'White', true, false, 126.85, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (177, 'NZXT', 'Cooler-996', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, 360, 250, 52, 'Silver', false, true, 50.3, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (178, 'be quiet!', 'Cooler-788', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 240, 195, 51, 'White', true, true, 241.67, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (179, 'Cooler Master', 'Cooler-707', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 192, 45, 'Silver', true, true, 62.1, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (180, 'NZXT', 'Cooler-345', 'AM4,AM5,LGA1700,LGA1200', 'Air', NULL, 280, 243, 51, 'Silver', false, true, 196.68, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (181, 'be quiet!', 'Cooler-477', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 280, 229, 55, 'Black', false, true, 130.67, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (182, 'Noctua', 'Cooler-951', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 360, 167, 55, 'Black', false, true, 64.87, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (183, 'Cooler Master', 'Cooler-454', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 240, 222, 56, 'White', false, true, 50.87, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (184, 'Noctua', 'Cooler-576', 'AM4,AM5,LGA1700,LGA1200', 'Air', 160, 360, 169, 51, 'Silver', false, true, 216.12, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (185, 'be quiet!', 'Cooler-813', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, NULL, 157, 57, 'Silver', false, true, 150.03, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (186, 'Cooler Master', 'Cooler-488', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 360, 152, 52, 'Black', false, false, 235.5, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (187, 'Noctua', 'Cooler-351', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 150, NULL, 194, 44, 'White', true, false, 150.86, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (188, 'be quiet!', 'Cooler-377', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 240, 167, 49, 'Black', true, false, 225.92, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (189, 'Noctua', 'Cooler-834', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, NULL, 207, 59, 'White', false, false, 217.52, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (190, 'NZXT', 'Cooler-326', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, NULL, 243, 58, 'Black', true, true, 244.47, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (191, 'be quiet!', 'Cooler-309', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, 360, 188, 51, 'Black', false, true, 95.74, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (192, 'be quiet!', 'Cooler-656', 'AM4,AM5,LGA1700,LGA1200', 'Air', 120, 360, 218, 42, 'Black', true, false, 134.49, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (193, 'Corsair', 'Cooler-880', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, 360, 179, 50, 'Silver', false, false, 79.59, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (194, 'Noctua', 'Cooler-492', 'AM4,AM5,LGA1700,LGA1200', 'Air', 150, NULL, 191, 60, 'White', false, false, 79.82, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (195, 'be quiet!', 'Cooler-349', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, 280, 247, 48, 'Silver', false, false, 146.52, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (196, 'NZXT', 'Cooler-359', 'AM4,AM5,LGA1700,LGA1200', 'AIO', NULL, NULL, 207, 49, 'White', true, true, 196.58, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (197, 'Corsair', 'Cooler-470', 'AM4,AM5,LGA1700,LGA1200', 'AIO', 120, NULL, 171, 42, 'Silver', true, true, 186.61, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (198, 'Cooler Master', 'Cooler-351', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 160, NULL, 168, 46, 'Black', false, true, 153.49, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (199, 'NZXT', 'Cooler-159', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', NULL, 240, 217, 57, 'Black', true, false, 201.43, 'https://example.com/cooler');
INSERT INTO public.cpu_cooler VALUES (200, 'be quiet!', 'Cooler-750', 'AM4,AM5,LGA1700,LGA1200', 'Custom Loop', 120, 360, 200, 53, 'Silver', false, false, 83.63, 'https://example.com/cooler');


--
-- Data for Name: gpu; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.gpu VALUES (1, 'Gigabyte', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 421, '8-pin', 1, 319, 109, 3, true, 1064.64, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (2, 'ASUS', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 16, 362, '6-pin', 2, 321, 116, 3, false, 1560.08, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (3, 'NVIDIA', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 256, '8-pin', 1, 253, 117, 3, false, 1508.45, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (4, 'AMD', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 16, 296, '8-pin', 2, 265, 110, 2, true, 1389.52, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (5, 'AMD', 'RTX 4080 Custom Edition', 'RTX 4080', 12, 342, '6-pin', 1, 258, 105, 2, true, 645.82, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (6, 'NVIDIA', 'RTX 4070 Custom Edition', 'RTX 4070', 10, 275, '6-pin', 2, 287, 119, 2, false, 1591.78, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (7, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 16, 160, '6-pin', 1, 250, 114, 2, false, 1467.15, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (8, 'NVIDIA', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 293, '16-pin', 2, 243, 112, 3, false, 924.88, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (9, 'AMD', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 10, 347, '8-pin', 1, 239, 116, 3, false, 1509.7, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (10, 'NVIDIA', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 24, 178, '6-pin', 1, 261, 109, 3, true, 1358.41, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (11, 'ASUS', 'RTX 4060 Custom Edition', 'RTX 4060', 16, 307, '6-pin', 2, 249, 121, 3, true, 442.84, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (12, 'MSI', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 10, 254, '16-pin', 1, 316, 115, 3, true, 889.68, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (13, 'MSI', 'RX 7600 Custom Edition', 'RX 7600', 10, 449, '16-pin', 1, 294, 132, 2, true, 303.39, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (14, 'ASUS', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 354, '8-pin', 1, 284, 110, 2, true, 1487.11, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (15, 'NVIDIA', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 24, 440, '8-pin', 1, 273, 122, 3, true, 792.7, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (16, 'AMD', 'RX 7600 Custom Edition', 'RX 7600', 8, 249, '6-pin', 1, 311, 111, 2, true, 502.41, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (17, 'ASUS', 'RTX 4060 Custom Edition', 'RTX 4060', 8, 434, '16-pin', 1, 283, 106, 3, true, 1593.72, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (18, 'Gigabyte', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 8, 237, '16-pin', 1, 329, 129, 3, false, 349.47, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (19, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 193, '8-pin', 1, 263, 102, 2, false, 664.64, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (20, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 8, 423, '16-pin', 1, 290, 110, 2, false, 1106.97, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (21, 'ASUS', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 359, '16-pin', 2, 231, 111, 2, true, 969.62, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (22, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 24, 196, '16-pin', 2, 275, 124, 3, false, 202.9, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (23, 'ASUS', 'RTX 4080 Custom Edition', 'RTX 4080', 24, 164, '8-pin', 2, 317, 108, 3, false, 967.05, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (24, 'Gigabyte', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 166, '16-pin', 2, 320, 132, 3, false, 1278.65, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (25, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 258, '8-pin', 1, 298, 125, 3, true, 1398.43, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (26, 'Gigabyte', 'RTX 4070 Custom Edition', 'RTX 4070', 10, 372, '8-pin', 2, 326, 114, 2, false, 576.54, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (27, 'Gigabyte', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 20, 301, '6-pin', 2, 279, 115, 3, false, 570.15, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (28, 'AMD', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 20, 347, '16-pin', 2, 268, 133, 3, false, 219.21, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (29, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 20, 325, '6-pin', 2, 314, 104, 3, false, 1348.08, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (30, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 20, 428, '8-pin', 2, 226, 116, 3, false, 1041.7, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (31, 'ASUS', 'RTX 4070 Custom Edition', 'RTX 4070', 24, 164, '6-pin', 1, 285, 113, 3, true, 836.39, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (32, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 20, 333, '8-pin', 1, 279, 111, 3, true, 498.26, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (33, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 16, 205, '6-pin', 1, 289, 107, 3, true, 1221.71, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (34, 'MSI', 'RX 7600 Custom Edition', 'RX 7600', 8, 287, '16-pin', 1, 323, 138, 2, false, 927.64, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (35, 'ASUS', 'RTX 4080 Custom Edition', 'RTX 4080', 20, 191, '6-pin', 2, 244, 124, 3, true, 897.05, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (36, 'Gigabyte', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 421, '6-pin', 1, 271, 122, 3, false, 834.3, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (37, 'Gigabyte', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 322, '8-pin', 2, 312, 102, 3, false, 879.94, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (38, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 8, 409, '16-pin', 1, 250, 120, 2, false, 1030.67, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (39, 'Gigabyte', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 296, '6-pin', 1, 252, 115, 3, true, 1446.32, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (40, 'ASUS', 'RTX 4060 Custom Edition', 'RTX 4060', 10, 235, '16-pin', 1, 230, 122, 3, false, 1253.49, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (41, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 20, 431, '8-pin', 1, 270, 119, 3, true, 512.75, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (42, 'AMD', 'RX 7600 Custom Edition', 'RX 7600', 8, 204, '6-pin', 1, 308, 104, 3, false, 1538.39, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (43, 'Gigabyte', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 16, 278, '16-pin', 1, 297, 117, 3, false, 742.4, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (44, 'Gigabyte', 'RX 7600 Custom Edition', 'RX 7600', 12, 425, '16-pin', 1, 247, 133, 2, true, 1402.02, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (45, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 362, '16-pin', 1, 320, 104, 3, true, 1451.28, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (46, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 12, 348, '8-pin', 2, 328, 100, 2, false, 471.4, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (47, 'Gigabyte', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 281, '16-pin', 1, 278, 102, 3, true, 794.41, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (48, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 10, 216, '6-pin', 2, 261, 101, 3, false, 643.22, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (49, 'ASUS', 'RTX 4080 Custom Edition', 'RTX 4080', 24, 325, '8-pin', 2, 242, 115, 3, true, 416.6, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (50, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 24, 381, '16-pin', 1, 282, 110, 2, true, 1364.98, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (51, 'AMD', 'RX 7600 Custom Edition', 'RX 7600', 24, 354, '6-pin', 2, 327, 139, 3, false, 1355.36, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (52, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 20, 174, '6-pin', 1, 321, 120, 2, false, 976.19, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (53, 'Gigabyte', 'RTX 4080 Custom Edition', 'RTX 4080', 12, 252, '6-pin', 2, 267, 138, 2, false, 1507.69, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (54, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 8, 252, '8-pin', 1, 302, 111, 3, false, 243.11, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (55, 'Gigabyte', 'RTX 4070 Custom Edition', 'RTX 4070', 8, 336, '16-pin', 2, 275, 137, 3, true, 1573.71, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (56, 'AMD', 'RTX 4070 Custom Edition', 'RTX 4070', 10, 251, '8-pin', 1, 231, 120, 2, false, 1083.99, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (57, 'NVIDIA', 'RTX 4060 Custom Edition', 'RTX 4060', 20, 168, '8-pin', 1, 259, 101, 3, true, 1214.88, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (58, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 8, 373, '8-pin', 2, 326, 127, 3, false, 630.83, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (59, 'MSI', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 20, 207, '16-pin', 2, 263, 112, 2, false, 1123.8, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (60, 'ASUS', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 367, '8-pin', 1, 284, 136, 3, true, 477.12, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (61, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 10, 163, '16-pin', 2, 276, 127, 3, false, 1557.28, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (62, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 12, 356, '8-pin', 1, 230, 114, 3, false, 506.45, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (63, 'ASUS', 'RTX 4070 Custom Edition', 'RTX 4070', 8, 266, '16-pin', 1, 233, 105, 2, false, 824.52, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (64, 'AMD', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 16, 443, '16-pin', 2, 243, 127, 2, true, 1043.09, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (65, 'MSI', 'RTX 4060 Custom Edition', 'RTX 4060', 16, 284, '16-pin', 1, 309, 108, 2, false, 858.03, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (66, 'MSI', 'RX 7600 Custom Edition', 'RX 7600', 24, 337, '8-pin', 1, 266, 107, 3, false, 1069.03, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (67, 'AMD', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 358, '8-pin', 1, 237, 137, 3, false, 822.05, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (68, 'AMD', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 20, 317, '16-pin', 1, 322, 102, 3, false, 425.52, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (69, 'NVIDIA', 'RTX 4060 Custom Edition', 'RTX 4060', 24, 220, '6-pin', 2, 278, 106, 3, false, 487.87, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (70, 'MSI', 'RX 7600 Custom Edition', 'RX 7600', 20, 370, '16-pin', 2, 302, 117, 3, true, 757.74, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (71, 'MSI', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 10, 395, '16-pin', 1, 262, 122, 3, false, 365.29, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (72, 'Gigabyte', 'RTX 4070 Custom Edition', 'RTX 4070', 20, 242, '8-pin', 2, 328, 102, 3, true, 1312.45, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (73, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 16, 305, '6-pin', 1, 230, 132, 2, false, 1013.41, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (74, 'NVIDIA', 'RX 7600 Custom Edition', 'RX 7600', 12, 223, '16-pin', 1, 318, 132, 3, true, 1440.06, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (75, 'NVIDIA', 'RX 7600 Custom Edition', 'RX 7600', 12, 447, '8-pin', 1, 321, 128, 2, true, 1118.82, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (76, 'MSI', 'RX 7600 Custom Edition', 'RX 7600', 8, 382, '8-pin', 1, 246, 107, 3, false, 815.74, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (77, 'AMD', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 12, 312, '8-pin', 1, 308, 131, 2, true, 519.87, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (78, 'MSI', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 382, '6-pin', 2, 288, 124, 3, true, 453.73, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (79, 'Gigabyte', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 365, '8-pin', 2, 248, 111, 2, false, 949.56, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (80, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 8, 390, '16-pin', 2, 273, 112, 3, true, 1434.3, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (81, 'NVIDIA', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 191, '8-pin', 1, 240, 109, 2, true, 430.24, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (82, 'NVIDIA', 'RTX 4060 Custom Edition', 'RTX 4060', 24, 315, '8-pin', 1, 326, 129, 2, true, 451.44, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (83, 'NVIDIA', 'RTX 4060 Custom Edition', 'RTX 4060', 10, 232, '8-pin', 1, 231, 102, 3, true, 488.18, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (84, 'ASUS', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 10, 251, '8-pin', 2, 264, 140, 2, true, 893.05, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (85, 'Gigabyte', 'RTX 4060 Custom Edition', 'RTX 4060', 12, 193, '8-pin', 2, 247, 132, 3, false, 1106.42, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (86, 'ASUS', 'RTX 4060 Custom Edition', 'RTX 4060', 20, 240, '16-pin', 2, 326, 121, 3, true, 1255.36, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (87, 'Gigabyte', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 425, '16-pin', 1, 322, 128, 3, false, 624.9, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (88, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 16, 355, '8-pin', 2, 312, 123, 3, true, 1378.76, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (89, 'AMD', 'RX 7600 Custom Edition', 'RX 7600', 12, 241, '8-pin', 1, 318, 100, 2, true, 433.63, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (90, 'MSI', 'RX 7600 Custom Edition', 'RX 7600', 16, 206, '8-pin', 2, 244, 140, 3, false, 624.38, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (91, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 24, 420, '16-pin', 1, 287, 138, 2, false, 790.01, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (92, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 370, '8-pin', 2, 230, 101, 3, false, 980.92, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (93, 'MSI', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 10, 264, '6-pin', 1, 295, 135, 3, true, 660.17, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (94, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 446, '6-pin', 2, 275, 106, 3, false, 1346.53, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (95, 'Gigabyte', 'RTX 4060 Custom Edition', 'RTX 4060', 10, 369, '6-pin', 2, 274, 109, 2, true, 849.47, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (96, 'AMD', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 12, 340, '16-pin', 1, 230, 131, 3, true, 947.27, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (97, 'AMD', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 24, 183, '8-pin', 2, 296, 116, 2, true, 382.72, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (98, 'Gigabyte', 'RX 7600 Custom Edition', 'RX 7600', 24, 197, '8-pin', 1, 236, 130, 3, true, 687.48, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (99, 'Gigabyte', 'RTX 4070 Custom Edition', 'RTX 4070', 10, 388, '8-pin', 2, 283, 140, 2, false, 1266.92, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (100, 'AMD', 'RTX 4080 Custom Edition', 'RTX 4080', 8, 442, '8-pin', 2, 236, 114, 3, false, 817.6, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (101, 'AMD', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 12, 180, '6-pin', 1, 306, 124, 3, false, 691.31, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (102, 'NVIDIA', 'RX 7600 Custom Edition', 'RX 7600', 24, 429, '16-pin', 2, 311, 130, 3, true, 817.1, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (103, 'Gigabyte', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 16, 370, '6-pin', 2, 243, 127, 2, true, 1495.87, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (104, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 12, 214, '6-pin', 2, 267, 116, 3, false, 510.12, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (105, 'NVIDIA', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 331, '8-pin', 1, 249, 133, 3, false, 1589.07, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (106, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 16, 202, '8-pin', 1, 299, 107, 2, false, 1349.5, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (107, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 8, 174, '6-pin', 1, 330, 101, 3, false, 352.9, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (108, 'Gigabyte', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 425, '16-pin', 1, 302, 137, 3, true, 1314.31, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (109, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 20, 358, '16-pin', 1, 305, 108, 3, false, 848.53, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (110, 'NVIDIA', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 249, '6-pin', 2, 306, 106, 3, false, 967, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (111, 'AMD', 'RTX 4080 Custom Edition', 'RTX 4080', 20, 189, '6-pin', 2, 249, 101, 3, false, 763.02, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (112, 'AMD', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 20, 295, '6-pin', 2, 301, 128, 3, true, 798.83, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (113, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 8, 201, '16-pin', 1, 230, 108, 3, true, 1384.12, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (114, 'MSI', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 373, '8-pin', 2, 319, 127, 2, false, 599.99, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (115, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 10, 299, '16-pin', 1, 287, 103, 3, true, 740.36, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (116, 'NVIDIA', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 20, 257, '16-pin', 1, 258, 119, 3, true, 609.49, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (117, 'ASUS', 'RTX 4080 Custom Edition', 'RTX 4080', 20, 283, '6-pin', 1, 285, 120, 2, false, 1318.37, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (118, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 301, '8-pin', 1, 287, 119, 2, true, 352.91, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (119, 'Gigabyte', 'RTX 4080 Custom Edition', 'RTX 4080', 12, 313, '6-pin', 2, 319, 102, 2, false, 1194.6, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (120, 'NVIDIA', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 282, '8-pin', 1, 312, 106, 3, true, 973.07, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (121, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 254, '6-pin', 2, 324, 125, 3, false, 1223.63, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (122, 'ASUS', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 385, '8-pin', 2, 264, 125, 3, false, 1548.06, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (123, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 24, 430, '6-pin', 1, 276, 140, 2, true, 791.29, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (124, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 16, 326, '16-pin', 1, 228, 134, 3, true, 899.77, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (125, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 16, 415, '6-pin', 1, 287, 120, 3, false, 1387.17, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (126, 'ASUS', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 20, 371, '16-pin', 1, 258, 119, 3, false, 824.43, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (127, 'AMD', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 269, '8-pin', 1, 300, 110, 2, false, 1311.15, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (128, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 398, '16-pin', 2, 287, 113, 3, true, 766.4, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (129, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 8, 161, '8-pin', 2, 321, 102, 3, false, 364.33, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (130, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 10, 289, '6-pin', 2, 319, 106, 3, false, 1498.94, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (131, 'ASUS', 'RTX 4060 Custom Edition', 'RTX 4060', 16, 252, '8-pin', 2, 313, 130, 2, false, 468.96, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (132, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 10, 242, '6-pin', 1, 301, 105, 3, false, 748.62, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (133, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 408, '16-pin', 2, 292, 125, 2, true, 789.83, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (134, 'MSI', 'RTX 4060 Custom Edition', 'RTX 4060', 8, 229, '8-pin', 1, 294, 138, 3, false, 965.94, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (135, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 313, '8-pin', 1, 323, 129, 3, true, 828.99, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (136, 'MSI', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 431, '6-pin', 1, 323, 121, 3, false, 336.4, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (137, 'Gigabyte', 'RTX 4060 Custom Edition', 'RTX 4060', 16, 271, '16-pin', 1, 225, 130, 3, true, 1395.38, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (138, 'ASUS', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 292, '8-pin', 2, 264, 100, 3, true, 651.87, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (139, 'Gigabyte', 'RTX 4060 Custom Edition', 'RTX 4060', 12, 418, '8-pin', 2, 321, 120, 3, true, 439.57, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (140, 'Gigabyte', 'RX 7600 Custom Edition', 'RX 7600', 10, 447, '8-pin', 1, 287, 126, 2, false, 1055.57, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (141, 'ASUS', 'RTX 4080 Custom Edition', 'RTX 4080', 8, 398, '8-pin', 2, 329, 131, 2, true, 268.26, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (142, 'Gigabyte', 'RX 7600 Custom Edition', 'RX 7600', 24, 344, '8-pin', 2, 243, 122, 3, false, 1490.59, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (143, 'AMD', 'RTX 4080 Custom Edition', 'RTX 4080', 8, 364, '8-pin', 2, 310, 127, 3, false, 850.67, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (144, 'Gigabyte', 'RX 7600 Custom Edition', 'RX 7600', 20, 370, '6-pin', 2, 308, 122, 3, true, 1571.93, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (145, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 10, 399, '16-pin', 1, 292, 129, 3, true, 237.97, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (146, 'AMD', 'RX 7600 Custom Edition', 'RX 7600', 12, 315, '16-pin', 1, 280, 114, 3, false, 875.11, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (147, 'Gigabyte', 'RTX 4080 Custom Edition', 'RTX 4080', 12, 353, '16-pin', 1, 251, 135, 2, false, 1298.81, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (148, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 12, 338, '16-pin', 1, 243, 130, 3, true, 601.65, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (149, 'NVIDIA', 'RX 7600 Custom Edition', 'RX 7600', 20, 277, '6-pin', 1, 315, 102, 3, false, 334.99, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (150, 'ASUS', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 208, '16-pin', 2, 263, 123, 3, false, 920.09, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (151, 'Gigabyte', 'RTX 4070 Custom Edition', 'RTX 4070', 16, 239, '6-pin', 2, 324, 124, 3, true, 1146.67, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (152, 'ASUS', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 12, 434, '8-pin', 1, 265, 131, 2, false, 1381.68, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (153, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 24, 423, '6-pin', 2, 328, 110, 3, false, 781.42, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (154, 'MSI', 'RTX 4060 Custom Edition', 'RTX 4060', 10, 290, '6-pin', 2, 241, 138, 3, false, 1062.13, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (155, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 12, 268, '16-pin', 2, 255, 130, 3, false, 208.86, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (156, 'ASUS', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 20, 160, '16-pin', 1, 321, 107, 3, true, 870.67, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (157, 'Gigabyte', 'RX 7600 Custom Edition', 'RX 7600', 20, 402, '16-pin', 1, 276, 139, 3, false, 807.1, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (158, 'Gigabyte', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 334, '16-pin', 1, 270, 116, 2, true, 446.09, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (159, 'NVIDIA', 'RTX 4060 Custom Edition', 'RTX 4060', 10, 219, '8-pin', 1, 289, 139, 2, false, 1145.57, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (160, 'ASUS', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 203, '8-pin', 1, 305, 126, 3, false, 658.85, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (161, 'NVIDIA', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 24, 395, '16-pin', 2, 257, 101, 3, false, 1289.5, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (162, 'MSI', 'RX 7600 Custom Edition', 'RX 7600', 12, 176, '6-pin', 1, 260, 127, 3, false, 846.55, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (163, 'NVIDIA', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 16, 307, '8-pin', 2, 309, 130, 2, true, 1596.91, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (164, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 16, 409, '6-pin', 1, 319, 135, 3, false, 1503.85, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (165, 'NVIDIA', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 183, '6-pin', 2, 318, 108, 3, false, 613.75, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (166, 'ASUS', 'RTX 4070 Custom Edition', 'RTX 4070', 20, 320, '16-pin', 1, 263, 135, 3, false, 972.84, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (167, 'AMD', 'RTX 4070 Custom Edition', 'RTX 4070', 24, 223, '6-pin', 1, 244, 115, 2, false, 535.78, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (168, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 8, 274, '6-pin', 1, 252, 118, 3, true, 1177.01, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (169, 'NVIDIA', 'RX 7600 Custom Edition', 'RX 7600', 8, 318, '6-pin', 2, 283, 131, 3, false, 1438.69, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (170, 'Gigabyte', 'RX 7600 Custom Edition', 'RX 7600', 20, 277, '16-pin', 1, 300, 137, 3, false, 280.85, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (171, 'NVIDIA', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 201, '6-pin', 1, 242, 134, 2, false, 516.42, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (172, 'MSI', 'RTX 4080 Custom Edition', 'RTX 4080', 24, 215, '8-pin', 1, 329, 119, 2, false, 1079.08, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (173, 'Gigabyte', 'RTX 4060 Custom Edition', 'RTX 4060', 10, 394, '16-pin', 1, 242, 127, 3, false, 1102.97, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (174, 'AMD', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 24, 445, '8-pin', 2, 257, 119, 2, false, 815.23, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (175, 'NVIDIA', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 421, '16-pin', 2, 252, 138, 3, false, 1201.43, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (176, 'NVIDIA', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 293, '6-pin', 2, 244, 117, 2, true, 869.32, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (177, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 12, 186, '6-pin', 2, 283, 122, 3, false, 762.58, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (178, 'NVIDIA', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 16, 194, '6-pin', 1, 254, 117, 3, false, 795.98, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (179, 'ASUS', 'RTX 4080 Custom Edition', 'RTX 4080', 12, 409, '6-pin', 2, 230, 121, 3, true, 726.25, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (180, 'Gigabyte', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 10, 280, '16-pin', 1, 316, 120, 3, true, 811.1, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (181, 'MSI', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 20, 227, '16-pin', 2, 296, 124, 3, true, 828.38, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (182, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 24, 250, '8-pin', 1, 239, 113, 2, false, 756.17, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (183, 'Gigabyte', 'RTX 4060 Custom Edition', 'RTX 4060', 20, 195, '8-pin', 2, 240, 139, 3, false, 385.54, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (184, 'MSI', 'RTX 4070 Custom Edition', 'RTX 4070', 8, 415, '16-pin', 1, 295, 138, 3, true, 1039.09, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (185, 'NVIDIA', 'RX 7600 Custom Edition', 'RX 7600', 12, 178, '6-pin', 2, 248, 132, 3, true, 1177.14, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (186, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 10, 286, '8-pin', 1, 325, 136, 2, true, 1152.87, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (187, 'AMD', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 24, 392, '6-pin', 1, 266, 100, 3, true, 772.02, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (188, 'AMD', 'RTX 4060 Custom Edition', 'RTX 4060', 8, 339, '16-pin', 1, 233, 103, 2, true, 1410.85, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (189, 'AMD', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 10, 347, '16-pin', 2, 320, 123, 2, true, 908.46, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (190, 'ASUS', 'RX 7600 Custom Edition', 'RX 7600', 24, 170, '16-pin', 1, 270, 105, 2, false, 367.33, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (191, 'NVIDIA', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 24, 425, '6-pin', 1, 272, 131, 3, true, 476.84, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (192, 'AMD', 'RX 7900 XTX Custom Edition', 'RX 7900 XTX', 8, 211, '6-pin', 2, 283, 130, 3, false, 448.22, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (193, 'NVIDIA', 'RTX 4080 Custom Edition', 'RTX 4080', 12, 312, '6-pin', 2, 289, 111, 3, false, 593.89, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (194, 'MSI', 'RX 7600 Custom Edition', 'RX 7600', 24, 389, '16-pin', 1, 251, 124, 3, true, 304.58, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (195, 'AMD', 'RX 7600 Custom Edition', 'RX 7600', 20, 234, '16-pin', 2, 235, 130, 3, true, 577.75, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (196, 'MSI', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 20, 392, '16-pin', 2, 328, 139, 3, true, 1269.5, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (197, 'Gigabyte', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 10, 309, '6-pin', 2, 293, 120, 3, true, 1088.49, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (198, 'Gigabyte', 'RX 7600 Custom Edition', 'RX 7600', 20, 402, '6-pin', 2, 306, 132, 3, false, 1297, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (199, 'AMD', 'RX 7700 XT Custom Edition', 'RX 7700 XT', 24, 169, '6-pin', 1, 326, 125, 3, true, 233.36, 'https://example.com/gpu');
INSERT INTO public.gpu VALUES (200, 'Gigabyte', 'RTX 4080 Custom Edition', 'RTX 4080', 20, 299, '6-pin', 1, 261, 124, 2, false, 1071.88, 'https://example.com/gpu');


--
-- Data for Name: motherboard; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.motherboard VALUES (1, 'ASUS', 'A520-214 Pro', 'A520', 'AM4', 'Mini-ITX', 'DDR4', 4, 6000, 1, 2, 4, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 5', '5.1', 4, 2, 1, NULL, 160, 'Debug LED', 151.88, 'https://example.com/asus/a520-214-pro');
INSERT INTO public.motherboard VALUES (2, 'Biostar', 'H410-548 Pro', 'H410', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 5200, 1, 1, 3, 'PCIe 5.0', 6, '2.5GbE', 'Wi-Fi 6', '5.2', 6, 6, 1, 0, 140, 'Debug LED', 342.47, 'https://example.com/biostar/h410-548-pro');
INSERT INTO public.motherboard VALUES (3, 'Gigabyte', 'X570-791 Pro', 'X570', 'AM4', 'Micro-ATX', 'DDR4', 4, 3600, 1, 3, 4, 'PCIe 5.0', 4, '2.5GbE', NULL, '4.2', 2, 6, 2, 2, 120, 'BIOS Flashback', 178.04, 'https://example.com/gigabyte/x570-791-pro');
INSERT INTO public.motherboard VALUES (4, 'Biostar', 'X570-114 Pro', 'X570', 'AM4', 'Micro-ATX', 'DDR4', 4, 4800, 1, 3, 1, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6E', '5.1', 6, 2, 2, 2, 160, 'BIOS Flashback', 103.25, 'https://example.com/biostar/x570-114-pro');
INSERT INTO public.motherboard VALUES (5, 'Gigabyte', 'X670E-243 Pro', 'X670E', 'AM5', 'ATX', 'DDR5', 4, 3600, 1, 3, 2, 'PCIe 5.0', 6, '2.5GbE', NULL, '5.1', 2, 2, 0, 0, 140, NULL, 230.4, 'https://example.com/gigabyte/x670e-243-pro');
INSERT INTO public.motherboard VALUES (6, 'ASRock', 'X570-420 Pro', 'X570', 'AM4', 'Micro-ATX', 'DDR4', 4, 5200, 1, 1, 1, 'PCIe 5.0', 6, '2.5GbE', NULL, '5.2', 4, 4, 1, NULL, 140, 'Debug LED', 300.98, 'https://example.com/asrock/x570-420-pro');
INSERT INTO public.motherboard VALUES (7, 'ASUS', 'H670-662 Pro', 'H670', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 4800, 1, 1, 2, 'PCIe 5.0', 8, '2.5GbE', NULL, '5.0', 4, 4, 0, 0, 120, NULL, 399.2, 'https://example.com/asus/h670-662-pro');
INSERT INTO public.motherboard VALUES (8, 'MSI', 'X670-851 Pro', 'X670', 'AM5', 'Mini-ITX', 'DDR5', 4, 3600, 1, 1, 4, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 6', '5.1', 2, 2, 0, NULL, 120, 'RGB Header', 348.32, 'https://example.com/msi/x670-851-pro');
INSERT INTO public.motherboard VALUES (9, 'Gigabyte', 'B650E-623 Pro', 'B650E', 'AM5', 'Micro-ATX', 'DDR5', 4, 6000, 1, 2, 1, 'PCIe 4.0', 8, '1GbE', NULL, '5.0', 6, 6, 2, NULL, 160, 'Debug LED', 247.95, 'https://example.com/gigabyte/b650e-623-pro');
INSERT INTO public.motherboard VALUES (10, 'Gigabyte', 'B660-922 Pro', 'B660', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 4000, 1, 2, 3, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 6', '5.2', 2, 2, 2, 0, 140, NULL, 296.51, 'https://example.com/gigabyte/b660-922-pro');
INSERT INTO public.motherboard VALUES (11, 'ASUS', 'B650E-513 Pro', 'B650E', 'AM5', 'Mini-ITX', 'DDR5', 4, 3600, 1, 2, 1, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 5', '4.2', 4, 4, 1, NULL, 120, 'BIOS Flashback', 114.8, 'https://example.com/asus/b650e-513-pro');
INSERT INTO public.motherboard VALUES (12, 'Biostar', 'B550-836 Pro', 'B550', 'AM4', 'ATX', 'DDR4', 4, 6000, 1, 2, 1, 'PCIe 5.0', 8, '2.5GbE', NULL, '5.1', 6, 2, 2, NULL, 140, 'RGB Header', 244.38, 'https://example.com/biostar/b550-836-pro');
INSERT INTO public.motherboard VALUES (13, 'MSI', 'Z790-293 Pro', 'Z790', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 6000, 1, 1, 3, 'PCIe 5.0', 6, '2.5GbE', NULL, '5.0', 6, 6, 0, NULL, 160, NULL, 328.62, 'https://example.com/msi/z790-293-pro');
INSERT INTO public.motherboard VALUES (14, 'Gigabyte', 'B660-150 Pro', 'B660', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 4000, 1, 1, 3, 'PCIe 4.0', 4, '2.5GbE', NULL, '5.2', 6, 6, 1, 1, 160, NULL, 384.33, 'https://example.com/gigabyte/b660-150-pro');
INSERT INTO public.motherboard VALUES (15, 'ASUS', 'Z590-860 Pro', 'Z590', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 4000, 1, 1, 3, 'PCIe 4.0', 4, '2.5GbE', 'Wi-Fi 6', NULL, 4, 2, 0, 1, 160, 'Q-Flash', 233.57, 'https://example.com/asus/z590-860-pro');
INSERT INTO public.motherboard VALUES (16, 'Biostar', 'Z590-465 Pro', 'Z590', 'LGA1200', 'ATX', 'DDR4', 4, 3600, 1, 2, 2, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 5', '5.2', 2, 2, 1, NULL, 140, NULL, 348.61, 'https://example.com/biostar/z590-465-pro');
INSERT INTO public.motherboard VALUES (17, 'MSI', 'B460-370 Pro', 'B460', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 6000, 1, 1, 1, 'PCIe 4.0', 8, '1GbE', NULL, '5.1', 6, 2, 0, NULL, 140, 'Q-Flash', 344.1, 'https://example.com/msi/b460-370-pro');
INSERT INTO public.motherboard VALUES (18, 'ASUS', 'X570-102 Pro', 'X570', 'AM4', 'ATX', 'DDR4', 4, 3600, 1, 3, 1, 'PCIe 5.0', 8, '2.5GbE', NULL, '5.0', 4, 4, 1, 2, 140, 'Debug LED', 317.41, 'https://example.com/asus/x570-102-pro');
INSERT INTO public.motherboard VALUES (19, 'MSI', 'X570-631 Pro', 'X570', 'AM4', 'ATX', 'DDR4', 4, 4800, 1, 1, 1, 'PCIe 4.0', 6, '1GbE', NULL, NULL, 2, 2, 0, 0, 140, 'Debug LED', 178.32, 'https://example.com/msi/x570-631-pro');
INSERT INTO public.motherboard VALUES (20, 'Gigabyte', 'Z790-619 Pro', 'Z790', 'LGA1700', 'ATX', 'DDR5', 4, 3200, 1, 1, 3, 'PCIe 5.0', 6, '2.5GbE', 'Wi-Fi 6E', '5.0', 2, 4, 2, 1, 140, 'Q-Flash', 256.56, 'https://example.com/gigabyte/z790-619-pro');
INSERT INTO public.motherboard VALUES (21, 'Biostar', 'Z790-782 Pro', 'Z790', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 4000, 1, 1, 1, 'PCIe 5.0', 8, '1GbE', NULL, '5.2', 2, 6, 1, 0, 160, NULL, 334.23, 'https://example.com/biostar/z790-782-pro');
INSERT INTO public.motherboard VALUES (22, 'MSI', 'B550-491 Pro', 'B550', 'AM4', 'Micro-ATX', 'DDR4', 4, 4000, 1, 2, 1, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6', NULL, 6, 2, 1, 2, 120, NULL, 231.41, 'https://example.com/msi/b550-491-pro');
INSERT INTO public.motherboard VALUES (23, 'ASRock', 'H670-619 Pro', 'H670', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 6000, 1, 1, 1, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 6E', '5.2', 2, 2, 2, 2, 160, 'RGB Header', 264.07, 'https://example.com/asrock/h670-619-pro');
INSERT INTO public.motherboard VALUES (24, 'ASRock', 'Z490-112 Pro', 'Z490', 'LGA1200', 'ATX', 'DDR4', 4, 4000, 1, 1, 3, 'PCIe 5.0', 6, '1GbE', 'Wi-Fi 5', '4.2', 4, 4, 1, NULL, 140, 'BIOS Flashback', 254.15, 'https://example.com/asrock/z490-112-pro');
INSERT INTO public.motherboard VALUES (25, 'Biostar', 'Z490-129 Pro', 'Z490', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 4800, 1, 3, 2, 'PCIe 5.0', 4, '1GbE', 'Wi-Fi 6E', '5.2', 4, 4, 1, 2, 140, 'RGB Header', 105.94, 'https://example.com/biostar/z490-129-pro');
INSERT INTO public.motherboard VALUES (26, 'MSI', 'A520-501 Pro', 'A520', 'AM4', 'Mini-ITX', 'DDR4', 4, 5200, 1, 2, 4, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 6E', '5.1', 2, 6, 1, 0, 120, NULL, 198.62, 'https://example.com/msi/a520-501-pro');
INSERT INTO public.motherboard VALUES (27, 'ASUS', 'B650-356 Pro', 'B650', 'AM5', 'Mini-ITX', 'DDR5', 4, 4000, 1, 1, 1, 'PCIe 4.0', 4, '1GbE', NULL, '5.0', 4, 6, 2, 2, 160, 'Debug LED', 164.54, 'https://example.com/asus/b650-356-pro');
INSERT INTO public.motherboard VALUES (28, 'Gigabyte', 'X570-920 Pro', 'X570', 'AM4', 'ATX', 'DDR4', 4, 5200, 1, 1, 1, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 5', '5.0', 6, 4, 0, 2, 120, 'RGB Header', 102.56, 'https://example.com/gigabyte/x570-920-pro');
INSERT INTO public.motherboard VALUES (29, 'ASUS', 'B650E-350 Pro', 'B650E', 'AM5', 'Micro-ATX', 'DDR5', 4, 4800, 1, 2, 4, 'PCIe 5.0', 4, '2.5GbE', NULL, '5.1', 4, 6, 0, 1, 140, NULL, 311.19, 'https://example.com/asus/b650e-350-pro');
INSERT INTO public.motherboard VALUES (30, 'Biostar', 'A520-182 Pro', 'A520', 'AM4', 'ATX', 'DDR4', 4, 4800, 1, 1, 2, 'PCIe 4.0', 6, '2.5GbE', NULL, '5.2', 4, 6, 2, 2, 120, 'BIOS Flashback', 183.87, 'https://example.com/biostar/a520-182-pro');
INSERT INTO public.motherboard VALUES (31, 'Biostar', 'X670-440 Pro', 'X670', 'AM5', 'Micro-ATX', 'DDR5', 4, 4000, 1, 1, 1, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 5', '5.1', 6, 4, 1, 2, 160, 'Debug LED', 227.46, 'https://example.com/biostar/x670-440-pro');
INSERT INTO public.motherboard VALUES (32, 'ASRock', 'Z590-412 Pro', 'Z590', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 5200, 1, 1, 1, 'PCIe 5.0', 4, '1GbE', 'Wi-Fi 6E', '4.2', 4, 4, 1, 0, 120, 'BIOS Flashback', 116.84, 'https://example.com/asrock/z590-412-pro');
INSERT INTO public.motherboard VALUES (33, 'MSI', 'B550-175 Pro', 'B550', 'AM4', 'Mini-ITX', 'DDR4', 4, 3200, 1, 1, 3, 'PCIe 4.0', 4, '2.5GbE', 'Wi-Fi 6', NULL, 2, 6, 1, 2, 160, 'BIOS Flashback', 294.65, 'https://example.com/msi/b550-175-pro');
INSERT INTO public.motherboard VALUES (34, 'ASUS', 'A520-508 Pro', 'A520', 'AM4', 'Micro-ATX', 'DDR4', 4, 4000, 1, 1, 2, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 6E', '4.2', 4, 6, 2, 1, 120, NULL, 195.12, 'https://example.com/asus/a520-508-pro');
INSERT INTO public.motherboard VALUES (35, 'Gigabyte', 'B550-886 Pro', 'B550', 'AM4', 'Mini-ITX', 'DDR4', 4, 6000, 1, 3, 1, 'PCIe 4.0', 4, '2.5GbE', 'Wi-Fi 5', '5.0', 6, 6, 0, 0, 120, 'BIOS Flashback', 398.86, 'https://example.com/gigabyte/b550-886-pro');
INSERT INTO public.motherboard VALUES (36, 'Gigabyte', 'H670-182 Pro', 'H670', 'LGA1700', 'ATX', 'DDR5', 4, 4000, 1, 2, 4, 'PCIe 4.0', 8, '2.5GbE', NULL, '5.0', 4, 2, 0, 2, 160, 'Q-Flash', 149.01, 'https://example.com/gigabyte/h670-182-pro');
INSERT INTO public.motherboard VALUES (37, 'MSI', 'Z790-628 Pro', 'Z790', 'LGA1700', 'ATX', 'DDR5', 4, 3600, 1, 3, 4, 'PCIe 5.0', 4, '1GbE', NULL, '5.2', 6, 2, 1, NULL, 160, 'Debug LED', 329, 'https://example.com/msi/z790-628-pro');
INSERT INTO public.motherboard VALUES (38, 'Biostar', 'Z790-686 Pro', 'Z790', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 3200, 1, 3, 1, 'PCIe 4.0', 8, '1GbE', NULL, '4.2', 4, 4, 1, 2, 120, 'Q-Flash', 110.14, 'https://example.com/biostar/z790-686-pro');
INSERT INTO public.motherboard VALUES (39, 'ASUS', 'Z790-585 Pro', 'Z790', 'LGA1700', 'ATX', 'DDR5', 4, 4800, 1, 1, 4, 'PCIe 5.0', 6, '1GbE', NULL, '5.0', 2, 6, 1, 1, 140, 'Debug LED', 114.87, 'https://example.com/asus/z790-585-pro');
INSERT INTO public.motherboard VALUES (40, 'ASUS', 'B650-948 Pro', 'B650', 'AM5', 'Mini-ITX', 'DDR5', 4, 3600, 1, 2, 1, 'PCIe 5.0', 8, '1GbE', 'Wi-Fi 6E', NULL, 4, 6, 0, NULL, 120, 'BIOS Flashback', 109.51, 'https://example.com/asus/b650-948-pro');
INSERT INTO public.motherboard VALUES (41, 'ASRock', 'B650E-255 Pro', 'B650E', 'AM5', 'Mini-ITX', 'DDR5', 4, 4800, 1, 2, 4, 'PCIe 5.0', 6, '2.5GbE', 'Wi-Fi 6', '4.2', 2, 4, 1, 0, 160, 'Q-Flash', 229.88, 'https://example.com/asrock/b650e-255-pro');
INSERT INTO public.motherboard VALUES (42, 'Gigabyte', 'Z490-400 Pro', 'Z490', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 4000, 1, 1, 2, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 5', '4.2', 4, 4, 1, 0, 160, 'Debug LED', 256.35, 'https://example.com/gigabyte/z490-400-pro');
INSERT INTO public.motherboard VALUES (43, 'Biostar', 'A520-556 Pro', 'A520', 'AM4', 'ATX', 'DDR4', 4, 5200, 1, 1, 2, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 6E', '4.2', 6, 2, 1, 2, 160, NULL, 311.37, 'https://example.com/biostar/a520-556-pro');
INSERT INTO public.motherboard VALUES (44, 'ASUS', 'B460-128 Pro', 'B460', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 6000, 1, 3, 1, 'PCIe 5.0', 8, '1GbE', 'Wi-Fi 6E', '5.2', 2, 6, 1, 2, 160, 'Debug LED', 392.79, 'https://example.com/asus/b460-128-pro');
INSERT INTO public.motherboard VALUES (45, 'MSI', 'B550-711 Pro', 'B550', 'AM4', 'Mini-ITX', 'DDR4', 4, 5200, 1, 3, 3, 'PCIe 4.0', 4, '2.5GbE', NULL, '4.2', 4, 6, 1, 0, 140, 'Q-Flash', 317.15, 'https://example.com/msi/b550-711-pro');
INSERT INTO public.motherboard VALUES (46, 'Gigabyte', 'X570-307 Pro', 'X570', 'AM4', 'Mini-ITX', 'DDR4', 4, 5200, 1, 3, 3, 'PCIe 4.0', 6, '2.5GbE', NULL, '5.1', 2, 4, 2, 2, 140, 'BIOS Flashback', 95.03, 'https://example.com/gigabyte/x570-307-pro');
INSERT INTO public.motherboard VALUES (47, 'MSI', 'Z490-989 Pro', 'Z490', 'LGA1200', 'ATX', 'DDR4', 4, 4800, 1, 2, 2, 'PCIe 4.0', 8, '2.5GbE', 'Wi-Fi 6E', '4.2', 6, 4, 2, 0, 120, 'BIOS Flashback', 231.16, 'https://example.com/msi/z490-989-pro');
INSERT INTO public.motherboard VALUES (48, 'ASRock', 'Z590-381 Pro', 'Z590', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 3200, 1, 2, 2, 'PCIe 4.0', 8, '1GbE', NULL, '5.1', 2, 2, 2, NULL, 160, 'Q-Flash', 155.89, 'https://example.com/asrock/z590-381-pro');
INSERT INTO public.motherboard VALUES (49, 'ASRock', 'Z490-390 Pro', 'Z490', 'LGA1200', 'ATX', 'DDR4', 4, 3600, 1, 3, 4, 'PCIe 5.0', 4, '2.5GbE', NULL, '5.2', 2, 4, 2, 2, 160, 'BIOS Flashback', 134.79, 'https://example.com/asrock/z490-390-pro');
INSERT INTO public.motherboard VALUES (50, 'ASUS', 'B650-709 Pro', 'B650', 'AM5', 'ATX', 'DDR5', 4, 3200, 1, 1, 1, 'PCIe 4.0', 4, '1GbE', NULL, '4.2', 4, 2, 1, 1, 160, 'Q-Flash', 235.35, 'https://example.com/asus/b650-709-pro');
INSERT INTO public.motherboard VALUES (51, 'MSI', 'B660-715 Pro', 'B660', 'LGA1700', 'ATX', 'DDR5', 4, 6000, 1, 1, 4, 'PCIe 5.0', 6, '1GbE', 'Wi-Fi 5', '4.2', 4, 4, 1, 0, 160, 'Q-Flash', 124.86, 'https://example.com/msi/b660-715-pro');
INSERT INTO public.motherboard VALUES (52, 'ASRock', 'Z490-961 Pro', 'Z490', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 3600, 1, 1, 4, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6', '5.2', 2, 2, 2, 0, 120, 'RGB Header', 215.5, 'https://example.com/asrock/z490-961-pro');
INSERT INTO public.motherboard VALUES (53, 'Gigabyte', 'Z490-448 Pro', 'Z490', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 5200, 1, 3, 4, 'PCIe 5.0', 6, '2.5GbE', 'Wi-Fi 5', '5.0', 2, 2, 1, 1, 160, NULL, 306.21, 'https://example.com/gigabyte/z490-448-pro');
INSERT INTO public.motherboard VALUES (54, 'Biostar', 'Z690-175 Pro', 'Z690', 'LGA1700', 'ATX', 'DDR5', 4, 3200, 1, 2, 4, 'PCIe 5.0', 6, '2.5GbE', 'Wi-Fi 6', NULL, 2, 6, 0, 1, 160, NULL, 378.89, 'https://example.com/biostar/z690-175-pro');
INSERT INTO public.motherboard VALUES (55, 'ASUS', 'B650-475 Pro', 'B650', 'AM5', 'Mini-ITX', 'DDR5', 4, 3200, 1, 2, 3, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 6E', '5.2', 2, 6, 2, 1, 120, NULL, 365.81, 'https://example.com/asus/b650-475-pro');
INSERT INTO public.motherboard VALUES (56, 'MSI', 'B660-577 Pro', 'B660', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 5200, 1, 1, 2, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 5', '5.1', 2, 2, 2, 0, 160, NULL, 245.92, 'https://example.com/msi/b660-577-pro');
INSERT INTO public.motherboard VALUES (57, 'ASRock', 'Z790-581 Pro', 'Z790', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 6000, 1, 1, 1, 'PCIe 5.0', 4, '1GbE', 'Wi-Fi 6E', '4.2', 6, 4, 2, 0, 160, 'RGB Header', 345.59, 'https://example.com/asrock/z790-581-pro');
INSERT INTO public.motherboard VALUES (58, 'ASUS', 'B660-318 Pro', 'B660', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 6000, 1, 2, 2, 'PCIe 4.0', 4, '2.5GbE', 'Wi-Fi 5', '5.2', 2, 2, 0, 0, 160, 'BIOS Flashback', 213.74, 'https://example.com/asus/b660-318-pro');
INSERT INTO public.motherboard VALUES (59, 'MSI', 'A520-760 Pro', 'A520', 'AM4', 'Micro-ATX', 'DDR4', 4, 6000, 1, 3, 4, 'PCIe 4.0', 8, '2.5GbE', 'Wi-Fi 6', '5.2', 2, 6, 2, 1, 120, 'Debug LED', 313.15, 'https://example.com/msi/a520-760-pro');
INSERT INTO public.motherboard VALUES (60, 'Gigabyte', 'Z490-913 Pro', 'Z490', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 4800, 1, 2, 2, 'PCIe 4.0', 4, '2.5GbE', NULL, '5.2', 4, 2, 1, 2, 160, 'Debug LED', 283.97, 'https://example.com/gigabyte/z490-913-pro');
INSERT INTO public.motherboard VALUES (61, 'ASUS', 'B550-181 Pro', 'B550', 'AM4', 'Micro-ATX', 'DDR4', 4, 4000, 1, 1, 4, 'PCIe 4.0', 4, '2.5GbE', 'Wi-Fi 6', '5.0', 6, 4, 2, 1, 140, 'RGB Header', 157.97, 'https://example.com/asus/b550-181-pro');
INSERT INTO public.motherboard VALUES (62, 'ASUS', 'Z790-170 Pro', 'Z790', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 6000, 1, 2, 2, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 6E', '5.0', 4, 2, 1, NULL, 120, 'Debug LED', 399.77, 'https://example.com/asus/z790-170-pro');
INSERT INTO public.motherboard VALUES (63, 'Biostar', 'H410-452 Pro', 'H410', 'LGA1200', 'ATX', 'DDR4', 4, 6000, 1, 2, 1, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6', NULL, 6, 4, 2, NULL, 160, NULL, 321.81, 'https://example.com/biostar/h410-452-pro');
INSERT INTO public.motherboard VALUES (64, 'Biostar', 'B660-341 Pro', 'B660', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 4800, 1, 1, 2, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6', '5.0', 2, 4, 0, 2, 120, 'RGB Header', 287.96, 'https://example.com/biostar/b660-341-pro');
INSERT INTO public.motherboard VALUES (65, 'ASUS', 'X670-760 Pro', 'X670', 'AM5', 'ATX', 'DDR5', 4, 3600, 1, 1, 2, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 6', '5.0', 4, 4, 0, NULL, 140, 'Debug LED', 199.68, 'https://example.com/asus/x670-760-pro');
INSERT INTO public.motherboard VALUES (66, 'MSI', 'Z690-497 Pro', 'Z690', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 3200, 1, 2, 1, 'PCIe 4.0', 4, '2.5GbE', 'Wi-Fi 6', '5.2', 6, 4, 2, 2, 120, NULL, 188.48, 'https://example.com/msi/z690-497-pro');
INSERT INTO public.motherboard VALUES (67, 'ASUS', 'B650E-337 Pro', 'B650E', 'AM5', 'ATX', 'DDR5', 4, 6000, 1, 2, 2, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 5', '5.0', 4, 2, 0, 1, 140, 'Debug LED', 285.59, 'https://example.com/asus/b650e-337-pro');
INSERT INTO public.motherboard VALUES (68, 'Gigabyte', 'H670-535 Pro', 'H670', 'LGA1700', 'ATX', 'DDR5', 4, 5200, 1, 1, 3, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 5', '5.2', 6, 2, 2, 0, 120, NULL, 395.31, 'https://example.com/gigabyte/h670-535-pro');
INSERT INTO public.motherboard VALUES (69, 'ASRock', 'H410-907 Pro', 'H410', 'LGA1200', 'ATX', 'DDR4', 4, 6000, 1, 1, 4, 'PCIe 4.0', 6, '2.5GbE', 'Wi-Fi 6', NULL, 4, 2, 1, NULL, 160, 'Debug LED', 190.65, 'https://example.com/asrock/h410-907-pro');
INSERT INTO public.motherboard VALUES (70, 'MSI', 'X570-377 Pro', 'X570', 'AM4', 'ATX', 'DDR4', 4, 3200, 1, 3, 1, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 6E', '5.2', 2, 6, 0, 1, 140, 'BIOS Flashback', 255.63, 'https://example.com/msi/x570-377-pro');
INSERT INTO public.motherboard VALUES (71, 'ASRock', 'A520-241 Pro', 'A520', 'AM4', 'ATX', 'DDR4', 4, 3200, 1, 1, 1, 'PCIe 4.0', 4, '2.5GbE', NULL, '5.2', 6, 4, 1, 0, 140, 'Debug LED', 180.31, 'https://example.com/asrock/a520-241-pro');
INSERT INTO public.motherboard VALUES (72, 'Gigabyte', 'X670E-814 Pro', 'X670E', 'AM5', 'ATX', 'DDR5', 4, 4800, 1, 2, 2, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6E', '4.2', 4, 4, 2, 2, 120, 'Debug LED', 101.15, 'https://example.com/gigabyte/x670e-814-pro');
INSERT INTO public.motherboard VALUES (73, 'Biostar', 'B460-343 Pro', 'B460', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 6000, 1, 3, 2, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 5', '5.2', 4, 4, 0, 1, 120, 'Debug LED', 317.93, 'https://example.com/biostar/b460-343-pro');
INSERT INTO public.motherboard VALUES (74, 'Biostar', 'B550-389 Pro', 'B550', 'AM4', 'ATX', 'DDR4', 4, 4000, 1, 1, 1, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 5', '4.2', 6, 6, 0, 1, 120, 'BIOS Flashback', 201.03, 'https://example.com/biostar/b550-389-pro');
INSERT INTO public.motherboard VALUES (75, 'Gigabyte', 'X670-519 Pro', 'X670', 'AM5', 'Mini-ITX', 'DDR5', 4, 4800, 1, 2, 1, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6', '5.0', 2, 4, 2, 1, 140, 'RGB Header', 347.95, 'https://example.com/gigabyte/x670-519-pro');
INSERT INTO public.motherboard VALUES (76, 'MSI', 'Z490-930 Pro', 'Z490', 'LGA1200', 'ATX', 'DDR4', 4, 6000, 1, 3, 2, 'PCIe 5.0', 4, '1GbE', 'Wi-Fi 5', '5.1', 2, 6, 0, 0, 120, 'Q-Flash', 324.06, 'https://example.com/msi/z490-930-pro');
INSERT INTO public.motherboard VALUES (77, 'Gigabyte', 'X670E-978 Pro', 'X670E', 'AM5', 'ATX', 'DDR5', 4, 3600, 1, 2, 1, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 5', '4.2', 6, 2, 0, 0, 140, 'RGB Header', 179.64, 'https://example.com/gigabyte/x670e-978-pro');
INSERT INTO public.motherboard VALUES (78, 'Gigabyte', 'Z690-441 Pro', 'Z690', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 4000, 1, 3, 2, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6', '5.2', 6, 4, 1, 0, 140, 'BIOS Flashback', 207.88, 'https://example.com/gigabyte/z690-441-pro');
INSERT INTO public.motherboard VALUES (79, 'ASRock', 'X670E-583 Pro', 'X670E', 'AM5', 'Micro-ATX', 'DDR5', 4, 5200, 1, 1, 2, 'PCIe 5.0', 4, '1GbE', NULL, '5.0', 2, 4, 2, 0, 160, 'RGB Header', 264.54, 'https://example.com/asrock/x670e-583-pro');
INSERT INTO public.motherboard VALUES (80, 'Biostar', 'Z690-467 Pro', 'Z690', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 6000, 1, 3, 3, 'PCIe 4.0', 8, '2.5GbE', 'Wi-Fi 6E', NULL, 2, 2, 0, 0, 120, 'RGB Header', 193.37, 'https://example.com/biostar/z690-467-pro');
INSERT INTO public.motherboard VALUES (81, 'Biostar', 'H410-302 Pro', 'H410', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 5200, 1, 3, 1, 'PCIe 5.0', 6, '2.5GbE', 'Wi-Fi 6E', '4.2', 6, 4, 2, 0, 140, 'RGB Header', 300.9, 'https://example.com/biostar/h410-302-pro');
INSERT INTO public.motherboard VALUES (82, 'ASUS', 'Z690-881 Pro', 'Z690', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 3200, 1, 1, 2, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 6E', '5.2', 2, 4, 1, 2, 160, 'RGB Header', 302.75, 'https://example.com/asus/z690-881-pro');
INSERT INTO public.motherboard VALUES (83, 'ASRock', 'Z690-552 Pro', 'Z690', 'LGA1700', 'ATX', 'DDR5', 4, 6000, 1, 2, 2, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6', '5.0', 2, 4, 0, 2, 120, NULL, 295.19, 'https://example.com/asrock/z690-552-pro');
INSERT INTO public.motherboard VALUES (84, 'ASUS', 'Z790-727 Pro', 'Z790', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 6000, 1, 3, 4, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 5', '5.0', 2, 4, 1, 2, 140, 'Debug LED', 366.65, 'https://example.com/asus/z790-727-pro');
INSERT INTO public.motherboard VALUES (85, 'MSI', 'X670-342 Pro', 'X670', 'AM5', 'Mini-ITX', 'DDR5', 4, 4000, 1, 3, 3, 'PCIe 5.0', 4, '1GbE', NULL, '5.2', 2, 2, 1, 2, 140, 'Debug LED', 173.19, 'https://example.com/msi/x670-342-pro');
INSERT INTO public.motherboard VALUES (86, 'Biostar', 'Z590-627 Pro', 'Z590', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 3200, 1, 1, 1, 'PCIe 5.0', 8, '1GbE', NULL, NULL, 2, 4, 2, NULL, 160, 'BIOS Flashback', 332.07, 'https://example.com/biostar/z590-627-pro');
INSERT INTO public.motherboard VALUES (87, 'Biostar', 'X670E-875 Pro', 'X670E', 'AM5', 'ATX', 'DDR5', 4, 6000, 1, 1, 1, 'PCIe 5.0', 6, '1GbE', 'Wi-Fi 5', '5.0', 6, 2, 0, NULL, 160, 'BIOS Flashback', 365.75, 'https://example.com/biostar/x670e-875-pro');
INSERT INTO public.motherboard VALUES (88, 'Gigabyte', 'H670-367 Pro', 'H670', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 4800, 1, 2, 1, 'PCIe 4.0', 6, '1GbE', 'Wi-Fi 6', '5.0', 4, 6, 0, 1, 160, 'Q-Flash', 130.31, 'https://example.com/gigabyte/h670-367-pro');
INSERT INTO public.motherboard VALUES (89, 'ASUS', 'B650-628 Pro', 'B650', 'AM5', 'Mini-ITX', 'DDR5', 4, 3600, 1, 2, 4, 'PCIe 4.0', 8, '2.5GbE', 'Wi-Fi 6', '4.2', 2, 2, 2, 0, 120, 'Debug LED', 156.36, 'https://example.com/asus/b650-628-pro');
INSERT INTO public.motherboard VALUES (90, 'MSI', 'B460-192 Pro', 'B460', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 4000, 1, 2, 3, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 6E', NULL, 6, 2, 0, 1, 120, 'RGB Header', 189.57, 'https://example.com/msi/b460-192-pro');
INSERT INTO public.motherboard VALUES (91, 'ASUS', 'B660-136 Pro', 'B660', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 3600, 1, 1, 4, 'PCIe 5.0', 8, '1GbE', 'Wi-Fi 6', NULL, 6, 6, 0, 1, 160, NULL, 373.44, 'https://example.com/asus/b660-136-pro');
INSERT INTO public.motherboard VALUES (92, 'Biostar', 'Z590-902 Pro', 'Z590', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 3200, 1, 3, 1, 'PCIe 4.0', 6, '2.5GbE', 'Wi-Fi 5', '5.2', 2, 6, 0, NULL, 120, 'BIOS Flashback', 140, 'https://example.com/biostar/z590-902-pro');
INSERT INTO public.motherboard VALUES (93, 'ASUS', 'B660-805 Pro', 'B660', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 4000, 1, 3, 3, 'PCIe 4.0', 6, '2.5GbE', 'Wi-Fi 6', '4.2', 4, 6, 0, NULL, 120, 'Q-Flash', 254.4, 'https://example.com/asus/b660-805-pro');
INSERT INTO public.motherboard VALUES (94, 'Gigabyte', 'B650E-953 Pro', 'B650E', 'AM5', 'Micro-ATX', 'DDR5', 4, 3600, 1, 3, 2, 'PCIe 4.0', 4, '2.5GbE', 'Wi-Fi 5', NULL, 6, 6, 2, 2, 160, 'BIOS Flashback', 115.77, 'https://example.com/gigabyte/b650e-953-pro');
INSERT INTO public.motherboard VALUES (95, 'MSI', 'B550-715 Pro', 'B550', 'AM4', 'Mini-ITX', 'DDR4', 4, 5200, 1, 3, 1, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 6E', '5.1', 4, 2, 0, 2, 140, 'Q-Flash', 274.43, 'https://example.com/msi/b550-715-pro');
INSERT INTO public.motherboard VALUES (96, 'ASUS', 'Z690-200 Pro', 'Z690', 'LGA1700', 'ATX', 'DDR5', 4, 6000, 1, 3, 1, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 6E', '5.2', 6, 4, 0, 0, 160, 'Q-Flash', 163.08, 'https://example.com/asus/z690-200-pro');
INSERT INTO public.motherboard VALUES (97, 'MSI', 'A520-874 Pro', 'A520', 'AM4', 'ATX', 'DDR4', 4, 6000, 1, 1, 2, 'PCIe 5.0', 6, '1GbE', NULL, NULL, 6, 4, 0, 0, 140, 'Q-Flash', 172.42, 'https://example.com/msi/a520-874-pro');
INSERT INTO public.motherboard VALUES (98, 'MSI', 'B660-514 Pro', 'B660', 'LGA1700', 'ATX', 'DDR5', 4, 6000, 1, 3, 3, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 5', '5.2', 4, 2, 0, 2, 140, 'Q-Flash', 338.72, 'https://example.com/msi/b660-514-pro');
INSERT INTO public.motherboard VALUES (99, 'Biostar', 'B550-825 Pro', 'B550', 'AM4', 'Micro-ATX', 'DDR4', 4, 3600, 1, 3, 3, 'PCIe 5.0', 6, '1GbE', 'Wi-Fi 6E', NULL, 4, 2, 1, 0, 160, 'BIOS Flashback', 315.77, 'https://example.com/biostar/b550-825-pro');
INSERT INTO public.motherboard VALUES (100, 'ASUS', 'B550-332 Pro', 'B550', 'AM4', 'ATX', 'DDR4', 4, 4000, 1, 3, 3, 'PCIe 4.0', 8, '1GbE', NULL, NULL, 2, 6, 0, 1, 120, 'Debug LED', 312.82, 'https://example.com/asus/b550-332-pro');
INSERT INTO public.motherboard VALUES (101, 'MSI', 'H670-582 Pro', 'H670', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 3200, 1, 1, 3, 'PCIe 4.0', 6, '2.5GbE', 'Wi-Fi 6', '4.2', 2, 6, 2, 1, 120, 'BIOS Flashback', 313, 'https://example.com/msi/h670-582-pro');
INSERT INTO public.motherboard VALUES (102, 'ASRock', 'Z690-836 Pro', 'Z690', 'LGA1700', 'ATX', 'DDR5', 4, 5200, 1, 1, 3, 'PCIe 5.0', 6, '2.5GbE', NULL, '5.1', 6, 4, 1, 1, 160, 'Q-Flash', 280.23, 'https://example.com/asrock/z690-836-pro');
INSERT INTO public.motherboard VALUES (103, 'MSI', 'X570-753 Pro', 'X570', 'AM4', 'Mini-ITX', 'DDR4', 4, 4800, 1, 2, 4, 'PCIe 5.0', 6, '1GbE', 'Wi-Fi 6E', '5.0', 2, 4, 1, 2, 120, 'Debug LED', 93.6, 'https://example.com/msi/x570-753-pro');
INSERT INTO public.motherboard VALUES (104, 'Gigabyte', 'B650-205 Pro', 'B650', 'AM5', 'ATX', 'DDR5', 4, 3200, 1, 1, 1, 'PCIe 4.0', 8, '2.5GbE', NULL, '5.0', 6, 4, 1, 1, 120, 'RGB Header', 398.58, 'https://example.com/gigabyte/b650-205-pro');
INSERT INTO public.motherboard VALUES (105, 'Biostar', 'A520-351 Pro', 'A520', 'AM4', 'Mini-ITX', 'DDR4', 4, 5200, 1, 1, 2, 'PCIe 4.0', 8, '2.5GbE', 'Wi-Fi 6', '5.1', 6, 4, 2, 2, 160, 'RGB Header', 303.54, 'https://example.com/biostar/a520-351-pro');
INSERT INTO public.motherboard VALUES (106, 'Biostar', 'Z790-991 Pro', 'Z790', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 3200, 1, 2, 4, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 6', '5.1', 6, 6, 2, NULL, 160, NULL, 366.89, 'https://example.com/biostar/z790-991-pro');
INSERT INTO public.motherboard VALUES (107, 'ASUS', 'H410-884 Pro', 'H410', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 5200, 1, 2, 3, 'PCIe 5.0', 6, '1GbE', 'Wi-Fi 5', '5.1', 4, 6, 0, 1, 160, 'RGB Header', 129.06, 'https://example.com/asus/h410-884-pro');
INSERT INTO public.motherboard VALUES (108, 'ASRock', 'B460-855 Pro', 'B460', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 5200, 1, 3, 1, 'PCIe 5.0', 6, '1GbE', 'Wi-Fi 6E', '5.0', 2, 2, 2, 2, 120, 'RGB Header', 230.78, 'https://example.com/asrock/b460-855-pro');
INSERT INTO public.motherboard VALUES (109, 'Biostar', 'Z490-695 Pro', 'Z490', 'LGA1200', 'Mini-ITX', 'DDR4', 4, 3200, 1, 1, 3, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 6', '5.1', 4, 2, 2, NULL, 140, 'RGB Header', 320.08, 'https://example.com/biostar/z490-695-pro');
INSERT INTO public.motherboard VALUES (110, 'MSI', 'Z590-365 Pro', 'Z590', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 3200, 1, 1, 4, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 6E', '5.0', 6, 6, 0, 0, 120, 'Q-Flash', 312.67, 'https://example.com/msi/z590-365-pro');
INSERT INTO public.motherboard VALUES (111, 'ASUS', 'X570-204 Pro', 'X570', 'AM4', 'Micro-ATX', 'DDR4', 4, 6000, 1, 2, 1, 'PCIe 4.0', 8, '2.5GbE', 'Wi-Fi 6', '5.0', 4, 4, 0, NULL, 140, NULL, 183.52, 'https://example.com/asus/x570-204-pro');
INSERT INTO public.motherboard VALUES (112, 'ASUS', 'B460-564 Pro', 'B460', 'LGA1200', 'ATX', 'DDR4', 4, 3200, 1, 3, 1, 'PCIe 4.0', 6, '2.5GbE', 'Wi-Fi 6E', '4.2', 2, 6, 0, 0, 160, 'BIOS Flashback', 117.71, 'https://example.com/asus/b460-564-pro');
INSERT INTO public.motherboard VALUES (113, 'MSI', 'B550-241 Pro', 'B550', 'AM4', 'Micro-ATX', 'DDR4', 4, 6000, 1, 1, 4, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 5', '5.2', 4, 6, 0, NULL, 120, 'Debug LED', 392.63, 'https://example.com/msi/b550-241-pro');
INSERT INTO public.motherboard VALUES (114, 'ASUS', 'B660-110 Pro', 'B660', 'LGA1700', 'ATX', 'DDR5', 4, 6000, 1, 2, 4, 'PCIe 5.0', 8, '1GbE', 'Wi-Fi 6', '5.0', 6, 2, 0, NULL, 160, NULL, 150.91, 'https://example.com/asus/b660-110-pro');
INSERT INTO public.motherboard VALUES (115, 'Biostar', 'B650E-658 Pro', 'B650E', 'AM5', 'ATX', 'DDR5', 4, 3200, 1, 2, 3, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 6E', '4.2', 2, 6, 1, 2, 120, 'Q-Flash', 341.2, 'https://example.com/biostar/b650e-658-pro');
INSERT INTO public.motherboard VALUES (116, 'Gigabyte', 'H410-342 Pro', 'H410', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 3200, 1, 1, 3, 'PCIe 4.0', 6, '2.5GbE', 'Wi-Fi 5', NULL, 6, 4, 0, 1, 140, 'Debug LED', 356.73, 'https://example.com/gigabyte/h410-342-pro');
INSERT INTO public.motherboard VALUES (117, 'Gigabyte', 'X570-909 Pro', 'X570', 'AM4', 'Mini-ITX', 'DDR4', 4, 3200, 1, 3, 4, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 6E', '5.2', 6, 2, 0, 0, 140, 'RGB Header', 314.47, 'https://example.com/gigabyte/x570-909-pro');
INSERT INTO public.motherboard VALUES (118, 'ASUS', 'A520-496 Pro', 'A520', 'AM4', 'ATX', 'DDR4', 4, 3600, 1, 1, 2, 'PCIe 5.0', 6, '2.5GbE', NULL, '5.2', 4, 6, 1, NULL, 140, 'BIOS Flashback', 301.08, 'https://example.com/asus/a520-496-pro');
INSERT INTO public.motherboard VALUES (119, 'ASRock', 'Z590-192 Pro', 'Z590', 'LGA1200', 'ATX', 'DDR4', 4, 4800, 1, 1, 2, 'PCIe 4.0', 4, '1GbE', NULL, '5.2', 6, 6, 2, 0, 160, 'BIOS Flashback', 344.74, 'https://example.com/asrock/z590-192-pro');
INSERT INTO public.motherboard VALUES (120, 'Gigabyte', 'Z690-830 Pro', 'Z690', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 5200, 1, 1, 1, 'PCIe 4.0', 6, '2.5GbE', NULL, '5.1', 2, 4, 1, 1, 160, NULL, 164.53, 'https://example.com/gigabyte/z690-830-pro');
INSERT INTO public.motherboard VALUES (121, 'Biostar', 'Z590-720 Pro', 'Z590', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 4000, 1, 2, 3, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 5', '5.1', 2, 4, 1, 0, 140, 'Debug LED', 353.68, 'https://example.com/biostar/z590-720-pro');
INSERT INTO public.motherboard VALUES (122, 'Gigabyte', 'A520-140 Pro', 'A520', 'AM4', 'ATX', 'DDR4', 4, 5200, 1, 2, 2, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 5', '5.0', 6, 6, 1, 1, 120, 'Debug LED', 176.26, 'https://example.com/gigabyte/a520-140-pro');
INSERT INTO public.motherboard VALUES (123, 'MSI', 'B650E-702 Pro', 'B650E', 'AM5', 'ATX', 'DDR5', 4, 4000, 1, 2, 2, 'PCIe 5.0', 6, '2.5GbE', 'Wi-Fi 6', '5.2', 6, 4, 2, 0, 140, 'RGB Header', 230.92, 'https://example.com/msi/b650e-702-pro');
INSERT INTO public.motherboard VALUES (124, 'ASRock', 'B650-384 Pro', 'B650', 'AM5', 'Mini-ITX', 'DDR5', 4, 3200, 1, 2, 4, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 6', '5.2', 2, 2, 2, 2, 120, 'Debug LED', 303.68, 'https://example.com/asrock/b650-384-pro');
INSERT INTO public.motherboard VALUES (125, 'ASRock', 'X570-525 Pro', 'X570', 'AM4', 'Micro-ATX', 'DDR4', 4, 3200, 1, 1, 4, 'PCIe 4.0', 8, '2.5GbE', 'Wi-Fi 6E', '5.2', 4, 4, 2, 0, 160, 'Q-Flash', 242.56, 'https://example.com/asrock/x570-525-pro');
INSERT INTO public.motherboard VALUES (126, 'Gigabyte', 'Z590-548 Pro', 'Z590', 'LGA1200', 'ATX', 'DDR4', 4, 6000, 1, 1, 3, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 6', '5.1', 2, 2, 2, 1, 160, 'BIOS Flashback', 323.78, 'https://example.com/gigabyte/z590-548-pro');
INSERT INTO public.motherboard VALUES (127, 'ASRock', 'B650E-367 Pro', 'B650E', 'AM5', 'Mini-ITX', 'DDR5', 4, 4800, 1, 2, 4, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 5', '4.2', 6, 2, 0, NULL, 160, 'Debug LED', 151.45, 'https://example.com/asrock/b650e-367-pro');
INSERT INTO public.motherboard VALUES (128, 'ASUS', 'X670E-418 Pro', 'X670E', 'AM5', 'Micro-ATX', 'DDR5', 4, 4000, 1, 3, 1, 'PCIe 4.0', 4, '1GbE', NULL, '4.2', 4, 6, 2, 0, 140, 'RGB Header', 177.96, 'https://example.com/asus/x670e-418-pro');
INSERT INTO public.motherboard VALUES (129, 'MSI', 'B460-346 Pro', 'B460', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 3600, 1, 1, 2, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 6E', '5.0', 2, 4, 1, NULL, 140, 'BIOS Flashback', 146.78, 'https://example.com/msi/b460-346-pro');
INSERT INTO public.motherboard VALUES (130, 'ASUS', 'B460-441 Pro', 'B460', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 3600, 1, 1, 2, 'PCIe 5.0', 8, '1GbE', NULL, '4.2', 2, 6, 2, NULL, 160, 'BIOS Flashback', 264.14, 'https://example.com/asus/b460-441-pro');
INSERT INTO public.motherboard VALUES (131, 'ASRock', 'H670-247 Pro', 'H670', 'LGA1700', 'Micro-ATX', 'DDR5', 4, 3600, 1, 3, 2, 'PCIe 4.0', 6, '2.5GbE', NULL, '4.2', 4, 6, 1, 2, 120, 'Debug LED', 308, 'https://example.com/asrock/h670-247-pro');
INSERT INTO public.motherboard VALUES (132, 'Gigabyte', 'X670-762 Pro', 'X670', 'AM5', 'Micro-ATX', 'DDR5', 4, 4800, 1, 3, 2, 'PCIe 4.0', 6, '1GbE', NULL, '5.2', 4, 6, 2, NULL, 140, NULL, 199.78, 'https://example.com/gigabyte/x670-762-pro');
INSERT INTO public.motherboard VALUES (133, 'MSI', 'B660-417 Pro', 'B660', 'LGA1700', 'ATX', 'DDR5', 4, 3600, 1, 1, 3, 'PCIe 5.0', 4, '2.5GbE', 'Wi-Fi 5', '5.2', 4, 6, 1, 0, 160, 'Debug LED', 313.36, 'https://example.com/msi/b660-417-pro');
INSERT INTO public.motherboard VALUES (134, 'ASRock', 'Z790-549 Pro', 'Z790', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 4000, 1, 1, 4, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 6E', '4.2', 2, 2, 2, 1, 140, NULL, 142.24, 'https://example.com/asrock/z790-549-pro');
INSERT INTO public.motherboard VALUES (135, 'Biostar', 'X670-495 Pro', 'X670', 'AM5', 'Mini-ITX', 'DDR5', 4, 5200, 1, 2, 2, 'PCIe 5.0', 4, '1GbE', NULL, '5.0', 2, 6, 0, 0, 160, 'Q-Flash', 113.07, 'https://example.com/biostar/x670-495-pro');
INSERT INTO public.motherboard VALUES (136, 'ASUS', 'B650E-843 Pro', 'B650E', 'AM5', 'ATX', 'DDR5', 4, 4000, 1, 3, 1, 'PCIe 5.0', 8, '1GbE', 'Wi-Fi 6', '5.0', 4, 2, 2, 0, 140, 'Q-Flash', 355.8, 'https://example.com/asus/b650e-843-pro');
INSERT INTO public.motherboard VALUES (137, 'ASUS', 'H410-859 Pro', 'H410', 'LGA1200', 'Micro-ATX', 'DDR4', 4, 6000, 1, 2, 2, 'PCIe 5.0', 4, '1GbE', 'Wi-Fi 6E', '5.0', 2, 2, 1, 2, 120, NULL, 132.67, 'https://example.com/asus/h410-859-pro');
INSERT INTO public.motherboard VALUES (138, 'ASUS', 'B660-734 Pro', 'B660', 'LGA1700', 'ATX', 'DDR5', 4, 5200, 1, 3, 4, 'PCIe 5.0', 4, '1GbE', 'Wi-Fi 6', '5.1', 4, 6, 1, 2, 120, 'Q-Flash', 211.85, 'https://example.com/asus/b660-734-pro');
INSERT INTO public.motherboard VALUES (139, 'ASUS', 'X670E-343 Pro', 'X670E', 'AM5', 'ATX', 'DDR5', 4, 3600, 1, 2, 1, 'PCIe 4.0', 8, '2.5GbE', NULL, '5.0', 2, 2, 1, 0, 120, 'RGB Header', 299.94, 'https://example.com/asus/x670e-343-pro');
INSERT INTO public.motherboard VALUES (140, 'MSI', 'B650-839 Pro', 'B650', 'AM5', 'Micro-ATX', 'DDR5', 4, 3600, 1, 1, 4, 'PCIe 5.0', 8, '2.5GbE', NULL, '4.2', 2, 2, 0, NULL, 160, 'Debug LED', 370.08, 'https://example.com/msi/b650-839-pro');
INSERT INTO public.motherboard VALUES (141, 'MSI', 'B650E-645 Pro', 'B650E', 'AM5', 'ATX', 'DDR5', 4, 3600, 1, 3, 2, 'PCIe 4.0', 6, '2.5GbE', 'Wi-Fi 6', '5.2', 4, 4, 1, NULL, 160, 'RGB Header', 206.73, 'https://example.com/msi/b650e-645-pro');
INSERT INTO public.motherboard VALUES (142, 'ASRock', 'X670-770 Pro', 'X670', 'AM5', 'Micro-ATX', 'DDR5', 4, 5200, 1, 3, 3, 'PCIe 5.0', 6, '1GbE', NULL, '5.1', 2, 6, 0, 0, 160, 'Debug LED', 374.46, 'https://example.com/asrock/x670-770-pro');
INSERT INTO public.motherboard VALUES (143, 'MSI', 'B650-737 Pro', 'B650', 'AM5', 'ATX', 'DDR5', 4, 4000, 1, 3, 3, 'PCIe 5.0', 4, '2.5GbE', NULL, NULL, 6, 4, 1, 0, 160, 'Q-Flash', 158.97, 'https://example.com/msi/b650-737-pro');
INSERT INTO public.motherboard VALUES (144, 'ASUS', 'H670-577 Pro', 'H670', 'LGA1700', 'ATX', 'DDR5', 4, 6000, 1, 2, 4, 'PCIe 4.0', 6, '2.5GbE', NULL, '4.2', 2, 4, 2, 1, 140, 'Debug LED', 221.17, 'https://example.com/asus/h670-577-pro');
INSERT INTO public.motherboard VALUES (145, 'Gigabyte', 'A520-995 Pro', 'A520', 'AM4', 'ATX', 'DDR4', 4, 4800, 1, 2, 1, 'PCIe 4.0', 6, '2.5GbE', 'Wi-Fi 5', NULL, 4, 6, 0, NULL, 160, 'BIOS Flashback', 296.8, 'https://example.com/gigabyte/a520-995-pro');
INSERT INTO public.motherboard VALUES (146, 'MSI', 'Z690-721 Pro', 'Z690', 'LGA1700', 'Mini-ITX', 'DDR5', 4, 3600, 1, 3, 1, 'PCIe 4.0', 4, '1GbE', 'Wi-Fi 6E', '5.2', 4, 6, 1, 2, 140, 'BIOS Flashback', 283.32, 'https://example.com/msi/z690-721-pro');
INSERT INTO public.motherboard VALUES (147, 'ASRock', 'B650E-527 Pro', 'B650E', 'AM5', 'Micro-ATX', 'DDR5', 4, 6000, 1, 1, 4, 'PCIe 4.0', 8, '1GbE', 'Wi-Fi 5', '4.2', 2, 4, 1, 0, 140, 'RGB Header', 229.13, 'https://example.com/asrock/b650e-527-pro');
INSERT INTO public.motherboard VALUES (148, 'Gigabyte', 'H670-411 Pro', 'H670', 'LGA1700', 'ATX', 'DDR5', 4, 3600, 1, 2, 3, 'PCIe 5.0', 8, '2.5GbE', 'Wi-Fi 6E', '5.1', 6, 2, 0, 1, 120, 'BIOS Flashback', 248.88, 'https://example.com/gigabyte/h670-411-pro');
INSERT INTO public.motherboard VALUES (149, 'MSI', 'X570-456 Pro', 'X570', 'AM4', 'Mini-ITX', 'DDR4', 4, 4800, 1, 3, 4, 'PCIe 4.0', 6, '2.5GbE', NULL, '5.2', 4, 4, 1, 1, 160, 'RGB Header', 206.19, 'https://example.com/msi/x570-456-pro');
INSERT INTO public.motherboard VALUES (150, 'MSI', 'B650E-678 Pro', 'B650E', 'AM5', 'Mini-ITX', 'DDR5', 4, 3600, 1, 1, 1, 'PCIe 5.0', 6, '1GbE', 'Wi-Fi 6', '5.2', 6, 2, 1, 1, 140, 'Debug LED', 215.37, 'https://example.com/msi/b650e-678-pro');


--
-- Data for Name: psu; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.psu VALUES (1, 'Thermaltake', 'PSU-1034W', 'ATX', 750, 180, 1, 1, 0, '80+ Gold', true, 94.05, 'https://example.com/psu');
INSERT INTO public.psu VALUES (2, 'Cooler Master', 'PSU-581W', 'ATX', 550, 180, 2, 1, 1, '80+ Silver', true, 86.94, 'https://example.com/psu');
INSERT INTO public.psu VALUES (3, 'Cooler Master', 'PSU-1181W', 'ATX', 1200, 160, 1, 4, 2, '80+ Silver', true, 185.45, 'https://example.com/psu');
INSERT INTO public.psu VALUES (4, 'Corsair', 'PSU-625W', 'ATX', 850, 140, 1, 1, 1, '80+ Silver', false, 215.05, 'https://example.com/psu');
INSERT INTO public.psu VALUES (5, 'Cooler Master', 'PSU-510W', 'SFX', 1200, 180, 1, 1, 2, '80+ Silver', false, 254.01, 'https://example.com/psu');
INSERT INTO public.psu VALUES (6, 'Corsair', 'PSU-855W', 'ATX', 750, 160, 1, 4, 1, '80+ Gold', true, 246.14, 'https://example.com/psu');
INSERT INTO public.psu VALUES (7, 'Seasonic', 'PSU-1081W', 'SFX', 550, 160, 2, 3, 2, '80+ Bronze', true, 193.23, 'https://example.com/psu');
INSERT INTO public.psu VALUES (8, 'EVGA', 'PSU-548W', 'SFX', 1000, 180, 1, 1, 2, '80+ Gold', true, 130.78, 'https://example.com/psu');
INSERT INTO public.psu VALUES (9, 'Corsair', 'PSU-928W', 'ATX', 650, 140, 1, 1, 0, '80+ Bronze', true, 91.71, 'https://example.com/psu');
INSERT INTO public.psu VALUES (10, 'EVGA', 'PSU-663W', 'ATX', 850, 140, 1, 4, 0, '80+ Platinum', true, 87.58, 'https://example.com/psu');
INSERT INTO public.psu VALUES (11, 'Seasonic', 'PSU-954W', 'SFX', 650, 140, 1, 3, 2, '80+ Platinum', false, 156.15, 'https://example.com/psu');
INSERT INTO public.psu VALUES (12, 'Thermaltake', 'PSU-869W', 'ATX', 1200, 160, 2, 1, 2, '80+ Gold', false, 149.01, 'https://example.com/psu');
INSERT INTO public.psu VALUES (13, 'EVGA', 'PSU-1130W', 'ATX', 1200, 140, 2, 4, 2, '80+ Gold', true, 126.19, 'https://example.com/psu');
INSERT INTO public.psu VALUES (14, 'Seasonic', 'PSU-1192W', 'ATX', 1200, 180, 1, 2, 0, '80+ Bronze', true, 237.4, 'https://example.com/psu');
INSERT INTO public.psu VALUES (15, 'Corsair', 'PSU-1035W', 'ATX', 1000, 140, 2, 4, 2, '80+ Gold', true, 268.65, 'https://example.com/psu');
INSERT INTO public.psu VALUES (16, 'Thermaltake', 'PSU-555W', 'ATX', 1000, 160, 1, 3, 2, '80+ Bronze', true, 265.78, 'https://example.com/psu');
INSERT INTO public.psu VALUES (17, 'Cooler Master', 'PSU-873W', 'ATX', 550, 180, 2, 1, 0, '80+ Platinum', true, 79.56, 'https://example.com/psu');
INSERT INTO public.psu VALUES (18, 'Thermaltake', 'PSU-847W', 'SFX', 1000, 140, 1, 4, 1, '80+ Silver', true, 194.55, 'https://example.com/psu');
INSERT INTO public.psu VALUES (19, 'Corsair', 'PSU-778W', 'SFX', 750, 140, 2, 4, 0, '80+ Platinum', true, 89.44, 'https://example.com/psu');
INSERT INTO public.psu VALUES (20, 'Thermaltake', 'PSU-691W', 'SFX', 650, 180, 2, 3, 0, '80+ Gold', true, 108.8, 'https://example.com/psu');
INSERT INTO public.psu VALUES (21, 'Cooler Master', 'PSU-1124W', 'SFX', 850, 160, 2, 3, 1, '80+ Gold', true, 249.21, 'https://example.com/psu');
INSERT INTO public.psu VALUES (22, 'EVGA', 'PSU-978W', 'ATX', 650, 180, 2, 1, 0, '80+ Gold', true, 226.87, 'https://example.com/psu');
INSERT INTO public.psu VALUES (23, 'Thermaltake', 'PSU-524W', 'ATX', 750, 180, 2, 2, 0, '80+ Silver', false, 97.5, 'https://example.com/psu');
INSERT INTO public.psu VALUES (24, 'Cooler Master', 'PSU-735W', 'ATX', 550, 180, 2, 4, 0, '80+ Bronze', true, 139.55, 'https://example.com/psu');
INSERT INTO public.psu VALUES (25, 'Thermaltake', 'PSU-1155W', 'SFX', 550, 180, 2, 4, 0, '80+ Gold', false, 118.85, 'https://example.com/psu');
INSERT INTO public.psu VALUES (26, 'Thermaltake', 'PSU-580W', 'SFX', 850, 180, 2, 2, 2, '80+ Gold', true, 162.35, 'https://example.com/psu');
INSERT INTO public.psu VALUES (27, 'Seasonic', 'PSU-622W', 'SFX', 550, 160, 1, 4, 0, '80+ Silver', true, 128.7, 'https://example.com/psu');
INSERT INTO public.psu VALUES (28, 'Cooler Master', 'PSU-835W', 'SFX', 550, 140, 2, 1, 2, '80+ Silver', true, 222.3, 'https://example.com/psu');
INSERT INTO public.psu VALUES (29, 'Cooler Master', 'PSU-988W', 'SFX', 1200, 180, 2, 3, 0, '80+ Bronze', false, 200.78, 'https://example.com/psu');
INSERT INTO public.psu VALUES (30, 'Thermaltake', 'PSU-842W', 'SFX', 1000, 180, 2, 3, 1, '80+ Gold', true, 167.69, 'https://example.com/psu');
INSERT INTO public.psu VALUES (31, 'EVGA', 'PSU-791W', 'SFX', 1200, 180, 2, 4, 1, '80+ Silver', true, 197.89, 'https://example.com/psu');
INSERT INTO public.psu VALUES (32, 'EVGA', 'PSU-1043W', 'ATX', 550, 180, 1, 2, 1, '80+ Bronze', true, 141.62, 'https://example.com/psu');
INSERT INTO public.psu VALUES (33, 'Corsair', 'PSU-811W', 'ATX', 1000, 160, 1, 3, 1, '80+ Platinum', true, 289.72, 'https://example.com/psu');
INSERT INTO public.psu VALUES (34, 'Corsair', 'PSU-624W', 'SFX', 1200, 160, 2, 4, 1, '80+ Silver', false, 197.02, 'https://example.com/psu');
INSERT INTO public.psu VALUES (35, 'Corsair', 'PSU-732W', 'SFX', 1000, 140, 2, 4, 2, '80+ Gold', true, 170.95, 'https://example.com/psu');
INSERT INTO public.psu VALUES (36, 'Thermaltake', 'PSU-590W', 'SFX', 850, 140, 2, 3, 2, '80+ Bronze', true, 155.18, 'https://example.com/psu');
INSERT INTO public.psu VALUES (37, 'Corsair', 'PSU-810W', 'SFX', 750, 160, 2, 1, 0, '80+ Gold', false, 123.22, 'https://example.com/psu');
INSERT INTO public.psu VALUES (38, 'Thermaltake', 'PSU-581W', 'SFX', 650, 180, 2, 1, 0, '80+ Silver', false, 174.59, 'https://example.com/psu');
INSERT INTO public.psu VALUES (39, 'EVGA', 'PSU-1129W', 'SFX', 1200, 160, 1, 4, 1, '80+ Bronze', false, 88.35, 'https://example.com/psu');
INSERT INTO public.psu VALUES (40, 'Seasonic', 'PSU-950W', 'ATX', 1200, 160, 2, 3, 1, '80+ Gold', false, 262.63, 'https://example.com/psu');
INSERT INTO public.psu VALUES (41, 'Cooler Master', 'PSU-704W', 'ATX', 1200, 180, 1, 4, 1, '80+ Silver', false, 161.8, 'https://example.com/psu');
INSERT INTO public.psu VALUES (42, 'Thermaltake', 'PSU-1156W', 'SFX', 850, 180, 1, 2, 1, '80+ Bronze', true, 150.35, 'https://example.com/psu');
INSERT INTO public.psu VALUES (43, 'Corsair', 'PSU-1068W', 'ATX', 650, 160, 2, 2, 2, '80+ Platinum', false, 67.4, 'https://example.com/psu');
INSERT INTO public.psu VALUES (44, 'EVGA', 'PSU-927W', 'ATX', 1200, 140, 1, 4, 1, '80+ Platinum', false, 91.15, 'https://example.com/psu');
INSERT INTO public.psu VALUES (45, 'Cooler Master', 'PSU-682W', 'SFX', 550, 180, 2, 3, 2, '80+ Platinum', true, 249.45, 'https://example.com/psu');
INSERT INTO public.psu VALUES (46, 'Seasonic', 'PSU-1070W', 'ATX', 650, 180, 2, 3, 0, '80+ Platinum', true, 261.09, 'https://example.com/psu');
INSERT INTO public.psu VALUES (47, 'Thermaltake', 'PSU-678W', 'ATX', 550, 140, 1, 1, 1, '80+ Bronze', false, 243.64, 'https://example.com/psu');
INSERT INTO public.psu VALUES (48, 'Cooler Master', 'PSU-712W', 'SFX', 1000, 180, 1, 2, 2, '80+ Platinum', true, 203.43, 'https://example.com/psu');
INSERT INTO public.psu VALUES (49, 'Thermaltake', 'PSU-987W', 'SFX', 1200, 160, 2, 3, 0, '80+ Bronze', true, 193.49, 'https://example.com/psu');
INSERT INTO public.psu VALUES (50, 'Cooler Master', 'PSU-636W', 'ATX', 650, 180, 1, 2, 2, '80+ Silver', false, 173.31, 'https://example.com/psu');
INSERT INTO public.psu VALUES (51, 'Corsair', 'PSU-630W', 'SFX', 550, 140, 1, 4, 2, '80+ Platinum', true, 255.09, 'https://example.com/psu');
INSERT INTO public.psu VALUES (52, 'Cooler Master', 'PSU-1150W', 'ATX', 650, 160, 2, 1, 1, '80+ Gold', true, 63.35, 'https://example.com/psu');
INSERT INTO public.psu VALUES (53, 'Corsair', 'PSU-1144W', 'ATX', 750, 140, 2, 3, 2, '80+ Bronze', true, 213.77, 'https://example.com/psu');
INSERT INTO public.psu VALUES (54, 'Thermaltake', 'PSU-552W', 'SFX', 550, 140, 2, 3, 0, '80+ Platinum', true, 187.19, 'https://example.com/psu');
INSERT INTO public.psu VALUES (55, 'Corsair', 'PSU-691W', 'SFX', 850, 160, 1, 2, 1, '80+ Platinum', true, 215.31, 'https://example.com/psu');
INSERT INTO public.psu VALUES (56, 'Thermaltake', 'PSU-1103W', 'SFX', 550, 140, 2, 4, 0, '80+ Bronze', true, 279.24, 'https://example.com/psu');
INSERT INTO public.psu VALUES (57, 'Thermaltake', 'PSU-702W', 'SFX', 750, 180, 2, 2, 1, '80+ Platinum', false, 123.53, 'https://example.com/psu');
INSERT INTO public.psu VALUES (58, 'Seasonic', 'PSU-775W', 'ATX', 1200, 180, 2, 1, 0, '80+ Silver', false, 196.88, 'https://example.com/psu');
INSERT INTO public.psu VALUES (59, 'Seasonic', 'PSU-1138W', 'SFX', 650, 180, 1, 4, 2, '80+ Gold', false, 133.99, 'https://example.com/psu');
INSERT INTO public.psu VALUES (60, 'Seasonic', 'PSU-798W', 'ATX', 1000, 140, 2, 1, 0, '80+ Gold', true, 87.08, 'https://example.com/psu');
INSERT INTO public.psu VALUES (61, 'Corsair', 'PSU-978W', 'SFX', 550, 180, 1, 3, 1, '80+ Platinum', false, 283.1, 'https://example.com/psu');
INSERT INTO public.psu VALUES (62, 'Cooler Master', 'PSU-524W', 'SFX', 850, 140, 1, 4, 0, '80+ Gold', true, 148.99, 'https://example.com/psu');
INSERT INTO public.psu VALUES (63, 'Corsair', 'PSU-508W', 'ATX', 750, 160, 1, 4, 0, '80+ Platinum', false, 101.23, 'https://example.com/psu');
INSERT INTO public.psu VALUES (64, 'Cooler Master', 'PSU-1067W', 'ATX', 650, 140, 1, 4, 1, '80+ Platinum', true, 263.22, 'https://example.com/psu');
INSERT INTO public.psu VALUES (65, 'Cooler Master', 'PSU-739W', 'SFX', 850, 180, 1, 2, 0, '80+ Bronze', false, 193.72, 'https://example.com/psu');
INSERT INTO public.psu VALUES (66, 'EVGA', 'PSU-794W', 'SFX', 1200, 160, 1, 1, 0, '80+ Platinum', false, 219.5, 'https://example.com/psu');
INSERT INTO public.psu VALUES (67, 'Seasonic', 'PSU-991W', 'ATX', 1200, 180, 1, 4, 1, '80+ Silver', false, 151.06, 'https://example.com/psu');
INSERT INTO public.psu VALUES (68, 'Corsair', 'PSU-818W', 'SFX', 550, 180, 2, 3, 1, '80+ Silver', false, 220.33, 'https://example.com/psu');
INSERT INTO public.psu VALUES (69, 'EVGA', 'PSU-741W', 'SFX', 750, 160, 2, 1, 2, '80+ Bronze', true, 229.71, 'https://example.com/psu');
INSERT INTO public.psu VALUES (70, 'EVGA', 'PSU-1174W', 'ATX', 850, 180, 1, 3, 0, '80+ Bronze', false, 152.81, 'https://example.com/psu');
INSERT INTO public.psu VALUES (71, 'Seasonic', 'PSU-960W', 'ATX', 850, 140, 1, 2, 2, '80+ Platinum', false, 211.94, 'https://example.com/psu');
INSERT INTO public.psu VALUES (72, 'Cooler Master', 'PSU-1004W', 'SFX', 1200, 180, 1, 3, 0, '80+ Platinum', true, 253.53, 'https://example.com/psu');
INSERT INTO public.psu VALUES (73, 'Thermaltake', 'PSU-544W', 'ATX', 850, 180, 2, 2, 2, '80+ Gold', true, 69.45, 'https://example.com/psu');
INSERT INTO public.psu VALUES (74, 'Seasonic', 'PSU-658W', 'SFX', 750, 140, 2, 2, 1, '80+ Gold', true, 160.84, 'https://example.com/psu');
INSERT INTO public.psu VALUES (75, 'Thermaltake', 'PSU-963W', 'ATX', 650, 140, 2, 1, 1, '80+ Platinum', false, 262.73, 'https://example.com/psu');
INSERT INTO public.psu VALUES (76, 'Cooler Master', 'PSU-815W', 'ATX', 850, 140, 2, 4, 0, '80+ Gold', true, 180.33, 'https://example.com/psu');
INSERT INTO public.psu VALUES (77, 'Thermaltake', 'PSU-800W', 'SFX', 850, 160, 1, 4, 2, '80+ Gold', false, 104.28, 'https://example.com/psu');
INSERT INTO public.psu VALUES (78, 'Thermaltake', 'PSU-841W', 'ATX', 650, 180, 2, 3, 1, '80+ Silver', true, 215.2, 'https://example.com/psu');
INSERT INTO public.psu VALUES (79, 'Thermaltake', 'PSU-756W', 'ATX', 1200, 140, 1, 4, 2, '80+ Gold', true, 134.15, 'https://example.com/psu');
INSERT INTO public.psu VALUES (80, 'Seasonic', 'PSU-683W', 'ATX', 650, 140, 2, 4, 0, '80+ Silver', true, 73.5, 'https://example.com/psu');
INSERT INTO public.psu VALUES (81, 'Corsair', 'PSU-1152W', 'SFX', 550, 140, 2, 2, 0, '80+ Bronze', true, 91.31, 'https://example.com/psu');
INSERT INTO public.psu VALUES (82, 'EVGA', 'PSU-512W', 'SFX', 850, 180, 1, 2, 1, '80+ Platinum', true, 58.32, 'https://example.com/psu');
INSERT INTO public.psu VALUES (83, 'Corsair', 'PSU-867W', 'ATX', 1000, 180, 2, 3, 0, '80+ Platinum', true, 278, 'https://example.com/psu');
INSERT INTO public.psu VALUES (84, 'Seasonic', 'PSU-1034W', 'ATX', 850, 160, 1, 3, 0, '80+ Gold', false, 295.14, 'https://example.com/psu');
INSERT INTO public.psu VALUES (85, 'Thermaltake', 'PSU-1133W', 'SFX', 750, 160, 1, 3, 0, '80+ Bronze', true, 63.05, 'https://example.com/psu');
INSERT INTO public.psu VALUES (86, 'Cooler Master', 'PSU-755W', 'ATX', 1200, 180, 1, 1, 2, '80+ Gold', true, 65.43, 'https://example.com/psu');
INSERT INTO public.psu VALUES (87, 'Cooler Master', 'PSU-634W', 'ATX', 650, 140, 1, 4, 2, '80+ Bronze', false, 113.7, 'https://example.com/psu');
INSERT INTO public.psu VALUES (88, 'Thermaltake', 'PSU-1170W', 'SFX', 750, 160, 1, 2, 0, '80+ Gold', false, 269.77, 'https://example.com/psu');
INSERT INTO public.psu VALUES (89, 'Thermaltake', 'PSU-837W', 'ATX', 850, 140, 2, 1, 0, '80+ Bronze', false, 50.87, 'https://example.com/psu');
INSERT INTO public.psu VALUES (90, 'Corsair', 'PSU-768W', 'SFX', 1200, 160, 2, 2, 0, '80+ Silver', false, 162.54, 'https://example.com/psu');
INSERT INTO public.psu VALUES (91, 'EVGA', 'PSU-581W', 'ATX', 550, 180, 2, 4, 2, '80+ Silver', false, 184.02, 'https://example.com/psu');
INSERT INTO public.psu VALUES (92, 'Seasonic', 'PSU-521W', 'SFX', 550, 160, 1, 4, 2, '80+ Bronze', true, 251.45, 'https://example.com/psu');
INSERT INTO public.psu VALUES (93, 'Cooler Master', 'PSU-702W', 'SFX', 850, 180, 2, 4, 0, '80+ Platinum', false, 187.28, 'https://example.com/psu');
INSERT INTO public.psu VALUES (94, 'EVGA', 'PSU-897W', 'SFX', 1200, 160, 2, 2, 0, '80+ Gold', true, 117.16, 'https://example.com/psu');
INSERT INTO public.psu VALUES (95, 'EVGA', 'PSU-616W', 'ATX', 550, 180, 2, 2, 1, '80+ Bronze', true, 201.44, 'https://example.com/psu');
INSERT INTO public.psu VALUES (96, 'Seasonic', 'PSU-1187W', 'ATX', 850, 160, 2, 4, 0, '80+ Silver', false, 182.81, 'https://example.com/psu');
INSERT INTO public.psu VALUES (97, 'Thermaltake', 'PSU-897W', 'ATX', 850, 140, 1, 3, 1, '80+ Silver', true, 74.26, 'https://example.com/psu');
INSERT INTO public.psu VALUES (98, 'Cooler Master', 'PSU-688W', 'ATX', 650, 180, 1, 1, 2, '80+ Bronze', false, 122.17, 'https://example.com/psu');
INSERT INTO public.psu VALUES (99, 'Seasonic', 'PSU-905W', 'SFX', 1200, 140, 1, 3, 1, '80+ Silver', true, 131.31, 'https://example.com/psu');
INSERT INTO public.psu VALUES (100, 'EVGA', 'PSU-1050W', 'ATX', 1000, 140, 1, 3, 2, '80+ Silver', false, 288.26, 'https://example.com/psu');
INSERT INTO public.psu VALUES (101, 'Seasonic', 'PSU-988W', 'SFX', 750, 180, 1, 4, 0, '80+ Gold', true, 214.97, 'https://example.com/psu');
INSERT INTO public.psu VALUES (102, 'EVGA', 'PSU-605W', 'ATX', 750, 160, 1, 4, 0, '80+ Silver', true, 149.42, 'https://example.com/psu');
INSERT INTO public.psu VALUES (103, 'Thermaltake', 'PSU-902W', 'ATX', 1200, 180, 2, 1, 1, '80+ Bronze', false, 165.47, 'https://example.com/psu');
INSERT INTO public.psu VALUES (104, 'Corsair', 'PSU-849W', 'ATX', 750, 140, 1, 1, 1, '80+ Gold', true, 234.29, 'https://example.com/psu');
INSERT INTO public.psu VALUES (105, 'Thermaltake', 'PSU-508W', 'SFX', 750, 140, 1, 3, 0, '80+ Gold', true, 106.71, 'https://example.com/psu');
INSERT INTO public.psu VALUES (106, 'Cooler Master', 'PSU-1125W', 'ATX', 850, 160, 2, 4, 2, '80+ Silver', true, 278.26, 'https://example.com/psu');
INSERT INTO public.psu VALUES (107, 'EVGA', 'PSU-1169W', 'SFX', 1200, 140, 1, 1, 0, '80+ Bronze', false, 107.23, 'https://example.com/psu');
INSERT INTO public.psu VALUES (108, 'Seasonic', 'PSU-659W', 'ATX', 550, 180, 2, 3, 1, '80+ Platinum', false, 108.65, 'https://example.com/psu');
INSERT INTO public.psu VALUES (109, 'Seasonic', 'PSU-701W', 'ATX', 550, 160, 1, 1, 0, '80+ Bronze', false, 138.88, 'https://example.com/psu');
INSERT INTO public.psu VALUES (110, 'Corsair', 'PSU-548W', 'ATX', 1000, 140, 1, 4, 0, '80+ Gold', true, 58.49, 'https://example.com/psu');
INSERT INTO public.psu VALUES (111, 'Seasonic', 'PSU-831W', 'SFX', 750, 160, 2, 2, 0, '80+ Silver', true, 144.66, 'https://example.com/psu');
INSERT INTO public.psu VALUES (112, 'Corsair', 'PSU-606W', 'ATX', 550, 140, 2, 4, 1, '80+ Bronze', false, 103.91, 'https://example.com/psu');
INSERT INTO public.psu VALUES (113, 'Cooler Master', 'PSU-751W', 'SFX', 550, 140, 1, 2, 2, '80+ Bronze', false, 119.08, 'https://example.com/psu');
INSERT INTO public.psu VALUES (114, 'Cooler Master', 'PSU-858W', 'ATX', 1200, 160, 1, 1, 1, '80+ Platinum', true, 65.73, 'https://example.com/psu');
INSERT INTO public.psu VALUES (115, 'Seasonic', 'PSU-986W', 'ATX', 1000, 180, 1, 1, 0, '80+ Bronze', false, 95.09, 'https://example.com/psu');
INSERT INTO public.psu VALUES (116, 'Corsair', 'PSU-1133W', 'ATX', 650, 160, 2, 3, 1, '80+ Bronze', false, 268.01, 'https://example.com/psu');
INSERT INTO public.psu VALUES (117, 'Thermaltake', 'PSU-1109W', 'SFX', 750, 160, 2, 4, 0, '80+ Bronze', false, 91.51, 'https://example.com/psu');
INSERT INTO public.psu VALUES (118, 'EVGA', 'PSU-658W', 'SFX', 1200, 160, 1, 2, 1, '80+ Gold', false, 160.08, 'https://example.com/psu');
INSERT INTO public.psu VALUES (119, 'EVGA', 'PSU-1070W', 'SFX', 750, 160, 2, 4, 2, '80+ Platinum', false, 295.93, 'https://example.com/psu');
INSERT INTO public.psu VALUES (120, 'Corsair', 'PSU-557W', 'SFX', 750, 160, 2, 1, 2, '80+ Gold', true, 166.51, 'https://example.com/psu');
INSERT INTO public.psu VALUES (121, 'EVGA', 'PSU-504W', 'SFX', 850, 160, 1, 1, 2, '80+ Bronze', false, 235.94, 'https://example.com/psu');
INSERT INTO public.psu VALUES (122, 'Seasonic', 'PSU-839W', 'ATX', 650, 180, 2, 3, 1, '80+ Gold', true, 58.23, 'https://example.com/psu');
INSERT INTO public.psu VALUES (123, 'EVGA', 'PSU-1068W', 'SFX', 650, 140, 2, 2, 1, '80+ Platinum', false, 205.58, 'https://example.com/psu');
INSERT INTO public.psu VALUES (124, 'Thermaltake', 'PSU-820W', 'ATX', 1000, 160, 2, 2, 0, '80+ Gold', false, 83.19, 'https://example.com/psu');
INSERT INTO public.psu VALUES (125, 'Cooler Master', 'PSU-1158W', 'ATX', 1200, 140, 1, 1, 1, '80+ Silver', true, 183.43, 'https://example.com/psu');
INSERT INTO public.psu VALUES (126, 'EVGA', 'PSU-726W', 'ATX', 650, 180, 2, 1, 0, '80+ Silver', true, 141.12, 'https://example.com/psu');
INSERT INTO public.psu VALUES (127, 'Corsair', 'PSU-500W', 'SFX', 1200, 160, 2, 4, 2, '80+ Silver', false, 273.51, 'https://example.com/psu');
INSERT INTO public.psu VALUES (128, 'Cooler Master', 'PSU-515W', 'SFX', 550, 180, 2, 4, 1, '80+ Platinum', true, 182.74, 'https://example.com/psu');
INSERT INTO public.psu VALUES (129, 'Seasonic', 'PSU-1015W', 'ATX', 850, 180, 2, 2, 1, '80+ Platinum', false, 87.98, 'https://example.com/psu');
INSERT INTO public.psu VALUES (130, 'Seasonic', 'PSU-587W', 'ATX', 650, 180, 2, 4, 1, '80+ Bronze', false, 154.06, 'https://example.com/psu');
INSERT INTO public.psu VALUES (131, 'Corsair', 'PSU-567W', 'SFX', 750, 160, 1, 2, 1, '80+ Silver', false, 86.18, 'https://example.com/psu');
INSERT INTO public.psu VALUES (132, 'Seasonic', 'PSU-522W', 'ATX', 1200, 180, 2, 1, 0, '80+ Platinum', false, 60.57, 'https://example.com/psu');
INSERT INTO public.psu VALUES (133, 'Seasonic', 'PSU-925W', 'ATX', 1200, 140, 1, 1, 1, '80+ Platinum', false, 101.97, 'https://example.com/psu');
INSERT INTO public.psu VALUES (134, 'EVGA', 'PSU-905W', 'ATX', 850, 140, 1, 1, 2, '80+ Gold', true, 216.42, 'https://example.com/psu');
INSERT INTO public.psu VALUES (135, 'Seasonic', 'PSU-768W', 'ATX', 550, 180, 1, 3, 0, '80+ Silver', false, 146.8, 'https://example.com/psu');
INSERT INTO public.psu VALUES (136, 'EVGA', 'PSU-546W', 'ATX', 1200, 160, 1, 4, 1, '80+ Platinum', true, 187.84, 'https://example.com/psu');
INSERT INTO public.psu VALUES (137, 'Cooler Master', 'PSU-869W', 'ATX', 1200, 180, 2, 3, 0, '80+ Platinum', true, 115.87, 'https://example.com/psu');
INSERT INTO public.psu VALUES (138, 'Thermaltake', 'PSU-827W', 'SFX', 650, 180, 1, 2, 0, '80+ Silver', false, 226.43, 'https://example.com/psu');
INSERT INTO public.psu VALUES (139, 'Seasonic', 'PSU-1007W', 'ATX', 1200, 160, 1, 3, 0, '80+ Platinum', false, 202.47, 'https://example.com/psu');
INSERT INTO public.psu VALUES (140, 'Thermaltake', 'PSU-618W', 'ATX', 850, 140, 2, 2, 1, '80+ Bronze', true, 263.17, 'https://example.com/psu');
INSERT INTO public.psu VALUES (141, 'Cooler Master', 'PSU-750W', 'SFX', 750, 140, 2, 1, 0, '80+ Bronze', true, 176.85, 'https://example.com/psu');
INSERT INTO public.psu VALUES (142, 'Corsair', 'PSU-589W', 'SFX', 850, 160, 2, 4, 2, '80+ Silver', false, 81.02, 'https://example.com/psu');
INSERT INTO public.psu VALUES (143, 'EVGA', 'PSU-1078W', 'ATX', 550, 140, 2, 1, 1, '80+ Gold', false, 107.24, 'https://example.com/psu');
INSERT INTO public.psu VALUES (144, 'EVGA', 'PSU-760W', 'ATX', 750, 160, 2, 2, 1, '80+ Bronze', false, 242.92, 'https://example.com/psu');
INSERT INTO public.psu VALUES (145, 'Thermaltake', 'PSU-1200W', 'SFX', 1200, 160, 2, 4, 0, '80+ Platinum', true, 68.84, 'https://example.com/psu');
INSERT INTO public.psu VALUES (146, 'EVGA', 'PSU-766W', 'SFX', 550, 140, 2, 4, 1, '80+ Gold', true, 262.47, 'https://example.com/psu');
INSERT INTO public.psu VALUES (147, 'Seasonic', 'PSU-858W', 'SFX', 550, 160, 1, 2, 2, '80+ Silver', false, 299.29, 'https://example.com/psu');
INSERT INTO public.psu VALUES (148, 'Corsair', 'PSU-1100W', 'SFX', 550, 180, 1, 4, 0, '80+ Silver', false, 114.75, 'https://example.com/psu');
INSERT INTO public.psu VALUES (149, 'Cooler Master', 'PSU-945W', 'SFX', 750, 160, 2, 2, 2, '80+ Silver', false, 203.39, 'https://example.com/psu');
INSERT INTO public.psu VALUES (150, 'Seasonic', 'PSU-631W', 'ATX', 550, 140, 1, 3, 0, '80+ Gold', true, 102.84, 'https://example.com/psu');
INSERT INTO public.psu VALUES (151, 'Seasonic', 'PSU-1128W', 'SFX', 750, 160, 1, 2, 0, '80+ Silver', false, 54.2, 'https://example.com/psu');
INSERT INTO public.psu VALUES (152, 'Thermaltake', 'PSU-561W', 'ATX', 550, 180, 2, 3, 2, '80+ Platinum', false, 62.46, 'https://example.com/psu');
INSERT INTO public.psu VALUES (153, 'Seasonic', 'PSU-772W', 'SFX', 1000, 140, 2, 4, 1, '80+ Bronze', false, 61.1, 'https://example.com/psu');
INSERT INTO public.psu VALUES (154, 'EVGA', 'PSU-886W', 'ATX', 850, 180, 1, 3, 1, '80+ Platinum', true, 54.08, 'https://example.com/psu');
INSERT INTO public.psu VALUES (155, 'Corsair', 'PSU-1091W', 'ATX', 1200, 140, 1, 1, 1, '80+ Platinum', false, 122.13, 'https://example.com/psu');
INSERT INTO public.psu VALUES (156, 'Corsair', 'PSU-939W', 'ATX', 650, 140, 2, 1, 2, '80+ Platinum', false, 286.09, 'https://example.com/psu');
INSERT INTO public.psu VALUES (157, 'EVGA', 'PSU-1047W', 'ATX', 550, 180, 2, 4, 0, '80+ Platinum', false, 140, 'https://example.com/psu');
INSERT INTO public.psu VALUES (158, 'Cooler Master', 'PSU-532W', 'ATX', 750, 180, 2, 1, 2, '80+ Gold', true, 96.12, 'https://example.com/psu');
INSERT INTO public.psu VALUES (159, 'Cooler Master', 'PSU-595W', 'ATX', 1200, 140, 1, 3, 2, '80+ Bronze', true, 288.15, 'https://example.com/psu');
INSERT INTO public.psu VALUES (160, 'Cooler Master', 'PSU-913W', 'ATX', 1000, 140, 1, 4, 1, '80+ Silver', false, 257.83, 'https://example.com/psu');
INSERT INTO public.psu VALUES (161, 'EVGA', 'PSU-668W', 'ATX', 550, 140, 1, 4, 2, '80+ Gold', true, 246.69, 'https://example.com/psu');
INSERT INTO public.psu VALUES (162, 'Thermaltake', 'PSU-560W', 'ATX', 550, 140, 1, 3, 2, '80+ Silver', true, 85.88, 'https://example.com/psu');
INSERT INTO public.psu VALUES (163, 'Seasonic', 'PSU-1090W', 'SFX', 750, 140, 2, 3, 0, '80+ Gold', true, 115.99, 'https://example.com/psu');
INSERT INTO public.psu VALUES (164, 'Corsair', 'PSU-1055W', 'ATX', 650, 180, 2, 1, 2, '80+ Platinum', false, 148.05, 'https://example.com/psu');
INSERT INTO public.psu VALUES (165, 'Corsair', 'PSU-526W', 'ATX', 1000, 160, 1, 1, 0, '80+ Gold', false, 226.15, 'https://example.com/psu');
INSERT INTO public.psu VALUES (166, 'Corsair', 'PSU-938W', 'SFX', 1000, 160, 1, 2, 1, '80+ Platinum', false, 227.7, 'https://example.com/psu');
INSERT INTO public.psu VALUES (167, 'Thermaltake', 'PSU-508W', 'SFX', 1200, 180, 2, 1, 1, '80+ Silver', true, 140.91, 'https://example.com/psu');
INSERT INTO public.psu VALUES (168, 'Cooler Master', 'PSU-804W', 'SFX', 850, 160, 2, 3, 1, '80+ Bronze', true, 106.6, 'https://example.com/psu');
INSERT INTO public.psu VALUES (169, 'Cooler Master', 'PSU-777W', 'SFX', 550, 140, 1, 3, 1, '80+ Bronze', true, 54.57, 'https://example.com/psu');
INSERT INTO public.psu VALUES (170, 'Corsair', 'PSU-1095W', 'SFX', 1000, 160, 2, 3, 2, '80+ Platinum', false, 79.4, 'https://example.com/psu');
INSERT INTO public.psu VALUES (171, 'Cooler Master', 'PSU-725W', 'SFX', 550, 180, 2, 1, 0, '80+ Gold', false, 233.12, 'https://example.com/psu');
INSERT INTO public.psu VALUES (172, 'Seasonic', 'PSU-1044W', 'ATX', 550, 160, 1, 3, 2, '80+ Platinum', false, 257.8, 'https://example.com/psu');
INSERT INTO public.psu VALUES (173, 'Corsair', 'PSU-927W', 'ATX', 650, 160, 1, 1, 2, '80+ Bronze', false, 158.55, 'https://example.com/psu');
INSERT INTO public.psu VALUES (174, 'EVGA', 'PSU-1003W', 'ATX', 1200, 140, 1, 1, 1, '80+ Platinum', true, 278, 'https://example.com/psu');
INSERT INTO public.psu VALUES (175, 'Seasonic', 'PSU-588W', 'ATX', 650, 180, 2, 4, 1, '80+ Silver', true, 215.67, 'https://example.com/psu');
INSERT INTO public.psu VALUES (176, 'Seasonic', 'PSU-610W', 'ATX', 1000, 180, 1, 3, 0, '80+ Gold', false, 269.28, 'https://example.com/psu');
INSERT INTO public.psu VALUES (177, 'Corsair', 'PSU-902W', 'ATX', 850, 180, 1, 4, 0, '80+ Silver', false, 105.44, 'https://example.com/psu');
INSERT INTO public.psu VALUES (178, 'EVGA', 'PSU-1074W', 'SFX', 1000, 140, 1, 4, 1, '80+ Bronze', false, 131.8, 'https://example.com/psu');
INSERT INTO public.psu VALUES (179, 'Corsair', 'PSU-747W', 'ATX', 850, 180, 2, 1, 0, '80+ Gold', true, 233.05, 'https://example.com/psu');
INSERT INTO public.psu VALUES (180, 'Corsair', 'PSU-859W', 'SFX', 750, 140, 1, 3, 0, '80+ Silver', false, 105.46, 'https://example.com/psu');
INSERT INTO public.psu VALUES (181, 'Cooler Master', 'PSU-1079W', 'ATX', 650, 180, 1, 4, 1, '80+ Bronze', true, 146.34, 'https://example.com/psu');
INSERT INTO public.psu VALUES (182, 'Corsair', 'PSU-860W', 'SFX', 750, 180, 2, 1, 1, '80+ Bronze', true, 172.87, 'https://example.com/psu');
INSERT INTO public.psu VALUES (183, 'Seasonic', 'PSU-729W', 'ATX', 850, 160, 2, 1, 1, '80+ Silver', true, 120.88, 'https://example.com/psu');
INSERT INTO public.psu VALUES (184, 'Cooler Master', 'PSU-523W', 'SFX', 750, 180, 1, 2, 0, '80+ Platinum', false, 210.89, 'https://example.com/psu');
INSERT INTO public.psu VALUES (185, 'Seasonic', 'PSU-1001W', 'ATX', 650, 180, 2, 3, 1, '80+ Silver', false, 174.6, 'https://example.com/psu');
INSERT INTO public.psu VALUES (186, 'Seasonic', 'PSU-781W', 'SFX', 750, 140, 2, 2, 2, '80+ Gold', true, 58.15, 'https://example.com/psu');
INSERT INTO public.psu VALUES (187, 'EVGA', 'PSU-576W', 'ATX', 1200, 140, 2, 4, 2, '80+ Platinum', true, 147.97, 'https://example.com/psu');
INSERT INTO public.psu VALUES (188, 'EVGA', 'PSU-845W', 'ATX', 1000, 160, 2, 1, 1, '80+ Platinum', true, 180.84, 'https://example.com/psu');
INSERT INTO public.psu VALUES (189, 'Cooler Master', 'PSU-1152W', 'ATX', 650, 140, 1, 3, 0, '80+ Bronze', false, 128.73, 'https://example.com/psu');
INSERT INTO public.psu VALUES (190, 'Seasonic', 'PSU-1187W', 'SFX', 650, 180, 2, 4, 2, '80+ Gold', false, 286.66, 'https://example.com/psu');
INSERT INTO public.psu VALUES (191, 'Thermaltake', 'PSU-871W', 'SFX', 1200, 160, 1, 1, 1, '80+ Bronze', true, 284.39, 'https://example.com/psu');
INSERT INTO public.psu VALUES (192, 'Cooler Master', 'PSU-683W', 'SFX', 650, 160, 1, 4, 0, '80+ Bronze', true, 242.4, 'https://example.com/psu');
INSERT INTO public.psu VALUES (193, 'Seasonic', 'PSU-575W', 'SFX', 750, 160, 1, 2, 0, '80+ Platinum', true, 185.28, 'https://example.com/psu');
INSERT INTO public.psu VALUES (194, 'Corsair', 'PSU-534W', 'SFX', 550, 140, 2, 2, 2, '80+ Platinum', true, 236.23, 'https://example.com/psu');
INSERT INTO public.psu VALUES (195, 'EVGA', 'PSU-907W', 'ATX', 750, 160, 2, 2, 1, '80+ Gold', true, 191.03, 'https://example.com/psu');
INSERT INTO public.psu VALUES (196, 'Cooler Master', 'PSU-931W', 'ATX', 1200, 140, 2, 1, 1, '80+ Silver', true, 100.22, 'https://example.com/psu');
INSERT INTO public.psu VALUES (197, 'EVGA', 'PSU-736W', 'ATX', 550, 160, 2, 4, 1, '80+ Bronze', true, 249.52, 'https://example.com/psu');
INSERT INTO public.psu VALUES (198, 'EVGA', 'PSU-1140W', 'ATX', 1200, 160, 1, 1, 2, '80+ Bronze', true, 227.64, 'https://example.com/psu');
INSERT INTO public.psu VALUES (199, 'Corsair', 'PSU-926W', 'SFX', 850, 160, 2, 2, 2, '80+ Bronze', false, 139.19, 'https://example.com/psu');
INSERT INTO public.psu VALUES (200, 'Corsair', 'PSU-984W', 'ATX', 850, 160, 1, 1, 2, '80+ Bronze', false, 126.22, 'https://example.com/psu');


--
-- Data for Name: ram; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.ram VALUES (1, 'G.Skill', 'Model-9240', 'DDR5', '16GB', 3600, 'CL16', true, 226.62, 'https://example.com/ram');
INSERT INTO public.ram VALUES (2, 'Kingston', 'Model-7884', 'DDR4', '16GB', 5200, 'CL14', false, 142.39, 'https://example.com/ram');
INSERT INTO public.ram VALUES (3, 'Kingston', 'Model-1240', 'DDR4', '32GB', 5200, 'CL16', true, 173.56, 'https://example.com/ram');
INSERT INTO public.ram VALUES (4, 'TeamGroup', 'Model-3373', 'DDR5', '32GB', 4000, 'CL15', true, 253.98, 'https://example.com/ram');
INSERT INTO public.ram VALUES (5, 'G.Skill', 'Model-3699', 'DDR4', '32GB', 4000, 'CL14', true, 98.01, 'https://example.com/ram');
INSERT INTO public.ram VALUES (6, 'Corsair', 'Model-8826', 'DDR5', '32GB', 3600, 'CL14', true, 177.13, 'https://example.com/ram');
INSERT INTO public.ram VALUES (7, 'Kingston', 'Model-7301', 'DDR4', '64GB', 3000, 'CL20', false, 58.86, 'https://example.com/ram');
INSERT INTO public.ram VALUES (8, 'Corsair', 'Model-4995', 'DDR5', '32GB', 3600, 'CL15', true, 231.96, 'https://example.com/ram');
INSERT INTO public.ram VALUES (9, 'TeamGroup', 'Model-5126', 'DDR4', '8GB', 3600, 'CL20', true, 161.98, 'https://example.com/ram');
INSERT INTO public.ram VALUES (10, 'Kingston', 'Model-3581', 'DDR4', '16GB', 3600, 'CL15', false, 63.58, 'https://example.com/ram');
INSERT INTO public.ram VALUES (11, 'G.Skill', 'Model-6926', 'DDR4', '64GB', 4000, 'CL14', false, 297.11, 'https://example.com/ram');
INSERT INTO public.ram VALUES (12, 'G.Skill', 'Model-5496', 'DDR5', '16GB', 3000, 'CL19', true, 133.01, 'https://example.com/ram');
INSERT INTO public.ram VALUES (13, 'G.Skill', 'Model-1215', 'DDR5', '64GB', 4000, 'CL20', false, 71.27, 'https://example.com/ram');
INSERT INTO public.ram VALUES (14, 'TeamGroup', 'Model-6698', 'DDR4', '8GB', 3200, 'CL16', false, 135.7, 'https://example.com/ram');
INSERT INTO public.ram VALUES (15, 'Kingston', 'Model-8761', 'DDR4', '16GB', 5200, 'CL19', true, 165.11, 'https://example.com/ram');
INSERT INTO public.ram VALUES (16, 'Crucial', 'Model-8836', 'DDR5', '32GB', 4000, 'CL14', true, 44.25, 'https://example.com/ram');
INSERT INTO public.ram VALUES (17, 'G.Skill', 'Model-7751', 'DDR4', '8GB', 4800, 'CL14', false, 189.76, 'https://example.com/ram');
INSERT INTO public.ram VALUES (18, 'Corsair', 'Model-9740', 'DDR4', '8GB', 3000, 'CL19', false, 235.66, 'https://example.com/ram');
INSERT INTO public.ram VALUES (19, 'Corsair', 'Model-4756', 'DDR5', '64GB', 3600, 'CL20', true, 201.68, 'https://example.com/ram');
INSERT INTO public.ram VALUES (20, 'TeamGroup', 'Model-1212', 'DDR5', '16GB', 4000, 'CL14', true, 142.92, 'https://example.com/ram');
INSERT INTO public.ram VALUES (21, 'TeamGroup', 'Model-7729', 'DDR4', '64GB', 4800, 'CL20', false, 290.14, 'https://example.com/ram');
INSERT INTO public.ram VALUES (22, 'Kingston', 'Model-6308', 'DDR5', '32GB', 4000, 'CL20', false, 105.64, 'https://example.com/ram');
INSERT INTO public.ram VALUES (23, 'TeamGroup', 'Model-2837', 'DDR4', '64GB', 6000, 'CL17', true, 197.79, 'https://example.com/ram');
INSERT INTO public.ram VALUES (24, 'TeamGroup', 'Model-8490', 'DDR5', '64GB', 6000, 'CL19', false, 46.26, 'https://example.com/ram');
INSERT INTO public.ram VALUES (25, 'Kingston', 'Model-6477', 'DDR5', '64GB', 3200, 'CL15', false, 123.47, 'https://example.com/ram');
INSERT INTO public.ram VALUES (26, 'Crucial', 'Model-6022', 'DDR5', '32GB', 3000, 'CL15', false, 281.4, 'https://example.com/ram');
INSERT INTO public.ram VALUES (27, 'Kingston', 'Model-1747', 'DDR4', '64GB', 3000, 'CL19', false, 30.83, 'https://example.com/ram');
INSERT INTO public.ram VALUES (28, 'Crucial', 'Model-9918', 'DDR5', '8GB', 5200, 'CL18', true, 120.09, 'https://example.com/ram');
INSERT INTO public.ram VALUES (29, 'G.Skill', 'Model-9203', 'DDR5', '16GB', 5200, 'CL20', false, 148.92, 'https://example.com/ram');
INSERT INTO public.ram VALUES (30, 'Corsair', 'Model-9275', 'DDR4', '8GB', 4800, 'CL19', false, 238.2, 'https://example.com/ram');
INSERT INTO public.ram VALUES (31, 'Kingston', 'Model-4192', 'DDR5', '16GB', 5200, 'CL20', false, 39.38, 'https://example.com/ram');
INSERT INTO public.ram VALUES (32, 'TeamGroup', 'Model-5999', 'DDR4', '64GB', 4800, 'CL20', true, 142.73, 'https://example.com/ram');
INSERT INTO public.ram VALUES (33, 'Crucial', 'Model-2792', 'DDR4', '16GB', 3200, 'CL19', false, 55.08, 'https://example.com/ram');
INSERT INTO public.ram VALUES (34, 'G.Skill', 'Model-6102', 'DDR5', '32GB', 4800, 'CL17', false, 156.53, 'https://example.com/ram');
INSERT INTO public.ram VALUES (35, 'G.Skill', 'Model-1235', 'DDR4', '8GB', 3000, 'CL20', true, 282.98, 'https://example.com/ram');
INSERT INTO public.ram VALUES (36, 'G.Skill', 'Model-3722', 'DDR5', '32GB', 3200, 'CL14', true, 99.39, 'https://example.com/ram');
INSERT INTO public.ram VALUES (37, 'TeamGroup', 'Model-1261', 'DDR4', '8GB', 5200, 'CL16', false, 30.35, 'https://example.com/ram');
INSERT INTO public.ram VALUES (38, 'TeamGroup', 'Model-5185', 'DDR5', '16GB', 3000, 'CL18', true, 56.24, 'https://example.com/ram');
INSERT INTO public.ram VALUES (39, 'Kingston', 'Model-1347', 'DDR4', '16GB', 3000, 'CL14', false, 265.19, 'https://example.com/ram');
INSERT INTO public.ram VALUES (40, 'TeamGroup', 'Model-4892', 'DDR5', '32GB', 3000, 'CL18', false, 52.35, 'https://example.com/ram');
INSERT INTO public.ram VALUES (41, 'Kingston', 'Model-3117', 'DDR5', '16GB', 4000, 'CL20', false, 158.21, 'https://example.com/ram');
INSERT INTO public.ram VALUES (42, 'Kingston', 'Model-2335', 'DDR4', '16GB', 4800, 'CL17', true, 199.05, 'https://example.com/ram');
INSERT INTO public.ram VALUES (43, 'Crucial', 'Model-9820', 'DDR5', '16GB', 5200, 'CL16', false, 282.4, 'https://example.com/ram');
INSERT INTO public.ram VALUES (44, 'Corsair', 'Model-2217', 'DDR4', '32GB', 5200, 'CL20', false, 221.04, 'https://example.com/ram');
INSERT INTO public.ram VALUES (45, 'TeamGroup', 'Model-4342', 'DDR5', '64GB', 5200, 'CL18', true, 32.93, 'https://example.com/ram');
INSERT INTO public.ram VALUES (46, 'G.Skill', 'Model-8853', 'DDR4', '8GB', 4000, 'CL16', false, 156.29, 'https://example.com/ram');
INSERT INTO public.ram VALUES (47, 'Kingston', 'Model-5906', 'DDR4', '8GB', 5200, 'CL16', false, 62.55, 'https://example.com/ram');
INSERT INTO public.ram VALUES (48, 'TeamGroup', 'Model-9160', 'DDR5', '32GB', 3200, 'CL20', true, 151.36, 'https://example.com/ram');
INSERT INTO public.ram VALUES (49, 'TeamGroup', 'Model-2027', 'DDR5', '16GB', 3600, 'CL14', false, 187.23, 'https://example.com/ram');
INSERT INTO public.ram VALUES (50, 'Corsair', 'Model-4752', 'DDR4', '16GB', 4800, 'CL19', false, 246.63, 'https://example.com/ram');
INSERT INTO public.ram VALUES (51, 'G.Skill', 'Model-6699', 'DDR5', '8GB', 3000, 'CL18', true, 152.31, 'https://example.com/ram');
INSERT INTO public.ram VALUES (52, 'Kingston', 'Model-3933', 'DDR5', '16GB', 4000, 'CL15', false, 51.47, 'https://example.com/ram');
INSERT INTO public.ram VALUES (53, 'Crucial', 'Model-2590', 'DDR4', '64GB', 4000, 'CL14', false, 241.58, 'https://example.com/ram');
INSERT INTO public.ram VALUES (54, 'Kingston', 'Model-6488', 'DDR5', '16GB', 4800, 'CL17', false, 98.67, 'https://example.com/ram');
INSERT INTO public.ram VALUES (55, 'G.Skill', 'Model-6419', 'DDR5', '8GB', 5200, 'CL16', false, 84.16, 'https://example.com/ram');
INSERT INTO public.ram VALUES (56, 'Corsair', 'Model-9864', 'DDR5', '8GB', 6000, 'CL20', false, 111.95, 'https://example.com/ram');
INSERT INTO public.ram VALUES (57, 'G.Skill', 'Model-1967', 'DDR4', '64GB', 3600, 'CL14', true, 269.72, 'https://example.com/ram');
INSERT INTO public.ram VALUES (58, 'Corsair', 'Model-1796', 'DDR4', '64GB', 3000, 'CL20', false, 76.67, 'https://example.com/ram');
INSERT INTO public.ram VALUES (59, 'Crucial', 'Model-2335', 'DDR4', '8GB', 3000, 'CL16', true, 104.1, 'https://example.com/ram');
INSERT INTO public.ram VALUES (60, 'Kingston', 'Model-4805', 'DDR4', '16GB', 6000, 'CL17', true, 62.98, 'https://example.com/ram');
INSERT INTO public.ram VALUES (61, 'TeamGroup', 'Model-5958', 'DDR5', '8GB', 3000, 'CL20', true, 286.95, 'https://example.com/ram');
INSERT INTO public.ram VALUES (62, 'Kingston', 'Model-9166', 'DDR5', '8GB', 4000, 'CL17', true, 158.97, 'https://example.com/ram');
INSERT INTO public.ram VALUES (63, 'Corsair', 'Model-7886', 'DDR5', '32GB', 3000, 'CL17', false, 65.87, 'https://example.com/ram');
INSERT INTO public.ram VALUES (64, 'Corsair', 'Model-6893', 'DDR5', '8GB', 3200, 'CL18', false, 179.71, 'https://example.com/ram');
INSERT INTO public.ram VALUES (65, 'Crucial', 'Model-1613', 'DDR4', '32GB', 4800, 'CL15', true, 283.28, 'https://example.com/ram');
INSERT INTO public.ram VALUES (66, 'TeamGroup', 'Model-4241', 'DDR5', '32GB', 4000, 'CL15', true, 51.88, 'https://example.com/ram');
INSERT INTO public.ram VALUES (67, 'Crucial', 'Model-8933', 'DDR5', '64GB', 3000, 'CL16', true, 109.96, 'https://example.com/ram');
INSERT INTO public.ram VALUES (68, 'TeamGroup', 'Model-8108', 'DDR5', '16GB', 4800, 'CL18', false, 53.06, 'https://example.com/ram');
INSERT INTO public.ram VALUES (69, 'TeamGroup', 'Model-9626', 'DDR4', '16GB', 4000, 'CL15', false, 53.22, 'https://example.com/ram');
INSERT INTO public.ram VALUES (70, 'TeamGroup', 'Model-8940', 'DDR4', '16GB', 5200, 'CL16', true, 283.72, 'https://example.com/ram');
INSERT INTO public.ram VALUES (71, 'Corsair', 'Model-8885', 'DDR4', '64GB', 6000, 'CL18', true, 223.83, 'https://example.com/ram');
INSERT INTO public.ram VALUES (72, 'Kingston', 'Model-8667', 'DDR4', '32GB', 4800, 'CL19', false, 30.65, 'https://example.com/ram');
INSERT INTO public.ram VALUES (73, 'TeamGroup', 'Model-9948', 'DDR5', '16GB', 3200, 'CL16', false, 167.96, 'https://example.com/ram');
INSERT INTO public.ram VALUES (74, 'TeamGroup', 'Model-8380', 'DDR4', '32GB', 6000, 'CL19', false, 50.86, 'https://example.com/ram');
INSERT INTO public.ram VALUES (75, 'Kingston', 'Model-8976', 'DDR5', '16GB', 6000, 'CL15', false, 171.41, 'https://example.com/ram');
INSERT INTO public.ram VALUES (76, 'TeamGroup', 'Model-8276', 'DDR4', '8GB', 5200, 'CL20', false, 94.72, 'https://example.com/ram');
INSERT INTO public.ram VALUES (77, 'TeamGroup', 'Model-8458', 'DDR5', '64GB', 3200, 'CL14', false, 196.37, 'https://example.com/ram');
INSERT INTO public.ram VALUES (78, 'TeamGroup', 'Model-4121', 'DDR5', '16GB', 4000, 'CL19', true, 215.02, 'https://example.com/ram');
INSERT INTO public.ram VALUES (79, 'Corsair', 'Model-4090', 'DDR4', '64GB', 4800, 'CL16', true, 295.11, 'https://example.com/ram');
INSERT INTO public.ram VALUES (80, 'Crucial', 'Model-5455', 'DDR4', '64GB', 4000, 'CL16', false, 245.14, 'https://example.com/ram');
INSERT INTO public.ram VALUES (81, 'Corsair', 'Model-4313', 'DDR5', '16GB', 3000, 'CL15', false, 263.12, 'https://example.com/ram');
INSERT INTO public.ram VALUES (82, 'Corsair', 'Model-5932', 'DDR4', '8GB', 5200, 'CL19', false, 152.51, 'https://example.com/ram');
INSERT INTO public.ram VALUES (83, 'G.Skill', 'Model-5550', 'DDR4', '16GB', 4000, 'CL16', false, 91.48, 'https://example.com/ram');
INSERT INTO public.ram VALUES (84, 'TeamGroup', 'Model-3513', 'DDR5', '64GB', 5200, 'CL15', true, 206.36, 'https://example.com/ram');
INSERT INTO public.ram VALUES (85, 'Corsair', 'Model-6062', 'DDR5', '32GB', 4800, 'CL16', true, 238.02, 'https://example.com/ram');
INSERT INTO public.ram VALUES (86, 'TeamGroup', 'Model-3995', 'DDR4', '16GB', 4000, 'CL17', true, 157.29, 'https://example.com/ram');
INSERT INTO public.ram VALUES (87, 'Crucial', 'Model-6064', 'DDR5', '32GB', 3000, 'CL19', true, 279.02, 'https://example.com/ram');
INSERT INTO public.ram VALUES (88, 'TeamGroup', 'Model-4235', 'DDR4', '64GB', 4800, 'CL19', false, 233.26, 'https://example.com/ram');
INSERT INTO public.ram VALUES (89, 'G.Skill', 'Model-2362', 'DDR4', '32GB', 6000, 'CL19', true, 131.13, 'https://example.com/ram');
INSERT INTO public.ram VALUES (90, 'Corsair', 'Model-6809', 'DDR5', '16GB', 3000, 'CL14', true, 221.83, 'https://example.com/ram');
INSERT INTO public.ram VALUES (91, 'Crucial', 'Model-4581', 'DDR5', '64GB', 3200, 'CL15', false, 111.2, 'https://example.com/ram');
INSERT INTO public.ram VALUES (92, 'TeamGroup', 'Model-4435', 'DDR5', '16GB', 3200, 'CL16', true, 73.81, 'https://example.com/ram');
INSERT INTO public.ram VALUES (93, 'Crucial', 'Model-7293', 'DDR4', '16GB', 4000, 'CL19', true, 68.96, 'https://example.com/ram');
INSERT INTO public.ram VALUES (94, 'Corsair', 'Model-3367', 'DDR5', '16GB', 4000, 'CL20', true, 124.13, 'https://example.com/ram');
INSERT INTO public.ram VALUES (95, 'TeamGroup', 'Model-1755', 'DDR4', '8GB', 4800, 'CL16', true, 262.08, 'https://example.com/ram');
INSERT INTO public.ram VALUES (96, 'Crucial', 'Model-3280', 'DDR5', '32GB', 3000, 'CL17', true, 216.22, 'https://example.com/ram');
INSERT INTO public.ram VALUES (97, 'TeamGroup', 'Model-8717', 'DDR5', '8GB', 3200, 'CL14', true, 266.71, 'https://example.com/ram');
INSERT INTO public.ram VALUES (98, 'G.Skill', 'Model-3875', 'DDR5', '32GB', 4000, 'CL16', true, 188.11, 'https://example.com/ram');
INSERT INTO public.ram VALUES (99, 'Kingston', 'Model-8066', 'DDR5', '64GB', 4800, 'CL19', true, 106.33, 'https://example.com/ram');
INSERT INTO public.ram VALUES (100, 'Kingston', 'Model-1247', 'DDR5', '32GB', 3000, 'CL14', true, 83.29, 'https://example.com/ram');
INSERT INTO public.ram VALUES (101, 'G.Skill', 'Model-2233', 'DDR5', '32GB', 3200, 'CL15', true, 69.6, 'https://example.com/ram');
INSERT INTO public.ram VALUES (102, 'TeamGroup', 'Model-5140', 'DDR5', '8GB', 4000, 'CL17', false, 289.52, 'https://example.com/ram');
INSERT INTO public.ram VALUES (103, 'Crucial', 'Model-8889', 'DDR5', '64GB', 3000, 'CL17', false, 62.33, 'https://example.com/ram');
INSERT INTO public.ram VALUES (104, 'G.Skill', 'Model-1524', 'DDR5', '8GB', 3000, 'CL18', true, 153.68, 'https://example.com/ram');
INSERT INTO public.ram VALUES (105, 'Crucial', 'Model-8543', 'DDR5', '64GB', 3600, 'CL18', true, 248.27, 'https://example.com/ram');
INSERT INTO public.ram VALUES (106, 'Crucial', 'Model-9582', 'DDR5', '32GB', 4000, 'CL15', true, 283.7, 'https://example.com/ram');
INSERT INTO public.ram VALUES (107, 'Corsair', 'Model-4800', 'DDR4', '32GB', 3600, 'CL14', true, 68.17, 'https://example.com/ram');
INSERT INTO public.ram VALUES (108, 'Kingston', 'Model-5422', 'DDR5', '8GB', 6000, 'CL20', false, 122.06, 'https://example.com/ram');
INSERT INTO public.ram VALUES (109, 'Corsair', 'Model-6236', 'DDR4', '16GB', 6000, 'CL18', false, 292.58, 'https://example.com/ram');
INSERT INTO public.ram VALUES (110, 'G.Skill', 'Model-5969', 'DDR5', '16GB', 3600, 'CL14', false, 118.72, 'https://example.com/ram');
INSERT INTO public.ram VALUES (111, 'TeamGroup', 'Model-4894', 'DDR4', '64GB', 3600, 'CL18', true, 274.81, 'https://example.com/ram');
INSERT INTO public.ram VALUES (112, 'G.Skill', 'Model-8385', 'DDR5', '8GB', 5200, 'CL14', false, 38.31, 'https://example.com/ram');
INSERT INTO public.ram VALUES (113, 'G.Skill', 'Model-7618', 'DDR5', '64GB', 6000, 'CL20', false, 50.2, 'https://example.com/ram');
INSERT INTO public.ram VALUES (114, 'TeamGroup', 'Model-4752', 'DDR4', '32GB', 3600, 'CL17', false, 169.84, 'https://example.com/ram');
INSERT INTO public.ram VALUES (115, 'Crucial', 'Model-5949', 'DDR5', '8GB', 3600, 'CL16', true, 96.73, 'https://example.com/ram');
INSERT INTO public.ram VALUES (116, 'TeamGroup', 'Model-9884', 'DDR5', '32GB', 3600, 'CL15', false, 285.56, 'https://example.com/ram');
INSERT INTO public.ram VALUES (117, 'Crucial', 'Model-7512', 'DDR4', '16GB', 5200, 'CL16', false, 194.21, 'https://example.com/ram');
INSERT INTO public.ram VALUES (118, 'G.Skill', 'Model-3762', 'DDR4', '32GB', 5200, 'CL16', false, 112.68, 'https://example.com/ram');
INSERT INTO public.ram VALUES (119, 'G.Skill', 'Model-1180', 'DDR5', '8GB', 4800, 'CL18', true, 228.14, 'https://example.com/ram');
INSERT INTO public.ram VALUES (120, 'Corsair', 'Model-1363', 'DDR5', '32GB', 3600, 'CL16', false, 35.32, 'https://example.com/ram');
INSERT INTO public.ram VALUES (121, 'G.Skill', 'Model-9726', 'DDR4', '32GB', 3200, 'CL16', false, 33.73, 'https://example.com/ram');
INSERT INTO public.ram VALUES (122, 'Corsair', 'Model-4971', 'DDR5', '32GB', 4000, 'CL17', false, 108.09, 'https://example.com/ram');
INSERT INTO public.ram VALUES (123, 'Kingston', 'Model-6488', 'DDR5', '64GB', 3000, 'CL19', false, 182.65, 'https://example.com/ram');
INSERT INTO public.ram VALUES (124, 'G.Skill', 'Model-1852', 'DDR4', '32GB', 3200, 'CL14', false, 105.15, 'https://example.com/ram');
INSERT INTO public.ram VALUES (125, 'Crucial', 'Model-6021', 'DDR5', '16GB', 6000, 'CL16', true, 160.3, 'https://example.com/ram');
INSERT INTO public.ram VALUES (126, 'Kingston', 'Model-5254', 'DDR4', '16GB', 3600, 'CL18', true, 235.59, 'https://example.com/ram');
INSERT INTO public.ram VALUES (127, 'Corsair', 'Model-5827', 'DDR5', '8GB', 3200, 'CL17', true, 239.16, 'https://example.com/ram');
INSERT INTO public.ram VALUES (128, 'Kingston', 'Model-3755', 'DDR4', '64GB', 4000, 'CL14', true, 151.62, 'https://example.com/ram');
INSERT INTO public.ram VALUES (129, 'TeamGroup', 'Model-1429', 'DDR4', '64GB', 3000, 'CL16', false, 82.56, 'https://example.com/ram');
INSERT INTO public.ram VALUES (130, 'Kingston', 'Model-3637', 'DDR4', '8GB', 4000, 'CL16', false, 249.71, 'https://example.com/ram');
INSERT INTO public.ram VALUES (131, 'TeamGroup', 'Model-2381', 'DDR4', '32GB', 3600, 'CL16', true, 48.13, 'https://example.com/ram');
INSERT INTO public.ram VALUES (132, 'TeamGroup', 'Model-8336', 'DDR5', '16GB', 6000, 'CL15', false, 69.85, 'https://example.com/ram');
INSERT INTO public.ram VALUES (133, 'TeamGroup', 'Model-4894', 'DDR5', '64GB', 5200, 'CL20', false, 293.8, 'https://example.com/ram');
INSERT INTO public.ram VALUES (134, 'Crucial', 'Model-6680', 'DDR4', '16GB', 3000, 'CL20', false, 198.19, 'https://example.com/ram');
INSERT INTO public.ram VALUES (135, 'Kingston', 'Model-3723', 'DDR4', '32GB', 4000, 'CL16', false, 235.54, 'https://example.com/ram');
INSERT INTO public.ram VALUES (136, 'Crucial', 'Model-6453', 'DDR4', '8GB', 5200, 'CL17', false, 160.88, 'https://example.com/ram');
INSERT INTO public.ram VALUES (137, 'Crucial', 'Model-6424', 'DDR4', '32GB', 3200, 'CL20', true, 75.06, 'https://example.com/ram');
INSERT INTO public.ram VALUES (138, 'TeamGroup', 'Model-9593', 'DDR4', '8GB', 6000, 'CL16', false, 161.93, 'https://example.com/ram');
INSERT INTO public.ram VALUES (139, 'G.Skill', 'Model-2449', 'DDR4', '32GB', 3200, 'CL16', false, 203.19, 'https://example.com/ram');
INSERT INTO public.ram VALUES (140, 'Crucial', 'Model-7648', 'DDR4', '32GB', 5200, 'CL17', true, 281.86, 'https://example.com/ram');
INSERT INTO public.ram VALUES (141, 'TeamGroup', 'Model-2250', 'DDR5', '8GB', 3000, 'CL16', true, 251.79, 'https://example.com/ram');
INSERT INTO public.ram VALUES (142, 'Crucial', 'Model-6699', 'DDR4', '64GB', 4000, 'CL16', true, 261.8, 'https://example.com/ram');
INSERT INTO public.ram VALUES (143, 'G.Skill', 'Model-4687', 'DDR5', '8GB', 3000, 'CL17', true, 289.65, 'https://example.com/ram');
INSERT INTO public.ram VALUES (144, 'Crucial', 'Model-4127', 'DDR5', '32GB', 6000, 'CL19', true, 130.85, 'https://example.com/ram');
INSERT INTO public.ram VALUES (145, 'Crucial', 'Model-9048', 'DDR4', '8GB', 3000, 'CL20', false, 83.09, 'https://example.com/ram');
INSERT INTO public.ram VALUES (146, 'Crucial', 'Model-4201', 'DDR4', '8GB', 5200, 'CL16', true, 162.57, 'https://example.com/ram');
INSERT INTO public.ram VALUES (147, 'TeamGroup', 'Model-6864', 'DDR4', '8GB', 4800, 'CL15', false, 226.87, 'https://example.com/ram');
INSERT INTO public.ram VALUES (148, 'Crucial', 'Model-3975', 'DDR5', '32GB', 3000, 'CL20', true, 230.48, 'https://example.com/ram');
INSERT INTO public.ram VALUES (149, 'TeamGroup', 'Model-9380', 'DDR5', '32GB', 3600, 'CL20', true, 150.38, 'https://example.com/ram');
INSERT INTO public.ram VALUES (150, 'Corsair', 'Model-7207', 'DDR4', '16GB', 3000, 'CL18', false, 98.1, 'https://example.com/ram');
INSERT INTO public.ram VALUES (151, 'Corsair', 'Model-7100', 'DDR4', '32GB', 6000, 'CL16', true, 269.36, 'https://example.com/ram');
INSERT INTO public.ram VALUES (152, 'TeamGroup', 'Model-8535', 'DDR4', '32GB', 4000, 'CL18', false, 63.18, 'https://example.com/ram');
INSERT INTO public.ram VALUES (153, 'Kingston', 'Model-7938', 'DDR5', '32GB', 3200, 'CL20', true, 95.09, 'https://example.com/ram');
INSERT INTO public.ram VALUES (154, 'TeamGroup', 'Model-9268', 'DDR5', '16GB', 4800, 'CL16', false, 286.26, 'https://example.com/ram');
INSERT INTO public.ram VALUES (155, 'Kingston', 'Model-6471', 'DDR4', '32GB', 3200, 'CL19', false, 39.48, 'https://example.com/ram');
INSERT INTO public.ram VALUES (156, 'Corsair', 'Model-4492', 'DDR4', '8GB', 4000, 'CL18', false, 80.28, 'https://example.com/ram');
INSERT INTO public.ram VALUES (157, 'Corsair', 'Model-5564', 'DDR4', '32GB', 4000, 'CL20', false, 35.35, 'https://example.com/ram');
INSERT INTO public.ram VALUES (158, 'Corsair', 'Model-6536', 'DDR4', '32GB', 4800, 'CL19', true, 269.07, 'https://example.com/ram');
INSERT INTO public.ram VALUES (159, 'Kingston', 'Model-8147', 'DDR5', '16GB', 3600, 'CL19', false, 227.49, 'https://example.com/ram');
INSERT INTO public.ram VALUES (160, 'G.Skill', 'Model-3076', 'DDR5', '32GB', 4800, 'CL20', false, 117.38, 'https://example.com/ram');
INSERT INTO public.ram VALUES (161, 'Crucial', 'Model-7369', 'DDR5', '16GB', 5200, 'CL14', false, 290.88, 'https://example.com/ram');
INSERT INTO public.ram VALUES (162, 'Corsair', 'Model-2384', 'DDR5', '32GB', 3200, 'CL15', false, 87.26, 'https://example.com/ram');
INSERT INTO public.ram VALUES (163, 'Corsair', 'Model-4930', 'DDR4', '32GB', 3000, 'CL19', false, 232.42, 'https://example.com/ram');
INSERT INTO public.ram VALUES (164, 'G.Skill', 'Model-1891', 'DDR5', '8GB', 6000, 'CL16', true, 143.37, 'https://example.com/ram');
INSERT INTO public.ram VALUES (165, 'Kingston', 'Model-4424', 'DDR5', '64GB', 3600, 'CL16', false, 65.94, 'https://example.com/ram');
INSERT INTO public.ram VALUES (166, 'G.Skill', 'Model-3293', 'DDR4', '8GB', 6000, 'CL14', true, 279.26, 'https://example.com/ram');
INSERT INTO public.ram VALUES (167, 'TeamGroup', 'Model-6323', 'DDR4', '32GB', 3000, 'CL18', false, 37.64, 'https://example.com/ram');
INSERT INTO public.ram VALUES (168, 'TeamGroup', 'Model-4832', 'DDR4', '32GB', 6000, 'CL15', false, 55.49, 'https://example.com/ram');
INSERT INTO public.ram VALUES (169, 'G.Skill', 'Model-5790', 'DDR4', '64GB', 4800, 'CL15', true, 274.75, 'https://example.com/ram');
INSERT INTO public.ram VALUES (170, 'G.Skill', 'Model-4184', 'DDR5', '8GB', 6000, 'CL19', true, 250.83, 'https://example.com/ram');
INSERT INTO public.ram VALUES (171, 'Corsair', 'Model-1379', 'DDR4', '16GB', 6000, 'CL14', true, 241.13, 'https://example.com/ram');
INSERT INTO public.ram VALUES (172, 'Kingston', 'Model-9524', 'DDR4', '8GB', 5200, 'CL20', false, 84.3, 'https://example.com/ram');
INSERT INTO public.ram VALUES (173, 'Kingston', 'Model-6759', 'DDR5', '8GB', 5200, 'CL14', true, 189.23, 'https://example.com/ram');
INSERT INTO public.ram VALUES (174, 'Crucial', 'Model-2426', 'DDR5', '16GB', 4800, 'CL15', true, 75.39, 'https://example.com/ram');
INSERT INTO public.ram VALUES (175, 'Corsair', 'Model-3682', 'DDR5', '32GB', 4000, 'CL20', false, 163.96, 'https://example.com/ram');
INSERT INTO public.ram VALUES (176, 'Corsair', 'Model-8580', 'DDR4', '16GB', 4000, 'CL19', true, 294.68, 'https://example.com/ram');
INSERT INTO public.ram VALUES (177, 'Corsair', 'Model-8968', 'DDR4', '16GB', 3600, 'CL17', true, 72.66, 'https://example.com/ram');
INSERT INTO public.ram VALUES (178, 'TeamGroup', 'Model-3127', 'DDR4', '64GB', 5200, 'CL19', false, 108.33, 'https://example.com/ram');
INSERT INTO public.ram VALUES (179, 'G.Skill', 'Model-4874', 'DDR4', '16GB', 4800, 'CL17', false, 186.7, 'https://example.com/ram');
INSERT INTO public.ram VALUES (180, 'Crucial', 'Model-2018', 'DDR4', '32GB', 5200, 'CL14', false, 31.89, 'https://example.com/ram');
INSERT INTO public.ram VALUES (181, 'TeamGroup', 'Model-5491', 'DDR4', '64GB', 4000, 'CL14', false, 210, 'https://example.com/ram');
INSERT INTO public.ram VALUES (182, 'G.Skill', 'Model-5102', 'DDR5', '8GB', 4800, 'CL19', true, 209.84, 'https://example.com/ram');
INSERT INTO public.ram VALUES (183, 'Corsair', 'Model-6213', 'DDR5', '16GB', 6000, 'CL16', false, 231.99, 'https://example.com/ram');
INSERT INTO public.ram VALUES (184, 'Kingston', 'Model-3839', 'DDR4', '8GB', 6000, 'CL14', false, 96.42, 'https://example.com/ram');
INSERT INTO public.ram VALUES (185, 'TeamGroup', 'Model-9192', 'DDR5', '32GB', 6000, 'CL20', false, 211.87, 'https://example.com/ram');
INSERT INTO public.ram VALUES (186, 'TeamGroup', 'Model-9857', 'DDR4', '16GB', 5200, 'CL19', true, 269.05, 'https://example.com/ram');
INSERT INTO public.ram VALUES (187, 'Corsair', 'Model-4853', 'DDR5', '64GB', 3200, 'CL20', false, 87.71, 'https://example.com/ram');
INSERT INTO public.ram VALUES (188, 'G.Skill', 'Model-9515', 'DDR4', '32GB', 4000, 'CL18', false, 265.14, 'https://example.com/ram');
INSERT INTO public.ram VALUES (189, 'Crucial', 'Model-2013', 'DDR5', '8GB', 3000, 'CL17', false, 96.35, 'https://example.com/ram');
INSERT INTO public.ram VALUES (190, 'Kingston', 'Model-7029', 'DDR4', '64GB', 5200, 'CL16', true, 103.42, 'https://example.com/ram');
INSERT INTO public.ram VALUES (191, 'Crucial', 'Model-7660', 'DDR5', '8GB', 5200, 'CL15', true, 164.61, 'https://example.com/ram');
INSERT INTO public.ram VALUES (192, 'Crucial', 'Model-7835', 'DDR4', '16GB', 6000, 'CL15', false, 128.59, 'https://example.com/ram');
INSERT INTO public.ram VALUES (193, 'Corsair', 'Model-6197', 'DDR4', '16GB', 3600, 'CL17', false, 234.45, 'https://example.com/ram');
INSERT INTO public.ram VALUES (194, 'Crucial', 'Model-4785', 'DDR5', '16GB', 5200, 'CL18', false, 200.34, 'https://example.com/ram');
INSERT INTO public.ram VALUES (195, 'Corsair', 'Model-1708', 'DDR4', '32GB', 3200, 'CL17', false, 56.33, 'https://example.com/ram');
INSERT INTO public.ram VALUES (196, 'Corsair', 'Model-8988', 'DDR5', '8GB', 4000, 'CL15', true, 277.72, 'https://example.com/ram');
INSERT INTO public.ram VALUES (197, 'Kingston', 'Model-3703', 'DDR5', '16GB', 3600, 'CL17', false, 227.86, 'https://example.com/ram');
INSERT INTO public.ram VALUES (198, 'Kingston', 'Model-5222', 'DDR4', '16GB', 3200, 'CL15', false, 155.7, 'https://example.com/ram');
INSERT INTO public.ram VALUES (199, 'TeamGroup', 'Model-6674', 'DDR4', '16GB', 4800, 'CL17', false, 36.21, 'https://example.com/ram');
INSERT INTO public.ram VALUES (200, 'Kingston', 'Model-6427', 'DDR4', '8GB', 3200, 'CL18', true, 161.07, 'https://example.com/ram');


--
-- Data for Name: saved_builds; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.saved_builds VALUES (1, 1, 5, 12, 18, 30, 3, 4, 15, 4, 'High-Performance Build', '2025-05-10 00:41:08.433185', NULL);


--
-- Data for Name: ssd; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.ssd VALUES (1, 'Samsung', 'SSD-495', '1TB', 'U.2', 'PCIe 5.0', 3567, 6594, true, 245.42, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (2, 'Samsung', 'SSD-754', '2TB', 'M.2', 'PCIe 5.0', 1107, 5827, true, 45.3, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (3, 'Crucial', 'SSD-967', '1TB', '2.5"', 'PCIe 5.0', 1486, 5147, false, 316.82, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (4, 'Sabrent', 'SSD-989', '500GB', 'U.2', 'PCIe 5.0', 3711, 1743, true, 240.92, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (5, 'ADATA', 'SSD-167', '1TB', '2.5"', 'PCIe 3.0', 1569, 5412, true, 65.43, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (6, 'Samsung', 'SSD-156', '250GB', 'U.2', 'PCIe 4.0', 1435, 5303, false, 123.55, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (7, 'Crucial', 'SSD-822', '1TB', 'U.2', 'PCIe 5.0', 2378, 4127, false, 148.26, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (8, 'ADATA', 'SSD-893', '2TB', 'M.2', 'PCIe 5.0', 2730, 2378, true, 246.69, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (9, 'ADATA', 'SSD-220', '1TB', '2.5"', 'PCIe 5.0', 1176, 6590, false, 124.08, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (10, 'Samsung', 'SSD-336', '2TB', '2.5"', 'PCIe 5.0', 3171, 5117, false, 43.6, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (11, 'Samsung', 'SSD-164', '2TB', '2.5"', 'PCIe 3.0', 1813, 2065, false, 136.81, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (12, 'ADATA', 'SSD-632', '2TB', 'M.2', 'PCIe 3.0', 5627, 3461, false, 232.53, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (13, 'ADATA', 'SSD-533', '500GB', 'M.2', 'PCIe 3.0', 1719, 5906, false, 40.27, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (14, 'Western Digital', 'SSD-385', '2TB', 'M.2', 'PCIe 3.0', 3914, 1744, false, 218.68, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (15, 'ADATA', 'SSD-841', '500GB', '2.5"', 'PCIe 4.0', 6242, 3552, true, 227.08, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (16, 'Samsung', 'SSD-160', '250GB', '2.5"', 'PCIe 5.0', 6294, 2227, true, 249.04, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (17, 'Western Digital', 'SSD-104', '1TB', 'M.2', 'PCIe 5.0', 3350, 878, false, 59.06, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (18, 'ADATA', 'SSD-828', '500GB', '2.5"', 'PCIe 5.0', 2676, 6491, true, 303.33, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (19, 'ADATA', 'SSD-460', '250GB', 'U.2', 'PCIe 4.0', 3296, 6511, false, 108.44, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (20, 'Crucial', 'SSD-231', '2TB', '2.5"', 'PCIe 5.0', 6084, 4813, false, 317.97, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (21, 'Crucial', 'SSD-927', '2TB', 'M.2', 'PCIe 5.0', 4535, 4832, false, 235.78, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (22, 'Western Digital', 'SSD-954', '250GB', 'M.2', 'PCIe 5.0', 7102, 5648, true, 125.85, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (23, 'Western Digital', 'SSD-376', '2TB', 'M.2', 'PCIe 3.0', 6938, 4723, true, 215.87, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (24, 'Sabrent', 'SSD-778', '2TB', '2.5"', 'PCIe 3.0', 1455, 6510, true, 64.65, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (25, 'Samsung', 'SSD-305', '250GB', 'M.2', 'PCIe 5.0', 4342, 1502, true, 277.64, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (26, 'Western Digital', 'SSD-980', '500GB', 'U.2', 'PCIe 3.0', 7376, 5809, false, 77, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (27, 'Western Digital', 'SSD-868', '1TB', '2.5"', 'PCIe 3.0', 6449, 2759, false, 335.33, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (28, 'Crucial', 'SSD-276', '1TB', 'U.2', 'PCIe 5.0', 3924, 5446, false, 188.98, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (29, 'Western Digital', 'SSD-298', '2TB', '2.5"', 'PCIe 5.0', 6986, 1400, true, 56.01, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (30, 'Sabrent', 'SSD-351', '250GB', 'U.2', 'PCIe 3.0', 2132, 2833, false, 156.85, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (31, 'ADATA', 'SSD-439', '1TB', 'U.2', 'PCIe 5.0', 2177, 2447, true, 286.29, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (32, 'Western Digital', 'SSD-266', '2TB', '2.5"', 'PCIe 4.0', 6712, 1848, true, 141.08, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (33, 'Western Digital', 'SSD-933', '250GB', 'U.2', 'PCIe 3.0', 6071, 6420, true, 89.99, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (34, 'Western Digital', 'SSD-458', '2TB', 'M.2', 'PCIe 3.0', 4679, 3203, true, 58.88, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (35, 'Sabrent', 'SSD-450', '2TB', 'M.2', 'PCIe 3.0', 1874, 856, true, 328.7, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (36, 'ADATA', 'SSD-910', '250GB', '2.5"', 'PCIe 5.0', 3559, 3518, false, 332.44, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (37, 'ADATA', 'SSD-970', '2TB', 'M.2', 'PCIe 5.0', 3860, 3086, false, 264.37, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (38, 'ADATA', 'SSD-489', '2TB', 'M.2', 'PCIe 5.0', 7337, 2329, false, 283.22, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (39, 'Sabrent', 'SSD-709', '1TB', 'M.2', 'PCIe 3.0', 4044, 1025, true, 129.11, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (40, 'Samsung', 'SSD-148', '500GB', '2.5"', 'PCIe 3.0', 3532, 1629, true, 67.11, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (41, 'Crucial', 'SSD-790', '500GB', '2.5"', 'PCIe 5.0', 5310, 3704, false, 177.48, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (42, 'Western Digital', 'SSD-960', '2TB', 'M.2', 'PCIe 3.0', 4907, 2334, true, 133.71, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (43, 'Sabrent', 'SSD-408', '2TB', 'U.2', 'PCIe 3.0', 6003, 4381, true, 60.66, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (44, 'Sabrent', 'SSD-188', '2TB', '2.5"', 'PCIe 3.0', 4883, 2230, false, 72.74, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (45, 'Samsung', 'SSD-872', '2TB', '2.5"', 'PCIe 5.0', 7451, 3804, true, 87.69, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (46, 'Crucial', 'SSD-374', '250GB', 'U.2', 'PCIe 4.0', 6216, 6456, false, 285.6, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (47, 'Crucial', 'SSD-477', '500GB', '2.5"', 'PCIe 4.0', 1659, 6611, false, 215.4, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (48, 'ADATA', 'SSD-667', '500GB', 'M.2', 'PCIe 5.0', 6189, 6340, false, 240.18, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (49, 'Crucial', 'SSD-857', '250GB', 'M.2', 'PCIe 4.0', 4259, 1900, true, 89.64, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (50, 'Sabrent', 'SSD-304', '250GB', '2.5"', 'PCIe 4.0', 1206, 4662, true, 304.28, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (51, 'Western Digital', 'SSD-658', '250GB', 'U.2', 'PCIe 3.0', 5890, 3308, true, 47.89, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (52, 'Samsung', 'SSD-102', '250GB', '2.5"', 'PCIe 3.0', 3455, 3226, false, 165.41, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (53, 'Crucial', 'SSD-390', '500GB', 'M.2', 'PCIe 4.0', 5911, 4143, false, 325.13, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (54, 'Crucial', 'SSD-283', '1TB', '2.5"', 'PCIe 5.0', 2874, 3276, false, 160.83, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (55, 'Crucial', 'SSD-144', '1TB', '2.5"', 'PCIe 3.0', 3906, 1685, false, 121.92, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (56, 'ADATA', 'SSD-429', '500GB', 'M.2', 'PCIe 4.0', 1778, 3419, false, 154.78, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (57, 'ADATA', 'SSD-464', '1TB', '2.5"', 'PCIe 3.0', 3644, 2731, false, 324.09, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (58, 'Western Digital', 'SSD-321', '500GB', '2.5"', 'PCIe 3.0', 1427, 1245, true, 65.77, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (59, 'Sabrent', 'SSD-814', '1TB', '2.5"', 'PCIe 4.0', 6614, 5285, true, 242.81, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (60, 'ADATA', 'SSD-385', '2TB', '2.5"', 'PCIe 4.0', 7419, 1879, false, 176.16, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (61, 'Western Digital', 'SSD-646', '2TB', '2.5"', 'PCIe 5.0', 2361, 4870, false, 208.88, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (62, 'Sabrent', 'SSD-195', '500GB', '2.5"', 'PCIe 4.0', 6075, 6675, true, 244.11, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (63, 'Western Digital', 'SSD-518', '1TB', '2.5"', 'PCIe 5.0', 4442, 3616, true, 231.94, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (64, 'Crucial', 'SSD-325', '250GB', 'M.2', 'PCIe 5.0', 6336, 2065, false, 309.98, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (65, 'Western Digital', 'SSD-555', '250GB', 'U.2', 'PCIe 3.0', 6221, 3125, true, 155.12, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (66, 'Samsung', 'SSD-640', '500GB', '2.5"', 'PCIe 4.0', 6433, 1650, true, 336.89, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (67, 'Samsung', 'SSD-791', '250GB', 'M.2', 'PCIe 3.0', 5216, 2793, false, 239.03, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (68, 'Sabrent', 'SSD-645', '2TB', '2.5"', 'PCIe 3.0', 2317, 2335, true, 247.99, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (69, 'Sabrent', 'SSD-537', '2TB', 'M.2', 'PCIe 4.0', 4749, 4136, false, 248.3, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (70, 'Western Digital', 'SSD-463', '250GB', '2.5"', 'PCIe 4.0', 5509, 1302, false, 171.3, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (71, 'Crucial', 'SSD-929', '250GB', 'U.2', 'PCIe 4.0', 5916, 3353, true, 229.06, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (72, 'Samsung', 'SSD-585', '500GB', '2.5"', 'PCIe 3.0', 2300, 2015, false, 224.54, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (73, 'Sabrent', 'SSD-681', '500GB', '2.5"', 'PCIe 5.0', 6170, 3022, false, 193.29, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (74, 'Western Digital', 'SSD-993', '500GB', 'U.2', 'PCIe 4.0', 5876, 1756, true, 127.4, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (75, 'Samsung', 'SSD-386', '500GB', 'M.2', 'PCIe 5.0', 2723, 2733, false, 111.17, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (76, 'ADATA', 'SSD-437', '250GB', '2.5"', 'PCIe 4.0', 6787, 1642, false, 246.36, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (77, 'ADATA', 'SSD-964', '500GB', 'U.2', 'PCIe 5.0', 5924, 2991, true, 210.87, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (78, 'Samsung', 'SSD-411', '1TB', 'U.2', 'PCIe 3.0', 6476, 1882, false, 269.01, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (79, 'ADATA', 'SSD-837', '250GB', 'M.2', 'PCIe 3.0', 3987, 3778, false, 338.13, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (80, 'Sabrent', 'SSD-328', '1TB', '2.5"', 'PCIe 3.0', 5179, 3001, false, 73.51, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (81, 'Western Digital', 'SSD-381', '500GB', 'U.2', 'PCIe 5.0', 7363, 3691, true, 117.21, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (82, 'Western Digital', 'SSD-257', '2TB', 'U.2', 'PCIe 4.0', 1643, 5440, false, 66.83, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (83, 'Crucial', 'SSD-348', '2TB', '2.5"', 'PCIe 4.0', 4821, 5693, true, 95.42, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (84, 'Samsung', 'SSD-522', '2TB', '2.5"', 'PCIe 3.0', 6272, 7000, false, 233.21, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (85, 'Western Digital', 'SSD-686', '1TB', '2.5"', 'PCIe 3.0', 5907, 2111, false, 248.7, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (86, 'Crucial', 'SSD-432', '1TB', 'M.2', 'PCIe 5.0', 1805, 4925, true, 227.02, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (87, 'Western Digital', 'SSD-367', '500GB', '2.5"', 'PCIe 4.0', 3208, 6734, false, 112.9, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (88, 'Sabrent', 'SSD-828', '1TB', 'M.2', 'PCIe 3.0', 5266, 854, true, 225.6, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (89, 'Sabrent', 'SSD-993', '250GB', 'U.2', 'PCIe 4.0', 2619, 4930, true, 86.53, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (90, 'Crucial', 'SSD-730', '500GB', 'U.2', 'PCIe 3.0', 2522, 3453, true, 140.41, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (91, 'Sabrent', 'SSD-547', '1TB', 'M.2', 'PCIe 5.0', 6040, 4470, false, 316.32, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (92, 'Crucial', 'SSD-155', '500GB', '2.5"', 'PCIe 5.0', 6215, 3531, true, 73.3, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (93, 'Samsung', 'SSD-677', '2TB', 'U.2', 'PCIe 5.0', 3143, 6083, false, 45.29, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (94, 'Sabrent', 'SSD-648', '2TB', '2.5"', 'PCIe 3.0', 1433, 5254, true, 108.88, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (95, 'Samsung', 'SSD-489', '250GB', 'M.2', 'PCIe 3.0', 5361, 5674, true, 127.95, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (96, 'Crucial', 'SSD-605', '500GB', '2.5"', 'PCIe 5.0', 1240, 4511, true, 265.43, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (97, 'Samsung', 'SSD-588', '250GB', 'U.2', 'PCIe 4.0', 4763, 6912, false, 295.86, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (98, 'Crucial', 'SSD-175', '1TB', '2.5"', 'PCIe 5.0', 6128, 3313, true, 113.7, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (99, 'ADATA', 'SSD-821', '2TB', 'M.2', 'PCIe 5.0', 2407, 6901, true, 314.91, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (100, 'ADATA', 'SSD-747', '2TB', 'U.2', 'PCIe 4.0', 6388, 2830, true, 344.63, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (101, 'ADATA', 'SSD-474', '1TB', 'M.2', 'PCIe 5.0', 4075, 3238, false, 316.75, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (102, 'Samsung', 'SSD-884', '2TB', '2.5"', 'PCIe 4.0', 4808, 3664, false, 245.92, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (103, 'Crucial', 'SSD-219', '2TB', 'U.2', 'PCIe 5.0', 3655, 4295, true, 63.07, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (104, 'Samsung', 'SSD-602', '250GB', 'M.2', 'PCIe 4.0', 3612, 3712, true, 233.87, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (105, 'Crucial', 'SSD-941', '250GB', 'M.2', 'PCIe 5.0', 6317, 2222, true, 132.67, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (106, 'Crucial', 'SSD-929', '1TB', '2.5"', 'PCIe 5.0', 6147, 6996, true, 243.04, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (107, 'ADATA', 'SSD-249', '500GB', 'M.2', 'PCIe 5.0', 5109, 1175, true, 57.68, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (108, 'Crucial', 'SSD-732', '2TB', 'U.2', 'PCIe 5.0', 1625, 5645, true, 183.94, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (109, 'Western Digital', 'SSD-135', '2TB', 'M.2', 'PCIe 4.0', 3214, 4928, false, 166.04, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (110, 'Crucial', 'SSD-707', '2TB', 'U.2', 'PCIe 5.0', 4776, 2477, true, 335.9, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (111, 'Samsung', 'SSD-321', '1TB', '2.5"', 'PCIe 4.0', 6056, 1843, true, 240.61, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (112, 'Sabrent', 'SSD-277', '1TB', 'U.2', 'PCIe 5.0', 4950, 1446, true, 111.43, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (113, 'Sabrent', 'SSD-479', '250GB', 'U.2', 'PCIe 3.0', 6006, 1920, true, 129.88, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (114, 'Samsung', 'SSD-163', '2TB', 'U.2', 'PCIe 5.0', 5768, 3472, true, 279.45, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (115, 'ADATA', 'SSD-517', '500GB', 'U.2', 'PCIe 3.0', 5123, 1012, false, 270.2, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (116, 'Sabrent', 'SSD-551', '2TB', '2.5"', 'PCIe 4.0', 2545, 3224, false, 174.75, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (117, 'ADATA', 'SSD-716', '2TB', 'U.2', 'PCIe 4.0', 6651, 4285, false, 238.9, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (118, 'Crucial', 'SSD-784', '1TB', 'U.2', 'PCIe 3.0', 6932, 6984, false, 273.19, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (119, 'Crucial', 'SSD-212', '250GB', '2.5"', 'PCIe 5.0', 5343, 2257, false, 146.24, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (120, 'ADATA', 'SSD-469', '2TB', 'U.2', 'PCIe 3.0', 2030, 5243, true, 163.85, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (121, 'Samsung', 'SSD-415', '1TB', 'M.2', 'PCIe 4.0', 3309, 2647, true, 274.34, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (122, 'Sabrent', 'SSD-208', '250GB', '2.5"', 'PCIe 3.0', 5088, 4885, false, 319.14, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (123, 'ADATA', 'SSD-261', '2TB', '2.5"', 'PCIe 5.0', 4393, 991, true, 53.17, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (124, 'Samsung', 'SSD-277', '1TB', 'U.2', 'PCIe 5.0', 6526, 3653, false, 119.47, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (125, 'Samsung', 'SSD-271', '250GB', 'M.2', 'PCIe 3.0', 7358, 890, false, 242.91, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (126, 'Sabrent', 'SSD-478', '250GB', 'U.2', 'PCIe 4.0', 3603, 2773, false, 171.03, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (127, 'Sabrent', 'SSD-152', '1TB', '2.5"', 'PCIe 5.0', 5503, 4157, false, 41.95, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (128, 'Samsung', 'SSD-581', '2TB', 'M.2', 'PCIe 5.0', 5617, 3991, false, 330.49, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (129, 'Samsung', 'SSD-910', '1TB', '2.5"', 'PCIe 3.0', 5139, 6635, false, 218.35, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (130, 'Samsung', 'SSD-501', '2TB', 'M.2', 'PCIe 4.0', 2543, 6312, true, 273.69, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (131, 'Crucial', 'SSD-420', '1TB', 'M.2', 'PCIe 4.0', 6572, 871, true, 163.52, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (132, 'Crucial', 'SSD-899', '1TB', '2.5"', 'PCIe 3.0', 2629, 6403, false, 142.69, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (133, 'ADATA', 'SSD-500', '1TB', 'U.2', 'PCIe 3.0', 7357, 1831, true, 48.75, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (134, 'Crucial', 'SSD-451', '2TB', 'M.2', 'PCIe 3.0', 2449, 6844, false, 171.29, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (135, 'Crucial', 'SSD-825', '250GB', 'U.2', 'PCIe 5.0', 2117, 4826, false, 47.49, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (136, 'Samsung', 'SSD-925', '500GB', '2.5"', 'PCIe 3.0', 1360, 1739, true, 114.93, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (137, 'ADATA', 'SSD-793', '1TB', 'M.2', 'PCIe 3.0', 4623, 4281, true, 105.23, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (138, 'Crucial', 'SSD-752', '250GB', '2.5"', 'PCIe 4.0', 4804, 6695, true, 148.07, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (139, 'Western Digital', 'SSD-533', '250GB', '2.5"', 'PCIe 4.0', 1848, 2159, false, 253.17, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (140, 'Sabrent', 'SSD-598', '2TB', 'U.2', 'PCIe 3.0', 5094, 1451, true, 341.02, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (141, 'Samsung', 'SSD-187', '500GB', 'M.2', 'PCIe 5.0', 5396, 1005, false, 196.36, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (142, 'Samsung', 'SSD-609', '2TB', 'M.2', 'PCIe 5.0', 7078, 2868, true, 209.68, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (143, 'Samsung', 'SSD-186', '1TB', 'M.2', 'PCIe 3.0', 3127, 1555, true, 100.2, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (144, 'ADATA', 'SSD-300', '500GB', 'M.2', 'PCIe 4.0', 4316, 2284, false, 125.67, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (145, 'Sabrent', 'SSD-483', '250GB', '2.5"', 'PCIe 3.0', 2823, 1670, false, 64.19, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (146, 'Samsung', 'SSD-928', '500GB', 'M.2', 'PCIe 5.0', 1159, 6394, true, 304.88, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (147, 'Samsung', 'SSD-986', '2TB', 'M.2', 'PCIe 4.0', 4459, 4743, false, 150.91, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (148, 'ADATA', 'SSD-192', '250GB', '2.5"', 'PCIe 5.0', 1900, 1718, true, 175.08, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (149, 'Crucial', 'SSD-689', '500GB', 'M.2', 'PCIe 5.0', 3924, 1505, true, 95.09, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (150, 'Sabrent', 'SSD-423', '500GB', 'U.2', 'PCIe 3.0', 6417, 5639, false, 176.85, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (151, 'Crucial', 'SSD-327', '1TB', 'M.2', 'PCIe 4.0', 7087, 6135, true, 228.52, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (152, 'ADATA', 'SSD-104', '1TB', '2.5"', 'PCIe 5.0', 5109, 7000, true, 49.95, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (153, 'ADATA', 'SSD-157', '2TB', 'M.2', 'PCIe 3.0', 5040, 4885, false, 79.25, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (154, 'Sabrent', 'SSD-440', '2TB', '2.5"', 'PCIe 3.0', 1632, 1715, false, 93.32, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (155, 'Crucial', 'SSD-461', '2TB', 'M.2', 'PCIe 4.0', 3564, 3773, true, 98.33, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (156, 'Western Digital', 'SSD-309', '2TB', 'U.2', 'PCIe 4.0', 5507, 6784, false, 110.21, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (157, 'Western Digital', 'SSD-952', '2TB', 'M.2', 'PCIe 4.0', 7476, 965, true, 294.6, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (158, 'Western Digital', 'SSD-255', '250GB', 'U.2', 'PCIe 3.0', 6965, 1755, false, 227.62, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (159, 'Samsung', 'SSD-311', '250GB', '2.5"', 'PCIe 3.0', 2133, 6781, true, 56.62, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (160, 'Crucial', 'SSD-848', '2TB', '2.5"', 'PCIe 4.0', 4064, 4891, false, 254.44, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (161, 'ADATA', 'SSD-961', '1TB', 'U.2', 'PCIe 5.0', 5613, 1568, true, 73.03, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (162, 'Western Digital', 'SSD-822', '2TB', 'U.2', 'PCIe 5.0', 2410, 4530, false, 343.76, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (163, 'Crucial', 'SSD-878', '500GB', '2.5"', 'PCIe 5.0', 1232, 6557, true, 153.39, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (164, 'Western Digital', 'SSD-379', '2TB', '2.5"', 'PCIe 5.0', 4238, 5801, false, 149, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (165, 'Samsung', 'SSD-489', '500GB', '2.5"', 'PCIe 3.0', 4000, 1673, false, 189.29, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (166, 'Western Digital', 'SSD-280', '500GB', 'M.2', 'PCIe 3.0', 3397, 6187, false, 208.56, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (167, 'Samsung', 'SSD-741', '500GB', 'M.2', 'PCIe 5.0', 6953, 5904, true, 290.79, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (168, 'ADATA', 'SSD-990', '1TB', 'M.2', 'PCIe 4.0', 6240, 5920, false, 77.11, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (169, 'Sabrent', 'SSD-125', '2TB', '2.5"', 'PCIe 3.0', 2302, 3680, false, 149.54, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (170, 'ADATA', 'SSD-784', '1TB', '2.5"', 'PCIe 4.0', 4272, 873, false, 244.74, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (171, 'Crucial', 'SSD-549', '2TB', 'M.2', 'PCIe 4.0', 3515, 2230, true, 67.37, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (172, 'Sabrent', 'SSD-285', '500GB', 'U.2', 'PCIe 4.0', 2281, 1884, true, 232.92, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (173, 'Samsung', 'SSD-542', '1TB', '2.5"', 'PCIe 3.0', 5991, 4685, true, 134.16, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (174, 'ADATA', 'SSD-593', '250GB', 'U.2', 'PCIe 3.0', 3796, 3217, false, 133.21, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (175, 'Samsung', 'SSD-808', '1TB', 'U.2', 'PCIe 4.0', 3239, 2022, true, 106.33, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (176, 'Western Digital', 'SSD-952', '2TB', 'U.2', 'PCIe 4.0', 1441, 6423, false, 318.66, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (177, 'ADATA', 'SSD-142', '250GB', 'U.2', 'PCIe 4.0', 3565, 5550, true, 88.76, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (178, 'Samsung', 'SSD-575', '500GB', '2.5"', 'PCIe 3.0', 1601, 6834, true, 186.78, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (179, 'Samsung', 'SSD-657', '250GB', 'M.2', 'PCIe 4.0', 5348, 2383, false, 50.76, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (180, 'Crucial', 'SSD-494', '1TB', '2.5"', 'PCIe 3.0', 1018, 2137, false, 199.63, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (181, 'Samsung', 'SSD-281', '1TB', 'M.2', 'PCIe 5.0', 5541, 2691, true, 238.75, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (182, 'Samsung', 'SSD-832', '1TB', '2.5"', 'PCIe 5.0', 2407, 5188, true, 131.83, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (183, 'ADATA', 'SSD-701', '250GB', 'U.2', 'PCIe 3.0', 5820, 6942, true, 99.7, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (184, 'ADATA', 'SSD-604', '250GB', 'M.2', 'PCIe 3.0', 2764, 6457, false, 163.18, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (185, 'ADATA', 'SSD-546', '1TB', '2.5"', 'PCIe 4.0', 4333, 6825, true, 178.23, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (186, 'ADATA', 'SSD-583', '500GB', 'U.2', 'PCIe 3.0', 6677, 6119, true, 121.34, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (187, 'Crucial', 'SSD-325', '2TB', 'M.2', 'PCIe 3.0', 3425, 4895, false, 109.74, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (188, 'Crucial', 'SSD-382', '2TB', 'M.2', 'PCIe 4.0', 2898, 1407, false, 320.39, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (189, 'ADATA', 'SSD-607', '500GB', 'U.2', 'PCIe 4.0', 2310, 1238, false, 207.37, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (190, 'Samsung', 'SSD-136', '1TB', 'M.2', 'PCIe 4.0', 3697, 1629, false, 244.56, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (191, 'ADATA', 'SSD-421', '2TB', '2.5"', 'PCIe 5.0', 6155, 5010, false, 270.64, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (192, 'ADATA', 'SSD-994', '500GB', '2.5"', 'PCIe 5.0', 7184, 4779, true, 167.45, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (193, 'Samsung', 'SSD-781', '2TB', 'M.2', 'PCIe 3.0', 7345, 2410, false, 41.43, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (194, 'Crucial', 'SSD-142', '500GB', 'M.2', 'PCIe 4.0', 2829, 5157, false, 63.15, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (195, 'Crucial', 'SSD-910', '2TB', '2.5"', 'PCIe 5.0', 3390, 4393, false, 51.29, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (196, 'Crucial', 'SSD-809', '2TB', '2.5"', 'PCIe 4.0', 6793, 6308, false, 223.96, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (197, 'Samsung', 'SSD-469', '2TB', 'U.2', 'PCIe 3.0', 6981, 2123, false, 339.54, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (198, 'Sabrent', 'SSD-630', '250GB', '2.5"', 'PCIe 3.0', 1096, 6080, true, 272.76, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (199, 'Sabrent', 'SSD-158', '2TB', 'U.2', 'PCIe 3.0', 3108, 3746, false, 91.95, 'https://example.com/ssd', NULL);
INSERT INTO public.ssd VALUES (200, 'ADATA', 'SSD-587', '250GB', 'M.2', 'PCIe 5.0', 3537, 5346, true, 137.81, 'https://example.com/ssd', NULL);


--
-- Data for Name: user_info; Type: TABLE DATA; Schema: public; Owner: new_user
--

INSERT INTO public.user_info VALUES (1, 'alexsmith', 'alexsmith@example.com', 'A1B2C3D4E5F6G7H8');
INSERT INTO public.user_info VALUES (2, 'janedoe', 'janedoe@example.com', 'H1G2F3E4D5C6B7A8');
INSERT INTO public.user_info VALUES (3, 'johnwick', 'johnwick@example.com', 'Z9Y8X7W6V5U4T3S2');
INSERT INTO public.user_info VALUES (4, 'emilywhite', 'emilywhite@example.com', 'M1N2B3V4C5X6Z7Q8');
INSERT INTO public.user_info VALUES (5, 'michaelb', 'michaelb@example.com', 'L8K7J6H5G4F3D2S1');
INSERT INTO public.user_info VALUES (6, 'sarahkhan', 'sarahkhan@example.com', 'P1O2I3U4Y5T6R7E8');
INSERT INTO public.user_info VALUES (7, 'davidlee', 'davidlee@example.com', 'N8M7L6K5J4H3G2F1');
INSERT INTO public.user_info VALUES (8, 'laurenj', 'laurenj@example.com', 'E1R2T3Y4U5I6O7P8');
INSERT INTO public.user_info VALUES (9, 'jasonx', 'jasonx@example.com', 'C9X8Z7A6S5D4F3G2');
INSERT INTO public.user_info VALUES (10, 'olivert', 'olivert@example.com', 'Q1W2E3R4T5Y6U7I8');
INSERT INTO public.user_info VALUES (11, 'hannahb', 'hannahb@example.com', 'I8U7Y6T5R4E3W2Q1');
INSERT INTO public.user_info VALUES (12, 'nathanf', 'nathanf@example.com', 'B1V2C3X4Z5A6S7D8');
INSERT INTO public.user_info VALUES (13, 'chloew', 'chloew@example.com', 'T8Y7U6I5O4P3L2K1');
INSERT INTO public.user_info VALUES (14, 'ethanr', 'ethanr@example.com', 'Z1X2C3V4B5N6M7L8');
INSERT INTO public.user_info VALUES (15, 'ameliag', 'ameliag@example.com', 'J8K7L6M5N4B3V2C1');
INSERT INTO public.user_info VALUES (16, 'benjaminc', 'benjaminc@example.com', 'W1E2R3T4Y5U6I7O8');
INSERT INTO public.user_info VALUES (17, 'graceh', 'graceh@example.com', 'S9D8F7G6H5J4K3L2');
INSERT INTO public.user_info VALUES (18, 'lucaso', 'lucaso@example.com', 'P8O7I6U5Y4T3R2E1');
INSERT INTO public.user_info VALUES (19, 'avaw', 'avaw@example.com', 'A2S3D4F5G6H7J8K9');
INSERT INTO public.user_info VALUES (20, 'jackm', 'jackm@example.com', 'L1K2J3H4G5F6D7S8');


--
-- Name: casing_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.casing_id_seq', 200, true);


--
-- Name: cpu_cooler_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.cpu_cooler_id_seq', 200, true);


--
-- Name: cpu_new_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.cpu_new_id_seq', 1, false);


--
-- Name: gpu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.gpu_id_seq', 200, true);


--
-- Name: motherboard_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.motherboard_id_seq', 150, true);


--
-- Name: psu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.psu_id_seq', 200, true);


--
-- Name: ram_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.ram_id_seq', 200, true);


--
-- Name: saved_builds_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.saved_builds_id_seq', 2, true);


--
-- Name: ssd_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.ssd_id_seq', 200, true);


--
-- Name: user_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: new_user
--

SELECT pg_catalog.setval('public.user_info_id_seq', 20, true);


--
-- Name: casing casing_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.casing
    ADD CONSTRAINT casing_pkey PRIMARY KEY (id);


--
-- Name: cpu_cooler cpu_cooler_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.cpu_cooler
    ADD CONSTRAINT cpu_cooler_pkey PRIMARY KEY (id);


--
-- Name: cpu cpu_new_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.cpu
    ADD CONSTRAINT cpu_new_pkey PRIMARY KEY (id);


--
-- Name: gpu gpu_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.gpu
    ADD CONSTRAINT gpu_pkey PRIMARY KEY (id);


--
-- Name: motherboard motherboard_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.motherboard
    ADD CONSTRAINT motherboard_pkey PRIMARY KEY (id);


--
-- Name: psu psu_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.psu
    ADD CONSTRAINT psu_pkey PRIMARY KEY (id);


--
-- Name: ram ram_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.ram
    ADD CONSTRAINT ram_pkey PRIMARY KEY (id);


--
-- Name: saved_builds saved_builds_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_pkey PRIMARY KEY (id);


--
-- Name: ssd ssd_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.ssd
    ADD CONSTRAINT ssd_pkey PRIMARY KEY (id);


--
-- Name: cpu uk4kpqhgjphun7t95jdlij6b4al; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.cpu
    ADD CONSTRAINT uk4kpqhgjphun7t95jdlij6b4al UNIQUE (model_name);


--
-- Name: user_info user_info_pkey; Type: CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_pkey PRIMARY KEY (id);


--
-- Name: saved_builds saved_builds_casing_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_casing_id_fkey FOREIGN KEY (casing_id) REFERENCES public.casing(id);


--
-- Name: saved_builds saved_builds_cpu_cooler_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_cpu_cooler_id_fkey FOREIGN KEY (cpu_cooler_id) REFERENCES public.cpu_cooler(id);


--
-- Name: saved_builds saved_builds_cpu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_cpu_id_fkey FOREIGN KEY (cpu_id) REFERENCES public.cpu(id);


--
-- Name: saved_builds saved_builds_gpu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_gpu_id_fkey FOREIGN KEY (gpu_id) REFERENCES public.gpu(id);


--
-- Name: saved_builds saved_builds_motherboard_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_motherboard_id_fkey FOREIGN KEY (motherboard_id) REFERENCES public.motherboard(id);


--
-- Name: saved_builds saved_builds_psu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_psu_id_fkey FOREIGN KEY (psu_id) REFERENCES public.psu(id);


--
-- Name: saved_builds saved_builds_ram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_ram_id_fkey FOREIGN KEY (ram_id) REFERENCES public.ram(id);


--
-- Name: saved_builds saved_builds_ssd_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_ssd_id_fkey FOREIGN KEY (ssd_id) REFERENCES public.ssd(id);


--
-- Name: saved_builds saved_builds_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: new_user
--

ALTER TABLE ONLY public.saved_builds
    ADD CONSTRAINT saved_builds_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

