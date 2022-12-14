PGDMP                         z            market    14.3    14.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16537    market    DATABASE     c   CREATE DATABASE market WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE market;
                postgres    false            ?            1259    16541    products    TABLE     ?   CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying NOT NULL,
    price integer NOT NULL,
    quantity integer NOT NULL,
    description character varying,
    version bigint
);
    DROP TABLE public.products;
       public         heap    postgres    false            ?            1259    16540    products_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.products_id_seq;
       public          postgres    false    210                       0    0    products_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;
          public          postgres    false    209            ?            1259    16550 	   purchases    TABLE     ?   CREATE TABLE public.purchases (
    id integer NOT NULL,
    user_id integer,
    product_id integer,
    date date,
    sum integer,
    quantity integer
);
    DROP TABLE public.purchases;
       public         heap    postgres    false            ?            1259    16549    purchases_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.purchases_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.purchases_id_seq;
       public          postgres    false    212                       0    0    purchases_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.id;
          public          postgres    false    211            ?            1259    16557    users    TABLE     ?   CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying,
    password character varying,
    is_admin boolean
);
    DROP TABLE public.users;
       public         heap    postgres    false            ?            1259    16556    users_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    214            	           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    213            f           2604    16544    products id    DEFAULT     j   ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);
 :   ALTER TABLE public.products ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    209    210            g           2604    16553    purchases id    DEFAULT     l   ALTER TABLE ONLY public.purchases ALTER COLUMN id SET DEFAULT nextval('public.purchases_id_seq'::regclass);
 ;   ALTER TABLE public.purchases ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    212    211    212            h           2604    16560    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    213    214            ?          0    16541    products 
   TABLE DATA           S   COPY public.products (id, name, price, quantity, description, version) FROM stdin;
    public          postgres    false    210   Y       ?          0    16550 	   purchases 
   TABLE DATA           Q   COPY public.purchases (id, user_id, product_id, date, sum, quantity) FROM stdin;
    public          postgres    false    212   ?                  0    16557    users 
   TABLE DATA           =   COPY public.users (id, name, password, is_admin) FROM stdin;
    public          postgres    false    214   ?       
           0    0    products_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.products_id_seq', 49, true);
          public          postgres    false    209                       0    0    purchases_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.purchases_id_seq', 17, true);
          public          postgres    false    211                       0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 25, true);
          public          postgres    false    213            j           2606    16548    products products_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.products DROP CONSTRAINT products_pk;
       public            postgres    false    210            l           2606    16555    purchases purchases_pk 
   CONSTRAINT     T   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pk PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.purchases DROP CONSTRAINT purchases_pk;
       public            postgres    false    212            o           2606    16565    users users_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pk;
       public            postgres    false    214            m           1259    16563    users_id_uindex    INDEX     F   CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);
 #   DROP INDEX public.users_id_uindex;
       public            postgres    false    214            ?   ?   x?5?=
?0??Y>?NP??z,??B?B?.?-? X??!??Kj???i???K??/??vus???h?a??.v??|I?q?\\??+<(J8???x?%;?S?5R?{[<????<???3?$????h?h???R_?2?      ?   =   x?U˱?0???%?vH????s ?@ԧ?@`DI?j9?K??yC?O
??)???⹑? ??          !   x?3?LL??̃?%\F??ũE"?+F??? ?(	?     