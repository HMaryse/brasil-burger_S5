--
-- PostgreSQL database dump
--

\restrict S7vKxwwodOR57pMY5DRUibNUF7ZuJ2y5enzXaOHCj7qypLYxvMtOGf9abshMsCH

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2025-12-09 00:44:27

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 865 (class 1247 OID 16417)
-- Name: etat_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.etat_type AS ENUM (
    'DISPONIBLE',
    'INDISPONIBLE'
);


ALTER TYPE public.etat_type OWNER TO postgres;

--
-- TOC entry 868 (class 1247 OID 16422)
-- Name: mode_consommation_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.mode_consommation_type AS ENUM (
    'SUR_PLACE',
    'A_EMPORTER',
    'LIVRAISON'
);


ALTER TYPE public.mode_consommation_type OWNER TO postgres;

--
-- TOC entry 871 (class 1247 OID 16430)
-- Name: paiement_mode_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.paiement_mode_type AS ENUM (
    'WAVE',
    'OM',
    'CASH'
);


ALTER TYPE public.paiement_mode_type OWNER TO postgres;

--
-- TOC entry 874 (class 1247 OID 16438)
-- Name: statut_paiement_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.statut_paiement_type AS ENUM (
    'EN_ATTENTE',
    'VALIDÉ',
    'REFUSÉ'
);


ALTER TYPE public.statut_paiement_type OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16457)
-- Name: burger; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.burger (
    id integer NOT NULL,
    nom character varying(150) NOT NULL,
    prix numeric(10,2) NOT NULL,
    image character varying(255),
    etat public.etat_type DEFAULT 'DISPONIBLE'::public.etat_type NOT NULL
);


ALTER TABLE public.burger OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16456)
-- Name: burger_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.burger_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.burger_id_seq OWNER TO postgres;

--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 219
-- Name: burger_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.burger_id_seq OWNED BY public.burger.id;


--
-- TOC entry 218 (class 1259 OID 16446)
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    id integer NOT NULL,
    nom_complet character varying(150) NOT NULL,
    telephone character varying(30) NOT NULL,
    adresse text
);


ALTER TABLE public.client OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16445)
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.client_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.client_id_seq OWNER TO postgres;

--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 217
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;


--
-- TOC entry 230 (class 1259 OID 16508)
-- Name: commande; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commande (
    id integer NOT NULL,
    client_id integer NOT NULL,
    livreur_id integer,
    date_commande timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    statut character varying(30) DEFAULT 'EN_COURS'::character varying NOT NULL,
    mode_consommation public.mode_consommation_type NOT NULL,
    adresse text,
    est_paye boolean DEFAULT false
);


ALTER TABLE public.commande OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16507)
-- Name: commande_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.commande_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.commande_id_seq OWNER TO postgres;

--
-- TOC entry 4927 (class 0 OID 0)
-- Dependencies: 229
-- Name: commande_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.commande_id_seq OWNED BY public.commande.id;


--
-- TOC entry 232 (class 1259 OID 16530)
-- Name: commande_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commande_item (
    id integer NOT NULL,
    commande_id integer NOT NULL,
    burger_id integer,
    menu_id integer,
    quantite integer DEFAULT 1 NOT NULL,
    prix_total numeric(10,2) NOT NULL
);


ALTER TABLE public.commande_item OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16553)
-- Name: commande_item_complement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commande_item_complement (
    id integer NOT NULL,
    commande_item_id integer NOT NULL,
    complement_id integer NOT NULL,
    quantite integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.commande_item_complement OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16552)
-- Name: commande_item_complement_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.commande_item_complement_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.commande_item_complement_id_seq OWNER TO postgres;

--
-- TOC entry 4928 (class 0 OID 0)
-- Dependencies: 233
-- Name: commande_item_complement_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.commande_item_complement_id_seq OWNED BY public.commande_item_complement.id;


--
-- TOC entry 231 (class 1259 OID 16529)
-- Name: commande_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.commande_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.commande_item_id_seq OWNER TO postgres;

--
-- TOC entry 4929 (class 0 OID 0)
-- Dependencies: 231
-- Name: commande_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.commande_item_id_seq OWNED BY public.commande_item.id;


--
-- TOC entry 222 (class 1259 OID 16465)
-- Name: complement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.complement (
    id integer NOT NULL,
    nom character varying(150) NOT NULL,
    prix numeric(10,2) NOT NULL,
    image character varying(255),
    etat public.etat_type DEFAULT 'DISPONIBLE'::public.etat_type NOT NULL
);


ALTER TABLE public.complement OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16464)
-- Name: complement_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.complement_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.complement_id_seq OWNER TO postgres;

--
-- TOC entry 4930 (class 0 OID 0)
-- Dependencies: 221
-- Name: complement_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.complement_id_seq OWNED BY public.complement.id;


--
-- TOC entry 228 (class 1259 OID 16499)
-- Name: livreur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.livreur (
    id integer NOT NULL,
    nom_complet character varying(150) NOT NULL,
    telephone character varying(30) NOT NULL
);


ALTER TABLE public.livreur OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16498)
-- Name: livreur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.livreur_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.livreur_id_seq OWNER TO postgres;

--
-- TOC entry 4931 (class 0 OID 0)
-- Dependencies: 227
-- Name: livreur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.livreur_id_seq OWNED BY public.livreur.id;


--
-- TOC entry 224 (class 1259 OID 16473)
-- Name: menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu (
    id integer NOT NULL,
    nom character varying(150) NOT NULL,
    image character varying(255),
    etat public.etat_type DEFAULT 'DISPONIBLE'::public.etat_type NOT NULL
);


ALTER TABLE public.menu OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16481)
-- Name: menu_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu_detail (
    id integer NOT NULL,
    menu_id integer NOT NULL,
    burger_id integer NOT NULL,
    quantite integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.menu_detail OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16480)
-- Name: menu_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.menu_detail_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.menu_detail_id_seq OWNER TO postgres;

--
-- TOC entry 4932 (class 0 OID 0)
-- Dependencies: 225
-- Name: menu_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.menu_detail_id_seq OWNED BY public.menu_detail.id;


--
-- TOC entry 223 (class 1259 OID 16472)
-- Name: menu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.menu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.menu_id_seq OWNER TO postgres;

--
-- TOC entry 4933 (class 0 OID 0)
-- Dependencies: 223
-- Name: menu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.menu_id_seq OWNED BY public.menu.id;


--
-- TOC entry 236 (class 1259 OID 16571)
-- Name: paiement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.paiement (
    id integer NOT NULL,
    commande_id integer NOT NULL,
    montant numeric(10,2) NOT NULL,
    date_paiement timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    mode_paiement public.paiement_mode_type NOT NULL,
    statut_paiement public.statut_paiement_type DEFAULT 'VALIDÉ'::public.statut_paiement_type NOT NULL
);


ALTER TABLE public.paiement OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16570)
-- Name: paiement_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.paiement_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.paiement_id_seq OWNER TO postgres;

--
-- TOC entry 4934 (class 0 OID 0)
-- Dependencies: 235
-- Name: paiement_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.paiement_id_seq OWNED BY public.paiement.id;


--
-- TOC entry 4699 (class 2604 OID 16460)
-- Name: burger id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.burger ALTER COLUMN id SET DEFAULT nextval('public.burger_id_seq'::regclass);


--
-- TOC entry 4698 (class 2604 OID 16449)
-- Name: client id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);


--
-- TOC entry 4708 (class 2604 OID 16511)
-- Name: commande id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande ALTER COLUMN id SET DEFAULT nextval('public.commande_id_seq'::regclass);


--
-- TOC entry 4712 (class 2604 OID 16533)
-- Name: commande_item id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item ALTER COLUMN id SET DEFAULT nextval('public.commande_item_id_seq'::regclass);


--
-- TOC entry 4714 (class 2604 OID 16556)
-- Name: commande_item_complement id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item_complement ALTER COLUMN id SET DEFAULT nextval('public.commande_item_complement_id_seq'::regclass);


--
-- TOC entry 4701 (class 2604 OID 16468)
-- Name: complement id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.complement ALTER COLUMN id SET DEFAULT nextval('public.complement_id_seq'::regclass);


--
-- TOC entry 4707 (class 2604 OID 16502)
-- Name: livreur id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livreur ALTER COLUMN id SET DEFAULT nextval('public.livreur_id_seq'::regclass);


--
-- TOC entry 4703 (class 2604 OID 16476)
-- Name: menu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu ALTER COLUMN id SET DEFAULT nextval('public.menu_id_seq'::regclass);


--
-- TOC entry 4705 (class 2604 OID 16484)
-- Name: menu_detail id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_detail ALTER COLUMN id SET DEFAULT nextval('public.menu_detail_id_seq'::regclass);


--
-- TOC entry 4716 (class 2604 OID 16574)
-- Name: paiement id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paiement ALTER COLUMN id SET DEFAULT nextval('public.paiement_id_seq'::regclass);


--
-- TOC entry 4903 (class 0 OID 16457)
-- Dependencies: 220
-- Data for Name: burger; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.burger (id, nom, prix, image, etat) FROM stdin;
\.


--
-- TOC entry 4901 (class 0 OID 16446)
-- Dependencies: 218
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client (id, nom_complet, telephone, adresse) FROM stdin;
\.


--
-- TOC entry 4913 (class 0 OID 16508)
-- Dependencies: 230
-- Data for Name: commande; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commande (id, client_id, livreur_id, date_commande, statut, mode_consommation, adresse, est_paye) FROM stdin;
\.


--
-- TOC entry 4915 (class 0 OID 16530)
-- Dependencies: 232
-- Data for Name: commande_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commande_item (id, commande_id, burger_id, menu_id, quantite, prix_total) FROM stdin;
\.


--
-- TOC entry 4917 (class 0 OID 16553)
-- Dependencies: 234
-- Data for Name: commande_item_complement; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commande_item_complement (id, commande_item_id, complement_id, quantite) FROM stdin;
\.


--
-- TOC entry 4905 (class 0 OID 16465)
-- Dependencies: 222
-- Data for Name: complement; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.complement (id, nom, prix, image, etat) FROM stdin;
\.


--
-- TOC entry 4911 (class 0 OID 16499)
-- Dependencies: 228
-- Data for Name: livreur; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.livreur (id, nom_complet, telephone) FROM stdin;
\.


--
-- TOC entry 4907 (class 0 OID 16473)
-- Dependencies: 224
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.menu (id, nom, image, etat) FROM stdin;
\.


--
-- TOC entry 4909 (class 0 OID 16481)
-- Dependencies: 226
-- Data for Name: menu_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.menu_detail (id, menu_id, burger_id, quantite) FROM stdin;
\.


--
-- TOC entry 4919 (class 0 OID 16571)
-- Dependencies: 236
-- Data for Name: paiement; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.paiement (id, commande_id, montant, date_paiement, mode_paiement, statut_paiement) FROM stdin;
\.


--
-- TOC entry 4935 (class 0 OID 0)
-- Dependencies: 219
-- Name: burger_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.burger_id_seq', 1, false);


--
-- TOC entry 4936 (class 0 OID 0)
-- Dependencies: 217
-- Name: client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.client_id_seq', 1, false);


--
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 229
-- Name: commande_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.commande_id_seq', 1, false);


--
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 233
-- Name: commande_item_complement_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.commande_item_complement_id_seq', 1, false);


--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 231
-- Name: commande_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.commande_item_id_seq', 1, false);


--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 221
-- Name: complement_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.complement_id_seq', 1, false);


--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 227
-- Name: livreur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.livreur_id_seq', 1, false);


--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 225
-- Name: menu_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.menu_detail_id_seq', 1, false);


--
-- TOC entry 4943 (class 0 OID 0)
-- Dependencies: 223
-- Name: menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.menu_id_seq', 1, false);


--
-- TOC entry 4944 (class 0 OID 0)
-- Dependencies: 235
-- Name: paiement_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.paiement_id_seq', 1, false);


--
-- TOC entry 4724 (class 2606 OID 16463)
-- Name: burger burger_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.burger
    ADD CONSTRAINT burger_pkey PRIMARY KEY (id);


--
-- TOC entry 4720 (class 2606 OID 16453)
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- TOC entry 4722 (class 2606 OID 16455)
-- Name: client client_telephone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_telephone_key UNIQUE (telephone);


--
-- TOC entry 4740 (class 2606 OID 16559)
-- Name: commande_item_complement commande_item_complement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item_complement
    ADD CONSTRAINT commande_item_complement_pkey PRIMARY KEY (id);


--
-- TOC entry 4738 (class 2606 OID 16536)
-- Name: commande_item commande_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item
    ADD CONSTRAINT commande_item_pkey PRIMARY KEY (id);


--
-- TOC entry 4736 (class 2606 OID 16518)
-- Name: commande commande_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande
    ADD CONSTRAINT commande_pkey PRIMARY KEY (id);


--
-- TOC entry 4726 (class 2606 OID 16471)
-- Name: complement complement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.complement
    ADD CONSTRAINT complement_pkey PRIMARY KEY (id);


--
-- TOC entry 4732 (class 2606 OID 16504)
-- Name: livreur livreur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livreur
    ADD CONSTRAINT livreur_pkey PRIMARY KEY (id);


--
-- TOC entry 4734 (class 2606 OID 16506)
-- Name: livreur livreur_telephone_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livreur
    ADD CONSTRAINT livreur_telephone_key UNIQUE (telephone);


--
-- TOC entry 4730 (class 2606 OID 16487)
-- Name: menu_detail menu_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_detail
    ADD CONSTRAINT menu_detail_pkey PRIMARY KEY (id);


--
-- TOC entry 4728 (class 2606 OID 16479)
-- Name: menu menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id);


--
-- TOC entry 4742 (class 2606 OID 16580)
-- Name: paiement paiement_commande_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paiement
    ADD CONSTRAINT paiement_commande_id_key UNIQUE (commande_id);


--
-- TOC entry 4744 (class 2606 OID 16578)
-- Name: paiement paiement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paiement
    ADD CONSTRAINT paiement_pkey PRIMARY KEY (id);


--
-- TOC entry 4747 (class 2606 OID 16519)
-- Name: commande commande_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande
    ADD CONSTRAINT commande_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- TOC entry 4749 (class 2606 OID 16542)
-- Name: commande_item commande_item_burger_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item
    ADD CONSTRAINT commande_item_burger_id_fkey FOREIGN KEY (burger_id) REFERENCES public.burger(id);


--
-- TOC entry 4750 (class 2606 OID 16537)
-- Name: commande_item commande_item_commande_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item
    ADD CONSTRAINT commande_item_commande_id_fkey FOREIGN KEY (commande_id) REFERENCES public.commande(id);


--
-- TOC entry 4752 (class 2606 OID 16560)
-- Name: commande_item_complement commande_item_complement_commande_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item_complement
    ADD CONSTRAINT commande_item_complement_commande_item_id_fkey FOREIGN KEY (commande_item_id) REFERENCES public.commande_item(id);


--
-- TOC entry 4753 (class 2606 OID 16565)
-- Name: commande_item_complement commande_item_complement_complement_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item_complement
    ADD CONSTRAINT commande_item_complement_complement_id_fkey FOREIGN KEY (complement_id) REFERENCES public.complement(id);


--
-- TOC entry 4751 (class 2606 OID 16547)
-- Name: commande_item commande_item_menu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande_item
    ADD CONSTRAINT commande_item_menu_id_fkey FOREIGN KEY (menu_id) REFERENCES public.menu(id);


--
-- TOC entry 4748 (class 2606 OID 16524)
-- Name: commande commande_livreur_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commande
    ADD CONSTRAINT commande_livreur_id_fkey FOREIGN KEY (livreur_id) REFERENCES public.livreur(id);


--
-- TOC entry 4745 (class 2606 OID 16493)
-- Name: menu_detail menu_detail_burger_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_detail
    ADD CONSTRAINT menu_detail_burger_id_fkey FOREIGN KEY (burger_id) REFERENCES public.burger(id);


--
-- TOC entry 4746 (class 2606 OID 16488)
-- Name: menu_detail menu_detail_menu_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_detail
    ADD CONSTRAINT menu_detail_menu_id_fkey FOREIGN KEY (menu_id) REFERENCES public.menu(id);


--
-- TOC entry 4754 (class 2606 OID 16581)
-- Name: paiement paiement_commande_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paiement
    ADD CONSTRAINT paiement_commande_id_fkey FOREIGN KEY (commande_id) REFERENCES public.commande(id);


-- Completed on 2025-12-09 00:44:29

--
-- PostgreSQL database dump complete
--

\unrestrict S7vKxwwodOR57pMY5DRUibNUF7ZuJ2y5enzXaOHCj7qypLYxvMtOGf9abshMsCH

